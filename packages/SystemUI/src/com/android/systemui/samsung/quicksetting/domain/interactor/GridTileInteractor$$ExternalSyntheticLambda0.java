package com.android.systemui.samsung.quicksetting.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.pipeline.domain.interactor.CurrentTilesInteractor;
import com.android.systemui.samsung.quicksetting.domain.model.GridTileData;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickButton;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickTile;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileFolder;
import com.android.systemui.samsung.quicksetting.domain.model.items.QuickTileKt$getDummyTile$1;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes2.dex */
public final /* synthetic */ class GridTileInteractor$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GridTileInteractor f$0;
    public final /* synthetic */ GridTileData f$1;

    public /* synthetic */ GridTileInteractor$$ExternalSyntheticLambda0(GridTileInteractor gridTileInteractor, GridTileData gridTileData) {
        this.$r8$classId = 0;
        this.f$0 = gridTileInteractor;
        this.f$1 = gridTileData;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                CurrentTilesInteractor currentTilesInteractor = this.f$0.currentTilesInteractor;
                GridTileData gridTileData = this.f$1;
                QSTile tileBySpecString = currentTilesInteractor.getTileBySpecString(gridTileData.spec);
                String str = gridTileData.spec;
                return tileBySpecString != null ? new QuickTile(0L, null, str, GridTileInteractor.getQuickTileResourceId(str), tileBySpecString, 3, null) : new QuickTile(0L, null, str, GridTileInteractor.getQuickTileResourceId(str), null, 19, null);
            case 1:
                GridTileData gridTileData2 = this.f$1;
                List<String> listSplit$default = StringsKt__StringsKt.split$default(gridTileData2.spec, new String[]{","}, 0, 6);
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSplit$default, 10));
                for (String str2 : listSplit$default) {
                    GridTileInteractor gridTileInteractor = this.f$0;
                    gridTileInteractor.getClass();
                    int quickTileResourceId = GridTileInteractor.getQuickTileResourceId(gridTileData2.spec);
                    QSTile tileBySpecString2 = gridTileInteractor.currentTilesInteractor.getTileBySpecString(str2);
                    if (tileBySpecString2 == null) {
                        tileBySpecString2 = new QuickTileKt$getDummyTile$1();
                    }
                    arrayList.add(new QuickTile(0L, null, str2, quickTileResourceId, tileBySpecString2, 3, null));
                }
                return new QuickTileFolder(0L, null, arrayList, 3, null);
            default:
                GridTileData gridTileData3 = this.f$1;
                String str3 = gridTileData3.spec;
                this.f$0.getClass();
                boolean zAreEqual = Intrinsics.areEqual(str3, "NearByDevices");
                int i = R.drawable.quick_panel_icon_device_control;
                if (!zAreEqual && Intrinsics.areEqual(str3, "SmartThings")) {
                    i = R.drawable.ic_smartthings;
                }
                return new QuickButton(0L, null, str3, i, gridTileData3.spec, new GridTileInteractor$$ExternalSyntheticLambda5(), 3, null);
        }
    }

    public /* synthetic */ GridTileInteractor$$ExternalSyntheticLambda0(GridTileData gridTileData, GridTileInteractor gridTileInteractor, int i) {
        this.$r8$classId = i;
        this.f$1 = gridTileData;
        this.f$0 = gridTileInteractor;
    }
}
