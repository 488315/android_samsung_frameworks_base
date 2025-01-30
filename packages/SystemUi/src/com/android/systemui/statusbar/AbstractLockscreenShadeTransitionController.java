package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.LargeScreenUtils;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AbstractLockscreenShadeTransitionController implements Dumpable, PanelScreenShotLogger.LogProvider {
    public final Context context;
    public float dragDownAmount;
    public boolean useSplitShade;

    public AbstractLockscreenShadeTransitionController(Context context, ConfigurationController configurationController, DumpManager dumpManager) {
        this.context = context;
        this.useSplitShade = LargeScreenUtils.shouldUseSplitNotificationShade(context.getResources());
        updateResources();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.AbstractLockscreenShadeTransitionController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                AbstractLockscreenShadeTransitionController abstractLockscreenShadeTransitionController = AbstractLockscreenShadeTransitionController.this;
                abstractLockscreenShadeTransitionController.useSplitShade = LargeScreenUtils.shouldUseSplitNotificationShade(abstractLockscreenShadeTransitionController.context.getResources());
                abstractLockscreenShadeTransitionController.updateResources();
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

    public abstract void onDragDownAmountChanged(float f);

    public final void setDragDownAmount(float f) {
        if (f == this.dragDownAmount) {
            return;
        }
        this.dragDownAmount = f;
        onDragDownAmountChanged(f);
    }

    public abstract void updateResources();
}
