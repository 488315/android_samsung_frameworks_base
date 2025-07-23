package android.window;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.window.IWindowContainerToken;

/* loaded from: classes5.dex */
public final class WindowContainerToken implements Parcelable {
    public static final Parcelable.Creator<WindowContainerToken> CREATOR = new Parcelable.Creator<WindowContainerToken>() { // from class: android.window.WindowContainerToken.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContainerToken createFromParcel(Parcel parcel) {
            return new WindowContainerToken(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowContainerToken[] newArray(int i) {
            return new WindowContainerToken[i];
        }
    };
    private final IWindowContainerToken mRealToken;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WindowContainerToken(IWindowContainerToken iWindowContainerToken) {
        this.mRealToken = iWindowContainerToken;
    }

    private WindowContainerToken(Parcel parcel) {
        this.mRealToken = IWindowContainerToken.Stub.asInterface(parcel.readStrongBinder());
    }

    public IBinder asBinder() {
        return this.mRealToken.asBinder();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mRealToken.asBinder());
    }

    public int hashCode() {
        return this.mRealToken.asBinder().hashCode();
    }

    public String toString() {
        return "WCT{" + this.mRealToken + "}";
    }

    public boolean equals(Object obj) {
        return (obj instanceof WindowContainerToken) && this.mRealToken.asBinder() == ((WindowContainerToken) obj).asBinder();
    }
}
