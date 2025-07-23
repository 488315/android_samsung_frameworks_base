package com.android.systemui.common.ui.domain.interactor;

import com.android.systemui.common.ui.data.repository.ConfigurationRepositoryImpl;
import java.util.LinkedHashMap;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ConfigurationInteractorImpl$dimensionPixelSize$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set<Integer> $resourceIds;
    int label;
    final /* synthetic */ ConfigurationInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigurationInteractorImpl$dimensionPixelSize$2(Set<Integer> set, ConfigurationInteractorImpl configurationInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.$resourceIds = set;
        this.this$0 = configurationInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ConfigurationInteractorImpl$dimensionPixelSize$2(this.$resourceIds, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigurationInteractorImpl$dimensionPixelSize$2) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Set<Integer> set = this.$resourceIds;
        ConfigurationInteractorImpl configurationInteractorImpl = this.this$0;
        int mapCapacity = MapsKt__MapsJVMKt.mapCapacity(CollectionsKt__IterablesKt.collectionSizeOrDefault(set, 10));
        if (mapCapacity < 16) {
            mapCapacity = 16;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(mapCapacity);
        for (Object obj2 : set) {
            linkedHashMap.put(obj2, new Integer(((ConfigurationRepositoryImpl) configurationInteractorImpl.repository).context.getResources().getDimensionPixelSize(((Number) obj2).intValue())));
        }
        return linkedHashMap;
    }
}
