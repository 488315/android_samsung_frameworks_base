package android.security;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.security.IKeyChainAliasCallback;
import android.security.IKeyChainService;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.security.keystore2.AndroidKeyStoreProvider;
import android.system.keystore2.KeyDescriptor;
import android.util.Log;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.samsung.ucm.keystore.UcmKeyStoreKeyFactory;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.security.KeyPair;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import javax.security.auth.x500.X500Principal;

/* loaded from: classes3.dex */
public final class KeyChain {
    public static final String ACCOUNT_TYPE = "com.android.keychain";
    private static final String ACTION_CHOOSER = "com.android.keychain.CHOOSER";
    private static final String ACTION_INSTALL = "android.credentials.INSTALL";
    public static final String ACTION_KEYCHAIN_CHANGED = "android.security.action.KEYCHAIN_CHANGED";
    public static final String ACTION_KEY_ACCESS_CHANGED = "android.security.action.KEY_ACCESS_CHANGED";
    public static final String ACTION_STORAGE_CHANGED = "android.security.STORAGE_CHANGED";
    public static final String ACTION_TRUST_STORE_CHANGED = "android.security.action.TRUST_STORE_CHANGED";
    private static final String ANDROID_SOURCE = "android";
    private static final int BIND_KEY_CHAIN_SERVICE_TIMEOUT_MS = 30000;
    private static final String CERT_INSTALLER_PACKAGE = "com.android.certinstaller";
    public static final String EXTRA_ALIAS = "alias";
    public static final String EXTRA_AUTHENTICATION_POLICY = "android.security.extra.AUTHENTICATION_POLICY";
    public static final String EXTRA_CERTIFICATE = "CERT";
    public static final String EXTRA_ISSUERS = "issuers";
    public static final String EXTRA_KEY_ACCESSIBLE = "android.security.extra.KEY_ACCESSIBLE";
    public static final String EXTRA_KEY_ALIAS = "android.security.extra.KEY_ALIAS";
    public static final String EXTRA_KEY_TYPES = "key_types";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_PKCS12 = "PKCS12";
    public static final String EXTRA_RESPONSE = "response";
    public static final String EXTRA_SENDER = "sender";
    public static final String EXTRA_URI = "uri";
    public static final String GRANT_ALIAS_PREFIX = "ks2_keychain_grant_id:";
    private static final String KEYCHAIN_PACKAGE = "com.android.keychain";
    public static final String KEY_ALIAS_SELECTION_DENIED = "android:alias-selection-denied";
    public static final int KEY_ATTESTATION_CANNOT_ATTEST_IDS = 3;
    public static final int KEY_ATTESTATION_CANNOT_COLLECT_DATA = 2;
    public static final int KEY_ATTESTATION_FAILURE = 4;
    public static final int KEY_ATTESTATION_MISSING_CHALLENGE = 1;
    public static final int KEY_ATTESTATION_SUCCESS = 0;
    public static final int KEY_GEN_FAILURE = 7;
    public static final int KEY_GEN_INVALID_ALGORITHM_PARAMETERS = 4;
    public static final int KEY_GEN_MISSING_ALIAS = 1;
    public static final int KEY_GEN_NO_KEYSTORE_PROVIDER = 5;
    public static final int KEY_GEN_NO_SUCH_ALGORITHM = 3;
    public static final int KEY_GEN_STRONGBOX_UNAVAILABLE = 6;
    public static final int KEY_GEN_SUCCESS = 0;
    public static final int KEY_GEN_SUPERFLUOUS_ATTESTATION_CHALLENGE = 2;
    public static final String LOG = "KeyChain";
    private static final String SETTINGS_PACKAGE = "com.android.settings";
    private static final String UCM_KEYCHAIN_SCHEME = "ucmkeychain";

    @Deprecated
    public static boolean isBoundKeyAlgorithm(String str) {
        return true;
    }

    public static Intent createInstallIntent() {
        Intent intent = new Intent("android.credentials.INSTALL");
        intent.setClassName(CERT_INSTALLER_PACKAGE, "com.android.certinstaller.CertInstallerMain");
        return intent;
    }

    public static Intent createManageCredentialsIntent(AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        Intent intent = new Intent(Credentials.ACTION_MANAGE_CREDENTIALS);
        intent.setComponent(ComponentName.createRelative("com.android.settings", ".security.RequestManageCredentials"));
        intent.putExtra(EXTRA_AUTHENTICATION_POLICY, appUriAuthenticationPolicy);
        return intent;
    }

