package com.android.systemui.wallpapers.data.repository;

import com.android.systemui.user.data.model.SelectedUserModel;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class WallpaperRepositoryImpl$getWallpaperInfo$3 extends AdaptedFunctionReference implements Function3 {
    public static final WallpaperRepositoryImpl$getWallpaperInfo$3 INSTANCE = new WallpaperRepositoryImpl$getWallpaperInfo$3();

    public WallpaperRepositoryImpl$getWallpaperInfo$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        String str = WallpaperRepositoryImpl.TAG;
        return new Pair((Unit) obj, (SelectedUserModel) obj2);
    }
}
