package com.android.systemui.qs.composefragment.viewmodel;

import androidx.compose.runtime.SnapshotStateKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.media.controls.ui.view.MediaHost$$ExternalSyntheticLambda0;
import com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class QSFragmentComposeViewModel$onActivated$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ QSFragmentComposeViewModel this$0;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
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
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                this.label = 1;
                qSFragmentComposeViewModel.getClass();
                Object collect = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(qSFragmentComposeViewModel, 6)).collect(new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateSquishinessInteractor$3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        QSFragmentComposeViewModel.this.squishinessInteractor.repository._squishiness.updateState(null, Float.valueOf(((Number) obj2).floatValue()));
                        return Unit.INSTANCE;
                    }
                }, this);
                if (collect != coroutineSingletons) {
                    collect = Unit.INSTANCE;
                }
                if (collect == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.this$0, continuation);
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
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                this.label = 1;
                qSFragmentComposeViewModel.getClass();
                Object collect = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(qSFragmentComposeViewModel, 0)).collect(new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateQqsMediaExpansion$3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        QSFragmentComposeViewModel.this.qqsMediaHost.setExpansion(((Number) obj2).floatValue());
                        return Unit.INSTANCE;
                    }
                }, this);
                if (collect != coroutineSingletons) {
                    collect = Unit.INSTANCE;
                }
                if (collect == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                this.label = 1;
                qSFragmentComposeViewModel.getClass();
                Object collect = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(qSFragmentComposeViewModel, 7)).collect(new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateMediaSquishiness$3
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        float floatValue = ((Number) obj2).floatValue();
                        MediaHost.MediaHostStateHolder mediaHostStateHolder = QSFragmentComposeViewModel.this.qsMediaHost.state;
                        if (!Float.valueOf(floatValue).equals(Float.valueOf(mediaHostStateHolder.squishFraction))) {
                            mediaHostStateHolder.squishFraction = floatValue;
                            MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = mediaHostStateHolder.changedListener;
                            if (mediaHost$$ExternalSyntheticLambda0 != null) {
                                mediaHost$$ExternalSyntheticLambda0.invoke();
                            }
                        }
                        return Unit.INSTANCE;
                    }
                }, this);
                if (collect != coroutineSingletons) {
                    collect = Unit.INSTANCE;
                }
                if (collect == coroutineSingletons) {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass4(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass4(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                this.label = 1;
                qSFragmentComposeViewModel.getClass();
                Object coroutineScope = CoroutineScopeKt.coroutineScope(new QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2(qSFragmentComposeViewModel, null), this);
                if (coroutineScope != obj2) {
                    coroutineScope = Unit.INSTANCE;
                }
                if (coroutineScope == obj2) {
                    return obj2;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass5(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass5(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Hydrator hydrator = this.this$0.hydrator;
                this.label = 1;
                if (hydrator.activate(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$6, reason: invalid class name */
    final class AnonymousClass6 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass6(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass6(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass6) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                QuickSettingsContainerViewModel quickSettingsContainerViewModel = this.this$0.containerViewModel;
                this.label = 1;
                if (quickSettingsContainerViewModel.activate(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$7, reason: invalid class name */
    final class AnonymousClass7 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass7(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass7(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass7) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                QuickQuickSettingsViewModel quickQuickSettingsViewModel = this.this$0.quickQuickSettingsViewModel;
                this.label = 1;
                if (quickQuickSettingsViewModel.activate(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$8, reason: invalid class name */
    final class AnonymousClass8 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass8(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass8(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel = this.this$0.qqsMediaInRowViewModel;
                this.label = 1;
                if (mediaInRowInLandscapeViewModel.activate(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$9, reason: invalid class name */
    final class AnonymousClass9 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ QSFragmentComposeViewModel this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass9(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
            super(2, continuation);
            this.this$0 = qSFragmentComposeViewModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass9(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass9) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                MediaInRowInLandscapeViewModel mediaInRowInLandscapeViewModel = this.this$0.qsMediaInRowViewModel;
                this.label = 1;
                if (mediaInRowInLandscapeViewModel.activate(this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QSFragmentComposeViewModel$onActivated$2(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
        super(2, continuation);
        this.this$0 = qSFragmentComposeViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        QSFragmentComposeViewModel$onActivated$2 qSFragmentComposeViewModel$onActivated$2 = new QSFragmentComposeViewModel$onActivated$2(this.this$0, continuation);
        qSFragmentComposeViewModel$onActivated$2.L$0 = obj;
        return qSFragmentComposeViewModel$onActivated$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((QSFragmentComposeViewModel$onActivated$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 7);
            QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
            if (qSFragmentComposeViewModel.usingMedia) {
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(qSFragmentComposeViewModel, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(this.this$0, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(this.this$0, null), 7);
            }
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(this.this$0, null), 7);
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(this.this$0, null), 7);
            this.label = 1;
            if (DelayKt.awaitCancellation(this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
