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
import com.android.systemui.controls.CustomControlInterface;
import com.android.systemui.controls.management.model.CustomElementWrapper;
import com.android.systemui.controls.p005ui.CanUseIconPredicate;
import com.android.systemui.controls.p005ui.RenderInfo;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import com.android.systemui.controls.util.ControlsRuneWrapperImpl;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ControlCommonCustomHolder extends CustomHolder {
    public final CanUseIconPredicate canUseIconPredicate;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public final CheckBox favorite;
    public final Function2 favoriteCallback;
    public final ImageView icon;
    public final ImageView overlayCustomIcon;
    public final TextView removed;
    public final TextView title;

    public ControlCommonCustomHolder(View view, int i, int i2, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper, Function2 function2) {
        super(view, null);
        ImageView imageView;
        this.controlsUtil = controlsUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        this.favoriteCallback = function2;
        ImageView imageView2 = (ImageView) this.itemView.requireViewById(R.id.icon);
        this.icon = imageView2;
        TextView textView = (TextView) this.itemView.requireViewById(R.id.title);
        ControlsUtil.Companion.getClass();
        ControlsUtil.Companion.updateFontSize(textView, R.dimen.control_custom_text_size, 1.1f);
        this.title = textView;
        TextView textView2 = (TextView) this.itemView.requireViewById(R.id.status);
        ControlsUtil.Companion.updateFontSize(textView2, R.dimen.control_custom_text_size, 1.1f);
        this.removed = textView2;
        this.canUseIconPredicate = new CanUseIconPredicate(i);
        ViewStub viewStub = (ViewStub) this.itemView.requireViewById(i2);
        viewStub.setLayoutResource(R.layout.controls_checkbox_view);
        this.favorite = (CheckBox) viewStub.inflate();
        boolean z = BasicRune.CONTROLS_OVERLAY_CUSTOM_ICON;
        if (z) {
            this.overlayCustomIcon = (ImageView) this.itemView.requireViewById(R.id.overlay_custom_icon);
        }
        if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
            Context context = this.itemView.getContext();
            controlsUtil.getClass();
            if (ControlsUtil.isFoldDelta(context)) {
                Resources resources = view.getResources();
                int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_custom_base_item_size_fold);
                ControlsUtil.Companion.setSize(view, dimensionPixelSize, dimensionPixelSize);
                int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.control_custom_icon_size_fold);
                ControlsUtil.Companion.setSize(imageView2, dimensionPixelSize2, dimensionPixelSize2);
                if (z && (imageView = this.overlayCustomIcon) != null) {
                    ControlsUtil.Companion.setSize(imageView, dimensionPixelSize2, dimensionPixelSize2);
                }
                float dimension = resources.getDimension(R.dimen.control_custom_text_size_fold);
                textView.setTextSize(0, dimension);
                textView2.setTextSize(0, dimension);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0100  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:57:? A[RETURN, SYNTHETIC] */
    @Override // com.android.systemui.controls.management.adapter.CustomHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void bindData(CustomElementWrapper customElementWrapper) {
        Unit unit;
        resetForReuse();
        final ControlInterface controlInterface = (ControlInterface) customElementWrapper;
        CustomControlInterface customControlInterface = (CustomControlInterface) customElementWrapper;
        CharSequence title = controlInterface.getTitle();
        TextView textView = this.title;
        textView.setText(title);
        setSubtitleText(controlInterface.getSubtitle());
        updateFavorite(controlInterface.getFavorite());
        boolean removed = controlInterface.getRemoved();
        View view = this.itemView;
        this.removed.setText(removed ? view.getContext().getText(R.string.controls_custom_removed) : "");
        setContentDescription(this.favorite, textView);
        view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.adapter.ControlCommonCustomHolder$bindData$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ControlCommonCustomHolder.this.updateFavorite(!r2.favorite.isChecked());
                ControlCommonCustomHolder.this.favoriteCallback.invoke(controlInterface.getControlId(), Boolean.valueOf(ControlCommonCustomHolder.this.favorite.isChecked()));
            }
        });
        ComponentName component = controlInterface.getComponent();
        int deviceType = controlInterface.getDeviceType();
        RenderInfo.Companion companion = RenderInfo.Companion;
        Context context = view.getContext();
        companion.getClass();
        RenderInfo lookup = RenderInfo.Companion.lookup(context, component, deviceType, 0);
        Context context2 = view.getContext();
        ImageView imageView = this.icon;
        Unit unit2 = null;
        imageView.setImageTintList(null);
        Icon customIcon = controlInterface.getCustomIcon();
        ControlsRuneWrapper controlsRuneWrapper = this.controlsRuneWrapper;
        ControlsUtil controlsUtil = this.controlsUtil;
        if (customIcon != null) {
            if (!((Boolean) this.canUseIconPredicate.invoke(customIcon)).booleanValue()) {
                customIcon = null;
            }
            if (customIcon != null) {
                imageView.setImageIcon(customIcon);
                if (BasicRune.CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING && customControlInterface.getUseCustomIconWithoutPadding()) {
                    imageView.setPadding(0, 0, 0, 0);
                }
                if (BasicRune.CONTROLS_USE_CUSTOM_ICON_WITHOUT_SHADOW_BG && !customControlInterface.getUseCustomIconWithoutShadowBg() && (context2.getResources().getConfiguration().uiMode & 48) == 16) {
                    Drawable drawable = context2.getResources().getDrawable(R.drawable.custom_icon_shadow_background, context2.getTheme());
                    ((ControlsRuneWrapperImpl) controlsRuneWrapper).getClass();
                    if (BasicRune.CONTROLS_SAMSUNG_STYLE_FOLD) {
                        controlsUtil.getClass();
                        if (ControlsUtil.isFoldDelta(context2)) {
                            int dimensionPixelSize = context2.getResources().getDimensionPixelSize(R.dimen.control_custom_icon_size_fold);
                            BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
                            if (bitmapDrawable != null) {
                                drawable = new BitmapDrawable(context2.getResources(), Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(), dimensionPixelSize, dimensionPixelSize, true));
                            }
                        }
                    }
                    imageView.setBackground(drawable);
                }
                unit = Unit.INSTANCE;
                if (unit == null) {
                    imageView.setImageDrawable(lookup.icon);
                    if (controlInterface.getDeviceType() != 52) {
                        imageView.setImageTintList(context2.getResources().getColorStateList(lookup.foreground, context2.getTheme()));
                    }
                    imageView.setBackground(null);
                }
                LayerDrawable layerDrawable = (LayerDrawable) view.getBackground();
                customControlInterface.getCustomColor();
                controlsUtil.getClass();
                layerDrawable.mutate();
                ((GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.background)).setColor(context2.getResources().getColor(R.color.control_favorite_default_background, context2.getTheme()));
                if (BasicRune.CONTROLS_LOTTIE_ICON_ANIMATION) {
                    updateLottieIcon(customControlInterface);
                }
                ((ControlsRuneWrapperImpl) controlsRuneWrapper).getClass();
                if (BasicRune.CONTROLS_OVERLAY_CUSTOM_ICON) {
                    return;
                }
                Icon overlayCustomIcon = customControlInterface.getOverlayCustomIcon();
                ImageView imageView2 = this.overlayCustomIcon;
                if (overlayCustomIcon != null) {
                    if (imageView2 != null) {
                        imageView2.setImageIcon(overlayCustomIcon);
                    }
                    if (imageView2 != null) {
                        imageView2.setVisibility(0);
                    }
                    unit2 = Unit.INSTANCE;
                }
                if (unit2 != null || imageView2 == null) {
                    return;
                }
                imageView2.setVisibility(8);
                return;
            }
        }
        unit = null;
        if (unit == null) {
        }
        LayerDrawable layerDrawable2 = (LayerDrawable) view.getBackground();
        customControlInterface.getCustomColor();
        controlsUtil.getClass();
        layerDrawable2.mutate();
        ((GradientDrawable) layerDrawable2.findDrawableByLayerId(R.id.background)).setColor(context2.getResources().getColor(R.color.control_favorite_default_background, context2.getTheme()));
        if (BasicRune.CONTROLS_LOTTIE_ICON_ANIMATION) {
        }
        ((ControlsRuneWrapperImpl) controlsRuneWrapper).getClass();
        if (BasicRune.CONTROLS_OVERLAY_CUSTOM_ICON) {
        }
    }

    public void resetForReuse() {
        if (BasicRune.CONTROLS_USE_CUSTOM_ICON_WITHOUT_PADDING) {
            Resources resources = this.itemView.getContext().getResources();
            this.controlsUtil.getClass();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.control_custom_icon_padding_size);
            this.icon.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        }
    }

    public void setContentDescription(CheckBox checkBox, TextView textView) {
        textView.setImportantForAccessibility(2);
        checkBox.setContentDescription(String.valueOf(textView.getText()));
    }

    @Override // com.android.systemui.controls.management.adapter.CustomHolder
    public final void updateFavorite(boolean z) {
        this.favorite.setChecked(z);
    }

    public void setSubtitleText(CharSequence charSequence) {
    }

    public void updateLottieIcon(CustomControlInterface customControlInterface) {
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
