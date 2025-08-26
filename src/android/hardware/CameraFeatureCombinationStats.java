package android.hardware;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CameraFeatureCombinationStats implements Parcelable {
    public static final long CAMERA_FEATURE_4K = 32;
    public static final long CAMERA_FEATURE_60_FPS = 1;
    public static final long CAMERA_FEATURE_HLG10 = 4;
    public static final long CAMERA_FEATURE_JPEG = 8;
    public static final long CAMERA_FEATURE_JPEG_R = 16;
    public static final long CAMERA_FEATURE_STABILIZATION = 2;
    public static final long CAMERA_FEATURE_UNKNOWN = 0;
    public static final Parcelable.Creator<CameraFeatureCombinationStats> CREATOR = new Parcelable.Creator<CameraFeatureCombinationStats>() { // from class: android.hardware.CameraFeatureCombinationStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraFeatureCombinationStats createFromParcel(Parcel parcel) {
            CameraFeatureCombinationStats cameraFeatureCombinationStats = new CameraFeatureCombinationStats();
            cameraFeatureCombinationStats.readFromParcel(parcel);
            return cameraFeatureCombinationStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraFeatureCombinationStats[] newArray(int i) {
            return new CameraFeatureCombinationStats[i];
        }
    };
    public String mCameraId;
    public int mUid = 0;
    public long mFeatureCombination = 0;
    public int mQueryType = 0;
    public int mStatus = 0;

    public @interface QueryType {
        public static final byte QUERY_FEATURE_COMBINATION = 0;
        public static final byte QUERY_SESSION_CHARACTERISTICS = 1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.mCameraId);
        parcel.writeInt(this.mUid);
        parcel.writeLong(this.mFeatureCombination);
        parcel.writeInt(this.mQueryType);
        parcel.writeInt(this.mStatus);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.mCameraId = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.mUid = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.mFeatureCombination = parcel.readLong();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.mQueryType = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.mStatus = parcel.readInt();
                                if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
