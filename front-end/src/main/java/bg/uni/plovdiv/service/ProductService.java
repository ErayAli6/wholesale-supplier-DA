package bg.uni.plovdiv.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ProductService {

    @Value("${wholesale.backend.supplier.api}")
    private String backEndApi;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public String getAllProducts() {
        String url = backEndApi + "/product";
        return restTemplate.exchange(url, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    public String getProductByBarcode(String barcode) {
        String url = backEndApi + "/product/getByBarcode?barcode=" + barcode;
        return restTemplate.exchange(url, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    public String removeProduct(String barcode) {
        String url = backEndApi + "product/remove?barcode=" + barcode;
        return restTemplate.exchange(url, HttpMethod.DELETE, createRequest(), String.class).getBody();
    }

    public String registerProduct(String barcode, String brand, String model, String category, int quantity, double price, LocalDate manufactureDate, byte[] photo) {
        ObjectNode jsonBody = getJsonNode(barcode, brand, model, category, quantity, price, manufactureDate, photo);
        String url = backEndApi + "/product";
        return restTemplate.exchange(url, HttpMethod.POST, createRequest(jsonBody.toString()), String.class).getBody();
    }

    public String editProduct(String barcode, String brand, String model, String category, int quantity, double price, LocalDate manufactureDate, byte[] photo) {
        ObjectNode jsonBody = getJsonNode(barcode, brand, model, category, quantity, price, manufactureDate, photo);
        String url = backEndApi + "/product";
        return restTemplate.exchange(url, HttpMethod.PUT, createRequest(jsonBody.toString()), String.class).getBody();
    }

    private ObjectNode getJsonNode(String barcode, String brand, String model, String category, int quantity, double price, LocalDate manufactureDate, byte[] photo) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("barcode", barcode);
        jsonNode.put("brand", brand);
        jsonNode.put("model", model);
        jsonNode.put("category", category);
        jsonNode.put("quantity", quantity);
        jsonNode.put("price", price);
        jsonNode.put("manufactureDate", manufactureDate.toString());
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
