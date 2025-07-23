package android.net;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ProxyInfoWrapper implements Parcelable {
    public static final Parcelable.Creator<ProxyInfoWrapper> CREATOR = new Parcelable.Creator<ProxyInfoWrapper>() { // from class: android.net.ProxyInfoWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProxyInfoWrapper createFromParcel(Parcel parcel) {
            return new ProxyInfoWrapper(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProxyInfoWrapper[] newArray(int i) {
            return new ProxyInfoWrapper[i];
        }
    };
    private ProxyInfo mProxyInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ProxyInfoWrapper(ProxyInfo proxyInfo) {
        this.mProxyInfo = proxyInfo;
    }

    public ProxyInfo getProxyInfo() {
        return this.mProxyInfo;
    }

    private ProxyInfoWrapper(Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.mProxyInfo = (ProxyInfo) ProxyInfo.CREATOR.createFromParcel(parcel);
        } else {
            this.mProxyInfo = null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        if (this.mProxyInfo != null) {
            parcel.writeInt(1);
            this.mProxyInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }
}
