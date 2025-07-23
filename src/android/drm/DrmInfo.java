package android.drm;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

@Deprecated
/* loaded from: classes.dex */
public class DrmInfo {
    private final HashMap<String, Object> mAttributes = new HashMap<>();
    private byte[] mData;
    private final int mInfoType;
    private final String mMimeType;

    public DrmInfo(int i, byte[] bArr, String str) {
        this.mInfoType = i;
        this.mMimeType = str;
        this.mData = bArr;
        if (isValid()) {
            return;
        }
        throw new IllegalArgumentException("infoType: " + i + ",mimeType: " + str + ",data: " + Arrays.toString(bArr));
    }

    public DrmInfo(int i, String str, String str2) {
        this.mInfoType = i;
        this.mMimeType = str2;
        try {
            this.mData = DrmUtils.readBytes(str);
        } catch (IOException unused) {
            this.mData = null;
        }
        if (isValid()) {
            return;
        }
        Arrays.toString(this.mData);
        throw new IllegalArgumentException();
    }

    public void put(String str, Object obj) {
        this.mAttributes.put(str, obj);
    }

    public Object get(String str) {
        return this.mAttributes.get(str);
    }

    public Iterator<String> keyIterator() {
        return this.mAttributes.keySet().iterator();
    }

    public Iterator<Object> iterator() {
        return this.mAttributes.values().iterator();
    }

    public byte[] getData() {
        return this.mData;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public int getInfoType() {
        return this.mInfoType;
    }

    boolean isValid() {
        byte[] bArr;
        String str = this.mMimeType;
        return (str == null || str.equals("") || (bArr = this.mData) == null || bArr.length <= 0 || !DrmInfoRequest.isValidType(this.mInfoType)) ? false : true;
    }
}
