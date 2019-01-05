package QueryModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import BuildTree.TreeBuilder;


public class TableFileModel extends RandomAccessFile{
	String tableName;
	TreeBuilder buildTree;
	TableFileModel file;
	String dbName;
	

	public TableFileModel(String name, String mode,String tableName) throws FileNotFoundException {
		super(name, mode);
		this.tableName = tableName;
		buildTree = new TreeBuilder(this);
		file = this;
		// TODO Auto-generated constructor stub
	}
	
	public void SetDbName(String name){
		dbName = name;
	}
	
	public void AddCell() throws IOException{
		if(tableName.equals("davisbase_tables")){
			System.out.println(tableName);
			String colType[] = new String[2];
			String colValue[] = new String[2];
			int cellSize = 0;
			
			colType[0] = "TEXT";
			colValue[0] = "davisbase_tables";
			cellSize += colValue[0].length();
			colType[1] = "TEXT";
			colValue[1] = dbName;
			cellSize += colValue[1].length();
			buildTree.Insert(1,colType,colValue,cellSize,0,false);
			cellSize = 0;
			
			colType[0] = "TEXT";
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colType[1] = "TEXT";
			colValue[1] = dbName;
			cellSize += colValue[1].length();
			buildTree.Insert(2,colType,colValue,cellSize,0,false);
			
			
			
		}else if(tableName.equals("davisbase_columns")){
			Integer rowId = 1;
			String colType[] = new String[6];
			String colValue[] = new String[6];
			int cellSize = 0;
			Integer orderNum = 1;
			
			colType[0] = "TEXT";
			colType[1] = "TEXT";
			colType[2] = "TEXT";
			colType[3] = "TINYINT";
			colType[4] = "TEXT";
			colType[5] = "TEXT";
			colValue[0] = "davisbase_tables";
			cellSize += colValue[0].length();
			colValue[1] = "rowId";
			cellSize += colValue[1].length();
			colValue[2] = "INT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "PRI";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_tables";
			cellSize += colValue[0].length();
			colValue[1] = "table_name";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_tables";
			cellSize += colValue[0].length();
			colValue[1] = "dbName";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			orderNum = 1;
			rowId++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "rowId";
			cellSize += colValue[1].length();
			colValue[2] = "INT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "PRI";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "table_name";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "column_name";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "data_type";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "ordinal_position";
			cellSize += colValue[1].length();
			colValue[2] = "TINYINT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "is_nullable";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "COLUMN_KEY";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
			rowId++;
			orderNum++;
			cellSize = 0;
			colValue[0] = "davisbase_columns";
			cellSize += colValue[0].length();
			colValue[1] = "dbName";
			cellSize += colValue[1].length();
			colValue[2] = "TEXT";
			cellSize += colValue[2].length();
			colValue[3] = orderNum.toString();
			cellSize += colValue[3].length();
			colValue[4] = "NO";
			cellSize += colValue[4].length();
			colValue[5] = "NULL";
			cellSize += colValue[5].length();
			buildTree.Insert(rowId,colType,colValue,cellSize,0,false);
			
		}else{
			
		}
	}
	
	
	public void CreateTable(TableFileModel dbTable,TableFileModel dbColumn,String command) throws IOException{
		int tableRowId =  dbTable.buildTree.FindLastRowId(0) +1;
		String colType[] = new String[2];
		String colValue[] = new String[2];
		int cellSize = 0;
		
		colType[0] = "TEXT";
		colValue[0] = tableName;
		cellSize += colValue[0].length();
		colType[1] = "TEXT";
		colValue[1] = dbName;
		cellSize += colValue[1].length();
		dbTable.buildTree.Insert(tableRowId,colType,colValue,cellSize,0,false);
		
		if(command.contains("primary key")==false) {
			System.out.println("No int primary key found..\nCannot create table");
			return;
		}
		
		int columnRowId = dbColumn.buildTree.FindLastRowId(0) + 1;
		String[] columns = command.split(",");
		String columnName, dataType;
		String isNullable, columnKey;
		Integer ordinalPosition = 0;
		
		
		colType = new String[7];
		colValue = new String[7];
		colType[0] = "TEXT";
		colType[1] = "TEXT";
		colType[2] = "TEXT";
		colType[3] = "TINYINT";
		colType[4] = "TEXT";
		colType[5] = "TEXT";
		colType[6] = "TEXT";
		
		for (int i = 0; i < columns.length; i++) {
			String column = columns[i];
			String[] tokens = column.trim().split(" ");
			columnName = tokens[0].toLowerCase().trim();
			dataType = tokens[1].toLowerCase().trim();
			column = column.toLowerCase();
			isNullable = column.contains("not null") ? "NO" : "YES";
			columnKey = column.contains("primary key") ? "PRI" : "NO";
			if (columnKey.equals("PRI"))
				isNullable="NO";
			ordinalPosition++;
			
			cellSize = 0;
			colValue[0] = tableName;
			cellSize += colValue[0].length();
			colValue[1] = columnName;
			cellSize += colValue[1].length();
			colValue[2] = dataType;
			cellSize += colValue[2].length();
			colValue[3] = ordinalPosition.toString();
			cellSize += colValue[3].length();
			colValue[4] = isNullable;
			cellSize += colValue[4].length();
			colValue[5] = columnKey;
			cellSize += colValue[5].length();
			colValue[6] = dbName;
			cellSize += colValue[6].length();
			dbColumn.buildTree.Insert(columnRowId,colType,colValue,cellSize,0,false);
			columnRowId++;
		}
		
	}
	
