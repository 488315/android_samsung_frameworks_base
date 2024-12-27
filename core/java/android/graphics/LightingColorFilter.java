package android.graphics;

public class LightingColorFilter extends ColorFilter {
    private int mAdd;
    private int mMul;

    private static native long native_CreateLightingFilter(int i, int i2);

    private static native void native_SetLightingFilterAdd(long j, int i);

    private static native void native_SetLightingFilterMul(long j, int i);

    public LightingColorFilter(int mul, int add) {
        this.mMul = mul;
        this.mAdd = add;
    }

    public int getColorMultiply() {
        return this.mMul;
    }

    public void setColorMultiply(int mul) {
        if (this.mMul != mul) {
            this.mMul = mul;
            native_SetLightingFilterMul(getNativeInstance(), mul);
        }
    }

    public int getColorAdd() {
        return this.mAdd;
    }

    public void setColorAdd(int add) {
        if (this.mAdd != add) {
            this.mAdd = add;
            native_SetLightingFilterAdd(getNativeInstance(), add);
        }
    }

    @Override // android.graphics.ColorFilter
    long createNativeInstance() {
        return native_CreateLightingFilter(this.mMul, this.mAdd);
    }
}
