package com.android.systemui.qs.pipeline.domain.interactor;

import android.content.pm.UserInfo;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.ScRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.SecQSTileInstanceManager;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.pipeline.dagger.QSType;
import com.android.systemui.qs.pipeline.data.domain.interactor.FotaUpdateInteractorImpl;
import com.android.systemui.qs.pipeline.data.domain.interactor.RemovedTilesInteractorImpl;
import com.android.systemui.qs.pipeline.data.repository.MinimumTilesResourceRepository;
import com.android.systemui.qs.pipeline.data.repository.TileSpecRepository;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl;
import com.android.systemui.qs.pipeline.domain.model.TileModel;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger$$ExternalSyntheticLambda0;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl$special$$inlined$map$2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
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
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes2.dex */
final class CurrentTilesInteractorImpl$startTileCollection$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ CurrentTilesInteractorImpl this$0;

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ CurrentTilesInteractorImpl this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = currentTilesInteractorImpl;
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
                final CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                UserRepositoryImpl$special$$inlined$map$2 userRepositoryImpl$special$$inlined$map$2 = ((UserRepositoryImpl) currentTilesInteractorImpl.userRepository).selectedUserInfo;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl.startTileCollection.1.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        UserInfo userInfo = (UserInfo) obj2;
                        boolean z = ScRune.QUICK_MANAGE_MULTI_QSHOST;
                        CurrentTilesInteractorImpl currentTilesInteractorImpl2 = currentTilesInteractorImpl;
                        if (z) {
                            final SecQSTileInstanceManager secQSTileInstanceManager = currentTilesInteractorImpl2.tileInstanceManager;
                            final int i2 = userInfo.id;
                            if (secQSTileInstanceManager.mUserId != i2) {
                                secQSTileInstanceManager.mUserId = i2;
                                Log.i("SecQSTileInstanceManager", "onUserChanged to " + i2);
                                final ArrayMap arrayMap = new ArrayMap();
                                secQSTileInstanceManager.mTileInstances.keySet().stream().forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSTileInstanceManager$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj3) {
                                        SecQSTileInstanceManager secQSTileInstanceManager2 = secQSTileInstanceManager;
                                        ArrayMap arrayMap2 = arrayMap;
                                        int i3 = i2;
                                        TileSpec tileSpec = (TileSpec) obj3;
                                        if (secQSTileInstanceManager2.mTileInstances.get(tileSpec) instanceof CustomTile) {
                                            arrayMap2.put(tileSpec, new ArraySet((ArraySet) secQSTileInstanceManager2.mTileUsingHosts.get(tileSpec)));
                                        } else if (((QSHost) secQSTileInstanceManager2.mQSHost.get()).isBarTile(tileSpec.getSpec())) {
                                            ((QSTile) secQSTileInstanceManager2.mTileInstances.get(tileSpec)).userSwitch(i3);
                                        }
                                    }
                                });
                                for (int i3 = 0; i3 < arrayMap.size(); i3++) {
                                    final TileSpec tileSpec = (TileSpec) arrayMap.keyAt(i3);
                                    ((ArraySet) arrayMap.get(tileSpec)).stream().forEach(new Consumer() { // from class: com.android.systemui.qs.SecQSTileInstanceManager$$ExternalSyntheticLambda1
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj3) {
                                            SecQSTileInstanceManager secQSTileInstanceManager2 = secQSTileInstanceManager;
                                            TileSpec tileSpec2 = tileSpec;
                                            secQSTileInstanceManager2.releaseTileUsing(obj3, tileSpec2);
                                            secQSTileInstanceManager2.mQSPipelineLogger.logTileDestroyed(tileSpec2, QSPipelineLogger.TileDestroyedReason.RELEASE_CUSTOM_TILE_USER_CHANGED);
                                        }
                                    });
                                }
                            }
                        }
                        currentTilesInteractorImpl2.currentUser.updateState(null, new Integer(userInfo.id));
                        currentTilesInteractorImpl2._userContext.setValue(((UserTrackerImpl) currentTilesInteractorImpl2.userTracker).getUserContext());
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

    /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;
        final /* synthetic */ CurrentTilesInteractorImpl this$0;

        /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ CoroutineScope $$this$launch;
            int I$0;
            /* synthetic */ Object L$0;
            Object L$1;
            Object L$2;
            Object L$3;
            Object L$4;
            Object L$5;
            Object L$6;
            int label;
            final /* synthetic */ CurrentTilesInteractorImpl this$0;

            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1$1, reason: invalid class name and collision with other inner class name */
            final class C04111 extends SuspendLambda implements Function2 {
                final /* synthetic */ List<TileSpec> $fotalist;
                int label;
                final /* synthetic */ CurrentTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public C04111(CurrentTilesInteractorImpl currentTilesInteractorImpl, List<? extends TileSpec> list, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = currentTilesInteractorImpl;
                    this.$fotalist = list;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new C04111(this.this$0, this.$fotalist, continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((C04111) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
                        int iIntValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
                        List<TileSpec> list = this.$fotalist;
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

            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1$6, reason: invalid class name */
            final class AnonymousClass6 extends SuspendLambda implements Function2 {
                int label;
                final /* synthetic */ CurrentTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass6(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = currentTilesInteractorImpl;
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
                        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
                        int iIntValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
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

            /* renamed from: com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractorImpl$startTileCollection$1$2$1$8, reason: invalid class name */
            final class AnonymousClass8 extends SuspendLambda implements Function2 {
                final /* synthetic */ List<TileSpec> $resolvedSpecs;
                int label;
                final /* synthetic */ CurrentTilesInteractorImpl this$0;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                public AnonymousClass8(CurrentTilesInteractorImpl currentTilesInteractorImpl, List<? extends TileSpec> list, Continuation continuation) {
                    super(2, continuation);
                    this.this$0 = currentTilesInteractorImpl;
                    this.$resolvedSpecs = list;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass8(this.this$0, this.$resolvedSpecs, continuation);
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
                        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                        TileSpecRepository tileSpecRepository = currentTilesInteractorImpl.tileSpecRepository;
                        int iIntValue = ((Number) currentTilesInteractorImpl.currentUser.getValue()).intValue();
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
            public AnonymousClass1(CurrentTilesInteractorImpl currentTilesInteractorImpl, CoroutineScope coroutineScope, Continuation continuation) {
                super(2, continuation);
                this.this$0 = currentTilesInteractorImpl;
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

            /* JADX WARN: Code restructure failed: missing block: B:128:0x0479, code lost:
            
                if (r1 == r2) goto L137;
             */
            /* JADX WARN: Code restructure failed: missing block: B:136:0x04a3, code lost:
            
                if (r5 == r2) goto L137;
             */
            /* JADX WARN: Path cross not found for [B:101:0x03e2, B:100:0x03df], limit reached: 230 */
            /* JADX WARN: Removed duplicated region for block: B:127:0x0461  */
            /* JADX WARN: Removed duplicated region for block: B:132:0x0481  */
            /* JADX WARN: Removed duplicated region for block: B:140:0x04aa  */
            /* JADX WARN: Removed duplicated region for block: B:144:0x04bb  */
            /* JADX WARN: Removed duplicated region for block: B:43:0x01b2  */
            /* JADX WARN: Removed duplicated region for block: B:80:0x0346  */
            /* JADX WARN: Type inference failed for: r10v0, types: [T, java.util.List] */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:136:0x04a3 -> B:138:0x04a6). Please report as a decompilation issue!!! */
            /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:142:0x04b4 -> B:143:0x04b7). Please report as a decompilation issue!!! */
            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invokeSuspend(Object obj) throws Throwable {
                int i;
                int i2;
                Set set;
                CurrentTilesInteractorImpl currentTilesInteractorImpl;
                Ref$ObjectRef ref$ObjectRef;
                List list;
                Set set2;
                Map map;
                Iterator it;
                int i3;
                int i4;
                int i5;
                TileSpec tileSpec;
                int i6;
                Object objAccess$createTile;
                QSTile qSTile;
                QSTile qSTile2;
                QSTile qSTile3;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i7 = this.label;
                if (i7 == 0) {
                    ResultKt.throwOnFailure(obj);
                    DataWithUserChange dataWithUserChange = (DataWithUserChange) this.L$0;
                    int i8 = dataWithUserChange.userId;
                    Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
                    ref$ObjectRef2.element = dataWithUserChange.tiles;
                    Set set3 = dataWithUserChange.installedComponents;
                    List list2 = dataWithUserChange.knoxBlockTiles;
                    boolean z = CurrentTilesInteractorImpl.DEBUG;
                    boolean z2 = dataWithUserChange.userChange;
                    if (z) {
                        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i8, "userId = ", "CurrentTilesInteractor");
                        Log.i("CurrentTilesInteractor", "newTileList = " + ref$ObjectRef2.element);
                        Log.i("CurrentTilesInteractor", "components = " + set3);
                        Log.i("CurrentTilesInteractor", "knoxBlockedTileList = " + list2);
                        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("userChanged = ", "CurrentTilesInteractor", z2);
                    }
                    Boolean bool = (Boolean) ((LinkedHashMap) ((FotaUpdateInteractorImpl) this.this$0.fotaUpdateInteractor).fotaUpdateMap).get(QSType.QS);
                    if (bool != null ? bool.booleanValue() : false) {
                        CurrentTilesInteractorImpl currentTilesInteractorImpl2 = this.this$0;
                        List<TileSpec> list3 = dataWithUserChange.tiles;
                        List defaultTiles = currentTilesInteractorImpl2.getDefaultTiles();
                        i = 1;
                        if (z) {
                            i2 = i8;
                            set = set3;
                            Log.d("CurrentTilesInteractor", "recalculatedTileListForFota " + list3.size() + " " + ((ArrayList) defaultTiles).size());
                            StringBuilder sb = new StringBuilder("oldList : ");
                            sb.append(list3);
                            Log.d("CurrentTilesInteractor", sb.toString());
                            Log.d("CurrentTilesInteractor", "newList : " + defaultTiles);
                        } else {
                            i2 = i8;
                            set = set3;
                        }
                        if (!list3.isEmpty()) {
                            ArrayList arrayList = new ArrayList();
                            for (TileSpec tileSpec2 : list3) {
                                if (!currentTilesInteractorImpl2.isUnsupportedTile(tileSpec2) && !currentTilesInteractorImpl2.isBarTile(tileSpec2)) {
                                    arrayList.add(tileSpec2);
                                }
                            }
                            ArrayList arrayList2 = (ArrayList) defaultTiles;
                            int size = arrayList2.size();
                            int i9 = 0;
                            while (i9 < size) {
                                Object obj2 = arrayList2.get(i9);
                                int i10 = i9 + 1;
                                TileSpec tileSpec3 = (TileSpec) obj2;
                                if (arrayList.contains(tileSpec3)) {
                                    i4 = size;
                                    i5 = i10;
                                } else {
                                    int iIndexOf = arrayList2.indexOf(tileSpec3);
                                    if (arrayList.size() < iIndexOf) {
                                        arrayList.add(tileSpec3);
                                    } else {
                                        arrayList.add(iIndexOf, tileSpec3);
                                    }
                                    if (z) {
                                        int iIndexOf2 = arrayList.indexOf(tileSpec3);
                                        i4 = size;
                                        i5 = i10;
                                        StringBuilder sb2 = new StringBuilder(" add : ");
                                        sb2.append(tileSpec3);
                                        sb2.append(" ");
                                        sb2.append(iIndexOf);
                                        sb2.append(" to ");
                                        RecyclerView$$ExternalSyntheticOutline0.m(iIndexOf2, "CurrentTilesInteractor", sb2);
                                    }
                                }
                                size = i4;
                                i9 = i5;
                            }
                            Log.d("CurrentTilesInteractor", "resultList : " + arrayList.size() + "  " + arrayList);
                            defaultTiles = arrayList;
                        }
                        CurrentTilesInteractorImpl currentTilesInteractorImpl3 = this.this$0;
                        ArrayList arrayList3 = new ArrayList();
                        for (Object obj3 : defaultTiles) {
                            if (!((List) ((RemovedTilesInteractorImpl) currentTilesInteractorImpl3.removedTilesInteractor).removedTiles.$$delegate_0.getValue()).contains((TileSpec) obj3)) {
                                arrayList3.add(obj3);
                            }
                        }
                        ((FotaUpdateInteractorImpl) this.this$0.fotaUpdateInteractor).finishFotaUpdate(QSType.QS);
                        if (!Intrinsics.areEqual(ref$ObjectRef2.element, arrayList3)) {
                            Log.w("CurrentTilesInteractor", "Fota update to " + arrayList3);
                            CoroutineTracingKt.launchTraced$default(this.$$this$launch, null, null, new C04111(this.this$0, arrayList3, null), 7);
                            CurrentTilesInteractorImpl currentTilesInteractorImpl4 = this.this$0;
                            currentTilesInteractorImpl4.logger.logTilesFotaUpdatedAndRecalculated(dataWithUserChange.tiles, currentTilesInteractorImpl4.getDefaultTiles(), arrayList3, false, ((Number) this.this$0.userId.$$delegate_0.getValue()).intValue());
                            return Unit.INSTANCE;
                        }
                        Log.w("CurrentTilesInteractor", "Fota maintain as " + arrayList3);
                        CurrentTilesInteractorImpl currentTilesInteractorImpl5 = this.this$0;
                        currentTilesInteractorImpl5.logger.logTilesFotaUpdatedAndRecalculated(dataWithUserChange.tiles, currentTilesInteractorImpl5.getDefaultTiles(), arrayList3, true, ((Number) this.this$0.userId.$$delegate_0.getValue()).intValue());
                    } else {
                        i = 1;
                        i2 = i8;
                        set = set3;
                    }
                    Map map2 = this.this$0.specsToTiles;
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Map.Entry entry : ((LinkedHashMap) map2).entrySet()) {
                        if (!((List) ref$ObjectRef2.element).contains(entry.getKey()) && (entry.getValue() instanceof CurrentTilesInteractorImpl.TileOrNotInstalled.Tile)) {
                            linkedHashMap.put(entry.getKey(), entry.getValue());
                        }
                    }
                    CurrentTilesInteractorImpl currentTilesInteractorImpl6 = this.this$0;
                    for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                        currentTilesInteractorImpl6.logger.logTileDestroyed((TileSpec) entry2.getKey(), z2 ? QSPipelineLogger.TileDestroyedReason.TILE_NOT_PRESENT_IN_NEW_USER : QSPipelineLogger.TileDestroyedReason.TILE_REMOVED);
                        if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
                            currentTilesInteractorImpl6.tileInstanceManager.releaseTileUsing(currentTilesInteractorImpl6.tileUsingByPanel, (TileSpec) entry2.getKey());
                        } else {
                            ((CurrentTilesInteractorImpl.TileOrNotInstalled.Tile) entry2.getValue()).tile.destroy();
                        }
                    }
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    Iterable iterable = (Iterable) ref$ObjectRef2.element;
                    currentTilesInteractorImpl = this.this$0;
                    ref$ObjectRef = ref$ObjectRef2;
                    list = list2;
                    set2 = set;
                    map = linkedHashMap2;
                    it = iterable.iterator();
                    i3 = i2;
                    if (it.hasNext()) {
                    }
                } else if (i7 == 1) {
                    i3 = this.I$0;
                    tileSpec = (TileSpec) this.L$6;
                    it = (Iterator) this.L$5;
                    currentTilesInteractorImpl = (CurrentTilesInteractorImpl) this.L$4;
                    map = (Map) this.L$3;
                    list = (List) this.L$2;
                    set2 = (Set) this.L$1;
                    ref$ObjectRef = (Ref$ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    i6 = 1;
                    objAccess$createTile = obj;
                    qSTile = (QSTile) objAccess$createTile;
                    if (qSTile != null) {
                    }
                    Unit unit = Unit.INSTANCE;
                    i = i6;
                    if (it.hasNext()) {
                    }
                } else {
                    if (i7 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    i3 = this.I$0;
                    tileSpec = (TileSpec) this.L$6;
                    it = (Iterator) this.L$5;
                    currentTilesInteractorImpl = (CurrentTilesInteractorImpl) this.L$4;
                    map = (Map) this.L$3;
                    list = (List) this.L$2;
                    set2 = (Set) this.L$1;
                    ref$ObjectRef = (Ref$ObjectRef) this.L$0;
                    ResultKt.throwOnFailure(obj);
                    Object objAccess$createTile2 = obj;
                    i6 = 1;
                    qSTile = (QSTile) objAccess$createTile2;
                    if (qSTile != null) {
                        map.put(tileSpec, CurrentTilesInteractorImpl.TileOrNotInstalled.Tile.m2910boximpl(qSTile));
                    }
                    Unit unit2 = Unit.INSTANCE;
                    i = i6;
                    if (it.hasNext()) {
                        tileSpec = (TileSpec) it.next();
                        if (!map.containsKey(tileSpec)) {
                            if (tileSpec instanceof TileSpec.Empty) {
                                Boxing.boxInt(Log.d("CurrentTilesInteractor", tileSpec + " - except EmptyTile"));
                            } else if (!currentTilesInteractorImpl.tileFeatureChecker.isAvailableCustomTile(tileSpec)) {
                                Boxing.boxInt(Log.d("CurrentTilesInteractor", tileSpec + " - except by feature checker"));
                            } else if (currentTilesInteractorImpl.isBarTile(tileSpec)) {
                                Boxing.boxInt(Log.d("CurrentTilesInteractor", tileSpec + " - except BarTile"));
                            } else {
                                if (!(tileSpec instanceof TileSpec.CustomTileSpec) || set2.contains(((TileSpec.CustomTileSpec) tileSpec).componentName)) {
                                    if (currentTilesInteractorImpl.specsToTiles.containsKey(tileSpec)) {
                                        CurrentTilesInteractorImpl.TileOrNotInstalled tileOrNotInstalled = (CurrentTilesInteractorImpl.TileOrNotInstalled) MapsKt__MapsKt.getValue(tileSpec, currentTilesInteractorImpl.specsToTiles);
                                        if (!(tileOrNotInstalled instanceof CurrentTilesInteractorImpl.TileOrNotInstalled.NotInstalled)) {
                                            if (!(tileOrNotInstalled instanceof CurrentTilesInteractorImpl.TileOrNotInstalled.Tile)) {
                                                throw new NoWhenBranchMatchedException();
                                            }
                                            qSTile2 = ((CurrentTilesInteractorImpl.TileOrNotInstalled.Tile) tileOrNotInstalled).tile;
                                            boolean zIsDestroyed = qSTile2.isDestroyed();
                                            QSPipelineLogger qSPipelineLogger = currentTilesInteractorImpl.logger;
                                            if (zIsDestroyed) {
                                                qSPipelineLogger.getClass();
                                                LogLevel logLevel = LogLevel.DEBUG;
                                                QSPipelineLogger$$ExternalSyntheticLambda0 qSPipelineLogger$$ExternalSyntheticLambda0 = new QSPipelineLogger$$ExternalSyntheticLambda0(4);
                                                LogBuffer logBuffer = qSPipelineLogger.tileListLogBuffer;
                                                LogMessage logMessageObtain = logBuffer.obtain("QSTileListLog", logLevel, qSPipelineLogger$$ExternalSyntheticLambda0, null);
                                                ((LogMessageImpl) logMessageObtain).str1 = tileSpec.toString();
                                                logBuffer.commit(logMessageObtain);
                                            } else {
                                                boolean zIsAvailable = qSTile2.isAvailable();
                                                String str = currentTilesInteractorImpl.tileUsingByPanel;
                                                SecQSTileInstanceManager secQSTileInstanceManager = currentTilesInteractorImpl.tileInstanceManager;
                                                if (zIsAvailable) {
                                                    if (qSTile2 instanceof CustomTile) {
                                                        CustomTile customTile = (CustomTile) qSTile2;
                                                        qSTile3 = qSTile2;
                                                        if (customTile.mUser != i3) {
                                                            if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
                                                                secQSTileInstanceManager.releaseTileUsing(str, tileSpec);
                                                            } else {
                                                                customTile.destroy();
                                                            }
                                                            qSPipelineLogger.logTileDestroyed(tileSpec, QSPipelineLogger.TileDestroyedReason.CUSTOM_TILE_USER_CHANGED);
                                                        }
                                                    } else if (qSTile2.getCurrentTileUser() != i3) {
                                                        qSTile2.userSwitch(i3);
                                                        qSPipelineLogger.logTileUserChanged(tileSpec, i3);
                                                        if (qSTile2 == null) {
                                                            this.L$0 = ref$ObjectRef;
                                                            this.L$1 = set2;
                                                            this.L$2 = list;
                                                            this.L$3 = map;
                                                            this.L$4 = currentTilesInteractorImpl;
                                                            this.L$5 = it;
                                                            this.L$6 = tileSpec;
                                                            this.I$0 = i3;
                                                            i6 = i;
                                                            this.label = i6;
                                                            objAccess$createTile = CurrentTilesInteractorImpl.access$createTile(currentTilesInteractorImpl, tileSpec, this);
                                                        } else {
                                                            i6 = i;
                                                            qSTile = qSTile2;
                                                            if (qSTile != null) {
                                                            }
                                                            Unit unit22 = Unit.INSTANCE;
                                                            i = i6;
                                                            if (it.hasNext()) {
                                                                int i11 = i;
                                                                List list4 = CollectionsKt___CollectionsKt.toList(map.keySet());
                                                                ((LinkedHashMap) this.this$0.specsToTiles).clear();
                                                                this.this$0.specsToTiles.putAll(map);
                                                                LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                                                                for (Map.Entry entry3 : map.entrySet()) {
                                                                    if ((entry3.getValue() instanceof CurrentTilesInteractorImpl.TileOrNotInstalled.Tile) && !list.contains(entry3.getKey())) {
                                                                        linkedHashMap3.put(entry3.getKey(), entry3.getValue());
                                                                    }
                                                                }
                                                                ArrayList arrayList4 = new ArrayList(linkedHashMap3.size());
                                                                for (Map.Entry entry4 : linkedHashMap3.entrySet()) {
                                                                    arrayList4.add(new TileModel((TileSpec) entry4.getKey(), ((CurrentTilesInteractorImpl.TileOrNotInstalled.Tile) entry4.getValue()).tile));
                                                                }
                                                                Log.i("CurrentTilesInteractor", "resolvedSpecs = [" + list4.size() + "]  " + list4);
                                                                Log.i("CurrentTilesInteractor", "newResolvedTiles = [" + arrayList4.size() + "]  " + arrayList4);
                                                                this.this$0._currentSpecsAndTiles.updateState(null, arrayList4);
                                                                QSPipelineLogger qSPipelineLogger2 = this.this$0.logger;
                                                                LinkedHashMap linkedHashMap4 = new LinkedHashMap();
                                                                for (Map.Entry entry5 : map.entrySet()) {
                                                                    if (entry5.getValue() instanceof CurrentTilesInteractorImpl.TileOrNotInstalled.NotInstalled) {
                                                                        linkedHashMap4.put(entry5.getKey(), entry5.getValue());
                                                                    }
                                                                }
                                                                qSPipelineLogger2.logTilesNotInstalled(i3, linkedHashMap4.keySet());
                                                                int size2 = arrayList4.size();
                                                                CurrentTilesInteractorImpl currentTilesInteractorImpl7 = this.this$0;
                                                                if (size2 < (currentTilesInteractorImpl7.retailModeRepository.getInRetailMode() ? i11 : ((MinimumTilesResourceRepository) currentTilesInteractorImpl7.minimumTilesRepository).minNumberOfTiles)) {
                                                                    CoroutineTracingKt.launchTraced$default(this.$$this$launch, null, null, new AnonymousClass6(this.this$0, null), 7);
                                                                } else {
                                                                    Iterable iterable2 = (Iterable) ref$ObjectRef.element;
                                                                    ArrayList arrayList5 = new ArrayList();
                                                                    for (Object obj4 : iterable2) {
                                                                        if (!(((TileSpec) obj4) instanceof TileSpec.Empty)) {
                                                                            arrayList5.add(obj4);
                                                                        }
                                                                    }
                                                                    if (list4.equals(arrayList5)) {
                                                                        Log.d("CurrentTilesInteractor", "Don't need any operation");
                                                                        if (arrayList4.isEmpty() && !set2.isEmpty()) {
                                                                            this.this$0.forceUIUpdate.tryEmit(Boolean.TRUE);
                                                                        }
                                                                        Unit unit3 = Unit.INSTANCE;
                                                                    } else {
                                                                        CoroutineTracingKt.launchTraced$default(this.$$this$launch, null, null, new AnonymousClass8(this.this$0, list4, null), 7);
                                                                    }
                                                                }
                                                                return Unit.INSTANCE;
                                                            }
                                                        }
                                                    } else {
                                                        qSTile3 = qSTile2;
                                                    }
                                                    qSTile2 = qSTile3;
                                                    if (qSTile2 == null) {
                                                    }
                                                } else {
                                                    qSPipelineLogger.logTileDestroyed(tileSpec, QSPipelineLogger.TileDestroyedReason.EXISTING_TILE_NOT_AVAILABLE);
                                                    if (ScRune.QUICK_MANAGE_MULTI_QSHOST) {
                                                        secQSTileInstanceManager.releaseTileUsing(str, tileSpec);
                                                    } else {
                                                        qSTile2.destroy();
                                                    }
                                                }
                                            }
                                        }
                                        qSTile2 = null;
                                        if (qSTile2 == null) {
                                        }
                                    } else {
                                        i6 = i;
                                        this.L$0 = ref$ObjectRef;
                                        this.L$1 = set2;
                                        this.L$2 = list;
                                        this.L$3 = map;
                                        this.L$4 = currentTilesInteractorImpl;
                                        this.L$5 = it;
                                        this.L$6 = tileSpec;
                                        this.I$0 = i3;
                                        this.label = 2;
                                        objAccess$createTile2 = CurrentTilesInteractorImpl.access$createTile(currentTilesInteractorImpl, tileSpec, this);
                                    }
                                    return coroutineSingletons;
                                }
                                map.put(tileSpec, CurrentTilesInteractorImpl.TileOrNotInstalled.NotInstalled.INSTANCE);
                                Unit unit4 = Unit.INSTANCE;
                            }
                        }
                        i6 = i;
                        i = i6;
                        if (it.hasNext()) {
                        }
                    }
                }
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = currentTilesInteractorImpl;
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
                CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
                FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = currentTilesInteractorImpl.refreshUserAndTiles;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(currentTilesInteractorImpl, coroutineScope, null);
                this.label = 1;
                if (FlowKt.collectLatest(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, anonymousClass1, this) == coroutineSingletons) {
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
    public CurrentTilesInteractorImpl$startTileCollection$1(CurrentTilesInteractorImpl currentTilesInteractorImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = currentTilesInteractorImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        CurrentTilesInteractorImpl$startTileCollection$1 currentTilesInteractorImpl$startTileCollection$1 = new CurrentTilesInteractorImpl$startTileCollection$1(this.this$0, continuation);
        currentTilesInteractorImpl$startTileCollection$1.L$0 = obj;
        return currentTilesInteractorImpl$startTileCollection$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CurrentTilesInteractorImpl$startTileCollection$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.this$0, null), 7);
        CurrentTilesInteractorImpl currentTilesInteractorImpl = this.this$0;
        CoroutineTracingKt.launchTraced$default(coroutineScope, currentTilesInteractorImpl.backgroundDispatcher, null, new AnonymousClass2(currentTilesInteractorImpl, null), 5);
        return Unit.INSTANCE;
    }
}
