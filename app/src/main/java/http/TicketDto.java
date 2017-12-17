package http;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "Ticket")
public class TicketDto {

	@Id
	@Column(nullable = false, unique = true, updatable = false)
	private String id;

	@Column(nullable = false)
	private String busId;

	@Column(nullable = false, updatable = false)
	private String createdOn;

	@Column(nullable = false, updatable = false)
	private String expiresOn;

	@Column(nullable = false)
	@SerializedName(value = "checked")
	private boolean isChecked;

	public TicketDto() {
	}

	public TicketDto(String busId, String createdOn, boolean isChecked) {
		setBusId(busId);
		setCreatedOn(createdOn);
		setChecked(isChecked);
	}

	public TicketDto(String ticketId, String busId, String createdOn, boolean isChecked) {
		this(busId, createdOn, isChecked);
		setId(ticketId);
	}

	public TicketDto(Ticket ticket) {
		setBusId(ticket.getBusId());
		setCreatedOn(Utils.simpleDateFormat.format(ticket.getCreatedOn()));
		setChecked(ticket.isChecked());
		setExpiresOn(Utils.simpleDateFormat.format(ticket.getExpiresOn()));
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

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getExpiresOn() {
		return expiresOn;
	}

	public void setExpiresOn(String expiresOn) {
		this.expiresOn = expiresOn;
	}

}
