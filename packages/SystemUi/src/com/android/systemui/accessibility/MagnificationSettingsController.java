package com.android.systemui.accessibility;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Range;
import com.android.internal.graphics.SfVsyncFrameCallbackProvider;
import com.android.systemui.R;
import com.android.systemui.accessibility.AccessibilityLogger;
import com.android.systemui.accessibility.WindowMagnification;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MagnificationSettingsController implements ComponentCallbacks {
    public static final Range A11Y_ACTION_SCALE_RANGE = new Range(Float.valueOf(1.0f), Float.valueOf(8.0f));
    public final Configuration mConfiguration;
    public final Context mContext;
    public final int mDisplayId;
    public final Callback mSettingsControllerCallback;
    public final WindowMagnificationSettings mWindowMagnificationSettings;
    final WindowMagnificationSettingsCallback mWindowMagnificationSettingsCallback;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.accessibility.MagnificationSettingsController$1 */
    public final class C09961 implements WindowMagnificationSettingsCallback {
        public C09961() {
        }

        public final void onSettingsPanelVisibilityChanged(boolean z) {
            MagnificationSettingsController magnificationSettingsController = MagnificationSettingsController.this;
            Callback callback = magnificationSettingsController.mSettingsControllerCallback;
            int i = magnificationSettingsController.mDisplayId;
            WindowMagnification.C10003 c10003 = (WindowMagnification.C10003) callback;
            WindowMagnification windowMagnification = WindowMagnification.this;
            windowMagnification.mHandler.post(new WindowMagnification$3$$ExternalSyntheticLambda1(c10003, i, z, 0));
            windowMagnification.mA11yLogger.uiEventLogger.log(z ? AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_PANEL_OPENED : AccessibilityLogger.MagnificationSettingsEvent.MAGNIFICATION_SETTINGS_PANEL_CLOSED);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        C09961 c09961 = new C09961();
        this.mWindowMagnificationSettingsCallback = c09961;
        this.mContext = context;
        context.setTheme(2132018524);
        this.mDisplayId = context.getDisplayId();
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
        this.mSettingsControllerCallback = callback;
        if (windowMagnificationSettings != null) {
            this.mWindowMagnificationSettings = windowMagnificationSettings;
        } else {
            this.mWindowMagnificationSettings = new WindowMagnificationSettings(context, c09961, sfVsyncFrameCallbackProvider, secureSettings);
        }
    }

    public void onConfigurationChanged(int i) {
        WindowMagnificationSettings windowMagnificationSettings = this.mWindowMagnificationSettings;
        windowMagnificationSettings.getClass();
        if ((i & 512) == 0 && (Integer.MIN_VALUE & i) == 0 && (1073741824 & i) == 0 && (i & 4) == 0 && (i & 4096) == 0) {
            if ((i & 128) != 0) {
                windowMagnificationSettings.hideSettingPanel(false);
                return;
            }
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
