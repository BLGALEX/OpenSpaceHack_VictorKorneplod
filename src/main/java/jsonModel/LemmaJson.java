package jsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LemmaJson {

    @SerializedName("start")
    @Expose
    public Integer start;
    @SerializedName("end")
    @Expose
    public Integer end;
    @SerializedName("value")
    @Expose
    public String value;
}