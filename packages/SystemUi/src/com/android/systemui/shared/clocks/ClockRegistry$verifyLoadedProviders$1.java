package com.android.systemui.shared.clocks;

import com.android.systemui.plugins.PluginLifecycleManager;
import com.android.systemui.shared.clocks.ClockRegistry;
import java.util.Iterator;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.shared.clocks.ClockRegistry$verifyLoadedProviders$1", m277f = "ClockRegistry.kt", m278l = {}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class ClockRegistry$verifyLoadedProviders$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ClockRegistry this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockRegistry$verifyLoadedProviders$1(ClockRegistry clockRegistry, Continuation<? super ClockRegistry$verifyLoadedProviders$1> continuation) {
        super(2, continuation);
        this.this$0 = clockRegistry;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ClockRegistry$verifyLoadedProviders$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ClockRegistry$verifyLoadedProviders$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ClockRegistry clockRegistry = this.this$0;
        if (clockRegistry.keepAllLoaded) {
            Iterator it = clockRegistry.availableClocks.entrySet().iterator();
            while (it.hasNext()) {
                PluginLifecycleManager pluginLifecycleManager = ((ClockRegistry.ClockInfo) ((Map.Entry) it.next()).getValue()).manager;
                if (pluginLifecycleManager != null) {
                    pluginLifecycleManager.loadPlugin();
                }
            }
            this.this$0.isVerifying.set(false);
            return Unit.INSTANCE;
        }
        ClockRegistry.ClockInfo clockInfo = (ClockRegistry.ClockInfo) clockRegistry.availableClocks.get(clockRegistry.getCurrentClockId());
        if (clockInfo == null) {
            Iterator it2 = this.this$0.availableClocks.entrySet().iterator();
            while (it2.hasNext()) {
                PluginLifecycleManager pluginLifecycleManager2 = ((ClockRegistry.ClockInfo) ((Map.Entry) it2.next()).getValue()).manager;
                if (pluginLifecycleManager2 != null) {
                    pluginLifecycleManager2.unloadPlugin();
                }
            }
            this.this$0.isVerifying.set(false);
            return Unit.INSTANCE;
        }
        PluginLifecycleManager pluginLifecycleManager3 = clockInfo.manager;
        if (pluginLifecycleManager3 != null) {
            pluginLifecycleManager3.loadPlugin();
        }
        Iterator it3 = this.this$0.availableClocks.entrySet().iterator();
        while (it3.hasNext()) {
            PluginLifecycleManager pluginLifecycleManager4 = ((ClockRegistry.ClockInfo) ((Map.Entry) it3.next()).getValue()).manager;
            if (pluginLifecycleManager4 != null && pluginLifecycleManager4.isLoaded() && !Intrinsics.areEqual(pluginLifecycleManager3, pluginLifecycleManager4)) {
                pluginLifecycleManager4.unloadPlugin();
            }
        }
        this.this$0.isVerifying.set(false);
        return Unit.INSTANCE;
    }
}
