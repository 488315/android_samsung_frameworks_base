package androidx.room;

import androidx.room.InvalidationTracker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class InvalidationTracker$setAutoCloser$1 extends FunctionReferenceImpl implements Function0 {
    public InvalidationTracker$setAutoCloser$1(Object obj) {
        super(0, obj, InvalidationTracker.class, "onAutoCloseCallback", "onAutoCloseCallback()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        InvalidationTracker invalidationTracker = (InvalidationTracker) this.receiver;
        synchronized (invalidationTracker.trackerLock) {
            try {
                MultiInstanceInvalidationClient multiInstanceInvalidationClient = invalidationTracker.multiInstanceInvalidationClient;
                if (multiInstanceInvalidationClient != null) {
                    ReentrantLock reentrantLock = invalidationTracker.observerMapLock;
                    reentrantLock.lock();
                    try {
                        List list = CollectionsKt___CollectionsKt.toList(((LinkedHashMap) invalidationTracker.observerMap).keySet());
                        reentrantLock.unlock();
                        ArrayList arrayList = new ArrayList();
                        for (Object obj : list) {
                            InvalidationTracker.Observer observer = (InvalidationTracker.Observer) obj;
                            observer.getClass();
                            if (!(observer instanceof MultiInstanceInvalidationClient$observer$1)) {
                                arrayList.add(obj);
                            }
                        }
                        if (arrayList.isEmpty()) {
                            multiInstanceInvalidationClient.stop();
                        }
                    } catch (Throwable th) {
                        reentrantLock.unlock();
                        throw th;
                    }
                }
                ObservedTableStates observedTableStates = invalidationTracker.implementation.observedTableStates;
                ReentrantLock reentrantLock2 = observedTableStates.lock;
                reentrantLock2.lock();
                try {
                    boolean[] zArr = observedTableStates.tableObservedState;
                    Arrays.fill(zArr, 0, zArr.length, false);
                    observedTableStates.needsSync = true;
                    Unit unit = Unit.INSTANCE;
                } finally {
                    reentrantLock2.unlock();
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
        return Unit.INSTANCE;
    }
}
