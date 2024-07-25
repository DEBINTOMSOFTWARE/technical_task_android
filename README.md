# UserManager App
## Description
UserManager is an Android application developed as part of the Sliide Android developer challenge. It's a simple yet powerful tool for managing users, leveraging the public API from https://gorest.co.in/. The app demonstrates modern Android development practices and showcases a range of technical skills.

## Project Details
Architecture: MVI (Model-View-Intent) following clean code architecture principles
Minimum SDK: 24
Language: Kotlin
UI Framework: Jetpack Compose
Dependency Injection: Hilt
Asynchronous Programming: Kotlin Coroutines and Flow
Testing: Unit tests for layers (Repository, Use Cases)

## Features

### User List Display:

Shows users from the last page of the API endpoint,
Each entry includes name, email address, and relative creation time,
Implements loading and error states,

### Add New User:

'+' button triggers a pop-up dialog for user creation,
Captures name and email,
Adds new user to the list upon successful creation (201 response),

### Remove Existing User:

Long press on a user item prompts a confirmation dialog,
Removes user from the list upon successful deletion (204 response),

## Additional Features:

Certificate pinning for enhanced security,
ProGuard rules implemented for app optimization,
Caching mechanism for improved performance,
Accessibility features incorporated,
Support for configuration changes (e.g., device rotation),

## App Workflow
App launches and fetches the user list,
Users can view, add, or remove users,
All actions are reflected in real-time in the UI,
Error handling and loading states provide a smooth user experience,

## Technical Highlights

Clean Architecture implementation,
MVI architecture,
Extensive use of Kotlin features (Coroutines, Flow),
Custom components (e.g., Custom Dialog and Text),
Optimized code for performance,
Comprehensive unit testing, including TDD approach for repository,

## Known Issues
UI inconsistency with Alert Dialog during configuration changes,
Theming requires improvement for better visual coherence,

## Future Enhancements

Implement accessibility features for API responses,
Add UI integration testing,
Develop offline storage capabilities,
Refine and expand theming across the app,
Implement pagination for user list,

## Assumptions

Internet connectivity is required for app functionality



