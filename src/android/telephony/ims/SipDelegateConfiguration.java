package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class SipDelegateConfiguration implements Parcelable {
    public static final Parcelable.Creator<SipDelegateConfiguration> CREATOR = new Parcelable.Creator<SipDelegateConfiguration>() { // from class: android.telephony.ims.SipDelegateConfiguration.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipDelegateConfiguration createFromParcel(Parcel parcel) {
            return new SipDelegateConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SipDelegateConfiguration[] newArray(int i) {
            return new SipDelegateConfiguration[i];
        }
    };
    public static final int SIP_TRANSPORT_TCP = 1;
    public static final int SIP_TRANSPORT_UDP = 0;
    public static final int UDP_PAYLOAD_SIZE_UNDEFINED = -1;
    private String mAssociatedUriHeader;
    private String mCniHeader;
    private String mContactUserParam;
    private Uri mGruu;
    private String mHomeDomain;
    private String mImei;
    private IpSecConfiguration mIpSecConfiguration;
    private boolean mIsSipCompactFormEnabled;
    private boolean mIsSipKeepaliveEnabled;
    private final InetSocketAddress mLocalAddress;
    private int mMaxUdpPayloadSize;
    private InetSocketAddress mNatAddress;
    private String mPaniHeader;
    private String mPathHeader;
    private String mPlaniHeader;
    private String mPrivateUserIdentifier;
    private String mPublicUserIdentifier;
    private String mServiceRouteHeader;
    private String mSipAuthHeader;
    private String mSipAuthNonce;
    private final InetSocketAddress mSipServerAddress;
    private final int mTransportType;
    private String mUserAgentHeader;
    private final long mVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransportType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class IpSecConfiguration {
        private final int mLastLocalTxPort;
        private final int mLastRemoteTxPort;
        private final int mLocalRxPort;
        private final int mLocalTxPort;
        private final int mRemoteRxPort;
        private final int mRemoteTxPort;
        private final String mSecurityHeader;

        public IpSecConfiguration(int i, int i2, int i3, int i4, int i5, int i6, String str) {
            this.mLocalTxPort = i;
            this.mLocalRxPort = i2;
            this.mLastLocalTxPort = i3;
            this.mRemoteTxPort = i4;
            this.mRemoteRxPort = i5;
            this.mLastRemoteTxPort = i6;
            this.mSecurityHeader = str;
        }

        public int getLocalTxPort() {
            return this.mLocalTxPort;
        }

        public int getLocalRxPort() {
            return this.mLocalRxPort;
        }

        public int getLastLocalTxPort() {
            return this.mLastLocalTxPort;
        }

        public int getRemoteTxPort() {
            return this.mRemoteTxPort;
        }

        public int getRemoteRxPort() {
            return this.mRemoteRxPort;
        }

        public int getLastRemoteTxPort() {
            return this.mLastRemoteTxPort;
        }

        public String getSipSecurityVerifyHeader() {
            return this.mSecurityHeader;
        }

        public void addToParcel(Parcel parcel) {
            parcel.writeInt(this.mLocalTxPort);
            parcel.writeInt(this.mLocalRxPort);
            parcel.writeInt(this.mLastLocalTxPort);
            parcel.writeInt(this.mRemoteTxPort);
            parcel.writeInt(this.mRemoteRxPort);
            parcel.writeInt(this.mLastRemoteTxPort);
            parcel.writeString(this.mSecurityHeader);
        }

        public static IpSecConfiguration fromParcel(Parcel parcel) {
            return new IpSecConfiguration(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString());
        }

        public String toString() {
            return "IpSecConfiguration{localTx=" + this.mLocalTxPort + ", localRx=" + this.mLocalRxPort + ", lastLocalTx=" + this.mLastLocalTxPort + ", remoteTx=" + this.mRemoteTxPort + ", remoteRx=" + this.mRemoteRxPort + ", lastRemoteTx=" + this.mLastRemoteTxPort + ", securityHeader=" + this.mSecurityHeader + '}';
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                IpSecConfiguration ipSecConfiguration = (IpSecConfiguration) obj;
                if (this.mLocalTxPort == ipSecConfiguration.mLocalTxPort && this.mLocalRxPort == ipSecConfiguration.mLocalRxPort && this.mLastLocalTxPort == ipSecConfiguration.mLastLocalTxPort && this.mRemoteTxPort == ipSecConfiguration.mRemoteTxPort && this.mRemoteRxPort == ipSecConfiguration.mRemoteRxPort && this.mLastRemoteTxPort == ipSecConfiguration.mLastRemoteTxPort && Objects.equals(this.mSecurityHeader, ipSecConfiguration.mSecurityHeader)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mLocalTxPort), Integer.valueOf(this.mLocalRxPort), Integer.valueOf(this.mLastLocalTxPort), Integer.valueOf(this.mRemoteTxPort), Integer.valueOf(this.mRemoteRxPort), Integer.valueOf(this.mLastRemoteTxPort), this.mSecurityHeader);
        }
    }

    public static final class Builder {
        private final SipDelegateConfiguration mConfig;

        public Builder(long j, int i, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
            this.mConfig = new SipDelegateConfiguration(j, i, inetSocketAddress, inetSocketAddress2);
        }

        public Builder(SipDelegateConfiguration sipDelegateConfiguration) {
            this.mConfig = sipDelegateConfiguration.copyAndIncrementVersion();
        }

        public Builder setSipCompactFormEnabled(boolean z) {
            this.mConfig.mIsSipCompactFormEnabled = z;
            return this;
        }

        public Builder setSipKeepaliveEnabled(boolean z) {
            this.mConfig.mIsSipKeepaliveEnabled = z;
            return this;
        }

        public Builder setMaxUdpPayloadSizeBytes(int i) {
            this.mConfig.mMaxUdpPayloadSize = i;
            return this;
        }

        public Builder setPublicUserIdentifier(String str) {
            this.mConfig.mPublicUserIdentifier = str;
            return this;
        }

        public Builder setPrivateUserIdentifier(String str) {
            this.mConfig.mPrivateUserIdentifier = str;
            return this;
        }

        public Builder setHomeDomain(String str) {
            this.mConfig.mHomeDomain = str;
            return this;
        }

        public Builder setImei(String str) {
            this.mConfig.mImei = str;
            return this;
        }

        public Builder setIpSecConfiguration(IpSecConfiguration ipSecConfiguration) {
            this.mConfig.mIpSecConfiguration = ipSecConfiguration;
            return this;
        }

        public Builder setNatSocketAddress(InetSocketAddress inetSocketAddress) {
            this.mConfig.mNatAddress = inetSocketAddress;
            return this;
        }

        public Builder setPublicGruuUri(Uri uri) {
            this.mConfig.mGruu = uri;
            return this;
        }

        public Builder setSipAuthenticationHeader(String str) {
            this.mConfig.mSipAuthHeader = str;
            return this;
        }

        public Builder setSipAuthenticationNonce(String str) {
            this.mConfig.mSipAuthNonce = str;
            return this;
        }

        public Builder setSipServiceRouteHeader(String str) {
            this.mConfig.mServiceRouteHeader = str;
            return this;
        }

        public Builder setSipPathHeader(String str) {
            this.mConfig.mPathHeader = str;
            return this;
        }

        public Builder setSipUserAgentHeader(String str) {
            this.mConfig.mUserAgentHeader = str;
            return this;
        }

        public Builder setSipContactUserParameter(String str) {
            this.mConfig.mContactUserParam = str;
            return this;
        }

        public Builder setSipPaniHeader(String str) {
            this.mConfig.mPaniHeader = str;
            return this;
        }

        public Builder setSipPlaniHeader(String str) {
            this.mConfig.mPlaniHeader = str;
            return this;
        }

        public Builder setSipCniHeader(String str) {
            this.mConfig.mCniHeader = str;
            return this;
        }

        public Builder setSipAssociatedUriHeader(String str) {
            this.mConfig.mAssociatedUriHeader = str;
            return this;
        }

        public SipDelegateConfiguration build() {
            return this.mConfig;
        }
    }

    private SipDelegateConfiguration(long j, int i, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2) {
        this.mIsSipCompactFormEnabled = false;
        this.mIsSipKeepaliveEnabled = false;
        this.mMaxUdpPayloadSize = -1;
        this.mPublicUserIdentifier = null;
        this.mPrivateUserIdentifier = null;
        this.mHomeDomain = null;
        this.mImei = null;
        this.mGruu = null;
        this.mSipAuthHeader = null;
        this.mSipAuthNonce = null;
        this.mServiceRouteHeader = null;
        this.mPathHeader = null;
        this.mUserAgentHeader = null;
        this.mContactUserParam = null;
        this.mPaniHeader = null;
        this.mPlaniHeader = null;
        this.mCniHeader = null;
        this.mAssociatedUriHeader = null;
        this.mIpSecConfiguration = null;
        this.mNatAddress = null;
        this.mVersion = j;
        this.mTransportType = i;
        this.mLocalAddress = inetSocketAddress;
        this.mSipServerAddress = inetSocketAddress2;
    }

    private SipDelegateConfiguration(Parcel parcel) {
        this.mIsSipCompactFormEnabled = false;
        this.mIsSipKeepaliveEnabled = false;
        this.mMaxUdpPayloadSize = -1;
        this.mPublicUserIdentifier = null;
        this.mPrivateUserIdentifier = null;
        this.mHomeDomain = null;
        this.mImei = null;
        this.mGruu = null;
        this.mSipAuthHeader = null;
        this.mSipAuthNonce = null;
        this.mServiceRouteHeader = null;
        this.mPathHeader = null;
        this.mUserAgentHeader = null;
        this.mContactUserParam = null;
        this.mPaniHeader = null;
        this.mPlaniHeader = null;
        this.mCniHeader = null;
        this.mAssociatedUriHeader = null;
        this.mIpSecConfiguration = null;
        this.mNatAddress = null;
        this.mVersion = parcel.readLong();
        this.mTransportType = parcel.readInt();
        this.mLocalAddress = readAddressFromParcel(parcel);
        this.mSipServerAddress = readAddressFromParcel(parcel);
        this.mIsSipCompactFormEnabled = parcel.readBoolean();
        this.mIsSipKeepaliveEnabled = parcel.readBoolean();
        this.mMaxUdpPayloadSize = parcel.readInt();
        this.mPublicUserIdentifier = parcel.readString();
        this.mPrivateUserIdentifier = parcel.readString();
        this.mHomeDomain = parcel.readString();
        this.mImei = parcel.readString();
        this.mGruu = (Uri) parcel.readParcelable(null, Uri.class);
        this.mSipAuthHeader = parcel.readString();
        this.mSipAuthNonce = parcel.readString();
        this.mServiceRouteHeader = parcel.readString();
        this.mPathHeader = parcel.readString();
        this.mUserAgentHeader = parcel.readString();
        this.mContactUserParam = parcel.readString();
        this.mPaniHeader = parcel.readString();
        this.mPlaniHeader = parcel.readString();
        this.mCniHeader = parcel.readString();
        this.mAssociatedUriHeader = parcel.readString();
        if (parcel.readBoolean()) {
            this.mIpSecConfiguration = IpSecConfiguration.fromParcel(parcel);
        }
        if (parcel.readBoolean()) {
            this.mNatAddress = readAddressFromParcel(parcel);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mVersion);
        parcel.writeInt(this.mTransportType);
        writeAddressToParcel(this.mLocalAddress, parcel);
        writeAddressToParcel(this.mSipServerAddress, parcel);
        parcel.writeBoolean(this.mIsSipCompactFormEnabled);
        parcel.writeBoolean(this.mIsSipKeepaliveEnabled);
        parcel.writeInt(this.mMaxUdpPayloadSize);
        parcel.writeString(this.mPublicUserIdentifier);
        parcel.writeString(this.mPrivateUserIdentifier);
        parcel.writeString(this.mHomeDomain);
        parcel.writeString(this.mImei);
        parcel.writeParcelable(this.mGruu, i);
        parcel.writeString(this.mSipAuthHeader);
        parcel.writeString(this.mSipAuthNonce);
        parcel.writeString(this.mServiceRouteHeader);
        parcel.writeString(this.mPathHeader);
        parcel.writeString(this.mUserAgentHeader);
        parcel.writeString(this.mContactUserParam);
        parcel.writeString(this.mPaniHeader);
        parcel.writeString(this.mPlaniHeader);
        parcel.writeString(this.mCniHeader);
        parcel.writeString(this.mAssociatedUriHeader);
        parcel.writeBoolean(this.mIpSecConfiguration != null);
        IpSecConfiguration ipSecConfiguration = this.mIpSecConfiguration;
        if (ipSecConfiguration != null) {
            ipSecConfiguration.addToParcel(parcel);
        }
        parcel.writeBoolean(this.mNatAddress != null);
        InetSocketAddress inetSocketAddress = this.mNatAddress;
        if (inetSocketAddress != null) {
            writeAddressToParcel(inetSocketAddress, parcel);
        }
    }

    public SipDelegateConfiguration copyAndIncrementVersion() {
        SipDelegateConfiguration sipDelegateConfiguration = new SipDelegateConfiguration(getVersion() + 1, this.mTransportType, this.mLocalAddress, this.mSipServerAddress);
        sipDelegateConfiguration.mIsSipCompactFormEnabled = this.mIsSipCompactFormEnabled;
        sipDelegateConfiguration.mIsSipKeepaliveEnabled = this.mIsSipKeepaliveEnabled;
        sipDelegateConfiguration.mMaxUdpPayloadSize = this.mMaxUdpPayloadSize;
        sipDelegateConfiguration.mIpSecConfiguration = this.mIpSecConfiguration;
        sipDelegateConfiguration.mNatAddress = this.mNatAddress;
        sipDelegateConfiguration.mPublicUserIdentifier = this.mPublicUserIdentifier;
        sipDelegateConfiguration.mPrivateUserIdentifier = this.mPrivateUserIdentifier;
        sipDelegateConfiguration.mHomeDomain = this.mHomeDomain;
        sipDelegateConfiguration.mImei = this.mImei;
        sipDelegateConfiguration.mGruu = this.mGruu;
        sipDelegateConfiguration.mSipAuthHeader = this.mSipAuthHeader;
        sipDelegateConfiguration.mSipAuthNonce = this.mSipAuthNonce;
        sipDelegateConfiguration.mServiceRouteHeader = this.mServiceRouteHeader;
        sipDelegateConfiguration.mPathHeader = this.mPathHeader;
        sipDelegateConfiguration.mUserAgentHeader = this.mUserAgentHeader;
        sipDelegateConfiguration.mContactUserParam = this.mContactUserParam;
        sipDelegateConfiguration.mPaniHeader = this.mPaniHeader;
        sipDelegateConfiguration.mPlaniHeader = this.mPlaniHeader;
        sipDelegateConfiguration.mCniHeader = this.mCniHeader;
        sipDelegateConfiguration.mAssociatedUriHeader = this.mAssociatedUriHeader;
        return sipDelegateConfiguration;
    }

    public long getVersion() {
        return this.mVersion;
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public InetSocketAddress getLocalAddress() {
        return this.mLocalAddress;
    }

    public InetSocketAddress getSipServerAddress() {
        return this.mSipServerAddress;
    }

    public boolean isSipCompactFormEnabled() {
        return this.mIsSipCompactFormEnabled;
    }

    public boolean isSipKeepaliveEnabled() {
        return this.mIsSipKeepaliveEnabled;
    }

    public int getMaxUdpPayloadSizeBytes() {
        return this.mMaxUdpPayloadSize;
    }

    public String getPublicUserIdentifier() {
        return this.mPublicUserIdentifier;
    }

    public String getPrivateUserIdentifier() {
        return this.mPrivateUserIdentifier;
    }

    public String getHomeDomain() {
        return this.mHomeDomain;
    }

    public String getImei() {
        return this.mImei;
    }

    public IpSecConfiguration getIpSecConfiguration() {
        return this.mIpSecConfiguration;
    }

    public InetSocketAddress getNatSocketAddress() {
        return this.mNatAddress;
    }

    public Uri getPublicGruuUri() {
        return this.mGruu;
    }

    public String getSipAuthenticationHeader() {
        return this.mSipAuthHeader;
    }

    public String getSipAuthenticationNonce() {
        return this.mSipAuthNonce;
    }

    public String getSipServiceRouteHeader() {
        return this.mServiceRouteHeader;
    }

    public String getSipPathHeader() {
        return this.mPathHeader;
    }

    public String getSipUserAgentHeader() {
        return this.mUserAgentHeader;
    }

    public String getSipContactUserParameter() {
        return this.mContactUserParam;
    }

    public String getSipPaniHeader() {
        return this.mPaniHeader;
    }

    public String getSipPlaniHeader() {
        return this.mPlaniHeader;
    }

    public String getSipCniHeader() {
        return this.mCniHeader;
    }

    public String getSipAssociatedUriHeader() {
        return this.mAssociatedUriHeader;
    }

    private void writeAddressToParcel(InetSocketAddress inetSocketAddress, Parcel parcel) {
        parcel.writeByteArray(inetSocketAddress.getAddress().getAddress());
        parcel.writeInt(inetSocketAddress.getPort());
    }

    private InetSocketAddress readAddressFromParcel(Parcel parcel) {
        byte[] bArrCreateByteArray = parcel.createByteArray();
        try {
            return new InetSocketAddress(InetAddress.getByAddress(bArrCreateByteArray), parcel.readInt());
        } catch (UnknownHostException unused) {
            Log.e("SipDelegateConfiguration", "exception reading address, returning null");
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            SipDelegateConfiguration sipDelegateConfiguration = (SipDelegateConfiguration) obj;
            if (this.mVersion == sipDelegateConfiguration.mVersion && this.mTransportType == sipDelegateConfiguration.mTransportType && this.mIsSipCompactFormEnabled == sipDelegateConfiguration.mIsSipCompactFormEnabled && this.mIsSipKeepaliveEnabled == sipDelegateConfiguration.mIsSipKeepaliveEnabled && this.mMaxUdpPayloadSize == sipDelegateConfiguration.mMaxUdpPayloadSize && Objects.equals(this.mLocalAddress, sipDelegateConfiguration.mLocalAddress) && Objects.equals(this.mSipServerAddress, sipDelegateConfiguration.mSipServerAddress) && Objects.equals(this.mPublicUserIdentifier, sipDelegateConfiguration.mPublicUserIdentifier) && Objects.equals(this.mPrivateUserIdentifier, sipDelegateConfiguration.mPrivateUserIdentifier) && Objects.equals(this.mHomeDomain, sipDelegateConfiguration.mHomeDomain) && Objects.equals(this.mImei, sipDelegateConfiguration.mImei) && Objects.equals(this.mGruu, sipDelegateConfiguration.mGruu) && Objects.equals(this.mSipAuthHeader, sipDelegateConfiguration.mSipAuthHeader) && Objects.equals(this.mSipAuthNonce, sipDelegateConfiguration.mSipAuthNonce) && Objects.equals(this.mServiceRouteHeader, sipDelegateConfiguration.mServiceRouteHeader) && Objects.equals(this.mPathHeader, sipDelegateConfiguration.mPathHeader) && Objects.equals(this.mUserAgentHeader, sipDelegateConfiguration.mUserAgentHeader) && Objects.equals(this.mContactUserParam, sipDelegateConfiguration.mContactUserParam) && Objects.equals(this.mPaniHeader, sipDelegateConfiguration.mPaniHeader) && Objects.equals(this.mPlaniHeader, sipDelegateConfiguration.mPlaniHeader) && Objects.equals(this.mCniHeader, sipDelegateConfiguration.mCniHeader) && Objects.equals(this.mAssociatedUriHeader, sipDelegateConfiguration.mAssociatedUriHeader) && Objects.equals(this.mIpSecConfiguration, sipDelegateConfiguration.mIpSecConfiguration) && Objects.equals(this.mNatAddress, sipDelegateConfiguration.mNatAddress)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.mVersion), Integer.valueOf(this.mTransportType), this.mLocalAddress, this.mSipServerAddress, Boolean.valueOf(this.mIsSipCompactFormEnabled), Boolean.valueOf(this.mIsSipKeepaliveEnabled), Integer.valueOf(this.mMaxUdpPayloadSize), this.mPublicUserIdentifier, this.mPrivateUserIdentifier, this.mHomeDomain, this.mImei, this.mGruu, this.mSipAuthHeader, this.mSipAuthNonce, this.mServiceRouteHeader, this.mPathHeader, this.mUserAgentHeader, this.mContactUserParam, this.mPaniHeader, this.mPlaniHeader, this.mCniHeader, this.mAssociatedUriHeader, this.mIpSecConfiguration, this.mNatAddress);
    }

    public String toString() {
        return "SipDelegateConfiguration{ mVersion=" + this.mVersion + ", mTransportType=" + this.mTransportType + '}';
    }
}
