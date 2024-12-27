package android.app.admin;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Printer;
import android.util.SparseArray;

import com.android.internal.R;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;

public final class DeviceAdminInfo implements Parcelable {
    public static final Parcelable.Creator<DeviceAdminInfo> CREATOR;
    public static final int HEADLESS_DEVICE_OWNER_MODE_AFFILIATED = 1;
    public static final int HEADLESS_DEVICE_OWNER_MODE_SINGLE_USER = 2;
    public static final int HEADLESS_DEVICE_OWNER_MODE_UNSUPPORTED = 0;
    static final String TAG = "DeviceAdminInfo";
    public static final int USES_ENCRYPTED_STORAGE = 7;
    public static final int USES_POLICY_ALLOW_BLUETOOTH_MODE = 17;
    public static final int USES_POLICY_ALLOW_BROWSER = 15;
    public static final int USES_POLICY_ALLOW_DESKTOP_SYNC = 18;
    public static final int USES_POLICY_ALLOW_INTERNET_SHARING = 16;
    public static final int USES_POLICY_ALLOW_IRDA = 19;
    public static final int USES_POLICY_ALLOW_POPIMAP_EMAIL = 14;
    public static final int USES_POLICY_ALLOW_STORAGE_CARD = 11;
    public static final int USES_POLICY_ALLOW_TEXT_MESSAGING = 13;
    public static final int USES_POLICY_ALLOW_WIFI = 12;
    public static final int USES_POLICY_DISABLE_CAMERA = 8;
    public static final int USES_POLICY_DISABLE_KEYGUARD_FEATURES = 9;
    public static final int USES_POLICY_EDM_BEGIN = 22;
    public static final int USES_POLICY_EXPIRE_PASSWORD = 6;
    public static final int USES_POLICY_FORCE_LOCK = 3;
    public static final int USES_POLICY_LIMIT_PASSWORD = 0;
    public static final int USES_POLICY_PASSWORD_RECOVERABLE = 10;
    public static final int USES_POLICY_REQUIRE_STORAGECARD_ENCRYPTION = 20;
    public static final int USES_POLICY_RESET_PASSWORD = 2;
    public static final int USES_POLICY_SETS_GLOBAL_PROXY = 5;
    public static final int USES_POLICY_SIMPLE_PASSWORD_ENABLED = 21;
    public static final int USES_POLICY_WATCH_LOGIN = 1;
    public static final int USES_POLICY_WIPE_DATA = 4;
    final ActivityInfo mActivityInfo;
    int mHeadlessDeviceOwnerMode;
    boolean mSupportsTransferOwnership;
    int mUsesPolicies;
    boolean mVisible;
    static ArrayList<PolicyInfo> sPoliciesDisplayOrder = new ArrayList<>();
    static HashMap<String, Integer> sKnownPolicies = new HashMap<>();
    static SparseArray<PolicyInfo> sRevKnownPolicies = new SparseArray<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface HeadlessDeviceOwnerMode {}

    public static class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        public final String tag;

        public PolicyInfo(int ident, String tag, int label, int description) {
            this(ident, tag, label, description, label, description);
        }

