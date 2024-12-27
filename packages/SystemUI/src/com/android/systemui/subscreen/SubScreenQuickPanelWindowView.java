package com.android.systemui.subscreen;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.FrameLayout;

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
