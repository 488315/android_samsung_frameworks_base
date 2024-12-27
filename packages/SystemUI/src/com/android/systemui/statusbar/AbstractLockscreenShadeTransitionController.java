package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.SplitShadeStateController;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class AbstractLockscreenShadeTransitionController implements Dumpable, PanelScreenShotLogger.LogProvider {
    public final Context context;
    public float dragDownAmount;
    public final SplitShadeStateController splitShadeStateController;

    public AbstractLockscreenShadeTransitionController(Context context, ConfigurationController configurationController, DumpManager dumpManager, SplitShadeStateController splitShadeStateController) {
        this.context = context;
        this.splitShadeStateController = splitShadeStateController;
        context.getResources();
        ((SplitShadeStateControllerImpl) splitShadeStateController).shouldUseSplitNotificationShade();
        updateResources$1();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                AbstractLockscreenShadeTransitionController abstractLockscreenShadeTransitionController = AbstractLockscreenShadeTransitionController.this;
                abstractLockscreenShadeTransitionController.context.getResources();
                ((SplitShadeStateControllerImpl) abstractLockscreenShadeTransitionController.splitShadeStateController).shouldUseSplitNotificationShade();
                abstractLockscreenShadeTransitionController.updateResources$1();
            }
        });
        dumpManager.registerDumpable(this);
        PanelScreenShotLogger.INSTANCE.addLogProvider("AbstractLockscreenShadeTransitionController", this);
    }

    public abstract void dump(IndentingPrintWriter indentingPrintWriter);

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        dump(new IndentingPrintWriter(printWriter, "  "));
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("AbstractLockscreenShadeTransitionController", arrayList);
        PanelScreenShotLogger.addLogItem(arrayList, "dragDownAmount", Float.valueOf(this.dragDownAmount));
        return arrayList;
    }

    public abstract void updateResources$1();
}
