package MiniJava.parser;

import MiniJava.Log.Log;
import MiniJava.errorHandler.ErrorHandler;
import MiniJava.scanner.token.Token;

import java.util.Stack;

public class Parser {
    private Stack<Integer> parsStack;
    private ParserServicesFacade services;

    public Parser() {
        parsStack = new Stack<>();
        parsStack.push(0);
        services = new ParserServicesFacade();
    }

    public void startParse(java.util.Scanner sc) {
        services.initializeLexicalAnalyzer(sc);
        Token lookAhead = services.getNextToken();
        boolean finish = false;

        while (!finish) {
            try {
                Log.print(lookAhead.toString() + "\t" + parsStack.peek());

                Action currentAction = services.getParseTable().getActionTable(parsStack.peek(), lookAhead);
                Log.print(currentAction.toString());

                switch (currentAction.action) {
                    case shift:
                        parsStack.push(currentAction.number);
                        lookAhead = services.getNextToken();
                        break;

                    case reduce:
                        Rule rule = services.getRule(currentAction.number);
                        for (int i = 0; i < rule.RHS.size(); i++) {
                            parsStack.pop();
                        }

                        Log.print(parsStack.peek() + "\t" + rule.LHS);
                        parsStack.push(services.getParseTable().getGotoTable(parsStack.peek(), rule.LHS));
                        Log.print(parsStack.peek() + "");

                        services.runSemanticAction(rule.semanticAction, lookAhead);
                        break;

                    case accept:
                        finish = true;
                        break;
                }
                Log.print("");
            } catch (Exception ignored) {
                ignored.printStackTrace();
            }
        }

        if (!ErrorHandler.hasError) {
            services.printMemory();
        }
    }
}
