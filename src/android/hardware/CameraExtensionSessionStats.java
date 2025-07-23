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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.key);
        parcel.writeString(this.cameraId);
        parcel.writeString(this.clientName);
        parcel.writeInt(this.type);
        parcel.writeBoolean(this.isAdvanced);
        parcel.writeInt(this.captureFormat);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.key = parcel.readString();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.cameraId = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.clientName = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.type = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.isAdvanced = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.captureFormat = parcel.readInt();
                                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
