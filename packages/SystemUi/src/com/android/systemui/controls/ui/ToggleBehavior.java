package com.android.systemui.controls.ui;

import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ToggleTemplate;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ui.view.ActionIconView;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ToggleBehavior implements Behavior, CustomBehavior, CustomButtonBehavior {
    public Control control;
    public ControlViewHolder cvh;
    public ToggleTemplate template;

    @Override // com.android.systemui.controls.ui.Behavior
    public final void bind(ControlWithState controlWithState, int i) {
        ToggleTemplate toggleTemplate;
        Control control = controlWithState.control;
        Intrinsics.checkNotNull(control);
        this.control = control;
        ControlViewHolder cvh = getCvh();
        Control control2 = this.control;
        if (control2 == null) {
            control2 = null;
        }
        CharSequence statusText = control2.getStatusText();
        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
        cvh.setStatusText(statusText, false);
        Control control3 = this.control;
        ControlTemplate controlTemplate = (control3 != null ? control3 : null).getControlTemplate();
        if (controlTemplate instanceof ToggleTemplate) {
            toggleTemplate = (ToggleTemplate) controlTemplate;
        } else {
            if (!(controlTemplate instanceof TemperatureControlTemplate)) {
                Log.e("ControlsUiController", "Unsupported template type: " + controlTemplate);
                return;
            }
            toggleTemplate = (ToggleTemplate) ((TemperatureControlTemplate) controlTemplate).getTemplate();
        }
        this.template = toggleTemplate;
        (BasicRune.CONTROLS_SAMSUNG_STYLE ? (RippleDrawable) getCvh().layout.getBackground() : (LayerDrawable) getCvh().layout.getBackground()).findDrawableByLayerId(R.id.clip_layer).setLevel(10000);
        getCvh().m119x3918d5b8(i, getTemplate().isChecked(), true);
    }

    @Override // com.android.systemui.controls.ui.CustomBehavior
    public final void dispose() {
        ActionIconView actionIconView;
        getCvh().layout.setOnClickListener(null);
        if (!BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON || (actionIconView = getCvh().getCustomControlViewHolder().actionIcon) == null) {
            return;
        }
        actionIconView.setOnClickListener(null);
    }

    @Override // com.android.systemui.controls.ui.CustomButtonBehavior
    public final CharSequence getContentDescription() {
        return getTemplate().getContentDescription();
    }

    public final ControlViewHolder getCvh() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder != null) {
            return controlViewHolder;
        }
        return null;
    }

    public final ToggleTemplate getTemplate() {
        ToggleTemplate toggleTemplate = this.template;
        if (toggleTemplate != null) {
            return toggleTemplate;
        }
        return null;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void initialize(final ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
        boolean z = BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON;
        ViewGroup viewGroup = controlViewHolder.layout;
        if (!z) {
            viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ToggleBehavior$initialize$3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    ((ControlActionCoordinatorImpl) controlViewHolder2.controlActionCoordinator).toggle(controlViewHolder2, this.getTemplate().getTemplateId(), this.getTemplate().isChecked());
                }
            });
            return;
        }
        viewGroup.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ToggleBehavior$initialize$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CustomControlActionCoordinator customControlActionCoordinator = ControlViewHolder.this.getCustomControlViewHolder().customControlActionCoordinator;
                if (customControlActionCoordinator != null) {
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                    String templateId = this.getTemplate().getTemplateId();
                    Control control = this.control;
                    if (control == null) {
                        control = null;
                    }
                    ((ControlActionCoordinatorImpl) customControlActionCoordinator).customTouch(controlViewHolder2, templateId, control);
                }
            }
        });
        ActionIconView actionIconView = controlViewHolder.getCustomControlViewHolder().actionIcon;
        if (actionIconView != null) {
            actionIconView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ToggleBehavior$initialize$2$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CustomControlActionCoordinator customControlActionCoordinator = ControlViewHolder.this.getCustomControlViewHolder().customControlActionCoordinator;
                    if (customControlActionCoordinator != null) {
                        ((ControlActionCoordinatorImpl) customControlActionCoordinator).toggleMainAction(ControlViewHolder.this, this.getTemplate().getTemplateId(), this.getTemplate().isChecked());
                    }
                }
            });
        }
    }
}
