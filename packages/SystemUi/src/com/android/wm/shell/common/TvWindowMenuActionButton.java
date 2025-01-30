package com.android.wm.shell.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TvWindowMenuActionButton extends RelativeLayout {
    public final View mButtonBackgroundView;
    public Icon mCurrentIcon;
    public final ImageView mIconImageView;

    public TvWindowMenuActionButton(Context context) {
        this(context, null, 0, 0);
    }

    public final void setImageIconAsync(final Icon icon, Handler handler) {
        this.mCurrentIcon = icon;
        this.mIconImageView.setImageDrawable(null);
        icon.loadDrawableAsync(((RelativeLayout) this).mContext, new Icon.OnDrawableLoadedListener() { // from class: com.android.wm.shell.common.TvWindowMenuActionButton$$ExternalSyntheticLambda0
            @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
            public final void onDrawableLoaded(Drawable drawable) {
                TvWindowMenuActionButton tvWindowMenuActionButton = TvWindowMenuActionButton.this;
                Icon icon2 = icon;
                if (tvWindowMenuActionButton.mIconImageView.getDrawable() == null && tvWindowMenuActionButton.mCurrentIcon == icon2) {
                    tvWindowMenuActionButton.mIconImageView.setImageDrawable(drawable);
                }
            }
        }, handler);
    }

    public final void setIsCustomCloseAction(boolean z) {
        this.mIconImageView.setImageTintList(getResources().getColorStateList(z ? R.color.tv_window_menu_close_icon : R.color.tv_window_menu_icon));
        this.mButtonBackgroundView.setBackgroundTintList(getResources().getColorStateList(z ? R.color.tv_window_menu_close_icon_bg : R.color.tv_window_menu_icon_bg));
    }

    @Override // android.view.View
    public final String toString() {
        return getContentDescription() == null ? "TvWindowMenuActionButton" : getContentDescription().toString();
    }

    public TvWindowMenuActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public TvWindowMenuActionButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TvWindowMenuActionButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.tv_window_menu_action_button, this);
        ImageView imageView = (ImageView) findViewById(R.id.icon);
        this.mIconImageView = imageView;
        this.mButtonBackgroundView = findViewById(R.id.background);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{android.R.attr.src, android.R.attr.text}, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 != 0) {
            setContentDescription(getContext().getString(resourceId2));
        }
        obtainStyledAttributes.recycle();
        setIsCustomCloseAction(false);
    }
}
