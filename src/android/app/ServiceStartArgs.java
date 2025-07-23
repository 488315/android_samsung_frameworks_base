package android.app;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class ServiceStartArgs implements Parcelable {
    public static final Parcelable.Creator<ServiceStartArgs> CREATOR = new Parcelable.Creator<ServiceStartArgs>() { // from class: android.app.ServiceStartArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceStartArgs createFromParcel(Parcel parcel) {
            return new ServiceStartArgs(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ServiceStartArgs[] newArray(int i) {
            return new ServiceStartArgs[i];
        }
    };
    public final Intent args;
    public final int flags;
    public final int startId;
    public final boolean taskRemoved;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ServiceStartArgs(boolean z, int i, int i2, Intent intent) {
        this.taskRemoved = z;
        this.startId = i;
        this.flags = i2;
        this.args = intent;
    }

    public String toString() {
        return "ServiceStartArgs{taskRemoved=" + this.taskRemoved + ", startId=" + this.startId + ", flags=0x" + Integer.toHexString(this.flags) + ", args=" + this.args + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.taskRemoved ? 1 : 0);
        parcel.writeInt(this.startId);
        parcel.writeInt(this.flags);
        if (this.args != null) {
            parcel.writeInt(1);
            this.args.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
    }

    public ServiceStartArgs(Parcel parcel) {
        this.taskRemoved = parcel.readInt() != 0;
        this.startId = parcel.readInt();
        this.flags = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.args = Intent.CREATOR.createFromParcel(parcel);
        } else {
            this.args = null;
        }
    }
}
