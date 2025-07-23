package com.android.systemui.media;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class SecMediaPlayerData$sortedMediaPlayers$2 extends FunctionReferenceImpl implements Function0 {
    public static final SecMediaPlayerData$sortedMediaPlayers$2 INSTANCE = new SecMediaPlayerData$sortedMediaPlayers$2();

    public SecMediaPlayerData$sortedMediaPlayers$2() {
        super(0, CollectionsKt.class, "arrayListOf", "arrayListOf()Ljava/util/ArrayList;", 1);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return new ArrayList();
    }
}
