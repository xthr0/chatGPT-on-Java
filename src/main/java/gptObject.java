import com.fasterxml.jackson.annotation.JsonProperty;

public class gptObject {

    private final String text;
    private final String index;
    private final String logprobs;
    private final String finish_reason;

public gptObject(
@JsonProperty("text") String text,
@JsonProperty("index") String index,
@JsonProperty("logprobs") String logprobs,
@JsonProperty("finish_reason") String finish_reason)
{
    this.text = text;
    this.index = index;
    this.logprobs = logprobs;
    this.finish_reason = finish_reason;

}
    public String getText() {
        return text;
    }

    public String getIndex() {
        return index;
    }

    public String getLogprobs() {
        return logprobs;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    @Override
    public String toString() {
        return "OpenAI_JSON{" +
                "text='" + text + '\'' +
                ", index='" + index + '\'' +
                ", logprobs='" + logprobs + '\'' +
                ", finish_reason='" + finish_reason + '\'' +
                '}';
    }
}
