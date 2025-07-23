package com.android.systemui.samsung.quicksetting.domain.model.items;

import com.android.internal.logging.InstanceId;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.qs.QSTile;
import kotlin.NotImplementedError;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QuickTileKt$getDummyTile$1 implements QSTile {
    @Override // com.android.systemui.plugins.qs.QSTile
    public final int getCurrentTileUser() {
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final InstanceId getInstanceId() {
        throw new NotImplementedError("An operation is not implemented: Not yet implemented");
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final QSTile.State getState() {
        return new QSTile.State();
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return null;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final String getTileSpec() {
        return "";
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isDestroyed() {
        return false;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean isListening() {
        return true;
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void addCallback(QSTile.Callback callback) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void click(Expandable expandable) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void longClick(Expandable expandable) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void removeCallback(QSTile.Callback callback) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void secondaryClick(Expandable expandable) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void setDetailListening(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void setTileSpec(String str) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void userSwitch(int i) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void refreshState() {
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final void removeCallbacks() {
    }

    @Override // com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final void setListening(Object obj, boolean z) {
    }
}
