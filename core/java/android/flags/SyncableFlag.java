package android.flags;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.Parcel;
import android.os.Parcelable;

public final class SyncableFlag implements Parcelable {
    public static final Parcelable.Creator<SyncableFlag> CREATOR =
            new Parcelable.Creator<SyncableFlag>() { // from class: android.flags.SyncableFlag.1
                @Override // android.os.Parcelable.Creator
                public SyncableFlag createFromParcel(Parcel in) {
                    return new SyncableFlag(
                            in.readString(),
                            in.readString(),
                            in.readString(),
                            in.readBoolean(),
                            in.readBoolean());
                }

                @Override // android.os.Parcelable.Creator
                public SyncableFlag[] newArray(int size) {
                    return new SyncableFlag[size];
                }
            };
    private final boolean mDynamic;
    private final String mName;
    private final String mNamespace;
    private final boolean mOverridden;
    private final String mValue;

    public SyncableFlag(String namespace, String name, String value, boolean dynamic) {
        this(namespace, name, value, dynamic, false);
    }

    public SyncableFlag(
            String namespace, String name, String value, boolean dynamic, boolean overridden) {
        this.mNamespace = namespace;
        this.mName = name;
        this.mValue = value;
        this.mDynamic = dynamic;
        this.mOverridden = overridden;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mNamespace);
        dest.writeString(this.mName);
        dest.writeString(this.mValue);
        dest.writeBoolean(this.mDynamic);
        dest.writeBoolean(this.mOverridden);
    }

    public String toString() {
        return getNamespace()
                + MediaMetrics.SEPARATOR
                + getName()
                + NavigationBarInflaterView.SIZE_MOD_START
                + getValue()
                + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
