package homeWork.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;


@Setter
@Getter
@ToString

@JsonIgnoreProperties(ignoreUnknown = true)
public class Code {

    @JsonProperty("post code")
    private String postCode;


    private String country;

    @JsonProperty("country abbreviation")
    private String countryAbbreviation;

    @JsonProperty("places")
    private List <Places> placess;

    @JsonProperty("place name")
    private String placeName;
}
