package com.android.systemui.communal.domain.interactor;

import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.communal.data.model.CommunalMediaModel;
import com.android.systemui.communal.data.model.CommunalSmartspaceTimer;
import com.android.systemui.communal.data.repository.CommunalMediaRepository;
import com.android.systemui.communal.data.repository.CommunalMediaRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalSmartspaceRepository;
import com.android.systemui.communal.data.repository.CommunalSmartspaceRepositoryImpl;
import com.android.systemui.communal.data.repository.CommunalWidgetRepository;
import com.android.systemui.communal.domain.interactor.CommunalInteractor;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.communal.shared.model.CommunalContentSize;
import com.android.systemui.communal.shared.model.CommunalWidgetContentModel;
import com.android.systemui.communal.widgets.EditWidgetsActivityStarter;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class CommunalInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DISCLAIMER_RESET_MILLIS;
    public final StateFlowImpl _editActivityShowing;
    public final StateFlowImpl _editModeOpen;
    public int _firstVisibleItemIndex;
    public int _firstVisibleItemOffset;
    public final StateFlowImpl _isDisclaimerDismissed;
    public final StateFlowImpl _selectedKey;
    public final SharedFlowImpl _userActivity;
    public final ActivityStarter activityStarter;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher bgDispatcher;
    public final CoroutineScope bgScope;
    public final CommunalPrefsInteractor communalPrefsInteractor;
    public final CommunalSceneInteractor communalSceneInteractor;
    public final ChannelFlowTransformLatest communalWidgets;
    public final Lazy ctaTileContent$delegate;
    public final ReadonlyStateFlow desiredScene;
    public final ReadonlyStateFlow dreamFromOccluded;
    public final ReadonlyStateFlow editActivityShowing;
    public final ReadonlyStateFlow editModeOpen;
    public final EditWidgetsActivityStarter editWidgetsActivityStarter;
    public final Lazy isCommunalAvailable$delegate;
    public final ReadonlyStateFlow isCommunalBlurring;
    public final ReadonlyStateFlow isCommunalEnabled;
    public final ReadonlySharedFlow isCommunalShowing;
    public final ReadonlyStateFlow isCommunalVisible;
    public final ReadonlyStateFlow isDisclaimerDismissed;
    public final ReadonlyStateFlow isIdleOnCommunal;
    public final Logger logger;
    public final ManagedProfileController managedProfileController;
    public final CommunalMediaRepository mediaRepository;
    public final ReadonlyStateFlow selectedKey;
    public final ReadonlyStateFlow showCommunalFromOccluded;
    public final CommunalSmartspaceRepository smartspaceRepository;
    public final List tutorialContent;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 updateOnWorkProfileBroadcastReceived;
    public final ReadonlySharedFlow userActivity;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 widgetContent;
    public final CommunalWidgetRepository widgetRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$ongoingContent$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        final /* synthetic */ boolean $isMediaHostVisible;
        /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;
        final /* synthetic */ CommunalInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(boolean z, CommunalInteractor communalInteractor, Continuation continuation) {
            super(3, continuation);
            this.$isMediaHostVisible = z;
            this.this$0 = communalInteractor;
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$isMediaHostVisible, this.this$0, (Continuation) obj3);
            anonymousClass1.L$0 = (List) obj;
            anonymousClass1.L$1 = (CommunalMediaModel) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            List list = (List) this.L$0;
            CommunalMediaModel communalMediaModel = (CommunalMediaModel) this.L$1;
            ArrayList arrayList = new ArrayList();
            List<CommunalSmartspaceTimer> list2 = list;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (CommunalSmartspaceTimer communalSmartspaceTimer : list2) {
                arrayList2.add(new CommunalContentModel.Smartspace(communalSmartspaceTimer.smartspaceTargetId, communalSmartspaceTimer.remoteViews, communalSmartspaceTimer.createdTimestampMillis, null, 8, null));
            }
            arrayList.addAll(arrayList2);
            if (this.$isMediaHostVisible && communalMediaModel.hasAnyMediaOrRecommendation) {
                arrayList.add(new CommunalContentModel.Umo(communalMediaModel.createdTimestampMillis, null, null, 6, null));
            }
            if (arrayList.size() > 1) {
                CollectionsKt__MutableCollectionsJVMKt.sortWith(arrayList, new Comparator() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$ongoingContent$1$invokeSuspend$$inlined$sortByDescending$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(((CommunalContentModel.Ongoing) obj3).getCreatedTimestampMillis()), Long.valueOf(((CommunalContentModel.Ongoing) obj2).getCreatedTimestampMillis()));
                    }
                });
            }
            CommunalInteractor communalInteractor = this.this$0;
            Companion companion = CommunalInteractor.Companion;
            communalInteractor.getClass();
            ArrayList arrayList3 = new ArrayList();
            int span = CommunalContentSize.FixedSize.FULL.getSpan();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                CommunalContentModel.Ongoing ongoing = (CommunalContentModel.Ongoing) obj2;
                if (span < ongoing.getMinSize().getSpan()) {
                    CommunalInteractor.resizeItems$resizeColumn(arrayList3);
                    arrayList3.clear();
                    span = CommunalContentSize.FixedSize.FULL.getSpan();
                }
                arrayList3.add(ongoing);
                span -= ongoing.getMinSize().getSpan();
            }
            CommunalInteractor.resizeItems$resizeColumn(arrayList3);
            return arrayList;
        }
    }

    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$setDisclaimerDismissed$1, reason: invalid class name and case insensitive filesystem */
    final class C08331 extends SuspendLambda implements Function2 {
        int label;

        public C08331(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CommunalInteractor.this.new C08331(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08331) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CommunalInteractor.this._isDisclaimerDismissed.updateState(null, Boolean.TRUE);
                CommunalInteractor.Companion.getClass();
                long j = CommunalInteractor.DISCLAIMER_RESET_MILLIS;
                this.label = 1;
                if (DelayKt.m3468delayVtjQ1oo(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            CommunalInteractor.this._isDisclaimerDismissed.updateState(null, Boolean.FALSE);
            return Unit.INSTANCE;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DISCLAIMER_RESET_MILLIS = DurationKt.toDuration(30, DurationUnit.MINUTES);
    }

    public CommunalInteractor(CoroutineScope coroutineScope, CoroutineScope coroutineScope2, CoroutineDispatcher coroutineDispatcher, BroadcastDispatcher broadcastDispatcher, CommunalWidgetRepository communalWidgetRepository, CommunalPrefsInteractor communalPrefsInteractor, CommunalMediaRepository communalMediaRepository, CommunalSmartspaceRepository communalSmartspaceRepository, final KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, final CommunalSettingsInteractor communalSettingsInteractor, EditWidgetsActivityStarter editWidgetsActivityStarter, UserTracker userTracker, ActivityStarter activityStarter, UserManager userManager, CommunalSceneInteractor communalSceneInteractor, SceneInteractor sceneInteractor, LogBuffer logBuffer, final TableLogBuffer tableLogBuffer, ManagedProfileController managedProfileController) {
        this.applicationScope = coroutineScope;
        this.bgScope = coroutineScope2;
        this.bgDispatcher = coroutineDispatcher;
        this.widgetRepository = communalWidgetRepository;
        this.communalPrefsInteractor = communalPrefsInteractor;
        this.mediaRepository = communalMediaRepository;
        this.smartspaceRepository = communalSmartspaceRepository;
        this.editWidgetsActivityStarter = editWidgetsActivityStarter;
        this.userTracker = userTracker;
        this.activityStarter = activityStarter;
        this.userManager = userManager;
        this.communalSceneInteractor = communalSceneInteractor;
        this.managedProfileController = managedProfileController;
        this.logger = new Logger(logBuffer, "CommunalInteractor");
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._editModeOpen = stateFlowImplMutableStateFlow;
        this.editModeOpen = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._editActivityShowing = stateFlowImplMutableStateFlow2;
        this.editActivityShowing = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(null);
        this._selectedKey = stateFlowImplMutableStateFlow3;
        this.selectedKey = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.isCommunalEnabled = communalSettingsInteractor.isCommunalEnabled;
        this.isCommunalAvailable$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CommunalInteractor.Companion companion = CommunalInteractor.Companion;
                CommunalSettingsInteractor communalSettingsInteractor2 = communalSettingsInteractor;
                communalSettingsInteractor2.isV2FlagEnabled();
                Flow flowAllOf = BooleanFlowOperators.INSTANCE.allOf(communalSettingsInteractor2.isCommunalEnabled, keyguardInteractor.isKeyguardShowing);
                CommunalInteractor communalInteractor = this;
                return FlowKt.shareIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(flowAllOf, new CommunalInteractor$isCommunalAvailable$2$1(communalInteractor, null)), tableLogBuffer, "", "isCommunalAvailable", false), communalInteractor.applicationScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 1);
            }
        });
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._isDisclaimerDismissed = stateFlowImplMutableStateFlow4;
        this.isDisclaimerDismissed = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        final ReadonlyStateFlow readonlyStateFlow = keyguardTransitionInteractor.startedKeyguardTransitionStep;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        if (((TransitionStep) obj).to == KeyguardState.OCCLUDED) {
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, isCommunalAvailable(), CommunalInteractor$showCommunalFromOccluded$4.INSTANCE);
        Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                    KeyguardState keyguardState;
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
                        Pair pair = (Pair) obj;
                        Boolean boolValueOf = Boolean.valueOf(((Boolean) pair.component2()).booleanValue() && ((keyguardState = ((TransitionStep) pair.component1()).from) == KeyguardState.GLANCEABLE_HUB || keyguardState == KeyguardState.DREAMING));
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
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.showCommunalFromOccluded = FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        final Flow flowTransition = keyguardTransitionInteractor.transition(Edge.Companion.create$default(Edge.Companion, null, KeyguardState.OCCLUDED, 1));
        Flow flow = new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((TransitionStep) obj).from == KeyguardState.DREAMING);
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
                Object objCollect = flowTransition.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.dreamFromOccluded = FlowKt.stateIn(flow, coroutineScope, startedEagerly, bool);
        this.desiredScene = communalSceneInteractor.currentScene;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 1, BufferOverflow.DROP_OLDEST, 1);
        this._userActivity = sharedFlowImplMutableSharedFlow$default;
        this.userActivity = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default);
        this.isCommunalShowing = FlowKt.shareIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(FlowKt.transformLatest(new SafeFlow(new CommunalInteractor$isCommunalShowing$1(null)), new CommunalInteractor$special$$inlined$flatMapLatest$1(null, sceneInteractor, this))), new CommunalInteractor$isCommunalShowing$3(this, null)), tableLogBuffer, "", "isCommunalShowing", false), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 1);
        CommunalInteractor$isCommunalBlurring$1 communalInteractor$isCommunalBlurring$1 = new CommunalInteractor$isCommunalBlurring$1(null);
        Flow flow2 = communalSettingsInteractor.communalBackground;
        ReadonlyStateFlow readonlyStateFlow2 = communalSceneInteractor.isCommunalVisible;
        this.isCommunalBlurring = FlowKt.stateIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlow2, flow2, communalInteractor$isCommunalBlurring$1), coroutineScope, startedEagerly, bool);
        this.isIdleOnCommunal = communalSceneInteractor.isIdleOnCommunal;
        this.isCommunalVisible = readonlyStateFlow2;
        IntentFilter intentFilterM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.MANAGED_PROFILE_AVAILABLE", "android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
        Unit unit = Unit.INSTANCE;
        FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new FlowKt.AnonymousClass1(null), BroadcastDispatcher.broadcastFlow$default(broadcastDispatcher, intentFilterM, null, 14));
        this.updateOnWorkProfileBroadcastReceived = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        this.communalWidgets = kotlinx.coroutines.flow.FlowKt.transformLatest(isCommunalAvailable(), new CommunalInteractor$special$$inlined$flatMapLatest$2(null, this));
        final Flow communalWidgets = communalWidgetRepository.getCommunalWidgets();
        this.widgetContent = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CommunalInteractor this$0;

                /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CommunalInteractor communalInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = communalInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean zContains;
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
                        List list = (List) obj;
                        List userProfiles = ((UserTrackerImpl) this.this$0.userTracker).getUserProfiles();
                        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
                        Iterator it = userProfiles.iterator();
                        while (it.hasNext()) {
                            arrayList.add(Integer.valueOf(((UserInfo) it.next()).id));
                        }
                        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                        ArrayList arrayList2 = new ArrayList();
                        for (Object obj3 : list) {
                            CommunalWidgetContentModel communalWidgetContentModel = (CommunalWidgetContentModel) obj3;
                            if (communalWidgetContentModel instanceof CommunalWidgetContentModel.Available) {
                                Set set2 = set;
                                UserHandle profile = ((CommunalWidgetContentModel.Available) communalWidgetContentModel).providerInfo.getProfile();
                                zContains = CollectionsKt___CollectionsKt.contains(set2, profile != null ? Integer.valueOf(profile.getIdentifier()) : null);
                            } else {
                                if (!(communalWidgetContentModel instanceof CommunalWidgetContentModel.Pending)) {
                                    throw new NoWhenBranchMatchedException();
                                }
                                zContains = true;
                            }
                            if (zContains) {
                                arrayList2.add(obj3);
                            }
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(arrayList2, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = communalWidgets.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, communalSettingsInteractor.workProfileUserDisallowedByDevicePolicy, new CommunalInteractor$widgetContent$2(this, null)), flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, new CommunalInteractor$widgetContent$3(this, null));
        this.ctaTileContent$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                CommunalInteractor.Companion companion2 = CommunalInteractor.Companion;
                communalSettingsInteractor.isV2FlagEnabled();
                final ReadonlyStateFlow readonlyStateFlow3 = this.communalPrefsInteractor.isCtaDismissed;
                return new Flow() { // from class: com.android.systemui.communal.domain.interactor.CommunalInteractor$ctaTileContent_delegate$lambda$13$$inlined$map$1

                    /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$ctaTileContent_delegate$lambda$13$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.communal.domain.interactor.CommunalInteractor$ctaTileContent_delegate$lambda$13$$inlined$map$1$2$1, reason: invalid class name */
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
                                Object objSingletonList = ((Boolean) obj).booleanValue() ? EmptyList.INSTANCE : Collections.singletonList(new CommunalContentModel.CtaTileInViewMode());
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(objSingletonList, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = readonlyStateFlow3.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            }
        });
        CommunalContentModel.Tutorial tutorial = new CommunalContentModel.Tutorial(0, CommunalContentSize.FixedSize.FULL);
        CommunalContentSize.FixedSize fixedSize = CommunalContentSize.FixedSize.THIRD;
        CommunalContentModel.Tutorial tutorial2 = new CommunalContentModel.Tutorial(1, fixedSize);
        CommunalContentModel.Tutorial tutorial3 = new CommunalContentModel.Tutorial(2, fixedSize);
        CommunalContentModel.Tutorial tutorial4 = new CommunalContentModel.Tutorial(3, fixedSize);
        CommunalContentSize.FixedSize fixedSize2 = CommunalContentSize.FixedSize.HALF;
        this.tutorialContent = Arrays.asList(tutorial, tutorial2, tutorial3, tutorial4, new CommunalContentModel.Tutorial(4, fixedSize2), new CommunalContentModel.Tutorial(5, fixedSize2), new CommunalContentModel.Tutorial(6, fixedSize2), new CommunalContentModel.Tutorial(7, fixedSize2));
    }

    public static final void resizeItems$resizeColumn(List list) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.isEmpty()) {
            return;
        }
        CommunalContentSize.Companion companion = CommunalContentSize.Companion;
        int span = CommunalContentSize.FixedSize.FULL.getSpan() / arrayList.size();
        companion.getClass();
        CommunalContentSize.FixedSize size = CommunalContentSize.Companion.toSize(span);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((CommunalContentModel.Ongoing) it.next()).setSize(size);
        }
    }

    public final Flow isCommunalAvailable() {
        return (Flow) this.isCommunalAvailable$delegate.getValue();
    }

    public final Flow ongoingContent(boolean z) {
        return kotlinx.coroutines.flow.FlowKt.flowOn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(((CommunalSmartspaceRepositoryImpl) this.smartspaceRepository).timers, ((CommunalMediaRepositoryImpl) this.mediaRepository).mediaModel, new AnonymousClass1(z, this, null)), this.bgDispatcher);
    }

    public final void setDisclaimerDismissed() {
        CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new C08331(null), 6);
    }
}
