package net.sheephaven.core;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.command.ICommand;

public class CommandSheepHaven extends CommandBase {

	@Override
	public int compareTo(Object arg0) {
		return this.getCommandName().compareTo(((ICommand) arg0).getCommandName());
	}

	@Override
	public String getCommandName() {
		return "sheephaven";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/" + this.getCommandName() + " help";
	}

	@Override
	public List getCommandAliases() {
		return null;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] arguments) {
		System.out.println("process");
		if (arguments.length <= 0)
			throw new WrongUsageException("Type '" + this.getCommandUsage(sender) + "' for help.");

		if (arguments[0].matches("test")) {
			System.out.println("test");
			//
			return;
		} else if (arguments[0].matches("help")) {
			sender.sendChatToPlayer("Format: '" + this.getCommandName() + " <command> <arguments>'");
			sender.sendChatToPlayer("Available commands:");
			sender.sendChatToPlayer("- version : Version information.");
			return;
		}

		throw new WrongUsageException(this.getCommandUsage(sender));
	}
}
