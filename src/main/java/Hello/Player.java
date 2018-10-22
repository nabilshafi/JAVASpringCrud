package Hello;

import javafx.geometry.Pos;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Player {

    @Id
    private Long id;
    private String name;
    private String teamId;
    private  Position position;
    private String birthday;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public Player(Long id, String name, String teamId, String  birthday, Position pos ) {
        super();
        this.id = id;
        this.position = pos;
        this.name = name;
        this.birthday = birthday;
        this.teamId = teamId;

    }
    public Player(){
        super();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
