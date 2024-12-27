package android.security;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import java.util.concurrent.atomic.AtomicReference;

import javax.security.auth.x500.X500Principal;

public final class KeyChain {
    public static final String ACCOUNT_TYPE = "com.android.keychain";
    private static final String ACTION_CHOOSER = "com.android.keychain.CHOOSER";
    private static final String ACTION_INSTALL = "android.credentials.INSTALL";
    public static final String ACTION_KEYCHAIN_CHANGED = "android.security.action.KEYCHAIN_CHANGED";
    public static final String ACTION_KEY_ACCESS_CHANGED =
            "android.security.action.KEY_ACCESS_CHANGED";
    public static final String ACTION_STORAGE_CHANGED = "android.security.STORAGE_CHANGED";
    public static final String ACTION_TRUST_STORE_CHANGED =
            "android.security.action.TRUST_STORE_CHANGED";
    private static final String ANDROID_SOURCE = "android";
    private static final String CERT_INSTALLER_PACKAGE = "com.android.certinstaller";
    public static final String EXTRA_ALIAS = "alias";
    public static final String EXTRA_AUTHENTICATION_POLICY =
            "android.security.extra.AUTHENTICATION_POLICY";
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

    public static Intent createInstallIntent() {
        Intent intent = new Intent("android.credentials.INSTALL");
        intent.setClassName(CERT_INSTALLER_PACKAGE, "com.android.certinstaller.CertInstallerMain");
        return intent;
    }

    public static Intent createManageCredentialsIntent(AppUriAuthenticationPolicy policy) {
        Intent intent = new Intent(Credentials.ACTION_MANAGE_CREDENTIALS);
        intent.setComponent(
                ComponentName.createRelative(
                        "com.android.settings", ".security.RequestManageCredentials"));
        intent.putExtra(EXTRA_AUTHENTICATION_POLICY, policy);
        return intent;
    }

    public static void choosePrivateKeyAlias(
            Activity activity,
            KeyChainAliasCallback response,
            String[] keyTypes,
            Principal[] issuers,
            String host,
            int port,
            String alias) {
        Uri uri = null;
        if (host != null) {
            uri = new Uri.Builder().authority(host + (port != -1 ? ":" + port : "")).build();
        }
        choosePrivateKeyAlias(activity, response, keyTypes, issuers, uri, alias);
    }

    public static void choosePrivateKeyAlias(
            Activity activity,
            KeyChainAliasCallback response,
            String[] keyTypes,
            Principal[] issuers,
            Uri uri,
            String alias) {
        if (activity == null) {
            throw new NullPointerException("activity == null");
        }
        if (response == null) {
            throw new NullPointerException("response == null");
        }
        Intent intent = new Intent(ACTION_CHOOSER);
        intent.setPackage("com.android.keychain");
        intent.putExtra("response", new AliasResponse(response));
        intent.putExtra("uri", uri);
        intent.putExtra("alias", alias);
        intent.putExtra(EXTRA_KEY_TYPES, keyTypes);
        ArrayList<byte[]> issuersList = new ArrayList<>();
        if (issuers != null) {
            for (Principal issuer : issuers) {
                if (!(issuer instanceof X500Principal)) {
                    throw new IllegalArgumentException(
                            String.format(
                                    "Issuer %s is of type %s, not X500Principal",
                                    issuer.toString(), issuer.getClass()));
                }
                issuersList.add(((X500Principal) issuer).getEncoded());
            }
        }
        intent.putExtra(EXTRA_ISSUERS, issuersList);
        intent.putExtra(
                EXTRA_SENDER, PendingIntent.getActivity(activity, 0, new Intent(), 67108864));
        activity.startActivity(intent);
    }

