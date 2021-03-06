package lottery.parameters;

import java.io.File;

import lottery.parameters.Parameters.Command;
import lottery.settings.BitcoinLotterySettings;


public class CommandParser {

	public static class CommandArg {
		public CommandArg(Command command, String dir, boolean testnet) {
			this.command = command;
			this.root = dir;
			this.testnet = testnet;
		}
		
		public Command command;
		public String root;
		public boolean testnet;
	}
	
	public static CommandArg parse(String[] args) {
		String root = new File(System.getProperty("user.home"), BitcoinLotterySettings.defaultDir).getAbsolutePath();
		boolean testnet = false;
		CommandArg defaultCommand = new CommandArg(Command.HELP, root, testnet); 
		if (args.length < 1 || args.length > 3) {
			return defaultCommand;
		}
		else {
			for (int k = 1; k < args.length; ++k) {
				root = getDir(args[k]) == null ? root : getDir(args[k]);
				testnet = isTestnet(args[k]) == false ? testnet : isTestnet(args[k]);
				//TODO: else return HELP
			}
			Command command = getCommand(args[0]);
			return new CommandArg(command, root, testnet);
		}
	}
	
	protected static boolean isTestnet(String arg) {
		return arg.equals(BitcoinLotterySettings.argTestnet);
	}

	//returns null if arg is not of the form --dir=<path>
	protected static String getDir(String arg) {
		if (arg.startsWith(BitcoinLotterySettings.argDirPrefix)) {
			return arg.substring(BitcoinLotterySettings.argDirPrefix.length());
		}
		else {
			return null;
		}
	}
	
	//returns Command.HELP if arg is not a proper command	
	protected static Command getCommand(String arg) {
		if (arg.equals(BitcoinLotterySettings.argVersion))
			return Command.VERSION;
		if (arg.equals(BitcoinLotterySettings.argGen))
			return Command.GENERATE_KEYS;
		if (arg.equals(BitcoinLotterySettings.argClaimMoney))
			return Command.CLAIM_MONEY;
		if (arg.equals(BitcoinLotterySettings.argOpen))
			return Command.OPEN;
		if (arg.equals(BitcoinLotterySettings.argLottery))
			return Command.LOTTERY;
		else
			return Command.HELP;
	}
}
