package com.android.systemui.media;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final /* synthetic */ class SecMediaPlayerData$mediaData$2 extends FunctionReferenceImpl implements Function0 {
    public static final SecMediaPlayerData$mediaData$2 INSTANCE = new SecMediaPlayerData$mediaData$2();

    public SecMediaPlayerData$mediaData$2() {
        super(0, ConcurrentHashMap.class, "<init>", "<init>()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return new ConcurrentHashMap();
    }
}
