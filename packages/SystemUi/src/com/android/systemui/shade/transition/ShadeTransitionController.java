package com.android.systemui.shade.transition;

import android.content.Context;
import android.content.res.Configuration;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.p013qs.InterfaceC1922QS;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeExpansionStateManagerKt;
import com.android.systemui.shade.ShadeStateListener;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.PrintWriter;
import kotlin.Function;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShadeTransitionController {
    public final Context context;
    public Integer currentPanelState;
    public boolean inSplitShade;
    public ShadeExpansionChangeEvent lastShadeExpansionChangeEvent;

    /* renamed from: qs */
    public InterfaceC1922QS f344qs;
    public final ScrimShadeTransitionController scrimShadeTransitionController;
    public ShadeViewController shadeViewController;
    public final SysuiStatusBarStateController statusBarStateController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shade.transition.ShadeTransitionController$2 */
    public final /* synthetic */ class C24722 implements ShadeStateListener, FunctionAdapter {
        public C24722() {
        }

        public final boolean equals(Object obj) {
            if ((obj instanceof ShadeStateListener) && (obj instanceof FunctionAdapter)) {
                return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
            }
            return false;
        }

        @Override // kotlin.jvm.internal.FunctionAdapter
        public final Function getFunctionDelegate() {
            return new FunctionReferenceImpl(1, ShadeTransitionController.this, ShadeTransitionController.class, "onPanelStateChanged", "onPanelStateChanged(I)V", 0);
        }

        public final int hashCode() {
            return getFunctionDelegate().hashCode();
        }

        @Override // com.android.systemui.shade.ShadeStateListener
        public final void onPanelStateChanged(int i) {
            Integer valueOf = Integer.valueOf(i);
            ShadeTransitionController shadeTransitionController = ShadeTransitionController.this;
            shadeTransitionController.currentPanelState = valueOf;
            Integer valueOf2 = Integer.valueOf(i);
            ScrimShadeTransitionController scrimShadeTransitionController = shadeTransitionController.scrimShadeTransitionController;
            scrimShadeTransitionController.currentPanelState = valueOf2;
            scrimShadeTransitionController.onStateChanged();
        }
    }

    public ShadeTransitionController(ConfigurationController configurationController, ShadeExpansionStateManager shadeExpansionStateManager, DumpManager dumpManager, Context context, ScrimShadeTransitionController scrimShadeTransitionController, SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.context = context;
        this.scrimShadeTransitionController = scrimShadeTransitionController;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.inSplitShade = context.getResources().getBoolean(R.bool.config_use_split_notification_shade);
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.shade.transition.ShadeTransitionController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                ShadeTransitionController shadeTransitionController = ShadeTransitionController.this;
                shadeTransitionController.inSplitShade = shadeTransitionController.context.getResources().getBoolean(R.bool.config_use_split_notification_shade);
            }
        });
        ShadeExpansionChangeEvent addExpansionListener = shadeExpansionStateManager.addExpansionListener(new ShadeTransitionController$currentState$1(this));
        this.lastShadeExpansionChangeEvent = addExpansionListener;
        scrimShadeTransitionController.lastExpansionEvent = addExpansionListener;
        scrimShadeTransitionController.onStateChanged();
        shadeExpansionStateManager.stateListeners.add(new C24722());
        dumpManager.registerCriticalDumpable("ShadeTransitionController", new Dumpable() { // from class: com.android.systemui.shade.transition.ShadeTransitionController.3
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                ShadeTransitionController shadeTransitionController = ShadeTransitionController.this;
                boolean z = shadeTransitionController.inSplitShade;
                boolean z2 = ((StatusBarStateControllerImpl) shadeTransitionController.statusBarStateController).mUpcomingState == 0;
                Integer num = shadeTransitionController.currentPanelState;
                String panelStateToString = num != null ? ShadeExpansionStateManagerKt.panelStateToString(num.intValue()) : null;
                ShadeExpansionChangeEvent shadeExpansionChangeEvent = shadeTransitionController.lastShadeExpansionChangeEvent;
                boolean z3 = shadeTransitionController.f344qs != null;
                boolean z4 = shadeTransitionController.shadeViewController != null;
                StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("\n            ShadeTransitionController:\n                inSplitShade: ", z, "\n                isScreenUnlocked: ", z2, "\n                currentPanelState: ");
                m69m.append(panelStateToString);
                m69m.append("\n                lastPanelExpansionChangeEvent: ");
                m69m.append(shadeExpansionChangeEvent);
                m69m.append("\n                qs.isInitialized: ");
                m69m.append(z3);
                m69m.append("\n                npvc.isInitialized: ");
                m69m.append(z4);
                m69m.append("\n                nssl.isInitialized: false\n            ");
                printWriter.println(StringsKt__IndentKt.trimIndent(m69m.toString()));
            }
        });
    }
}
