package com.android.systemui.inputdevice.tutorial.domain.interactor;

import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.data.repository.DeviceType;
import com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository;
import com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor;
import com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialCommand;
import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.touchpad.data.repository.TouchpadRepository;
import com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TutorialSchedulerInteractor {
    public static final Companion Companion = new Companion(null);
    public static final long DEFAULT_LAUNCH_DELAY_SEC;
    public final CoroutineScope backgroundScope;
    public final StateFlowImpl commandTutorials;
    public final Map isAnyDeviceConnected;
    public final SafeFlow keyboardScheduleFlow;
    public final InputDeviceTutorialLogger logger;
    public final TutorialSchedulerRepository repo;
    public final SafeFlow touchpadScheduleFlow;
    public final FlowKt__LimitKt$drop$$inlined$unsafeFlow$1 tutorialTypeUpdates;
    public final TutorialSchedulerInteractor$special$$inlined$map$2 tutorials;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TutorialCommand implements Command {
        public TutorialCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            if (list.isEmpty()) {
                help(printWriter);
                return;
            }
            String str = (String) list.get(0);
            int hashCode = str.hashCode();
            TutorialSchedulerInteractor tutorialSchedulerInteractor = TutorialSchedulerInteractor.this;
            if (hashCode != -1039689911) {
                if (hashCode != 3237038) {
                    if (hashCode == 94746189 && str.equals("clear")) {
                        return;
                    }
                } else if (str.equals("info")) {
                    return;
                }
            } else if (str.equals("notify")) {
                if (list.size() != 2) {
                    help(printWriter);
                }
                String str2 = (String) list.get(1);
                int hashCode2 = str2.hashCode();
                if (hashCode2 != -819522316) {
                    if (hashCode2 != 3029889) {
                        if (hashCode2 == 503739367 && str2.equals("keyboard")) {
                            tutorialSchedulerInteractor.commandTutorials.setValue(TutorialType.KEYBOARD);
                            return;
                        }
                    } else if (str2.equals("both")) {
                        tutorialSchedulerInteractor.commandTutorials.setValue(TutorialType.BOTH);
                        return;
                    }
                } else if (str2.equals("touchpad")) {
                    tutorialSchedulerInteractor.commandTutorials.setValue(TutorialType.TOUCHPAD);
                    return;
                }
                help(printWriter);
                return;
            }
            help(printWriter);
        }

        public final void help(PrintWriter printWriter) {
            AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "Usage: adb shell cmd statusbar peripheral_tutorial <command>", "Available commands:", "  clear", "  info");
            printWriter.println("  notify [keyboard|touchpad|both]");
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TutorialType {
        public static final /* synthetic */ TutorialType[] $VALUES;
        public static final TutorialType BOTH;
        public static final TutorialType KEYBOARD;
        public static final TutorialType NONE;
        public static final TutorialType TOUCHPAD;

        static {
            TutorialType tutorialType = new TutorialType("KEYBOARD", 0);
            KEYBOARD = tutorialType;
            TutorialType tutorialType2 = new TutorialType("TOUCHPAD", 1);
            TOUCHPAD = tutorialType2;
            TutorialType tutorialType3 = new TutorialType("BOTH", 2);
            BOTH = tutorialType3;
            TutorialType tutorialType4 = new TutorialType(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 3);
            NONE = tutorialType4;
            TutorialType[] tutorialTypeArr = {tutorialType, tutorialType2, tutorialType3, tutorialType4};
            $VALUES = tutorialTypeArr;
            EnumEntriesKt.enumEntries(tutorialTypeArr);
        }

        private TutorialType(String str, int i) {
        }

        public static TutorialType valueOf(String str) {
            return (TutorialType) Enum.valueOf(TutorialType.class, str);
        }

        public static TutorialType[] values() {
            return (TutorialType[]) $VALUES.clone();
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DEFAULT_LAUNCH_DELAY_SEC = Duration.m3445toLongimpl(DurationKt.toDuration(72, DurationUnit.HOURS), DurationUnit.SECONDS);
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$2] */
    public TutorialSchedulerInteractor(KeyboardRepository keyboardRepository, TouchpadRepository touchpadRepository, TutorialSchedulerRepository tutorialSchedulerRepository, InputDeviceTutorialLogger inputDeviceTutorialLogger, CommandRegistry commandRegistry, CoroutineScope coroutineScope) {
        this.repo = tutorialSchedulerRepository;
        this.logger = inputDeviceTutorialLogger;
        this.backgroundScope = coroutineScope;
        commandRegistry.registerCommand("peripheral_tutorial", new Function0() { // from class: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TutorialSchedulerInteractor.Companion companion = TutorialSchedulerInteractor.Companion;
                return TutorialSchedulerInteractor.this.new TutorialCommand();
            }
        });
        KeyboardRepositoryImpl keyboardRepositoryImpl = (KeyboardRepositoryImpl) keyboardRepository;
        TouchpadRepositoryImpl touchpadRepositoryImpl = (TouchpadRepositoryImpl) touchpadRepository;
        this.isAnyDeviceConnected = MapsKt__MapsKt.mapOf(new Pair(DeviceType.KEYBOARD, keyboardRepositoryImpl.isAnyKeyboardConnected), new Pair(DeviceType.TOUCHPAD, touchpadRepositoryImpl.isAnyTouchpadConnected));
        SafeFlow safeFlow = new SafeFlow(new TutorialSchedulerInteractor$touchpadScheduleFlow$1(this, null));
        this.touchpadScheduleFlow = safeFlow;
        SafeFlow safeFlow2 = new SafeFlow(new TutorialSchedulerInteractor$keyboardScheduleFlow$1(this, null));
        this.keyboardScheduleFlow = safeFlow2;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyboardRepositoryImpl.isAnyKeyboardConnected, touchpadRepositoryImpl.isAnyTouchpadConnected, TutorialSchedulerInteractor$tutorialTypeUpdates$3.INSTANCE);
        this.tutorialTypeUpdates = FlowKt.drop(new Flow() { // from class: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L66
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        kotlin.Pair r5 = (kotlin.Pair) r5
                        java.lang.Object r6 = r5.component1()
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        java.lang.Object r5 = r5.component2()
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r6 == 0) goto L4f
                        if (r5 == 0) goto L4f
                        com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r5 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.BOTH
                        goto L5b
                    L4f:
                        if (r6 == 0) goto L54
                        com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r5 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.KEYBOARD
                        goto L5b
                    L54:
                        if (r5 == 0) goto L59
                        com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r5 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.TOUCHPAD
                        goto L5b
                    L59:
                        com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r5 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.NONE
                    L5b:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L66
                        return r1
                    L66:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.commandTutorials = StateFlowKt.MutableStateFlow(TutorialType.NONE);
        final ChannelLimitedFlowMerge merge = FlowKt.merge(safeFlow, safeFlow2);
        this.tutorials = new Flow() { // from class: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ TutorialSchedulerInteractor this$0;

                /* renamed from: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    Object L$2;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, TutorialSchedulerInteractor tutorialSchedulerInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = tutorialSchedulerInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:20:0x013a, code lost:
                
                    if (r12.emit(r11, r0) != r1) goto L49;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:30:0x0107, code lost:
                
                    if (r13 != r1) goto L46;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:40:0x00c8, code lost:
                
                    if (r2 != r1) goto L29;
                 */
                /* JADX WARN: Removed duplicated region for block: B:29:0x0105  */
                /* JADX WARN: Removed duplicated region for block: B:39:0x00c6  */
                /* JADX WARN: Removed duplicated region for block: B:41:0x006c  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                    /*
                        Method dump skipped, instructions count: 320
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x006b, code lost:
    
        if (r9 == r1) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$resolveTutorialType(com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor r7, com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r8, kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            Method dump skipped, instructions count: 209
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.access$resolveTutorialType(com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor, com.android.systemui.inputdevice.tutorial.data.repository.DeviceType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x01d6, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.first(new com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$waitForDeviceConnection$$inlined$filter$1(r10), r0) != r1) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x012a, code lost:
    
        if (r12 != r1) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0086, code lost:
    
        if (r12 == r1) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$schedule(com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor r10, com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r11, kotlin.coroutines.jvm.internal.ContinuationImpl r12) {
        /*
            Method dump skipped, instructions count: 494
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.access$schedule(com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor, com.android.systemui.inputdevice.tutorial.data.repository.DeviceType, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final void updateLaunchInfo(TutorialType tutorialType) {
        BuildersKt.launch$default(this.backgroundScope, null, null, new TutorialSchedulerInteractor$updateLaunchInfo$1(tutorialType, this, null), 3);
    }
}
