package com.android.systemui.accessibility.data.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.accessibility.data.repository.AccessibilityRepositoryImpl$isTouchExplorationEnabled$1$listener$1 */
/* loaded from: classes.dex */
final /* synthetic */ class C1008x9c1f5fb1 extends AdaptedFunctionReference implements Function1 {
    public C1008x9c1f5fb1(Object obj) {
        super(1, obj, ProducerScope.class, "trySend", "trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;", 8);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ((ChannelCoroutine) ((ProducerScope) this.receiver)).mo2872trySendJP2dKIU(Boolean.valueOf(booleanValue));
        return Unit.INSTANCE;
    }
}
