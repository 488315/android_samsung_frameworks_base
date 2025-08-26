package android.hardware.radio.messaging;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class GsmBroadcastSmsConfigInfo implements Parcelable {
    public static final Parcelable.Creator<GsmBroadcastSmsConfigInfo> CREATOR = new Parcelable.Creator<GsmBroadcastSmsConfigInfo>() { // from class: android.hardware.radio.messaging.GsmBroadcastSmsConfigInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GsmBroadcastSmsConfigInfo createFromParcel(Parcel parcel) {
            GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = new GsmBroadcastSmsConfigInfo();
            gsmBroadcastSmsConfigInfo.readFromParcel(parcel);
            return gsmBroadcastSmsConfigInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GsmBroadcastSmsConfigInfo[] newArray(int i) {
            return new GsmBroadcastSmsConfigInfo[i];
        }
    };
    public int fromServiceId = 0;
    public int toServiceId = 0;
    public int fromCodeScheme = 0;
    public int toCodeScheme = 0;
    public boolean selected = false;

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
        parcel.writeInt(this.fromServiceId);
        parcel.writeInt(this.toServiceId);
        parcel.writeInt(this.fromCodeScheme);
        parcel.writeInt(this.toCodeScheme);
        parcel.writeBoolean(this.selected);
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
                this.fromServiceId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.toServiceId = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.fromCodeScheme = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.toCodeScheme = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.selected = parcel.readBoolean();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("fromServiceId: " + this.fromServiceId);
        stringJoiner.add("toServiceId: " + this.toServiceId);
        stringJoiner.add("fromCodeScheme: " + this.fromCodeScheme);
        stringJoiner.add("toCodeScheme: " + this.toCodeScheme);
        stringJoiner.add("selected: " + this.selected);
        return "GsmBroadcastSmsConfigInfo" + stringJoiner.toString();
    }
}
