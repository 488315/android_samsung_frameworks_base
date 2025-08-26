package com.android.systemui.qs.pipeline.simulation.interactor;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.systemui.ScRune;
import com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractor;
import com.android.systemui.qs.pipeline.data.domain.interactor.TilesBackUpRestoreInteractorImpl;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepository;
import com.android.systemui.qs.pipeline.simulation.data.repository.TestTileDataRepositoryImpl;
import com.android.systemui.qs.pipeline.simulation.data.source.TileDataSource;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class SimulationInteractor {
    public final TilesBackUpRestoreInteractor backUpRestoreInteractor;
    public final CoroutineScope scope;
    public final TestTileDataRepository testTileDataRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.qs.pipeline.simulation.interactor.SimulationInteractor$init$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.qs.pipeline.simulation.interactor.SimulationInteractor$init$1$1, reason: invalid class name and collision with other inner class name */
        final class C04151 extends SuspendLambda implements Function2 {
            int label;
            final /* synthetic */ SimulationInteractor this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C04151(SimulationInteractor simulationInteractor, Continuation continuation) {
                super(2, continuation);
                this.this$0 = simulationInteractor;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C04151(this.this$0, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C04151) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) throws NumberFormatException {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                SimulationInteractor simulationInteractor = this.this$0;
                ((TilesBackUpRestoreInteractorImpl) simulationInteractor.backUpRestoreInteractor).setRestoreData(simulationInteractor.makeRestoreData("sep_version"));
                SimulationInteractor simulationInteractor2 = this.this$0;
                ((TilesBackUpRestoreInteractorImpl) simulationInteractor2.backUpRestoreInteractor).setRestoreData(simulationInteractor2.makeRestoreData("has_edited"));
                SimulationInteractor simulationInteractor3 = this.this$0;
                ((TilesBackUpRestoreInteractorImpl) simulationInteractor3.backUpRestoreInteractor).setRestoreData(simulationInteractor3.makeRestoreData("removed_tile_list"));
                SimulationInteractor simulationInteractor4 = this.this$0;
                ((TilesBackUpRestoreInteractorImpl) simulationInteractor4.backUpRestoreInteractor).setRestoreData(simulationInteractor4.makeRestoreData("tile_list"));
                SimulationInteractor simulationInteractor5 = this.this$0;
                ((TilesBackUpRestoreInteractorImpl) simulationInteractor5.backUpRestoreInteractor).setRestoreData(simulationInteractor5.makeRestoreData("qqs_has_edited"));
                SimulationInteractor simulationInteractor6 = this.this$0;
                ((TilesBackUpRestoreInteractorImpl) simulationInteractor6.backUpRestoreInteractor).setRestoreData(simulationInteractor6.makeRestoreData("qqs_tile_list"));
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SimulationInteractor.this.new AnonymousClass1(continuation);
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
                SimulationInteractor simulationInteractor = SimulationInteractor.this;
                SharedFlowImpl sharedFlowImpl = ((TestTileDataRepositoryImpl) simulationInteractor.testTileDataRepository).doRestore;
                C04151 c04151 = new C04151(simulationInteractor, null);
                this.label = 1;
                if (FlowKt.collectLatest(sharedFlowImpl, c04151, this) == coroutineSingletons) {
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
    }

    public SimulationInteractor(TestTileDataRepository testTileDataRepository, TilesBackUpRestoreInteractor tilesBackUpRestoreInteractor, CoroutineScope coroutineScope) {
        this.testTileDataRepository = testTileDataRepository;
        this.backUpRestoreInteractor = tilesBackUpRestoreInteractor;
        this.scope = coroutineScope;
    }

    public final void init() {
        if (ScRune.QUICK_MANAGE_TILE_LIST_TEST) {
            BuildersKt.launch$default(this.scope, null, null, new AnonymousClass1(null), 3);
        }
    }

    public final String makeRestoreData(String str) {
        int iHashCode = str.hashCode();
        TestTileDataRepository testTileDataRepository = this.testTileDataRepository;
        switch (iHashCode) {
            case -1289769360:
                if (!str.equals("removed_tile_list")) {
                    return "";
                }
                StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                sbM.append((tileDataSource != null ? tileDataSource : null).getRemovedTiles());
                return sbM.toString();
            case -1020575241:
                if (!str.equals("sep_version")) {
                    return "";
                }
                StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource2 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                sbM2.append((tileDataSource2 != null ? tileDataSource2 : null).getSepVersion());
                return sbM2.toString();
            case -851078257:
                if (!str.equals("tile_list")) {
                    return "";
                }
                StringBuilder sbM3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource3 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                sbM3.append((tileDataSource3 != null ? tileDataSource3 : null).getTiles());
                return sbM3.toString();
            case 75675811:
                if (!str.equals("qqs_tile_list")) {
                    return "";
                }
                StringBuilder sbM4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource4 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                sbM4.append((tileDataSource4 != null ? tileDataSource4 : null).getQqsTiles());
                return sbM4.toString();
            case 432981722:
                if (!str.equals("qqs_has_edited")) {
                    return "";
                }
                StringBuilder sbM5 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource5 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                sbM5.append((tileDataSource5 != null ? tileDataSource5 : null).getQqsEdited());
                return sbM5.toString();
            case 1768376686:
                if (!str.equals("has_edited")) {
                    return "";
                }
                StringBuilder sbM6 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(str, "::");
                TileDataSource tileDataSource6 = ((TestTileDataRepositoryImpl) testTileDataRepository).tileDataSource;
                sbM6.append((tileDataSource6 != null ? tileDataSource6 : null).getQsEdited());
                return sbM6.toString();
            default:
                return "";
        }
    }
}
