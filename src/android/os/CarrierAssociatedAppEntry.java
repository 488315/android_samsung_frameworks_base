package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class CarrierAssociatedAppEntry implements Parcelable {
    public static final Parcelable.Creator<CarrierAssociatedAppEntry> CREATOR = new Parcelable.Creator<CarrierAssociatedAppEntry>() { // from class: android.os.CarrierAssociatedAppEntry.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierAssociatedAppEntry createFromParcel(Parcel parcel) {
            return new CarrierAssociatedAppEntry(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CarrierAssociatedAppEntry[] newArray(int i) {
            return new CarrierAssociatedAppEntry[i];
        }
    };
    public static final int SDK_UNSPECIFIED = -1;
    public final int addedInSdk;
    public final String packageName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CarrierAssociatedAppEntry(String str, int i) {
        this.packageName = str;
        this.addedInSdk = i;
    }

    public CarrierAssociatedAppEntry(Parcel parcel) {
        this.packageName = parcel.readString();
        this.addedInSdk = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeInt(this.addedInSdk);
    }
}
