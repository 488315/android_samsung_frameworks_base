package android.sec.enterprise;

import android.os.ServiceManager;
import android.sec.enterprise.IEDMProxy;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.sec.enterprise.auditlog.AuditLog;
import android.sec.enterprise.certificate.CertificatePolicy;
import android.sec.enterprise.kioskmode.KioskMode;
import java.util.List;

/* loaded from: classes3.dex */
public class EnterpriseDeviceManager {
    public static final String ACTION_KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL = "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL";
    private static final String EDM_CLASS_NAME_NEW = "com.samsung.android.knox.EnterpriseDeviceManager";
    public static final String ENTERPRISE_PROXY_SERVICE = "edm_proxy";
    public static final String EXTRA_USER_ID_INTERNAL = "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL";
    private static final String TAG = "EnterpriseDeviceManager";
    private static EnterpriseDeviceManager mInstance;
    private static boolean mInstanceCreated;
    private volatile ApplicationPolicy mApplicationPolicy;
    private volatile ApplicationRestrictionsManager mApplicationRestrictionsManager;
    private volatile AuditLog mAuditLog;
    private volatile BluetoothPolicy mBluetoothPolicy;
    private volatile BrowserPolicy mBrowserPolicy;
    private volatile CertificatePolicy mCertificatePolicy;
    private volatile DeviceAccountPolicy mDeviceAccountPolicy;
    private volatile DeviceInventory mDeviceInventory;
    private volatile KioskMode mKioskMode;
    private volatile PasswordPolicy mPasswordPolicy;
    private volatile PhoneRestrictionPolicy mPhonePolicy;
    private volatile RestrictionPolicy mRestrictionPolicy;
    private volatile RoamingPolicy mRoamingPolicy;
    private volatile WifiPolicy mWifiPolicy;

    public static class EDMProxyServiceHelper {
        private static IEDMProxy mService;

        public static IEDMProxy getService() {
            if (mService == null) {
                mService = IEDMProxy.Stub.asInterface(ServiceManager.getService(EnterpriseDeviceManager.ENTERPRISE_PROXY_SERVICE));
            }
            return mService;
        }
    }

    public static IEDMProxy getService() {
        return EDMProxyServiceHelper.getService();
    }

    public static EnterpriseDeviceManager getInstance() {
        synchronized (EnterpriseDeviceManager.class) {
            if (!mInstanceCreated) {
                mInstance = new EnterpriseDeviceManager();
                mInstanceCreated = true;
            }
        }
        return mInstance;
    }

    public ApplicationPolicy getApplicationPolicy() {
        ApplicationPolicy applicationPolicy;
        ApplicationPolicy applicationPolicy2 = this.mApplicationPolicy;
        if (applicationPolicy2 != null) {
            return applicationPolicy2;
        }
        synchronized (this) {
            applicationPolicy = this.mApplicationPolicy;
            if (applicationPolicy == null) {
                applicationPolicy = new ApplicationPolicy();
                this.mApplicationPolicy = applicationPolicy;
            }
        }
        return applicationPolicy;
    }

    public ApplicationRestrictionsManager getApplicationRestrictionsManager() {
        ApplicationRestrictionsManager applicationRestrictionsManager;
        ApplicationRestrictionsManager applicationRestrictionsManager2 = this.mApplicationRestrictionsManager;
        if (applicationRestrictionsManager2 != null) {
            return applicationRestrictionsManager2;
        }
        synchronized (this) {
            applicationRestrictionsManager = this.mApplicationRestrictionsManager;
            if (applicationRestrictionsManager == null) {
                applicationRestrictionsManager = new ApplicationRestrictionsManager();
                this.mApplicationRestrictionsManager = applicationRestrictionsManager;
            }
        }
        return applicationRestrictionsManager;
    }

    public RoamingPolicy getRoamingPolicy() {
        RoamingPolicy roamingPolicy;
        RoamingPolicy roamingPolicy2 = this.mRoamingPolicy;
        if (roamingPolicy2 != null) {
            return roamingPolicy2;
        }
        synchronized (this) {
            roamingPolicy = this.mRoamingPolicy;
            if (roamingPolicy == null) {
                roamingPolicy = new RoamingPolicy();
                this.mRoamingPolicy = roamingPolicy;
            }
        }
        return roamingPolicy;
    }

    public RestrictionPolicy getRestrictionPolicy() {
        RestrictionPolicy restrictionPolicy;
        RestrictionPolicy restrictionPolicy2 = this.mRestrictionPolicy;
        if (restrictionPolicy2 != null) {
            return restrictionPolicy2;
        }
        synchronized (this) {
            restrictionPolicy = this.mRestrictionPolicy;
            if (restrictionPolicy == null) {
                restrictionPolicy = new RestrictionPolicy();
                this.mRestrictionPolicy = restrictionPolicy;
            }
        }
        return restrictionPolicy;
    }

    public PasswordPolicy getPasswordPolicy() {
        PasswordPolicy passwordPolicy;
        PasswordPolicy passwordPolicy2 = this.mPasswordPolicy;
        if (passwordPolicy2 != null) {
            return passwordPolicy2;
        }
        synchronized (this) {
            passwordPolicy = this.mPasswordPolicy;
            if (passwordPolicy == null) {
                passwordPolicy = new PasswordPolicy();
                this.mPasswordPolicy = passwordPolicy;
            }
        }
        return passwordPolicy;
    }

