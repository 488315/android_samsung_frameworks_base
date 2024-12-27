package com.android.systemui.controls.management.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.SecControlInterface;
import com.android.systemui.controls.ui.CanUseIconPredicate;
import com.android.systemui.controls.util.ControlsUtil;
import kotlin.jvm.functions.Function2;

public abstract class SecControlCommonHolder extends SecHolder {
    public final CanUseIconPredicate canUseIconPredicate;
    public final ControlsUtil controlsUtil;
    public final CheckBox favorite;
    public final Function2 favoriteCallback;
    public final ImageView icon;
    public final ImageView overlayCustomIcon;
    public final TextView removed;
    public final TextView title;

    public SecControlCommonHolder(View view, int i, int i2, ControlsUtil controlsUtil, Function2 function2) {
        super(view, null);
        this.controlsUtil = controlsUtil;
        this.favoriteCallback = function2;
        ImageView imageView = (ImageView) this.itemView.requireViewById(R.id.icon);
        this.icon = imageView;
        TextView textView = (TextView) this.itemView.requireViewById(R.id.title);
        ControlsUtil.Companion.getClass();
        ControlsUtil.Companion.updateFontSize(textView, R.dimen.sec_control_text_size, 1.1f);
        this.title = textView;
        TextView textView2 = (TextView) this.itemView.requireViewById(R.id.status);
        ControlsUtil.Companion.updateFontSize(textView2, R.dimen.sec_control_text_size, 1.1f);
        this.removed = textView2;
        this.canUseIconPredicate = new CanUseIconPredicate(i);
        ViewStub viewStub = (ViewStub) this.itemView.requireViewById(i2);
        viewStub.setLayoutResource(R.layout.controls_checkbox);
        this.favorite = (CheckBox) viewStub.inflate();
        ImageView imageView2 = (ImageView) this.itemView.requireViewById(R.id.overlay_custom_icon);
        this.overlayCustomIcon = imageView2;
        if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
            Context context = this.itemView.getContext();
            controlsUtil.getClass();
            if (ControlsUtil.isFoldDelta(context)) {
                Resources resources = view.getResources();
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_base_item_size_fold);
                ControlsUtil.Companion.setSize(view, dimensionPixelSize, dimensionPixelSize);
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.control_icon_size_fold);
                ControlsUtil.Companion.setSize(imageView, dimensionPixelSize2, dimensionPixelSize2);
                if (imageView2 != null) {
                    ControlsUtil.Companion.setSize(imageView2, dimensionPixelSize2, dimensionPixelSize2);
                }
                float dimension = resources.getDimension(R.dimen.control_text_size_fold);
                textView.setTextSize(0, dimension);
                textView2.setTextSize(0, dimension);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0156  */
    @Override // com.android.systemui.controls.management.adapter.SecHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bindData(com.android.systemui.controls.management.model.SecElementWrapper r12) {
        /*
            Method dump skipped, instructions count: 372
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.management.adapter.SecControlCommonHolder.bindData(com.android.systemui.controls.management.model.SecElementWrapper):void");
    }

    public void resetForReuse() {
        ImageView imageView = this.icon;
        Resources resources = this.itemView.getContext().getResources();
        this.controlsUtil.getClass();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_icon_padding_size);
        imageView.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
    }

    public void setContentDescription(CheckBox checkBox, TextView textView) {
        textView.setImportantForAccessibility(2);
        checkBox.setContentDescription(String.valueOf(textView.getText()));
    }

    @Override // com.android.systemui.controls.management.adapter.SecHolder
    public final void updateFavorite(boolean z) {
        this.favorite.setChecked(z);
    }

    public static /* synthetic */ void getIcon$annotations() {
    }

    public static /* synthetic */ void getOverlayCustomIcon$annotations() {
    }

    public static /* synthetic */ void getRemoved$annotations() {
    }

    public static /* synthetic */ void getTitle$annotations() {
    }

    public void setSubtitleText(CharSequence charSequence) {
    }

    public void updateLottieIcon(SecControlInterface secControlInterface) {
    }
}
