package android.app.admin;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Printer;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.R;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
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
    public @interface HeadlessDeviceOwnerMode {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        public final String tag;

        public PolicyInfo(int i, String str, int i2, int i3) {
            this(i, str, i2, i3, i2, i3);
        }

        public PolicyInfo(int i, String str, int i2, int i3, int i4, int i5) {
            this.ident = i;
            this.tag = str;
            this.label = i2;
            this.description = i3;
            this.labelForSecondaryUsers = i4;
            this.descriptionForSecondaryUsers = i5;
        }
    }

    static {
        sPoliciesDisplayOrder.add(new PolicyInfo(4, "wipe-data", R.string.policylab_wipeData, R.string.policydesc_wipeData, R.string.policylab_wipeData_secondaryUser, R.string.policydesc_wipeData_secondaryUser));
        sPoliciesDisplayOrder.add(new PolicyInfo(2, "reset-password", R.string.policylab_resetPassword, R.string.policydesc_resetPassword));
        sPoliciesDisplayOrder.add(new PolicyInfo(0, "limit-password", R.string.policylab_limitPassword, R.string.policydesc_limitPassword));
        sPoliciesDisplayOrder.add(new PolicyInfo(1, "watch-login", R.string.policylab_watchLogin, R.string.policydesc_watchLogin, R.string.policylab_watchLogin, R.string.policydesc_watchLogin_secondaryUser));
        sPoliciesDisplayOrder.add(new PolicyInfo(3, "force-lock", R.string.policylab_forceLock, R.string.policydesc_forceLock));
        sPoliciesDisplayOrder.add(new PolicyInfo(5, "set-global-proxy", R.string.policylab_setGlobalProxy, R.string.policydesc_setGlobalProxy));
        sPoliciesDisplayOrder.add(new PolicyInfo(6, "expire-password", R.string.policylab_expirePassword, R.string.policydesc_expirePassword));
        sPoliciesDisplayOrder.add(new PolicyInfo(7, "encrypted-storage", R.string.policylab_encryptedStorage, R.string.policydesc_encryptedStorage));
        sPoliciesDisplayOrder.add(new PolicyInfo(8, "disable-camera", R.string.policylab_disableCamera, R.string.policydesc_disableCamera));
        sPoliciesDisplayOrder.add(new PolicyInfo(9, "disable-keyguard-features", R.string.policylab_disableKeyguardFeatures, R.string.policydesc_disableKeyguardFeatures));
        sPoliciesDisplayOrder.add(new PolicyInfo(20, "require-storagecard-encryption", R.string.policylab_require_storagecard_encryption, R.string.policydesc_require_storagecard_encryption));
        sPoliciesDisplayOrder.add(new PolicyInfo(10, "recover-password", R.string.policylab_recoverPassword, R.string.policydesc_recoverPassword));
        sPoliciesDisplayOrder.add(new PolicyInfo(14, "allow-popimapemail", R.string.policylab_allow_popimapemail, R.string.policydesc_allow_popimapemail));
        sPoliciesDisplayOrder.add(new PolicyInfo(11, "allow-storagecard", R.string.policylab_allow_storagecard, R.string.policydesc_allow_storagecard));
        sPoliciesDisplayOrder.add(new PolicyInfo(12, "allow-wifi", R.string.policylab_allow_wifi, R.string.policydesc_allow_wifi));
        sPoliciesDisplayOrder.add(new PolicyInfo(13, "allow-textmessaging", R.string.policylab_allow_textmessaging, R.string.policydesc_allow_textmessaging));
        sPoliciesDisplayOrder.add(new PolicyInfo(15, "allow-browser", R.string.policylab_allow_browser, R.string.policydesc_allow_browser));
        sPoliciesDisplayOrder.add(new PolicyInfo(16, "allow-internetsharing", R.string.policylab_allow_internetsharing, R.string.policydesc_allow_internetsharing));
        sPoliciesDisplayOrder.add(new PolicyInfo(17, "allow-bluetoothmode", R.string.policylab_allow_bluetoothmode, R.string.policydesc_allow_bluetoothmode));
        sPoliciesDisplayOrder.add(new PolicyInfo(18, "allow-desktopsync", R.string.policylab_allow_desktopsync, R.string.policydesc_allow_desktopsync));
        sPoliciesDisplayOrder.add(new PolicyInfo(19, "allow-irda", R.string.policylab_allow_irda, R.string.policydesc_allow_irda));
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            sRevKnownPolicies.put(policyInfo.ident, policyInfo);
            sKnownPolicies.put(policyInfo.tag, Integer.valueOf(policyInfo.ident));
        }
        CREATOR = new Parcelable.Creator<DeviceAdminInfo>() { // from class: android.app.admin.DeviceAdminInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DeviceAdminInfo createFromParcel(Parcel parcel) {
                return new DeviceAdminInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DeviceAdminInfo[] newArray(int i2) {
                return new DeviceAdminInfo[i2];
            }
        };
    }

    public DeviceAdminInfo(Context context, ResolveInfo resolveInfo) throws XmlPullParserException, IOException {
        this(context, resolveInfo.activityInfo);
    }

    public DeviceAdminInfo(Context context, ActivityInfo activityInfo) throws XmlPullParserException, IOException {
        int next;
        this.mHeadlessDeviceOwnerMode = 0;
        this.mActivityInfo = activityInfo;
        PackageManager packageManager = context.getPackageManager();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser loadXmlMetaData = activityInfo.loadXmlMetaData(packageManager, DeviceAdminReceiver.DEVICE_ADMIN_META_DATA);
                try {
                    if (loadXmlMetaData == null) {
                        throw new XmlPullParserException("No android.app.device_admin meta-data");
                    }
                    Resources resourcesForApplication = packageManager.getResourcesForApplication(activityInfo.applicationInfo);
                    AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!"device-admin".equals(loadXmlMetaData.getName())) {
                        throw new XmlPullParserException("Meta-data does not start with device-admin tag");
                    }
                    TypedArray obtainAttributes = resourcesForApplication.obtainAttributes(asAttributeSet, R.styleable.DeviceAdmin);
                    this.mVisible = obtainAttributes.getBoolean(0, true);
                    obtainAttributes.recycle();
                    int depth = loadXmlMetaData.getDepth();
                    while (true) {
                        int next2 = loadXmlMetaData.next();
                        if (next2 == 1 || (next2 == 3 && loadXmlMetaData.getDepth() <= depth)) {
                            break;
                        }
                        if (next2 != 3 && next2 != 4) {
                            String name = loadXmlMetaData.getName();
                            if (name.equals("uses-policies")) {
                                int depth2 = loadXmlMetaData.getDepth();
                                while (true) {
                                    int next3 = loadXmlMetaData.next();
                                    if (next3 != 1 && (next3 != 3 || loadXmlMetaData.getDepth() > depth2)) {
                                        if (next3 != 3 && next3 != 4) {
                                            String name2 = loadXmlMetaData.getName();
                                            Integer num = sKnownPolicies.get(name2);
                                            if (num != null) {
                                                this.mUsesPolicies |= 1 << num.intValue();
                                            } else {
                                                Log.w(TAG, "Unknown tag under uses-policies of " + getComponent() + ": " + name2);
                                            }
                                        }
                                    }
                                }
                            } else if (name.equals("support-transfer-ownership")) {
                                if (loadXmlMetaData.next() != 3) {
                                    throw new XmlPullParserException("support-transfer-ownership tag must be empty.");
                                }
                                this.mSupportsTransferOwnership = true;
                            } else if (name.equals("headless-system-user")) {
                                String attributeValue = loadXmlMetaData.getAttributeValue(null, "headless-device-owner-mode");
                                attributeValue = attributeValue == null ? loadXmlMetaData.getAttributeValue(null, "device-owner-mode") : attributeValue;
                                if ("unsupported".equalsIgnoreCase(attributeValue)) {
                                    this.mHeadlessDeviceOwnerMode = 0;
                                } else if ("affiliated".equalsIgnoreCase(attributeValue)) {
                                    this.mHeadlessDeviceOwnerMode = 1;
                                } else if ("single_user".equalsIgnoreCase(attributeValue)) {
                                    this.mHeadlessDeviceOwnerMode = 2;
                                } else {
                                    Log.e(TAG, "Unknown headless-system-user mode: " + attributeValue);
                                }
                            }
                        }
                    }
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    xmlResourceParser = loadXmlMetaData;
                    throw new XmlPullParserException("Unable to create context for: " + this.mActivityInfo.packageName);
                } catch (Throwable th) {
                    th = th;
                    xmlResourceParser = loadXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (PackageManager.NameNotFoundException unused2) {
        }
    }

    DeviceAdminInfo(Parcel parcel) {
        this.mHeadlessDeviceOwnerMode = 0;
        this.mActivityInfo = ActivityInfo.CREATOR.createFromParcel(parcel);
        this.mUsesPolicies = parcel.readInt();
        this.mSupportsTransferOwnership = parcel.readBoolean();
        this.mHeadlessDeviceOwnerMode = parcel.readInt();
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

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mActivityInfo.loadLabel(packageManager);
    }

    public CharSequence loadDescription(PackageManager packageManager) throws Resources.NotFoundException {
        if (this.mActivityInfo.descriptionRes != 0) {
            return packageManager.getText(this.mActivityInfo.packageName, this.mActivityInfo.descriptionRes, this.mActivityInfo.applicationInfo);
        }
        throw new Resources.NotFoundException();
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mActivityInfo.loadIcon(packageManager);
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public boolean usesPolicy(int i) {
        return i < 22 && (this.mUsesPolicies & (1 << i)) != 0;
    }

    public String getTagForPolicy(int i) {
        return sRevKnownPolicies.get(i).tag;
    }

    public boolean supportsTransferOwnership() {
        return this.mSupportsTransferOwnership;
    }

    public int getHeadlessDeviceOwnerMode() {
        return this.mHeadlessDeviceOwnerMode;
    }

    public ArrayList<PolicyInfo> getUsedPolicies() {
        ArrayList<PolicyInfo> arrayList = new ArrayList<>();
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            if (usesPolicy(policyInfo.ident)) {
                arrayList.add(policyInfo);
            }
        }
        return arrayList;
    }

    public void writePoliciesToXml(TypedXmlSerializer typedXmlSerializer) throws IllegalArgumentException, IllegalStateException, IOException {
        typedXmlSerializer.attributeInt(null, "flags", this.mUsesPolicies);
    }

    public void readPoliciesFromXml(TypedXmlPullParser typedXmlPullParser) throws XmlPullParserException, IOException {
        this.mUsesPolicies = typedXmlPullParser.getAttributeInt(null, "flags");
    }

    public void dump(Printer printer, String str) {
        printer.println("mVisible: " + this.mVisible);
        printer.println("mUsesPolicies: " + this.mUsesPolicies);
        printer.println("mSupportsTransferOwnership: " + this.mSupportsTransferOwnership);
        printer.println("mHeadlessDeviceOwnerMode: " + this.mHeadlessDeviceOwnerMode);
        printer.println(str + "Receiver:");
        this.mActivityInfo.dump(printer, str + "  ");
    }

    public String toString() {
        return "DeviceAdminInfo{" + this.mActivityInfo.name + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mActivityInfo.writeToParcel(parcel, i);
        parcel.writeInt(this.mUsesPolicies);
        parcel.writeBoolean(this.mSupportsTransferOwnership);
        parcel.writeInt(this.mHeadlessDeviceOwnerMode);
    }

    public int getPermissions() {
        return this.mUsesPolicies;
    }

    public void setPermissions(int i) {
        this.mUsesPolicies = i;
    }
}
