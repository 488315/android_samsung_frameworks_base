package com.samsung.android.knox.analytics.activation;

import android.content.Context;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.SystemProperties;
import android.provider.Settings;

import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.license.EnterpriseLicenseService;
import com.android.server.enterprise.license.IActivationKlmElmObserver;

import com.samsung.android.knox.analytics.activation.model.ActivationInfo;
import com.samsung.android.knox.analytics.activation.model.IActivationObserver;
import com.samsung.android.knox.analytics.util.B2CFeatures;
import com.samsung.android.knox.analytics.util.Log;
import com.samsung.android.knox.analytics.util.UserManagerHelper;
import com.samsung.android.knox.license.LicenseInfo;
import com.samsung.android.knox.license.LicenseResult;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ActivationMonitor implements IActivationKlmElmObserver {
    public static final boolean BLOCK_KNOX_ANALYTICS = false;
    public static final String CHINA_COUNTRY_CODE = "China";
    public static final String COUNTRY_CODE_PROPERTY = "ro.csc.country_code";
    public static final String[] ELM_PACKAGE_BLACKLIST = {"com.sec.android.app.shealth"};
    public static final String[] PACKAGE_TRIGGER_BLACKLISTED = {"com.samsung.knox.securefolder"};
    public static final String SETTINGS_KEY_KES_STATUS = "is_kes_enabled";
    public static final String SETTINGS_KEY_KLM_ON_PREMISE_STATUS = "onpremise_activated";
    public static final String SETTINGS_KEY_KLM_STATUS = "klm_activated";
    public static final String TAG = "[KnoxAnalytics] ActivationMonitor";
    public B2CListener mB2CListener;
    public Context mContext;
    public DevicePolicyListener mDevicePolicyListener;
    public KESListener mKESListener;
    public UserManagerHelper mUserManagerHelper;
    public ActivationInfo.ActivationStatus mActivationStatus = ActivationInfo.ActivationStatus.OFF;
    public EnterpriseLicenseService mLicenseService = null;
    public BitSet mTriggers = new BitSet();
    public boolean mIsOnBootCheckings = true;
    public boolean mIsOnPremiseActivated = false;
    public boolean mIsChinaDevice = false;
    public List mObservers = new ArrayList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.knox.analytics.activation.ActivationMonitor$2, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[]
                $SwitchMap$com$samsung$android$knox$analytics$activation$model$ActivationInfo$ActivationStatus;

        static {
            int[] iArr = new int[ActivationInfo.ActivationStatus.values().length];
            $SwitchMap$com$samsung$android$knox$analytics$activation$model$ActivationInfo$ActivationStatus =
                    iArr;
            try {
                iArr[ActivationInfo.ActivationStatus.ON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$analytics$activation$model$ActivationInfo$ActivationStatus[
                                ActivationInfo.ActivationStatus.OFF.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$samsung$android$knox$analytics$activation$model$ActivationInfo$ActivationStatus[
                                ActivationInfo.ActivationStatus.OFF_FORCEFUL.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public enum TRIGGERS {
        ELM(1, "ELM", 0),
        KLM(2, "KLM", 1),
        DO(3, "DO", 2),
        PO(4, "PO", 3),
        KME(5, "KES", 4),
        B2C(6, "B2C", 5);

        private final int mId;
        private final String mName;
        private final int mValue;

        TRIGGERS(int i, String str, int i2) {
            this.mValue = i;
            this.mName = str;
            this.mId = i2;
        }

        public final int getId() {
            return this.mId;
        }

        public final String getName() {
            return this.mName;
        }

        public final int getValue() {
            return this.mValue;
        }
    }

    public ActivationMonitor(Context context) {
        Log.d(TAG, "constructor()");
        this.mContext = context;
        this.mUserManagerHelper = new UserManagerHelper(this.mContext);
    }

    public static List filterOutBlacklistedElm(LicenseInfo[] licenseInfoArr) {
        String str = TAG;
        Log.d(str, "filterOutBlacklistedElm()");
        ArrayList arrayList = new ArrayList();
        if (licenseInfoArr == null) {
            Log.d(str, "filterOutBlacklistedElm(): empty LicenseInfo array");
            return arrayList;
        }
        for (LicenseInfo licenseInfo : licenseInfoArr) {
            if (!isPackageBlacklisted(licenseInfo.getPackageName())) {
                arrayList.add(licenseInfo);
            }
        }
        return arrayList;
    }

    public static boolean isPackageAllowedToRunAnalytics(String str) {
        Log.d(TAG, "isPackageAllowedToRunAnalytics(" + str + ")");
        String[] strArr = PACKAGE_TRIGGER_BLACKLISTED;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            if (strArr[i].equals(str)) {
                Log.d(TAG, "isPackageAllowedToRunAnalytics(): Not allowed");
                return false;
            }
        }
        return true;
    }

    public static boolean isPackageBlacklisted(String str) {
        Log.d(TAG, "isPackageBlacklisted(" + str + ")");
        if (str == null) {
            return false;
        }
        for (String str2 : ELM_PACKAGE_BLACKLIST) {
            if (str2.equals(str)) {
                Log.d(TAG, "isPackageBlacklisted(): blacklisted");
                return true;
            }
        }
        return false;
    }

    public final void bootChecking() {
        if (this.mIsChinaDevice) {
            Log.d(TAG, "bootChecking() - Device country is china, returning ...");
        } else {
            Log.d(TAG, "bootChecking()");
            new Thread(
                            new Runnable() { // from class:
                                             // com.samsung.android.knox.analytics.activation.ActivationMonitor.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    String str = ActivationMonitor.TAG;
                                    Log.d(str, "bootChecking() - run");
                                    ActivationMonitor activationMonitor = ActivationMonitor.this;
                                    activationMonitor.mIsOnBootCheckings = true;
                                    if (activationMonitor.mTriggers.cardinality() == 0) {
                                        ActivationMonitor.this.checkDOOnBoot();
                                    }
                                    if (ActivationMonitor.this.mTriggers.cardinality() == 0) {
                                        ActivationMonitor.this.checkPOOnBoot();
                                    }
                                    if (ActivationMonitor.this.mTriggers.cardinality() == 0) {
                                        ActivationMonitor.this.checkKLMOnBoot();
                                    }
                                    if (ActivationMonitor.this.mTriggers.cardinality() == 0) {
                                        ActivationMonitor.this.checkKesOnBoot();
                                    }
                                    if (ActivationMonitor.this.mTriggers.cardinality() == 0) {
                                        ActivationMonitor.this.checkELMOnBoot();
                                    }
                                    if (ActivationMonitor.this.mTriggers.cardinality() == 0) {
                                        ActivationMonitor.this.checkB2COnBoot();
                                    }
                                    ActivationMonitor.this.checkConditionsToStart();
                                    ActivationMonitor.this.mIsOnBootCheckings = false;
                                    Log.d(str, "bootChecking() - finished");
                                }
                            })
                    .start();
        }
    }

    public final void checkB2C(String str, String str2, boolean z) {
        String str3 = TAG;
        StringBuilder m =
                InitialConfiguration$$ExternalSyntheticOutline0.m(
                        "checkB2C() - package ", str, " feature ", str2, " isActivation? ");
        m.append(z);
        Log.d(str3, m.toString());
        if (str != null) {
            B2CFeatures.addOrRemoveB2CFeature(this.mContext, str, str2, z);
        }
        setTrigger(TRIGGERS.B2C, B2CFeatures.getB2CActivationStatus(this.mContext), str, z);
    }

    public final void checkB2COnBoot() {
        Log.d(TAG, "checkB2ConBoot()");
        checkB2C(null, null, false);
    }

    public final void checkB2COnlyAndApplyFeatureWhitelist() {
        Log.d(TAG, "checkB2COnlyAndApplyFeatureWhitelist()");
        TRIGGERS triggers = TRIGGERS.B2C;
        if (!isTriggerSet(triggers)) {
            B2CFeatures.removeB2CFeaturesFromWhitelist(this.mContext);
        } else if (isOnlyB2CTriggerSet(triggers)) {
            B2CFeatures.applyWhitelistForB2CFeatures(this.mContext);
        } else {
            B2CFeatures.removeB2CFeaturesFromWhitelist(this.mContext);
        }
    }

    public final void checkChina() {
        String str = SystemProperties.get(COUNTRY_CODE_PROPERTY);
        this.mIsChinaDevice = str != null && str.equalsIgnoreCase(CHINA_COUNTRY_CODE);
        Log.d(TAG, "checkChina(): " + this.mIsChinaDevice);
    }

    public final synchronized void checkConditionsToStart() {
        String str = TAG;
        Log.d(str, "checkConditionsToStart()");
        ActivationInfo.ActivationStatus checkNewStatus = checkNewStatus();
        checkB2COnlyAndApplyFeatureWhitelist();
        if (checkNewStatus.equals(this.mActivationStatus)) {
            Log.d(str, "checkConditionsToStart() - already in the correct state");
            return;
        }
        this.mActivationStatus = checkNewStatus;
        int i =
                AnonymousClass2
                        .$SwitchMap$com$samsung$android$knox$analytics$activation$model$ActivationInfo$ActivationStatus[
                        checkNewStatus.ordinal()];
        if (i == 1) {
            notifyKnoxAnalyticsActivation(!this.mIsOnBootCheckings);
        } else if (i == 2) {
            notifyKnoxAnalyticsDeactivation(false);
        } else if (i == 3) {
            notifyKnoxAnalyticsDeactivation(true);
        }
    }

    public final void checkDO(String str, boolean z) {
        Log.d(TAG, "checkDO()");
        setTrigger(TRIGGERS.DO, this.mUserManagerHelper.isDoActive(), str, z);
    }

    public final void checkDOOnBoot() {
        Log.d(TAG, "checkDOOnBoot()");
        setTrigger(TRIGGERS.DO, this.mUserManagerHelper.isDoActive(), null, false);
    }

    public final void checkELM(String str, boolean z) {
        Log.d(TAG, "checkELM()");
        setTrigger(TRIGGERS.ELM, isElmActive(), str, z);
    }

    public final void checkELMOnBoot() {
        Log.d(TAG, "checkELMOnBoot()");
        checkELM(null, false);
    }

    public final void checkKLM(String str, boolean z) {
        Log.d(TAG, "checkKLM()");
        checkOnPremise();
        setTrigger(TRIGGERS.KLM, isKlmActive(), str, z);
    }

    public final void checkKLMOnBoot() {
        Log.d(TAG, "checkKLMOnBoot()");
        checkKLM(null, false);
    }

    public final void checkKes(String str, boolean z) {
        Log.d(TAG, "checkKes()");
        setTrigger(TRIGGERS.KME, isKesActive(), str, z);
    }

    public final void checkKesOnBoot() {
        Log.d(TAG, "checkKesOnBoot");
        checkKes(null, false);
    }

    public final ActivationInfo.ActivationStatus checkNewStatus() {
        if (this.mIsOnPremiseActivated) {
            return ActivationInfo.ActivationStatus.OFF_FORCEFUL;
        }
        boolean z = this.mTriggers.cardinality() > 0;
        Log.d(TAG, "checkNewStatus() = " + String.valueOf(z));
        return z ? ActivationInfo.ActivationStatus.ON : ActivationInfo.ActivationStatus.OFF;
    }

    public final void checkOnPremise() {
        int i =
                Settings.Secure.getInt(
                        this.mContext.getContentResolver(), SETTINGS_KEY_KLM_ON_PREMISE_STATUS, 0);
        boolean z = i == 1;
        String str = TAG;
        Log.d(str, "checkOnPremise(): " + i);
        if (z == this.mIsOnPremiseActivated) {
            Log.d(str, "checkOnPremise(): Didn't change, returning");
        } else {
            this.mIsOnPremiseActivated = z;
        }
    }

    public final void checkPOOnBoot() {
        Log.d(TAG, "checkPOOnBoot()");
        setTrigger(TRIGGERS.PO, this.mUserManagerHelper.isAnyPOActive(), null, false);
    }

    public final List getElmLicenseList() {
        EnterpriseLicenseService licenseService = getLicenseService();
        if (licenseService != null) {
            return filterOutBlacklistedElm(licenseService.getAllLicenseInfo());
        }
        Log.e(TAG, "getElmLicenseList(): Error getting ELS");
        return null;
    }

    public final EnterpriseLicenseService getLicenseService() {
        if (this.mLicenseService == null) {
            this.mLicenseService =
                    (EnterpriseLicenseService)
                            EnterpriseService.getPolicyService("enterprise_license_policy");
        }
        return this.mLicenseService;
    }

    public final boolean isElmActive() {
        String str = TAG;
        Log.d(str, "isElmActive()");
        List elmLicenseList = getElmLicenseList();
        boolean z = elmLicenseList != null && elmLicenseList.size() > 0;
        Log.d(str, "isElmActive(): " + String.valueOf(z));
        return z;
    }

    public final boolean isKesActive() {
        boolean z =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(), SETTINGS_KEY_KES_STATUS, 0)
                        > 0;
        Log.d(TAG, "isKesActive(): " + String.valueOf(z));
        return z;
    }

    public final boolean isKlmActive() {
        boolean z =
                Settings.Secure.getInt(
                                this.mContext.getContentResolver(), SETTINGS_KEY_KLM_STATUS, 0)
                        > 0;
        Log.d(TAG, "checkKLM(): " + String.valueOf(z));
        return z;
    }

    public final boolean isOnlyB2CTriggerSet(TRIGGERS triggers) {
        BitSet bitSet = (BitSet) this.mTriggers.clone();
        bitSet.flip(TRIGGERS.B2C.getValue());
        return bitSet.cardinality() == 0;
    }

    public final boolean isTriggerSet(TRIGGERS triggers) {
        return this.mTriggers.get(triggers.getValue());
    }

    public final void notifyKnoxAnalyticsActivation(boolean z) {
        Log.d(TAG, "notifyKnoxAnalyticsActivation()");
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IActivationObserver) it.next()).onKnoxAnalyticsActivation(z);
        }
    }

    public final void notifyKnoxAnalyticsDeactivation(boolean z) {
        Log.d(TAG, "notifyKnoxAnalyticsDeactivation()");
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IActivationObserver) it.next()).onKnoxAnalyticsDeactivation(z);
        }
    }

    public final void notifyStatusChanged(int i, boolean z, String str) {
        String str2 = TAG;
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "notifyStatusChanged() pkg: ", str, " trigger: ", " status ");
        m.append(z);
        Log.d(str2, m.toString());
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IActivationObserver) it.next()).onStatusChanged(i, z, str);
        }
    }

    public final void notifyTriggerChanged(int i, boolean z, String str) {
        String str2 = TAG;
        StringBuilder m =
                StorageManagerService$$ExternalSyntheticOutline0.m(
                        i, "notifyTriggerChanged() pkg: ", str, " trigger: ", " status ");
        m.append(z);
        Log.d(str2, m.toString());
        Iterator it = this.mObservers.iterator();
        while (it.hasNext()) {
            ((IActivationObserver) it.next()).onTriggerChanged(i, z, str);
        }
    }

    public final void onBootPhase(int i) {
        if (this.mIsChinaDevice) {
            Log.d(TAG, "onBootPhase() - Device country is china, returning ...");
            return;
        }
        if (i == 480) {
            Log.d(TAG, "onBootPhase(PHASE_LOCK_SETTINGS_READY)");
            return;
        }
        if (i == 500) {
            Log.d(TAG, "onBootPhase(PHASE_SYSTEM_SERVICES_READY)");
            return;
        }
        if (i != 600) {
            if (i != 1000) {
                return;
            }
            Log.d(TAG, "onBootPhase(PHASE_BOOT_COMPLETED)");
        } else {
            Log.d(TAG, "onBootPhase(PHASE_SYSTEM_SERVICES_READY)");
            checkChina();
            bootChecking();
            registerListenersObservers();
        }
    }

    public final void onPoAdded(int i) {
        String poPackageName = this.mUserManagerHelper.getPoPackageName(i);
        if (poPackageName != null) {
            setTrigger(TRIGGERS.PO, this.mUserManagerHelper.isAnyPOActive(), poPackageName, true);
        }
    }

    public final void onPoRemoved(String str) {
        setTrigger(TRIGGERS.PO, this.mUserManagerHelper.isAnyPOActive(), str, false);
    }

    @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
    public final /* bridge */ /* synthetic */ void onUpdateContainerLicenseStatus(String str) {}

    @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
    public final void onUpdateElm(String str, LicenseResult licenseResult) {
        if (!licenseResult.isSuccess()
                || licenseResult.getType() == LicenseResult.Type.ELM_VALIDATION) {
            return;
        }
        checkELM(str, licenseResult.isActivation());
    }

    @Override // com.android.server.enterprise.license.IActivationKlmElmObserver
    public final void onUpdateKlm(String str, LicenseResult licenseResult) {
        if (!licenseResult.isSuccess()
                || licenseResult.getType() == LicenseResult.Type.KLM_VALIDATION) {
            return;
        }
        checkKLM(str, licenseResult.isActivation());
    }

    public final void registerListenersObservers() {
        String str = TAG;
        Log.d(str, "registerListenersObservers()");
        DevicePolicyListener devicePolicyListener = new DevicePolicyListener(this, this.mContext);
        this.mDevicePolicyListener = devicePolicyListener;
        devicePolicyListener.register();
        KESListener kESListener = new KESListener(this, this.mContext);
        this.mKESListener = kESListener;
        kESListener.register();
        B2CListener b2CListener = new B2CListener(this, this.mContext);
        this.mB2CListener = b2CListener;
        b2CListener.register();
        EnterpriseLicenseService licenseService = getLicenseService();
        if (licenseService == null) {
            Log.e(
                    str,
                    "registerListenersObservers() - EnterpriseLicenseService is null, can't observe"
                        + " license");
        } else {
            licenseService.enforcePermission$1();
            ((ArrayList) licenseService.mKlmElmChangeList).add(this);
        }
    }

    public final void registerObserver(IActivationObserver iActivationObserver) {
        Log.d(TAG, "registerObserver()");
        this.mObservers.add(iActivationObserver);
    }

    public final void setTrigger(TRIGGERS triggers, boolean z, String str, boolean z2) {
        String str2 = TAG;
        Log.d(str2, "setTrigger(" + triggers.getValue() + ", " + String.valueOf(z) + ") , " + z2);
        BitSet bitSet = (BitSet) this.mTriggers.clone();
        if (isPackageAllowedToRunAnalytics(str)) {
            if (!this.mIsOnBootCheckings
                    && !this.mIsOnPremiseActivated
                    && !isPackageBlacklisted(str)) {
                notifyStatusChanged(triggers.getId(), z2, str);
            }
            if (z) {
                this.mTriggers.set(triggers.getValue());
            } else {
                this.mTriggers.set(triggers.getValue(), false);
            }
            if (this.mTriggers.equals(bitSet)) {
                Log.d(str2, "setTrigger(): didn't change, returning");
            } else {
                if (this.mIsOnBootCheckings) {
                    return;
                }
                if (!this.mIsOnPremiseActivated && !isPackageBlacklisted(str)) {
                    notifyTriggerChanged(triggers.getId(), z, str);
                }
                checkConditionsToStart();
            }
        }
    }

    public final boolean unregisterObserver(IActivationObserver iActivationObserver) {
        return this.mObservers.remove(iActivationObserver);
    }
}
