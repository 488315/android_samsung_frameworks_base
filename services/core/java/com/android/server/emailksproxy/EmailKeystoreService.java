package com.android.server.emailksproxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.IKeyChainService;
import android.util.Log;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.samsung.android.knox.util.ISemKeyStoreService;
import com.samsung.android.knox.util.SemCertAndroidKeyStore;
import com.samsung.android.knox.util.SemCertByte;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.LinkedBlockingQueue;

public final class EmailKeystoreService extends ISemKeyStoreService.Stub {
    public static final boolean DBG = Debug.semIsProductDev();
    public Context mContext;

    public final class KeyChainConnection implements Closeable {
        public final Context context;
        public final IKeyChainService service;
        public final ServiceConnection serviceConnection;

        public KeyChainConnection(Context context, AnonymousClass1 anonymousClass1, IKeyChainService iKeyChainService) {
            this.context = context;
            this.serviceConnection = anonymousClass1;
            this.service = iKeyChainService;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public final void close() {
            this.context.unbindService(this.serviceConnection);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.ServiceConnection, com.android.server.emailksproxy.EmailKeystoreService$1] */
    public static KeyChainConnection bind(Context context, int i) {
        if (context == 0) {
            throw new NullPointerException("context == null");
        }
        Looper myLooper = Looper.myLooper();
        if (myLooper != null && myLooper == context.getMainLooper()) {
            throw new IllegalStateException("calling this from your main thread can lead to deadlock");
        }
        final LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(1);
        ?? r2 = new ServiceConnection() { // from class: com.android.server.emailksproxy.EmailKeystoreService.1
            public volatile boolean mConnectedAtLeastOnce = false;

            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                if (this.mConnectedAtLeastOnce) {
                    return;
                }
                this.mConnectedAtLeastOnce = true;
                Log.d("EmailKeystoreService", "Bind to keychain service happened");
                if (iBinder != null) {
                    try {
                        linkedBlockingQueue.put(IKeyChainService.Stub.asInterface(iBinder));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
            }
        };
        Intent intent = new Intent(IKeyChainService.class.getName());
        intent.setComponent(intent.resolveSystemService(context.getPackageManager(), 0));
        if (DBG) {
            Log.d("EmailKeystoreService", "KeyChainConnection: " + Binder.getCallingUid());
        }
        UserHandle userHandle = new UserHandle(UserHandle.getUserId(i));
        long clearCallingIdentity = Binder.clearCallingIdentity();
        boolean bindServiceAsUser = context.bindServiceAsUser(intent, (ServiceConnection) r2, 1, userHandle);
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (bindServiceAsUser) {
            return new KeyChainConnection(context, r2, (IKeyChainService) linkedBlockingQueue.take());
        }
        throw new AssertionError("Could not bind to KeyChainService");
    }

    public final int getKeystoreStatus() {
        if (!isCallerSignedByAndroid()) {
            return 0;
        }
        if (!DBG) {
            return 1;
        }
        Log.d("EmailKeystoreService", "getKeystoreStatus returns: 1");
        return 1;
    }

    public final void grantAccessForAKS(int i, String str) {
        Log.d("EmailKeystoreService", "grantAccessForAKS()  uid = " + i + " alias = " + str);
        if (str == null || i == -1) {
            Log.w("EmailKeystoreService", "grantAccessForAKS()   alias == null");
            return;
        }
        KeyChainConnection keyChainConnection = null;
        try {
            try {
                keyChainConnection = bind(this.mContext, i);
                IKeyChainService iKeyChainService = keyChainConnection.service;
                Log.d("EmailKeystoreService", "grantAccessforAKS call setGrant : uid = " + i + " alias = " + str);
                iKeyChainService.setGrant(i, str, true);
                keyChainConnection.close();
            } catch (Throwable th) {
                if (keyChainConnection != null) {
                    keyChainConnection.close();
                }
                throw th;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AssertionError e2) {
            e2.printStackTrace();
        } catch (InterruptedException e3) {
            e3.printStackTrace();
        } catch (Exception e4) {
            e4.printStackTrace();
        }
    }

    public final int installCACert(SemCertAndroidKeyStore semCertAndroidKeyStore) {
        if (!isCallerSignedByAndroid()) {
            return 1;
        }
        if (semCertAndroidKeyStore == null) {
            return -2;
        }
        try {
            for (Certificate certificate : semCertAndroidKeyStore.certs) {
                installCaCertificate(certificate.getEncoded());
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public final void installCaCertificate(byte[] bArr) {
        TrustedCertificateStore trustedCertificateStore = new TrustedCertificateStore();
        try {
        } catch (IOException e) {
            if (DBG) {
                Log.d("EmailKeystoreService", "IOException while installing CA Certificate");
            }
            e.printStackTrace();
        } catch (CertificateException e2) {
            if (DBG) {
                Log.d("EmailKeystoreService", "CertificateException while installing CA Certificate");
            }
            e2.printStackTrace();
        }
        synchronized (trustedCertificateStore) {
            try {
                X509Certificate x509Certificate = (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr));
                if (x509Certificate == null) {
                    if (DBG) {
                        Log.d("EmailKeystoreService", "CA Certificate parse error");
                    }
                    return;
                }
                trustedCertificateStore.installCertificate(x509Certificate);
                if (trustedCertificateStore.getCertificateAlias((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(bArr))) != null && DBG) {
                    Log.d("EmailKeystoreService", "CA Certificate successfully installed");
                }
                this.mContext.sendBroadcast(new Intent("android.security.STORAGE_CHANGED"));
            } finally {
            }
        }
    }

    public final int installCertificateInAndroidKeyStore(SemCertByte semCertByte, String str, char[] cArr, int i) {
        if (!isCallerSignedByAndroid() || semCertByte == null) {
            return 1;
        }
        long j = 0;
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                                    byte[] bArr = new byte[semCertByte.certsize];
                                    keyStore.load(new ByteArrayInputStream(semCertByte.certBytes), cArr);
                                    Key key = keyStore.getKey(str, cArr);
                                    Certificate[] certificateArr = new Certificate[1];
                                    j = Binder.clearCallingIdentity();
                                    if (DBG) {
                                        Log.d("EmailKeystoreService", "installCertificateInAndroidKeyStore: " + UserHandle.getUserId(i));
                                    }
                                    KeyStore keyStore2 = KeyStore.getInstance("AndroidKeyStore");
                                    keyStore2.load(null);
                                    keyStore2.setKeyEntry(str, key.getEncoded(), keyStore.getCertificateChain(str));
                                    Binder.restoreCallingIdentity(j);
                                    return 0;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Binder.restoreCallingIdentity(j);
                                    return 4;
                                }
                            } catch (NoSuchAlgorithmException e2) {
                                e2.printStackTrace();
                                Binder.restoreCallingIdentity(j);
                                return 2;
                            }
                        } catch (UnrecoverableKeyException e3) {
                            e3.printStackTrace();
                            Binder.restoreCallingIdentity(j);
                            return 2;
                        }
                    } catch (KeyStoreException e4) {
                        e4.printStackTrace();
                        Binder.restoreCallingIdentity(j);
                        return 2;
                    }
                } catch (IOException e5) {
                    e5.printStackTrace();
                    Binder.restoreCallingIdentity(j);
                    return 4;
                }
            } catch (CertificateException e6) {
                e6.printStackTrace();
                Binder.restoreCallingIdentity(j);
                return 3;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(j);
            throw th;
        }
    }

    public final int isAliasExists(String str) {
        Log.d("EmailKeystoreService", "isAliasExists()");
        if (DBG) {
            Log.d("EmailKeystoreService", "isAliasExists:: calling uid : " + Binder.getCallingUid());
        }
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            if (keyStore.containsAlias(str)) {
                Log.d("EmailKeystoreService", "isAliasExists:: returns SUCCESS");
                return 0;
            }
            Log.d("EmailKeystoreService", "isAliasExists:: returns FAILURE");
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public final boolean isCallerSignedByAndroid() {
        String nameForUid = this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid());
        PackageManager packageManager = this.mContext.getPackageManager();
        if (nameForUid == null || packageManager.checkSignatures("android", nameForUid) != 0) {
            Log.d("EmailKeystoreService", "isCallerSignedByAndroid()--false");
            return false;
        }
        Log.d("EmailKeystoreService", "isCallerSignedByAndroid()--true");
        return true;
    }
}
