package androidx.room.util;

import android.os.Looper;
import androidx.room.RoomDatabase;
import androidx.room.TransactionElement;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ListIterator;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes.dex */
public abstract class DBUtil {
    public static final void dropFtsSyncTriggers(SQLiteConnection sQLiteConnection) {
        ListBuilder createListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        SQLiteStatement prepare = sQLiteConnection.prepare("SELECT name FROM sqlite_master WHERE type = 'trigger'");
        while (prepare.step()) {
            try {
                createListBuilder.add(prepare.getText(0));
            } finally {
            }
        }
        Unit unit = Unit.INSTANCE;
        prepare.close();
        ListIterator listIterator = createListBuilder.build().listIterator(0);
        while (true) {
            ListBuilder.Itr itr = (ListBuilder.Itr) listIterator;
            if (!itr.hasNext()) {
                return;
            }
            String str = (String) itr.next();
            if (str.startsWith("room_fts_content_sync_")) {
                SQLite.execSQL(sQLiteConnection, "DROP TRIGGER IF EXISTS ".concat(str));
            }
        }
    }

    public static final CoroutineContext getCoroutineContext(RoomDatabase roomDatabase, boolean z, ContinuationImpl continuationImpl) {
        ContinuationInterceptor continuationInterceptor;
        if (!roomDatabase.inCompatibilityMode$room_runtime_release()) {
            ContextScope contextScope = roomDatabase.coroutineScope;
            return (contextScope != null ? contextScope : null).coroutineContext;
        }
        TransactionElement transactionElement = (TransactionElement) continuationImpl.getContext().get(TransactionElement.Key);
        if (transactionElement != null && (continuationInterceptor = transactionElement.transactionDispatcher) != null) {
            ContextScope contextScope2 = roomDatabase.coroutineScope;
            if (contextScope2 == null) {
                contextScope2 = null;
            }
            CoroutineContext plus = contextScope2.coroutineContext.plus(continuationInterceptor);
            if (plus != null) {
                return plus;
            }
        }
        if (!z) {
            ContextScope contextScope3 = roomDatabase.coroutineScope;
            return (contextScope3 != null ? contextScope3 : null).coroutineContext;
        }
        CoroutineContext coroutineContext = roomDatabase.transactionContext;
        if (coroutineContext == null) {
            return null;
        }
        return coroutineContext;
    }

    public static final Object performBlocking(RoomDatabase roomDatabase, boolean z, boolean z2, Function1 function1) {
        if (!roomDatabase.allowMainThreadQueries && Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Cannot access database on the main thread since it may potentially lock the UI for a long period of time.");
        }
        if (roomDatabase.inCompatibilityMode$room_runtime_release() && !roomDatabase.inTransaction() && roomDatabase.suspendingTransactionId.get() != null) {
            throw new IllegalStateException("Cannot access database on a different coroutine context inherited from a suspending transaction.");
        }
        return BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new DBUtil__DBUtil_androidKt$performBlocking$1(roomDatabase, z, z2, function1, null));
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ac A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ad A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object performSuspending(androidx.room.RoomDatabase r14, boolean r15, boolean r16, kotlin.jvm.functions.Function1 r17, kotlin.coroutines.jvm.internal.ContinuationImpl r18) {
        /*
            r0 = r18
            boolean r1 = r0 instanceof androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$1
            if (r1 == 0) goto L16
            r1 = r0
            androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$1 r1 = (androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$1) r1
            int r2 = r1.label
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L16
            int r2 = r2 - r3
            r1.label = r2
        L14:
            r6 = r1
            goto L1c
        L16:
            androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$1 r1 = new androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$1
            r1.<init>(r0)
            goto L14
        L1c:
            java.lang.Object r0 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r7 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 3
            r3 = 2
            r8 = 1
            if (r1 == 0) goto L51
            if (r1 == r8) goto L4d
            if (r1 == r3) goto L39
            if (r1 != r2) goto L31
            kotlin.ResultKt.throwOnFailure(r0)
            return r0
        L31:
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r15 = "call to 'resume' before 'invoke' with coroutine"
            r14.<init>(r15)
            throw r14
        L39:
            boolean r14 = r6.Z$1
            boolean r15 = r6.Z$0
            java.lang.Object r1 = r6.L$1
            kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
            java.lang.Object r3 = r6.L$0
            androidx.room.RoomDatabase r3 = (androidx.room.RoomDatabase) r3
            kotlin.ResultKt.throwOnFailure(r0)
            r12 = r14
            r13 = r1
            r10 = r3
        L4b:
            r11 = r15
            goto L97
        L4d:
            kotlin.ResultKt.throwOnFailure(r0)
            return r0
        L51:
            kotlin.ResultKt.throwOnFailure(r0)
            boolean r0 = r14.inCompatibilityMode$room_runtime_release()
            if (r0 == 0) goto L7d
            boolean r0 = r14.isOpenInternal()
            if (r0 == 0) goto L7d
            boolean r0 = r14.inTransaction()
            if (r0 == 0) goto L7d
            androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$lambda$1$$inlined$internalPerform$1 r0 = new androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$lambda$1$$inlined$internalPerform$1
            r4 = 0
            r3 = r14
            r2 = r15
            r1 = r16
            r5 = r17
            r0.<init>(r1, r2, r3, r4, r5)
            r2 = r0
            r6.label = r8
            java.lang.Object r14 = r14.useConnection$room_runtime_release(r15, r2, r6)
            if (r14 != r7) goto L7c
            goto Lac
        L7c:
            return r14
        L7d:
            r4 = r16
            r6.L$0 = r14
            r5 = r17
            r6.L$1 = r5
            r6.Z$0 = r15
            r6.Z$1 = r4
            r6.label = r3
            kotlin.coroutines.CoroutineContext r3 = getCoroutineContext(r14, r4, r6)
            if (r3 != r7) goto L92
            goto Lac
        L92:
            r10 = r14
            r0 = r3
            r12 = r4
            r13 = r5
            goto L4b
        L97:
            kotlin.coroutines.CoroutineContext r0 = (kotlin.coroutines.CoroutineContext) r0
            androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$$inlined$compatCoroutineExecute$DBUtil__DBUtil_androidKt$1 r8 = new androidx.room.util.DBUtil__DBUtil_androidKt$performSuspending$$inlined$compatCoroutineExecute$DBUtil__DBUtil_androidKt$1
            r9 = 0
            r8.<init>(r9, r10, r11, r12, r13)
            r14 = 0
            r6.L$0 = r14
            r6.L$1 = r14
            r6.label = r2
            java.lang.Object r14 = kotlinx.coroutines.BuildersKt.withContext(r0, r8, r6)
            if (r14 != r7) goto Lad
        Lac:
            return r7
        Lad:
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.util.DBUtil.performSuspending(androidx.room.RoomDatabase, boolean, boolean, kotlin.jvm.functions.Function1, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final int readVersion(File file) {
        FileChannel channel = new FileInputStream(file).getChannel();
        try {
            ByteBuffer allocate = ByteBuffer.allocate(4);
            channel.tryLock(60L, 4L, true);
            channel.position(60L);
            if (channel.read(allocate) != 4) {
                throw new IOException("Bad database header, unable to read 4 bytes at offset 60");
            }
            allocate.rewind();
            int i = allocate.getInt();
            channel.close();
            return i;
        } catch (Throwable th) {
            try {
                throw th;
            } catch (Throwable th2) {
                CloseableKt.closeFinally(channel, th);
                throw th2;
            }
        }
    }
}
