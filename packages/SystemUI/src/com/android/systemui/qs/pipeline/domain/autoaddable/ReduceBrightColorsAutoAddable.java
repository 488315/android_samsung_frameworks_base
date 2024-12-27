package com.android.systemui.qs.pipeline.domain.autoaddable;

import android.view.accessibility.Flags;
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.pipeline.domain.model.AutoAddTracking;
import com.android.systemui.qs.pipeline.shared.TileSpec;

public final class ReduceBrightColorsAutoAddable extends CallbackControllerAutoAddable {
    public final boolean available;
    public final String description;

    public ReduceBrightColorsAutoAddable(ReduceBrightColorsController reduceBrightColorsController, boolean z) {
        super(reduceBrightColorsController);
        this.available = z;
        this.description = "ReduceBrightColorsAutoAddable (" + getAutoAddTracking() + ")";
    }

    @Override // com.android.systemui.qs.pipeline.domain.autoaddable.CallbackControllerAutoAddable
    public final AutoAddTracking getAutoAddTracking() {
        return Flags.a11yQsShortcut() ? AutoAddTracking.Disabled.INSTANCE : this.available ? super.getAutoAddTracking() : AutoAddTracking.Disabled.INSTANCE;
    }

    @Override // com.android.systemui.qs.pipeline.domain.model.AutoAddable
    public final String getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.qs.pipeline.domain.autoaddable.CallbackControllerAutoAddable
    public final TileSpec getSpec() {
        TileSpec.Companion.getClass();
        return TileSpec.Companion.create("ReduceBrightColors");
    }
}
