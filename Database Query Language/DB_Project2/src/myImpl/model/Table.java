package myImpl.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Table {
	
	public static String pattern = "\\(([ ]?([ ]?\\w+[ ]?(\\([0-9]+\\))*([ ]?,[ ]?)?)+)\\)";
	
	private String schema;
	private String name;
	private List<Column> columns;
	private String tableFileName;
	private RandomAccessFile randomAccessFile;
	
	public Table(String schema) {
		this.schema = schema;
	}
	
	public String getSchema() {
		return schema;
	}
	
	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.tableFileName = schema + "." + name + ".tbl";
		this.name = name;
	}
	
	public void setupColumnsMetadata() throws IOException {
		if (getRandomAccessFile().length() == 0) {
			String columnsAsString = getColumnsAsString();
			getRandomAccessFile().writeBytes(columnsAsString + "\n");
		}
	}
	
	public String getColumnsMetadata() throws IOException {
		getRandomAccessFile();
		if (randomAccessFile.length() == 0)
			return "";
		else {
			randomAccessFile.seek(0);
			return randomAccessFile.readLine().trim();
		}
	}
	
	public List<Column> getColumns() {
		if (columns == null) {
			columns = new LinkedList<>();
		}
		return columns;
	}
	
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public void addColumn(Column column) {
		getColumns().add(column);
	}
	
	public RandomAccessFile getRandomAccessFile(String mode) throws FileNotFoundException {
		return randomAccessFile = new RandomAccessFile(tableFileName, mode);
	}
	
	public RandomAccessFile getRandomAccessFile() throws FileNotFoundException {
		return getRandomAccessFile("rw");
	}
	
	public String getTableFileName() {
		return tableFileName;
	}
	
	public String getColumnsAsString() {
		StringBuffer buffer = new StringBuffer();
		
		for (Iterator<Column> columnsIterator = columns.iterator(); columnsIterator.hasNext();) {
			Column column = columnsIterator.next();
			buffer.append(column);
			
			if (columnsIterator.hasNext())
				buffer.append("#");
		}
		
		return buffer.toString();
	}
	
	public static Table loadDataIfPresent(String schema, String name) throws Exception {
		Table table = new Table(schema);
		table.setName(name);
		
		RandomAccessFile tableRandomAccessFile = table.getRandomAccessFile("r");
		
		tableRandomAccessFile.seek(0);
		String tableMetaData = tableRandomAccessFile.readLine();
		table.parseColumnsFromMetaData(tableMetaData);
		
		int position = tableMetaData.length() + "\n".length();
		
		int rownum = 0;
		while (position < tableRandomAccessFile.length()) {
			tableRandomAccessFile.seek(position);
			String entry = tableRandomAccessFile.readLine();
			table.insertEntry(entry, rownum);
			position += entry.length() + "\n".length();
			rownum ++;
		}
		
		tableRandomAccessFile.close();
		
		return table;
		
	}

	private boolean parseColumnsFromMetaData(String tableMetaData) throws FileNotFoundException {
		String[] columnsMetaData = tableMetaData.split("#");
		
		boolean result = true;
		for (int index = 0; index < columnsMetaData.length; index++) {
			String columnMetaData = columnsMetaData [index];
			
			String columnname = columnMetaData.split("/")[0];
			String columndatatype = columnMetaData.split("/")[1];
			
			if (columnname == null || columnname.length() == 0 || columndatatype == null || columndatatype.length() == 0) {
				System.out.println("empty column name. Incorrect metadata : " + columnMetaData);
				return false;
			}
			
			Column column = new Column(this);
			column.setName(columnname);
			
			if (columnMetaData.contains("pk"))
				column.setPrimaryKey(true);
			
			if (columnMetaData.contains("notnull"))
				column.setNullable(false);
			
			if (columndatatype.contains("-"))
				columndatatype = columndatatype.substring(0, columndatatype.indexOf("-")) + 
						"(" + columndatatype.substring(columndatatype.indexOf("-") + 1, columndatatype.lastIndexOf("-")) + ")";

			DataType datatype = DataType.parseType(columndatatype);
			
			if (datatype == null) {
				System.out.println("unable to create datatype for column : " + columnMetaData);
				return false;
			}
			
			column.setDatatype(datatype);
			getColumns().add(column);
		}
		
		return result;
	}

	private boolean insertEntry(String entry, int rownum) {
		String[] columnData = entry.split(",");
		
		boolean result = true;
		if (columnData.length != getColumns().size()) {
			System.out.println("incorrect data entry. number of columns are not equal to number of elements in entry : " + entry);
			return false;
		}
		
		for (int index = 0; index < columnData.length; index++) {
			try {
				getColumns().get(index).populateContent(columnData[index], rownum);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("exception while loading content for the column : '" + getColumns().get(index).getName() + "' with value : " + columnData[index]);
				return false;
			}
		}
		
		return result;
	}
	
}
