package androidx.room;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.internal.ContextScope;

/* loaded from: classes.dex */
final /* synthetic */ class RoomDatabase$closeBarrier$1 extends FunctionReferenceImpl implements Function0 {
    public RoomDatabase$closeBarrier$1(Object obj) {
        super(0, obj, RoomDatabase.class, "onClosed", "onClosed()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() throws Exception {
        RoomDatabase roomDatabase = (RoomDatabase) this.receiver;
        ContextScope contextScope = roomDatabase.coroutineScope;
        if (contextScope == null) {
            contextScope = null;
        }
        CoroutineScopeKt.cancel(contextScope, null);
        InvalidationTracker invalidationTracker = roomDatabase.internalTracker;
        if (invalidationTracker == null) {
            invalidationTracker = null;
        }
        MultiInstanceInvalidationClient multiInstanceInvalidationClient = invalidationTracker.multiInstanceInvalidationClient;
        if (multiInstanceInvalidationClient != null) {
            multiInstanceInvalidationClient.stop();
        }
        RoomConnectionManager roomConnectionManager = roomDatabase.connectionManager;
        (roomConnectionManager != null ? roomConnectionManager : null).connectionPool.close();
        return Unit.INSTANCE;
    }
}
