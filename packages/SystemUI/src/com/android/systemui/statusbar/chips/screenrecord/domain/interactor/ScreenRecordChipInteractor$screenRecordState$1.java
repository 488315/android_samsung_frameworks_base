package com.android.systemui.statusbar.chips.screenrecord.domain.interactor;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
final class ScreenRecordChipInteractor$screenRecordState$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ ScreenRecordChipInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenRecordChipInteractor$screenRecordState$1(ScreenRecordChipInteractor screenRecordChipInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = screenRecordChipInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj3).booleanValue();
        ScreenRecordChipInteractor$screenRecordState$1 screenRecordChipInteractor$screenRecordState$1 = new ScreenRecordChipInteractor$screenRecordState$1(this.this$0, (Continuation) obj4);
        screenRecordChipInteractor$screenRecordState$1.L$0 = (ScreenRecordModel) obj;
        screenRecordChipInteractor$screenRecordState$1.L$1 = (MediaProjectionState) obj2;
        screenRecordChipInteractor$screenRecordState$1.Z$0 = zBooleanValue;
        return screenRecordChipInteractor$screenRecordState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Intent intent;
        ComponentName component;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ScreenRecordModel screenRecordModel = (ScreenRecordModel) this.L$0;
        MediaProjectionState mediaProjectionState = (MediaProjectionState) this.L$1;
        String packageName = null;
        if (this.Z$0 && (screenRecordModel instanceof ScreenRecordModel.Starting)) {
            LogBuffer logBuffer = this.this$0.logger;
            final int i = 0;
            logBuffer.commit(logBuffer.obtain(ScreenRecordChipInteractor.TAG, LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor$screenRecordState$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    LogMessage logMessage = (LogMessage) obj2;
                    switch (i) {
                        case 0:
                            return "State: Recording(hostPackage=null, taskPackage=null) due to force-start";
                        case 1:
                            return "State: DoingNothing";
                        case 2:
                            return "State: Starting(" + logMessage.getLong1() + ")";
                        default:
                            return MotionLayout$$ExternalSyntheticOutline0.m("State: Recording(hostPackage=", logMessage.getStr1(), ", taskPackage=", logMessage.getStr2(), ")");
                    }
                }
            }, null));
            return new ScreenRecordChipModel.Recording(null, null);
        }
        if (screenRecordModel instanceof ScreenRecordModel.DoingNothing) {
            LogBuffer logBuffer2 = this.this$0.logger;
            final int i2 = 1;
            logBuffer2.commit(logBuffer2.obtain(ScreenRecordChipInteractor.TAG, LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor$screenRecordState$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    LogMessage logMessage = (LogMessage) obj2;
                    switch (i2) {
                        case 0:
                            return "State: Recording(hostPackage=null, taskPackage=null) due to force-start";
                        case 1:
                            return "State: DoingNothing";
                        case 2:
                            return "State: Starting(" + logMessage.getLong1() + ")";
                        default:
                            return MotionLayout$$ExternalSyntheticOutline0.m("State: Recording(hostPackage=", logMessage.getStr1(), ", taskPackage=", logMessage.getStr2(), ")");
                    }
                }
            }, null));
            return ScreenRecordChipModel.DoingNothing.INSTANCE;
        }
        if (screenRecordModel instanceof ScreenRecordModel.Starting) {
            LogBuffer logBuffer3 = this.this$0.logger;
            final int i3 = 2;
            LogMessage logMessageObtain = logBuffer3.obtain(ScreenRecordChipInteractor.TAG, LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor$screenRecordState$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    LogMessage logMessage = (LogMessage) obj2;
                    switch (i3) {
                        case 0:
                            return "State: Recording(hostPackage=null, taskPackage=null) due to force-start";
                        case 1:
                            return "State: DoingNothing";
                        case 2:
                            return "State: Starting(" + logMessage.getLong1() + ")";
                        default:
                            return MotionLayout$$ExternalSyntheticOutline0.m("State: Recording(hostPackage=", logMessage.getStr1(), ", taskPackage=", logMessage.getStr2(), ")");
                    }
                }
            }, null);
            ScreenRecordModel.Starting starting = (ScreenRecordModel.Starting) screenRecordModel;
            ((LogMessageImpl) logMessageObtain).long1 = starting.millisUntilStarted;
            logBuffer3.commit(logMessageObtain);
            return new ScreenRecordChipModel.Starting(starting.millisUntilStarted);
        }
        if (!(screenRecordModel instanceof ScreenRecordModel.Recording)) {
            throw new NoWhenBranchMatchedException();
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = mediaProjectionState instanceof MediaProjectionState.Projecting.SingleTask ? ((MediaProjectionState.Projecting.SingleTask) mediaProjectionState).task : null;
        String hostPackage = mediaProjectionState instanceof MediaProjectionState.Projecting ? ((MediaProjectionState.Projecting) mediaProjectionState).getHostPackage() : null;
        LogBuffer logBuffer4 = this.this$0.logger;
        final int i4 = 3;
        LogMessage logMessageObtain2 = logBuffer4.obtain(ScreenRecordChipInteractor.TAG, LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.chips.screenrecord.domain.interactor.ScreenRecordChipInteractor$screenRecordState$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                switch (i4) {
                    case 0:
                        return "State: Recording(hostPackage=null, taskPackage=null) due to force-start";
                    case 1:
                        return "State: DoingNothing";
                    case 2:
                        return "State: Starting(" + logMessage.getLong1() + ")";
                    default:
                        return MotionLayout$$ExternalSyntheticOutline0.m("State: Recording(hostPackage=", logMessage.getStr1(), ", taskPackage=", logMessage.getStr2(), ")");
                }
            }
        }, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
        logMessageImpl.str1 = hostPackage;
        if (runningTaskInfo != null && (intent = runningTaskInfo.baseIntent) != null && (component = intent.getComponent()) != null) {
            packageName = component.getPackageName();
        }
        logMessageImpl.str2 = packageName;
        logBuffer4.commit(logMessageObtain2);
        return new ScreenRecordChipModel.Recording(hostPackage, runningTaskInfo);
    }
}