    public static void choosePrivateKeyAlias(Activity activity, KeyChainAliasCallback keyChainAliasCallback, String[] strArr, Principal[] principalArr, String str, int i, String str2) {
        Uri uri;
        String str3;
        if (str != null) {
            Uri.Builder builder = new Uri.Builder();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            if (i != -1) {
                str3 = ":" + i;
            } else {
                str3 = "";
            }
            sb.append(str3);
            uri = builder.authority(sb.toString()).build();
        } else {
            uri = null;
        }
        choosePrivateKeyAlias(activity, keyChainAliasCallback, strArr, principalArr, uri, str2);
    }

    public static void choosePrivateKeyAlias(Activity activity, KeyChainAliasCallback keyChainAliasCallback, String[] strArr, Principal[] principalArr, Uri uri, String str) {
        if (activity == null) {
            throw new NullPointerException("activity == null");
        }
        if (keyChainAliasCallback == null) {
            throw new NullPointerException("response == null");
        }
        Intent intent = new Intent(ACTION_CHOOSER);
        intent.setPackage("com.android.keychain");
        intent.putExtra("response", new AliasResponse(keyChainAliasCallback));
        intent.putExtra("uri", uri);
        intent.putExtra("alias", str);
        intent.putExtra(EXTRA_KEY_TYPES, strArr);
        ArrayList arrayList = new ArrayList();
        if (principalArr != null) {
            for (Principal principal : principalArr) {
                if (!(principal instanceof X500Principal)) {
                    throw new IllegalArgumentException(String.format("Issuer %s is of type %s, not X500Principal", principal.toString(), principal.getClass()));
                }
                arrayList.add(((X500Principal) principal).getEncoded());
            }
        }
        intent.putExtra(EXTRA_ISSUERS, arrayList);
        intent.putExtra(EXTRA_SENDER, PendingIntent.getActivity(activity, 0, new Intent(), 67108864));
        activity.startActivity(intent);
    }

