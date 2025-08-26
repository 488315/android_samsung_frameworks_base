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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.protocol);
        parcel.writeTypedObject(this.fec, i);
        parcel.writeInt(this.igmp);
        parcel.writeLong(this.bitrate);
        parcel.writeTypedObject(this.ipAddr, i);
        parcel.writeString(this.contentUrl);
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
                this.protocol = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.fec = (FrontendIptvSettingsFec) parcel.readTypedObject(FrontendIptvSettingsFec.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.igmp = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.bitrate = parcel.readLong();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.ipAddr = (DemuxIpAddress) parcel.readTypedObject(DemuxIpAddress.CREATOR);
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.contentUrl = parcel.readString();
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
