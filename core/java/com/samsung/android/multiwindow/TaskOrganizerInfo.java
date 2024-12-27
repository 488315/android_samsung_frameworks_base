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

    private TaskOrganizerInfo(Bundle b) {
        this.mChangeToHorizontalSplitLayout = false;
        this.mSplitScreenCreateMode = -1;
        this.mExitSplitScreenTopTaskId = -1;
        this.mSplitToFreeformTaskId = -1;
        this.mExitSplitScreenStageType = 0;
        this.mAssistantActivityIntent = null;
        this.mRequestedSplitRatio = 0.0f;
        this.mDeferSplitRotationInPort = false;
        this.mSplitFeasibleMode = -1;
        if (b == null) {
            return;
        }
        b.setDefusable(true);
        this.mChangeToHorizontalSplitLayout = b.getBoolean(KEY_CHANGE_TO_HORIZONTAL_SPLIT_LAYOUT, false);
        this.mSplitScreenCreateMode = b.getInt(KEY_SPLIT_SCREEN_CREATE_MODE, -1);
        this.mChangeSplitLayoutForLaunchAdjacent = b.getBoolean(KEY_CHANGE_SPLIT_LAYOUT_FOR_LAUNCH_ADJACENT, false);
        this.mExitSplitScreenTopTaskId = b.getInt(KEY_EXIT_SPLIT_SCREEN_TOP_TASK_ID, -1);
        this.mSplitToFreeformTaskId = b.getInt(KEY_EXIT_SPLIT_TO_FREEFORM_TASK_ID, -1);
        this.mExitSplitScreenStageType = b.getInt(KEY_EXIT_SPLIT_SCREEN_STAGE_TYPE, 0);
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            this.mSplitFeasibleMode = b.getInt(KEY_SPLIT_FEASIBLE_MODE, -1);
        }
    }

    public static TaskOrganizerInfo fromBundle(Bundle b) {
        return new TaskOrganizerInfo(b);
    }

    public Bundle toBundle() {
        Bundle b = new Bundle();
        if (this.mChangeToHorizontalSplitLayout) {
            b.putBoolean(KEY_CHANGE_TO_HORIZONTAL_SPLIT_LAYOUT, true);
        }
        b.putInt(KEY_SPLIT_SCREEN_CREATE_MODE, this.mSplitScreenCreateMode);
        b.putBoolean(KEY_CHANGE_SPLIT_LAYOUT_FOR_LAUNCH_ADJACENT, this.mChangeSplitLayoutForLaunchAdjacent);
        b.putInt(KEY_EXIT_SPLIT_SCREEN_TOP_TASK_ID, this.mExitSplitScreenTopTaskId);
        b.putInt(KEY_EXIT_SPLIT_TO_FREEFORM_TASK_ID, this.mSplitToFreeformTaskId);
        b.putInt(KEY_EXIT_SPLIT_SCREEN_STAGE_TYPE, this.mExitSplitScreenStageType);
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE) {
            b.putInt(KEY_SPLIT_FEASIBLE_MODE, this.mSplitFeasibleMode);
        }
        return b;
    }

    public void changeToHorizontalSplitLayout() {
        this.mChangeToHorizontalSplitLayout = true;
    }

    public boolean isChangeToHorizontalSplitLayout() {
        return this.mChangeToHorizontalSplitLayout;
    }

    public void setSplitScreenCreateModeForLaunchAdjacent(int splitScreenCreateMode) {
        this.mSplitScreenCreateMode = splitScreenCreateMode;
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

    public void setExitSplitScreenTopTaskId(int taskId) {
        this.mExitSplitScreenTopTaskId = taskId;
    }

    public int getSplitToFreeformTaskId() {
        return this.mSplitToFreeformTaskId;
    }

    public void setSplitToFreeformTaskId(int taskId) {
        this.mSplitToFreeformTaskId = taskId;
    }

    public int getExitSplitScreenStageType() {
        return this.mExitSplitScreenStageType;
    }

    public void setExitSplitScreenStageType(int type) {
        this.mExitSplitScreenStageType = type;
    }

    public int getSplitFeasibleMode() {
        return this.mSplitFeasibleMode;
    }

    public void setSplitFeasibleMode(int splitFeasibleMode) {
        this.mSplitFeasibleMode = splitFeasibleMode;
    }

    public void setAssistantActivityToSplit(Intent intent, float splitRatio, boolean deferSplitRotationInPort) {
        this.mAssistantActivityIntent = intent;
        this.mRequestedSplitRatio = splitRatio;
        this.mDeferSplitRotationInPort = deferSplitRotationInPort;
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
        StringBuilder sb = new StringBuilder();
        sb.append("TaskOrganizerInfo{");
        if (this.mSplitScreenCreateMode != -1) {
            sb.append(" mSplitScreenCreateMode=");
            sb.append(ActivityTaskManager.splitCreateModeToString(this.mSplitScreenCreateMode));
        }
        if (this.mChangeSplitLayoutForLaunchAdjacent) {
            sb.append(" mChangeSplitLayoutForLaunchAdjacent=true");
        }
        if (this.mExitSplitScreenTopTaskId != -1) {
            sb.append(" mExitSplitScreenTopTaskId=").append(this.mExitSplitScreenTopTaskId);
        }
        if (this.mSplitToFreeformTaskId != -1) {
            sb.append(" mSplitToFreeformTaskId=").append(this.mSplitToFreeformTaskId);
        }
        if (this.mExitSplitScreenStageType != 0) {
            sb.append(" mExitSplitScreenStageType=").append(this.mExitSplitScreenStageType);
        }
        sb.append(" mChangeToHorizontalSplitLayout=").append(this.mChangeToHorizontalSplitLayout);
        if (CoreRune.MW_MULTI_SPLIT_ENSURE_APP_SIZE && this.mSplitFeasibleMode != -1) {
            sb.append(" mSplitFeasibleMode=").append(this.mSplitFeasibleMode);
        }
        sb.append("}");
        return sb.toString();
    }
}
