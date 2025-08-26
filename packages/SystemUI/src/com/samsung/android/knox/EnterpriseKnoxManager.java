package com.samsung.android.knox;

import android.content.Context;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.knox.EdmConstants;
import com.samsung.android.knox.ISemPersonaManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.integrity.EnhancedAttestationPolicy;
import com.samsung.android.knox.keystore.CertificatePolicy;
import com.samsung.android.knox.keystore.ClientCertificateManager;
import com.samsung.android.knox.keystore.EnterpriseCertEnrollPolicy;
import com.samsung.android.knox.keystore.TimaKeystore;
import com.samsung.android.knox.kpm.KnoxPushService;
import com.samsung.android.knox.license.KnoxEnterpriseLicenseManager;
import com.samsung.android.knox.log.AuditLog;
import com.samsung.android.knox.net.billing.EnterpriseBillingPolicy;
import com.samsung.android.knox.net.nap.NetworkAnalytics;
import com.samsung.android.knox.net.vpn.GenericVpnPolicy;
import com.samsung.android.knox.net.vpn.KnoxVpnContext;
import com.samsung.android.knox.restriction.AdvancedRestrictionPolicy;
import com.samsung.android.knox.threatdefense.ThreatDefensePolicy;
import com.samsung.android.knox.zt.networktrust.filter.NetworkFilterManager;
import java.util.HashMap;
import java.util.Objects;

/* loaded from: classes4.dex */
public class EnterpriseKnoxManager {
    public static final int DEVICE_KNOXIFIED = 1;
    public static final int DEVICE_NOT_KNOXIFIED = 0;
    public static final String KNOX_ENTERPRISE_POLICY_SERVICE = "knox_enterprise_policy";
    public static String TAG = "EnterpriseKnoxManager";
    public static EnterpriseKnoxManager gEKM;
    public static EnterpriseKnoxManager mParentInstance;
    public static final Object mSync = new Object();
    public AdvancedRestrictionPolicy mAdvancedRestrictionPolicy;
    public boolean mAdvancedRestrictionPolicyCreated;
    public AuditLog mAuditLogPolicy;
    public boolean mAuditLogPolicyCreated;
    public CertificatePolicy mCertificatePolicy;
    public boolean mCertificatePolicyCreated;
    public ClientCertificateManager mClientCertificateManagerPolicy;
    public boolean mClientCertificateManagerPolicyCreated;
    public final Context mContext;
    public final ContextInfo mContextInfo;
    public EnterpriseBillingPolicy mEnterpriseBillingPolicy;
    public boolean mEnterpriseBillingPolicyCreated;
    public HashMap<Integer, Pair<Integer, KnoxContainerManager>> mKnoxContainerMgrMap;
    public KnoxEnterpriseLicenseManager mKnoxEnterpriseLicenseManager;
    public boolean mKnoxEnterpriseLicenseManagerCreated;
    public NetworkAnalytics mNetworkAnalytics;
    public boolean mNetworkAnalyticsCreated;
    public NetworkFilterManager mNwFilterMgr;
    public boolean mNwFilterMgrPolicyCreated;
    public ThreatDefensePolicy mThreatDefensePolicy;
    public boolean mThreatDefensePolicyCreated;
    public TimaKeystore mTimaKeystorePolicy;
    public boolean mTimaKeystorePolicyCreated;

    public enum EnterpriseKnoxSdkVersion {
        KNOX_ENTERPRISE_SDK_VERSION_NONE,
        KNOX_ENTERPRISE_SDK_VERSION_1_0,
        KNOX_ENTERPRISE_SDK_VERSION_1_0_1,
        KNOX_ENTERPRISE_SDK_VERSION_1_0_2,
        KNOX_ENTERPRISE_SDK_VERSION_1_1_0,
        KNOX_ENTERPRISE_SDK_VERSION_1_2_0,
        KNOX_ENTERPRISE_SDK_VERSION_2_0,
        KNOX_ENTERPRISE_SDK_VERSION_2_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_2,
        KNOX_ENTERPRISE_SDK_VERSION_2_3,
        KNOX_ENTERPRISE_SDK_VERSION_2_4,
        KNOX_ENTERPRISE_SDK_VERSION_2_4_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_5,
        KNOX_ENTERPRISE_SDK_VERSION_2_5_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_6,
        KNOX_ENTERPRISE_SDK_VERSION_2_7,
        KNOX_ENTERPRISE_SDK_VERSION_2_7_1,
        KNOX_ENTERPRISE_SDK_VERSION_2_8,
        KNOX_ENTERPRISE_SDK_VERSION_2_9,
        KNOX_ENTERPRISE_SDK_VERSION_3_0,
        KNOX_ENTERPRISE_SDK_VERSION_3_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_2,
        KNOX_ENTERPRISE_SDK_VERSION_3_2_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_3,
        KNOX_ENTERPRISE_SDK_VERSION_3_4,
        KNOX_ENTERPRISE_SDK_VERSION_3_4_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_5,
        KNOX_ENTERPRISE_SDK_VERSION_3_6,
        KNOX_ENTERPRISE_SDK_VERSION_3_7,
        KNOX_ENTERPRISE_SDK_VERSION_3_7_1,
        KNOX_ENTERPRISE_SDK_VERSION_3_8,
        KNOX_ENTERPRISE_SDK_VERSION_3_9,
        KNOX_ENTERPRISE_SDK_VERSION_3_10,
        KNOX_ENTERPRISE_SDK_VERSION_3_11,
        KNOX_ENTERPRISE_SDK_VERSION_3_12;

