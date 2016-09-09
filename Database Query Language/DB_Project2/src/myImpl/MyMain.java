package myImpl;

import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myImpl.model.Column;
import myImpl.model.Table;

/**
 * @author Chris Irwin Davis
 * @version 1.0 <b>This is an example of how to read/write binary data files
 *          using RandomAccessFile class</b>
 *
 */
public class MyMain {
	// This can be changed to whatever you like
	static String prompt = "davisql> ";
	static Scanner scan = new Scanner(System.in);

	/*
	 * This example does not dynamically load a table schema to be able to
	 * read/write any table -- there is exactly ONE hardcoded table schema.
	 * These are the variables associated with that hardecoded table schema.
	 * Your database engine will need to define these on-the-fly from whatever
	 * table schema you use from your information_schema
	 */
	static String widgetTableFileName = "widgets.dat";
	static String tableIdIndexName = "widgets.id.ndx";
	static int id;
	static String name;
	static short quantity;
	static float probability;
	static String currentSchema;
//	static int numberOfSchemas;
	static long indexPointer = 18;
	//static int numberOfTables=0;
	static int indexHandler = 0;
	static String tableFinal;
	static int finalIndex = 0;
	static String tableName;
	static int numberOfEntriesCurrTable;

	public static void main(String[] args) {
		/* Display the welcome splash screen */
		splashScreen();

		/*
		 * Manually create a binary data file for the single hardcoded table. It
		 * inserts 5 hardcoded records. The schema is inherent in the code,
		 * pre-defined, and static.
		 * 
		 * An index file for the ID field is created at the same time.
		 */
		hardCodedCreateTableWithIndex();
		
		try {
			RandomAccessFile masterFile = new RandomAccessFile("schema_info", "r");
			if (masterFile.length() == 0) {
				masterFile.close();
				MakeInformationSchema.main(null);
			} else {
				masterFile.close();
			}
		} catch (Exception e) {
		}
		

		/*
		 * The Scanner class is used to collect user commands from the prompt
		 * There are many ways to do this. This is just one.
		 *
		 * Each time the semicolon (;) delimiter is entered, the userCommand
		 * String is re-populated.
		 */
		Scanner scanner = new Scanner(System.in).useDelimiter(";");
		String userCommand; // Variable to collect user input from the prompt

		do { // do-while !exit
			System.out.print(prompt);
			userCommand = scanner.next().trim();

			/*
			 * This switch handles a very small list of commands of known
			 * syntax. You will probably want to write a parse(userCommand)
			 * method to to interpret more complex commands.
			 */

			

			parse(userCommand);
		} while (!userCommand.equals("exit"));
		System.out.println("Exiting...");
		scanner.close();
	} /* End main() method */

	// ===========================================================================
	// STATIC METHOD DEFINTIONS BEGIN HERE
	// ===========================================================================

