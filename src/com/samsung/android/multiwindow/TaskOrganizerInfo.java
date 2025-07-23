package com.samsung.android.multiwindow;

import android.app.ActivityTaskManager;
import android.content.Intent;
import android.os.Bundle;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes6.dex */
public class TaskOrganizerInfo {
    private static final String KEY_ASSISTANT_ACTIVITY_INTENT = "assistant_activity_intent";
    private static final String KEY_CHANGE_SPLIT_LAYOUT_FOR_LAUNCH_ADJACENT = "change_split_layout_for_launch_adjacent";
    private static final String KEY_CHANGE_TO_HORIZONTAL_SPLIT_LAYOUT = "change_to_horizontal_split_layout";
    private static final String KEY_DEFER_SPLIT_ROTATION_IN_PORT = "defer_split_rotation_in_port";
    private static final String KEY_EXIT_SPLIT_SCREEN_STAGE_TYPE = "exit_split_screen_stage_type";
    private static final String KEY_EXIT_SPLIT_SCREEN_TOP_TASK_ID = "exit_split_screen_top_task_id";
    private static final String KEY_EXIT_SPLIT_TO_FREEFORM_TASK_ID = "split_to_freeform_task_id";
    private static final String KEY_REQUESTED_SPLIT_RATIO = "requested_split_ratio";
    private static final String KEY_SPLIT_FEASIBLE_MODE = "split_feasible_mode";
    private static final String KEY_SPLIT_SCREEN_CREATE_MODE = "split_screen_create_mode";
    private Intent mAssistantActivityIntent;
    private boolean mChangeSplitLayoutForLaunchAdjacent;
    private boolean mChangeToHorizontalSplitLayout;
    private boolean mDeferSplitRotationInPort;
    private int mExitSplitScreenStageType;
    private int mExitSplitScreenTopTaskId;
    private float mRequestedSplitRatio;
    private int mSplitFeasibleMode;
    private int mSplitScreenCreateMode;
    private int mSplitToFreeformTaskId;

    public TaskOrganizerInfo() {
        this.mChangeToHorizontalSplitLayout = false;
        this.mSplitScreenCreateMode = -1;
        this.mExitSplitScreenTopTaskId = -1;
        this.mSplitToFreeformTaskId = -1;
        this.mExitSplitScreenStageType = 0;
        this.mAssistantActivityIntent = null;
        this.mRequestedSplitRatio = 0.0f;
        this.mDeferSplitRotationInPort = false;
        this.mSplitFeasibleMode = -1;
    }

