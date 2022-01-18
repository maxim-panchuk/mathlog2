package model;

import lexem.Lexeme;
import lexem.LexemeBuffer;
import lexem.LexemeType;
import util.Node;
import util.NodeType;

import java.util.HashMap;
import java.util.Map;

public class Counter {

    public Map<String, Integer> vars = new HashMap<>();

    public Node expr (LexemeBuffer lexemeBuffer) {
        Lexeme lexeme = lexemeBuffer.next();
        if (lexeme.lexemeType == LexemeType.EOF) {
            return null;
        } else {
            lexemeBuffer.back();
            return impl(lexemeBuffer);
        }
    }

    public Node impl (LexemeBuffer lexemeBuffer) {
        Node leftChild = dis(lexemeBuffer);
        while (lexemeBuffer.next().lexemeType == LexemeType.OP_IMPL) {
            Node emptyNode = insertNode(NodeType.IMPL, null);
            emptyNode.leftChild = leftChild;
            Node rightChild = impl(lexemeBuffer);
            emptyNode.rightChild = rightChild;
            leftChild.parentNode = emptyNode;
            rightChild.parentNode = emptyNode;
            emptyNode.data = "(->," + leftChild.data + "," + rightChild.data + ")";
            leftChild = emptyNode;
        }
        lexemeBuffer.back();
        return leftChild;


    }


    public Node dis (LexemeBuffer lexemeBuffer) {
        Node leftChild = con(lexemeBuffer);
        while (lexemeBuffer.next().lexemeType == LexemeType.OP_DIS) {
            Node emptyNode = insertNode(NodeType.DIS, null);
            emptyNode.leftChild = leftChild;
            Node rightChild = con(lexemeBuffer);
            emptyNode.rightChild = rightChild;
            leftChild.parentNode = emptyNode;
            rightChild.parentNode = emptyNode;
            emptyNode.data = "(|," + leftChild.data + "," + rightChild.data + ")";
            leftChild = emptyNode;
        }
        lexemeBuffer.back();
        return leftChild;
    }

    public Node con (LexemeBuffer lexemeBuffer) {
        Node leftChild = neg(lexemeBuffer);
        while (lexemeBuffer.next().lexemeType == LexemeType.OP_CON) {
            Node emptyNode = insertNode(NodeType.CON, null);
            emptyNode.leftChild = leftChild;
            Node rightChild = neg(lexemeBuffer);
            emptyNode.rightChild = rightChild;
            leftChild.parentNode = emptyNode;
            rightChild.parentNode = emptyNode;
            emptyNode.data = "(&," + leftChild.data + "," + rightChild.data + ")";
            leftChild = emptyNode;
        }
        lexemeBuffer.back();
        return leftChild;
    }


    public Node neg (LexemeBuffer lexemeBuffer) {
        Node node;
        if (lexemeBuffer.next().lexemeType == LexemeType.OP_NEG) {
            Node childNode = neg(lexemeBuffer);
            node = insertNode(NodeType.NEG, "(!" + childNode.data + ")");
            node.leftChild = childNode;
            node.leftChild.parentNode = node;
            return node;
        } else {
            lexemeBuffer.back();
            return var(lexemeBuffer);
        }
    }

    public Node var (LexemeBuffer lexemeBuffer) {
        Lexeme lexeme = lexemeBuffer.next();
        if (lexeme.lexemeType == LexemeType.VAR) {
            return insertNode(NodeType.VAR, lexeme.value);
        } else if (lexeme.lexemeType == LexemeType.LEFT_BRACKET) {
            Node node = expr(lexemeBuffer);
            lexeme = lexemeBuffer.next();
            if (lexeme.lexemeType != LexemeType.RIGHT_BRACKET) {
                throw new RuntimeException("Unexpected token: " + lexeme.value
                        + " at position: " + lexemeBuffer.getPos());
            }
            return node;
        } else {
            throw new RuntimeException("Unexpected token: " + lexeme.value
                    + " at position: " + lexemeBuffer.getPos());
        }
    }

    private Node insertNode (NodeType nodeType, String data) {
        if (nodeType == NodeType.VAR) {
            vars.putIfAbsent(data, 0);
        }
        Node node;
            node = new Node(nodeType, data,
                            null, null, null);
            return node;
    }
}