    public static boolean isCredentialManagementApp(Context context) {
        try {
            KeyChainConnection keyChainConnection = bind(context);
            try {
                boolean isCredentialManagementApp =
                        keyChainConnection
                                .getService()
                                .isCredentialManagementApp(context.getPackageName());
                if (keyChainConnection == null) {
                    return isCredentialManagementApp;
                }
                keyChainConnection.close();
                return isCredentialManagementApp;
            } catch (Throwable th) {
                if (keyChainConnection != null) {
                    try {
                        keyChainConnection.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        } catch (InterruptedException e2) {
            throw new RuntimeException(
                    "Interrupted while checking whether the caller is the credential management"
                        + " app.",
                    e2);
        } catch (SecurityException e3) {
            return false;
        }
    }

    public static AppUriAuthenticationPolicy getCredentialManagementAppPolicy(Context context)
            throws SecurityException {
        AppUriAuthenticationPolicy policy = null;
        try {
            KeyChainConnection keyChainConnection = bind(context);
            try {
                policy = keyChainConnection.getService().getCredentialManagementAppPolicy();
                if (keyChainConnection != null) {
                    keyChainConnection.close();
                }
            } finally {
            }
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        } catch (InterruptedException e2) {
            throw new RuntimeException(
                    "Interrupted while getting credential management app policy.", e2);
        }
        return policy;
    }

    public static boolean setCredentialManagementApp(
            Context context, String packageName, AppUriAuthenticationPolicy authenticationPolicy) {
        try {
            KeyChainConnection keyChainConnection = bind(context);
            try {
                keyChainConnection
                        .getService()
                        .setCredentialManagementApp(packageName, authenticationPolicy);
                if (keyChainConnection != null) {
                    keyChainConnection.close();
                    return true;
                }
                return true;
            } catch (Throwable th) {
                if (keyChainConnection != null) {
                    try {
                        keyChainConnection.close();
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
            KeyChainConnection keyChainConnection = bind(context);
            try {
                keyChainConnection.getService().removeCredentialManagementApp();
                if (keyChainConnection != null) {
                    keyChainConnection.close();
                    return true;
                }
                return true;
            } catch (Throwable th) {
                if (keyChainConnection != null) {
                    try {
                        keyChainConnection.close();
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

        private AliasResponse(KeyChainAliasCallback keyChainAliasResponse) {
            this.keyChainAliasResponse = keyChainAliasResponse;
        }

        @Override // android.security.IKeyChainAliasCallback
        public void alias(String alias) {
            this.keyChainAliasResponse.alias(alias);
        }
    }

    public static PrivateKey getPrivateKey(Context context, String alias)
            throws KeyChainException, InterruptedException {
        KeyPair keyPair = getKeyPair(context, alias);
        if (keyPair != null) {
            return keyPair.getPrivate();
        }
        return null;
    }

    private static KeyDescriptor getGrantDescriptor(String keyid) {
        KeyDescriptor result = new KeyDescriptor();
        result.domain = 1;
        result.blob = null;
        result.alias = null;
        try {
            result.nspace =
                    Long.parseUnsignedLong(keyid.substring(GRANT_ALIAS_PREFIX.length()), 16);
            return result;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String getGrantString(KeyDescriptor key) {
        return String.format("ks2_keychain_grant_id:%016X", Long.valueOf(key.nspace));
    }

    public static KeyPair getKeyPair(Context context, String alias)
            throws KeyChainException, InterruptedException {
        if (alias == null) {
            throw new NullPointerException("alias == null");
        }
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        if (isUcmKeyChainUriAndProvider(alias)) {
            if (isAndroidProvider(alias)) {
                String originalAlias = getRawAlias(alias);
                Log.d(
                        LOG,
                        "Provider is ANDROID_SOURCE, flow default sequence with alias : "
                                + originalAlias);
                alias = originalAlias;
            } else {
                return new KeyPair(null, getUCMPrivateKey(alias));
            }
        }
        try {
            KeyChainConnection keyChainConnection = bind(context.getApplicationContext());
            try {
                String keyId = keyChainConnection.getService().requestPrivateKey(alias);
                if (keyChainConnection != null) {
                    keyChainConnection.close();
                }
                if (keyId == null) {
                    return null;
                }
                try {
                    return AndroidKeyStoreProvider.loadAndroidKeyStoreKeyPairFromKeystore(
                            KeyStore2.getInstance(), getGrantDescriptor(keyId));
                } catch (KeyPermanentlyInvalidatedException | UnrecoverableKeyException e) {
                    throw new KeyChainException(e);
                }
            } finally {
            }
        } catch (RemoteException e2) {
            throw new KeyChainException(e2);
        } catch (RuntimeException e3) {
            throw new KeyChainException(e3);
        }
    }

    public static X509Certificate[] getCertificateChain(Context context, String alias)
            throws KeyChainException, InterruptedException {
        if (alias == null) {
            throw new NullPointerException("alias == null");
        }
        if (isUcmKeyChainUriAndProvider(alias)) {
            if (isAndroidProvider(alias)) {
                String originalAlias = getRawAlias(alias);
                Log.d(
                        LOG,
                        "Provider is ANDROID_SOURCE, flow default sequence with alias : "
                                + originalAlias);
                alias = originalAlias;
            } else {
                IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
                if (lService != null) {
                    try {
                        byte[] certificateBytes = lService.ucmGetCertificateChain(alias);
                        if (certificateBytes == null) {
                            return null;
                        }
                        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                        ByteArrayInputStream bIs = new ByteArrayInputStream(certificateBytes);
                        List<Certificate> certList = (List) certFactory.generateCertificates(bIs);
                        X509Certificate[] chain = new X509Certificate[certList.size()];
                        int index = 0;
                        for (Certificate certificate : certList) {
                            if (certificate instanceof X509Certificate) {
                                chain[index] = (X509Certificate) certificate;
                                index++;
                            } else {
                                Log.e(LOG, "A certificate in the chain is not X509");
                                return new X509Certificate[0];
                            }
                        }
                        return chain;
                    } catch (RemoteException re) {
                        Log.e(LOG, "Remote Exception " + re);
                    } catch (CertificateException e) {
                        throw new KeyChainException(e);
                    }
                }
            }
        }
        try {
            KeyChainConnection keyChainConnection = bind(context.getApplicationContext());
            try {
                IKeyChainService keyChainService = keyChainConnection.getService();
                byte[] certificateBytes2 = keyChainService.getCertificate(alias);
                if (certificateBytes2 != null) {
                    byte[] certChainBytes = keyChainService.getCaCertificates(alias);
                    if (keyChainConnection != null) {
                        keyChainConnection.close();
                    }
                    try {
                        X509Certificate leafCert = toCertificate(certificateBytes2);
                        if (certChainBytes != null && certChainBytes.length != 0) {
                            Collection<? extends X509Certificate> chain2 =
                                    toCertificates(certChainBytes);
                            ArrayList<X509Certificate> fullChain =
                                    new ArrayList<>(chain2.size() + 1);
                            fullChain.add(leafCert);
                            fullChain.addAll(chain2);
                            return (X509Certificate[])
                                    fullChain.toArray(new X509Certificate[fullChain.size()]);
                        }
                        TrustedCertificateStore store = new TrustedCertificateStore();
                        List<X509Certificate> chain3 = store.getCertificateChain(leafCert);
                        return (X509Certificate[])
                                chain3.toArray(new X509Certificate[chain3.size()]);
                    } catch (RuntimeException | CertificateException e2) {
                        throw new KeyChainException(e2);
                    }
                }
                if (keyChainConnection != null) {
                    keyChainConnection.close();
                }
                return null;
            } finally {
            }
        } catch (RemoteException e3) {
            throw new KeyChainException(e3);
        } catch (RuntimeException e4) {
            throw new KeyChainException(e4);
        }
    }

    public static boolean isKeyAlgorithmSupported(String algorithm) {
        String algUpper = algorithm.toUpperCase(Locale.US);
        return KeyProperties.KEY_ALGORITHM_EC.equals(algUpper) || "RSA".equals(algUpper);
    }

    @Deprecated
    public static boolean isBoundKeyAlgorithm(String algorithm) {
        return true;
    }

    public static X509Certificate toCertificate(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes == null");
        }
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            Certificate cert = certFactory.generateCertificate(new ByteArrayInputStream(bytes));
            return (X509Certificate) cert;
        } catch (CertificateException e) {
            throw new AssertionError(e);
        }
    }

    public static Collection<X509Certificate> toCertificates(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("bytes == null");
        }
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            return certFactory.generateCertificates(new ByteArrayInputStream(bytes));
        } catch (CertificateException e) {
            throw new AssertionError(e);
        }
    }

    public static class KeyChainConnection implements Closeable {
        private final Context mContext;
        private final IKeyChainService mService;
        private final ServiceConnection mServiceConnection;

        protected KeyChainConnection(
                Context context, ServiceConnection serviceConnection, IKeyChainService service) {
            this.mContext = context;
            this.mServiceConnection = serviceConnection;
            this.mService = service;
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

    public static KeyChainConnection bindAsUser(Context context, UserHandle user)
            throws InterruptedException {
        return bindAsUser(context, null, user);
    }

    @SystemApi
    public static String getWifiKeyGrantAsUser(Context context, UserHandle user, String alias) {
        try {
            try {
                KeyChainConnection keyChainConnection =
                        bindAsUser(context.getApplicationContext(), user);
                try {
                    String wifiKeyGrantAsUser =
                            keyChainConnection.getService().getWifiKeyGrantAsUser(alias);
                    if (keyChainConnection != null) {
                        keyChainConnection.close();
                    }
                    return wifiKeyGrantAsUser;
                } catch (Throwable th) {
                    if (keyChainConnection != null) {
                        try {
                            keyChainConnection.close();
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
    public static boolean hasWifiKeyGrantAsUser(Context context, UserHandle user, String alias) {
        try {
            try {
                KeyChainConnection keyChainConnection =
                        bindAsUser(context.getApplicationContext(), user);
                try {
                    boolean hasGrant = keyChainConnection.getService().hasGrant(1010, alias);
                    if (keyChainConnection != null) {
                        keyChainConnection.close();
                    }
                    return hasGrant;
                } catch (Throwable th) {
                    if (keyChainConnection != null) {
                        try {
                            keyChainConnection.close();
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

    public static KeyChainConnection bindAsUser(Context context, Handler handler, UserHandle user)
            throws InterruptedException {
        boolean bindSucceed;
        if (context == null) {
            throw new NullPointerException("context == null");
        }
        if (handler == null) {
            ensureNotOnMainThread(context);
        }
        if (!UserManager.get(context).isUserUnlocked(user)) {
            throw new IllegalStateException("User must be unlocked");
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference<IKeyChainService> keyChainService = new AtomicReference<>();
        ServiceConnection keyChainServiceConnection =
                new ServiceConnection() { // from class: android.security.KeyChain.1
                    volatile boolean mConnectedAtLeastOnce = false;

                    @Override // android.content.ServiceConnection
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        if (!this.mConnectedAtLeastOnce) {
                            this.mConnectedAtLeastOnce = true;
                            keyChainService.set(
                                    IKeyChainService.Stub.asInterface(
                                            Binder.allowBlocking(service)));
                            countDownLatch.countDown();
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onBindingDied(ComponentName name) {
                        if (!this.mConnectedAtLeastOnce) {
                            this.mConnectedAtLeastOnce = true;
                            countDownLatch.countDown();
                        }
                    }

                    @Override // android.content.ServiceConnection
                    public void onServiceDisconnected(ComponentName name) {}
                };
        Intent intent = new Intent(IKeyChainService.class.getName());
        ComponentName comp = intent.resolveSystemService(context.getPackageManager(), 0);
        if (comp == null) {
            throw new AssertionError("could not resolve KeyChainService");
        }
        intent.setComponent(comp);
        if (handler != null) {
            bindSucceed =
                    context.bindServiceAsUser(intent, keyChainServiceConnection, 1, handler, user);
        } else {
            bindSucceed = context.bindServiceAsUser(intent, keyChainServiceConnection, 1, user);
        }
        if (!bindSucceed) {
            context.unbindService(keyChainServiceConnection);
            throw new AssertionError("could not bind to KeyChainService");
        }
        countDownLatch.await();
        IKeyChainService service = keyChainService.get();
        if (service != null) {
            return new KeyChainConnection(context, keyChainServiceConnection, service);
        }
        context.unbindService(keyChainServiceConnection);
        throw new AssertionError("KeyChainService died while binding");
    }

    private static void ensureNotOnMainThread(Context context) {
        Looper looper = Looper.myLooper();
        if (looper != null && looper == context.getMainLooper()) {
            throw new IllegalStateException(
                    "calling this from your main thread can lead to deadlock");
        }
    }

    private static boolean isKeyChainUri(String uri) {
        if (uri == null) {
            return false;
        }
        Log.d(LOG, "isKeyChainUri: " + uri);
        Uri parsedUri = Uri.parse(uri);
        if (parsedUri == null) {
            return false;
        }
        return UCM_KEYCHAIN_SCHEME.equals(parsedUri.getScheme());
    }

    private static String getSource(String uri) {
        List<String> paths = Uri.parse(uri).getPathSegments();
        if (paths == null || paths.size() < 3) {
            Log.d(LOG, "getSource: null or wrong");
            return null;
        }
        try {
            String sourcePath = paths.get(0);
            String resourcetype = paths.get(1);
            String uriuid = paths.get(2);
            Log.d(
                    LOG,
                    "getSource: source = "
                            + sourcePath
                            + ", resource type = "
                            + resourcetype
                            + ", uid = "
                            + uriuid);
            return sourcePath;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getRawAlias(String uri) {
        return Uri.parse(uri).getLastPathSegment();
    }

    private static PrivateKey getUCMPrivateKey(String alias) {
        IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
        if (lService == null) {
            Log.e(LOG, "getUCMPrivateKey. lService is null");
            return null;
        }
        try {
            byte[] certificateBytes = lService.ucmGetCertificateChain(alias);
            if (certificateBytes == null) {
                Log.e(LOG, "getUCMPrivateKey. certificateBytes is null");
                return null;
            }
            return UcmKeyStoreKeyFactory.getPrivateKey(alias, certificateBytes);
        } catch (RemoteException re) {
            Log.e(LOG, "Remote Exception " + re);
            return null;
        }
    }

    private static boolean isUcmKeyChainUriAndProvider(String alias) {
        if (!isKeyChainUri(alias)) {
            return false;
        }
        String provider = getSource(alias);
        Log.d(LOG, "Provider : " + provider);
        return provider != null;
    }

    private static boolean isAndroidProvider(String alias) {
        return "android".equals(getSource(alias));
    }
}
