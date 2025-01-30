package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LayoutProviderContainerImpl implements LayoutProviderContainer {
    public final Context context;

    public LayoutProviderContainerImpl(Context context) {
        this.context = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer
    public final LayoutProvider updateLayoutProvider(boolean z, boolean z2) {
        Context context = this.context;
        if (!z) {
            return z2 ? new LayoutProviderImpl(context) : new TabletLayoutProviderImpl(context);
        }
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length > 1) {
            context = context.createDisplayContext(displays[1]);
        }
        return new CoverLayoutProviderImpl(context);
    }
}
