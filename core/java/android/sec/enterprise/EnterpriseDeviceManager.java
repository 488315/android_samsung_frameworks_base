package android.sec.enterprise;

import android.os.ServiceManager;
import android.sec.enterprise.adapterlayer.ISystemUIAdapterCallback;
import android.sec.enterprise.auditlog.AuditLog;
import android.sec.enterprise.certificate.CertificatePolicy;
import android.sec.enterprise.kioskmode.KioskMode;
import java.util.List;

/* loaded from: classes3.dex */
public class EnterpriseDeviceManager {
  public static final String ACTION_KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL =
      "com.samsung.android.knox.intent.action.KNOXFRAMEWORK_SYSTEMUI_UPDATE_INTENT_INTERNAL";
  private static final String EDM_CLASS_NAME_NEW =
      "com.samsung.android.knox.EnterpriseDeviceManager";
  public static final String ENTERPRISE_PROXY_SERVICE = "edm_proxy";
  public static final String EXTRA_USER_ID_INTERNAL =
      "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL";
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
        mService =
            IEDMProxy.Stub.asInterface(
                ServiceManager.getService(EnterpriseDeviceManager.ENTERPRISE_PROXY_SERVICE));
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
    ApplicationPolicy result = this.mApplicationPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mApplicationPolicy;
        if (result == null) {
          ApplicationPolicy applicationPolicy = new ApplicationPolicy();
          result = applicationPolicy;
          this.mApplicationPolicy = applicationPolicy;
        }
      }
    }
    return result;
  }

  public ApplicationRestrictionsManager getApplicationRestrictionsManager() {
    ApplicationRestrictionsManager result = this.mApplicationRestrictionsManager;
    if (result == null) {
      synchronized (this) {
        result = this.mApplicationRestrictionsManager;
        if (result == null) {
          ApplicationRestrictionsManager applicationRestrictionsManager =
              new ApplicationRestrictionsManager();
          result = applicationRestrictionsManager;
          this.mApplicationRestrictionsManager = applicationRestrictionsManager;
        }
      }
    }
    return result;
  }

  public RoamingPolicy getRoamingPolicy() {
    RoamingPolicy result = this.mRoamingPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mRoamingPolicy;
        if (result == null) {
          RoamingPolicy roamingPolicy = new RoamingPolicy();
          result = roamingPolicy;
          this.mRoamingPolicy = roamingPolicy;
        }
      }
    }
    return result;
  }

  public RestrictionPolicy getRestrictionPolicy() {
    RestrictionPolicy result = this.mRestrictionPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mRestrictionPolicy;
        if (result == null) {
          RestrictionPolicy restrictionPolicy = new RestrictionPolicy();
          result = restrictionPolicy;
          this.mRestrictionPolicy = restrictionPolicy;
        }
      }
    }
    return result;
  }

  public PasswordPolicy getPasswordPolicy() {
    PasswordPolicy result = this.mPasswordPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mPasswordPolicy;
        if (result == null) {
          PasswordPolicy passwordPolicy = new PasswordPolicy();
          result = passwordPolicy;
          this.mPasswordPolicy = passwordPolicy;
        }
      }
    }
    return result;
  }

  public DeviceInventory getDeviceInventory() {
    DeviceInventory result = this.mDeviceInventory;
    if (result == null) {
      synchronized (this) {
        result = this.mDeviceInventory;
        if (result == null) {
          DeviceInventory deviceInventory = new DeviceInventory();
          result = deviceInventory;
          this.mDeviceInventory = deviceInventory;
        }
      }
    }
    return result;
  }

  public BluetoothPolicy getBluetoothPolicy() {
    BluetoothPolicy result = this.mBluetoothPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mBluetoothPolicy;
        if (result == null) {
          BluetoothPolicy bluetoothPolicy = new BluetoothPolicy();
          result = bluetoothPolicy;
          this.mBluetoothPolicy = bluetoothPolicy;
        }
      }
    }
    return result;
  }

  public BrowserPolicy getBrowserPolicy() {
    BrowserPolicy result = this.mBrowserPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mBrowserPolicy;
        if (result == null) {
          BrowserPolicy browserPolicy = new BrowserPolicy();
          result = browserPolicy;
          this.mBrowserPolicy = browserPolicy;
        }
      }
    }
    return result;
  }

  public PhoneRestrictionPolicy getPhoneRestrictionPolicy() {
    PhoneRestrictionPolicy result = this.mPhonePolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mPhonePolicy;
        if (result == null) {
          PhoneRestrictionPolicy phoneRestrictionPolicy = new PhoneRestrictionPolicy();
          result = phoneRestrictionPolicy;
          this.mPhonePolicy = phoneRestrictionPolicy;
        }
      }
    }
    return result;
  }

  public WifiPolicy getWifiPolicy() {
    WifiPolicy result = this.mWifiPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mWifiPolicy;
        if (result == null) {
          WifiPolicy wifiPolicy = new WifiPolicy();
          result = wifiPolicy;
          this.mWifiPolicy = wifiPolicy;
        }
      }
    }
    return result;
  }

  public KioskMode getKioskMode() {
    KioskMode result = this.mKioskMode;
    if (result == null) {
      synchronized (this) {
        result = this.mKioskMode;
        if (result == null) {
          KioskMode kioskMode = new KioskMode();
          result = kioskMode;
          this.mKioskMode = kioskMode;
        }
      }
    }
    return result;
  }

  public CertificatePolicy getCertificatePolicy() {
    CertificatePolicy result = this.mCertificatePolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mCertificatePolicy;
        if (result == null) {
          CertificatePolicy certificatePolicy = new CertificatePolicy();
          result = certificatePolicy;
          this.mCertificatePolicy = certificatePolicy;
        }
      }
    }
    return result;
  }

  public AuditLog getAuditPolicy() {
    AuditLog result = this.mAuditLog;
    if (result == null) {
      synchronized (this) {
        result = this.mAuditLog;
        if (result == null) {
          AuditLog auditLog = new AuditLog();
          result = auditLog;
          this.mAuditLog = auditLog;
        }
      }
    }
    return result;
  }

  public DeviceAccountPolicy getDeviceAccountPolicy() {
    DeviceAccountPolicy result = this.mDeviceAccountPolicy;
    if (result == null) {
      synchronized (this) {
        result = this.mDeviceAccountPolicy;
        if (result == null) {
          DeviceAccountPolicy deviceAccountPolicy = new DeviceAccountPolicy();
          result = deviceAccountPolicy;
          this.mDeviceAccountPolicy = deviceAccountPolicy;
        }
      }
    }
    return result;
  }

  public List<String> getELMPermissions(String packageName) {
    try {
      IEDMProxy lService = getService();
      if (lService == null) {
        return null;
      }
      return lService.getELMPermissions(packageName);
    } catch (Exception e) {
      return null;
    }
  }

  public boolean registerSystemUICallback(ISystemUIAdapterCallback callback) {
    try {
      IEDMProxy lService = getService();
      if (lService != null) {
        return lService.registerSystemUICallback(callback);
      }
      return false;
    } catch (Exception e) {
      return false;
    }
  }
}
