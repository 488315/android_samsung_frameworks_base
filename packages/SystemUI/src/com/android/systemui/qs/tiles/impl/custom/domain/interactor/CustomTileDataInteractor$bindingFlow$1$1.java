package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomTileDataInteractor$bindingFlow$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileLogger $qsTileLogger;
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomTileDataInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        int label;
        final /* synthetic */ CustomTileDataInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CustomTileDataInteractor customTileDataInteractor, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.this$0 = customTileDataInteractor;
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, this.$user, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            CustomTileDefaultsRepository customTileDefaultsRepository = this.this$0.defaultsRepository;
            UserHandle userHandle = this.$user;
            userHandle.getClass();
            ((CustomTileDefaultsRepositoryImpl) customTileDefaultsRepository).defaultsRequests.tryEmit(new CustomTileDefaultsRepositoryImpl.DefaultsRequest(userHandle, this.this$0.tileSpec.componentName, true));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileDataInteractor$bindingFlow$1$1(CustomTileDataInteractor customTileDataInteractor, UserHandle userHandle, QSTileLogger qSTileLogger, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileDataInteractor;
        this.$user = userHandle;
        this.$qsTileLogger = qSTileLogger;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomTileDataInteractor$bindingFlow$1$1 customTileDataInteractor$bindingFlow$1$1 = new CustomTileDataInteractor$bindingFlow$1$1(this.this$0, this.$user, this.$qsTileLogger, continuation);
        customTileDataInteractor$bindingFlow$1$1.L$0 = obj;
        return customTileDataInteractor$bindingFlow$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileDataInteractor$bindingFlow$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x00db, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r11, r10) == r0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00dd, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00c9, code lost:
    
        if (((kotlinx.coroutines.channels.ChannelCoroutine) r1)._channel.send(r11, r10) == r0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0097, code lost:
    
        if (r11.bindOnStart(r10) == r0) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x006f, code lost:
    
        if (r11.initForUser(r7, r10) == r0) goto L32;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
