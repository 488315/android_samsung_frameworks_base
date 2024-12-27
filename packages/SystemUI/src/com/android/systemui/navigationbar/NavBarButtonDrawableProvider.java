package com.android.systemui.navigationbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.android.systemui.BasicRune;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawableProvider;
import com.android.systemui.navigationbar.gestural.GestureHintDrawable;
import com.samsung.systemui.splugins.navigationbar.IconResource;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NavBarButtonDrawableProvider implements KeyButtonDrawableProvider {
    public static final Companion Companion = new Companion(null);
    public static volatile NavBarButtonDrawableProvider INSTANCE;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public final GestureHintDrawable getGestureHintDrawable(Context context, IconResource iconResource, int i) {
        GestureHintDrawable.Companion companion = GestureHintDrawable.Companion;
        Drawable drawable = iconResource.mLightDrawable;
        Drawable drawable2 = iconResource.mDarkDrawable;
        companion.getClass();
        if (drawable2 == null) {
            return new GestureHintDrawable(new Drawable[]{drawable});
        }
        if ((!BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN || context.getDisplayId() != 1) && (i == 1 || i == 3)) {
            drawable = GestureHintDrawable.Companion.rotateDrawable(context.getResources(), drawable, i);
            drawable2 = GestureHintDrawable.Companion.rotateDrawable(context.getResources(), drawable2, i);
        }
        return new GestureHintDrawable(new Drawable[]{drawable, drawable2});
    }
}
