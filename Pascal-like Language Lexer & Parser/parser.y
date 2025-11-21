%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

FILE *yyin, *yyout;
void print_token_table_header();
void yyerror(const char *s);
extern int yylex();
extern int yylineno;
extern char* yytext;
extern void display_error_messages(); 

typedef struct {
    char lexeme[100];
    char category[50];
    int value;
} Token;

Token token_list[1000];
int current_token_index = 0;

void add_token(const char *lexeme, const char *category, int value) {
    strcpy(token_list[current_token_index].lexeme, lexeme);
    strcpy(token_list[current_token_index].category, category);
    token_list[current_token_index].value = value;
    current_token_index++;
}

%}

%union {
    char* id;
    int num;
}

%token PROGRAM BEGIN_TOKEN END_TOKEN KEYWORD STRING_LITERAL
%token <num> NUMBER
%token <id> IDENTIFIER STRING
%token GREATER LESS GEQ LEQ NEQ PLUS MINUS MUL DIV ASSIGN SEMICOLON COLON COMMA
%token READLN WRITE WRITELN VAR INTEGER LPAREN RPAREN EQUAL DOT WRITEIN INVALID
%token USES CONST FOR TO DO
%error-verbose

%%

program:
    PROGRAM IDENTIFIER SEMICOLON uses_section const_section declaration_list 
    BEGIN_TOKEN statement_list END_TOKEN DOT
    | error DOT { yyerrok; }
    ;

uses_section:
    /* empty */
    | USES uses_list SEMICOLON {
        add_token("USES", "Keyword", 0);
    }
    ;

uses_list:
    IDENTIFIER {
        add_token($1, "Library", 0);
    }
    | uses_list COMMA IDENTIFIER {
        add_token($3, "Library", 0);
    }
    ;

const_section:
    /* empty */
    | CONST const_declarations {
        add_token("CONST", "Keyword", 0);
    }
    ;

const_declarations:
    const_declaration
    | const_declarations const_declaration
    ;

const_declaration:
    IDENTIFIER EQUAL NUMBER SEMICOLON {
        add_token($1, "Constant", $3);
    }
    ;

declaration_list:
    VAR var_declarations {
        add_token("VAR", "Keyword", 0);
    }
    ;

var_declarations:
    var_declaration
    | var_declarations var_declaration
    ;

var_declaration:
    IDENTIFIER COLON type SEMICOLON {
        add_token($1, "Variable", 0);
    }
    ;

type:
    INTEGER {
        add_token("INTEGER", "Type", 0);
    }
    | STRING {
        add_token("STRING", "Type", 0);
    }
    ;

statement_list:
    statement
    | statement_list statement
    ;

statement:
    IDENTIFIER ASSIGN expression SEMICOLON {
        add_token($1, "Assignment", 0);
    }
    | WRITE LPAREN write_parameter RPAREN SEMICOLON {
        add_token("WRITE", "Output", 0);
    }
    | WRITELN LPAREN write_parameter RPAREN SEMICOLON {
        add_token("WRITELN", "Output", 0);
    }
    | WRITEIN LPAREN IDENTIFIER RPAREN SEMICOLON {
        add_token($3, "Identifier", 0);
    }
    | READLN LPAREN IDENTIFIER RPAREN SEMICOLON {
        add_token("READLN", "Input", 0);
    }
    | for_statement
    ;

write_parameter:
    expression
    | STRING_LITERAL 
    ;

for_statement:
    FOR IDENTIFIER ASSIGN expression TO expression DO statement 
    ;

expression:
    term
    | STRING_LITERAL {
        add_token(yytext, "String Literal", 0);
    }
    | expression PLUS term {
        add_token("+", "Operator", 0);
    }
    | expression MINUS term {
        add_token("-", "Operator", 0);
    }
    | expression GREATER term {
        add_token(">", "Relational Operator", 0);
    }
    | expression LESS term {
        add_token("<", "Relational Operator", 0);
    }
    | expression GEQ term {
        add_token(">=", "Relational Operator", 0);
    }
    | expression LEQ term {
        add_token("<=", "Relational Operator", 0);
    }
    | expression EQUAL term {
        add_token("==", "Equality Operator", 0);
    }
    | expression NEQ term {
        add_token("!=", "Relational Operator", 0);
    }
    ;

term:
    factor
    | term MUL factor {
        add_token("*", "Operator", 0);
    }
    | term DIV factor {
        add_token("/", "Operator", 0);
    }
    ;

factor:
    NUMBER {
        add_token(yytext, "Number", $1);
    }
    | IDENTIFIER {
        add_token($1, "Identifier", 0);
    }
    | LPAREN expression RPAREN {
        add_token("(", "Parenthesis", 0);
        add_token(")", "Parenthesis", 0);
    }
    | INVALID {
        yyerror("Invalid token");
    }
    ;

%%

void yyerror(const char *s){};

int main(int argc, char *argv[]) {
    if (argc != 3) {
        fprintf(stderr, "Usage: %s <input_file> <output_file>\n", argv[0]);
        return 1;
    }

    yyin = fopen(argv[1], "r");
    if (!yyin) {
        perror("Error opening input file");
        return 1;
    }

    yyout = fopen(argv[2], "w");
    if (!yyout) {
        perror("Error opening output file");
        fclose(yyin);
        return 1;
    }

    print_token_table_header();
    int parse_result = yyparse();

    display_error_messages();
    
    fclose(yyin);
    fclose(yyout);
    return parse_result;
}