package com.android.systemui.controls.management.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewStub;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.ControlInterface;
import com.android.systemui.controls.SecControlInterface;
import com.android.systemui.controls.management.model.SecElementWrapper;
import com.android.systemui.controls.ui.CanUseIconPredicate;
import com.android.systemui.controls.ui.RenderInfo;
import com.android.systemui.controls.util.ControlsUtil;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public abstract class SecControlCommonHolder extends SecHolder {
    public final CanUseIconPredicate canUseIconPredicate;
    public final ControlsUtil controlsUtil;
    public final CheckBox favorite;
    public final Function2 favoriteCallback;
    public final ImageView icon;
    public final ImageView overlayCustomIcon;
    public final TextView removed;
    public final TextView title;

    public SecControlCommonHolder(View view, int i, int i2, ControlsUtil controlsUtil, Function2 function2) throws Resources.NotFoundException {
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
    /* JADX WARN: Removed duplicated region for block: B:30:0x00f7  */
    @Override // com.android.systemui.controls.management.adapter.SecHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bindData(SecElementWrapper secElementWrapper) throws Resources.NotFoundException {
        resetForReuse();
        final ControlInterface controlInterface = (ControlInterface) secElementWrapper;
        SecControlInterface secControlInterface = (SecControlInterface) controlInterface;
        this.title.setText(controlInterface.getTitle());
        setSubtitleText(controlInterface.getSubtitle());
        updateFavorite(controlInterface.getFavorite());
        this.removed.setText(controlInterface.getRemoved() ? this.itemView.getContext().getText(R.string.sec_controls_removed) : "");
        setContentDescription(this.favorite, this.title);
        this.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.SecControlCommonHolder.bindData.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SecControlCommonHolder.this.updateFavorite(!r2.favorite.isChecked());
                SecControlCommonHolder.this.favoriteCallback.invoke(controlInterface.getControlId(), Boolean.valueOf(SecControlCommonHolder.this.favorite.isChecked()));
            }
        });
        ComponentName component = controlInterface.getComponent();
        int deviceType = controlInterface.getDeviceType();
        RenderInfo.Companion companion = RenderInfo.Companion;
        Context context = this.itemView.getContext();
        companion.getClass();
        RenderInfo renderInfoLookup = RenderInfo.Companion.lookup(context, component, deviceType, 0);
        Context context2 = this.itemView.getContext();
        this.icon.setImageTintList(null);
        Icon customIcon = controlInterface.getCustomIcon();
        ControlsUtil controlsUtil = this.controlsUtil;
        if (customIcon == null) {
            ImageView imageView = this.icon;
            imageView.setImageDrawable(renderInfoLookup.icon);
            if (controlInterface.getDeviceType() != 52) {
                imageView.setImageTintList(context2.getResources().getColorStateList(renderInfoLookup.foreground, context2.getTheme()));
            }
            imageView.setBackground(null);
        } else {
            if (!((Boolean) this.canUseIconPredicate.mo781invoke(customIcon)).booleanValue()) {
                customIcon = null;
            }
            if (customIcon != null) {
                this.icon.setImageIcon(customIcon);
                if (secControlInterface.getIconWithoutPadding()) {
                    this.icon.setPadding(0, 0, 0, 0);
                }
                if (!secControlInterface.getIconWithoutShadowBg() && (context2.getResources().getConfiguration().uiMode & 48) == 16) {
                    Drawable drawable = context2.getResources().getDrawable(R.drawable.control_icon_shadow_bg, context2.getTheme());
                    if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
                        controlsUtil.getClass();
                        if (ControlsUtil.isFoldDelta(context2)) {
                            int dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.control_icon_size_fold);
                            BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                            if (bitmapDrawable != null) {
                                drawable = new BitmapDrawable(context2.getResources(), Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(), dimensionPixelSize, dimensionPixelSize, true));
                            }
                        }
                    }
                    this.icon.setBackground(drawable);
                }
            }
        }
        context2.getClass();
        LayerDrawable layerDrawable = (LayerDrawable) this.itemView.getBackground();
        controlsUtil.getClass();
        layerDrawable.mutate();
        ((GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.background)).setColor(context2.getResources().getColor(R.color.control_favorite_default_background, context2.getTheme()));
        updateLottieIcon(secControlInterface);
        Icon overlayCustomIcon = secControlInterface.getOverlayCustomIcon();
        if (overlayCustomIcon == null) {
            ImageView imageView2 = this.overlayCustomIcon;
            if (imageView2 != null) {
                imageView2.setVisibility(8);
                return;
            }
            return;
        }
        ImageView imageView3 = this.overlayCustomIcon;
        if (imageView3 != null) {
            imageView3.setImageIcon(overlayCustomIcon);
        }
        ImageView imageView4 = this.overlayCustomIcon;
        if (imageView4 != null) {
            imageView4.setVisibility(0);
        }
    }

    public void resetForReuse() throws Resources.NotFoundException {
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

    public void setSubtitleText(CharSequence charSequence) {
    }

    public void updateLottieIcon(SecControlInterface secControlInterface) {
    }

    public static /* synthetic */ void getIcon$annotations() {
    }

    public static /* synthetic */ void getOverlayCustomIcon$annotations() {
    }

    public static /* synthetic */ void getRemoved$annotations() {
    }

    public static /* synthetic */ void getTitle$annotations() {
    }
}
