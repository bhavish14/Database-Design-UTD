package DatabaseHelper;

public interface DatabaseConstants {
	public int PAGE_SIZE = 512;
	public static final byte TABLES_TABLE_SCHEMA_ROWID = 0;
    public static final byte TABLES_TABLE_SCHEMA_DATABASE_NAME = 1;
    public static final byte TABLES_TABLE_SCHEMA_TABLE_NAME = 2;
    public static final byte TABLES_TABLE_SCHEMA_RECORD_COUNT = 3;
    public static final byte TABLES_TABLE_SCHEMA_COL_TBL_ST_ROWID = 4;
    public static final byte TABLES_TABLE_SCHEMA_NXT_AVL_COL_TBL_ROWID = 5;


    public static final byte COLUMNS_TABLE_SCHEMA_ROWID = 0;
    public static final byte COLUMNS_TABLE_SCHEMA_DATABASE_NAME = 1;
    public static final byte COLUMNS_TABLE_SCHEMA_TABLE_NAME = 2;
    public static final byte COLUMNS_TABLE_SCHEMA_COLUMN_NAME = 3;
    public static final byte COLUMNS_TABLE_SCHEMA_DATA_TYPE = 4;
    public static final byte COLUMNS_TABLE_SCHEMA_COLUMN_KEY = 5;
    public static final byte COLUMNS_TABLE_SCHEMA_ORDINAL_POSITION = 6;
    public static final byte COLUMNS_TABLE_SCHEMA_IS_NULLABLE = 7;

    public static final String PRIMARY_KEY_IDENTIFIER = "PRI";

	public static final String SELECT_COMMAND = "SELECT";
	public static final String DROP_TABLE_COMMAND = "DROP TABLE";
	public static final String DROP_DATABASE_COMMAND = "DROP DATABASE";
	public static final String HELP_COMMAND = "HELP";
	public static final String VERSION_COMMAND = "VERSION";
	public static final String EXIT_COMMAND = "EXIT";
	public static final String SHOW_TABLES_COMMAND = "SHOW TABLES";
	public static final String SHOW_DATABASES_COMMAND = "SHOW DATABASES";
	public static final String INSERT_COMMAND = "INSERT INTO";
	public static final String DELETE_COMMAND = "DELETE FROM";
	public static final String UPDATE_COMMAND = "UPDATE";
	public static final String CREATE_TABLE_COMMAND = "CREATE TABLE";
	public static final String CREATE_DATABASE_COMMAND = "CREATE DATABASE";
	public static final String USE_DATABASE_COMMAND = "USE";
	public static final String DESC_TABLE_COMMAND = "DESC";
	public  static final String NO_DATABASE_SELECTED_MESSAGE = "No database selected";
    public static final String USE_HELP_MESSAGE = "\nType 'help;' to display supported commands.";
    
    public String PROMPT = "DavisBase> ";
    public String VERSION = "v1.0";
    public String COPYRIGHT = "©2018 DavisBase Group I";

    public static String DEFAULT_FILE_EXTENSION = ".tbl";
    public static String DEFAULT_DATA_DIRNAME = "user_data";
    String DEFAULT_CATALOG_DATABASENAME = "catalog";
    String SYSTEM_TABLES_TABLENAME = "davisbase_tables";
    String SYSTEM_COLUMNS_TABLENAME = "davisbase_columns";

    //DataTypeEnum Class DatabaseConstants
    byte INVALID_CLASS = -1;
    byte TINYINT = 0;
    byte SMALLINT = 1;
    byte INT = 2;
    byte BIGINT = 3;
    byte REAL = 4;
    byte DOUBLE = 5;
    byte DATE = 6;
    byte DATETIME = 7;
    byte TEXT = 8;

    //Serial Code DatabaseConstants
    byte ONE_BYTE_NULL_SERIAL_TYPE_CODE = 0x00;
    byte TWO_BYTE_NULL_SERIAL_TYPE_CODE = 0x01;
    byte FOUR_BYTE_NULL_SERIAL_TYPE_CODE = 0x02;
    byte EIGHT_BYTE_NULL_SERIAL_TYPE_CODE = 0x03;
    byte TINY_INT_SERIAL_TYPE_CODE = 0x04;
    byte SMALL_INT_SERIAL_TYPE_CODE = 0x05;
    byte INT_SERIAL_TYPE_CODE = 0x06;
    byte BIG_INT_SERIAL_TYPE_CODE = 0x07;
    byte REAL_SERIAL_TYPE_CODE = 0x08;
    byte DOUBLE_SERIAL_TYPE_CODE = 0x09;
    byte DATE_TIME_SERIAL_TYPE_CODE = 0x0A;
    byte DATE_SERIAL_TYPE_CODE = 0x0B;
    byte TEXT_SERIAL_TYPE_CODE = 0x0C;

}
