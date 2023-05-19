package bg.uni.plovdiv.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PurchaseService {

    private final String backEndUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public PurchaseService(@Value("${wholesale.backend.supplier.url}") String backEndUrl, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.backEndUrl = backEndUrl + "/purchase";
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getAllPurchases() {
        return restTemplate.exchange(backEndUrl, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    public String getPurchasesById(Long id) {
        String url = backEndUrl + "/getById?id=" + id;
        return restTemplate.exchange(url, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    public String addPurchase(String bulstat, double totalPrice, List<String> barcodes, List<Integer> quantities, String orderType) {
        String url = backEndUrl + "?bulstat=" + bulstat + "&totalPrice=" + totalPrice + "&orderType=" + orderType;
        ArrayNode jsonBody = getJsonNode(barcodes, quantities);
        String responseBody = restTemplate.exchange(url, HttpMethod.POST, createRequest(jsonBody.toString()), String.class).getBody();
        if (responseBody != null && responseBody.equals("true")) {
            return "Purchase added successfully.";
        } else {
            return "Failed to add purchase.";
        }
    }

    private ArrayNode getJsonNode(List<String> barcodes, List<Integer> quantities) {
        ArrayNode jsonArray = objectMapper.createArrayNode();

        for (int i = 0; i < barcodes.size(); i++) {
            ObjectNode barcodeAndQuantity = objectMapper.createObjectNode();
            barcodeAndQuantity.put("barcode", barcodes.get(i));
            barcodeAndQuantity.put("quantity", quantities.get(i));
            jsonArray.add(barcodeAndQuantity);
        }

        return jsonArray;
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
