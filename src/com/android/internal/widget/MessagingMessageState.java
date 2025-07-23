package com.android.internal.widget;

import android.app.Notification;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/* loaded from: classes6.dex */
public class MessagingMessageState {
    private MessagingGroup mGroup;
    private final View mHostView;
    private boolean mIsHidingAnimated;
    private boolean mIsHistoric;
    private Notification.MessagingStyle.Message mMessage;

    MessagingMessageState(View view) {
        this.mHostView = view;
    }

    public void setMessage(Notification.MessagingStyle.Message message) {
        this.mMessage = message;
    }

    public Notification.MessagingStyle.Message getMessage() {
        return this.mMessage;
    }

    public void setGroup(MessagingGroup messagingGroup) {
        this.mGroup = messagingGroup;
    }

    public MessagingGroup getGroup() {
        return this.mGroup;
    }

    public void setIsHistoric(boolean z) {
        this.mIsHistoric = z;
    }

    public void setIsHidingAnimated(boolean z) {
        ViewParent parent = this.mHostView.getParent();
        this.mIsHidingAnimated = z;
        this.mHostView.invalidate();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).invalidate();
        }
    }

    public boolean isHidingAnimated() {
        return this.mIsHidingAnimated;
    }

    public View getHostView() {
        return this.mHostView;
    }

    public void recycle() {
        this.mHostView.setAlpha(1.0f);
        this.mHostView.setTranslationY(0.0f);
        MessagingPropertyAnimator.recycle(this.mHostView);
        this.mIsHidingAnimated = false;
        this.mIsHistoric = false;
        this.mGroup = null;
        this.mMessage = null;
    }
}
