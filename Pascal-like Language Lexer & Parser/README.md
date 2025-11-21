# Pascal-like Language Lexer & Parser

### The project can:
- Recognize keywords, identifiers, numbers, strings, operators, and delimiters.
- Handle variable and constant declarations.
- Parse statements such as assignments, `write`, `writeln`, `readln`, and `for` loops.
- Detect invalid characters and provide detailed error reporting.
- Maintain a token table with lexeme, category, and value.

The parser uses **Bison** for grammar rules and **Flex** for lexical analysis, storing tokens in structured arrays for easy processing and verification.

### Features
- Lexer produces a **token table** with categories and values.
- Parser supports **semantic actions** to track tokens and errors.
- Detects **unknown characters** and prevents parsing crashes.
- Demonstrates practical use of **Flex/Bison integration** in C.
- Designed for educational purposes and to illustrate compiler construction principles.

## Usage
```bash
# Compile
bison -d parser.y
flex lexer.l
gcc parser.tab.c lex.yy.c -o parser -lfl

# Run
./parser input.pas output.txt
