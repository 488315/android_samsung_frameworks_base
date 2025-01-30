package com.android.wm.shell.bubbles;

import android.app.PendingIntent;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.wm.shell.bubbles.BubbleViewInfoTask;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class BubbleViewInfoTask$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ BubbleViewInfoTask f$0;
    public final /* synthetic */ BubbleViewInfoTask.BubbleViewInfo f$1;

    public /* synthetic */ BubbleViewInfoTask$$ExternalSyntheticLambda0(BubbleViewInfoTask bubbleViewInfoTask, BubbleViewInfoTask.BubbleViewInfo bubbleViewInfo) {
        this.f$0 = bubbleViewInfoTask;
        this.f$1 = bubbleViewInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        BubbleViewInfoTask bubbleViewInfoTask = this.f$0;
        BubbleViewInfoTask.BubbleViewInfo bubbleViewInfo = this.f$1;
        Bubble bubble = bubbleViewInfoTask.mBubble;
        boolean z = true;
        if (!(((bubble.mIconView == null || bubble.mExpandedView == null) && bubble.mBubbleBarExpandedView == null) ? false : true)) {
            bubble.mIconView = bubbleViewInfo.imageView;
            bubble.mExpandedView = bubbleViewInfo.expandedView;
            bubble.mBubbleBarExpandedView = null;
        }
        bubble.mShortcutInfo = bubbleViewInfo.shortcutInfo;
        String str = bubbleViewInfo.appName;
        bubble.mAppName = str;
        if (bubble.mTitle == null) {
            bubble.mTitle = str;
        }
        bubble.mFlyoutMessage = bubbleViewInfo.flyoutMessage;
        bubble.mBadgeBitmap = bubbleViewInfo.badgeBitmap;
        bubble.mBubbleBitmap = bubbleViewInfo.bubbleBitmap;
        bubble.mDotColor = bubbleViewInfo.dotColor;
        bubble.mDotPath = bubbleViewInfo.dotPath;
        final BubbleExpandedView bubbleExpandedView = bubble.mExpandedView;
        if (bubbleExpandedView != null) {
            Log.d("Bubbles", "update: bubble=" + bubble);
            if (bubbleExpandedView.mStackView == null) {
                Log.w("Bubbles", "Stack is null for bubble: " + bubble);
            } else {
                Bubble bubble2 = bubbleExpandedView.mBubble;
                if (bubble2 != null) {
                    if (!((bubbleExpandedView.mPendingIntent != null) != (bubble.mIntent != null))) {
                        z = false;
                    }
                }
                if (!z) {
                    String str2 = bubble2.mKey;
                    String str3 = bubble.mKey;
                    if (!str3.equals(str2)) {
                        Log.w("Bubbles", "Trying to update entry with different key, new bubble: " + str3 + " old bubble: " + str3);
                    }
                }
                bubbleExpandedView.mBubble = bubble;
                bubbleExpandedView.mManageButton.setContentDescription(bubbleExpandedView.getResources().getString(R.string.bubbles_settings_button_description, bubble.mAppName));
                bubbleExpandedView.mManageButton.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.bubbles.BubbleExpandedView.7
                    public C38047() {
                    }

                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                        BubbleExpandedView.this.mStackView.setupLocalMenu(accessibilityNodeInfo);
                    }
                });
                if (z) {
                    Bubble bubble3 = bubbleExpandedView.mBubble;
                    PendingIntent pendingIntent = bubble3.mIntent;
                    bubbleExpandedView.mPendingIntent = pendingIntent;
                    if ((pendingIntent != null || bubble3.hasMetadataShortcutId()) && bubbleExpandedView.mTaskView != null) {
                        bubbleExpandedView.setContentVisibility(false);
                        bubbleExpandedView.mTaskView.setVisibility(0);
                    }
                }
                bubbleExpandedView.applyThemeAttrs();
            }
        }
        if (bubble.mBubbleBarExpandedView != null) {
            throw null;
        }
        BadgedImageView badgedImageView = bubble.mIconView;
        if (badgedImageView != null) {
            badgedImageView.setRenderedBubble(bubble);
        }
        BubbleViewInfoTask.Callback callback = bubbleViewInfoTask.mCallback;
        if (callback != null) {
            callback.onBubbleViewsReady(bubbleViewInfoTask.mBubble);
        }
    }
}
