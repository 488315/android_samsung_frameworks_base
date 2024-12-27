package com.android.systemui.plugins.qs;

import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@Dependencies({@DependsOn(target = QSTile.class), @DependsOn(target = QSTileView.class)})
@ProvidesInterface(action = QSFactory.ACTION, version = 3)
public interface QSFactory extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_QS_FACTORY";
    public static final int VERSION = 3;

    QSTile createTile(String str);

    default boolean isSystemTile(String str) {
        return false;
    }
}
