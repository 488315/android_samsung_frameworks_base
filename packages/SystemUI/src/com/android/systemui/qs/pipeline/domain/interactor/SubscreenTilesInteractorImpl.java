package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.Context;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
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
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileLifecycleManager$$ExternalSyntheticLambda0;
import com.android.systemui.qs.nano.QsTileState;
import com.android.systemui.qs.pipeline.data.domain.interactor.FotaUpdateInteractor;
import com.android.systemui.qs.pipeline.data.repository.DefaultTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.InstalledTilesComponentRepository;
import com.android.systemui.qs.pipeline.data.repository.MinimumTilesRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.data.repository.TilesSettingConverter;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.QSPipelineFlagsRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.util.DeviceType;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class SubscreenTilesInteractorImpl implements CurrentTilesInteractor {
    public static final boolean DEBUG;
    public final StateFlowImpl _currentSpecsAndTiles;
    public final StateFlowImpl _userContext;
    public final CoroutineDispatcher backgroundDispatcher;
    public final StateFlowImpl currentBarTileList;
    public final ReadonlyStateFlow currentTiles;
    public final StateFlowImpl currentUser;
    public final DefaultTilesRepository defaultTilesRepository;
    public final QSPipelineFlagsRepository featureFlags;
    public final FotaUpdateInteractor fotaUpdateInteractor;
    public final InstalledTilesComponentRepository installedTilesComponentRepository;
    public final QSPipelineLogger logger;
    public final CoroutineDispatcher mainDispatcher;
    public final MinimumTilesRepository minimumTilesRepository;
    public final RetailModeRepository retailModeRepository;
    public final CoroutineScope scope;
    public final Map specsToTiles;
    public final QSFactory tileFactory;
    public final TileFeatureChecker tileFeatureChecker;
    public final SecQSTileInstanceManager tileInstanceManager;
    public final TileSpecRepository tileSpecRepository;
    public final String tileUsingBySubScreen;
    public final SharedFlowImpl tilesUpdatedFlow;
    public final Flow userAndTiles;
    public final ReadonlyStateFlow userContext;
    public final ReadonlyStateFlow userId;
    public final UserRepository userRepository;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$addTile$1, reason: invalid class name and case insensitive filesystem */
    final class C09991 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $position;
        final /* synthetic */ TileSpec $spec;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C09991(TileSpec tileSpec, int i, Continuation continuation) {
            super(2, continuation);
            this.$spec = tileSpec;
            this.$position = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SubscreenTilesInteractorImpl.this.new C09991(this.$spec, this.$position, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C09991) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                final ReadonlyStateFlow readonlyStateFlow = SubscreenTilesInteractorImpl.this.currentTiles;
                Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$addTile$1$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
            SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = SubscreenTilesInteractorImpl.this;
            TileSpecRepository tileSpecRepository = subscreenTilesInteractorImpl.tileSpecRepository;
            int i2 = ((UserRepositoryImpl) subscreenTilesInteractorImpl.userRepository).getSelectedUserInfo().id;
            TileSpec tileSpec = this.$spec;
            int i3 = this.$position;
            this.label = 2;
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$removeTiles$2, reason: invalid class name */
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
            return SubscreenTilesInteractorImpl.this.new AnonymousClass2(this.$user, this.$specs, continuation);
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
                TileSpecRepository tileSpecRepository = SubscreenTilesInteractorImpl.this.tileSpecRepository;
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

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$resetTiles$1, reason: invalid class name and case insensitive filesystem */
    final class C10001 extends SuspendLambda implements Function2 {
        int label;

        public C10001(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SubscreenTilesInteractorImpl.this.new C10001(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10001) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = SubscreenTilesInteractorImpl.this;
                TileSpecRepository tileSpecRepository = subscreenTilesInteractorImpl.tileSpecRepository;
                int iIntValue = ((Number) subscreenTilesInteractorImpl.currentUser.getValue()).intValue();
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

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$setTiles$2, reason: invalid class name and case insensitive filesystem */
    final class C10012 extends SuspendLambda implements Function2 {
        final /* synthetic */ List<TileSpec> $specs;
        final /* synthetic */ int $user;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        public C10012(int i, List<? extends TileSpec> list, Continuation continuation) {
            super(2, continuation);
            this.$user = i;
            this.$specs = list;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SubscreenTilesInteractorImpl.this.new C10012(this.$user, this.$specs, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10012) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TileSpecRepository tileSpecRepository = SubscreenTilesInteractorImpl.this.tileSpecRepository;
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

    static {
        new Companion(null);
        DEBUG = !DeviceType.isShipBuild();
    }

    public SubscreenTilesInteractorImpl(TileSpecRepository tileSpecRepository, InstalledTilesComponentRepository installedTilesComponentRepository, UserRepository userRepository, MinimumTilesRepository minimumTilesRepository, RetailModeRepository retailModeRepository, Lazy lazy, QSFactory qSFactory, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, CoroutineScope coroutineScope, QSPipelineLogger qSPipelineLogger, QSPipelineFlagsRepository qSPipelineFlagsRepository, BootAnimationFinishedCache bootAnimationFinishedCache, SecQSTileInstanceManager secQSTileInstanceManager, SecQSPanelResourcePicker secQSPanelResourcePicker, DefaultTilesRepository defaultTilesRepository, TileFeatureChecker tileFeatureChecker, FotaUpdateInteractor fotaUpdateInteractor) {
        this.tileSpecRepository = tileSpecRepository;
        this.installedTilesComponentRepository = installedTilesComponentRepository;
        this.userRepository = userRepository;
        this.minimumTilesRepository = minimumTilesRepository;
        this.retailModeRepository = retailModeRepository;
        this.tileFactory = qSFactory;
        this.userTracker = userTracker;
        this.mainDispatcher = coroutineDispatcher;
        this.backgroundDispatcher = coroutineDispatcher2;
        this.scope = coroutineScope;
        this.logger = qSPipelineLogger;
        this.featureFlags = qSPipelineFlagsRepository;
        this.tileInstanceManager = secQSTileInstanceManager;
        this.defaultTilesRepository = defaultTilesRepository;
        this.tileFeatureChecker = tileFeatureChecker;
        this.fotaUpdateInteractor = fotaUpdateInteractor;
        EmptyList emptyList = EmptyList.INSTANCE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(emptyList);
        this._currentSpecsAndTiles = stateFlowImplMutableStateFlow;
        this.currentTiles = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.specsToTiles = new LinkedHashMap();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(Integer.valueOf(userTrackerImpl.getUserId()));
        this.currentUser = stateFlowImplMutableStateFlow2;
        this.userId = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(userTrackerImpl.getUserContext());
        this._userContext = stateFlowImplMutableStateFlow3;
        this.userContext = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.tileUsingBySubScreen = "Subscreen";
        this.tilesUpdatedFlow = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.currentBarTileList = StateFlowKt.MutableStateFlow(emptyList);
        this.userAndTiles = FlowKt.flowOn(com.android.systemui.util.kotlin.FlowKt.pairwiseBy(FlowKt.distinctUntilChanged(FlowKt.transformLatest(stateFlowImplMutableStateFlow2, new SubscreenTilesInteractorImpl$special$$inlined$flatMapLatest$1(null, this))), new UserTilesAndComponents(-1, emptyList, EmptySet.INSTANCE, null, 8, null), new SubscreenTilesInteractorImpl$userAndTiles$2(null)), coroutineDispatcher2);
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("SubscreenTilesInteractor", "SubscreenTilesInteractor not initialized for non-primary user, just return");
            return;
        }
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            BootAnimationFinishedCacheImpl bootAnimationFinishedCacheImpl = (BootAnimationFinishedCacheImpl) bootAnimationFinishedCache;
            if (bootAnimationFinishedCacheImpl.bootAnimationFinished.get()) {
                BuildersKt.launch$default(coroutineScope, null, null, new SubscreenTilesInteractorImpl$startTileCollection$1(this, null), 3);
            } else {
                bootAnimationFinishedCacheImpl.addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl.1
                    @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
                    public final void onBootAnimationFinished() {
                        boolean z = SubscreenTilesInteractorImpl.DEBUG;
                        SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = SubscreenTilesInteractorImpl.this;
                        subscreenTilesInteractorImpl.getClass();
                        BuildersKt.launch$default(subscreenTilesInteractorImpl.scope, null, null, new SubscreenTilesInteractorImpl$startTileCollection$1(subscreenTilesInteractorImpl, null), 3);
                    }
                });
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$createTile(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, TileSpec tileSpec, ContinuationImpl continuationImpl) throws Throwable {
        SubscreenTilesInteractorImpl$createTile$1 subscreenTilesInteractorImpl$createTile$1;
        subscreenTilesInteractorImpl.getClass();
        if (continuationImpl instanceof SubscreenTilesInteractorImpl$createTile$1) {
            subscreenTilesInteractorImpl$createTile$1 = (SubscreenTilesInteractorImpl$createTile$1) continuationImpl;
            int i = subscreenTilesInteractorImpl$createTile$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                subscreenTilesInteractorImpl$createTile$1.label = i - Integer.MIN_VALUE;
            } else {
                subscreenTilesInteractorImpl$createTile$1 = new SubscreenTilesInteractorImpl$createTile$1(subscreenTilesInteractorImpl, continuationImpl);
            }
        }
        Object objWithContext = subscreenTilesInteractorImpl$createTile$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = subscreenTilesInteractorImpl$createTile$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            SubscreenTilesInteractorImpl$createTile$tile$1 subscreenTilesInteractorImpl$createTile$tile$1 = new SubscreenTilesInteractorImpl$createTile$tile$1(subscreenTilesInteractorImpl, tileSpec, null);
            subscreenTilesInteractorImpl$createTile$1.L$0 = subscreenTilesInteractorImpl;
            subscreenTilesInteractorImpl$createTile$1.L$1 = tileSpec;
            subscreenTilesInteractorImpl$createTile$1.label = 1;
            objWithContext = BuildersKt.withContext(subscreenTilesInteractorImpl.mainDispatcher, subscreenTilesInteractorImpl$createTile$tile$1, subscreenTilesInteractorImpl$createTile$1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            tileSpec = (TileSpec) subscreenTilesInteractorImpl$createTile$1.L$1;
            subscreenTilesInteractorImpl = (SubscreenTilesInteractorImpl) subscreenTilesInteractorImpl$createTile$1.L$0;
            ResultKt.throwOnFailure(objWithContext);
        }
        QSTile qSTile = (QSTile) objWithContext;
        if (qSTile == null) {
            subscreenTilesInteractorImpl.logger.logTileNotFoundInFactory(tileSpec);
            return null;
        }
        if (qSTile.isAvailable()) {
            subscreenTilesInteractorImpl.logger.logTileCreated(tileSpec);
            return qSTile;
        }
        subscreenTilesInteractorImpl.logger.logTileDestroyed(tileSpec, QSPipelineLogger.TileDestroyedReason.NEW_TILE_NOT_AVAILABLE);
        if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
            subscreenTilesInteractorImpl.tileInstanceManager.releaseTileUsing(subscreenTilesInteractorImpl.tileUsingBySubScreen, tileSpec);
            return null;
        }
        qSTile.destroy();
        return null;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void addTile(TileSpec tileSpec, int i) {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            BuildersKt.launch$default(this.scope, this.backgroundDispatcher, null, new C09991(tileSpec, i, null), 2);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final QSTile createTileSync(TileSpec tileSpec) {
        if (!ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            return null;
        }
        this.featureFlags.getClass();
        return this.tileFactory.createTile(tileSpec.getSpec());
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("SubscreenTileInteractorImpl:");
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
            if (this.tileFeatureChecker.isAvailableCustomTile((TileSpec) obj)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
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

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final boolean isUnsupportedTile(TileSpec tileSpec) {
        TilesSettingConverter tilesSettingConverter = TilesSettingConverter.INSTANCE;
        String string = ((Context) this.userContext.$$delegate_0.getValue()).getString(R.string.quick_settings_unsupported_tiles);
        tilesSettingConverter.getClass();
        if (!((ArrayList) TilesSettingConverter.toTilesList(string)).contains(tileSpec)) {
            return false;
        }
        Log.d("SubscreenTilesInteractor", "isUnsupportedTile " + tileSpec);
        return true;
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void removeTiles(Collection collection) {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
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
                QSTile qSTileRequestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingBySubScreen, (TileSpec.CustomTileSpec) obj2);
                if (qSTileRequestTileUsing instanceof CustomTile) {
                    TileLifecycleManager tileLifecycleManager = ((CustomTile) qSTileRequestTileUsing).mServiceManager.mStateManager;
                    tileLifecycleManager.onStopListening();
                    tileLifecycleManager.onTileRemoved();
                    tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
                }
            }
            if (CollectionsKt___CollectionsKt.intersect(set2, collection2).isEmpty()) {
                return;
            }
            BuildersKt.launch$default(this.scope, null, null, new AnonymousClass2(iIntValue, collection, null), 3);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void resetTiles() {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
            BuildersKt.launch$default(this.scope, null, null, new C10001(null), 3);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void setTiles(List list) {
        if (ScRune.QUICK_MANAGE_SUBSCREEN_TILE_LIST) {
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
                QSTile qSTileRequestTileUsing = this.tileInstanceManager.requestTileUsing(this.tileUsingBySubScreen, (TileSpec.CustomTileSpec) obj2);
                if (qSTileRequestTileUsing instanceof CustomTile) {
                    TileLifecycleManager tileLifecycleManager = ((CustomTile) qSTileRequestTileUsing).mServiceManager.mStateManager;
                    tileLifecycleManager.onStopListening();
                    tileLifecycleManager.onTileRemoved();
                    tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
                }
            }
            BuildersKt.launch$default(this.scope, null, null, new C10012(iIntValue, list, null), 3);
        }
    }

    @Override // com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor
    public final void refreshCurrentTiles() {
    }
}
