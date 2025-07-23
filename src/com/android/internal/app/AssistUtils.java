package com.android.internal.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.app.IVoiceInteractionManagerService;
import java.util.ArrayList;
import java.util.Set;

/* loaded from: classes5.dex */
public class AssistUtils {
    public static final int INVOCATION_TYPE_ASSIST_BUTTON = 7;
    public static final int INVOCATION_TYPE_GESTURE = 1;
    public static final int INVOCATION_TYPE_HOME_BUTTON_LONG_PRESS = 5;
    public static final String INVOCATION_TYPE_KEY = "invocation_type";
    public static final int INVOCATION_TYPE_LAUNCHER_SYSTEM_SHORTCUT = 9;
    public static final int INVOCATION_TYPE_NAV_HANDLE_LONG_PRESS = 8;
    public static final int INVOCATION_TYPE_PHYSICAL_GESTURE = 2;
    public static final int INVOCATION_TYPE_POWER_BUTTON_LONG_PRESS = 6;
    public static final int INVOCATION_TYPE_QUICK_SEARCH_BAR = 4;
    public static final int INVOCATION_TYPE_UNKNOWN = 0;
    public static final int INVOCATION_TYPE_VOICE = 3;
    private static final String TAG = "AssistUtils";
    private final Context mContext;
    private final IVoiceInteractionManagerService mVoiceInteractionManagerService = IVoiceInteractionManagerService.Stub.asInterface(ServiceManager.getService(Context.VOICE_INTERACTION_MANAGER_SERVICE));

    public AssistUtils(Context context) {
        this.mContext = context;
    }

    @Deprecated
    public boolean showSessionForActiveService(Bundle bundle, int i, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) {
        return showSessionForActiveServiceInternal(bundle, i, null, iVoiceInteractionSessionShowCallback, iBinder);
    }

    public boolean showSessionForActiveService(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) {
        return showSessionForActiveServiceInternal(bundle, i, str, iVoiceInteractionSessionShowCallback, iBinder);
    }

    private boolean showSessionForActiveServiceInternal(Bundle bundle, int i, String str, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, IBinder iBinder) {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                return iVoiceInteractionManagerService.showSessionForActiveService(bundle, i, str, iVoiceInteractionSessionShowCallback, iBinder);
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call showSessionForActiveService", e);
            return false;
        }
    }

    public void getActiveServiceSupportedActions(Set<String> set, IVoiceActionCheckCallback iVoiceActionCheckCallback) {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.getActiveServiceSupportedActions(new ArrayList(set), iVoiceActionCheckCallback);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call activeServiceSupportedActions", e);
            try {
                iVoiceActionCheckCallback.onComplete(null);
            } catch (RemoteException unused) {
            }
        }
    }

    public void launchVoiceAssistFromKeyguard() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.launchVoiceAssistFromKeyguard();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call launchVoiceAssistFromKeyguard", e);
        }
    }

    public boolean activeServiceSupportsAssistGesture() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                if (iVoiceInteractionManagerService.activeServiceSupportsAssist()) {
                    return true;
                }
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call activeServiceSupportsAssistGesture", e);
            return false;
        }
    }

    public boolean activeServiceSupportsLaunchFromKeyguard() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                if (iVoiceInteractionManagerService.activeServiceSupportsLaunchFromKeyguard()) {
                    return true;
                }
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call activeServiceSupportsLaunchFromKeyguard", e);
            return false;
        }
    }

    public ComponentName getActiveServiceComponentName() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                return iVoiceInteractionManagerService.getActiveServiceComponentName();
            }
            return null;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call getActiveServiceComponentName", e);
            return null;
        }
    }

    public boolean isSessionRunning() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                if (iVoiceInteractionManagerService.isSessionRunning()) {
                    return true;
                }
            }
            return false;
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call isSessionRunning", e);
            return false;
        }
    }

    public void hideCurrentSession() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.hideCurrentSession();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call hideCurrentSession", e);
        }
    }

    public void onLockscreenShown() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.onLockscreenShown();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to call onLockscreenShown", e);
        }
    }

    public void registerVoiceInteractionSessionListener(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.registerVoiceInteractionSessionListener(iVoiceInteractionSessionListener);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to register voice interaction listener", e);
        }
    }

    public void subscribeVisualQueryRecognitionStatus(IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.subscribeVisualQueryRecognitionStatus(iVisualQueryRecognitionStatusListener);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to register visual query detection start listener", e);
        }
    }

    public void enableVisualQueryDetection(IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.enableVisualQueryDetection(iVisualQueryDetectionAttentionListener);
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to register visual query detection attention listener", e);
        }
    }

    public void disableVisualQueryDetection() {
        try {
            IVoiceInteractionManagerService iVoiceInteractionManagerService = this.mVoiceInteractionManagerService;
            if (iVoiceInteractionManagerService != null) {
                iVoiceInteractionManagerService.disableVisualQueryDetection();
            }
        } catch (RemoteException e) {
            Log.w(TAG, "Failed to register visual query detection attention listener", e);
        }
    }

    public ComponentName getAssistComponentForUser(int i) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), Settings.Secure.ASSISTANT, i);
        if (stringForUser != null) {
            return ComponentName.unflattenFromString(stringForUser);
        }
        return null;
    }

    public static boolean isPreinstalledAssistant(Context context, ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(componentName.getPackageName(), 0);
            return applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp();
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isDisclosureEnabled(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.ASSIST_DISCLOSURE_ENABLED, 0) != 0;
    }

    public static boolean shouldDisclose(Context context, ComponentName componentName) {
        return (allowDisablingAssistDisclosure(context) && !isDisclosureEnabled(context) && isPreinstalledAssistant(context, componentName)) ? false : true;
    }

    public static boolean allowDisablingAssistDisclosure(Context context) {
        return context.getResources().getBoolean(R.bool.config_allowDisablingAssistDisclosure);
    }
}
