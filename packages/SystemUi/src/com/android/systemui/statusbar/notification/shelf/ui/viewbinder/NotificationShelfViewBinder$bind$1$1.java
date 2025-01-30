package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Function;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1", m277f = "NotificationShelfViewBinder.kt", m278l = {88}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class NotificationShelfViewBinder$bind$1$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ NotificationShelf $shelf;
    final /* synthetic */ NotificationShelf $this_apply;
    final /* synthetic */ NotificationShelfViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @DebugMetadata(m276c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1", m277f = "NotificationShelfViewBinder.kt", m278l = {95}, m279m = "invokeSuspend")
    /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1 */
    final class C29101 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationShelf $shelf;
        final /* synthetic */ NotificationShelf $this_apply;
        final /* synthetic */ NotificationShelfViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$1", m277f = "NotificationShelfViewBinder.kt", m278l = {90}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationShelf $this_apply;
            final /* synthetic */ NotificationShelfViewModel $viewModel;
            int label;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$1$1, reason: invalid class name and collision with other inner class name */
            public final /* synthetic */ class C48781 implements FlowCollector, FunctionAdapter {
                public final /* synthetic */ NotificationShelf $tmp0;

                public C48781(NotificationShelf notificationShelf) {
                    this.$tmp0 = notificationShelf;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    NotificationShelf notificationShelf = this.$tmp0;
                    if (notificationShelf.mShelfRefactorFlagEnabled) {
                        notificationShelf.mCanModifyColorOfNotifications = booleanValue;
                    }
                    Unit unit = Unit.INSTANCE;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    return unit;
                }

                public final boolean equals(Object obj) {
                    if ((obj instanceof FlowCollector) && (obj instanceof FunctionAdapter)) {
                        return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
                    }
                    return false;
                }

                @Override // kotlin.jvm.internal.FunctionAdapter
                public final Function getFunctionDelegate() {
                    return new AdaptedFunctionReference(2, this.$tmp0, NotificationShelf.class, "setCanModifyColorOfNotifications", "setCanModifyColorOfNotifications(Z)V", 4);
                }

                public final int hashCode() {
                    return getFunctionDelegate().hashCode();
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, Continuation<? super AnonymousClass1> continuation) {
                super(2, continuation);
                this.$viewModel = notificationShelfViewModel;
                this.$this_apply = notificationShelf;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$viewModel, this.$this_apply, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isShelfStatic = this.$viewModel.interactor.isShelfStatic();
                    Flow flow = new Flow() { // from class: com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1

                        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2 */
                        public final class C29112 implements FlowCollector {
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                            @DebugMetadata(m276c = "com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2", m277f = "NotificationShelfViewModel.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                            /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                            public final class AnonymousClass1 extends ContinuationImpl {
                                Object L$0;
                                int label;
                                /* synthetic */ Object result;

                                public AnonymousClass1(Continuation continuation) {
                                    super(continuation);
                                }

                                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                                public final Object invokeSuspend(Object obj) {
                                    this.result = obj;
                                    this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                                    return C29112.this.emit(null, this);
                                }
                            }

                            public C29112(FlowCollector flowCollector) {
                                this.$this_unsafeFlow = flowCollector;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                            /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final Object emit(Object obj, Continuation continuation) {
                                AnonymousClass1 anonymousClass1;
                                int i;
                                if (continuation instanceof AnonymousClass1) {
                                    anonymousClass1 = (AnonymousClass1) continuation;
                                    int i2 = anonymousClass1.label;
                                    if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                        anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                        Object obj2 = anonymousClass1.result;
                                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                        i = anonymousClass1.label;
                                        if (i != 0) {
                                            ResultKt.throwOnFailure(obj2);
                                            Boolean valueOf = Boolean.valueOf(!((Boolean) obj).booleanValue());
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        } else {
                                            if (i != 1) {
                                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                            }
                                            ResultKt.throwOnFailure(obj2);
                                        }
                                        return Unit.INSTANCE;
                                    }
                                }
                                anonymousClass1 = new AnonymousClass1(continuation);
                                Object obj22 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                }
                                return Unit.INSTANCE;
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                            Object collect = Flow.this.collect(new C29112(flowCollector), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    };
                    C48781 c48781 = new C48781(this.$this_apply);
                    this.label = 1;
                    if (flow.collect(c48781, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        @DebugMetadata(m276c = "com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$2", m277f = "NotificationShelfViewBinder.kt", m278l = {94}, m279m = "invokeSuspend")
        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$2, reason: invalid class name */
        final class AnonymousClass2 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationShelf $this_apply;
            final /* synthetic */ NotificationShelfViewModel $viewModel;
            int label;

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$1$1$1$2$1, reason: invalid class name */
            public final /* synthetic */ class AnonymousClass1 implements FlowCollector, FunctionAdapter {
                public final /* synthetic */ NotificationShelf $tmp0;

                public AnonymousClass1(NotificationShelf notificationShelf) {
                    this.$tmp0 = notificationShelf;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    NotificationShelf notificationShelf = this.$tmp0;
                    if (notificationShelf.mShelfRefactorFlagEnabled) {
                        notificationShelf.mCanInteract = booleanValue;
                        notificationShelf.updateInteractiveness();
                    }
                    Unit unit = Unit.INSTANCE;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    return unit;
                }

                public final boolean equals(Object obj) {
                    if ((obj instanceof FlowCollector) && (obj instanceof FunctionAdapter)) {
                        return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
                    }
                    return false;
                }

                @Override // kotlin.jvm.internal.FunctionAdapter
                public final Function getFunctionDelegate() {
                    return new AdaptedFunctionReference(2, this.$tmp0, NotificationShelf.class, "setCanInteract", "setCanInteract(Z)V", 4);
                }

                public final int hashCode() {
                    return getFunctionDelegate().hashCode();
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass2(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, Continuation<? super AnonymousClass2> continuation) {
                super(2, continuation);
                this.$viewModel = notificationShelfViewModel;
                this.$this_apply = notificationShelf;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass2(this.$viewModel, this.$this_apply, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    Flow flow = ((KeyguardRepositoryImpl) this.$viewModel.interactor.keyguardRepository).isKeyguardShowing;
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$this_apply);
                    this.label = 1;
                    if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C29101(NotificationShelf notificationShelf, NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf2, Continuation<? super C29101> continuation) {
            super(2, continuation);
            this.$shelf = notificationShelf;
            this.$viewModel = notificationShelfViewModel;
            this.$this_apply = notificationShelf2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C29101 c29101 = new C29101(this.$shelf, this.$viewModel, this.$this_apply, continuation);
            c29101.L$0 = obj;
            return c29101;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C29101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.$viewModel, this.$this_apply, null), 3);
                BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass2(this.$viewModel, this.$this_apply, null), 3);
                NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                NotificationShelf notificationShelf = this.$shelf;
                NotificationShelfViewModel notificationShelfViewModel = this.$viewModel;
                this.label = 1;
                if (NotificationShelfViewBinder.access$registerViewListenersWhileAttached(notificationShelfViewBinder, notificationShelf, notificationShelfViewModel, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotificationShelfViewBinder$bind$1$1(NotificationShelf notificationShelf, NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf2, Continuation<? super NotificationShelfViewBinder$bind$1$1> continuation) {
        super(3, continuation);
        this.$shelf = notificationShelf;
        this.$viewModel = notificationShelfViewModel;
        this.$this_apply = notificationShelf2;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        NotificationShelfViewBinder$bind$1$1 notificationShelfViewBinder$bind$1$1 = new NotificationShelfViewBinder$bind$1$1(this.$shelf, this.$viewModel, this.$this_apply, (Continuation) obj3);
        notificationShelfViewBinder$bind$1$1.L$0 = (LifecycleOwner) obj;
        return notificationShelfViewBinder$bind$1$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            C29101 c29101 = new C29101(this.$shelf, this.$viewModel, this.$this_apply, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, c29101, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
