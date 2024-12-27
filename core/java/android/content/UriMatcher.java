package android.content;

import java.util.ArrayList;

public class UriMatcher {
    private static final int EXACT = 0;
    public static final int NO_MATCH = -1;
    private static final int NUMBER = 1;
    private static final int TEXT = 2;
    private ArrayList<UriMatcher> mChildren;
    private int mCode;
    private final String mText;
    private final int mWhich;

    public UriMatcher(int code) {
        this.mCode = code;
        this.mWhich = -1;
        this.mChildren = new ArrayList<>();
        this.mText = null;
    }

    private UriMatcher(int which, String text) {
        this.mCode = -1;
        this.mWhich = which;
        this.mChildren = new ArrayList<>();
        this.mText = text;
    }

    public void addURI(String authority, String path, int code) {
        if (code < 0) {
            throw new IllegalArgumentException("code " + code + " is invalid: it must be positive");
        }
        String[] tokens = null;
        if (path != null) {
            String newPath = path;
            if (path.length() > 1 && path.charAt(0) == '/') {
                newPath = path.substring(1);
            }
            tokens = newPath.split("/");
        }
        int numTokens = tokens != null ? tokens.length : 0;
        UriMatcher node = this;
        int i = -1;
        while (i < numTokens) {
            String token = i < 0 ? authority : tokens[i];
            ArrayList<UriMatcher> children = node.mChildren;
            int numChildren = children.size();
            int j = 0;
            while (true) {
                if (j >= numChildren) {
                    break;
                }
                UriMatcher child = children.get(j);
                if (!token.equals(child.mText)) {
                    j++;
                } else {
                    node = child;
                    break;
                }
            }
            if (j == numChildren) {
                UriMatcher child2 = createChild(token);
                node.mChildren.add(child2);
                node = child2;
            }
            i++;
        }
        node.mCode = code;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static UriMatcher createChild(String token) {
        char c;
        switch (token.hashCode()) {
            case 35:
                if (token.equals("#")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 42:
                if (token.equals("*")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                return new UriMatcher(1, "#");
            case 1:
                return new UriMatcher(2, "*");
            default:
                return new UriMatcher(0, token);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x006b A[LOOP:0: B:9:0x0015->B:37:0x006b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0069 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int match(android.net.Uri r14) {
        /*
            r13 = this;
            java.util.List r0 = r14.getPathSegments()
            int r1 = r0.size()
            r2 = r13
            if (r1 != 0) goto L14
            java.lang.String r3 = r14.getAuthority()
            if (r3 != 0) goto L14
            int r3 = r13.mCode
            return r3
        L14:
            r3 = -1
        L15:
            if (r3 >= r1) goto L6e
            if (r3 >= 0) goto L1e
            java.lang.String r4 = r14.getAuthority()
            goto L24
        L1e:
            java.lang.Object r4 = r0.get(r3)
            java.lang.String r4 = (java.lang.String) r4
        L24:
            java.util.ArrayList<android.content.UriMatcher> r5 = r2.mChildren
            if (r5 != 0) goto L29
            goto L6e
        L29:
            r2 = 0
            int r6 = r5.size()
            r7 = 0
        L2f:
            if (r7 >= r6) goto L67
            java.lang.Object r8 = r5.get(r7)
            android.content.UriMatcher r8 = (android.content.UriMatcher) r8
            int r9 = r8.mWhich
            switch(r9) {
                case 0: goto L58;
                case 1: goto L3f;
                case 2: goto L3d;
                default: goto L3c;
            }
        L3c:
            goto L61
        L3d:
            r2 = r8
            goto L61
        L3f:
            int r9 = r4.length()
            r10 = 0
        L44:
            if (r10 >= r9) goto L56
            char r11 = r4.charAt(r10)
            r12 = 48
            if (r11 < r12) goto L61
            r12 = 57
            if (r11 <= r12) goto L53
            goto L61
        L53:
            int r10 = r10 + 1
            goto L44
        L56:
            r2 = r8
            goto L61
        L58:
            java.lang.String r9 = r8.mText
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L61
            r2 = r8
        L61:
            if (r2 == 0) goto L64
            goto L67
        L64:
            int r7 = r7 + 1
            goto L2f
        L67:
            if (r2 != 0) goto L6b
            r7 = -1
            return r7
        L6b:
            int r3 = r3 + 1
            goto L15
        L6e:
            int r3 = r2.mCode
            return r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.content.UriMatcher.match(android.net.Uri):int");
    }
}
