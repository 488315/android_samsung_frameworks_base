package com.android.systemui.communal;

import android.app.DreamManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.common.domain.interactor.BatteryInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.communal.posturing.domain.interactor.PosturingInteractor;
import com.android.systemui.communal.posturing.shared.model.PosturedState;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.util.kotlin.BooleanFlowOperators;
import com.android.systemui.utils.coroutines.flow.LatestConflatedKt;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DevicePosturingListener implements CoreStartable {
    public final DreamManager dreamManager;
    public final PosturingInteractor posturingInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DevicePosturingCommand implements Command {
        public DevicePosturingCommand() {
        }

        @Override // com.android.systemui.statusbar.commandline.Command
        public final void execute(PrintWriter printWriter, List list) {
            Object obj;
            String str = (String) CollectionsKt___CollectionsKt.getOrNull(0, list);
            if (str != null) {
                Locale locale = Locale.ROOT;
                if (!Intrinsics.areEqual(str.toLowerCase(locale), "help")) {
                    String lowerCase = str.toLowerCase(locale);
                    int hashCode = lowerCase.hashCode();
                    if (hashCode == 3569038) {
                        if (lowerCase.equals("true")) {
                            obj = PosturedState.Postured.INSTANCE;
                        }
                        printWriter.println("Invalid argument!");
                        printWriter.println("Usage: $ adb shell cmd statusbar device-postured <true|false|clear>");
                        obj = null;
                    } else if (hashCode != 94746189) {
                        if (hashCode == 97196323 && lowerCase.equals("false")) {
                            obj = PosturedState.NotPostured.INSTANCE;
                        }
                        printWriter.println("Invalid argument!");
                        printWriter.println("Usage: $ adb shell cmd statusbar device-postured <true|false|clear>");
                        obj = null;
                    } else {
                        if (lowerCase.equals("clear")) {
                            obj = PosturedState.Unknown.INSTANCE;
                        }
                        printWriter.println("Invalid argument!");
                        printWriter.println("Usage: $ adb shell cmd statusbar device-postured <true|false|clear>");
                        obj = null;
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

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1$2$1 r0 = (com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1$2$1 r0 = new com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.communal.shared.model.WhenToDream r5 = (com.android.systemui.communal.shared.model.WhenToDream) r5
                        com.android.systemui.communal.shared.model.WhenToDream r6 = com.android.systemui.communal.shared.model.WhenToDream.WHILE_POSTURED
                        if (r5 != r6) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.DevicePosturingListener$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new DevicePosturingListener$postured$2(this, null));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
