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
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
}
