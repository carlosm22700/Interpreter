package java.com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// static imports are considered bad style but this works for now

import static java.com.craftinginterpreters.lox.TokenType.*;

//Scanner will make its way through the source code, adding token until it runs out of characters. 
class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    // fields below keep track of where scanner is on source code.
    private int start = 0; // start points to the first character in the lexeme being scanned.
    private int current = 0; // points to the  curr. character being considered.
    private int line = 1; // tracks what source line current is on so we can produce tokens that know their location.
 
    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // We are at the beginning of the next lexeme.
            start = current;
            scanTokens();
        }
        // Once Scanner is at end (out of characters), append one final "end of file" token.
        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;      
            case ')': addToken(RIGHT_PAREN); break;     
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;
            case '!': addToken(match('=') ? BANG_EQUAL : BANG); break;
            case '=': addToken(match('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<': addToken(match('=') ? LESS_EQUAL: LESS); break;
            case '>': addToken(match('=') ? GREATER_EQUAL : GREATER); break;
            
            default:
            Lox.error(line, "Unexpected character.");
            break;
        }


    }

    private boolean match(char expected) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current++;
        return true;
    }
    
    // Helper function to tell us if we've consumed all of the characters
    private boolean isAtEnd() {
        return current >= source.length();
    }

    // helper for scan

    private char advance() {
        current ++;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
        
    }
}

