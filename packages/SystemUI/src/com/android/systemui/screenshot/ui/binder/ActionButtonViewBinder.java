package com.android.systemui.screenshot.ui.binder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.screenshot.ui.TransitioningIconDrawable;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonViewModel;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActionButtonViewBinder {
    public static void bind(View view, final ActionButtonViewModel actionButtonViewModel) {
        ImageView imageView = (ImageView) view.requireViewById(R.id.overlay_action_chip_icon);
        TextView textView = (TextView) view.requireViewById(R.id.overlay_action_chip_text);
        if (imageView.getDrawable() == null) {
            imageView.setImageDrawable(new TransitioningIconDrawable());
        }
        Drawable drawable = imageView.getDrawable();
        TransitioningIconDrawable transitioningIconDrawable = drawable instanceof TransitioningIconDrawable ? (TransitioningIconDrawable) drawable : null;
        ActionButtonAppearance actionButtonAppearance = actionButtonViewModel.appearance;
        if (transitioningIconDrawable != null) {
            Drawable drawable2 = actionButtonAppearance.icon;
            if (!Objects.equals(transitioningIconDrawable.drawable, drawable2) || transitioningIconDrawable.transitionAnimator.isRunning()) {
                if (drawable2 != null) {
                    drawable2.setColorFilter(transitioningIconDrawable.colorFilter);
                }
                if (drawable2 != null) {
                    drawable2.setTintList(transitioningIconDrawable.tint);
                }
                if (transitioningIconDrawable.drawable == null) {
                    transitioningIconDrawable.drawable = drawable2;
                    transitioningIconDrawable.invalidateSelf();
                } else if (transitioningIconDrawable.enteringDrawable != null) {
                    transitioningIconDrawable.enteringDrawable = drawable2;
                } else {
                    transitioningIconDrawable.enteringDrawable = drawable2;
                    transitioningIconDrawable.transitionAnimator.setCurrentFraction(0.0f);
                    transitioningIconDrawable.transitionAnimator.start();
                    transitioningIconDrawable.invalidateSelf();
                }
            }
        }
        imageView.setImageDrawable(actionButtonAppearance.icon);
        if (!actionButtonAppearance.tint) {
            imageView.setImageTintList(null);
        }
        textView.setText(actionButtonAppearance.label);
        Drawable drawable3 = actionButtonAppearance.customBackground;
        if (drawable3 != null) {
            if (drawable3.canApplyTheme()) {
                drawable3.applyTheme(view.getRootView().getContext().getTheme());
            }
            view.setBackground(drawable3);
        }
        CharSequence charSequence = actionButtonAppearance.label;
        boolean z = charSequence != null && charSequence.length() > 0;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) textView.getLayoutParams();
        if (z) {
            layoutParams.setMarginStart(imageView.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_padding_start));
            layoutParams.setMarginEnd(imageView.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_spacing));
            layoutParams2.setMarginStart(0);
            layoutParams2.setMarginEnd(textView.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_padding_end));
        } else {
            int dimensionPixelSize = imageView.getResources().getDimensionPixelSize(R.dimen.overlay_action_chip_icon_only_padding_horizontal);
            layoutParams.setMarginStart(dimensionPixelSize);
            layoutParams.setMarginEnd(dimensionPixelSize);
        }
        imageView.setLayoutParams(layoutParams);
        textView.setLayoutParams(layoutParams2);
        if (actionButtonViewModel.onClicked != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ui.binder.ActionButtonViewBinder$bind$2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    ActionButtonViewModel.this.onClicked.invoke();
                }
            });
        } else {
            view.setOnClickListener(null);
        }
        view.setTag(Integer.valueOf(actionButtonViewModel.id));
        view.setContentDescription(actionButtonAppearance.description);
        view.setVisibility(0);
        view.setAlpha(1.0f);
    }
}
