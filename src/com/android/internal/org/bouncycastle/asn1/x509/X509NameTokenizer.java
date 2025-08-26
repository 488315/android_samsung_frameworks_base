package com.android.internal.org.bouncycastle.asn1.x509;

/* loaded from: classes5.dex */
public class X509NameTokenizer {
    private StringBuffer buf;
    private int index;
    private char separator;
    private String value;

    public X509NameTokenizer(String str) {
        this(str, ',');
    }

    public X509NameTokenizer(String str, char c) {
        this.buf = new StringBuffer();
        this.value = str;
        this.index = -1;
        this.separator = c;
    }

    public boolean hasMoreTokens() {
        return this.index != this.value.length();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String nextToken() {
        if (this.index == this.value.length()) {
            return null;
        }
        int i = this.index + 1;
        this.buf.setLength(0);
        boolean z = false;
        boolean z2 = false;
        while (i != this.value.length()) {
            char cCharAt = this.value.charAt(i);
            if (cCharAt == '\"') {
                if (!z) {
                    z2 = !z2;
                }
                this.buf.append(cCharAt);
            } else if (z || z2) {
                this.buf.append(cCharAt);
            } else {
                if (cCharAt == '\\') {
                    this.buf.append(cCharAt);
                    z = true;
                } else {
                    if (cCharAt == this.separator) {
                        break;
                    }
                    if (cCharAt == '#') {
                        StringBuffer stringBuffer = this.buf;
                        if (stringBuffer.charAt(stringBuffer.length() - 1) == '=') {
                            this.buf.append('\\');
                        } else if (cCharAt == '+' && this.separator != '+') {
                            this.buf.append('\\');
                        }
                        this.buf.append(cCharAt);
                    }
                }
                i++;
            }
            z = false;
            i++;
        }
        this.index = i;
        return this.buf.toString();
    }
}
