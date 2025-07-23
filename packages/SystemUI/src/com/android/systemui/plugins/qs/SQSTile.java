package com.android.systemui.plugins.qs;

import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Dependencies({@DependsOn(target = QSIconView.class), @DependsOn(target = QSTile.Callback.class), @DependsOn(target = QSTile.Icon.class), @DependsOn(target = QSTile.State.class)})
@ProvidesInterface(version = 5)
/* loaded from: classes2.dex */
public interface SQSTile extends QSTile {
    public static final int VERSION = 4;

    @Override // com.android.systemui.plugins.qs.QSTile
    default DetailAdapter getDetailAdapter() {
        return null;
    }

    String getTileMapKey();

    void sendTileStatusLog();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    @ProvidesInterface(version = 2)
    public interface SCallback extends QSTile.Callback {
        default void onScanStateChanged(boolean z) {
        }

        default void onShowDetail(boolean z) {
        }

        default void onToggleStateChanged(boolean z) {
        }

        default void onUpdateDetail() {
        }

        default void onScrollToDetail(int i, int i2) {
        }
    }

    default void updateDetail() {
    }
}
