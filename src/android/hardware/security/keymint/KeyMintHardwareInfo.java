package android.hardware.security.keymint;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class KeyMintHardwareInfo implements Parcelable {
    public static final Parcelable.Creator<KeyMintHardwareInfo> CREATOR = new Parcelable.Creator<KeyMintHardwareInfo>() { // from class: android.hardware.security.keymint.KeyMintHardwareInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyMintHardwareInfo createFromParcel(Parcel parcel) {
            KeyMintHardwareInfo keyMintHardwareInfo = new KeyMintHardwareInfo();
            keyMintHardwareInfo.readFromParcel(parcel);
            return keyMintHardwareInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyMintHardwareInfo[] newArray(int i) {
            return new KeyMintHardwareInfo[i];
        }
    };
    public String keyMintAuthorName;
    public String keyMintName;
    public int versionNumber = 0;
    public int securityLevel = 0;
    public boolean timestampTokenRequired = false;

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
        parcel.writeInt(this.versionNumber);
        parcel.writeInt(this.securityLevel);
        parcel.writeString(this.keyMintName);
        parcel.writeString(this.keyMintAuthorName);
        parcel.writeBoolean(this.timestampTokenRequired);
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
                this.versionNumber = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.securityLevel = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.keyMintName = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.keyMintAuthorName = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.timestampTokenRequired = parcel.readBoolean();
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
