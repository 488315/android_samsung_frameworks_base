package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.systemui.navigationbar.NavBarButtonDrawableProvider;
import com.android.systemui.navigationbar.buttons.ButtonInterface;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawableProvider;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.samsung.systemui.splugins.navigationbar.IconResource;
import com.samsung.systemui.splugins.navigationbar.IconType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavigationHintHandle extends FrameLayout implements ButtonInterface {
    public final float darkIntensity;
    public GestureHintDrawable hintDrawable;
    public NavBarIconResourceMapper iconResourceMapper;
    public final Context mContext;
    public GestureHintDrawable viDrawable;

    /* JADX WARN: Multi-variable type inference failed */
    public NavigationHintHandle(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void setCurrentRotation(int i, boolean z) {
        View childAt;
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        if (navBarIconResourceMapper != null) {
            IconResource iconResource = navBarIconResourceMapper.getIconResource(IconType.TYPE_GESTURE_HINT);
            int i2 = z ? i : 0;
            KeyButtonDrawableProvider keyButtonDrawableProvider = navBarIconResourceMapper.buttonDrawableProvider;
            Context context = navBarIconResourceMapper.context;
            this.hintDrawable = ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getGestureHintDrawable(context, iconResource, i2);
            this.viDrawable = ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getGestureHintDrawable(context, navBarIconResourceMapper.getIconResource(IconType.TYPE_GESTURE_HINT_VI), z ? i : 0);
        }
        int dimension = (int) this.mContext.getResources().getDimension(R.dimen.samsung_hint_bottom_padding);
        GestureHintDrawable gestureHintDrawable = this.hintDrawable;
        GestureHintDrawable gestureHintDrawable2 = this.viDrawable;
        if (gestureHintDrawable != null && gestureHintDrawable2 != null) {
            for (int i3 = 0; i3 < 2; i3++) {
                if (!z || i == 0 || i == 2) {
                    gestureHintDrawable.setLayerGravity(i3, 80);
                    int i4 = i3;
                    gestureHintDrawable.setLayerInset(i4, 0, dimension, 0, dimension);
                    gestureHintDrawable2.setLayerGravity(i3, 80);
                    gestureHintDrawable2.setLayerInset(i4, 0, dimension, 0, dimension);
                } else if (i == 1) {
                    gestureHintDrawable.setLayerGravity(i3, 5);
                    int i5 = i3;
                    gestureHintDrawable.setLayerInset(i5, dimension, 0, dimension, 0);
                    gestureHintDrawable2.setLayerGravity(i3, 5);
                    gestureHintDrawable2.setLayerInset(i5, dimension, 0, dimension, 0);
                } else if (i == 3) {
                    gestureHintDrawable.setLayerGravity(i3, 3);
                    int i6 = i3;
                    gestureHintDrawable.setLayerInset(i6, dimension, 0, dimension, 0);
                    gestureHintDrawable2.setLayerGravity(i3, 3);
                    gestureHintDrawable2.setLayerInset(i6, dimension, 0, dimension, 0);
                }
            }
        }
        setBackground(this.hintDrawable);
        if (getChildCount() == 0) {
            childAt = new View(this.mContext);
            addView(childAt);
        } else {
            childAt = getChildAt(0);
        }
        childAt.setBackground(this.viDrawable);
        childAt.setScaleX(0.0f);
        childAt.setScaleY(0.0f);
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void setDarkIntensity(float f) {
        GestureHintDrawable gestureHintDrawable = this.hintDrawable;
        if (gestureHintDrawable != null) {
            if (this.darkIntensity == f) {
                return;
            }
            if (gestureHintDrawable != null) {
                gestureHintDrawable.setDarkIntensity(f);
            }
            GestureHintDrawable gestureHintDrawable2 = this.viDrawable;
            if (gestureHintDrawable2 != null) {
                gestureHintDrawable2.setDarkIntensity(f);
            }
            invalidate();
        }
    }

    public /* synthetic */ NavigationHintHandle(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public NavigationHintHandle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.darkIntensity = -1.0f;
        setFocusable(false);
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void setImageDrawable(Drawable drawable) {
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void abortCurrentGesture() {
    }

    @Override // com.android.systemui.navigationbar.buttons.ButtonInterface
    public final void setVertical() {
    }
}
