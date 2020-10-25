package jsonModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnotationsCorrectionJson {

    @SerializedName("spelling-correction-token")
    @Expose
    public List<SpellingCorrectionTokenJson> spellingCorrectionToken = null;

}