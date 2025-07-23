package android.telecom;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class ParcelableRttCall implements Parcelable {
    public static final Parcelable.Creator<ParcelableRttCall> CREATOR = new Parcelable.Creator<ParcelableRttCall>() { // from class: android.telecom.ParcelableRttCall.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableRttCall createFromParcel(Parcel parcel) {
            return new ParcelableRttCall(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableRttCall[] newArray(int i) {
            return new ParcelableRttCall[i];
        }
    };
    private final ParcelFileDescriptor mReceiveStream;
    private final int mRttMode;
    private final ParcelFileDescriptor mTransmitStream;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelableRttCall(int i, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2) {
        this.mRttMode = i;
        this.mTransmitStream = parcelFileDescriptor;
        this.mReceiveStream = parcelFileDescriptor2;
    }

    protected ParcelableRttCall(Parcel parcel) {
        this.mRttMode = parcel.readInt();
        this.mTransmitStream = (ParcelFileDescriptor) parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader(), ParcelFileDescriptor.class);
        this.mReceiveStream = (ParcelFileDescriptor) parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader(), ParcelFileDescriptor.class);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRttMode);
        parcel.writeParcelable(this.mTransmitStream, i);
        parcel.writeParcelable(this.mReceiveStream, i);
    }

    public int getRttMode() {
        return this.mRttMode;
    }

    public ParcelFileDescriptor getReceiveStream() {
        return this.mReceiveStream;
    }

    public ParcelFileDescriptor getTransmitStream() {
        return this.mTransmitStream;
    }
}
