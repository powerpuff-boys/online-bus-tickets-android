package http;

public class TicketConvertor {
	public static TicketDto convertToDto(Ticket ticket) {
		return new TicketDto(ticket);
	}

	public static Ticket convertToTicket(TicketDto ticketDto) {
		return new Ticket(ticketDto);
	}
}
