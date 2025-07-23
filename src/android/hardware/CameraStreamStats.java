package android.hardware;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes2.dex */
public class CameraStreamStats implements Parcelable {
    public static final Parcelable.Creator<CameraStreamStats> CREATOR = new Parcelable.Creator<CameraStreamStats>() { // from class: android.hardware.CameraStreamStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraStreamStats createFromParcel(Parcel parcel) {
            try {
                return new CameraStreamStats(parcel);
            } catch (Exception e) {
                Log.e(CameraStreamStats.TAG, "Exception creating CameraStreamStats from parcel", e);
                return null;
            }
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraStreamStats[] newArray(int i) {
            return new CameraStreamStats[i];
        }
    };
    public static final int HISTOGRAM_TYPE_CAPTURE_LATENCY = 1;
    public static final int HISTOGRAM_TYPE_UNKNOWN = 0;
    private static final String TAG = "CameraStreamStats";
    private int mColorSpace;
    private int mDataSpace;
    private long mDynamicRangeProfile;
    private long mErrorCount;
    private int mFormat;
    private int mHeight;
    private float[] mHistogramBins;
    private long[] mHistogramCounts;
    private int mHistogramType;
    private int mMaxAppBuffers;
    private int mMaxHalBuffers;
    private float mMaxPreviewFps;
    private long mRequestCount;
    private int mStartLatencyMs;
    private long mStreamUseCase;
    private long mUsage;
    private int mWidth;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CameraStreamStats() {
        this.mWidth = 0;
        this.mHeight = 0;
        this.mFormat = 0;
        this.mMaxPreviewFps = 0.0f;
        this.mDataSpace = 0;
        this.mUsage = 0L;
        this.mRequestCount = 0L;
        this.mErrorCount = 0L;
        this.mStartLatencyMs = 0;
        this.mMaxHalBuffers = 0;
        this.mMaxAppBuffers = 0;
        this.mHistogramType = 0;
        this.mDynamicRangeProfile = 1L;
        this.mStreamUseCase = 0L;
        this.mColorSpace = -1;
    }

    public CameraStreamStats(int i, int i2, int i3, float f, int i4, long j, long j2, long j3, int i5, int i6, int i7, long j4, long j5, int i8) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mFormat = i3;
        this.mMaxPreviewFps = f;
        this.mDataSpace = i4;
        this.mUsage = j;
        this.mRequestCount = j2;
        this.mErrorCount = j3;
        this.mStartLatencyMs = i5;
        this.mMaxHalBuffers = i6;
        this.mMaxAppBuffers = i7;
        this.mHistogramType = 0;
        this.mDynamicRangeProfile = j4;
        this.mStreamUseCase = j5;
        this.mColorSpace = i8;
    }

    private CameraStreamStats(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
        parcel.writeInt(this.mFormat);
        parcel.writeFloat(this.mMaxPreviewFps);
        parcel.writeInt(this.mDataSpace);
        parcel.writeLong(this.mUsage);
        parcel.writeLong(this.mRequestCount);
        parcel.writeLong(this.mErrorCount);
        parcel.writeInt(this.mStartLatencyMs);
        parcel.writeInt(this.mMaxHalBuffers);
        parcel.writeInt(this.mMaxAppBuffers);
        parcel.writeInt(this.mHistogramType);
        parcel.writeFloatArray(this.mHistogramBins);
        parcel.writeLongArray(this.mHistogramCounts);
        parcel.writeLong(this.mDynamicRangeProfile);
        parcel.writeLong(this.mStreamUseCase);
        parcel.writeInt(this.mColorSpace);
    }

    public void readFromParcel(Parcel parcel) {
        this.mWidth = parcel.readInt();
        this.mHeight = parcel.readInt();
        this.mFormat = parcel.readInt();
        this.mMaxPreviewFps = parcel.readFloat();
        this.mDataSpace = parcel.readInt();
        this.mUsage = parcel.readLong();
        this.mRequestCount = parcel.readLong();
        this.mErrorCount = parcel.readLong();
        this.mStartLatencyMs = parcel.readInt();
        this.mMaxHalBuffers = parcel.readInt();
        this.mMaxAppBuffers = parcel.readInt();
        this.mHistogramType = parcel.readInt();
        this.mHistogramBins = parcel.createFloatArray();
        this.mHistogramCounts = parcel.createLongArray();
        this.mDynamicRangeProfile = parcel.readLong();
        this.mStreamUseCase = parcel.readLong();
        this.mColorSpace = parcel.readInt();
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getFormat() {
        return this.mFormat;
    }

    public float getMaxPreviewFps() {
        return this.mMaxPreviewFps;
    }

    public int getDataSpace() {
        return this.mDataSpace;
    }

    public long getUsage() {
        return this.mUsage;
    }

    public long getRequestCount() {
        return this.mRequestCount;
    }

    public long getErrorCount() {
        return this.mErrorCount;
    }

    public int getStartLatencyMs() {
        return this.mStartLatencyMs;
    }

    public int getMaxHalBuffers() {
        return this.mMaxHalBuffers;
    }

    public int getMaxAppBuffers() {
        return this.mMaxAppBuffers;
    }

    public int getHistogramType() {
        return this.mHistogramType;
    }

    public float[] getHistogramBins() {
        return this.mHistogramBins;
    }

    public long[] getHistogramCounts() {
        return this.mHistogramCounts;
    }

    public long getDynamicRangeProfile() {
        return this.mDynamicRangeProfile;
    }

    public int getColorSpace() {
        return this.mColorSpace;
    }

    public long getStreamUseCase() {
        return this.mStreamUseCase;
    }
}
