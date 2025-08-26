package com.android.systemui.controls.management.adapter;

import android.content.res.Resources;
import android.service.controls.Control;
import android.service.controls.templates.ControlTemplate;
import android.service.controls.templates.StatelessTemplate;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.management.model.MainControlModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.Behavior;
import com.android.systemui.controls.ui.ControlActionCoordinatorImpl;
import com.android.systemui.controls.ui.ControlViewHolder;
import com.android.systemui.controls.ui.ControlWithState;
import com.android.systemui.controls.ui.SecActionButtonBehavior;
import com.android.systemui.controls.ui.SecBehavior;
import com.android.systemui.controls.ui.SecControlViewHolder;
import com.android.systemui.controls.ui.TouchBehavior;
import com.android.systemui.controls.ui.view.ControlsActionButton;
import com.android.systemui.controls.util.ControlsUtil;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class ControlHolder extends Holder {
    public final ControlViewHolder controlViewHolder;
    public final Map holders;

    public ControlHolder(View view, ControlViewHolder controlViewHolder, Map<String, ControlViewHolder> map) {
        super(view, null);
        this.controlViewHolder = controlViewHolder;
        this.holders = map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008d  */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12, types: [com.android.systemui.controls.ui.SecBehavior] */
    /* JADX WARN: Type inference failed for: r3v33 */
    @Override // com.android.systemui.controls.management.adapter.Holder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bindData(MainModel mainModel) throws Resources.NotFoundException {
        ControlWithState controlWithState;
        if ((mainModel instanceof MainControlModel) && (controlWithState = ((MainControlModel) mainModel).controlWithState) != null) {
            ControlViewHolder controlViewHolder = this.controlViewHolder;
            SecControlViewHolder secControlViewHolder = controlViewHolder.getSecControlViewHolder();
            Behavior behavior = controlViewHolder.behavior;
            ImageView imageView = secControlViewHolder.icon;
            if (secControlViewHolder.controlsUtil != null) {
                Resources resources = imageView.getContext().getResources();
                ControlsUtil.Companion companion = ControlsUtil.Companion;
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_icon_padding_size);
                imageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
            }
            imageView.setVisibility(0);
            imageView.setBackground(null);
            imageView.setImageDrawable(null);
            imageView.setImageState(new int[0], false);
            imageView.setImageTintList(null);
            imageView.setAlpha(1.0f);
            ControlsActionButton controlsActionButton = secControlViewHolder.actionIcon;
            if (controlsActionButton != null) {
                ImageView imageView2 = controlsActionButton.actionIcon;
                if (imageView2 != null) {
                    imageView2.setImageDrawable(null);
                }
                ImageView imageView3 = controlsActionButton.actionIcon;
                if (imageView3 != null) {
                    imageView3.setVisibility(8);
                }
                ProgressBar progressBar = controlsActionButton.actionIconProgress;
                if (progressBar != null) {
                    progressBar.setVisibility(8);
                }
            }
            ImageView imageView4 = secControlViewHolder.statusIcon;
            if (imageView4 != null) {
                imageView4.setImageDrawable(null);
            }
            LottieAnimationView lottieAnimationView = secControlViewHolder.animationView;
            if (lottieAnimationView != null) {
                lottieAnimationView.setVisibility(8);
                lottieAnimationView.cancelAnimation();
            }
            ImageView imageView5 = secControlViewHolder.overlayCustomIcon;
            if (imageView5 != null) {
                imageView5.setVisibility(8);
                imageView5.setImageDrawable(null);
            }
            if (behavior instanceof TouchBehavior) {
                ControlTemplate controlTemplate = ((TouchBehavior) behavior).template;
                if (controlTemplate == null) {
                    controlTemplate = null;
                }
                if (!(controlTemplate instanceof StatelessTemplate)) {
                }
            } else {
                SecBehavior secBehavior = secControlViewHolder.secBehavior;
                if (secBehavior != null) {
                    secBehavior.dispose();
                    secControlViewHolder.secBehavior = null;
                }
                ViewGroup viewGroup = secControlViewHolder.layout;
                viewGroup.setOnClickListener(null);
                viewGroup.setOnTouchListener(null);
                behavior = null;
            }
            controlViewHolder.behavior = behavior;
            controlViewHolder.cws = controlWithState;
            int controlStatus = controlViewHolder.getControlStatus();
            ControlInfo controlInfo = controlWithState.ci;
            if (controlStatus == 0 || controlViewHolder.getControlStatus() == 2) {
                controlViewHolder.title.setText(controlInfo.controlTitle);
                controlViewHolder.subtitle.setText(controlInfo.controlSubtitle);
            } else {
                Control control = controlWithState.control;
                if (control != null) {
                    controlViewHolder.title.setText(control.getTitle());
                    controlViewHolder.subtitle.setText(control.getSubtitle());
                }
            }
            if (controlWithState.control != null) {
                controlViewHolder.layout.setClickable(true);
                String str = controlInfo.controlId;
                ControlActionCoordinatorImpl controlActionCoordinatorImpl = (ControlActionCoordinatorImpl) controlViewHolder.controlActionCoordinator;
                if (!controlActionCoordinatorImpl.isLocked()) {
                    ControlActionCoordinatorImpl.Action action = controlActionCoordinatorImpl.pendingAction;
                    if (Intrinsics.areEqual(action != null ? action.controlId : null, str)) {
                        ControlActionCoordinatorImpl.Action action2 = controlActionCoordinatorImpl.pendingAction;
                        if (action2 != null) {
                            action2.invoke();
                        }
                        controlActionCoordinatorImpl.pendingAction = null;
                    }
                }
            }
            boolean z = controlViewHolder.isLoading;
            controlViewHolder.isLoading = false;
            controlViewHolder.behavior = controlViewHolder.bindBehavior(controlViewHolder.behavior, ControlViewHolder.findBehaviorClass$default(controlViewHolder, controlViewHolder.getControlStatus(), controlViewHolder.getControlTemplate(), controlViewHolder.getDeviceType()), 0);
            SecControlViewHolder secControlViewHolder2 = controlViewHolder.getSecControlViewHolder();
            Behavior behavior2 = controlViewHolder.behavior;
            secControlViewHolder2.getClass();
            SecActionButtonBehavior secActionButtonBehavior = behavior2 instanceof SecBehavior ? (SecBehavior) behavior2 : 0;
            secControlViewHolder2.secBehavior = secActionButtonBehavior;
            SecActionButtonBehavior secActionButtonBehavior2 = secActionButtonBehavior instanceof SecActionButtonBehavior ? secActionButtonBehavior : null;
            if (secActionButtonBehavior2 != null) {
                CharSequence contentDescription = secActionButtonBehavior2.getContentDescription();
                ControlsActionButton controlsActionButton2 = secControlViewHolder2.actionIcon;
                if (controlsActionButton2 != null && contentDescription.length() > 0) {
                    controlsActionButton2.actionButtonDescription = contentDescription;
                    controlsActionButton2.updateContentDescription();
                }
                ControlsActionButton controlsActionButton3 = secControlViewHolder2.actionIcon;
                if (controlsActionButton3 != null) {
                    CharSequence text = secControlViewHolder2.title.getText();
                    controlsActionButton3.subTitle = secControlViewHolder2.subtitle.getText();
                    controlsActionButton3.title = text;
                    controlsActionButton3.updateContentDescription();
                }
                Log.d("SecControlViewHolder", "setSecBehavior des = " + ((Object) contentDescription) + ", title = " + ((Object) secControlViewHolder2.title.getText()) + ", subtitle = " + ((Object) secControlViewHolder2.subtitle.getText()));
            }
            controlViewHolder.updateContentDescription();
            if (z && !controlViewHolder.isLoading) {
                controlViewHolder.controlsMetricsLogger.refreshEnd(controlViewHolder);
            }
        }
    }
}
