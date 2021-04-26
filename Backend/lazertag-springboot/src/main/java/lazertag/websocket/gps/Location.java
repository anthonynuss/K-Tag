package lazertag.websocket.gps;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "location")
@Data
public class Location {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Size(max = 100)
    @Column
    private int userId;
	
	@NotNull
    @Lob
    private float latitude;
	
	@NotNull
    @Lob
    private float longitude;
	
	@NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent")
    private Date sent = new Date();
	
	
	public Location() {};
	
	public Location(int userId, float latitude, float longitude) {
		this.userId = userId;
		this.latitude = latitude;
		this.longitude = longitude;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }
    
    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    
    public float[] getLocation() {
    	
    	return new float[]{ latitude,longitude};
    }
    
    public void setLocation(float location[]) {
    	latitude = location[1];
    	longitude = location[2];
    }

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    
}
