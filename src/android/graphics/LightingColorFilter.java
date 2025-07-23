package android.graphics;

/* loaded from: classes.dex */
public class LightingColorFilter extends ColorFilter {
    private int mAdd;
    private int mMul;

    private static native long native_CreateLightingFilter(int i, int i2);

    private static native void native_SetLightingFilterAdd(long j, int i);

    private static native void native_SetLightingFilterMul(long j, int i);

    public LightingColorFilter(int i, int i2) {
        this.mMul = i;
        this.mAdd = i2;
    }

    public int getColorMultiply() {
        return this.mMul;
    }

    public void setColorMultiply(int i) {
        if (this.mMul != i) {
            this.mMul = i;
            native_SetLightingFilterMul(getNativeInstance(), i);
        }
    }

    public int getColorAdd() {
        return this.mAdd;
    }

    public void setColorAdd(int i) {
        if (this.mAdd != i) {
            this.mAdd = i;
            native_SetLightingFilterAdd(getNativeInstance(), i);
        }
    }

    @Override // android.graphics.ColorFilter
    long createNativeInstance() {
        return native_CreateLightingFilter(this.mMul, this.mAdd);
    }
}
