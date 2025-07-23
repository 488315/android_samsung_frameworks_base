package androidx.core.provider;

import android.util.Base64;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FontRequest {
    public final List mCertificates;
    public final int mCertificatesArray;
    public final String mIdentifier;
    public final String mProviderAuthority;
    public final String mProviderPackage;
    public final String mQuery;

    public FontRequest(String str, String str2, String str3, List<List<byte[]>> list) {
        str.getClass();
        this.mProviderAuthority = str;
        str2.getClass();
        this.mProviderPackage = str2;
        str3.getClass();
        this.mQuery = str3;
        list.getClass();
        this.mCertificates = list;
        this.mCertificatesArray = 0;
        this.mIdentifier = str + "-" + str2 + "-" + str3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:");
        for (int i = 0; i < this.mCertificates.size(); i++) {
            sb.append(" [");
            List list = (List) this.mCertificates.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString((byte[]) list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        sb.append("mCertificatesArray: " + this.mCertificatesArray);
        return sb.toString();
    }

    public FontRequest(String str, String str2, String str3, int i) {
        str.getClass();
        this.mProviderAuthority = str;
        str2.getClass();
        this.mProviderPackage = str2;
        str3.getClass();
        this.mQuery = str3;
        this.mCertificates = null;
        if (i != 0) {
            this.mCertificatesArray = i;
            this.mIdentifier = str + "-" + str2 + "-" + str3;
            return;
        }
        throw new IllegalArgumentException();
    }
}
