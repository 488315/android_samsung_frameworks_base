package android.net;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ProxyInfoWrapper implements Parcelable {
    public static final Parcelable.Creator<ProxyInfoWrapper> CREATOR =
            new Parcelable.Creator<
                    ProxyInfoWrapper>() { // from class: android.net.ProxyInfoWrapper.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ProxyInfoWrapper createFromParcel(Parcel in) {
                    return new ProxyInfoWrapper(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ProxyInfoWrapper[] newArray(int size) {
                    return new ProxyInfoWrapper[size];
                }
            };
    private ProxyInfo mProxyInfo;

    public ProxyInfoWrapper(ProxyInfo proxyInfo) {
        this.mProxyInfo = proxyInfo;
    }

    public ProxyInfo getProxyInfo() {
        return this.mProxyInfo;
    }

    private ProxyInfoWrapper(Parcel in) {
        if (in.readInt() != 0) {
            this.mProxyInfo = (ProxyInfo) ProxyInfo.CREATOR.createFromParcel(in);
        } else {
            this.mProxyInfo = null;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        if (this.mProxyInfo != null) {
            dest.writeInt(1);
            this.mProxyInfo.writeToParcel(dest, flags);
        } else {
            dest.writeInt(0);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
