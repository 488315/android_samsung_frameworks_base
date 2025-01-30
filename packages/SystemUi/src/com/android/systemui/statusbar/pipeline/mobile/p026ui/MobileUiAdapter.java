package com.android.systemui.statusbar.pipeline.mobile.p026ui;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.mobile.p026ui.viewmodel.MobileIconsViewModel;
import java.io.PrintWriter;
import java.util.List;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileUiAdapter implements CoreStartable {
    public final StatusBarIconController iconController;
    public boolean isCollecting;
    public List lastValue;
    public final MobileViewLogger logger;
    public final MobileIconsViewModel mobileIconsViewModel;
    public final CoroutineScope scope;
    public final StatusBarPipelineFlags statusBarPipelineFlags;

    public MobileUiAdapter(StatusBarIconController statusBarIconController, MobileIconsViewModel mobileIconsViewModel, MobileViewLogger mobileViewLogger, CoroutineScope coroutineScope, StatusBarPipelineFlags statusBarPipelineFlags) {
        this.iconController = statusBarIconController;
        this.mobileIconsViewModel = mobileIconsViewModel;
        this.logger = mobileViewLogger;
        this.scope = coroutineScope;
        this.statusBarPipelineFlags = statusBarPipelineFlags;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("isCollecting=", this.isCollecting, printWriter);
        printWriter.println("Last values sent to icon controller: " + this.lastValue);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.statusBarPipelineFlags.useNewMobileIcons();
        BuildersKt.launch$default(this.scope, null, null, new MobileUiAdapter$start$1(this, null), 3);
    }
}
