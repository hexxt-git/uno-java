## repport.md

Below is an overview of the project’s structure, classes, methods, and how various object-oriented programming patterns are applied.

---

### Project Structure

-   [`makefile`](makefile) – Automates compile and run steps for a linux environment.
-   [`readme.md`](readme.md) – Overview and instructions.
-   [`src/Main.java`](src/Main.java) – Entry point of the application.
-   src/cards/ – Holds card types and logic.
-   src/constants/ – Enumerations for colors, actions, alignments, etc.
-   src/display/ – UI logic (components, display, input).
-   src/game/ – Core game classes (deck, card stack, Uno game flow).
-   src/player/ – Abstract player class and implementations.

---

### Key Classes & Methods

1. **src/cards/Card.java**

    - Base class with color tracking and a method [`isValidPlay`](src/cards/Card.java) for checking play legality.
    - Demonstrates encapsulation of color data.

2. **src/cards/ActionCard.java**

    - Extends [`Card`](src/cards/Card.java).
    - Adds an extra field [`action`](src/cards/ActionCard.java) of type [`Action`](src/constants/Action.java).

3. **src/cards/NumberCard.java**

    - Extends [`Card`](src/cards/Card.java).
    - Stores a numeric value and overrides [`isValidPlay`](src/cards/NumberCard.java).


4. **src/player/Player.java**

    - Abstract class with final methods (e.g., [`draw`](src/player/Player.java)) plus abstract [`play`](src/player/Player.java) and [`chooseColor`](src/player/Player.java).
    - Enforces a common contract for concrete classes.

5. **src/player/HumanPlayer.java** & **src/player/BotPlayer.java**

    - Extend [`Player`](src/player/Player.java).
    - Provide different strategies for [`play`](src/player/HumanPlayer.java) and [`play`](src/player/BotPlayer.java).

6. **src/game/Deck.java**, **src/game/CardStack.java**, **src/game/Uno.java**

    - [`Deck`](src/game/Deck.java) is a specialized [`CardStack`](src/game/CardStack.java) that initializes Uno cards.
    - [`Uno`](src/game/Uno.java) orchestrates gameplay, turn logic, and card draws.

7. **src/display/Component.java** (abstract)

    - Declares abstract [`render`](src/display/Component.java) and fields for positioning.
    - Constains template for behavior that child components must fulfil.

8. **src/display/ParentComponent.java**, **src/display/TextComponent.java**, etc.
    - Demonstrate composition by embedding multiple [`Component`](src/display/Component.java) instances.
    - Use alignment, borders, text rendering, and nesting of child components.

---

### OOP Patterns & Examples

1. **Inheritance**

    - [`Card`](src/cards/Card.java) is the base for [`ActionCard`](src/cards/ActionCard.java) and [`NumberCard`](src/cards/NumberCard.java).
    - [`Player`](src/player/Player.java) is extended by [`HumanPlayer`](src/player/HumanPlayer.java) and [`BotPlayer`](src/player/BotPlayer.java).

2. **Polymorphism**

    - [`play`](src/player/Player.java) calls differ in [`HumanPlayer`](src/player/HumanPlayer.java) vs. [`BotPlayer`](src/player/BotPlayer.java).
    - [`isValidPlay`](src/cards/Card.java) is overridden in [`NumberCard`](src/cards/NumberCard.java).

3. **Encapsulation**

    - Fields like [`Card.color`](src/cards/Card.java) are kept private with getters and setters controlling changes.
    - [`Player`](src/player/Player.java) manages its own [`hand`](src/game/Uno.java) state.

4. **Abstraction**

    - [`Component`](src/display/Component.java) and [`Player`](src/player/Player.java) are abstract, forcing subclasses to define methods like [`render`](src/display/Component.java) or [`play`](src/player/Player.java).

5. **Composition**
    - [`ParentComponent`](src/display/ParentComponent.java) maintains a collection of [`Component`](src/display/Component.java) children, showcasing a “has-a” relationship.
    - [`Uno`](src/game/Uno.java) composes a [`Deck`](src/game/Deck.java) and a [`CardStack`](src/game/CardStack.java) to manage game flow.

Overall, the codebase uses classic OOP techniques — inheritance for specialized card or player types, polymorphism for varied behaviors, and composition to assemble user interface components and game data structures.
