package com.android.systemui.scene.domain.startable;

import android.util.IndentingPrintWriter;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.AuthInteractionProperties;
import com.android.systemui.CoreStartable;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryHapticsInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.TrustInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.model.SysUiState;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.domain.interactor.DisabledContentInteractor;
import com.android.systemui.scene.domain.interactor.SceneBackInteractor;
import com.android.systemui.scene.domain.interactor.SceneContainerOcclusionInteractor;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.session.shared.SessionStorage;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.logger.SceneLogger;
import com.android.systemui.scene.shared.logger.SceneLogger$$ExternalSyntheticLambda0;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.DeviceProvisioningInteractor;
import com.android.systemui.util.DumpUtilsKt;
import com.google.android.msdl.domain.MSDLPlayer;
import dagger.Lazy;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SceneContainerStartable implements CoreStartable {
    public final AlternateBouncerInteractor alternateBouncerInteractor;
    public final Lazy authenticationInteractor;
    public final BouncerInteractor bouncerInteractor;
    public final Lazy centralSurfacesOptLazy;
    public final DeviceEntryHapticsInteractor deviceEntryHapticsInteractor;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DeviceProvisioningInteractor deviceProvisioningInteractor;
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final DisabledContentInteractor disabledContentInteractor;
    public final DismissCallbackRegistry dismissCallbackRegistry;
    public final DeviceEntryFaceAuthInteractor faceUnlockInteractor;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public final HeadsUpNotificationInteractor headsUpInteractor;
    public final KeyguardEnabledInteractor keyguardEnabledInteractor;
    public final KeyguardInteractor keyguardInteractor;
    public final SceneContainerOcclusionInteractor occlusionInteractor;
    public final PowerInteractor powerInteractor;
    public final SceneBackInteractor sceneBackInteractor;
    public final SceneInteractor sceneInteractor;
    public final SceneLogger sceneLogger;
    public final ShadeInteractor shadeInteractor;
    public final SessionStorage shadeSessionStorage;
    public final Lazy simBouncerInteractor;
    public final SysuiStatusBarStateController statusBarStateController;
    public final SysUiState sysUiState;
    public final TableLogBuffer tableLogBuffer;
    public final TrustInteractor trustInteractor;
    public final UiEventLogger uiEventLogger;
    public final VibratorHelper vibratorHelper;
    public final NotificationShadeWindowController windowController;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SceneContainerStartable(CoroutineScope coroutineScope, SceneInteractor sceneInteractor, DeviceEntryInteractor deviceEntryInteractor, DeviceEntryHapticsInteractor deviceEntryHapticsInteractor, DeviceUnlockedInteractor deviceUnlockedInteractor, BouncerInteractor bouncerInteractor, KeyguardInteractor keyguardInteractor, SysUiState sysUiState, SceneLogger sceneLogger, FalsingCollector falsingCollector, FalsingManager falsingManager, PowerInteractor powerInteractor, Lazy lazy, Lazy lazy2, NotificationShadeWindowController notificationShadeWindowController, DeviceProvisioningInteractor deviceProvisioningInteractor, Lazy lazy3, HeadsUpNotificationInteractor headsUpNotificationInteractor, SceneContainerOcclusionInteractor sceneContainerOcclusionInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, ShadeInteractor shadeInteractor, UiEventLogger uiEventLogger, SceneBackInteractor sceneBackInteractor, SessionStorage sessionStorage, KeyguardEnabledInteractor keyguardEnabledInteractor, DismissCallbackRegistry dismissCallbackRegistry, SysuiStatusBarStateController sysuiStatusBarStateController, AlternateBouncerInteractor alternateBouncerInteractor, VibratorHelper vibratorHelper, MSDLPlayer mSDLPlayer, DisabledContentInteractor disabledContentInteractor, ActivityTransitionAnimator activityTransitionAnimator, ShadeModeInteractor shadeModeInteractor, TableLogBuffer tableLogBuffer, TrustInteractor trustInteractor) {
        this.sceneInteractor = sceneInteractor;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.deviceEntryHapticsInteractor = deviceEntryHapticsInteractor;
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        this.bouncerInteractor = bouncerInteractor;
        this.keyguardInteractor = keyguardInteractor;
        this.sysUiState = sysUiState;
        this.sceneLogger = sceneLogger;
        this.falsingCollector = falsingCollector;
        this.falsingManager = falsingManager;
        this.powerInteractor = powerInteractor;
        this.simBouncerInteractor = lazy;
        this.authenticationInteractor = lazy2;
        this.windowController = notificationShadeWindowController;
        this.deviceProvisioningInteractor = deviceProvisioningInteractor;
        this.centralSurfacesOptLazy = lazy3;
        this.headsUpInteractor = headsUpNotificationInteractor;
        this.occlusionInteractor = sceneContainerOcclusionInteractor;
        this.faceUnlockInteractor = deviceEntryFaceAuthInteractor;
        this.shadeInteractor = shadeInteractor;
        this.uiEventLogger = uiEventLogger;
        this.sceneBackInteractor = sceneBackInteractor;
        this.shadeSessionStorage = sessionStorage;
        this.keyguardEnabledInteractor = keyguardEnabledInteractor;
        this.dismissCallbackRegistry = dismissCallbackRegistry;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.alternateBouncerInteractor = alternateBouncerInteractor;
        this.vibratorHelper = vibratorHelper;
        this.disabledContentInteractor = disabledContentInteractor;
        this.tableLogBuffer = tableLogBuffer;
        this.trustInteractor = trustInteractor;
        new AuthInteractionProperties(null, 1, null);
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.append("SceneContainerFlag").println(":");
        asIndenting.increaseIndent();
        try {
            asIndenting.append("Framework availability").println(":");
            asIndenting.increaseIndent();
            DumpUtilsKt.println(asIndenting, "isEnabled", Boolean.FALSE);
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
        String requirementDescription = SceneContainerFlag.requirementDescription();
        SceneLogger sceneLogger = this.sceneLogger;
        sceneLogger.getClass();
        LogLevel logLevel = LogLevel.WARNING;
        SceneLogger$$ExternalSyntheticLambda0 sceneLogger$$ExternalSyntheticLambda0 = new SceneLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = sceneLogger.logBuffer;
        LogMessage obtain = logBuffer.obtain("SceneFramework", logLevel, sceneLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = false;
        logMessageImpl.str1 = requirementDescription;
        logBuffer.commit(obtain);
    }
}
