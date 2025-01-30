package com.samsung.android.knox.net.firewall;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.net.firewall.Firewall;
import java.security.InvalidParameterException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class FirewallRule implements Parcelable {
    public static final String ADDRESS = "address";
    public static final String ADDRESS_TYPE = "address type";
    public static final String APP_IDENTITY = "app identity";
    public static final Parcelable.Creator<FirewallRule> CREATOR = new Parcelable.Creator<FirewallRule>() { // from class: com.samsung.android.knox.net.firewall.FirewallRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final FirewallRule createFromParcel(Parcel parcel) {
            return new FirewallRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final FirewallRule[] newArray(int i) {
            return new FirewallRule[i];
        }

        @Override // android.os.Parcelable.Creator
        public final FirewallRule createFromParcel(Parcel parcel) {
            return new FirewallRule(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final FirewallRule[] newArray(int i) {
            return new FirewallRule[i];
        }
    };
    public static final String DIRECTION = "direction";
    public static final String IS_INVALID = " is invalid.";
    public static final String NETWORK_INTERFACE = "network interface";
    public static final String PACKAGE_NAME = "package name";
    public static final String PARAMETER = "Parameter: ";
    public static final String PORT_LOCATION = "port location";
    public static final String PORT_NUMBER = "port number";
    public static final String PROTOCOL = "protocol";
    public static final String RULE_TYPE = "rule type";
    public static final String TARGET_IP = "target IP";
    public static final String TARGET_PORT_NUMBER = "target port number";
    public static final String UNSUPPORTED_METHOD = "This method is not supported for this RuleType: ";
    public String mAddress;
    public Firewall.AddressType mAddressType;
    public AppIdentity mAppIdentity;
    public Firewall.Direction mDirection;
    public int mId;
    public Firewall.NetworkInterface mNetworkInterface;
    public int mPackageUid = -1;
    public Firewall.PortLocation mPortLocation;
    public String mPortNumber;
    public Firewall.Protocol mProtocol;
    public RuleType mRuleType;
    public Status mStatus;
    public String mStrNetworkInterface;
    public String mTargetIp;
    public String mTargetPortNumber;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.knox.net.firewall.FirewallRule$2 */
    public static /* synthetic */ class C47242 {

        /* renamed from: $SwitchMap$com$samsung$android$knox$net$firewall$FirewallRule$RuleType */
        public static final /* synthetic */ int[] f490x6178b957;

        static {
            int[] iArr = new int[RuleType.values().length];
            f490x6178b957 = iArr;
            try {
                iArr[RuleType.ALLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f490x6178b957[RuleType.DENY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f490x6178b957[RuleType.REDIRECT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f490x6178b957[RuleType.REDIRECT_EXCEPTION.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum RuleType {
        DENY,
        ALLOW,
        REDIRECT,
        REDIRECT_EXCEPTION
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Status {
        DISABLED,
        ENABLED,
        PENDING
    }

    public FirewallRule(RuleType ruleType, Firewall.AddressType addressType) {
        if (ruleType == null) {
            throw new InvalidParameterException("Parameter: rule type is invalid.");
        }
        if (addressType == null) {
            throw new InvalidParameterException("Parameter: address type is invalid.");
        }
        this.mRuleType = ruleType;
        this.mStatus = Status.DISABLED;
        this.mAddressType = addressType;
        this.mAddress = "*";
        this.mPortNumber = "*";
        this.mAppIdentity = new AppIdentity("*", (String) null);
        this.mPortLocation = Firewall.PortLocation.ALL;
        this.mNetworkInterface = Firewall.NetworkInterface.ALL_NETWORKS;
        this.mDirection = Firewall.Direction.ALL;
        this.mProtocol = Firewall.Protocol.ALL;
        this.mTargetIp = null;
        this.mTargetPortNumber = null;
        this.mId = -1;
        this.mStrNetworkInterface = null;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:130:0x00fd, code lost:
    
        if (r3.equals(r5.mRuleType) != false) goto L88;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean equals(Object obj) {
        AppIdentity appIdentity;
        AppIdentity appIdentity2;
        boolean z;
        RuleType ruleType;
        boolean z2 = false;
        if (!(obj instanceof FirewallRule)) {
            return false;
        }
        FirewallRule firewallRule = (FirewallRule) obj;
        Firewall.AddressType addressType = firewallRule.mAddressType;
        boolean z3 = ((addressType == null && this.mAddressType == null) || (addressType != null && addressType.equals(this.mAddressType))) & true;
        String str = firewallRule.mAddress;
        boolean z4 = z3 & ((str == null && this.mAddress == null) || (str != null && str.equals(this.mAddress)));
        Firewall.NetworkInterface networkInterface = firewallRule.mNetworkInterface;
        boolean z5 = z4 & ((networkInterface == null && this.mNetworkInterface == null) || (networkInterface != null && networkInterface.equals(this.mNetworkInterface)));
        String str2 = firewallRule.mStrNetworkInterface;
        boolean z6 = z5 & ((str2 == null && this.mStrNetworkInterface == null) || (str2 != null && str2.equals(this.mStrNetworkInterface)));
        AppIdentity appIdentity3 = firewallRule.mAppIdentity;
        boolean z7 = z6 & ((appIdentity3 == null && this.mAppIdentity == null) || (appIdentity3 != null && this.mAppIdentity != null && appIdentity3.getPackageName() == null && this.mAppIdentity.getPackageName() == null) || !((appIdentity = firewallRule.mAppIdentity) == null || this.mAppIdentity == null || appIdentity.getPackageName() == null || !firewallRule.mAppIdentity.getPackageName().equals(this.mAppIdentity.getPackageName())));
        AppIdentity appIdentity4 = firewallRule.mAppIdentity;
        boolean z8 = z7 & ((appIdentity4 != null && this.mAppIdentity != null && appIdentity4.getSignature() == null && this.mAppIdentity.getSignature() == null) || !((appIdentity2 = firewallRule.mAppIdentity) == null || this.mAppIdentity == null || appIdentity2.getSignature() == null || !firewallRule.mAppIdentity.getSignature().equals(this.mAppIdentity.getSignature())));
        RuleType ruleType2 = RuleType.DENY;
        if (!ruleType2.equals(firewallRule.mRuleType) || !ruleType2.equals(this.mRuleType)) {
            RuleType ruleType3 = RuleType.ALLOW;
            if (ruleType3.equals(firewallRule.mRuleType)) {
            }
            String str3 = firewallRule.mPortNumber;
            boolean z9 = z8 & ((str3 != null && this.mPortNumber == null) || (str3 != null && str3.equals(this.mPortNumber)));
            Firewall.Protocol protocol = firewallRule.mProtocol;
            boolean z10 = z9 & ((protocol != null && this.mProtocol == null) || (protocol != null && protocol.equals(this.mProtocol)));
            RuleType ruleType4 = firewallRule.mRuleType;
            boolean z11 = z10 & ((ruleType4 != null && this.mRuleType == null) || (ruleType4 != null && ruleType4.equals(this.mRuleType)));
            Status status = firewallRule.mStatus;
            z = z11 & ((status != null && this.mStatus == null) || (status != null && status.equals(this.mStatus)));
            ruleType = RuleType.REDIRECT;
            if (!ruleType.equals(firewallRule.mRuleType) && ruleType.equals(this.mRuleType)) {
                boolean z12 = z & ((firewallRule.getTargetIpAddress() == null && getTargetIpAddress() == null) || (firewallRule.getTargetIpAddress() != null && firewallRule.getTargetIpAddress().equals(getTargetIpAddress())));
                if ((firewallRule.getTargetPortNumber() == null && getTargetPortNumber() == null) || (firewallRule.getTargetPortNumber() != null && firewallRule.getTargetPortNumber().equals(getTargetPortNumber()))) {
                    z2 = true;
                }
                return z12 & z2;
            }
        }
        z8 = z8 & ((firewallRule.getDirection() == null && getDirection() == null) || (firewallRule.getDirection() != null && firewallRule.getDirection().equals(getDirection()))) & ((firewallRule.getPortLocation() == null && getPortLocation() == null) || (firewallRule.getPortLocation() != null && firewallRule.getPortLocation().equals(getPortLocation())));
        String str32 = firewallRule.mPortNumber;
        boolean z92 = z8 & ((str32 != null && this.mPortNumber == null) || (str32 != null && str32.equals(this.mPortNumber)));
        Firewall.Protocol protocol2 = firewallRule.mProtocol;
        boolean z102 = z92 & ((protocol2 != null && this.mProtocol == null) || (protocol2 != null && protocol2.equals(this.mProtocol)));
        RuleType ruleType42 = firewallRule.mRuleType;
        boolean z112 = z102 & ((ruleType42 != null && this.mRuleType == null) || (ruleType42 != null && ruleType42.equals(this.mRuleType)));
        Status status2 = firewallRule.mStatus;
        z = z112 & ((status2 != null && this.mStatus == null) || (status2 != null && status2.equals(this.mStatus)));
        ruleType = RuleType.REDIRECT;
        return !ruleType.equals(firewallRule.mRuleType) ? z : z;
    }

    public final Firewall.AddressType getAddressType() {
        return this.mAddressType;
    }

    public final AppIdentity getApplication() {
        return this.mAppIdentity;
    }

    public final Firewall.Direction getDirection() {
        if (RuleType.ALLOW.equals(this.mRuleType) || RuleType.DENY.equals(this.mRuleType)) {
            return this.mDirection;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
    }

    public final int getId() {
        return this.mId;
    }

    public final String getIpAddress() {
        return this.mAddress;
    }

    public final Firewall.NetworkInterface getNetworkInterface() {
        return this.mNetworkInterface;
    }

    public final String getPackageName() {
        return this.mAppIdentity.getPackageName();
    }

    public final int getPackageUid() {
        return this.mPackageUid;
    }

    public final Firewall.PortLocation getPortLocation() {
        if (RuleType.ALLOW.equals(this.mRuleType) || RuleType.DENY.equals(this.mRuleType)) {
            return this.mPortLocation;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
    }

    public final String getPortNumber() {
        return this.mPortNumber;
    }

    public final Firewall.Protocol getProtocol() {
        return this.mProtocol;
    }

    public final RuleType getRuleType() {
        return this.mRuleType;
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public final String getStrNetworkInterface() {
        return this.mStrNetworkInterface;
    }

    public final String getTargetIpAddress() {
        if (RuleType.REDIRECT.equals(this.mRuleType)) {
            return this.mTargetIp;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
    }

    public final String getTargetPortNumber() {
        if (RuleType.REDIRECT.equals(this.mRuleType)) {
            return this.mTargetPortNumber;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
    }

    public final int hashCode() {
        String str = this.mAddress;
        int hashCode = ((str == null ? 0 : str.hashCode()) + 31) * 31;
        Firewall.AddressType addressType = this.mAddressType;
        int hashCode2 = (hashCode + (addressType == null ? 0 : addressType.hashCode())) * 31;
        Firewall.Direction direction = this.mDirection;
        int hashCode3 = (((hashCode2 + (direction == null ? 0 : direction.hashCode())) * 31) + this.mId) * 31;
        Firewall.NetworkInterface networkInterface = this.mNetworkInterface;
        int hashCode4 = (hashCode3 + (networkInterface == null ? 0 : networkInterface.hashCode())) * 31;
        AppIdentity appIdentity = this.mAppIdentity;
        int hashCode5 = (hashCode4 + (appIdentity == null ? 0 : appIdentity.hashCode())) * 31;
        Firewall.PortLocation portLocation = this.mPortLocation;
        int hashCode6 = (hashCode5 + (portLocation == null ? 0 : portLocation.hashCode())) * 31;
        String str2 = this.mPortNumber;
        int hashCode7 = (hashCode6 + (str2 == null ? 0 : str2.hashCode())) * 31;
        Firewall.Protocol protocol = this.mProtocol;
        int hashCode8 = (hashCode7 + (protocol == null ? 0 : protocol.hashCode())) * 31;
        RuleType ruleType = this.mRuleType;
        int hashCode9 = (hashCode8 + (ruleType == null ? 0 : ruleType.hashCode())) * 31;
        Status status = this.mStatus;
        int hashCode10 = (hashCode9 + (status == null ? 0 : status.hashCode())) * 31;
        String str3 = this.mTargetIp;
        int hashCode11 = (hashCode10 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.mTargetPortNumber;
        int hashCode12 = (hashCode11 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.mStrNetworkInterface;
        return hashCode12 + (str5 != null ? str5.hashCode() : 0);
    }

    public final void setApplication(AppIdentity appIdentity) {
        if (appIdentity == null || !FirewallRuleValidator.validatePackageName(appIdentity.getPackageName())) {
            throw new InvalidParameterException("Parameter: app identity is invalid.");
        }
        this.mAppIdentity = appIdentity;
    }

    public final void setDirection(Firewall.Direction direction) {
        if (direction == null) {
            throw new InvalidParameterException("Parameter: direction is invalid.");
        }
        if (RuleType.ALLOW.equals(this.mRuleType) || RuleType.DENY.equals(this.mRuleType)) {
            this.mDirection = direction;
        } else {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
        }
    }

    public final void setId(int i) {
        this.mId = i;
    }

    public final void setIpAddress(String str) {
        if (this.mAddressType.equals(Firewall.AddressType.IPV4)) {
            if (!FirewallRuleValidator.validadeIpv4Range(str) && !FirewallRuleValidator.validateIpv4Address(str) && !"*".equals(str)) {
                throw new InvalidParameterException("Parameter: address is invalid.");
            }
        } else if (this.mAddressType.equals(Firewall.AddressType.IPV6) && !FirewallRuleValidator.validadeIpv6Range(str) && !FirewallRuleValidator.validateIpv6Address(str) && !"*".equals(str)) {
            throw new InvalidParameterException("Parameter: address is invalid.");
        }
        this.mAddress = str;
    }

    public final void setNetworkInterface(Firewall.NetworkInterface networkInterface) {
        if (networkInterface == null) {
            throw new InvalidParameterException("Parameter: network interface is invalid.");
        }
        this.mNetworkInterface = networkInterface;
    }

    public final void setPackageName(String str) {
        if (TextUtils.isEmpty(str) || !FirewallRuleValidator.validatePackageName(str)) {
            throw new InvalidParameterException("Parameter: package name is invalid.");
        }
        this.mAppIdentity = new AppIdentity(str, (String) null);
    }

    public final void setPackageUid(int i) {
        this.mPackageUid = i;
    }

    public final void setPortLocation(Firewall.PortLocation portLocation) {
        if (portLocation == null) {
            throw new InvalidParameterException("Parameter: port location is invalid.");
        }
        if (RuleType.ALLOW.equals(this.mRuleType) || RuleType.DENY.equals(this.mRuleType)) {
            this.mPortLocation = portLocation;
        } else {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
        }
    }

    public final void setPortNumber(String str) {
        if (!FirewallRuleValidator.validatePortNumber(str) && !FirewallRuleValidator.validadePortNumberRange(str) && !"*".equals(str)) {
            throw new InvalidParameterException("Parameter: port number is invalid.");
        }
        this.mPortNumber = str;
    }

    public final void setProtocol(Firewall.Protocol protocol) {
        if (protocol == null) {
            throw new InvalidParameterException("Parameter: protocol is invalid.");
        }
        this.mProtocol = protocol;
    }

    public final void setStatus(Status status) {
        this.mStatus = status;
    }

    public final void setStrNetworkInterface(String str) {
        this.mStrNetworkInterface = str;
    }

    public final void setTargetIpAddress(String str) {
        if (!RuleType.REDIRECT.equals(this.mRuleType)) {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
        }
        if (this.mAddressType.equals(Firewall.AddressType.IPV4)) {
            if (!FirewallRuleValidator.validateIpv4Address(str)) {
                throw new InvalidParameterException("Parameter: target IP is invalid.");
            }
        } else if (!FirewallRuleValidator.validateIpv6Address(str)) {
            throw new InvalidParameterException("Parameter: target IP is invalid.");
        }
        this.mTargetIp = str;
    }

    public final void setTargetPortNumber(String str) {
        if (!RuleType.REDIRECT.equals(this.mRuleType)) {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + this.mRuleType.toString());
        }
        if (!FirewallRuleValidator.validatePortNumber(str)) {
            throw new InvalidParameterException("Parameter: target port number is invalid.");
        }
        this.mTargetPortNumber = str;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = C47242.f490x6178b957[this.mRuleType.ordinal()];
        if (i == 1 || i == 2) {
            sb.append("\nIP Address: " + this.mAddress);
            sb.append("\nPort Number: " + this.mPortNumber);
            sb.append("\nPort Location: " + getPortLocation());
            sb.append("\nPackage Name: " + this.mAppIdentity.getPackageName());
            sb.append("\nSignature: " + this.mAppIdentity.getSignature());
            if (TextUtils.isEmpty(this.mStrNetworkInterface)) {
                sb.append("\nNetwork Interface: " + this.mNetworkInterface);
            } else {
                sb.append("\nNetwork Interface: " + this.mStrNetworkInterface);
            }
            sb.append("\nDirection: " + getDirection());
            sb.append("\nProtocol: " + this.mProtocol);
            sb.append("\nAddress Type: " + this.mAddressType + "\n");
        } else if (i == 3) {
            sb.append("\nSource IP Address: " + this.mAddress);
            sb.append("\nSource Port Number: " + this.mPortNumber);
            sb.append("\nTarget IP Address: " + getTargetIpAddress());
            sb.append("\nTarget Port Number: " + getTargetPortNumber());
            sb.append("\nPackage Name: " + this.mAppIdentity.getPackageName());
            sb.append("\nSignature: " + this.mAppIdentity.getSignature());
            if (TextUtils.isEmpty(this.mStrNetworkInterface)) {
                sb.append("\nNetwork Interface: " + this.mNetworkInterface);
            } else {
                sb.append("\nNetwork Interface: " + this.mStrNetworkInterface);
            }
            sb.append("\nProtocol: " + this.mProtocol + "\n");
            sb.append("\nAddress Type: " + this.mAddressType + "\n");
        } else if (i == 4) {
            sb.append("\nIP Address: " + this.mAddress);
            sb.append("\nPort Number: " + this.mPortNumber);
            sb.append("\nPackage Name: " + this.mAppIdentity.getPackageName());
            sb.append("\nSignature: " + this.mAppIdentity.getSignature());
            sb.append("\nProtocol: " + this.mProtocol + "\n");
            sb.append("\nAddress Type: " + this.mAddressType + "\n");
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeSerializable(this.mRuleType);
        parcel.writeSerializable(this.mStatus);
        parcel.writeString(this.mAddress);
        parcel.writeString(this.mPortNumber);
        parcel.writeSerializable(this.mPortLocation);
        parcel.writeParcelable(this.mAppIdentity, i);
        parcel.writeSerializable(this.mNetworkInterface);
        parcel.writeSerializable(this.mDirection);
        parcel.writeSerializable(this.mProtocol);
        parcel.writeSerializable(this.mAddressType);
        parcel.writeString(this.mTargetIp);
        parcel.writeString(this.mTargetPortNumber);
        parcel.writeString(this.mStrNetworkInterface);
    }

    public FirewallRule(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mRuleType = (RuleType) parcel.readSerializable();
        this.mStatus = (Status) parcel.readSerializable();
        this.mAddress = parcel.readString();
        this.mPortNumber = parcel.readString();
        this.mPortLocation = (Firewall.PortLocation) parcel.readSerializable();
        this.mAppIdentity = (AppIdentity) parcel.readParcelable(AppIdentity.class.getClassLoader());
        this.mNetworkInterface = (Firewall.NetworkInterface) parcel.readSerializable();
        this.mDirection = (Firewall.Direction) parcel.readSerializable();
        this.mProtocol = (Firewall.Protocol) parcel.readSerializable();
        this.mAddressType = (Firewall.AddressType) parcel.readSerializable();
        this.mTargetIp = parcel.readString();
        this.mTargetPortNumber = parcel.readString();
        this.mStrNetworkInterface = parcel.readString();
    }
}
