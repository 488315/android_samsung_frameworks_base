package com.android.server.wm;

import android.hardware.HardwareBuffer;
import android.util.ArraySet;
import android.window.TaskSnapshot;
import java.io.File;
import java.util.Arrays;

/* loaded from: classes3.dex */
public class TaskSnapshotPersister extends BaseAppSnapshotPersister {
  public final ArraySet mPersistedTaskIdsSinceLastRemoveObsolete;

  public TaskSnapshotPersister(
      SnapshotPersistQueue snapshotPersistQueue,
      BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
    super(snapshotPersistQueue, persistInfoProvider);
    this.mPersistedTaskIdsSinceLastRemoveObsolete = new ArraySet();
  }

  @Override // com.android.server.wm.BaseAppSnapshotPersister
  public void persistSnapshot(int i, int i2, TaskSnapshot taskSnapshot) {
    synchronized (this.mLock) {
      this.mPersistedTaskIdsSinceLastRemoveObsolete.add(Integer.valueOf(i));
      super.persistSnapshot(i, i2, taskSnapshot);
    }
  }

  public void onTaskRemovedFromRecents(int i, int i2) {
    synchronized (this.mLock) {
      this.mPersistedTaskIdsSinceLastRemoveObsolete.remove(Integer.valueOf(i));
      super.removeSnap(i, i2);
    }
  }

  public void onTaskRemovedFromRecentsBuffer(HardwareBuffer hardwareBuffer) {
    synchronized (this.mLock) {
      super.closeBuffer(hardwareBuffer);
    }
  }

  public void removeObsoleteFiles(ArraySet arraySet, int[] iArr) {
    synchronized (this.mLock) {
      this.mPersistedTaskIdsSinceLastRemoveObsolete.clear();
      this.mSnapshotPersistQueue.sendToQueueLocked(
          new RemoveObsoleteFilesQueueItem(arraySet, iArr, this.mPersistInfoProvider));
    }
  }

  class RemoveObsoleteFilesQueueItem extends SnapshotPersistQueue.WriteQueueItem {
    public final ArraySet mPersistentTaskIds;
    public final int[] mRunningUserIds;

    public RemoveObsoleteFilesQueueItem(
        ArraySet arraySet,
        int[] iArr,
        BaseAppSnapshotPersister.PersistInfoProvider persistInfoProvider) {
      super(persistInfoProvider);
      this.mPersistentTaskIds = new ArraySet(arraySet);
      this.mRunningUserIds = Arrays.copyOf(iArr, iArr.length);
    }

    @Override // com.android.server.wm.SnapshotPersistQueue.WriteQueueItem
    public void write() {
      ArraySet arraySet;
      synchronized (TaskSnapshotPersister.this.mLock) {
        arraySet =
            new ArraySet(TaskSnapshotPersister.this.mPersistedTaskIdsSinceLastRemoveObsolete);
      }
      for (int i : this.mRunningUserIds) {
        File directory = this.mPersistInfoProvider.getDirectory(i);
        String[] list = directory.list();
        if (list != null) {
          for (String str : list) {
            int taskId = getTaskId(str);
            if (!this.mPersistentTaskIds.contains(Integer.valueOf(taskId))
                && !arraySet.contains(Integer.valueOf(taskId))) {
              new File(directory, str).delete();
            }
          }
        }
      }
    }

    public int getTaskId(String str) {
      int lastIndexOf;
      if ((!str.endsWith(".proto") && !str.endsWith(".jpg"))
          || (lastIndexOf = str.lastIndexOf(46)) == -1) {
        return -1;
      }
      String substring = str.substring(0, lastIndexOf);
      if (substring.endsWith("_reduced")) {
        substring = substring.substring(0, substring.length() - 8);
      }
      try {
        return Integer.parseInt(substring);
      } catch (NumberFormatException unused) {
        return -1;
      }
    }
  }
}
