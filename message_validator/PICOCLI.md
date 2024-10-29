# Message Validator CLI

This CLI validates messages using the `UserProfileUpdate` and `UserBlocked` validators.

## Build and Run

1. Build the project:

```bash
./gradlew :message_validator:build
```

2. Validate a valid User Profile Update:

```bash
./gradlew :message_validator:run --args='-t profile -p "1,John Doe,631152000000,1698278400000,male,women"'
```

3. Validate a valid Blocked User Message

```bash
./gradlew :message_validator:run --args='-t blocked -p "1,2"'
```

## Invalid Cases User Profile

1. Invalid User ID
```bash
./gradlew :message_validator:run --args='-t profile -p "0,John Doe,631152000000,1698278400000,male,women"'
```

2. Empty Name
```bash
./gradlew :message_validator:run --args='-t profile -p "1,,631152000000,1698278400000,male,women"'
```

3. Birth Date Older Than 100 Years
```bash
./gradlew :message_validator:run --args='-t profile -p "1,John Doe,315532800000,1698278400000,male,women"'
```

4. Future Last Seen Date
```bash
./gradlew :message_validator:run --args='-t profile -p "1,John Doe,631152000000,1893456000000,male,women"'
```

5. Invalid Gender Field
```bash
./gradlew :message_validator:run --args='-t profile -p "1,John Doe,631152000000,1698278400000,unknown,women"'
```

6. Invalid InterestedIn Field
```bash
./gradlew :message_validator:run --args='-t profile -p "1,John Doe,631152000000,1698278400000,male,invalid"'
```
# Invalid Cases User Blocked

1. Actor User ID is Zero
```bash
./gradlew :message_validator:run --args='-t blocked -p "0,2"'
```

2. Blocked User ID is Zero
```bash
./gradlew :message_validator:run --args='-t blocked -p "1,0"'
```