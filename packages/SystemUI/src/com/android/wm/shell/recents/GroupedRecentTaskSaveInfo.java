package com.android.wm.shell.recents;

import android.graphics.Rect;
import com.android.wm.shell.shared.split.SplitBounds;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;
import org.json.JSONObject;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class GroupedRecentTaskSaveInfo {
    public Rect mCellBounds;
    public int mCellPosition;
    public int mCellTaskId;
    public Rect mLeftTopBounds;
    public int mLeftTopTaskId;
    public boolean mParallelMultiSplit;
    public Rect mRightBottomBounds;
    public int mRightBottomTaskId;
    public int mSplitDivision;

    public GroupedRecentTaskSaveInfo() {
        this.mLeftTopTaskId = -1;
        this.mRightBottomTaskId = -1;
        this.mCellTaskId = -1;
        this.mSplitDivision = -1;
        this.mCellPosition = 0;
        this.mParallelMultiSplit = false;
    }

    public static GroupedRecentTaskSaveInfo jsonToGroupedRecentTaskSaveInfo(JSONObject jSONObject) {
        GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = new GroupedRecentTaskSaveInfo();
        groupedRecentTaskSaveInfo.mLeftTopTaskId = jSONObject.getInt("left_top_taskid");
        groupedRecentTaskSaveInfo.mRightBottomTaskId = jSONObject.getInt("right_bottom_taskid");
        boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z) {
            groupedRecentTaskSaveInfo.mCellTaskId = jSONObject.getInt("cell_taskid");
        }
        groupedRecentTaskSaveInfo.mLeftTopBounds = new Rect(Rect.unflattenFromString(jSONObject.getString("left_top_bounds")));
        groupedRecentTaskSaveInfo.mRightBottomBounds = new Rect(Rect.unflattenFromString(jSONObject.getString("right_bottom_bounds")));
        if (z) {
            groupedRecentTaskSaveInfo.mCellBounds = new Rect(Rect.unflattenFromString(jSONObject.getString("cell_bounds")));
        }
        groupedRecentTaskSaveInfo.mSplitDivision = jSONObject.getInt("split_division");
        if (z) {
            groupedRecentTaskSaveInfo.mCellPosition = jSONObject.getInt("cell_position");
        }
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
            groupedRecentTaskSaveInfo.mParallelMultiSplit = jSONObject.getBoolean("parallel_multi_split");
        }
        return groupedRecentTaskSaveInfo;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof GroupedRecentTaskSaveInfo)) {
            return false;
        }
        GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = (GroupedRecentTaskSaveInfo) obj;
        return this.mLeftTopTaskId == groupedRecentTaskSaveInfo.mLeftTopTaskId && this.mRightBottomTaskId == groupedRecentTaskSaveInfo.mRightBottomTaskId && this.mCellTaskId == groupedRecentTaskSaveInfo.mCellTaskId && Objects.equals(this.mLeftTopBounds, groupedRecentTaskSaveInfo.mLeftTopBounds) && Objects.equals(this.mRightBottomBounds, groupedRecentTaskSaveInfo.mRightBottomBounds) && Objects.equals(this.mCellBounds, groupedRecentTaskSaveInfo.mCellBounds) && this.mSplitDivision == groupedRecentTaskSaveInfo.mSplitDivision && this.mCellPosition == groupedRecentTaskSaveInfo.mCellPosition && this.mParallelMultiSplit == groupedRecentTaskSaveInfo.mParallelMultiSplit;
    }

    public final JSONObject groupedRecentTaskSaveInfoToJSON() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("left_top_taskid", this.mLeftTopTaskId);
        jSONObject.put("right_bottom_taskid", this.mRightBottomTaskId);
        boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z) {
            jSONObject.put("cell_taskid", this.mCellTaskId);
        }
        jSONObject.put("left_top_bounds", this.mLeftTopBounds.flattenToString());
        jSONObject.put("right_bottom_bounds", this.mRightBottomBounds.flattenToString());
        if (z) {
            jSONObject.put("cell_bounds", this.mCellBounds.flattenToString());
        }
        jSONObject.put("split_division", this.mSplitDivision);
        if (z) {
            jSONObject.put("cell_position", this.mCellPosition);
        }
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
            jSONObject.put("parallel_multi_split", this.mParallelMultiSplit);
        }
        return jSONObject;
    }

    public final String toString() {
        return "leftTop: " + this.mLeftTopBounds + ", taskId: " + this.mLeftTopTaskId + "\nrightBottom: " + this.mRightBottomBounds + ", taskId: " + this.mRightBottomTaskId + "\ncell: " + this.mCellBounds + ", taskId: " + this.mCellTaskId + "\n, splitDivision: " + this.mSplitDivision + ", cellPosition: " + this.mCellPosition + ", ParallelMultiSplit: " + this.mParallelMultiSplit;
    }

    public GroupedRecentTaskSaveInfo(SplitBounds splitBounds) {
        int i;
        this.mLeftTopTaskId = -1;
        this.mRightBottomTaskId = -1;
        this.mCellTaskId = -1;
        this.mSplitDivision = -1;
        this.mCellPosition = 0;
        this.mParallelMultiSplit = false;
        this.mLeftTopTaskId = splitBounds.leftTopTaskId;
        this.mRightBottomTaskId = splitBounds.rightBottomTaskId;
        boolean z = CoreRune.MW_MULTI_SPLIT_RECENT_TASKS;
        if (z && (i = splitBounds.cellTaskId) != -1) {
            this.mCellTaskId = i;
        }
        this.mLeftTopBounds = new Rect(splitBounds.leftTopBounds);
        this.mRightBottomBounds = new Rect(splitBounds.rightBottomBounds);
        if (z) {
            Rect rect = new Rect();
            this.mCellBounds = rect;
            if (splitBounds.cellTaskId != -1) {
                rect.set(splitBounds.cellTaskBounds);
            } else {
                rect.setEmpty();
            }
        }
        this.mSplitDivision = splitBounds.appsStackedVertically ? 1 : 0;
        if (z && splitBounds.cellTaskId != -1) {
            this.mCellPosition = splitBounds.cellPosition;
        }
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
            this.mParallelMultiSplit = splitBounds.parallelMultiSplit;
        }
    }
}
