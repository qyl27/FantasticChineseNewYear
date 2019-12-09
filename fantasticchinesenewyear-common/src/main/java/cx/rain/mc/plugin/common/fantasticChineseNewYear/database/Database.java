package cx.rain.mc.plugin.common.fantasticChineseNewYear.database;

import cx.rain.mc.plugin.common.fantasticChineseNewYear.util.enumerate.DatabaseType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Database {
    private DatabaseType type = null;
    private IDatabase db = null;

    private static HashMap<String, String> tables = new HashMap<>();

    static {
        tables.put("redpackets",
                "create table if not exists `redpackets` (" +
                        "`Id` int(11) not null auto_increment," +
                        "`SenderUuid` text not null," +
                        "`SendTime` bigint(20) not null," +
                        "`ExpireTime` bigint(20) not null," +
                        "`RedPacketType` tinyint(1) not null," +
                        "`RewardType` tinyint(1) not null," +
                        "`Items` text null," +
                        "`Money` double null default null," +
                        "`Points` double null default null," +
                        "`Amount` int(11) null default null," +
                        "`ReceiverPlayerUuid` text null," +
                        "`Password` text null," +
                        "`ClaimPlayersAndRewards` text not null," +
                        "primary key (`Id`)" +
                        ") engine = InnoDB auto_increment = 1"
        );
    }

    public Database(DatabaseType dbType, String host, int port, String user, String password,
                    String databaseName, boolean useSsl,
                    String path, String fileName) {
        type = dbType;
        switch (dbType) {
            case MySql:
                db = new MySql(host, port, user, password, databaseName, useSsl);
                break;
            case Sqlite:
                db = new Sqlite(path, fileName);
                break;
        }
    }

    public DatabaseType getType() {
        return type;
    }

    public Connection getConnection() throws SQLException {
        return db.getConnection();
    }

    public boolean isDatabaseInitialized() throws SQLException {
        ArrayList<String> tableExists = new ArrayList<>();
        ArrayList<String> tableNames = new ArrayList<>(tables.keySet());

        Statement st =  getConnection().createStatement();
        ResultSet rs = st.executeQuery("show tables;");
        while (rs.next()) {
            tableExists.add(rs.getString(1));
        }
        rs.close();
        st.close();

        Collections.sort(tableNames);
        Collections.sort(tableExists);

        return tableNames.equals(tableExists);
    }

    public void initializeDatabase() throws SQLException {
        if (!isDatabaseInitialized()) {
            // Todo: Clear old struct?
            /*
            for (String s : tables.keySet()) {
                PreparedStatement ps = getConnection().prepareStatement("drop table if exists `" + s + "`;");
                ps.execute();
                ps.close();
            }
             */

            for (String s : tables.values()) {
                PreparedStatement ps = getConnection().prepareStatement(s);
                ps.execute();
                ps.close();
            }
        }
    }

    public void dispose() {
        db.dispose();
    }
}
