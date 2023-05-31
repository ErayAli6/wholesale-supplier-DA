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

@Service
public class CompanyService {

    private final String backEndUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public CompanyService(@Value("${wholesale.backend.supplier.url}") String backEndUrl, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.backEndUrl = backEndUrl + "/company";
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getCompanies(String token) {
        return restTemplate.exchange("http://localhost:8080/api/company", HttpMethod.GET, createRequest(token), String.class).getBody();
    }

    public String getCompanyByBulstat(String bulstat, String token) {
        String url = backEndUrl + "/getByBulstat?bulstat=" + bulstat;
        return restTemplate.exchange(url, HttpMethod.GET, createRequest(token), String.class).getBody();
    }

    public String removeCompany(String bulstat, String token) {
        String url = backEndUrl + "/remove?bulstat=" + bulstat;
        String responseBody = restTemplate.exchange(url, HttpMethod.DELETE, createRequest(token), String.class).getBody();
        if (responseBody != null && responseBody.equals("true")) {
            return "Company removed successfully.";
        } else {
            return "Failed to remove company.";
        }
    }

    public String addCompany(String bulstat, String name, String country, String state, String city, String street, String number, String zipCode, String vatNumber, String phoneNumber, String email, String token) {
        ObjectNode jsonBody = getJsonNode(bulstat, name, country, state, city, street, number, zipCode, vatNumber, phoneNumber, email);
        String responseBody = restTemplate.exchange(backEndUrl, HttpMethod.POST, createRequest(jsonBody.toString(), token), String.class).getBody();
        if (responseBody != null && responseBody.equals("true")) {
            return "Company added successfully.";
        } else {
            return "Failed to add company.";
        }
    }

    public String editCompany(String bulstat, String name, String country, String state, String city, String street, String number, String zipCode, String vatNumber, String phoneNumber, String email, String token) {
        ObjectNode jsonBody = getJsonNode(bulstat, name, country, state, city, street, number, zipCode, vatNumber, phoneNumber, email);
        String responseBody = restTemplate.exchange(backEndUrl, HttpMethod.PUT, createRequest(jsonBody.toString(), token), String.class).getBody();
        if (responseBody != null && responseBody.equals("true")) {
            return "Company edited successfully.";
        } else {
            return "Failed to edit company.";
        }
    }

    private ObjectNode getJsonNode(String bulstat, String name, String country, String state, String city, String street, String number, String zipCode, String vatNumber, String phoneNumber, String email) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("bulstat", bulstat);
        jsonNode.put("name", name);

        ObjectNode addressNode = objectMapper.createObjectNode();
        addressNode.put("country", country);
        addressNode.put("state", state);
        addressNode.put("city", city);
        addressNode.put("street", street);
        addressNode.put("number", number);
        addressNode.put("zipCode", zipCode);

        jsonNode.set("address", addressNode);
        jsonNode.put("vatNumber", vatNumber);
        jsonNode.put("phoneNumber", phoneNumber);
        jsonNode.put("email", email);
        return jsonNode;
    }

    private HttpEntity<String> createRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<String> createRequest(String json, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(json, headers);
    }
}
