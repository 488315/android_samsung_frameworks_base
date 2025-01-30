package com.android.systemui.p016qs;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.p016qs.SecQSPanel;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QSButtonGridController {
    public static final float BUTTON_WIDTH_MAX_RATIO;
    public static final float BUTTON_WIDTH_MIN_RATIO;
    public final Lazy mQSPanelControllerLazy;
    public final Uri[] mSettingsValueList = {Settings.Global.getUriFor("quickstar_qs_tile_layout_custom_matrix"), Settings.Global.getUriFor("quickstar_qs_tile_layout_custom_matrix_width")};
    public final C20361 mSettingListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.QSButtonGridController.1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            SecQSPanel.QSTileLayout qSTileLayout = ((SecQSPanelController) QSButtonGridController.this.mQSPanelControllerLazy.get()).mTileLayout;
            if (qSTileLayout == null) {
                return;
            }
            Log.d("QSButtonGridController", "settingsHelper onChanged() Settings:" + QSButtonGridController.isQSButtonGridPopupEnabled());
            if (!QSButtonGridController.isQSButtonGridPopupEnabled()) {
                ((PagedTileLayout) qSTileLayout).updateTileWidth(1.0f);
                return;
            }
            PagedTileLayout pagedTileLayout = (PagedTileLayout) qSTileLayout;
            ((TileLayout) ((ArrayList) pagedTileLayout.mSecPagedTileLayout.pagesSupplier.get()).get(0)).mLastCellWidth = 0;
            int intValue = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("quickstar_qs_tile_layout_custom_matrix_width").getIntValue();
            float f = QSButtonGridController.BUTTON_WIDTH_MAX_RATIO;
            float f2 = QSButtonGridController.BUTTON_WIDTH_MIN_RATIO;
            float f3 = (f - f2) / 10.0f;
            float min = Math.min(Math.max(Math.round((f - (intValue * f3)) * 1000.0f) / 1000.0f, f2), f);
            Log.d("QSButtonGridController", "QUICKSTAR_QS_TILE_LAYOUT_CUSTOM_MATRIX result[P:" + intValue + ", R:" + min + "] dP:" + QSButtonGridController.getDefaultProgress() + ", iR:" + f3 + ", cDR:" + (f - (QSButtonGridController.getDefaultProgress() * f3)));
            pagedTileLayout.updateTileWidth(min);
        }
    };

    static {
        boolean z = QpRune.QUICK_TABLET;
        BUTTON_WIDTH_MAX_RATIO = z ? 1.41f : 1.52f;
        BUTTON_WIDTH_MIN_RATIO = z ? 0.62f : 0.76f;
    }

    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.systemui.qs.QSButtonGridController$1] */
    public QSButtonGridController(Context context, Lazy lazy) {
        this.mQSPanelControllerLazy = lazy;
    }

    public static int getDefaultProgress() {
        float f = BUTTON_WIDTH_MAX_RATIO;
        return Math.min(Math.max(Math.round((f - 1.0f) / ((f - BUTTON_WIDTH_MIN_RATIO) / 10.0f)), 0), 10);
    }

    public static boolean isQSButtonGridPopupEnabled() {
        return ((SettingsHelper) Dependency.get(SettingsHelper.class)).isQSButtonGridPopupEnabled();
    }
}