    private TaskOrganizerInfo(Bundle bundle) {
        this.mChangeToHorizontalSplitLayout = false;
        this.mSplitScreenCreateMode = -1;
        this.mExitSplitScreenTopTaskId = -1;
        this.mSplitToFreeformTaskId = -1;
        this.mExitSplitScreenStageType = 0;
        this.mAssistantActivityIntent = null;
        this.mRequestedSplitRatio = 0.0f;
        this.mDeferSplitRotationInPort = false;
        this.mSplitFeasibleMode = -1;
        if (bundle == null) {
            return;
        }
        bundle.setDefusable(true);
        this.mChangeToHorizontalSplitLayout = bundle.getBoolean(KEY_CHANGE_TO_HORIZONTAL_SPLIT_LAYOUT, false);
        this.mSplitScreenCreateMode = bundle.getInt(KEY_SPLIT_SCREEN_CREATE_MODE, -1);
        this.mChangeSplitLayoutForLaunchAdjacent = bundle.getBoolean(KEY_CHANGE_SPLIT_LAYOUT_FOR_LAUNCH_ADJACENT, false);
        this.mExitSplitScreenTopTaskId = bundle.getInt(KEY_EXIT_SPLIT_SCREEN_TOP_TASK_ID, -1);
        this.mSplitToFreeformTaskId = bundle.getInt(KEY_EXIT_SPLIT_TO_FREEFORM_TASK_ID, -1);
        this.mExitSplitScreenStageType = bundle.getInt(KEY_EXIT_SPLIT_SCREEN_STAGE_TYPE, 0);
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            this.mSplitFeasibleMode = bundle.getInt(KEY_SPLIT_FEASIBLE_MODE, -1);
        }
        this.mAssistantActivityIntent = (Intent) bundle.getParcelable(KEY_ASSISTANT_ACTIVITY_INTENT, Intent.class);
        this.mRequestedSplitRatio = bundle.getFloat(KEY_REQUESTED_SPLIT_RATIO, 0.0f);
        this.mDeferSplitRotationInPort = bundle.getBoolean(KEY_DEFER_SPLIT_ROTATION_IN_PORT, false);
    }

    public static TaskOrganizerInfo fromBundle(Bundle bundle) {
        return new TaskOrganizerInfo(bundle);
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        if (this.mChangeToHorizontalSplitLayout) {
            bundle.putBoolean(KEY_CHANGE_TO_HORIZONTAL_SPLIT_LAYOUT, true);
        }
        bundle.putInt(KEY_SPLIT_SCREEN_CREATE_MODE, this.mSplitScreenCreateMode);
        bundle.putBoolean(KEY_CHANGE_SPLIT_LAYOUT_FOR_LAUNCH_ADJACENT, this.mChangeSplitLayoutForLaunchAdjacent);
        bundle.putInt(KEY_EXIT_SPLIT_SCREEN_TOP_TASK_ID, this.mExitSplitScreenTopTaskId);
        bundle.putInt(KEY_EXIT_SPLIT_TO_FREEFORM_TASK_ID, this.mSplitToFreeformTaskId);
        bundle.putInt(KEY_EXIT_SPLIT_SCREEN_STAGE_TYPE, this.mExitSplitScreenStageType);
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            bundle.putInt(KEY_SPLIT_FEASIBLE_MODE, this.mSplitFeasibleMode);
        }
        bundle.putParcelable(KEY_ASSISTANT_ACTIVITY_INTENT, this.mAssistantActivityIntent);
        bundle.putFloat(KEY_REQUESTED_SPLIT_RATIO, this.mRequestedSplitRatio);
        bundle.putBoolean(KEY_DEFER_SPLIT_ROTATION_IN_PORT, this.mDeferSplitRotationInPort);
        return bundle;
    }

    public void changeToHorizontalSplitLayout() {
        this.mChangeToHorizontalSplitLayout = true;
    }

    public boolean isChangeToHorizontalSplitLayout() {
        return this.mChangeToHorizontalSplitLayout;
    }

    public void setSplitScreenCreateModeForLaunchAdjacent(int i) {
        this.mSplitScreenCreateMode = i;
        this.mChangeSplitLayoutForLaunchAdjacent = true;
    }

    public int getSplitScreenCreateMode() {
        return this.mSplitScreenCreateMode;
    }

    public boolean isChangeSplitLayoutForLaunchAdjacent() {
        return this.mChangeSplitLayoutForLaunchAdjacent;
    }

    public int getExitSplitScreenTopTaskId() {
        return this.mExitSplitScreenTopTaskId;
    }

    public void setExitSplitScreenTopTaskId(int i) {
        this.mExitSplitScreenTopTaskId = i;
    }

    public int getSplitToFreeformTaskId() {
        return this.mSplitToFreeformTaskId;
    }

    public void setSplitToFreeformTaskId(int i) {
        this.mSplitToFreeformTaskId = i;
    }

    public int getExitSplitScreenStageType() {
        return this.mExitSplitScreenStageType;
    }

    public void setExitSplitScreenStageType(int i) {
        this.mExitSplitScreenStageType = i;
    }

    public int getSplitFeasibleMode() {
        return this.mSplitFeasibleMode;
    }

    public void setSplitFeasibleMode(int i) {
        this.mSplitFeasibleMode = i;
    }

    public void setAssistantActivityToSplit(Intent intent, float f, boolean z) {
        this.mAssistantActivityIntent = intent;
        this.mRequestedSplitRatio = f;
        this.mDeferSplitRotationInPort = z;
    }

    public Intent getAssistantActivityIntent() {
        return this.mAssistantActivityIntent;
    }

    public float getRequestedSplitRatio() {
        return this.mRequestedSplitRatio;
    }

    public boolean getDeferSplitRotationInPort() {
        return this.mDeferSplitRotationInPort;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("TaskOrganizerInfo{");
        if (this.mSplitScreenCreateMode != -1) {
            sb.append(" mSplitScreenCreateMode=");
            sb.append(ActivityTaskManager.splitCreateModeToString(this.mSplitScreenCreateMode));
        }
        if (this.mChangeSplitLayoutForLaunchAdjacent) {
            sb.append(" mChangeSplitLayoutForLaunchAdjacent=true");
        }
        if (this.mExitSplitScreenTopTaskId != -1) {
            sb.append(" mExitSplitScreenTopTaskId=");
            sb.append(this.mExitSplitScreenTopTaskId);
        }
        if (this.mSplitToFreeformTaskId != -1) {
            sb.append(" mSplitToFreeformTaskId=");
            sb.append(this.mSplitToFreeformTaskId);
        }
        if (this.mExitSplitScreenStageType != 0) {
            sb.append(" mExitSplitScreenStageType=");
            sb.append(this.mExitSplitScreenStageType);
        }
        sb.append(" mChangeToHorizontalSplitLayout=");
        sb.append(this.mChangeToHorizontalSplitLayout);
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && this.mSplitFeasibleMode != -1) {
            sb.append(" mSplitFeasibleMode=");
            sb.append(this.mSplitFeasibleMode);
        }
        if (this.mAssistantActivityIntent != null) {
            sb.append(" mAssistantActivityIntent=");
            sb.append(this.mAssistantActivityIntent.getComponent());
        }
        if (this.mRequestedSplitRatio != 0.0f) {
            sb.append(" mRequestedSplitRatio=");
            sb.append(this.mRequestedSplitRatio);
        }
        if (this.mDeferSplitRotationInPort) {
            sb.append(" mDeferSplitRotation=");
            sb.append(this.mDeferSplitRotationInPort);
        }
        sb.append("}");
        return sb.toString();
    }
}
