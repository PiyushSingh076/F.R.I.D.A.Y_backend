package com.ai.SpringAiDemo;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GenAIController {
    private final ChatService chatService;

    private final ImageService imageService;
    private final RecipeService recipeService;

    public GenAIController(ChatService chatService, ImageModel imageModel, ImageService imageService, RecipeService recipeService) {
        this.chatService = chatService;
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("ask-ai")
    public String getResponse(@RequestParam String prompt){
        return chatService.getResponse(prompt);
    }

    @GetMapping("ask-ai-options")
    public String getResponseOptions(@RequestParam String prompt){
        return chatService.getResponseOptions(prompt);
    }

//    @GetMapping("generate-image")
//    //this is for just one image generation only
//    public void generateImages(HttpServletResponse response , @RequestParam String prompt) throws IOException {
//        //without httpservlt resoponse image url is genrated with it it directs user to image
//        ImageResponse imgresponse=imageService.generateImage(prompt);
//        String imgUrl=imgresponse.getResult().getOutput().getUrl();
//        response.sendRedirect(imgUrl);
//
//    }

    @GetMapping("generate-image")
//    for more than 1 images
    public List<String> generateImages(HttpServletResponse response ,
                                       @RequestParam String prompt,
                                          @RequestParam (defaultValue="hd") String quality,
                                       @RequestParam (defaultValue="1") int n,
                                        @RequestParam (defaultValue="1024") int width,
                                        @RequestParam (defaultValue="1024") int height) throws IOException {

        //without httpservlt resoponse image url is genrated with it it directs user to image
        //by parametrizing it we can actually ask user for their preference abt what kind of image they would like
        ImageResponse imgresponse=imageService.generateImage(prompt,quality,n,width,height);

        //Streams to get urls from ImageResponse
        List<String> imageUrls=imgresponse.getResults().stream()
                .map(result-> result.getOutput().getUrl())
                .toList();

        return imageUrls;
    }

    @GetMapping("recipe")
//    for more than 1 images
    public String recipe(@RequestParam String ingredients,
                               @RequestParam(defaultValue = "any") String cuisine,
                               @RequestParam(defaultValue = "") String dietaryRestrictions){
        return recipeService.createRecipe(ingredients,cuisine,dietaryRestrictions);

    }
}
