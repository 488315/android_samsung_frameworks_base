package android.net.wifi.sharedconnectivity.app;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArraySet;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.Set;

@SystemApi
/* loaded from: classes3.dex */
public final class KnownNetwork implements Parcelable {
    public static final Parcelable.Creator<KnownNetwork> CREATOR = new Parcelable.Creator<KnownNetwork>() { // from class: android.net.wifi.sharedconnectivity.app.KnownNetwork.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnownNetwork createFromParcel(Parcel parcel) {
            return KnownNetwork.readFromParcel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KnownNetwork[] newArray(int i) {
            return new KnownNetwork[i];
        }
    };
    public static final int NETWORK_SOURCE_CLOUD_SELF = 2;
    public static final int NETWORK_SOURCE_NEARBY_SELF = 1;
    public static final int NETWORK_SOURCE_UNKNOWN = 0;
    private final Bundle mExtras;
    private final NetworkProviderInfo mNetworkProviderInfo;
    private final int mNetworkSource;
    private final ArraySet<Integer> mSecurityTypes;
    private final String mSsid;

    @Retention(RetentionPolicy.SOURCE)
    public @interface NetworkSource {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private NetworkProviderInfo mNetworkProviderInfo;
        private String mSsid;
        private int mNetworkSource = -1;
        private final ArraySet<Integer> mSecurityTypes = new ArraySet<>();
        private Bundle mExtras = Bundle.EMPTY;

        public Builder setNetworkSource(int i) {
            this.mNetworkSource = i;
            return this;
        }

        public Builder setSsid(String str) {
            this.mSsid = str;
            return this;
        }

        public Builder addSecurityType(int i) {
            this.mSecurityTypes.add(Integer.valueOf(i));
            return this;
        }

        public Builder setNetworkProviderInfo(NetworkProviderInfo networkProviderInfo) {
            this.mNetworkProviderInfo = networkProviderInfo;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public KnownNetwork build() {
            return new KnownNetwork(this.mNetworkSource, this.mSsid, this.mSecurityTypes, this.mNetworkProviderInfo, this.mExtras);
        }
    }

    private static void validate(int i, String str, Set<Integer> set, NetworkProviderInfo networkProviderInfo) {
        if (i != 0 && i != 2 && i != 1) {
            throw new IllegalArgumentException("Illegal network source");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("SSID must be set");
        }
        if (set.isEmpty()) {
            throw new IllegalArgumentException("SecurityTypes must be set");
        }
        if (i == 1 && networkProviderInfo == null) {
            throw new IllegalArgumentException("Device info must be provided when network source is NETWORK_SOURCE_NEARBY_SELF");
        }
    }

    private KnownNetwork(int i, String str, ArraySet<Integer> arraySet, NetworkProviderInfo networkProviderInfo, Bundle bundle) {
        validate(i, str, arraySet, networkProviderInfo);
        this.mNetworkSource = i;
        this.mSsid = str;
        this.mSecurityTypes = new ArraySet<>((ArraySet) arraySet);
        this.mNetworkProviderInfo = networkProviderInfo;
        this.mExtras = bundle;
    }

    public int getNetworkSource() {
        return this.mNetworkSource;
    }

    public String getSsid() {
        return this.mSsid;
    }

    public Set<Integer> getSecurityTypes() {
        return this.mSecurityTypes;
    }

    public NetworkProviderInfo getNetworkProviderInfo() {
        return this.mNetworkProviderInfo;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof KnownNetwork)) {
            return false;
        }
        KnownNetwork knownNetwork = (KnownNetwork) obj;
        return this.mNetworkSource == knownNetwork.getNetworkSource() && Objects.equals(this.mSsid, knownNetwork.getSsid()) && Objects.equals(this.mSecurityTypes, knownNetwork.getSecurityTypes()) && Objects.equals(this.mNetworkProviderInfo, knownNetwork.getNetworkProviderInfo());
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mNetworkSource), this.mSsid, this.mSecurityTypes, this.mNetworkProviderInfo);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeInt(this.mNetworkSource);
        parcel.writeString(this.mSsid);
        parcel.writeArraySet(this.mSecurityTypes);
        if (this.mNetworkProviderInfo != null) {
            parcel.writeBoolean(true);
            this.mNetworkProviderInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeBoolean(false);
        }
        parcel.writeBundle(this.mExtras);
    }

    public static KnownNetwork readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        String string = parcel.readString();
        ArraySet<? extends Object> arraySet = parcel.readArraySet(null);
        if (parcel.readBoolean()) {
            return new KnownNetwork(i, string, arraySet, NetworkProviderInfo.readFromParcel(parcel), parcel.readBundle());
        }
        return new KnownNetwork(i, string, arraySet, null, parcel.readBundle());
    }

    public String toString() {
        return "KnownNetwork[NetworkSource=" + this.mNetworkSource + ", ssid=" + this.mSsid + ", securityTypes=" + this.mSecurityTypes.toString() + ", networkProviderInfo=" + this.mNetworkProviderInfo.toString() + ", extras=" + this.mExtras.toString() + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
