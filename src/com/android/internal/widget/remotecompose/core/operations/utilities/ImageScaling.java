package com.android.internal.widget.remotecompose.core.operations.utilities;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.operations.Utils;

/* loaded from: classes6.dex */
public class ImageScaling {
    private static final boolean DEBUG = false;
    public static final int SCALE_CROP = 5;
    public static final int SCALE_FILL_BOUNDS = 6;
    public static final int SCALE_FILL_HEIGHT = 3;
    public static final int SCALE_FILL_WIDTH = 2;
    public static final int SCALE_FIT = 4;
    public static final int SCALE_FIXED_SCALE = 7;
    public static final int SCALE_INSIDE = 1;
    public static final int SCALE_NONE = 0;
    private float mDstBottom;
    private float mDstLeft;
    private float mDstRight;
    private float mDstTop;
    public float mFinalDstBottom;
    public float mFinalDstLeft;
    public float mFinalDstRight;
    public float mFinalDstTop;
    private float mScaleFactor;
    private int mScaleType;
    private float mSrcBottom;
    private float mSrcLeft;
    private float mSrcRight;
    private float mSrcTop;

    public ImageScaling() {
    }

    public ImageScaling(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i, float f9) {
        this.mSrcLeft = f;
        this.mSrcTop = f2;
        this.mSrcRight = f3;
        this.mSrcBottom = f4;
        this.mDstLeft = f5;
        this.mDstTop = f6;
        this.mDstRight = f7;
        this.mDstBottom = f8;
        this.mScaleType = i;
        this.mScaleFactor = f9;
        adjustDrawToType();
    }

    public void setup(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, int i, float f9) {
        this.mSrcLeft = f;
        this.mSrcTop = f2;
        this.mSrcRight = f3;
        this.mSrcBottom = f4;
        this.mDstLeft = f5;
        this.mDstTop = f6;
        this.mDstRight = f7;
        this.mDstBottom = f8;
        this.mScaleType = i;
        this.mScaleFactor = f9;
        adjustDrawToType();
    }

    static String str(float f) {
        return ("  " + ((int) f)).substring(r2.length() - 3);
    }

    void print(String str, float f, float f2, float f3, float f4) {
        Utils.log((str + str(f) + ", " + str(f2) + ", " + str(f3) + ", " + str(f4) + ", ") + " [" + str(f3 - f) + " x " + str(f4 - f2) + NavigationBarInflaterView.SIZE_MOD_END);
    }

    private void adjustDrawToType() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10 = (int) (this.mSrcRight - this.mSrcLeft);
        int i11 = (int) (this.mSrcBottom - this.mSrcTop);
        float f = this.mDstRight;
        float f2 = this.mDstLeft;
        float f3 = f - f2;
        float f4 = this.mDstBottom;
        float f5 = this.mDstTop;
        float f6 = f4 - f5;
        int i12 = (int) f3;
        int i13 = (int) f6;
        if (i11 == 0 || i10 == 0) {
            return;
        }
        int i14 = this.mScaleType;
        if (i14 == 0) {
            i = (i13 - i11) / 2;
            i13 = i11 + i;
            i2 = (i12 - i10) / 2;
        } else if (i14 == 1) {
            if (i13 <= i11 || i12 <= i10) {
                if (i10 * f6 > f3 * i11) {
                    i11 = (i11 * i12) / i10;
                    i10 = i12;
                } else {
                    i10 = (i10 * i13) / i11;
                    i11 = i13;
                }
            }
            i = (i13 - i11) / 2;
            i13 = i11 + i;
            i2 = (i12 - i10) / 2;
        } else {
            i4 = 0;
            if (i14 == 2) {
                int i15 = (i11 * i12) / i10;
                i5 = (i13 - i15) / 2;
                i6 = i15 + i5;
                i4 = 0 / 2;
                i12 += i4;
            } else if (i14 == 3) {
                i10 = (i10 * i13) / i11;
                i = 0 / 2;
                i13 += i;
                i2 = (i12 - i10) / 2;
            } else {
                if (i14 != 4) {
                    if (i14 != 5) {
                        if (i14 != 7) {
                            i3 = 0;
                        } else {
                            float f7 = this.mScaleFactor;
                            int i16 = (int) (i11 * f7);
                            i10 = (int) (i10 * f7);
                            i = (i13 - i16) / 2;
                            i13 = i16 + i;
                            i2 = (i12 - i10) / 2;
                        }
                    } else if (i10 * f6 < f3 * i11) {
                        i9 = (i11 * i12) / i10;
                        i5 = (i13 - i9) / 2;
                        i6 = i9 + i5;
                    } else {
                        i7 = (i10 * i13) / i11;
                        i8 = (i12 - i7) / 2;
                        int i17 = i8;
                        i12 = i7 + i8;
                        i3 = 0;
                        i4 = i17;
                    }
                } else if (i10 * f6 > f3 * i11) {
                    i9 = (i11 * i12) / i10;
                    i5 = (i13 - i9) / 2;
                    i6 = i9 + i5;
                } else {
                    i7 = (i10 * i13) / i11;
                    i8 = (i12 - i7) / 2;
                    int i172 = i8;
                    i12 = i7 + i8;
                    i3 = 0;
                    i4 = i172;
                }
                this.mFinalDstRight = i12 + f2;
                this.mFinalDstLeft = i4 + f2;
                this.mFinalDstBottom = i13 + f5;
                this.mFinalDstTop = i3 + f5;
            }
            int i18 = i5;
            i13 = i6;
            i3 = i18;
            this.mFinalDstRight = i12 + f2;
            this.mFinalDstLeft = i4 + f2;
            this.mFinalDstBottom = i13 + f5;
            this.mFinalDstTop = i3 + f5;
        }
        i12 = i10 + i2;
        i3 = i;
        i4 = i2;
        this.mFinalDstRight = i12 + f2;
        this.mFinalDstLeft = i4 + f2;
        this.mFinalDstBottom = i13 + f5;
        this.mFinalDstTop = i3 + f5;
    }

    public static String typeToString(int i) {
        return new String[]{"none", "inside", "fill_width", "fill_height", "fit", "crop", "fill_bounds", "fixed_scale"}[i];
    }
}
