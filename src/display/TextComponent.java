package display;

import constants.TextAlignment;

public class TextComponent extends Component {
    private String innerText;
    private TextAlignment textAlignment = TextAlignment.Left;

    public TextAlignment getTextAlignment() {
        return textAlignment;
    }

    public TextComponent setTextAlignment(TextAlignment textAlignment) {
        this.textAlignment = textAlignment;
        return this;
    }

    public TextComponent(String initialText) {
        innerText = initialText;
    }

    public String getInnerText() {
        return innerText;
    }

    public TextComponent setInnerText(String innerText) {
        this.innerText = innerText;
        return this;
    }

    public void render(Display display) {
        int maxRowWidth = w;
        int col = 0;
        for (String slice : innerText.split("\n|\r\n?")) {
            String[] words = slice.split(" ");
            StringBuilder line = new StringBuilder();
            int currentRowWidth = 0;
            int i = 0;
            while (i < words.length) {
                // skip ansi special characters
                int wordLength = words[i].replaceAll("\\[0-9]+\\[[0-9;]*[a-zA-Z]", "").length();
                if (currentRowWidth + wordLength + 1 > maxRowWidth) {
                    String finalLine = line.toString();
                    int final_x = x;
                    switch (textAlignment) {
                        case Left:
                            final_x = x;
                            break;
                        case Center:
                            final_x = x + (w - finalLine.length()) / 2;
                            break;
                        case Right:
                            final_x = x + w - finalLine.length();
                            break;
                    }
                    display.moveCursor(final_x, y + col++);
                    if (col > h + 1)
                        return; // todo add an indicator for this
                        // potentially add scrolling 
                    display.print(finalLine);
                    line = new StringBuilder();
                    currentRowWidth = 0;
                }
                if (line.length() > 0) {
                    line.append(" ");
                    currentRowWidth++;
                }
                line.append(words[i]);
                currentRowWidth += wordLength;
                i++;
            }
            if (line.length() > 0) {
                String finalLine = line.toString();
                int final_x = x;
                switch (textAlignment) {
                    case Left:
                        final_x = x;
                        break;
                    case Center:
                        final_x = x + (w - finalLine.length()) / 2;
                        break;
                    case Right:
                        final_x = x + w - finalLine.length();
                        break;
                }
                display.moveCursor(final_x, y + col++);
                if (col > h + 1)
                    return; // todo add an indicator for this
                    // potentially add scrolling 
                display.print(finalLine);
            }
            if (slice.length() == 0)
                col++;
        }
    }
}
