package com.fspoitmo.fspoitmo.Services;

import com.fspoitmo.fspoitmo.Exceptions.UserException;
import com.fspoitmo.fspoitmo.Exceptions.UserExceptionType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ParseService {



    private Document mainPage;


    public String getSNP() {
        return mainPage.select("h3").text();
    }

    public String getAvatar() {
        return "https://ifspo.ifmo.ru/" + mainPage.select("div.span3 > div > img.img-circle").attr("src");
    }
    public String getGroup() {
        Elements listElements =  mainPage.select("body > div > div > div.span9 > div > div.span3 > ul > li:last-child");
        String groupDirty = listElements.text();
        groupDirty = groupDirty.substring(0, groupDirty.indexOf(" "));
        return groupDirty;
    }

    public String getId() {
        return mainPage.select("form.nomargin > input").attr("value");
    }

    public Document getMainPage() {
        return mainPage;
    }

    public void setMainPage(String login, String password) throws UserException {
        this.mainPage = Jsoup.parse(getMainPage(login, password));
    }

    public String getMainPage(String login, String password) throws UserException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Sec-Fetch-Site", "same-origin");
        headers.set("Accept-Encoding", "gzip, deflate, br");
        headers.set("Host", "ifspo.ifmo.ru");
        headers.set("Sec-Fetch-User", "?1");
        headers.set("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.set("Sec-Fetch-Dest", "document");
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36 OPR/67.0.3575.130");
        headers.set("Accept", "*/*");
        headers.set("Connection", "keep-alive");
        headers.set("Upgrade-Insecure-Requests", "1");
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity entity = new HttpEntity(headers);


        ResponseEntity<String> ansFirst = new RestTemplate().exchange("https://ifspo.ifmo.ru/", HttpMethod.GET, entity, String.class);
        String cookie = ansFirst.getHeaders().getFirst(headers.SET_COOKIE);
        headers.set("Cookie", cookie);
        cookie = cookie.substring(0, cookie.indexOf(";"));




        MultiValueMap<String, String> userData= new LinkedMultiValueMap<>();

        userData.add("User[login]", login);
        userData.add("User[password]", password);
        entity = new HttpEntity(userData, headers);
        ResponseEntity<String> cookie2 = new RestTemplate().exchange("https://ifspo.ifmo.ru", HttpMethod.POST, entity, String.class);

        String secondCookie = cookie2.getHeaders().toString();
        secondCookie = secondCookie.substring(secondCookie.indexOf("/\", \"") + 5);
        secondCookie = secondCookie.substring(secondCookie.indexOf("/\", \"") + 5);
        secondCookie = secondCookie.substring(secondCookie.indexOf("/\", \"") + 5);
        secondCookie = secondCookie.substring(0, secondCookie.indexOf(";"));

        headers.set("Cookie", secondCookie);
        entity = new HttpEntity(userData, headers);
        ResponseEntity<String> mainPage = new RestTemplate().exchange("https://ifspo.ifmo.ru/profile", HttpMethod.GET, entity, String.class);
        if(mainPage.toString().contains("Disallowed Key Characters"))
            throw new UserException(UserExceptionType.VALIDATION_ERROR);

        System.out.println("итого:" + mainPage.getBody());
        return mainPage.getBody();
    }


}
