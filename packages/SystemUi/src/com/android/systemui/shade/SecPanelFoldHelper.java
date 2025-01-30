package com.android.systemui.shade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.keyguard.AbstractC0689x6838b71d;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.systemui.keyguard.DisplayLifecycle;
import java.util.function.BooleanSupplier;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecPanelFoldHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BooleanSupplier qsExpandedSupplier;

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

    public SecPanelFoldHelper(BooleanSupplier booleanSupplier) {
        this.qsExpandedSupplier = booleanSupplier;
        new DisplayLifecycle.Observer() { // from class: com.android.systemui.shade.SecPanelFoldHelper$displayLifecycleObserver$1
            public int isFolderOpened = -1;

            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                AbstractC0731x5bb8a836.m72m("onFolderStateChanged newFolderOpenState = ", z ? 1 : 0, ", isFolderOpened = ", z, "SecPanelFoldHelper");
                if (this.isFolderOpened == z) {
                    return;
                }
                this.isFolderOpened = z ? 1 : 0;
                String str = z ? !z ? "FOLD_UNKNOWN" : "FOLD_OPEN" : "FOLD_CLOSE";
                int i = SecPanelFoldHelper.$r8$clinit;
                SecPanelFoldHelper secPanelFoldHelper = SecPanelFoldHelper.this;
                secPanelFoldHelper.getClass();
                Log.d("SecPanelFoldHelper", "onFolderStateChanged(" + str + ") in KeyguardShowing:null, QsExpanded:" + secPanelFoldHelper.qsExpandedSupplier.getAsBoolean() + ", PanelExpanded:null, Tracking:null");
            }
        };
        new BroadcastReceiver() { // from class: com.android.systemui.shade.SecPanelFoldHelper$screenRatioListener$1
            public final String INTENT_ACTION = "com.samsung.intent.action.SET_SCREEN_RATIO_VALUE";

            {
                new IntentFilter("com.samsung.intent.action.SET_SCREEN_RATIO_VALUE");
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if (Intrinsics.areEqual(this.INTENT_ACTION, intent.getAction())) {
                    AbstractC0689x6838b71d.m68m("onReceive(", this.INTENT_ACTION, ")", "SecPanelFoldHelper");
                    SecPanelFoldHelper secPanelFoldHelper = SecPanelFoldHelper.this;
                    int i = SecPanelFoldHelper.$r8$clinit;
                    secPanelFoldHelper.getClass();
                }
            }
        };
    }
}
