package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class CustomTileDataInteractor$bindingFlow$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomTileDataInteractor this$0;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            CustomTileDataInteractor customTileDataInteractor = this.this$0;
            ((CustomTileDefaultsRepositoryImpl) customTileDataInteractor.defaultsRepository).defaultsRequests.tryEmit(new CustomTileDefaultsRepositoryImpl.DefaultsRequest(this.$user, customTileDataInteractor.tileSpec.componentName, true));
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileDataInteractor$bindingFlow$1$1(CustomTileDataInteractor customTileDataInteractor, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileDataInteractor;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomTileDataInteractor$bindingFlow$1$1 customTileDataInteractor$bindingFlow$1$1 = new CustomTileDataInteractor$bindingFlow$1$1(this.this$0, this.$user, continuation);
        customTileDataInteractor$bindingFlow$1$1.L$0 = obj;
        return customTileDataInteractor$bindingFlow$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileDataInteractor$bindingFlow$1$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00bb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00a9 A[RETURN] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r9.label
            r2 = 0
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            if (r1 == 0) goto L39
            if (r1 == r6) goto L31
            if (r1 == r5) goto L29
            if (r1 == r4) goto L20
            if (r1 != r3) goto L18
            kotlin.ResultKt.throwOnFailure(r10)
            goto Lbc
        L18:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L20:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto Laa
        L29:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L7b
        L31:
            java.lang.Object r1 = r9.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L6c
        L39:
            kotlin.ResultKt.throwOnFailure(r10)
            java.lang.Object r10 = r9.L$0
            r1 = r10
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor r10 = r9.this$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor r10 = r10.serviceInteractor
            android.os.UserHandle r7 = r9.$user
            android.os.UserHandle r8 = r10.currentUser
            boolean r8 = r7.equals(r8)
            if (r8 == 0) goto L50
            goto L5b
        L50:
            r10.currentUser = r7
            kotlinx.coroutines.Job r7 = r10.destructionJob
            if (r7 == 0) goto L59
            r7.cancel(r2)
        L59:
            r10.tileServiceManager = r2
        L5b:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor r10 = r9.this$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor r10 = r10.customTileInteractor
            android.os.UserHandle r7 = r9.$user
            r9.L$0 = r1
            r9.label = r6
            java.lang.Object r10 = r10.initForUser(r7, r9)
            if (r10 != r0) goto L6c
            return r0
        L6c:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor r10 = r9.this$0
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileServiceInteractor r10 = r10.serviceInteractor
            r9.L$0 = r1
            r9.label = r5
            java.lang.Object r10 = r10.bindOnStart(r9)
            if (r10 != r0) goto L7b
            return r0
        L7b:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor r10 = r9.this$0
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepository r10 = r10.packageUpdatesRepository
            android.os.UserHandle r5 = r9.$user
            com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl r10 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl) r10
            kotlinx.coroutines.flow.Flow r10 = r10.getPackageChangesForUser(r5)
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1$1 r5 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1$1
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor r6 = r9.this$0
            android.os.UserHandle r7 = r9.$user
            r5.<init>(r6, r7, r2)
            kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 r6 = new kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1
            r6.<init>(r10, r5)
            kotlinx.coroutines.flow.FlowKt.launchIn(r6, r1)
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            r9.L$0 = r1
            r9.label = r4
            r4 = r1
            kotlinx.coroutines.channels.ChannelCoroutine r4 = (kotlinx.coroutines.channels.ChannelCoroutine) r4
            kotlinx.coroutines.channels.Channel r4 = r4._channel
            java.lang.Object r10 = r4.send(r10, r9)
            if (r10 != r0) goto Laa
            return r0
        Laa:
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1$2 r10 = new com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1$2
            com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor r4 = r9.this$0
            r10.<init>()
            r9.L$0 = r2
            r9.label = r3
            java.lang.Object r9 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r10, r9)
            if (r9 != r0) goto Lbc
            return r0
        Lbc:
            kotlin.Unit r9 = kotlin.Unit.INSTANCE
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
