package android.telephony.gba;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.Objects;

@SystemApi
/* loaded from: classes4.dex */
public final class UaSecurityProtocolIdentifier implements Parcelable {
    public static final int ORG_3GPP = 1;
    public static final int ORG_3GPP2 = 2;
    public static final int ORG_GSMA = 4;
    public static final int ORG_LOCAL = 255;
    public static final int ORG_NONE = 0;
    public static final int ORG_OMA = 3;
    private static final int PROTOCOL_SIZE = 5;
    public static final int UA_SECURITY_PROTOCOL_3GPP_GENERATION_TMPI = 256;
    public static final int UA_SECURITY_PROTOCOL_3GPP_GENERIC_PUSH_LAYER = 5;
    public static final int UA_SECURITY_PROTOCOL_3GPP_HTTP_BASED_MBMS = 3;
    public static final int UA_SECURITY_PROTOCOL_3GPP_HTTP_DIGEST_AUTHENTICATION = 2;
    public static final int UA_SECURITY_PROTOCOL_3GPP_IMS_MEDIA_PLANE = 6;
    public static final int UA_SECURITY_PROTOCOL_3GPP_MBMS = 1;
    public static final int UA_SECURITY_PROTOCOL_3GPP_SIP_BASED_MBMS = 4;
    public static final int UA_SECURITY_PROTOCOL_3GPP_SUBSCRIBER_CERTIFICATE = 0;
    public static final int UA_SECURITY_PROTOCOL_3GPP_TLS_BROWSER = 131072;
    public static final int UA_SECURITY_PROTOCOL_3GPP_TLS_DEFAULT = 65536;
    private int mOrg;
    private int mProtocol;
    private int mTlsCipherSuite;
    private static final int[] sUaSp3gppIds = {0, 1, 2, 3, 4, 5, 6, 256, 65536, 131072};
    public static final Parcelable.Creator<UaSecurityProtocolIdentifier> CREATOR = new Parcelable.Creator<UaSecurityProtocolIdentifier>() { // from class: android.telephony.gba.UaSecurityProtocolIdentifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UaSecurityProtocolIdentifier createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            int readInt3 = parcel.readInt();
            if (readInt < 0 || readInt2 < 0 || readInt3 < 0) {
                return null;
            }
            Builder builder = new Builder();
            if (readInt > 0) {
                try {
                    builder.setOrg(readInt);
                } catch (IllegalArgumentException unused) {
                    return null;
                }
            }
            if (readInt2 > 0) {
                builder.setProtocol(readInt2);
            }
            if (readInt3 > 0) {
                builder.setTlsCipherSuite(readInt3);
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UaSecurityProtocolIdentifier[] newArray(int i) {
            return new UaSecurityProtocolIdentifier[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface OrganizationCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface UaSecurityProtocol3gpp {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private UaSecurityProtocolIdentifier() {
    }

    private UaSecurityProtocolIdentifier(UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier) {
        this.mOrg = uaSecurityProtocolIdentifier.mOrg;
        this.mProtocol = uaSecurityProtocolIdentifier.mProtocol;
        this.mTlsCipherSuite = uaSecurityProtocolIdentifier.mTlsCipherSuite;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[5];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.put((byte) this.mOrg);
        wrap.putInt(this.mTlsCipherSuite | this.mProtocol);
        return bArr;
    }

    public int getOrg() {
        return this.mOrg;
    }

    public int getProtocol() {
        return this.mProtocol;
    }

    public int getTlsCipherSuite() {
        return this.mTlsCipherSuite;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mOrg);
        parcel.writeInt(this.mProtocol);
        parcel.writeInt(this.mTlsCipherSuite);
    }

    public String toString() {
        return "UaSecurityProtocolIdentifier[" + this.mOrg + " , " + (this.mTlsCipherSuite | this.mProtocol) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UaSecurityProtocolIdentifier)) {
            return false;
        }
        UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier = (UaSecurityProtocolIdentifier) obj;
        return this.mOrg == uaSecurityProtocolIdentifier.mOrg && this.mProtocol == uaSecurityProtocolIdentifier.mProtocol && this.mTlsCipherSuite == uaSecurityProtocolIdentifier.mTlsCipherSuite;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mOrg), Integer.valueOf(this.mProtocol), Integer.valueOf(this.mTlsCipherSuite));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isTlsSupported() {
        if (this.mOrg != 1) {
            return false;
        }
        int i = this.mProtocol;
        return i == 65536 || i == 131072;
    }

    public static final class Builder {
        private final UaSecurityProtocolIdentifier mSp;

        public Builder() {
            this.mSp = new UaSecurityProtocolIdentifier();
        }

        public Builder(UaSecurityProtocolIdentifier uaSecurityProtocolIdentifier) {
            Objects.requireNonNull(uaSecurityProtocolIdentifier);
            this.mSp = new UaSecurityProtocolIdentifier();
        }

        public Builder setOrg(int i) {
            if (i < 0 || i > 255) {
                throw new IllegalArgumentException("illegal organization code");
            }
            this.mSp.mOrg = i;
            this.mSp.mProtocol = 0;
            this.mSp.mTlsCipherSuite = 0;
            return this;
        }

        public Builder setProtocol(int i) {
            if (i < 0 || ((i > 6 && i != 256 && i != 65536 && i != 131072) || this.mSp.mOrg != 1)) {
                throw new IllegalArgumentException("illegal protocol code");
            }
            this.mSp.mProtocol = i;
            this.mSp.mTlsCipherSuite = 0;
            return this;
        }

        public Builder setTlsCipherSuite(int i) {
            if (!this.mSp.isTlsSupported()) {
                throw new IllegalArgumentException("The protocol does not support TLS");
            }
            if (!TlsParams.isTlsCipherSuiteSupported(i)) {
                throw new IllegalArgumentException("TLS cipher suite is not supported");
            }
            this.mSp.mTlsCipherSuite = i;
            return this;
        }

        public UaSecurityProtocolIdentifier build() {
            return new UaSecurityProtocolIdentifier();
        }
    }
}
