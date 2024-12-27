package com.android.systemui.statusbar.connectivity.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.view.ContextThemeWrapper;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MobileContextProvider implements Dumpable, DemoMode {
    public static final Companion Companion = new Companion(null);
    public Integer demoMcc;
    public Integer demoMnc;
    public final DemoModeController demoModeController;
    public final Map subscriptions = new LinkedHashMap();

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public MobileContextProvider(NetworkController networkController, DumpManager dumpManager, DemoModeController demoModeController) {
        this.demoModeController = demoModeController;
        ((NetworkControllerImpl) networkController).addCallback(new SignalCallback() { // from class: com.android.systemui.statusbar.connectivity.ui.MobileContextProvider$signalCallback$1
            @Override // com.android.systemui.statusbar.connectivity.SignalCallback
            public final void setSubs(List list) {
                MobileContextProvider mobileContextProvider = MobileContextProvider.this;
                ((LinkedHashMap) mobileContextProvider.subscriptions).clear();
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
                    mobileContextProvider.subscriptions.put(Integer.valueOf(subscriptionInfo.getSubscriptionId()), subscriptionInfo);
                }
            }
        });
        dumpManager.registerDumpable(this);
        demoModeController.addCallback((DemoMode) this);
    }

    @Override // com.android.systemui.demomode.DemoMode
    public final List demoCommands() {
        return Collections.singletonList("network");
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void dispatchDemoCommand(Bundle bundle, String str) {
        String string = bundle.getString("mccmnc");
        if (string == null) {
            return;
        }
        if (string.length() == 5 || string.length() == 6) {
            this.demoMcc = Integer.valueOf(Integer.parseInt(string.subSequence(0, 3).toString()));
            this.demoMnc = Integer.valueOf(Integer.parseInt(string.subSequence(3, string.length()).toString()));
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("Subscriptions below will be inflated with a configuration context with MCC/MNC overrides");
        for (Map.Entry entry : ((LinkedHashMap) this.subscriptions).entrySet()) {
            int intValue = ((Number) entry.getKey()).intValue();
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) entry.getValue();
            int mcc = subscriptionInfo.getMcc();
            int mnc = subscriptionInfo.getMnc();
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(intValue, mcc, "  Subscription with subId(", ") with MCC/MNC(", "/");
            m.append(mnc);
            m.append(")");
            printWriter.println(m.toString());
        }
        Object obj = this.demoMcc;
        if (obj == null) {
            obj = "(none)";
        }
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  MCC override: ", obj, printWriter);
        Object obj2 = this.demoMnc;
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  MNC override: ", obj2 != null ? obj2 : "(none)", printWriter);
    }

    public final Context getMobileContextForSub(int i, Context context) {
        this.demoModeController.getClass();
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) ((LinkedHashMap) this.subscriptions).get(Integer.valueOf(i));
        if (subscriptionInfo == null) {
            return context;
        }
        int mcc = subscriptionInfo.getMcc();
        int mnc = subscriptionInfo.getMnc();
        Companion.getClass();
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.mcc = mcc;
        configuration.mnc = mnc;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, context.getTheme());
        contextThemeWrapper.applyOverrideConfiguration(configuration);
        return contextThemeWrapper;
    }

    @Override // com.android.systemui.demomode.DemoModeCommandReceiver
    public final void onDemoModeFinished() {
        this.demoMcc = null;
        this.demoMnc = null;
    }
}
