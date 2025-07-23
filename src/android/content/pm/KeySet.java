package android.content.pm;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class KeySet implements Parcelable {
    public static final Parcelable.Creator<KeySet> CREATOR = new Parcelable.Creator<KeySet>() { // from class: android.content.pm.KeySet.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeySet createFromParcel(Parcel parcel) {
            return KeySet.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeySet[] newArray(int i) {
            return new KeySet[i];
        }
    };
    private final IBinder token;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeySet(IBinder iBinder) {
        if (iBinder == null) {
            throw new NullPointerException("null value for KeySet IBinder token");
        }
        this.token = iBinder;
    }

    public IBinder getToken() {
        return this.token;
    }

    public boolean equals(Object obj) {
        return (obj instanceof KeySet) && this.token == ((KeySet) obj).token;
    }

    public int hashCode() {
        return this.token.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static KeySet readFromParcel(Parcel parcel) {
        return new KeySet(parcel.readStrongBinder());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.token);
    }
}
