package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;

public final class LayoutProviderContainerImpl implements LayoutProviderContainer {
    public final Context context;

    public LayoutProviderContainerImpl(Context context) {
        this.context = context;
    }

    @Override // com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer
    public final LayoutProvider updateLayoutProvider(boolean z, boolean z2) {
        if (!z) {
            return z2 ? new LayoutProviderImpl(this.context) : new TabletLayoutProviderImpl(this.context);
        }
        Context context = this.context;
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length > 1) {
            context = context.createDisplayContext(displays[1]);
        }
        return new CoverLayoutProviderImpl(context);
    }
}
