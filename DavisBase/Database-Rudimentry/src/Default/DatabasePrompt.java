package Default;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import QueryUtil.QueryParser;

/**
 * 
 *  @version 1.0
 *  <b>
 *  <p>This is an example of how to create an interactive prompt</p>
 *  <p>There is also some guidance to get started with read/write of
 *     binary data files using RandomAccessFile class</p>
 *  </b>
 *
 */
   
public class DatabasePrompt {
	static String prompt = "davisql> ";
	static String version = "v1.0b(example)";
	static String copyright = "©2016 Chris Irwin Davis";
	static boolean isExit = false;
	/*
	 * Page size for all files is 512 bytes by default.
	 * You may choose to make it user modifiable
	 */
	static long pageSize = 512; 

	/* 
	 *  The Scanner class is used to collect user commands from the prompt
	 *  There are many ways to do this. This is just one.
	 *
	 *  Each time the semicolon (;) delimiter is entered, the userCommand 
	 *  String is re-populated.
	 */
	static Scanner scanner = new Scanner(System.in).useDelimiter(";");

	/** ***********************************************************************
	 *  Main method
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		/* Display the welcome screen */
		splashScreen();

		/* Variable to collect user input from the prompt */
		QueryParser.Init();
		String userCommand = ""; 

		while(!isExit) {
			System.out.print(prompt);
			/* toLowerCase() renders command case insensitive */
			userCommand = scanner.next().replace("\n", " ").replace("\r", "").replace("\t","").trim().toLowerCase();
			parseUserCommand(userCommand);
		}
		System.out.println("Exiting...");


	}

	/** ***********************************************************************
	 *  Static method definitions
	 */

	/**
	 *  Display the splash screen
	 */
	public static void splashScreen() {
		System.out.println(line("-",80));
		System.out.println("Welcome to DavisBaseLite"); // Display the string.
		System.out.println("DavisBaseLite Version " + getVersion());
		System.out.println(getCopyright());
		System.out.println("\nType \"help;\" to display supported commands.");
		System.out.println(line("-",80));
	}

	/**
	 * @param s The String to be repeated
	 * @param num The number of time to repeat String s.
	 * @return String A String object, which is the String s appended to itself num times.
	 */
	public static String line(String s,int num) {
		String a = "";
		for(int i=0;i<num;i++) {
			a += s;
		}
		return a;
	}

	public static void printCmd(String s) {
		System.out.println("\n\t" + s + "\n");
	}
	public static void printDef(String s) {
		System.out.println("\t\t" + s);
	}

	/**
	 *  Help: Display supported commands
	 */
	public static void help() {
		System.out.println(line("*",80));
		System.out.println("SUPPORTED COMMANDS\n");
		System.out.println("All commands below are case insensitive\n");
		System.out.println("SHOW TABLES;");
		System.out.println("\tDisplay the names of all tables.\n");
		System.out.println("SELECT <column_list> FROM <table_name> [WHERE <condition>];");
		System.out.println("\tDisplay table records whose optional <condition>");
		System.out.println("\tis <column_name> = <value>.\n");
		System.out.println("DROP TABLE <table_name>;");
		System.out.println("\tRemove table data (i.e. all records) and its schema.\n");
		System.out.println("UPDATE TABLE <table_name> SET <column_name> = <value> [WHERE <condition>];");
		System.out.println("\tModify records data whose optional <condition> is\n");
		System.out.println("VERSION;");
		System.out.println("\tDisplay the program version.\n");
		System.out.println("HELP;");
		System.out.println("\tDisplay this help information.\n");
		System.out.println("EXIT;");
		System.out.println("\tExit the program.\n");
		System.out.println(line("*",80));
	}

	/** return the DavisBase version */
	public static String getVersion() {
		return version;
	}

	public static String getCopyright() {
		return copyright;
	}

	public static void displayVersion() {
		System.out.println("DavisBaseLite Version " + getVersion());
		System.out.println(getCopyright());
	}

	public static void parseUserCommand (String userCommand) {

		/* commandTokens is an array of Strings that contains one token per array element 
		 * The first token can be used to determine the type of command 
		 * The other tokens can be used to pass relevant parameters to each command-specific
		 * method inside each case statement */
		// String[] commandTokens = userCommand.split(" ");
		ArrayList<String> commandTokens = new ArrayList<String>(Arrays.asList(userCommand.split(" ")));
		//handles tabs does not handle space before a \n
		
		/*
		 *  This switch handles a very small list of hardcoded commands of known syntax.
		 *  You will want to rewrite this method to interpret more complex commands. 
		 */
		switch (commandTokens.get(0)) {
		case "insert":
			try {
				QueryParser.InsertIntoTableCommand(userCommand);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Command execution unsuccessful. Check the query");
//				e.printStackTrace();
			}
			break;
		case "delete":
			try {
				QueryParser.DeleteCommand(userCommand);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Command execution unsuccessful. Check the query");
//				e.printStackTrace();
			}
			System.out.println("delete command");
			break;
		case "update":
			try {
				QueryParser.UpdateCommand(userCommand);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Command execution unsuccessful. Check the query");
				e.printStackTrace();
			}
			break;
		case "select":
			try {
				QueryParser.ParseQueryStringCommand(userCommand);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Command execution unsuccessful. Check the query" + e.getMessage());
//				e.printStackTrace();
			}
			break;
		case "show":
			String token = commandTokens.get(1).toLowerCase().trim();
//			if(token.equals("databases")){
//				try {
//					QueryParser.showDatabase(userCommand);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else if(token.equals("tables")){
				try {
					QueryParser.ShowTableCommand(userCommand);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Command execution unsuccessful. Check the query");
//					e.printStackTrace();
				}
		    break;
		case "drop":
//			if(commandTokens.get(1).toLowerCase().trim().equals("database")){
//				try {
//					QueryParser.dropDatabase(userCommand);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else{
				try {
					QueryParser.DropTableCommand(userCommand);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Command execution unsuccessful. Check the query");
//					e.printStackTrace();
				}
			break;
		case "create":
//			if(commandTokens.get(1).toLowerCase().trim().equals("database")){
//				//create database
//				try {
//					QueryParser.createDatabase(userCommand);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else{
				//create table
				QueryParser.ParseCreateCommand(userCommand);
			break;
//		case "use":
//			try {
//				QueryParser.usedatabase(userCommand);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			break;
		case "help":
			help();
			break;
		case "version":
			displayVersion();
			break;
		case "exit":
			isExit = true;
			break;
		case "quit":
			isExit = true;
			break;
		default:
			System.out.println("I didn't understand the command: \"" + userCommand + "\"");
		}
	}
}
