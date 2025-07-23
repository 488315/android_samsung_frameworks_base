package android.drm;

@Deprecated
/* loaded from: classes.dex */
public class DrmConvertedStatus {
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_INPUTDATA_ERROR = 2;
    public static final int STATUS_OK = 1;
    public final byte[] convertedData;
    public final int offset;
    public final int statusCode;

    private boolean isValidStatusCode(int i) {
        return i == 1 || i == 2 || i == 3;
    }

    public DrmConvertedStatus(int i, byte[] bArr, int i2) {
        if (!isValidStatusCode(i)) {
            throw new IllegalArgumentException("Unsupported status code: " + i);
        }
        this.statusCode = i;
        this.convertedData = bArr;
        this.offset = i2;
    }
}