	public List<String[]> GetHeaderDetails(String name) throws IOException{
		List<String[]> list = file.buildTree.FindColumnEntry(name);
		return list;
	}
	
	public List<String[]> GetColumnValues(TableFileModel tablefile,int ordinalNum,String pattern,String operator) throws IOException{
		//List<String[]> list = dbColumn.buildTree.findHeader(name);
		List<String[]> list = tablefile.buildTree.FindColumnEntry(pattern,ordinalNum,operator);
		return list;
	}
	
	public List<String[]> FindAll() throws IOException{
		List<String[]> list = file.buildTree.FindAll();
		return list;
	}
	
	public void Insert(int rowId,String[] colType, String[] colValue, int cellSize) throws IOException{
		file.buildTree.Insert(rowId, colType, colValue, cellSize, 0, false);
	}
	
	public void Delete(String pattern) throws IOException{
		file.buildTree.DeletePattern(pattern);
	}
	
	public void Delete(String pattern,int ordinalnum, String operator) throws IOException{
		file.buildTree.DeletePattern(pattern,ordinalnum,operator);
	}
	
	public void Delete(String pattern1,int ordinalnum1, String oper1,String pattern2,int ordinalnum2, String oper2) throws IOException{
		file.buildTree.DeletePattern(pattern1,ordinalnum1,oper1,pattern2,ordinalnum2,oper2);
	}
	
	public void Update(String val, int ordnum, int[] Ordinalnum,String[] values, String[] oper1, String[] oper2) throws IOException{
		file.buildTree.Update(val, ordnum, Ordinalnum,values, oper1, oper2);
	}
	
