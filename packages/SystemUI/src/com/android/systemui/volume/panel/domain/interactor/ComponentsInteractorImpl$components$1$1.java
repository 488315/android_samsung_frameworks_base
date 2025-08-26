package com.android.systemui.volume.panel.domain.interactor;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.volume.panel.shared.VolumePanelLogger;
import com.android.systemui.volume.panel.shared.VolumePanelLogger$$ExternalSyntheticLambda0;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
final class ComponentsInteractorImpl$components$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $componentKey;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ ComponentsInteractorImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ComponentsInteractorImpl$components$1$1(ComponentsInteractorImpl componentsInteractorImpl, String str, Continuation continuation) {
        super(2, continuation);
        this.this$0 = componentsInteractorImpl;
        this.$componentKey = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ComponentsInteractorImpl$components$1$1 componentsInteractorImpl$components$1$1 = new ComponentsInteractorImpl$components$1$1(this.this$0, this.$componentKey, continuation);
        componentsInteractorImpl$components$1$1.Z$0 = ((Boolean) obj).booleanValue();
        return componentsInteractorImpl$components$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((ComponentsInteractorImpl$components$1$1) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        VolumePanelLogger volumePanelLogger = this.this$0.logger;
        String str = this.$componentKey;
        volumePanelLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        VolumePanelLogger$$ExternalSyntheticLambda0 volumePanelLogger$$ExternalSyntheticLambda0 = new VolumePanelLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = volumePanelLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("SysUI_VolumePanel", logLevel, volumePanelLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        return Unit.INSTANCE;
    }
}
