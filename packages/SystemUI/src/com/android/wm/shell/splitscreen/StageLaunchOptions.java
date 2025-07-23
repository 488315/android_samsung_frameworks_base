package com.android.wm.shell.splitscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.window.RemoteTransition;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class StageLaunchOptions {
    public final boolean mAppsStackedVertically;
    public final float mCellRatio;
    public Intent mCellStageIntent;
    public final UserHandle mCellStageUserHandle;
    public int mCellStageWindowConfigPosition;
    public final int mCellTaskId;
    public final String mLaunchFrom;
    public final int mLaunchTaskId;
    public final int mLeftTopTaskId;
    public Intent mMainStageIntent;
    public UserHandle mMainStageUserHandle;
    public final boolean mParallelMultiSplit;
    public final PendingIntent mPendingIntent;
    public RemoteTransition mRemoteTransition;
    public final int mRightBottomTaskId;
    public Intent mSideStageIntent;
    public int mSideStagePosition;
    public UserHandle mSideStageUserHandle;
    public final int mSplitCreateMode;
    public int mSplitDivision;
    public final float mStageRatio;
    public final Intent mTapIntent;
    public final int mTapTaskId;
    public final UserHandle mTapUserHandle;

    private StageLaunchOptions() {
        this.mSideStagePosition = -1;
        this.mSplitCreateMode = -1;
        this.mStageRatio = 0.5f;
        this.mCellRatio = 0.5f;
        this.mLaunchTaskId = -1;
        this.mLeftTopTaskId = -1;
        this.mRightBottomTaskId = -1;
        this.mCellTaskId = -1;
        this.mSplitDivision = -1;
        this.mCellStageWindowConfigPosition = 0;
        this.mTapTaskId = -1;
    }

    public StageLaunchOptions(Bundle bundle) {
        this.mSideStagePosition = -1;
        this.mSplitCreateMode = -1;
        this.mStageRatio = 0.5f;
        this.mCellRatio = 0.5f;
        this.mLaunchTaskId = -1;
        this.mLeftTopTaskId = -1;
        this.mRightBottomTaskId = -1;
        this.mCellTaskId = -1;
        this.mSplitDivision = -1;
        this.mCellStageWindowConfigPosition = 0;
        this.mTapTaskId = -1;
        this.mSideStagePosition = bundle.getInt("stage_position");
        this.mSplitCreateMode = bundle.getInt("split_create_mode");
        float f = bundle.getFloat("stage_ratio");
        this.mStageRatio = f;
        if (f == 0.0f) {
            this.mStageRatio = 0.5f;
        }
        this.mCellRatio = bundle.getFloat("cell_ratio");
        this.mLaunchTaskId = bundle.getInt("launch_task_id");
        this.mMainStageIntent = (Intent) bundle.getParcelable("main_stage_intent", Intent.class);
        this.mSideStageIntent = (Intent) bundle.getParcelable("side_stage_intent", Intent.class);
        this.mMainStageUserHandle = (UserHandle) bundle.getParcelable("main_stage_user_handle", UserHandle.class);
        this.mSideStageUserHandle = (UserHandle) bundle.getParcelable("side_stage_user_handle", UserHandle.class);
        this.mLeftTopTaskId = bundle.getInt("left_top_task_id");
        this.mRightBottomTaskId = bundle.getInt("right_bottom_task_id");
        this.mCellTaskId = bundle.getInt("cell_task_id");
        this.mTapTaskId = bundle.getInt("tap_task_id", -1);
        this.mTapIntent = (Intent) bundle.getParcelable("tap_intent", Intent.class);
        this.mTapUserHandle = (UserHandle) bundle.getParcelable("tap_user_handle", UserHandle.class);
        this.mCellStageIntent = (Intent) bundle.getParcelable("cell_stage_intent", Intent.class);
        this.mCellStageUserHandle = (UserHandle) bundle.getParcelable("cell_stage_user_handle", UserHandle.class);
        this.mAppsStackedVertically = bundle.getBoolean("grouped_recent_vertically");
        bundle.getInt("change_app_stage_type");
        this.mCellStageWindowConfigPosition = bundle.getInt("cell_stage_position");
        this.mLaunchFrom = bundle.getString("launch_from");
        this.mSplitDivision = bundle.getInt("split_division");
        this.mPendingIntent = (PendingIntent) bundle.getParcelable("pending_intent", PendingIntent.class);
        this.mRemoteTransition = (RemoteTransition) bundle.getParcelable("remote_transition", RemoteTransition.class);
        this.mParallelMultiSplit = bundle.getBoolean("parallel_multi_split");
    }
}
