package WebScrapingGMaps.WebScrapingGMaps.controller;

import WebScrapingGMaps.WebScrapingGMaps.service.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/myapi")
public class MyAPIController {

    @Autowired
    private final ScrapingService scrapingService;

    public MyAPIController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @PostMapping
    public ResponseEntity<String> myApi(@RequestBody Map<String, String> body) {
        String word = body.get("word");
        scrapingService.scrapeData(word);


        return ResponseEntity.ok("Processo iniciado com a palavra: " + word);
    }
}
