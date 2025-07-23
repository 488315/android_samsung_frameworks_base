package com.android.systemui.qs.customize;

import android.view.View;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.android.systemui.qs.customize.SecTileQueryHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class CustomTileInfo extends SecTileQueryHelper.TileInfo {
    public SecCustomizeTileView customTileView;
    public String customizeTileContentDes;
    public View.OnLongClickListener longClickListener;

    public final String toString() {
        StringBuilder sb = new StringBuilder("CustomTileInfo{longClickListener=");
        sb.append(this.longClickListener);
        sb.append(", customTileView=");
        sb.append(this.customTileView);
        sb.append(", customizeTileContentDes='");
        return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.customizeTileContentDes, ", isNewCustomTile=false}");
    }
}
