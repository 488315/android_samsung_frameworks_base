package androidx.room.coroutines;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.sqlite.SQLiteConnection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.ExceptionsKt__ExceptionsKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.text.StringsKt__StringsKt$lineSequence$$inlined$Sequence$1;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;
import kotlinx.coroutines.channels.ChannelResult;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Pool {
    public final int capacity;
    public final BufferedChannel channel;
    public final Function0 connectionFactory;
    public final ConnectionWithLock[] connections;
    public final AtomicInteger size = new AtomicInteger(0);

    /* JADX WARN: Type inference failed for: r4v3, types: [androidx.room.coroutines.Pool$$ExternalSyntheticLambda0] */
    public Pool(int i, Function0 function0) {
        this.capacity = i;
        this.connectionFactory = function0;
        this.connections = new ConnectionWithLock[i];
        this.channel = ChannelKt.Channel$default(i, null, new Function1() { // from class: androidx.room.coroutines.Pool$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Pool.this.recycle((ConnectionWithLock) obj);
                return Unit.INSTANCE;
            }
        }, 2);
    }

    public final void dump(StringBuilder sb) {
        sb.append("\t" + toString() + " (capacity=" + this.capacity + ')');
        sb.append('\n');
        ConnectionWithLock[] connectionWithLockArr = this.connections;
        int length = connectionWithLockArr.length;
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            ConnectionWithLock connectionWithLock = connectionWithLockArr[i2];
            i++;
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "\t\t[", "] - ");
            m.append(connectionWithLock != null ? connectionWithLock.delegate.toString() : null);
            sb.append(m.toString());
            sb.append('\n');
            if (connectionWithLock != null) {
                if (connectionWithLock.acquireCoroutineContext == null && connectionWithLock.acquireThrowable == null) {
                    sb.append("\t\tStatus: Free connection");
                    sb.append('\n');
                } else {
                    sb.append("\t\tStatus: Acquired connection");
                    sb.append('\n');
                    CoroutineContext coroutineContext = connectionWithLock.acquireCoroutineContext;
                    if (coroutineContext != null) {
                        sb.append("\t\tCoroutine: " + coroutineContext);
                        sb.append('\n');
                    }
                    Throwable th = connectionWithLock.acquireThrowable;
                    if (th != null) {
                        sb.append("\t\tAcquired:");
                        sb.append('\n');
                        Iterator it = CollectionsKt___CollectionsKt.drop(SequencesKt___SequencesKt.toList(new StringsKt__StringsKt$lineSequence$$inlined$Sequence$1(ExceptionsKt__ExceptionsKt.stackTraceToString(th))), 1).iterator();
                        while (it.hasNext()) {
                            sb.append("\t\t" + ((String) it.next()));
                            sb.append('\n');
                        }
                    }
                }
            }
        }
    }

    public final void recycle(ConnectionWithLock connectionWithLock) {
        Object mo3456trySendJP2dKIU = this.channel.mo3456trySendJP2dKIU(connectionWithLock);
        ChannelResult.Companion companion = ChannelResult.Companion;
        if (mo3456trySendJP2dKIU instanceof ChannelResult.Failed) {
            connectionWithLock.close();
            if (!(mo3456trySendJP2dKIU instanceof ChannelResult.Closed)) {
                throw new IllegalStateException("Couldn't recycle connection");
            }
        }
    }

    public final void tryOpenNewConnection() {
        int i = this.size.get();
        if (i >= this.capacity) {
            return;
        }
        if (!this.size.compareAndSet(i, i + 1)) {
            tryOpenNewConnection();
            return;
        }
        ConnectionWithLock connectionWithLock = new ConnectionWithLock((SQLiteConnection) this.connectionFactory.invoke(), null, 2, null);
        Object mo3456trySendJP2dKIU = this.channel.mo3456trySendJP2dKIU(connectionWithLock);
        ChannelResult.Companion companion = ChannelResult.Companion;
        if (!(mo3456trySendJP2dKIU instanceof ChannelResult.Failed)) {
            this.connections[i] = connectionWithLock;
            return;
        }
        connectionWithLock.close();
        if (!(mo3456trySendJP2dKIU instanceof ChannelResult.Closed)) {
            throw new IllegalStateException("Couldn't send a new connection for acquisition");
        }
    }
}
