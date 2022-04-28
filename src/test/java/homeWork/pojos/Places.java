package homeWork.pojos;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString

@JsonIgnoreProperties(ignoreUnknown = true)
public class Places {



    @JsonProperty("place name")
    private String placeName;

    @JsonProperty("state")
    private String state;

    @JsonProperty("latitude")
    private String latitude;

    @JsonProperty("state abbreviation")
    private String stateAbbreviation;

    @JsonProperty("post code")
    private String postCode;
}
