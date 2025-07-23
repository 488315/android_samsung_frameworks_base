package android.app;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ZenBypassingApp implements Parcelable {
    public static final Parcelable.Creator<ZenBypassingApp> CREATOR = new Parcelable.Creator<ZenBypassingApp>() { // from class: android.app.ZenBypassingApp.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenBypassingApp createFromParcel(Parcel parcel) {
            return new ZenBypassingApp(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ZenBypassingApp[] newArray(int i) {
            return new ZenBypassingApp[i];
        }
    };
    private boolean mAllChannelsBypass;
    private String mPkg;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ZenBypassingApp(String str, boolean z) {
        this.mPkg = str;
        this.mAllChannelsBypass = z;
    }

    public ZenBypassingApp(Parcel parcel) {
        this.mPkg = parcel.readString();
        this.mAllChannelsBypass = parcel.readBoolean();
    }

    public String getPkg() {
        return this.mPkg;
    }

    public boolean doAllChannelsBypass() {
        return this.mAllChannelsBypass;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mPkg);
        parcel.writeBoolean(this.mAllChannelsBypass);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ZenBypassingApp)) {
            return false;
        }
        ZenBypassingApp zenBypassingApp = (ZenBypassingApp) obj;
        return this.mAllChannelsBypass == zenBypassingApp.mAllChannelsBypass && Objects.equals(this.mPkg, zenBypassingApp.mPkg);
    }

    public int hashCode() {
        return Objects.hash(this.mPkg, Boolean.valueOf(this.mAllChannelsBypass));
    }

    public String toString() {
        return "ZenBypassingApp{mPkg='" + this.mPkg + "', mAllChannelsBypass=" + this.mAllChannelsBypass + '}';
    }
}
