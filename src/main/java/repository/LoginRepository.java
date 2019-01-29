package repository;

import java.sql.SQLException;

public class LoginRepository extends BaseRepository {
    public int authenticateUser(String username, String password)  {
    try {
        String            sql;
        int               userID;

        sql = "select USERID from users " +
                "where USERNAME= ? AND STATUS=1 and pwdcompare(?,PASSWORD,0)=1";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);


        resultSet = preparedStatement.executeQuery();

        if(resultSet.next()) {
            userID = resultSet.getInt("USERID");
        }
        else {
            userID = -1;
        }


        return userID;

    }
    catch (SQLException e) {
        e.printStackTrace();
        return -1;
    }finally {
        this.cleanResources();
    }
}

}
