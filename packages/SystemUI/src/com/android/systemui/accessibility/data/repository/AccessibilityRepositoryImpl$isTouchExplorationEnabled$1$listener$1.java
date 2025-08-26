package com.android.systemui.accessibility.data.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
final /* synthetic */ class AccessibilityRepositoryImpl$isTouchExplorationEnabled$1$listener$1 extends AdaptedFunctionReference implements Function1 {
    public AccessibilityRepositoryImpl$isTouchExplorationEnabled$1$listener$1(Object obj) {
        super(1, obj, ProducerScope.class, "trySend", "trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;", 8);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        ((ChannelCoroutine) ((ProducerScope) this.receiver)).mo3476trySendJP2dKIU(bool);
        return Unit.INSTANCE;
    }
}
