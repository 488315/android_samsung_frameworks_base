package com.android.systemui.qs;

import android.content.Intent;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import com.android.systemui.plugins.qs.DetailAdapter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
