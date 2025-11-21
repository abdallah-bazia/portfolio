#ifndef SHARE_H
#define SHARE_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

extern int yylineno; // Declaration


// Maximum error messages that can be stored
#define MAX_ERROR_COUNT 1000

// External declarations for shared variables
extern FILE *yyout; // Output file for tokens and errors
extern char error_messages[MAX_ERROR_COUNT][256]; // Array to store error messages
extern int total_error_count; // Counter for error messages

// Shared utility functions
void display_error_messages();
void print_token_table_header();

#endif // SHARE_H
