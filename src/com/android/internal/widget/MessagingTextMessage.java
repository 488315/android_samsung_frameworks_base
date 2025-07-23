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
/* loaded from: classes6.dex */
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

    public MessagingTextMessage(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = new MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mState = new MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    public MessagingTextMessage(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mState = new MessagingMessageState(this);
        this.mPrecomputedText = null;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public MessagingMessageState getState() {
        return this.mState;
    }

    @Override // com.android.internal.widget.MessagingMessage
    public boolean setMessage(Notification.MessagingStyle.Message message, boolean z) {
        super.setMessage(message, z);
        if (z) {
            this.mPrecomputedText = PrecomputedText.create(message.getText(), getTextMetricsParams());
            return true;
        }
        lambda$setTextAsync$0(message.getText());
        this.mPrecomputedText = null;
        return true;
    }

    static MessagingMessage createMessage(IMessagingLayout iMessagingLayout, Notification.MessagingStyle.Message message, boolean z) {
        MessagingLinearLayout messagingLinearLayout = iMessagingLayout.getMessagingLinearLayout();
        MessagingTextMessage acquire = sInstancePool.acquire();
        if (acquire == null) {
            acquire = (MessagingTextMessage) LayoutInflater.from(iMessagingLayout.getContext()).inflate(R.layout.notification_template_messaging_text_message, (ViewGroup) messagingLinearLayout, false);
            acquire.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        acquire.setMessage(message, z);
        return acquire;
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
        if ((getMeasuredHeight() >= getLayoutHeight() + getPaddingTop() + getPaddingBottom() || getLineCount() > 1) && (layout = getLayout()) != null) {
            return layout.getEllipsisCount(layout.getLineCount() - 1) > 0 ? 1 : 0;
        }
        return 2;
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    public void setMaxDisplayedLines(int i) {
        setMaxLines(i);
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
    public void setColor(int i) {
        setTextColor(i);
    }

    @Override // com.android.internal.widget.MessagingMessage
    public void finalizeInflate() {
        try {
            CharSequence charSequence = this.mPrecomputedText;
            if (charSequence == null) {
                charSequence = getState().getMessage().getText();
            }
            lambda$setTextAsync$0(charSequence);
        } catch (IllegalArgumentException e) {
            Log.wtf(TAG, "PrecomputedText setText failed for TextView:" + this, e);
            this.mPrecomputedText = null;
            lambda$setTextAsync$0(getState().getMessage().getText());
        }
    }
}
