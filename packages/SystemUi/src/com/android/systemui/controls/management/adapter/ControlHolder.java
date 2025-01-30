package com.android.systemui.controls.management.adapter;

import android.content.res.Resources;
import android.service.controls.Control;
import android.service.controls.templates.StatelessTemplate;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlsMetricsLoggerImpl;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.p005ui.Behavior;
import com.android.systemui.controls.p005ui.ControlActionCoordinatorImpl;
import com.android.systemui.controls.p005ui.ControlViewHolder;
import com.android.systemui.controls.p005ui.ControlWithState;
import com.android.systemui.controls.p005ui.CustomBehavior;
import com.android.systemui.controls.p005ui.CustomButtonBehavior;
import com.android.systemui.controls.p005ui.CustomControlViewHolder;
import com.android.systemui.controls.p005ui.TouchBehavior;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.controls.p005ui.view.ActionIconView;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlHolder extends Holder {
    public final ControlViewHolder controlViewHolder;
    public final Map holders;

    public ControlHolder(View view, ControlViewHolder controlViewHolder, Map<String, ControlViewHolder> map) {
        super(view, null);
        this.controlViewHolder = controlViewHolder;
        this.holders = map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v25 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7, types: [com.android.systemui.controls.ui.CustomBehavior] */
    @Override // com.android.systemui.controls.management.adapter.Holder
    public final void bindData(MainModel mainModel) {
        ControlWithState controlWithState;
        ImageView imageView;
        ImageView imageView2;
        LottieAnimationView lottieAnimationView;
        ImageView imageView3;
        ActionIconView actionIconView;
        ProgressBar progressBar;
        if ((mainModel instanceof MainControlModel) && (controlWithState = ((MainControlModel) mainModel).controlWithState) != null) {
            final ControlViewHolder controlViewHolder = this.controlViewHolder;
            controlViewHolder.getClass();
            boolean z = BasicRune.CONTROLS_SAMSUNG_STYLE;
            boolean z2 = false;
            if (z) {
                CustomControlViewHolder customControlViewHolder = controlViewHolder.getCustomControlViewHolder();
                Behavior behavior = controlViewHolder.behavior;
                customControlViewHolder.getClass();
                boolean z3 = BasicRune.CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING;
                ImageView imageView4 = customControlViewHolder.icon;
                if (z3 && customControlViewHolder.controlsUtil != null) {
                    Resources resources = imageView4.getContext().getResources();
                    ControlsUtil.Companion companion = ControlsUtil.Companion;
                    int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_custom_icon_padding_size);
                    imageView4.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                }
                imageView4.setVisibility(0);
                imageView4.setBackground(null);
                imageView4.setImageDrawable(null);
                imageView4.setImageState(new int[0], false);
                imageView4.setImageTintList(null);
                imageView4.setAlpha(1.0f);
                if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON && (actionIconView = customControlViewHolder.actionIcon) != null) {
                    ImageView imageView5 = actionIconView.actionIcon;
                    imageView5.setImageDrawable(null);
                    imageView5.setVisibility(8);
                    if (BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON_PROGRESS && (progressBar = actionIconView.actionIconProgress) != null) {
                        progressBar.setVisibility(8);
                    }
                }
                if (BasicRune.CONTROLS_CUSTOM_STATUS && (imageView3 = customControlViewHolder.statusIcon) != null) {
                    imageView3.setImageDrawable(null);
                }
                if (BasicRune.CONTROLS_LOTTIE_ICON_ANIMATION && (lottieAnimationView = customControlViewHolder.animationView) != null) {
                    lottieAnimationView.setVisibility(8);
                    lottieAnimationView.cancelAnimation();
                }
                if ((customControlViewHolder.controlsRuneWrapper != null && BasicRune.CONTROLS_OVERLAY_CUSTOM_ICON) && (imageView2 = customControlViewHolder.overlayCustomIcon) != null) {
                    imageView2.setVisibility(8);
                    imageView2.setImageDrawable(null);
                }
                if (!(behavior instanceof TouchBehavior) || !(((TouchBehavior) behavior).getTemplate() instanceof StatelessTemplate)) {
                    CustomBehavior customBehavior = customControlViewHolder.customBehavior;
                    if (customBehavior != null) {
                        customBehavior.dispose();
                        customControlViewHolder.customBehavior = null;
                    }
                    ViewGroup viewGroup = customControlViewHolder.layout;
                    viewGroup.setOnClickListener(null);
                    viewGroup.setOnTouchListener(null);
                    behavior = null;
                }
                controlViewHolder.behavior = behavior;
            }
            ControlInfo controlInfo = controlWithState.f249ci;
            if (z || !controlViewHolder.userInteractionInProgress) {
                controlViewHolder.cws = controlWithState;
                int controlStatus = controlViewHolder.getControlStatus();
                TextView textView = controlViewHolder.subtitle;
                TextView textView2 = controlViewHolder.title;
                Control control = controlWithState.control;
                if (controlStatus == 0 || controlViewHolder.getControlStatus() == 2) {
                    textView2.setText(controlInfo.controlTitle);
                    textView.setText(controlInfo.controlSubtitle);
                } else if (control != null) {
                    textView2.setText(control.getTitle());
                    textView.setText(control.getSubtitle());
                    if (!z && (imageView = controlViewHolder.chevronIcon) != null) {
                        imageView.setVisibility(controlViewHolder.usePanel() ? 0 : 4);
                    }
                }
                if (control != null) {
                    ViewGroup viewGroup2 = controlViewHolder.layout;
                    viewGroup2.setClickable(true);
                    if (!BasicRune.CONTROLS_CUSTOM_MAIN_ACTION_ICON) {
                        viewGroup2.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.controls.ui.ControlViewHolder$bindData$2$1
                            @Override // android.view.View.OnLongClickListener
                            public final boolean onLongClick(View view) {
                                ControlViewHolder controlViewHolder2 = ControlViewHolder.this;
                                ((ControlActionCoordinatorImpl) controlViewHolder2.controlActionCoordinator).longPress(controlViewHolder2);
                                return true;
                            }
                        });
                    }
                    String str = controlInfo.controlId;
                    ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) controlViewHolder.controlActionCoordinator;
                    if (!controlActionCoordinatorImpl.isLocked()) {
                        ControlActionCoordinatorImpl.Action action = controlActionCoordinatorImpl.pendingAction;
                        if (Intrinsics.areEqual(action != null ? action.controlId : null, str)) {
                            ControlActionCoordinatorImpl.Action action2 = controlActionCoordinatorImpl.pendingAction;
                            Intrinsics.checkNotNull(action2);
                            controlActionCoordinatorImpl.showSettingsDialogIfNeeded(action2);
                            ControlActionCoordinatorImpl.Action action3 = controlActionCoordinatorImpl.pendingAction;
                            if (action3 != null) {
                                action3.invoke();
                            }
                            controlActionCoordinatorImpl.pendingAction = null;
                        }
                    }
                }
                boolean z4 = controlViewHolder.isLoading;
                controlViewHolder.isLoading = false;
                controlViewHolder.behavior = controlViewHolder.bindBehavior(controlViewHolder.behavior, controlViewHolder.findBehaviorClass(controlViewHolder.getControlStatus(), controlViewHolder.getControlTemplate(), controlViewHolder.getDeviceType(), controlViewHolder.getCustomControlViewHolder().layoutType), 0);
                if (z) {
                    CustomControlViewHolder customControlViewHolder2 = controlViewHolder.getCustomControlViewHolder();
                    Behavior behavior2 = controlViewHolder.behavior;
                    customControlViewHolder2.getClass();
                    CustomButtonBehavior customButtonBehavior = behavior2 instanceof CustomBehavior ? (CustomBehavior) behavior2 : 0;
                    customControlViewHolder2.customBehavior = customButtonBehavior;
                    CustomButtonBehavior customButtonBehavior2 = customButtonBehavior instanceof CustomButtonBehavior ? customButtonBehavior : null;
                    if (customButtonBehavior2 != null) {
                        CharSequence contentDescription = customButtonBehavior2.getContentDescription();
                        ActionIconView actionIconView2 = customControlViewHolder2.actionIcon;
                        if (actionIconView2 != null) {
                            if (contentDescription.length() > 0) {
                                actionIconView2.actionButtonDescription = contentDescription;
                                actionIconView2.updateContentDescription();
                            }
                        }
                        ActionIconView actionIconView3 = customControlViewHolder2.actionIcon;
                        TextView textView3 = customControlViewHolder2.subtitle;
                        TextView textView4 = customControlViewHolder2.title;
                        if (actionIconView3 != null) {
                            CharSequence text = textView4.getText();
                            actionIconView3.subTitle = textView3.getText();
                            actionIconView3.title = text;
                            actionIconView3.updateContentDescription();
                        }
                        Log.d("CustomControlViewHolder", "setCustomBehavior des = " + ((Object) contentDescription) + ", title = " + ((Object) textView4.getText()) + ", subtitle = " + ((Object) textView3.getText()));
                    }
                }
                controlViewHolder.updateContentDescription();
                if (z4 && !controlViewHolder.isLoading) {
                    z2 = true;
                }
                if (z2) {
                    ((ControlsMetricsLoggerImpl) controlViewHolder.controlsMetricsLogger).refreshEnd(controlViewHolder);
                }
            }
        }
    }

    public final void updateDimStatus(boolean z) {
        CustomControlViewHolder customControlViewHolder = this.controlViewHolder.getCustomControlViewHolder();
        customControlViewHolder.layout.setAlpha(z ? customControlViewHolder.context.getResources().getFloat(R.dimen.controls_card_dim_alpha) : 1.0f);
    }
}
