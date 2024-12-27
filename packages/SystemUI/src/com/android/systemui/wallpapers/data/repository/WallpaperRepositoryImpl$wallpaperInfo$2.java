package com.android.systemui.wallpapers.data.repository;

import com.android.systemui.user.data.model.SelectedUserModel;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

final /* synthetic */ class WallpaperRepositoryImpl$wallpaperInfo$2 extends AdaptedFunctionReference implements Function3 {
    public static final WallpaperRepositoryImpl$wallpaperInfo$2 INSTANCE = new WallpaperRepositoryImpl$wallpaperInfo$2();

    public WallpaperRepositoryImpl$wallpaperInfo$2() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        return new Pair((Unit) obj, (SelectedUserModel) obj2);
    }
}
