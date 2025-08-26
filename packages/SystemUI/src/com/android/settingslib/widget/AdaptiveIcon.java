package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class AdaptiveIcon extends LayerDrawable {
    public final AdaptiveConstantState mAdaptiveConstantState;
    int mBackgroundColor;

    class AdaptiveConstantState extends Drawable.ConstantState {
        public int mColor;
        public final Context mContext;
        public final Drawable mDrawable;

        public AdaptiveConstantState(Context context, Drawable drawable) {
            this.mContext = context;
            this.mDrawable = drawable;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final int getChangingConfigurations() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public final Drawable newDrawable() {
            AdaptiveIcon adaptiveIcon = new AdaptiveIcon(this.mContext, this.mDrawable);
            adaptiveIcon.setBackgroundColor(this.mColor);
            return adaptiveIcon;
        }
    }

    public AdaptiveIcon(Context context, Drawable drawable) {
        this(context, drawable, R.dimen.dashboard_tile_foreground_image_inset);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        return this.mAdaptiveConstantState;
    }

    public final void setBackgroundColor(int i) {
        this.mBackgroundColor = i;
        getDrawable(0).setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
        RecyclerView$$ExternalSyntheticOutline0.m(this.mBackgroundColor, "AdaptiveHomepageIcon", new StringBuilder("Setting background color "));
        this.mAdaptiveConstantState.mColor = i;
    }

    public AdaptiveIcon(Context context, Drawable drawable, int i) throws Resources.NotFoundException {
        super(new Drawable[]{new AdaptiveIconShapeDrawable(context.getResources()), drawable});
        this.mBackgroundColor = -1;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(i);
        setLayerInset(1, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        this.mAdaptiveConstantState = new AdaptiveConstantState(context, drawable);
    }
}
