package myImpl.model;

public class DataType {

	private String name;
	private DataTypeType type;
	private Integer size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataTypeType getType() {
		return type;
	}

	public void setType(DataTypeType type) {
		this.type = type;
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Integer getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		return getName() + (size == null ? "" : "-" + size + "-");
	}

	public static DataType parseType(String content) {
		DataType dataType = null;
		DataTypeType dataTypeType = null;
		
		for (DataTypeType value : DataTypeType.values()) {
			if (content.toLowerCase().contains(value.getName())) {
				dataTypeType = value;
				
				if (value.getName().equalsIgnoreCase(DataTypeType.DATETIME.getName()))
					break;
			}
		}
		
		if (dataTypeType != null) {
			dataType = new DataType();
			dataType.setName(dataTypeType.getName());
			dataType.setType(dataTypeType);
			
			if (dataTypeType == DataTypeType.CHAR || dataTypeType == DataTypeType.VARCHAR) {
				if (content.contains("(") && content.contains(")")) {
					try {
						dataType.setSize(Integer.parseInt(content.substring(content.indexOf("(") + 1, content.indexOf(")"))));
					} catch (Exception e) {
						System.out.println(" non int size mentioned for datatype " + dataTypeType.getName() + ". Please provide integer size.");
						return null;
					}
				} else {
					System.out.println(" no size mentioned for datatype " + dataTypeType.getName() + ". Please provide size.");
					return null;
				}
			}
		}  else {
			System.out.println(" no matching data type found for column data :" + content + ". Please ensure is one of the following :");
			for (DataTypeType value : DataTypeType.values()) 
				System.out.println(value.name());
		}
		
		return dataType;
	}

}
