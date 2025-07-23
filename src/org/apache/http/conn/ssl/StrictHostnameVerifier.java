package org.apache.http.conn.ssl;

import javax.net.ssl.SSLException;

@Deprecated
/* loaded from: classes6.dex */
public class StrictHostnameVerifier extends AbstractVerifier {
    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, String[] strArr, String[] strArr2) throws SSLException {
        verify(str, strArr, strArr2, true);
    }

    public final String toString() {
        return "STRICT";
    }
}
