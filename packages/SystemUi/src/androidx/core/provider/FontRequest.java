package androidx.core.provider;

import android.util.Base64;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        this.mIdentifier = MotionLayout$$ExternalSyntheticOutline0.m22m(str, "-", str2, "-", str3);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:");
        int i = 0;
        while (true) {
            List list = this.mCertificates;
            if (i >= list.size()) {
                sb.append("}");
                sb.append("mCertificatesArray: " + this.mCertificatesArray);
                return sb.toString();
            }
            sb.append(" [");
            List list2 = (List) list.get(i);
            for (int i2 = 0; i2 < list2.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString((byte[]) list2.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
            i++;
        }
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
            this.mIdentifier = MotionLayout$$ExternalSyntheticOutline0.m22m(str, "-", str2, "-", str3);
            return;
        }
        throw new IllegalArgumentException();
    }
}
