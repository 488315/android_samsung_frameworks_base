package com.android.systemui.scene.domain.startable;

import android.util.IndentingPrintWriter;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.WindowManagerLockscreenVisibilityInteractor;
import com.android.systemui.model.SysUiState;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.session.shared.SessionStorage;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.DeviceProvisioningInteractor;
import com.android.systemui.util.DumpUtilsKt;
import dagger.Lazy;
import java.io.PrintWriter;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SceneContainerStartable implements CoreStartable {
    public final Lazy authenticationInteractor;
    public final BouncerInteractor bouncerInteractor;
    public final Lazy centralSurfacesOptLazy;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DeviceProvisioningInteractor deviceProvisioningInteractor;
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final int displayId;
    public final DeviceEntryFaceAuthInteractor faceUnlockInteractor;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public final HeadsUpNotificationInteractor headsUpInteractor;
    public final KeyguardInteractor keyguardInteractor;
    public final SceneContainerOcclusionInteractor occlusionInteractor;
    public final PowerInteractor powerInteractor;
    public final SceneBackInteractor sceneBackInteractor;
    public final SceneInteractor sceneInteractor;
    public final SceneLogger sceneLogger;
    public final ShadeInteractor shadeInteractor;
    public final SessionStorage shadeSessionStorage;
    public final Lazy simBouncerInteractor;
    public final SysUiState sysUiState;
    public final UiEventLogger uiEventLogger;
    public final NotificationShadeWindowController windowController;
    public final WindowManagerLockscreenVisibilityInteractor windowMgrLockscreenVisInteractor;

    public SceneContainerStartable(CoroutineScope coroutineScope, SceneInteractor sceneInteractor, DeviceEntryInteractor deviceEntryInteractor, DeviceUnlockedInteractor deviceUnlockedInteractor, BouncerInteractor bouncerInteractor, KeyguardInteractor keyguardInteractor, SysUiState sysUiState, int i, SceneLogger sceneLogger, FalsingCollector falsingCollector, FalsingManager falsingManager, PowerInteractor powerInteractor, Lazy lazy, Lazy lazy2, NotificationShadeWindowController notificationShadeWindowController, DeviceProvisioningInteractor deviceProvisioningInteractor, Lazy lazy3, HeadsUpNotificationInteractor headsUpNotificationInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, ShadeInteractor shadeInteractor, UiEventLogger uiEventLogger, SceneBackInteractor sceneBackInteractor, SessionStorage sessionStorage, WindowManagerLockscreenVisibilityInteractor windowManagerLockscreenVisibilityInteractor) {
        this.sceneLogger = sceneLogger;
        this.falsingManager = falsingManager;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.append("SceneContainerFlag").println(":");
        asIndenting.increaseIndent();
        try {
            Flags.sceneContainer();
            DumpUtilsKt.println(asIndenting, "isEnabled", Boolean.FALSE);
            asIndenting.append("requirementDescription").println(":");
            asIndenting.increaseIndent();
            asIndenting.println(SceneContainerFlag.requirementDescription());
            asIndenting.decreaseIndent();
        } catch (Throwable th) {
            throw th;
        } finally {
            asIndenting.decreaseIndent();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.sceneContainer();
        this.sceneLogger.logFrameworkEnabled(SceneContainerFlag.requirementDescription());
    }
}
