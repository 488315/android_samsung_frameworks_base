package com.android.systemui.clipboardoverlay;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.os.IBinder;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import com.android.internal.policy.PhoneWindow;
import com.android.systemui.screenshot.FloatingWindowUtil;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ClipboardOverlayWindow extends PhoneWindow implements ViewRootImpl.ActivityConfigCallback {
    public final Context mContext;
    public boolean mKeyboardVisible;
    public ClipboardOverlayController$$ExternalSyntheticLambda0 mOnKeyboardChangeListener;
    public ClipboardOverlayController$$ExternalSyntheticLambda1 mOnOrientationChangeListener;
    public final int mOrientation;
    public final WindowManager.LayoutParams mWindowLayoutParams;
    public final WindowManager mWindowManager;

    public ClipboardOverlayWindow(Context context, WindowManager windowManager) {
        super(context);
        View peekDecorView;
        this.mContext = context;
        this.mOrientation = context.getResources().getConfiguration().orientation;
        requestFeature(1);
        requestFeature(13);
        setBackgroundDrawableResource(R.color.transparent);
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
}
