package com.android.systemui.media;

import java.util.concurrent.ConcurrentHashMap;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
