package messages

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class UserBlockedPayload {

    final long actorUserId
    final long blockedUserId

    UserBlockedPayload(long actorUserId, long blockedUserId) {
        this.actorUserId = actorUserId
        this.blockedUserId = blockedUserId
    }
}
