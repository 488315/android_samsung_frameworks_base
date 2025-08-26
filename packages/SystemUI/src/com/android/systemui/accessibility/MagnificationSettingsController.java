package com.android.systemui.accessibility;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.util.Log;
import android.util.Range;
import android.view.accessibility.IMagnificationConnectionCallback;
import com.android.internal.accessibility.common.MagnificationConstants;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.R;
import com.android.systemui.accessibility.AccessibilityLogger;
import com.android.systemui.accessibility.MagnificationImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public class MagnificationSettingsController implements ComponentCallbacks {
    public static final Range A11Y_ACTION_SCALE_RANGE = new Range(Float.valueOf(1.0f), Float.valueOf(MagnificationConstants.SCALE_MAX_VALUE));
    public final Configuration mConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public final Callback mSettingsControllerCallback;
    public final WindowMagnificationSettings mWindowMagnificationSettings;
    final WindowMagnificationSettingsCallback mWindowMagnificationSettingsCallback;

    /* renamed from: com.android.systemui.accessibility.MagnificationSettingsController$1, reason: invalid class name */
    public class AnonymousClass1 implements WindowMagnificationSettingsCallback {
        public AnonymousClass1() {
        }

        public final void onMagnifierScale(float f, boolean z) {
            IMagnificationConnectionCallback iMagnificationConnectionCallback;
            MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
            Callback callback = magnificationSettingsController.mSettingsControllerCallback;
            int i = magnificationSettingsController.mDisplayId;
            float fFloatValue = ((Float) MagnificationSettingsController.A11Y_ACTION_SCALE_RANGE.clamp(Float.valueOf(f))).floatValue();
            MagnificationImpl.AnonymousClass4 anonymousClass4 = (MagnificationImpl.AnonymousClass4) callback;
            MagnificationConnectionImpl magnificationConnectionImpl = MagnificationImpl.this.mMagnificationConnectionImpl;
            if (magnificationConnectionImpl != null && (iMagnificationConnectionCallback = magnificationConnectionImpl.mConnectionCallback) != null) {
                try {
                    iMagnificationConnectionCallback.onPerformScaleAction(i, fFloatValue, z);
                } catch (RemoteException e) {
                    Log.e("WindowMagnificationConnectionImpl", "Failed to inform performing scale action", e);
                }
            }
            AccessibilityLogger accessibilityLogger = MagnificationImpl.this.mA11yLogger;
            AccessibilityLogger.MagnificationSettingsEvent magnificationSettingsEvent = AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_ZOOM_SLIDER_CHANGED;
            synchronized (accessibilityLogger.clock) {
                try {
                    long jElapsedRealtime = accessibilityLogger.clock.elapsedRealtime();
                    boolean z2 = Intrinsics.areEqual(magnificationSettingsEvent, accessibilityLogger.lastEventThrottled) && jElapsedRealtime - accessibilityLogger.lastTimeThrottledMs < ((long) 2000);
                    accessibilityLogger.lastEventThrottled = magnificationSettingsEvent;
                    accessibilityLogger.lastTimeThrottledMs = jElapsedRealtime;
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

    public MagnificationSettingsController(Context context, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, Callback callback, SecureSettings secureSettings, WindowManagerProvider windowManagerProvider) {
        this(context, sfVsyncFrameCallbackProvider, callback, secureSettings, windowManagerProvider, null);
    }

    @Override // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        int iDiff = configuration.diff(this.mConfiguration);
        this.mConfiguration.setTo(configuration);
        onConfigurationChanged(iDiff);
    }

    public MagnificationSettingsController(Context context, SfVsyncFrameCallbackProvider sfVsyncFrameCallbackProvider, Callback callback, SecureSettings secureSettings, WindowManagerProvider windowManagerProvider, WindowMagnificationSettings windowMagnificationSettings) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mWindowMagnificationSettingsCallback = anonymousClass1;
        Context contextCreateWindowContext = context.createWindowContext(context.getDisplay(), 2024, null);
        this.mContext = contextCreateWindowContext;
        contextCreateWindowContext.setTheme(R.style.Theme_SystemUI);
        this.mDisplayId = contextCreateWindowContext.getDisplayId();
        this.mConfiguration = new Configuration(contextCreateWindowContext.getResources().getConfiguration());
        this.mSettingsControllerCallback = callback;
        if (windowMagnificationSettings != null) {
            this.mWindowMagnificationSettings = windowMagnificationSettings;
        } else {
            ((WindowManagerProviderImpl) windowManagerProvider).getClass();
            this.mWindowMagnificationSettings = new WindowMagnificationSettings(contextCreateWindowContext, anonymousClass1, sfVsyncFrameCallbackProvider, secureSettings, WindowManagerUtils.getWindowManager(contextCreateWindowContext));
        }
    }

    public void onConfigurationChanged(int i) {
        WindowMagnificationSettings windowMagnificationSettings = this.mWindowMagnificationSettings;
        if (windowMagnificationSettings == null) {
            return;
        }
        if ((i & 512) == 0 && (Integer.MIN_VALUE & i) == 0 && (1073741824 & i) == 0 && (i & 4) == 0 && (i & 4096) == 0 && (268435456 & i) == 0) {
            if ((i & 128) == 0 && (i & 1024) == 0) {
                return;
            }
            windowMagnificationSettings.hideSettingPanel(false);
            return;
        }
        windowMagnificationSettings.mParams.width = windowMagnificationSettings.getPanelWidth(windowMagnificationSettings.mContext);
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
