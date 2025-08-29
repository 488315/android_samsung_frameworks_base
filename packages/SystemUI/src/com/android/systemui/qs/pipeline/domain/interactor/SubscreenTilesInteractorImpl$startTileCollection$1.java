package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.pm.UserInfo;
import android.util.Log;
import com.android.systemui.ScRune;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSTileInstanceManager;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.data.domain.interactor.FotaUpdateInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.FotaUpdateInteractorImpl;
import com.android.systemui.qs.pipeline.data.repository.MinimumTilesResourceRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
final class SubscreenTilesInteractorImpl$startTileCollection$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ SubscreenTilesInteractorImpl this$0;

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$startTileCollection$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ SubscreenTilesInteractorImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = subscreenTilesInteractorImpl;
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
                final SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = this.this$0;
                UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) subscreenTilesInteractorImpl.userRepository).selectedUserInfo;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl.startTileCollection.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        SubscreenTilesInteractorImpl subscreenTilesInteractorImpl2 = subscreenTilesInteractorImpl;
                        subscreenTilesInteractorImpl2.currentUser.updateState(null, new Integer(((UserInfo) obj2).id));
                        subscreenTilesInteractorImpl2._userContext.setValue(((UserTrackerImpl) subscreenTilesInteractorImpl2.userTracker).getUserContext());
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (userRepositoryImpl$special$$inlined$map$2.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$startTileCollection$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ SubscreenTilesInteractorImpl this$0;

        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$startTileCollection$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$launch;
            int I$0;
            /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            boolean Z$0;
            int label;
            final /* synthetic */ SubscreenTilesInteractorImpl this$0;

            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$startTileCollection$1$2$1$5, reason: invalid class name */
            final class AnonymousClass5 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ SubscreenTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass5(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = subscreenTilesInteractorImpl;
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
                        SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = subscreenTilesInteractorImpl.tileSpecRepository;
                        int iIntValue = ((Number) subscreenTilesInteractorImpl.currentUser.getValue()).intValue();
                        this.label = 1;
                        if (tileSpecRepository.prependDefault(iIntValue, this) == coroutineSingletons) {
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

            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.SubscreenTilesInteractorImpl$startTileCollection$1$2$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                final /* synthetic */ List<TileSpec> $resolvedSpecs;
                int label;
                final /* synthetic */ SubscreenTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public AnonymousClass6(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, List<? extends TileSpec> list, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = subscreenTilesInteractorImpl;
                    this.$resolvedSpecs = list;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass6(this.this$0, this.$resolvedSpecs, continuation);
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
                        SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = subscreenTilesInteractorImpl.tileSpecRepository;
                        int iIntValue = ((Number) subscreenTilesInteractorImpl.currentUser.getValue()).intValue();
                        List<TileSpec> list = this.$resolvedSpecs;
                        this.label = 1;
                        if (tileSpecRepository.setTiles(iIntValue, list, this) == coroutineSingletons) {
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
            public AnonymousClass1(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.this$0 = subscreenTilesInteractorImpl;
                this.$$this$launch = coroutineScope;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, this.$$this$launch, continuation);
                anonymousClass1.L$0 = obj;
                return anonymousClass1;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((AnonymousClass1) create((DataWithUserChange) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            /* JADX WARN: Code restructure failed: missing block: B:81:0x024e, code lost:
            
                if (r5 == r1) goto L90;
             */
            /* JADX WARN: Code restructure failed: missing block: B:89:0x0278, code lost:
            
                if (r6 == r1) goto L90;
             */
            /* JADX WARN: Removed duplicated region for block: B:41:0x017b  */
            /* JADX WARN: Removed duplicated region for block: B:80:0x0237  */
            /* JADX WARN: Removed duplicated region for block: B:85:0x0256  */
            /* JADX WARN: Removed duplicated region for block: B:93:0x0280  */
            /* JADX WARN: Removed duplicated region for block: B:95:0x028e  */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x01a7 -> B:94:0x0287). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:89:0x0278 -> B:91:0x027b). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) throws Throwable {
                int i;
                boolean z;
                Set set;
                Iterator it;
                SubscreenTilesInteractorImpl subscreenTilesInteractorImpl;
                Map map;
                boolean z2;
                List list;
                String str;
                TileSpec tileSpec;
                Object objAccess$createTile;
                int i2;
                QSTile qSTile;
                char c;
                QSTile qSTile2;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i3 = this.label;
                char c2 = 2;
                int i4 = 1;
                String str2 = "SubscreenTilesInteractor";
                if (i3 == 0) {
                    ResultKt.throwOnFailure(obj);
                    DataWithUserChange dataWithUserChange = (DataWithUserChange) this.L$0;
                    i = dataWithUserChange.userId;
                    List list2 = dataWithUserChange.tiles;
                    Set set2 = dataWithUserChange.installedComponents;
                    if (SubscreenTilesInteractorImpl.DEBUG) {
                        Log.i("SubscreenTilesInteractor", "userId = " + i);
                        Log.i("SubscreenTilesInteractor", "newTileList = " + list2);
                        Log.i("SubscreenTilesInteractor", "components = " + set2);
                    }
                    FotaUpdateInteractor fotaUpdateInteractor = this.this$0.fotaUpdateInteractor;
                    QSType qSType = QSType.SUBQS;
                    Boolean bool = (Boolean) ((LinkedHashMap) ((FotaUpdateInteractorImpl) fotaUpdateInteractor).fotaUpdateMap).get(qSType);
                    if (bool != null ? bool.booleanValue() : false) {
                        Log.w("SubscreenTilesInteractor", "Fota update");
                        this.this$0.resetTiles();
                        ((FotaUpdateInteractorImpl) this.this$0.fotaUpdateInteractor).finishFotaUpdate(qSType);
                    }
                    Map map2 = this.this$0.specsToTiles;
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Map.Entry entry : ((LinkedHashMap) map2).entrySet()) {
                        if (!list2.contains(entry.getKey()) && (entry.getValue() instanceof SubscreenTilesInteractorImpl.TileOrNotInstalled.Tile)) {
                            linkedHashMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    SubscreenTilesInteractorImpl subscreenTilesInteractorImpl2 = this.this$0;
                    Iterator it2 = linkedHashMap.entrySet().iterator();
                    while (true) {
                        boolean zHasNext = it2.hasNext();
                        z = dataWithUserChange.userChange;
                        if (!zHasNext) {
                            break;
                        }
                        Map.Entry entry2 = (Map.Entry) it2.next();
                        subscreenTilesInteractorImpl2.logger.logTileDestroyed((TileSpec) entry2.getKey(), z ? QSPipelineLogger.TileDestroyedReason.TILE_NOT_PRESENT_IN_NEW_USER : QSPipelineLogger.TileDestroyedReason.TILE_REMOVED);
                        if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
                            subscreenTilesInteractorImpl2.tileInstanceManager.releaseTileUsing(subscreenTilesInteractorImpl2.tileUsingBySubScreen, (TileSpec) entry2.getKey());
                        } else {
                            ((SubscreenTilesInteractorImpl.TileOrNotInstalled.Tile) entry2.getValue()).tile.destroy();
                        }
                    }
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    SubscreenTilesInteractorImpl subscreenTilesInteractorImpl3 = this.this$0;
                    set = set2;
                    it = list2.iterator();
                    subscreenTilesInteractorImpl = subscreenTilesInteractorImpl3;
                    map = linkedHashMap2;
                    z2 = z;
                    list = list2;
                    if (it.hasNext()) {
                    }
                } else if (i3 == 1) {
                    z2 = this.Z$0;
                    i = this.I$0;
                    tileSpec = (TileSpec) this.L$5;
                    it = (Iterator) this.L$4;
                    subscreenTilesInteractorImpl = (SubscreenTilesInteractorImpl) this.L$3;
                    map = (Map) this.L$2;
                    set = (Set) this.L$1;
                    list = (List) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    i2 = 1;
                    str = "SubscreenTilesInteractor";
                    objAccess$createTile = obj;
                    qSTile = (QSTile) objAccess$createTile;
                    c = 2;
                    if (qSTile != null) {
                    }
                    char c3 = c;
                    i4 = i2;
                    c2 = c3;
                    str2 = str;
                    if (it.hasNext()) {
                    }
                } else {
                    if (i3 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    z2 = this.Z$0;
                    i = this.I$0;
                    tileSpec = (TileSpec) this.L$5;
                    it = (Iterator) this.L$4;
                    subscreenTilesInteractorImpl = (SubscreenTilesInteractorImpl) this.L$3;
                    map = (Map) this.L$2;
                    set = (Set) this.L$1;
                    list = (List) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    c = 2;
                    i2 = 1;
                    str = "SubscreenTilesInteractor";
                    Object objAccess$createTile2 = obj;
                    qSTile = (QSTile) objAccess$createTile2;
                    if (qSTile != null) {
                        map.put(tileSpec, SubscreenTilesInteractorImpl.TileOrNotInstalled.Tile.m2910boximpl(qSTile));
                    }
                    char c32 = c;
                    i4 = i2;
                    c2 = c32;
                    str2 = str;
                    if (it.hasNext()) {
                        tileSpec = (TileSpec) it.next();
                        if (!map.containsKey(tileSpec)) {
                            if (!subscreenTilesInteractorImpl.tileFeatureChecker.isAvailableCustomTile(tileSpec)) {
                                Boxing.boxInt(Log.d(str2, tileSpec + " - except by feature checker"));
                            } else {
                                if (!(tileSpec instanceof TileSpec.CustomTileSpec) || set.contains(((TileSpec.CustomTileSpec) tileSpec).componentName)) {
                                    if (subscreenTilesInteractorImpl.specsToTiles.containsKey(tileSpec)) {
                                        SubscreenTilesInteractorImpl.TileOrNotInstalled tileOrNotInstalled = (SubscreenTilesInteractorImpl.TileOrNotInstalled) MapsKt__MapsKt.getValue(tileSpec, subscreenTilesInteractorImpl.specsToTiles);
                                        if (tileOrNotInstalled instanceof SubscreenTilesInteractorImpl.TileOrNotInstalled.NotInstalled) {
                                            str = str2;
                                        } else {
                                            if (!(tileOrNotInstalled instanceof SubscreenTilesInteractorImpl.TileOrNotInstalled.Tile)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            qSTile = ((SubscreenTilesInteractorImpl.TileOrNotInstalled.Tile) tileOrNotInstalled).tile;
                                            boolean zIsAvailable = qSTile.isAvailable();
                                            String str3 = subscreenTilesInteractorImpl.tileUsingBySubScreen;
                                            SecQSTileInstanceManager secQSTileInstanceManager = subscreenTilesInteractorImpl.tileInstanceManager;
                                            QSPipelineLogger qSPipelineLogger = subscreenTilesInteractorImpl.logger;
                                            if (zIsAvailable) {
                                                str = str2;
                                                if (qSTile instanceof CustomTile) {
                                                    CustomTile customTile = (CustomTile) qSTile;
                                                    qSTile2 = qSTile;
                                                    if (customTile.mUser != i) {
                                                        if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
                                                            secQSTileInstanceManager.releaseTileUsing(str3, tileSpec);
                                                        } else {
                                                            customTile.destroy();
                                                        }
                                                        qSPipelineLogger.logTileDestroyed(tileSpec, QSPipelineLogger.TileDestroyedReason.CUSTOM_TILE_USER_CHANGED);
                                                    }
                                                } else if (z2) {
                                                    qSTile.userSwitch(i);
                                                    qSPipelineLogger.logTileUserChanged(tileSpec, i);
                                                    if (qSTile == null) {
                                                        this.L$0 = list;
                                                        this.L$1 = set;
                                                        this.L$2 = map;
                                                        this.L$3 = subscreenTilesInteractorImpl;
                                                        this.L$4 = it;
                                                        this.L$5 = tileSpec;
                                                        this.I$0 = i;
                                                        this.Z$0 = z2;
                                                        i2 = 1;
                                                        this.label = 1;
                                                        objAccess$createTile = SubscreenTilesInteractorImpl.access$createTile(subscreenTilesInteractorImpl, tileSpec, this);
                                                    } else {
                                                        i2 = 1;
                                                        c = 2;
                                                        if (qSTile != null) {
                                                        }
                                                        char c322 = c;
                                                        i4 = i2;
                                                        c2 = c322;
                                                        str2 = str;
                                                        if (it.hasNext()) {
                                                            int i5 = i4;
                                                            String str4 = str2;
                                                            List list3 = CollectionsKt___CollectionsKt.toList(map.keySet());
                                                            ((LinkedHashMap) this.this$0.specsToTiles).clear();
                                                            this.this$0.specsToTiles.putAll(map);
                                                            LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                                                            for (Map.Entry entry3 : map.entrySet()) {
                                                                if (entry3.getValue() instanceof SubscreenTilesInteractorImpl.TileOrNotInstalled.Tile) {
                                                                    linkedHashMap3.put(entry3.getKey(), entry3.getValue());
                                                                }
                                                            }
                                                            ArrayList arrayList = new ArrayList(linkedHashMap3.size());
                                                            for (Map.Entry entry4 : linkedHashMap3.entrySet()) {
                                                                arrayList.add(new TileModel((TileSpec) entry4.getKey(), ((SubscreenTilesInteractorImpl.TileOrNotInstalled.Tile) entry4.getValue()).tile));
                                                            }
                                                            Log.i(str4, "newTileList = " + list3);
                                                            this.this$0._currentSpecsAndTiles.updateState(null, arrayList);
                                                            QSPipelineLogger qSPipelineLogger2 = this.this$0.logger;
                                                            LinkedHashMap linkedHashMap4 = new LinkedHashMap();
                                                            for (Map.Entry entry5 : map.entrySet()) {
                                                                if (entry5.getValue() instanceof SubscreenTilesInteractorImpl.TileOrNotInstalled.NotInstalled) {
                                                                    linkedHashMap4.put(entry5.getKey(), entry5.getValue());
                                                                }
                                                            }
                                                            qSPipelineLogger2.logTilesNotInstalled(i, linkedHashMap4.keySet());
                                                            int size = arrayList.size();
                                                            SubscreenTilesInteractorImpl subscreenTilesInteractorImpl4 = this.this$0;
                                                            if (size < (subscreenTilesInteractorImpl4.retailModeRepository.getInRetailMode() ? i5 : ((MinimumTilesResourceRepository) subscreenTilesInteractorImpl4.minimumTilesRepository).minNumberOfTiles)) {
                                                                BuildersKt.launch$default(this.$$this$launch, null, null, new AnonymousClass5(this.this$0, null), 3);
                                                            } else if (!Intrinsics.areEqual(list3, list)) {
                                                                BuildersKt.launch$default(this.$$this$launch, null, null, new AnonymousClass6(this.this$0, list3, null), 3);
                                                            }
                                                            return Unit.INSTANCE;
                                                        }
                                                    }
                                                } else {
                                                    qSTile2 = qSTile;
                                                }
                                                qSTile = qSTile2;
                                                if (qSTile == null) {
                                                }
                                            } else {
                                                str = str2;
                                                qSPipelineLogger.logTileDestroyed(tileSpec, QSPipelineLogger.TileDestroyedReason.EXISTING_TILE_NOT_AVAILABLE);
                                                if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
                                                    secQSTileInstanceManager.releaseTileUsing(str3, tileSpec);
                                                } else {
                                                    qSTile.destroy();
                                                }
                                            }
                                        }
                                        qSTile = null;
                                        if (qSTile == null) {
                                        }
                                    } else {
                                        i2 = i4;
                                        str = str2;
                                        this.L$0 = list;
                                        this.L$1 = set;
                                        this.L$2 = map;
                                        this.L$3 = subscreenTilesInteractorImpl;
                                        this.L$4 = it;
                                        this.L$5 = tileSpec;
                                        this.I$0 = i;
                                        this.Z$0 = z2;
                                        c = 2;
                                        this.label = 2;
                                        objAccess$createTile2 = SubscreenTilesInteractorImpl.access$createTile(subscreenTilesInteractorImpl, tileSpec, this);
                                    }
                                    return coroutineSingletons;
                                }
                                map.put(tileSpec, SubscreenTilesInteractorImpl.TileOrNotInstalled.NotInstalled.INSTANCE);
                            }
                        }
                        int i6 = i4;
                        c = c2;
                        i2 = i6;
                        str = str2;
                        char c3222 = c;
                        i4 = i2;
                        c2 = c3222;
                        str2 = str;
                        if (it.hasNext()) {
                        }
                    }
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = subscreenTilesInteractorImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.this$0, continuation);
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
                SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = this.this$0;
                Flow flow = subscreenTilesInteractorImpl.userAndTiles;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(subscreenTilesInteractorImpl, coroutineScope, null);
                this.label = 1;
                if (FlowKt.collectLatest(flow, anonymousClass1, this) == coroutineSingletons) {
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
    public SubscreenTilesInteractorImpl$startTileCollection$1(SubscreenTilesInteractorImpl subscreenTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = subscreenTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SubscreenTilesInteractorImpl$startTileCollection$1 subscreenTilesInteractorImpl$startTileCollection$1 = new SubscreenTilesInteractorImpl$startTileCollection$1(this.this$0, continuation);
        subscreenTilesInteractorImpl$startTileCollection$1.L$0 = obj;
        return subscreenTilesInteractorImpl$startTileCollection$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SubscreenTilesInteractorImpl$startTileCollection$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 3);
        SubscreenTilesInteractorImpl subscreenTilesInteractorImpl = this.this$0;
        BuildersKt.launch$default(coroutineScope, subscreenTilesInteractorImpl.backgroundDispatcher, null, new AnonymousClass2(subscreenTilesInteractorImpl, null), 2);
        return Unit.INSTANCE;
    }
}
