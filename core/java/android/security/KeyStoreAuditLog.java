package android.security;

import android.content.Context;
import android.media.p008tv.interactive.TvInteractiveAppView;
import android.p009os.IBinder;
import android.p009os.Process;
import android.p009os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.auditlog.AuditEvents;
import android.sec.enterprise.auditlog.AuditLog;
import android.sec.enterprise.certificate.CertificatePolicy;
import android.system.keystore2.KeyDescriptor;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.Pair;
import com.samsung.android.security.mdf.MdfUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class KeyStoreAuditLog {
  public static final int CLEAR = 1;
  public static final int DELETE = 2;
  public static final int EXECUTION_EXCEPTION = 201;
  public static final int GENERATE = 4;
  public static final int IMPORT = 5;
  public static final int INSERT = 3;
  private static final int INVALID_DOMAIN = -1;
  private static final int INVALID_NAMESPACE = 0;
  public static final int NO_ERROR = 1;
  public static final int NO_ERROR2 = 0;
  public static final int REMOTE_EXCEPTION = 200;
  private static final String TAG = "KeyStoreAuditLog";
  private static Pair<Long, KeyDescriptor> mKeyDescriptorBeforeImportKey = null;

  public static void setKeyDescriptorBeforeImportKey(long keyId, KeyDescriptor keyDescriptor) {
    mKeyDescriptorBeforeImportKey = Pair.create(Long.valueOf(keyId), keyDescriptor);
  }

  private KeyStoreAuditLog() {}

  public static void logMdfKeyGenFailed(String errorMsg, String className) {
    if (MdfUtils.isMdfEnforced()) {
      AuditLog.logPrivileged(
          3,
          1,
          false,
          Process.myPid(),
          className,
          String.format(AuditEvents.AUDIT_KEY_GENERATION_FAILED_WITH_ERROR, errorMsg));
    }
  }

  public static class AuditLogParams {
    private final String mAlias;
    private final String mClassName;
    private Context mContext;
    private byte[] mEncodedCerts;
    private int mOperationType;
    private List<X509Certificate> mX509Certificates;
    private long mNamespace = 0;
    private int mDomain = -1;
    private int mUserId = KeyStoreAuditLog.getUserId(Process.myUid());
    private int mErrorCode = 1;

    public AuditLogParams(String alias, String className) {
      this.mAlias = alias;
      this.mClassName = className;
    }

    public List<X509Certificate> getX509Certificates() {
      List<X509Certificate> list = this.mX509Certificates;
      if (list != null) {
        return list;
      }
      byte[] bArr = this.mEncodedCerts;
      return bArr != null ? KeyStoreAuditLog.toCertificates(bArr) : Collections.emptyList();
    }

    public void setX509Certificates(List<X509Certificate> x509Certificates) {
      this.mX509Certificates = x509Certificates;
    }

    public void setNamespace(long namespace) {
      this.mNamespace = namespace;
    }

    public long getNamespace() {
      return this.mNamespace;
    }

    public byte[] getChainBytes() {
      byte[] bArr = this.mEncodedCerts;
      if (bArr != null) {
        return bArr;
      }
      List<X509Certificate> list = this.mX509Certificates;
      if (list != null) {
        return KeyStoreAuditLog.convertCertificatesToPem(
            (Certificate[]) list.toArray(new X509Certificate[list.size()]));
      }
      return null;
    }

    public void setContext(Context context) {
      this.mContext = context;
    }

    public Context getContext() {
      return this.mContext;
    }

    public String getAlias() {
      return this.mAlias;
    }

    public String getClassName() {
      return this.mClassName;
    }

    public void setErrorCode(int errorCode) {
      this.mErrorCode = errorCode;
    }

    public int getErrorCode() {
      return this.mErrorCode;
    }

    public void setOperationType(int operationType) {
      this.mOperationType = operationType;
    }

    public int getOperationType() {
      return this.mOperationType;
    }

    public void setUserId(int userId) {
      this.mUserId = userId;
    }

    public int getUserId() {
      return this.mUserId;
    }

    public boolean hasCertificates() {
      List<X509Certificate> list = this.mX509Certificates;
      return ((list == null || list.isEmpty()) && this.mEncodedCerts == null) ? false : true;
    }

    public void setEncodedCerts(byte[] encodedCerts) {
      this.mEncodedCerts = encodedCerts;
    }

    public void setUserCertAndChain(byte[] userCert, byte[] chain) {
      if (userCert == null) {
        if (chain == null) {
          return;
        }
        setEncodedCerts(chain);
        return;
      }
      setX509Certificates(KeyStoreAuditLog.mergeUserCertAndChain(userCert, chain));
    }

    public void setDomain(int domain) {
      this.mDomain = domain;
    }

    public int getDomain() {
      return this.mDomain;
    }

    public static AuditLogParams init(KeyDescriptor keyDescriptor, int operation, String tag) {
      return init(keyDescriptor, operation, tag, 1);
    }

    public static AuditLogParams init(
        KeyDescriptor keyDescriptor, int operation, String tag, int errorCode) {
      return init(
          keyDescriptor.alias,
          keyDescriptor.nspace,
          keyDescriptor.domain,
          operation,
          tag,
          errorCode);
    }

    public static AuditLogParams init(
        String alias, long nspace, int domain, int operation, String tag, int errorCode) {
      AuditLogParams params = new AuditLogParams(alias, tag);
      params.setNamespace(nspace);
      params.setDomain(domain);
      params.setOperationType(operation);
      params.setErrorCode(errorCode);
      return params;
    }

    public String toString() {
      return "AuditLogParams{mAlias='"
          + this.mAlias
          + DateFormat.QUOTE
          + ", mClassName='"
          + this.mClassName
          + DateFormat.QUOTE
          + ", mUserId="
          + this.mUserId
          + ", mNamespace="
          + this.mNamespace
          + ", mDomain="
          + this.mDomain
          + ", mContext="
          + this.mContext
          + ", mOperationType="
          + this.mOperationType
          + ", mErrorCode="
          + this.mErrorCode
          + ", mX509Certificates="
          + this.mX509Certificates
          + ", mEncodedCerts="
          + Arrays.toString(this.mEncodedCerts)
          + '}';
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  static class LogMessage {
    public static final String KEEP = null;
    public static final String REMOVE = "";
    String message;
    String redactedMessage;

    public LogMessage(String message, String redactedMessage) {
      this.message = message;
      this.redactedMessage = redactedMessage;
    }

    public String toString() {
      return "LogMessage{message='"
          + this.message
          + DateFormat.QUOTE
          + ", redactedMessage='"
          + this.redactedMessage
          + DateFormat.QUOTE
          + '}';
    }
  }

  public static void auditLogPrivilegedAsUser(final AuditLogParams params) {
    List<LogMessage> logMessages = new ArrayList<>();
    boolean z = true;
    if (params.getErrorCode() != 1 && params.getErrorCode() != 0) {
      z = false;
    }
    final boolean success = z;
    String credentialUsage =
        getKeystoreString(params.getDomain(), params.getNamespace(), params.getOperationType());
    switch (params.getOperationType()) {
      case 1:
        logMessages.add(
            new LogMessage(
                String.format(
                    success
                        ? AuditEvents.AUDIT_CLEARING_CREDENTIALS_SUCCEEDED
                        : AuditEvents.AUDIT_CLEARING_CREDENTIALS_FAILED,
                    credentialUsage,
                    getErrorMessage(params.getErrorCode())),
                LogMessage.KEEP));
        break;
      case 2:
        if (params.hasCertificates()) {
          for (X509Certificate certificate : params.getX509Certificates()) {
            logMessages.add(
                new LogMessage(
                    String.format(
                        success
                            ? AuditEvents.AUDIT_DELETING_CERTIFICATE_SUCCEEDED
                            : AuditEvents.AUDIT_DELETING_CERTIFICATE_FAILED,
                        getKeyString(params.getAlias()),
                        credentialUsage,
                        params.getAlias(),
                        certificate.getSubjectDN(),
                        certificate.getIssuerDN()),
                    ""));
          }
          break;
        } else {
          logMessages.add(
              new LogMessage(
                  String.format(
                      success
                          ? AuditEvents.AUDIT_KEY_DESTRUCTION_ACTIVITY_SUCCEEDED
                          : AuditEvents.AUDIT_KEY_DESTRUCTION_ACTIVITY_FAILED,
                      credentialUsage,
                      params.getAlias(),
                      getRequesterInfo(params.getContext()),
                      getErrorMessage(params.getErrorCode())),
                  ""));
          break;
        }
      case 3:
        if (!credentialUsage.isEmpty()) {
          for (X509Certificate certificate2 : params.getX509Certificates()) {
            logMessages.add(
                new LogMessage(
                    String.format(
                        success
                            ? AuditEvents.AUDIT_INSTALLING_CERTIFICATE_SUCCEEDED
                            : AuditEvents.AUDIT_INSTALLING_CERTIFICATE_FAILED,
                        getKeyString(params.getAlias()),
                        credentialUsage,
                        params.getAlias(),
                        certificate2.getSubjectDN(),
                        certificate2.getIssuerDN(),
                        getErrorMessage(params.getErrorCode())),
                    ""));
          }
          break;
        }
        break;
      case 4:
        logMessages.add(
            new LogMessage(
                String.format(
                    AuditEvents.AUDIT_KEY_GENERATION_FAILED,
                    getErrorMessage(params.getErrorCode())),
                ""));
        break;
      case 5:
        logMessages.add(
            new LogMessage(
                String.format(
                    success
                        ? AuditEvents.AUDIT_KEY_IMPORTING_ACTIVITY_SUCCEEDED
                        : AuditEvents.AUDIT_KEY_IMPORTING_ACTIVITY_FAILED,
                    credentialUsage,
                    params.getAlias(),
                    getRequesterInfo(params.getContext()),
                    getErrorMessage(params.getErrorCode())),
                ""));
        break;
      default:
        return;
    }
    final int userId =
        getUserIdForDomainOrNamespace(
            params.getUserId(), params.getDomain(), params.getNamespace());
    logMessages.forEach(
        new Consumer() { // from class: android.security.KeyStoreAuditLog$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            AuditLog.logPrivilegedAsUser(
                success ? 5 : 1,
                1,
                success,
                Process.myPid(),
                params.getClassName(),
                r4.message,
                userId != -1 ? ((KeyStoreAuditLog.LogMessage) obj).redactedMessage : null,
                userId);
          }
        });
  }

  public static boolean isAuditLogEnabledAsUser() {
    return isAuditLogEnabledAsUser(getUserId(Process.myUid()));
  }

  public static boolean isAuditLogEnabledAsUser(int userId) {
    return AuditLog.isAuditLogEnabledAsUser(userId);
  }

  public static void notifyCertificateRemovedAsUser(AuditLogParams params) {
    List<X509Certificate> certList = params.getX509Certificates();
    int userId = params.getUserId();
    if (certList != null && certList.size() > 0) {
      CertificatePolicy certPolicy = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
      for (X509Certificate certificate : certList) {
        certPolicy.notifyCertificateRemovedAsUser(
            certificate.getSubjectX500Principal().getName(), userId);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static byte[] convertCertificatesToPem(Certificate[] certificates) {
    if (certificates == null) {
      return null;
    }
    try {
      return Credentials.convertToPem(certificates);
    } catch (IOException e) {
      Log.m96e(TAG, "Could not convert certificate.");
      return null;
    } catch (IllegalArgumentException ile) {
      Log.m94d(TAG, "Not a certificate " + ile.getMessage());
      return null;
    } catch (CertificateException e2) {
      Log.m96e(TAG, "Could not convert certificate.");
      return null;
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static List<X509Certificate> mergeUserCertAndChain(byte[] userCert, byte[] chain) {
    X509Certificate leaf = toCertificate(userCert);
    if (leaf == null) {
      return Collections.emptyList();
    }
    List<X509Certificate> certificates = new ArrayList<>();
    certificates.add(leaf);
    if (chain != null) {
      certificates.addAll(toCertificates(chain));
    }
    return certificates;
  }

  public static void checkCertificateTrustful(AuditLogParams params) throws KeyStoreException {
    CertificatePolicy certPolicy = EnterpriseDeviceManager.getInstance().getCertificatePolicy();
    byte[] value = params.getChainBytes();
    if (certPolicy == null || value == null) {
      return;
    }
    int userId = params.getUserId();
    boolean isCertificateTrustedByMdm = true;
    if (certPolicy.isCertificateTrustedUntrustedEnabledAsUser(userId)
        && !certPolicy.isCaCertificateTrustedAsUser(value, false, userId)) {
      isCertificateTrustedByMdm = false;
    }
    if (certPolicy.isCertificateValidationAtInstallEnabledAsUser(userId)
        && certPolicy.validateCertificateAtInstallAsUser(value, userId) != -1) {
      isCertificateTrustedByMdm = false;
    }
    if (!isCertificateTrustedByMdm) {
      throw new KeyStoreException(6, "Certificate not trusted by MDM");
    }
  }

  private static X509Certificate toCertificate(byte[] bytes) {
    if (bytes == null) {
      return null;
    }
    try {
      CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
      return (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(bytes));
    } catch (CertificateException e) {
      Log.m103w(TAG, "Couldn't parse certificate in keystore", e);
      return null;
    }
  }

  public static List<X509Certificate> toCertificates(byte[] bytes) {
    if (bytes == null) {
      return Collections.emptyList();
    }
    try {
      CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
      return (List) certFactory.generateCertificates(new ByteArrayInputStream(bytes));
    } catch (CertificateException e) {
      Log.m103w(TAG, "Couldn't parse certificates in keystore", e);
      return Collections.emptyList();
    }
  }

  private static String getKeyString(String key) {
    return (key == null || !key.startsWith(Credentials.USER_PRIVATE_KEY))
        ? TvInteractiveAppView.BI_INTERACTIVE_APP_KEY_CERTIFICATE
        : "private key";
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static int getUserId(int uid) {
    return UserHandle.getUserId(uid);
  }

  private static int getUserIdForDomainOrNamespace(int userId, int domain, long namespace) {
    if (domain == 2 || namespace == 102) {
      return -1;
    }
    return userId;
  }

  private static String getKeystoreString(int domain, long namespace, int operationType) {
    Pair<Long, KeyDescriptor> pair;
    if (operationType == 3
        && (pair = mKeyDescriptorBeforeImportKey) != null
        && pair.first.longValue() == namespace) {
      KeyDescriptor keyDescriptor = mKeyDescriptorBeforeImportKey.second;
      if (keyDescriptor != null) {
        domain = keyDescriptor.domain;
        namespace = keyDescriptor.nspace;
      }
      mKeyDescriptorBeforeImportKey = null;
    }
    if (domain == 2 || namespace == 102) {
      return "Wi-Fi";
    }
    if (domain != 0 && namespace != -1) {
      return "";
    }
    return "VPN and Apps";
  }

  private static String getErrorMessage(int error) {
    switch (error) {
      case 0:
      case 1:
        return "";
      case 200:
        return " Cannot connect to KeyStore";
      case 201:
        return " Completed with execution exception";
      default:
        return " with error " + error;
    }
  }

  private static String getRequesterInfo(Context context) {
    String role;
    int myUid = Process.myUid();
    int myPid = Process.myPid();
    String role2 = myUid == 1000 ? "SystemApp" : "UserApp";
    String packageName = getPackageNameForUid(context, myUid);
    if (isCallerAdmin(packageName, myUid, myPid)) {
      role = role2 + "|Administrator";
    } else {
      role = role2 + "|NonAdministrator";
    }
    return packageName + ": uid=" + myUid + " pid=" + myPid + " role=" + role;
  }

  private static String getPackageNameForUid(Context context, int uid) {
    if (context == null) {
      try {
        IBinder bPkgMngr =
            (IBinder)
                Class.forName("android.os.ServiceManager")
                    .getMethod("getService", String.class)
                    .invoke(null, "package");
        Object mPkgMngr =
            Class.forName("android.content.pm.IPackageManager$Stub")
                .getMethod("asInterface", IBinder.class)
                .invoke(null, bPkgMngr);
        Method mthdGetName = mPkgMngr.getClass().getMethod("getNameForUid", Integer.TYPE);
        String packageName = (String) mthdGetName.invoke(mPkgMngr, Integer.valueOf(uid));
        return packageName;
      } catch (Exception ex) {
        Log.m96e(TAG, "Cannot retrieve package name for uid " + uid + " " + ex.getMessage());
        return "";
      }
    }
    String packageName2 = context.getPackageManager().getNameForUid(uid);
    return packageName2;
  }

  private static boolean isCallerAdmin(String packageName, int uid, int pid) {
    try {
      IBinder bEdm =
          (IBinder)
              Class.forName("android.os.ServiceManager")
                  .getMethod("getService", String.class)
                  .invoke(null, "enterprise_policy");
      Object mEdm =
          Class.forName("com.samsung.android.knox.IEnterpriseDeviceManager$Stub")
              .getMethod("asInterface", IBinder.class)
              .invoke(null, bEdm);
      Method mthdCheck = mEdm.getClass().getMethod("packageHasActiveAdmins", String.class);
      return ((Boolean) mthdCheck.invoke(mEdm, packageName)).booleanValue();
    } catch (Exception ex) {
      Log.m97e(
          TAG,
          "Administrator status cannot be defined for requester: uid=" + uid + " pid=" + pid,
          ex);
      return false;
    }
  }
}
