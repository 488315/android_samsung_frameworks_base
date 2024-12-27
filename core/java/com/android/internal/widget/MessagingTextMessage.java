package com.android.internal.widget;

import android.app.Notification;
import android.content.Context;
import android.text.Layout;
import android.text.PrecomputedText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class MessagingTextMessage extends ImageFloatingTextView implements MessagingMessage {
    private static final String TAG = "MessagingTextMessage";
    private static final MessagingPool<MessagingTextMessage> sInstancePool = new MessagingPool<>(20);
    private PrecomputedText mPrecomputedText;
    private final MessagingMessageState mState;

    public MessagingTextMessage(Context context) {
        super(context);
        this.mState = new MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mState = new MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mState = new MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mState = new MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public MessagingMessageState getState() {
        return this.mState;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public boolean setMessage(Notification.MessagingStyle.Message message, boolean usePrecomputedText) {
        super.setMessage(message, usePrecomputedText);
        if (usePrecomputedText) {
            this.mPrecomputedText = PrecomputedText.create(message.getText(), getTextMetricsParams());
            return true;
        }
        lambda$setTextAsync$0(message.getText());
        this.mPrecomputedText = null;
        return true;
    }

    static MessagingMessage createMessage(IMessagingLayout layout, Notification.MessagingStyle.Message m, boolean usePrecomputedText) {
        MessagingLinearLayout messagingLinearLayout = layout.getMessagingLinearLayout();
        MessagingTextMessage createdMessage = sInstancePool.acquire();
        if (createdMessage == null) {
            createdMessage = (MessagingTextMessage) LayoutInflater.from(layout.getContext()).inflate(R.layout.notification_template_messaging_text_message, (ViewGroup) messagingLinearLayout, false);
            createdMessage.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        createdMessage.setMessage(m, usePrecomputedText);
        return createdMessage;
    }

    @Override // com.android.internal.widget.MessagingMessage, com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void recycle() {
        super.recycle();
        sInstancePool.release((MessagingPool<MessagingTextMessage>) this);
    }

    public static void dropCache() {
        sInstancePool.clear();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getMeasuredType() {
        Layout layout;
        boolean measuredTooSmall = getMeasuredHeight() < (getLayoutHeight() + getPaddingTop()) + getPaddingBottom();
        if ((!measuredTooSmall || getLineCount() > 1) && (layout = getLayout()) != null) {
            return layout.getEllipsisCount(layout.getLineCount() - 1) > 0 ? 1 : 0;
        }
        return 2;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int lines) {
        setMaxLines(lines);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public int getConsumedLines() {
        return getLineCount();
    }

    public int getLayoutHeight() {
        Layout layout = getLayout();
        if (layout == null) {
            return 0;
        }
        return layout.getHeight();
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void setColor(int color) {
        setTextColor(color);
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void finalizeInflate() {
        try {
            lambda$setTextAsync$0(this.mPrecomputedText != null ? this.mPrecomputedText : getState().getMessage().getText());
        } catch (IllegalArgumentException exception) {
            Log.wtf(TAG, "PrecomputedText setText failed for TextView:" + this, exception);
            this.mPrecomputedText = null;
            lambda$setTextAsync$0(getState().getMessage().getText());
        }
    }
}
