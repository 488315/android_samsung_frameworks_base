package com.android.systemui.navigationbar.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.android.systemui.navigationbar.views.buttons.KeyButtonDrawableProvider;
import com.samsung.systemui.splugins.navigationbar.IconResource;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class NavBarButtonDrawableProvider implements KeyButtonDrawableProvider {
    public static final Companion Companion = new Companion(null);
    public static volatile NavBarButtonDrawableProvider INSTANCE;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final GestureHintDrawable getGestureHintDrawable(Context context, IconResource iconResource, int i) {
        GestureHintDrawable.Companion companion = GestureHintDrawable.Companion;
        Drawable drawableRotateDrawable = iconResource.mLightDrawable;
        Drawable drawableRotateDrawable2 = iconResource.mDarkDrawable;
        companion.getClass();
        if (drawableRotateDrawable2 == null) {
            return new GestureHintDrawable(new Drawable[]{drawableRotateDrawable});
        }
        if ((!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || context.getDisplayId() != 1) && (i == 1 || i == 3)) {
            drawableRotateDrawable = GestureHintDrawable.Companion.rotateDrawable(context.getResources(), drawableRotateDrawable, i);
            drawableRotateDrawable2 = GestureHintDrawable.Companion.rotateDrawable(context.getResources(), drawableRotateDrawable2, i);
        }
        return new GestureHintDrawable(new Drawable[]{drawableRotateDrawable, drawableRotateDrawable2});
    }
}
