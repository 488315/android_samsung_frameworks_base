package com.android.systemui.inputdevice.tutorial.domain.interactor;

import android.os.SystemProperties;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.inputdevice.tutorial.data.repository.DeviceType;
import com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository;
import java.io.PrintWriter;
import java.time.Duration;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class TutorialSchedulerInteractor$TutorialCommand$execute$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ PrintWriter $pw;
    Object L$0;
    int label;
    final /* synthetic */ TutorialSchedulerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSchedulerInteractor$TutorialCommand$execute$2(PrintWriter printWriter, TutorialSchedulerInteractor tutorialSchedulerInteractor, Continuation continuation) {
        super(2, continuation);
        this.$pw = printWriter;
        this.this$0 = tutorialSchedulerInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TutorialSchedulerInteractor$TutorialCommand$execute$2(this.$pw, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerInteractor$TutorialCommand$execute$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0077 A[PHI: r1 r7
      0x0077: PHI (r1v6 java.io.PrintWriter) = (r1v3 java.io.PrintWriter), (r1v9 java.io.PrintWriter) binds: [B:16:0x0074, B:10:0x0036] A[DONT_GENERATE, DONT_INLINE]
      0x0077: PHI (r7v8 java.lang.Object) = (r7v7 java.lang.Object), (r7v0 java.lang.Object) binds: [B:16:0x0074, B:10:0x0036] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x008e A[PHI: r1 r7
      0x008e: PHI (r1v10 java.io.PrintWriter) = (r1v7 java.io.PrintWriter), (r1v13 java.io.PrintWriter) binds: [B:19:0x008b, B:9:0x002e] A[DONT_GENERATE, DONT_INLINE]
      0x008e: PHI (r7v12 java.lang.Object) = (r7v11 java.lang.Object), (r7v0 java.lang.Object) binds: [B:19:0x008b, B:9:0x002e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a5 A[PHI: r1 r7
      0x00a5: PHI (r1v14 java.io.PrintWriter) = (r1v11 java.io.PrintWriter), (r1v17 java.io.PrintWriter) binds: [B:22:0x00a2, B:8:0x0025] A[DONT_GENERATE, DONT_INLINE]
      0x00a5: PHI (r7v16 java.lang.Object) = (r7v15 java.lang.Object), (r7v0 java.lang.Object) binds: [B:22:0x00a2, B:8:0x0025] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00be A[PHI: r1 r7
      0x00be: PHI (r1v18 java.io.PrintWriter) = (r1v15 java.io.PrintWriter), (r1v23 java.io.PrintWriter) binds: [B:25:0x00bb, B:7:0x001c] A[DONT_GENERATE, DONT_INLINE]
      0x00be: PHI (r7v20 java.lang.Object) = (r7v19 java.lang.Object), (r7v0 java.lang.Object) binds: [B:25:0x00bb, B:7:0x001c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00d5  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        PrintWriter printWriter;
        PrintWriter printWriter2;
        PrintWriter printWriter3;
        PrintWriter printWriter4;
        PrintWriter printWriter5;
        Object scheduledTutorialLaunchTime;
        PrintWriter printWriter6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (this.label) {
            case 0:
                ResultKt.throwOnFailure(obj);
                printWriter = this.$pw;
                TutorialSchedulerRepository tutorialSchedulerRepository = this.this$0.repo;
                DeviceType deviceType = DeviceType.KEYBOARD;
                this.L$0 = printWriter;
                this.label = 1;
                obj = tutorialSchedulerRepository.getFirstConnectionTime(deviceType, this);
                if (obj != coroutineSingletons) {
                    DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("Keyboard connect time = ", obj, printWriter);
                    printWriter2 = this.$pw;
                    TutorialSchedulerRepository tutorialSchedulerRepository2 = this.this$0.repo;
                    DeviceType deviceType2 = DeviceType.KEYBOARD;
                    this.L$0 = printWriter2;
                    this.label = 2;
                    obj = tutorialSchedulerRepository2.isNotified(deviceType2, this);
                    if (obj != coroutineSingletons) {
                        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         notified = ", obj, printWriter2);
                        printWriter3 = this.$pw;
                        TutorialSchedulerRepository tutorialSchedulerRepository3 = this.this$0.repo;
                        DeviceType deviceType3 = DeviceType.KEYBOARD;
                        this.L$0 = printWriter3;
                        this.label = 3;
                        obj = tutorialSchedulerRepository3.getScheduledTutorialLaunchTime(deviceType3, this);
                        if (obj != coroutineSingletons) {
                            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         launch time = ", obj, printWriter3);
                            printWriter4 = this.$pw;
                            TutorialSchedulerRepository tutorialSchedulerRepository4 = this.this$0.repo;
                            DeviceType deviceType4 = DeviceType.TOUCHPAD;
                            this.L$0 = printWriter4;
                            this.label = 4;
                            obj = tutorialSchedulerRepository4.getFirstConnectionTime(deviceType4, this);
                            if (obj != coroutineSingletons) {
                                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("Touchpad connect time = ", obj, printWriter4);
                                printWriter5 = this.$pw;
                                TutorialSchedulerRepository tutorialSchedulerRepository5 = this.this$0.repo;
                                DeviceType deviceType5 = DeviceType.TOUCHPAD;
                                this.L$0 = printWriter5;
                                this.label = 5;
                                obj = tutorialSchedulerRepository5.isNotified(deviceType5, this);
                                if (obj != coroutineSingletons) {
                                    DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         notified = ", obj, printWriter5);
                                    PrintWriter printWriter7 = this.$pw;
                                    TutorialSchedulerRepository tutorialSchedulerRepository6 = this.this$0.repo;
                                    DeviceType deviceType6 = DeviceType.TOUCHPAD;
                                    this.L$0 = printWriter7;
                                    this.label = 6;
                                    scheduledTutorialLaunchTime = tutorialSchedulerRepository6.getScheduledTutorialLaunchTime(deviceType6, this);
                                    if (scheduledTutorialLaunchTime != coroutineSingletons) {
                                        printWriter6 = printWriter7;
                                        obj = scheduledTutorialLaunchTime;
                                        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         launch time = ", obj, printWriter6);
                                        PrintWriter printWriter8 = this.$pw;
                                        TutorialSchedulerInteractor.Companion.getClass();
                                        printWriter8.println("Delay time = " + Duration.ofSeconds(SystemProperties.getLong("persist.peripheral_tutorial_delay_sec", TutorialSchedulerInteractor.DEFAULT_LAUNCH_DELAY_SEC)).getSeconds() + " sec");
                                        return Unit.INSTANCE;
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                printWriter = (PrintWriter) this.L$0;
                ResultKt.throwOnFailure(obj);
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("Keyboard connect time = ", obj, printWriter);
                printWriter2 = this.$pw;
                TutorialSchedulerRepository tutorialSchedulerRepository22 = this.this$0.repo;
                DeviceType deviceType22 = DeviceType.KEYBOARD;
                this.L$0 = printWriter2;
                this.label = 2;
                obj = tutorialSchedulerRepository22.isNotified(deviceType22, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                printWriter2 = (PrintWriter) this.L$0;
                ResultKt.throwOnFailure(obj);
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         notified = ", obj, printWriter2);
                printWriter3 = this.$pw;
                TutorialSchedulerRepository tutorialSchedulerRepository32 = this.this$0.repo;
                DeviceType deviceType32 = DeviceType.KEYBOARD;
                this.L$0 = printWriter3;
                this.label = 3;
                obj = tutorialSchedulerRepository32.getScheduledTutorialLaunchTime(deviceType32, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                printWriter3 = (PrintWriter) this.L$0;
                ResultKt.throwOnFailure(obj);
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         launch time = ", obj, printWriter3);
                printWriter4 = this.$pw;
                TutorialSchedulerRepository tutorialSchedulerRepository42 = this.this$0.repo;
                DeviceType deviceType42 = DeviceType.TOUCHPAD;
                this.L$0 = printWriter4;
                this.label = 4;
                obj = tutorialSchedulerRepository42.getFirstConnectionTime(deviceType42, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                printWriter4 = (PrintWriter) this.L$0;
                ResultKt.throwOnFailure(obj);
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("Touchpad connect time = ", obj, printWriter4);
                printWriter5 = this.$pw;
                TutorialSchedulerRepository tutorialSchedulerRepository52 = this.this$0.repo;
                DeviceType deviceType52 = DeviceType.TOUCHPAD;
                this.L$0 = printWriter5;
                this.label = 5;
                obj = tutorialSchedulerRepository52.isNotified(deviceType52, this);
                if (obj != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                printWriter5 = (PrintWriter) this.L$0;
                ResultKt.throwOnFailure(obj);
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         notified = ", obj, printWriter5);
                PrintWriter printWriter72 = this.$pw;
                TutorialSchedulerRepository tutorialSchedulerRepository62 = this.this$0.repo;
                DeviceType deviceType62 = DeviceType.TOUCHPAD;
                this.L$0 = printWriter72;
                this.label = 6;
                scheduledTutorialLaunchTime = tutorialSchedulerRepository62.getScheduledTutorialLaunchTime(deviceType62, this);
                if (scheduledTutorialLaunchTime != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                printWriter6 = (PrintWriter) this.L$0;
                ResultKt.throwOnFailure(obj);
                DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("         launch time = ", obj, printWriter6);
                PrintWriter printWriter82 = this.$pw;
                TutorialSchedulerInteractor.Companion.getClass();
                printWriter82.println("Delay time = " + Duration.ofSeconds(SystemProperties.getLong("persist.peripheral_tutorial_delay_sec", TutorialSchedulerInteractor.DEFAULT_LAUNCH_DELAY_SEC)).getSeconds() + " sec");
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }
}
