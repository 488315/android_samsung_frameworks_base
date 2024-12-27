package com.android.server.wm;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.os.FileUtils;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.Xml;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.function.pooled.PooledPredicate;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.wm.PersisterQueue;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Predicate;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TaskPersister implements PersisterQueue.Listener {
    public final Object mIoLock;
    public final PersisterQueue mPersisterQueue;
    public final RecentTasks mRecentTasks;
    public final ActivityTaskManagerService mService;
    public final File mTaskIdsDir;
    public final SparseArray mTaskIdsInFile;
    public final ActivityTaskSupervisor mTaskSupervisor;
    public final ArraySet mTmpTaskIds;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.TaskPersister$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            long j = ((Task) obj2).mLastTimeMoved - ((Task) obj).mLastTimeMoved;
            if (j < 0) {
                return -1;
            }
            return j > 0 ? 1 : 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DedicatedProcessWriteQueueItem implements PersisterQueue.WriteQueueItem {
        public final HashMap mProcToPkgList;
        public final ActivityTaskManagerService mService;
        public final int mUserId;

        public DedicatedProcessWriteQueueItem(HashMap hashMap, ActivityTaskManagerService activityTaskManagerService, int i) {
            this.mProcToPkgList = hashMap;
            this.mService = activityTaskManagerService;
            this.mUserId = i;
        }

        public static byte[] saveToXml(HashMap hashMap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.startTag((String) null, "dedicated_process");
            for (String str : hashMap.keySet()) {
                resolveSerializer.startTag((String) null, "task");
                resolveSerializer.attribute((String) null, "process_name", str);
                resolveSerializer.attribute((String) null, "package_name", (String) hashMap.get(str));
                resolveSerializer.endTag((String) null, "task");
            }
            resolveSerializer.endTag((String) null, "dedicated_process");
            resolveSerializer.endDocument();
            resolveSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            FileOutputStream fileOutputStream;
            byte[] bArr;
            AtomicFile atomicFile;
            HashMap hashMap = this.mProcToPkgList;
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                fileOutputStream = null;
                try {
                    try {
                        bArr = saveToXml(hashMap);
                    } catch (IOException unused) {
                        bArr = null;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (bArr == null) {
                return;
            }
            try {
                File file = new File(Environment.getDataSystemCeDirectory(this.mUserId), "dedicated_tasks");
                if (!file.isDirectory() && !file.mkdirs()) {
                    Slog.e("TaskPersister", "Failure creating tasks directory for user " + this.mUserId + ": " + file);
                    return;
                }
                AtomicFile atomicFile2 = new AtomicFile(new File(file, "dedicated_process_task.xml"));
                try {
                    fileOutputStream = atomicFile2.startWrite();
                    fileOutputStream.write(bArr);
                    atomicFile2.finishWrite(fileOutputStream);
                } catch (IOException e) {
                    atomicFile = atomicFile2;
                    e = e;
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    Slog.e("TaskPersister", "Unable to open " + atomicFile + " for persisting. " + e);
                }
            } catch (IOException e2) {
                e = e2;
                atomicFile = null;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ImageWriteQueueItem implements PersisterQueue.WriteQueueItem {
        public final String mFilePath;
        public Bitmap mImage;

        public ImageWriteQueueItem(Bitmap bitmap, String str) {
            this.mFilePath = str;
            this.mImage = bitmap;
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final boolean matches(PersisterQueue.WriteQueueItem writeQueueItem) {
            return this.mFilePath.equals(((ImageWriteQueueItem) writeQueueItem).mFilePath);
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            String str = this.mFilePath;
            File parentFile = new File(str).getParentFile();
            if (!parentFile.isDirectory() && !parentFile.mkdir()) {
                BootReceiver$$ExternalSyntheticOutline0.m("Error while creating images directory for file: ", str, "TaskPersister");
                return;
            }
            Bitmap bitmap = this.mImage;
            OutputStream outputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                    try {
                        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
                        bitmap.compress(compressFormat, 100, fileOutputStream2);
                        IoUtils.closeQuietly(fileOutputStream2);
                        outputStream = compressFormat;
                    } catch (Exception e) {
                        e = e;
                        fileOutputStream = fileOutputStream2;
                        Slog.e("TaskPersister", "saveImage: unable to save " + str, e);
                        IoUtils.closeQuietly(fileOutputStream);
                        outputStream = fileOutputStream;
                    } catch (Throwable th) {
                        th = th;
                        outputStream = fileOutputStream2;
                        IoUtils.closeQuietly(outputStream);
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }

        public final String toString() {
            return "ImageWriteQueueItem{path=" + this.mFilePath + ", image=(" + this.mImage.getWidth() + "x" + this.mImage.getHeight() + ")}";
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void updateFrom(PersisterQueue.WriteQueueItem writeQueueItem) {
            this.mImage = ((ImageWriteQueueItem) writeQueueItem).mImage;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RecentTaskFile {
        public final File mFile;
        public final int mTaskId;
        public final ByteArrayInputStream mXmlContent;

        public RecentTaskFile(File file, int i) {
            this.mTaskId = i;
            this.mFile = file;
            this.mXmlContent = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RecentTaskFiles {
        public final ArrayList mLoadedFiles;
        public final File[] mUserTaskFiles;

        public RecentTaskFiles(File[] fileArr, ArrayList arrayList) {
            this.mUserTaskFiles = fileArr;
            this.mLoadedFiles = arrayList;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TaskWriteQueueItem implements PersisterQueue.WriteQueueItem {
        public final ActivityTaskManagerService mService;
        public final Task mTask;

        public TaskWriteQueueItem(Task task, ActivityTaskManagerService activityTaskManagerService) {
            this.mTask = task;
            this.mService = activityTaskManagerService;
        }

        public static byte[] saveToXml(Task task) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            XmlSerializer resolveSerializer = Xml.resolveSerializer(byteArrayOutputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.startTag((String) null, "task");
            resolveSerializer.attributeInt((String) null, "task_id", task.mTaskId);
            ComponentName componentName = task.realActivity;
            if (componentName != null) {
                resolveSerializer.attribute((String) null, "real_activity", componentName.flattenToShortString());
            }
            resolveSerializer.attributeBoolean((String) null, "real_activity_suspended", task.realActivitySuspended);
            ComponentName componentName2 = task.origActivity;
            if (componentName2 != null) {
                resolveSerializer.attribute((String) null, "orig_activity", componentName2.flattenToShortString());
            }
            String str = task.affinity;
            if (str != null) {
                resolveSerializer.attribute((String) null, "affinity", str);
                if (!task.affinity.equals(task.rootAffinity)) {
                    String str2 = task.rootAffinity;
                    if (str2 == null) {
                        str2 = "@";
                    }
                    resolveSerializer.attribute((String) null, "root_affinity", str2);
                }
            } else {
                String str3 = task.rootAffinity;
                if (str3 != null) {
                    resolveSerializer.attribute((String) null, "root_affinity", str3);
                }
            }
            String str4 = task.mWindowLayoutAffinity;
            if (str4 != null) {
                resolveSerializer.attribute((String) null, "window_layout_affinity", str4);
            }
            resolveSerializer.attributeBoolean((String) null, "root_has_reset", task.rootWasReset);
            resolveSerializer.attributeBoolean((String) null, "auto_remove_recents", task.autoRemoveRecents);
            resolveSerializer.attributeInt((String) null, "user_id", task.mUserId);
            resolveSerializer.attributeBoolean((String) null, "user_setup_complete", task.mUserSetupComplete);
            resolveSerializer.attributeInt((String) null, "effective_uid", task.effectiveUid);
            resolveSerializer.attributeLong((String) null, "last_time_moved", task.mLastTimeMoved);
            resolveSerializer.attributeBoolean((String) null, "never_relinquish_identity", task.mNeverRelinquishIdentity);
            CharSequence charSequence = task.lastDescription;
            if (charSequence != null) {
                resolveSerializer.attribute((String) null, "last_description", charSequence.toString());
            }
            ActivityManager.TaskDescription taskDescription = task.mTaskDescription;
            if (taskDescription != null) {
                taskDescription.saveToXml(resolveSerializer);
            }
            resolveSerializer.attributeInt((String) null, "task_affiliation", task.mAffiliatedTaskId);
            resolveSerializer.attributeInt((String) null, "prev_affiliation", task.mPrevAffiliateTaskId);
            resolveSerializer.attributeInt((String) null, "next_affiliation", task.mNextAffiliateTaskId);
            resolveSerializer.attributeInt((String) null, "calling_uid", task.mCallingUid);
            String str5 = task.mCallingPackage;
            if (str5 == null) {
                str5 = "";
            }
            resolveSerializer.attribute((String) null, "calling_package", str5);
            String str6 = task.mCallingFeatureId;
            resolveSerializer.attribute((String) null, "calling_feature_id", str6 != null ? str6 : "");
            resolveSerializer.attributeInt((String) null, "resize_mode", task.mResizeMode);
            resolveSerializer.attributeBoolean((String) null, "supports_picture_in_picture", task.mSupportsPictureInPicture);
            Rect rect = task.mLastNonFullscreenBounds;
            if (rect != null) {
                resolveSerializer.attribute((String) null, "non_fullscreen_bounds", rect.flattenToString());
            }
            resolveSerializer.attributeInt((String) null, "min_width", task.mMinWidth);
            resolveSerializer.attributeInt((String) null, "min_height", task.mMinHeight);
            resolveSerializer.attributeInt((String) null, "persist_task_version", 1);
            Point point = task.mLastTaskSnapshotData.taskSize;
            if (point != null) {
                resolveSerializer.attribute((String) null, "last_snapshot_task_size", point.flattenToString());
            }
            Rect rect2 = task.mLastTaskSnapshotData.contentInsets;
            if (rect2 != null) {
                resolveSerializer.attribute((String) null, "last_snapshot_content_insets", rect2.flattenToString());
            }
            Point point2 = task.mLastTaskSnapshotData.bufferSize;
            if (point2 != null) {
                resolveSerializer.attribute((String) null, "last_snapshot_buffer_size", point2.flattenToString());
            }
            if (CoreRune.FW_DEDICATED_MEMORY) {
                String str7 = task.mHostProcessName;
                if (str7 != null) {
                    resolveSerializer.attribute((String) null, "host_process_name", str7);
                }
                boolean z = task.mDedicatedTask;
                if (z) {
                    resolveSerializer.attribute((String) null, "dedicated_task", String.valueOf(z));
                }
            }
            if (task.affinityIntent != null) {
                resolveSerializer.startTag((String) null, "affinity_intent");
                task.affinityIntent.saveToXml(resolveSerializer);
                resolveSerializer.endTag((String) null, "affinity_intent");
            }
            if (task.intent != null) {
                resolveSerializer.startTag((String) null, KnoxCustomManagerService.INTENT);
                task.intent.saveToXml(resolveSerializer);
                resolveSerializer.endTag((String) null, KnoxCustomManagerService.INTENT);
            }
            Task.sTmpException = null;
            PooledPredicate obtainPredicate = PooledLambda.obtainPredicate(new Task$$ExternalSyntheticLambda7(2), PooledLambda.__(ActivityRecord.class), task.getBottomMostActivity(), resolveSerializer);
            task.forAllActivities((Predicate) obtainPredicate);
            obtainPredicate.recycle();
            Exception exc = Task.sTmpException;
            if (exc != null) {
                throw exc;
            }
            resolveSerializer.endTag((String) null, "task");
            resolveSerializer.endDocument();
            resolveSerializer.flush();
            return byteArrayOutputStream.toByteArray();
        }

        @Override // com.android.server.wm.PersisterQueue.WriteQueueItem
        public final void process() {
            FileOutputStream fileOutputStream;
            byte[] saveToXml;
            AtomicFile atomicFile;
            Task task = this.mTask;
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    fileOutputStream = null;
                    if (task.inRecents) {
                        try {
                            saveToXml = saveToXml(task);
                        } catch (Exception unused) {
                        }
                    }
                    saveToXml = null;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            if (saveToXml == null) {
                return;
            }
            try {
                File file = new File(Environment.getDataSystemCeDirectory(task.mUserId), "recent_tasks");
                if (!file.isDirectory() && !file.mkdirs()) {
                    Slog.e("TaskPersister", "Failure creating tasks directory for user " + task.mUserId + ": " + file + " Dropping persistence for task " + task);
                    return;
                }
                AtomicFile atomicFile2 = new AtomicFile(new File(file, String.valueOf(task.mTaskId) + "_task.xml"));
                try {
                    fileOutputStream = atomicFile2.startWrite();
                    fileOutputStream.write(saveToXml);
                    atomicFile2.finishWrite(fileOutputStream);
                } catch (IOException e) {
                    atomicFile = atomicFile2;
                    e = e;
                    if (fileOutputStream != null) {
                        atomicFile.failWrite(fileOutputStream);
                    }
                    Slog.e("TaskPersister", "Unable to open " + atomicFile + " for persisting. " + e);
                }
            } catch (IOException e2) {
                e = e2;
                atomicFile = null;
            }
        }

        public final String toString() {
            return "TaskWriteQueueItem{task=" + this.mTask + "}";
        }
    }

    public TaskPersister(File file) {
        this.mTaskIdsInFile = new SparseArray();
        this.mIoLock = new Object();
        this.mTmpTaskIds = new ArraySet();
        this.mTaskIdsDir = file;
        this.mTaskSupervisor = null;
        this.mService = null;
        this.mRecentTasks = null;
        PersisterQueue persisterQueue = new PersisterQueue();
        this.mPersisterQueue = persisterQueue;
        persisterQueue.mListeners.add(this);
    }

    public TaskPersister(File file, ActivityTaskSupervisor activityTaskSupervisor, ActivityTaskManagerService activityTaskManagerService, RecentTasks recentTasks, PersisterQueue persisterQueue) {
        this.mTaskIdsInFile = new SparseArray();
        this.mIoLock = new Object();
        this.mTmpTaskIds = new ArraySet();
        File file2 = new File(file, "recent_images");
        if (file2.exists() && (!FileUtils.deleteContents(file2) || !file2.delete())) {
            Slog.i("TaskPersister", "Failure deleting legacy images directory: " + file2);
        }
        File file3 = new File(file, "recent_tasks");
        if (file3.exists() && (!FileUtils.deleteContents(file3) || !file3.delete())) {
            Slog.i("TaskPersister", "Failure deleting legacy tasks directory: " + file3);
        }
        this.mTaskIdsDir = new File(Environment.getDataDirectory(), "system_de");
        this.mTaskSupervisor = activityTaskSupervisor;
        this.mService = activityTaskManagerService;
        this.mRecentTasks = recentTasks;
        this.mPersisterQueue = persisterQueue;
        persisterQueue.mListeners.add(this);
    }

    public static String fileToString(File file) {
        String lineSeparator = System.lineSeparator();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            StringBuffer stringBuffer = new StringBuffer(((int) file.length()) * 2);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return stringBuffer.toString();
                }
                stringBuffer.append(readLine + lineSeparator);
            }
        } catch (IOException unused) {
            Slog.e("TaskPersister", "Couldn't read file " + file.getName());
            return null;
        }
    }

    public static RecentTaskFiles loadTasksForUser(int i) {
        ArrayList arrayList = new ArrayList();
        File file = new File(Environment.getDataSystemCeDirectory(i), "recent_tasks");
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            Slog.i("TaskPersister", "loadTasksForUser: Unable to list files from " + file + " exists=" + file.exists());
            return new RecentTaskFiles(new File[0], arrayList);
        }
        for (File file2 : listFiles) {
            if (file2.getName().endsWith("_task.xml")) {
                try {
                    try {
                        arrayList.add(new RecentTaskFile(file2, Integer.parseInt(file2.getName().substring(0, file2.getName().length() - 9))));
                    } catch (IOException e) {
                        Slog.w("TaskPersister", "Failed to read file: " + fileToString(file2), e);
                        file2.delete();
                    }
                } catch (NumberFormatException e2) {
                    Slog.w("TaskPersister", "Unexpected task file name", e2);
                }
            }
        }
        return new RecentTaskFiles(listFiles, arrayList);
    }

    public static void removeObsoleteFiles(ArraySet arraySet, File[] fileArr) {
        if (fileArr == null) {
            Slog.e("TaskPersister", "File error accessing recents directory (directory doesn't exist?).");
            return;
        }
        for (File file : fileArr) {
            String name = file.getName();
            int indexOf = name.indexOf(95);
            if (indexOf > 0) {
                try {
                    if (!arraySet.contains(Integer.valueOf(Integer.parseInt(name.substring(0, indexOf))))) {
                        file.delete();
                    }
                } catch (Exception unused) {
                    Slog.wtf("TaskPersister", "removeObsoleteFiles: Can't parse file=" + file.getName());
                    file.delete();
                }
            }
        }
    }

    public static Task taskIdToTask(ArrayList arrayList, int i) {
        if (i < 0) {
            return null;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Task task = (Task) arrayList.get(size);
            if (task.mTaskId == i) {
                return task;
            }
        }
        NandswapManager$$ExternalSyntheticOutline0.m(i, "Restore affiliation error looking for taskId=", "TaskPersister");
        return null;
    }

    public final File getUserPersistedTaskIdsFile(int i) {
        File file = new File(this.mTaskIdsDir, String.valueOf(i));
        if (!file.exists() && !file.mkdirs()) {
            Slog.e("TaskPersister", "Error while creating user directory: " + file);
        }
        return new File(file, "persisted_taskIds.txt");
    }

    public final void onPreProcessItem(boolean z) {
        int[] usersWithRecentsLoadedLocked;
        int[] usersWithRecentsLoadedLocked2;
        if (z) {
            this.mTmpTaskIds.clear();
            WindowManagerGlobalLock windowManagerGlobalLock = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    RecentTasks recentTasks = this.mRecentTasks;
                    ArraySet arraySet = this.mTmpTaskIds;
                    int size = recentTasks.mTasks.size();
                    for (int i = 0; i < size; i++) {
                        Task task = (Task) recentTasks.mTasks.get(i);
                        Task rootTask = task.getRootTask();
                        if ((task.isPersistable || task.inRecents) && (rootTask == null || !rootTask.isActivityTypeHomeOrRecents())) {
                            arraySet.add(Integer.valueOf(task.mTaskId));
                        }
                    }
                    this.mService.mWindowManager.removeObsoleteTaskFiles(this.mTmpTaskIds, this.mRecentTasks.usersWithRecentsLoadedLocked());
                } finally {
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            ArraySet arraySet2 = this.mTmpTaskIds;
            WindowManagerGlobalLock windowManagerGlobalLock2 = this.mService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock2) {
                try {
                    usersWithRecentsLoadedLocked = this.mRecentTasks.usersWithRecentsLoadedLocked();
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            for (int i2 : usersWithRecentsLoadedLocked) {
                removeObsoleteFiles(arraySet2, new File(Environment.getDataSystemCeDirectory(i2), "recent_images").listFiles());
                removeObsoleteFiles(arraySet2, new File(Environment.getDataSystemCeDirectory(i2), "recent_tasks").listFiles());
            }
            if (CoreRune.FW_DEDICATED_MEMORY) {
                WindowManagerGlobalLock windowManagerGlobalLock3 = this.mService.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock3) {
                    try {
                        usersWithRecentsLoadedLocked2 = this.mRecentTasks.usersWithRecentsLoadedLocked();
                    } finally {
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                for (int i3 : usersWithRecentsLoadedLocked2) {
                    if (this.mRecentTasks.mUserToProcs.get(i3).size() <= 0) {
                        File[] listFiles = new File(Environment.getDataSystemCeDirectory(i3), "dedicated_tasks").listFiles();
                        if (listFiles == null) {
                            Slog.e("TaskPersister", "File error accessing recents directory (directory doesn't exist?).");
                        } else {
                            for (File file : listFiles) {
                                file.delete();
                            }
                        }
                    }
                }
            }
        }
        SparseArray sparseArray = new SparseArray();
        WindowManagerGlobalLock windowManagerGlobalLock4 = this.mService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock4) {
            try {
                for (int i4 : this.mRecentTasks.usersWithRecentsLoadedLocked()) {
                    SparseBooleanArray sparseBooleanArray = (SparseBooleanArray) this.mRecentTasks.mPersistedTaskIds.get(i4);
                    if (sparseBooleanArray == null) {
                        Slog.wtf("ActivityTaskManager", "Loaded user without loaded tasks, userId=" + i4);
                        sparseBooleanArray = new SparseBooleanArray();
                    }
                    SparseBooleanArray sparseBooleanArray2 = (SparseBooleanArray) this.mTaskIdsInFile.get(i4);
                    if (sparseBooleanArray2 == null || !sparseBooleanArray2.equals(sparseBooleanArray)) {
                        SparseBooleanArray clone = sparseBooleanArray.clone();
                        this.mTaskIdsInFile.put(i4, clone);
                        sparseArray.put(i4, clone);
                    }
                }
            } finally {
                WindowManagerService.resetPriorityAfterLockedSection();
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
        for (int i5 = 0; i5 < sparseArray.size(); i5++) {
            writePersistedTaskIdsForUser((SparseBooleanArray) sparseArray.valueAt(i5), sparseArray.keyAt(i5));
        }
    }

    public final SparseBooleanArray readPersistedTaskIdsFromFileForUser(int i) {
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        synchronized (this.mIoLock) {
            BufferedReader bufferedReader = null;
            try {
                try {
                    BufferedReader bufferedReader2 = new BufferedReader(new FileReader(getUserPersistedTaskIdsFile(i)));
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            for (String str : readLine.split("\\s+")) {
                                sparseBooleanArray.put(Integer.parseInt(str), true);
                            }
                        } catch (FileNotFoundException unused) {
                            bufferedReader = bufferedReader2;
                            IoUtils.closeQuietly(bufferedReader);
                            HermesService$3$$ExternalSyntheticOutline0.m(i, "Loaded persisted task ids for user ", "TaskPersister");
                            return sparseBooleanArray;
                        } catch (Exception e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            Slog.e("TaskPersister", "Error while reading taskIds file for user " + i, e);
                            IoUtils.closeQuietly(bufferedReader);
                            HermesService$3$$ExternalSyntheticOutline0.m(i, "Loaded persisted task ids for user ", "TaskPersister");
                            return sparseBooleanArray;
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            IoUtils.closeQuietly(bufferedReader);
                            throw th;
                        }
                    }
                    IoUtils.closeQuietly(bufferedReader2);
                } catch (FileNotFoundException unused2) {
                    IoUtils.closeQuietly(bufferedReader);
                    HermesService$3$$ExternalSyntheticOutline0.m(i, "Loaded persisted task ids for user ", "TaskPersister");
                    return sparseBooleanArray;
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        HermesService$3$$ExternalSyntheticOutline0.m(i, "Loaded persisted task ids for user ", "TaskPersister");
        return sparseBooleanArray;
    }

    public final void saveDedicatedProcessName(int i, HashMap hashMap) {
        synchronized (this.mPersisterQueue) {
            this.mPersisterQueue.addItem(new DedicatedProcessWriteQueueItem(hashMap, this.mService, i), false);
        }
        this.mPersisterQueue.yieldIfQueueTooDeep();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v5 */
    public void writePersistedTaskIdsForUser(SparseBooleanArray sparseBooleanArray, int i) {
        int size;
        if (i < 0) {
            return;
        }
        File userPersistedTaskIdsFile = getUserPersistedTaskIdsFile(i);
        synchronized (this.mIoLock) {
            ?? r1 = 0;
            BufferedWriter bufferedWriter = null;
            try {
                try {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(userPersistedTaskIdsFile));
                    int i2 = 0;
                    while (true) {
                        try {
                            size = sparseBooleanArray.size();
                            if (i2 >= size) {
                                break;
                            }
                            if (sparseBooleanArray.valueAt(i2)) {
                                bufferedWriter2.write(String.valueOf(sparseBooleanArray.keyAt(i2)));
                                bufferedWriter2.newLine();
                            }
                            i2++;
                        } catch (Exception e) {
                            e = e;
                            bufferedWriter = bufferedWriter2;
                            Slog.e("TaskPersister", "Error while writing taskIds file for user " + i, e);
                            IoUtils.closeQuietly(bufferedWriter);
                            r1 = bufferedWriter;
                        } catch (Throwable th) {
                            th = th;
                            r1 = bufferedWriter2;
                            IoUtils.closeQuietly((AutoCloseable) r1);
                            throw th;
                        }
                    }
                    IoUtils.closeQuietly(bufferedWriter2);
                    r1 = size;
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }
}
