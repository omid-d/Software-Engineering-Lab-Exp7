package MiniJava.parser;

import MiniJava.codeGenerator.CodeGenerator;
import MiniJava.scanner.lexicalAnalyzer;
import MiniJava.scanner.token.Token;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ParserServicesFacade {
    private lexicalAnalyzer lexicalAnalyzer;
    private ArrayList<Rule> rules;
    private ParseTable parseTable;
    private CodeGenerator codeGenerator;

    public ParserServicesFacade() {
        this.rules = loadRules("src/main/resources/Rules");
        this.parseTable = loadParseTable("src/main/resources/parseTable");
        this.codeGenerator = new CodeGenerator();
    }

    public void initializeLexicalAnalyzer(java.util.Scanner scanner) {
        this.lexicalAnalyzer = new lexicalAnalyzer(scanner);
    }

    public Token getNextToken() {
        return lexicalAnalyzer.getNextToken();
    }

    public Rule getRule(int index) {
        return rules.get(index);
    }

    public ParseTable getParseTable() {
        return parseTable;
    }

    public void runSemanticAction(int actionIndex, Token lookAhead) {
        try {
            codeGenerator.semanticFunction(actionIndex, lookAhead);
        } catch (Exception e) {
            System.out.println("Code Generator Error");
        }
    }

    public void printMemory() {
        codeGenerator.printMemory();
    }

    private ArrayList<Rule> loadRules(String path) {
        ArrayList<Rule> ruleList = new ArrayList<>();
        try {
            for (String stringRule : Files.readAllLines(Paths.get(path))) {
                ruleList.add(new Rule(stringRule));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ruleList;
    }

    private ParseTable loadParseTable(String path) {
        try {
            return new ParseTable(Files.readAllLines(Paths.get(path)).get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
