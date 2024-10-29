package validator

import messages.UserBlocked
import messages.UserBlockedPayload
import messages.UserProfileUpdate
import messages.UserProfileUpdatePayload
import picocli.CommandLine
import java.util.concurrent.Callable

@CommandLine.Command(
        name = 'message-validator',
        description = 'Validates messages like UserProfileUpdate and UserBlocked',
        mixinStandardHelpOptions = true
)
class ValidatorCliApp implements Callable<Integer> {

    @CommandLine.Option(names = ['-t', '--type'], description = 'Type of message (profile or blocked)', required = true)
    String messageType

    @CommandLine.Option(names = ['-p', '--payload'], description = 'Payload (format: actorId,blockedId or userId,name,lastSeen)')
    String payload

    @Override
    Integer call() {
        switch (messageType) {
            case 'profile':
                validateUserProfileUpdate()
                break
            case 'blocked':
                validateUserBlocked()
                break
            default:
                println 'Invalid message type!'
                return 1
        }
        return 0
    }

    private void validateUserBlocked() {
        def (actorId, blockedId) = payload.split(',')*.toLong()
        def message = new UserBlocked(new UserBlockedPayload(actorId, blockedId))
        def validator = new UserBlockedValidator()
        def result = validator.validate(message)

        if (result.success()) {
            println 'UserBlocked message is valid.'
        } else {
            println "Validation failed: ${result.errors.join(', ')}"
        }
    }

    private void validateUserProfileUpdate() {
        def (userId, name, lastSeen) = payload.split(',')
        def message = new UserProfileUpdate(
                new UserProfileUpdatePayload(userId.toLong(), name, 0L, lastSeen.toLong(), 'male', 'women')
        )
        def validator = new UserProfileUpdateValidator()
        def result = validator.validate(message)

        if (result.success()) {
            println 'UserProfileUpdate message is valid.'
        } else {
            println "Validation failed: ${result.errors.join(', ')}"
        }
    }

    static void main(String[] args) {
        int exitCode = new CommandLine(new ValidatorCliApp()).execute(args)
        System.exit(exitCode)
    }
}
