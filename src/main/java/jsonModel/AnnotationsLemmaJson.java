package jsonModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnotationsLemmaJson {

    @SerializedName("lemma")
    @Expose
    public List<LemmaJson> lemmas = null;

}