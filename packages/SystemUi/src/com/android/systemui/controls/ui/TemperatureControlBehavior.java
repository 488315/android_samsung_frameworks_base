package com.android.systemui.controls.ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TemperatureControlBehavior implements Behavior, CustomBehavior {
    public Drawable clipLayer;
    public Control control;
    public ControlViewHolder cvh;
    public Behavior subBehavior;

    @Override // com.android.systemui.controls.ui.Behavior
    public final void bind(ControlWithState controlWithState, int i) {
        Control control = controlWithState.control;
        Intrinsics.checkNotNull(control);
        this.control = control;
        ControlViewHolder cvh = getCvh();
        CharSequence statusText = getControl().getStatusText();
        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
        cvh.setStatusText(statusText, false);
        this.clipLayer = (BasicRune.CONTROLS_SAMSUNG_STYLE ? (RippleDrawable) getCvh().layout.getBackground() : (LayerDrawable) getCvh().layout.getBackground()).findDrawableByLayerId(R.id.clip_layer);
        final TemperatureControlTemplate temperatureControlTemplate = (TemperatureControlTemplate) getControl().getControlTemplate();
        int currentActiveMode = temperatureControlTemplate.getCurrentActiveMode();
        ControlTemplate template = temperatureControlTemplate.getTemplate();
        if (!Intrinsics.areEqual(template, ControlTemplate.getNoTemplateObject()) && !Intrinsics.areEqual(template, ControlTemplate.getErrorTemplate())) {
            this.subBehavior = getCvh().bindBehavior(this.subBehavior, getCvh().findBehaviorClass(getControl().getStatus(), template, getControl().getDeviceType(), 0), currentActiveMode);
            return;
        }
        boolean z = (currentActiveMode == 0 || currentActiveMode == 1) ? false : true;
        Drawable drawable = this.clipLayer;
        if (drawable == null) {
            drawable = null;
        }
        drawable.setLevel(z ? 10000 : 0);
        getCvh().m119x3918d5b8(currentActiveMode, z, true);
        getCvh().layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.TemperatureControlBehavior$bind$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (!BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON) {
                    ((ControlActionCoordinatorImpl) TemperatureControlBehavior.this.getCvh().controlActionCoordinator).touch(TemperatureControlBehavior.this.getCvh(), temperatureControlTemplate.getTemplateId(), TemperatureControlBehavior.this.getControl());
                } else {
                    CustomControlActionCoordinator customControlActionCoordinator = TemperatureControlBehavior.this.getCvh().getCustomControlViewHolder().customControlActionCoordinator;
                    if (customControlActionCoordinator != null) {
                        ((ControlActionCoordinatorImpl) customControlActionCoordinator).customTouch(TemperatureControlBehavior.this.getCvh(), temperatureControlTemplate.getTemplateId(), TemperatureControlBehavior.this.getControl());
                    }
                }
            }
        });
    }

    @Override // com.android.systemui.controls.ui.CustomBehavior
    public final void dispose() {
        getCvh().layout.setOnClickListener(null);
        Behavior behavior = this.subBehavior;
        CustomBehavior customBehavior = behavior instanceof CustomBehavior ? (CustomBehavior) behavior : null;
        if (customBehavior != null) {
            customBehavior.dispose();
        }
    }

    public final Control getControl() {
        Control control = this.control;
        if (control != null) {
            return control;
        }
        return null;
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
