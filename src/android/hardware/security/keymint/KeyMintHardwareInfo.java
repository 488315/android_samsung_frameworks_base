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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.versionNumber);
        parcel.writeInt(this.securityLevel);
        parcel.writeString(this.keyMintName);
        parcel.writeString(this.keyMintAuthorName);
        parcel.writeBoolean(this.timestampTokenRequired);
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
                this.versionNumber = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.securityLevel = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.keyMintName = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.keyMintAuthorName = parcel.readString();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.timestampTokenRequired = parcel.readBoolean();
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