        public String getInternalVersion() {
            return EdmConstants.getEnterpriseKnoxSdkVersion().getInternalVersion();
        }
    }

    public EnterpriseKnoxManager(ContextInfo contextInfo) {
        this.mEnterpriseBillingPolicyCreated = false;
        this.mNetworkAnalyticsCreated = false;
        this.mCertificatePolicyCreated = false;
        this.mKnoxEnterpriseLicenseManagerCreated = false;
        this.mAuditLogPolicyCreated = false;
        this.mAdvancedRestrictionPolicyCreated = false;
        this.mClientCertificateManagerPolicyCreated = false;
        this.mTimaKeystorePolicyCreated = false;
        this.mThreatDefensePolicyCreated = false;
        this.mNwFilterMgrPolicyCreated = false;
        this.mKnoxContainerMgrMap = new HashMap<>();
        this.mContextInfo = contextInfo;
        this.mContext = null;
    }

    public static EnterpriseKnoxManager createInstance(ContextInfo contextInfo) {
        return new EnterpriseKnoxManager(contextInfo);
    }

    public static int getDeviceKnoxifiedState() {
        return "1".equals(SystemProperties.get("ro.config.knoxtakeover", "0")) ? 1 : 0;
    }

    public static EnterpriseKnoxManager getInstance() {
        EnterpriseKnoxManager enterpriseKnoxManager;
        synchronized (mSync) {
            try {
                if (gEKM == null) {
                    gEKM = new EnterpriseKnoxManager(new ContextInfo(Process.myUid()));
                }
                enterpriseKnoxManager = gEKM;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseKnoxManager;
    }

    public static EnterpriseKnoxManager getParentInstance(Context context) {
        EnterpriseKnoxManager enterpriseKnoxManager;
        if (!AccessController.enforceWpcod()) {
            return null;
        }
        synchronized (mSync) {
            EnterpriseKnoxManager enterpriseKnoxManager2 = mParentInstance;
            if (enterpriseKnoxManager2 == null || (context != null && enterpriseKnoxManager2.mContext == null)) {
                mParentInstance = new EnterpriseKnoxManager(new ContextInfo(Process.myUid(), true), context);
            }
            enterpriseKnoxManager = mParentInstance;
        }
        return enterpriseKnoxManager;
    }

    public AdvancedRestrictionPolicy getAdvancedRestrictionPolicy(Context context) {
        synchronized (AdvancedRestrictionPolicy.class) {
            try {
                if (!this.mAdvancedRestrictionPolicyCreated) {
                    this.mAdvancedRestrictionPolicy = new AdvancedRestrictionPolicy(this.mContextInfo, context);
                    this.mAdvancedRestrictionPolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mAdvancedRestrictionPolicy;
    }

    public AuditLog getAuditLogPolicy(Context context) {
        if (context == null) {
            return null;
        }
        synchronized (AuditLog.class) {
            try {
                if (!this.mAuditLogPolicyCreated) {
                    this.mAuditLogPolicy = AuditLog.createInstance(this.mContextInfo, context);
                    this.mAuditLogPolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mAuditLogPolicy;
    }

    public CertificatePolicy getCertificatePolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getCertificatePolicy");
        synchronized (CertificatePolicy.class) {
            try {
                if (!this.mCertificatePolicyCreated) {
                    this.mCertificatePolicy = new CertificatePolicy(this.mContextInfo);
                    this.mCertificatePolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mCertificatePolicy;
    }

    public ClientCertificateManager getClientCertificateManagerPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getClientCertificateManagerPolicy");
        synchronized (ClientCertificateManager.class) {
            try {
                if (!this.mClientCertificateManagerPolicyCreated) {
                    this.mClientCertificateManagerPolicy = new ClientCertificateManager(this.mContextInfo);
                    this.mClientCertificateManagerPolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mClientCertificateManagerPolicy;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public EnhancedAttestationPolicy getEnhancedAttestationPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getEnhancedAttestationPolicy");
        return EnhancedAttestationPolicy.getInstance(this.mContext);
    }

    public EnterpriseBillingPolicy getEnterpriseBillingPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getEnterpriseBillingPolicy");
        synchronized (EnterpriseBillingPolicy.class) {
            try {
                if (!this.mEnterpriseBillingPolicyCreated) {
                    EnterpriseBillingPolicy enterpriseBillingPolicy = new EnterpriseBillingPolicy(this.mContextInfo);
                    this.mEnterpriseBillingPolicy = enterpriseBillingPolicy;
                    Objects.toString(enterpriseBillingPolicy);
                    this.mEnterpriseBillingPolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mEnterpriseBillingPolicy;
    }

    public EnterpriseCertEnrollPolicy getEnterpriseCertEnrollPolicy(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getEnterpriseCertEnrollPolicy");
        return EnterpriseCertEnrollPolicy.getInstance(this.mContextInfo, str);
    }

    public GenericVpnPolicy getGenericVpnPolicy(String str, int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getGenericVpnPolicy");
        try {
            return GenericVpnPolicy.getInstance(this.mContext, new KnoxVpnContext(this.mContextInfo.mCallerUid, i, str));
        } catch (Exception e) {
            Log.e(TAG, "Exception at getGenericVpnPolicy" + Log.getStackTraceString(e));
            return null;
        }
    }

    public synchronized KnoxContainerManager getKnoxContainerManager(Context context, int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getKnoxContainerManager");
        return getKnoxContainerManager(context, new ContextInfo(Process.myUid(), i));
    }

    public KnoxEnterpriseLicenseManager getKnoxEnterpriseLicenseManager(Context context) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getKnoxEnterpriseLicenseManager");
        if (context == null) {
            return null;
        }
        synchronized (KnoxEnterpriseLicenseManager.class) {
            try {
                if (!this.mKnoxEnterpriseLicenseManagerCreated) {
                    this.mKnoxEnterpriseLicenseManager = KnoxEnterpriseLicenseManager.getInstance(context);
                    this.mKnoxEnterpriseLicenseManagerCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mKnoxEnterpriseLicenseManager;
    }

    public KnoxPushService getKnoxPushService() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getKnoxPushService");
        return KnoxPushService.getInstance(this.mContext);
    }

    public NetworkAnalytics getNetworkAnalytics(Context context) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getNetworkAnalytics");
        synchronized (NetworkAnalytics.class) {
            try {
                if (!this.mNetworkAnalyticsCreated) {
                    NetworkAnalytics networkAnalytics = NetworkAnalytics.getInstance(this.mContextInfo, context);
                    this.mNetworkAnalytics = networkAnalytics;
                    Objects.toString(networkAnalytics);
                    this.mNetworkAnalyticsCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mNetworkAnalytics;
    }

    public NetworkFilterManager getNetworkFilterManagerPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getNetworkFilterManagerPolicy");
        synchronized (NetworkFilterManager.class) {
            try {
                if (!this.mNwFilterMgrPolicyCreated) {
                    this.mNwFilterMgr = NetworkFilterManager.getInstance(this.mContextInfo, this.mContext);
                    this.mNwFilterMgrPolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mNwFilterMgr;
    }

    public ThreatDefensePolicy getThreatDefensePolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getThreatDefensePolicy");
        synchronized (ThreatDefensePolicy.class) {
            try {
                if (!this.mThreatDefensePolicyCreated) {
                    this.mThreatDefensePolicy = new ThreatDefensePolicy(this.mContextInfo, this.mContext);
                    this.mThreatDefensePolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mThreatDefensePolicy;
    }

    public TimaKeystore getTimaKeystorePolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getTimaKeystorePolicy");
        synchronized (TimaKeystore.class) {
            try {
                if (!this.mTimaKeystorePolicyCreated) {
                    this.mTimaKeystorePolicy = new TimaKeystore(this.mContextInfo);
                    this.mTimaKeystorePolicyCreated = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return this.mTimaKeystorePolicy;
    }

    public EdmConstants.EnterpriseKnoxSdkVersion getVersion() {
        return EdmConstants.getEnterpriseKnoxSdkVersion();
    }

    public static EnterpriseKnoxManager createInstance(ContextInfo contextInfo, Context context) {
        return new EnterpriseKnoxManager(contextInfo, context);
    }

    public synchronized KnoxContainerManager getKnoxContainerManager(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getKnoxContainerManager");
        return getKnoxContainerManager(new ContextInfo(Process.myUid(), i));
    }

    public static EnterpriseKnoxManager getInstance(ContextInfo contextInfo) {
        EnterpriseKnoxManager enterpriseKnoxManager;
        synchronized (mSync) {
            try {
                if (gEKM == null) {
                    gEKM = new EnterpriseKnoxManager(contextInfo);
                }
                enterpriseKnoxManager = gEKM;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseKnoxManager;
    }

    public synchronized KnoxContainerManager getKnoxContainerManager(Context context, ContextInfo contextInfo) {
        KnoxContainerManager knoxContainerManager;
        AccessController.throwIfParentInstance(this.mContextInfo, "getKnoxContainerManager");
        int i = contextInfo.mContainerId;
        ISemPersonaManager.Stub.asInterface(ServiceManager.getService("persona"));
        if (this.mKnoxContainerMgrMap.containsKey(Integer.valueOf(i))) {
            if (((Integer) this.mKnoxContainerMgrMap.get(Integer.valueOf(i)).first).intValue() == contextInfo.mCallerUid) {
                return (KnoxContainerManager) this.mKnoxContainerMgrMap.get(Integer.valueOf(i)).second;
            }
            this.mKnoxContainerMgrMap.remove(Integer.valueOf(i));
        }
        KnoxContainerManager knoxContainerManager2 = null;
        if (contextInfo.mContainerId <= 0) {
            return null;
        }
        try {
            knoxContainerManager = new KnoxContainerManager(context, contextInfo);
        } catch (NoSuchFieldException e) {
            e = e;
        }
        try {
            this.mKnoxContainerMgrMap.put(Integer.valueOf(i), new Pair<>(Integer.valueOf(contextInfo.mCallerUid), knoxContainerManager));
        } catch (NoSuchFieldException e2) {
            e = e2;
            knoxContainerManager2 = knoxContainerManager;
            Log.e(TAG, "Failed at KnoxContainerManager API getKnoxContainerManager ", e);
            knoxContainerManager = knoxContainerManager2;
            return knoxContainerManager;
        }
        return knoxContainerManager;
    }

    public AdvancedRestrictionPolicy getAdvancedRestrictionPolicy() {
        return getAdvancedRestrictionPolicy(this.mContext);
    }

    public AuditLog getAuditLogPolicy() {
        return getAuditLogPolicy(this.mContext);
    }

    public KnoxEnterpriseLicenseManager getKnoxEnterpriseLicenseManager() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getKnoxEnterpriseLicenseManager");
        return getKnoxEnterpriseLicenseManager(this.mContext);
    }

    public NetworkAnalytics getNetworkAnalytics() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getNetworkAnalytics");
        return getNetworkAnalytics(this.mContext);
    }

    public static EnterpriseKnoxManager getInstance(Context context) {
        EnterpriseKnoxManager enterpriseKnoxManager;
        synchronized (mSync) {
            EnterpriseKnoxManager enterpriseKnoxManager2 = gEKM;
            if (enterpriseKnoxManager2 == null || (context != null && enterpriseKnoxManager2.mContext == null)) {
                gEKM = new EnterpriseKnoxManager(new ContextInfo(Process.myUid()), context);
            }
            enterpriseKnoxManager = gEKM;
        }
        return enterpriseKnoxManager;
    }

    public EnterpriseKnoxManager(ContextInfo contextInfo, Context context) {
        this.mEnterpriseBillingPolicyCreated = false;
        this.mNetworkAnalyticsCreated = false;
        this.mCertificatePolicyCreated = false;
        this.mKnoxEnterpriseLicenseManagerCreated = false;
        this.mAuditLogPolicyCreated = false;
        this.mAdvancedRestrictionPolicyCreated = false;
        this.mClientCertificateManagerPolicyCreated = false;
        this.mTimaKeystorePolicyCreated = false;
        this.mThreatDefensePolicyCreated = false;
        this.mNwFilterMgrPolicyCreated = false;
        this.mKnoxContainerMgrMap = new HashMap<>();
        this.mContextInfo = contextInfo;
        this.mContext = context;
    }

    public static EnterpriseKnoxManager getInstance(ContextInfo contextInfo, Context context) {
        EnterpriseKnoxManager enterpriseKnoxManager;
        synchronized (mSync) {
            EnterpriseKnoxManager enterpriseKnoxManager2 = gEKM;
            if (enterpriseKnoxManager2 == null || (context != null && enterpriseKnoxManager2.mContext == null)) {
                gEKM = new EnterpriseKnoxManager(contextInfo, context);
            }
            enterpriseKnoxManager = gEKM;
        }
        return enterpriseKnoxManager;
    }

    public synchronized KnoxContainerManager getKnoxContainerManager(ContextInfo contextInfo) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getKnoxContainerManager");
        return getKnoxContainerManager(this.mContext, contextInfo);
    }
}
