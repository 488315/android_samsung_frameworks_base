package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class HostEndpointInfo implements Parcelable {
    public static final Parcelable.Creator<HostEndpointInfo> CREATOR = new Parcelable.Creator<HostEndpointInfo>() { // from class: android.hardware.contexthub.HostEndpointInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HostEndpointInfo createFromParcel(Parcel parcel) {
            HostEndpointInfo hostEndpointInfo = new HostEndpointInfo();
            hostEndpointInfo.readFromParcel(parcel);
            return hostEndpointInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public HostEndpointInfo[] newArray(int i) {
            return new HostEndpointInfo[i];
        }
    };
    public String attributionTag;
    public char hostEndpointId = 0;
    public String packageName;
    public int type;

    public @interface Type {
        public static final int APP = 2;
        public static final int FRAMEWORK = 1;
        public static final int NATIVE = 3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.hostEndpointId);
        parcel.writeInt(this.type);
        parcel.writeString(this.packageName);
        parcel.writeString(this.attributionTag);
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
                this.hostEndpointId = (char) parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.packageName = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.attributionTag = parcel.readString();
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
