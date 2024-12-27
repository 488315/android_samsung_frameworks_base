package com.android.systemui.plugins.qs;

import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@Dependencies({@DependsOn(target = QSIconView.class), @DependsOn(target = QSTile.Callback.class), @DependsOn(target = QSTile.Icon.class), @DependsOn(target = QSTile.State.class)})
@ProvidesInterface(version = 4)
/* loaded from: classes2.dex */
public interface SQSTile extends QSTile {
    public static final int VERSION = 4;

    @Override // com.android.systemui.plugins.qs.QSTile
    default DetailAdapter getDetailAdapter() {
        return null;
    }

    String getTileMapKey();

    void sendTileStatusLog();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    @ProvidesInterface(version = 2)
    public interface SCallback extends QSTile.Callback {
        default void onUpdateDetail() {
        }

        default void onScanStateChanged(boolean z) {
        }

        default void onShowDetail(boolean z) {
        }

        default void onToggleStateChanged(boolean z) {
        }

        default void onScrollToDetail(int i, int i2) {
        }
    }
}
