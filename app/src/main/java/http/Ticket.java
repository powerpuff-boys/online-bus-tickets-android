package http;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Ticket {

	private String id;

	private String busId;

	private Date createdOn;

	private Date expiresOn;

	private boolean isChecked;

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public Ticket() {
	}

	public Ticket(String busId, Date createdOn, boolean isChecked) {
		setBusId(busId);
		setCreatedOn(createdOn);
		setChecked(isChecked);
		setExpiresOn();
	}

	public Ticket(String ticketId, String busId, Date createdOn, boolean isChecked) {
		this(busId, createdOn, isChecked);
		setId(ticketId);
		setExpiresOn();
	}

	public Ticket(TicketDto ticketDto) {
		setId(ticketDto.getId());
		setBusId(ticketDto.getBusId());
		try {
			setCreatedOn(simpleDateFormat.parse(ticketDto.getCreatedOn()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		setExpiresOn();
		setChecked(ticketDto.isChecked());

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date localDateTime2) {
		this.createdOn = localDateTime2;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Date getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(createdOn);
		cal.add(Calendar.HOUR, 2);
		this.expiresOn = cal.getTime();
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", busId=" + busId + ", createdOn=" + createdOn + ", isChecked=" + isChecked + "]";
	}

}
