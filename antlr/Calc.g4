grammar Calc;

prog:   expr
    ;

expr:   left=expr op='%'
    |   left=expr op='^' right=expr
    |   left=expr op=('*'|'/') right=expr
    |   left=expr op=('+'|'-') right=expr
    |   neg='-' right=expr
    |   funcCall
    |   NUMBER
    |   '(' expr ')'
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

fragment
DIGIT:  '0'..'9';

CONSTANT
    :   'pi'
    |   'e'
    ;

WS  :   [ \t\n\r]+ -> skip;