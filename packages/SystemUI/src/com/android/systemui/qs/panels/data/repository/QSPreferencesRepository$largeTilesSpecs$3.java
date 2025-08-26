package com.android.systemui.qs.panels.data.repository;

import android.content.pm.UserInfo;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;

/* loaded from: classes2.dex */
final /* synthetic */ class QSPreferencesRepository$largeTilesSpecs$3 extends AdaptedFunctionReference implements Function3 {
    public static final QSPreferencesRepository$largeTilesSpecs$3 INSTANCE = new QSPreferencesRepository$largeTilesSpecs$3();

    public QSPreferencesRepository$largeTilesSpecs$3() {
        super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int i = QSPreferencesRepository.$r8$clinit;
        return new Pair((Unit) obj, (UserInfo) obj2);
    }
}
