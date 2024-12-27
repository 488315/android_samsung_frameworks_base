package com.android.server.inputmethod;

import android.os.Process;
import android.util.IntArray;
import android.util.SparseArray;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class AdditionalSubtypeMapRepository {
    public static final SparseArray sPerUserMap = new SparseArray();
    public static final SingleThreadedBackgroundWriter sWriter = new SingleThreadedBackgroundWriter();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SingleThreadedBackgroundWriter {
        public final ReentrantLock mLock;
        public final Condition mLockNotifier;
        public final SparseArray mPendingTasks;
        public final IntArray mRemovedUsers;
        public final AnonymousClass1 mWriterThread;

        /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.inputmethod.AdditionalSubtypeMapRepository$SingleThreadedBackgroundWriter$1] */
        public SingleThreadedBackgroundWriter() {
            ReentrantLock reentrantLock = new ReentrantLock();
            this.mLock = reentrantLock;
            this.mLockNotifier = reentrantLock.newCondition();
            this.mPendingTasks = new SparseArray();
            this.mRemovedUsers = new IntArray();
            this.mWriterThread = new Thread() { // from class: com.android.server.inputmethod.AdditionalSubtypeMapRepository.SingleThreadedBackgroundWriter.1
                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    Process.setThreadPriority(10);
                    while (true) {
                        SingleThreadedBackgroundWriter.this.mLock.lock();
                        while (SingleThreadedBackgroundWriter.this.mPendingTasks.size() == 0) {
                            try {
                                SingleThreadedBackgroundWriter.this.mLockNotifier.awaitUninterruptibly();
                            } catch (Throwable th) {
                                SingleThreadedBackgroundWriter.this.mLock.unlock();
                                throw th;
                            }
                        }
                        SparseArray clone = SingleThreadedBackgroundWriter.this.mPendingTasks.clone();
                        SingleThreadedBackgroundWriter.this.mPendingTasks.clear();
                        IntArray clone2 = SingleThreadedBackgroundWriter.this.mRemovedUsers.size() == 0 ? null : SingleThreadedBackgroundWriter.this.mRemovedUsers.clone();
                        SingleThreadedBackgroundWriter.this.mLock.unlock();
                        int size = clone.size();
                        ArrayList arrayList = new ArrayList(size);
                        for (int i = 0; i < size; i++) {
                            int keyAt = clone.keyAt(i);
                            if (clone2 == null || !clone2.contains(keyAt)) {
                                arrayList.add((WriteTask) clone.valueAt(i));
                            }
                        }
                        arrayList.forEach(new AdditionalSubtypeMapRepository$SingleThreadedBackgroundWriter$1$$ExternalSyntheticLambda0());
                    }
                }
            };
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WriteTask extends Record {
        public final InputMethodMap inputMethodMap;
        public final AdditionalSubtypeMap subtypeMap;
        public final int userId;

        public WriteTask(int i, AdditionalSubtypeMap additionalSubtypeMap, InputMethodMap inputMethodMap) {
            this.userId = i;
            this.subtypeMap = additionalSubtypeMap;
            this.inputMethodMap = inputMethodMap;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, WriteTask.class, Object.class), WriteTask.class, "userId;subtypeMap;inputMethodMap", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->userId:I", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->subtypeMap:Lcom/android/server/inputmethod/AdditionalSubtypeMap;", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->inputMethodMap:Lcom/android/server/inputmethod/InputMethodMap;").dynamicInvoker().invoke(this, obj) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, WriteTask.class), WriteTask.class, "userId;subtypeMap;inputMethodMap", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->userId:I", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->subtypeMap:Lcom/android/server/inputmethod/AdditionalSubtypeMap;", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->inputMethodMap:Lcom/android/server/inputmethod/InputMethodMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, WriteTask.class), WriteTask.class, "userId;subtypeMap;inputMethodMap", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->userId:I", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->subtypeMap:Lcom/android/server/inputmethod/AdditionalSubtypeMap;", "FIELD:Lcom/android/server/inputmethod/AdditionalSubtypeMapRepository$WriteTask;->inputMethodMap:Lcom/android/server/inputmethod/InputMethodMap;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }
    }

    public static AdditionalSubtypeMap get(int i) {
        SparseArray sparseArray = sPerUserMap;
        AdditionalSubtypeMap additionalSubtypeMap = (AdditionalSubtypeMap) sparseArray.get(i);
        if (additionalSubtypeMap != null) {
            return additionalSubtypeMap;
        }
        AdditionalSubtypeMap load = AdditionalSubtypeUtils.load(i);
        sparseArray.put(i, load);
        return load;
    }

    public static void putAndSave(int i, AdditionalSubtypeMap additionalSubtypeMap, InputMethodMap inputMethodMap) {
        SparseArray sparseArray = sPerUserMap;
        if (((AdditionalSubtypeMap) sparseArray.get(i)) == additionalSubtypeMap) {
            return;
        }
        sparseArray.put(i, additionalSubtypeMap);
        SingleThreadedBackgroundWriter singleThreadedBackgroundWriter = sWriter;
        singleThreadedBackgroundWriter.getClass();
        WriteTask writeTask = new WriteTask(i, additionalSubtypeMap, inputMethodMap);
        singleThreadedBackgroundWriter.mLock.lock();
        try {
            if (!singleThreadedBackgroundWriter.mRemovedUsers.contains(i)) {
                singleThreadedBackgroundWriter.mPendingTasks.put(i, writeTask);
                singleThreadedBackgroundWriter.mLockNotifier.signalAll();
            }
        } finally {
            singleThreadedBackgroundWriter.mLock.unlock();
        }
    }
}
