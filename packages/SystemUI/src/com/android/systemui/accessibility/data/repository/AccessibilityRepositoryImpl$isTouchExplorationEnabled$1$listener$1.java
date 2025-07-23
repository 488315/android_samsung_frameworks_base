package com.android.systemui.accessibility.data.repository;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class AccessibilityRepositoryImpl$isTouchExplorationEnabled$1$listener$1 extends AdaptedFunctionReference implements Function1 {
    public AccessibilityRepositoryImpl$isTouchExplorationEnabled$1$listener$1(Object obj) {
        super(1, obj, ProducerScope.class, "trySend", "trySend-JP2dKIU(Ljava/lang/Object;)Ljava/lang/Object;", 8);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        ((ChannelCoroutine) ((ProducerScope) this.receiver)).mo3456trySendJP2dKIU(bool);
        return Unit.INSTANCE;
    }
}
