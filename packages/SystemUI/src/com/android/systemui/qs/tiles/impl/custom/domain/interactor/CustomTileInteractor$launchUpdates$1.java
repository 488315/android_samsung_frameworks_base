package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.UserHandle;
import android.service.quicksettings.Tile;
import com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class CustomTileInteractor$launchUpdates$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomTileInteractor this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor$launchUpdates$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ CustomTileInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CustomTileInteractor customTileInteractor, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.this$0 = customTileInteractor;
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$user, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((Tile) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x006d, code lost:
        
            if (r7 == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L28
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r8)
                goto L70
            L10:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L18:
                java.lang.Object r1 = r7.L$2
                android.service.quicksettings.Tile r1 = (android.service.quicksettings.Tile) r1
                java.lang.Object r3 = r7.L$1
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                java.lang.Object r4 = r7.L$0
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository r4 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository) r4
                kotlin.ResultKt.throwOnFailure(r8)
                goto L4b
            L28:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                r1 = r8
                android.service.quicksettings.Tile r1 = (android.service.quicksettings.Tile) r1
                com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor r8 = r7.this$0
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository r4 = r8.customTileRepository
                android.os.UserHandle r8 = r7.$user
                r7.L$0 = r4
                r7.L$1 = r8
                r7.L$2 = r1
                r7.label = r3
                r3 = r4
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r3 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl) r3
                java.lang.Object r3 = r3.isTileActive(r7)
                if (r3 != r0) goto L48
                goto L6f
            L48:
                r6 = r3
                r3 = r8
                r8 = r6
            L4b:
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                r5 = 0
                r7.L$0 = r5
                r7.L$1 = r5
                r7.L$2 = r5
                r7.label = r2
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r4 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl) r4
                r4.getClass()
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$$ExternalSyntheticLambda1 r2 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$$ExternalSyntheticLambda1
                r2.<init>(r1)
                java.lang.Object r7 = r4.updateTile(r3, r8, r2, r7)
                if (r7 != r0) goto L6b
                goto L6d
            L6b:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
            L6d:
                if (r7 != r0) goto L70
            L6f:
                return r0
            L70:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor$launchUpdates$1.AnonymousClass1.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor$launchUpdates$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ UserHandle $user;
        /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;
        final /* synthetic */ CustomTileInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(CustomTileInteractor customTileInteractor, UserHandle userHandle, Continuation continuation) {
            super(2, continuation);
            this.this$0 = customTileInteractor;
            this.$user = userHandle;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, this.$user, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CustomTileDefaults) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:17:0x0076, code lost:
        
            if (r7 == r0) goto L22;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r8) {
            /*
                r7 = this;
                kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                int r1 = r7.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L28
                if (r1 == r3) goto L18
                if (r1 != r2) goto L10
                kotlin.ResultKt.throwOnFailure(r8)
                goto L79
            L10:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r8)
                throw r7
            L18:
                java.lang.Object r1 = r7.L$2
                com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults r1 = (com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults) r1
                java.lang.Object r3 = r7.L$1
                android.os.UserHandle r3 = (android.os.UserHandle) r3
                java.lang.Object r4 = r7.L$0
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository r4 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository) r4
                kotlin.ResultKt.throwOnFailure(r8)
                goto L4b
            L28:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                r1 = r8
                com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults r1 = (com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults) r1
                com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor r8 = r7.this$0
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepository r4 = r8.customTileRepository
                android.os.UserHandle r8 = r7.$user
                r7.L$0 = r4
                r7.L$1 = r8
                r7.L$2 = r1
                r7.label = r3
                r3 = r4
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r3 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl) r3
                java.lang.Object r3 = r3.isTileActive(r7)
                if (r3 != r0) goto L48
                goto L78
            L48:
                r6 = r3
                r3 = r8
                r8 = r6
            L4b:
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                r5 = 0
                r7.L$0 = r5
                r7.L$1 = r5
                r7.L$2 = r5
                r7.label = r2
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl r4 = (com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl) r4
                r4.getClass()
                boolean r2 = r1 instanceof com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults.Result
                if (r2 == 0) goto L74
                com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$$ExternalSyntheticLambda0 r2 = new com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileRepositoryImpl$$ExternalSyntheticLambda0
                com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults$Result r1 = (com.android.systemui.qs.tiles.impl.custom.data.model.CustomTileDefaults.Result) r1
                r2.<init>()
                java.lang.Object r7 = r4.updateTile(r3, r8, r2, r7)
                if (r7 != r0) goto L71
                goto L76
            L71:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                goto L76
            L74:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
            L76:
                if (r7 != r0) goto L79
            L78:
                return r0
            L79:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileInteractor$launchUpdates$1.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomTileInteractor$launchUpdates$1(CustomTileInteractor customTileInteractor, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = customTileInteractor;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CustomTileInteractor$launchUpdates$1 customTileInteractor$launchUpdates$1 = new CustomTileInteractor$launchUpdates$1(this.this$0, this.$user, continuation);
        customTileInteractor$launchUpdates$1.L$0 = obj;
        return customTileInteractor$launchUpdates$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CustomTileInteractor$launchUpdates$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CustomTileInteractor customTileInteractor = this.this$0;
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(customTileInteractor.tileUpdates, new AnonymousClass1(customTileInteractor, this.$user, null)), this.this$0.backgroundContext), coroutineScope);
        CustomTileDefaultsRepository customTileDefaultsRepository = this.this$0.defaultsRepository;
        FlowKt.launchIn(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new CustomTileDefaultsRepositoryImpl$defaults$$inlined$map$1(new CustomTileDefaultsRepositoryImpl$defaults$$inlined$filter$1(((CustomTileDefaultsRepositoryImpl) customTileDefaultsRepository).defaults, this.$user)), new AnonymousClass2(this.this$0, this.$user, null)), this.this$0.backgroundContext), coroutineScope);
        return Unit.INSTANCE;
    }
}
