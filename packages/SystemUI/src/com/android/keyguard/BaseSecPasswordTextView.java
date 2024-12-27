package com.android.keyguard;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import com.android.systemui.widget.SystemUIEditText;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class BaseSecPasswordTextView extends SystemUIEditText {
    public int mMaxLength;
    public boolean mShowPassword;
    public String mText;
    public UserActivityListener mUserActivityListener;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface UserActivityListener {
        void onUserActivity();
    }

    public BaseSecPasswordTextView(Context context) {
        super(context);
        this.mText = "";
        this.mShowPassword = true;
        this.mMaxLength = 256;
    }

    public final void deleteLastChar() {
        int length = this.mText.length();
        if (length > 0) {
            CharSequence transformedText = getTransformedText();
            int i = length - 1;
            this.mText = this.mText.substring(0, i);
            onDelete(i);
            sendAccessibilityEventTypeViewTextChanged(transformedText, transformedText.length() - 1, 1, 0);
        }
        onUserActivity();
    }

    public int getTextCharsSize() {
        return 0;
    }

    public CharSequence getTransformedText() {
        return String.valueOf((char) 8226).repeat(this.mText.length());
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(EditText.class.getName());
        accessibilityEvent.setPassword(true);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EditText.class.getName());
        accessibilityNodeInfo.setPassword(true);
        accessibilityNodeInfo.setText(getTransformedText());
        accessibilityNodeInfo.setEditable(true);
        accessibilityNodeInfo.setInputType(16);
    }

    public void onUserActivity() {
        UserActivityListener userActivityListener = this.mUserActivityListener;
        if (userActivityListener != null) {
            userActivityListener.onUserActivity();
        }
    }

    public final void reset(boolean z, boolean z2) {
        CharSequence transformedText = getTransformedText();
        this.mText = "";
        onReset(z);
        if (z2) {
            sendAccessibilityEventTypeViewTextChanged(transformedText, 0, transformedText.length(), 0);
        }
    }

    public final void sendAccessibilityEventTypeViewTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (AccessibilityManager.getInstance(((EditText) this).mContext).isTouchExplorationEnabled() && isShown()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(16);
            obtain.setFromIndex(i);
            obtain.setRemovedCount(i2);
            obtain.setAddedCount(i3);
            obtain.setBeforeText(charSequence);
            CharSequence transformedText = getTransformedText();
            if (!TextUtils.isEmpty(transformedText)) {
                obtain.getText().add(transformedText);
            }
            obtain.setPassword(true);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    public BaseSecPasswordTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mText = "";
        this.mShowPassword = true;
        this.mMaxLength = 256;
    }

    public void onDelete(int i) {
    }

    public void onReset(boolean z) {
    }
}
