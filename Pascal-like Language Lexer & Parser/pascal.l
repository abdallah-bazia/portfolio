%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "parser.tab.h"

extern YYSTYPE yylval;
extern FILE *yyout;

// Buffer for unknown character messages
#define MAX_ERROR_COUNT 1000
char error_messages[MAX_ERROR_COUNT][256];
int total_error_count = 0;



void display_error_messages() {
    fprintf(yyout, "\nUnknown characters encountered:\n");
    for (int i = 0; i < total_error_count; i++) {
        fprintf(yyout, "%s", error_messages[i]);
    }
}

void print_token_table_header() {
    fprintf(yyout, "Lexeme  Category  Value\n");
}
%}
%option noinput
%option yylineno
%option nounput


%%



"for"          { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return FOR; }
"readln"       { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return READLN; }
"var"          { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return VAR; }
"begin"        { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return BEGIN_TOKEN; }
"program"      { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return PROGRAM; }
"uses"         { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return USES; }
"writeIn"      { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return WRITEIN; }
"end"          { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return END_TOKEN; }
"const"        { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return CONST; }
"Write"        { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return WRITE; }
"to"           { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return TO; }
"do"           { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return DO; }
[ \t]+         ; 
[\n]           ; 
"writeln"      { fprintf(yyout, "%s  KEYWORD  -\n", yytext); return WRITELN; }
"integer"      { fprintf(yyout, "%s  INTEGER  -\n", yytext); return INTEGER; }
"string"       { yylval.id = strdup(yytext); fprintf(yyout, "%s  STRING  -\n", yytext); return STRING; }
[0-9]+         { yylval.num = atoi(yytext); fprintf(yyout, "%s  NUMBER  %d\n", yytext, yylval.num); return NUMBER; }

";"            { fprintf(yyout, "%s  SEMICOLON  -\n", yytext); return SEMICOLON; }
":"            { fprintf(yyout, "%s  COLON  -\n", yytext); return COLON; }
"."            { fprintf(yyout, "%s  DOT  -\n", yytext); return DOT; }
"<"            { fprintf(yyout, "%s  LESS  -\n", yytext); return LESS; }
">="           { fprintf(yyout, "%s  GEQ  -\n", yytext); return GEQ; }
"<="           { fprintf(yyout, "%s  LEQ  -\n", yytext); return LEQ; }
"<>"           { fprintf(yyout, "%s  NEQ  -\n", yytext); return NEQ; }
"("            { fprintf(yyout, "%s  LPAREN  -\n", yytext); return LPAREN; }
","            { fprintf(yyout, "%s  COMMA  -\n", yytext); return COMMA; }
":="           { fprintf(yyout, "%s  ASSIGN  -\n", yytext); return ASSIGN; }
"="            { fprintf(yyout, "%s  EQUAL  -\n", yytext); return EQUAL; }
">"            { fprintf(yyout, "%s  GREATER  -\n", yytext); return GREATER; }
")"            { fprintf(yyout, "%s  RPAREN  -\n", yytext); return RPAREN; }
"*"            { fprintf(yyout, "%s  MUL  -\n", yytext); return MUL; }
"/"            { fprintf(yyout, "%s  DIV  -\n", yytext); return DIV; }
"+"            { fprintf(yyout, "%s  PLUS  -\n", yytext); return PLUS; }
"-"            { fprintf(yyout, "%s  MINUS  -\n", yytext); return MINUS; }
[a-zA-Z][a-zA-Z0-9]* { yylval.id = strdup(yytext); fprintf(yyout, "%s  IDENTIFIER  -\n", yytext); return IDENTIFIER; }
\'([^\'\n]|\\\')*\' { fprintf(yyout, "%s  STRING_LITERAL  %s\n", yytext, yytext); return STRING_LITERAL; }
[!@#$%^&*_+\-\[\]{};\\|.<>\/\?~]+[a-zA-Z0-9_]* { 
    if (total_error_count < MAX_ERROR_COUNT) {
        snprintf(error_messages[total_error_count], 256, "Line %d: Unknown char in '%s'\n", yylineno, yytext);
        total_error_count++;
    }
    return INVALID;
}


%%

int yywrap(void) {
    fprintf(yyout, ". End of Token Table .\n");
    return 1;
}

