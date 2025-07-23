package android.content.pm;

import android.annotation.SystemApi;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.NtpTrustedTime;
import com.android.internal.util.Parcelling;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.Set;

/* loaded from: classes.dex */
public class PermissionInfo extends PackageItemInfo implements Parcelable {
    public static final int FLAG_COSTS_MONEY = 1;
    public static final int FLAG_HARD_RESTRICTED = 4;
    public static final int FLAG_IMMUTABLY_RESTRICTED = 16;
    public static final int FLAG_INSTALLED = 1073741824;

    @SystemApi
    public static final int FLAG_REMOVED = 2;
    public static final int FLAG_SOFT_RESTRICTED = 8;
    public static final int PROTECTION_DANGEROUS = 1;
    public static final int PROTECTION_FLAG_APPOP = 64;

    @SystemApi
    public static final int PROTECTION_FLAG_APP_PREDICTOR = 2097152;

    @SystemApi
    public static final int PROTECTION_FLAG_COMPANION = 8388608;

    @SystemApi
    public static final int PROTECTION_FLAG_CONFIGURATOR = 524288;
    public static final int PROTECTION_FLAG_DEVELOPMENT = 32;

    @SystemApi
    public static final int PROTECTION_FLAG_DOCUMENTER = 262144;

    @SystemApi
    public static final int PROTECTION_FLAG_INCIDENT_REPORT_APPROVER = 1048576;
    public static final int PROTECTION_FLAG_INSTALLER = 256;
    public static final int PROTECTION_FLAG_INSTANT = 4096;

    @SystemApi
    public static final int PROTECTION_FLAG_KNOWN_SIGNER = 134217728;

    @SystemApi
    public static final int PROTECTION_FLAG_MODULE = 4194304;

    @SystemApi
    public static final int PROTECTION_FLAG_OEM = 16384;
    public static final int PROTECTION_FLAG_PRE23 = 128;
    public static final int PROTECTION_FLAG_PREINSTALLED = 1024;
    public static final int PROTECTION_FLAG_PRIVILEGED = 16;

    @SystemApi
    public static final int PROTECTION_FLAG_RECENTS = 33554432;

    @SystemApi
    public static final int PROTECTION_FLAG_RETAIL_DEMO = 16777216;

    @SystemApi
    public static final int PROTECTION_FLAG_ROLE = 67108864;
    public static final int PROTECTION_FLAG_RUNTIME_ONLY = 8192;
    public static final int PROTECTION_FLAG_SETUP = 2048;

    @Deprecated
    public static final int PROTECTION_FLAG_SYSTEM = 16;

    @SystemApi
    public static final int PROTECTION_FLAG_SYSTEM_TEXT_CLASSIFIER = 65536;

    @SystemApi
    public static final int PROTECTION_FLAG_VENDOR_PRIVILEGED = 32768;
    public static final int PROTECTION_FLAG_VERIFIER = 512;

    @SystemApi
    public static final int PROTECTION_FLAG_WELLBEING = 131072;
    public static final int PROTECTION_INTERNAL = 4;

    @Deprecated
    public static final int PROTECTION_MASK_BASE = 15;

    @Deprecated
    public static final int PROTECTION_MASK_FLAGS = 65520;
    public static final int PROTECTION_NORMAL = 0;
    public static final int PROTECTION_SIGNATURE = 2;

    @Deprecated
    public static final int PROTECTION_SIGNATURE_OR_SYSTEM = 3;

    @SystemApi
    public final String backgroundPermission;
    public int descriptionRes;
    public int flags;
    public String group;

    @SystemApi
    public Set<String> knownCerts;
    public CharSequence nonLocalizedDescription;

    @Deprecated
    public int protectionLevel;

