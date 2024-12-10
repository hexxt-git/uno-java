all: compile run

# The compile command finds all Java source files in the src directory
# and compiles them into the bin directory.
compile:
	find src -name "*.java" | xargs javac -d bin

run:
	java -cp bin UnoGame

clean:
	rm -rf bin/*.class