package ru.geekbrains.gainanov.market.core.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.gainanov.market.core.converters.ProductConverter;
import ru.geekbrains.gainanov.market.core.services.ProductService;
import ru.geekbrains.gainanov.market.core.soap.products.GetAllProductsRequest;
import ru.geekbrains.gainanov.market.core.soap.products.GetAllProductsResponse;
import ru.geekbrains.gainanov.market.core.soap.products.GetProductByIdRequest;
import ru.geekbrains.gainanov.market.core.soap.products.GetProductByIdResponse;

import java.util.stream.Collectors;

@Endpoint
@RequiredArgsConstructor
public class ProductsEndpoint {
    private static final String NAMESPACE_URI = "http://www.geekbrains.ru/spring/gainanovmarket/products";
    private final ProductService productService;
    private final ProductConverter productConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        response.setProduct(productConverter.entityToWs(productService.findById(request.getId()).get()));
        return response;
    }

    /*
    Пример запроса: POST http://localhost:8189/market/ws
    Header -> Content-Type: text/xml

    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.ru/spring/gainanovmarket/products">
        <soapenv:Header/>
        <soapenv:Body>
            <f:getAllProductsRequest/>
        </soapenv:Body>
    </soapenv:Envelope>

     <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.ru/spring/gainanovmarket/products">
        <soapenv:Header/>
        <soapenv:Body>
            <f:getProductByIdRequest>
                <f:id>1</f:id>
            </f:getProductByIdRequest>
        </soapenv:Body>
    </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        System.out.println(request);
        productService.findAll()
                .stream()
                .map(productConverter::entityToWs)
                .collect(Collectors.toList())
                .forEach(response.getProducts()::add);
        return response;
    }
}
