package com.android.systemui.qs.composefragment.viewmodel;

import android.content.res.Resources;
import android.graphics.Rect;
import android.util.IndentingPrintWriter;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.lifecycle.LifecycleCoroutineScope;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.classifier.domain.interactor.FalsingInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.media.controls.ui.view.MediaHost;
import com.android.systemui.media.controls.ui.view.MediaHost$$ExternalSyntheticLambda0;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.panels.domain.interactor.TileSquishinessInteractor;
import com.android.systemui.qs.panels.ui.viewmodel.InFirstPageViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.MediaInRowInLandscapeViewModel;
import com.android.systemui.qs.panels.ui.viewmodel.QuickQuickSettingsViewModel;
import com.android.systemui.qs.ui.viewmodel.QuickSettingsContainerViewModel;
import com.android.systemui.scene.shared.model.Overlays;
import com.android.systemui.shade.LargeScreenHeaderHelper;
import com.android.systemui.shade.PanelTransitionStateChangeEvent;
import com.android.systemui.shade.PanelTransitionStateListener;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.disableflags.domain.interactor.DisableFlagsInteractor;
import com.android.systemui.statusbar.disableflags.shared.model.DisableFlagsModel;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.LargeScreenUtils;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class QSFragmentComposeViewModel extends ExclusiveActivatable implements Dumpable {
    public final State alphaProgress$delegate;
    public final State anyShadeExpanded$delegate;
    public Runnable collapseExpandAccessibilityAction;
    public final State collapsedLandscapeMedia$delegate;
    public final QuickSettingsContainerViewModel containerViewModel;
    public final State expansionState$delegate;
    public final FalsingInteractor falsingInteractor;
    public final FooterActionsController footerActionsController;
    public final FooterActionsViewModel footerActionsViewModel;
    public final State forceQs$delegate;
    public final State headerTranslation$delegate;
    public final MutableState heightOverride$delegate;
    public final Hydrator hydrator;
    public final InFirstPageViewModel inFirstPageViewModel;
    public final State isBypassEnabled$delegate;
    public final State isEditing$delegate;
    public final State isInBouncerTransit$delegate;
    public final MutableState isInSplitShade$delegate;
    public final State isNotTransitioning$delegate;
    public final State isQsEnabled$delegate;
    public final MutableState isQsExpanded$delegate;
    public final State isQsFullyCollapsed$delegate;
    public final State isQsFullyExpanded$delegate;
    public final MutableState isQsVisible$delegate;
    public final MutableState isSmallScreen$delegate;
    public final MutableState isStackScrollerOverscrolling$delegate;
    public final MutableState isTransitioningToFullShade$delegate;
    public final LargeScreenHeaderHelper largeScreenHeaderHelper;
    public final LargeScreenShadeInterpolator largeScreenShadeInterpolator;
    public final MutableState lockscreenToShadeProgress$delegate;
    public final State mediaSquishiness$delegate;
    public final MutableState overScrollAmount$delegate;
    public final MutableState panelExpansionFraction$delegate;
    public final MutableState panelState$delegate;
    public final MutableState proposedTranslation$delegate;
    public final State qqsBottomPadding$delegate;
    public final State qqsHeaderHeight$delegate;
    public final MutableState qqsHeight$delegate;
    public final MediaHost qqsMediaHost;
    public final MediaInRowInLandscapeViewModel qqsMediaInRowViewModel;
    public final State qqsMediaVisible$delegate;
    public final MutableState qsExpansion$delegate;
    public final MediaHost qsMediaHost;
    public final MediaInRowInLandscapeViewModel qsMediaInRowViewModel;
    public final State qsMediaTranslationY$delegate;
    public final State qsMediaVisible$delegate;
    public final MutableState qsScrollHeight$delegate;
    public final State qsScrollTranslationY$delegate;
    public final QuickQuickSettingsViewModel quickQuickSettingsViewModel;
    public final Resources resources;
    public final SecPanelSplitHelper secPanelSplitHelper;
    public final State shouldApplySquishinessToMedia$delegate;
    public final MutableState shouldUpdateSquishinessOnMedia$delegate;
    public final State showCollapsedOnKeyguard$delegate;
    public final MutableState squishinessFraction$delegate;
    public final TileSquishinessInteractor squishinessInteractor;
    public final State statusBarState$delegate;
    public final SysuiStatusBarStateController sysuiStatusBarStateController;
    public final UiEventLogger uiEventLogger;
    public final boolean usingMedia;
    public final State viewAlpha$delegate;
    public final State viewTranslationY$delegate;

    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return QSFragmentComposeViewModel.this.new AnonymousClass1(continuation);
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
                final QSFragmentComposeViewModel qSFragmentComposeViewModel = QSFragmentComposeViewModel.this;
                this.L$0 = qSFragmentComposeViewModel;
                this.label = 1;
                CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt__IntrinsicsJvmKt.intercepted(this), 1);
                cancellableContinuationImpl.initCancellability();
                final PanelTransitionStateListener panelTransitionStateListener = new PanelTransitionStateListener() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$1$1$listener$1
                    @Override // com.android.systemui.shade.PanelTransitionStateListener
                    public final void onPanelTransitionStateChanged(PanelTransitionStateChangeEvent panelTransitionStateChangeEvent) {
                        ((SnapshotMutableStateImpl) qSFragmentComposeViewModel.panelState$delegate).setValue(Integer.valueOf(panelTransitionStateChangeEvent.state));
                    }
                };
                qSFragmentComposeViewModel.secPanelSplitHelper.addListener(panelTransitionStateListener);
                cancellableContinuationImpl.invokeOnCancellation(new Function1() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$1$1$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        qSFragmentComposeViewModel.secPanelSplitHelper.removeListener(panelTransitionStateListener);
                        return Unit.INSTANCE;
                    }
                });
                if (cancellableContinuationImpl.getResult() == coroutineSingletons) {
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

    public interface Factory {
        QSFragmentComposeViewModel create(LifecycleCoroutineScope lifecycleCoroutineScope);
    }

    public final class QSExpansionState {
        public final float progress;

        public QSExpansionState(float f) {
            this.progress = f;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof QSExpansionState) && Float.compare(this.progress, ((QSExpansionState) obj).progress) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.progress);
        }

        public final String toString() {
            return "QSExpansionState(progress=" + this.progress + ")";
        }
    }

    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$1, reason: invalid class name and case insensitive filesystem */
    final class C09741 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C09741(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return QSFragmentComposeViewModel.this.onActivated(this);
        }
    }

    /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

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
            public final Object invokeSuspend(Object obj) throws Throwable {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                    this.label = 1;
                    qSFragmentComposeViewModel.getClass();
                    Object objCollect = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(qSFragmentComposeViewModel, 6)).collect(new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateSquishinessInteractor$3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            qSFragmentComposeViewModel.squishinessInteractor.repository._squishiness.updateState(null, Float.valueOf(((Number) obj2).floatValue()));
                            return Unit.INSTANCE;
                        }
                    }, this);
                    if (objCollect != coroutineSingletons) {
                        objCollect = Unit.INSTANCE;
                    }
                    if (objCollect == coroutineSingletons) {
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

        /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$onActivated$2$2, reason: invalid class name and collision with other inner class name */
        final class C03882 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ QSFragmentComposeViewModel this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C03882(QSFragmentComposeViewModel qSFragmentComposeViewModel, Continuation continuation) {
                super(2, continuation);
                this.this$0 = qSFragmentComposeViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C03882(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C03882) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws Throwable {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                    this.label = 1;
                    qSFragmentComposeViewModel.getClass();
                    Object objCollect = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(qSFragmentComposeViewModel, 0)).collect(new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateQqsMediaExpansion$3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            qSFragmentComposeViewModel.qqsMediaHost.setExpansion(((Number) obj2).floatValue());
                            return Unit.INSTANCE;
                        }
                    }, this);
                    if (objCollect != coroutineSingletons) {
                        objCollect = Unit.INSTANCE;
                    }
                    if (objCollect == coroutineSingletons) {
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
            public final Object invokeSuspend(Object obj) throws Throwable {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    final QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                    this.label = 1;
                    qSFragmentComposeViewModel.getClass();
                    Object objCollect = SnapshotStateKt.snapshotFlow(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(qSFragmentComposeViewModel, 7)).collect(new FlowCollector() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$hydrateMediaSquishiness$3
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        public final Object emit(Object obj2, Continuation continuation) {
                            float fFloatValue = ((Number) obj2).floatValue();
                            MediaHost.MediaHostStateHolder mediaHostStateHolder = qSFragmentComposeViewModel.qsMediaHost.state;
                            if (!Float.valueOf(fFloatValue).equals(Float.valueOf(mediaHostStateHolder.squishFraction))) {
                                mediaHostStateHolder.squishFraction = fFloatValue;
                                MediaHost$$ExternalSyntheticLambda0 mediaHost$$ExternalSyntheticLambda0 = mediaHostStateHolder.changedListener;
                                if (mediaHost$$ExternalSyntheticLambda0 != null) {
                                    mediaHost$$ExternalSyntheticLambda0.invoke();
                                }
                            }
                            return Unit.INSTANCE;
                        }
                    }, this);
                    if (objCollect != coroutineSingletons) {
                        objCollect = Unit.INSTANCE;
                    }
                    if (objCollect == coroutineSingletons) {
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
                    Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new QSFragmentComposeViewModel$hydrateMediaDisappearParameters$2(qSFragmentComposeViewModel, null), this);
                    if (objCoroutineScope != obj2) {
                        objCoroutineScope = Unit.INSTANCE;
                    }
                    if (objCoroutineScope == obj2) {
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

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = QSFragmentComposeViewModel.this.new AnonymousClass2(continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
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
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(QSFragmentComposeViewModel.this, null), 7);
                QSFragmentComposeViewModel qSFragmentComposeViewModel = QSFragmentComposeViewModel.this;
                if (qSFragmentComposeViewModel.usingMedia) {
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C03882(qSFragmentComposeViewModel, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(QSFragmentComposeViewModel.this, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(QSFragmentComposeViewModel.this, null), 7);
                }
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(QSFragmentComposeViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass6(QSFragmentComposeViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass7(QSFragmentComposeViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass8(QSFragmentComposeViewModel.this, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass9(QSFragmentComposeViewModel.this, null), 7);
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

    public QSFragmentComposeViewModel(QuickSettingsContainerViewModel.Factory factory, Resources resources, QuickQuickSettingsViewModel.Factory factory2, FooterActionsViewModel.Factory factory3, FooterActionsController footerActionsController, SysuiStatusBarStateController sysuiStatusBarStateController, DeviceEntryInteractor deviceEntryInteractor, DisableFlagsInteractor disableFlagsInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, LargeScreenShadeInterpolator largeScreenShadeInterpolator, ShadeInteractor shadeInteractor, ConfigurationInteractor configurationInteractor, LargeScreenHeaderHelper largeScreenHeaderHelper, TileSquishinessInteractor tileSquishinessInteractor, FalsingInteractor falsingInteractor, InFirstPageViewModel inFirstPageViewModel, TableLogBuffer tableLogBuffer, MediaInRowInLandscapeViewModel.Factory factory4, MediaHost mediaHost, MediaHost mediaHost2, boolean z, UiEventLogger uiEventLogger, LifecycleCoroutineScope lifecycleCoroutineScope, SecPanelSplitHelper secPanelSplitHelper) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22;
        this.resources = resources;
        this.footerActionsController = footerActionsController;
        this.sysuiStatusBarStateController = sysuiStatusBarStateController;
        this.largeScreenShadeInterpolator = largeScreenShadeInterpolator;
        this.largeScreenHeaderHelper = largeScreenHeaderHelper;
        this.squishinessInteractor = tileSquishinessInteractor;
        this.falsingInteractor = falsingInteractor;
        this.inFirstPageViewModel = inFirstPageViewModel;
        this.qqsMediaHost = mediaHost;
        this.qsMediaHost = mediaHost2;
        this.usingMedia = z;
        this.uiEventLogger = uiEventLogger;
        this.secPanelSplitHelper = secPanelSplitHelper;
        QuickSettingsContainerViewModel quickSettingsContainerViewModelCreate = factory.create(true, null);
        this.containerViewModel = quickSettingsContainerViewModelCreate;
        this.quickQuickSettingsViewModel = factory2.create();
        this.qqsMediaInRowViewModel = factory4.create(1);
        this.qsMediaInRowViewModel = factory4.create(0);
        Hydrator hydrator = new Hydrator("QSFragmentComposeViewModel.hydrator", tableLogBuffer);
        this.hydrator = hydrator;
        FooterActionsViewModel footerActionsViewModelCreate = factory3.create(lifecycleCoroutineScope);
        CoroutineTracingKt.launchTraced$default(lifecycleCoroutineScope, null, null, new QSFragmentComposeViewModel$footerActionsViewModel$1$1(this, null), 7);
        this.footerActionsViewModel = footerActionsViewModelCreate;
        Boolean bool = Boolean.FALSE;
        this.isQsExpanded$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isQsVisible$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.panelState$delegate = SnapshotStateKt.mutableStateOf$default(0);
        CoroutineTracingKt.launchTraced$default(lifecycleCoroutineScope, null, null, new AnonymousClass1(null), 7);
        this.qsExpansion$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(-1.0f));
        this.isQsFullyCollapsed$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 8));
        this.panelExpansionFraction$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
        this.squishinessFraction$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(1.0f));
        ConfigurationInteractorImpl configurationInteractorImpl = (ConfigurationInteractorImpl) configurationInteractor;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = configurationInteractorImpl.onAnyConfigurationChange;
        this.qqsHeaderHeight$delegate = hydrator.hydratedStateOf("qqsHeaderHeight", 0, new Flow() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ QSFragmentComposeViewModel this$0;

                /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, QSFragmentComposeViewModel qSFragmentComposeViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = qSFragmentComposeViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        QSFragmentComposeViewModel qSFragmentComposeViewModel = this.this$0;
                        Integer num = new Integer(LargeScreenUtils.shouldUseLargeScreenShadeHeader(qSFragmentComposeViewModel.resources) ? 0 : qSFragmentComposeViewModel.largeScreenHeaderHelper.getLargeScreenHeaderHeight());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.qqsBottomPadding$delegate = hydrator.hydratedStateOf("qqsBottomPadding", Integer.valueOf(resources.getDimensionPixelSize(R.dimen.qqs_layout_padding_bottom)), configurationInteractorImpl.dimensionPixelSize(R.dimen.qqs_layout_padding_bottom));
        this.qqsHeight$delegate = SnapshotStateKt.mutableStateOf$default(1);
        this.qsScrollHeight$delegate = SnapshotStateKt.mutableStateOf$default(0);
        this.isStackScrollerOverscrolling$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.proposedTranslation$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
        Boolean boolValueOf = Boolean.valueOf(((DisableFlagsModel) disableFlagsInteractor.disableFlags.$$delegate_0.getValue()).isQuickSettingsEnabled());
        final ReadonlyStateFlow readonlyStateFlow = disableFlagsInteractor.disableFlags;
        this.isQsEnabled$delegate = hydrator.hydratedStateOf("isQsEnabled", boolValueOf, new Flow() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2

            /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((DisableFlagsModel) obj).isQuickSettingsEnabled());
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isInSplitShade$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.isTransitioningToFullShade$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.lockscreenToShadeProgress$delegate = SnapshotStateKt.mutableStateOf$default(Float.valueOf(0.0f));
        this.isSmallScreen$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.heightOverride$delegate = SnapshotStateKt.mutableStateOf$default(-1);
        this.expansionState$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 13));
        this.isQsFullyExpanded$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 14));
        this.overScrollAmount$delegate = SnapshotStateKt.mutableStateOf$default(0);
        this.viewTranslationY$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 15));
        this.qsScrollTranslationY$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 16));
        this.viewAlpha$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 1));
        Boolean boolValueOf2 = Boolean.valueOf(z);
        if (z) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSFragmentComposeViewModelKt$mediaHostVisible$2(mediaHost, null), FlowKt.callbackFlow(new QSFragmentComposeViewModelKt$mediaHostVisible$1(mediaHost, null)));
        } else {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        }
        this.qqsMediaVisible$delegate = hydrator.hydratedStateOf("qqsMediaVisible", boolValueOf2, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2);
        Boolean boolValueOf3 = Boolean.valueOf(z);
        if (z) {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSFragmentComposeViewModelKt$mediaHostVisible$2(mediaHost2, null), FlowKt.callbackFlow(new QSFragmentComposeViewModelKt$mediaHostVisible$1(mediaHost2, null)));
        } else {
            flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(bool);
        }
        this.qsMediaVisible$delegate = hydrator.hydratedStateOf("qsMediaVisible", boolValueOf3, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$22);
        this.shouldUpdateSquishinessOnMedia$delegate = SnapshotStateKt.mutableStateOf$default(bool);
        this.qsMediaTranslationY$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 2));
        this.isEditing$delegate = hydrator.hydratedStateOf(quickSettingsContainerViewModelCreate.editModeViewModel.isEditing, "isEditing");
        this.isNotTransitioning$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 3));
        Boolean boolValueOf4 = Boolean.valueOf(resources.getBoolean(R.bool.config_quickSettingsMediaLandscapeCollapsed));
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), configurationInteractorImpl.onAnyConfigurationChange);
        this.collapsedLandscapeMedia$delegate = hydrator.hydratedStateOf("collapsedLandscapeMedia", boolValueOf4, new Flow() { // from class: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3

            /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ QSFragmentComposeViewModel this$0;

                /* renamed from: com.android.systemui.qs.composefragment.viewmodel.QSFragmentComposeViewModel$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, QSFragmentComposeViewModel qSFragmentComposeViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = qSFragmentComposeViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(this.this$0.resources.getBoolean(R.bool.config_quickSettingsMediaLandscapeCollapsed));
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.shouldApplySquishinessToMedia$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 4));
        this.mediaSquishiness$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 5));
        SnapshotStateKt.mutableStateOf$default(new Rect());
        SnapshotStateKt.mutableStateOf$default(bool);
        this.statusBarState$delegate = hydrator.hydratedStateOf("statusBarState", Integer.valueOf(sysuiStatusBarStateController.getState()), new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new QSFragmentComposeViewModel$statusBarState$3(this, null), FlowConflatedKt.conflatedCallbackFlow(new QSFragmentComposeViewModel$statusBarState$2(this, null))));
        SnapshotStateKt.mutableStateOf$default(0);
        this.isBypassEnabled$delegate = hydrator.hydratedStateOf(deviceEntryInteractor.isBypassEnabled, "isBypassEnabled");
        this.showCollapsedOnKeyguard$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 9));
        this.forceQs$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 10));
        this.headerTranslation$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 11));
        this.alphaProgress$delegate = SnapshotStateKt.derivedStateOf(new QSFragmentComposeViewModel$$ExternalSyntheticLambda0(this, 12));
        Edge.Companion companion = Edge.Companion;
        this.isInBouncerTransit$delegate = hydrator.hydratedStateOf("isInBouncerTransit", bool, keyguardTransitionInteractor.isInTransition(Edge.Companion.create$default(companion, Overlays.Bouncer), Edge.Companion.create$default(companion, null, KeyguardState.PRIMARY_BOUNCER, 1)));
        this.anyShadeExpanded$delegate = hydrator.hydratedStateOf(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded(), "anyShadeExpanded");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.append("Quick Settings state").println(":");
        indentingPrintWriterAsIndenting.increaseIndent();
        try {
            Boolean bool = (Boolean) ((SnapshotMutableStateImpl) this.isQsExpanded$delegate).getValue();
            bool.booleanValue();
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isQSExpanded", bool);
            Boolean bool2 = (Boolean) ((SnapshotMutableStateImpl) this.isQsVisible$delegate).getValue();
            bool2.booleanValue();
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isQSVisible", bool2);
            Boolean bool3 = (Boolean) ((SnapshotMutableStateImpl) this.anyShadeExpanded$delegate).getValue();
            bool3.booleanValue();
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "anyShadeExpanded", bool3);
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isQSVisibleAndAnyShadeExpanded", Boolean.valueOf(isQsVisibleAndAnyShadeExpanded()));
            Boolean bool4 = (Boolean) ((SnapshotMutableStateImpl) this.isQsEnabled$delegate).getValue();
            bool4.booleanValue();
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isQSEnabled", bool4);
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isCustomizing", this.containerViewModel.editModeViewModel.isEditing.$$delegate_0.getValue());
            DumpUtilsKt.println(indentingPrintWriterAsIndenting, "inFirstPage", Boolean.valueOf(this.inFirstPageViewModel.inFirstPage));
            indentingPrintWriterAsIndenting.decreaseIndent();
            indentingPrintWriterAsIndenting.append("Expansion state").println(":");
            indentingPrintWriterAsIndenting.increaseIndent();
            try {
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qsExpansion", Float.valueOf(getQsExpansion$1()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "panelExpansionFraction", Float.valueOf(((Number) ((SnapshotMutableStateImpl) this.panelExpansionFraction$delegate).getValue()).floatValue()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "squishinessFraction", Float.valueOf(((Number) ((SnapshotMutableStateImpl) this.squishinessFraction$delegate).getValue()).floatValue()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "proposedTranslation", Float.valueOf(((Number) ((SnapshotMutableStateImpl) this.proposedTranslation$delegate).getValue()).floatValue()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "expansionState", (QSExpansionState) this.expansionState$delegate.getValue());
                Boolean bool5 = (Boolean) this.forceQs$delegate.getValue();
                bool5.booleanValue();
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "forceQS", bool5);
                indentingPrintWriterAsIndenting.append("Derived values").println(":");
                indentingPrintWriterAsIndenting.increaseIndent();
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "headerTranslation", Float.valueOf(((Number) this.headerTranslation$delegate.getValue()).floatValue()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "translationScaleY", Float.valueOf(getTranslationScaleY()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "viewTranslationY", Float.valueOf(((Number) this.viewTranslationY$delegate.getValue()).floatValue()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qsScrollTranslationY", Float.valueOf(((Number) this.qsScrollTranslationY$delegate.getValue()).floatValue()));
                DumpUtilsKt.println(indentingPrintWriterAsIndenting, "viewAlpha", Float.valueOf(((Number) this.viewAlpha$delegate.getValue()).floatValue()));
                indentingPrintWriterAsIndenting.decreaseIndent();
                indentingPrintWriterAsIndenting.decreaseIndent();
                indentingPrintWriterAsIndenting.append("Shade state").println(":");
                indentingPrintWriterAsIndenting.increaseIndent();
                try {
                    Boolean bool6 = (Boolean) ((SnapshotMutableStateImpl) this.isStackScrollerOverscrolling$delegate).getValue();
                    bool6.booleanValue();
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "stackOverscrolling", bool6);
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "overscrollAmount", Integer.valueOf(((Number) ((SnapshotMutableStateImpl) this.overScrollAmount$delegate).getValue()).intValue()));
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "statusBarState", StatusBarState.toString(getStatusBarState()));
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isKeyguardState", Boolean.valueOf(isKeyguardState$1()));
                    Boolean bool7 = (Boolean) ((SnapshotMutableStateImpl) this.isSmallScreen$delegate).getValue();
                    bool7.booleanValue();
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isSmallScreen", bool7);
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "heightOverride", ((Number) ((SnapshotMutableStateImpl) this.heightOverride$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qqsHeaderHeight", ((Number) ((SnapshotMutableStateImpl) this.qqsHeaderHeight$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qqsBottomPadding", ((Number) ((SnapshotMutableStateImpl) this.qqsBottomPadding$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "isSplitShade", Boolean.valueOf(isInSplitShade()));
                    Boolean bool8 = (Boolean) this.showCollapsedOnKeyguard$delegate.getValue();
                    bool8.booleanValue();
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "showCollapsedOnKeyguard", bool8);
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qqsHeight", ((Number) ((SnapshotMutableStateImpl) this.qqsHeight$delegate).getValue()).intValue() + "px");
                    DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qsScrollHeight", ((Number) ((SnapshotMutableStateImpl) this.qsScrollHeight$delegate).getValue()).intValue() + "px");
                    indentingPrintWriterAsIndenting.decreaseIndent();
                    indentingPrintWriterAsIndenting.append("Media").println(":");
                    indentingPrintWriterAsIndenting.increaseIndent();
                    try {
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qqsMediaVisible", Boolean.valueOf(getQqsMediaVisible()));
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qqsMediaInRow", Boolean.valueOf(this.qqsMediaInRowViewModel.getShouldMediaShowInRow()));
                        Boolean bool9 = (Boolean) ((SnapshotMutableStateImpl) this.qsMediaVisible$delegate).getValue();
                        bool9.booleanValue();
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qsMediaVisible", bool9);
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qsMediaInRow", Boolean.valueOf(this.qsMediaInRowViewModel.getShouldMediaShowInRow()));
                        Boolean bool10 = (Boolean) ((SnapshotMutableStateImpl) this.collapsedLandscapeMedia$delegate).getValue();
                        bool10.booleanValue();
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "collapsedLandscapeMedia", bool10);
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qqsMediaExpansion", Float.valueOf(getQqsMediaExpansion()));
                        Boolean bool11 = (Boolean) ((SnapshotMutableStateImpl) this.shouldUpdateSquishinessOnMedia$delegate).getValue();
                        bool11.booleanValue();
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "shouldUpdateSquishinessOnMedia", bool11);
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "mediaSquishiness", Float.valueOf(((Number) this.mediaSquishiness$delegate.getValue()).floatValue()));
                        DumpUtilsKt.println(indentingPrintWriterAsIndenting, "qsMediaTranslationY", Float.valueOf(((Number) this.qsMediaTranslationY$delegate.getValue()).floatValue()));
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable th) {
                throw th;
            } finally {
            }
        } finally {
        }
    }

    public final float getQqsMediaExpansion() {
        return (this.qqsMediaInRowViewModel.getShouldMediaShowInRow() && ((Boolean) ((SnapshotMutableStateImpl) this.collapsedLandscapeMedia$delegate).getValue()).booleanValue()) ? 0.0f : 1.0f;
    }

    public final boolean getQqsMediaVisible() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.qqsMediaVisible$delegate).getValue()).booleanValue();
    }

    public final float getQsExpansion$1() {
        return ((Number) ((SnapshotMutableStateImpl) this.qsExpansion$delegate).getValue()).floatValue();
    }

    public final int getStatusBarState() {
        return ((Number) ((SnapshotMutableStateImpl) this.statusBarState$delegate).getValue()).intValue();
    }

    public final float getTranslationScaleY() {
        return (getQsExpansion$1() - 1) * (isInSplitShade() ? 1.0f : 0.1f);
    }

    public final boolean isInSplitShade() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isInSplitShade$delegate).getValue()).booleanValue();
    }

    public final boolean isKeyguardState$1() {
        return getStatusBarState() == 1;
    }

    public final boolean isQsVisibleAndAnyShadeExpanded() {
        boolean zBooleanValue = ((Boolean) ((SnapshotMutableStateImpl) this.anyShadeExpanded$delegate).getValue()).booleanValue();
        MutableState mutableState = this.panelState$delegate;
        return (zBooleanValue && ((Boolean) ((SnapshotMutableStateImpl) this.isQsVisible$delegate).getValue()).booleanValue() && ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).intValue() == 0) || ((Number) ((SnapshotMutableStateImpl) mutableState).getValue()).intValue() == 3;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        C09741 c09741;
        if (continuation instanceof C09741) {
            c09741 = (C09741) continuation;
            int i = c09741.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09741.label = i - Integer.MIN_VALUE;
            } else {
                c09741 = new C09741(continuation);
            }
        }
        Object obj = c09741.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09741.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            float qqsMediaExpansion = getQqsMediaExpansion();
            MediaHost mediaHost = this.qqsMediaHost;
            mediaHost.setExpansion(qqsMediaExpansion);
            mediaHost.setShowsOnlyActiveMedia(true);
            mediaHost.init(1);
            MediaHost mediaHost2 = this.qsMediaHost;
            mediaHost2.setExpansion(1.0f);
            mediaHost2.setShowsOnlyActiveMedia(false);
            mediaHost2.init(0);
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(null);
            c09741.label = 1;
            if (CoroutineScopeKt.coroutineScope(anonymousClass2, c09741) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
