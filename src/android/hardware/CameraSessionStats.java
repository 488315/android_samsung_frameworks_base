package android.hardware;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Range;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CameraSessionStats implements Parcelable {
    public static final int CAMERA_API_LEVEL_1 = 1;
    public static final int CAMERA_API_LEVEL_2 = 2;
    public static final int CAMERA_FACING_BACK = 0;
    public static final int CAMERA_FACING_EXTERNAL = 2;
    public static final int CAMERA_FACING_FRONT = 1;
    public static final int CAMERA_STATE_ACTIVE = 1;
    public static final int CAMERA_STATE_CLOSED = 3;
    public static final int CAMERA_STATE_IDLE = 2;
    public static final int CAMERA_STATE_OPEN = 0;
    public static final int CAMERA_STATE_OPENING = 100;
    public static final int CAMERA_STATE_OPENING_FAILED = 101;
    public static final Parcelable.Creator<CameraSessionStats> CREATOR = new Parcelable.Creator<CameraSessionStats>() { // from class: android.hardware.CameraSessionStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraSessionStats createFromParcel(Parcel parcel) {
            return new CameraSessionStats(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraSessionStats[] newArray(int i) {
            return new CameraSessionStats[i];
        }
    };
    private int mApiLevel;
    private CameraExtensionSessionStats mCameraExtensionSessionStats;
    private String mCameraId;
    private String mClientName;
    private boolean mDeviceError;
    private int mFacing;
    private int mInternalReconfigure;
    private boolean mIsNdk;
    private int mLatencyMs;
    private long mLogId;
    private float mMaxPreviewFps;
    private Range<Integer> mMostRequestedFpsRange;
    private int mNewCameraState;
    private long mRequestCount;
    private long mResultErrorCount;
    private int mSessionIndex;
    private int mSessionType;
    private ArrayList<CameraStreamStats> mStreamStats;
    private boolean mUsedUltraWide;
    private boolean mUsedZoomOverride;
    private String mUserTag;
    private int mVideoStabilizationMode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CameraSessionStats() {
        this.mFacing = -1;
        this.mNewCameraState = -1;
        this.mApiLevel = -1;
        this.mIsNdk = false;
        this.mLatencyMs = -1;
        this.mLogId = 0L;
        this.mMaxPreviewFps = 0.0f;
        this.mSessionType = -1;
        this.mInternalReconfigure = -1;
        this.mRequestCount = 0L;
        this.mResultErrorCount = 0L;
        this.mDeviceError = false;
        this.mStreamStats = new ArrayList<>();
        this.mVideoStabilizationMode = -1;
        this.mUsedUltraWide = false;
        this.mUsedZoomOverride = false;
        this.mMostRequestedFpsRange = new Range<>(0, 0);
        this.mSessionIndex = 0;
        this.mCameraExtensionSessionStats = new CameraExtensionSessionStats();
    }

    public CameraSessionStats(String str, int i, int i2, String str2, int i3, boolean z, int i4, float f, int i5, int i6, long j, int i7) {
        this.mCameraId = str;
        this.mFacing = i;
        this.mNewCameraState = i2;
        this.mClientName = str2;
        this.mApiLevel = i3;
        this.mIsNdk = z;
        this.mLatencyMs = i4;
        this.mLogId = j;
        this.mMaxPreviewFps = f;
        this.mSessionType = i5;
        this.mInternalReconfigure = i6;
        this.mStreamStats = new ArrayList<>();
        this.mVideoStabilizationMode = -1;
        this.mUsedUltraWide = false;
        this.mUsedZoomOverride = false;
        this.mMostRequestedFpsRange = new Range<>(0, 0);
        this.mSessionIndex = i7;
        this.mCameraExtensionSessionStats = new CameraExtensionSessionStats();
    }

    private CameraSessionStats(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCameraId);
        parcel.writeInt(this.mFacing);
        parcel.writeInt(this.mNewCameraState);
        parcel.writeString(this.mClientName);
        parcel.writeInt(this.mApiLevel);
        parcel.writeBoolean(this.mIsNdk);
        parcel.writeInt(this.mLatencyMs);
        parcel.writeLong(this.mLogId);
        parcel.writeFloat(this.mMaxPreviewFps);
        parcel.writeInt(this.mSessionType);
        parcel.writeInt(this.mInternalReconfigure);
        parcel.writeLong(this.mRequestCount);
        parcel.writeLong(this.mResultErrorCount);
        parcel.writeBoolean(this.mDeviceError);
        parcel.writeTypedList(this.mStreamStats);
        parcel.writeString(this.mUserTag);
        parcel.writeInt(this.mVideoStabilizationMode);
        parcel.writeBoolean(this.mUsedUltraWide);
        parcel.writeBoolean(this.mUsedZoomOverride);
        parcel.writeInt(this.mSessionIndex);
        this.mCameraExtensionSessionStats.writeToParcel(parcel, 0);
        parcel.writeInt(this.mMostRequestedFpsRange.getLower().intValue());
        parcel.writeInt(this.mMostRequestedFpsRange.getUpper().intValue());
    }

    public void readFromParcel(Parcel parcel) {
        this.mCameraId = parcel.readString();
        this.mFacing = parcel.readInt();
        this.mNewCameraState = parcel.readInt();
        this.mClientName = parcel.readString();
        this.mApiLevel = parcel.readInt();
        this.mIsNdk = parcel.readBoolean();
        this.mLatencyMs = parcel.readInt();
        this.mLogId = parcel.readLong();
        this.mMaxPreviewFps = parcel.readFloat();
        this.mSessionType = parcel.readInt();
        this.mInternalReconfigure = parcel.readInt();
        this.mRequestCount = parcel.readLong();
        this.mResultErrorCount = parcel.readLong();
        this.mDeviceError = parcel.readBoolean();
        ArrayList<CameraStreamStats> arrayList = new ArrayList<>();
        parcel.readTypedList(arrayList, CameraStreamStats.CREATOR);
        this.mStreamStats = arrayList;
        this.mUserTag = parcel.readString();
        this.mVideoStabilizationMode = parcel.readInt();
        this.mUsedUltraWide = parcel.readBoolean();
        this.mUsedZoomOverride = parcel.readBoolean();
        this.mSessionIndex = parcel.readInt();
        this.mCameraExtensionSessionStats = CameraExtensionSessionStats.CREATOR.createFromParcel(parcel);
        this.mMostRequestedFpsRange = new Range<>(Integer.valueOf(parcel.readInt()), Integer.valueOf(parcel.readInt()));
    }

    public String getCameraId() {
        return this.mCameraId;
    }

    public int getFacing() {
        return this.mFacing;
    }

    public int getNewCameraState() {
        return this.mNewCameraState;
    }

    public String getClientName() {
        return this.mClientName;
    }

    public int getApiLevel() {
        return this.mApiLevel;
    }

    public boolean isNdk() {
        return this.mIsNdk;
    }

    public int getLatencyMs() {
        return this.mLatencyMs;
    }

    public long getLogId() {
        return this.mLogId;
    }

    public float getMaxPreviewFps() {
        return this.mMaxPreviewFps;
    }

    public int getSessionType() {
        return this.mSessionType;
    }

    public int getInternalReconfigureCount() {
        return this.mInternalReconfigure;
    }

    public long getRequestCount() {
        return this.mRequestCount;
    }

    public long getResultErrorCount() {
        return this.mResultErrorCount;
    }

    public boolean getDeviceErrorFlag() {
        return this.mDeviceError;
    }

    public List<CameraStreamStats> getStreamStats() {
        return this.mStreamStats;
    }

    public String getUserTag() {
        return this.mUserTag;
    }

    public int getVideoStabilizationMode() {
        return this.mVideoStabilizationMode;
    }

    public boolean getUsedUltraWide() {
        return this.mUsedUltraWide;
    }

    public boolean getUsedZoomOverride() {
        return this.mUsedZoomOverride;
    }

    public int getSessionIndex() {
        return this.mSessionIndex;
    }

    public CameraExtensionSessionStats getExtensionSessionStats() {
        return this.mCameraExtensionSessionStats;
    }

    public Range<Integer> getMostRequestedFpsRange() {
        return this.mMostRequestedFpsRange;
    }
}
