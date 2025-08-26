package com.android.systemui.communal.widgets;

import com.android.systemui.CoreStartable;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.shared.model.GlanceableHubMultiUserHelper;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.domain.interactor.UserLockedInteractor;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowKt;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;

/* loaded from: classes2.dex */
public final class CommunalAppWidgetHostStartable implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy appWidgetHost$delegate;
    public final dagger.Lazy appWidgetHostLazy;
    public final CoroutineScope bgScope;
    public final Lazy communalInteractor$delegate;
    public final dagger.Lazy communalInteractorLazy;
    public final Lazy communalSettingsInteractor$delegate;
    public final dagger.Lazy communalSettingsInteractorLazy;
    public final Lazy communalWidgetHost$delegate;
    public final dagger.Lazy communalWidgetHostLazy;
    public final GlanceableHubMultiUserHelper glanceableHubMultiUserHelper;
    public final Lazy glanceableHubWidgetManager$delegate;
    public final dagger.Lazy glanceableHubWidgetManagerLazy;
    public final Lazy keyguardInteractor$delegate;
    public final dagger.Lazy keyguardInteractorLazy;
    public final CoroutineDispatcher uiDispatcher;
    public final Lazy userTracker$delegate;
    public final dagger.Lazy userTrackerLazy;

    public CommunalAppWidgetHostStartable(dagger.Lazy lazy, dagger.Lazy lazy2, dagger.Lazy lazy3, dagger.Lazy lazy4, dagger.Lazy lazy5, dagger.Lazy lazy6, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, dagger.Lazy lazy7, GlanceableHubMultiUserHelper glanceableHubMultiUserHelper, UserLockedInteractor userLockedInteractor) {
        this.appWidgetHostLazy = lazy;
        this.communalWidgetHostLazy = lazy2;
        this.communalInteractorLazy = lazy3;
        this.communalSettingsInteractorLazy = lazy4;
        this.keyguardInteractorLazy = lazy5;
        this.userTrackerLazy = lazy6;
        this.bgScope = coroutineScope;
        this.uiDispatcher = coroutineDispatcher;
        this.glanceableHubWidgetManagerLazy = lazy7;
        this.glanceableHubMultiUserHelper = glanceableHubMultiUserHelper;
        final int i = 0;
        this.appWidgetHost$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalAppWidgetHostStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return (CommunalAppWidgetHost) this.f$0.appWidgetHostLazy.get();
                    case 1:
                        return (CommunalWidgetHost) this.f$0.communalWidgetHostLazy.get();
                    case 2:
                        return (CommunalInteractor) this.f$0.communalInteractorLazy.get();
                    case 3:
                        return (CommunalSettingsInteractor) this.f$0.communalSettingsInteractorLazy.get();
                    case 4:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                    case 5:
                        return (UserTracker) this.f$0.userTrackerLazy.get();
                    default:
                        return (GlanceableHubWidgetManager) this.f$0.glanceableHubWidgetManagerLazy.get();
                }
            }
        });
        final int i2 = 1;
        this.communalWidgetHost$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalAppWidgetHostStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return (CommunalAppWidgetHost) this.f$0.appWidgetHostLazy.get();
                    case 1:
                        return (CommunalWidgetHost) this.f$0.communalWidgetHostLazy.get();
                    case 2:
                        return (CommunalInteractor) this.f$0.communalInteractorLazy.get();
                    case 3:
                        return (CommunalSettingsInteractor) this.f$0.communalSettingsInteractorLazy.get();
                    case 4:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                    case 5:
                        return (UserTracker) this.f$0.userTrackerLazy.get();
                    default:
                        return (GlanceableHubWidgetManager) this.f$0.glanceableHubWidgetManagerLazy.get();
                }
            }
        });
        final int i3 = 2;
        this.communalInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalAppWidgetHostStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return (CommunalAppWidgetHost) this.f$0.appWidgetHostLazy.get();
                    case 1:
                        return (CommunalWidgetHost) this.f$0.communalWidgetHostLazy.get();
                    case 2:
                        return (CommunalInteractor) this.f$0.communalInteractorLazy.get();
                    case 3:
                        return (CommunalSettingsInteractor) this.f$0.communalSettingsInteractorLazy.get();
                    case 4:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                    case 5:
                        return (UserTracker) this.f$0.userTrackerLazy.get();
                    default:
                        return (GlanceableHubWidgetManager) this.f$0.glanceableHubWidgetManagerLazy.get();
                }
            }
        });
        final int i4 = 3;
        this.communalSettingsInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalAppWidgetHostStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        return (CommunalAppWidgetHost) this.f$0.appWidgetHostLazy.get();
                    case 1:
                        return (CommunalWidgetHost) this.f$0.communalWidgetHostLazy.get();
                    case 2:
                        return (CommunalInteractor) this.f$0.communalInteractorLazy.get();
                    case 3:
                        return (CommunalSettingsInteractor) this.f$0.communalSettingsInteractorLazy.get();
                    case 4:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                    case 5:
                        return (UserTracker) this.f$0.userTrackerLazy.get();
                    default:
                        return (GlanceableHubWidgetManager) this.f$0.glanceableHubWidgetManagerLazy.get();
                }
            }
        });
        final int i5 = 4;
        this.keyguardInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalAppWidgetHostStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        return (CommunalAppWidgetHost) this.f$0.appWidgetHostLazy.get();
                    case 1:
                        return (CommunalWidgetHost) this.f$0.communalWidgetHostLazy.get();
                    case 2:
                        return (CommunalInteractor) this.f$0.communalInteractorLazy.get();
                    case 3:
                        return (CommunalSettingsInteractor) this.f$0.communalSettingsInteractorLazy.get();
                    case 4:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                    case 5:
                        return (UserTracker) this.f$0.userTrackerLazy.get();
                    default:
                        return (GlanceableHubWidgetManager) this.f$0.glanceableHubWidgetManagerLazy.get();
                }
            }
        });
        final int i6 = 5;
        this.userTracker$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalAppWidgetHostStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        return (CommunalAppWidgetHost) this.f$0.appWidgetHostLazy.get();
                    case 1:
                        return (CommunalWidgetHost) this.f$0.communalWidgetHostLazy.get();
                    case 2:
                        return (CommunalInteractor) this.f$0.communalInteractorLazy.get();
                    case 3:
                        return (CommunalSettingsInteractor) this.f$0.communalSettingsInteractorLazy.get();
                    case 4:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                    case 5:
                        return (UserTracker) this.f$0.userTrackerLazy.get();
                    default:
                        return (GlanceableHubWidgetManager) this.f$0.glanceableHubWidgetManagerLazy.get();
                }
            }
        });
        final int i7 = 6;
        this.glanceableHubWidgetManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$$ExternalSyntheticLambda0
            public final /* synthetic */ CommunalAppWidgetHostStartable f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        return (CommunalAppWidgetHost) this.f$0.appWidgetHostLazy.get();
                    case 1:
                        return (CommunalWidgetHost) this.f$0.communalWidgetHostLazy.get();
                    case 2:
                        return (CommunalInteractor) this.f$0.communalInteractorLazy.get();
                    case 3:
                        return (CommunalSettingsInteractor) this.f$0.communalSettingsInteractorLazy.get();
                    case 4:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                    case 5:
                        return (UserTracker) this.f$0.userTrackerLazy.get();
                    default:
                        return (GlanceableHubWidgetManager) this.f$0.glanceableHubWidgetManagerLazy.get();
                }
            }
        });
    }

    public final CommunalInteractor getCommunalInteractor() {
        return (CommunalInteractor) this.communalInteractor$delegate.getValue();
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.glanceableHubMultiUserHelper.getClass();
        ((CommunalSettingsInteractor) this.communalSettingsInteractor$delegate.getValue()).isV2FlagEnabled();
        final Flow flowPairwise = FlowKt.pairwise(BooleanFlowOperators.INSTANCE.anyOf(getCommunalInteractor().isCommunalAvailable(), getCommunalInteractor().editModeOpen), Boolean.FALSE);
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.sample(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$onStartInForegroundUser$$inlined$filter$1

            /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$onStartInForegroundUser$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.widgets.CommunalAppWidgetHostStartable$onStartInForegroundUser$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        WithPrev withPrev = (WithPrev) obj;
                        if (((Boolean) withPrev.component1()).booleanValue() != ((Boolean) withPrev.component2()).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
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
                Object objCollect = flowPairwise.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new CommunalAppWidgetHostStartable$onStartInForegroundUser$2(this, null)), getCommunalInteractor().communalWidgets, CommunalAppWidgetHostStartable$onStartInForegroundUser$5.INSTANCE), new CommunalAppWidgetHostStartable$onStartInForegroundUser$6(this, null));
        CoroutineScope coroutineScope = this.bgScope;
        kotlinx.coroutines.flow.FlowKt.launchIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope);
        kotlinx.coroutines.flow.FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(((CommunalAppWidgetHost) this.appWidgetHost$delegate.getValue()).appWidgetIdToRemove, new CommunalAppWidgetHostStartable$onStartInForegroundUser$7(this, null)), coroutineScope);
    }
}