    public DeviceInventory getDeviceInventory() {
        DeviceInventory deviceInventory;
        DeviceInventory deviceInventory2 = this.mDeviceInventory;
        if (deviceInventory2 != null) {
            return deviceInventory2;
        }
        synchronized (this) {
            deviceInventory = this.mDeviceInventory;
            if (deviceInventory == null) {
                deviceInventory = new DeviceInventory();
                this.mDeviceInventory = deviceInventory;
            }
        }
        return deviceInventory;
    }

    public BluetoothPolicy getBluetoothPolicy() {
        BluetoothPolicy bluetoothPolicy;
        BluetoothPolicy bluetoothPolicy2 = this.mBluetoothPolicy;
        if (bluetoothPolicy2 != null) {
            return bluetoothPolicy2;
        }
        synchronized (this) {
            bluetoothPolicy = this.mBluetoothPolicy;
            if (bluetoothPolicy == null) {
                bluetoothPolicy = new BluetoothPolicy();
                this.mBluetoothPolicy = bluetoothPolicy;
            }
        }
        return bluetoothPolicy;
    }

    public BrowserPolicy getBrowserPolicy() {
        BrowserPolicy browserPolicy;
        BrowserPolicy browserPolicy2 = this.mBrowserPolicy;
        if (browserPolicy2 != null) {
            return browserPolicy2;
        }
        synchronized (this) {
            browserPolicy = this.mBrowserPolicy;
            if (browserPolicy == null) {
                browserPolicy = new BrowserPolicy();
                this.mBrowserPolicy = browserPolicy;
            }
        }
        return browserPolicy;
    }

    public PhoneRestrictionPolicy getPhoneRestrictionPolicy() {
        PhoneRestrictionPolicy phoneRestrictionPolicy;
        PhoneRestrictionPolicy phoneRestrictionPolicy2 = this.mPhonePolicy;
        if (phoneRestrictionPolicy2 != null) {
            return phoneRestrictionPolicy2;
        }
        synchronized (this) {
            phoneRestrictionPolicy = this.mPhonePolicy;
            if (phoneRestrictionPolicy == null) {
                phoneRestrictionPolicy = new PhoneRestrictionPolicy();
                this.mPhonePolicy = phoneRestrictionPolicy;
            }
        }
        return phoneRestrictionPolicy;
    }

    public WifiPolicy getWifiPolicy() {
        WifiPolicy wifiPolicy;
        WifiPolicy wifiPolicy2 = this.mWifiPolicy;
        if (wifiPolicy2 != null) {
            return wifiPolicy2;
        }
        synchronized (this) {
            wifiPolicy = this.mWifiPolicy;
            if (wifiPolicy == null) {
                wifiPolicy = new WifiPolicy();
                this.mWifiPolicy = wifiPolicy;
            }
        }
        return wifiPolicy;
    }

    public KioskMode getKioskMode() {
        KioskMode kioskMode;
        KioskMode kioskMode2 = this.mKioskMode;
        if (kioskMode2 != null) {
            return kioskMode2;
        }
        synchronized (this) {
            kioskMode = this.mKioskMode;
            if (kioskMode == null) {
                kioskMode = new KioskMode();
                this.mKioskMode = kioskMode;
            }
        }
        return kioskMode;
    }

    public CertificatePolicy getCertificatePolicy() {
        CertificatePolicy certificatePolicy;
        CertificatePolicy certificatePolicy2 = this.mCertificatePolicy;
        if (certificatePolicy2 != null) {
            return certificatePolicy2;
        }
        synchronized (this) {
            certificatePolicy = this.mCertificatePolicy;
            if (certificatePolicy == null) {
                certificatePolicy = new CertificatePolicy();
                this.mCertificatePolicy = certificatePolicy;
            }
        }
        return certificatePolicy;
    }

    public AuditLog getAuditPolicy() {
        AuditLog auditLog;
        AuditLog auditLog2 = this.mAuditLog;
        if (auditLog2 != null) {
            return auditLog2;
        }
        synchronized (this) {
            auditLog = this.mAuditLog;
            if (auditLog == null) {
                auditLog = new AuditLog();
                this.mAuditLog = auditLog;
            }
        }
        return auditLog;
    }

    public DeviceAccountPolicy getDeviceAccountPolicy() {
        DeviceAccountPolicy deviceAccountPolicy;
        DeviceAccountPolicy deviceAccountPolicy2 = this.mDeviceAccountPolicy;
        if (deviceAccountPolicy2 != null) {
            return deviceAccountPolicy2;
        }
        synchronized (this) {
            deviceAccountPolicy = this.mDeviceAccountPolicy;
            if (deviceAccountPolicy == null) {
                deviceAccountPolicy = new DeviceAccountPolicy();
                this.mDeviceAccountPolicy = deviceAccountPolicy;
            }
        }
        return deviceAccountPolicy;
    }

    public List<String> getELMPermissions(String str) {
        try {
            IEDMProxy service = getService();
            if (service != null) {
                return service.getELMPermissions(str);
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    public boolean registerSystemUICallback(ISystemUIAdapterCallback iSystemUIAdapterCallback) {
        try {
            IEDMProxy service = getService();
            if (service != null) {
                return service.registerSystemUICallback(iSystemUIAdapterCallback);
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }
}
