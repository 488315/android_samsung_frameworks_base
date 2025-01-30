package com.android.wm.shell.recents;

import android.graphics.Rect;
import com.android.wm.shell.util.SplitBounds;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GroupedRecentTaskSaveInfo {
    public Rect mCellBounds;
    public int mCellPosition;
    public int mCellTaskId;
    public Rect mLeftTopBounds;
    public int mLeftTopTaskId;
    public Rect mRightBottomBounds;
    public int mRightBottomTaskId;
    public int mSplitDivision;

    public GroupedRecentTaskSaveInfo() {
        this.mLeftTopTaskId = -1;
        this.mRightBottomTaskId = -1;
        this.mCellTaskId = -1;
        this.mSplitDivision = -1;
        this.mCellPosition = 0;
    }

    public static GroupedRecentTaskSaveInfo jsonToGroupedRecentTaskSaveInfo(JSONObject jSONObject) {
        GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = new GroupedRecentTaskSaveInfo();
        groupedRecentTaskSaveInfo.mLeftTopTaskId = jSONObject.getInt("left_top_taskid");
        groupedRecentTaskSaveInfo.mRightBottomTaskId = jSONObject.getInt("right_bottom_taskid");
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            groupedRecentTaskSaveInfo.mCellTaskId = jSONObject.getInt("cell_taskid");
        }
        groupedRecentTaskSaveInfo.mLeftTopBounds = new Rect(Rect.unflattenFromString(jSONObject.getString("left_top_bounds")));
        groupedRecentTaskSaveInfo.mRightBottomBounds = new Rect(Rect.unflattenFromString(jSONObject.getString("right_bottom_bounds")));
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            groupedRecentTaskSaveInfo.mCellBounds = new Rect(Rect.unflattenFromString(jSONObject.getString("cell_bounds")));
        }
        groupedRecentTaskSaveInfo.mSplitDivision = jSONObject.getInt("split_division");
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            groupedRecentTaskSaveInfo.mCellPosition = jSONObject.getInt("cell_position");
        }
        return groupedRecentTaskSaveInfo;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof GroupedRecentTaskSaveInfo)) {
            return false;
        }
        GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo = (GroupedRecentTaskSaveInfo) obj;
        return this.mLeftTopTaskId == groupedRecentTaskSaveInfo.mLeftTopTaskId && this.mRightBottomTaskId == groupedRecentTaskSaveInfo.mRightBottomTaskId && this.mCellTaskId == groupedRecentTaskSaveInfo.mCellTaskId && Objects.equals(this.mLeftTopBounds, groupedRecentTaskSaveInfo.mLeftTopBounds) && Objects.equals(this.mRightBottomBounds, groupedRecentTaskSaveInfo.mRightBottomBounds) && Objects.equals(this.mCellBounds, groupedRecentTaskSaveInfo.mCellBounds);
    }

    public final JSONObject groupedRecentTaskSaveInfoToJSON() {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("left_top_taskid", this.mLeftTopTaskId);
        jSONObject.put("right_bottom_taskid", this.mRightBottomTaskId);
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            jSONObject.put("cell_taskid", this.mCellTaskId);
        }
        jSONObject.put("left_top_bounds", this.mLeftTopBounds.flattenToString());
        jSONObject.put("right_bottom_bounds", this.mRightBottomBounds.flattenToString());
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            jSONObject.put("cell_bounds", this.mCellBounds.flattenToString());
        }
        jSONObject.put("split_division", this.mSplitDivision);
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            jSONObject.put("cell_position", this.mCellPosition);
        }
        return jSONObject;
    }

    public final String toString() {
        return "leftTop: " + this.mLeftTopBounds + ", taskId: " + this.mLeftTopTaskId + "\nrightBottom: " + this.mRightBottomBounds + ", taskId: " + this.mRightBottomTaskId + "\ncell: " + this.mCellBounds + ", taskId: " + this.mCellTaskId;
    }

    public GroupedRecentTaskSaveInfo(SplitBounds splitBounds) {
        int i;
        this.mLeftTopTaskId = -1;
        this.mRightBottomTaskId = -1;
        this.mCellTaskId = -1;
        this.mSplitDivision = -1;
        this.mCellPosition = 0;
        this.mLeftTopTaskId = splitBounds.leftTopTaskId;
        this.mRightBottomTaskId = splitBounds.rightBottomTaskId;
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && (i = splitBounds.cellTaskId) != -1) {
            this.mCellTaskId = i;
        }
        this.mLeftTopBounds = new Rect(splitBounds.leftTopBounds);
        this.mRightBottomBounds = new Rect(splitBounds.rightBottomBounds);
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS) {
            Rect rect = new Rect();
            this.mCellBounds = rect;
            if (splitBounds.cellTaskId != -1) {
                rect.set(splitBounds.cellTaskBounds);
            } else {
                rect.setEmpty();
            }
        }
        this.mSplitDivision = splitBounds.appsStackedVertically ? 1 : 0;
        if (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || splitBounds.cellTaskId == -1) {
            return;
        }
        this.mCellPosition = splitBounds.cellPosition;
    }
}
