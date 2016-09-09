package myImpl.model;

import java.util.Date;

public enum DataTypeType {
	
	BYTE("byte", 1, Byte.class),
	SHORT_INT("short int", 2, Short.class),
	SHORT("short", 2, Short.class),
	INT("int", 4, Integer.class),
	CHAR("char", String.class),
	VARCHAR("varchar", String.class),	
	FLOAT("float", 4, Float.class),	
	DATETIME("datetime", 8, Date.class),	
	DATE("date", Date.class),	
	DOUBLE("double", 8, Double.class);
	
	private String name;
	private Integer blockSize;
	private Class<?> clazz;
	
	private DataTypeType(String name) {
		this.name = name;
	}
	
	private DataTypeType(String name, Class clazz) {
		this(name);
		this.clazz = clazz;
	}
	
	private DataTypeType(String name, int blockSize) {
		this(name);
		this.blockSize = blockSize;
	}
	
	private DataTypeType(String name, int blockSize, Class clazz) {
		this(name, blockSize);
		this.clazz = clazz;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getBlockSize() {
		return blockSize;
	}
	
	public void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}
	
	public Class<?> getClazz() {
		return clazz;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
