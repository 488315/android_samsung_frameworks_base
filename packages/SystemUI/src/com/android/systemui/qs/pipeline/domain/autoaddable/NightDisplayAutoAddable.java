package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import com.android.systemui.dagger.NightDisplayListenerModule$Builder;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NightDisplayAutoAddable implements AutoAddable {
    public final AutoAddTracking autoAddTracking;
    public final String description;
    public final NightDisplayListenerModule$Builder nightDisplayListenerBuilder;
    public final TileSpec spec;

    public NightDisplayAutoAddable(NightDisplayListenerModule$Builder nightDisplayListenerModule$Builder, Context context) {
        boolean isNightDisplayAvailable = ColorDisplayManager.isNightDisplayAvailable(context);
        TileSpec.Companion.getClass();
        this.description = "NightDisplayAutoAddable (" + (isNightDisplayAvailable ? new AutoAddTracking.IfNotAdded(TileSpec.Companion.create("night")) : AutoAddTracking.Disabled.INSTANCE) + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }
}
