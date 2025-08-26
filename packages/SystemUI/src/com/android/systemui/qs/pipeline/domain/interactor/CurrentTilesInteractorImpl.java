package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.ScRune;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.nano.SystemUIProtoDump;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecQSTileInstanceManager;
import com.android.systemui.qs.TileFeatureChecker;
import com.android.systemui.qs.TileStateToProtoKt;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.CustomTileStatePersisterImpl;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileLifecycleManager$$ExternalSyntheticLambda0;
import com.android.systemui.qs.external.TileServiceKey;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper;
import com.android.systemui.qs.pipeline.data.domain.interactor.FotaUpdateInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedSharedPrefsRepository;
import com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepositoryImpl;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl;
import com.android.systemui.qs.pipeline.data.repository.MinimumTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.TileNameConverter;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.DeviceType;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class CurrentTilesInteractorImpl implements CurrentTilesInteractor {
    public final StateFlowImpl _currentBarTileList;
    public final StateFlowImpl _currentSpecsAndTiles;
    public final StateFlowImpl _userContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public List bottomBarTileList;
    public List brightnessVolumeBarTileList;
    public final StateFlowImpl currentBarTileList;
    public final ReadonlyStateFlow currentTiles;
    public final StateFlowImpl currentUser;
    public final CustomTileAddedRepository customTileAddedRepository;
    public final CustomTileStatePersister customTileStatePersister;
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineFlagsRepository featureFlags;
    public final SharedFlowImpl forceUIUpdate;
    public final FotaUpdateInteractor fotaUpdateInteractor;
    public final ArrayList hiddenTilesByKnoxInTopBottomBar;
    public final InstalledTilesComponentRepository installedTilesComponentRepository;
    public final KnoxPolicyTilesRepository knoxPolicyTilesRepository;
    public final QSPipelineLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final MinimumTilesRepository minimumTilesRepository;
    public final SharedFlowImpl refreshTiles;
    public final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 refreshUserAndTiles;
    public final RemovedTilesInteractor removedTilesInteractor;
    public final SecQSPanelResourcePicker resourcePicker;
    public final RetailModeRepository retailModeRepository;
    public final CoroutineScope scope;
    public List smartViewBarTileList;
    public final Map specsToTiles;
    public final QSFactory tileFactory;
    public final TileFeatureChecker tileFeatureChecker;
    public final SecQSTileInstanceManager tileInstanceManager;
    public final TileLifecycleManager.Factory tileLifecycleManagerFactory;
    public final TileSpecRepository tileSpecRepository;
    public final String tileUsingByBar;
    public final String tileUsingByPanel;
    public final Flow tilesUpdatedFlow;
    public List topBarTile;
    public final ReadonlyStateFlow userContext;
    public final ReadonlyStateFlow userId;
    public final UserRepository userRepository;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = !DeviceType.isShipBuild();
    public static final int BAR_TILE_NUM_DEFAULT = 2;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final List access$toTileList(Companion companion, List list, Resources resources) {
            companion.getClass();
            List<String> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (String str : list2) {
                TileSpec.Companion companion2 = TileSpec.Companion;
                TileNameConverter.INSTANCE.getClass();
                String tileSpec = TileNameConverter.toTileSpec(resources, str);
                companion2.getClass();
                arrayList.add(TileSpec.Companion.create(tileSpec));
            }
            return arrayList;
        }

        private Companion() {
        }
    }

    public interface TileOrNotInstalled {

        public final class NotInstalled implements TileOrNotInstalled {
            public static final NotInstalled INSTANCE = new NotInstalled();

            private NotInstalled() {
            }
        }

        public final class Tile implements TileOrNotInstalled {
            public final QSTile tile;

            private /* synthetic */ Tile(QSTile qSTile) {
                this.tile = qSTile;
            }

            /* renamed from: box-impl, reason: not valid java name */
            public static final /* synthetic */ Tile m2910boximpl(QSTile qSTile) {
                return new Tile(qSTile);
            }

            public final boolean equals(Object obj) {
                if (obj instanceof Tile) {
                    return Intrinsics.areEqual(this.tile, ((Tile) obj).tile);
                }
                return false;
            }

            public final int hashCode() {
                return this.tile.hashCode();
            }

            public final String toString() {
                return "Tile(tile=" + this.tile + ")";
            }
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$addTile$1, reason: invalid class name and case insensitive filesystem */
    final class C09931 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $position;
        final /* synthetic */ TileSpec $spec;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09931(TileSpec tileSpec, int i, Continuation continuation) {
            super(2, continuation);
            this.$spec = tileSpec;
            this.$position = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CurrentTilesInteractorImpl.this.new C09931(this.$spec, this.$position, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09931) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
        
            if (r1.addTile(r6, r3, r4, r5) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ReadonlyStateFlow readonlyStateFlow = CurrentTilesInteractorImpl.this.currentTiles;
                Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                if (!((List) obj).isEmpty()) {
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
                };
                this.label = 1;
                if (FlowKt.first(flow, this) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            CurrentTilesInteractorImpl currentTilesInteractorImpl = CurrentTilesInteractorImpl.this;
            TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
            int i2 = ((UserRepositoryImpl) currentTilesInteractorImpl.userRepository).getSelectedUserInfo().id;
            TileSpec tileSpec = this.$spec;
            int i3 = this.$position;
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$removeTiles$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Collection<TileSpec> $specs;
        final /* synthetic */ int $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public AnonymousClass2(int i, Collection<? extends TileSpec> collection, Continuation continuation) {
            super(2, continuation);
            this.$user = i;
            this.$specs = collection;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CurrentTilesInteractorImpl.this.new AnonymousClass2(this.$user, this.$specs, continuation);
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
                TileSpecRepository tileSpecRepository = CurrentTilesInteractorImpl.this.tileSpecRepository;
                int i2 = this.$user;
                Collection<TileSpec> collection = this.$specs;
                this.label = 1;
                if (tileSpecRepository.removeTiles(i2, collection, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$resetTiles$1, reason: invalid class name and case insensitive filesystem */
    final class C09941 extends SuspendLambda implements Function2 {
        int label;

        public C09941(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CurrentTilesInteractorImpl.this.new C09941(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09941) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CurrentTilesInteractorImpl currentTilesInteractorImpl = CurrentTilesInteractorImpl.this;
                TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
                int iIntValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
                this.label = 1;
                if (tileSpecRepository.resetToDefault(iIntValue, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$setTiles$2, reason: invalid class name and case insensitive filesystem */
    final class C09952 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<TileSpec> $specs;
        final /* synthetic */ int $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C09952(int i, List<? extends TileSpec> list, Continuation continuation) {
            super(2, continuation);
            this.$user = i;
            this.$specs = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CurrentTilesInteractorImpl.this.new C09952(this.$user, this.$specs, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09952) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TileSpecRepository tileSpecRepository = CurrentTilesInteractorImpl.this.tileSpecRepository;
                int i2 = this.$user;
                List<TileSpec> list = this.$specs;
                this.label = 1;
                if (tileSpecRepository.setTiles(i2, list, this) == coroutineSingletons) {
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

    public CurrentTilesInteractorImpl(TileSpecRepository tileSpecRepository, InstalledTilesComponentRepository installedTilesComponentRepository, UserRepository userRepository, MinimumTilesRepository minimumTilesRepository, RetailModeRepository retailModeRepository, CustomTileStatePersister customTileStatePersister, Lazy lazy, QSFactory qSFactory, CustomTileAddedRepository customTileAddedRepository, TileLifecycleManager.Factory factory, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, QSPipelineLogger qSPipelineLogger, QSPipelineFlagsRepository qSPipelineFlagsRepository, BootAnimationFinishedCache bootAnimationFinishedCache, SecQSTileInstanceManager secQSTileInstanceManager, SecQSPanelResourcePicker secQSPanelResourcePicker, KnoxPolicyTilesRepository knoxPolicyTilesRepository, DefaultTilesRepository defaultTilesRepository, RemovedTilesInteractor removedTilesInteractor, TileFeatureChecker tileFeatureChecker, FotaUpdateInteractor fotaUpdateInteractor) {
        this.tileSpecRepository = tileSpecRepository;
        this.installedTilesComponentRepository = installedTilesComponentRepository;
        this.userRepository = userRepository;
        this.minimumTilesRepository = minimumTilesRepository;
        this.retailModeRepository = retailModeRepository;
        this.customTileStatePersister = customTileStatePersister;
        this.tileFactory = qSFactory;
        this.customTileAddedRepository = customTileAddedRepository;
        this.tileLifecycleManagerFactory = factory;
        this.userTracker = userTracker;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.scope = coroutineScope;
        this.logger = qSPipelineLogger;
        this.featureFlags = qSPipelineFlagsRepository;
        this.tileInstanceManager = secQSTileInstanceManager;
        this.resourcePicker = secQSPanelResourcePicker;
        this.knoxPolicyTilesRepository = knoxPolicyTilesRepository;
        this.defaultTilesRepository = defaultTilesRepository;
        this.removedTilesInteractor = removedTilesInteractor;
        this.tileFeatureChecker = tileFeatureChecker;
        this.fotaUpdateInteractor = fotaUpdateInteractor;
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(emptyList);
        this._currentSpecsAndTiles = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.currentTiles = readonlyStateFlowAsStateFlow;
        this.specsToTiles = new LinkedHashMap();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Integer.valueOf(userTrackerImpl.getUserId()));
        this.currentUser = stateFlowImplMutableStateFlow2;
        this.userId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(userTrackerImpl.getUserContext());
        this._userContext = stateFlowImplMutableStateFlow3;
        this.userContext = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.topBarTile = new ArrayList();
        this.brightnessVolumeBarTileList = new ArrayList();
        this.bottomBarTileList = new ArrayList();
        this.smartViewBarTileList = new ArrayList();
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.forceUIUpdate = sharedFlowImplMutableSharedFlow$default;
        this.tileUsingByBar = "Bar";
        this.tileUsingByPanel = "Panel";
        this.tilesUpdatedFlow = FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CurrentTilesInteractorImpl$tilesUpdatedFlow$4(this, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(((KnoxPolicyTilesRepositoryImpl) knoxPolicyTilesRepository).knoxBlockedTiles, new CurrentTilesInteractorImpl$tilesUpdatedFlow$1(this, null)), readonlyStateFlowAsStateFlow, new CurrentTilesInteractorImpl$tilesUpdatedFlow$2(null)), sharedFlowImplMutableSharedFlow$default, new CurrentTilesInteractorImpl$tilesUpdatedFlow$3(null))), coroutineDispatcher2);
        this.hiddenTilesByKnoxInTopBottomBar = new ArrayList();
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(emptyList);
        this._currentBarTileList = stateFlowImplMutableStateFlow4;
        this.currentBarTileList = stateFlowImplMutableStateFlow4;
        Flow flowFlowOn = FlowKt.flowOn(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(FlowKt.distinctUntilChanged(FlowKt.transformLatest(stateFlowImplMutableStateFlow2, new CurrentTilesInteractorImpl$special$$inlined$flatMapLatest$1(null, this))), new UserTilesAndComponents(-1, emptyList, EmptySet.INSTANCE, null, 8, null), new CurrentTilesInteractorImpl$userAndTiles$2(null)), coroutineDispatcher2);
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this.refreshTiles = sharedFlowImplMutableSharedFlow$default2;
        this.refreshUserAndTiles = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CurrentTilesInteractorImpl$refreshUserAndTiles$2(this, null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flowFlowOn, sharedFlowImplMutableSharedFlow$default2, new CurrentTilesInteractorImpl$refreshUserAndTiles$1(null)));
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("CurrentTilesInteractor", "CurrentTilesInteractor not initialized for non-primary user, just return");
            return;
        }
        BootAnimationFinishedCacheImpl bootAnimationFinishedCacheImpl = (BootAnimationFinishedCacheImpl) bootAnimationFinishedCache;
        if (bootAnimationFinishedCacheImpl.bootAnimationFinished.get()) {
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new CurrentTilesInteractorImpl$startTileCollection$1(this, null), 7);
        } else {
            bootAnimationFinishedCacheImpl.addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl.1
                @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
                public final void onBootAnimationFinished() {
                    Companion companion = CurrentTilesInteractorImpl.Companion;
                    CurrentTilesInteractorImpl currentTilesInteractorImpl = CurrentTilesInteractorImpl.this;
                    currentTilesInteractorImpl.getClass();
                    CoroutineTracingKt.launchTraced$default(currentTilesInteractorImpl.scope, null, null, new CurrentTilesInteractorImpl$startTileCollection$1(currentTilesInteractorImpl, null), 7);
                }
            });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$createTile(CurrentTilesInteractorImpl currentTilesInteractorImpl, TileSpec tileSpec, ContinuationImpl continuationImpl) throws Throwable {
        CurrentTilesInteractorImpl$createTile$1 currentTilesInteractorImpl$createTile$1;
        currentTilesInteractorImpl.getClass();
        if (continuationImpl instanceof CurrentTilesInteractorImpl$createTile$1) {
            currentTilesInteractorImpl$createTile$1 = (CurrentTilesInteractorImpl$createTile$1) continuationImpl;
            int i = currentTilesInteractorImpl$createTile$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                currentTilesInteractorImpl$createTile$1.label = i - Integer.MIN_VALUE;
            } else {
                currentTilesInteractorImpl$createTile$1 = new CurrentTilesInteractorImpl$createTile$1(currentTilesInteractorImpl, continuationImpl);
            }
        }
        Object objWithContext = currentTilesInteractorImpl$createTile$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = currentTilesInteractorImpl$createTile$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            CurrentTilesInteractorImpl$createTile$tile$1 currentTilesInteractorImpl$createTile$tile$1 = new CurrentTilesInteractorImpl$createTile$tile$1(currentTilesInteractorImpl, tileSpec, null);
            currentTilesInteractorImpl$createTile$1.L$0 = currentTilesInteractorImpl;
            currentTilesInteractorImpl$createTile$1.L$1 = tileSpec;
            currentTilesInteractorImpl$createTile$1.label = 1;
            objWithContext = BuildersKt.withContext(currentTilesInteractorImpl.mainDispatcher, currentTilesInteractorImpl$createTile$tile$1, currentTilesInteractorImpl$createTile$1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            tileSpec = (TileSpec) currentTilesInteractorImpl$createTile$1.L$1;
            currentTilesInteractorImpl = (CurrentTilesInteractorImpl) currentTilesInteractorImpl$createTile$1.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        QSTile qSTile = (QSTile) objWithContext;
        if (qSTile == null) {
            currentTilesInteractorImpl.logger.logTileNotFoundInFactory(tileSpec);
            return null;
        }
        if (qSTile.isAvailable()) {
            currentTilesInteractorImpl.logger.logTileCreated(tileSpec);
            return qSTile;
        }
        currentTilesInteractorImpl.logger.logTileDestroyed(tileSpec, QSPipelineLogger.TileDestroyedReason.NEW_TILE_NOT_AVAILABLE);
        if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
            currentTilesInteractorImpl.tileInstanceManager.releaseTileUsing(currentTilesInteractorImpl.tileUsingByPanel, tileSpec);
            return null;
        }
        qSTile.destroy();
        return null;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void addTile(TileSpec tileSpec, int i) {
        CoroutineTracingKt.launchTraced$default(this.scope, this.backgroundDispatcher, null, new C09931(tileSpec, i, null), 5);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final QSTile createTileSync(TileSpec tileSpec) {
        this.featureFlags.getClass();
        return this.tileFactory.createTile(tileSpec.getSpec());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("CurrentTileInteractorImpl:");
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("User: ", this.userId.$$delegate_0.getValue(), printWriter);
        Iterable iterable = (Iterable) this.currentTiles.$$delegate_0.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).tile);
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (obj instanceof Dumpable) {
                arrayList2.add(obj);
            }
        }
        int size2 = arrayList2.size();
        while (i < size2) {
            Object obj2 = arrayList2.get(i);
            i++;
            ((Dumpable) obj2).dump(printWriter, strArr);
        }
        printWriter.println("KnoxBlockedTileList:");
        printWriter.println("   " + ((List) ((KnoxPolicyTilesRepositoryImpl) this.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue()));
        SecQSTileInstanceManager secQSTileInstanceManager = this.tileInstanceManager;
        secQSTileInstanceManager.getClass();
        printWriter.println("SecQSTileInstanceManager:");
        printWriter.println("  mTileInstances[" + secQSTileInstanceManager.mTileInstances.size() + "] : " + secQSTileInstanceManager.mTileInstances);
        StringBuilder sb = new StringBuilder("  mTileUsingHosts : ");
        sb.append(secQSTileInstanceManager.mTileUsingHosts);
        printWriter.println(sb.toString());
    }

    @Override // com.android.systemui.ProtoDumpable
    public final void dumpProto(SystemUIProtoDump systemUIProtoDump) {
        Iterable iterable = (Iterable) this.currentTiles.$$delegate_0.getValue();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).tile.getState());
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            QSTile.State state = (QSTile.State) obj;
            QsTileState proto = state != null ? TileStateToProtoKt.toProto(state) : null;
            if (proto != null) {
                arrayList2.add(proto);
            }
        }
        systemUIProtoDump.tiles = (QsTileState[]) arrayList2.toArray(new QsTileState[0]);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final TileModel getBarTileBySpecString() {
        TileSpec.Companion.getClass();
        TileSpec tileSpecCreate = TileSpec.Companion.create("custom(com.samsung.android.mydevice/.quicksettings.MyDeviceTileService)");
        Iterator it = Arrays.asList(this.topBarTile, this.brightnessVolumeBarTileList, this.bottomBarTileList, this.smartViewBarTileList).iterator();
        while (it.hasNext()) {
            for (TileModel tileModel : (List) it.next()) {
                if (Intrinsics.areEqual(tileModel.spec, tileSpecCreate)) {
                    return tileModel;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x048f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x017a  */
    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ArrayList getBarTilesByType(int i, int i2) {
        int i3;
        String topBarTileList;
        String strM;
        int i4;
        QSTile qSTileRequestTileUsing;
        Collection collectionTake;
        int i5;
        Collection collectionTake2;
        int length;
        int i6;
        int i7;
        int i8;
        Collection collectionTake3;
        int length2;
        int i9;
        List listSplit;
        Collection collectionTake4;
        int length3;
        int i10;
        int i11;
        int i12;
        int i13;
        String[] strArr;
        int i14;
        ArrayList arrayList = new ArrayList();
        List list = (List) ((KnoxPolicyTilesRepositoryImpl) this.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        ReadonlyStateFlow readonlyStateFlow = this.userContext;
        if (i2 != 0) {
            strM = "";
            if (i2 == 1) {
                i3 = 1;
                topBarTileList = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getString(R.string.sec_brightness_volume_bar_tiles_default);
                Unit unit = Unit.INSTANCE;
            } else if (i2 == 2) {
                i3 = 1;
                List listSplit2 = new Regex(",").split(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBottomBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue()));
                if (listSplit2.isEmpty()) {
                    collectionTake2 = EmptyList.INSTANCE;
                    String[] strArr2 = (String[]) collectionTake2.toArray(new String[0]);
                    length = strArr2.length;
                    topBarTileList = "";
                    i6 = 0;
                    while (i6 < length) {
                    }
                    Unit unit2 = Unit.INSTANCE;
                } else {
                    ListIterator listIterator = listSplit2.listIterator(listSplit2.size());
                    while (listIterator.hasPrevious()) {
                        if (((String) listIterator.previous()).length() != 0) {
                            collectionTake2 = CollectionsKt___CollectionsKt.take(listSplit2, listIterator.nextIndex() + 1);
                            break;
                        }
                    }
                    collectionTake2 = EmptyList.INSTANCE;
                    String[] strArr22 = (String[]) collectionTake2.toArray(new String[0]);
                    length = strArr22.length;
                    topBarTileList = "";
                    i6 = 0;
                    while (i6 < length) {
                        String str = strArr22[i6];
                        str.getClass();
                        int length4 = str.length() - 1;
                        String[] strArr3 = strArr22;
                        int i15 = 0;
                        boolean z = false;
                        while (true) {
                            i7 = length;
                            if (i15 > length4) {
                                i8 = i6;
                                break;
                            }
                            i8 = i6;
                            boolean z2 = Intrinsics.compare(str.charAt(!z ? i15 : length4), 32) <= 0;
                            if (z) {
                                if (!z2) {
                                    break;
                                }
                                length4--;
                            } else if (z2) {
                                i15++;
                            } else {
                                z = true;
                            }
                            length = i7;
                            i6 = i8;
                        }
                        if (str.subSequence(i15, length4 + 1).toString().length() != 0) {
                            TileSpec.Companion.getClass();
                            TileSpec tileSpecCreate = TileSpec.Companion.create(str);
                            List list2 = this.topBarTile;
                            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                            Iterator it = list2.iterator();
                            while (it.hasNext()) {
                                arrayList2.add(((TileModel) it.next()).spec);
                            }
                            if (!arrayList2.contains(tileSpecCreate)) {
                                List list3 = this.smartViewBarTileList;
                                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                                Iterator it2 = list3.iterator();
                                while (it2.hasNext()) {
                                    arrayList3.add(((TileModel) it2.next()).spec);
                                }
                                if (!arrayList3.contains(tileSpecCreate)) {
                                    topBarTileList = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(topBarTileList, str, ",");
                                }
                            }
                        }
                        i6 = i8 + 1;
                        strArr22 = strArr3;
                        length = i7;
                    }
                    Unit unit22 = Unit.INSTANCE;
                }
            } else {
                if (i2 != 3) {
                    return null;
                }
                List listSplit3 = new Regex(",").split(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getSmartViewBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue()));
                if (listSplit3.isEmpty()) {
                    collectionTake3 = EmptyList.INSTANCE;
                    String[] strArr4 = (String[]) collectionTake3.toArray(new String[0]);
                    length2 = strArr4.length;
                    i3 = 1;
                    i9 = 0;
                    topBarTileList = "";
                    while (i9 < length2) {
                    }
                    listSplit = new Regex(",").split(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBottomBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue()));
                    if (listSplit.isEmpty()) {
                    }
                } else {
                    ListIterator listIterator2 = listSplit3.listIterator(listSplit3.size());
                    while (listIterator2.hasPrevious()) {
                        if (((String) listIterator2.previous()).length() != 0) {
                            collectionTake3 = CollectionsKt___CollectionsKt.take(listSplit3, listIterator2.nextIndex() + 1);
                            break;
                        }
                    }
                    collectionTake3 = EmptyList.INSTANCE;
                    String[] strArr42 = (String[]) collectionTake3.toArray(new String[0]);
                    length2 = strArr42.length;
                    i3 = 1;
                    i9 = 0;
                    topBarTileList = "";
                    while (i9 < length2) {
                        String str2 = strArr42[i9];
                        str2.getClass();
                        int length5 = str2.length() - 1;
                        int i16 = 0;
                        boolean z3 = false;
                        while (true) {
                            i13 = i9;
                            if (i16 > length5) {
                                strArr = strArr42;
                                break;
                            }
                            strArr = strArr42;
                            boolean z4 = Intrinsics.compare(str2.charAt(!z3 ? i16 : length5), 32) <= 0;
                            if (z3) {
                                if (!z4) {
                                    break;
                                }
                                length5--;
                            } else if (z4) {
                                i16++;
                            } else {
                                z3 = true;
                            }
                            i9 = i13;
                            strArr42 = strArr;
                        }
                        if (str2.subSequence(i16, length5 + 1).toString().length() == 0) {
                            i14 = length2;
                        } else {
                            TileSpec.Companion.getClass();
                            TileSpec tileSpecCreate2 = TileSpec.Companion.create(str2);
                            List list4 = this.topBarTile;
                            i14 = length2;
                            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
                            Iterator it3 = list4.iterator();
                            while (it3.hasNext()) {
                                arrayList4.add(((TileModel) it3.next()).spec);
                            }
                            if (!arrayList4.contains(tileSpecCreate2)) {
                                topBarTileList = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(topBarTileList, str2, ",");
                            }
                        }
                        i9 = i13 + 1;
                        length2 = i14;
                        strArr42 = strArr;
                    }
                    listSplit = new Regex(",").split(secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getBottomBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue()));
                    if (listSplit.isEmpty()) {
                        ListIterator listIterator3 = listSplit.listIterator(listSplit.size());
                        while (listIterator3.hasPrevious()) {
                            if (((String) listIterator3.previous()).length() != 0) {
                                collectionTake4 = CollectionsKt___CollectionsKt.take(listSplit, listIterator3.nextIndex() + 1);
                                break;
                            }
                        }
                        collectionTake4 = EmptyList.INSTANCE;
                        String[] strArr5 = (String[]) collectionTake4.toArray(new String[0]);
                        length3 = strArr5.length;
                        i10 = 0;
                        while (i10 < length3) {
                            String str3 = strArr5[i10];
                            str3.getClass();
                            int length6 = str3.length() - 1;
                            String[] strArr6 = strArr5;
                            int i17 = 0;
                            boolean z5 = false;
                            while (true) {
                                i11 = length3;
                                if (i17 > length6) {
                                    i12 = i10;
                                    break;
                                }
                                i12 = i10;
                                boolean z6 = Intrinsics.compare(str3.charAt(!z5 ? i17 : length6), 32) <= 0;
                                if (z5) {
                                    if (!z6) {
                                        break;
                                    }
                                    length6--;
                                } else if (z6) {
                                    i17++;
                                } else {
                                    z5 = true;
                                }
                                length3 = i11;
                                i10 = i12;
                            }
                            if (str3.subSequence(i17, length6 + 1).toString().length() != 0) {
                                TileSpec.Companion.getClass();
                                TileSpec tileSpecCreate3 = TileSpec.Companion.create(str3);
                                List list5 = this.topBarTile;
                                ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list5, 10));
                                Iterator it4 = list5.iterator();
                                while (it4.hasNext()) {
                                    arrayList5.add(((TileModel) it4.next()).spec);
                                }
                                if (!arrayList5.contains(tileSpecCreate3)) {
                                    strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM, str3, ",");
                                }
                            }
                            i10 = i12 + 1;
                            strArr5 = strArr6;
                            length3 = i11;
                        }
                        Unit unit3 = Unit.INSTANCE;
                    } else {
                        collectionTake4 = EmptyList.INSTANCE;
                        String[] strArr52 = (String[]) collectionTake4.toArray(new String[0]);
                        length3 = strArr52.length;
                        i10 = 0;
                        while (i10 < length3) {
                        }
                        Unit unit32 = Unit.INSTANCE;
                    }
                }
            }
        } else {
            i3 = 1;
            topBarTileList = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileList(i, (Context) readonlyStateFlow.$$delegate_0.getValue());
            StateFlow stateFlow = readonlyStateFlow.$$delegate_0;
            Context context = (Context) stateFlow.getValue();
            SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(secQSPanelResourcePickHelper.getTargetPicker().getSmartViewBarTileList(i, context), ",", secQSPanelResourcePickHelper.getTargetPicker().getBottomBarTileList(i, (Context) stateFlow.getValue()));
            Unit unit4 = Unit.INSTANCE;
        }
        ArrayList arrayList6 = new ArrayList();
        List listSplit4 = new Regex(",").split(topBarTileList);
        ArrayList arrayList7 = new ArrayList();
        for (Object obj : listSplit4) {
            if (((String) obj).length() > 0) {
                arrayList7.add(obj);
            }
        }
        int i18 = 0;
        for (String str4 : (String[]) arrayList7.toArray(new String[0])) {
            str4.getClass();
            TileSpec.Companion.getClass();
            TileSpec tileSpecCreate4 = TileSpec.Companion.create(str4);
            if ((this.hiddenTilesByKnoxInTopBottomBar.isEmpty() || !list.contains(tileSpecCreate4)) && (!(tileSpecCreate4 instanceof TileSpec.CustomTileSpec) || isAvailableBarTile(tileSpecCreate4))) {
                arrayList6.add(tileSpecCreate4);
            } else {
                i18++;
            }
        }
        if (i2 == 3 && i18 == 0 && arrayList6.size() == (i5 = i3)) {
            i18 += i5;
        }
        int topBarTileNum = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileNum(i, (Context) readonlyStateFlow.$$delegate_0.getValue());
        boolean z7 = ((TunerService) Dependency.sDependency.getDependencyInner(TunerService.class)).getValue(0, "hide_smart_view_large_tile_on_panel") != 1;
        if (i2 == 0 && topBarTileNum > BAR_TILE_NUM_DEFAULT) {
            TileSpec.Companion.getClass();
            if (isAvailableBarTile(TileSpec.Companion.create("custom(com.samsung.android.smartmirroring/.tile.SmartMirroringTile)")) && !z7) {
                i18++;
            }
        }
        if (i18 >= topBarTileNum || i18 <= 0 || strM.length() <= 0) {
            i4 = 0;
        } else {
            List listSplit5 = new Regex(",").split(strM);
            if (listSplit5.isEmpty()) {
                collectionTake = EmptyList.INSTANCE;
                i4 = 0;
                while (i < r7) {
                }
            } else {
                ListIterator listIterator4 = listSplit5.listIterator(listSplit5.size());
                while (listIterator4.hasPrevious()) {
                    if (((String) listIterator4.previous()).length() != 0) {
                        collectionTake = CollectionsKt___CollectionsKt.take(listSplit5, listIterator4.nextIndex() + 1);
                        break;
                    }
                }
                collectionTake = EmptyList.INSTANCE;
                i4 = 0;
                for (String str5 : (String[]) collectionTake.toArray(new String[0])) {
                    str5.getClass();
                    TileSpec.Companion.getClass();
                    TileSpec tileSpecCreate5 = TileSpec.Companion.create(str5);
                    boolean z8 = false;
                    int length7 = str5.length() - 1;
                    int i19 = 0;
                    while (i19 <= length7) {
                        boolean z9 = Intrinsics.compare(str5.charAt(!z8 ? i19 : length7), 32) <= 0;
                        if (z8) {
                            if (!z9) {
                                break;
                            }
                            length7--;
                        } else if (z9) {
                            i19++;
                        } else {
                            z8 = true;
                        }
                    }
                    if (str5.subSequence(i19, length7 + 1).toString().length() != 0 && !arrayList6.contains(tileSpecCreate5) && isAvailableBarTile(tileSpecCreate5)) {
                        arrayList6.add(tileSpecCreate5);
                        if (arrayList6.size() == topBarTileNum) {
                            break;
                        }
                    }
                }
            }
        }
        ArrayList arrayList8 = new ArrayList();
        int size = arrayList6.size();
        int i20 = i4;
        while (i20 < size) {
            Object obj2 = arrayList6.get(i20);
            i20++;
            TileSpec tileSpec = (TileSpec) obj2;
            if (!(tileSpec instanceof TileSpec.Invalid) && (qSTileRequestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingByBar, tileSpec)) != null) {
                arrayList.add(qSTileRequestTileUsing);
                arrayList8.add(new TileModel(tileSpec, qSTileRequestTileUsing));
            }
        }
        if (i2 == 0) {
            this.topBarTile = arrayList8;
        } else if (i2 == 1) {
            this.brightnessVolumeBarTileList = arrayList8;
        } else if (i2 == 2) {
            if (!this.bottomBarTileList.equals(arrayList8)) {
                List listPlus = CollectionsKt___CollectionsKt.plus((Iterable) this.smartViewBarTileList, (Collection) CollectionsKt___CollectionsKt.plus((Iterable) this.bottomBarTileList, (Collection) CollectionsKt___CollectionsKt.plus((Iterable) this.brightnessVolumeBarTileList, (Collection) this.topBarTile)));
                Log.d("CurrentTilesInteractor", "updateCurrentBarTileList= " + listPlus);
                this._currentBarTileList.updateState(null, listPlus);
            }
            this.bottomBarTileList = arrayList8;
        } else if (i2 == 3) {
            this.smartViewBarTileList = arrayList8;
        }
        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i2, arrayList.size(), "getBarTilesByType type=", ", tiles.size =", ", tiles=");
        sbM.append(arrayList);
        Log.d("CurrentTilesInteractor", sbM.toString());
        return arrayList;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlowImpl getCurrentBarTileList() {
        return this.currentBarTileList;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlow getCurrentTiles() {
        return this.currentTiles;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final List getDefaultTiles() {
        List defaultTiles = this.defaultTilesRepository.getDefaultTiles();
        ArrayList arrayList = new ArrayList();
        for (Object obj : defaultTiles) {
            TileSpec tileSpec = (TileSpec) obj;
            if (!isBarTile(tileSpec) && this.tileFeatureChecker.isAvailableCustomTile(tileSpec)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final QSTile getTileBySpecString(String str) {
        if (StringsKt__StringsKt.contains(((List) ((KnoxPolicyTilesRepositoryImpl) this.knoxPolicyTilesRepository).knoxBlockedTiles.$$delegate_0.getValue()).toString(), str, false)) {
            return null;
        }
        TileSpec.Companion.getClass();
        return this.tileInstanceManager.requestTileUsing(this.tileUsingByBar, TileSpec.Companion.create(str));
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final Flow getTilesUpdatedFlow() {
        return this.tilesUpdatedFlow;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlow getUserContext() {
        return this.userContext;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final StateFlow getUserId() {
        return this.userId;
    }

    public final boolean isAvailableBarTile(TileSpec tileSpec) {
        List list;
        if (!this.tileFeatureChecker.isAvailableCustomTile(tileSpec)) {
            return false;
        }
        InstalledTilesComponentRepository installedTilesComponentRepository = this.installedTilesComponentRepository;
        int userId = ((UserTrackerImpl) this.userTracker).getUserId();
        InstalledTilesComponentRepositoryImpl installedTilesComponentRepositoryImpl = (InstalledTilesComponentRepositoryImpl) installedTilesComponentRepository;
        synchronized (installedTilesComponentRepositoryImpl.userMap) {
            list = (List) installedTilesComponentRepositoryImpl.getForUserLocked(userId).getValue();
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(((ServiceInfo) it.next()).getComponentName());
        }
        if (tileSpec instanceof TileSpec.CustomTileSpec) {
            return linkedHashSet.contains(((TileSpec.CustomTileSpec) tileSpec).componentName);
        }
        return true;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isBarTile(TileSpec tileSpec) {
        ReadonlyStateFlow readonlyStateFlow = this.userContext;
        Context context = (Context) readonlyStateFlow.$$delegate_0.getValue();
        int i = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources().getConfiguration().orientation;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.resourcePicker;
        String topBarTileList = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTopBarTileList(i, context);
        String string = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getString(R.string.sec_brightness_volume_bar_tiles_default);
        Context context2 = (Context) readonlyStateFlow.$$delegate_0.getValue();
        int i2 = ((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources().getConfiguration().orientation;
        SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = secQSPanelResourcePicker.resourcePickHelper;
        String smartViewBarTileList = secQSPanelResourcePickHelper.getTargetPicker().getSmartViewBarTileList(i2, context2);
        Context context3 = (Context) readonlyStateFlow.$$delegate_0.getValue();
        String bottomBarTileList = secQSPanelResourcePickHelper.getTargetPicker().getBottomBarTileList(((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources().getConfiguration().orientation, context3);
        StringBuilder sb = new StringBuilder();
        sb.append(topBarTileList);
        sb.append(",");
        sb.append(string);
        sb.append(",");
        sb.append(smartViewBarTileList);
        return CollectionsKt___CollectionsKt.toHashSet(Companion.access$toTileList(Companion, StringsKt__StringsKt.split$default(TransitionKt$$ExternalSyntheticOutline0.m(sb, ",", bottomBarTileList), new String[]{","}, 0, 6), ((Context) readonlyStateFlow.$$delegate_0.getValue()).getResources())).contains(tileSpec);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isLargeBarTile(String str) {
        if (str == null) {
            return false;
        }
        TileSpec.Companion.getClass();
        TileSpec tileSpecCreate = TileSpec.Companion.create(str);
        List list = this.topBarTile;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(((TileModel) it.next()).spec);
        }
        if (arrayList.contains(tileSpecCreate)) {
            return true;
        }
        List list2 = this.bottomBarTileList;
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((TileModel) it2.next()).spec);
        }
        if (arrayList2.contains(tileSpecCreate)) {
            return true;
        }
        List list3 = this.smartViewBarTileList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
        Iterator it3 = list3.iterator();
        while (it3.hasNext()) {
            arrayList3.add(((TileModel) it3.next()).spec);
        }
        return arrayList3.contains(tileSpecCreate);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isUnsupportedTile(TileSpec tileSpec) {
        TilesSettingConverter tilesSettingConverter = TilesSettingConverter.INSTANCE;
        String string = ((Context) this.userContext.$$delegate_0.getValue()).getString(R.string.quick_settings_unsupported_tiles);
        tilesSettingConverter.getClass();
        if (!((ArrayList) TilesSettingConverter.toTilesList(string)).contains(tileSpec)) {
            return false;
        }
        Log.d("CurrentTilesInteractor", "isUnsupportedTile " + tileSpec);
        return true;
    }

    public final void onCustomTileRemoved(TileSpec.CustomTileSpec customTileSpec, ComponentName componentName, int i) {
        boolean z = ScRune.QUICK_MANAGE_MULTI_QSHOST;
        CustomTileAddedRepository customTileAddedRepository = this.customTileAddedRepository;
        CustomTileStatePersister customTileStatePersister = this.customTileStatePersister;
        if (!z) {
            TileLifecycleManager tileLifecycleManagerCreate = this.tileLifecycleManagerFactory.create(new Intent().setComponent(componentName), UserHandle.of(i));
            tileLifecycleManagerCreate.onStopListening();
            tileLifecycleManagerCreate.onTileRemoved();
            ((CustomTileStatePersisterImpl) customTileStatePersister).sharedPreferences.edit().remove(new TileServiceKey(componentName, i).string).apply();
            ((CustomTileAddedSharedPrefsRepository) customTileAddedRepository).setTileAdded(componentName, false, i);
            tileLifecycleManagerCreate.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManagerCreate, 3));
            return;
        }
        QSTile qSTileRequestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingByPanel, customTileSpec);
        if (qSTileRequestTileUsing instanceof CustomTile) {
            TileLifecycleManager tileLifecycleManager = ((CustomTile) qSTileRequestTileUsing).mServiceManager.mStateManager;
            tileLifecycleManager.onStopListening();
            tileLifecycleManager.onTileRemoved();
            tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
            ((CustomTileStatePersisterImpl) customTileStatePersister).sharedPreferences.edit().remove(new TileServiceKey(componentName, i).string).apply();
            ((CustomTileAddedSharedPrefsRepository) customTileAddedRepository).setTileAdded(componentName, false, i);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void refreshCurrentTiles() {
        Log.d("CurrentTilesInteractor", "refreshCurrentTiles");
        this.refreshTiles.tryEmit(Unit.INSTANCE);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void removeTiles(Collection collection) {
        Set set = CollectionsKt___CollectionsKt.toSet(getCurrentTilesSpecs());
        int iIntValue = ((Number) this.currentUser.getValue()).intValue();
        Set set2 = set;
        Collection collection2 = collection;
        Set setIntersect = CollectionsKt___CollectionsKt.intersect(set2, collection2);
        ArrayList arrayList = new ArrayList();
        for (Object obj : setIntersect) {
            if (obj instanceof TileSpec.CustomTileSpec) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            TileSpec.CustomTileSpec customTileSpec = (TileSpec.CustomTileSpec) obj2;
            onCustomTileRemoved(customTileSpec, customTileSpec.componentName, iIntValue);
        }
        if (CollectionsKt___CollectionsKt.intersect(set2, collection2).isEmpty()) {
            return;
        }
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass2(iIntValue, collection, null), 7);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void resetTiles() {
        ((RemovedTilesInteractorImpl) this.removedTilesInteractor).resetRemovedTiles();
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C09941(null), 7);
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void setTiles(List list) {
        List currentTilesSpecs = getCurrentTilesSpecs();
        int iIntValue = ((Number) this.currentUser.getValue()).intValue();
        if (currentTilesSpecs.equals(list)) {
            return;
        }
        List listMinus = CollectionsKt___CollectionsKt.minus((Iterable) currentTilesSpecs, (Iterable) list);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listMinus) {
            if (obj instanceof TileSpec.CustomTileSpec) {
                arrayList.add(obj);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            TileSpec.CustomTileSpec customTileSpec = (TileSpec.CustomTileSpec) obj2;
            onCustomTileRemoved(customTileSpec, customTileSpec.componentName, iIntValue);
        }
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new C09952(iIntValue, list, null), 7);
    }
}
