package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes.dex */
public final class ChangedPackages implements Parcelable {
    public static final Parcelable.Creator<ChangedPackages> CREATOR = new Parcelable.Creator<ChangedPackages>() { // from class: android.content.pm.ChangedPackages.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChangedPackages createFromParcel(Parcel parcel) {
            return new ChangedPackages(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ChangedPackages[] newArray(int i) {
            return new ChangedPackages[i];
        }
    };
    private final List<String> mPackageNames;
    private final int mSequenceNumber;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ChangedPackages(int i, List<String> list) {
        this.mSequenceNumber = i;
        this.mPackageNames = list;
    }

    protected ChangedPackages(Parcel parcel) {
        this.mSequenceNumber = parcel.readInt();
        this.mPackageNames = parcel.createStringArrayList();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSequenceNumber);
        parcel.writeStringList(this.mPackageNames);
    }

    public int getSequenceNumber() {
        return this.mSequenceNumber;
    }

    public List<String> getPackageNames() {
        return this.mPackageNames;
    }
}
