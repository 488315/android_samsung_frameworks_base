package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import com.android.systemui.dagger.NightDisplayListenerModule$Builder;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.domain.model.AutoAddable;
import com.android.systemui.qs.pipeline.shared.TileSpec;

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
