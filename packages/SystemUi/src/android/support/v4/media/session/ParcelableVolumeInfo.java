package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ParcelableVolumeInfo implements Parcelable {
    public static final Parcelable.Creator<ParcelableVolumeInfo> CREATOR = new Parcelable.Creator() { // from class: android.support.v4.media.session.ParcelableVolumeInfo.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new ParcelableVolumeInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ParcelableVolumeInfo[i];
        }
    };
    public final int audioStream;
    public final int controlType;
    public final int currentVolume;
    public final int maxVolume;
    public final int volumeType;

    public ParcelableVolumeInfo(int i, int i2, int i3, int i4, int i5) {
        this.volumeType = i;
        this.audioStream = i2;
        this.controlType = i3;
        this.maxVolume = i4;
        this.currentVolume = i5;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.volumeType);
        parcel.writeInt(this.controlType);
        parcel.writeInt(this.maxVolume);
        parcel.writeInt(this.currentVolume);
        parcel.writeInt(this.audioStream);
    }

    public ParcelableVolumeInfo(Parcel parcel) {
        this.volumeType = parcel.readInt();
        this.controlType = parcel.readInt();
        this.maxVolume = parcel.readInt();
        this.currentVolume = parcel.readInt();
        this.audioStream = parcel.readInt();
    }
}
