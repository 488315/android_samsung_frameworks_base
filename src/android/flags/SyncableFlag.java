package android.flags;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public final class SyncableFlag implements Parcelable {
    public static final Parcelable.Creator<SyncableFlag> CREATOR = new Parcelable.Creator<SyncableFlag>() { // from class: android.flags.SyncableFlag.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncableFlag createFromParcel(Parcel parcel) {
            return new SyncableFlag(parcel.readString(), parcel.readString(), parcel.readString(), parcel.readBoolean(), parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SyncableFlag[] newArray(int i) {
            return new SyncableFlag[i];
        }
    };
    private final boolean mDynamic;
    private final String mName;
    private final String mNamespace;
    private final boolean mOverridden;
    private final String mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SyncableFlag(String str, String str2, String str3, boolean z) {
        this(str, str2, str3, z, false);
    }

    public SyncableFlag(String str, String str2, String str3, boolean z, boolean z2) {
        this.mNamespace = str;
        this.mName = str2;
        this.mValue = str3;
        this.mDynamic = z;
        this.mOverridden = z2;
    }

    public String getNamespace() {
        return this.mNamespace;
    }

    public String getName() {
        return this.mName;
    }

    public String getValue() {
        return this.mValue;
    }

    public boolean isDynamic() {
        return this.mDynamic;
    }

    public boolean isOverridden() {
        return this.mOverridden;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mNamespace);
        parcel.writeString(this.mName);
        parcel.writeString(this.mValue);
        parcel.writeBoolean(this.mDynamic);
        parcel.writeBoolean(this.mOverridden);
    }

    public String toString() {
        return getNamespace() + MediaMetrics.SEPARATOR + getName() + NavigationBarInflaterView.SIZE_MOD_START + getValue() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
