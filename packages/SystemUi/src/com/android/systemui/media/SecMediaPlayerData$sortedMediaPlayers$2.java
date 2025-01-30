package com.android.systemui.media;

import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class SecMediaPlayerData$sortedMediaPlayers$2 extends FunctionReferenceImpl implements Function0 {
    public static final SecMediaPlayerData$sortedMediaPlayers$2 INSTANCE = new SecMediaPlayerData$sortedMediaPlayers$2();

    public SecMediaPlayerData$sortedMediaPlayers$2() {
        super(0, CollectionsKt.class, "arrayListOf", "arrayListOf()Ljava/util/ArrayList;", 1);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return new ArrayList();
    }
}
