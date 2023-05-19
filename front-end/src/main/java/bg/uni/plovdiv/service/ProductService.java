package bg.uni.plovdiv.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Base64;

@Service
public class ProductService {

    private final String backEndUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public ProductService(@Value("${wholesale.backend.supplier.url}") String backEndUrl, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.backEndUrl = backEndUrl + "/product";
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getAllProducts() {
        return restTemplate.exchange(backEndUrl, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    public String getProductByBarcode(String barcode) {
        String url = backEndUrl + "/getByBarcode?barcode=" + barcode;
        return restTemplate.exchange(url, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    public String removeProduct(String barcode) {
        String url = backEndUrl + "/remove?barcode=" + barcode;
        String responseBody = restTemplate.exchange(url, HttpMethod.DELETE, createRequest(), String.class).getBody();
        if (responseBody != null && responseBody.equals("true")) {
            return "Product removed successfully.";
        } else {
            return "Failed to remove product.";
        }
    }

    public String registerProduct(String barcode, String brand, String model, String category, int quantity, double price, LocalDate manufactureDate, byte[] photo) {
        ObjectNode jsonBody = getJsonNode(barcode, brand, model, category, quantity, price, manufactureDate, photo);
        String responseBody = restTemplate.exchange(backEndUrl, HttpMethod.POST, createRequest(jsonBody.toString()), String.class).getBody();
        if (responseBody != null && responseBody.equals("true")) {
            return "Product registered successfully.";
        } else {
            return "Failed to register product.";
        }
    }

    public String editProduct(String barcode, String brand, String model, String category, int quantity, double price, LocalDate manufactureDate, byte[] photo) {
        ObjectNode jsonBody = getJsonNode(barcode, brand, model, category, quantity, price, manufactureDate, photo);
        String responseBody = restTemplate.exchange(backEndUrl, HttpMethod.PUT, createRequest(jsonBody.toString()), String.class).getBody();
        if (responseBody != null && responseBody.equals("true")) {
            return "Product edited successfully.";
        } else {
            return "Failed to edit product.";
        }
    }

    private ObjectNode getJsonNode(String barcode, String brand, String model, String category, int quantity, double price, LocalDate manufactureDate, byte[] photo) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("barcode", barcode);
        jsonNode.put("brand", brand);
        jsonNode.put("model", model);
        jsonNode.put("category", category);
        jsonNode.put("quantity", quantity);
        jsonNode.put("price", price);
        if (manufactureDate != null) {
            jsonNode.put("manufactureDate", manufactureDate.toString());
        }
        if (photo != null) {
            jsonNode.put("photo", Base64.getEncoder().encodeToString(photo));
        }
        return jsonNode;
    }

    private HttpEntity<String> createRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<String> createRequest(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }
}
