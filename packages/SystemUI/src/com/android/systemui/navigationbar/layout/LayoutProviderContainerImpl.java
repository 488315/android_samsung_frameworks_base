package com.android.systemui.navigationbar.layout;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;
import com.samsung.systemui.splugins.navigationbar.LayoutProvider;
import com.samsung.systemui.splugins.navigationbar.LayoutProviderContainer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Context context = this.context;
        Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length > 1) {
            Log.d("LayoutProviderContainerImpl", "getCoverDisplayContext, cover display=" + displays[1]);
            context = context.createDisplayContext(displays[1]);
        } else {
            Log.e("LayoutProviderContainerImpl", "getCoverDisplayContext, cannot find display with id 1");
        }
        return new CoverLayoutProviderImpl(context);
    }
}
