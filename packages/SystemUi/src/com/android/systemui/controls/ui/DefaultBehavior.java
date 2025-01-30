package com.android.systemui.controls.ui;

import android.service.controls.Control;
import android.view.View;
import com.android.systemui.BasicRune;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultBehavior implements Behavior, CustomBehavior {
    public ControlViewHolder cvh;

    @Override // com.android.systemui.controls.ui.Behavior
    public final void bind(ControlWithState controlWithState, int i) {
        ControlViewHolder cvh = getCvh();
        final Control control = controlWithState.control;
        CharSequence statusText = control != null ? control.getStatusText() : null;
        if (statusText == null) {
            statusText = "";
        }
        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
        cvh.setStatusText(statusText, false);
        getCvh().m119x3918d5b8(i, false, true);
        if (!BasicRune.CONTROLS_SAMSUNG_STYLE || control == null) {
            return;
        }
        getCvh().layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DefaultBehavior$bind$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomControlActionCoordinator customControlActionCoordinator = DefaultBehavior.this.getCvh().getCustomControlViewHolder().customControlActionCoordinator;
                if (customControlActionCoordinator != null) {
                    ((ControlActionCoordinatorImpl) customControlActionCoordinator).customTouch(DefaultBehavior.this.getCvh(), control.getControlTemplate().getTemplateId(), control);
                }
            }
        });
    }

    @Override // com.android.systemui.controls.ui.CustomBehavior
    public final void dispose() {
        getCvh().layout.setOnClickListener(null);
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}
