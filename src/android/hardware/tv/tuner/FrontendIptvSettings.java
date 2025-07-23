package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendIptvSettings implements Parcelable {
    public static final Parcelable.Creator<FrontendIptvSettings> CREATOR = new Parcelable.Creator<FrontendIptvSettings>() { // from class: android.hardware.tv.tuner.FrontendIptvSettings.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIptvSettings createFromParcel(Parcel parcel) {
            FrontendIptvSettings frontendIptvSettings = new FrontendIptvSettings();
            frontendIptvSettings.readFromParcel(parcel);
            return frontendIptvSettings;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendIptvSettings[] newArray(int i) {
            return new FrontendIptvSettings[i];
        }
    };
    public String contentUrl;
    public FrontendIptvSettingsFec fec;
    public DemuxIpAddress ipAddr;
    public int protocol = 0;
    public int igmp = 0;
    public long bitrate = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.protocol);
        parcel.writeTypedObject(this.fec, i);
        parcel.writeInt(this.igmp);
        parcel.writeLong(this.bitrate);
        parcel.writeTypedObject(this.ipAddr, i);
        parcel.writeString(this.contentUrl);
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
                this.protocol = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.fec = (FrontendIptvSettingsFec) parcel.readTypedObject(FrontendIptvSettingsFec.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.igmp = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.bitrate = parcel.readLong();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.ipAddr = (DemuxIpAddress) parcel.readTypedObject(DemuxIpAddress.CREATOR);
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.contentUrl = parcel.readString();
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.ipAddr) | describeContents(this.fec);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
