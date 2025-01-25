### Project Structure

- `makefile` – Automates compile and run steps for a Linux environment.
- `readme.md` – Overview and instructions.
- `src/Main.java` – Entry point of the application.
- `src/cards/` – Holds card types and logic.
- `src/constants/` – Enumerations for colors, actions, alignments, etc.
- `src/display/` – UI logic (components, display, input).
- `src/game/` – Core game classes (deck, card stack, Uno game flow).
- `src/player/` – Abstract player class and implementations.
- `src/leaderboard/` – Handles player score tracking and leaderboard management.

### Key Classes & Methods

#### **src/cards/Card.java**

- Base class with color tracking and a method `isValidPlay` for checking play legality.
- Demonstrates encapsulation of color data.

#### **src/cards/ActionCard.java**

- Extends `Card`.
- Adds an extra field `action` of type `Action`.

#### **src/cards/NumberCard.java**

- Extends `Card`.
- Stores a numeric value and overrides `isValidPlay`.

#### **src/player/Player.java**

- Abstract class with final methods (e.g., `draw`) plus abstract `play` and `chooseColor`.
- Enforces a common contract for concrete classes.

#### **src/player/HumanPlayer.java** & **src/player/BotPlayer.java**

- Extend `Player`.
- Provide different strategies for `play` and `chooseColor`.

#### **src/game/Deck.java**, **src/game/CardStack.java**, **src/game/Uno.java**

- `Deck` is a specialized `CardStack` that initializes Uno cards.
- `Uno` orchestrates gameplay, turn logic, and card draws.

#### **src/display/Component.java** (abstract)

- Declares abstract `render` and fields for positioning.
- Contains a template for behavior that child components must fulfill.

#### **src/display/ParentComponent.java**, **src/display/TextComponent.java**, etc.

- Demonstrate composition by embedding multiple `Component` instances.
- Use alignment, borders, text rendering, and nesting of child components.

#### **src/leaderboard/Leaderboard.java** (Interface)

- Declares methods to manage scores and display the leaderboard:
  - `int calculateScore()` – Placeholder for score calculation logic.
  - `void createScore(int score, String name)` – Adds a new score entry.
  - `void updateScore(int score, String name)` – Updates the score of an existing player.
  - `void printLeaderboard()` – Outputs the leaderboard to a file.

#### **src/leaderboard/LeaderboardImpl.java**

- Implements `Leaderboard`.
- Maintains a list of scores with the following logic:
  - **Private Inner Class ‘Score’**:
    - Fields: `name` and `score`.
    - Represents individual player scores.
  - **Methods**:
    - `printLeaderboard()` – Sorts scores in descending order, generates a unique game ID, and writes scores to a file.
    - `createScore(int score, String name)` – Adds a new score to the list.
    - `updateScore(int score, String name)` – Updates an existing player's score.
    - `calculateScore()` – Currently unimplemented, intended for future custom scoring logic.

### Work Distribution

#### Salah Zeghdani

- Focus on `src/display/` features:
  - Implement UI components such as `TextComponent`, `ParentComponent`, and their integration with the game logic.
  - Ensure smooth rendering and user interaction for the terminal user interface (TUI).

#### Mkhlouf Meddad

- Document the basic workflow of the game (e.g., starting, playing turns, ending).
- create the cards methodes 



#### Ayman Charfaoui

- Implement core game mechanics:
  - Focus on `src/game/Uno.java` to manage game flow.
  - Integrate player actions and deck management.
  - Ensure smooth interaction between `Deck`, `CardStack`, and `Player`.

#### Wail Bentafat

- Develop and manage the `src/leaderboard/` package:
  - Complete the `calculateScore()` method in `LeaderboardImpl.java`.
  - Implement methods for shuffling and initializing the deck in Deck.java.

####

### OOP Patterns & Examples

#### **Inheritance**

- `Card` is the base for `ActionCard` and `NumberCard`.
- `Player` is extended by `HumanPlayer` and `BotPlayer`.

#### **Polymorphism**

- `play` calls differ in `HumanPlayer` vs. `BotPlayer`.
- `isValidPlay` is overridden in `NumberCard`.

#### **Encapsulation**

- Fields like `Card.color` are private with getters and setters controlling changes.
- `Player` manages its own `hand` state.

#### **Abstraction**

- `Component` and `Player` are abstract, forcing subclasses to define methods like `render` or `play`.
- `Leaderboard` interface abstracts the contract for score management.

#### **Composition**

- `ParentComponent` maintains a collection of `Component` children, showcasing a “has-a” relationship.
- `Uno` composes a `Deck` and a `CardStack` to manage game flow.
- `LeaderboardImpl` composes an `ArrayList` of `Score` objects for managing scores.

### Summary

The codebase leverages classic OOP techniques:

- **Inheritance** for specialized card and player types.
- **Polymorphism** for varied behaviors like play strategies and score management.
- **Encapsulation** for protecting internal states of objects like `Card` and `Player`.
- **Abstraction** to define contracts for extensible components like `Player` and `Leaderboard`.
- **Composition** to build complex behaviors by assembling smaller objects, such as UI components, game logic, and score management.

