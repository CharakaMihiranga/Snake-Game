package lk.ijse.model;

import lk.ijse.dto.ScoreDto;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScoreModel {
    public boolean save(ScoreDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("Insert into user values (?,?)",
                dto.getName(),
                dto.getScore()
        );
    }
    public ArrayList<ScoreDto> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM score");
        ArrayList<ScoreDto> scores = new ArrayList<>();

        while (resultSet.next()){
            ScoreDto dto = new ScoreDto(
                    resultSet.getString(1),
                    resultSet.getInt(2)
            );
            scores.add(dto);
        }
        return scores;
    }
}
