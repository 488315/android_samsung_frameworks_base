package android.hardware.tv.tuner;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrontendScanAtsc3PlpInfo implements Parcelable {
    public static final Parcelable.Creator<FrontendScanAtsc3PlpInfo> CREATOR = new Parcelable.Creator<FrontendScanAtsc3PlpInfo>() { // from class: android.hardware.tv.tuner.FrontendScanAtsc3PlpInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanAtsc3PlpInfo createFromParcel(Parcel parcel) {
            FrontendScanAtsc3PlpInfo frontendScanAtsc3PlpInfo = new FrontendScanAtsc3PlpInfo();
            frontendScanAtsc3PlpInfo.readFromParcel(parcel);
            return frontendScanAtsc3PlpInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrontendScanAtsc3PlpInfo[] newArray(int i) {
            return new FrontendScanAtsc3PlpInfo[i];
        }
    };
    public int plpId = 0;
    public boolean bLlsFlag = false;

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
        parcel.writeInt(this.plpId);
        parcel.writeBoolean(this.bLlsFlag);
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
                this.plpId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.bLlsFlag = parcel.readBoolean();
                    if (iDataPosition > Integer.MAX_VALUE - i) {
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
