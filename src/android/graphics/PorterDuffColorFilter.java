package android.graphics;

import android.graphics.PorterDuff;

/* loaded from: classes.dex */
public class PorterDuffColorFilter extends ColorFilter {
    private int mColor;
    private PorterDuff.Mode mMode;

    private static native long native_CreateBlendModeFilter(int i, int i2);

    public PorterDuffColorFilter(int i, PorterDuff.Mode mode) {
        this.mColor = i;
        this.mMode = mode;
    }

    public int getColor() {
        return this.mColor;
    }

    public PorterDuff.Mode getMode() {
        return this.mMode;
    }

    @Override // android.graphics.ColorFilter
    long createNativeInstance() {
        return native_CreateBlendModeFilter(this.mColor, this.mMode.nativeInt);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            PorterDuffColorFilter porterDuffColorFilter = (PorterDuffColorFilter) obj;
            if (this.mColor == porterDuffColorFilter.mColor && this.mMode.nativeInt == porterDuffColorFilter.mMode.nativeInt) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (this.mMode.hashCode() * 31) + this.mColor;
    }
}
