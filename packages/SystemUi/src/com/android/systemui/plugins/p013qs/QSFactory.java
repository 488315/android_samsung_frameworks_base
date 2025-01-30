package com.android.systemui.plugins.p013qs;

import android.content.Context;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@Dependencies({@DependsOn(target = QSTile.class), @DependsOn(target = QSTileView.class)})
@ProvidesInterface(action = QSFactory.ACTION, version = 2)
/* loaded from: classes2.dex */
public interface QSFactory extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_QS_FACTORY";
    public static final int VERSION = 2;

    QSTileView createCoverScreenTileView(Context context, QSTile qSTile, boolean z);

    QSTile createTile(String str);

    QSTileView createTileView(Context context, QSTile qSTile, boolean z);

    default boolean isSystemTile(String str) {
        return false;
    }
}