	public boolean CompareToValue(byte type,String value, String pattern, String operator) throws IOException{
		boolean ret = false;
		int strVal;
		Integer intValue;
		Float floatValue;
		Double doubleValue;
		Long longValue;
		if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.TINYINT)) {
			strVal = Integer.parseInt(value);
			int val = Integer.parseInt(pattern);
			if(operator.equals("<")){
				return (strVal < val);
			}else if(operator.equals(">")){
				return (strVal > val);
			}else if(operator.equals(">=")){
				return (strVal >= val);
			}else if(operator.equals("<=")){
				return (strVal <= val);
			}else if(operator.equals("=")){
				return (strVal == val);
			}
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.SMALLINT)) {
			strVal =  Integer.parseInt(value);
			intValue = strVal;
			int val = Integer.parseInt(pattern);
			if(operator.equals("<")){
				return (intValue < val);
			}else if(operator.equals(">")){
				return (intValue > val);
			}else if(operator.equals(">=")){
				return (intValue >= val);
			}else if(operator.equals("<=")){
				return (intValue <= val);
			}else if(operator.equals("=")){
				return (intValue == val);
			}
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.INT)) {
			intValue = Integer.parseInt(value);
			int val = Integer.parseInt(pattern);
			if(operator.equals("<")){
				return (intValue < val);
			}else if(operator.equals(">")){
				return (intValue > val);
			}else if(operator.equals(">=")){
				return (intValue >= val);
			}else if(operator.equals("<=")){
				return (intValue <= val);
			}else if(operator.equals("=")){
				return (intValue == val);
			}
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.BIGINT)) {
			longValue = Long.parseLong(value);
			long val = Long.parseLong(pattern);
			if(operator.equals("<")){
				return (longValue < val);
			}else if(operator.equals(">")){
				return (longValue > val);
			}else if(operator.equals(">=")){
				return (longValue >= val);
			}else if(operator.equals("<=")){
				return (longValue <= val);
			}else if(operator.equals("=")){
				return (longValue == val);
			}
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.REAL)) {
			floatValue = Float.parseFloat(value);
			float val = Float.parseFloat(pattern);
			if(operator.equals("<")){
				return (floatValue < val);
			}else if(operator.equals(">")){
				return (floatValue > val);
			}else if(operator.equals(">=")){
				return (floatValue >= val);
			}else if(operator.equals("<=")){
				return (floatValue <= val);
			}else if(operator.equals("=")){
				return (floatValue == val);
			}
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DOUBLE)) {
			doubleValue = Double.parseDouble(value);
			float val = Float.parseFloat(pattern);
			if(operator.equals("<")){
				return (doubleValue < val);
			}else if(operator.equals(">")){
				return (doubleValue > val);
			}else if(operator.equals(">=")){
				return (doubleValue >= val);
			}else if(operator.equals("<=")){
				return (doubleValue <= val);
			}else if(operator.equals("=")){
				return (doubleValue == val);
			}
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATETIME)) {
			longValue = Long.parseLong(value);
			long val = Long.parseLong(pattern);
			if(operator.equals("<")){
				return (longValue < val);
			}else if(operator.equals(">")){
				return (longValue > val);
			}else if(operator.equals(">=")){
				return (longValue >= val);
			}else if(operator.equals("<=")){
				return (longValue <= val);
			}else if(operator.equals("=")){
				return (longValue == val);
			}
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATE)) {
			longValue = Long.parseLong(value);
			long val = Long.parseLong(pattern);
			if(operator.equals("<")){
				return (longValue < val);
			}else if(operator.equals(">")){
				return (longValue > val);
			}else if(operator.equals(">=")){
				return (longValue >= val);
			}else if(operator.equals("<=")){
				return (longValue <= val);
			}else if(operator.equals("=")){
				return (longValue == val);
			}
		} else {
			//int length = type - 12;
			//String val =  this.readString(length);
			if(operator.equals("=")){
				return (value.equals(pattern));
			}
		}
		return ret;
	}
	
	
	
	public String ReadByType(byte type,int val) throws IOException{
		int strVal;
		Integer intValue;
		Float floatValue;
		Double doubleValue;
		Long longValue;
		if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.TINYINT)) {
			strVal = this.readByte();
			intValue = strVal;
			return intValue.toString();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.SMALLINT)) {
			strVal =  this.readShort();
			intValue = strVal;
			return intValue.toString();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.INT)) {
			intValue = this.readInt();
			return intValue.toString();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.BIGINT)) {
			longValue = this.readLong();
			return longValue.toString();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.REAL)) {
			floatValue = this.readFloat();
			return floatValue.toString();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DOUBLE)) {
			doubleValue = this.readDouble();
			return doubleValue.toString();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATETIME)) {
			longValue = this.readLong();
			return longValue.toString();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATE)) {
			longValue = this.readLong();
			return longValue.toString();
		} else {
			int length = type - 12;
			return this.ReadString(length);
		}
	}
	public Long ReadByDateType(byte type,int val) throws IOException{
		long longValue = 0;
		if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATE)) {
			longValue = this.readLong();
		}
		return longValue;
	}
	
	
	
	
	public Object ReadByType(byte type) throws IOException{
		if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.TINYINT)) {
			return this.readByte();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.SMALLINT)) {
			return this.readShort();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.INT)) {
			return this.readInt();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.BIGINT)) {
			return this.readLong();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.REAL)) {
			return this.readFloat();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DOUBLE)) {
			return this.readDouble();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATETIME)) {
			return this.readLong();
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATE)) {
			return this.readLong();
		} else {
			int length = type - 12;
			return this.ReadString(length);
		}
	}

	public void WriteByType(Object v) throws IOException {
		if (v instanceof Byte) {
			this.writeByte(((Byte) v).byteValue());
		} else if (v instanceof Short) {
			this.writeShort(((Short) v).shortValue());
		} else if (v instanceof Integer) {
			this.writeInt(((Integer) v).intValue());
		} else if (v instanceof Long) {
			this.writeLong(((Long) v).longValue());
		} else if (v instanceof Float) {
			this.writeFloat(((Float) v).floatValue());
		} else if (v instanceof Double) {
			this.writeDouble(((Double) v).doubleValue());
		} else {
			this.writeBytes(v.toString());
		}
	}
	
	public void WriteByType(String v,Byte type) throws IOException {
		if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.TINYINT)) {
			this.writeByte(Integer.parseInt(v));
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.SMALLINT)) {
			this.writeShort(Short.parseShort(v));
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.INT)) {
			this.writeInt(Integer.parseInt(v));
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.BIGINT)) {
			this.writeLong(Long.parseLong(v));
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.REAL)) {
			this.writeFloat(Float.parseFloat(v));
		} else if (type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DOUBLE)) {
			this.writeDouble(Double.parseDouble(v));
		} else if(type == QueryUtil.DataType.getTypeCode(QueryUtil.DataType.DATE)) {
			this.writeLong(Long.parseLong(v));
		}
		else {
			this.writeBytes(v.toString());
		}
	}
	
	public String ReadString(int length) throws IOException {
		byte[] byteVal = new byte[length];
		this.read(byteVal);
		return new String(byteVal);
	}

}
