package android.hardware;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class CameraExtensionSessionStats implements Parcelable {
    public static final Parcelable.Creator<CameraExtensionSessionStats> CREATOR = new Parcelable.Creator<CameraExtensionSessionStats>() { // from class: android.hardware.CameraExtensionSessionStats.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraExtensionSessionStats createFromParcel(Parcel parcel) {
            CameraExtensionSessionStats cameraExtensionSessionStats = new CameraExtensionSessionStats();
            cameraExtensionSessionStats.readFromParcel(parcel);
            return cameraExtensionSessionStats;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraExtensionSessionStats[] newArray(int i) {
            return new CameraExtensionSessionStats[i];
        }
    };
    public String cameraId;
    public String clientName;
    public String key;
    public int type = -1;
    public boolean isAdvanced = false;
    public int captureFormat = 0;

    public @interface Type {
        public static final int EXTENSION_AUTOMATIC = 0;
        public static final int EXTENSION_BOKEH = 2;
        public static final int EXTENSION_FACE_RETOUCH = 1;
        public static final int EXTENSION_HDR = 3;
        public static final int EXTENSION_NIGHT = 4;
        public static final int EXTENSION_NONE = -1;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.key);
        parcel.writeString(this.cameraId);
        parcel.writeString(this.clientName);
        parcel.writeInt(this.type);
        parcel.writeBoolean(this.isAdvanced);
        parcel.writeInt(this.captureFormat);
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
                this.key = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.cameraId = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.clientName = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.type = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.isAdvanced = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.captureFormat = parcel.readInt();
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
