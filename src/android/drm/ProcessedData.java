package android.drm;

@Deprecated
/* loaded from: classes.dex */
public class ProcessedData {
    private String mAccountId;
    private final byte[] mData;
    private String mSubscriptionId;

    ProcessedData(byte[] bArr, String str) {
        this.mSubscriptionId = "";
        this.mData = bArr;
        this.mAccountId = str;
    }

    ProcessedData(byte[] bArr, String str, String str2) {
        this.mData = bArr;
        this.mAccountId = str;
        this.mSubscriptionId = str2;
    }

    public byte[] getData() {
        return this.mData;
    }

    public String getAccountId() {
        return this.mAccountId;
    }

    public String getSubscriptionId() {
        return this.mSubscriptionId;
    }
}
