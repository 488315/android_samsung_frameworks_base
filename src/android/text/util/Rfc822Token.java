package android.text.util;

/* loaded from: classes4.dex */
public class Rfc822Token {
    private String mAddress;
    private String mComment;
    private String mName;

    public Rfc822Token(String str, String str2, String str3) {
        this.mName = str;
        this.mAddress = str2;
        this.mComment = str3;
    }

    public String getName() {
        return this.mName;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public String getComment() {
        return this.mComment;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setAddress(String str) {
        this.mAddress = str;
    }

    public void setComment(String str) {
        this.mComment = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        String str = this.mName;
        if (str != null && str.length() != 0) {
            sb.append(quoteNameIfNecessary(this.mName));
            sb.append(' ');
        }
        String str2 = this.mComment;
        if (str2 != null && str2.length() != 0) {
            sb.append('(');
            sb.append(quoteComment(this.mComment));
            sb.append(") ");
        }
        String str3 = this.mAddress;
        if (str3 != null && str3.length() != 0) {
            sb.append('<');
            sb.append(this.mAddress);
            sb.append('>');
        }
        return sb.toString();
    }

    public static String quoteNameIfNecessary(String str) {
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt < 'A' || charAt > 'Z') && ((charAt < 'a' || charAt > 'z') && charAt != ' ' && (charAt < '0' || charAt > '9'))) {
                return "\"" + quoteName(str) + '\"';
            }
        }
        return str;
    }

    public static String quoteName(String str) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '\\' || charAt == '\"') {
                sb.append('\\');
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    public static String quoteComment(String str) {
        int length = str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt == '(' || charAt == ')' || charAt == '\\') {
                sb.append('\\');
            }
            sb.append(charAt);
        }
        return sb.toString();
    }

    public int hashCode() {
        String str = this.mName;
        int hashCode = str != null ? 527 + str.hashCode() : 17;
        String str2 = this.mAddress;
        if (str2 != null) {
            hashCode = (hashCode * 31) + str2.hashCode();
        }
        String str3 = this.mComment;
        return str3 != null ? (hashCode * 31) + str3.hashCode() : hashCode;
    }

    private static boolean stringEquals(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        return str.equals(str2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Rfc822Token)) {
            return false;
        }
        Rfc822Token rfc822Token = (Rfc822Token) obj;
        return stringEquals(this.mName, rfc822Token.mName) && stringEquals(this.mAddress, rfc822Token.mAddress) && stringEquals(this.mComment, rfc822Token.mComment);
    }
}
