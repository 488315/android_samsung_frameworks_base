package com.android.server.enterprise.certificate;

import android.R;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.org.conscrypt.TrustedCertificateStore;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.RestrictionToastManager;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.application.ApplicationPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.ucm.UniversalCredentialManagerService;
import com.android.server.enterprise.utils.CertificateUtil;
import com.android.server.enterprise.utils.EnterpriseDumpHelper;
import com.android.server.enterprise.utils.SecContentProviderUtil;
import com.android.server.enterprise.utils.Utils;
import com.android.server.knox.dar.DarManagerService;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.keystore.CertificateControlInfo;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.keystore.ICertificatePolicy;
import com.samsung.android.knox.keystore.PermissionApplicationPrivateKey;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidator;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CertificatePolicy extends ICertificatePolicy.Stub implements EnterpriseServiceCallback {
    public boolean mBootCompleted;
    public final AnonymousClass2 mBootReceiver;
    public final Context mContext;
    public final DarManagerService mDarManagerService;
    public EnterpriseDeviceManager mEDM;
    public final EdmStorageProvider mEdmStorageProvider;
    public final EnterpriseDumpHelper mEnterpriseDumpHelper;
    public final EdmKeyStore mNativeKeyStore;
    public final AnonymousClass2 mPackageChangeIntentReceiver;
    public final PackageManager mPackageManager;
    public final PackageManagerAdapter mPackageManagerAdapter;
    public final RollbackRefreshOperation mRollbackRefresh;
    public final RollbackRefreshHandler mRollbackRefreshHandler;
    public final CertificateCache mTrustedCache;
    public final EdmKeyStore mTrustedKeyStore;
    public UniversalCredentialManagerService mUCMService;
    public final CertificateCache mUntrustedCache;
    public final EdmKeyStore mUntrustedKeyStore;
    public final EdmKeyStore mUserKeyStore;
    public final AnonymousClass2 mUserReceiver;
    public final CertificateUtil mUtils;
    public final Object mLock = new Object();
    public final TrustedCertificateStore mCertStore = new TrustedCertificateStore();
    public final AnonymousClass1 mCheckRevocation = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.certificate.CertificatePolicy$1, reason: invalid class name */
    public final class AnonymousClass1 extends ThreadLocal {
        @Override // java.lang.ThreadLocal
        public final /* bridge */ /* synthetic */ Object initialValue() {
            return Boolean.FALSE;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RollbackRefreshHandler extends Handler {
        public RollbackRefreshHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            CertificatePolicy certificatePolicy = CertificatePolicy.this;
            if (i == 0 || i == 1 || i == 2) {
                certificatePolicy.mRollbackRefresh.execute(i, message.arg1);
                return;
            }
            if (i == 3) {
                RollbackRefreshOperation rollbackRefreshOperation = certificatePolicy.mRollbackRefresh;
                Iterator it = ((ArrayList) rollbackRefreshOperation.mUtils.getAllUsersId()).iterator();
                while (it.hasNext()) {
                    rollbackRefreshOperation.execute(2, ((Integer) it.next()).intValue());
                }
                return;
            }
            if (i != 4) {
                return;
            }
            RollbackRefreshOperation rollbackRefreshOperation2 = certificatePolicy.mRollbackRefresh;
            int i2 = message.arg1;
            ((HashSet) rollbackRefreshOperation2.mPendingKeystoreAction).remove(Integer.valueOf(i2));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrustListOperation {
        public final int mAction;
        public final CertificateCache mCache;
        public final String mDbColumn;
        public final EdmKeyStore mKeyStore;

        public TrustListOperation(CertificatePolicy certificatePolicy, int i) {
            this.mAction = i;
            if (i != 0) {
                if (i != 1) {
                    if (i == 2) {
                        this.mKeyStore = certificatePolicy.mTrustedKeyStore;
                        this.mDbColumn = "trustedCaList";
                        this.mCache = certificatePolicy.mTrustedCache;
                        return;
                    } else if (i == 3) {
                        this.mKeyStore = certificatePolicy.mUntrustedKeyStore;
                        this.mDbColumn = "untrustedCertsList";
                        this.mCache = certificatePolicy.mUntrustedCache;
                        return;
                    } else if (i != 4) {
                        if (i != 5) {
                            return;
                        }
                    }
                }
                this.mKeyStore = certificatePolicy.mUntrustedKeyStore;
                this.mDbColumn = "untrustedCertsList";
                this.mCache = certificatePolicy.mUntrustedCache;
                return;
            }
            this.mKeyStore = certificatePolicy.mTrustedKeyStore;
            this.mDbColumn = "trustedCaList";
            this.mCache = certificatePolicy.mTrustedCache;
        }
    }

    /* renamed from: -$$Nest$mdisplayAppSignature, reason: not valid java name */
    public static void m498$$Nest$mdisplayAppSignature(CertificatePolicy certificatePolicy, String str) {
        PackageInfo packageInfo;
        Signature[] signatureArr;
        ApplicationInfo applicationInfo;
        certificatePolicy.getClass();
        int callingUid = Binder.getCallingUid();
        if (certificatePolicy.isSignatureIdentityInformationEnabled(null, false)) {
            try {
                PackageManagerAdapter packageManagerAdapter = certificatePolicy.mPackageManagerAdapter;
                int userId = UserHandle.getUserId(callingUid);
                packageManagerAdapter.getClass();
                packageInfo = PackageManagerAdapter.getPackageInfo(8768, userId, str);
            } catch (Exception e) {
                e.printStackTrace();
                packageInfo = null;
            }
            if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || signatureArr.length <= 0) {
                return;
            }
            try {
                applicationInfo = certificatePolicy.mPackageManager.getApplicationInfo(str, 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("CertificatePolicy", "Name not found");
                applicationInfo = null;
            }
            String string = applicationInfo != null ? certificatePolicy.mContext.getString(17043039, certificatePolicy.mPackageManager.getApplicationLabel(applicationInfo).toString()) : null;
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList = new ArrayList();
            for (Signature signature : packageInfo.signatures) {
                arrayList.add(signature.toCharsString());
            }
            Iterator it = ((ArrayList) certificatePolicy.getIdentitiesFromSignatures(null, arrayList)).iterator();
            while (it.hasNext()) {
                String[] strArr = (String[]) it.next();
                sb.append("\n");
                sb.append(strArr[0]);
                if (!strArr[1].isEmpty()) {
                    sb.append(" / ");
                    sb.append(strArr[1]);
                }
            }
            StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(string);
            m.append(sb.toString());
            String sb2 = m.toString();
            NotificationManager notificationManager = (NotificationManager) certificatePolicy.mContext.getSystemService("notification");
            long currentTimeMillis = System.currentTimeMillis();
            String replace = sb.toString().replace("\n", "");
            Notification notification = new Notification(R.drawable.stat_notify_error, sb2, currentTimeMillis);
            notification.flags |= 16;
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", str, null));
            intent.putExtra("appInfoPkgName", str);
            intent.setFlags(268435456);
            notification.setLatestEventInfo(certificatePolicy.mContext, string, replace, PendingIntent.getActivity(certificatePolicy.mContext, 0, intent, 67108864));
            notificationManager.notify(certificatePolicy.mUtils.mRandom.nextInt(), notification);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.enterprise.certificate.CertificatePolicy$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.enterprise.certificate.CertificatePolicy$2] */
    /* JADX WARN: Type inference failed for: r1v11, types: [android.content.BroadcastReceiver, com.android.server.enterprise.certificate.CertificatePolicy$2] */
    public CertificatePolicy(Context context) {
        this.mPackageChangeIntentReceiver = null;
        this.mDarManagerService = null;
        final int i = 1;
        this.mBootReceiver = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.certificate.CertificatePolicy.2
            public final /* synthetic */ CertificatePolicy this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i) {
                    case 0:
                        this.this$0.getClass();
                        Uri data = intent.getData();
                        String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                        String action = intent.getAction();
                        boolean booleanExtra = intent.getBooleanExtra("isMarketInstallation", false);
                        if (schemeSpecificPart != null) {
                            try {
                                String trim = schemeSpecificPart.trim();
                                if (trim.length() <= 0 || action == null) {
                                    return;
                                }
                                String trim2 = action.trim();
                                if (trim2.length() > 0 && trim2.equals("android.intent.action.PACKAGE_ADDED") && booleanExtra) {
                                    CertificatePolicy.m498$$Nest$mdisplayAppSignature(this.this$0, trim);
                                    return;
                                }
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    case 1:
                        String action2 = intent.getAction();
                        Log.d("CertificatePolicy", action2);
                        if (action2 != null) {
                            if (action2.equals("android.intent.action.LOCKED_BOOT_COMPLETED") || action2.equals("com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL")) {
                                CertificatePolicy certificatePolicy = this.this$0;
                                certificatePolicy.mBootCompleted = true;
                                certificatePolicy.mContext.unregisterReceiver(certificatePolicy.mBootReceiver);
                                CertificatePolicy certificatePolicy2 = this.this$0;
                                certificatePolicy2.initCache(0);
                                certificatePolicy2.initCache(1);
                                this.this$0.executeRollbackRefresh(3, -1);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        String action3 = intent.getAction();
                        if (action3 != null) {
                            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                            if (action3.equals("android.intent.action.USER_REMOVED")) {
                                if (intExtra != 0) {
                                    CertificatePolicy certificatePolicy3 = this.this$0;
                                    synchronized (certificatePolicy3.mLock) {
                                        try {
                                            certificatePolicy3.mNativeKeyStore.cleanUid(intExtra);
                                            certificatePolicy3.mUserKeyStore.cleanUid(intExtra);
                                            if (((HashSet) certificatePolicy3.mRollbackRefresh.mPendingKeystoreAction).contains(Integer.valueOf(intExtra))) {
                                                certificatePolicy3.executeRollbackRefresh(4, intExtra);
                                            }
                                            certificatePolicy3.mTrustedCache.removeUserFromCache(intExtra);
                                            certificatePolicy3.mUntrustedCache.removeUserFromCache(intExtra);
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                            if (!"android.intent.action.USER_ADDED".equals(action3)) {
                                if ("android.intent.action.DEVICE_LOCKED_CHANGED".equals(action3) || "android.intent.action.BOOT_COMPLETED".equals(action3)) {
                                    this.this$0.notifyUserKeystoreUnlocked(intExtra);
                                    return;
                                }
                                return;
                            }
                            this.this$0.getClass();
                            ((PersonaManagerAdapter) CertificatePolicy.getPersonaManagerAdapter$2()).getClass();
                            if (SemPersonaManager.isKnoxId(intExtra)) {
                                return;
                            }
                            Log.i("CertificatePolicy", "Reloading cache for new user");
                            CertificatePolicy certificatePolicy4 = this.this$0;
                            certificatePolicy4.initCache(0);
                            certificatePolicy4.initCache(1);
                            return;
                        }
                        return;
                }
            }
        };
        final int i2 = 2;
        this.mUserReceiver = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.certificate.CertificatePolicy.2
            public final /* synthetic */ CertificatePolicy this$0;

            {
                this.this$0 = this;
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                switch (i2) {
                    case 0:
                        this.this$0.getClass();
                        Uri data = intent.getData();
                        String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                        String action = intent.getAction();
                        boolean booleanExtra = intent.getBooleanExtra("isMarketInstallation", false);
                        if (schemeSpecificPart != null) {
                            try {
                                String trim = schemeSpecificPart.trim();
                                if (trim.length() <= 0 || action == null) {
                                    return;
                                }
                                String trim2 = action.trim();
                                if (trim2.length() > 0 && trim2.equals("android.intent.action.PACKAGE_ADDED") && booleanExtra) {
                                    CertificatePolicy.m498$$Nest$mdisplayAppSignature(this.this$0, trim);
                                    return;
                                }
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                        return;
                    case 1:
                        String action2 = intent.getAction();
                        Log.d("CertificatePolicy", action2);
                        if (action2 != null) {
                            if (action2.equals("android.intent.action.LOCKED_BOOT_COMPLETED") || action2.equals("com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL")) {
                                CertificatePolicy certificatePolicy = this.this$0;
                                certificatePolicy.mBootCompleted = true;
                                certificatePolicy.mContext.unregisterReceiver(certificatePolicy.mBootReceiver);
                                CertificatePolicy certificatePolicy2 = this.this$0;
                                certificatePolicy2.initCache(0);
                                certificatePolicy2.initCache(1);
                                this.this$0.executeRollbackRefresh(3, -1);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        String action3 = intent.getAction();
                        if (action3 != null) {
                            int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                            if (action3.equals("android.intent.action.USER_REMOVED")) {
                                if (intExtra != 0) {
                                    CertificatePolicy certificatePolicy3 = this.this$0;
                                    synchronized (certificatePolicy3.mLock) {
                                        try {
                                            certificatePolicy3.mNativeKeyStore.cleanUid(intExtra);
                                            certificatePolicy3.mUserKeyStore.cleanUid(intExtra);
                                            if (((HashSet) certificatePolicy3.mRollbackRefresh.mPendingKeystoreAction).contains(Integer.valueOf(intExtra))) {
                                                certificatePolicy3.executeRollbackRefresh(4, intExtra);
                                            }
                                            certificatePolicy3.mTrustedCache.removeUserFromCache(intExtra);
                                            certificatePolicy3.mUntrustedCache.removeUserFromCache(intExtra);
                                        } finally {
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                            if (!"android.intent.action.USER_ADDED".equals(action3)) {
                                if ("android.intent.action.DEVICE_LOCKED_CHANGED".equals(action3) || "android.intent.action.BOOT_COMPLETED".equals(action3)) {
                                    this.this$0.notifyUserKeystoreUnlocked(intExtra);
                                    return;
                                }
                                return;
                            }
                            this.this$0.getClass();
                            ((PersonaManagerAdapter) CertificatePolicy.getPersonaManagerAdapter$2()).getClass();
                            if (SemPersonaManager.isKnoxId(intExtra)) {
                                return;
                            }
                            Log.i("CertificatePolicy", "Reloading cache for new user");
                            CertificatePolicy certificatePolicy4 = this.this$0;
                            certificatePolicy4.initCache(0);
                            certificatePolicy4.initCache(1);
                            return;
                        }
                        return;
                }
            }
        };
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        this.mPackageManager = context.getPackageManager();
        try {
            if (this.mPackageChangeIntentReceiver == null) {
                IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
                intentFilter.addDataScheme("package");
                final int i3 = 0;
                ?? r1 = new BroadcastReceiver(this) { // from class: com.android.server.enterprise.certificate.CertificatePolicy.2
                    public final /* synthetic */ CertificatePolicy this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i3) {
                            case 0:
                                this.this$0.getClass();
                                Uri data = intent.getData();
                                String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                                String action = intent.getAction();
                                boolean booleanExtra = intent.getBooleanExtra("isMarketInstallation", false);
                                if (schemeSpecificPart != null) {
                                    try {
                                        String trim = schemeSpecificPart.trim();
                                        if (trim.length() <= 0 || action == null) {
                                            return;
                                        }
                                        String trim2 = action.trim();
                                        if (trim2.length() > 0 && trim2.equals("android.intent.action.PACKAGE_ADDED") && booleanExtra) {
                                            CertificatePolicy.m498$$Nest$mdisplayAppSignature(this.this$0, trim);
                                            return;
                                        }
                                        return;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return;
                                    }
                                }
                                return;
                            case 1:
                                String action2 = intent.getAction();
                                Log.d("CertificatePolicy", action2);
                                if (action2 != null) {
                                    if (action2.equals("android.intent.action.LOCKED_BOOT_COMPLETED") || action2.equals("com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL")) {
                                        CertificatePolicy certificatePolicy = this.this$0;
                                        certificatePolicy.mBootCompleted = true;
                                        certificatePolicy.mContext.unregisterReceiver(certificatePolicy.mBootReceiver);
                                        CertificatePolicy certificatePolicy2 = this.this$0;
                                        certificatePolicy2.initCache(0);
                                        certificatePolicy2.initCache(1);
                                        this.this$0.executeRollbackRefresh(3, -1);
                                        return;
                                    }
                                    return;
                                }
                                return;
                            default:
                                String action3 = intent.getAction();
                                if (action3 != null) {
                                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", 0);
                                    if (action3.equals("android.intent.action.USER_REMOVED")) {
                                        if (intExtra != 0) {
                                            CertificatePolicy certificatePolicy3 = this.this$0;
                                            synchronized (certificatePolicy3.mLock) {
                                                try {
                                                    certificatePolicy3.mNativeKeyStore.cleanUid(intExtra);
                                                    certificatePolicy3.mUserKeyStore.cleanUid(intExtra);
                                                    if (((HashSet) certificatePolicy3.mRollbackRefresh.mPendingKeystoreAction).contains(Integer.valueOf(intExtra))) {
                                                        certificatePolicy3.executeRollbackRefresh(4, intExtra);
                                                    }
                                                    certificatePolicy3.mTrustedCache.removeUserFromCache(intExtra);
                                                    certificatePolicy3.mUntrustedCache.removeUserFromCache(intExtra);
                                                } finally {
                                                }
                                            }
                                            return;
                                        }
                                        return;
                                    }
                                    if (!"android.intent.action.USER_ADDED".equals(action3)) {
                                        if ("android.intent.action.DEVICE_LOCKED_CHANGED".equals(action3) || "android.intent.action.BOOT_COMPLETED".equals(action3)) {
                                            this.this$0.notifyUserKeystoreUnlocked(intExtra);
                                            return;
                                        }
                                        return;
                                    }
                                    this.this$0.getClass();
                                    ((PersonaManagerAdapter) CertificatePolicy.getPersonaManagerAdapter$2()).getClass();
                                    if (SemPersonaManager.isKnoxId(intExtra)) {
                                        return;
                                    }
                                    Log.i("CertificatePolicy", "Reloading cache for new user");
                                    CertificatePolicy certificatePolicy4 = this.this$0;
                                    certificatePolicy4.initCache(0);
                                    certificatePolicy4.initCache(1);
                                    return;
                                }
                                return;
                        }
                    }
                };
                this.mPackageChangeIntentReceiver = r1;
                context.registerReceiver(r1, intentFilter);
                Log.d("CertificatePolicy", "registerPackageChangeReceiver() : Done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mContext.registerReceiver(this.mBootReceiver, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.LOCKED_BOOT_COMPLETED", "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL"), 2);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_SWITCHED");
        ActivityManagerService$$ExternalSyntheticOutline0.m(intentFilter2, "android.intent.action.USER_REMOVED", "android.intent.action.USER_ADDED", "android.intent.action.DEVICE_LOCKED_CHANGED", "android.intent.action.BOOT_COMPLETED");
        this.mContext.registerReceiver(this.mUserReceiver, intentFilter2, 2);
        this.mTrustedKeyStore = EdmKeyStore.getInstance(0);
        this.mUserKeyStore = EdmKeyStore.getInstance(1);
        this.mNativeKeyStore = EdmKeyStore.getInstance(2);
        this.mUntrustedKeyStore = EdmKeyStore.getInstance(3);
        this.mUtils = new CertificateUtil(this.mContext);
        this.mTrustedCache = new CertificateCache(this.mContext, this.mEdmStorageProvider);
        this.mUntrustedCache = new CertificateCache(this.mContext, this.mEdmStorageProvider);
        this.mRollbackRefreshHandler = new RollbackRefreshHandler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("RollbackRefreshHandler").getLooper());
        this.mRollbackRefresh = new RollbackRefreshOperation(this, this.mContext);
        this.mPackageManagerAdapter = PackageManagerAdapter.getInstance(this.mContext);
        this.mEnterpriseDumpHelper = new EnterpriseDumpHelper(this.mContext);
        this.mDarManagerService = (DarManagerService) ServiceManager.getService("dar");
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0083 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0066 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void auditLog(int r16, int r17, java.util.List r18) {
        /*
            r0 = r17
            long r1 = android.os.Binder.clearCallingIdentity()
            r3 = r18
            java.util.ArrayList r3 = (java.util.ArrayList) r3     // Catch: java.lang.Throwable -> L2e
            java.util.Iterator r3 = r3.iterator()     // Catch: java.lang.Throwable -> L2e
        Le:
            boolean r4 = r3.hasNext()     // Catch: java.lang.Throwable -> L2e
            if (r4 == 0) goto L94
            java.lang.Object r4 = r3.next()     // Catch: java.lang.Throwable -> L2e
            java.security.cert.X509Certificate r4 = (java.security.cert.X509Certificate) r4     // Catch: java.lang.Throwable -> L2e
            if (r4 == 0) goto Le
            java.security.Principal r5 = r4.getSubjectDN()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r6 = "null"
            if (r5 == 0) goto L31
            java.security.Principal r5 = r4.getSubjectDN()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L2e
            goto L32
        L2e:
            r0 = move-exception
            goto L98
        L31:
            r5 = r6
        L32:
            java.security.Principal r7 = r4.getIssuerDN()     // Catch: java.lang.Throwable -> L2e
            if (r7 == 0) goto L40
            java.security.Principal r4 = r4.getIssuerDN()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r6 = r4.toString()     // Catch: java.lang.Throwable -> L2e
        L40:
            r4 = -1
            if (r0 == 0) goto L5d
            r7 = 1
            r8 = 0
            if (r0 == r7) goto L5b
            r9 = 2
            if (r0 == r9) goto L58
            r9 = 3
            if (r0 == r9) goto L60
            r7 = 4
            if (r0 == r7) goto L5d
            r7 = 5
            if (r0 == r7) goto L55
        L53:
            r7 = r4
            goto L60
        L55:
            java.lang.String r8 = "Admin %d has added a certificate to the untrusted DB. Subject : %s, Issuer : %s"
            goto L53
        L58:
            java.lang.String r8 = "Admin %d has removed a certificate from the trusted DB. Subject : %s, Issuer : %s"
            goto L53
        L5b:
            r7 = 0
            goto L60
        L5d:
            java.lang.String r8 = "Admin %d has added a certificate to the trusted DB. Subject : %s, Issuer : %s"
            goto L53
        L60:
            if (r8 != 0) goto L64
            if (r7 == r4) goto Le
        L64:
            if (r8 == 0) goto L83
            int r12 = android.os.Process.myPid()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r13 = "CertificatePolicy"
            java.lang.Integer r4 = java.lang.Integer.valueOf(r16)     // Catch: java.lang.Throwable -> L2e
            java.lang.Object[] r4 = new java.lang.Object[]{r4, r5, r6}     // Catch: java.lang.Throwable -> L2e
            java.lang.String r14 = java.lang.String.format(r8, r4)     // Catch: java.lang.Throwable -> L2e
            int r15 = android.os.UserHandle.getUserId(r16)     // Catch: java.lang.Throwable -> L2e
            r10 = 1
            r11 = 1
            r9 = 5
            android.sec.enterprise.auditlog.AuditLog.logAsUser(r9, r10, r11, r12, r13, r14, r15)     // Catch: java.lang.Throwable -> L2e
            goto Le
        L83:
            int r4 = android.os.UserHandle.getUserId(r16)     // Catch: java.lang.Throwable -> L2e
            java.lang.Integer r8 = java.lang.Integer.valueOf(r16)     // Catch: java.lang.Throwable -> L2e
            java.lang.Object[] r5 = new java.lang.Object[]{r8, r5, r6}     // Catch: java.lang.Throwable -> L2e
            android.sec.enterprise.auditlog.AuditLog.logEventAsUser(r4, r7, r5)     // Catch: java.lang.Throwable -> L2e
            goto Le
        L94:
            android.os.Binder.restoreCallingIdentity(r1)
            return
        L98:
            android.os.Binder.restoreCallingIdentity(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.auditLog(int, int, java.util.List):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00bb, code lost:
    
        if (r9 == null) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:5:0x0085, code lost:
    
        if (r9 != null) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00c1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.security.cert.X509Certificate findIssuerInAndroidKeystore(android.content.Context r9, java.security.cert.X509Certificate r10, int r11) {
        /*
            java.lang.String r0 = "CertificatePolicy"
            java.lang.String r1 = "findIssuerInAndroidKeystore "
            java.lang.String r2 = "findIssuerInAndroidKeystore - is KeyChainService running for user "
            long r3 = android.os.Binder.clearCallingIdentity()
            r5 = 0
            android.os.UserHandle r6 = new android.os.UserHandle     // Catch: java.lang.Throwable -> L8e java.lang.AssertionError -> L90 java.lang.InterruptedException -> L92
            r6.<init>(r11)     // Catch: java.lang.Throwable -> L8e java.lang.AssertionError -> L90 java.lang.InterruptedException -> L92
            android.security.KeyChain$KeyChainConnection r9 = android.security.KeyChain.bindAsUser(r9, r6)     // Catch: java.lang.Throwable -> L8e java.lang.AssertionError -> L90 java.lang.InterruptedException -> L92
            if (r9 == 0) goto L85
            android.security.IKeyChainService r6 = r9.getService()     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            if (r6 == 0) goto L85
            r7 = 1
            java.security.cert.Certificate[] r7 = new java.security.cert.Certificate[r7]     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            r8 = 0
            r7[r8] = r10     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            byte[] r10 = android.security.Credentials.convertToPem(r7)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            byte[] r10 = r6.findIssuer(r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            if (r10 == 0) goto L85
            java.lang.String r6 = "X.509"
            java.security.cert.CertificateFactory r6 = java.security.cert.CertificateFactory.getInstance(r6)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            java.io.ByteArrayInputStream r7 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            r7.<init>(r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            java.security.cert.Certificate r10 = r6.generateCertificate(r7)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            java.security.cert.X509Certificate r10 = (java.security.cert.X509Certificate) r10     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e android.os.RemoteException -> L50 java.security.cert.CertificateException -> L52 java.io.IOException -> L54 java.lang.AssertionError -> L95
            if (r10 == 0) goto L48
            r9.close()
            android.os.Binder.restoreCallingIdentity(r3)
            return r10
        L48:
            r5 = r10
            goto L85
        L4a:
            r10 = move-exception
            r5 = r9
            goto Lbf
        L4e:
            r10 = move-exception
            goto Lac
        L50:
            r10 = move-exception
            goto L56
        L52:
            r10 = move-exception
            goto L66
        L54:
            r10 = move-exception
            goto L76
        L56:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            r6.append(r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            java.lang.String r10 = r6.toString()     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            android.util.Log.e(r0, r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            goto L85
        L66:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            r6.append(r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            java.lang.String r10 = r6.toString()     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            android.util.Log.e(r0, r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            goto L85
        L76:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            r6.<init>(r1)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            r6.append(r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            java.lang.String r10 = r6.toString()     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
            android.util.Log.e(r0, r10)     // Catch: java.lang.Throwable -> L4a java.lang.InterruptedException -> L4e java.lang.AssertionError -> L95
        L85:
            if (r9 == 0) goto L8a
        L87:
            r9.close()
        L8a:
            android.os.Binder.restoreCallingIdentity(r3)
            goto Lbe
        L8e:
            r10 = move-exception
            goto Lbf
        L90:
            r9 = r5
            goto L95
        L92:
            r10 = move-exception
            r9 = r5
            goto Lac
        L95:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a
            r10.<init>(r2)     // Catch: java.lang.Throwable -> L4a
            r10.append(r11)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r11 = "?"
            r10.append(r11)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L4a
            android.util.Log.e(r0, r10)     // Catch: java.lang.Throwable -> L4a
            if (r9 == 0) goto L8a
            goto L87
        Lac:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4a
            r11.<init>(r1)     // Catch: java.lang.Throwable -> L4a
            r11.append(r10)     // Catch: java.lang.Throwable -> L4a
            java.lang.String r10 = r11.toString()     // Catch: java.lang.Throwable -> L4a
            android.util.Log.e(r0, r10)     // Catch: java.lang.Throwable -> L4a
            if (r9 == 0) goto L8a
            goto L87
        Lbe:
            return r5
        Lbf:
            if (r5 == 0) goto Lc4
            r5.close()
        Lc4:
            android.os.Binder.restoreCallingIdentity(r3)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.findIssuerInAndroidKeystore(android.content.Context, java.security.cert.X509Certificate, int):java.security.cert.X509Certificate");
    }

    public static IPersonaManagerAdapter getPersonaManagerAdapter$2() {
        return (IPersonaManagerAdapter) AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
    }

    public static String getValidStr(String str) {
        if (str == null) {
            return null;
        }
        try {
            String trim = str.trim();
            if (trim.length() > 0) {
                return trim;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendCertificatePolicyCacheUpdateCommand(Context context, String str) {
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CERTIFICATE_POLICY_CHANGED_INTERNAL");
        if (str != null) {
            intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_POLICY_TYPE_CHANGED_INTERNAL", str);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            context.sendBroadcastAsUser(intent, UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ContentValues toContentValues(PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("pkgName", permissionApplicationPrivateKey.getPackageName());
        contentValues.put("host", permissionApplicationPrivateKey.getHost());
        contentValues.put("port", Integer.valueOf(permissionApplicationPrivateKey.getPort()));
        contentValues.put("alias", permissionApplicationPrivateKey.getAlias());
        if (permissionApplicationPrivateKey.getStorageName() != null) {
            contentValues.put("storageName", permissionApplicationPrivateKey.getStorageName());
        }
        return contentValues;
    }

    public static PermissionApplicationPrivateKey validatePkey(PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
        if (permissionApplicationPrivateKey == null) {
            return null;
        }
        String validStr = getValidStr(permissionApplicationPrivateKey.getPackageName());
        String validStr2 = getValidStr(permissionApplicationPrivateKey.getHost());
        String validStr3 = getValidStr(permissionApplicationPrivateKey.getAlias());
        int port = permissionApplicationPrivateKey.getPort();
        String validStr4 = getValidStr(permissionApplicationPrivateKey.getStorageName());
        if (validStr == null || validStr2 == null || validStr3 == null) {
            return null;
        }
        return new PermissionApplicationPrivateKey(validStr, validStr2, port, validStr3, validStr4);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005f A[LOOP:0: B:19:0x0059->B:21:0x005f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0129 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x00cb  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:106:0x0107 -> B:99:0x010d). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addCertificateList(com.samsung.android.knox.ContextInfo r18, java.util.List r19, int r20) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.addCertificateList(com.samsung.android.knox.ContextInfo, java.util.List, int):boolean");
    }

    public final boolean addPermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
        UniversalCredentialManagerService universalCredentialManagerService;
        boolean z;
        int i = enforceCertificatePermission$1(contextInfo).mCallerUid;
        PermissionApplicationPrivateKey validatePkey = validatePkey(permissionApplicationPrivateKey);
        if (validatePkey == null) {
            return false;
        }
        if (!TextUtils.isEmpty(validatePkey.getStorageName())) {
            String storageName = validatePkey.getStorageName();
            synchronized (this) {
                try {
                    if (this.mUCMService == null) {
                        this.mUCMService = (UniversalCredentialManagerService) EnterpriseService.getPolicyService("knox_ucsm_policy");
                    }
                    universalCredentialManagerService = this.mUCMService;
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (universalCredentialManagerService != null) {
                CredentialStorage[] availableCredentialStorages = universalCredentialManagerService.getAvailableCredentialStorages(contextInfo, true);
                if (availableCredentialStorages != null) {
                    for (CredentialStorage credentialStorage : availableCredentialStorages) {
                        if (credentialStorage != null && storageName.equals(credentialStorage.name)) {
                            this.mUCMService.enforceSecurityPermission(contextInfo, credentialStorage);
                            z = true;
                            break;
                        }
                    }
                }
                z = false;
                if (!z) {
                    DualAppManagerService$$ExternalSyntheticOutline0.m("Not able to find credential storage ", storageName, "CertificatePolicy");
                }
            } else {
                Log.d("CertificatePolicy", "Couldn't enforce UCM permission. Is UCM service running?");
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        int userId = UserHandle.getUserId(i);
        if (validatePkey.getHost().equals("*")) {
            Iterator it = ((ArrayList) retrieveAppPermissionsFromDb(userId)).iterator();
            while (it.hasNext()) {
                PermissionApplicationPrivateKey permissionApplicationPrivateKey2 = (PermissionApplicationPrivateKey) it.next();
                if (validatePkey.getPackageName().equalsIgnoreCase(permissionApplicationPrivateKey2.getPackageName()) && permissionApplicationPrivateKey2.getHost().equals("*")) {
                    Log.e("CertificatePolicy", "Operation not allowed, another rule for given package name has host set to wildcard");
                    return false;
                }
            }
        }
        boolean privateKeyGrant = setPrivateKeyGrant(userId, validatePkey.getPackageName(), validatePkey.getAlias(), validatePkey.getStorageName(), true);
        if (!privateKeyGrant) {
            return privateKeyGrant;
        }
        ContentValues contentValues = toContentValues(validatePkey);
        contentValues.put("adminUid", Integer.valueOf(i));
        return this.mEdmStorageProvider.insert("PermAppPrivateKey", contentValues) > 0;
    }

    public final boolean addTrustedCaCertificateList(ContextInfo contextInfo, List list) {
        return addCertificateList(enforceCertificatePermission$1(contextInfo), list, 0);
    }

    public final boolean addUntrustedCertificateList(ContextInfo contextInfo, List list) {
        return addCertificateList(enforceCertificatePermission$1(contextInfo), list, 1);
    }

    public final boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z) {
        ContextInfo enforceCertificatePermission$1 = enforceCertificatePermission$1(contextInfo);
        if (enforceCertificatePermission$1 == null) {
            return false;
        }
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("CERTIFICATE", enforceCertificatePermission$1.mCallerUid, z, 0, "allowUserRemoveCertificate");
        if (!putBoolean) {
            return putBoolean;
        }
        this.mUtils.sendIntentToSettings(UserHandle.getUserId(enforceCertificatePermission$1.mCallerUid), this.mBootCompleted);
        return putBoolean;
    }

    public final boolean clearCertificates(int i, int i2) {
        int userId = UserHandle.getUserId(i);
        TrustListOperation trustListOperation = new TrustListOperation(this, i2);
        if (trustListOperation.mCache.getCacheEntrySize(userId) == 0) {
            return true;
        }
        String str = trustListOperation.mDbColumn;
        List convertStringToList = this.mEdmStorageProvider.putString(i, 0, "CERTIFICATE", str, null) ? Utils.convertStringToList(this.mEdmStorageProvider.getString(i, 0, "CERTIFICATE", str)) : null;
        if (convertStringToList == null || convertStringToList.size() <= 0) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(((HashMap) trustListOperation.mKeyStore.getCertificates(userId, convertStringToList)).values());
        removeAliases(trustListOperation, i, convertStringToList, arrayList);
        return true;
    }

    public final boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo) {
        int i = enforceCertificatePermission$1(contextInfo).mCallerUid;
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        Iterator it = ((ArrayList) this.mEdmStorageProvider.getValues("PermAppPrivateKey", new String[]{"pkgName", "alias", "storageName"}, contentValues)).iterator();
        while (it.hasNext()) {
            ContentValues contentValues2 = (ContentValues) it.next();
            setPrivateKeyGrant(UserHandle.getUserId(i), contentValues2.getAsString("pkgName"), contentValues2.getAsString("alias"), contentValues2.getAsString("storageName"), false);
        }
        return this.mEdmStorageProvider.delete("PermAppPrivateKey", contentValues) > 0;
    }

    public final boolean clearTrustedCaCertificateList(ContextInfo contextInfo) {
        return clearCertificates(enforceCertificatePermission$1(contextInfo).mCallerUid, 2);
    }

    public final boolean clearUntrustedCertificateList(ContextInfo contextInfo) {
        return clearCertificates(enforceCertificatePermission$1(contextInfo).mCallerUid, 3);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.DUMP") != 0) {
            printWriter.println("Permission Denial: can't dump CertificatePolicy");
            return;
        }
        this.mTrustedCache.dump(printWriter, "[Trusted Cache]" + System.lineSeparator());
        this.mUntrustedCache.dump(printWriter, "[Untrusted Cache]" + System.lineSeparator());
        this.mTrustedKeyStore.dump(printWriter, "[Trusted Keystore]" + System.lineSeparator());
        this.mUserKeyStore.dump(printWriter, "[User Keystore]" + System.lineSeparator());
        this.mNativeKeyStore.dump(printWriter, "[Native Keystore]" + System.lineSeparator());
        this.mUntrustedKeyStore.dump(printWriter, "[Untrusted Keystore]" + System.lineSeparator());
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "PermAppPrivateKey", new String[]{"adminUid", "pkgName", "host", "port", "alias", "storageName"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "CERTIFICATE", new String[]{"trustedCaList", "untrustedCertsList", "signatureIdentityInformationEnabled", "notificateSignatureFailureToUser", "validateCertificateAtInstall", "allowUserRemoveCertificate"}, null);
        this.mEnterpriseDumpHelper.dumpTable(printWriter, "generic", new String[]{"systemDisabledList", "systemPrevDisabledList", "userRemovedList", "nativeRemovedList", "nativeRemovedList_wifi"}, null);
    }

    public final boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean("CERTIFICATE", enforceCertificatePermission$1(contextInfo).mCallerUid, z, 0, "notificateSignatureFailureToUser");
    }

    public final boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z) {
        boolean putBoolean = this.mEdmStorageProvider.putBoolean("CERTIFICATE", enforceCertificatePermission$1(contextInfo).mCallerUid, z, 0, "validateCertificateAtInstall");
        if (putBoolean) {
            sendCertificatePolicyCacheUpdateCommand(this.mContext, "CERTIFICATE_VALIDATION");
        }
        return putBoolean;
    }

    public final boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z) {
        return this.mEdmStorageProvider.putBoolean("CERTIFICATE", enforceCertificatePermission$1(contextInfo).mCallerUid, z, 0, "signatureIdentityInformationEnabled");
    }

    public final ContextInfo enforceCertificatePermission$1(ContextInfo contextInfo) {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM.enforceActiveAdminPermissionByContext(contextInfo, new ArrayList(Arrays.asList("com.samsung.android.knox.permission.KNOX_CERTIFICATE")));
    }

    public final void executeRollbackRefresh(int i, int i2) {
        Message message = new Message();
        message.what = i;
        message.arg1 = i2;
        this.mRollbackRefreshHandler.sendMessage(message);
    }

    public final List getCertificateChainFromSystem(X509Certificate x509Certificate, int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(x509Certificate);
        int i2 = 0;
        while (true) {
            X509Certificate x509Certificate2 = (X509Certificate) arrayList.get(i2);
            if (x509Certificate2.getSubjectX500Principal().equals(x509Certificate2.getIssuerX500Principal())) {
                return arrayList;
            }
            X509Certificate findIssuerInAndroidKeystore = findIssuerInAndroidKeystore(this.mContext, x509Certificate2, i);
            if (findIssuerInAndroidKeystore == null) {
                Log.d("CertificatePolicy", "getCertificateChain error. Could not find certificate.");
                throw new CertificateException("Could not build certificate chain; certificate not found: " + x509Certificate2.getIssuerX500Principal().getName());
            }
            arrayList.add(findIssuerInAndroidKeystore);
            i2++;
        }
    }

    public final List getCertificatesFromDb(int i, String str, EdmKeyStore edmKeyStore) {
        ArrayList arrayList = new ArrayList();
        List valuesListAsUser = this.mEdmStorageProvider.getValuesListAsUser(0, i, "CERTIFICATE", new String[]{"adminUid", str});
        ArrayList arrayList2 = new ArrayList();
        Iterator it = ((ArrayList) valuesListAsUser).iterator();
        while (it.hasNext()) {
            ContentValues contentValues = (ContentValues) it.next();
            String asString = contentValues.getAsString(str);
            if (!TextUtils.isEmpty(asString)) {
                CertificateControlInfo certificateControlInfo = new CertificateControlInfo();
                Integer asInteger = contentValues.getAsInteger("adminUid");
                if (asInteger != null) {
                    certificateControlInfo.adminPackageName = this.mEdmStorageProvider.getPackageNameForUid(asInteger.intValue());
                }
                arrayList2.clear();
                arrayList2.addAll(((HashMap) edmKeyStore.getCertificates(i, Utils.convertStringToList(asString))).values());
                certificateControlInfo.entries = arrayList2;
                arrayList.add(certificateControlInfo);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0040  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List getCertificatesList(int r5, int r6) {
        /*
            r4 = this;
            java.lang.String r0 = "trustedCaList"
            if (r6 == 0) goto L2f
            r1 = 1
            java.lang.String r2 = "untrustedCertsList"
            if (r6 == r1) goto L2a
            r1 = 2
            if (r6 == r1) goto L23
            r1 = 3
            if (r6 == r1) goto L1b
            r1 = 4
            if (r6 == r1) goto L2f
            r0 = 5
            if (r6 == r0) goto L2a
            r0 = 0
            r6 = r0
            r2 = r6
            goto L34
        L1b:
            com.android.server.enterprise.certificate.EdmKeyStore r0 = r4.mUntrustedKeyStore
            com.android.server.enterprise.certificate.CertificateCache r6 = r4.mUntrustedCache
        L1f:
            r3 = r0
            r0 = r6
            r6 = r3
            goto L34
        L23:
            com.android.server.enterprise.certificate.EdmKeyStore r6 = r4.mTrustedKeyStore
            com.android.server.enterprise.certificate.CertificateCache r1 = r4.mTrustedCache
        L27:
            r2 = r0
            r0 = r1
            goto L34
        L2a:
            com.android.server.enterprise.certificate.EdmKeyStore r0 = r4.mUntrustedKeyStore
            com.android.server.enterprise.certificate.CertificateCache r6 = r4.mUntrustedCache
            goto L1f
        L2f:
            com.android.server.enterprise.certificate.EdmKeyStore r6 = r4.mTrustedKeyStore
            com.android.server.enterprise.certificate.CertificateCache r1 = r4.mTrustedCache
            goto L27
        L34:
            int r0 = r0.getCacheEntrySize(r5)
            if (r0 != 0) goto L40
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            return r4
        L40:
            java.util.List r0 = r4.getCertificatesFromDb(r5, r2, r6)
            if (r5 == 0) goto L51
            r5 = 0
            java.util.List r4 = r4.getCertificatesFromDb(r5, r2, r6)
            r5 = r0
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            r5.addAll(r4)
        L51:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.getCertificatesList(int, int):java.util.List");
    }

    public final List getGenericListAsUser(int i, String str) {
        return Utils.convertStringToList(this.mEdmStorageProvider.getGenericValueAsUser(i, str));
    }

    public final List getIdentitiesFromSignatures(ContextInfo contextInfo, List list) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            try {
                try {
                    SslCertificate sslCertificate = new SslCertificate((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(new Signature((String) list.get(i)).toByteArray())));
                    String cName = sslCertificate.getIssuedTo().getCName();
                    String oName = sslCertificate.getIssuedTo().getOName();
                    String uName = sslCertificate.getIssuedTo().getUName();
                    if (oName.isEmpty()) {
                        uName = "";
                        if (cName.isEmpty()) {
                            cName = sslCertificate.getIssuedTo().getDName();
                        }
                    } else {
                        if (!cName.isEmpty()) {
                            uName = cName;
                        }
                        cName = oName;
                    }
                    arrayList.add(new String[]{cName, uName});
                } catch (CertificateException unused) {
                    Log.w("CertificatePolicy", "X509Certificate error");
                    return arrayList;
                }
            } catch (CertificateException unused2) {
                Log.w("CertificatePolicy", "CertificateFactory error");
            }
        }
        return arrayList;
    }

    public final List getListPermissionApplicationPrivateKey(ContextInfo contextInfo) {
        enforceCertificatePermission$1(contextInfo);
        return retrieveAppPermissionsFromDb(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final List getPackagesForPid(int i) {
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (runningAppProcesses == null) {
                return null;
            }
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == i) {
                    String[] strArr = runningAppProcessInfo.pkgList;
                    if (strArr != null) {
                        return Arrays.asList(strArr);
                    }
                    return null;
                }
            }
            return null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean getPolicyValueBackwardCompatibleAsUser(int r7, java.lang.String r8, boolean r9) {
        /*
            r6 = this;
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r0 = getPersonaManagerAdapter$2()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r0 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r0
            r0.getClass()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r7)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L53
            com.android.server.enterprise.storage.EdmStorageProvider r0 = r6.mEdmStorageProvider
            int r0 = r0.getMUMContainerOwnerUid(r7)
            r3 = -1
            if (r0 == r3) goto L38
            com.android.server.enterprise.storage.EdmStorageProvider r3 = r6.mEdmStorageProvider     // Catch: com.android.server.enterprise.storage.SettingNotFoundException -> L23
            java.lang.String r4 = "CERTIFICATE"
            boolean r3 = r3.getBoolean(r0, r2, r4, r8)     // Catch: com.android.server.enterprise.storage.SettingNotFoundException -> L23
            goto L39
        L23:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "getPolicyValueBackwardCompatibleAsUser: "
            r4.<init>(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            java.lang.String r4 = "CertificatePolicy"
            android.util.Log.e(r4, r3)
        L38:
            r3 = r9
        L39:
            int r0 = android.os.UserHandle.getUserId(r0)
            if (r7 == r0) goto L44
            boolean r6 = r6.getStrictestValueAsUser(r7, r8, r9)
            goto L45
        L44:
            r6 = r3
        L45:
            if (r9 != r1) goto L4e
            if (r3 == 0) goto L4c
            if (r6 == 0) goto L4c
            goto L7c
        L4c:
            r1 = r2
            goto L7c
        L4e:
            if (r3 != 0) goto L7c
            if (r6 == 0) goto L4c
            goto L7c
        L53:
            if (r7 <= 0) goto L78
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r0 = getPersonaManagerAdapter$2()
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r0 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r0
            r0.getClass()
            boolean r0 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r7)
            if (r0 != 0) goto L78
            boolean r7 = r6.getStrictestValueAsUser(r7, r8, r9)
            boolean r6 = r6.getStrictestValueAsUser(r2, r8, r9)
            if (r9 != r1) goto L73
            if (r7 == 0) goto L4c
            if (r6 == 0) goto L4c
            goto L7c
        L73:
            if (r7 != 0) goto L7c
            if (r6 == 0) goto L4c
            goto L7c
        L78:
            boolean r1 = r6.getStrictestValueAsUser(r2, r8, r9)
        L7c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.getPolicyValueBackwardCompatibleAsUser(int, java.lang.String, boolean):boolean");
    }

    public final boolean getStrictestValueAsUser(int i, String str, boolean z) {
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(i, "CERTIFICATE", str).iterator();
        while (it.hasNext()) {
            boolean booleanValue = ((Boolean) it.next()).booleanValue();
            if (booleanValue != z) {
                return booleanValue;
            }
        }
        return z;
    }

    public final List getTrustedCaCertificateList(ContextInfo contextInfo) {
        return getCertificatesList(UserHandle.getUserId(enforceCertificatePermission$1(contextInfo).mCallerUid), 4);
    }

    public final List getUntrustedCertificateList(ContextInfo contextInfo) {
        return getCertificatesList(UserHandle.getUserId(enforceCertificatePermission$1(contextInfo).mCallerUid), 5);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0029 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initCache(int r9) {
        /*
            r8 = this;
            java.lang.String r0 = "trustedCaList"
            if (r9 == 0) goto L25
            java.lang.String r1 = "untrustedCertsList"
            r2 = 1
            if (r9 == r2) goto L22
            r2 = 2
            if (r9 == r2) goto L1d
            r2 = 3
            if (r9 == r2) goto L1a
            r2 = 4
            if (r9 == r2) goto L25
            r0 = 5
            if (r9 == r0) goto L22
            r0 = 0
            r1 = r0
            goto L28
        L1a:
            com.android.server.enterprise.certificate.CertificateCache r0 = r8.mUntrustedCache
            goto L28
        L1d:
            com.android.server.enterprise.certificate.CertificateCache r9 = r8.mTrustedCache
        L1f:
            r1 = r0
            r0 = r9
            goto L28
        L22:
            com.android.server.enterprise.certificate.CertificateCache r0 = r8.mUntrustedCache
            goto L28
        L25:
            com.android.server.enterprise.certificate.CertificateCache r9 = r8.mTrustedCache
            goto L1f
        L28:
            monitor-enter(r0)
            java.util.Map r9 = r0.mCache     // Catch: java.lang.Throwable -> L8a
            java.util.HashMap r9 = (java.util.HashMap) r9     // Catch: java.lang.Throwable -> L8a
            r9.clear()     // Catch: java.lang.Throwable -> L8a
            monitor-exit(r0)
            java.lang.String r9 = "adminUid"
            java.lang.String[] r9 = new java.lang.String[]{r9, r1}
            com.android.server.enterprise.utils.CertificateUtil r2 = r8.mUtils
            java.util.List r2 = r2.getAllUsersId()
            java.util.ArrayList r2 = (java.util.ArrayList) r2
            java.util.Iterator r2 = r2.iterator()
        L43:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L89
            java.lang.Object r3 = r2.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            com.android.server.enterprise.storage.EdmStorageProvider r4 = r8.mEdmStorageProvider
            java.lang.String r5 = "CERTIFICATE"
            r6 = 0
            java.util.List r4 = r4.getValuesListAsUser(r6, r3, r5, r9)
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            java.util.Iterator r4 = r4.iterator()
        L62:
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L43
            java.lang.Object r5 = r4.next()
            android.content.ContentValues r5 = (android.content.ContentValues) r5
            java.lang.String r6 = r5.getAsString(r1)
            if (r6 == 0) goto L62
            java.lang.String r7 = "adminUid"
            java.lang.Integer r5 = r5.getAsInteger(r7)
            if (r5 != 0) goto L7d
            goto L62
        L7d:
            java.util.List r6 = com.android.server.enterprise.utils.Utils.convertStringToList(r6)
            int r5 = r5.intValue()
            r0.addToCache(r3, r5, r6)
            goto L62
        L89:
            return
        L8a:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.initCache(int):void");
    }

    public final boolean isCaCertificateDisabledAsUser(String str, int i) {
        return ((ArrayList) getGenericListAsUser(i, "systemDisabledList")).contains(str);
    }

    public final boolean isCaCertificateTrustedAsUser(CertificateInfo certificateInfo, boolean z, int i) {
        return isCaCertificateTrustedAsUser(certificateInfo, z, true, i);
    }

    public final boolean isCaCertificateTrustedAsUser(CertificateInfo certificateInfo, boolean z, boolean z2, int i) {
        if (this.mTrustedCache.getCacheEntrySize(i) == 0 && this.mUntrustedCache.getCacheEntrySize(i) == 0) {
            return true;
        }
        X509Certificate x509Certificate = (X509Certificate) certificateInfo.getCertificate();
        String certificateAlias = this.mCertStore.getCertificateAlias(x509Certificate, true);
        boolean verifyCertificateTrustful = ((certificateAlias == null ? false : certificateAlias.startsWith("system:")) || !z2) ? verifyCertificateTrustful(x509Certificate, 2, i) : verifyCertificateTrustful(x509Certificate, 3, i);
        if (z && !verifyCertificateTrustful) {
            RestrictionToastManager.show(R.string.config_rearDisplayPhysicalAddress);
        }
        return verifyCertificateTrustful;
    }

    public final boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo) {
        enforceCertificatePermission$1(contextInfo);
        return getPolicyValueBackwardCompatibleAsUser(Utils.getCallingOrCurrentUserId(contextInfo), "notificateSignatureFailureToUser", false);
    }

    public final boolean isCertificateTrustedUntrustedEnabledAsUser(int i) {
        return (this.mTrustedCache.getCacheEntrySize(i) == 0 && this.mUntrustedCache.getCacheEntrySize(i) == 0) ? false : true;
    }

    public final boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo) {
        return isCertificateValidationAtInstallEnabledAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isCertificateValidationAtInstallEnabledAsUser(int i) {
        return getPolicyValueBackwardCompatibleAsUser(i, "validateCertificateAtInstall", false);
    }

    public final boolean isOcspCheckEnabled(ContextInfo contextInfo) {
        List packagesForPid = getPackagesForPid(Binder.getCallingPid());
        if (packagesForPid != null && packagesForPid.contains("com.android.certinstaller") && isCertificateValidationAtInstallEnabled(contextInfo)) {
            return ((Boolean) this.mCheckRevocation.get()).booleanValue();
        }
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (packagesForPid != null && applicationPolicy != null) {
            Iterator it = packagesForPid.iterator();
            while (it.hasNext()) {
                if (ApplicationPolicy.getApplicationControlState(contextInfo, (String) it.next(), "OcspCheck") || ApplicationPolicy.getApplicationControlState(contextInfo, "*", "OcspCheck")) {
                    return true;
                }
            }
        }
        return false;
    }

    public final String isPrivateKeyApplicationPermitted(ContextInfo contextInfo, String str, String str2, int i, List list) {
        return isPrivateKeyApplicationPermittedAsUser(str, str2, i, list, Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final String isPrivateKeyApplicationPermittedAsUser(String str, String str2, int i, List list, int i2) {
        String validStr = getValidStr(str);
        String str3 = null;
        if (validStr != null) {
            Iterator it = ((ArrayList) retrieveAppPermissionsFromDb(i2)).iterator();
            while (it.hasNext()) {
                PermissionApplicationPrivateKey permissionApplicationPrivateKey = (PermissionApplicationPrivateKey) it.next();
                if (validStr.equalsIgnoreCase(permissionApplicationPrivateKey.getPackageName())) {
                    if (permissionApplicationPrivateKey.getHost().equals("*")) {
                        str3 = validateAlias(permissionApplicationPrivateKey.getStorageName(), permissionApplicationPrivateKey.getAlias(), validStr, i2, list);
                    } else {
                        str2 = getValidStr(str2);
                        if (str2 != null) {
                            String host = permissionApplicationPrivateKey.getHost();
                            if (!host.startsWith(".")) {
                                host = ".".concat(host);
                            }
                            if (!str2.equalsIgnoreCase(permissionApplicationPrivateKey.getHost())) {
                                Locale locale = Locale.ENGLISH;
                                if (!str2.toLowerCase(locale).endsWith(host.toLowerCase(locale))) {
                                }
                            }
                            if (permissionApplicationPrivateKey.getPort() == -1 || i == permissionApplicationPrivateKey.getPort()) {
                                str3 = validateAlias(permissionApplicationPrivateKey.getStorageName(), permissionApplicationPrivateKey.getAlias(), validStr, i2, list);
                            }
                        }
                    }
                }
                if (str3 != null) {
                    break;
                }
            }
        }
        return str3;
    }

    public final boolean isRevocationCheckEnabled(ContextInfo contextInfo) {
        List packagesForPid = getPackagesForPid(Binder.getCallingPid());
        if (packagesForPid != null && packagesForPid.contains("com.android.certinstaller") && isCertificateValidationAtInstallEnabled(contextInfo)) {
            return ((Boolean) this.mCheckRevocation.get()).booleanValue();
        }
        ApplicationPolicy applicationPolicy = (ApplicationPolicy) EnterpriseService.getPolicyService("application_policy");
        if (packagesForPid != null && applicationPolicy != null) {
            Iterator it = packagesForPid.iterator();
            while (it.hasNext()) {
                if (ApplicationPolicy.getApplicationControlState(contextInfo, (String) it.next(), "RevocationCheck") || ApplicationPolicy.getApplicationControlState(contextInfo, "*", "RevocationCheck")) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        String nameForUid = this.mContext.getPackageManager().getNameForUid(contextInfo.mCallerUid);
        if (nameForUid == null || !nameForUid.equals("com.android.packageinstaller")) {
            contextInfo = enforceCertificatePermission$1(contextInfo);
        }
        Iterator it = this.mEdmStorageProvider.getBooleanListAsUser(UserHandle.getUserId(contextInfo.mCallerUid), "CERTIFICATE", "signatureIdentityInformationEnabled").iterator();
        while (it.hasNext()) {
            if (((Boolean) it.next()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public final boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo) {
        return isUserRemoveCertificatesAllowedAsUser(Utils.getCallingOrCurrentUserId(contextInfo));
    }

    public final boolean isUserRemoveCertificatesAllowedAsUser(int i) {
        return getPolicyValueBackwardCompatibleAsUser(i, "allowUserRemoveCertificate", true);
    }

    public final void notifyCertificateFailure(String str, String str2, boolean z) {
        notifyCertificateFailureAsUser(str, str2, z, Utils.getCallingOrCurrentUserId(null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x01bd, code lost:
    
        if (r12 != 13) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00fd, code lost:
    
        if (r12 != 13) goto L79;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x03a0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:24:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x024b  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x02a5  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x02fe  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0338  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0355  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void notifyCertificateFailureAsUser(java.lang.String r17, java.lang.String r18, boolean r19, final int r20) {
        /*
            Method dump skipped, instructions count: 1022
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.notifyCertificateFailureAsUser(java.lang.String, java.lang.String, boolean, int):void");
    }

    public final void notifyCertificateRemovedAsUser(String str, int i) {
        if (UserHandle.getAppId(Binder.getCallingUid()) != Process.myUid()) {
            throw new SecurityException("Can only be called by system user");
        }
        Intent intent = new Intent("com.samsung.android.knox.intent.action.CERTIFICATE_REMOVED");
        intent.putExtra("com.samsung.android.knox.intent.extra.CERTIFICATE_REMOVED_SUBJECT", str);
        intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID", i);
        ((PersonaManagerAdapter) getPersonaManagerAdapter$2()).getClass();
        int userId = SemPersonaManager.isKnoxId(i) ? UserHandle.getUserId(this.mEdmStorageProvider.getMUMContainerOwnerUid(i)) : i;
        AudioService$$ExternalSyntheticOutline0.m(DirEncryptService$$ExternalSyntheticOutline0.m(userId, "Sending certificate removed intent to user ", " containing: ", str, " (subject), "), i, " (userId)", "CertificatePolicy");
        this.mContext.sendBroadcastAsUser(intent, new UserHandle(userId), "com.samsung.android.knox.permission.KNOX_CERTIFICATE");
        SecContentProviderUtil.notifyPolicyChangesAsUser(this.mContext, "CertificatePolicy/certificateRemoved", i);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {
    }

    public final void notifyUserKeystoreUnlocked(int i) {
        if (((HashSet) this.mRollbackRefresh.mPendingKeystoreAction).contains(Integer.valueOf(i))) {
            executeRollbackRefresh(4, i);
            executeRollbackRefresh(2, i);
        }
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {
        sendCertificatePolicyCacheUpdateCommand(this.mContext, null);
        this.mUtils.sendIntentToSettings(UserHandle.getUserId(i), this.mBootCompleted);
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        Log.d("CertificatePolicy", "onPreAdminRemoval...");
        clearCertificates(i, 2);
        clearCertificates(i, 3);
    }

    public final void putGenericListAsUser(int i, String str, Collection collection) {
        EdmStorageProvider edmStorageProvider = this.mEdmStorageProvider;
        char[] cArr = Utils.HEX_DIGITS;
        edmStorageProvider.putGenericValueAsUser(i, str, (collection == null || collection.size() <= 0) ? "" : TextUtils.join(",", collection));
    }

    public final boolean removeAliases(TrustListOperation trustListOperation, int i, List list, List list2) {
        if (list.size() == 0) {
            return false;
        }
        int userId = UserHandle.getUserId(i);
        CertificateCache certificateCache = trustListOperation.mCache;
        auditLog(i, trustListOperation.mAction, list2);
        synchronized (certificateCache) {
            Iterator it = ((ArrayList) certificateCache.getUserIdList(userId, i)).iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                num.getClass();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    String str = (String) it2.next();
                    if (((HashMap) certificateCache.mCache).containsKey(num)) {
                        Map map = (Map) ((HashMap) certificateCache.mCache).get(num);
                        if (map.containsKey(str)) {
                            ((List) map.get(str)).remove(Integer.valueOf(i));
                            if (((List) map.get(str)).size() == 0) {
                                map.remove(str);
                            }
                        }
                        if (((Map) ((HashMap) certificateCache.mCache).get(num)).keySet().size() == 0) {
                            ((HashMap) certificateCache.mCache).remove(num);
                        }
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator it3 = list.iterator();
        while (it3.hasNext()) {
            String str2 = (String) it3.next();
            synchronized (certificateCache) {
                if (((HashMap) certificateCache.mCache).containsKey(Integer.valueOf(userId))) {
                    Map map2 = (Map) ((HashMap) certificateCache.mCache).get(Integer.valueOf(userId));
                    if (map2 != null && map2.containsKey(str2)) {
                    }
                }
                arrayList.add(str2);
            }
        }
        trustListOperation.mKeyStore.removeCertificates(userId, arrayList);
        updateKeystores(trustListOperation.mAction, certificateCache.getCacheEntrySize(userId), i);
        return true;
    }

    public final boolean removeCertificateList(ContextInfo contextInfo, List list, int i) {
        int i2 = contextInfo.mCallerUid;
        if (list == null || list.size() == 0) {
            return false;
        }
        TrustListOperation trustListOperation = new TrustListOperation(this, i);
        EdmKeyStore edmKeyStore = trustListOperation.mKeyStore;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            X509Certificate x509Certificate = (X509Certificate) ((CertificateInfo) it.next()).getCertificate();
            edmKeyStore.getClass();
            arrayList.add(EdmKeyStore.generateAlias(x509Certificate.getSubjectX500Principal()));
        }
        String str = trustListOperation.mDbColumn;
        StringBuilder sb = new StringBuilder();
        ArrayList arrayList2 = new ArrayList();
        String string = this.mEdmStorageProvider.getString(i2, 0, "CERTIFICATE", str);
        if (string != null) {
            Iterator it2 = new HashSet(Utils.convertStringToList(string)).iterator();
            while (it2.hasNext()) {
                String str2 = (String) it2.next();
                if (arrayList.contains(str2)) {
                    arrayList2.add(str2);
                } else {
                    sb.append(str2 + ",");
                }
            }
            if (!this.mEdmStorageProvider.putString(i2, 0, "CERTIFICATE", str, sb.toString().length() > 0 ? sb.substring(0, sb.lastIndexOf(",")) : null)) {
                arrayList2 = new ArrayList();
            }
        }
        ArrayList arrayList3 = new ArrayList();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            if (arrayList2.contains(arrayList.get(i3))) {
                arrayList3.add((CertificateInfo) list.get(i3));
            }
        }
        ArrayList arrayList4 = new ArrayList();
        Iterator it3 = arrayList3.iterator();
        while (it3.hasNext()) {
            arrayList4.add((X509Certificate) ((CertificateInfo) it3.next()).getCertificate());
        }
        return removeAliases(trustListOperation, i2, arrayList2, arrayList4);
    }

    public final boolean removePermissionApplicationPrivateKey(ContextInfo contextInfo, PermissionApplicationPrivateKey permissionApplicationPrivateKey) {
        int i = enforceCertificatePermission$1(contextInfo).mCallerUid;
        PermissionApplicationPrivateKey validatePkey = validatePkey(permissionApplicationPrivateKey);
        if (validatePkey == null) {
            return false;
        }
        ContentValues contentValues = toContentValues(validatePkey);
        contentValues.put("adminUid", Integer.valueOf(i));
        boolean z = this.mEdmStorageProvider.delete("PermAppPrivateKey", contentValues) > 0;
        if (z) {
            return z & setPrivateKeyGrant(UserHandle.getUserId(i), validatePkey.getPackageName(), validatePkey.getAlias(), validatePkey.getStorageName(), false);
        }
        return z;
    }

    public final boolean removeTrustedCaCertificateList(ContextInfo contextInfo, List list) {
        return removeCertificateList(enforceCertificatePermission$1(contextInfo), list, 2);
    }

    public final boolean removeUntrustedCertificateList(ContextInfo contextInfo, List list) {
        return removeCertificateList(enforceCertificatePermission$1(contextInfo), list, 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0036 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List retrieveAppPermissionsFromDb(int r13) {
        /*
            r12 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r5 = "alias"
            java.lang.String r6 = "storageName"
            java.lang.String r1 = "adminUid"
            java.lang.String r2 = "pkgName"
            java.lang.String r3 = "host"
            java.lang.String r4 = "port"
            java.lang.String[] r1 = new java.lang.String[]{r1, r2, r3, r4, r5, r6}
            android.content.ContentValues r2 = new android.content.ContentValues
            r2.<init>()
            r3 = 0
            java.lang.String r13 = com.android.server.enterprise.storage.EdmStorageProviderBase.getAdminLUIDWhereIn(r3, r13)
            java.lang.String r3 = "#SelectClause#"
            r2.put(r13, r3)
            com.android.server.enterprise.storage.EdmStorageProvider r13 = r12.mEdmStorageProvider
            java.lang.String r3 = "PermAppPrivateKey"
            java.util.List r13 = r13.getValues(r3, r1, r2)
            java.util.ArrayList r13 = (java.util.ArrayList) r13
            java.util.Iterator r13 = r13.iterator()
        L36:
            boolean r1 = r13.hasNext()
            if (r1 == 0) goto Lb9
            java.lang.Object r1 = r13.next()
            android.content.ContentValues r1 = (android.content.ContentValues) r1
            r2 = 0
            if (r1 == 0) goto Lb2
            int r3 = r1.size()
            if (r3 <= 0) goto Lb2
            java.lang.String r3 = "adminUid"
            java.lang.Long r3 = r1.getAsLong(r3)     // Catch: java.lang.Exception -> L6e
            java.lang.String r4 = "pkgName"
            java.lang.String r6 = r1.getAsString(r4)     // Catch: java.lang.Exception -> L6e
            java.lang.String r4 = "host"
            java.lang.String r7 = r1.getAsString(r4)     // Catch: java.lang.Exception -> L6e
            java.lang.String r4 = "port"
            java.lang.Integer r4 = r1.getAsInteger(r4)     // Catch: java.lang.Exception -> L6e
            if (r4 != 0) goto L70
            r4 = -1
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Exception -> L6e
            goto L70
        L6e:
            r1 = move-exception
            goto L9f
        L70:
            java.lang.String r5 = "alias"
            java.lang.String r9 = r1.getAsString(r5)     // Catch: java.lang.Exception -> L6e
            java.lang.String r5 = "storageName"
            java.lang.String r10 = r1.getAsString(r5)     // Catch: java.lang.Exception -> L6e
            com.samsung.android.knox.keystore.PermissionApplicationPrivateKey r1 = new com.samsung.android.knox.keystore.PermissionApplicationPrivateKey     // Catch: java.lang.Exception -> L6e
            int r8 = r4.intValue()     // Catch: java.lang.Exception -> L6e
            r5 = r1
            r5.<init>(r6, r7, r8, r9, r10)     // Catch: java.lang.Exception -> L6e
            if (r3 == 0) goto L9d
            long r2 = r3.longValue()     // Catch: java.lang.Exception -> L98
            int r2 = (int) r2     // Catch: java.lang.Exception -> L98
            com.android.server.enterprise.storage.EdmStorageProvider r3 = r12.mEdmStorageProvider     // Catch: java.lang.Exception -> L98
            java.lang.String r2 = r3.getPackageNameForUid(r2)     // Catch: java.lang.Exception -> L98
            r1.setAdminPkgName(r2)     // Catch: java.lang.Exception -> L98
            goto L9d
        L98:
            r2 = move-exception
            r11 = r2
            r2 = r1
            r1 = r11
            goto L9f
        L9d:
            r2 = r1
            goto Lb2
        L9f:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Parsing ContentValues error"
            r3.<init>(r4)
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            java.lang.String r3 = "CertificatePolicy"
            android.util.Log.d(r3, r1)
        Lb2:
            if (r2 == 0) goto L36
            r0.add(r2)
            goto L36
        Lb9:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.retrieveAppPermissionsFromDb(int):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0060, code lost:
    
        if (r7 != null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean setPrivateKeyGrant(int r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, boolean r12) {
        /*
            r7 = this;
            java.lang.String r0 = "setPrivateKeyGrant - is KeyChainService running for user "
            long r1 = android.os.Binder.clearCallingIdentity()
            android.content.Context r3 = r7.mContext     // Catch: java.lang.Throwable -> L90
            com.android.server.enterprise.adapterlayer.PackageManagerAdapter r3 = com.android.server.enterprise.adapterlayer.PackageManagerAdapter.getInstance(r3)     // Catch: java.lang.Throwable -> L90
            r3.getClass()     // Catch: java.lang.Throwable -> L90
            r3 = 0
            android.content.pm.ApplicationInfo r4 = com.android.server.enterprise.adapterlayer.PackageManagerAdapter.getApplicationInfo(r3, r8, r9)     // Catch: java.lang.Throwable -> L90
            android.os.Binder.restoreCallingIdentity(r1)
            r1 = 1
            if (r4 != 0) goto L1f
            if (r12 == 0) goto L1e
            return r3
        L1e:
            return r1
        L1f:
            int r2 = r4.uid
            long r4 = android.os.Binder.clearCallingIdentity()
            android.content.Context r7 = r7.mContext     // Catch: java.lang.Throwable -> L6c java.lang.AssertionError -> L6e java.lang.InterruptedException -> L8c
            android.os.UserHandle r6 = new android.os.UserHandle     // Catch: java.lang.Throwable -> L6c java.lang.AssertionError -> L6e java.lang.InterruptedException -> L8c
            r6.<init>(r8)     // Catch: java.lang.Throwable -> L6c java.lang.AssertionError -> L6e java.lang.InterruptedException -> L8c
            android.security.KeyChain$KeyChainConnection r7 = android.security.KeyChain.bindAsUser(r7, r6)     // Catch: java.lang.Throwable -> L6c java.lang.AssertionError -> L6e java.lang.InterruptedException -> L8c
            android.os.Binder.restoreCallingIdentity(r4)
            if (r7 == 0) goto L60
            if (r11 == 0) goto L4f
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = new com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            r8.<init>(r11)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = r8.setResourceId(r1)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = r8.setUid(r2)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            com.samsung.android.knox.ucm.core.UniversalCredentialUtil$UcmUriBuilder r8 = r8.setAlias(r10)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            java.lang.String r10 = r8.build()     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            goto L4f
        L4d:
            r8 = move-exception
            goto L58
        L4f:
            android.security.IKeyChainService r8 = r7.getService()     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            r8.setGrant(r2, r10, r12)     // Catch: java.lang.Throwable -> L4d java.lang.Throwable -> L5c
            r3 = r1
            goto L60
        L58:
            r7.close()
            throw r8
        L5c:
            r7.close()
            goto L63
        L60:
            if (r7 == 0) goto L63
            goto L5c
        L63:
            if (r3 == 0) goto L6b
            java.lang.String r7 = "com.sec.android.app.sbrowser"
            r9.equals(r7)
        L6b:
            return r3
        L6c:
            r7 = move-exception
            goto L88
        L6e:
            java.lang.String r7 = "CertificatePolicy"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6c
            r9.<init>(r0)     // Catch: java.lang.Throwable -> L6c
            r9.append(r8)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r8 = "?"
            r9.append(r8)     // Catch: java.lang.Throwable -> L6c
            java.lang.String r8 = r9.toString()     // Catch: java.lang.Throwable -> L6c
            android.util.Log.e(r7, r8)     // Catch: java.lang.Throwable -> L6c
            android.os.Binder.restoreCallingIdentity(r4)
            return r3
        L88:
            android.os.Binder.restoreCallingIdentity(r4)
            throw r7
        L8c:
            android.os.Binder.restoreCallingIdentity(r4)
            return r3
        L90:
            r7 = move-exception
            android.os.Binder.restoreCallingIdentity(r1)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.enterprise.certificate.CertificatePolicy.setPrivateKeyGrant(int, java.lang.String, java.lang.String, java.lang.String, boolean):boolean");
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {
        int i;
        String genericValueAsUser = this.mEdmStorageProvider.getGenericValueAsUser(0, "cert_migration");
        if (genericValueAsUser == null || !"ok".equals(genericValueAsUser)) {
            this.mTrustedKeyStore.performKeystoreUpgrade();
            this.mUntrustedKeyStore.performKeystoreUpgrade();
            this.mUserKeyStore.performKeystoreUpgrade();
            this.mNativeKeyStore.performKeystoreUpgrade();
            Iterator it = ((ArrayList) this.mUtils.getAllUsersId()).iterator();
            while (it.hasNext()) {
                int intValue = ((Integer) it.next()).intValue();
                List genericListAsUser = getGenericListAsUser(intValue, "userRemovedList");
                ArrayList arrayList = new ArrayList();
                Iterator it2 = ((ArrayList) genericListAsUser).iterator();
                while (it2.hasNext()) {
                    String str = (String) it2.next();
                    int indexOf = str.indexOf("_");
                    if (indexOf != -1) {
                        try {
                            Integer.parseInt(str.substring(0, indexOf));
                            i = indexOf + 1;
                        } catch (NullPointerException | NumberFormatException unused) {
                        }
                        if (str.length() > i) {
                            arrayList.add(str.substring(i));
                        } else {
                            arrayList.add(str);
                        }
                    } else {
                        arrayList.add(str);
                    }
                }
                putGenericListAsUser(intValue, "userRemovedList", arrayList);
            }
            this.mEdmStorageProvider.putGenericValueAsUser(0, "cert_migration", "ok");
        }
    }

    public final void triggerContainerOperation(int i, int i2, int i3, int i4) {
        int i5;
        if (i == 0) {
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) this.mUtils.getAllUsersId()).iterator();
            while (it.hasNext()) {
                Integer num = (Integer) it.next();
                int intValue = num.intValue();
                ((PersonaManagerAdapter) getPersonaManagerAdapter$2()).getClass();
                if (SemPersonaManager.isKnoxId(intValue) && i2 == this.mEdmStorageProvider.getMUMContainerOwnerUid(intValue)) {
                    CertificateCache certificateCache = this.mTrustedCache;
                    synchronized (certificateCache) {
                        i5 = 0;
                        if (((HashMap) certificateCache.mCache).containsKey(num)) {
                            Iterator it2 = ((Map) ((HashMap) certificateCache.mCache).get(num)).entrySet().iterator();
                            while (it2.hasNext()) {
                                i5 += ((List) ((Map.Entry) it2.next()).getValue()).size();
                            }
                        }
                    }
                    if (i5 == i4) {
                        arrayList.add(num);
                    }
                }
            }
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                executeRollbackRefresh(i3, ((Integer) it3.next()).intValue());
            }
        }
    }

    public final void updateKeystores(int i, int i2, int i3) {
        int userId = UserHandle.getUserId(i3);
        if (i == 0) {
            if (i2 == 0) {
                executeRollbackRefresh(1, userId);
                return;
            } else {
                executeRollbackRefresh(0, userId);
                triggerContainerOperation(userId, i3, 1, 1);
                return;
            }
        }
        if (i == 1) {
            executeRollbackRefresh(1, userId);
            return;
        }
        if (i != 2) {
            if (i != 3) {
                return;
            }
            executeRollbackRefresh(0, userId);
        } else if (i2 == 0) {
            executeRollbackRefresh(0, userId);
        } else {
            executeRollbackRefresh(1, userId);
            triggerContainerOperation(userId, i3, 0, 0);
        }
    }

    public final String validateAlias(String str, String str2, String str3, int i, List list) {
        String str4;
        UniversalCredentialUtil universalCredentialUtil;
        if (TextUtils.isEmpty(str)) {
            GestureWakeup$$ExternalSyntheticOutline0.m(InitialConfiguration$$ExternalSyntheticOutline0.m(" validateAlias called. storage name : null or empty, alias : ", str2, ", packageName : ", str3, ", userId : "), i, "CertificatePolicy");
            str4 = str2;
        } else {
            StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m(" validateAlias called. storage name : ", str, ", alias : ", str2, ", packageName : ");
            m.append(str3);
            m.append(", userId : ");
            m.append(i);
            Log.d("CertificatePolicy", m.toString());
            this.mPackageManagerAdapter.getClass();
            PackageInfo packageInfo = PackageManagerAdapter.getPackageInfo(0, i, str3);
            if (packageInfo == null || packageInfo.applicationInfo == null || (universalCredentialUtil = UniversalCredentialUtil.getInstance()) == null) {
                str4 = null;
            } else {
                UniversalCredentialUtil.UcmUriBuilder uid = new UniversalCredentialUtil.UcmUriBuilder(str).setResourceId(1).setUid(packageInfo.applicationInfo.uid);
                String build = uid.build();
                str4 = uid.setAlias(str2).build();
                Bundle saw = universalCredentialUtil.saw(build, 1);
                if (saw != null) {
                    String[] stringArray = saw.getStringArray("stringarrayresponse");
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(saw.getInt("errorresponse", -1), "statusCode - ", "CertificatePolicy");
                    if (stringArray == null) {
                        return null;
                    }
                    list = Arrays.asList(stringArray);
                }
            }
        }
        if (list == null) {
            list = Collections.emptyList();
        }
        if (list.contains(str2)) {
            return str4;
        }
        return null;
    }

    public final int validateCertificateAtInstall(CertificateInfo certificateInfo) {
        return validateCertificateAtInstallAsUser(certificateInfo, Utils.getCallingOrCurrentUserId(null));
    }

    public final int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i) {
        return validateCerts(i, (X509Certificate) certificateInfo.getCertificate());
    }

    public final int validateCerts(int i, X509Certificate... x509CertificateArr) {
        List<? extends Certificate> list;
        X509Certificate x509Certificate;
        Throwable cause;
        List asList = Arrays.asList(x509CertificateArr);
        try {
            ArrayList arrayList = new ArrayList();
            if (asList.size() == 1) {
                list = getCertificateChainFromSystem((X509Certificate) asList.get(0), i);
            } else {
                arrayList.addAll(asList);
                list = arrayList;
            }
            if (list.size() == 1) {
                this.mCheckRevocation.set(Boolean.FALSE);
                x509Certificate = (X509Certificate) list.get(0);
            } else {
                this.mCheckRevocation.set(Boolean.TRUE);
                x509Certificate = (X509Certificate) list.remove(list.size() - 1);
            }
            Set singleton = Collections.singleton(new TrustAnchor(x509Certificate, null));
            CertPath generateCertPath = CertificateFactory.getInstance("X.509").generateCertPath(list);
            try {
                PKIXParameters pKIXParameters = new PKIXParameters((Set<TrustAnchor>) singleton);
                try {
                    CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
                    pKIXParameters.setRevocationEnabled(false);
                    try {
                        certPathValidator.validate(generateCertPath, pKIXParameters);
                        Log.d("CertificatePolicy", "Validation success");
                        return -1;
                    } catch (InvalidAlgorithmParameterException e) {
                        Log.e("CertificatePolicy", "Should not happen!" + e);
                        return 0;
                    } catch (CertPathValidatorException e2) {
                        String message = e2.getMessage();
                        Log.e("CertificatePolicy", "Validation failed: " + message);
                        if (message.contains("Additional certificate path checker failed.") && (cause = e2.getCause()) != null && cause.getMessage() != null) {
                            message = cause.getMessage();
                        }
                        if (message.contains("is revoked") || message.contains("Certificate revocation after")) {
                            return 2;
                        }
                        if (message.contains("OCSP check failed!") || message.contains("Certificate status could not be determined.") || message.contains("CRL distribution point extension could not be read") || message.contains("No additional CRL locations could be decoded from CRL distribution point extension.") || message.contains("Distribution points could not be read.") || message.contains("No valid CRL found.") || message.contains("Unable to get CRL for certificate")) {
                            return 13;
                        }
                        if (message.contains(", expiration time") || (e2.getCause() instanceof CertificateExpiredException)) {
                            return 4;
                        }
                        return (message.contains(", validation time") || (e2.getCause() instanceof CertificateNotYetValidException)) ? 3 : 0;
                    }
                } catch (NoSuchAlgorithmException e3) {
                    Log.d("CertificatePolicy", "Should not happen!" + e3);
                    return 0;
                }
            } catch (InvalidAlgorithmParameterException e4) {
                Log.e("CertificatePolicy", "Should not happen!" + e4);
                return 0;
            }
        } catch (AssertionError e5) {
            Log.e("CertificatePolicy", "If FIPS mode is turned on, cannot use MD5 algorithm : " + e5);
            return 0;
        } catch (CertificateException e6) {
            Log.e("CertificatePolicy", "Failure generating cert path: " + e6);
            return 8;
        }
    }

    public final int validateChainAtInstall(List list) {
        return validateChainAtInstallAsUser(list, Utils.getCallingOrCurrentUserId(null));
    }

    public final int validateChainAtInstallAsUser(List list, int i) {
        int validateCerts = validateCerts(i, (X509Certificate) ((CertificateInfo) list.get(list.size() - 1)).getCertificate());
        if (validateCerts != -1) {
            return validateCerts;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((X509Certificate) ((CertificateInfo) it.next()).getCertificate());
        }
        return validateCerts(i, (X509Certificate[]) arrayList.toArray(new X509Certificate[arrayList.size()]));
    }

    public final boolean verifyCertificateTrustful(X509Certificate x509Certificate, int i, int i2) {
        ((PersonaManagerAdapter) getPersonaManagerAdapter$2()).getClass();
        boolean containsCertificateOrChain = ((i & 1) != 1 || this.mTrustedCache.getCacheEntrySize(i2) == 0) ? true : this.mTrustedKeyStore.containsCertificateOrChain(this.mContext, this.mTrustedCache, x509Certificate, i2, SemPersonaManager.isKnoxId(i2) ? this.mEdmStorageProvider.getMUMContainerOwnerUid(i2) : -1);
        return (!containsCertificateOrChain || this.mUntrustedCache.getCacheEntrySize(i2) == 0) ? containsCertificateOrChain : containsCertificateOrChain & (!this.mUntrustedKeyStore.containsCertificateOrChain(this.mContext, this.mUntrustedCache, x509Certificate, i2, r0));
    }
}
