package QueryUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import QueryModel.TableFileModel;


public class QueryParser {
	static TableFileModel tableModel;
	static TableFileModel columnModel;
	static String folderPath;
	static String dbName;
	
	public static void Setfoldername(String name){
		dbName = name;
		folderPath = "data/"+name+"/";
	}
	
	public static void Init(){

		try {
			File fileDir = new File("data/catalog");
			if(fileDir != null){
				boolean success = fileDir.mkdirs();
				if(!success){
					//System.out.println("could not create directories");
					//return;
				}
			}
			fileDir = new File("data/user_data");
			if(fileDir != null){
				boolean success = fileDir.mkdirs();
				if(!success){
					//System.out.println("could not create directories");
					//return;
				}
			}
			Setfoldername("user_data");
			tableModel = new TableFileModel("data/catalog/davisbase_tables.tbl","rw","davisbase_tables");
			if(tableModel.length() == 0){
				tableModel.SetDbName("catalog");
				tableModel.AddCell();
			}
			tableModel.SetDbName("catalog");
			
			columnModel = new TableFileModel("data/catalog/davisbase_column.tbl","rw","davisbase_columns");
			if(columnModel.length() == 0){
				columnModel.SetDbName("catalog");
				columnModel.AddCell();
			}
			columnModel.SetDbName("catalog");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("initialization failed");
		}
	}
	
	public static void UseDatabaseCommand(String userCommand) throws IOException{
		String[] commands = userCommand.split(" ");
		Setfoldername(commands[2].toLowerCase().trim());
	}
	
	public static void CreateDatabaseCommand(String userCommand) throws IOException{
		String[] commands = userCommand.split(" ");
		File fileDir = new File("data/"+commands[2].toLowerCase().trim());
		if(fileDir.exists()){
			System.out.println("Database already exists");
			return;
		}else{
			fileDir.mkdir();
		}
	}
	
	public static void DropDatabaseCommand(String userCommand) throws IOException{
		String[] commands = userCommand.split(" ");
		File fileDir = new File("data/"+commands[2].toLowerCase().trim());
		if(fileDir.exists()){
			for(File f:fileDir.listFiles()){
				String path = f.getAbsolutePath();
				String db = path.split("/")[path.split("/").length-2];
				tableModel.Delete(db, 3, "=");
				columnModel.Delete(db, 8, "=");
				f.delete();
			}
			fileDir.delete();
		}
		Setfoldername("user_data");
	}
	
	public static void ShowDatabaseCommand(String userCommand) throws IOException{
		List<String[]> list = tableModel.GetColumnValues(columnModel,2,"davisbase_tables","=");		
		List<String[]> list2 = tableModel.FindAll();
		System.out.println(list.get(2)[2]);
		HashMap<String,Integer> map = new HashMap<>();
		for(int i = 0; i < list2.size(); i++){
			String[] cell = list2.get(i);
			map.put(cell[2], 1);
		}
		
		File fileDir = new File("data/");
		if(fileDir.exists()){
			for(String f:fileDir.list()){
				System.out.println(f);
			}
		}
	}
	
	public static void ShowTableCommand(String userCommand) throws Exception{
		List<String[]> colValuesList = tableModel.GetColumnValues(columnModel,2,"davisbase_tables","=");		
		List<String[]> allList = tableModel.FindAll();
		System.out.print(colValuesList.get(1)[2]);
		System.out.print("\t"+colValuesList.get(2)[2]);
		System.out.print("\n");
		for(int i = 0; i < allList.size(); i++){
			String[] cell = allList.get(i);
			System.out.print(cell[1]);
			System.out.print("\t"+cell[2]);
			System.out.println("\n");
		}
	}
	
