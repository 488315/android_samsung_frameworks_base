package com.samsung.android.knox.net.firewall;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.samsung.android.knox.AppIdentity;
import com.samsung.android.knox.net.firewall.Firewall;
import java.security.InvalidParameterException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class FirewallRule implements Parcelable {
    public static final String ADDRESS = "address";
    public static final String ADDRESS_TYPE = "address type";
    public static final String APP_IDENTITY = "app identity";
    public static final Parcelable.Creator<FirewallRule> CREATOR = new Parcelable.Creator<FirewallRule>() { // from class: com.samsung.android.knox.net.firewall.FirewallRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FirewallRule createFromParcel(Parcel parcel) {
            return new FirewallRule(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FirewallRule[] newArray(int i) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum RuleType {
        DENY,
        ALLOW,
        REDIRECT,
        REDIRECT_EXCEPTION
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x016e, code lost:
    
        if (r3.equals(getRuleType()) != false) goto L93;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r6) {
        /*
            Method dump skipped, instructions count: 679
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.net.firewall.FirewallRule.equals(java.lang.Object):boolean");
    }

    public Firewall.AddressType getAddressType() {
        return this.mAddressType;
    }

    public AppIdentity getApplication() {
        return this.mAppIdentity;
    }

    public Firewall.Direction getDirection() {
        if (RuleType.ALLOW.equals(getRuleType()) || RuleType.DENY.equals(getRuleType())) {
            return this.mDirection;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
    }

    public int getId() {
        return this.mId;
    }

    public String getIpAddress() {
        return this.mAddress;
    }

    public Firewall.NetworkInterface getNetworkInterface() {
        return this.mNetworkInterface;
    }

    public String getPackageName() {
        return this.mAppIdentity.getPackageName();
    }

    public int getPackageUid() {
        return this.mPackageUid;
    }

    public Firewall.PortLocation getPortLocation() {
        if (RuleType.ALLOW.equals(getRuleType()) || RuleType.DENY.equals(getRuleType())) {
            return this.mPortLocation;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
    }

    public String getPortNumber() {
        return this.mPortNumber;
    }

    public Firewall.Protocol getProtocol() {
        return this.mProtocol;
    }

    public RuleType getRuleType() {
        return this.mRuleType;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public String getStrNetworkInterface() {
        return this.mStrNetworkInterface;
    }

    public String getTargetIpAddress() {
        if (RuleType.REDIRECT.equals(getRuleType())) {
            return this.mTargetIp;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
    }

    public String getTargetPortNumber() {
        if (RuleType.REDIRECT.equals(getRuleType())) {
            return this.mTargetPortNumber;
        }
        throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
    }

    public int hashCode() {
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

    public void setApplication(AppIdentity appIdentity) {
        if (appIdentity == null || !FirewallRuleValidator.validatePackageName(appIdentity.getPackageName())) {
            throw new InvalidParameterException("Parameter: app identity is invalid.");
        }
        this.mAppIdentity = appIdentity;
    }

    public void setDirection(Firewall.Direction direction) {
        if (direction == null) {
            throw new InvalidParameterException("Parameter: direction is invalid.");
        }
        if (RuleType.ALLOW.equals(getRuleType()) || RuleType.DENY.equals(getRuleType())) {
            this.mDirection = direction;
        } else {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
        }
    }

    public void setId(int i) {
        this.mId = i;
    }

    public void setIpAddress(String str) {
        if (this.mAddressType.equals(Firewall.AddressType.IPV4)) {
            if (!FirewallRuleValidator.validadeIpv4Range(str) && !FirewallRuleValidator.validateIpv4Address(str) && !"*".equals(str)) {
                throw new InvalidParameterException("Parameter: address is invalid.");
            }
        } else if (this.mAddressType.equals(Firewall.AddressType.IPV6) && !FirewallRuleValidator.validadeIpv6Range(str) && !FirewallRuleValidator.validateIpv6Address(str) && !"*".equals(str)) {
            throw new InvalidParameterException("Parameter: address is invalid.");
        }
        this.mAddress = str;
    }

    public void setNetworkInterface(Firewall.NetworkInterface networkInterface) {
        if (networkInterface == null) {
            throw new InvalidParameterException("Parameter: network interface is invalid.");
        }
        this.mNetworkInterface = networkInterface;
    }

    public void setPackageName(String str) {
        if (TextUtils.isEmpty(str) || !FirewallRuleValidator.validatePackageName(str)) {
            throw new InvalidParameterException("Parameter: package name is invalid.");
        }
        this.mAppIdentity = new AppIdentity(str, (String) null);
    }

    public void setPackageUid(int i) {
        this.mPackageUid = i;
    }

    public void setPortLocation(Firewall.PortLocation portLocation) {
        if (portLocation == null) {
            throw new InvalidParameterException("Parameter: port location is invalid.");
        }
        if (RuleType.ALLOW.equals(getRuleType()) || RuleType.DENY.equals(getRuleType())) {
            this.mPortLocation = portLocation;
        } else {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
        }
    }

    public void setPortNumber(String str) {
        if (!FirewallRuleValidator.validatePortNumber(str) && !FirewallRuleValidator.validadePortNumberRange(str) && !"*".equals(str)) {
            throw new InvalidParameterException("Parameter: port number is invalid.");
        }
        this.mPortNumber = str;
    }

    public void setProtocol(Firewall.Protocol protocol) {
        if (protocol == null) {
            throw new InvalidParameterException("Parameter: protocol is invalid.");
        }
        this.mProtocol = protocol;
    }

    public void setStatus(Status status) {
        this.mStatus = status;
    }

    public void setStrNetworkInterface(String str) {
        this.mStrNetworkInterface = str;
    }

    public void setTargetIpAddress(String str) {
        if (!RuleType.REDIRECT.equals(getRuleType())) {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
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

    public void setTargetPortNumber(String str) {
        if (!RuleType.REDIRECT.equals(getRuleType())) {
            throw new UnsupportedOperationException(UNSUPPORTED_METHOD + getRuleType().toString());
        }
        if (!FirewallRuleValidator.validatePortNumber(str)) {
            throw new InvalidParameterException("Parameter: target port number is invalid.");
        }
        this.mTargetPortNumber = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int ordinal = this.mRuleType.ordinal();
        if (ordinal == 0 || ordinal == 1) {
            sb.append("\nIP Address: " + getIpAddress());
            sb.append("\nPort Number: " + getPortNumber());
            sb.append("\nPort Location: " + getPortLocation());
            sb.append("\nPackage Name: " + getApplication().getPackageName());
            sb.append("\nSignature: " + getApplication().getSignature());
            if (TextUtils.isEmpty(this.mStrNetworkInterface)) {
                sb.append("\nNetwork Interface: " + getNetworkInterface());
            } else {
                sb.append("\nNetwork Interface: " + getStrNetworkInterface());
            }
            sb.append("\nDirection: " + getDirection());
            sb.append("\nProtocol: " + getProtocol());
            sb.append("\nAddress Type: " + getAddressType() + "\n");
        } else if (ordinal == 2) {
            sb.append("\nSource IP Address: " + getIpAddress());
            sb.append("\nSource Port Number: " + getPortNumber());
            sb.append("\nTarget IP Address: " + getTargetIpAddress());
            sb.append("\nTarget Port Number: " + getTargetPortNumber());
            sb.append("\nPackage Name: " + getApplication().getPackageName());
            sb.append("\nSignature: " + getApplication().getSignature());
            if (TextUtils.isEmpty(this.mStrNetworkInterface)) {
                sb.append("\nNetwork Interface: " + getNetworkInterface());
            } else {
                sb.append("\nNetwork Interface: " + getStrNetworkInterface());
            }
            sb.append("\nProtocol: " + getProtocol() + "\n");
            sb.append("\nAddress Type: " + getAddressType() + "\n");
        } else if (ordinal == 3) {
            sb.append("\nIP Address: " + getIpAddress());
            sb.append("\nPort Number: " + getPortNumber());
            sb.append("\nPackage Name: " + getApplication().getPackageName());
            sb.append("\nSignature: " + getApplication().getSignature());
            sb.append("\nProtocol: " + getProtocol() + "\n");
            sb.append("\nAddress Type: " + getAddressType() + "\n");
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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
