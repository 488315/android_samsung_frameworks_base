package com.android.systemui.qs.customize;

import android.view.View;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.qs.customize.SecTileQueryHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class CustomTileInfo extends SecTileQueryHelper.TileInfo {
    public SecCustomizeTileView customTileView;
    public String customizeTileContentDes;
    public boolean isNewCustomTile;
    public View.OnLongClickListener longClickListener;

    public final String toString() {
        StringBuilder sb = new StringBuilder("CustomTileInfo{longClickListener=");
        sb.append(this.longClickListener);
        sb.append(", customTileView=");
        sb.append(this.customTileView);
        sb.append(", customizeTileContentDes='");
        sb.append(this.customizeTileContentDes);
        sb.append(", isNewCustomTile=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.isNewCustomTile, "}");
    }
}
