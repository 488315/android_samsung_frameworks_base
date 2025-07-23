package com.samsung.android.knox.zt.config;

import android.content.Context;
import android.os.IBinder;
import android.util.Log;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.zt.config.IEventListener;
import com.samsung.android.knox.zt.config.IKnoxZtCoreService;
import com.samsung.android.knox.zt.config.IResultListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ConfigurationManager {
    public static final String FEATURE_SECURE_LOG = "secureLog";
    public static ArrayList<String> KNOXZT_PERM = new ArrayList<>(Collections.singletonList(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_SECURITY_TAG));
    public static final int RESULT_CODE_FAIL_FEATURE_UNAVAILABLE = 4;
    public static final int RESULT_CODE_FAIL_PERMISSION_ERROR = 3;
    public static final int RESULT_CODE_FAIL_SERVICE_UNAVAILABLE = 1;
    public static final int RESULT_CODE_FAIL_WRONG_ARGUMENT = 2;
    public static final int RESULT_CODE_SUCCESS = 0;
    public static final String SERVICE_NAME_KNOXZT_CORE = "knoxztcore";
    public static final String TAG = "ConfigurationManager";
    public static volatile ConfigurationManager sInstance;
    public final Context mContext;
    public final HashMap<ITrustEventListener, IEventListener> mListeners = new HashMap<>();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface KnoxZtFeature {
    }

    private ConfigurationManager(Context context) {
        this.mContext = context;
    }

    public static ConfigurationManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ConfigurationManager.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ConfigurationManager(context);
                    }
                } finally {
                }
            }
        }
        return sInstance;
    }

    public int check(final ITrustResultListener iTrustResultListener) {
        String str = TAG;
        Log.i(str, "Enter check()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.check(new IResultListener.Stub() { // from class: com.samsung.android.knox.zt.config.ConfigurationManager.1
                    @Override // com.samsung.android.knox.zt.config.IResultListener
                    public void onFail(String str2) {
                        iTrustResultListener.onFail(str2);
                    }

                    @Override // com.samsung.android.knox.zt.config.IResultListener
                    public void onSuccess() {
                        iTrustResultListener.onSuccess();
                    }
                });
            } else {
                Log.e(str, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave check() with ", TAG);
        return i;
    }

    public String configFeature(@KnoxZtFeature String str, String str2) {
        String str3 = TAG;
        Log.i(str3, "Enter configFeature()");
        String str4 = "{\"resultCode\":3}";
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                str4 = service.configFeature(str, str2);
            } else {
                Log.e(str3, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Leave configFeature() with ", str4, TAG);
        return str4;
    }

    public int disable() {
        String str = TAG;
        Log.i(str, "Enter disable()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.disable();
            } else {
                Log.e(str, "disable getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave disable() with ", TAG);
        return i;
    }

    public int disableFeature(@KnoxZtFeature String str) {
        String str2 = TAG;
        Log.i(str2, "Enter disableFeature()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.disableFeature(str);
            } else {
                Log.e(str2, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave disableFeature() with ", TAG);
        return i;
    }

    public int enable(String str) {
        return enable(str, true);
    }

    public int enableFeature(@KnoxZtFeature String str) {
        String str2 = TAG;
        Log.i(str2, "Enter enableFeature()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.enableFeature(str);
            } else {
                Log.e(str2, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave enableFeature() with ", TAG);
        return i;
    }

    public String getConfiguration(@KnoxZtFeature String str) {
        String str2 = TAG;
        Log.i(str2, "Enter getConfiguration()");
        String str3 = "{\"resultCode\":1}";
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                str3 = service.getConfiguration(str);
            } else {
                Log.e(str2, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Leave getConfiguration() with ", str3, TAG);
        return str3;
    }

    public List<TrustFactorType> getFactorsToSetup() {
        String str = TAG;
        Log.i(str, "Enter getFactorsToSetup()");
        List<TrustFactorType> arrayList = new ArrayList<>();
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                arrayList = service.getFactorsToSetup();
            } else {
                Log.e(str, "getFactorsToSetup getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        Log.i(TAG, "Leave getFactorsToSetup() with " + arrayList);
        return arrayList;
    }

    public final IKnoxZtCoreService getService() {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Object invoke = cls.getMethod("getService", String.class).invoke(cls, SERVICE_NAME_KNOXZT_CORE);
            if (invoke != null) {
                return IKnoxZtCoreService.Stub.asInterface((IBinder) invoke);
            }
            throw new RuntimeException("failed to find knoxztcore service");
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException(th.toString());
        }
    }

    public List<TrustActionType> getValidActions() {
        String str = TAG;
        Log.i(str, "Enter getValidActions()");
        List<TrustActionType> arrayList = new ArrayList<>();
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                arrayList = service.getValidActions();
            } else {
                Log.e(str, "getValidActions getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        Log.i(TAG, "Leave getValidActions() with " + arrayList);
        return arrayList;
    }

    public boolean isEnabled() {
        String str = TAG;
        Log.i(str, "Enter isEnabled()");
        boolean z = false;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                z = service.isEnabled();
            } else {
                Log.e(str, "isEnabled getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isEnabled() with ", TAG, z);
        return z;
    }

    public boolean isStarted() {
        String str = TAG;
        Log.i(str, "Enter isStarted()");
        boolean z = false;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                z = service.isStarted();
            } else {
                Log.e(str, "isStarted getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isStarted() with ", TAG, z);
        return z;
    }

    public int notifyTestFactorScoreChange(String str, long j, boolean z) {
        String str2 = TAG;
        Log.i(str2, "Enter notifyTestFactorScoreChange()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.notifyTestFactorScoreChange(str, j, z);
            } else {
                Log.e(str2, "notifyTestFactorScoreChange getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave notifyTestFactorScoreChange() with ", TAG);
        return i;
    }

    public int registerListener(final ITrustEventListener iTrustEventListener) {
        String str = TAG;
        Log.i(str, "Enter registerListener()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                this.mListeners.put(iTrustEventListener, new IEventListener.Stub() { // from class: com.samsung.android.knox.zt.config.ConfigurationManager.4
                    @Override // com.samsung.android.knox.zt.config.IEventListener
                    public void onFail(String str2) {
                        iTrustEventListener.onFail(str2);
                    }

                    @Override // com.samsung.android.knox.zt.config.IEventListener
                    public void onStateUpdate(boolean z, String str2) {
                        iTrustEventListener.onStateUpdate(z, str2);
                    }

                    @Override // com.samsung.android.knox.zt.config.IEventListener
                    public void onSuccess() {
                        iTrustEventListener.onSuccess();
                    }
                });
                i = service.registerListener(this.mListeners.get(iTrustEventListener));
            } else {
                Log.e(str, "registerListener getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave registerListener() with ", TAG);
        return i;
    }

    public int start(final ITrustResultListener iTrustResultListener) {
        String str = TAG;
        Log.i(str, "Enter start()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.start(new IResultListener.Stub() { // from class: com.samsung.android.knox.zt.config.ConfigurationManager.2
                    @Override // com.samsung.android.knox.zt.config.IResultListener
                    public void onFail(String str2) {
                        iTrustResultListener.onFail(str2);
                    }

                    @Override // com.samsung.android.knox.zt.config.IResultListener
                    public void onSuccess() {
                        iTrustResultListener.onSuccess();
                    }
                });
            } else {
                Log.e(str, "start getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave start() with ", TAG);
        return i;
    }

    public int stop(final ITrustResultListener iTrustResultListener) {
        String str = TAG;
        Log.i(str, "Enter stop()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.stop(new IResultListener.Stub() { // from class: com.samsung.android.knox.zt.config.ConfigurationManager.3
                    @Override // com.samsung.android.knox.zt.config.IResultListener
                    public void onFail(String str2) {
                        iTrustResultListener.onFail(str2);
                    }

                    @Override // com.samsung.android.knox.zt.config.IResultListener
                    public void onSuccess() {
                        iTrustResultListener.onSuccess();
                    }
                });
            } else {
                Log.e(str, "stop getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave stop() with ", TAG);
        return i;
    }

    public int unregisterListener(ITrustEventListener iTrustEventListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterListener()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.unregisterListener(this.mListeners.get(iTrustEventListener));
                this.mListeners.remove(iTrustEventListener);
            } else {
                Log.e(str, "unregisterListener getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave unregisterListener() with ", TAG);
        return i;
    }

    public int enable(String str, boolean z) {
        String str2 = TAG;
        Log.i(str2, "Enter enable()");
        int i = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                i = service.enable(str, z);
            } else {
                Log.e(str2, "enable getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "Leave enable() with ", TAG);
        return i;
    }
}
