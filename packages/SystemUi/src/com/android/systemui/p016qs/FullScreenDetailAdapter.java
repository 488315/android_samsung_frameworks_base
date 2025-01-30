package com.android.systemui.p016qs;

import android.content.Intent;
import com.android.systemui.plugins.p013qs.DetailAdapter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class FullScreenDetailAdapter implements DetailAdapter {
    @Override // com.android.systemui.plugins.p013qs.DetailAdapter
    public final Intent getSettingsIntent() {
        return null;
    }

    @Override // com.android.systemui.plugins.p013qs.DetailAdapter
    public final CharSequence getTitle() {
        return null;
    }

    @Override // com.android.systemui.plugins.p013qs.DetailAdapter
    public final Boolean getToggleState() {
        return null;
    }

    @Override // com.android.systemui.plugins.p013qs.DetailAdapter
    public final boolean shouldUseFullScreen() {
        return true;
    }

    @Override // com.android.systemui.plugins.p013qs.DetailAdapter
    public final void setToggleState(boolean z) {
    }
}
