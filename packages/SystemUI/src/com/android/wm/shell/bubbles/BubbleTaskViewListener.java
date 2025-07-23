package com.android.wm.shell.bubbles;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.bubbles.Bubble;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleTransitions;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.taskview.TaskView;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubbleTaskViewListener implements TaskView.Listener {
    public Bubble mBubble;
    public final Callback mCallback;
    public final Context mContext;
    public final BubbleExpandedViewManager mExpandedViewManager;
    public final View mParentView;
    public PendingIntent mPendingIntent;
    public int mTaskId;
    public TaskView mTaskView;
    public boolean mInitialized = false;
    public boolean mDestroyed = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onBackPressed();

        void onContentVisibilityChanged(boolean z);

        void onTaskCreated();

        void onTaskRemovalStarted();
    }

    public BubbleTaskViewListener(Context context, BubbleTaskView bubbleTaskView, View view, BubbleExpandedViewManager bubbleExpandedViewManager, Callback callback) {
        this.mTaskId = -1;
        this.mContext = context;
        this.mTaskView = bubbleTaskView.taskView;
        this.mParentView = view;
        this.mExpandedViewManager = bubbleExpandedViewManager;
        this.mCallback = callback;
        bubbleTaskView.delegateListener = this;
        if (bubbleTaskView.isCreated) {
            this.mTaskId = bubbleTaskView.taskId;
            callback.onTaskCreated();
        }
    }

    public final String getBubbleKey() {
        Bubble bubble = this.mBubble;
        return bubble != null ? bubble.mKey : "";
    }

    @Override // com.android.wm.shell.taskview.TaskView.Listener
    public final void onBackPressedOnTaskRoot(int i) {
        if (this.mTaskId == i && ((BubbleExpandedViewManager$Companion$fromBubbleController$1) this.mExpandedViewManager).$controller.mBubbleData.mExpanded) {
            this.mCallback.onBackPressed();
        }
    }

    @Override // com.android.wm.shell.taskview.TaskView.Listener
    public final void onInitialized() {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            boolean z = this.mDestroyed;
            boolean z2 = this.mInitialized;
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 668755647009536824L, 15, Boolean.valueOf(z), Boolean.valueOf(z2), String.valueOf(getBubbleKey()));
        }
        if (this.mDestroyed || this.mInitialized) {
            return;
        }
        final ActivityOptions makeCustomAnimation = ActivityOptions.makeCustomAnimation(this.mContext, 0, 0);
        final Rect rect = new Rect();
        this.mTaskView.getBoundsOnScreen(rect);
        this.mParentView.post(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleTaskViewListener$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                boolean z3;
                BubbleTaskViewListener bubbleTaskViewListener = BubbleTaskViewListener.this;
                ActivityOptions activityOptions = makeCustomAnimation;
                Rect rect2 = rect;
                bubbleTaskViewListener.getClass();
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, 8281022114795829194L, 0, String.valueOf(bubbleTaskViewListener.getBubbleKey()));
                }
                try {
                    activityOptions.setTaskAlwaysOnTop(true);
                    activityOptions.setPendingIntentBackgroundActivityStartMode(3);
                    if (bubbleTaskViewListener.mBubble.hasMetadataShortcutId()) {
                        z3 = true;
                    } else {
                        Bubble.BubbleType bubbleType = bubbleTaskViewListener.mBubble.mType;
                        Bubble.BubbleType bubbleType2 = Bubble.BubbleType.TYPE_CHAT;
                        z3 = false;
                    }
                    Bubble bubble = bubbleTaskViewListener.mBubble;
                    BubbleTransitions.BubbleTransition bubbleTransition = bubble.mPreparingTransition;
                    if (bubbleTransition != null) {
                        bubbleTransition.surfaceCreated();
                    } else {
                        if (!(bubble.mType == Bubble.BubbleType.TYPE_APP) && !bubble.isNote()) {
                            if (z3) {
                                if (bubbleTaskViewListener.mBubble.isChat()) {
                                    activityOptions.setLaunchedFromBubble(true);
                                    activityOptions.setApplyActivityFlagsForBubbles(true);
                                } else {
                                    activityOptions.setApplyMultipleTaskFlagForShortcut(true);
                                }
                                TaskView taskView = bubbleTaskViewListener.mTaskView;
                                taskView.mTaskViewController.startShortcutActivity(taskView.mTaskViewTaskController, bubbleTaskViewListener.mBubble.mShortcutInfo, activityOptions, rect2);
                            } else {
                                activityOptions.setLaunchedFromBubble(true);
                                Bubble bubble2 = bubbleTaskViewListener.mBubble;
                                if (bubble2 != null) {
                                    bubble2.mPendingIntentActive = true;
                                }
                                Intent intent = new Intent();
                                intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME);
                                intent.addFlags(134217728);
                                bubbleTaskViewListener.mTaskView.startActivity(bubbleTaskViewListener.mPendingIntent, intent, activityOptions, rect2);
                            }
                        }
                        Context createContextAsUser = bubbleTaskViewListener.mContext.createContextAsUser(bubbleTaskViewListener.mBubble.mUser, 4);
                        Intent intent2 = new Intent();
                        Bubble bubble3 = bubbleTaskViewListener.mBubble;
                        PendingIntent pendingIntent = bubble3.mPendingIntent;
                        if (pendingIntent == null) {
                            pendingIntent = PendingIntent.getActivity(createContextAsUser, 0, bubble3.mIntent, 167772160, null);
                        }
                        bubbleTaskViewListener.mTaskView.startActivity(pendingIntent, intent2, activityOptions, rect2);
                    }
                } catch (RuntimeException e) {
                    Log.w("BubbleTaskViewListener", "Exception while displaying bubble: " + bubbleTaskViewListener.getBubbleKey() + ", " + e.getMessage() + "; removing bubble");
                    ((BubbleExpandedViewManager$Companion$fromBubbleController$1) bubbleTaskViewListener.mExpandedViewManager).$controller.removeBubble(10, bubbleTaskViewListener.getBubbleKey());
                }
                bubbleTaskViewListener.mInitialized = true;
            }
        });
    }

    @Override // com.android.wm.shell.taskview.TaskView.Listener
    public final void onReleased() {
        this.mDestroyed = true;
    }

    @Override // com.android.wm.shell.taskview.TaskView.Listener
    public final void onTaskCreated(int i, ComponentName componentName) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -7720174569596046123L, 1, Long.valueOf(i), String.valueOf(getBubbleKey()));
        }
        this.mTaskId = i;
        Bubble bubble = this.mBubble;
        if (bubble != null && bubble.isNote()) {
            String str = this.mBubble.mKey;
            int i2 = this.mTaskId;
            BubbleController.BubblesImpl.CachedState cachedState = ((BubbleExpandedViewManager$Companion$fromBubbleController$1) this.mExpandedViewManager).$controller.mImpl.mCachedState;
            synchronized (cachedState) {
                cachedState.mNoteBubbleTaskIds.put(str, Integer.valueOf(i2));
            }
        }
        this.mCallback.onTaskCreated();
    }

    @Override // com.android.wm.shell.taskview.TaskView.Listener
    public final void onTaskRemovalStarted(int i) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -4366049516418813198L, 1, Long.valueOf(i), String.valueOf(getBubbleKey()));
        }
        Bubble bubble = this.mBubble;
        if (bubble != null) {
            ((BubbleExpandedViewManager$Companion$fromBubbleController$1) this.mExpandedViewManager).$controller.removeBubble(3, bubble.mKey);
        }
        TaskView taskView = this.mTaskView;
        if (taskView != null) {
            taskView.getHolder().removeCallback(taskView);
            taskView.mTaskViewTaskController.performRelease();
            ((ViewGroup) this.mParentView).removeView(this.mTaskView);
            this.mTaskView = null;
        }
        this.mCallback.onTaskRemovalStarted();
    }

    @Override // com.android.wm.shell.taskview.TaskView.Listener
    public final void onTaskVisibilityChanged(int i, boolean z) {
        this.mCallback.onContentVisibilityChanged(z);
    }

    public final boolean setBubble(Bubble bubble) {
        Bubble bubble2 = this.mBubble;
        boolean z = true;
        if (bubble2 != null) {
            if (((bubble2 == null || this.mPendingIntent == null) ? false : true) == (bubble.mPendingIntent != null)) {
                z = false;
            }
        }
        this.mBubble = bubble;
        if (z) {
            this.mPendingIntent = bubble.mPendingIntent;
        }
        return z;
    }
}
