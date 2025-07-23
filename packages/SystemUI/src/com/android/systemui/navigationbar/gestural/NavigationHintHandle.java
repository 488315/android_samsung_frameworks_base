package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.navigationbar.icon.NavBarIconResourceMapper;
import com.android.systemui.navigationbar.views.NavBarButtonDrawableProvider;
import com.android.systemui.navigationbar.views.buttons.ButtonInterface;
import com.android.systemui.navigationbar.views.buttons.KeyButtonDrawableProvider;
import com.samsung.systemui.splugins.navigationbar.IconResource;
import com.samsung.systemui.splugins.navigationbar.IconType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NavigationHintHandle extends FrameLayout implements ButtonInterface {
    public final float darkIntensity;
    public GestureHintDrawable hintDrawable;
    public NavBarIconResourceMapper iconResourceMapper;
    public final Context mContext;
    public GestureHintDrawable viDrawable;

    public NavigationHintHandle(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.navigationbar.views.buttons.ButtonInterface
    public final void setCurrentRotation(int i, boolean z) {
        View childAt;
        GestureHintDrawable gestureHintDrawable;
        GestureHintDrawable gestureHintDrawable2;
        NavBarIconResourceMapper navBarIconResourceMapper = this.iconResourceMapper;
        if (navBarIconResourceMapper != null) {
            IconType iconType = IconType.TYPE_GESTURE_HINT;
            Context context = navBarIconResourceMapper.context;
            IconResource iconResource = navBarIconResourceMapper.getIconResource(iconType);
            int i2 = z ? i : 0;
            KeyButtonDrawableProvider keyButtonDrawableProvider = navBarIconResourceMapper.buttonDrawableProvider;
            this.hintDrawable = ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getGestureHintDrawable(context, iconResource, i2);
            this.viDrawable = ((NavBarButtonDrawableProvider) keyButtonDrawableProvider).getGestureHintDrawable(navBarIconResourceMapper.context, navBarIconResourceMapper.getIconResource(IconType.TYPE_GESTURE_HINT_VI), z ? i : 0);
        }
        int dimension = (int) this.mContext.getResources().getDimension((BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN && this.mContext.getDisplay().getDisplayId() == 1) ? R.dimen.large_cover_hint_bottom_padding : R.dimen.samsung_hint_bottom_padding);
        GestureHintDrawable gestureHintDrawable3 = this.hintDrawable;
        GestureHintDrawable gestureHintDrawable4 = this.viDrawable;
        if (gestureHintDrawable3 != null && gestureHintDrawable4 != null) {
            int i3 = 0;
            while (i3 < 2) {
                if (!z || i == 0 || i == 2) {
                    gestureHintDrawable = gestureHintDrawable3;
                    gestureHintDrawable.setLayerGravity(i3, 80);
                    int i4 = dimension;
                    gestureHintDrawable.setLayerInset(i3, 0, i4, 0, i4);
                    gestureHintDrawable4.setLayerGravity(i3, 80);
                    gestureHintDrawable2 = gestureHintDrawable4;
                    gestureHintDrawable2.setLayerInset(i3, 0, i4, 0, i4);
                    dimension = i4;
                } else if (i == 1) {
                    gestureHintDrawable3.setLayerGravity(i3, 5);
                    int i5 = dimension;
                    gestureHintDrawable3.setLayerInset(i3, dimension, 0, i5, 0);
                    gestureHintDrawable = gestureHintDrawable3;
                    gestureHintDrawable4.setLayerGravity(i3, 5);
                    gestureHintDrawable2 = gestureHintDrawable4;
                    gestureHintDrawable2.setLayerInset(i3, dimension, 0, i5, 0);
                } else {
                    gestureHintDrawable = gestureHintDrawable3;
                    if (i == 3) {
                        gestureHintDrawable.setLayerGravity(i3, 3);
                        int i6 = dimension;
                        gestureHintDrawable.setLayerInset(i3, dimension, 0, i6, 0);
                        gestureHintDrawable4.setLayerGravity(i3, 3);
                        gestureHintDrawable2 = gestureHintDrawable4;
                        gestureHintDrawable2.setLayerInset(i3, dimension, 0, i6, 0);
                    } else {
                        gestureHintDrawable2 = gestureHintDrawable4;
                    }
                }
                i3++;
                gestureHintDrawable4 = gestureHintDrawable2;
                gestureHintDrawable3 = gestureHintDrawable;
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

    @Override // com.android.systemui.navigationbar.views.buttons.ButtonInterface
    public final void setDarkIntensity(float f) {
        GestureHintDrawable gestureHintDrawable = this.hintDrawable;
        if (gestureHintDrawable == null || this.darkIntensity == f) {
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

    public /* synthetic */ NavigationHintHandle(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public NavigationHintHandle(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.darkIntensity = -1.0f;
        setFocusable(false);
    }

    @Override // com.android.systemui.navigationbar.views.buttons.ButtonInterface
    public final void setImageDrawable(Drawable drawable) {
    }

    @Override // com.android.systemui.navigationbar.views.buttons.ButtonInterface
    public final void abortCurrentGesture() {
    }
}
