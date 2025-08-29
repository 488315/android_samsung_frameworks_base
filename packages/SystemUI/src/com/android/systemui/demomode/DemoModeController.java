package com.android.systemui.demomode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class DemoModeController implements CallbackController, Dumpable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BroadcastDispatcher broadcastDispatcher;
    public final DemoModeController$broadcastReceiver$1 broadcastReceiver;
    public final Context context;
    public final DumpManager dumpManager;
    public final GlobalSettings globalSettings;
    public boolean initialized;
    public final Map receiverMap;
    public final List receivers = new ArrayList();
    public final DemoModeController$tracker$1 tracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $command;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, Continuation continuation) {
            super(2, continuation);
            this.$command = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = DemoModeController.this.new AnonymousClass1(this.$command, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.demomode.DemoMode, com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1$callback$1] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final String str = this.$command;
                final ?? r1 = new DemoMode() { // from class: com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1$callback$1
                    @Override // com.android.systemui.demomode.DemoMode
                    public final List demoCommands() {
                        return Collections.singletonList(str);
                    }

                    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                    public final void dispatchDemoCommand(Bundle bundle, String str2) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(bundle);
                    }
                };
                DemoModeController.this.addCallback((DemoMode) r1);
                final DemoModeController demoModeController = DemoModeController.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        demoModeController.removeCallback((DemoMode) r1);
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

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.demomode.DemoModeController$broadcastReceiver$1] */
    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.demomode.DemoModeController$tracker$1] */
    public DemoModeController(Context context, DumpManager dumpManager, GlobalSettings globalSettings, BroadcastDispatcher broadcastDispatcher) {
        this.context = context;
        this.dumpManager = dumpManager;
        this.globalSettings = globalSettings;
        this.broadcastDispatcher = broadcastDispatcher;
        globalSettings.putInt("sysui_tuner_demo_on", 0);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List<String> list = DemoMode.COMMANDS;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        for (String str : list) {
            str.getClass();
            arrayList.add((List) linkedHashMap.put(str, new ArrayList()));
        }
        this.receiverMap = linkedHashMap;
        final Context context2 = this.context;
        final GlobalSettings globalSettings2 = this.globalSettings;
        this.tracker = new DemoModeAvailabilityTracker(context2, globalSettings2) { // from class: com.android.systemui.demomode.DemoModeController$tracker$1
            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public final void onDemoModeAvailabilityChanged() {
                int i = DemoModeController.$r8$clinit;
                this.this$0.getClass();
            }

            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public final void onDemoModeFinished() {
                DemoModeController demoModeController = this.this$0;
                demoModeController.getClass();
                if (this.isInDemoMode) {
                    demoModeController.exitDemoMode$1();
                }
            }

            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public final void onDemoModeStarted() {
                this.this$0.getClass();
            }
        };
        this.broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.demomode.DemoModeController$broadcastReceiver$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context3, Intent intent) {
                Bundle extras;
                if ("com.android.systemui.demo".equals(intent.getAction()) && (extras = intent.getExtras()) != null) {
                    String lowerCase = StringsKt__StringsKt.trim(extras.getString("command", "")).toString().toLowerCase(Locale.ROOT);
                    if (lowerCase.length() == 0) {
                        return;
                    }
                    try {
                        this.this$0.dispatchDemoCommand(extras, lowerCase);
                    } catch (Throwable th) {
                        Log.w("DemoModeController", "Error running demo command, intent=" + intent + " " + th);
                    }
                }
            }
        };
    }

    public final Flow demoFlowForCommand() {
        return FlowConflatedKt.conflatedCallbackFlow(new AnonymousClass1("network", null));
    }

    public final void dispatchDemoCommand(Bundle bundle, String str) {
        Assert.isMainThread();
        if (this.tracker.isDemoModeAvailable) {
            if (!Intrinsics.areEqual(str, "enter") && Intrinsics.areEqual(str, "exit")) {
                exitDemoMode$1();
            }
            Object obj = ((LinkedHashMap) this.receiverMap).get(str);
            obj.getClass();
            Iterator it = ((Iterable) obj).iterator();
            while (it.hasNext()) {
                ((DemoMode) it.next()).dispatchDemoCommand(bundle, str);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) throws IOException {
        List list;
        printWriter.println("DemoModeController state -");
        printWriter.println("  isInDemoMode=false");
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m(printWriter, "  isDemoModeAllowed=", this.tracker.isDemoModeAvailable);
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
            Unit unit = Unit.INSTANCE;
        }
        List list2 = list;
        final int i = 0;
        printWriter.println("  receivers=[" + CollectionsKt___CollectionsKt.joinToString$default(list2, ", ", null, null, new Function1() { // from class: com.android.systemui.demomode.DemoModeController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                DemoMode demoMode = (DemoMode) obj;
                switch (i) {
                    case 0:
                        int i2 = DemoModeController.$r8$clinit;
                        if (!demoMode.getClass().isAnonymousClass()) {
                            break;
                        } else {
                            break;
                        }
                    default:
                        int i3 = DemoModeController.$r8$clinit;
                        if (!demoMode.getClass().isAnonymousClass()) {
                            break;
                        } else {
                            break;
                        }
                }
                return demoMode.getClass().getSimpleName();
            }
        }, 30) + "]");
        printWriter.println("  receiverMap= [");
        for (Map.Entry entry : ((LinkedHashMap) this.receiverMap).entrySet()) {
            String str = (String) entry.getKey();
            List list3 = (List) entry.getValue();
            final int i2 = 1;
            printWriter.println(MotionLayout$$ExternalSyntheticOutline0.m("    ", str, " : [", CollectionsKt___CollectionsKt.joinToString$default(list3, ", ", null, null, new Function1() { // from class: com.android.systemui.demomode.DemoModeController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    DemoMode demoMode = (DemoMode) obj;
                    switch (i2) {
                        case 0:
                            int i22 = DemoModeController.$r8$clinit;
                            if (!demoMode.getClass().isAnonymousClass()) {
                                break;
                            } else {
                                break;
                            }
                        default:
                            int i3 = DemoModeController.$r8$clinit;
                            if (!demoMode.getClass().isAnonymousClass()) {
                                break;
                            } else {
                                break;
                            }
                    }
                    return demoMode.getClass().getSimpleName();
                }
            }, 30), "]"));
        }
        printWriter.println(" ]");
    }

    public final void exitDemoMode$1() {
        List list;
        Assert.isMainThread();
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
            Unit unit = Unit.INSTANCE;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((DemoModeCommandReceiver) it.next()).onDemoModeFinished();
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(DemoMode demoMode) {
        List<String> listDemoCommands = demoMode.demoCommands();
        listDemoCommands.getClass();
        for (String str : listDemoCommands) {
            if (!this.receiverMap.containsKey(str)) {
                throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Command (", str, ") not recognized. See DemoMode.java for valid commands"));
            }
            Object obj = ((LinkedHashMap) this.receiverMap).get(str);
            obj.getClass();
            ((List) obj).add(demoMode);
        }
        synchronized (this) {
            ((ArrayList) this.receivers).add(demoMode);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(DemoMode demoMode) {
        synchronized (this) {
            try {
                Iterator it = demoMode.demoCommands().iterator();
                while (it.hasNext()) {
                    Object obj = ((LinkedHashMap) this.receiverMap).get((String) it.next());
                    obj.getClass();
                    ((List) obj).remove(demoMode);
                }
                ((ArrayList) this.receivers).remove(demoMode);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
