package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.util.SparseArray;
import com.android.systemui.R;
import com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.pipeline.shared.logging.QSPipelineLogger;
import com.android.systemui.retail.data.repository.RetailModeRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class TileSpecSettingsRepository implements TileSpecRepository {
    public final QSPipelineLogger logger;
    public final Resources resources;
    public final RetailModeRepository retailModeRepository;
    public final Lazy retailModeTiles$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$retailModeTiles$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            List<String> split$default = StringsKt__StringsKt.split$default(TileSpecSettingsRepository.this.resources.getString(R.string.quick_settings_tiles_retail_mode), new String[]{","}, 0, 6);
            TileSpec.Companion companion = TileSpec.Companion;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(split$default, 10));
            for (String str : split$default) {
                companion.getClass();
                arrayList.add(TileSpec.Companion.create(str));
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (!(((TileSpec) next) instanceof TileSpec.Invalid)) {
                    arrayList2.add(next);
                }
            }
            return arrayList2;
        }
    });
    public final SparseArray userTileRepositories = new SparseArray();
    public final UserTileSpecRepository.Factory userTileSpecRepositoryFactory;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public TileSpecSettingsRepository(Resources resources, QSPipelineLogger qSPipelineLogger, RetailModeRepository retailModeRepository, UserTileSpecRepository.Factory factory) {
        this.resources = resources;
        this.logger = qSPipelineLogger;
        this.retailModeRepository = retailModeRepository;
        this.userTileSpecRepositoryFactory = factory;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object tilesSpecs(int r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$1
            if (r0 == 0) goto L13
            r0 = r6
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
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L33
            if (r2 != r3) goto L2b
            java.lang.Object r4 = r0.L$0
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository r4 = (com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L5c
        L2b:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L33:
            kotlin.ResultKt.throwOnFailure(r6)
            android.util.SparseArray r6 = r4.userTileRepositories
            boolean r6 = r6.contains(r5)
            if (r6 != 0) goto L49
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository$Factory r6 = r4.userTileSpecRepositoryFactory
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r6 = r6.create(r5)
            android.util.SparseArray r2 = r4.userTileRepositories
            r2.put(r5, r6)
        L49:
            android.util.SparseArray r6 = r4.userTileRepositories
            java.lang.Object r5 = r6.get(r5)
            com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository r5 = (com.android.systemui.qs.pipeline.data.repository.UserTileSpecRepository) r5
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r6 = r5.tiles(r0)
            if (r6 != r1) goto L5c
            return r1
        L5c:
            kotlinx.coroutines.flow.Flow r6 = (kotlinx.coroutines.flow.Flow) r6
            com.android.systemui.retail.data.repository.RetailModeRepository r5 = r4.retailModeRepository
            com.android.systemui.retail.data.repository.RetailModeSettingsRepository r5 = (com.android.systemui.retail.data.repository.RetailModeSettingsRepository) r5
            kotlinx.coroutines.flow.ReadonlyStateFlow r5 = r5.retailMode
            com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1 r0 = new com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository$tilesSpecs$$inlined$flatMapLatest$1
            r1 = 0
            r0.<init>(r1, r4, r6)
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r4 = kotlinx.coroutines.flow.FlowKt.transformLatest(r5, r0)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.TileSpecSettingsRepository.tilesSpecs(int, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
