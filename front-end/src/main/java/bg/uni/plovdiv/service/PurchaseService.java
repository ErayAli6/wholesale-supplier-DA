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
        String url = backEndUrl + "/getById?id" + id;
        return restTemplate.exchange(url, HttpMethod.GET, createRequest(), String.class).getBody();
    }

    public String addPurchase(String bulstat, double totalPrice, List<String> barcode, List<Integer> quantity, String orderType) {
        ObjectNode jsonBody = getJsonNode(bulstat, totalPrice, barcode, quantity, orderType);
        return restTemplate.exchange(backEndUrl, HttpMethod.POST, createRequest(jsonBody.toString()), String.class).getBody();
    }

    private ObjectNode getJsonNode(String bulstat, double totalPrice, List<String> barcode, List<Integer> quantity, String orderType) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("bulstat", bulstat);

        ArrayNode barcodeAndQuantityList = objectMapper.createArrayNode();
        for (int i = 0; i < barcode.size(); i++) {
            ObjectNode barcodeAndQuantity = objectMapper.createObjectNode();
            barcodeAndQuantity.put("barcode", barcode.get(i));
            barcodeAndQuantity.put("quantity", quantity.get(i));
            barcodeAndQuantityList.add(barcodeAndQuantity);
        }

        jsonNode.set("barcodeAndQuantityList", barcodeAndQuantityList);

        jsonNode.put("totalPrice", totalPrice);
        jsonNode.put("orderType", orderType);

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
