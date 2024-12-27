package android.net;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.DabTableEntry$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class NetworkTestResultParcelable implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public String redirectUrl;
    public long timestampMillis = 0;
    public int result = 0;
    public int probesSucceeded = 0;
    public int probesAttempted = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.net.NetworkTestResultParcelable$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            NetworkTestResultParcelable networkTestResultParcelable = new NetworkTestResultParcelable();
            networkTestResultParcelable.readFromParcel(parcel);
            return networkTestResultParcelable;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NetworkTestResultParcelable[i];
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.timestampMillis = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.result = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.probesSucceeded = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.probesAttempted = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.redirectUrl = parcel.readString();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                                parcel.setDataPosition(dataPosition + readInt);
                                return;
                            }
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("timestampMillis: " + this.timestampMillis);
        return AmFmBandRange$$ExternalSyntheticOutline0.m(stringJoiner, DabTableEntry$$ExternalSyntheticOutline0.m(this.redirectUrl, "NetworkTestResultParcelable", AmFmBandRange$$ExternalSyntheticOutline0.m(AmFmBandRange$$ExternalSyntheticOutline0.m(AmFmBandRange$$ExternalSyntheticOutline0.m(new StringBuilder("result: "), this.result, stringJoiner, "probesSucceeded: "), this.probesSucceeded, stringJoiner, "probesAttempted: "), this.probesAttempted, stringJoiner, "redirectUrl: "), stringJoiner));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.timestampMillis);
        parcel.writeInt(this.result);
        parcel.writeInt(this.probesSucceeded);
        parcel.writeInt(this.probesAttempted);
        parcel.writeString(this.redirectUrl);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(dataPosition2, dataPosition, parcel, dataPosition2);
    }
}
