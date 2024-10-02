[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/h3RlyAsJ)
# A3-DiceGamesApp

## About Us

This app was built by:

- **Aditya Ashok Tailor**  
  Student ID: 2022A7PS0389G  
  Email: [f20220389@goa.bits-pilani.ac.in](mailto:f20220389@goa.bits-pilani.ac.in)

- **S Mohammed Ashraf**  
  Student ID: 2022A7PS0645G  
  Email: [f20220645@goa.bits-pilani.ac.in](mailto:f20220645@goa.bits-pilani.ac.in)



## Brief Description

The **Dice Games App** builds on our previous app, adding a high-stakes gaming experience. Players can still roll the dice to earn coins on rolling a 6. The new feature allows users to participate in a **high-risk, high-reward game** where they can bet their earned coins on rolling matching dice.

The game includes three modes:

- **2 Alike**: Bet on two matching dice to win or lose **2x** the wagered amount.
- **3 Alike**: Bet on three matching dice to win or lose **3x** the wagered amount.
- **4 Alike**: Bet on four matching dice to win or lose **4x** the wagered amount.

Each mode offers increasing rewards and risks, creating a more thrilling and engaging gameplay experience. Players can multiply their earnings or face greater losses based on the dice outcome.

  
## Overview of Task Completion

1. **GameViewModel Implementation**:
    - Implemented the core game logic in the `GameViewModel` to ensure all provided JUnit test cases pass.
    - This involved fixing logic for handling the wallet and dice roll behavior.

2. **WalletFragment and UI Implementation**:
    - Developed a fully functioning `WalletFragment` to  display balance and dice rolls.
    - Created both portrait and landscape UIs to ensure the app works seamlessly across different orientations.
    
3. **GamesFragment and UI**:
    - Designed and implemented the `GamesFragment` to provide the high-risk, high-reward game modes.
    - Created user-friendly UI elements that allow players to place wagers and see the dice rolls. This was integrated with the `GameViewModel` to ensure data flow between UI and logic.

4. **GamePlay and Testing**:
    - Added and passed new **JUnit tests** for both `GamePlay` and `MockedGamePlay`, ensuring the game mechanics work as intended. 
    - Thoroughly tested the wager functionality, coin balance and game results updates.
  
5. **NavGraph and InfoFragment**:
    - Added a new `InfoFragment` to display game rules and connected it to the navigation flow via `NavGraph`.
    - Implemented the "Info" button to allow seamless navigation between fragments.

6. **Persisting Coin Balance using Shared Preferences**:
    - Created a new class called `DiceGamesPrefs` to persist the balance across app kills and device reboots using Shared Preferences.
    - Thoroughly tested this feature using Instrumented tests by recreating the activity during the test.
      ```{
         @Test
        public void testBalancePersistenceAfterRestart() {
            onView(withId(R.id.btn_die)).perform(click());
            String initialBalance = getTextFromView(R.id.txt_balance);
            activityRule.getScenario().recreate(); 
            onView(withId(R.id.txt_balance)).check(matches(withText(initialBalance)));
        }
8. **Bug Fixes and Improvements**:
    - Fixed a toast message to correctly announce the game results.
    - Persisted dice values and ensured consistent behavior when switching between different fragments and orientations, and loading an initial die value for all dice.
  
9. **Accessibility and Espresso Tests**:
    - Added **Espresso tests** to verify accessibility compliance and interaction with UI elements, ensuring the app is accessible to all users.
    - Improved accessibility by running **Accessibility Scanner** and addressing the issues highlighted in the feedback (addressed below).

---

## Testing

We followed a **Test-Driven Development (TDD)** approach throughout the project. Initially, failing test cases were written to define the desired behavior of the app. Afterward, we developed code to meet these requirements, ensuring a smooth, bug-free experience.

### Key Testing Highlights:

1. **JUnit Tests**:
    - Added comprehensive **JUnit tests** for both wallet and game mechanics.
    - The tests focused on dice rolling logic, coin balance updates, and ensuring that wagers and payouts were handled correctly.
    - Mocked scenarios were used to ensure edge cases like invalid wagers and unexpected game states were handled properly.

2. **Instrumented Tests**:
    - Created instrumented tests to validate the UI and interactions between fragments, ensuring proper navigation and data flow.
    - Used **Mockito** to simulate a mock die and control the behavior during gameplay, allowing us to test specific scenarios.
    - Leveraged the `@Spy` annotation in our tests to partially mock objects while preserving the real object’s behavior for certain methods, making it easier to validate interactions and results.
    - Tests ensured that buttons, text fields, and other UI elements behaved correctly, and game outcomes updated as expected.

3. **Bug Fixes and Verification**:
   - After implementing new functionality, we rigorously tested for potential bugs, ensuring that everything from wallet balance persistence to UI updates worked as intended.

4. **Accessibility Testing**:
    - Integrated accessibility tests with **Espresso**, ensuring compliance with color contrast, font size, and other accessibility standards.
    - Ensured the app was navigable using **TalkBack**, enhancing the experience for visually impaired users.

5. **Stress Testing**:  
   - We performed stress testing using **Monkey**, which bombarded the app with random inputs to simulate real-world usage under high load. The app successfully passed without any crashes or critical issues.
---

## Accessibility Enhancement  
- **Accessibility Scanner Report:**
    - We tested the app on Accessiblity Scanner to identify and address potential accessibility issues, ensuring that users with disabilities can navigate and interact with the app effectively.
    - Upon using the Accessibility Scanner we received the following feedbacks for improvements on our UI in order to make it even more accessible to differently abled users:
![Screenshot From 2024-10-02 21-02-38](https://github.com/user-attachments/assets/7568afd7-de88-46a9-b7b3-b5f7e79c1843)
![image](https://github.com/user-attachments/assets/1a95c525-6502-4400-a0a8-c74d39ac2e40)


    - These are some of the images containing the analysis of the UI as done by the Accessibility Scanner. Similarly we had a total of **12 screens** analysed.

    - Accordingly, we changed the text from **dip to scaled pixels (sp)**, changed the size of the text and adjusted the contrast ratios on our UI to suit the suggestions provided by the Accessibility Scanner.

    
 

## Time Taken

The project took approximately **15** hours to code, test, and refine.

## Pair Programming

We utilized pair programming to collaborate efficiently. One member focused on writing failing tests and edge cases, while the other implemented the app logic to pass the same. We jointly reviewed the code and ensured that both coding and documentation were done collaboratively.

## Difficulty Level

We would rate the difficulty of this assignment as **7.5** out of 10. Some of the challenges faced included writing the Instrumented Testcases, persisting the balance using Shared Preferences and making sure that UI had a good accessibility score.

## Bibliography

1. **ChatGPT and Claude:**  
   Used ChatGPT for understanding test-driven development and accessibility testing best practices.
   
2. **Android Documentation:**  
   Referred to [Android Documentation](https://developer.android.com/docs) for implementing SharedPreferences and Fragments.
   
3. **Swaroop Sir's course site to use Shared Preferences:**
   We referred to [Swaroop Sir's website](https://swaroopjoshi.in/courses/mobile-app-dev/09-persistent-data/) in order to understand how to use Shared Preferences to persist data across process kills and device reboots.
