package reloadly.ms_02.controller;

import org.springframework.http.*;
import reloadly.ms_02.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@RestController
public class AccountController {

    protected String baseUrl = "http://account-services/"; // localhost:4000/api/

    protected HttpHeaders headers = new HttpHeaders();

    @Autowired
    RestTemplate template;

    @PostConstruct()
    public void authentification() {
//        Account account = this.findByName("admin");
//        String token = account.getToken();
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NjkwODIwMjYsInN1YiI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiQURNSU4iLCJVU0VSUyJdLCJleHAiOjE2NjkxNjg0MjZ9.WeLBpMN5olM6yDwo3PGdaNj9Zlbdd-sXYntT36cSgQfoczRYqxBvpG4UKt7Lo3Nkn9CRvTAjTJT3lTmUUc1zDQ";


        headers.add("Authorization", "Bearer " + token);
    }

    @PostMapping(value = "register", produces = MediaType.APPLICATION_JSON_VALUE)
    public String register(@RequestBody Account account) {
        return template.postForObject(baseUrl + "register", account, String.class);
    }

    @PostMapping(value = "confirm", produces = MediaType.APPLICATION_JSON_VALUE)
    public String confirm(@RequestParam("user") String user, @RequestParam("pwd") String pwd, @RequestParam("enabled") String enabled) {
        String url = baseUrl + "/confirm?user="+user+"&pwd=" + pwd + "&enabled=" + enabled;
        return template.postForObject(url, null, String.class);
    }

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public String login(@RequestParam("user") String user, @RequestParam("pwd") String pwd) {
        return template.postForObject(baseUrl + "/login?user="+user+"&pwd=" + pwd, null, String.class);
    }

    @GetMapping(value = "accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Account> all() {
        Account[] accounts = template.exchange(baseUrl + "accounts", HttpMethod.GET, new HttpEntity<>(headers), Account[].class).getBody();

        return Arrays.asList(accounts);
    }

    @GetMapping(value = "account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account find(@PathVariable("id") int id) {
        Account account = template.exchange(baseUrl + "account/" + id, HttpMethod.GET, new HttpEntity<>(headers), Account.class).getBody();

        return account;
    }

    @GetMapping(value = "account/findByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account findByName(@PathVariable("name") String name) {
        Account account = template.exchange(baseUrl + "account/findByName/" + name, HttpMethod.GET, new HttpEntity<>(headers), Account.class).getBody();

        return account;
    }

    @PostMapping(value = "account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestBody Account account) {
        boolean response = template.exchange(baseUrl + "account", HttpMethod.POST, new HttpEntity<Account>(account, headers), Boolean.class).getBody();
        return response;
    }

    @PutMapping(value = "account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean update(@RequestBody Account account) {
        boolean response = template.exchange(baseUrl + "account", HttpMethod.PUT, new HttpEntity<>(account, headers), Boolean.class).getBody();
        return response;
    }

    @DeleteMapping(value = "account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean delete(@PathVariable("id") int id) {
        boolean response = template.exchange(baseUrl + "account/" + id, HttpMethod.DELETE, new HttpEntity<>(headers), Boolean.class).getBody();
        return response;
    }

    @DeleteMapping(value = "account/deleteByName/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean deleteByName(@PathVariable("name") String name) {
        boolean response = template.exchange(baseUrl + "account/deleteByName/" + name, HttpMethod.DELETE, new HttpEntity<>(headers), Boolean.class).getBody();
        return response;
    }

}
