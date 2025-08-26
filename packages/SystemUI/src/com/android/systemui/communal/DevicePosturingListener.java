package com.android.systemui.communal;

import android.app.DreamManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.common.domain.interactor.BatteryInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor;
import com.android.systemui.communal.posturing.shared.model.PosturedState;
import com.android.systemui.communal.shared.model.WhenToDream;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class DevicePosturingListener implements CoreStartable {
    public final DreamManager dreamManager;
    public final PosturingInteractor posturingInteractor;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class DevicePosturingCommand implements Command {
        public DevicePosturingCommand() {
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x0054  */
        @Override // com.android.systemui.statusbar.commandline.Command
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void execute(PrintWriter printWriter, List list) {
            Object obj;
            String str = (String) CollectionsKt___CollectionsKt.getOrNull(0, list);
            if (str != null) {
                Locale locale = Locale.ROOT;
                if (!Intrinsics.areEqual(str.toLowerCase(locale), "help")) {
                    String lowerCase = str.toLowerCase(locale);
                    int iHashCode = lowerCase.hashCode();
                    if (iHashCode != 3569038) {
                        if (iHashCode != 94746189) {
                            if (iHashCode == 97196323 && lowerCase.equals("false")) {
                                obj = PosturedState.NotPostured.INSTANCE;
                            } else {
                                printWriter.println("Invalid argument!");
                                printWriter.println("Usage: $ adb shell cmd statusbar device-postured <true|false|clear>");
                                obj = null;
                            }
                        } else if (lowerCase.equals("clear")) {
                            obj = PosturedState.Unknown.INSTANCE;
                        }
                    } else if (lowerCase.equals("true")) {
                        obj = PosturedState.Postured.INSTANCE;
                    }
                    if (obj != null) {
                        DevicePosturingListener.this.posturingInteractor.debugPostured.updateState(null, obj);
                        return;
                    }
                    return;
                }
            }
            printWriter.println("Usage: $ adb shell cmd statusbar device-postured <true|false|clear>");
        }
    }

    static {
        new Companion(null);
    }

    public DevicePosturingListener(CommandRegistry commandRegistry, DreamManager dreamManager, PosturingInteractor posturingInteractor, CommunalSettingsInteractor communalSettingsInteractor, BatteryInteractor batteryInteractor, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        this.dreamManager = dreamManager;
        this.posturingInteractor = posturingInteractor;
        new DevicePosturingCommand();
        BooleanFlowOperators booleanFlowOperators = BooleanFlowOperators.INSTANCE;
        ReadonlyStateFlow readonlyStateFlow = batteryInteractor.isDevicePluggedIn;
        final Flow flow = communalSettingsInteractor.whenToDream;
        LatestConflatedKt.flatMapLatestConflated(booleanFlowOperators.allOf(readonlyStateFlow, new Flow() { // from class: com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1

            /* renamed from: com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Boolean boolValueOf = Boolean.valueOf(((WhenToDream) obj) == WhenToDream.WHILE_POSTURED);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new DevicePosturingListener$postured$2(this, null));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
