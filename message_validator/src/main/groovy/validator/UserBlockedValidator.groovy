package validator

import messages.UserBlocked

class UserBlockedValidator implements Validator<UserBlocked> {

    private final Validator<UserBlocked> validator

    UserBlockedValidator() {
        this.validator = new ValidatorBuilder<UserBlocked>()
                .add('Actor user ID must be greater than 0') { it.data.actorUserId } { id -> id > 0 }
                .add('Blocked user ID must be greater than 0') { it.data.blockedUserId } { id -> id > 0 }
                .add('Actor and blocked user IDs must not be the same') { it.data } { data -> data.actorUserId != data.blockedUserId }
                .build()
    }

    @Override
    ValidationResult validate(UserBlocked message) {
        validator.validate(message) // Removed unnecessary `return`
    }
}
