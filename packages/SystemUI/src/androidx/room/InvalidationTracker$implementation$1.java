package androidx.room;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes.dex */
final /* synthetic */ class InvalidationTracker$implementation$1 extends FunctionReferenceImpl implements Function1 {
    public InvalidationTracker$implementation$1(Object obj) {
        super(1, obj, InvalidationTracker.class, "notifyInvalidatedObservers", "notifyInvalidatedObservers(Ljava/util/Set;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Set setBuild;
        Set set = (Set) obj;
        InvalidationTracker invalidationTracker = (InvalidationTracker) this.receiver;
        ReentrantLock reentrantLock = invalidationTracker.observerMapLock;
        reentrantLock.lock();
        try {
            List<ObserverWrapper> list = CollectionsKt___CollectionsKt.toList(((LinkedHashMap) invalidationTracker.observerMap).values());
            reentrantLock.unlock();
            for (ObserverWrapper observerWrapper : list) {
                int[] iArr = observerWrapper.tableIds;
                int length = iArr.length;
                if (length != 0) {
                    int i = 0;
                    if (length != 1) {
                        SetBuilder setBuilder = new SetBuilder();
                        int length2 = iArr.length;
                        int i2 = 0;
                        while (i < length2) {
                            int i3 = i2 + 1;
                            if (set.contains(Integer.valueOf(iArr[i]))) {
                                setBuilder.add(observerWrapper.tableNames[i2]);
                            }
                            i++;
                            i2 = i3;
                        }
                        setBuild = setBuilder.build();
                    } else {
                        setBuild = set.contains(Integer.valueOf(iArr[0])) ? observerWrapper.singleTableSet : EmptySet.INSTANCE;
                    }
                } else {
                    setBuild = EmptySet.INSTANCE;
                }
                if (!setBuild.isEmpty()) {
                    observerWrapper.observer.onInvalidated(setBuild);
                }
            }
            return Unit.INSTANCE;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
