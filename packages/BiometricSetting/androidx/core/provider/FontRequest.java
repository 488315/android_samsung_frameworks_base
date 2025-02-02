package androidx.core.provider;

import android.util.Base64;
import java.util.List;

/* loaded from: classes.dex */
public final class FontRequest {
    private final List<List<byte[]>> mCertificates;
    private final String mIdentifier;
    private final String mProviderAuthority;
    private final String mProviderPackage;
    private final String mQuery;

    public FontRequest(String str, String str2, String str3, List<List<byte[]>> list) {
        str.getClass();
        this.mProviderAuthority = str;
        str2.getClass();
        this.mProviderPackage = str2;
        this.mQuery = str3;
        list.getClass();
        this.mCertificates = list;
        this.mIdentifier = str + "-" + str2 + "-" + str3;
    }

    public final List<List<byte[]>> getCertificates() {
        return this.mCertificates;
    }

    final String getId() {
        return this.mIdentifier;
    }

    public final String getProviderAuthority() {
        return this.mProviderAuthority;
    }

    public final String getProviderPackage() {
        return this.mProviderPackage;
    }

    public final String getQuery() {
        return this.mQuery;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:");
        int i = 0;
        while (true) {
            List<List<byte[]>> list = this.mCertificates;
            if (i >= list.size()) {
                sb.append("}mCertificatesArray: 0");
                return sb.toString();
            }
            sb.append(" [");
            List<byte[]> list2 = list.get(i);
            for (int i2 = 0; i2 < list2.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString(list2.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
            i++;
        }
    }
}
