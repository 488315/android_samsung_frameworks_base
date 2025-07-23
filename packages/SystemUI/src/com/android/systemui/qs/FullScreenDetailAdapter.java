package com.android.systemui.qs;

import android.content.Intent;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.DetailAdapter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(version = 1)
/* loaded from: classes2.dex */
public abstract class FullScreenDetailAdapter implements DetailAdapter {
    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public Intent getSettingsIntent() {
        return null;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public CharSequence getTitle() {
        return null;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Boolean getToggleState() {
        return null;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public boolean shouldUseFullScreen() {
        return true;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void setToggleState(boolean z) {
    }
}
