package constants;

public enum BorderStyle {
        None("", "", "", "", "", "", "", "", "", ""),
        Single(
                        "┌", "┐", "─", "─", "│", "│", "└", "┘", "┤ ", " ├"),
        Double(
                        "╔", "╗", "═", "═", "║", "║", "╚", "╝", "╣ ", " ╠"),
        Rounded(
                        "╭", "╮", "─", "─", "│", "│", "╰", "╯", "┤ ", " ├"),
        Bold(
                        "▛", "▜", "▀", "▄", "▌", "▌", "▙", "▟", "▌ ", " ▌"),
        Dashed(
                        "+", "+", "-", "-", "|", "|", "+", "+", "+ ", " +"),
        Hashes(
                        "#", "#", "#", "#", "#", "#", "#", "#", "# ", " #"),
        Dotted(
                        "·", "·", "·", "·", ":", ":", "·", "·", "· ", " ·"),
        Thick(
                        "█", "█", "█", "█", "█", "█", "█", "█", "█ ", " █");

        public final String topLeft;
        public final String topRight;
        public final String topHorizontal;
        public final String bottomHorizontal;
        public final String leftVertical;
        public final String rightVertical;
        public final String bottomLeft;
        public final String bottomRight;
        public final String titleLeft;
        public final String titleRight;

        private BorderStyle(String topLeft, String topRight, String topHorizontal, String bottomHorizontal,
                        String leftVertical, String rightVertical, String bottomLeft, String bottomRight,
                        String titleLeft, String titleRight) {
                this.topLeft = topLeft;
                this.topRight = topRight;
                this.topHorizontal = topHorizontal;
                this.bottomHorizontal = bottomHorizontal;
                this.leftVertical = leftVertical;
                this.rightVertical = rightVertical;
                this.bottomLeft = bottomLeft;
                this.bottomRight = bottomRight;
                this.titleLeft = titleLeft;
                this.titleRight = titleRight;
        }
}
