package com.android.server.permission.access;

import android.content.ApexEnvironment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseLongArray;

import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.immutable.MutableIntMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.jarjar.kotlin.io.CloseableKt;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Ref$ObjectRef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AccessPersistence {
    public final AccessPolicy policy;
    public WriteHandler writeHandler;
    public final Object scheduleLock = new Object();
    public final SparseLongArray pendingMutationTimesMillis = new SparseLongArray();
    public final MutableIntMap pendingStates = new MutableIntMap();
    public final Object writeLock = new Object();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WriteHandler extends Handler {
        public WriteHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            AccessPersistence.this.writePendingState(message.what);
        }
    }

    public AccessPersistence(AccessPolicy accessPolicy) {
        this.policy = accessPolicy;
    }

    public final void write(MutableAccessState mutableAccessState) {
        write(mutableAccessState.getSystemState(), mutableAccessState, -1);
        MutableIntReferenceMap userStates = mutableAccessState.getUserStates();
        int size = userStates.array.size();
        for (int i = 0; i < size; i++) {
            write(
                    (MutableUserState) userStates.valueAt(i),
                    mutableAccessState,
                    userStates.array.keyAt(i));
        }
    }

    public final void write(
            WritableState writableState, MutableAccessState mutableAccessState, int i) {
        long j;
        int writeMode = writableState.getWriteMode();
        if (writeMode != 0) {
            if (writeMode != 1) {
                if (writeMode != 2) {
                    throw new IllegalStateException(Integer.valueOf(writeMode).toString());
                }
                synchronized (this.scheduleLock) {
                    this.pendingStates.array.put(i, mutableAccessState);
                }
                writePendingState(i);
                return;
            }
            synchronized (this.scheduleLock) {
                try {
                    WriteHandler writeHandler = this.writeHandler;
                    if (writeHandler == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                        throw null;
                    }
                    writeHandler.removeMessages(i);
                    this.pendingStates.array.put(i, mutableAccessState);
                    long uptimeMillis = SystemClock.uptimeMillis();
                    SparseLongArray sparseLongArray = this.pendingMutationTimesMillis;
                    int indexOfKey = sparseLongArray.indexOfKey(i);
                    if (indexOfKey >= 0) {
                        j = sparseLongArray.valueAt(indexOfKey);
                    } else {
                        sparseLongArray.put(i, uptimeMillis);
                        j = uptimeMillis;
                    }
                    long j2 = uptimeMillis - j;
                    WriteHandler writeHandler2 = this.writeHandler;
                    if (writeHandler2 == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                        throw null;
                    }
                    Message obtainMessage = writeHandler2.obtainMessage(i);
                    if (j2 > 2000) {
                        obtainMessage.sendToTarget();
                    } else {
                        long j3 = 2000 - j2;
                        if (1000 <= j3) {
                            j3 = 1000;
                        }
                        WriteHandler writeHandler3 = this.writeHandler;
                        if (writeHandler3 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                            throw null;
                        }
                        writeHandler3.sendMessageDelayed(obtainMessage, j3);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void writePendingState(int i) {
        synchronized (this.writeLock) {
            try {
                Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                synchronized (this.scheduleLock) {
                    this.pendingMutationTimesMillis.delete(i);
                    MutableIntMap mutableIntMap = this.pendingStates;
                    Object removeReturnOld = mutableIntMap.array.removeReturnOld(i);
                    mutableIntMap.array.size();
                    ref$ObjectRef.element = removeReturnOld;
                    WriteHandler writeHandler = this.writeHandler;
                    if (writeHandler == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("writeHandler");
                        throw null;
                    }
                    writeHandler.removeMessages(i);
                }
                Object obj = ref$ObjectRef.element;
                if (obj == null) {
                    return;
                }
                if (i == -1) {
                    writeSystemState((AccessState) obj);
                } else {
                    writeUserState((AccessState) obj, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void writeSystemState(AccessState accessState) {
        File file;
        File file2 =
                new File(
                        ApexEnvironment.getApexEnvironment("com.android.permission")
                                .getDeviceProtectedDataDir(),
                        "access.abx");
        try {
            AtomicFile atomicFile = new AtomicFile(file2);
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                BinaryXmlSerializer binaryXmlSerializer = new BinaryXmlSerializer();
                binaryXmlSerializer.setOutput(startWrite, (String) null);
                binaryXmlSerializer.startDocument((String) null, Boolean.TRUE);
                this.policy.serializeSystemState(binaryXmlSerializer, accessState);
                binaryXmlSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
                CloseableKt.closeFinally(startWrite, null);
                file =
                        new File(
                                atomicFile.getBaseFile().getParentFile(),
                                atomicFile.getBaseFile().getName() + ".reservecopy");
            } finally {
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(atomicFile.getBaseFile());
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    try {
                        FileUtils.copy(fileInputStream, fileOutputStream);
                        fileOutputStream.getFD().sync();
                        CloseableKt.closeFinally(fileOutputStream, null);
                        CloseableKt.closeFinally(fileInputStream, null);
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                Slog.e("AccessPersistence", "Failed to write " + file, e);
            }
        } catch (Exception e2) {
            Slog.e("AccessPersistence", "Failed to serialize " + file2, e2);
        }
    }

    public final void writeUserState(AccessState accessState, int i) {
        File file =
                new File(
                        ApexEnvironment.getApexEnvironment("com.android.permission")
                                .getDeviceProtectedDataDirForUser(UserHandle.of(i)),
                        "access.abx");
        try {
            AtomicFile atomicFile = new AtomicFile(file);
            FileOutputStream startWrite = atomicFile.startWrite();
            try {
                BinaryXmlSerializer binaryXmlSerializer = new BinaryXmlSerializer();
                binaryXmlSerializer.setOutput(startWrite, (String) null);
                binaryXmlSerializer.startDocument((String) null, Boolean.TRUE);
                this.policy.serializeUserState(binaryXmlSerializer, accessState, i);
                binaryXmlSerializer.endDocument();
                atomicFile.finishWrite(startWrite);
                CloseableKt.closeFinally(startWrite, null);
                File file2 =
                        new File(
                                atomicFile.getBaseFile().getParentFile(),
                                atomicFile.getBaseFile().getName() + ".reservecopy");
                try {
                    FileInputStream fileInputStream = new FileInputStream(atomicFile.getBaseFile());
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        try {
                            FileUtils.copy(fileInputStream, fileOutputStream);
                            fileOutputStream.getFD().sync();
                            CloseableKt.closeFinally(fileOutputStream, null);
                            CloseableKt.closeFinally(fileInputStream, null);
                        } finally {
                        }
                    } finally {
                    }
                } catch (Exception e) {
                    Slog.e("AccessPersistence", "Failed to write " + file2, e);
                }
            } finally {
            }
        } catch (Exception e2) {
            Slog.e("AccessPersistence", "Failed to serialize " + file, e2);
        }
    }
}
