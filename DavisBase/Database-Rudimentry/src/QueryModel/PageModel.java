package QueryModel;

import java.io.IOException;
import DatabaseHelper.DatabaseConstants;
import QueryUtil.DataType;

public class PageModel {
	TableFileModel file;
	public int rowId;
	public int pageType;
	public String[] colType;
	public String[] colValue;
	public int cellSize;
	public int pageModelNum;
	static int pageSize = DatabaseConstants.PAGE_SIZE;
	
	public PageModel(TableFileModel file){
		this.file = file;
	}
	
	public PageModel(TableFileModel file,int rowId,String[] colType, String[] colValue, int cellSize,int pageModelNum,int pageType){
		this.file = file;
		this.colType = colType;
		this.colValue = colValue;
		this.pageModelNum = pageModelNum;
		this.rowId = rowId;
		this.cellSize = cellSize;
		this.pageType = pageType;
	}
	
	public PageModel(TableFileModel file, int pageModelNum, String[] colValue, int pageType){
		this.file = file;
		this.pageModelNum = pageModelNum;
		this.colValue = colValue;
		this.pageType = pageType;
	}
	
	public int WriteLeafNode() throws IOException{
		int position = pageModelNum * pageSize;
		file.seek(position);
		Byte currentPageType = file.readByte();
		short payLoadSize=0;
		short cellPosition=0;
		if(currentPageType == 0){
			file.seek(position);
			file.writeByte(pageType);
			file.seek(position+1);
			//setting right sibling page number reference to -1
			file.writeByte(0x01);
			file.seek(position+4);
			file.writeInt(-1);
			payLoadSize = (short)(colType.length+cellSize+1);
			cellPosition = (short)(pageSize - (6 + payLoadSize));		
		}else{
			int numOfCells = 0;
			file.seek(position+1);
			numOfCells = file.readByte();
			file.seek(position+2);
			cellPosition = file.readShort();
			if(cellPosition < (cellSize+colType.length+6+1+8+((numOfCells+1)*2))){
				return -1;
			}
			
			// writing record
			file.seek(position+1);
			numOfCells = file.readByte();
			file.seek(position+1);
			file.writeByte(numOfCells+1);
			cellPosition -= (cellSize+colType.length+6+1);
			payLoadSize = (short)(colType.length+cellSize+1);
		}
		file.seek(position+8);
		int j = 0;
		while(file.readShort() != 0){
			j +=2;
			file.seek(position+8+j);
		}
		file.seek(position+8+j);
		file.writeShort(cellPosition);
		file.seek(position+2);
		file.writeShort(cellPosition);
		//setting record header
		file.seek(position+cellPosition);
		file.writeShort(payLoadSize);
		file.seek(position+cellPosition+2);
		file.writeInt(rowId);
		file.seek(position+cellPosition+6);
		
		//setting payload
		file.writeByte(colType.length);
		for(int i=0; i < colType.length; i++){
			Byte type = DataType.getTypeCode(colType[i]);
			if(type == 0x0C){
				int len = colValue[i].length() & 0xFF;
				type = (byte) (type + (len & 0xFF));
			}
			file.seek(position+cellPosition+7+i);
			file.writeByte(type);
		}
		
		int newPosition = position +cellPosition+7+colType.length;
		for(int i = 0; i < colValue.length; i++){
			file.seek(newPosition);
			file.WriteByType(colValue[i], DataType.getTypeCode(colType[i]));
			newPosition += DataType.getTypeSize(colType[i]);
			if(DataType.getTypeCode(colType[i]) == 0x0C){
				newPosition += colValue[i].length();
			}
		}
		
		return 1;
	}
	
	public void WriteInteriorNode(int cellRowId,int leafPageNum) throws IOException{
		int position = pageModelNum * pageSize;
		file.seek(position);
		Byte currentPageType = file.readByte();
		short cellPosition = 0;
		int currentLeaf = 0;
		int currentRowid = 0;
		if(currentPageType != 0x05){
			file.seek(position);
			file.writeByte(pageType);
			file.seek(position+1);
			file.writeByte(0x01);
			file.seek(position+4);
			file.writeInt(-1);
			cellPosition = (short)(pageSize - 10);
		}else{
			file.seek(position+4);
			int rightPointer = file.readInt();
			if(leafPageNum == rightPointer){
				return;
			}
			file.seek(position+2);
			cellPosition = file.readShort();
			if(rightPointer != -1){
				currentLeaf = rightPointer;
				int newPosition = currentLeaf * pageSize;
				file.seek(newPosition+2);
				int newcellpos = file.readShort();
				file.seek(newPosition+newcellpos+2);
				currentRowid = file.readInt();
				cellPosition -= 8;
				file.seek(position+cellPosition);
				file.writeInt(rightPointer);
				file.seek(position+cellPosition+4);
				file.writeInt(currentRowid);
				file.seek(position+4);
				file.writeInt(leafPageNum);
				return;
			}else{
				file.seek(position+2);
				cellPosition = file.readShort();
				file.seek(position+cellPosition);
				currentLeaf = file.readInt();
				file.seek(position+cellPosition+4);
				currentRowid = file.readInt();
				if(currentLeaf == leafPageNum){
					if(cellRowId > currentRowid){
						file.seek(position+cellPosition+4);
						file.WriteByType(colValue[1], DataType.getTypeCode(DataType.INT));
					}
					return;
				}else{
					file.seek(position+4);
					rightPointer = file.readInt();
					if(rightPointer == -1){
						file.seek(position+4);
						file.writeInt(leafPageNum);
					}
					return;
				}
			}
		}
		file.seek(position+8);
		int j = 0;
		while(file.readByte() != 0){
			j +=2;
			file.seek(position+8+j);
		}
		file.seek(position+8+j);
		file.writeShort(cellPosition);
		file.seek(position+2);
		file.writeShort(cellPosition);
		file.seek(position+cellPosition);
		file.WriteByType(colValue[0], DataType.getTypeCode(DataType.INT));
		file.WriteByType(colValue[1], DataType.getTypeCode(DataType.INT));
	}
	
	public int FindLeafNode(int serialRowId,int serialPageNum,int cellSize) throws IOException{
		pageModelNum = serialPageNum;
		rowId = serialRowId;
		
		int position = pageModelNum * pageSize;
		file.seek(position+1);
		int numOfCells = file.readByte();
		int leafPageModel = 0;
		int cellPosition = 0;
		int cellRowId = 0;
		file.seek(position+4);
		int leafNodeFound = 0;
		int rightPointer = file.readInt();
		file.seek(position+8);
		for(int i = 0; i < numOfCells; i++){
			file.seek(position+8+(2*i));
			cellPosition = file.readShort();
			if(cellPosition == 0){
				continue;
			}
			file.seek(position+cellPosition);
			leafPageModel = file.readInt();
			file.seek(position+cellPosition+4);
			cellRowId = file.readInt();
			if(rowId > cellRowId){
				continue;
			}else{
				leafNodeFound = 1;
				break;
			}
		}
		if(leafNodeFound == 0){
			if(rightPointer!= -1){
				return rightPointer;
			}
		}
		return leafPageModel;	
		
	}

}
