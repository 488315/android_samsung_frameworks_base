package com.android.systemui.media;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public /* synthetic */ class SecMediaPlayerData$mediaData$2 extends FunctionReferenceImpl implements Function0 {
    public static final SecMediaPlayerData$mediaData$2 INSTANCE = new SecMediaPlayerData$mediaData$2();

    public SecMediaPlayerData$mediaData$2() {
        super(0, ConcurrentHashMap.class, "<init>", "<init>()V", 0);
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        return new ConcurrentHashMap();
    }
}