	public static void InsertIntoTableCommand(String userCommand) throws Exception{
		String tableName;
		String[] data;
		String[] columnList = {};
		if(userCommand.indexOf("(") == userCommand.lastIndexOf("(")){
			String strValues = userCommand.substring(0,userCommand.toLowerCase().indexOf("values"));
			String str2Values = userCommand.substring(userCommand.toLowerCase().indexOf("(")+1,userCommand.lastIndexOf(")"));
			
			String[] str1arr = strValues.trim().split(" ");
			tableName = str1arr[2].toLowerCase().trim();
			
			str2Values = str2Values.replaceAll("[()'\"]", "");
			data = str2Values.split(",");
			
		}else{
			tableName = userCommand.substring(userCommand.indexOf(")")+1,userCommand.toLowerCase().indexOf("values")-1).trim();
			data = userCommand.substring(userCommand.lastIndexOf("(") + 1, userCommand.lastIndexOf(")")).replaceAll("[()'\"]", "").split(",");
			columnList = userCommand.substring(userCommand.indexOf("(") + 1, userCommand.indexOf(")")).split(",");
		
		}
		

		List<String[]> tempList = columnModel.GetHeaderDetails(tableName);
		List<String[]> list = new ArrayList<>();
		
		for(int i = 0; i < tempList.size(); i++){
			String[] cell = tempList.get(i);
			if(cell[7].equals(dbName)){
				list.add(cell);
			}
		}
		
		TableFileModel tableFile = new TableFileModel(folderPath+tableName+".tbl","rw",tableName);
		//List<String[]> list = columnModel.getHeaderdetails(tableName);
		if(list.size() == 0){
			System.out.println("The Table you are inserting is not available");
			tableFile.close();
			return;
		}
		
		String[] colType = new String[data.length-1];
		String[] colValue= new String[data.length-1]; 
		int cellsize = 0;
		int rowId = 0;
		
		for(int i = 0; i < list.size(); i++){
			String[] collm = list.get(i);
			if(columnList.length != 0){
				if(!collm[2].equals(columnList[i].trim())){
					System.out.println("The column list provided does not match the table columns");
					tableFile.close();
					return;
				}
			}
			if(collm[5].toLowerCase().equals("no") && data[i].trim().toLowerCase().equals("null")){
				System.out.println("Value for "+collm[5]+" cannot be NULL");
				tableFile.close();
				return;
			}
			
			if(i == 0){
				if(!data[i].matches("\\d+")){
					System.out.println("The primary key has to be Integer");
				}else{
					rowId = Integer.parseInt(data[i].trim());
				}
			}else{
				if(collm[3].equals("float")){
					collm[3] = "double";
				}
				colType[i-1] = collm[3];
				colValue[i-1] = data[i].trim();
				if(DataType.getTypeSize(collm[3]) != 0){
					cellsize += DataType.getTypeSize(collm[3]);
				}else{
					cellsize += data[i].trim().length();
				}
			}
		}
		
		tableFile.Insert(rowId,colType,colValue,cellsize);
		tableFile.close();
	}
	
	
	public static void UpdateCommand(String userCommand) throws IOException{
		String strValues = userCommand.substring(0, userCommand.toLowerCase().indexOf("where"));
		String str2Values = userCommand.substring(userCommand.toLowerCase().indexOf("where")+6, userCommand.length());
		
		String[] str1arr = strValues.split(" ");
		String[] str2arr = str2Values.split(" ");
		
		String tableName = str1arr[1];
		String column = str1arr[3];
		String val = str1arr[5];
		int ordnum = 0;
		
		String[] condcolm = new String[str2arr.length/3];
		String[] oper1 = new String[str2arr.length/3];
		String[] values = new String[str2arr.length/3];
		String[] oper2 = new String[(str2arr.length/3)-1];
		int[] ordinalnum = new int[str2arr.length/3];
		int j = 0;
		for(int i = 0; i < str2arr.length; i+=4){
			condcolm[j] = str2arr[i];
			oper1[j] = str2arr[i+1];
			values[j] = str2arr[i+2];
			if((i+3) < str2arr.length){
				oper2[j] = str2arr[i+3];
			}
			j++;
		}
		String tableFileName = folderPath+tableName+".tbl";
		TableFileModel file = new TableFileModel(tableFileName,"rw",tableName);
		
		List<String[]> tempList = columnModel.GetHeaderDetails(tableName);
		List<String[]> list = new ArrayList<>();
		
		for(int i = 0; i < tempList.size(); i++){
			String[] cell = tempList.get(i);
			if(cell[7].equals(dbName)){
				list.add(cell);
			}
		}
		
		int numOrd = 0;
		for(int i = 0; i < list.size(); i++){
			String[] cell = list.get(i);
			String colmhead = cell[2];
			if(column.equals(colmhead)){
				ordnum = Integer.parseInt(cell[4]);
			}
			for(int k=0; k < condcolm.length; k++){
				if(colmhead.equals(condcolm[k])){
					ordinalnum[k] = Integer.parseInt(cell[4]);
					numOrd++;
				}
			}
		}
		if((numOrd != (str2arr.length/3)) || ordnum == 0){
			System.out.println("column name given is not valid column name");
			file.close();
			return;
		}
		
		file.Update(val, ordnum, ordinalnum,values, oper1, oper2);
		file.close();
	}
	
	/**
	 *  Stub method for dropping tables
	 *  @param dropTableString is a String of the user input
	 * @throws IOException 
	 */
	public static void DropTableCommand(String dropTableString) throws Exception {

		String tablename = dropTableString.toLowerCase().split(" ")[2];
		tableModel.Delete(tablename,2,"=",dbName,3,"=");
		columnModel.Delete(tablename,2,"=",dbName,8,"=");
		File delfile = new File(folderPath+tablename+".tbl");
		if(delfile.exists()){
			delfile.delete();
		}
		else
			System.out.println("No such table found.");
		
	}
	
