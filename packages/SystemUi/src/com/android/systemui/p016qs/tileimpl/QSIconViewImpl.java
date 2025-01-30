package com.android.systemui.p016qs.tileimpl;

import android.R;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.systemui.Dependency;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.plugins.p013qs.QSIconView;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.statusbar.ScalingDrawableWrapper;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSIconViewImpl extends QSIconView {
    public boolean isNonBGTile;
    public boolean mAnimationEnabled;
    public boolean mDisabledByPolicy;
    public final SlashImageView mIcon;
    public int mIconSizePx;
    public QSTile.Icon mLastIcon;
    public final SecQSPanelResourcePicker mResourcePicker;
    public int mState;
    public int mTint;

    public QSIconViewImpl(Context context) {
        super(context);
        this.mAnimationEnabled = true;
        this.mState = -1;
        this.isNonBGTile = false;
        this.mDisabledByPolicy = false;
        ValueAnimator valueAnimator = new ValueAnimator();
        context.getResources();
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mIconSizePx = getIconSize();
        SlashImageView slashImageView = new SlashImageView(((ViewGroup) this).mContext);
        slashImageView.setId(R.id.icon);
        slashImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        this.mIcon = slashImageView;
        addView(slashImageView);
        valueAnimator.setDuration(350L);
    }

    @Override // com.android.systemui.plugins.p013qs.QSIconView
    public final void disableAnimation() {
        this.mAnimationEnabled = false;
    }

    public final int getColor(QSTile.State state) {
        Context context = getContext();
        if (state.isNonBGTile) {
            return context.getColor(com.android.systemui.R.color.qs_tile_no_round_icon_color);
        }
        int i = state.state;
        if (i == 0) {
            return context.getColor(com.android.systemui.R.color.qs_tile_icon_on_dim_tint_color) & ((((int) ((context.getColor(com.android.systemui.R.color.qs_tile_icon_on_dim_tint_color) >>> 24) * 0.5f)) * 16777216) + 16777215);
        }
        if (i == 1) {
            return context.getColor(com.android.systemui.R.color.qs_tile_icon_off_tint_color);
        }
        if (i == 2) {
            return context.getColor(com.android.systemui.R.color.qs_tile_icon_on_dim_tint_color);
        }
        Log.e("QSTile", "Invalid state " + state);
        return 0;
    }

    public final int getIconSize() {
        if (this.isNonBGTile) {
            return ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sec_style_qs_no_bg_tile_icon_size);
        }
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = ((ViewGroup) this).mContext;
        secQSPanelResourcePicker.getClass();
        return SecQSPanelResourcePicker.getTileIconSize(context);
    }

    @Override // com.android.systemui.plugins.p013qs.QSIconView
    public final View getIconView() {
        return this.mIcon;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIconSizePx = getIconSize();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = (getMeasuredWidth() - this.mIcon.getMeasuredWidth()) / 2;
        SlashImageView slashImageView = this.mIcon;
        slashImageView.layout(measuredWidth, 0, slashImageView.getMeasuredWidth() + measuredWidth, slashImageView.getMeasuredHeight() + 0);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        this.mIconSizePx = getIconSize();
        int size = View.MeasureSpec.getSize(i);
        this.mIcon.measure(View.MeasureSpec.makeMeasureSpec(size, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS), View.MeasureSpec.makeMeasureSpec(this.mIconSizePx, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS));
        setMeasuredDimension(size, this.mIcon.getMeasuredHeight());
    }

    @Override // com.android.systemui.plugins.p013qs.QSIconView
    public final void onPanelModeChanged(QSTile.State state) {
        SlashImageView slashImageView = this.mIcon;
        if (slashImageView instanceof ImageView) {
            if (state.disabledByPolicy) {
                slashImageView.setColorFilter(getContext().getColor(com.android.systemui.R.color.qs_tile_disabled_by_policy_color), PorterDuff.Mode.SRC_IN);
                return;
            }
            slashImageView.setColorFilter(getColor(state), PorterDuff.Mode.SRC_IN);
            if (state.label == null) {
                this.mState = -1;
            }
        }
    }

    @Override // com.android.systemui.plugins.p013qs.QSIconView
    public final void setIcon(QSTile.State state, boolean z) {
        setIcon(this.mIcon, state, z);
    }

    @Override // android.view.View
    public final String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append('[');
        sb.append("state=" + this.mState);
        sb.append(", tint=" + this.mTint);
        if (this.mLastIcon != null) {
            sb.append(", lastIcon=" + this.mLastIcon.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00c3, code lost:
    
        if (r11 == false) goto L52;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r11v3, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r11v31 */
    /* JADX WARN: Type inference failed for: r11v32 */
    /* JADX WARN: Type inference failed for: r11v33 */
    /* JADX WARN: Type inference failed for: r11v34 */
    /* JADX WARN: Type inference failed for: r11v6, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r6v5, types: [com.android.systemui.qs.tileimpl.SlashImageView] */
    /* JADX WARN: Type inference failed for: r9v0, types: [android.widget.ImageView] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setIcon(ImageView imageView, QSTile.State state, boolean z) {
        boolean z2;
        boolean z3;
        boolean contains;
        QSTile.State copy = state.copy();
        this.isNonBGTile = state.isNonBGTile;
        if (state.disabledByPolicy) {
            imageView.setColorFilter(getColor(state), PorterDuff.Mode.SRC_IN);
        } else {
            int i = this.mState;
            int i2 = copy.state;
            if (i != i2 || this.mDisabledByPolicy != copy.disabledByPolicy) {
                this.mState = i2;
                imageView.setColorFilter(getColor(state), PorterDuff.Mode.SRC_IN);
            }
        }
        boolean z4 = this.mDisabledByPolicy;
        boolean z5 = copy.disabledByPolicy;
        if (z4 != z5) {
            this.mDisabledByPolicy = z5;
        }
        int i3 = copy.state;
        if (i3 != this.mState) {
            this.mState = i3;
            int color = getColor(copy);
            imageView.setImageTintList(ColorStateList.valueOf(color));
            this.mTint = color;
        }
        Supplier<QSTile.Icon> supplier = copy.iconSupplier;
        QSTile.Icon icon = supplier != null ? supplier.get() : copy.icon;
        if (Objects.equals(icon, imageView.getTag(com.android.systemui.R.id.qs_icon_tag)) && Objects.equals(copy.slash, imageView.getTag(com.android.systemui.R.id.qs_slash_tag))) {
            return;
        }
        if (z) {
            z2 = true;
            if (this.mAnimationEnabled && imageView.isShown() && imageView.getDrawable() != null) {
                Supplier<QSTile.Icon> supplier2 = copy.iconSupplier;
                if ((supplier2 != null ? supplier2.toString() : "").contains("CustomTile")) {
                    String str = copy.tileClassName;
                    String string = ((ViewGroup) this).mContext.getResources().getString(com.android.systemui.R.string.quick_settings_custom_tile_component_names);
                    if ("NearbyShare".equals(str)) {
                        contains = false;
                    } else {
                        contains = string.contains(str != null ? str : "");
                    }
                    z3 = !contains;
                } else {
                    z3 = false;
                }
            }
        }
        z2 = false;
        this.mLastIcon = icon;
        ?? drawable = icon != null ? z2 ? icon.getDrawable(((ViewGroup) this).mContext) : icon.getInvisibleDrawable(((ViewGroup) this).mContext) : 0;
        int padding = icon != null ? icon.getPadding() : 0;
        if (drawable != 0) {
            Drawable.ConstantState constantState = drawable.getConstantState();
            drawable = drawable;
            if (constantState != null) {
                drawable = drawable.getConstantState().newDrawable();
            }
            drawable.setAutoMirrored(false);
            drawable.setLayoutDirection(getLayoutDirection());
        }
        if ((drawable instanceof ScalingDrawableWrapper) || copy.isNonBGTile) {
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            if (copy.isNonBGTile) {
                setFocusable(false);
            }
        }
        if (imageView instanceof SlashImageView) {
            ?? r6 = (SlashImageView) imageView;
            r6.mAnimationEnabled = z2;
            r6.setState(drawable);
        } else {
            imageView.setImageDrawable(drawable);
        }
        imageView.setTag(com.android.systemui.R.id.qs_icon_tag, icon);
        imageView.setTag(com.android.systemui.R.id.qs_slash_tag, copy.slash);
        imageView.setPadding(0, padding, 0, padding);
        if (drawable instanceof Animatable2) {
            final Animatable2 animatable2 = (Animatable2) drawable;
            animatable2.start();
            if (!z2) {
                animatable2.stop();
                return;
            } else {
                if (copy.isTransient) {
                    animatable2.registerAnimationCallback(new Animatable2.AnimationCallback(this) { // from class: com.android.systemui.qs.tileimpl.QSIconViewImpl.1
                        @Override // android.graphics.drawable.Animatable2.AnimationCallback
                        public final void onAnimationEnd(Drawable drawable2) {
                            animatable2.start();
                        }
                    });
                    return;
                }
                return;
            }
        }
        if ((drawable instanceof AnimationDrawable) && imageView.isShown()) {
            Animatable animatable = (Animatable) drawable;
            animatable.start();
            if (imageView.isShown()) {
                return;
            }
            animatable.stop();
        }
    }
}
