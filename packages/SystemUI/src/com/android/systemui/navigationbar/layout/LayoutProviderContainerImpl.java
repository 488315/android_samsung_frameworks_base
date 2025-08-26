package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;

/* loaded from: classes2.dex */
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
        Context contextCreateDisplayContext = this.context;
        Display[] displays = ((DisplayManager) contextCreateDisplayContext.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length > 1) {
            Log.d("LayoutProviderContainerImpl", "getCoverDisplayContext, cover display=" + displays[1]);
            contextCreateDisplayContext = contextCreateDisplayContext.createDisplayContext(displays[1]);
        } else {
            Log.e("LayoutProviderContainerImpl", "getCoverDisplayContext, cannot find display with id 1");
        }
        return new CoverLayoutProviderImpl(contextCreateDisplayContext);
    }
}
