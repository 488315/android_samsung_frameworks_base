package com.android.internal.org.bouncycastle.jce.provider;

import com.android.internal.org.bouncycastle.asn1.ASN1InputStream;
import com.android.internal.org.bouncycastle.asn1.ASN1Primitive;
import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.util.encoders.Base64;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
public class PEMUtil {
    private final String _footer1;
    private final String _footer2;
    private final String _header1;
    private final String _header2;

    PEMUtil(String str) {
        this._header1 = "-----BEGIN " + str + "-----";
        this._header2 = "-----BEGIN X509 " + str + "-----";
        this._footer1 = "-----END " + str + "-----";
        this._footer2 = "-----END X509 " + str + "-----";
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0021, code lost:
    
        if (r3.length() == 0) goto L27;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String readLine(java.io.InputStream r4) throws java.io.IOException {
        /*
            r3 = this;
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
        L5:
            int r0 = r4.read()
            r1 = 13
            if (r0 == r1) goto L1b
            r2 = 10
            if (r0 == r2) goto L1b
            if (r0 < 0) goto L1b
            if (r0 != r1) goto L16
            goto L5
        L16:
            char r0 = (char) r0
            r3.append(r0)
            goto L5
        L1b:
            if (r0 < 0) goto L23
            int r1 = r3.length()
            if (r1 == 0) goto L5
        L23:
            if (r0 >= 0) goto L27
            r3 = 0
            return r3
        L27:
            java.lang.String r3 = r3.toString()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jce.provider.PEMUtil.readLine(java.io.InputStream):java.lang.String");
    }

    ASN1Sequence readPEMObject(InputStream inputStream) throws IOException {
        String readLine;
        StringBuffer stringBuffer = new StringBuffer();
        do {
            readLine = readLine(inputStream);
            if (readLine == null || readLine.startsWith(this._header1)) {
                break;
            }
        } while (!readLine.startsWith(this._header2));
        while (true) {
            String readLine2 = readLine(inputStream);
            if (readLine2 == null || readLine2.startsWith(this._footer1) || readLine2.startsWith(this._footer2)) {
                break;
            }
            stringBuffer.append(readLine2);
        }
        if (stringBuffer.length() == 0) {
            return null;
        }
        ASN1Primitive readObject = new ASN1InputStream(Base64.decode(stringBuffer.toString())).readObject();
        if (!(readObject instanceof ASN1Sequence)) {
            throw new IOException("malformed PEM data encountered");
        }
        return (ASN1Sequence) readObject;
    }
}
