package com.android.keyguard;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import com.android.systemui.widget.SystemUIEditText;

/* loaded from: classes.dex */
public abstract class BaseSecPasswordTextView extends SystemUIEditText {
    public int mMaxLength;
    public boolean mShowPassword;
    public String mText;
    public UserActivityListener mUserActivityListener;

    public interface UserActivityListener {
        void onUserActivity();
    }

    public BaseSecPasswordTextView(Context context) {
        super(context);
        this.mText = "";
        this.mShowPassword = true;
        this.mMaxLength = 256;
    }

    public final void append(char c) {
        CharSequence transformedText = getTransformedText();
        if (this.mText.length() >= this.mMaxLength || getTextCharsSize() >= this.mMaxLength) {
            return;
        }
        String strM = OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder(), this.mText, c);
        this.mText = strM;
        onAppend(c, strM.length());
        onUserActivity();
        sendAccessibilityEventTypeViewTextChanged(transformedText, transformedText.length(), 0, 1);
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
        accessibilityNodeInfo.setText(String.valueOf((char) 8226).repeat(this.mText.length()));
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
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(16);
            accessibilityEventObtain.setFromIndex(i);
            accessibilityEventObtain.setRemovedCount(i2);
            accessibilityEventObtain.setAddedCount(i3);
            accessibilityEventObtain.setBeforeText(charSequence);
            CharSequence transformedText = getTransformedText();
            if (!TextUtils.isEmpty(transformedText)) {
                accessibilityEventObtain.getText().add(transformedText);
            }
            accessibilityEventObtain.setPassword(true);
            sendAccessibilityEventUnchecked(accessibilityEventObtain);
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

    public void onAppend(char c, int i) {
    }
}
