package sg.edu.nus.iss.sereserch.ethan.dao;

/**
 *
 */
public class DAOConstants {
    public static final String dBName="//Annotate.db";
    public static final String QUERY_CREATE_ANNOTATIONS_TABLE ="CREATE TABLE IF NOT EXISTS Annotations(filename TEXT,xCoordinate REAL,yCoordinate REAL,label TEXT,name TEXT,color TEXT,size TEXT,rotation TEXT);";
    public static final String QUERY_DELETE_ANNOTATIONS_TABLE = "DELETE FROM Annotations WHERE filename=";
    public static final String QUERY_SELECT_ANNOTATIONS_TABLE = "SELECT * FROM Annotations WHERE TRIM(filename) =";
    public static final String QUERY_INSERT_ANNOTATIONS_TABLE = "INSERT INTO Annotations VALUES(";
}
