package com.android.systemui.plugins;

import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.app.smartspace.uitemplatedata.TapAction;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@ProvidesInterface(action = BcSmartspaceDataPlugin.ACTION, version = 1)
/* loaded from: classes2.dex */
public interface BcSmartspaceDataPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_BC_SMARTSPACE_DATA";
    public static final String TAG = "BcSmartspaceDataPlugin";
    public static final String UI_SURFACE_DREAM = "dream";
    public static final String UI_SURFACE_GLANCEABLE_HUB = "glanceable_hub";
    public static final String UI_SURFACE_HOME_SCREEN = "home";
    public static final String UI_SURFACE_LOCK_SCREEN_AOD = "lockscreen";
    public static final String UI_SURFACE_MEDIA = "media_data_manager";
    public static final int VERSION = 1;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SmartspaceEventNotifier {
        void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SmartspaceTargetListener {
        void onSmartspaceTargetsUpdated(List<? extends Parcelable> list);
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface TimeChangedDelegate {
        void register(Runnable runnable);

        void unregister();
    }

    default void addOnAttachStateChangeListener(View.OnAttachStateChangeListener onAttachStateChangeListener) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default SmartspaceView getView(ViewGroup viewGroup) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void notifySmartspaceEvent(SmartspaceTargetEvent smartspaceTargetEvent) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void onTargetsAvailable(List<SmartspaceTarget> list) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void registerListener(SmartspaceTargetListener smartspaceTargetListener) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void registerSmartspaceEventNotifier(SmartspaceEventNotifier smartspaceEventNotifier) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    default void unregisterListener(SmartspaceTargetListener smartspaceTargetListener) {
        throw new UnsupportedOperationException("Not implemented by " + getClass());
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface IntentStarter {
        default void startFromAction(SmartspaceAction smartspaceAction, View view, boolean z) {
            try {
                if (smartspaceAction.getIntent() != null) {
                    startIntent(view, smartspaceAction.getIntent(), z);
                } else if (smartspaceAction.getPendingIntent() != null) {
                    startPendingIntent(view, smartspaceAction.getPendingIntent(), z);
                }
            } catch (ActivityNotFoundException e) {
                Log.w(BcSmartspaceDataPlugin.TAG, "Could not launch intent for action: " + smartspaceAction, e);
            }
        }

        void startIntent(View view, Intent intent, boolean z);

        void startPendingIntent(View view, PendingIntent pendingIntent, boolean z);

        default void startFromAction(TapAction tapAction, View view, boolean z) {
            try {
                if (tapAction.getIntent() != null) {
                    startIntent(view, tapAction.getIntent(), z);
                } else if (tapAction.getPendingIntent() != null) {
                    startPendingIntent(view, tapAction.getPendingIntent(), z);
                }
            } catch (ActivityNotFoundException e) {
                Log.w(BcSmartspaceDataPlugin.TAG, "Could not launch intent for action: " + tapAction, e);
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface SmartspaceView {
        default int getCurrentCardTopPadding() {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        default int getSelectedPage() {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        default void registerConfigProvider(BcSmartspaceConfigPlugin bcSmartspaceConfigPlugin) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        void registerDataProvider(BcSmartspaceDataPlugin bcSmartspaceDataPlugin);

        default void setDnd(Drawable drawable, String str) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        void setDozeAmount(float f);

        void setFalsingManager(FalsingManager falsingManager);

        void setIntentStarter(IntentStarter intentStarter);

        default void setMediaTarget(SmartspaceTarget smartspaceTarget) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        default void setNextAlarm(Drawable drawable, String str) {
            throw new UnsupportedOperationException("Not implemented by " + getClass());
        }

        void setPrimaryTextColor(int i);

        void setUiSurface(String str);

        default void setDozing(boolean z) {
        }

        default void setKeyguardBypassEnabled(boolean z) {
        }

        default void setScreenOn(boolean z) {
        }

        default void setSplitShadeEnabled(boolean z) {
        }

        default void setTimeChangedDelegate(TimeChangedDelegate timeChangedDelegate) {
        }
    }
}
