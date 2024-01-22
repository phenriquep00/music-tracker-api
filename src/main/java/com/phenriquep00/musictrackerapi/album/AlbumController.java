package com.phenriquep00.musictrackerapi.album;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @GetMapping("/test")
    public String index()
    {
        return "Hello World";
    }
    
}
