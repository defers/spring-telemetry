package com.defers.springtelemetry.app.soap;

import com.defers.springtelemetry.app.soap.dto.Customer;
import com.defers.springtelemetry.app.soap.dto.GetCustomersByName;
import com.defers.springtelemetry.app.soap.dto.GetCustomersByNameResponse;
import jakarta.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CustomersExchange {
    private static final String NAMESPACE_URI = "http://customerservice.example.com/";
    private static final Logger log = LoggerFactory.getLogger(CustomersExchange.class);

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomersByName")
    public JAXBElement<GetCustomersByNameResponse> getCustomersByName(
            @RequestPayload JAXBElement<GetCustomersByName> getCustomersByName) {
        log.info("Входящий запрос {}", getCustomersByName.getName());

        var response = new GetCustomersByNameResponse();
        var customer = new Customer();
        customer.setName("Name1");
        customer.setCustomerId(5);
        customer.setRevenue(7);
        customer.setNumOrders(10);
        response.getReturn().add(customer);
        var qName = new QName(NAMESPACE_URI, "getCustomersByNameResponse");

        return new JAXBElement<>(qName, GetCustomersByNameResponse.class, null, response);
    }
}
