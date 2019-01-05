package BuildTree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import DatabaseHelper.DatabaseConstants;
import QueryModel.PageModel;
import QueryModel.TableFileModel;
import QueryUtil.DataType;


public class TreeBuilder {
	public TableFileModel file;
	static int pageSize = DatabaseConstants.PAGE_SIZE;

	public TreeBuilder(TableFileModel tableModel){
		this.file = tableModel;
	}

	public boolean InteriorCellPos(int pageNum) throws IOException{
		int position = (pageNum) * pageSize;
		position += 1;

		file.seek(position);
		Byte numOfCells = file.readByte();
		if(numOfCells < ((pageSize-8)/10)){
			return true;
		}else{
			return false;
		}
	}

	public void AddNewCell(int pageNum,Integer leafPageNum,Integer rowId) throws IOException{
		int pos = pageNum * pageSize;
		file.seek(pos);
		//byte pagetype = file.readByte();

		//if(pagetype != 0x05){
		String[] colvalue = new String[2];
		colvalue[1] = rowId.toString();
		colvalue[0] = leafPageNum.toString();
		PageModel page = new PageModel(file, pageNum,colvalue, 0x05);
		page.WriteInteriorNode(rowId,leafPageNum);
		//}else if (pagetype == 0x05){

		//}
	}

