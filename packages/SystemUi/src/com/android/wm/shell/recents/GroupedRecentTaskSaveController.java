package com.android.wm.shell.recents;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Slog;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GroupedRecentTaskSaveController {
    public final File mGroupedRecentSaveFile;
    public final Handler mHandler;
    public final Map mGroupedRecentTaskSaveMap = new HashMap();
    public final GroupedRecentTaskSaveController$$ExternalSyntheticLambda0 mSaveGroupedRecentTasks = new Runnable() { // from class: com.android.wm.shell.recents.GroupedRecentTaskSaveController$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            GroupedRecentTaskSaveController groupedRecentTaskSaveController = GroupedRecentTaskSaveController.this;
            groupedRecentTaskSaveController.getClass();
            try {
                FileWriter fileWriter = new FileWriter(groupedRecentTaskSaveController.mGroupedRecentSaveFile, false);
                try {
                    JSONArray jSONArray = new JSONArray();
                    synchronized (groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap) {
                        Iterator it = ((HashMap) groupedRecentTaskSaveController.mGroupedRecentTaskSaveMap).entrySet().iterator();
                        while (it.hasNext()) {
                            jSONArray.put(((GroupedRecentTaskSaveInfo) ((Map.Entry) it.next()).getValue()).groupedRecentTaskSaveInfoToJSON());
                        }
                    }
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("grouped_recent_tasks", jSONArray);
                    fileWriter.write(jSONObject.toString());
                    fileWriter.flush();
                    fileWriter.close();
                } catch (Throwable th) {
                    try {
                        fileWriter.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | JSONException e) {
                Slog.e("GroupedRecentTaskSaveInfo", "fail to save grouped recent tasks" + e);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.recents.GroupedRecentTaskSaveController$$ExternalSyntheticLambda0] */
    public GroupedRecentTaskSaveController(Context context) {
        HandlerThread handlerThread = new HandlerThread("recenttasks");
        handlerThread.start();
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mGroupedRecentSaveFile = new File(context.getFilesDir(), "grouped_recents.json");
    }

    public static int getTaskIdKey(int i, int i2) {
        return (Integer.toString(i) + "," + Integer.toString(i2)).hashCode();
    }

    public final boolean addGroupedRecentTaskSaveInfo(GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo) {
        int i;
        int taskIdKey = (!CoreRune.MW_MULTI_SPLIT_RECENT_TASKS || (i = groupedRecentTaskSaveInfo.mCellTaskId) == -1) ? getTaskIdKey(groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId) : getTaskIdKey(groupedRecentTaskSaveInfo.mLeftTopTaskId, groupedRecentTaskSaveInfo.mRightBottomTaskId, i);
        synchronized (this.mGroupedRecentTaskSaveMap) {
            GroupedRecentTaskSaveInfo groupedRecentTaskSaveInfo2 = (GroupedRecentTaskSaveInfo) ((HashMap) this.mGroupedRecentTaskSaveMap).getOrDefault(Integer.valueOf(taskIdKey), null);
            if (groupedRecentTaskSaveInfo2 != null && groupedRecentTaskSaveInfo2.equals(groupedRecentTaskSaveInfo)) {
                return false;
            }
            ((HashMap) this.mGroupedRecentTaskSaveMap).remove(Integer.valueOf(taskIdKey));
            ((HashMap) this.mGroupedRecentTaskSaveMap).put(Integer.valueOf(taskIdKey), groupedRecentTaskSaveInfo);
            return true;
        }
    }

    public final int getPossibleRemoveTaskIdKey(int i, int i2, int i3) {
        synchronized (this.mGroupedRecentTaskSaveMap) {
            int taskIdKey = getTaskIdKey(i, i2, i3);
            if (((HashMap) this.mGroupedRecentTaskSaveMap).containsKey(Integer.valueOf(taskIdKey))) {
                return taskIdKey;
            }
            int taskIdKey2 = getTaskIdKey(i2, i, i3);
            if (((HashMap) this.mGroupedRecentTaskSaveMap).containsKey(Integer.valueOf(taskIdKey2))) {
                return taskIdKey2;
            }
            return -1;
        }
    }

    public final void scheduleSaveGroupedRecentTasks() {
        Handler handler = this.mHandler;
        GroupedRecentTaskSaveController$$ExternalSyntheticLambda0 groupedRecentTaskSaveController$$ExternalSyntheticLambda0 = this.mSaveGroupedRecentTasks;
        handler.removeCallbacks(groupedRecentTaskSaveController$$ExternalSyntheticLambda0);
        handler.postDelayed(groupedRecentTaskSaveController$$ExternalSyntheticLambda0, 3000L);
    }

    public static int getTaskIdKey(int i, int i2, int i3) {
        return (Integer.toString(i) + "," + Integer.toString(i2) + "," + Integer.toString(i3)).hashCode();
    }
}
