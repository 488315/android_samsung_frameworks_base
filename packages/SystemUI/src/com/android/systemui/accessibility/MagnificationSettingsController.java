package com.android.systemui.accessibility;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.util.Log;
import android.util.Range;
import android.view.accessibility.IMagnificationConnectionCallback;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.R;
import com.android.systemui.accessibility.AccessibilityLogger;
import com.android.systemui.accessibility.Magnification;
import com.android.systemui.util.settings.SecureSettings;
import kotlin.Unit;

public final class MagnificationSettingsController implements ComponentCallbacks {
    public static final Range A11Y_ACTION_SCALE_RANGE = new Range(Float.valueOf(1.0f), Float.valueOf(8.0f));
    public final Configuration mConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public final Callback mSettingsControllerCallback;
    public final WindowMagnificationSettings mWindowMagnificationSettings;
    final WindowMagnificationSettingsCallback mWindowMagnificationSettingsCallback;

    /* renamed from: com.android.systemui.accessibility.MagnificationSettingsController$1, reason: invalid class name */
    public final class AnonymousClass1 implements WindowMagnificationSettingsCallback {
        public AnonymousClass1() {
        }

        public final void onMagnifierScale(float f, boolean z) {
            IMagnificationConnectionCallback iMagnificationConnectionCallback;
            MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
            Callback callback = magnificationSettingsController.mSettingsControllerCallback;
            int i = magnificationSettingsController.mDisplayId;
            float floatValue = ((Float) MagnificationSettingsController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(f))).floatValue();
            Magnification.AnonymousClass4 anonymousClass4 = (Magnification.AnonymousClass4) callback;
            MagnificationConnectionImpl magnificationConnectionImpl = Magnification.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                try {
                    iMagnificationConnectionCallback.onPerformScaleAction(i, floatValue, z);
                } catch (RemoteException e) {
                    Log.e("WindowMagnificationConnectionImpl", "Failed to inform performing scale action", e);
                }
            }
            AccessibilityLogger accessibilityLogger = Magnification.this.mA11yLogger;
            AccessibilityLogger.MagnificationSettingsEvent magnificationSettingsEvent = AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_ZOOM_SLIDER_CHANGED;
            synchronized (accessibilityLogger.clock) {
                try {
                    long elapsedRealtime = accessibilityLogger.clock.elapsedRealtime();
                    boolean z2 = magnificationSettingsEvent.equals(accessibilityLogger.lastEventThrottled) && elapsedRealtime - accessibilityLogger.lastTimeThrottledMs < ((long) 2000);
                    accessibilityLogger.lastEventThrottled = magnificationSettingsEvent;
                    accessibilityLogger.lastTimeThrottledMs = elapsedRealtime;
                    if (z2) {
                        return;
                    }
                    Unit unit = Unit.INSTANCE;
                    accessibilityLogger.uiEventLogger.log(magnificationSettingsEvent);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public interface Callback {
    }

    public MagnificationSettingsController(Context context, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, Callback callback, SecureSettings secureSettings) {
        this(context, sfVsyncFrameCallbackProvider, callback, secureSettings, null);
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        int diff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        onConfigurationChanged(diff);
    }

    public MagnificationSettingsController(Context context, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, Callback callback, SecureSettings secureSettings, WindowMagnificationSettings windowMagnificationSettings) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mWindowMagnificationSettingsCallback = anonymousClass1;
        Context createWindowContext = context.createWindowContext(context.getDisplay(), 2024, null);
        this.mContext = createWindowContext;
        createWindowContext.setTheme(R.style.Theme_SystemUI);
        this.mDisplayId = createWindowContext.getDisplayId();
        this.mConfiguration = new Configuration(createWindowContext.getResources().getConfiguration());
        this.mSettingsControllerCallback = callback;
        if (windowMagnificationSettings != null) {
            this.mWindowMagnificationSettings = windowMagnificationSettings;
        } else {
            this.mWindowMagnificationSettings = new WindowMagnificationSettings(createWindowContext, anonymousClass1, sfVsyncFrameCallbackProvider, secureSettings);
        }
    }

    public void onConfigurationChanged(int i) {
        WindowMagnificationSettings windowMagnificationSettings = this.mWindowMagnificationSettings;
        windowMagnificationSettings.getClass();
        if ((i & 512) == 0 && (Integer.MIN_VALUE & i) == 0 && (1073741824 & i) == 0 && (i & 4) == 0 && (i & 4096) == 0) {
            if ((i & 128) == 0 && (i & 1024) == 0) {
                return;
            }
            windowMagnificationSettings.hideSettingPanel(false);
            return;
        }
        windowMagnificationSettings.mParams.accessibilityTitle = windowMagnificationSettings.mContext.getString(R.string.accessibility_magnification_title);
        boolean z = windowMagnificationSettings.mIsVisible;
        windowMagnificationSettings.hideSettingPanel(false);
        windowMagnificationSettings.inflateView();
        if (z) {
            windowMagnificationSettings.showSettingPanel(false);
        }
    }

    @Override // android.content.ComponentCallbacks
    public final void onLowMemory() {
    }
}
