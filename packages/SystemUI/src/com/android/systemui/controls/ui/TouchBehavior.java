package com.android.systemui.controls.ui;

import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.view.View;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class TouchBehavior implements Behavior, SecBehavior {
    public Control control;
    public ControlViewHolder cvh;
    public boolean hasCustomColorInNoTemplate;
    public int lastColorOffset;
    public boolean statelessTouch;
    public ControlTemplate template;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0084  */
    @Override // com.android.systemui.controls.ui.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bind(com.android.systemui.controls.ui.ControlWithState r5, int r6) {
        /*
            r4 = this;
            android.service.controls.Control r5 = r5.control
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            r4.control = r5
            r4.lastColorOffset = r6
            com.android.systemui.controls.ui.ControlViewHolder r0 = r4.cvh
            r1 = 0
            if (r0 == 0) goto Lf
            goto L10
        Lf:
            r0 = r1
        L10:
            java.lang.CharSequence r5 = r5.getStatusText()
            java.util.Set r2 = com.android.systemui.controls.ui.ControlViewHolder.FORCE_PANEL_DEVICES
            r2 = 0
            r0.setStatusText(r5, r2)
            android.service.controls.Control r5 = r4.control
            if (r5 == 0) goto L1f
            goto L20
        L1f:
            r5 = r1
        L20:
            android.service.controls.templates.ControlTemplate r5 = r5.getControlTemplate()
            r4.template = r5
            boolean r5 = r5 instanceof android.service.controls.templates.StatelessTemplate
            if (r5 == 0) goto L40
            com.android.systemui.controls.ui.ControlViewHolder r5 = r4.cvh
            if (r5 == 0) goto L2f
            goto L30
        L2f:
            r5 = r1
        L30:
            com.android.systemui.controls.ui.SecControlViewHolder r5 = r5.getSecControlViewHolder()
            com.android.systemui.controls.ui.view.ControlsActionButton r5 = r5.actionIcon
            if (r5 == 0) goto L40
            com.android.systemui.controls.ui.TouchBehavior$bind$1 r0 = new com.android.systemui.controls.ui.TouchBehavior$bind$1
            r0.<init>()
            r5.setOnClickListener(r0)
        L40:
            android.service.controls.templates.ControlTemplate r5 = r4.template
            if (r5 == 0) goto L45
            goto L46
        L45:
            r5 = r1
        L46:
            android.service.controls.templates.ControlTemplate r0 = android.service.controls.templates.ControlTemplate.NO_TEMPLATE
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            r0 = 1
            if (r5 == 0) goto L5d
            android.service.controls.Control r5 = r4.control
            if (r5 == 0) goto L54
            goto L55
        L54:
            r5 = r1
        L55:
            android.content.res.ColorStateList r5 = r5.getCustomColor()
            if (r5 == 0) goto L5d
            r5 = r0
            goto L5e
        L5d:
            r5 = r2
        L5e:
            r4.hasCustomColorInNoTemplate = r5
            com.android.systemui.controls.ui.ControlViewHolder r5 = r4.cvh
            if (r5 == 0) goto L65
            goto L66
        L65:
            r5 = r1
        L66:
            android.view.ViewGroup r5 = r5.layout
            android.graphics.drawable.Drawable r5 = r5.getBackground()
            android.graphics.drawable.RippleDrawable r5 = (android.graphics.drawable.RippleDrawable) r5
            r3 = 2131362455(0x7f0a0297, float:1.8344691E38)
            android.graphics.drawable.Drawable r5 = r5.findDrawableByLayerId(r3)
            boolean r3 = r4.getEnabled$1()
            if (r3 == 0) goto L7d
            r2 = 10000(0x2710, float:1.4013E-41)
        L7d:
            r5.setLevel(r2)
            com.android.systemui.controls.ui.ControlViewHolder r5 = r4.cvh
            if (r5 == 0) goto L85
            r1 = r5
        L85:
            boolean r4 = r4.getEnabled$1()
            r1.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(r6, r4, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.ui.TouchBehavior.bind(com.android.systemui.controls.ui.ControlWithState, int):void");
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
        controlViewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.TouchBehavior$initialize$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecControlActionCoordinator secControlActionCoordinator = ControlViewHolder.this.getSecControlViewHolder().secControlActionCoordinator;
                if (secControlActionCoordinator != null) {
                    ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
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
