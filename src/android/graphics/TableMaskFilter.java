package android.graphics;

/* loaded from: classes.dex */
public class TableMaskFilter extends MaskFilter {
    private static native long nativeNewClip(int i, int i2);

    private static native long nativeNewGamma(float f);

    private static native long nativeNewTable(byte[] bArr);

    public TableMaskFilter(byte[] bArr) {
        if (bArr.length < 256) {
            throw new RuntimeException("table.length must be >= 256");
        }
        this.native_instance = nativeNewTable(bArr);
    }

    private TableMaskFilter(long j) {
        this.native_instance = j;
    }

    public static TableMaskFilter CreateClipTable(int i, int i2) {
        return new TableMaskFilter(nativeNewClip(i, i2));
    }

    public static TableMaskFilter CreateGammaTable(float f) {
        return new TableMaskFilter(nativeNewGamma(f));
    }
}
