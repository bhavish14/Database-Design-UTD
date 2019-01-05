package QueryUtil;

import java.util.HashMap;

public class DataType {
	public static final String TINYINT = "tinyint";
	public static final String SMALLINT = "smallint";
	public static final String INT = "int";
	public static final String BIGINT = "bigint";
	public static final String REAL = "real";
	public static final String DOUBLE = "double";
	public static final String DATETIME = "datetime";
	public static final String DATE = "date";
	public static final String TEXT = "text";

	public static HashMap<String, Byte> dataTypeMap = new HashMap<>();
	static {
		dataTypeMap.put(TINYINT, (byte) 0x04);
		dataTypeMap.put(SMALLINT, (byte) 0x05);
		dataTypeMap.put(INT, (byte) 0x06);
		dataTypeMap.put(BIGINT, (byte) 0x07);
		dataTypeMap.put(REAL, (byte) 0x08);
		dataTypeMap.put(DOUBLE, (byte) 0x09);
		dataTypeMap.put(DATETIME, (byte) 0x0A);
		dataTypeMap.put(DATE, (byte) 0x0B);
		dataTypeMap.put(TEXT, (byte) 0x0C);
	}
	
	public static HashMap<Byte, String> datacodeMap = new HashMap<>();
	static {
		datacodeMap.put( (byte) 0x04,TINYINT);
		datacodeMap.put( (byte) 0x05,SMALLINT);
		datacodeMap.put( (byte) 0x06,INT);
		datacodeMap.put( (byte) 0x07,BIGINT);
		datacodeMap.put( (byte) 0x08,REAL);
		datacodeMap.put( (byte) 0x09,DOUBLE);
		datacodeMap.put( (byte) 0x0A,DATETIME);
		datacodeMap.put( (byte) 0x0B,DATE);
		datacodeMap.put( (byte) 0x0C,TEXT);
	}

	public static HashMap<String, Integer> sizeMap = new HashMap<>();
	static {
		sizeMap.put(TINYINT, 1);
		sizeMap.put(SMALLINT, 2);
		sizeMap.put(INT, 4);
		sizeMap.put(BIGINT, 8);
		sizeMap.put(REAL, 4);
		sizeMap.put(DOUBLE, 8);
		sizeMap.put(DATETIME, 8);
		sizeMap.put(DATE, 8);
		sizeMap.put(TEXT, 0);
	}

	public static HashMap<String, Object> nullValueMap = new HashMap<>();
	static {
		nullValueMap.put(TINYINT, 0);
		nullValueMap.put(SMALLINT, 0);
		nullValueMap.put(INT, 0);
		nullValueMap.put(BIGINT, 0);
		nullValueMap.put(REAL, 0);
		nullValueMap.put(DOUBLE, 0);
		nullValueMap.put(DATETIME, 0);
		nullValueMap.put(DATE, 0);
		nullValueMap.put(TEXT, "");
	}

	public static HashMap<String, Byte> nullMap = new HashMap<>();
	static {
		nullMap.put(TINYINT, (byte) 0x00);
		nullMap.put(SMALLINT, (byte) 0x01);
		nullMap.put(INT, (byte) 0x02);
		nullMap.put(BIGINT, (byte) 0x03);
		nullMap.put(REAL, (byte) 0x02);
		nullMap.put(DOUBLE, (byte) 0x08);
		nullMap.put(DATETIME, (byte) 0x08);
		nullMap.put(DATE, (byte) 0x08);
		nullMap.put(TEXT, (byte) 0x01);
	}
	
	public static Object getNullValue(String dataType) {
		return nullValueMap.get(dataType.toLowerCase());
	}

	public static byte getTypeCode(String dataType) {
		return dataTypeMap.get(dataType.toLowerCase());
	}
	
	public static String getCodeval(Byte code) {
		if(code >= 0x0C){
			return "TEXT";
		}
		return datacodeMap.get(code);
	}

	public static byte getNullType(String dataType) {
		return nullMap.get(dataType.toLowerCase());
	}

	public static int getTypeSize(String dataType) {
		return sizeMap.get(dataType.toLowerCase());
	}
}