	public static void parse(String cmd) {
		// get schema name
		cmd = cmd.toLowerCase();
		String useSchemaName = cmd.substring(cmd.lastIndexOf(" ") + 1);
		
		if (cmd.compareToIgnoreCase(DDLCommands.showSchemas) == 0) {
			displaySchemas();
		} else if (cmd.compareToIgnoreCase(DDLCommands.helpstmt) == 0) {
			help();
		}
		
		else if(cmd.startsWith(DMLCommands.insertTable)) { 
			 insertIntoTable(cmd);
		}
		
		else if (cmd.startsWith(DDLCommands.createTable)) {
			createTable(cmd);
		} 
		else if (cmd.startsWith(DDLCommands.useSchema) && cmd.endsWith(useSchemaName) == true) {
			currentSchema = useSchemaName;
			System.out.println("Databse Changed");
			System.out.println("The current database schema in use is " + currentSchema);
		} 
		else if (cmd.equalsIgnoreCase(DDLCommands.showTables)) {
			displayTables(currentSchema);
		}
		else if (cmd.startsWith(DDLCommands.createSchema) && cmd.endsWith(useSchemaName)) {
			try {
				RandomAccessFile schemasFile = new RandomAccessFile("schema_info", "rw");
				// schemasFile.setLength(0);
				System.out.println(useSchemaName.length());
				schemasFile.seek(schemasFile.length());
				schemasFile.writeByte(useSchemaName.length());
				schemasFile.writeBytes(useSchemaName);
				System.out.println("New Schema '" + useSchemaName + "' is created. ");
				try{
					RandomAccessFile numberSchema = new RandomAccessFile("number_of_schemas","rw");
					numberSchema.seek(0);
					int numberOfSchemas = numberSchema.readInt();
					numberOfSchemas++;
					numberSchema.seek(0);
					numberSchema.writeInt(numberOfSchemas);
					numberSchema.close();
					
				}catch(Exception e){
					System.out.println(e);
				}
			
				// indexPointer = indexPointer+useSchemaName.length();
				// System.out.println(indexPointer);
				schemasFile.close();
			} catch (Exception e) {
				System.out.println(e);
			}

	//		System.out.println("The new number of schemas are:" + numberOfSchemas);
		} 
		else if (cmd.startsWith(DMLCommands.select)) {
			selectTable(cmd);
		} 
		else {
			System.out.println("I don't understand the command");
		}
	}

	private static void insertIntoTable(String cmd) {
		String tablename = cmd.substring(cmd.indexOf(DMLCommands.insertTable) + DMLCommands.insertTable.length()).trim().split(" ")[0];
		Table table;
		try {
			table = Table.loadDataIfPresent(currentSchema, tablename);
			RandomAccessFile tableRandomAccessFile = table.getRandomAccessFile();
			
			tableRandomAccessFile.seek(tableRandomAccessFile.length());
			String valueStatement = cmd.substring(cmd.indexOf("(") + 1, cmd.indexOf(")"));
			tableRandomAccessFile.writeBytes(valueStatement + "\n");
			tableRandomAccessFile.close();
		} catch (Exception e) {
			System.out.println("error while loading table : " + tablename);
			e.printStackTrace();
		}
	}

