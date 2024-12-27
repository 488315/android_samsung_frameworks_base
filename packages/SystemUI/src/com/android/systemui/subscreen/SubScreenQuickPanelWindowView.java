package com.android.systemui.subscreen;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SubScreenQuickPanelWindowView extends FrameLayout {
    public SubScreenQSEventHandler mSubScreenQSTouchHandler;

    public SubScreenQuickPanelWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.mSubScreenQSTouchHandler == null) {
            return false;
        }
        Log.d("SubScreenQuickPanelWindowView", "dispatchKeyEvent ,event:" + keyEvent);
        return (keyEvent.getKeyCode() == 4 && (keyEvent.getAction() == 1)) ? this.mSubScreenQSTouchHandler.dispatchKeyEvent(keyEvent) : super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        SubScreenQSEventHandler subScreenQSEventHandler = this.mSubScreenQSTouchHandler;
        if (subScreenQSEventHandler == null) {
            return false;
        }
        return subScreenQSEventHandler.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        SubScreenQSEventHandler subScreenQSEventHandler = this.mSubScreenQSTouchHandler;
        if (subScreenQSEventHandler == null) {
            return false;
        }
        return subScreenQSEventHandler.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        SubScreenQSEventHandler subScreenQSEventHandler = this.mSubScreenQSTouchHandler;
        if (subScreenQSEventHandler == null) {
            return;
        }
        subScreenQSEventHandler.onWindowVisibilityChanged();
    }
}
