package messages

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class UserProfileUpdate extends Envelope {

    final UserProfileUpdatePayload data 

    UserProfileUpdate(UserProfileUpdatePayload data = new UserProfileUpdatePayload()) {
        this.data = data
    }
}
