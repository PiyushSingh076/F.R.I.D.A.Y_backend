package com.ai.SpringAiDemo;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    private final ChatModel chatModel;

    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients,
                               String cuisine,
                               String dietaryInstructions){

        String template= """
                I want to create a recipe using the following ingredients: {ingredients}.
                The cuisine type i prefer is {cuisine}.
                Please consider the following dietary restrictions: {dietaryInstructions}.
                Please provide me with detailed recipe including title, list of ingredients, 
                and cooking instructions
                """;
        //this is basically adding to the ingredients and info that user gives then well just use chat bot

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String,Object> params= Map.of(
                "ingredients",ingredients,
                "cuisine",cuisine,
                "dietaryInstructions",dietaryInstructions
        );

        //the place holders in template need to be replacd with the map items and since there arent
        //single values hence we are using maps sp that in place of {ingredients} all ingredients
        //mentioned in map can be inserted there and so on...

        Prompt prompt = promptTemplate.create(params);//this fits all these values into the template
        return chatModel.call(prompt).getResult().getOutput().getContent();
    }
}
