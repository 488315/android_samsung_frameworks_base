package android.content;

import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

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
        String[] strArrSplit;
        if (i < 0) {
            throw new IllegalArgumentException("code " + i + " is invalid: it must be positive");
        }
        if (str2 != null) {
            if (str2.length() > 1 && str2.charAt(0) == '/') {
                str2 = str2.substring(1);
            }
            strArrSplit = str2.split("/");
        } else {
            strArrSplit = null;
        }
        int length = strArrSplit != null ? strArrSplit.length : 0;
        int i2 = -1;
        while (i2 < length) {
            String str3 = i2 < 0 ? str : strArrSplit[i2];
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
                UriMatcher uriMatcherCreateChild = createChild(str3);
                this.mChildren.add(uriMatcherCreateChild);
                this = uriMatcherCreateChild;
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

    /* JADX WARN: Removed duplicated region for block: B:35:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0066 A[LOOP:1: B:17:0x0030->B:38:0x0066, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0069 A[EDGE_INSN: B:48:0x0069->B:39:0x0069 BREAK  A[LOOP:1: B:17:0x0030->B:38:0x0066], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int match(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        int size = pathSegments.size();
        if (size == 0 && uri.getAuthority() == null) {
            return this.mCode;
        }
        int i = -1;
        while (i < size) {
            String authority = i < 0 ? uri.getAuthority() : pathSegments.get(i);
            ArrayList<UriMatcher> arrayList = this.mChildren;
            if (arrayList == null) {
                break;
            }
            int size2 = arrayList.size();
            UriMatcher uriMatcher = null;
            for (int i2 = 0; i2 < size2; i2++) {
                UriMatcher uriMatcher2 = arrayList.get(i2);
                int i3 = uriMatcher2.mWhich;
                if (i3 == 0) {
                    if (uriMatcher2.mText.equals(authority)) {
                    }
                    if (uriMatcher != null) {
                    }
                } else if (i3 != 1) {
                    if (i3 == 2) {
                        uriMatcher = uriMatcher2;
                    }
                    if (uriMatcher != null) {
                        break;
                    }
                } else {
                    int length = authority.length();
                    for (int i4 = 0; i4 < length; i4++) {
                        char cCharAt = authority.charAt(i4);
                        if (cCharAt < '0' || cCharAt > '9') {
                            break;
                        }
                    }
                    uriMatcher = uriMatcher2;
                    if (uriMatcher != null) {
                    }
                }
            }
            this = uriMatcher;
            if (this == null) {
                return -1;
            }
            i++;
        }
        return this.mCode;
    }
}
