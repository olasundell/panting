package se.atrosys.pant.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;

import java.time.YearMonth;
import java.util.Set;

/**
 * TODO write documentation
 */
@Builder
@Data
@JsonDeserialize(builder = InvoicingRequest.InvoicingRequestBuilder.class)
public class InvoicingRequest {
	private Integer customerId;
	private Set<YearMonth> periods;

	@JsonPOJOBuilder(withPrefix = "")
	public static class InvoicingRequestBuilder { }
}
