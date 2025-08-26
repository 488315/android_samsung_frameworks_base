package com.android.systemui.wallpapers.ui.viewmodel;

import android.graphics.RectF;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.Triple;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes3.dex */
final /* synthetic */ class WallpaperFocalAreaViewModel$wallpaperFocalAreaBounds$4 extends AdaptedFunctionReference implements Function4 {
    public static final WallpaperFocalAreaViewModel$wallpaperFocalAreaBounds$4 INSTANCE = new WallpaperFocalAreaViewModel$wallpaperFocalAreaBounds$4();

    public WallpaperFocalAreaViewModel$wallpaperFocalAreaBounds$4() {
        super(4, Triple.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        return new Triple((RectF) obj, (TransitionStep) obj2, (TransitionStep) obj3);
    }
}
