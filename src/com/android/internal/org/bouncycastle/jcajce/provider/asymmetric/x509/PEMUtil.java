package com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509;

import com.android.internal.org.bouncycastle.asn1.ASN1Sequence;
import com.android.internal.org.bouncycastle.util.encoders.Base64;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes5.dex */
class PEMUtil {
    private final Boundaries[] _supportedBoundaries;

    private static class Boundaries {
        private final String _footer;
        private final String _header;

        private Boundaries(String str) {
            this._header = "-----BEGIN " + str + "-----";
            this._footer = "-----END " + str + "-----";
        }

        public boolean isTheExpectedHeader(String str) {
            return str.startsWith(this._header);
        }

        public boolean isTheExpectedFooter(String str) {
            return str.startsWith(this._footer);
        }
    }

    PEMUtil(String str) {
        this._supportedBoundaries = new Boundaries[]{new Boundaries(str), new Boundaries("X509 " + str), new Boundaries("PKCS7")};
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001e, code lost:
    
        if (r4.length() == 0) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String readLine(java.io.InputStream r5) throws java.io.IOException {
        /*
            r4 = this;
            java.lang.StringBuffer r4 = new java.lang.StringBuffer
            r4.<init>()
        L5:
            int r0 = r5.read()
            r1 = 10
            r2 = 13
            if (r0 == r2) goto L18
            if (r0 == r1) goto L18
            if (r0 < 0) goto L18
            char r0 = (char) r0
            r4.append(r0)
            goto L5
        L18:
            if (r0 < 0) goto L20
            int r3 = r4.length()
            if (r3 == 0) goto L5
        L20:
            if (r0 >= 0) goto L2f
            int r5 = r4.length()
            if (r5 != 0) goto L2a
            r4 = 0
            return r4
        L2a:
            java.lang.String r4 = r4.toString()
            return r4
        L2f:
            if (r0 != r2) goto L43
            r0 = 1
            r5.mark(r0)
            int r2 = r5.read()
            if (r2 != r1) goto L3e
            r5.mark(r0)
        L3e:
            if (r2 <= 0) goto L43
            r5.reset()
        L43:
            java.lang.String r4 = r4.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.org.bouncycastle.jcajce.provider.asymmetric.x509.PEMUtil.readLine(java.io.InputStream):java.lang.String");
    }

    private Boundaries getBoundaries(String str) {
        Boundaries boundaries;
        int i = 0;
        while (true) {
            Boundaries[] boundariesArr = this._supportedBoundaries;
            if (i == boundariesArr.length) {
                return null;
            }
            boundaries = boundariesArr[i];
            if (boundaries.isTheExpectedHeader(str) || boundaries.isTheExpectedFooter(str)) {
                break;
            }
            i++;
        }
        return boundaries;
    }

    ASN1Sequence readPEMObject(InputStream inputStream, boolean z) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        Boundaries boundaries = null;
        while (boundaries == null) {
            String readLine = readLine(inputStream);
            if (readLine == null) {
                break;
            }
            boundaries = getBoundaries(readLine);
            if (boundaries != null && !boundaries.isTheExpectedHeader(readLine)) {
                throw new IOException("malformed PEM data: found footer where header was expected");
            }
        }
        if (boundaries == null) {
            if (z) {
                throw new IOException("malformed PEM data: no header found");
            }
            return null;
        }
        Boundaries boundaries2 = null;
        while (boundaries2 == null) {
            String readLine2 = readLine(inputStream);
            if (readLine2 == null) {
                break;
            }
            boundaries2 = getBoundaries(readLine2);
            if (boundaries2 != null) {
                if (!boundaries.isTheExpectedFooter(readLine2)) {
                    throw new IOException("malformed PEM data: header/footer mismatch");
                }
            } else {
                stringBuffer.append(readLine2);
            }
        }
        if (boundaries2 == null) {
            throw new IOException("malformed PEM data: no footer found");
        }
        if (stringBuffer.length() == 0) {
            return null;
        }
        try {
            return ASN1Sequence.getInstance(Base64.decode(stringBuffer.toString()));
        } catch (Exception unused) {
            throw new IOException("malformed PEM data encountered");
        }
    }
}
