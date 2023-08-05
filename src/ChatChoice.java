import com.google.gson.annotations.SerializedName;

public class ChatChoice {
    @SerializedName("finish_reason")
    private final String finishReason;

    public ChatChoice(String finishReason, int index, ChatMessage message) {
        this.finishReason = finishReason;
    }
}