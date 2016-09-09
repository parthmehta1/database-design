package myImpl.model;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Column {

	public static final String commaSeperatorPattern = "([ ]?\\w+[ ]?)+(\\([0-9]+\\))*([ ]?\\w+[ ]?)*";
	public static final String key = "primary key";
	public static final String notnull = "not null";

	private Table table;
	private String name;
	private DataType datatype;
	private boolean primaryKey;
	private boolean nullable = true;
	private String columnIndexFileName;
	private RandomAccessFile indexRandomAccessFile;
	private TreeMap<Object, List<Integer>> columnContent;

	public Column(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws FileNotFoundException {
		this.columnIndexFileName = getTable().getSchema() + "." + getTable().getName() + "." + name + ".ndx";
		this.indexRandomAccessFile = new RandomAccessFile(getColumnIndexFileName(), "rw");
		this.name = name;
	}

	public DataType getDatatype() {
		return datatype;
	}

	public void setDatatype(DataType datatype) {
		this.datatype = datatype;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getColumnIndexFileName() {
		return columnIndexFileName;
	}

	public RandomAccessFile getIndexRandomAccessFile() throws FileNotFoundException {
		return indexRandomAccessFile;
	}

	@Override
	public String toString() {
		return getName() + "/" + getDatatype().toString() + (isNullable() ? "" : "/notnull")
				+ (isPrimaryKey() ? "/pk" : "");
	}

	public static Column parseColumnString(Table table, String columnString) throws FileNotFoundException {
		Column column = null;
		DataType datatype = DataType.parseType(columnString);

		if (datatype != null) {
			column = new Column(table);
			column.setName(columnString.trim().split(" ")[0]);
			column.setDatatype(datatype);

			if (columnString.contains(key) || columnString.contains(key.toUpperCase()))
				column.setPrimaryKey(true);

			if (columnString.contains(notnull) || columnString.contains(notnull.toUpperCase()))
				column.setNullable(false);
		} else {
			System.out.println("error while creating column : " + columnString.trim().split(" ")[0]);
		}

		return column;
	}

	public TreeMap<Object, List<Integer>> getColumnContent() {
		if (columnContent == null)
			columnContent = new TreeMap<>();

		return columnContent;
	}

	public void populateContent(String content, int rownum) throws Exception {
			Object object = findObject(content);
			
			if (object == null) {
				System.out.println("error parsing value : " + content + " for datatype : " + datatype.getType());
				throw new Exception();
			}
			
			enterIntoContentMap(object, rownum);
	}
	
	public Object findObject(String content) {
		Object object = null;
		
		content = content.trim();
		if (datatype.getType() == DataTypeType.BYTE) {
			object = Byte.parseByte(content);
		
		} else if (datatype.getType() == DataTypeType.CHAR || datatype.getType() == DataTypeType.VARCHAR) {
			String actualcontent = content.replaceAll("'", "");
			if (actualcontent.length() < datatype.getSize()) {
				object = content;
			}
		} else if (datatype.getType() == DataTypeType.FLOAT) {
			object = Float.parseFloat(content);
		} else if (datatype.getType() == DataTypeType.FLOAT) {
			object = Double.parseDouble(content);
		} else if (datatype.getType() == DataTypeType.SHORT || datatype.getType() == DataTypeType.SHORT_INT) {
			object = Short.parseShort(content);
		}  else if (datatype.getType() == DataTypeType.DATE || datatype.getType() == DataTypeType.DATETIME) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd");
			content = content.replaceAll("'", "");
			
			try {
				object = dateFormat.parse(content);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}  else if (datatype.getType() == DataTypeType.INT) {
			object = Integer.parseInt(content);
		}
		
		return object;
	}
	
	private void enterIntoContentMap(Object object, int rownum) {
		if (getColumnContent().containsKey(object)) {
			getColumnContent().get(object).add(rownum);
		} else {
			List<Integer> rownums = new ArrayList<>();
			rownums.add(rownum);
			getColumnContent().put(object, rownums);
		}
	}
	
}