	private static void selectTable(String cmd) {
		String tablename = cmd.substring(cmd.indexOf(DMLCommands.from) + DMLCommands.from.length()).trim().split(" ")[0];
		
		try {
			Table table = Table.loadDataIfPresent(currentSchema, tablename);
			RandomAccessFile tableRandomAccessFile = table.getRandomAccessFile("r");
			tableRandomAccessFile.seek(0);
			String tableMetaData = tableRandomAccessFile.readLine();
			
			String selectStatement = cmd.substring(cmd.indexOf(DMLCommands.select) + DMLCommands.select.length(), cmd.indexOf(DMLCommands.from));
			String[] selectColNames = null;
			
			if (selectStatement.contains("*")) {
				selectColNames = new String [table.getColumns().size()];
				
				for (int index = 0; index < table.getColumns().size(); index ++)
					selectColNames [index] = table.getColumns().get(index).getName();
			} else {
				selectColNames = selectStatement.split(",");
			}

			int selectIndex = 0;
			int[] selectIndexes = new int [selectColNames.length];
			
			for (String selectColName : selectColNames) {
				int index = 0;
				for (String tableMetaDataItem : tableMetaData.split("#")) {
					if (tableMetaDataItem.split("/")[0].equalsIgnoreCase(selectColName.trim())) 
						break;
					index ++;
				}
				selectIndexes[selectIndex ++] = index;
			}
			Arrays.sort(selectIndexes);

			int position = tableMetaData.length() + "\n".length();
				
			Set<Integer> searchIndexes = null;
			Boolean showNoResults = null;
			
			if (cmd.contains(DMLCommands.where)) {
				String whereClause = cmd.substring(cmd.indexOf(DMLCommands.where) + DMLCommands.where.length());
				
				Column searchColumn = null;
				if (whereClause.contains("=") && (!whereClause.contains("<")) && (!whereClause.contains(">"))) {
					String searchColumnName = whereClause.split("=")[0].trim();
					String searchColumnValue = whereClause.split("=")[1].trim();
					
					for (int index = 0; index < table.getColumns().size(); index ++) {
						if (table.getColumns().get(index).getName().equalsIgnoreCase(searchColumnName))
							searchColumn = table.getColumns().get(index);
					}
					
					List<Integer> searchIndexList = searchColumn.getColumnContent().get(searchColumn.findObject(searchColumnValue));
					
					if (searchIndexList != null) {
						searchIndexes = new TreeSet<>(searchIndexList);
					} else {
						showNoResults = true;
					}
					
				} else if (whereClause.contains(">")) {
					boolean inclusive = (whereClause.contains("="));
					String searchColumnName = whereClause.split((inclusive) ? ">=" : ">")[0].trim();
					String searchColumnValue = whereClause.split((inclusive) ? ">=" : ">")[1].trim();
					
					for (int index = 0; index < table.getColumns().size(); index ++) {
						if (table.getColumns().get(index).getName().equalsIgnoreCase(searchColumnName))
							searchColumn = table.getColumns().get(index);
					}
					
					Map<Object, List<Integer>> rangeMap = searchColumn.getColumnContent().tailMap(searchColumn.findObject(searchColumnValue), inclusive);
					
					if (rangeMap != null && rangeMap.size() > 0) {
						searchIndexes = new TreeSet<>();

						for (Map.Entry<Object, List<Integer>> rangeMapEntry : rangeMap.entrySet())
							searchIndexes.addAll(rangeMapEntry.getValue());
					}
					
				} else if (whereClause.contains("<")) {
					boolean inclusive = (whereClause.contains("="));
					
					String searchColumnName = whereClause.split((inclusive) ? "<=" : "<")[0].trim();
					String searchColumnValue = whereClause.split((inclusive) ? "<=" : "<")[1].trim();
					
					for (int index = 0; index < table.getColumns().size(); index ++) {
						if (table.getColumns().get(index).getName().equalsIgnoreCase(searchColumnName))
							searchColumn = table.getColumns().get(index);
					}
					
					Map<Object, List<Integer>> rangeMap = searchColumn.getColumnContent().headMap(searchColumn.findObject(searchColumnValue), inclusive);
					
					if (rangeMap != null && rangeMap.size() > 0) {
						searchIndexes = new TreeSet<>();

						for (Map.Entry<Object, List<Integer>> rangeMapEntry : rangeMap.entrySet())
							searchIndexes.addAll(rangeMapEntry.getValue());
					}
				}
			}

			int loopcounter = 0;
			while (position < tableRandomAccessFile.length()) {
			
				tableRandomAccessFile.seek(position);
				String entry = tableRandomAccessFile.readLine();
				
				if ((showNoResults == null || (showNoResults != null && !showNoResults)) && (searchIndexes == null || (searchIndexes != null && searchIndexes.contains(loopcounter)))) {
					System.out.print(" > ");
					String[] entryData = entry.split(",");

					for (int index = 0; index < selectIndexes.length; index++) {
						int entryDataIndex = selectIndexes[index];
						System.out.print(entryData[entryDataIndex] + (index < (selectIndexes.length - 1) ? "," : ""));
					}
					System.out.println();
				}
				
				position += entry.length() + "\n".length();
				++ loopcounter;
			}
			
			tableRandomAccessFile.close();
		} catch (Exception e) {
			System.out.println("error while loading table : " + tablename);
			e.printStackTrace();
		}
	}

