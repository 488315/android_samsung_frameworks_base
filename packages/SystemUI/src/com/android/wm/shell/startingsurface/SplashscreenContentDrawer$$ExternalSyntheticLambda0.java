package com.android.wm.shell.startingsurface;

import android.content.res.TypedArray;
import java.util.function.UnaryOperator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SplashscreenContentDrawer$$ExternalSyntheticLambda0 implements UnaryOperator {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ TypedArray f$0;

    public /* synthetic */ SplashscreenContentDrawer$$ExternalSyntheticLambda0(TypedArray typedArray, int i) {
        this.$r8$classId = i;
        this.f$0 = typedArray;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        TypedArray typedArray = this.f$0;
        switch (i) {
            case 0:
                int i2 = SplashscreenContentDrawer.mThemeBackgroundColor;
                return Integer.valueOf(typedArray.getColor(56, ((Integer) obj).intValue()));
            case 1:
                int i3 = SplashscreenContentDrawer.mThemeBackgroundColor;
                return Integer.valueOf(typedArray.getResourceId(47, ((Integer) obj).intValue()));
            case 2:
                int i4 = SplashscreenContentDrawer.mThemeBackgroundColor;
                return typedArray.getDrawable(57);
            case 3:
                int i5 = SplashscreenContentDrawer.mThemeBackgroundColor;
                return typedArray.getDrawable(59);
            default:
                int i6 = SplashscreenContentDrawer.mThemeBackgroundColor;
                return Integer.valueOf(typedArray.getColor(60, ((Integer) obj).intValue()));
        }
    }
}
