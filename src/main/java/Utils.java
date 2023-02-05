import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;

public class Utils {
    public static ObjectMapper mapper = new ObjectMapper();

    public static String result;

    public static String getExampleText(String api) throws JsonProcessingException {
        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("Write a new conspiracy theory ")
                .maxTokens(2048)
                .echo(false)
                .user("testing")
                .build();

        System.out.println("Testing!");
        return mapper.writeValueAsString(new OpenAiService(api).createCompletion(request).getChoices());
    }
    public static String getText(String api, String text) throws JsonProcessingException, InterruptedException {
        CompletionRequest request = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt(text)
                .maxTokens(2048)
                .echo(false)
                .user("testing")
                .build();

        result = mapper.writeValueAsString(new OpenAiService(api).createCompletion(request).getChoices());
        System.out.print(" - Connected! - ");
        result = result.substring(1, result.length()-1);
        gptObject gpobject = mapper.readValue(result, gptObject.class);
        return gpobject.getText();
    }
}
