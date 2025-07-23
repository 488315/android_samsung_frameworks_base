package com.android.systemui.wallpapers.data.repository;

import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$4 extends AdaptedFunctionReference implements Function3 {
    public static final WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$4 INSTANCE = new WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$4();

    public WallpaperRepositoryImpl$wallpaperSupportsAmbientMode$4() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        String str = WallpaperRepositoryImpl.TAG;
        return new Pair((Unit) obj, (Unit) obj2);
    }
}
