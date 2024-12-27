package com.android.systemui.statusbar.pipeline.mobile.ui;

import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import java.io.PrintWriter;
import java.util.List;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MobileUiAdapter implements CoreStartable {
    public final StatusBarIconController iconController;
    public boolean isCollecting;
    public List lastValue;
    public final MobileViewLogger logger;
    public final MobileIconsViewModel mobileIconsViewModel;
    public final CoroutineScope scope;

    public MobileUiAdapter(StatusBarIconController statusBarIconController, MobileIconsViewModel mobileIconsViewModel, MobileViewLogger mobileViewLogger, CoroutineScope coroutineScope) {
        this.iconController = statusBarIconController;
        this.mobileIconsViewModel = mobileIconsViewModel;
        this.logger = mobileViewLogger;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ActiveUnlockConfig$$ExternalSyntheticOutline0.m("isCollecting=", this.isCollecting, printWriter);
        printWriter.println("Last values sent to icon controller: " + this.lastValue);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        BuildersKt.launch$default(this.scope, null, null, new MobileUiAdapter$start$1(this, null), 3);
    }
}
