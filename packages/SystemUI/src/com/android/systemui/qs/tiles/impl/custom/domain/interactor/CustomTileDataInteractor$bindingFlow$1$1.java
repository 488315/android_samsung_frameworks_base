package com.android.systemui.qs.tiles.impl.custom.domain.interactor;

import android.os.RemoteException;
import android.os.UserHandle;
import com.android.systemui.qs.tiles.base.shared.logging.QSTileLogger;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepository;
import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTilePackageUpdatesRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
final class CustomTileDataInteractor$bindingFlow$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ QSTileLogger $qsTileLogger;
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CustomTileDataInteractor this$0;

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

    /* JADX WARN: Code restructure failed: missing block: B:31:0x00db, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r11, r10) != r0) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00cc A[PHI: r1
      0x00cc: PHI (r1v5 kotlinx.coroutines.channels.ProducerScope) = (r1v4 kotlinx.coroutines.channels.ProducerScope), (r1v11 kotlinx.coroutines.channels.ProducerScope) binds: [B:28:0x00c9, B:11:0x0020] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        ProducerScope producerScope;
        Unit unit;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            producerScope = (ProducerScope) this.L$0;
            CustomTileServiceInteractor customTileServiceInteractor = this.this$0.serviceInteractor;
            UserHandle userHandle = this.$user;
            userHandle.getClass();
            if (!userHandle.equals(customTileServiceInteractor.currentUser)) {
                customTileServiceInteractor.currentUser = userHandle;
                StandaloneCoroutine standaloneCoroutine = customTileServiceInteractor.destructionJob;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                customTileServiceInteractor.tileServiceManager = null;
            }
            CustomTileInteractor customTileInteractor = this.this$0.customTileInteractor;
            UserHandle userHandle2 = this.$user;
            userHandle2.getClass();
            this.L$0 = producerScope;
            this.label = 1;
            if (customTileInteractor.initForUser(userHandle2, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i == 2) {
                producerScope = (ProducerScope) this.L$0;
                ResultKt.throwOnFailure(obj);
                CustomTilePackageUpdatesRepository customTilePackageUpdatesRepository = this.this$0.packageUpdatesRepository;
                UserHandle userHandle3 = this.$user;
                userHandle3.getClass();
                FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(((CustomTilePackageUpdatesRepositoryImpl) customTilePackageUpdatesRepository).getPackageChangesForUser(userHandle3), new AnonymousClass1(this.this$0, this.$user, null)), producerScope);
                unit = Unit.INSTANCE;
                this.L$0 = producerScope;
                this.label = 3;
                if (((ChannelCoroutine) producerScope)._channel.send(unit, this) != coroutineSingletons) {
                    final CustomTileDataInteractor customTileDataInteractor = this.this$0;
                    Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            CustomTileServiceInteractor customTileServiceInteractor2 = customTileDataInteractor.serviceInteractor;
                            customTileServiceInteractor2.getClass();
                            try {
                                CustomTileUserActionInteractor customTileUserActionInteractor = (CustomTileUserActionInteractor) customTileServiceInteractor2.userActionInteractor.get();
                                customTileUserActionInteractor.lastClickedExpandable.set(null);
                                customTileServiceInteractor2.getTileServiceManager().mStateManager.onStopListening();
                                customTileUserActionInteractor.revokeToken(false);
                                synchronized (customTileUserActionInteractor.token) {
                                    customTileUserActionInteractor.isShowingDialog = false;
                                    Unit unit2 = Unit.INSTANCE;
                                }
                                customTileServiceInteractor2.getTileServiceManager().setBindRequested(false);
                            } catch (RemoteException e) {
                                customTileServiceInteractor2.qsTileLogger.logError(customTileServiceInteractor2.tileSpec, "Unbinding failed", e);
                            }
                            return Unit.INSTANCE;
                        }
                    };
                    this.L$0 = null;
                    this.label = 4;
                }
                return coroutineSingletons;
            }
            if (i != 3) {
                if (i != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            final CustomTileDataInteractor customTileDataInteractor2 = this.this$0;
            Function0 function02 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.custom.domain.interactor.CustomTileDataInteractor$bindingFlow$1$1.2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    CustomTileServiceInteractor customTileServiceInteractor2 = customTileDataInteractor2.serviceInteractor;
                    customTileServiceInteractor2.getClass();
                    try {
                        CustomTileUserActionInteractor customTileUserActionInteractor = (CustomTileUserActionInteractor) customTileServiceInteractor2.userActionInteractor.get();
                        customTileUserActionInteractor.lastClickedExpandable.set(null);
                        customTileServiceInteractor2.getTileServiceManager().mStateManager.onStopListening();
                        customTileUserActionInteractor.revokeToken(false);
                        synchronized (customTileUserActionInteractor.token) {
                            customTileUserActionInteractor.isShowingDialog = false;
                            Unit unit2 = Unit.INSTANCE;
                        }
                        customTileServiceInteractor2.getTileServiceManager().setBindRequested(false);
                    } catch (RemoteException e) {
                        customTileServiceInteractor2.qsTileLogger.logError(customTileServiceInteractor2.tileSpec, "Unbinding failed", e);
                    }
                    return Unit.INSTANCE;
                }
            };
            this.L$0 = null;
            this.label = 4;
        }
        this.$qsTileLogger.logInfo("onBindingFlow for user:" + this.$user, this.this$0.tileSpec);
        CustomTileServiceInteractor customTileServiceInteractor2 = this.this$0.serviceInteractor;
        this.L$0 = producerScope;
        this.label = 2;
        if (customTileServiceInteractor2.bindOnStart(this) != coroutineSingletons) {
            CustomTilePackageUpdatesRepository customTilePackageUpdatesRepository2 = this.this$0.packageUpdatesRepository;
            UserHandle userHandle32 = this.$user;
            userHandle32.getClass();
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(((CustomTilePackageUpdatesRepositoryImpl) customTilePackageUpdatesRepository2).getPackageChangesForUser(userHandle32), new AnonymousClass1(this.this$0, this.$user, null)), producerScope);
            unit = Unit.INSTANCE;
            this.L$0 = producerScope;
            this.label = 3;
            if (((ChannelCoroutine) producerScope)._channel.send(unit, this) != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }
}
