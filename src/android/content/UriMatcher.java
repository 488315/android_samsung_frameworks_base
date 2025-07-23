package android.content;

import java.util.ArrayList;

/* loaded from: classes.dex */
public class UriMatcher {
    private static final int EXACT = 0;
    public static final int NO_MATCH = -1;
    private static final int NUMBER = 1;
    private static final int TEXT = 2;
    private ArrayList<UriMatcher> mChildren;
    private int mCode;
    private final String mText;
    private final int mWhich;

    public UriMatcher(int i) {
        this.mCode = i;
        this.mWhich = -1;
        this.mChildren = new ArrayList<>();
        this.mText = null;
    }

    private UriMatcher(int i, String str) {
        this.mCode = -1;
        this.mWhich = i;
        this.mChildren = new ArrayList<>();
        this.mText = str;
    }

    public void addURI(String str, String str2, int i) {
        String[] strArr;
        if (i < 0) {
            throw new IllegalArgumentException("code " + i + " is invalid: it must be positive");
        }
        if (str2 != null) {
            if (str2.length() > 1 && str2.charAt(0) == '/') {
                str2 = str2.substring(1);
            }
            strArr = str2.split("/");
        } else {
            strArr = null;
        }
        int length = strArr != null ? strArr.length : 0;
        int i2 = -1;
        while (i2 < length) {
            String str3 = i2 < 0 ? str : strArr[i2];
            ArrayList<UriMatcher> arrayList = this.mChildren;
            int size = arrayList.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                UriMatcher uriMatcher = arrayList.get(i3);
                if (str3.equals(uriMatcher.mText)) {
                    this = uriMatcher;
                    break;
                }
                i3++;
            }
            if (i3 == size) {
                UriMatcher createChild = createChild(str3);
                this.mChildren.add(createChild);
                this = createChild;
            }
            i2++;
        }
        this.mCode = i;
    }

    private static UriMatcher createChild(String str) {
        str.hashCode();
        if (str.equals("#")) {
            return new UriMatcher(1, "#");
        }
        if (str.equals("*")) {
            return new UriMatcher(2, "*");
        }
        return new UriMatcher(0, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0040, code lost:
    
        if (r10 != 2) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0060, code lost:
    
        if (r9.mText.equals(r4) != false) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int match(android.net.Uri r15) {
        /*
            r14 = this;
            java.util.List r0 = r15.getPathSegments()
            int r1 = r0.size()
            if (r1 != 0) goto L13
            java.lang.String r2 = r15.getAuthority()
            if (r2 != 0) goto L13
            int r14 = r14.mCode
            return r14
        L13:
            r2 = -1
            r3 = r2
        L15:
            if (r3 >= r1) goto L70
            if (r3 >= 0) goto L1e
            java.lang.String r4 = r15.getAuthority()
            goto L24
        L1e:
            java.lang.Object r4 = r0.get(r3)
            java.lang.String r4 = (java.lang.String) r4
        L24:
            java.util.ArrayList<android.content.UriMatcher> r5 = r14.mChildren
            if (r5 != 0) goto L29
            goto L70
        L29:
            int r14 = r5.size()
            r6 = 0
            r7 = 0
            r8 = r6
        L30:
            if (r8 >= r14) goto L69
            java.lang.Object r9 = r5.get(r8)
            android.content.UriMatcher r9 = (android.content.UriMatcher) r9
            int r10 = r9.mWhich
            if (r10 == 0) goto L5a
            r11 = 1
            if (r10 == r11) goto L43
            r11 = 2
            if (r10 == r11) goto L62
            goto L63
        L43:
            int r10 = r4.length()
            r11 = r6
        L48:
            if (r11 >= r10) goto L62
            char r12 = r4.charAt(r11)
            r13 = 48
            if (r12 < r13) goto L63
            r13 = 57
            if (r12 <= r13) goto L57
            goto L63
        L57:
            int r11 = r11 + 1
            goto L48
        L5a:
            java.lang.String r10 = r9.mText
            boolean r10 = r10.equals(r4)
            if (r10 == 0) goto L63
        L62:
            r7 = r9
        L63:
            if (r7 == 0) goto L66
            goto L69
        L66:
            int r8 = r8 + 1
            goto L30
        L69:
            r14 = r7
            if (r14 != 0) goto L6d
            return r2
        L6d:
            int r3 = r3 + 1
            goto L15
        L70:
            int r14 = r14.mCode
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.UriMatcher.match(android.net.Uri):int");
    }
}
