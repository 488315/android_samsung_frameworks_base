package com.android.systemui.controls.ui;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class TouchBehavior implements Behavior, SecBehavior {
    public Control control;
    public ControlViewHolder cvh;
    public boolean hasCustomColorInNoTemplate;
    public int lastColorOffset;
    public boolean statelessTouch;
    public ControlTemplate template;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0061  */
    @Override // com.android.systemui.controls.ui.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bind(ControlWithState controlWithState, int i) throws Resources.NotFoundException {
        boolean z;
        Control control = controlWithState.control;
        control.getClass();
        this.control = control;
        this.lastColorOffset = i;
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        CharSequence statusText = control.getStatusText();
        Set set = ControlViewHolder.FORCE_PANEL_DEVICES;
        controlViewHolder.setStatusText(statusText, false);
        Control control2 = this.control;
        if (control2 == null) {
            control2 = null;
        }
        ControlTemplate controlTemplate = control2.getControlTemplate();
        this.template = controlTemplate;
        if (controlTemplate == null) {
            controlTemplate = null;
        }
        if (controlTemplate instanceof StatelessTemplate) {
            ControlViewHolder controlViewHolder2 = this.cvh;
            if (controlViewHolder2 == null) {
                controlViewHolder2 = null;
            }
            ControlsActionButton controlsActionButton = controlViewHolder2.getSecControlViewHolder().actionIcon;
            if (controlsActionButton != null) {
                controlsActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.TouchBehavior.bind.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) throws Resources.NotFoundException {
                        ControlViewHolder controlViewHolder3 = TouchBehavior.this.cvh;
                        if (controlViewHolder3 == null) {
                            controlViewHolder3 = null;
                        }
                        SecControlActionCoordinator secControlActionCoordinator = controlViewHolder3.getSecControlViewHolder().secControlActionCoordinator;
                        if (secControlActionCoordinator != null) {
                            TouchBehavior touchBehavior = TouchBehavior.this;
                            ControlViewHolder controlViewHolder4 = touchBehavior.cvh;
                            if (controlViewHolder4 == null) {
                                controlViewHolder4 = null;
                            }
                            ControlTemplate controlTemplate2 = touchBehavior.template;
                            if (controlTemplate2 == null) {
                                controlTemplate2 = null;
                            }
                            String templateId = controlTemplate2.getTemplateId();
                            Control control3 = TouchBehavior.this.control;
                            if (control3 == null) {
                                control3 = null;
                            }
                            ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchActionButton(controlViewHolder4, templateId, control3);
                        }
                        TouchBehavior touchBehavior2 = TouchBehavior.this;
                        ControlTemplate controlTemplate3 = touchBehavior2.template;
                        if (controlTemplate3 == null) {
                            controlTemplate3 = null;
                        }
                        if (controlTemplate3 instanceof StatelessTemplate) {
                            touchBehavior2.statelessTouch = true;
                            ControlViewHolder controlViewHolder5 = touchBehavior2.cvh;
                            if (controlViewHolder5 == null) {
                                controlViewHolder5 = null;
                            }
                            controlViewHolder5.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(TouchBehavior.this.lastColorOffset, touchBehavior2.getEnabled$1(), true);
                            final TouchBehavior touchBehavior3 = TouchBehavior.this;
                            ControlViewHolder controlViewHolder6 = touchBehavior3.cvh;
                            (controlViewHolder6 != null ? controlViewHolder6 : null).uiExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.controls.ui.TouchBehavior.bind.1.1
                                @Override // java.lang.Runnable
                                public final void run() throws Resources.NotFoundException {
                                    TouchBehavior touchBehavior4 = touchBehavior3;
                                    touchBehavior4.statelessTouch = false;
                                    ControlViewHolder controlViewHolder7 = touchBehavior4.cvh;
                                    if (controlViewHolder7 == null) {
                                        controlViewHolder7 = null;
                                    }
                                    boolean enabled$1 = touchBehavior4.getEnabled$1();
                                    int i2 = touchBehavior3.lastColorOffset;
                                    Set set2 = ControlViewHolder.FORCE_PANEL_DEVICES;
                                    controlViewHolder7.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i2, enabled$1, true);
                                }
                            }, 3000L);
                        }
                    }
                });
            }
        }
        ControlTemplate controlTemplate2 = this.template;
        if (controlTemplate2 == null) {
            controlTemplate2 = null;
        }
        if (Intrinsics.areEqual(controlTemplate2, ControlTemplate.NO_TEMPLATE)) {
            Control control3 = this.control;
            if (control3 == null) {
                control3 = null;
            }
            if (control3.getCustomColor() != null) {
                z = true;
            }
        } else {
            z = false;
        }
        this.hasCustomColorInNoTemplate = z;
        ControlViewHolder controlViewHolder3 = this.cvh;
        if (controlViewHolder3 == null) {
            controlViewHolder3 = null;
        }
        Drawable drawableFindDrawableByLayerId = ((RippleDrawable) controlViewHolder3.layout.getBackground()).findDrawableByLayerId(R.id.clip_layer);
        if (drawableFindDrawableByLayerId == null) {
            drawableFindDrawableByLayerId = null;
        }
        drawableFindDrawableByLayerId.setLevel(getEnabled$1() ? 10000 : 0);
        ControlViewHolder controlViewHolder4 = this.cvh;
        (controlViewHolder4 != null ? controlViewHolder4 : null).applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, getEnabled$1(), true);
    }

    @Override // com.android.systemui.controls.ui.SecBehavior
    public final void dispose() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        controlViewHolder.layout.setOnClickListener(null);
        ControlViewHolder controlViewHolder2 = this.cvh;
        if (controlViewHolder2 == null) {
            controlViewHolder2 = null;
        }
        ControlsActionButton controlsActionButton = controlViewHolder2.getSecControlViewHolder().actionIcon;
        if (controlsActionButton != null) {
            controlsActionButton.setOnClickListener(null);
        }
    }

    public final boolean getEnabled$1() {
        return this.lastColorOffset > 0 || this.statelessTouch || this.hasCustomColorInNoTemplate;
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void initialize(final ControlViewHolder controlViewHolder) {
        this.cvh = controlViewHolder;
        controlViewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.TouchBehavior.initialize.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecControlActionCoordinator secControlActionCoordinator = controlViewHolder.getSecControlViewHolder().secControlActionCoordinator;
                if (secControlActionCoordinator != null) {
                    ControlViewHolder controlViewHolder2 = controlViewHolder;
                    ControlTemplate controlTemplate = this.template;
                    if (controlTemplate == null) {
                        controlTemplate = null;
                    }
                    String templateId = controlTemplate.getTemplateId();
                    Control control = this.control;
                    ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchCard(controlViewHolder2, templateId, control != null ? control : null);
                }
            }
        });
    }
}
