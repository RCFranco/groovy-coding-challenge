package messages

class UserBlocked extends Envelope {

    final UserBlockedPayload data

    UserBlocked(UserBlockedPayload blockUserPayload) {
        this.data = blockUserPayload
    }
}
