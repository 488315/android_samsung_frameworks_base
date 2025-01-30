package com.android.systemui.clipboardoverlay;

import android.R;
import android.app.ICompatCameraControlCallback;
import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import com.android.internal.policy.PhoneWindow;
import com.android.systemui.screenshot.FloatingWindowUtil;
import java.util.function.BiConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClipboardOverlayWindow extends PhoneWindow implements ViewRootImpl.ActivityConfigCallback {
    public final Context mContext;
    public boolean mKeyboardVisible;
    public BiConsumer mOnKeyboardChangeListener;
    public Runnable mOnOrientationChangeListener;
    public final int mOrientation;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;

    public ClipboardOverlayWindow(Context context) {
        super(context);
        View peekDecorView;
        this.mContext = context;
        this.mOrientation = context.getResources().getConfiguration().orientation;
        requestFeature(1);
        requestFeature(13);
        setBackgroundDrawableResource(R.color.transparent);
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        WindowManager.LayoutParams floatingWindowParams = FloatingWindowUtil.getFloatingWindowParams();
        this.mWindowLayoutParams = floatingWindowParams;
        floatingWindowParams.setTitle("ClipboardOverlay");
        setWindowManager(windowManager, (IBinder) null, (String) null);
        int i = floatingWindowParams.flags;
        int i2 = i | 8;
        floatingWindowParams.flags = i2;
        if (i2 == i || (peekDecorView = peekDecorView()) == null || !peekDecorView.isAttachedToWindow()) {
            return;
        }
        windowManager.updateViewLayout(peekDecorView, floatingWindowParams);
    }

    public final void onConfigurationChanged(Configuration configuration, int i) {
        if (this.mContext.getResources().getConfiguration().orientation != this.mOrientation) {
            this.mOnOrientationChangeListener.run();
        }
    }

    public final void requestCompatCameraControl(boolean z, boolean z2, ICompatCameraControlCallback iCompatCameraControlCallback) {
        Log.w("ClipboardOverlayWindow", "unexpected requestCompatCameraControl call");
    }
}
