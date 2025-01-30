package com.android.settingslib.core.lifecycle;

import android.app.Activity;
import android.provider.Settings;
import android.util.EventLog;
import android.view.Window;
import android.view.WindowManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class HideNonSystemOverlayMixin implements androidx.lifecycle.LifecycleObserver {
    public final Activity mActivity;

    public HideNonSystemOverlayMixin(Activity activity) {
        this.mActivity = activity;
    }

    public boolean isEnabled() {
        return Settings.Secure.getInt(this.mActivity.getContentResolver(), "secure_overlay_settings", 0) == 0;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Activity activity = this.mActivity;
        if (activity == null || !isEnabled()) {
            return;
        }
        activity.getWindow().addSystemFlags(524288);
        EventLog.writeEvent(1397638484, "120484087", -1, "");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Activity activity = this.mActivity;
        if (activity == null || !isEnabled()) {
            return;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.privateFlags &= -524289;
        window.setAttributes(attributes);
    }
}
