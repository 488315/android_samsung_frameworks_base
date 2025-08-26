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
        int iCheck = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iCheck = service.check(new IResultListener.Stub() { // from class: com.samsung.android.knox.zt.config.ConfigurationManager.1
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iCheck, "Leave check() with ", TAG);
        return iCheck;
    }

    public String configFeature(@KnoxZtFeature String str, String str2) {
        String str3 = TAG;
        Log.i(str3, "Enter configFeature()");
        String strConfigFeature = "{\"resultCode\":3}";
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                strConfigFeature = service.configFeature(str, str2);
            } else {
                Log.e(str3, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Leave configFeature() with ", strConfigFeature, TAG);
        return strConfigFeature;
    }

    public int disable() {
        String str = TAG;
        Log.i(str, "Enter disable()");
        int iDisable = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iDisable = service.disable();
            } else {
                Log.e(str, "disable getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iDisable, "Leave disable() with ", TAG);
        return iDisable;
    }

    public int disableFeature(@KnoxZtFeature String str) {
        String str2 = TAG;
        Log.i(str2, "Enter disableFeature()");
        int iDisableFeature = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iDisableFeature = service.disableFeature(str);
            } else {
                Log.e(str2, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iDisableFeature, "Leave disableFeature() with ", TAG);
        return iDisableFeature;
    }

    public int enable(String str) {
        return enable(str, true);
    }

    public int enableFeature(@KnoxZtFeature String str) {
        String str2 = TAG;
        Log.i(str2, "Enter enableFeature()");
        int iEnableFeature = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iEnableFeature = service.enableFeature(str);
            } else {
                Log.e(str2, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iEnableFeature, "Leave enableFeature() with ", TAG);
        return iEnableFeature;
    }

    public String getConfiguration(@KnoxZtFeature String str) {
        String str2 = TAG;
        Log.i(str2, "Enter getConfiguration()");
        String configuration = "{\"resultCode\":1}";
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                configuration = service.getConfiguration(str);
            } else {
                Log.e(str2, "check getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("Leave getConfiguration() with ", configuration, TAG);
        return configuration;
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
            Object objInvoke = cls.getMethod("getService", String.class).invoke(cls, SERVICE_NAME_KNOXZT_CORE);
            if (objInvoke != null) {
                return IKnoxZtCoreService.Stub.asInterface((IBinder) objInvoke);
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
        boolean zIsEnabled = false;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                zIsEnabled = service.isEnabled();
            } else {
                Log.e(str, "isEnabled getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isEnabled() with ", TAG, zIsEnabled);
        return zIsEnabled;
    }

    public boolean isStarted() {
        String str = TAG;
        Log.i(str, "Enter isStarted()");
        boolean zIsStarted = false;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                zIsStarted = service.isStarted();
            } else {
                Log.e(str, "isStarted getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("Leave isStarted() with ", TAG, zIsStarted);
        return zIsStarted;
    }

    public int notifyTestFactorScoreChange(String str, long j, boolean z) {
        String str2 = TAG;
        Log.i(str2, "Enter notifyTestFactorScoreChange()");
        int iNotifyTestFactorScoreChange = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iNotifyTestFactorScoreChange = service.notifyTestFactorScoreChange(str, j, z);
            } else {
                Log.e(str2, "notifyTestFactorScoreChange getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iNotifyTestFactorScoreChange, "Leave notifyTestFactorScoreChange() with ", TAG);
        return iNotifyTestFactorScoreChange;
    }

    public int registerListener(final ITrustEventListener iTrustEventListener) {
        String str = TAG;
        Log.i(str, "Enter registerListener()");
        int iRegisterListener = 1;
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
                iRegisterListener = service.registerListener(this.mListeners.get(iTrustEventListener));
            } else {
                Log.e(str, "registerListener getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iRegisterListener, "Leave registerListener() with ", TAG);
        return iRegisterListener;
    }

    public int start(final ITrustResultListener iTrustResultListener) {
        String str = TAG;
        Log.i(str, "Enter start()");
        int iStart = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iStart = service.start(new IResultListener.Stub() { // from class: com.samsung.android.knox.zt.config.ConfigurationManager.2
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStart, "Leave start() with ", TAG);
        return iStart;
    }

    public int stop(final ITrustResultListener iTrustResultListener) {
        String str = TAG;
        Log.i(str, "Enter stop()");
        int iStop = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iStop = service.stop(new IResultListener.Stub() { // from class: com.samsung.android.knox.zt.config.ConfigurationManager.3
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
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iStop, "Leave stop() with ", TAG);
        return iStop;
    }

    public int unregisterListener(ITrustEventListener iTrustEventListener) {
        String str = TAG;
        Log.i(str, "Enter unregisterListener()");
        int iUnregisterListener = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iUnregisterListener = service.unregisterListener(this.mListeners.get(iTrustEventListener));
                this.mListeners.remove(iTrustEventListener);
            } else {
                Log.e(str, "unregisterListener getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iUnregisterListener, "Leave unregisterListener() with ", TAG);
        return iUnregisterListener;
    }

    public int enable(String str, boolean z) {
        String str2 = TAG;
        Log.i(str2, "Enter enable()");
        int iEnable = 1;
        try {
            IKnoxZtCoreService service = getService();
            if (service != null) {
                iEnable = service.enable(str, z);
            } else {
                Log.e(str2, "enable getService failed!");
            }
        } catch (Exception e) {
            KeyguardUCMViewController$StateMachine$$ExternalSyntheticOutline0.m(e, new StringBuilder("Exception: "), TAG);
        }
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iEnable, "Leave enable() with ", TAG);
        return iEnable;
    }
}
