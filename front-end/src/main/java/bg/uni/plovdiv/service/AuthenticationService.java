package bg.uni.plovdiv.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class AuthenticationService {

    private final String backEndUrl;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public AuthenticationService(@Value("${wholesale.backend.supplier.url}") String backEndUrl, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.backEndUrl = backEndUrl + "/auth";
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getUsers(String token) {
        String url = backEndUrl + "/getUsers";
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, createRequest(token), String.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            return "You could get all users!";
        }
    }

    public String authenticate(String username, String password) {
        ObjectNode jsonBody = getJsonNode(username, password);
        String url = backEndUrl + "/authenticate";
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, createJsonRequest(jsonBody.toString()), String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                return jsonNode.get("token").asText();
            } else {
                return null;
            }
        } catch (HttpClientErrorException ex) {
            log.error("HTTP Error: " + ex.getStatusCode() + " - " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String register(String username, String password, String firstName, String lastName, String userRole, String token) {
        ObjectNode jsonBody = getJsonNode(username, password, firstName, lastName, userRole);
        String url = backEndUrl + "/register";
        try {
            restTemplate.exchange(url, HttpMethod.POST, createJsonRequest(jsonBody.toString(), token), String.class);
            return "Successfully registered a user with username:" + username;

        } catch (HttpClientErrorException ex) {
            log.error("HTTP Error: " + ex.getStatusCode() + " - " + ex.getMessage());
            return "You could not register a new user";
        }
    }

    public String remove(String username, String token) {
        String url = backEndUrl + "/remove?username=" + username;
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, createRequest(token), String.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            return "You could not remove users!";
        }
    }

    private ObjectNode getJsonNode(String username, String password, String firstName, String lastName, String userRole) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("username", username);
        jsonNode.put("password", password);
        jsonNode.put("firstName", firstName);
        jsonNode.put("lastName", lastName);
        jsonNode.put("userRole", userRole);
        return jsonNode;
    }

    private ObjectNode getJsonNode(String username, String password) {
        ObjectNode jsonBody = objectMapper.createObjectNode();
        jsonBody.put("username", username);
        jsonBody.put("password", password);
        return jsonBody;
    }

    private HttpEntity<String> createRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<String> createJsonRequest(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(json, headers);
    }

    private HttpEntity<String> createJsonRequest(String json, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        return new HttpEntity<>(json, headers);
    }
}
