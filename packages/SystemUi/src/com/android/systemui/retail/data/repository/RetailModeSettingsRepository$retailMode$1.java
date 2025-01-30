package com.android.systemui.retail.data.repository;

import android.database.ContentObserver;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.GlobalSettingsImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.retail.data.repository.RetailModeSettingsRepository$retailMode$1", m277f = "RetailModeRepository.kt", m278l = {71}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class RetailModeSettingsRepository$retailMode$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ GlobalSettings $globalSettings;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RetailModeSettingsRepository$retailMode$1(GlobalSettings globalSettings, Continuation<? super RetailModeSettingsRepository$retailMode$1> continuation) {
        super(2, continuation);
        this.$globalSettings = globalSettings;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RetailModeSettingsRepository$retailMode$1 retailModeSettingsRepository$retailMode$1 = new RetailModeSettingsRepository$retailMode$1(this.$globalSettings, continuation);
        retailModeSettingsRepository$retailMode$1.L$0 = obj;
        return retailModeSettingsRepository$retailMode$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RetailModeSettingsRepository$retailMode$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.database.ContentObserver, com.android.systemui.retail.data.repository.RetailModeSettingsRepository$retailMode$1$observer$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new ContentObserver() { // from class: com.android.systemui.retail.data.repository.RetailModeSettingsRepository$retailMode$1$observer$1
                {
                    super(null);
                }

                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2872trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            GlobalSettingsImpl globalSettingsImpl = (GlobalSettingsImpl) this.$globalSettings;
            globalSettingsImpl.registerContentObserverForUser(globalSettingsImpl.getUriFor("device_demo_mode"), false, (ContentObserver) r1, globalSettingsImpl.getUserId());
            final GlobalSettings globalSettings = this.$globalSettings;
            Function0 function0 = new Function0() { // from class: com.android.systemui.retail.data.repository.RetailModeSettingsRepository$retailMode$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    GlobalSettings.this.unregisterContentObserver(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