    public static boolean isCredentialManagementApp(Context context) {
        RemoteException e;
        boolean z;
        try {
            try {
                KeyChainConnection bind = bind(context);
                try {
                    z = bind.getService().isCredentialManagementApp(context.getPackageName());
                    if (bind == null) {
                        return z;
                    }
                    try {
                        bind.close();
                        return z;
                    } catch (RemoteException e2) {
                        e = e2;
                        e.rethrowAsRuntimeException();
                        return z;
                    }
                } catch (Throwable th) {
                    if (bind != null) {
                        try {
                            bind.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (InterruptedException e3) {
                throw new RuntimeException("Interrupted while checking whether the caller is the credential management app.", e3);
            } catch (SecurityException unused) {
                return false;
            }
        } catch (RemoteException e4) {
            e = e4;
            z = false;
        }
    }

    public static AppUriAuthenticationPolicy getCredentialManagementAppPolicy(Context context) throws SecurityException {
        AppUriAuthenticationPolicy appUriAuthenticationPolicy = null;
        try {
            KeyChainConnection bind = bind(context);
            try {
                appUriAuthenticationPolicy = bind.getService().getCredentialManagementAppPolicy();
                if (bind != null) {
                    bind.close();
                }
                return appUriAuthenticationPolicy;
            } finally {
            }
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return appUriAuthenticationPolicy;
        } catch (InterruptedException e2) {
            throw new RuntimeException("Interrupted while getting credential management app policy.", e2);
        }
    }

    public static boolean setCredentialManagementApp(Context context, String str, AppUriAuthenticationPolicy appUriAuthenticationPolicy) {
        try {
            KeyChainConnection bind = bind(context);
            try {
                bind.getService().setCredentialManagementApp(str, appUriAuthenticationPolicy);
                if (bind != null) {
                    bind.close();
                }
                return true;
            } catch (Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException | InterruptedException e) {
            Log.w(LOG, "Set credential management app failed", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    public static boolean removeCredentialManagementApp(Context context) {
        try {
            KeyChainConnection bind = bind(context);
            try {
                bind.getService().removeCredentialManagementApp();
                if (bind != null) {
                    bind.close();
                }
                return true;
            } catch (Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException | InterruptedException e) {
            Log.w(LOG, "Remove credential management app failed", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private static class AliasResponse extends IKeyChainAliasCallback.Stub {
        private final KeyChainAliasCallback keyChainAliasResponse;

        private AliasResponse(KeyChainAliasCallback keyChainAliasCallback) {
            this.keyChainAliasResponse = keyChainAliasCallback;
        }

        @Override // android.security.IKeyChainAliasCallback
        public void alias(String str) {
            this.keyChainAliasResponse.alias(str);
        }
    }

    public static PrivateKey getPrivateKey(Context context, String str) throws KeyChainException, InterruptedException {
        KeyPair keyPair = getKeyPair(context, str);
        if (keyPair != null) {
            return keyPair.getPrivate();
        }
        return null;
    }

    private static KeyDescriptor getGrantDescriptor(String str) {
        KeyDescriptor keyDescriptor = new KeyDescriptor();
        keyDescriptor.domain = 1;
        keyDescriptor.blob = null;
        keyDescriptor.alias = null;
        try {
            keyDescriptor.nspace = Long.parseUnsignedLong(str.substring(22), 16);
            return keyDescriptor;
        } catch (NumberFormatException unused) {
            return null;
        }
    }

    public static String getGrantString(KeyDescriptor keyDescriptor) {
        return String.format("ks2_keychain_grant_id:%016X", Long.valueOf(keyDescriptor.nspace));
    }

    public static KeyPair getKeyPair(Context context, String str) throws KeyChainException, InterruptedException {
        if (str == null) {
            throw new NullPointerException("alias == null");
        }
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        if (isUcmKeyChainUriAndProvider(str)) {
            if (isAndroidProvider(str)) {
                str = getRawAlias(str);
                Log.d(LOG, "Provider is ANDROID_SOURCE, flow default sequence with alias : " + str);
            } else {
                return new KeyPair(null, getUCMPrivateKey(str));
            }
        }
        try {
            KeyChainConnection bind = bind(context.getApplicationContext());
            try {
                String requestPrivateKey = bind.getService().requestPrivateKey(str);
                if (bind != null) {
                    bind.close();
                }
                if (requestPrivateKey == null) {
                    return null;
                }
                try {
                    return AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(KeyStore2.getInstance(), getGrantDescriptor(requestPrivateKey));
                } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e) {
                    throw new KeyChainException(e);
                }
            } catch (Throwable th) {
                if (bind != null) {
                    try {
                        bind.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException e2) {
            throw new KeyChainException(e2);
        } catch (RuntimeException e3) {
            throw new KeyChainException(e3);
        }
    }

    public static X509Certificate[] getCertificateChain(Context context, String str) throws KeyChainException, InterruptedException {
        if (str == null) {
            throw new NullPointerException("alias == null");
        }
        if (isUcmKeyChainUriAndProvider(str)) {
            if (isAndroidProvider(str)) {
                str = getRawAlias(str);
                Log.d(LOG, "Provider is ANDROID_SOURCE, flow default sequence with alias : " + str);
            } else {
                IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
                if (service != null) {
                    try {
                        byte[] ucmGetCertificateChain = service.ucmGetCertificateChain(str);
                        if (ucmGetCertificateChain == null) {
                            return null;
                        }
                        List<Certificate> list = (List) CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(ucmGetCertificateChain));
                        X509Certificate[] x509CertificateArr = new X509Certificate[list.size()];
                        int i = 0;
                        for (Certificate certificate : list) {
                            if (certificate instanceof X509Certificate) {
                                x509CertificateArr[i] = (X509Certificate) certificate;
                                i++;
                            } else {
                                Log.e(LOG, "A certificate in the chain is not X509");
                                return new X509Certificate[0];
                            }
                        }
                        return x509CertificateArr;
                    } catch (RemoteException e) {
                        Log.e(LOG, "Remote Exception " + e);
                    } catch (CertificateException e2) {
                        throw new KeyChainException(e2);
                    }
                }
            }
        }
        try {
            KeyChainConnection bind = bind(context.getApplicationContext());
            try {
                IKeyChainService service2 = bind.getService();
                byte[] certificate2 = service2.getCertificate(str);
                if (certificate2 == null) {
                    if (bind != null) {
                        bind.close();
                    }
                    return null;
                }
                byte[] caCertificates = service2.getCaCertificates(str);
                if (bind != null) {
                    bind.close();
                }
                try {
                    X509Certificate certificate3 = toCertificate(certificate2);
                    if (caCertificates != null && caCertificates.length != 0) {
                        Collection<X509Certificate> certificates = toCertificates(caCertificates);
                        ArrayList arrayList = new ArrayList(certificates.size() + 1);
                        arrayList.add(certificate3);
                        arrayList.addAll(certificates);
                        return (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]);
                    }
                    List certificateChain = new TrustedCertificateStore().getCertificateChain(certificate3);
                    return (X509Certificate[]) certificateChain.toArray(new X509Certificate[certificateChain.size()]);
                } catch (RuntimeException | CertificateException e3) {
                    throw new KeyChainException(e3);
                }
            } finally {
            }
        } catch (RemoteException e4) {
            throw new KeyChainException(e4);
        } catch (RuntimeException e5) {
            throw new KeyChainException(e5);
        }
    }

    public static boolean isKeyAlgorithmSupported(String str) {
        String upperCase = str.toUpperCase(Locale.US);
        return KeyProperties.KEY_ALGORITHM_EC.equals(upperCase) || "RSA".equals(upperCase);
    }

    public static X509Certificate toCertificate(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes == null");
        }
        try {
            return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            throw new AssertionError(e);
        }
    }

    public static Collection<X509Certificate> toCertificates(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("bytes == null");
        }
        try {
            return CertificateFactory.getInstance("X.509").generateCertificates(new ByteArrayInputStream(bArr));
        } catch (CertificateException e) {
            throw new AssertionError(e);
        }
    }

    public static class KeyChainConnection implements Closeable {
        private final Context mContext;
        private final IKeyChainService mService;
        private final ServiceConnection mServiceConnection;

        protected KeyChainConnection(Context context, ServiceConnection serviceConnection, IKeyChainService iKeyChainService) {
            this.mContext = context;
            this.mServiceConnection = serviceConnection;
            this.mService = iKeyChainService;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            this.mContext.unbindService(this.mServiceConnection);
        }

        public IKeyChainService getService() {
            return this.mService;
        }
    }

    public static KeyChainConnection bind(Context context) throws InterruptedException {
        return bindAsUser(context, Process.myUserHandle());
    }

    public static KeyChainConnection bindAsUser(Context context, UserHandle userHandle) throws InterruptedException {
        return bindAsUser(context, null, userHandle);
    }

    @SystemApi
    public static String getWifiKeyGrantAsUser(Context context, UserHandle userHandle, String str) {
        try {
            try {
                KeyChainConnection bindAsUser = bindAsUser(context.getApplicationContext(), userHandle);
                try {
                    String wifiKeyGrantAsUser = bindAsUser.getService().getWifiKeyGrantAsUser(str);
                    if (bindAsUser != null) {
                        bindAsUser.close();
                    }
                    return wifiKeyGrantAsUser;
                } catch (Throwable th) {
                    if (bindAsUser != null) {
                        try {
                            bindAsUser.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (RemoteException | RuntimeException e) {
                Log.i(LOG, "Couldn't get grant for wifi", e);
                return null;
            }
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            Log.i(LOG, "Interrupted while getting grant for wifi", e2);
            return null;
        }
    }

    @SystemApi
    public static boolean hasWifiKeyGrantAsUser(Context context, UserHandle userHandle, String str) {
        try {
            try {
                KeyChainConnection bindAsUser = bindAsUser(context.getApplicationContext(), userHandle);
                try {
                    boolean hasGrant = bindAsUser.getService().hasGrant(1010, str);
                    if (bindAsUser != null) {
                        bindAsUser.close();
                    }
                    return hasGrant;
                } catch (Throwable th) {
                    if (bindAsUser != null) {
                        try {
                            bindAsUser.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                Log.i(LOG, "Interrupted while querying grant for wifi", e);
                return false;
            }
        } catch (RemoteException | RuntimeException e2) {
            Log.i(LOG, "Couldn't query grant for wifi", e2);
            return false;
        }
    }

    public static KeyChainConnection bindAsUser(Context context, Handler handler, UserHandle userHandle) throws InterruptedException {
        Context context2;
        boolean bindServiceAsUser;
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        if (handler == null) {
            ensureNotOnMainThread(context);
        }
        if (!UserManager.get(context).isUserUnlocked(userHandle)) {
            throw new IllegalStateException("User must be unlocked");
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        ServiceConnection serviceConnection = new ServiceConnection() { // from class: android.security.KeyChain.1
            volatile boolean mConnectedAtLeastOnce = false;

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (this.mConnectedAtLeastOnce) {
                    return;
                }
                this.mConnectedAtLeastOnce = true;
                atomicReference.set(IKeyChainService.Stub.asInterface(Binder.allowBlocking(iBinder)));
                countDownLatch.countDown();
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                if (this.mConnectedAtLeastOnce) {
                    return;
                }
                this.mConnectedAtLeastOnce = true;
                countDownLatch.countDown();
            }
        };
        Intent intent = new Intent(IKeyChainService.class.getName());
        ComponentName resolveSystemService = intent.resolveSystemService(context.getPackageManager(), 0);
        if (resolveSystemService == null) {
            Log.e(LOG, "Intent Action : " + intent.getAction());
            Log.e(LOG, "Intent Component : " + intent.getComponent());
            Log.e(LOG, "IKeyChainService class Name : " + IKeyChainService.class.getName());
            Log.e(LOG, "Context: " + context.toString());
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 0);
                if (queryIntentServices == null) {
                    Log.e(LOG, "service is null : " + intent.getAction());
                } else if (queryIntentServices.isEmpty()) {
                    Log.e(LOG, "No services found matching the intent : " + intent.getAction());
                } else {
                    for (ResolveInfo resolveInfo : queryIntentServices) {
                        Log.d(LOG, "Matching service found : " + resolveInfo.serviceInfo.packageName + "/" + resolveInfo.serviceInfo.name);
                    }
                }
            } else {
                Log.e(LOG, "PackageManager is null : " + intent.getAction());
            }
            throw new AssertionError("could not resolve KeyChainService");
        }
        intent.setComponent(resolveSystemService);
        if (handler != null) {
            context2 = context;
            bindServiceAsUser = context2.bindServiceAsUser(intent, serviceConnection, 1, handler, userHandle);
        } else {
            context2 = context;
            bindServiceAsUser = context2.bindServiceAsUser(intent, serviceConnection, 1, userHandle);
        }
        if (!bindServiceAsUser) {
            context2.unbindService(serviceConnection);
            throw new AssertionError("could not bind to KeyChainService");
        }
        if (!countDownLatch.await(30000L, TimeUnit.MILLISECONDS)) {
            context2.unbindService(serviceConnection);
            throw new AssertionError("binding to KeyChainService timeout");
        }
        IKeyChainService iKeyChainService = (IKeyChainService) atomicReference.get();
        if (iKeyChainService != null) {
            return new KeyChainConnection(context2, serviceConnection, iKeyChainService);
        }
        context2.unbindService(serviceConnection);
        throw new AssertionError("KeyChainService died while binding");
    }

    private static void ensureNotOnMainThread(Context context) {
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper == context.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
    }

    private static boolean isKeyChainUri(String str) {
        if (str == null) {
            return false;
        }
        Log.d(LOG, "isKeyChainUri: " + str);
        Uri parse = Uri.parse(str);
        if (parse == null) {
            return false;
        }
        return UCM_KEYCHAIN_SCHEME.equals(parse.getScheme());
    }

    private static String getSource(String str) {
        List<String> pathSegments = Uri.parse(str).getPathSegments();
        if (pathSegments == null || pathSegments.size() < 3) {
            Log.d(LOG, "getSource: null or wrong");
            return null;
        }
        try {
            String str2 = pathSegments.get(0);
            Log.d(LOG, "getSource: source = " + str2 + ", resource type = " + pathSegments.get(1) + ", uid = " + pathSegments.get(2));
            return str2;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getRawAlias(String str) {
        return Uri.parse(str).getLastPathSegment();
    }

    private static PrivateKey getUCMPrivateKey(String str) {
        IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
        if (service == null) {
            Log.e(LOG, "getUCMPrivateKey. lService is null");
            return null;
        }
        try {
            byte[] ucmGetCertificateChain = service.ucmGetCertificateChain(str);
            if (ucmGetCertificateChain == null) {
                Log.e(LOG, "getUCMPrivateKey. certificateBytes is null");
                return null;
            }
            return UcmKeyStoreKeyFactory.getPrivateKey(str, ucmGetCertificateChain);
        } catch (RemoteException e) {
            Log.e(LOG, "Remote Exception " + e);
            return null;
        }
    }

    private static boolean isUcmKeyChainUriAndProvider(String str) {
        if (!isKeyChainUri(str)) {
            return false;
        }
        String source = getSource(str);
        Log.d(LOG, "Provider : " + source);
        return source != null;
    }

    private static boolean isAndroidProvider(String str) {
        return "android".equals(getSource(str));
    }
}
