package androidx.room;

import androidx.room.InvalidationTracker;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.builders.SetBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes.dex */
final class MultiInstanceInvalidationClient$invalidationCallback$1$onInvalidation$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String[] $tables;
    Object L$0;
    int label;
    final /* synthetic */ MultiInstanceInvalidationClient this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MultiInstanceInvalidationClient$invalidationCallback$1$onInvalidation$1(String[] strArr, MultiInstanceInvalidationClient multiInstanceInvalidationClient, Continuation continuation) {
        super(2, continuation);
        this.$tables = strArr;
        this.this$0 = multiInstanceInvalidationClient;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MultiInstanceInvalidationClient$invalidationCallback$1$onInvalidation$1(this.$tables, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MultiInstanceInvalidationClient$invalidationCallback$1$onInvalidation$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Set<String> set;
        Set setBuild;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            String[] strArr = this.$tables;
            Set set2 = ArraysKt___ArraysKt.toSet(Arrays.copyOf(strArr, strArr.length));
            SharedFlowImpl sharedFlowImpl = this.this$0.invalidatedTables;
            this.L$0 = set2;
            this.label = 1;
            if (sharedFlowImpl.emit(set2, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            set = set2;
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            set = (Set) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        InvalidationTracker invalidationTracker = this.this$0.invalidationTracker;
        ReentrantLock reentrantLock = invalidationTracker.observerMapLock;
        reentrantLock.lock();
        try {
            List<ObserverWrapper> list = CollectionsKt___CollectionsKt.toList(((LinkedHashMap) invalidationTracker.observerMap).values());
            reentrantLock.unlock();
            for (ObserverWrapper observerWrapper : list) {
                InvalidationTracker.Observer observer = observerWrapper.observer;
                observer.getClass();
                if (!(observer instanceof MultiInstanceInvalidationClient$observer$1)) {
                    String[] strArr2 = observerWrapper.tableNames;
                    int length = strArr2.length;
                    if (length == 0) {
                        setBuild = EmptySet.INSTANCE;
                    } else if (length != 1) {
                        SetBuilder setBuilder = new SetBuilder();
                        for (String str : set) {
                            int length2 = strArr2.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 < length2) {
                                    String str2 = strArr2[i2];
                                    if (StringsKt__StringsJVMKt.equals(str2, str, true)) {
                                        setBuilder.add(str2);
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                        setBuild = setBuilder.build();
                    } else {
                        Set set3 = set;
                        if ((set3 instanceof Collection) && set3.isEmpty()) {
                            setBuild = EmptySet.INSTANCE;
                        } else {
                            Iterator it = set3.iterator();
                            while (it.hasNext()) {
                                if (StringsKt__StringsJVMKt.equals((String) it.next(), strArr2[0], true)) {
                                    setBuild = observerWrapper.singleTableSet;
                                    break;
                                }
                            }
                            setBuild = EmptySet.INSTANCE;
                        }
                    }
                    if (!setBuild.isEmpty()) {
                        observerWrapper.observer.onInvalidated(setBuild);
                    }
                }
            }
            return Unit.INSTANCE;
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }
}
