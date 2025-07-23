package com.android.internal.widget.remotecompose.core.operations.layout.utils;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public class DebugLog {
    public static final boolean DEBUG_LAYOUT_ON = false;
    public static Node currentNode;
    public static Node node;

    public static void display() {
    }

    public static void e() {
    }

    public static void e(StringValueSupplier stringValueSupplier) {
    }

    public static void log(StringValueSupplier stringValueSupplier) {
    }

    public static void printNode(int i, Node node2, StringBuilder sb) {
    }

    public static void s(StringValueSupplier stringValueSupplier) {
    }

    public static class Node {
        public String endString;
        public ArrayList<Node> list = new ArrayList<>();
        public String name;
        public Node parent;

        public Node(Node node, String str) {
            this.parent = node;
            this.name = str;
            this.endString = str + " DONE";
            if (node != null) {
                node.add(this);
            }
        }

        public void add(Node node) {
            this.list.add(node);
        }
    }

    public static class LogNode extends Node {
        public LogNode(Node node, String str) {
            super(node, str);
        }
    }

    static {
        Node node2 = new Node(null, "Root");
        node = node2;
        currentNode = node2;
    }

    public static void clear() {
        Node node2 = new Node(null, "Root");
        node = node2;
        currentNode = node2;
    }
}
