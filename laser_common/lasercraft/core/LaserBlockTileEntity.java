package lasercraft.core;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

import org.lwjgl.util.vector.Vector3f;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LaserBlockTileEntity extends TileEntity {

    private enum LaserState {
        IDLE, MOVING, PROCESSING;
    }

    private int[][][]        scan                = new int[21][10][21];
    private ChunkCoordinates target              = null;

    private float            oldYaw              = 0f;
    private float            oldPitch            = 0f;

    private float            interpolation       = 0f;
    private float            processing_progress = 0f;

    private LaserState       State               = LaserState.IDLE;

    private LaserState       oldState;

    public LaserBlockTileEntity() {

    }

    public boolean hasReachedTarget() {
        return hasTarget() && interpolation >= 1f;
    }

    public boolean hasFinishedProcessing() {
        return processing_progress >= 1f;
    }

    public boolean hasTarget() {
        return target != null;
    }

    public void setTarget(ChunkCoordinates newTarget) {
        saveCurrentRotation();

        target = newTarget;

        interpolation = 0f;
    }

    @Override
    public void updateEntity() {
        /*if (!(worldObj.getWorldTime() % 2 == 0f)) return;

        if (oldState != State) {
            System.out.println("("
                    + FMLCommonHandler.instance().getEffectiveSide().name()
                    + ") Changed from " + oldState + " to " + State);
        }
        oldState = State;*/
        scan();
        if (State == LaserState.IDLE) {
            aquireNewTarget();
            if (hasTarget()) {
                interpolation = 0f;
                oldState = State;
                State = LaserState.MOVING;
            }
        } else if (State == LaserState.MOVING) {
            if (hasTarget()) {
                interpolation += 1f / 50f;
            } else {
                saveCurrentRotation();
                interpolation = 0f;
                oldState = State;
                State = LaserState.IDLE;
            }
            if (hasReachedTarget()) {
                /*System.out.println("("
                        + FMLCommonHandler.instance().getEffectiveSide().name()
                        + ") Reached target");*/
                interpolation = 1f;
                oldState = State;
                State = LaserState.PROCESSING;
            }
        } else if (State == LaserState.PROCESSING) {
            if (!hasTarget()) {
                saveCurrentRotation();
                interpolation = 0f;
                processing_progress = 0f;
                oldState = State;
                State = LaserState.IDLE;
            } else {
                processing_progress += 1f / 2f;
                if (hasFinishedProcessing()) {
                    saveCurrentRotation();
                    doneProcessing();
                    interpolation = 0f;
                    processing_progress = 0f;
                    oldState = State;
                    State = LaserState.IDLE;
                }
            }
        }
    }

    private void doneProcessing() {
        /*System.out.println("("
                + FMLCommonHandler.instance().getEffectiveSide().name()
                + ") poof");*/
        destroyTargetBlock();
        target = null;
    }

    private void saveCurrentRotation() {
        /*System.out.println("("
                + FMLCommonHandler.instance().getEffectiveSide().name()
                + ") Saved: " + interpolateAngles().y + " "
                + interpolateAngles().x);*/
        oldYaw = interpolateAngles().y;
        oldPitch = interpolateAngles().x;
    }

    private void destroyTargetBlock() {
        int ID = worldObj.getBlockId(target.posX, target.posY, target.posZ);
        /*System.out.println("("
                + FMLCommonHandler.instance().getEffectiveSide().name()
                + ") ID: " + ID);*/
        Block block = Block.blocksList[ID];
        if (block != null) {
            /*System.out.println("("
                    + FMLCommonHandler.instance().getEffectiveSide().name()
                    + ") Block: " + block.toString());*/
        } else {
            /*System.out.println("("
                    + FMLCommonHandler.instance().getEffectiveSide().name()
                    + ") Block is null!");*/
            updateScanAtWorld(target.posX, target.posY, target.posZ);
        }
        // block.breakBlock(this.worldObj, target.posX,target.posY,
        // target.posZ,0,this.worldObj.getBlockMetadata(target.posX,
        // target.posY,target.posZ));
        worldObj.setBlock(target.posX, target.posY, target.posZ, 0, 0, 2);
        updateScanAtWorld(target.posX, target.posY, target.posZ);
    }

    private void aquireNewTarget() {
        /*
         * EntityPlayer Player = this.worldObj.getClosestPlayer(xCoord, yCoord,
         * zCoord, 10000); ChunkCoordinates T = new
         * ChunkCoordinates((int)Player.posX,(int)Player.posY,(int)Player.posZ);
         * System.out.println(T.posX+" "+T.posY+" "+T.posZ); setTarget(T);
         */

        ChunkCoordinates T = null;
        for (int x = -10; x <= 10; x++) {
            for (int z = -10; z <= 10; z++) {
                for (int y = 0; y <= 9; y++) {
                    if (getScanAtLocal(x, y, z) > 0
                            && getScanAtLocal(x, y, z) != 503) {
                        int was = getScanAtLocal(x, y, z);
                        System.out.println("("
                                + FMLCommonHandler.instance()
                                        .getEffectiveSide().name()
                                + ") Scan suggests target at " + x + " " + y
                                + " " + z);
                        updateScanAtLocal(x, y, z);
                        if (!(getScanAtLocal(x, y, z) > 0 && getScanAtLocal(x,
                                y, z) != 503)) {
                            System.out.println("("
                                    + FMLCommonHandler.instance()
                                            .getEffectiveSide().name()
                                    + ") Scan suggestion failed recheck (was "
                                    + was + " is " + getScanAtLocal(x, y, z)
                                    + ")");
                            continue;
                        }
                        T = new ChunkCoordinates(xCoord + x, yCoord + y, zCoord
                                + z);
                        System.out.println("("
                                + FMLCommonHandler.instance()
                                        .getEffectiveSide().name()
                                + ") Scan picked target at (local): " + x + " "
                                + y + " " + z);
                        break;
                    }
                }
                if (T != null) {
                    break;
                }
            }
            if (T != null) {
                break;
            }
        }

        if (T != null) {
            setTarget(T);
        } else {
            // System.out.println("("+FMLCommonHandler.instance().getEffectiveSide().name()+") Failed to aquire target");
        }

    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    public void scan() {
        for (int x = -10; x <= 10; x++) {
            for (int z = -10; z <= 10; z++) {
                for (int y = 0; y <= 9; y++) {
                    // System.out.println("scan at (local): " + x + " " + y +
                    // " " + z);
                    if (worldObj
                            .blockExists(xCoord + x, yCoord + y, zCoord + z)) {
                        updateScanAtLocal(x, y, z);
                    }
                }
            }
        }
    }

    private void updateScanAtLocal(int x, int y, int z) {
        scan[x + 10][y][z + 10] = worldObj.getBlockId(xCoord + x, yCoord + y,
                zCoord + z);
    }

    private void updateScanAtWorld(int posX, int posY, int posZ) {
        // System.out.println("Update updated target at (local): "+(posX -
        // this.xCoord + 10)+" "+(posY - this.yCoord)+" "+(posZ - this.zCoord +
        // 10));
        scan[posX - xCoord + 10][posY - yCoord][posZ - zCoord + 10] = worldObj
                .getBlockId(posX, posY, posZ);
    }

    private int getScanAtLocal(int posX, int posY, int posZ) {
        return scan[posX + 10][posY][posZ + 10];
    }

    public Vector3f interpolateAngles() {
        Vector3f result;
        if (hasTarget()) {
            Vector3f interpolatedAngles = new Vector3f();

            ChunkCoordinates Laser_Pos_horiz_plane = new ChunkCoordinates(
                    xCoord, 0, zCoord);
            ChunkCoordinates TargetPos_horiz_plane = new ChunkCoordinates(
                    target.posX, 0, target.posZ);

            float dist_hor_squared = Laser_Pos_horiz_plane
                    .getDistanceSquaredToChunkCoordinates(TargetPos_horiz_plane);

            float dist_vert_squared = (float) Math.pow(target.posY - yCoord, 2);

            float targetPitch = (float) Math.atan2(
                    Math.sqrt(dist_vert_squared), Math.sqrt(dist_hor_squared));
            float targetYaw = (float) Math.atan2(target.posZ - zCoord,
                    target.posX - xCoord);

            interpolatedAngles.x = targetPitch * interpolation + oldPitch
                    * (1 - interpolation);
            interpolatedAngles.y = targetYaw * interpolation + oldYaw
                    * (1 - interpolation);

            interpolatedAngles.z = 0f;

            result = interpolatedAngles;

        } else {
            result = new Vector3f(oldPitch, oldYaw, 0f);
        }

        return result;

    }

    public Vec3 getLaserNuzzle() {
        double x = (float) (.5F + Math.cos(getYaw()) * Math.cos(getPitch())
                * .5F);
        double y = (float) (.5f + Math.sin(getPitch()) * .5F);
        double z = (float) (.5F + Math.sin(getYaw()) * Math.cos(getPitch())
                * .5F);

        Vec3 result = Vec3.createVectorHelper(x, y, z);

        return result;
    }

    public Vec3 getLaserDirection() {
        Vec3 result = Vec3
                .createVectorHelper(Math.cos(getYaw()) * Math.cos(getPitch()),
                        Math.sin(getPitch()),
                        Math.sin(getYaw()) * Math.cos(getPitch()));

        return result;
    }

    public double getLaserLength() {
        Vec3 pos = Vec3.createVectorHelper(xCoord, yCoord, zCoord);

        Vec3 start = pos.addVector(getLaserNuzzle().xCoord,
                getLaserNuzzle().yCoord, getLaserNuzzle().zCoord);

        start = start.addVector(getLaserDirection().xCoord,
                getLaserDirection().yCoord, getLaserDirection().zCoord);

        Vec3 end = start.addVector(getLaserDirection().xCoord * 100,
                getLaserDirection().yCoord * 100,
                getLaserDirection().zCoord * 100);

        MovingObjectPosition MOP = worldObj.rayTraceBlocks(start, end);

        if (MOP != null) {
            Vec3 Hit = Vec3.createVectorHelper(MOP.blockX - xCoord, MOP.blockY
                    - yCoord, MOP.blockZ - zCoord);
            // System.out.println("raytrace not null");
            // System.out.println(MOP.blockX+" "+MOP.blockY+" "+MOP.blockZ);
            // System.out.println(this.worldObj.getBlockId(MOP.blockX,MOP.blockY,MOP.blockZ));

            return (float) Hit.lengthVector() - .5; // Close enough
        } else
            // System.out.println("Start: "+start.toString());
            // System.out.println("End: "+end.toString());
            return 100.0;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        double rad = 20;

        AxisAlignedBB axisalignedbb = AxisAlignedBB
                .getAABBPool()
                .getAABB(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1,
                        zCoord + 1).expand(rad, rad, rad);
        return axisalignedbb;
    }

    public float getYaw() {
        return interpolateAngles().y;
    }

    public float getPitch() {
        return interpolateAngles().x;
    }

}
