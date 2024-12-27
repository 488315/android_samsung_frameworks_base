package com.android.systemui.qs;

import android.content.Intent;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.DetailAdapter;

@ProvidesInterface(version = 1)
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
