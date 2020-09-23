package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Building {

    private String buildingName;
    private List<Room> roomList;

    public Building(String buildingName) {
        this.buildingName = buildingName;
        this.roomList = new ArrayList<>();
    }
}

