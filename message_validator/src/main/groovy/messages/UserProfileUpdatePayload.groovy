package messages

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class UserProfileUpdatePayload {

    public long userId // must be greater then 0
    public String name // must not be null or empty
    public long birthDate // milliseconds since 1970, users can not be older then 100 years
    public long lastSeen // milliseconds since 1970
    public String gender // 'male', 'female'
    public String interestedIn // 'men', 'women', 'both'
}
