package org.apache.http.conn.ssl;

@Deprecated
/* loaded from: classes6.dex */
public class AllowAllHostnameVerifier extends AbstractVerifier {
    @Override // org.apache.http.conn.ssl.X509HostnameVerifier
    public final void verify(String str, String[] strArr, String[] strArr2) {
    }

    public final String toString() {
        return "ALLOW_ALL";
    }
}
