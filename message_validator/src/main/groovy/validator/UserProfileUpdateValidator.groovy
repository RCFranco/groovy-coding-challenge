package validator

import messages.UserProfileUpdate

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class UserProfileUpdateValidator implements Validator<UserProfileUpdate> {

    private final Validator<UserProfileUpdate> validator

    UserProfileUpdateValidator() {
        this.validator = new ValidatorBuilder<UserProfileUpdate>()
                .add('User ID must be greater than 0') { it.data.userId } { userId -> userId > 0 }
                .add('Name must not be null or empty') { it.data.name } { name -> name != null && !name.trim().empty }
                .add('Birth date must be within the last 100 years') { it.data.birthDate } { birthDate -> isValidBirthDate(birthDate) }
                .add('Last seen date must not be in the future') { it.data.lastSeen } { lastSeen -> isValidLastSeen(lastSeen) }
                .add('Gender must be \'male\' or \'female\'') { it.data.gender } { gender -> gender in ['male', 'female'] }
                .add('InterestedIn must be \'men\', \'women\', or \'both\'') { it.data.interestedIn } { interestedIn -> interestedIn in ['men', 'women', 'both'] }
                .build()
    }

    @Override
    ValidationResult validate(UserProfileUpdate message) {
        validator.validate(message) // Removed unnecessary `return`
    }

    private static boolean isValidBirthDate(long birthDate) {
        LocalDate birthLocalDate = Instant.ofEpochMilli(birthDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        LocalDate today = LocalDate.now()
        LocalDate hundredYearsAgo = today.minusYears(100)

        !birthLocalDate.isBefore(hundredYearsAgo) && !birthLocalDate.isAfter(today) // Removed unnecessary `return`
    }

    private static boolean isValidLastSeen(long lastSeen) {
        Instant lastSeenInstant = Instant.ofEpochMilli(lastSeen)
        !lastSeenInstant.isAfter(Instant.now()) // Removed unnecessary `return`
    }
}