    @SystemApi
    public int requestRes;
    private static final Parcelling.BuiltIn.ForStringSet sForStringSet = (Parcelling.BuiltIn.ForStringSet) Parcelling.Cache.getOrCreate(Parcelling.BuiltIn.ForStringSet.class);
    public static final Parcelable.Creator<PermissionInfo> CREATOR = new Parcelable.Creator<PermissionInfo>() { // from class: android.content.pm.PermissionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionInfo createFromParcel(Parcel parcel) {
            return new PermissionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PermissionInfo[] newArray(int i) {
            return new PermissionInfo[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Protection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProtectionFlags {
    }

    public static int fixProtectionLevel(int i) {
        if (i == 3) {
            i = 18;
        }
        return ((32768 & i) == 0 || (i & 16) != 0) ? i : i & (-32769);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String protectionToString(int i) {
        StringBuilder sb = new StringBuilder();
        int i2 = i & 15;
        if (i2 == 0) {
            sb.append("normal");
        } else if (i2 == 1) {
            sb.append("dangerous");
        } else if (i2 == 2) {
            sb.append("signature");
        } else if (i2 == 3) {
            sb.append("signatureOrSystem");
        } else if (i2 == 4) {
            sb.append("internal");
        } else {
            sb.append("????");
        }
        if ((i & 16) != 0) {
            sb.append("|privileged");
        }
        if ((i & 32) != 0) {
            sb.append("|development");
        }
        if ((i & 64) != 0) {
            sb.append("|appop");
        }
        if ((i & 128) != 0) {
            sb.append("|pre23");
        }
        if ((i & 256) != 0) {
            sb.append("|installer");
        }
        if ((i & 512) != 0) {
            sb.append("|verifier");
        }
        if ((i & 1024) != 0) {
            sb.append("|preinstalled");
        }
        if ((i & 2048) != 0) {
            sb.append("|setup");
        }
        if ((i & 4096) != 0) {
            sb.append("|instant");
        }
        if ((i & 8192) != 0) {
            sb.append("|runtime");
        }
        if ((i & 16384) != 0) {
            sb.append("|oem");
        }
        if ((32768 & i) != 0) {
            sb.append("|vendorPrivileged");
        }
        if ((65536 & i) != 0) {
            sb.append("|textClassifier");
        }
        if ((524288 & i) != 0) {
            sb.append("|configurator");
        }
        if ((1048576 & i) != 0) {
            sb.append("|incidentReportApprover");
        }
        if ((2097152 & i) != 0) {
            sb.append("|appPredictor");
        }
        if ((8388608 & i) != 0) {
            sb.append("|companion");
        }
        if ((16777216 & i) != 0) {
            sb.append("|retailDemo");
        }
        if ((33554432 & i) != 0) {
            sb.append("|recents");
        }
        if ((67108864 & i) != 0) {
            sb.append("|role");
        }
        if ((134217728 & i) != 0) {
            sb.append("|knownSigner");
        }
        if ((i & 4194304) != 0) {
            sb.append("|module");
        }
        return sb.toString();
    }

    public static String flagsToString(int i) {
        StringBuilder sb = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
        while (i != 0) {
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(i);
            i &= ~numberOfTrailingZeros;
            if (numberOfTrailingZeros == 1) {
                sb.append("costsMoney");
            } else if (numberOfTrailingZeros == 2) {
                sb.append(Environment.MEDIA_REMOVED);
            } else if (numberOfTrailingZeros == 4) {
                sb.append("hardRestricted");
            } else if (numberOfTrailingZeros == 8) {
                sb.append("softRestricted");
            } else if (numberOfTrailingZeros == 16) {
                sb.append("immutablyRestricted");
            } else if (numberOfTrailingZeros == 1073741824) {
                sb.append("installed");
            } else {
                sb.append(numberOfTrailingZeros);
            }
            if (i != 0) {
                sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            }
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    public PermissionInfo(String str) {
        this.knownCerts = Collections.EMPTY_SET;
        this.backgroundPermission = str;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @Deprecated
    public PermissionInfo() {
        this((String) null);
    }

    @Deprecated
    public PermissionInfo(PermissionInfo permissionInfo) {
        super(permissionInfo);
        this.knownCerts = Collections.EMPTY_SET;
        this.protectionLevel = permissionInfo.protectionLevel;
        this.flags = permissionInfo.flags;
        this.group = permissionInfo.group;
        this.backgroundPermission = permissionInfo.backgroundPermission;
        this.descriptionRes = permissionInfo.descriptionRes;
        this.requestRes = permissionInfo.requestRes;
        this.nonLocalizedDescription = permissionInfo.nonLocalizedDescription;
        this.knownCerts = permissionInfo.knownCerts;
    }

    public CharSequence loadDescription(PackageManager packageManager) {
        CharSequence text;
        CharSequence charSequence = this.nonLocalizedDescription;
        if (charSequence != null) {
            return charSequence;
        }
        if (this.descriptionRes == 0 || (text = packageManager.getText(this.packageName, this.descriptionRes, null)) == null) {
            return null;
        }
        return text;
    }

    public int getProtection() {
        return this.protectionLevel & 15;
    }

    public int getProtectionFlags() {
        return this.protectionLevel & (-16);
    }

    public String toString() {
        return "PermissionInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.name + "}";
    }

    @Override // android.content.pm.PackageItemInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.protectionLevel);
        parcel.writeInt(this.flags);
        parcel.writeString8(this.group);
        parcel.writeString8(this.backgroundPermission);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.requestRes);
        TextUtils.writeToParcel(this.nonLocalizedDescription, parcel, i);
        sForStringSet.parcel(this.knownCerts, parcel, i);
    }

    public int calculateFootprint() {
        int length = this.name.length();
        if (this.nonLocalizedLabel != null) {
            length += this.nonLocalizedLabel.length();
        }
        CharSequence charSequence = this.nonLocalizedDescription;
        return charSequence != null ? length + charSequence.length() : length;
    }

    public boolean isHardRestricted() {
        return (this.flags & 4) != 0;
    }

    public boolean isSoftRestricted() {
        return (this.flags & 8) != 0;
    }

    public boolean isRestricted() {
        return isHardRestricted() || isSoftRestricted();
    }

    public boolean isAppOp() {
        return (this.protectionLevel & 64) != 0;
    }

    public boolean isRuntime() {
        return getProtection() == 1;
    }

    private PermissionInfo(Parcel parcel) {
        super(parcel);
        this.knownCerts = Collections.EMPTY_SET;
        this.protectionLevel = parcel.readInt();
        this.flags = parcel.readInt();
        this.group = parcel.readString8();
        this.backgroundPermission = parcel.readString8();
        this.descriptionRes = parcel.readInt();
        this.requestRes = parcel.readInt();
        this.nonLocalizedDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.knownCerts = sForStringSet.unparcel(parcel);
    }
}