        public PolicyInfo(
                int ident,
                String tag,
                int label,
                int description,
                int labelForSecondaryUsers,
                int descriptionForSecondaryUsers) {
            this.ident = ident;
            this.tag = tag;
            this.label = label;
            this.description = description;
            this.labelForSecondaryUsers = labelForSecondaryUsers;
            this.descriptionForSecondaryUsers = descriptionForSecondaryUsers;
        }
    }

    static {
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        4,
                        "wipe-data",
                        R.string.policylab_wipeData,
                        R.string.policydesc_wipeData,
                        R.string.policylab_wipeData_secondaryUser,
                        R.string.policydesc_wipeData_secondaryUser));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        2,
                        "reset-password",
                        R.string.policylab_resetPassword,
                        R.string.policydesc_resetPassword));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        0,
                        "limit-password",
                        R.string.policylab_limitPassword,
                        R.string.policydesc_limitPassword));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        1,
                        "watch-login",
                        R.string.policylab_watchLogin,
                        R.string.policydesc_watchLogin,
                        R.string.policylab_watchLogin,
                        R.string.policydesc_watchLogin_secondaryUser));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        3,
                        "force-lock",
                        R.string.policylab_forceLock,
                        R.string.policydesc_forceLock));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        5,
                        "set-global-proxy",
                        R.string.policylab_setGlobalProxy,
                        R.string.policydesc_setGlobalProxy));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        6,
                        "expire-password",
                        R.string.policylab_expirePassword,
                        R.string.policydesc_expirePassword));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        7,
                        "encrypted-storage",
                        R.string.policylab_encryptedStorage,
                        R.string.policydesc_encryptedStorage));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        8,
                        "disable-camera",
                        R.string.policylab_disableCamera,
                        R.string.policydesc_disableCamera));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        9,
                        "disable-keyguard-features",
                        R.string.policylab_disableKeyguardFeatures,
                        R.string.policydesc_disableKeyguardFeatures));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        20,
                        "require-storagecard-encryption",
                        R.string.policylab_require_storagecard_encryption,
                        R.string.policydesc_require_storagecard_encryption));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        10,
                        "recover-password",
                        R.string.policylab_recoverPassword,
                        R.string.policydesc_recoverPassword));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        14,
                        "allow-popimapemail",
                        R.string.policylab_allow_popimapemail,
                        R.string.policydesc_allow_popimapemail));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        11,
                        "allow-storagecard",
                        R.string.policylab_allow_storagecard,
                        R.string.policydesc_allow_storagecard));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        12,
                        "allow-wifi",
                        R.string.policylab_allow_wifi,
                        R.string.policydesc_allow_wifi));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        13,
                        "allow-textmessaging",
                        R.string.policylab_allow_textmessaging,
                        R.string.policydesc_allow_textmessaging));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        15,
                        "allow-browser",
                        R.string.policylab_allow_browser,
                        R.string.policydesc_allow_browser));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        16,
                        "allow-internetsharing",
                        R.string.policylab_allow_internetsharing,
                        R.string.policydesc_allow_internetsharing));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        17,
                        "allow-bluetoothmode",
                        R.string.policylab_allow_bluetoothmode,
                        R.string.policydesc_allow_bluetoothmode));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        18,
                        "allow-desktopsync",
                        R.string.policylab_allow_desktopsync,
                        R.string.policydesc_allow_desktopsync));
        sPoliciesDisplayOrder.add(
                new PolicyInfo(
                        19,
                        "allow-irda",
                        R.string.policylab_allow_irda,
                        R.string.policydesc_allow_irda));
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo pi = sPoliciesDisplayOrder.get(i);
            sRevKnownPolicies.put(pi.ident, pi);
            sKnownPolicies.put(pi.tag, Integer.valueOf(pi.ident));
        }
        CREATOR =
                new Parcelable.Creator<
                        DeviceAdminInfo>() { // from class: android.app.admin.DeviceAdminInfo.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public DeviceAdminInfo createFromParcel(Parcel source) {
                        return new DeviceAdminInfo(source);
                    }

                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // android.os.Parcelable.Creator
                    public DeviceAdminInfo[] newArray(int size) {
                        return new DeviceAdminInfo[size];
                    }
                };
    }

    public DeviceAdminInfo(Context context, ResolveInfo resolveInfo)
            throws XmlPullParserException, IOException {
        this(context, resolveInfo.activityInfo);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0188, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:?, code lost:

       return;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DeviceAdminInfo(android.content.Context r20, android.content.pm.ActivityInfo r21)
            throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 447
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                        + " android.app.admin.DeviceAdminInfo.<init>(android.content.Context,"
                        + " android.content.pm.ActivityInfo):void");
    }

    DeviceAdminInfo(Parcel source) {
        this.mHeadlessDeviceOwnerMode = 0;
        this.mActivityInfo = ActivityInfo.CREATOR.createFromParcel(source);
        this.mUsesPolicies = source.readInt();
        this.mSupportsTransferOwnership = source.readBoolean();
        this.mHeadlessDeviceOwnerMode = source.readInt();
    }

    public String getPackageName() {
        return this.mActivityInfo.packageName;
    }

    public String getReceiverName() {
        return this.mActivityInfo.name;
    }

    public ActivityInfo getActivityInfo() {
        return this.mActivityInfo;
    }

    public ComponentName getComponent() {
        return new ComponentName(this.mActivityInfo.packageName, this.mActivityInfo.name);
    }

    public CharSequence loadLabel(PackageManager pm) {
        return this.mActivityInfo.loadLabel(pm);
    }

    public CharSequence loadDescription(PackageManager pm) throws Resources.NotFoundException {
        if (this.mActivityInfo.descriptionRes != 0) {
            return pm.getText(
                    this.mActivityInfo.packageName,
                    this.mActivityInfo.descriptionRes,
                    this.mActivityInfo.applicationInfo);
        }
        throw new Resources.NotFoundException();
    }

    public Drawable loadIcon(PackageManager pm) {
        return this.mActivityInfo.loadIcon(pm);
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean usesPolicy(int policyIdent) {
        return policyIdent < 22 && (this.mUsesPolicies & (1 << policyIdent)) != 0;
    }

    public String getTagForPolicy(int policyIdent) {
        return sRevKnownPolicies.get(policyIdent).tag;
    }

    public boolean supportsTransferOwnership() {
        return this.mSupportsTransferOwnership;
    }

    public int getHeadlessDeviceOwnerMode() {
        return this.mHeadlessDeviceOwnerMode;
    }

    public ArrayList<PolicyInfo> getUsedPolicies() {
        ArrayList<PolicyInfo> res = new ArrayList<>();
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo pi = sPoliciesDisplayOrder.get(i);
            if (usesPolicy(pi.ident)) {
                res.add(pi);
            }
        }
        return res;
    }

    public void writePoliciesToXml(TypedXmlSerializer out)
            throws IllegalArgumentException, IllegalStateException, IOException {
        out.attributeInt(null, "flags", this.mUsesPolicies);
    }

    public void readPoliciesFromXml(TypedXmlPullParser parser)
            throws XmlPullParserException, IOException {
        this.mUsesPolicies = parser.getAttributeInt(null, "flags");
    }

    public void dump(Printer pw, String prefix) {
        pw.println(prefix + "Receiver:");
        this.mActivityInfo.dump(pw, prefix + "  ");
    }

    public String toString() {
        return "DeviceAdminInfo{" + this.mActivityInfo.name + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mActivityInfo.writeToParcel(dest, flags);
        dest.writeInt(this.mUsesPolicies);
        dest.writeBoolean(this.mSupportsTransferOwnership);
        dest.writeInt(this.mHeadlessDeviceOwnerMode);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getPermissions() {
        return this.mUsesPolicies;
    }

    public void setPermissions(int val) {
        this.mUsesPolicies = val;
    }
}
