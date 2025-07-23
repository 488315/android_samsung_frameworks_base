package android.graphics;

import android.graphics.Shader;
import com.android.graphics.hwui.flags.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class BitmapShader extends Shader {
    public static final int FILTER_MODE_DEFAULT = 0;
    public static final int FILTER_MODE_LINEAR = 2;
    public static final int FILTER_MODE_NEAREST = 1;
    Bitmap mBitmap;
    private boolean mFilterFromPaint;
    private int mFilterMode;
    private boolean mIsDirectSampled;
    private int mMaxAniso;
    private Gainmap mOverrideGainmap;
    private boolean mRequestDirectSampling;
    private int mTileX;
    private int mTileY;

    @Retention(RetentionPolicy.SOURCE)
    public @interface FilterMode {
    }

    private static native long nativeCreate(long j, long j2, int i, int i2, int i3, boolean z, boolean z2, long j3);

    public BitmapShader(Bitmap bitmap, Shader.TileMode tileMode, Shader.TileMode tileMode2) {
        this(bitmap, tileMode.nativeInt, tileMode2.nativeInt);
    }

    private BitmapShader(Bitmap bitmap, int i, int i2) {
        this.mMaxAniso = 0;
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap must be non-null");
        }
        bitmap.checkRecycled("Cannot create BitmapShader for recycled bitmap");
        this.mBitmap = bitmap;
        this.mTileX = i;
        this.mTileY = i2;
        this.mFilterMode = 0;
        this.mFilterFromPaint = false;
        this.mIsDirectSampled = false;
        this.mRequestDirectSampling = false;
    }

    public int getFilterMode() {
        return this.mFilterMode;
    }

    public void setFilterMode(int i) {
        if (i != this.mFilterMode) {
            this.mFilterMode = i;
            this.mMaxAniso = 0;
            discardNativeInstance();
        }
    }

    public void setMaxAnisotropy(int i) {
        if (this.mMaxAniso == i || i <= 0) {
            return;
        }
        this.mMaxAniso = i;
        this.mFilterMode = 0;
        discardNativeInstance();
    }

    public void setOverrideGainmap(Gainmap gainmap) {
        if (!Flags.gainmapAnimations()) {
            throw new IllegalStateException("API not available");
        }
        if (gainmap == null) {
            this.mOverrideGainmap = null;
        } else {
            this.mOverrideGainmap = new Gainmap(gainmap, gainmap.getGainmapContents());
        }
        discardNativeInstance();
    }

    public int getMaxAnisotropy() {
        return this.mMaxAniso;
    }

    synchronized long getNativeInstanceWithDirectSampling() {
        this.mRequestDirectSampling = true;
        return getNativeInstance();
    }

    @Override // android.graphics.Shader
    protected long createNativeInstance(long j, boolean z) {
        boolean z2;
        this.mBitmap.checkRecycled("BitmapShader's bitmap has been recycled");
        int i = this.mFilterMode;
        boolean z3 = i == 2;
        if (i == 0) {
            this.mFilterFromPaint = z;
            z2 = z;
        } else {
            z2 = z3;
        }
        this.mIsDirectSampled = this.mRequestDirectSampling;
        this.mRequestDirectSampling = false;
        long nativeInstance = this.mBitmap.getNativeInstance();
        int i2 = this.mTileX;
        int i3 = this.mTileY;
        int i4 = this.mMaxAniso;
        boolean z4 = this.mIsDirectSampled;
        Gainmap gainmap = this.mOverrideGainmap;
        return nativeCreate(j, nativeInstance, i2, i3, i4, z2, z4, gainmap != null ? gainmap.mNativePtr : 0L);
    }

    @Override // android.graphics.Shader
    protected boolean shouldDiscardNativeInstance(boolean z) {
        if (this.mIsDirectSampled == this.mRequestDirectSampling) {
            return this.mFilterMode == 0 && this.mFilterFromPaint != z;
        }
        return true;
    }
}
