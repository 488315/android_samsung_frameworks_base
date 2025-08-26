package com.android.systemui.controls.ui;

import android.app.PendingIntent;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.RippleDrawable;
import android.service.controls.Control;
import android.service.controls.actions.CommandAction;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.TemperatureControlTemplate;
import android.service.controls.templates.ThumbnailTemplate;
import android.util.TypedValue;
import android.view.View;
import com.android.systemui.R;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.utils.SafeIconLoader;
import java.util.Set;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes2.dex */
public final class ThumbnailBehavior implements Behavior, SecBehavior, SecActionButtonBehavior {
    public final CanUseIconPredicate canUseIconPredicate;
    public Control control;
    public ControlViewHolder cvh;
    public final SafeIconLoader safeIconLoader;
    public int shadowColor;
    public float shadowOffsetX;
    public float shadowOffsetY;
    public float shadowRadius;
    public ThumbnailTemplate template;

    public ThumbnailBehavior(int i, SafeIconLoader safeIconLoader) {
        this.safeIconLoader = safeIconLoader;
        this.canUseIconPredicate = new CanUseIconPredicate(i);
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void bind(ControlWithState controlWithState, final int i) throws Resources.NotFoundException {
        Control control = controlWithState.control;
        control.getClass();
        this.control = control;
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
        ThumbnailTemplate thumbnailTemplate = controlTemplate instanceof ThumbnailTemplate ? (ThumbnailTemplate) controlTemplate : null;
        if (thumbnailTemplate == null) {
            Control control3 = this.control;
            if (control3 == null) {
                control3 = null;
            }
            thumbnailTemplate = (ThumbnailTemplate) ((TemperatureControlTemplate) control3.getControlTemplate()).getTemplate();
        }
        this.template = thumbnailTemplate;
        ControlViewHolder controlViewHolder2 = this.cvh;
        if (controlViewHolder2 == null) {
            controlViewHolder2 = null;
        }
        final ClipDrawable clipDrawable = (ClipDrawable) ((RippleDrawable) controlViewHolder2.layout.getBackground()).findDrawableByLayerId(R.id.clip_layer);
        ThumbnailTemplate thumbnailTemplate2 = this.template;
        if (thumbnailTemplate2 == null) {
            thumbnailTemplate2 = null;
        }
        clipDrawable.setLevel(thumbnailTemplate2.isActive() ? 10000 : 0);
        ControlViewHolder controlViewHolder3 = this.cvh;
        if (controlViewHolder3 == null) {
            controlViewHolder3 = null;
        }
        controlViewHolder3.layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior.bind.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlViewHolder controlViewHolder4 = ThumbnailBehavior.this.cvh;
                if (controlViewHolder4 == null) {
                    controlViewHolder4 = null;
                }
                SecControlActionCoordinator secControlActionCoordinator = controlViewHolder4.getSecControlViewHolder().secControlActionCoordinator;
                if (secControlActionCoordinator != null) {
                    ThumbnailBehavior thumbnailBehavior = ThumbnailBehavior.this;
                    ControlViewHolder controlViewHolder5 = thumbnailBehavior.cvh;
                    if (controlViewHolder5 == null) {
                        controlViewHolder5 = null;
                    }
                    ThumbnailTemplate thumbnailTemplate3 = thumbnailBehavior.template;
                    if (thumbnailTemplate3 == null) {
                        thumbnailTemplate3 = null;
                    }
                    String templateId = thumbnailTemplate3.getTemplateId();
                    Control control4 = ThumbnailBehavior.this.control;
                    ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchCard(controlViewHolder5, templateId, control4 != null ? control4 : null);
                }
            }
        });
        ControlViewHolder controlViewHolder4 = this.cvh;
        if (controlViewHolder4 == null) {
            controlViewHolder4 = null;
        }
        ControlsActionButton controlsActionButton = controlViewHolder4.getSecControlViewHolder().actionIcon;
        if (controlsActionButton != null) {
            controlsActionButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior.bind.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ControlViewHolder controlViewHolder5 = ThumbnailBehavior.this.cvh;
                    if (controlViewHolder5 == null) {
                        controlViewHolder5 = null;
                    }
                    SecControlActionCoordinator secControlActionCoordinator = controlViewHolder5.getSecControlViewHolder().secControlActionCoordinator;
                    if (secControlActionCoordinator != null) {
                        ThumbnailBehavior thumbnailBehavior = ThumbnailBehavior.this;
                        ControlViewHolder controlViewHolder6 = thumbnailBehavior.cvh;
                        if (controlViewHolder6 == null) {
                            controlViewHolder6 = null;
                        }
                        ThumbnailTemplate thumbnailTemplate3 = thumbnailBehavior.template;
                        if (thumbnailTemplate3 == null) {
                            thumbnailTemplate3 = null;
                        }
                        String templateId = thumbnailTemplate3.getTemplateId();
                        Control control4 = ThumbnailBehavior.this.control;
                        ((ControlActionCoordinatorImpl) secControlActionCoordinator).touchActionButton(controlViewHolder6, templateId, control4 != null ? control4 : null);
                    }
                }
            });
        }
        ThumbnailTemplate thumbnailTemplate3 = this.template;
        if (thumbnailTemplate3 == null) {
            thumbnailTemplate3 = null;
        }
        if (thumbnailTemplate3.isActive()) {
            ControlViewHolder controlViewHolder5 = this.cvh;
            if (controlViewHolder5 == null) {
                controlViewHolder5 = null;
            }
            controlViewHolder5.title.setVisibility(4);
            ControlViewHolder controlViewHolder6 = this.cvh;
            if (controlViewHolder6 == null) {
                controlViewHolder6 = null;
            }
            controlViewHolder6.subtitle.setVisibility(4);
            ControlViewHolder controlViewHolder7 = this.cvh;
            if (controlViewHolder7 == null) {
                controlViewHolder7 = null;
            }
            controlViewHolder7.status.setShadowLayer(this.shadowOffsetX, this.shadowOffsetY, this.shadowRadius, this.shadowColor);
            ControlViewHolder controlViewHolder8 = this.cvh;
            if (controlViewHolder8 == null) {
                controlViewHolder8 = null;
            }
            controlViewHolder8.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior.bind.3
                @Override // java.lang.Runnable
                public final void run() {
                    final Drawable drawableLoadDrawableCheckingUriGrant;
                    ThumbnailTemplate thumbnailTemplate4 = ThumbnailBehavior.this.template;
                    if (thumbnailTemplate4 == null) {
                        thumbnailTemplate4 = null;
                    }
                    Icon thumbnail = thumbnailTemplate4.getThumbnail();
                    if (!((Boolean) ThumbnailBehavior.this.canUseIconPredicate.mo781invoke(thumbnail)).booleanValue()) {
                        thumbnail = null;
                    }
                    if (thumbnail != null) {
                        SafeIconLoader safeIconLoader = ThumbnailBehavior.this.safeIconLoader;
                        drawableLoadDrawableCheckingUriGrant = thumbnail.loadDrawableCheckingUriGrant(safeIconLoader.serviceContext, safeIconLoader.iUriGrantsManager, safeIconLoader.serviceUid, safeIconLoader.packageName);
                    } else {
                        drawableLoadDrawableCheckingUriGrant = null;
                    }
                    final ThumbnailBehavior thumbnailBehavior = ThumbnailBehavior.this;
                    ControlViewHolder controlViewHolder9 = thumbnailBehavior.cvh;
                    DelayableExecutor delayableExecutor = (controlViewHolder9 != null ? controlViewHolder9 : null).uiExecutor;
                    final ClipDrawable clipDrawable2 = clipDrawable;
                    final int i2 = i;
                    delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior.bind.3.1
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            ControlViewHolder controlViewHolder10 = thumbnailBehavior.cvh;
                            if (controlViewHolder10 == null) {
                                controlViewHolder10 = null;
                            }
                            float dimensionPixelSize = controlViewHolder10.context.getResources().getDimensionPixelSize(R.dimen.control_corner_radius);
                            Drawable drawable = drawableLoadDrawableCheckingUriGrant;
                            if (drawable != null) {
                                clipDrawable2.setDrawable(new CornerDrawable(drawable, dimensionPixelSize));
                            }
                            ClipDrawable clipDrawable3 = clipDrawable2;
                            ControlViewHolder controlViewHolder11 = thumbnailBehavior.cvh;
                            if (controlViewHolder11 == null) {
                                controlViewHolder11 = null;
                            }
                            clipDrawable3.setColorFilter(new BlendModeColorFilter(controlViewHolder11.context.getResources().getColor(R.color.control_thumbnail_tint), BlendMode.LUMINOSITY));
                            ThumbnailBehavior thumbnailBehavior2 = thumbnailBehavior;
                            ControlViewHolder controlViewHolder12 = thumbnailBehavior2.cvh;
                            if (controlViewHolder12 == null) {
                                controlViewHolder12 = null;
                            }
                            ThumbnailTemplate thumbnailTemplate5 = thumbnailBehavior2.template;
                            controlViewHolder12.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i2, (thumbnailTemplate5 != null ? thumbnailTemplate5 : null).isActive(), true);
                        }
                    });
                }
            });
        } else {
            ControlViewHolder controlViewHolder9 = this.cvh;
            if (controlViewHolder9 == null) {
                controlViewHolder9 = null;
            }
            controlViewHolder9.title.setVisibility(0);
            ControlViewHolder controlViewHolder10 = this.cvh;
            if (controlViewHolder10 == null) {
                controlViewHolder10 = null;
            }
            controlViewHolder10.subtitle.setVisibility(0);
            ControlViewHolder controlViewHolder11 = this.cvh;
            if (controlViewHolder11 == null) {
                controlViewHolder11 = null;
            }
            controlViewHolder11.status.setShadowLayer(0.0f, 0.0f, 0.0f, this.shadowColor);
        }
        ControlViewHolder controlViewHolder12 = this.cvh;
        if (controlViewHolder12 == null) {
            controlViewHolder12 = null;
        }
        ThumbnailTemplate thumbnailTemplate4 = this.template;
        controlViewHolder12.applyRenderInfo$frameworks__base__packages__SystemUI__android_common__SystemUI_core(i, (thumbnailTemplate4 != null ? thumbnailTemplate4 : null).isActive(), true);
    }

    @Override // com.android.systemui.controls.ui.SecBehavior
    public final void dispose() {
        ControlViewHolder controlViewHolder = this.cvh;
        if (controlViewHolder == null) {
            controlViewHolder = null;
        }
        controlViewHolder.title.setVisibility(0);
        ControlViewHolder controlViewHolder2 = this.cvh;
        if (controlViewHolder2 == null) {
            controlViewHolder2 = null;
        }
        controlViewHolder2.subtitle.setVisibility(0);
        ControlViewHolder controlViewHolder3 = this.cvh;
        if (controlViewHolder3 == null) {
            controlViewHolder3 = null;
        }
        controlViewHolder3.status.setShadowLayer(0.0f, 0.0f, 0.0f, this.shadowColor);
        ControlViewHolder controlViewHolder4 = this.cvh;
        if (controlViewHolder4 == null) {
            controlViewHolder4 = null;
        }
        controlViewHolder4.layout.setOnClickListener(null);
        ControlViewHolder controlViewHolder5 = this.cvh;
        if (controlViewHolder5 == null) {
            controlViewHolder5 = null;
        }
        ControlsActionButton controlsActionButton = controlViewHolder5.getSecControlViewHolder().actionIcon;
        if (controlsActionButton != null) {
            controlsActionButton.setOnClickListener(null);
        }
        ControlViewHolder controlViewHolder6 = this.cvh;
        if (controlViewHolder6 == null) {
            controlViewHolder6 = null;
        }
        Pair pairInitClipLayerAndBaseLayer = controlViewHolder6.getSecControlViewHolder().initClipLayerAndBaseLayer();
        ControlViewHolder controlViewHolder7 = this.cvh;
        if (controlViewHolder7 == null) {
            controlViewHolder7 = null;
        }
        controlViewHolder7.clipLayer = (ClipDrawable) pairInitClipLayerAndBaseLayer.getFirst();
        ControlViewHolder controlViewHolder8 = this.cvh;
        (controlViewHolder8 != null ? controlViewHolder8 : null).baseLayer = (GradientDrawable) pairInitClipLayerAndBaseLayer.getSecond();
    }

    @Override // com.android.systemui.controls.ui.SecActionButtonBehavior
    public final CharSequence getContentDescription() {
        ThumbnailTemplate thumbnailTemplate = this.template;
        if (thumbnailTemplate == null) {
            thumbnailTemplate = null;
        }
        return thumbnailTemplate.getContentDescription();
    }

    @Override // com.android.systemui.controls.ui.Behavior
    public final void initialize(final ControlViewHolder controlViewHolder) throws Resources.NotFoundException {
        this.cvh = controlViewHolder;
        TypedValue typedValue = new TypedValue();
        controlViewHolder.context.getResources().getValue(R.dimen.controls_thumbnail_shadow_x, typedValue, true);
        this.shadowOffsetX = typedValue.getFloat();
        controlViewHolder.context.getResources().getValue(R.dimen.controls_thumbnail_shadow_y, typedValue, true);
        this.shadowOffsetY = typedValue.getFloat();
        controlViewHolder.context.getResources().getValue(R.dimen.controls_thumbnail_shadow_radius, typedValue, true);
        this.shadowRadius = typedValue.getFloat();
        this.shadowColor = controlViewHolder.context.getResources().getColor(R.color.control_thumbnail_shadow_color);
        controlViewHolder.layout.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.ThumbnailBehavior.initialize.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final ControlViewHolder controlViewHolder2 = controlViewHolder;
                ControlActionCoordinator controlActionCoordinator = controlViewHolder2.controlActionCoordinator;
                ThumbnailTemplate thumbnailTemplate = this.template;
                if (thumbnailTemplate == null) {
                    thumbnailTemplate = null;
                }
                final String templateId = thumbnailTemplate.getTemplateId();
                final Control control = this.control;
                if (control == null) {
                    control = null;
                }
                final ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) controlActionCoordinator;
                controlActionCoordinatorImpl.controlsMetricsLogger.touch(controlViewHolder2, controlActionCoordinatorImpl.isLocked());
                boolean zUsePanel = controlViewHolder2.usePanel();
                ControlWithState controlWithState = controlViewHolder2.cws;
                String str = (controlWithState != null ? controlWithState : null).ci.controlId;
                Function0 function0 = new Function0() { // from class: com.android.systemui.controls.ui.ControlActionCoordinatorImpl$$ExternalSyntheticLambda3
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        Control control2 = control;
                        int i = ControlActionCoordinatorImpl.$r8$clinit;
                        ControlViewHolder controlViewHolder3 = controlViewHolder2;
                        controlViewHolder3.layout.performHapticFeedback(6);
                        if (controlViewHolder3.usePanel()) {
                            PendingIntent appIntent = control2.getAppIntent();
                            ControlActionCoordinatorImpl controlActionCoordinatorImpl2 = controlActionCoordinatorImpl;
                            controlActionCoordinatorImpl2.getClass();
                            controlActionCoordinatorImpl2.bgExecutor.execute(new ControlActionCoordinatorImpl$showDetail$1(controlActionCoordinatorImpl2, appIntent, false, controlViewHolder3));
                        } else {
                            controlViewHolder3.action(new CommandAction(templateId));
                        }
                        return Unit.INSTANCE;
                    }
                };
                Control control2 = (controlWithState != null ? controlWithState : null).control;
                controlActionCoordinatorImpl.bouncerOrRun(controlActionCoordinatorImpl.createAction(str, function0, zUsePanel, control2 != null ? control2.isAuthRequired() : true));
            }
        });
    }
}