	public void DeletePattern(String pattern) throws IOException{
		int position = FindFirstLeafNode() * pageSize;
		int cellPosition = 0;
		int numOfCol = 0;
		int rightPointer = file.readInt();
		int patternFound = 0;
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			file.seek(position+4);
			rightPointer = file.readInt();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				patternFound = 0;
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				file.seek(position+cellPosition+6);
				numOfCol = file.readByte();
				String colValue;
				int colTypePosition = position+cellPosition+7;
				int colValuePos = position+cellPosition+7+numOfCol;
				for(int j = 0; j < numOfCol; j++){
					colTypePosition = position+cellPosition+7+j;
					file.seek(colTypePosition);
					Byte type = file.readByte();
					file.seek(colValuePos);
					colValue = file.ReadByType(type,1);
					if(colValue.equals(pattern)){
						patternFound = 1;
					}
					int size = DataType.getTypeSize(DataType.getCodeval(type));
					if(size == 0){
						colValuePos += colValue.length();
					}else{
						colValuePos += size;
					}
				}
				if(patternFound == 1){
					file.seek(position+cellPosition);
					int cellSize = file.readShort()+6;
					for(int k = 0; k < cellSize; k++){
						file.seek(position+cellPosition+k);
						file.writeByte(0x00);
					}
					file.seek(newPosition+(2*i));
					file.writeShort(0);
					if(i == numOfCells-1){
						file.seek(position+1);
						file.writeByte(numOfCells-1);
					}
				}
			}
			position = (rightPointer == -1) ? position :rightPointer*pageSize;
		}while(rightPointer != -1);
	}


	public void DeletePattern(String pattern, int ordinalNum, String operator) throws IOException{
		int position = FindFirstLeafNode() * pageSize;
		int cellPosition = 0;
		int numOfColumns = 0;
		int rightPointer = file.readInt();
		int patternFound = 0;
		int orderNumChk = 0;
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			file.seek(position+4);
			rightPointer = file.readInt();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				patternFound = 0;
				orderNumChk = 1;
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				if(cellPosition == 0){
					continue;
				}
				file.seek(position+cellPosition+6);
				numOfColumns = file.readByte();
				file.seek(position+cellPosition+2);
				Integer rowId = file.readInt();
				String colValue = rowId.toString();
				if(orderNumChk == ordinalNum){
					if(file.CompareToValue((byte)0x06, colValue, pattern, operator)){
						patternFound = 1;
					}
				}
				int colTypePosition = position+cellPosition+7;
				int colValuePos = position+cellPosition+7+numOfColumns;
				for(int j = 0; j < numOfColumns; j++){
					orderNumChk++;
					colTypePosition = position+cellPosition+7+j;
					file.seek(colTypePosition);
					Byte type = file.readByte();
					file.seek(colValuePos);
					colValue = file.ReadByType(type,1);
					if(orderNumChk == ordinalNum){
						if(file.CompareToValue(type, colValue, pattern, operator)){
							patternFound = 1;
						}
					}
					int size = DataType.getTypeSize(DataType.getCodeval(type));
					if(size == 0){
						colValuePos += colValue.length();
					}else{
						colValuePos += size;
					}
				}
				if(patternFound == 1){
					file.seek(position+cellPosition);
					int cellSize = file.readShort()+6;
					for(int k = 0; k < cellSize; k++){
						file.seek(position+cellPosition+k);
						file.writeByte(0x00);
					}
					file.seek(newPosition+(2*i));
					file.writeShort(0);
					if(i == numOfCells-1){
						file.seek(position+1);
						file.writeByte(numOfCells-1);
					}
				}
			}
			position = (rightPointer == -1) ? position :rightPointer*pageSize;
		}while(rightPointer != -1);
	}

	public void DeletePattern(String pattern, int ordinalnum, String oper,String pattern2,int ordinalnum2, String oper2) throws IOException{
		int position = FindFirstLeafNode() * pageSize;
		int cellPosition = 0;
		int numOfColumns = 0;
		int rightPointer = file.readInt();
		int patternFound = 0;
		int pattern2found = 0;
		int orderNumChk = 0;
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			file.seek(position+4);
			rightPointer = file.readInt();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				patternFound = 0;
				pattern2found = 0;
				orderNumChk = 1;
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				if(cellPosition == 0){
					continue;
				}
				file.seek(position+cellPosition+6);
				numOfColumns = file.readByte();
				file.seek(position+cellPosition+2);
				Integer rowId = file.readInt();
				String colValue = rowId.toString();
				if(orderNumChk == ordinalnum){
					if(file.CompareToValue((byte)0x06, colValue, pattern, oper)){
						patternFound = 1;
					}
				}
				if(orderNumChk == ordinalnum2){
					if(file.CompareToValue((byte)0x06, colValue, pattern2, oper2)){
						pattern2found = 1;
					}
				}
				int colTypePosition = position+cellPosition+7;
				int colValuePos = position+cellPosition+7+numOfColumns;
				for(int j = 0; j < numOfColumns; j++){
					orderNumChk++;
					colTypePosition = position+cellPosition+7+j;
					file.seek(colTypePosition);
					Byte type = file.readByte();
					file.seek(colValuePos);
					colValue = file.ReadByType(type,1);
					if(orderNumChk == ordinalnum){
						if(file.CompareToValue(type, colValue, pattern, oper)){
							patternFound = 1;
						}
					}
					if(orderNumChk == ordinalnum2){
						if(file.CompareToValue(type, colValue, pattern2, oper2)){
							pattern2found = 1;
						}
					}
					int size = DataType.getTypeSize(DataType.getCodeval(type));
					if(size == 0){
						colValuePos += colValue.length();
					}else{
						colValuePos += size;
					}
				}
				if(patternFound == 1 & pattern2found==1){
					file.seek(position+cellPosition);
					int cellSize = file.readShort()+6;
					for(int k = 0; k < cellSize; k++){
						file.seek(position+cellPosition+k);
						file.writeByte(0x00);
					}
					file.seek(newPosition+(2*i));
					file.writeShort(0);
					if(i == numOfCells-1){
						file.seek(position+1);
						file.writeByte(numOfCells-1);
					}
				}
			}
			position = (rightPointer == -1) ? position :rightPointer*pageSize;
		}while(rightPointer != -1);
	}

	public void Update(String value, int ordnum, int[] Ordinalnum,String[] values, String[] oper1, String[] oper2) throws IOException{
		int[] found = new int[oper1.length];
		int position = FindFirstLeafNode() * pageSize;
		int cellPosition = 0;
		int numOfColumns = 0;
		int rightPointer = file.readInt();
		//List<String[]> list = new ArrayList<String[]>();
		int patternFound = 0;
		int orderNumChk = 0;
		int conditionMet = 0;
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			file.seek(position+4);
			rightPointer = file.readInt();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				patternFound = 0;
				orderNumChk = 1;
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				if(cellPosition == 0){
					continue;
				}
				file.seek(position+cellPosition+6);
				numOfColumns = file.readByte();
				file.seek(position+cellPosition+2);
				Integer rowId = file.readInt();
				String colValue = rowId.toString();
				for(int k=0; k < Ordinalnum.length; k++){
					int ordinalnum = Ordinalnum[k];
					if(orderNumChk == ordinalnum){
						if(file.CompareToValue((byte)0x06, colValue, values[k], oper1[k])){
							found[k] = 1;
							patternFound = 1;
						}
					}
				}
				int colTypePosition = position+cellPosition+7;
				int colValuePos = position+cellPosition+7+numOfColumns;
				for(int j = 0; j < numOfColumns; j++){
					orderNumChk++;
					colTypePosition = position+cellPosition+7+j;
					file.seek(colTypePosition);
					Byte type = file.readByte();
					file.seek(colValuePos);
					colValue = file.ReadByType(type,1);
					for(int k=0; k < Ordinalnum.length; k++){
						int ordinalnum = Ordinalnum[k];
						if(orderNumChk == ordinalnum){
							if(file.CompareToValue(type, colValue, values[k], oper1[k])){
								found[k] = 1;
								patternFound = 1;
							}
						}
					}
					int size = DataType.getTypeSize(DataType.getCodeval(type));
					if(size == 0){
						colValuePos += colValue.length();
					}else{
						colValuePos += size;
					}
				}
				if(patternFound == 1){
					conditionMet = found[0];
					for(int k=1; k < found.length; k++){
						if(oper2[k-1].equals("and")){
							conditionMet = conditionMet & found[k];
						}else{
							conditionMet = conditionMet | found[k];
						}
					}

					if(conditionMet == 1){
						orderNumChk = 1;
						file.seek(position+cellPosition+6);
						numOfColumns = file.readByte();
						file.seek(position+cellPosition+2);
						if(ordnum == orderNumChk){
							file.writeInt(Integer.parseInt(value));
						}
						colTypePosition = position+cellPosition+7;
						colValuePos = position+cellPosition+7+numOfColumns;
						for(int j = 0; j < numOfColumns; j++){
							orderNumChk++;
							colTypePosition = position+cellPosition+7+j;
							file.seek(colTypePosition);
							Byte type = file.readByte();
							file.seek(colValuePos);
							colValue = file.ReadByType(type,1);
							//int datalen = 0;
							if(ordnum == orderNumChk){
								file.seek(colValuePos);
								file.WriteByType(value, type);
							}
							int size = DataType.getTypeSize(DataType.getCodeval(type));
							if(size == 0){
								colValuePos += colValue.length();
							}else{
								colValuePos += size;
							}
						}
					}
				}
			}
			position = (rightPointer == -1) ? position :rightPointer*pageSize;
		}while(rightPointer != -1);
	}

	public int FindAddLeafForCell(int rowId,String[] coltype, String[] colValue, int cellSize,int pageno) throws IOException{
		int ret = 0;
		Byte pagetype = 0;
		if(pageno == -1){

		}else{
			int position = (pageno)*pageSize;
			file.seek(position);
			pagetype = file.readByte();
		}
		if(pagetype == 0){
			int newpageno = (int)file.length()/pageSize;
			file.setLength(file.length()+pageSize);
			PageModel page = new PageModel(file,rowId,coltype,colValue,cellSize,newpageno,(byte)0x0D);
			page.WriteLeafNode();
			return newpageno;
		}else if(pagetype == 0x05){
			PageModel page = new PageModel(file);
			int leafnode = page.FindLeafNode(rowId,pageno,cellSize);
			page = new PageModel(file,rowId,coltype,colValue,cellSize,leafnode,(byte)0x0D);
			ret = page.WriteLeafNode();
			if(ret == -1){
				int oldleafnode = leafnode;
				leafnode = FindAddLeafForCell(rowId,coltype,colValue,cellSize,-1);
				int position = (oldleafnode)*pageSize;
				file.seek(position+4);
				file.writeInt(leafnode);
			}
			return leafnode;
		}else{

		}

		return ret;
	}

	public int FindLastRowId(int pageno) throws IOException{
		int position = (pageno)*pageSize;
		file.seek(position);
		Byte pagetype = file.readByte();
		if(pagetype == 0){
			return 0;
		}else if(pagetype == 0x05){
			file.seek(position+4);
			int rightpointer = file.readInt();
			if(rightpointer != -1){
				return FindLastRowId(rightpointer);
			}else{
				file.seek(position+2);
				short lastcellpos = file.readShort();
				file.seek(position+lastcellpos);
				int leafpage = file.readInt();
				return FindLastRowId(leafpage);
			}
		}else if(pagetype == 0x0D){
			file.seek(position+1);
			short lastcellpos;
			int numOfCells = file.readByte();
			while(true){
				file.seek(position+8+(numOfCells*2));
				lastcellpos = file.readShort();
				if(lastcellpos == 0){
					numOfCells--;
				}
				else{
					break;
				}
				if(numOfCells == 0){
					return -1;
				}
			}
			/*file.seek(position+2);
			short lastcellpos = file.readShort();*/
			file.seek(position+lastcellpos+2);
			return file.readInt();
		}

		return -1;
	}


	public void Insert(int rowId,String[] coltype, String[] colValue, int cellSize,int pageno,boolean leaf) throws IOException{
		if(!leaf){
			if(file.length() < ((pageno+1)*pageSize)){
				file.setLength((pageno+1)*pageSize);
			}

			if(InteriorCellPos(pageno)){
				int leafpageno = FindAddLeafForCell(rowId,coltype,colValue,cellSize,pageno);

				AddNewCell(pageno,leafpageno,rowId);
			}
		}
	}

	public int FindFirstLeafNode() throws IOException{
		int position = 0;
		int leafnode = 0;
		int cellPosition = 0;
		while(true){
			file.seek(position+8);
			cellPosition = file.readShort();
			file.seek(position+cellPosition);
			leafnode = file.readInt();
			int newPosition = leafnode * pageSize;
			file.seek(newPosition);
			int pagetype = file.readByte();
			if(pagetype == 0x0D){
				break;
			}else{
				position = newPosition;
			}
		}

		return leafnode;
	}

	public List<String[]> FindHeader(String table_name) throws IOException{
		int position = FindFirstLeafNode() * pageSize;
		file.seek(position+4);
		int cellPosition = 0;
		int numOfColumns = 0;
		int rightPointer = file.readInt();
		List<String[]> list = new ArrayList<String[]>();
		String colValue[];
		boolean cellfound = false;
		boolean cellpassed = false;
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				if(cellPosition == 0){
					continue;
				}
				file.seek(position+cellPosition+6);
				numOfColumns = file.readByte();
				file.seek(position+cellPosition+7);
				int tblnamesize = file.readByte() - 0x0C;
				int tblnamepos = position+cellPosition+7+numOfColumns;
				file.seek(tblnamepos);
				String tblname = file.ReadString(tblnamesize);
				if(tblname.equals(table_name)){
					colValue = new String[numOfColumns+1];
					file.seek(position+cellPosition+2);
					Integer rowId = file.readInt();
					colValue[0] = rowId.toString();
					int colTypePosition = position+cellPosition+7;
					int colValuePos = position+cellPosition+7+numOfColumns;
					for(int j = 0; j < numOfColumns; j++){
						colTypePosition = position+cellPosition+7+j;
						file.seek(colTypePosition);
						Byte type = file.readByte();
						file.seek(colValuePos);
						colValue[j+1] = file.ReadByType(type,1);
						int size = DataType.getTypeSize(DataType.getCodeval(type));
						if(size == 0){
							colValuePos += colValue[j+1].length();
						}else{
							colValuePos += size;
						}
					}
					list.add(colValue);
					cellfound = true;
				}else if(cellfound == true){
					cellpassed = true;
				}

			}
			if(cellfound == true && cellpassed == true){
				break;
			}else{
				position = (rightPointer == -1) ? position :rightPointer;
			}
		}while(rightPointer != -1);

		return list;

	}

	public List<String[]> FindAll() throws IOException{
		int position = FindFirstLeafNode() * pageSize;
		file.seek(position+4);
		int cellPosition = 0;
		int numOfColumns = 0;
		int rightPointer = file.readInt();
		List<String[]> list = new ArrayList<String[]>();
		String colValue[];
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				if(cellPosition == 0){
					continue;
				}
				file.seek(position+cellPosition+6);
				numOfColumns = file.readByte();
				colValue = new String[numOfColumns+1];
				file.seek(position+cellPosition+2);
				Integer rowId = file.readInt();
				colValue[0] = rowId.toString();
				int colTypePosition = position+cellPosition+7;
				int colValuePos = position+cellPosition+7+numOfColumns;
				for(int j = 0; j < numOfColumns; j++){
					colTypePosition = position+cellPosition+7+j;
					file.seek(colTypePosition);
					Byte type = file.readByte();
					
					file.seek(colValuePos);
					colValue[j+1] = file.ReadByType(type,1);
//					if(type == 11) {
//						long t = Long.parseLong(colValue[j+1]); 
//						System.out.println("long val "+ t);
//						 ZoneId zoneId = ZoneId.of ("America/Chicago" );
//						 Instant ins = Instant.ofEpochSecond(t);
//						 ZonedDateTime zdt2 = ZonedDateTime.ofInstant(ins, zoneId);
//						 Date date = Date.from(zdt2.toInstant());
//						 DateFormat formatter;
//						 formatter = new SimpleDateFormat("yyyy-MM-dd");
//						 formatter.setLenient(false);
//						 String x = formatter.format(date);
//						 System.out.println(x);
//					}
					int size = DataType.getTypeSize(DataType.getCodeval(type));
					if(size == 0){
						colValuePos += colValue[j+1].length();
					}else{
						colValuePos += size;
					}
				}
				list.add(colValue);

			}
			position = (rightPointer == -1) ? position :rightPointer;
		}while(rightPointer != -1);

		return list;
	}

	public List<String[]> FindColumnEntry(String pattern) throws IOException{
		int position = FindFirstLeafNode() * pageSize;
		int cellPosition = 0;
		int numOfColumns = 0;
		int rightPointer = file.readInt();
		List<String[]> list = new ArrayList<String[]>();
		String colValue[];
		int patternFound = 0;
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			file.seek(position+4);
			rightPointer = file.readInt();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				patternFound = 0;
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				if(cellPosition == 0){
					continue;
				}
				file.seek(position+cellPosition+6);
				numOfColumns = file.readByte();
				colValue = new String[numOfColumns+1];
				file.seek(position+cellPosition+2);
				Integer rowId = file.readInt();
				colValue[0] = rowId.toString();
				int colTypePosition = position+cellPosition+7;
				int colValuePos = position+cellPosition+7+numOfColumns;
				for(int j = 0; j < numOfColumns; j++){
					colTypePosition = position+cellPosition+7+j;
					file.seek(colTypePosition);
					Byte type = file.readByte();
					file.seek(colValuePos);
					colValue[j+1] = file.ReadByType(type,1);
					if(colValue[j+1].equals(pattern)){
						patternFound = 1;
					}
					int size = DataType.getTypeSize(DataType.getCodeval(type));
					if(size == 0){
						colValuePos += colValue[j+1].length();
					}else{
						colValuePos += size;
					}
				}
				if(patternFound == 1){
					list.add(colValue);
				}else{
					continue;
				}

			}
			position = (rightPointer == -1) ? position :rightPointer*pageSize;
		}while(rightPointer != -1);

		return list;

	}

	public List<String[]> FindColumnEntry(String pattern,int ordinalnum, String oper) throws IOException{
		int position = FindFirstLeafNode() * pageSize;
		int cellPosition = 0;
		int numOfColumns = 0;
		int rightPointer = file.readInt();
		List<String[]> list = new ArrayList<String[]>();
		String colValue[];
		int patternFound = 0;
		int orderNumChk = 0;
		do{
			file.seek(position+1);
			int numOfCells = file.readByte();
			file.seek(position+4);
			rightPointer = file.readInt();
			int newPosition = position +8;
			cellPosition = 0;
			for(int i = 0; i < numOfCells; i++){
				patternFound = 0;
				orderNumChk = 1;
				file.seek(newPosition+(2*i));
				cellPosition = file.readShort();
				if(cellPosition == 0){
					continue;
				}
				file.seek(position+cellPosition+6);
				numOfColumns = file.readByte();
				colValue = new String[numOfColumns+1];
				file.seek(position+cellPosition+2);
				Integer rowId = file.readInt();
				colValue[0] = rowId.toString();
				if(orderNumChk == ordinalnum){
					if(file.CompareToValue((byte)0x06, colValue[0], pattern, oper)){
						patternFound = 1;
					}
				}
				int colTypePosition = position+cellPosition+7;
				int colValuePos = position+cellPosition+7+numOfColumns;
				for(int j = 0; j < numOfColumns; j++){
					orderNumChk++;
					colTypePosition = position+cellPosition+7+j;
					file.seek(colTypePosition);
					Byte type = file.readByte();
					file.seek(colValuePos);
					colValue[j+1] = file.ReadByType(type,1);
					if(orderNumChk == ordinalnum){
						if(file.CompareToValue(type, colValue[j+1], pattern, oper)){
							patternFound = 1;
						}
					}
					int size = DataType.getTypeSize(DataType.getCodeval(type));
					if(size == 0){
						colValuePos += colValue[j+1].length();
					}else{
						colValuePos += size;
					}
				}
				if(patternFound == 1){
					list.add(colValue);
				}else{
					continue;
				}

			}
			position = (rightPointer == -1) ? position :rightPointer*pageSize;
		}while(rightPointer != -1);

		return list;

	}

}
