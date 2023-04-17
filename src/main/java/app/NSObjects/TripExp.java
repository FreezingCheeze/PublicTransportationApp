package app.NSObjects;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class TripExp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String uid;
    private String origin;
    private String destination;
    private String via;
    private String dep_time;
    private String arr_time;


    public  TripExp() {}
    public TripExp(int id,String uid,String origin, String destination,String via, String dep_time, String arr_time){
        this.setId(id);
        this.setUid(uid);
        this.setOrigin(origin);
        this.setDestination(destination);
        this.setVia(via);
        this.setDep_time(dep_time);
        this.setArr_time(arr_time);

    }

    public TripExp(String uid,String origin, String destination,String via, String dep_time, String arr_time){

        this.setUid(uid);
        this.setOrigin(origin);
        this.setDestination(destination);
        this.setVia(via);
        this.setDep_time(dep_time);
        this.setArr_time(arr_time);

    }

}
