package android.provider;

import android.util.Base64;
import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.Preconditions;
import java.util.Collections;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public final class FontRequest {
    private final List<List<byte[]>> mCertificates;
    private final String mIdentifier;
    private final String mProviderAuthority;
    private final String mProviderPackage;
    private final String mQuery;

    public FontRequest(String str, String str2, String str3) {
        String str4 = (String) Preconditions.checkNotNull(str);
        this.mProviderAuthority = str4;
        String str5 = (String) Preconditions.checkNotNull(str3);
        this.mQuery = str5;
        String str6 = (String) Preconditions.checkNotNull(str2);
        this.mProviderPackage = str6;
        this.mCertificates = Collections.EMPTY_LIST;
        this.mIdentifier = str4 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str6 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str5;
    }

    public FontRequest(String str, String str2, String str3, List<List<byte[]>> list) {
        String str4 = (String) Preconditions.checkNotNull(str);
        this.mProviderAuthority = str4;
        String str5 = (String) Preconditions.checkNotNull(str2);
        this.mProviderPackage = str5;
        String str6 = (String) Preconditions.checkNotNull(str3);
        this.mQuery = str6;
        this.mCertificates = (List) Preconditions.checkNotNull(list);
        this.mIdentifier = str4 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str5 + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + str6;
    }

    public String getProviderAuthority() {
        return this.mProviderAuthority;
    }

    public String getProviderPackage() {
        return this.mProviderPackage;
    }

    public String getQuery() {
        return this.mQuery;
    }

    public List<List<byte[]>> getCertificates() {
        return this.mCertificates;
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FontRequest {mProviderAuthority: " + this.mProviderAuthority + ", mProviderPackage: " + this.mProviderPackage + ", mQuery: " + this.mQuery + ", mCertificates:");
        for (int i = 0; i < this.mCertificates.size(); i++) {
            sb.append(" [");
            List<byte[]> list = this.mCertificates.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString(list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        return sb.toString();
    }
}
