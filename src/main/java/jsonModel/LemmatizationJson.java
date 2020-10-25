package jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LemmatizationJson {
    @SerializedName("text")
    @Expose
    public String text;
    @SerializedName("annotations")
    @Expose
    public AnnotationsLemmaJson lemma;
}