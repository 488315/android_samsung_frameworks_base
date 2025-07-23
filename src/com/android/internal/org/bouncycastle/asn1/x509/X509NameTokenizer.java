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

    public String nextToken() {
        if (this.index == this.value.length()) {
            return null;
        }
        int i = this.index + 1;
        this.buf.setLength(0);
        boolean z = false;
        boolean z2 = false;
        while (i != this.value.length()) {
            char charAt = this.value.charAt(i);
            if (charAt == '\"') {
                if (!z) {
                    z2 = !z2;
                }
                this.buf.append(charAt);
            } else if (z || z2) {
                this.buf.append(charAt);
            } else {
                if (charAt == '\\') {
                    this.buf.append(charAt);
                    z = true;
                } else {
                    if (charAt == this.separator) {
                        break;
                    }
                    if (charAt == '#') {
                        StringBuffer stringBuffer = this.buf;
                        if (stringBuffer.charAt(stringBuffer.length() - 1) == '=') {
                            this.buf.append('\\');
                            this.buf.append(charAt);
                        }
                    }
                    if (charAt == '+' && this.separator != '+') {
                        this.buf.append('\\');
                    }
                    this.buf.append(charAt);
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
