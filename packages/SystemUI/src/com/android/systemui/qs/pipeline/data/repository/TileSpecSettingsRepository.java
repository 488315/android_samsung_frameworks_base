package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.util.SparseArray;
import com.android.systemui.R;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferedChannel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TileSpecSettingsRepository implements TileSpecRepository {
    public final CoroutineScope applicationScope;
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final RetailModeRepository retailModeRepository;
    public final UserTileSpecRepository.Factory userTileSpecRepositoryFactory;
    public final Lazy retailModeTiles$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            int i = 0;
            List<String> split$default = StringsKt__StringsKt.split$default(TileSpecSettingsRepository.this.resources.getString(R.string.quick_settings_tiles_retail_mode), new String[]{","}, 0, 6);
            TileSpec.Companion companion = TileSpec.Companion;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
            for (String str : split$default) {
                companion.getClass();
                arrayList.add(TileSpec.Companion.create(str));
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (!(((TileSpec) obj) instanceof TileSpec.Invalid)) {
                    arrayList2.add(obj);
                }
            }
            return arrayList2;
        }
    });
    public final BufferedChannel _tilesUpgradePath = ChannelKt.Channel$default(5, null, null, 6);
    public final SparseArray userTileRepositories = new SparseArray();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TileSpecSettingsRepository(Resources resources, QSPipelineLogger qSPipelineLogger, RetailModeRepository retailModeRepository, UserTileSpecRepository.Factory factory, CoroutineScope coroutineScope) {
        this.resources = resources;
        this.logger = qSPipelineLogger;
        this.retailModeRepository = retailModeRepository;
        this.userTileSpecRepositoryFactory = factory;
        this.applicationScope = coroutineScope;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object addTile(int i, TileSpec tileSpec, int i2, SuspendLambda suspendLambda) {
        Object emit;
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        boolean z = tileSpec instanceof TileSpec.Invalid;
        if (z) {
            return Unit.INSTANCE;
        }
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        if (z) {
            emit = Unit.INSTANCE;
        } else {
            emit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.AddTile(tileSpec, i2), suspendLambda);
            if (emit != CoroutineSingletons.COROUTINE_SUSPENDED) {
                emit = Unit.INSTANCE;
            }
        }
        return emit == CoroutineSingletons.COROUTINE_SUSPENDED ? emit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object prependDefault(int i, SuspendLambda suspendLambda) {
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object emit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.PrependDefault(userTileSpecRepository.defaultTilesRepository.getDefaultTiles()), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (emit != coroutineSingletons) {
            emit = Unit.INSTANCE;
        }
        return emit == coroutineSingletons ? emit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Unit reconcileRestore() {
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object removeTiles(int i, Collection collection, SuspendLambda suspendLambda) {
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object emit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.RemoveTiles(collection), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (emit != coroutineSingletons) {
            emit = Unit.INSTANCE;
        }
        return emit == coroutineSingletons ? emit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object resetToDefault(int i, SuspendLambda suspendLambda) {
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object emit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.ResetToDefault(userTileSpecRepository.defaultTilesRepository.getDefaultTiles()), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (emit != coroutineSingletons) {
            emit = Unit.INSTANCE;
        }
        return emit == coroutineSingletons ? emit : Unit.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    public final Object setTiles(int i, List list, SuspendLambda suspendLambda) {
        if (this.retailModeRepository.getInRetailMode()) {
            return Unit.INSTANCE;
        }
        UserTileSpecRepository userTileSpecRepository = (UserTileSpecRepository) this.userTileRepositories.get(i);
        if (userTileSpecRepository == null) {
            return Unit.INSTANCE;
        }
        Object emit = userTileSpecRepository.changeEvents.emit(new UserTileSpecRepository.ChangeTiles(list), suspendLambda);
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (emit != coroutineSingletons) {
            emit = Unit.INSTANCE;
        }
        return emit == coroutineSingletons ? emit : Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    @Override // com.android.systemui.qs.pipeline.data.repository.TileSpecRepository
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object tilesSpecs(int r7, kotlin.coroutines.jvm.internal.ContinuationImpl r8) {
        /*
            r6 = this;
            boolean r0 = r8 instanceof com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$1
            if (r0 == 0) goto L13
            r0 = r8
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$1 r0 = (com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$1 r0 = new com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$1
            r0.<init>(r6, r8)
        L18:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L34
            if (r2 != r4) goto L2c
            java.lang.Object r6 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository r6 = (com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository) r6
            kotlin.ResultKt.throwOnFailure(r8)
            goto L68
        L2c:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L34:
            kotlin.ResultKt.throwOnFailure(r8)
            android.util.SparseArray r8 = r6.userTileRepositories
            boolean r8 = r8.contains(r7)
            if (r8 != 0) goto L55
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Factory r8 = r6.userTileSpecRepositoryFactory
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r8 = r8.create(r7)
            android.util.SparseArray r2 = r6.userTileRepositories
            r2.put(r7, r8)
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$2 r2 = new com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$2
            r2.<init>(r8, r6, r7, r3)
            r8 = 6
            kotlinx.coroutines.CoroutineScope r5 = r6.applicationScope
            com.android.app.tracing.coroutines.CoroutineTracingKt.launchTraced$default(r5, r3, r3, r2, r8)
        L55:
            android.util.SparseArray r8 = r6.userTileRepositories
            java.lang.Object r7 = r8.get(r7)
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r7 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r7
            r0.L$0 = r6
            r0.label = r4
            java.lang.Object r8 = r7.tiles(r0)
            if (r8 != r1) goto L68
            return r1
        L68:
            kotlinx.coroutines.flow.Flow r8 = (kotlinx.coroutines.flow.Flow) r8
            com.android.systemui.retail.data.repository.RetailModeRepository r7 = r6.retailModeRepository
            com.android.systemui.retail.data.repository.impl.RetailModeSettingsRepository r7 = (com.android.systemui.retail.data.repository.impl.RetailModeSettingsRepository) r7
            kotlinx.coroutines.flow.ReadonlyStateFlow r7 = r7.retailMode
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 r0 = new com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1
            r0.<init>(r3, r6, r8)
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r6 = kotlinx.coroutines.flow.FlowKt.transformLatest(r7, r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository.tilesSpecs(int, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