	private static void createTable(String cmd) {
		String[] createTableWords = cmd.split(" ");
		//int numberOfTables=0;
			try {
			Table table = new Table(currentSchema);
			table.setName(createTableWords[2]);

			String bracketString = null;

			Matcher m = Pattern.compile(Table.pattern).matcher(cmd);
			while (m.find())
				bracketString = m.group(0);

			m = Pattern.compile(Column.commaSeperatorPattern).matcher(bracketString);

			boolean success = true;
			while (m.find()) {
				String commaSeparated = m.group(0);
				Column column = Column.parseColumnString(table, commaSeparated);
				if (column != null) {
					table.addColumn(column);
					
				} else {
					System.out.println("error while creating table. !");
					success = false;
					break;
				}
			}

			if (!success)
				return;
			
			RandomAccessFile info_schema = new RandomAccessFile("tables_infoSchema", "rw");
			try{
				RandomAccessFile numberOfTablesFile = new RandomAccessFile("TableNumbers", "rw");
				numberOfTablesFile.seek(0);
				int numberOfTables = numberOfTablesFile.readInt();
				
				numberOfTables++;
				numberOfTablesFile.close();
				try{
					RandomAccessFile numberTables = new RandomAccessFile("TableNumbers", "rw");
					numberTables.seek(0);
					numberTables.writeInt(numberOfTables);
					numberTables.close();
				} catch(Exception e) {
					System.out.println(e);
				}
				
			} catch(Exception e) {
				System.out.println(e);
			}

			
			info_schema.seek(info_schema.length());
			info_schema.writeByte(table.getTableFileName().length());
			info_schema.writeBytes(table.getTableFileName());
			info_schema.close();
			
			table.setupColumnsMetadata();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Help: Display supported commands
	 * 
	 */

	public static void displaySchemas() {
		try {
			RandomAccessFile schemasFile = new RandomAccessFile("schema_info", "rw");
			RandomAccessFile numberSchema = new RandomAccessFile("number_of_schemas","rw");

			schemasFile.seek(0);
		//	System.out.println("\n");
			System.out.println(line("*", 80));
			int numberOfSchemas = numberSchema.readInt();
			for (int record = 0; record < numberOfSchemas; record++) {
				byte varcharLength = schemasFile.readByte();
		//		System.out.println(varcharLength);
				for (int i = 0; i < varcharLength; i++)
					System.out.print((char) schemasFile.readByte());
				System.out.print("\n");
				
			}
			System.out.println(line("*", 80));
			/*
			 * byte[] document = new byte[(int) schemasFile.length()];
			 * schemasFile.readFully(document); String s2 = new
			 * String(document); System.out.println(s2.length());
			 * System.out.println(s2);
			 */
			schemasFile.close();
			numberSchema.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void displayTables(String schemaName) {
		// int off = 0;
		schemaName = currentSchema;

		try {
			RandomAccessFile info_schema = new RandomAccessFile("tables_infoSchema", "rw");
			RandomAccessFile numberOfTablesFile = new RandomAccessFile("TableNumbers", "rw");
			info_schema.seek(0);
		//	numberOfTablesFile.seek(0);
		    int	numberOfTables = numberOfTablesFile.readInt();
			for (int record = 0; record < numberOfTables + 3; record++) {
				byte varcharLength = info_schema.readByte();
				// System.out.println(varcharLength);
				byte[] b = new byte[(int) info_schema.length()];

				info_schema.readFully(b, schemaName.length() + 1, varcharLength);
				String s2 = new String(b).trim();
				// System.out.println(""+s2);
				Pattern pattern = Pattern.compile(currentSchema.trim() + ".(.*?).tbl");
				Matcher matcher = pattern.matcher(s2);
				while (matcher.find()) {
					System.out.println(matcher.group(1));
				}
			}

			// RandomAccessFile schemasFile = new
			// RandomAccessFile("schema_information","rw");
			System.out.println("\n");

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void help() {
		System.out.println(line("*", 80));
		System.out.println();
		System.out.println("\tSHOW SCHEMAS;\t\t\t Display all the schemas available in the database");
		System.out.println("\tUSE schema_name;\t\t Use the schema ");
		System.out.println("\tSHOW TABLES;\t\t\t Show the tables in a schema ");
		System.out.println("\tCREATE SCHEMA schema_name;\t\t Create a new schema ");
		System.out.println("\tCREATE TABLE table_name;\t\t Create  ");
		System.out.println("\tUSE schema_name;\t\t\tUse the schema ");
		System.out.println("\tversion;\t\t\tShow the program version.");
		System.out.println("\thelp;\t\tShow this help information");
		System.out.println("\texit;\t\t\t\tExit the program");
		System.out.println();
		System.out.println();
		System.out.println(line("*", 80));
	}

	/**
	 * Display the welcome "splash screen"
	 */
	public static void splashScreen() {
		System.out.println(line("*", 80));
		System.out.println("Welcome to DavisBaseLite"); // Display the string.
		version();
		System.out.println("Type \"help;\" to display supported commands.");
		System.out.println(line("*", 80));
	}

	/**
	 * @param s
	 *            The String to be repeated
	 * @param num
	 *            The number of time to repeat String s. '* @return String A
	 *            String object, which is the String s appended to itself num
	 *            times.
	 */
	public static String line(String s, int num) {
		String a = "";
		for (int i = 0; i < num; i++) {
			a += s;
		}
		return a;
	}

	/**
	 * @param num
	 *            The number of newlines to be displayed to <b>stdout</b>
	 */
	public static void newline(int num) {
		for (int i = 0; i < num; i++) {
			System.out.println();
		}
	}

	public static void version() {
		System.out.println("DavisBaseLite v1.0\n");
	}

	/**
	 * This method reads a binary table file using a hard-coded table schema.
	 * Your query must be able to read a binary table file using a dynamically
	 * constructed table schema from the information_schema
	 */
	public static void displayAllRecords() {
		try {
			/* Open the widget table binary data file */
			RandomAccessFile widgetTableFile = new RandomAccessFile(widgetTableFileName, "rw");

			/*
			 * Navigate throught the binary data file, displaying each widget
			 * record in the order that it physically appears in the file.
			 * Convert binary data to appropriate data types for each field.
			 */
			for (int record = 0; record < 5; record++) {
				System.out.print(widgetTableFile.readInt());
				System.out.print("\t");
				byte varcharLength = widgetTableFile.readByte();
				for (int i = 0; i < varcharLength; i++)
					System.out.print((char) widgetTableFile.readByte());
				System.out.print("\t");
				System.out.print(widgetTableFile.readShort());
				System.out.print("\t");
				System.out.println(widgetTableFile.readFloat());
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void displayRecordID(int id) {
		try {
			int indexFileLocation = 0;
			long indexOfRecord = 0;
			boolean recordFound = false;

			RandomAccessFile widgetTableFile = new RandomAccessFile(widgetTableFileName, "rw");
			RandomAccessFile tableIdIndex = new RandomAccessFile(tableIdIndexName, "rw");

			/*
			 * Use exhaustive brute force seach over the binary index file to
			 * locate the requested ID va lues. Then use its assoicated address
			 * to seek the record in the widget table binary data file.
			 *
			 * You may instead want to load the binary index file into a HashMap
			 * or similar key:value data structure for efficient index-address
			 * lookup, but this is not required.
			 */
			while (!recordFound) {
				tableIdIndex.seek(indexFileLocation);
				if (tableIdIndex.readInt() == id) {
					tableIdIndex.seek(indexFileLocation + 4);
					indexOfRecord = tableIdIndex.readLong();
					recordFound = true;
				}
				/*
				 * Each index entry uses 12 bytes: ID=4-bytes + address=8-bytes
				 * Move ahead 12 bytes in the index file for each while() loop
				 * iteration to increment through index entries.
				 * 
				 */
				indexFileLocation += 12;
			}

			widgetTableFile.seek(indexOfRecord);
			System.out.print(widgetTableFile.readInt());
			System.out.print("\t");
			byte varcharLength = widgetTableFile.readByte();
			for (int i = 0; i < varcharLength; i++)
				System.out.print((char) widgetTableFile.readByte());
			System.out.print("\t");
			System.out.print(widgetTableFile.readShort());
			System.out.print("\t");
			System.out.println(widgetTableFile.readFloat());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * This method is hard-coded to create a binary table file with 5 records It
	 * also creates an index file for the ID field It is based on the following
	 * table schema:
	 * 
	 * CREATE TABLE table ( id unsigned int primary key, name varchar(25),
	 * quantity unsigned short, probability float );
	 */
	public static void hardCodedCreateTableWithIndex() {
		try {
			/*
			 * FIXME: Put all binary data files in a separate subdirectory
			 * (subdirectory tree?)
			 */
			/*
			 * FIXME: Should there not be separate Class static variables for
			 * the file names? and just hard code them here?
			 */
			/*
			 * TODO: Should there be separate methods to checkfor and
			 * subsequently create each file granularly, instead of a big bang
			 * all or nothing?
			 * 
			 */
			RandomAccessFile schemasFile = new RandomAccessFile("schema_info", "rw");
			RandomAccessFile numberOfTablesFile = new RandomAccessFile("TableNumbers", "rw");
			if (numberOfTablesFile.length() == 0) {
				try {
					RandomAccessFile numberTables = new RandomAccessFile("TableNumbers", "rw");
					numberTables.seek(0);
					numberTables.writeInt(0);
					numberTables.close();
				} catch (Exception e) {
					System.out.println(e);
				}
				numberOfTablesFile.close();
				schemasFile.setLength(0);

				RandomAccessFile numberSchema = new RandomAccessFile("number_of_schemas", "rw");
				numberSchema.setLength(0);
				numberSchema.close();
				try {
					RandomAccessFile numberSchema1 = new RandomAccessFile("number_of_schemas", "rw");
					numberSchema1.seek(0);
					numberSchema1.writeInt(1);
					numberSchema1.close();
				} catch (Exception e) {
					System.out.println(e);
				}

				numberOfTablesFile.setLength(0);

				String schema1 = "information_schema";
				// String schema2 = "pm";

				String table1 = "information_schema.SCHEMATA.tbl";
				String table2 = "information_schema.TABLE.tbl";
				String table3 = "information_schema.COLUMNS.tbl";
				schemasFile.writeByte(schema1.length());
				schemasFile.writeBytes(schema1);
				// schemasFile.writeByte(schema2.length());
				// schemasFile.writeBytes(schema2);
				RandomAccessFile info_schema = new RandomAccessFile("tables_infoSchema", "rw");

				schemasFile.close();
				info_schema.setLength(0);
				info_schema.writeByte(table1.length());
				info_schema.writeBytes(table1);
				info_schema.writeByte(table2.length());
				info_schema.writeBytes(table2);
				info_schema.writeByte(table3.length());
				info_schema.writeBytes(table3);
				System.out.println("sk" + info_schema.length());

				RandomAccessFile schemataTableFile = new RandomAccessFile("information_schema.schemata.tbl", "rw");

				RandomAccessFile tablesTableFile = new RandomAccessFile("information_schema.table.tbl", "rw");
				RandomAccessFile columnsTableFile = new RandomAccessFile("information_schema.columns.tbl", "rw");

				// ROW 1: information_schema.schemata.tbl
				schemataTableFile.writeByte("information_schema".length());
				schemataTableFile.writeBytes("information_schema");

				/*
				 * Create the TABLES table file. Remember!!! Column names are
				 * not stored in the tables themselves The column names
				 * (TABLE_SCHEMA, TABLE_NAME, TABLE_ROWS) and their order
				 * (ORDINAL_POSITION) are encoded in the COLUMNS table.
				 * Initially it has three rows (each row may have a different
				 * length):
				 */
				// ROW 1: information_schema.tables.tbl
				tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				tablesTableFile.writeBytes("information_schema");
				tablesTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
				tablesTableFile.writeBytes("SCHEMATA");
				tablesTableFile.writeLong(1); // TABLE_ROWS

				// ROW 2: information_schema.tables.tbl
				tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				tablesTableFile.writeBytes("information_schema");
				tablesTableFile.writeByte("TABLES".length()); // TABLE_NAME
				tablesTableFile.writeBytes("TABLES");
				tablesTableFile.writeLong(3); // TABLE_ROWS

				// ROW 3: information_schema.tables.tbl
				tablesTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				tablesTableFile.writeBytes("information_schema");
				tablesTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				tablesTableFile.writeBytes("COLUMNS");
				tablesTableFile.writeLong(7); // TABLE_ROWS

				/*
				 * Create the COLUMNS table file. Initially it has 11 rows:
				 */
				// ROW 1: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("SCHEMATA".length()); // TABLE_NAME
				columnsTableFile.writeBytes("SCHEMATA");
				columnsTableFile.writeByte("SCHEMA_NAME".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("SCHEMA_NAME");
				columnsTableFile.writeInt(1); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(64)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 2: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
				columnsTableFile.writeBytes("TABLES");
				columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("TABLE_SCHEMA");
				columnsTableFile.writeInt(1); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(64)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 3: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
				columnsTableFile.writeBytes("TABLES");
				columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("TABLE_NAME");
				columnsTableFile.writeInt(2); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(64)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 4: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("TABLES".length()); // TABLE_NAME
				columnsTableFile.writeBytes("TABLES");
				columnsTableFile.writeByte("TABLE_ROWS".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("TABLE_ROWS");
				columnsTableFile.writeInt(3); // ORDINAL_POSITION
				columnsTableFile.writeByte("long int".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("long int");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 5: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				columnsTableFile.writeBytes("COLUMNS");
				columnsTableFile.writeByte("TABLE_SCHEMA".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("TABLE_SCHEMA");
				columnsTableFile.writeInt(1); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(64)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 6: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				columnsTableFile.writeBytes("COLUMNS");
				columnsTableFile.writeByte("TABLE_NAME".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("TABLE_NAME");
				columnsTableFile.writeInt(2); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(64)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 7: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				columnsTableFile.writeBytes("COLUMNS");
				columnsTableFile.writeByte("COLUMN_NAME".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("COLUMN_NAME");
				columnsTableFile.writeInt(3); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(64)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 8: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				columnsTableFile.writeBytes("COLUMNS");
				columnsTableFile.writeByte("ORDINAL_POSITION".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("ORDINAL_POSITION");
				columnsTableFile.writeInt(4); // ORDINAL_POSITION
				columnsTableFile.writeByte("int".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("int");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 9: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				columnsTableFile.writeBytes("COLUMNS");
				columnsTableFile.writeByte("COLUMN_TYPE".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("COLUMN_TYPE");
				columnsTableFile.writeInt(5); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(64)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(64)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 10: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				columnsTableFile.writeBytes("COLUMNS");
				columnsTableFile.writeByte("IS_NULLABLE".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("IS_NULLABLE");
				columnsTableFile.writeInt(6); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(3)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");

				// ROW 11: information_schema.columns.tbl
				columnsTableFile.writeByte("information_schema".length()); // TABLE_SCHEMA
				columnsTableFile.writeBytes("information_schema");
				columnsTableFile.writeByte("COLUMNS".length()); // TABLE_NAME
				columnsTableFile.writeBytes("COLUMNS");
				columnsTableFile.writeByte("COLUMN_KEY".length()); // COLUMN_NAME
				columnsTableFile.writeBytes("COLUMN_KEY");
				columnsTableFile.writeInt(7); // ORDINAL_POSITION
				columnsTableFile.writeByte("varchar(3)".length()); // COLUMN_TYPE
				columnsTableFile.writeBytes("varchar(3)");
				columnsTableFile.writeByte("NO".length()); // IS_NULLABLE
				columnsTableFile.writeBytes("NO");
				columnsTableFile.writeByte("".length()); // COLUMN_KEY
				columnsTableFile.writeBytes("");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
