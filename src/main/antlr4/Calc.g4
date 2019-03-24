grammar Calc;

prog:   expr
    |   setup
    ;

expr:   left=expr op='%'
    |   left=expr op='^' right=expr
    |   left=expr op=('*'|'/') right=expr
    |   left=expr op=('+'|'-') right=expr
    |   neg='-' right=expr
    |   funcCall
    |   NUMBER
    |   VARIABLE
    |   '(' expr ')'
    ;

setup:  left=VARIABLE op='=' right=expr
    ;

funcCall
    :   f='sin' '(' expr ')'
    |   f='cos' '(' expr ')'
    |   f='log' '(' expr ')'
    ;

NUMBER
    :   DIGIT* '.' DIGIT*
    |   DIGIT+
    |   CONSTANT
    ;

VARIABLE
    :   LETTER+
    ;

fragment

DIGIT:  '0'..'9';

LETTER: 'a'..'z';

CONSTANT
    :   'PI'
    |   'E'
    ;

WS  :   [ \t\n\r]+ -> skip;