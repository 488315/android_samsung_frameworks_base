package android.hardware.location;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaMetrics;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spanned;

@SystemApi
/* loaded from: classes2.dex */
public final class NanoAppRpcService implements Parcelable {
    public static final Parcelable.Creator<NanoAppRpcService> CREATOR = new Parcelable.Creator<NanoAppRpcService>() { // from class: android.hardware.location.NanoAppRpcService.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppRpcService createFromParcel(Parcel parcel) {
            return new NanoAppRpcService(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NanoAppRpcService[] newArray(int i) {
            return new NanoAppRpcService[i];
        }
    };
    private long mServiceId;
    private int mServiceVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NanoAppRpcService(long j, int i) {
        this.mServiceId = j;
        this.mServiceVersion = i;
    }

    public long getId() {
        return this.mServiceId;
    }

    public int getVersion() {
        return this.mServiceVersion;
    }

    private int getMajorVersion() {
        return (this.mServiceVersion & (-16777216)) >>> 24;
    }

    private int getMinorVersion() {
        return (this.mServiceVersion & Spanned.SPAN_PRIORITY) >>> 16;
    }

    private int getPatchVersion() {
        return this.mServiceVersion & 65535;
    }

    private NanoAppRpcService(Parcel parcel) {
        this.mServiceId = parcel.readLong();
        this.mServiceVersion = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mServiceId);
        parcel.writeInt(this.mServiceVersion);
    }

    public String toString() {
        return "NanoAppRpcService[Id = " + Long.toHexString(this.mServiceId) + ", version = v" + getMajorVersion() + MediaMetrics.SEPARATOR + getMinorVersion() + MediaMetrics.SEPARATOR + getPatchVersion() + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof NanoAppRpcService) {
            NanoAppRpcService nanoAppRpcService = (NanoAppRpcService) obj;
            if (nanoAppRpcService.getId() == this.mServiceId && nanoAppRpcService.getVersion() == this.mServiceVersion) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (int) getId();
    }
}
