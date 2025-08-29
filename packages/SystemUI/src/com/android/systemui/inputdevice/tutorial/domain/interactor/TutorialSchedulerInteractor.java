package com.android.systemui.inputdevice.tutorial.domain.interactor;

import android.os.SystemProperties;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.Preferences;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger;
import com.android.systemui.inputdevice.tutorial.InputDeviceTutorialLogger$$ExternalSyntheticLambda0;
import com.android.systemui.inputdevice.tutorial.data.repository.DeviceType;
import com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository;
import com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor;
import com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialCommand;
import com.android.systemui.keyboard.data.repository.KeyboardRepository;
import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import com.android.systemui.log.ConstantStringsLoggerImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.touchpad.data.repository.TouchpadRepository;
import com.android.systemui.touchpad.data.repository.TouchpadRepositoryImpl;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__LimitKt$drop$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
            int iHashCode = str.hashCode();
            TutorialSchedulerInteractor tutorialSchedulerInteractor = TutorialSchedulerInteractor.this;
            if (iHashCode != -1039689911) {
                if (iHashCode != 3237038) {
                    if (iHashCode == 94746189 && str.equals("clear")) {
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
                int iHashCode2 = str2.hashCode();
                if (iHashCode2 != -819522316) {
                    if (iHashCode2 != 3029889) {
                        if (iHashCode2 == 503739367 && str2.equals("keyboard")) {
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

    /* renamed from: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$updateLaunchInfo$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ TutorialType $tutorialType;
        int label;
        final /* synthetic */ TutorialSchedulerInteractor this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(TutorialType tutorialType, TutorialSchedulerInteractor tutorialSchedulerInteractor, Continuation continuation) {
            super(2, continuation);
            this.$tutorialType = tutorialType;
            this.this$0 = tutorialSchedulerInteractor;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$tutorialType, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x0095, code lost:
        
            if (r7 == r0) goto L29;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                TutorialType tutorialType = this.$tutorialType;
                if (tutorialType == TutorialType.KEYBOARD || tutorialType == TutorialType.BOTH) {
                    TutorialSchedulerRepository tutorialSchedulerRepository = this.this$0.repo;
                    DeviceType deviceType = DeviceType.KEYBOARD;
                    Instant instantNow = Instant.now();
                    this.label = 1;
                    tutorialSchedulerRepository.getClass();
                    Object objUpdateData = tutorialSchedulerRepository.updateData(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType.name(), "_LAUNCHED_TIME")), new Long(instantNow.getEpochSecond()), this);
                    if (objUpdateData != coroutineSingletons) {
                        objUpdateData = Unit.INSTANCE;
                    }
                    if (objUpdateData != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    if (i != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
                ResultKt.throwOnFailure(obj);
            }
            TutorialType tutorialType2 = this.$tutorialType;
            if (tutorialType2 == TutorialType.TOUCHPAD || tutorialType2 == TutorialType.BOTH) {
                TutorialSchedulerRepository tutorialSchedulerRepository2 = this.this$0.repo;
                DeviceType deviceType2 = DeviceType.TOUCHPAD;
                Instant instantNow2 = Instant.now();
                this.label = 2;
                tutorialSchedulerRepository2.getClass();
                Object objUpdateData2 = tutorialSchedulerRepository2.updateData(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType2.name(), "_LAUNCHED_TIME")), new Long(instantNow2.getEpochSecond()), this);
                if (objUpdateData2 != coroutineSingletons) {
                    objUpdateData2 = Unit.INSTANCE;
                }
            }
            return Unit.INSTANCE;
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        DEFAULT_LAUNCH_DELAY_SEC = Duration.m3464toLongimpl(DurationKt.toDuration(72, DurationUnit.HOURS), DurationUnit.SECONDS);
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
                return this.f$0.new TutorialCommand();
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Pair pair = (Pair) obj;
                        boolean zBooleanValue = ((Boolean) pair.component1()).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) pair.component2()).booleanValue();
                        TutorialSchedulerInteractor.TutorialType tutorialType = (zBooleanValue && zBooleanValue2) ? TutorialSchedulerInteractor.TutorialType.BOTH : zBooleanValue ? TutorialSchedulerInteractor.TutorialType.KEYBOARD : zBooleanValue2 ? TutorialSchedulerInteractor.TutorialType.TOUCHPAD : TutorialSchedulerInteractor.TutorialType.NONE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(tutorialType, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.commandTutorials = StateFlowKt.MutableStateFlow(TutorialType.NONE);
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(safeFlow, safeFlow2);
        this.tutorials = new Flow() { // from class: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$special$$inlined$map$2

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

                /* JADX WARN: Code restructure failed: missing block: B:34:0x00c8, code lost:
                
                    if (r2 == r1) goto L48;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:47:0x013a, code lost:
                
                    if (r12.emit(r11, r0) == r1) goto L48;
                 */
                /* JADX WARN: Removed duplicated region for block: B:43:0x0105  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    AnonymousClass2 anonymousClass2;
                    TutorialSchedulerInteractor.TutorialType tutorialType;
                    Object objUpdateData;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        anonymousClass1.L$0 = this;
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$1 = flowCollector2;
                        anonymousClass1.label = 1;
                        Object objAccess$resolveTutorialType = TutorialSchedulerInteractor.access$resolveTutorialType(this.this$0, (DeviceType) obj, anonymousClass1);
                        if (objAccess$resolveTutorialType != coroutineSingletons) {
                            obj2 = objAccess$resolveTutorialType;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 == 1) {
                        FlowCollector flowCollector3 = (FlowCollector) anonymousClass1.L$1;
                        AnonymousClass2 anonymousClass22 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        flowCollector = flowCollector3;
                        this = anonymousClass22;
                    } else if (i2 == 2) {
                        tutorialType = (TutorialSchedulerInteractor.TutorialType) anonymousClass1.L$2;
                        flowCollector = (FlowCollector) anonymousClass1.L$1;
                        anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        if (tutorialType != TutorialSchedulerInteractor.TutorialType.TOUCHPAD || tutorialType == TutorialSchedulerInteractor.TutorialType.BOTH) {
                            TutorialSchedulerRepository tutorialSchedulerRepository = anonymousClass2.this$0.repo;
                            DeviceType deviceType = DeviceType.TOUCHPAD;
                            Instant instantNow = Instant.now();
                            anonymousClass1.L$0 = anonymousClass2;
                            anonymousClass1.L$1 = flowCollector;
                            anonymousClass1.L$2 = tutorialType;
                            anonymousClass1.label = 3;
                            tutorialSchedulerRepository.getClass();
                            objUpdateData = tutorialSchedulerRepository.updateData(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType.name(), "_NOTIFIED_TIME")), new Long(instantNow.getEpochSecond()), anonymousClass1);
                            if (objUpdateData != coroutineSingletons) {
                                objUpdateData = Unit.INSTANCE;
                            }
                            if (objUpdateData != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        InputDeviceTutorialLogger inputDeviceTutorialLogger = anonymousClass2.this$0.logger;
                        inputDeviceTutorialLogger.getClass();
                        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(5);
                        LogLevel logLevel = LogLevel.INFO;
                        LogBuffer logBuffer = inputDeviceTutorialLogger.buffer;
                        LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
                        ((LogMessageImpl) logMessageObtain).str1 = tutorialType.toString();
                        logBuffer.commit(logMessageObtain);
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.label = 4;
                    } else {
                        if (i2 != 3) {
                            if (i2 != 4) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                            return Unit.INSTANCE;
                        }
                        tutorialType = (TutorialSchedulerInteractor.TutorialType) anonymousClass1.L$2;
                        flowCollector = (FlowCollector) anonymousClass1.L$1;
                        anonymousClass2 = (AnonymousClass2) anonymousClass1.L$0;
                        ResultKt.throwOnFailure(obj2);
                        InputDeviceTutorialLogger inputDeviceTutorialLogger2 = anonymousClass2.this$0.logger;
                        inputDeviceTutorialLogger2.getClass();
                        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda02 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(5);
                        LogLevel logLevel2 = LogLevel.INFO;
                        LogBuffer logBuffer2 = inputDeviceTutorialLogger2.buffer;
                        LogMessage logMessageObtain2 = logBuffer2.obtain("InputDeviceTutorial", logLevel2, inputDeviceTutorialLogger$$ExternalSyntheticLambda02, null);
                        ((LogMessageImpl) logMessageObtain2).str1 = tutorialType.toString();
                        logBuffer2.commit(logMessageObtain2);
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.label = 4;
                    }
                    TutorialSchedulerInteractor.TutorialType tutorialType2 = (TutorialSchedulerInteractor.TutorialType) obj2;
                    if (tutorialType2 == TutorialSchedulerInteractor.TutorialType.KEYBOARD || tutorialType2 == TutorialSchedulerInteractor.TutorialType.BOTH) {
                        TutorialSchedulerRepository tutorialSchedulerRepository2 = this.this$0.repo;
                        DeviceType deviceType2 = DeviceType.KEYBOARD;
                        Instant instantNow2 = Instant.now();
                        anonymousClass1.L$0 = this;
                        anonymousClass1.L$1 = flowCollector;
                        anonymousClass1.L$2 = tutorialType2;
                        anonymousClass1.label = 2;
                        tutorialSchedulerRepository2.getClass();
                        Object objUpdateData2 = tutorialSchedulerRepository2.updateData(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType2.name(), "_NOTIFIED_TIME")), new Long(instantNow2.getEpochSecond()), anonymousClass1);
                        if (objUpdateData2 != coroutineSingletons) {
                            objUpdateData2 = Unit.INSTANCE;
                        }
                    }
                    anonymousClass2 = this;
                    tutorialType = tutorialType2;
                    if (tutorialType != TutorialSchedulerInteractor.TutorialType.TOUCHPAD) {
                    }
                    TutorialSchedulerRepository tutorialSchedulerRepository3 = anonymousClass2.this$0.repo;
                    DeviceType deviceType3 = DeviceType.TOUCHPAD;
                    Instant instantNow3 = Instant.now();
                    anonymousClass1.L$0 = anonymousClass2;
                    anonymousClass1.L$1 = flowCollector;
                    anonymousClass1.L$2 = tutorialType;
                    anonymousClass1.label = 3;
                    tutorialSchedulerRepository3.getClass();
                    objUpdateData = tutorialSchedulerRepository3.updateData(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType3.name(), "_NOTIFIED_TIME")), new Long(instantNow3.getEpochSecond()), anonymousClass1);
                    if (objUpdateData != coroutineSingletons) {
                    }
                    if (objUpdateData != coroutineSingletons) {
                        InputDeviceTutorialLogger inputDeviceTutorialLogger22 = anonymousClass2.this$0.logger;
                        inputDeviceTutorialLogger22.getClass();
                        InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda022 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(5);
                        LogLevel logLevel22 = LogLevel.INFO;
                        LogBuffer logBuffer22 = inputDeviceTutorialLogger22.buffer;
                        LogMessage logMessageObtain22 = logBuffer22.obtain("InputDeviceTutorial", logLevel22, inputDeviceTutorialLogger$$ExternalSyntheticLambda022, null);
                        ((LogMessageImpl) logMessageObtain22).str1 = tutorialType.toString();
                        logBuffer22.commit(logMessageObtain22);
                        anonymousClass1.L$0 = null;
                        anonymousClass1.L$1 = null;
                        anonymousClass1.L$2 = null;
                        anonymousClass1.label = 4;
                    }
                    return coroutineSingletons;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$resolveTutorialType(TutorialSchedulerInteractor tutorialSchedulerInteractor, DeviceType deviceType, ContinuationImpl continuationImpl) {
        TutorialSchedulerInteractor$resolveTutorialType$1 tutorialSchedulerInteractor$resolveTutorialType$1;
        TutorialSchedulerInteractor tutorialSchedulerInteractor2;
        DeviceType deviceType2;
        Object objIsNotified;
        boolean z;
        tutorialSchedulerInteractor.getClass();
        if (continuationImpl instanceof TutorialSchedulerInteractor$resolveTutorialType$1) {
            tutorialSchedulerInteractor$resolveTutorialType$1 = (TutorialSchedulerInteractor$resolveTutorialType$1) continuationImpl;
            int i = tutorialSchedulerInteractor$resolveTutorialType$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                tutorialSchedulerInteractor$resolveTutorialType$1.label = i - Integer.MIN_VALUE;
            } else {
                tutorialSchedulerInteractor$resolveTutorialType$1 = new TutorialSchedulerInteractor$resolveTutorialType$1(tutorialSchedulerInteractor, continuationImpl);
            }
        }
        Object objIsNotified2 = tutorialSchedulerInteractor$resolveTutorialType$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = tutorialSchedulerInteractor$resolveTutorialType$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objIsNotified2);
            tutorialSchedulerInteractor$resolveTutorialType$1.L$0 = tutorialSchedulerInteractor;
            tutorialSchedulerInteractor$resolveTutorialType$1.L$1 = deviceType;
            tutorialSchedulerInteractor$resolveTutorialType$1.label = 1;
            objIsNotified2 = tutorialSchedulerInteractor.repo.isNotified(deviceType, tutorialSchedulerInteractor$resolveTutorialType$1);
            if (objIsNotified2 != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                z = tutorialSchedulerInteractor$resolveTutorialType$1.Z$0;
                deviceType = (DeviceType) tutorialSchedulerInteractor$resolveTutorialType$1.L$0;
                ResultKt.throwOnFailure(objIsNotified2);
                return (((Boolean) objIsNotified2).booleanValue() && z) ? TutorialType.BOTH : deviceType != DeviceType.KEYBOARD ? TutorialType.KEYBOARD : TutorialType.TOUCHPAD;
            }
            deviceType2 = (DeviceType) tutorialSchedulerInteractor$resolveTutorialType$1.L$2;
            deviceType = (DeviceType) tutorialSchedulerInteractor$resolveTutorialType$1.L$1;
            tutorialSchedulerInteractor2 = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$resolveTutorialType$1.L$0;
            ResultKt.throwOnFailure(objIsNotified2);
            boolean zBooleanValue = ((Boolean) objIsNotified2).booleanValue();
            TutorialSchedulerRepository tutorialSchedulerRepository = tutorialSchedulerInteractor2.repo;
            tutorialSchedulerInteractor$resolveTutorialType$1.L$0 = deviceType;
            tutorialSchedulerInteractor$resolveTutorialType$1.L$1 = null;
            tutorialSchedulerInteractor$resolveTutorialType$1.L$2 = null;
            tutorialSchedulerInteractor$resolveTutorialType$1.Z$0 = zBooleanValue;
            tutorialSchedulerInteractor$resolveTutorialType$1.label = 3;
            objIsNotified = tutorialSchedulerRepository.isNotified(deviceType2, tutorialSchedulerInteractor$resolveTutorialType$1);
            if (objIsNotified != coroutineSingletons) {
                objIsNotified2 = objIsNotified;
                z = zBooleanValue;
                if (((Boolean) objIsNotified2).booleanValue()) {
                }
            }
            return coroutineSingletons;
        }
        deviceType = (DeviceType) tutorialSchedulerInteractor$resolveTutorialType$1.L$1;
        tutorialSchedulerInteractor = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$resolveTutorialType$1.L$0;
        ResultKt.throwOnFailure(objIsNotified2);
        if (((Boolean) objIsNotified2).booleanValue()) {
            return TutorialType.NONE;
        }
        DeviceType deviceType3 = DeviceType.KEYBOARD;
        if (deviceType == deviceType3) {
            deviceType3 = DeviceType.TOUCHPAD;
        }
        Object obj = tutorialSchedulerInteractor.isAnyDeviceConnected.get(deviceType3);
        obj.getClass();
        tutorialSchedulerInteractor$resolveTutorialType$1.L$0 = tutorialSchedulerInteractor;
        tutorialSchedulerInteractor$resolveTutorialType$1.L$1 = deviceType;
        tutorialSchedulerInteractor$resolveTutorialType$1.L$2 = deviceType3;
        tutorialSchedulerInteractor$resolveTutorialType$1.label = 2;
        Object objFirst = FlowKt.first((Flow) obj, tutorialSchedulerInteractor$resolveTutorialType$1);
        if (objFirst != coroutineSingletons) {
            tutorialSchedulerInteractor2 = tutorialSchedulerInteractor;
            deviceType2 = deviceType3;
            objIsNotified2 = objFirst;
            boolean zBooleanValue2 = ((Boolean) objIsNotified2).booleanValue();
            TutorialSchedulerRepository tutorialSchedulerRepository2 = tutorialSchedulerInteractor2.repo;
            tutorialSchedulerInteractor$resolveTutorialType$1.L$0 = deviceType;
            tutorialSchedulerInteractor$resolveTutorialType$1.L$1 = null;
            tutorialSchedulerInteractor$resolveTutorialType$1.L$2 = null;
            tutorialSchedulerInteractor$resolveTutorialType$1.Z$0 = zBooleanValue2;
            tutorialSchedulerInteractor$resolveTutorialType$1.label = 3;
            objIsNotified = tutorialSchedulerRepository2.isNotified(deviceType2, tutorialSchedulerInteractor$resolveTutorialType$1);
            if (objIsNotified != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x012a, code lost:
    
        if (r12 != r1) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01d6, code lost:
    
        if (kotlinx.coroutines.flow.FlowKt.first(new com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$waitForDeviceConnection$$inlined$filter$1(r10), r0) != r1) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0131 A[PHI: r10 r11
      0x0131: PHI (r10v9 com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor) = 
      (r10v1 com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor)
      (r10v11 com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor)
     binds: [B:22:0x0090, B:33:0x012e] A[DONT_GENERATE, DONT_INLINE]
      0x0131: PHI (r11v8 com.android.systemui.inputdevice.tutorial.data.repository.DeviceType) = 
      (r11v1 com.android.systemui.inputdevice.tutorial.data.repository.DeviceType)
      (r11v10 com.android.systemui.inputdevice.tutorial.data.repository.DeviceType)
     binds: [B:22:0x0090, B:33:0x012e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$schedule(TutorialSchedulerInteractor tutorialSchedulerInteractor, DeviceType deviceType, ContinuationImpl continuationImpl) {
        TutorialSchedulerInteractor$schedule$1 tutorialSchedulerInteractor$schedule$1;
        TutorialSchedulerInteractor tutorialSchedulerInteractor2;
        DeviceType deviceType2;
        Object objUpdateData;
        TutorialSchedulerInteractor tutorialSchedulerInteractor3;
        long jM3460plusLRDsOJo;
        DeviceType deviceType3;
        TutorialSchedulerInteractor tutorialSchedulerInteractor4;
        tutorialSchedulerInteractor.getClass();
        if (continuationImpl instanceof TutorialSchedulerInteractor$schedule$1) {
            tutorialSchedulerInteractor$schedule$1 = (TutorialSchedulerInteractor$schedule$1) continuationImpl;
            int i = tutorialSchedulerInteractor$schedule$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                tutorialSchedulerInteractor$schedule$1.label = i - Integer.MIN_VALUE;
            } else {
                tutorialSchedulerInteractor$schedule$1 = new TutorialSchedulerInteractor$schedule$1(tutorialSchedulerInteractor, continuationImpl);
            }
        }
        Object objWasEverConnected = tutorialSchedulerInteractor$schedule$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        switch (tutorialSchedulerInteractor$schedule$1.label) {
            case 0:
                ResultKt.throwOnFailure(objWasEverConnected);
                tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor;
                tutorialSchedulerInteractor$schedule$1.L$1 = deviceType;
                tutorialSchedulerInteractor$schedule$1.label = 1;
                objWasEverConnected = tutorialSchedulerInteractor.repo.wasEverConnected(deviceType, tutorialSchedulerInteractor$schedule$1);
                if (objWasEverConnected != coroutineSingletons) {
                    if (!((Boolean) objWasEverConnected).booleanValue()) {
                        TutorialSchedulerRepository tutorialSchedulerRepository = tutorialSchedulerInteractor.repo;
                        tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor;
                        tutorialSchedulerInteractor$schedule$1.L$1 = deviceType;
                        tutorialSchedulerInteractor$schedule$1.L$2 = tutorialSchedulerInteractor;
                        tutorialSchedulerInteractor$schedule$1.label = 4;
                        objWasEverConnected = tutorialSchedulerRepository.getFirstConnectionTime(deviceType, tutorialSchedulerInteractor$schedule$1);
                        if (objWasEverConnected != coroutineSingletons) {
                            tutorialSchedulerInteractor3 = tutorialSchedulerInteractor;
                            objWasEverConnected.getClass();
                            tutorialSchedulerInteractor.getClass();
                            java.time.Duration durationBetween = java.time.Duration.between((Instant) objWasEverConnected, Instant.now());
                            Companion.getClass();
                            java.time.Duration durationMinus = java.time.Duration.ofSeconds(SystemProperties.getLong("persist.peripheral_tutorial_delay_sec", DEFAULT_LAUNCH_DELAY_SEC)).minus(durationBetween);
                            long seconds = durationMinus.getSeconds();
                            DurationUnit durationUnit = DurationUnit.SECONDS;
                            jM3460plusLRDsOJo = Duration.m3460plusLRDsOJo(DurationKt.toDuration(seconds, durationUnit), DurationKt.toDuration(durationMinus.getNano(), DurationUnit.NANOSECONDS));
                            InputDeviceTutorialLogger inputDeviceTutorialLogger = tutorialSchedulerInteractor3.logger;
                            String str = "Tutorial is scheduled in " + Duration.m3464toLongimpl(jM3460plusLRDsOJo, durationUnit) + " seconds";
                            ConstantStringsLoggerImpl constantStringsLoggerImpl = inputDeviceTutorialLogger.$$delegate_0;
                            constantStringsLoggerImpl.getClass();
                            LogBuffer.log$default(constantStringsLoggerImpl.buffer, constantStringsLoggerImpl.tag, LogLevel.DEBUG, str);
                            tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor3;
                            tutorialSchedulerInteractor$schedule$1.L$1 = deviceType;
                            tutorialSchedulerInteractor$schedule$1.L$2 = null;
                            tutorialSchedulerInteractor$schedule$1.label = 5;
                            if (DelayKt.m3468delayVtjQ1oo(jM3460plusLRDsOJo, tutorialSchedulerInteractor$schedule$1) != coroutineSingletons) {
                                deviceType3 = deviceType;
                                tutorialSchedulerInteractor4 = tutorialSchedulerInteractor3;
                                tutorialSchedulerInteractor$schedule$1.L$0 = null;
                                tutorialSchedulerInteractor$schedule$1.L$1 = null;
                                tutorialSchedulerInteractor$schedule$1.label = 6;
                                Object obj = tutorialSchedulerInteractor4.isAnyDeviceConnected.get(deviceType3);
                                obj.getClass();
                                final Flow flow = (Flow) obj;
                                break;
                            }
                        }
                    } else {
                        ConstantStringsLoggerImpl constantStringsLoggerImpl2 = tutorialSchedulerInteractor.logger.$$delegate_0;
                        constantStringsLoggerImpl2.getClass();
                        LogBuffer.log$default(constantStringsLoggerImpl2.buffer, constantStringsLoggerImpl2.tag, LogLevel.DEBUG, "Waiting for " + deviceType + " to connect");
                        tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor;
                        tutorialSchedulerInteractor$schedule$1.L$1 = deviceType;
                        tutorialSchedulerInteractor$schedule$1.label = 2;
                        Object obj2 = tutorialSchedulerInteractor.isAnyDeviceConnected.get(deviceType);
                        obj2.getClass();
                        final Flow flow2 = (Flow) obj2;
                        if (FlowKt.first(new Flow() { // from class: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$waitForDeviceConnection$$inlined$filter$1

                            /* renamed from: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$waitForDeviceConnection$$inlined$filter$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$waitForDeviceConnection$$inlined$filter$1$2$1, reason: invalid class name */
                                public final class AnonymousClass1 extends ContinuationImpl {
                                    Object L$0;
                                    Object L$1;
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

                                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final Object emit(Object obj, Continuation continuation) {
                                    AnonymousClass1 anonymousClass1;
                                    if (continuation instanceof AnonymousClass1) {
                                        anonymousClass1 = (AnonymousClass1) continuation;
                                        int i = anonymousClass1.label;
                                        if ((i & Integer.MIN_VALUE) != 0) {
                                            anonymousClass1.label = i - Integer.MIN_VALUE;
                                        } else {
                                            anonymousClass1 = new AnonymousClass1(continuation);
                                        }
                                    }
                                    Object obj2 = anonymousClass1.result;
                                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                    int i2 = anonymousClass1.label;
                                    if (i2 == 0) {
                                        ResultKt.throwOnFailure(obj2);
                                        if (((Boolean) obj).booleanValue()) {
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        }
                                    } else {
                                        if (i2 != 1) {
                                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                        }
                                        ResultKt.throwOnFailure(obj2);
                                    }
                                    return Unit.INSTANCE;
                                }
                            }

                            @Override // kotlinx.coroutines.flow.Flow
                            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        }, tutorialSchedulerInteractor$schedule$1) != coroutineSingletons) {
                            DeviceType deviceType4 = deviceType;
                            tutorialSchedulerInteractor2 = tutorialSchedulerInteractor;
                            deviceType2 = deviceType4;
                            InputDeviceTutorialLogger inputDeviceTutorialLogger2 = tutorialSchedulerInteractor2.logger;
                            inputDeviceTutorialLogger2.getClass();
                            InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda0 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(9);
                            LogLevel logLevel = LogLevel.INFO;
                            LogBuffer logBuffer = inputDeviceTutorialLogger2.buffer;
                            LogMessage logMessageObtain = logBuffer.obtain("InputDeviceTutorial", logLevel, inputDeviceTutorialLogger$$ExternalSyntheticLambda0, null);
                            ((LogMessageImpl) logMessageObtain).str1 = deviceType2.toString();
                            logBuffer.commit(logMessageObtain);
                            Instant instantNow = Instant.now();
                            tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor2;
                            tutorialSchedulerInteractor$schedule$1.L$1 = deviceType2;
                            tutorialSchedulerInteractor$schedule$1.label = 3;
                            TutorialSchedulerRepository tutorialSchedulerRepository2 = tutorialSchedulerInteractor2.repo;
                            tutorialSchedulerRepository2.getClass();
                            objUpdateData = tutorialSchedulerRepository2.updateData(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType2.name(), "_CONNECTED_TIME")), new Long(instantNow.getEpochSecond()), tutorialSchedulerInteractor$schedule$1);
                            if (objUpdateData != coroutineSingletons) {
                                objUpdateData = Unit.INSTANCE;
                                break;
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                deviceType = (DeviceType) tutorialSchedulerInteractor$schedule$1.L$1;
                tutorialSchedulerInteractor = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$schedule$1.L$0;
                ResultKt.throwOnFailure(objWasEverConnected);
                if (!((Boolean) objWasEverConnected).booleanValue()) {
                }
                return coroutineSingletons;
            case 2:
                deviceType2 = (DeviceType) tutorialSchedulerInteractor$schedule$1.L$1;
                tutorialSchedulerInteractor2 = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$schedule$1.L$0;
                ResultKt.throwOnFailure(objWasEverConnected);
                InputDeviceTutorialLogger inputDeviceTutorialLogger22 = tutorialSchedulerInteractor2.logger;
                inputDeviceTutorialLogger22.getClass();
                InputDeviceTutorialLogger$$ExternalSyntheticLambda0 inputDeviceTutorialLogger$$ExternalSyntheticLambda02 = new InputDeviceTutorialLogger$$ExternalSyntheticLambda0(9);
                LogLevel logLevel2 = LogLevel.INFO;
                LogBuffer logBuffer2 = inputDeviceTutorialLogger22.buffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("InputDeviceTutorial", logLevel2, inputDeviceTutorialLogger$$ExternalSyntheticLambda02, null);
                ((LogMessageImpl) logMessageObtain2).str1 = deviceType2.toString();
                logBuffer2.commit(logMessageObtain2);
                Instant instantNow2 = Instant.now();
                tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor2;
                tutorialSchedulerInteractor$schedule$1.L$1 = deviceType2;
                tutorialSchedulerInteractor$schedule$1.label = 3;
                TutorialSchedulerRepository tutorialSchedulerRepository22 = tutorialSchedulerInteractor2.repo;
                tutorialSchedulerRepository22.getClass();
                objUpdateData = tutorialSchedulerRepository22.updateData(new Preferences.Key(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(deviceType2.name(), "_CONNECTED_TIME")), new Long(instantNow2.getEpochSecond()), tutorialSchedulerInteractor$schedule$1);
                if (objUpdateData != coroutineSingletons) {
                }
                break;
            case 3:
                deviceType2 = (DeviceType) tutorialSchedulerInteractor$schedule$1.L$1;
                tutorialSchedulerInteractor2 = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$schedule$1.L$0;
                ResultKt.throwOnFailure(objWasEverConnected);
                TutorialSchedulerInteractor tutorialSchedulerInteractor5 = tutorialSchedulerInteractor2;
                deviceType = deviceType2;
                tutorialSchedulerInteractor = tutorialSchedulerInteractor5;
                TutorialSchedulerRepository tutorialSchedulerRepository3 = tutorialSchedulerInteractor.repo;
                tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor;
                tutorialSchedulerInteractor$schedule$1.L$1 = deviceType;
                tutorialSchedulerInteractor$schedule$1.L$2 = tutorialSchedulerInteractor;
                tutorialSchedulerInteractor$schedule$1.label = 4;
                objWasEverConnected = tutorialSchedulerRepository3.getFirstConnectionTime(deviceType, tutorialSchedulerInteractor$schedule$1);
                if (objWasEverConnected != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                tutorialSchedulerInteractor = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$schedule$1.L$2;
                deviceType = (DeviceType) tutorialSchedulerInteractor$schedule$1.L$1;
                tutorialSchedulerInteractor3 = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$schedule$1.L$0;
                ResultKt.throwOnFailure(objWasEverConnected);
                objWasEverConnected.getClass();
                tutorialSchedulerInteractor.getClass();
                java.time.Duration durationBetween2 = java.time.Duration.between((Instant) objWasEverConnected, Instant.now());
                Companion.getClass();
                java.time.Duration durationMinus2 = java.time.Duration.ofSeconds(SystemProperties.getLong("persist.peripheral_tutorial_delay_sec", DEFAULT_LAUNCH_DELAY_SEC)).minus(durationBetween2);
                long seconds2 = durationMinus2.getSeconds();
                DurationUnit durationUnit2 = DurationUnit.SECONDS;
                jM3460plusLRDsOJo = Duration.m3460plusLRDsOJo(DurationKt.toDuration(seconds2, durationUnit2), DurationKt.toDuration(durationMinus2.getNano(), DurationUnit.NANOSECONDS));
                InputDeviceTutorialLogger inputDeviceTutorialLogger3 = tutorialSchedulerInteractor3.logger;
                String str2 = "Tutorial is scheduled in " + Duration.m3464toLongimpl(jM3460plusLRDsOJo, durationUnit2) + " seconds";
                ConstantStringsLoggerImpl constantStringsLoggerImpl3 = inputDeviceTutorialLogger3.$$delegate_0;
                constantStringsLoggerImpl3.getClass();
                LogBuffer.log$default(constantStringsLoggerImpl3.buffer, constantStringsLoggerImpl3.tag, LogLevel.DEBUG, str2);
                tutorialSchedulerInteractor$schedule$1.L$0 = tutorialSchedulerInteractor3;
                tutorialSchedulerInteractor$schedule$1.L$1 = deviceType;
                tutorialSchedulerInteractor$schedule$1.L$2 = null;
                tutorialSchedulerInteractor$schedule$1.label = 5;
                if (DelayKt.m3468delayVtjQ1oo(jM3460plusLRDsOJo, tutorialSchedulerInteractor$schedule$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                deviceType3 = (DeviceType) tutorialSchedulerInteractor$schedule$1.L$1;
                tutorialSchedulerInteractor4 = (TutorialSchedulerInteractor) tutorialSchedulerInteractor$schedule$1.L$0;
                ResultKt.throwOnFailure(objWasEverConnected);
                tutorialSchedulerInteractor$schedule$1.L$0 = null;
                tutorialSchedulerInteractor$schedule$1.L$1 = null;
                tutorialSchedulerInteractor$schedule$1.label = 6;
                Object obj3 = tutorialSchedulerInteractor4.isAnyDeviceConnected.get(deviceType3);
                obj3.getClass();
                final Flow flow3 = (Flow) obj3;
                break;
            case 6:
                ResultKt.throwOnFailure(objWasEverConnected);
                return Unit.INSTANCE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    public final void updateLaunchInfo(TutorialType tutorialType) {
        BuildersKt.launch$default(this.backgroundScope, null, null, new AnonymousClass1(tutorialType, this, null), 3);
    }
}
