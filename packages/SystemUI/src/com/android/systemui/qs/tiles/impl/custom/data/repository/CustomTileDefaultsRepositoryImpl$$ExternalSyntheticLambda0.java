package com.android.systemui.qs.tiles.impl.custom.data.repository;

import com.android.systemui.qs.tiles.impl.custom.data.repository.CustomTileDefaultsRepositoryImpl;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final /* synthetic */ class CustomTileDefaultsRepositoryImpl$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        CustomTileDefaultsRepositoryImpl.DefaultsRequest defaultsRequest = (CustomTileDefaultsRepositoryImpl.DefaultsRequest) obj;
        CustomTileDefaultsRepositoryImpl.DefaultsRequest defaultsRequest2 = (CustomTileDefaultsRepositoryImpl.DefaultsRequest) obj2;
        int i = CustomTileDefaultsRepositoryImpl.$r8$clinit;
        return Boolean.valueOf(defaultsRequest2.force ? false : Intrinsics.areEqual(defaultsRequest, defaultsRequest2));
    }
}
