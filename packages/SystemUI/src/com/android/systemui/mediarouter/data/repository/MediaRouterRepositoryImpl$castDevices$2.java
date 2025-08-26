package com.android.systemui.mediarouter.data.repository;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.policy.CastDevice;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
final class MediaRouterRepositoryImpl$castDevices$2 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaRouterRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaRouterRepositoryImpl$castDevices$2(MediaRouterRepositoryImpl mediaRouterRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaRouterRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaRouterRepositoryImpl$castDevices$2 mediaRouterRepositoryImpl$castDevices$2 = new MediaRouterRepositoryImpl$castDevices$2(this.this$0, continuation);
        mediaRouterRepositoryImpl$castDevices$2.L$0 = obj;
        return mediaRouterRepositoryImpl$castDevices$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaRouterRepositoryImpl$castDevices$2) create((List) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        List list = (List) this.L$0;
        list.getClass();
        List list2 = list;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it = list2.iterator();
        while (it.hasNext()) {
            arrayList.add(((CastDevice) it.next()).shortLogString);
        }
        String string = arrayList.toString();
        LogBuffer logBuffer = this.this$0.logger;
        LogMessage logMessageObtain = logBuffer.obtain("MediaRouterRepo", LogLevel.INFO, new MediaRouterRepositoryImpl$castDevices$2$$ExternalSyntheticLambda0(), null);
        ((LogMessageImpl) logMessageObtain).str1 = string;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
