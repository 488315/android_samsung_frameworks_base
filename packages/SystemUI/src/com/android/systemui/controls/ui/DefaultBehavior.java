package com.android.systemui.controls.ui;

import android.content.res.Resources;
import android.service.controls.Control;
import android.view.View;
import java.util.Set;

/* loaded from: classes2.dex */
public final class DefaultBehavior implements Behavior, SecBehavior {
    public ControlViewHolder cvh;

    @Override // com.android.systemui.controls.ui.Behavior
    public final void bind(ControlWithState controlWithState, int i) throws Resources.NotFoundException {
        CharSequence statusText;
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        Control control = controlWithState.control;
        if (control == null || (statusText = control.getStatusText()) == null) {
            statusText = "";
        }
        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
        controlViewHolder.setStatusText(statusText, false);
        ControlViewHolder controlViewHolder2 = this.cvh;
        if (controlViewHolder2 == null) {
            controlViewHolder2 = null;
        }
        controlViewHolder2.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, false, true);
        final Control control2 = controlWithState.control;
        if (control2 != null) {
            ControlViewHolder controlViewHolder3 = this.cvh;
            (controlViewHolder3 != null ? controlViewHolder3 : null).layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.DefaultBehavior$bind$1$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ControlViewHolder controlViewHolder4 = this.this$0.cvh;
                    if (controlViewHolder4 == null) {
                        controlViewHolder4 = null;
                    }
                    SecControlActionCoordinator secControlActionCoordinator = controlViewHolder4.getSecControlViewHolder().secControlActionCoordinator;
                    if (secControlActionCoordinator != null) {
                        ControlViewHolder controlViewHolder5 = this.this$0.cvh;
                        ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchCard(controlViewHolder5 != null ? controlViewHolder5 : null, control2.getControlTemplate().getTemplateId(), control2);
                    }
                }
            });
        }
    }

    @Override // com.android.systemui.controls.ui.SecBehavior
    public final void dispose() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        controlViewHolder.layout.setOnClickListener(null);
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void initialize(ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
    }
}
