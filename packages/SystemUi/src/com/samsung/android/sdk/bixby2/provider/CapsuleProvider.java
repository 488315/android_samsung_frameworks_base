package com.samsung.android.sdk.bixby2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.AbstractC0790xf8f53ce8;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.sdk.bixby2.AppMetaInfo;
import com.samsung.android.sdk.bixby2.Sbixby;
import com.samsung.android.sdk.bixby2.action.ActionHandler;
import com.samsung.android.sdk.bixby2.receiver.ApplicationTriggerReceiver;
import com.samsung.android.sdk.bixby2.state.StateHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class CapsuleProvider extends ContentProvider {
    public final Object sActionExecutionLock = new Object();
    public static final boolean mIsUserBuild = "user".equals(Build.TYPE);
    public static final Signature mBixbyAgentSignature = new Signature(Base64.decode("MIIE1DCCA7ygAwIBAgIJANIJlaecDarWMA0GCSqGSIb3DQEBBQUAMIGiMQswCQYDVQQGEwJLUjEUMBIGA1UECBMLU291dGggS29yZWExEzARBgNVBAcTClN1d29uIENpdHkxHDAaBgNVBAoTE1NhbXN1bmcgQ29ycG9yYXRpb24xDDAKBgNVBAsTA0RNQzEVMBMGA1UEAxMMU2Ftc3VuZyBDZXJ0MSUwIwYJKoZIhvcNAQkBFhZhbmRyb2lkLm9zQHNhbXN1bmcuY29tMB4XDTExMDYyMjEyMjUxMloXDTM4MTEwNzEyMjUxMlowgaIxCzAJBgNVBAYTAktSMRQwEgYDVQQIEwtTb3V0aCBLb3JlYTETMBEGA1UEBxMKU3V3b24gQ2l0eTEcMBoGA1UEChMTU2Ftc3VuZyBDb3Jwb3JhdGlvbjEMMAoGA1UECxMDRE1DMRUwEwYDVQQDEwxTYW1zdW5nIENlcnQxJTAjBgkqhkiG9w0BCQEWFmFuZHJvaWQub3NAc2Ftc3VuZy5jb20wggEgMA0GCSqGSIb3DQEBAQUAA4IBDQAwggEIAoIBAQDJhjhKPh8vsgZnDnjvIyIVwNJvRaInKNuZpE2hHDWsM6cf4HHEotaCWptMiLMz7ZbzxebGZtYPPulMSQiFq8+NxmD3B6q8d+rT4tDYrugQjBXNJg8uhQQsKNLyktqjxtoMe/I5HbeEGq3o/fDJ0N7893Ek5tLeCp4NLadGw2cOT/zchbcBu0dEhhuW/3MR2jYDxaEDNuVf+jS0NT7tyF9RAV4VGMZ+MJ45+HY5/xeBB/EJzRhBGmB38mlktuY/inC5YZ2wQwajI8Gh0jr4Z+GfFPVw/+Vz0OOgwrMGMqrsMXM4CZS+HjQeOpC9LkthVIH0bbOeqDgWRI7DX+sXNcHzAgEDo4IBCzCCAQcwHQYDVR0OBBYEFJMsOvcLYnoMdhC1oOdCfWz66j8eMIHXBgNVHSMEgc8wgcyAFJMsOvcLYnoMdhC1oOdCfWz66j8eoYGopIGlMIGiMQswCQYDVQQGEwJLUjEUMBIGA1UECBMLU291dGggS29yZWExEzARBgNVBAcTClN1d29uIENpdHkxHDAaBgNVBAoTE1NhbXN1bmcgQ29ycG9yYXRpb24xDDAKBgNVBAsTA0RNQzEVMBMGA1UEAxMMU2Ftc3VuZyBDZXJ0MSUwIwYJKoZIhvcNAQkBFhZhbmRyb2lkLm9zQHNhbXN1bmcuY29tggkA0gmVp5wNqtYwDAYDVR0TBAUwAwEB/zANBgkqhkiG9w0BAQUFAAOCAQEAMpYB/kDgNqSobMXUndjBtUFZmOcmN1OLDUMDaaxRUw9jqs6MAZoaZmFqLxuyxfq9bzEyYfOA40cWI/BT2ePFP1/W0ZZdewAOTcJEwbJ+L+mjI/8Hf1LEZ16GJHqoARhxN+MMm78BxWekKZ20vwslt9cQenuB7hAvcv9HlQFk4mdS4RTEL4udKkLnMIiX7GQOoZJO0Tq76dEgkSti9JJkk6htuUwLRvRMYWHVjC9kgWSJDFEt+yjULIVb9HDb7i2raWDK0E6B9xUl3tRs3Q81n5nEYNufAH2WzoO0shisLYLEjxJgjUaXM/BaM3VZRmnMv4pJVUTWxXAek2nAjIEBWA==", 0));
    public static final Signature mBixbyAgentSignatureForIOT = new Signature("3082040e308202f6a0030201020206017ce4114f86300d06092a864886f70d01010505003081bd310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373312d302b0603550403132453616d73756e6720506c6174666f726d204b657920666f7220494f542064657669636573301e170d3231313130333034333135325a170d3431313130323135303030305a3081bd310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373312d302b0603550403132453616d73756e6720506c6174666f726d204b657920666f7220494f54206465766963657330820122300d06092a864886f70d01010105000382010f003082010a0282010100b59bf685e92c3075041ec8952a511d23ea013c70af115d4aae07307cf86e7a7d9fd253cb405a394ce7767859f576ab7d5202210223e6f2ae2d52a9347933fadc9ff3396ffc784b68f2c106f6038a49b779f6221a1e5c1f636e96aacefec6ca460fe54b93d41ca6b16583411b6bf8eeae8014f8c46a1101445b9c42c3e2eee893d8c300e3ebab428e77a0d9f7acf7992c46649e6b8a206d249fda97978572428b38dbb7d71d715005080b7d3bcbc7280d321d299e09c8981eec7699a533049fff0c1c48f152fa16db0d1776dfe31db7735bb53633b6dc8f4b0c6effea9152d431ec48dbea09523fe1b0d3e1cb54b9e5e4b107ebae7b9d3b33dd1b77baedd9b0590203010001a3123010300e0603551d0f0101ff0404030205a0300d06092a864886f70d010105050003820101006fd7b92f463a1d2f5fc08dd3b06880a12afe76c80dc47f0113f06016de59a77859be19515c5b93285acb25e2701d832bef6fc4c489e081cffcd1fb3930420cf757eceb9b1f64cfe0ca705b00f73b7d0431aeb616085d3d1b392a41802e144acbf49d841ac9787988915446283d61cb4cc25272460a9d0717539f64feacb19042d027b3242f4332ee869f0d8254d514d9824f01b806470a637a124beb66ecf1ade20cbcac371c44b4595e9528c6b43dd3ae967c2d71134425d1684949b13f312b9c48f156c18431fb3d82b6f67bc04be8d1b4cf17042d91a0159471f90672e2f29e181ec106696de357b0c6398031c2806b5e8b4db606ee0d2e718a5a92218281");
    public static final Map actionMap = new HashMap();
    public static boolean mIsAppInitialized = false;
    public static boolean mWaitForHandler = false;
    public static final Object sWaitLock = new Object();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CapsuleResponseCallback {
        public CapsuleResponseCallback(CapsuleProvider capsuleProvider) {
            new Bundle();
        }
    }

    public static ActionHandler getActionHandler(String str) {
        Map map = actionMap;
        ActionHandler actionHandler = (ActionHandler) ((HashMap) map).get(str);
        Object obj = sWaitLock;
        synchronized (obj) {
            if (actionHandler == null) {
                if (mWaitForHandler) {
                    obj.wait(3000L);
                    actionHandler = (ActionHandler) ((HashMap) map).get(str);
                }
            }
        }
        return actionHandler;
    }

    public static Bundle updateStatus(int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("status_code", i);
        if (TextUtils.isEmpty(str) && i == -1) {
            str = "Failed to execute action.";
            Log.e("CapsuleProvider_1.0.24", "Failed to execute action.");
        }
        bundle.putString("status_message", str);
        return bundle;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02d5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:47:? A[RETURN, SYNTHETIC] */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bundle call(final String str, String str2, final Bundle bundle) {
        boolean z;
        AppMetaInfo defaultAppMetaInfo;
        String jSONObject;
        Log.i("CapsuleProvider_1.0.24", "call()");
        if (bundle != null) {
            bundle.toString();
        }
        if (mIsUserBuild) {
            int callingUid = Binder.getCallingUid();
            PackageManager packageManager = getContext().getPackageManager();
            String[] packagesForUid = packageManager.getPackagesForUid(callingUid);
            z = false;
            if (packagesForUid == null) {
                Log.e("CapsuleProvider_1.0.24", "packages is null");
            } else {
                for (String str3 : packagesForUid) {
                    if ("com.samsung.android.bixby.agent".equals(str3) || "com.samsung.android.app.routines".equals(str3)) {
                        try {
                            Signature[] signatureArr = packageManager.getPackageInfo(str3, 64).signatures;
                            if (signatureArr != null && signatureArr.length > 0 && (mBixbyAgentSignature.equals(signatureArr[0]) || mBixbyAgentSignatureForIOT.equals(signatureArr[0]))) {
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.e("CapsuleProvider_1.0.24", "Not allowed to access capsule provider. package (s): " + Arrays.toString(packagesForUid));
            }
            if (z) {
                throw new SecurityException("not allowed to access capsule provider.");
            }
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("method is null or empty. pass valid action name.");
            }
            if (!mIsAppInitialized && getContext() != null) {
                ApplicationTriggerReceiver applicationTriggerReceiver = new ApplicationTriggerReceiver();
                IntentFilter m5m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("com.samsung.android.sdk.bixby2.ACTION_APPLICATION_TRIGGER");
                if (getContext().getApplicationInfo().targetSdkVersion >= 34) {
                    getContext().registerReceiver(applicationTriggerReceiver, m5m, 4);
                } else {
                    getContext().registerReceiver(applicationTriggerReceiver, m5m);
                }
                Log.i("CapsuleProvider_1.0.24", "ApplicationTriggerReceiver registered");
                Intent intent = new Intent();
                intent.setAction("com.samsung.android.sdk.bixby2.ACTION_APPLICATION_TRIGGER");
                intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
                getContext().sendBroadcast(intent);
            }
            Object obj = sWaitLock;
            synchronized (obj) {
                if (!mIsAppInitialized) {
                    try {
                        obj.wait(5000L);
                    } catch (InterruptedException e2) {
                        Log.e("CapsuleProvider_1.0.24", "interrupted exception");
                        e2.printStackTrace();
                    }
                }
            }
            if (!mIsAppInitialized) {
                Log.e("CapsuleProvider_1.0.24", "App initialization error.");
                return updateStatus(-1, "Initialization Failure..");
            }
            if (!str.equals("getAppContext")) {
                if (bundle == null) {
                    throw new IllegalArgumentException("action params are EMPTY.");
                }
                synchronized (this) {
                    try {
                        Log.i("CapsuleProvider_1.0.24", "executeAction()");
                        final ActionHandler actionHandler = getActionHandler(str);
                        if (actionHandler == null) {
                            Log.e("CapsuleProvider_1.0.24", "Handler not found!!..");
                            return updateStatus(-2, "Action handler not found");
                        }
                        if (!bundle.containsKey("actionType")) {
                            Log.e("CapsuleProvider_1.0.24", "params missing");
                            return updateStatus(-1, "params missing..");
                        }
                        final CapsuleResponseCallback capsuleResponseCallback = new CapsuleResponseCallback(this);
                        Thread thread = new Thread(new Runnable() { // from class: com.samsung.android.sdk.bixby2.provider.CapsuleProvider.2
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActionHandler actionHandler2 = actionHandler;
                                CapsuleProvider.this.getContext();
                                actionHandler2.executeAction();
                            }
                        });
                        thread.start();
                        synchronized (this.sActionExecutionLock) {
                            this.sActionExecutionLock.wait(30000L);
                            Log.e("CapsuleProvider_1.0.24", "timeout occurred..");
                            thread.interrupt();
                        }
                        return updateStatus(-1, "action execution timed out");
                    } catch (Exception e3) {
                        Log.e("CapsuleProvider_1.0.24", "Unable to execute action." + e3.toString());
                        e3.printStackTrace();
                        return updateStatus(-1, e3.toString());
                    }
                }
            }
            Sbixby.getInstance();
            StateHandler stateHandler = StateHandler.getInstance();
            Context context = getContext();
            StateHandler.Callback callback = stateHandler.mCallback;
            if (callback == null) {
                Log.e("StateHandler", "StateHandler.Callback instance is null");
            } else {
                String onAppStateRequested = callback.onAppStateRequested();
                if (TextUtils.isEmpty(onAppStateRequested)) {
                    Log.e("StateHandler", "state info is empty.");
                } else {
                    String onCapsuleIdRequested = stateHandler.mCallback.onCapsuleIdRequested();
                    Sbixby.getInstance().getClass();
                    Map map = Sbixby.appMetaInfoMap;
                    if (TextUtils.isEmpty(onCapsuleIdRequested)) {
                        Log.e("StateHandler", "capsuleId is empty");
                        if (map != null) {
                            HashMap hashMap = (HashMap) map;
                            if (hashMap.size() != 0) {
                                if (hashMap.size() == 1) {
                                    Log.i("StateHandler", "Map for App Meta Info. has only one");
                                    defaultAppMetaInfo = (AppMetaInfo) ((Map.Entry) hashMap.entrySet().iterator().next()).getValue();
                                } else {
                                    Log.e("StateHandler", "No Capsule Id and multiple App Meta Info. Can't pick one");
                                }
                            }
                        }
                        defaultAppMetaInfo = StateHandler.getDefaultAppMetaInfo(context);
                    } else {
                        if (map != null) {
                            HashMap hashMap2 = (HashMap) map;
                            if (hashMap2.containsKey(onCapsuleIdRequested)) {
                                defaultAppMetaInfo = (AppMetaInfo) hashMap2.get(onCapsuleIdRequested);
                            }
                        }
                        Log.e("StateHandler", "Map for App Meta Info. is empty");
                        defaultAppMetaInfo = StateHandler.getDefaultAppMetaInfo(context);
                        if (defaultAppMetaInfo != null) {
                            defaultAppMetaInfo.capsuleId = onCapsuleIdRequested;
                        }
                    }
                    if (defaultAppMetaInfo != null) {
                        try {
                            JSONObject jSONObject2 = new JSONObject(onAppStateRequested);
                            jSONObject2.put("capsuleId", defaultAppMetaInfo.capsuleId);
                            jSONObject2.put("appId", context.getPackageName());
                            jSONObject2.put("appVersionCode", defaultAppMetaInfo.appVersionCode);
                            List<String> usedPermissionsWhenAppStateRequested = stateHandler.mCallback.getUsedPermissionsWhenAppStateRequested();
                            Log.i("StateHandler", "getUsedPermissionsWhenAppStateRequested() = " + usedPermissionsWhenAppStateRequested);
                            if (usedPermissionsWhenAppStateRequested != null && !usedPermissionsWhenAppStateRequested.isEmpty()) {
                                List clientDeniedPermissions = StateHandler.getClientDeniedPermissions(usedPermissionsWhenAppStateRequested, context, bundle);
                                Log.i("StateHandler", "deniedPermissionsInClient = " + clientDeniedPermissions);
                                if (clientDeniedPermissions != null) {
                                    StateHandler.adjustConceptsDueToPermissions(clientDeniedPermissions, jSONObject2);
                                }
                            }
                            jSONObject2.toString();
                            jSONObject = jSONObject2.toString();
                        } catch (Exception e4) {
                            AbstractC0790xf8f53ce8.m80m(e4, new StringBuilder("getAppState exception "), "StateHandler");
                        }
                        if (jSONObject != null) {
                            return null;
                        }
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("appContext", jSONObject);
                        return bundle2;
                    }
                    Log.e("StateHandler", "App Meta Info. is null");
                }
            }
            jSONObject = null;
            if (jSONObject != null) {
            }
        }
        z = true;
        if (z) {
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        return "actionUri";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        Log.i("CapsuleProvider_1.0.24", "onCreate");
        mWaitForHandler = true;
        return true;
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
