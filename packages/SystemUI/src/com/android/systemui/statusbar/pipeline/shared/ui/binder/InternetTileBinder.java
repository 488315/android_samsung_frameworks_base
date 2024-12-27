package com.android.systemui.statusbar.pipeline.shared.ui.binder;

import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.LifecycleRegistry;
import com.android.systemui.qs.tiles.InternetTileNewImpl;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class InternetTileBinder {
    public static final InternetTileBinder INSTANCE = new InternetTileBinder();

    private InternetTileBinder() {
    }

    public static void bind(LifecycleRegistry lifecycleRegistry, ReadonlyStateFlow readonlyStateFlow, InternetTileNewImpl.AnonymousClass1 anonymousClass1) {
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(lifecycleRegistry), null, null, new InternetTileBinder$bind$1(lifecycleRegistry, readonlyStateFlow, anonymousClass1, null), 3);
    }
}
