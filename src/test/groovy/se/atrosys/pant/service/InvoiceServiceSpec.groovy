package se.atrosys.pant.service

import spock.lang.Specification
import spock.lang.Unroll

/**
 * TODO write documentation 
 */
@Unroll
class InvoiceServiceSpec extends Specification {
	def "maximum of two numbers"() {
		expect:
		Math.max(a, b) == c

		where:
		a << [3, 5, 9]
		b << [7, 4, 9]
		c << [7, 5, 9]
	}
}
