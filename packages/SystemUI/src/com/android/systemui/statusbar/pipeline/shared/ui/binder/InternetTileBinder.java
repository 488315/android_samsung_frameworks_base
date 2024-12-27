package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.qs.tiles.InternetTileNewImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

public final class InternetTileBinder {
    public static final InternetTileBinder INSTANCE = new InternetTileBinder();

    private InternetTileBinder() {
    }

    public static void bind(LifecycleRegistry lifecycleRegistry, ReadonlyStateFlow readonlyStateFlow, InternetTileNewImpl.AnonymousClass1 anonymousClass1) {
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleRegistry), null, null, new InternetTileBinder$bind$1(lifecycleRegistry, readonlyStateFlow, anonymousClass1, null), 3);
    }
}
