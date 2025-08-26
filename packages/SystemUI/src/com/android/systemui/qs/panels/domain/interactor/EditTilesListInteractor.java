package com.android.systemui.qs.panels.domain.interactor;

import android.R;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.qs.panels.data.repository.IconAndNameCustomRepository;
import com.android.systemui.qs.panels.data.repository.StockTilesRepository;
import com.android.systemui.qs.panels.domain.model.EditTilesModel;
import com.android.systemui.qs.panels.shared.model.EditTileData;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.qs.shared.model.TileCategory;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfig;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProvider;
import com.android.systemui.qs.tiles.base.shared.model.QSTileConfigProviderImpl;
import com.android.systemui.qs.tiles.base.shared.model.QSTileUIConfig;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;

/* loaded from: classes2.dex */
public final class EditTilesListInteractor {
    public final IconAndNameCustomRepository iconAndNameCustomRepository;
    public final QSTileConfigProvider qsTileConfigProvider;
    public final StockTilesRepository stockTilesRepository;

    /* renamed from: com.android.systemui.qs.panels.domain.interactor.EditTilesListInteractor$getTilesToEdit$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            return EditTilesListInteractor.this.getTilesToEdit(this);
        }
    }

    public EditTilesListInteractor(StockTilesRepository stockTilesRepository, QSTileConfigProvider qSTileConfigProvider, IconAndNameCustomRepository iconAndNameCustomRepository) {
        this.stockTilesRepository = stockTilesRepository;
        this.qsTileConfigProvider = qSTileConfigProvider;
        this.iconAndNameCustomRepository = iconAndNameCustomRepository;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object getTilesToEdit(ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        List list;
        boolean zContainsKey;
        EditTileData editTileData;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object customTileData = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(customTileData);
            List list2 = this.stockTilesRepository.stockTiles;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            ArrayList arrayList2 = (ArrayList) list2;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                TileSpec tileSpec = (TileSpec) obj;
                String spec = tileSpec.getSpec();
                QSTileConfigProviderImpl qSTileConfigProviderImpl = (QSTileConfigProviderImpl) this.qsTileConfigProvider;
                qSTileConfigProviderImpl.getClass();
                TileSpec.Companion.getClass();
                TileSpec tileSpecCreate = TileSpec.Companion.create(spec);
                if (tileSpecCreate instanceof TileSpec.PlatformTileSpec) {
                    zContainsKey = qSTileConfigProviderImpl.configs.containsKey(spec);
                } else if (tileSpecCreate instanceof TileSpec.CustomTileSpec) {
                    zContainsKey = true;
                } else {
                    boolean z = tileSpecCreate instanceof TileSpec.Invalid;
                    zContainsKey = false;
                }
                if (zContainsKey) {
                    QSTileConfig config = qSTileConfigProviderImpl.getConfig(tileSpec.getSpec());
                    QSTileUIConfig qSTileUIConfig = config.uiConfig;
                    editTileData = new EditTileData(tileSpec, new Icon.Resource(qSTileUIConfig.getIconRes(), new ContentDescription.Resource(qSTileUIConfig.getLabelRes())), new Text.Resource(qSTileUIConfig.getLabelRes()), null, config.category);
                } else {
                    editTileData = new EditTileData(tileSpec, new Icon.Resource(R.drawable.star_on, new ContentDescription.Loaded(tileSpec.getSpec())), new Text.Loaded(tileSpec.getSpec()), null, TileCategory.UNKNOWN);
                }
                arrayList.add(editTileData);
            }
            anonymousClass1.L$0 = arrayList;
            anonymousClass1.label = 1;
            customTileData = this.iconAndNameCustomRepository.getCustomTileData(anonymousClass1);
            if (customTileData == coroutineSingletons) {
                return coroutineSingletons;
            }
            list = arrayList;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) anonymousClass1.L$0;
            ResultKt.throwOnFailure(customTileData);
        }
        return new EditTilesModel(list, (List) customTileData);
    }
}
