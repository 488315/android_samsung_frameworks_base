package android.drm;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Deprecated
/* loaded from: classes.dex */
public class DrmRights {
    private String mAccountId;
    private byte[] mData;
    private String mMimeType;
    private String mSubscriptionId;

    public DrmRights(String str, String str2) {
        instantiate(new File(str), str2);
    }

    public DrmRights(String str, String str2, String str3) {
        this(str, str2);
        this.mAccountId = str3;
    }

    public DrmRights(String str, String str2, String str3, String str4) {
        this(str, str2);
        this.mAccountId = str3;
        this.mSubscriptionId = str4;
    }

    public DrmRights(File file, String str) {
        instantiate(file, str);
    }

    private void instantiate(File file, String str) {
        try {
            this.mData = DrmUtils.readBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mMimeType = str;
        if (isValid()) {
            return;
        }
        throw new IllegalArgumentException("mimeType: " + this.mMimeType + ",data: " + Arrays.toString(this.mData));
    }

    public DrmRights(ProcessedData processedData, String str) {
        if (processedData == null) {
            throw new IllegalArgumentException("data is null");
        }
        this.mData = processedData.getData();
        this.mAccountId = processedData.getAccountId();
        this.mSubscriptionId = processedData.getSubscriptionId();
        this.mMimeType = str;
        if (isValid()) {
            return;
        }
        throw new IllegalArgumentException("mimeType: " + this.mMimeType + ",data: " + Arrays.toString(this.mData));
    }

    public byte[] getData() {
        return this.mData;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public String getAccountId() {
        return this.mAccountId;
    }

    public String getSubscriptionId() {
        return this.mSubscriptionId;
    }

    boolean isValid() {
        byte[] bArr;
        String str = this.mMimeType;
        return (str == null || str.equals("") || (bArr = this.mData) == null || bArr.length <= 0) ? false : true;
    }
}
