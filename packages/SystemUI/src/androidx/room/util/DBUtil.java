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
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes.dex */
public abstract class DBUtil {
    public static final void dropFtsSyncTriggers(SQLiteConnection sQLiteConnection) throws Exception {
        ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("SELECT name FROM sqlite_master WHERE type = 'trigger'");
        while (sQLiteStatementPrepare.step()) {
            try {
                listBuilderCreateListBuilder.add(sQLiteStatementPrepare.getText(0));
            } finally {
            }
        }
        Unit unit = Unit.INSTANCE;
        sQLiteStatementPrepare.close();
        ListIterator listIterator = listBuilderCreateListBuilder.build().listIterator(0);
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
            CoroutineContext coroutineContextPlus = contextScope2.coroutineContext.plus(continuationInterceptor);
            if (coroutineContextPlus != null) {
                return coroutineContextPlus;
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

    /* JADX WARN: Removed duplicated region for block: B:8:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object performSuspending(RoomDatabase roomDatabase, boolean z, boolean z2, Function1 function1, ContinuationImpl continuationImpl) throws Throwable {
        DBUtil__DBUtil_androidKt$performSuspending$1 dBUtil__DBUtil_androidKt$performSuspending$1;
        RoomDatabase roomDatabase2;
        boolean z3;
        Function1 function12;
        if (continuationImpl instanceof DBUtil__DBUtil_androidKt$performSuspending$1) {
            dBUtil__DBUtil_androidKt$performSuspending$1 = (DBUtil__DBUtil_androidKt$performSuspending$1) continuationImpl;
            int i = dBUtil__DBUtil_androidKt$performSuspending$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                dBUtil__DBUtil_androidKt$performSuspending$1.label = i - Integer.MIN_VALUE;
            } else {
                dBUtil__DBUtil_androidKt$performSuspending$1 = new DBUtil__DBUtil_androidKt$performSuspending$1(continuationImpl);
            }
        }
        DBUtil__DBUtil_androidKt$performSuspending$1 dBUtil__DBUtil_androidKt$performSuspending$12 = dBUtil__DBUtil_androidKt$performSuspending$1;
        Object obj = dBUtil__DBUtil_androidKt$performSuspending$12.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = dBUtil__DBUtil_androidKt$performSuspending$12.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            if (roomDatabase.inCompatibilityMode$room_runtime_release() && roomDatabase.isOpenInternal() && roomDatabase.inTransaction()) {
                DBUtil__DBUtil_androidKt$performSuspending$lambda$1$$inlined$internalPerform$1 dBUtil__DBUtil_androidKt$performSuspending$lambda$1$$inlined$internalPerform$1 = new DBUtil__DBUtil_androidKt$performSuspending$lambda$1$$inlined$internalPerform$1(z2, z, roomDatabase, null, function1);
                dBUtil__DBUtil_androidKt$performSuspending$12.label = 1;
                Object objUseConnection$room_runtime_release = roomDatabase.useConnection$room_runtime_release(z, dBUtil__DBUtil_androidKt$performSuspending$lambda$1$$inlined$internalPerform$1, dBUtil__DBUtil_androidKt$performSuspending$12);
                if (objUseConnection$room_runtime_release != coroutineSingletons) {
                    return objUseConnection$room_runtime_release;
                }
            } else {
                dBUtil__DBUtil_androidKt$performSuspending$12.L$0 = roomDatabase;
                dBUtil__DBUtil_androidKt$performSuspending$12.L$1 = function1;
                dBUtil__DBUtil_androidKt$performSuspending$12.Z$0 = z;
                dBUtil__DBUtil_androidKt$performSuspending$12.Z$1 = z2;
                dBUtil__DBUtil_androidKt$performSuspending$12.label = 2;
                CoroutineContext coroutineContext = getCoroutineContext(roomDatabase, z2, dBUtil__DBUtil_androidKt$performSuspending$12);
                if (coroutineContext != coroutineSingletons) {
                    roomDatabase2 = roomDatabase;
                    obj = coroutineContext;
                    z3 = z2;
                    function12 = function1;
                }
            }
        }
        if (i2 == 1) {
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        if (i2 != 2) {
            if (i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        boolean z4 = dBUtil__DBUtil_androidKt$performSuspending$12.Z$1;
        z = dBUtil__DBUtil_androidKt$performSuspending$12.Z$0;
        Function1 function13 = (Function1) dBUtil__DBUtil_androidKt$performSuspending$12.L$1;
        RoomDatabase roomDatabase3 = (RoomDatabase) dBUtil__DBUtil_androidKt$performSuspending$12.L$0;
        ResultKt.throwOnFailure(obj);
        z3 = z4;
        function12 = function13;
        roomDatabase2 = roomDatabase3;
        DBUtil__DBUtil_androidKt$performSuspending$$inlined$compatCoroutineExecute$DBUtil__DBUtil_androidKt$1 dBUtil__DBUtil_androidKt$performSuspending$$inlined$compatCoroutineExecute$DBUtil__DBUtil_androidKt$1 = new DBUtil__DBUtil_androidKt$performSuspending$$inlined$compatCoroutineExecute$DBUtil__DBUtil_androidKt$1(null, roomDatabase2, z, z3, function12);
        dBUtil__DBUtil_androidKt$performSuspending$12.L$0 = null;
        dBUtil__DBUtil_androidKt$performSuspending$12.L$1 = null;
        dBUtil__DBUtil_androidKt$performSuspending$12.label = 3;
        Object objWithContext = BuildersKt.withContext((CoroutineContext) obj, dBUtil__DBUtil_androidKt$performSuspending$$inlined$compatCoroutineExecute$DBUtil__DBUtil_androidKt$1, dBUtil__DBUtil_androidKt$performSuspending$12);
        return objWithContext == coroutineSingletons ? coroutineSingletons : objWithContext;
    }

    public static final int readVersion(File file) throws IOException {
        FileChannel channel = new FileInputStream(file).getChannel();
        try {
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4);
            channel.tryLock(60L, 4L, true);
            channel.position(60L);
            if (channel.read(byteBufferAllocate) != 4) {
                throw new IOException("Bad database header, unable to read 4 bytes at offset 60");
            }
            byteBufferAllocate.rewind();
            int i = byteBufferAllocate.getInt();
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
