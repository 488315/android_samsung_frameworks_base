package com.android.systemui.demomode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        globalSettings.putIntForUser(0, globalSettings.getUserId(), "sysui_tuner_demo_on");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        List list = DemoMode.COMMANDS;
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((List) linkedHashMap.put((String) it.next(), new ArrayList()));
        }
        this.receiverMap = linkedHashMap;
        final Context context2 = this.context;
        final GlobalSettings globalSettings2 = this.globalSettings;
        this.tracker = new DemoModeAvailabilityTracker(context2, globalSettings2) { // from class: com.android.systemui.demomode.DemoModeController$tracker$1
            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public final void onDemoModeAvailabilityChanged() {
                int i = DemoModeController.$r8$clinit;
                DemoModeController.this.getClass();
            }

            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public final void onDemoModeFinished() {
                DemoModeController demoModeController = DemoModeController.this;
                demoModeController.getClass();
                if (this.isInDemoMode) {
                    demoModeController.exitDemoMode();
                }
            }

            @Override // com.android.systemui.demomode.DemoModeAvailabilityTracker
            public final void onDemoModeStarted() {
                DemoModeController.this.getClass();
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
                        DemoModeController.this.dispatchDemoCommand(extras, lowerCase);
                    } catch (Throwable th) {
                        Log.w("DemoModeController", "Error running demo command, intent=" + intent + " " + th);
                    }
                }
            }
        };
    }

    public final Flow demoFlowForCommand() {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DemoModeController$demoFlowForCommand$1 demoModeController$demoFlowForCommand$1 = new DemoModeController$demoFlowForCommand$1(this, "network", null);
        conflatedCallbackFlow.getClass();
        return ConflatedCallbackFlow.conflatedCallbackFlow(demoModeController$demoFlowForCommand$1);
    }

    public final void dispatchDemoCommand(Bundle bundle, String str) {
        Assert.isMainThread();
        if (this.tracker.isDemoModeAvailable) {
            if (!Intrinsics.areEqual(str, "enter") && Intrinsics.areEqual(str, "exit")) {
                exitDemoMode();
            }
            Object obj = ((LinkedHashMap) this.receiverMap).get(str);
            Intrinsics.checkNotNull(obj);
            Iterator it = ((Iterable) obj).iterator();
            while (it.hasNext()) {
                ((DemoMode) it.next()).dispatchDemoCommand(bundle, str);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        List list;
        printWriter.println("DemoModeController state -");
        printWriter.println("  isInDemoMode=false");
        printWriter.println("  isDemoModeAllowed=" + this.tracker.isDemoModeAvailable);
        printWriter.print("  receivers=[");
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.receivers);
            Unit unit = Unit.INSTANCE;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            printWriter.print(" ".concat(((DemoModeCommandReceiver) it.next()).getClass().getSimpleName()));
        }
        printWriter.println(" ]");
        printWriter.println("  receiverMap= [");
        for (String str : ((LinkedHashMap) this.receiverMap).keySet()) {
            printWriter.print("    " + str + " : [");
            Object obj = ((LinkedHashMap) this.receiverMap).get(str);
            Intrinsics.checkNotNull(obj);
            Iterable iterable = (Iterable) obj;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(iterable, 10));
            Iterator it2 = iterable.iterator();
            while (it2.hasNext()) {
                arrayList.add(((DemoMode) it2.next()).getClass().getSimpleName());
            }
            printWriter.println(CollectionsKt___CollectionsKt.joinToString$default(arrayList, ",", null, null, null, 62).concat(" ]"));
        }
    }

    public final void exitDemoMode() {
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
        for (String str : demoMode.demoCommands()) {
            if (!this.receiverMap.containsKey(str)) {
                throw new IllegalStateException(PathParser$$ExternalSyntheticOutline0.m29m("Command (", str, ") not recognized. See DemoMode.java for valid commands"));
            }
            Object obj = ((LinkedHashMap) this.receiverMap).get(str);
            Intrinsics.checkNotNull(obj);
            ((List) obj).add(demoMode);
        }
        synchronized (this) {
            ((ArrayList) this.receivers).add(demoMode);
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(DemoMode demoMode) {
        synchronized (this) {
            Iterator it = demoMode.demoCommands().iterator();
            while (it.hasNext()) {
                Object obj = ((LinkedHashMap) this.receiverMap).get((String) it.next());
                Intrinsics.checkNotNull(obj);
                ((List) obj).remove(demoMode);
            }
            ((ArrayList) this.receivers).remove(demoMode);
        }
    }
}