	public static void DeleteCommand(String userCommand) throws Exception {
		String[] commandStr = userCommand.split(" ");
		if(commandStr.length < 8){
			System.out.println("Delete userCommand is not in right format");
			return;
		}
		String tableName = commandStr[3];
		String colName = commandStr[5];
		String oper = commandStr[6];
		String pattern = commandStr[7];
		String tableFileName = folderPath+tableName+".tbl";
		TableFileModel file = new TableFileModel(tableFileName,"rw",tableName);
		int ordinalnum = 0;
		List<String[]> tempList = columnModel.GetHeaderDetails(tableName);
		List<String[]> list = new ArrayList<>();
		
		for(int i = 0; i < tempList.size(); i++){
			String[] cell = tempList.get(i);
			if(cell[7].equals(dbName)){
				list.add(cell);
			}
		}
		for(int i = 0; i < list.size(); i++){
			String[] cell = list.get(i);
			if(colName.equals(cell[2])){
				ordinalnum= Integer.parseInt(cell[4]);
			}
		}
		if(ordinalnum == 0){
			System.out.println("invalid column name or table");
			file.close();
			return;
		}
		
		file.Delete(pattern, ordinalnum, oper);
		file.close();
 	}
	
	/**
	 *  Stub method for executing queries
	 *  @param queryString is a String of the user input
	 * @throws IOException 
	 * 
	 */
	public static void ParseQueryStringCommand(String userCommand) throws Exception {
		String[] commandStr = userCommand.split(" ");
		String dispcolm = commandStr[1].toLowerCase().trim();
		String tableName = commandStr[3].toLowerCase().trim();
		String tableFileName = folderPath+tableName+".tbl";
				
		TableFileModel file = new TableFileModel(tableFileName,"rw",tableName);
		List<String[]> tempList = columnModel.GetHeaderDetails(tableName);
		List<String[]> list = new ArrayList<>();
		if(tempList.size() != 0) {
			for(int i = 0; i < tempList.size(); i++){
				String[] cell = tempList.get(i);	
				if(cell[7].equals(dbName)){
					list.add(cell);
				}
				
			}
		}
		
		int ordinalnum = 0;
		List<String[]> list2;
		if(commandStr.length > 4){
			String colName = commandStr[5].toLowerCase().trim();
			String operator = commandStr[6].toLowerCase().trim();
			String value = commandStr[7].toLowerCase().trim();
			if(list.size() != 0) {
				for(int i = 0; i < list.size(); i++){
					String[] cell = list.get(i);
					if(colName.equals(cell[2])){
						ordinalnum= Integer.parseInt(cell[4]);
					}
				}
			}
			
			if(ordinalnum == 0){
				System.out.println("column name given is not valid column name");
				file.close();
				return;
			}
			list2 = file.GetColumnValues(file,ordinalnum,value,operator);
			
		}else{
			list2 = file.FindAll();
		}
		
		if(dispcolm.equals("*")){
			for(int i = 0; i < list.size(); i++){
				String[] cell = list.get(i);
				System.out.print(""+cell[2]+"\t");
			}
			System.out.print("\n");
			
			for(int i = 0; i < list2.size(); i++){
				String[] cell = list2.get(i);
				for(int j = 0; j < cell.length; j++){
					System.out.print(""+cell[j]+"\t");
				}
				System.out.print("\n");
				
			}
		}	
		file.close();
		
	}
	
	/**
	 *  Stub method for creating new tables
	 *  @param userCommand is a String of the user input
	 */
	public static void ParseCreateCommand(String userCommand) {
		String createTable = "create table ";
		String tableName = userCommand.substring(createTable.length(), userCommand.indexOf("(")).trim();
		userCommand = userCommand.substring(userCommand.indexOf("(") + 1, userCommand.lastIndexOf(")"));
		
		try {
			
			List<String[]> tempList = columnModel.GetHeaderDetails(tableName);
			List<String[]> list = new ArrayList<>();
			
			for(int i = 0; i < tempList.size(); i++){
				String[] cell = tempList.get(i);
				if(cell[7].equals(dbName)){
					list.add(cell);
				}
			}
			
			if(list.size() > 0){
				System.out.println("Table already exists");
				return;
			}
			
			String tableFileName = folderPath+tableName+".tbl";
			TableFileModel file = new TableFileModel(tableFileName,"rw",tableName);
			
			
			file.SetDbName(dbName);
			file.CreateTable(tableModel,columnModel,userCommand);
			file.close();
		}
		catch(Exception e) {
			System.out.println("Not able to create table file");
			System.out.println(e);
		}
		
	}

}
