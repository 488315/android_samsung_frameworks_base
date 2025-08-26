package android.graphics;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public final class Gainmap implements Parcelable {
    public static final Parcelable.Creator<Gainmap> CREATOR = new Parcelable.Creator<Gainmap>() { // from class: android.graphics.Gainmap.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Gainmap createFromParcel(Parcel parcel) {
            Gainmap gainmap = new Gainmap((Bitmap) parcel.readTypedObject(Bitmap.CREATOR));
            Gainmap.nReadGainmapFromParcel(gainmap.mNativePtr, parcel);
            return gainmap;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Gainmap[] newArray(int i) {
            return new Gainmap[i];
        }
    };
    public static final int GAINMAP_DIRECTION_HDR_TO_SDR = 1;
    public static final int GAINMAP_DIRECTION_SDR_TO_HDR = 0;
    private Bitmap mGainmapContents;
    final long mNativePtr;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GainmapDirection {
    }

    private static native long nCreateCopy(long j);

    private static native long nCreateEmpty();

    private static native ColorSpace nGetAlternativeColorSpace(long j);

    private static native int nGetDirection(long j);

    private static native float nGetDisplayRatioHdr(long j);

    private static native float nGetDisplayRatioSdr(long j);

    private static native void nGetEpsilonHdr(long j, float[] fArr);

    private static native void nGetEpsilonSdr(long j, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native long nGetFinalizer();

    private static native void nGetGamma(long j, float[] fArr);

    private static native void nGetRatioMax(long j, float[] fArr);

    private static native void nGetRatioMin(long j, float[] fArr);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nReadGainmapFromParcel(long j, Parcel parcel);

    private static native void nSetAlternativeColorSpace(long j, long j2);

    private static native void nSetBitmap(long j, Bitmap bitmap);

    private static native void nSetDirection(long j, int i);

    private static native void nSetDisplayRatioHdr(long j, float f);

    private static native void nSetDisplayRatioSdr(long j, float f);

    private static native void nSetEpsilonHdr(long j, float f, float f2, float f3);

    private static native void nSetEpsilonSdr(long j, float f, float f2, float f3);

    private static native void nSetGamma(long j, float f, float f2, float f3);

    private static native void nSetRatioMax(long j, float f, float f2, float f3);

    private static native void nSetRatioMin(long j, float f, float f2, float f3);

    private static native void nWriteGainmapToParcel(long j, Parcel parcel);

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private static class NoImagePreloadHolder {
        public static final NativeAllocationRegistry sRegistry = NativeAllocationRegistry.createMalloced(Gainmap.class.getClassLoader(), Gainmap.nGetFinalizer());

        private NoImagePreloadHolder() {
        }
    }

    private Gainmap(Bitmap bitmap, long j) {
        if (j == 0) {
            throw new RuntimeException("internal error: native gainmap is 0");
        }
        this.mNativePtr = j;
        setGainmapContents(bitmap);
        NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, j);
    }

    public Gainmap(Bitmap bitmap) {
        this(bitmap, nCreateEmpty());
    }

    public Gainmap(Gainmap gainmap, Bitmap bitmap) {
        this(bitmap, nCreateCopy(gainmap.mNativePtr));
    }

    public Gainmap asShared() {
        Bitmap bitmapAsShared = this.mGainmapContents.asShared();
        return bitmapAsShared == this.mGainmapContents ? this : new Gainmap(bitmapAsShared, nCreateCopy(this.mNativePtr));
    }

    public Bitmap getGainmapContents() {
        return this.mGainmapContents;
    }

    public void setGainmapContents(Bitmap bitmap) {
        if (bitmap.isRecycled()) {
            throw new IllegalArgumentException("Bitmap is recycled");
        }
        nSetBitmap(this.mNativePtr, bitmap);
        this.mGainmapContents = bitmap;
    }

    public void setRatioMin(float f, float f2, float f3) {
        nSetRatioMin(this.mNativePtr, f, f2, f3);
    }

    public float[] getRatioMin() {
        float[] fArr = new float[3];
        nGetRatioMin(this.mNativePtr, fArr);
        return fArr;
    }

    public void setRatioMax(float f, float f2, float f3) {
        nSetRatioMax(this.mNativePtr, f, f2, f3);
    }

    public float[] getRatioMax() {
        float[] fArr = new float[3];
        nGetRatioMax(this.mNativePtr, fArr);
        return fArr;
    }

    public void setGamma(float f, float f2, float f3) {
        nSetGamma(this.mNativePtr, f, f2, f3);
    }

    public float[] getGamma() {
        float[] fArr = new float[3];
        nGetGamma(this.mNativePtr, fArr);
        return fArr;
    }

    public void setEpsilonSdr(float f, float f2, float f3) {
        nSetEpsilonSdr(this.mNativePtr, f, f2, f3);
    }

    public float[] getEpsilonSdr() {
        float[] fArr = new float[3];
        nGetEpsilonSdr(this.mNativePtr, fArr);
        return fArr;
    }

    public void setEpsilonHdr(float f, float f2, float f3) {
        nSetEpsilonHdr(this.mNativePtr, f, f2, f3);
    }

    public float[] getEpsilonHdr() {
        float[] fArr = new float[3];
        nGetEpsilonHdr(this.mNativePtr, fArr);
        return fArr;
    }

    public void setDisplayRatioForFullHdr(float f) {
        if (!Float.isFinite(f) || f < 1.0f) {
            throw new IllegalArgumentException("setDisplayRatioForFullHdr must be >= 1.0f, got = " + f);
        }
        nSetDisplayRatioHdr(this.mNativePtr, f);
    }

    public float getDisplayRatioForFullHdr() {
        return nGetDisplayRatioHdr(this.mNativePtr);
    }

    public void setMinDisplayRatioForHdrTransition(float f) {
        if (!Float.isFinite(f) || f < 1.0f) {
            throw new IllegalArgumentException("setMinDisplayRatioForHdrTransition must be >= 1.0f, got = " + f);
        }
        nSetDisplayRatioSdr(this.mNativePtr, f);
    }

    public float getMinDisplayRatioForHdrTransition() {
        return nGetDisplayRatioSdr(this.mNativePtr);
    }

    public void setAlternativeImagePrimaries(ColorSpace colorSpace) {
        nSetAlternativeColorSpace(this.mNativePtr, colorSpace == null ? 0L : colorSpace.getNativeInstance());
    }

    public ColorSpace getAlternativeImagePrimaries() {
        return nGetAlternativeColorSpace(this.mNativePtr);
    }

    public void setGainmapDirection(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("Invalid gainmap direction: " + i);
        }
        nSetDirection(this.mNativePtr, i);
    }

    public int getGainmapDirection() {
        return nGetDirection(this.mNativePtr);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mNativePtr == 0) {
            throw new IllegalStateException("Cannot be written to a parcel");
        }
        parcel.writeTypedObject(this.mGainmapContents, i);
        nWriteGainmapToParcel(this.mNativePtr, parcel);
    }
}
