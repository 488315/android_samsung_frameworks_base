package com.android.systemui.volume;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.BroadcastReceiverType;
import com.android.systemui.volume.util.DeviceProvisionedWrapper;
import com.android.systemui.volume.util.DeviceStateManagerWrapper;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.volume.util.KeyguardUpdateMonitorWrapper$registerCallback$1;
import com.android.systemui.volume.util.SoundPoolWrapper;
import com.android.systemui.volume.util.SoundPoolWrapper$makeSound$1;
import com.android.systemui.volume.util.SystemServiceExtension;
import com.android.systemui.volume.util.VolumeExecutor;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.extensions.VolumeStateConverter;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumeStar;
import com.samsung.systemui.splugins.volume.VolumeState;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SamsungVolumeDialogImpl implements VolumeDialog {
    public final BroadcastReceiverManager broadcastReceiverManager;
    public final CoverUtilWrapper coverUtilWrapper;
    public final DeviceProvisionedWrapper deviceProvisionedWrapper;
    public final DeviceStateManagerWrapper deviceStateManagerWrapper;
    public final HandlerWrapper handlerWrapper;
    public final KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper;
    public final SoundPoolWrapper soundPoolWrapper;
    public final VolumePanelImpl volumePanel;
    public final VolumeDialogController volumeController = (VolumeDialogController) Dependency.sDependency.getDependencyInner(VolumeDialogController.class);
    public final SamsungVolumeDialogImpl$callbacks$1 callbacks = new VolumeDialogController.Callbacks() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$callbacks$1
        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onAccessibilityModeChanged(Boolean bool) {
            boolean booleanValue = bool.booleanValue();
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ACCESSIBILITY_MODE_CHANGED).isFromOutside(true).isShowA11yStream(booleanValue).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionComponentStateChanged(Boolean bool, Boolean bool2) {
            boolean booleanValue = bool.booleanValue();
            bool2.booleanValue();
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CAPTION_COMPONENT_CHANGED).isFromOutside(true).isCaptionComponentEnabled(booleanValue).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onConfigurationChanged() {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CONFIGURATION_CHANGED).isFromOutside(true).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onDismissRequested(int i) {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_REQUESTED).isFromOutside(true).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onKeyEvent(boolean z, boolean z2) {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_KEY_EVENT).isFromOutside(true).isKeyDown(z).isVibrating(z2).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onPlaySound(int i, boolean z) {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON).isFromKey(z).activeStream(i).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onScreenOff() {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SCREEN_OFF).isFromOutside(true).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowRequested(int i, boolean z, int i2) {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_PANEL_SHOW).isFromOutside(true).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSafetyWarning(int i) {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder((536870912 & i) != 0 ? VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_CSD_100_WARNING_DIALOG : VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_SAFETY_WARNING_DIALOG).isFromOutside(true).flags(i).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowVolumeLimiterToast() {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SHOW_VOLUME_LIMITER_DIALOG).isFromOutside(true).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onStateChanged(VolumeDialogController.State state) {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            VolumeState convert = VolumeStateConverter.convert(state);
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_STATE_CHANGED).activeStream(convert.getActiveStream()).setVolumeState(convert).isFromOutside(true).isVoiceCapable(volumePanelImpl.infraMediator.isVoiceCapable()).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onPlaySound(int i, boolean z, int i2) {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_PLAY_SOUND_ON).isFromKey(z).activeStream(i).volumeDirection(i2).build(), false);
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowSilentHint() {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowVibrateHint() {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onVolumeChangedFromKey() {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onLayoutDirectionChanged(int i) {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onCaptionEnabledStateChanged(Boolean bool, Boolean bool2) {
        }

        @Override // com.android.systemui.plugins.VolumeDialogController.Callbacks
        public final void onShowCsdWarning(int i, int i2) {
        }
    };
    public final SamsungVolumeDialogImpl$deviceProvisionedListener$1 deviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$deviceProvisionedListener$1
        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onDeviceProvisionedChanged() {
            VolumePanelImpl volumePanelImpl = SamsungVolumeDialogImpl.this.volumePanel;
            volumePanelImpl.getClass();
            volumePanelImpl.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SETUP_WIZARD_COMPLETE).isFromOutside(true).build(), false);
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onUserSetupChanged() {
        }

        @Override // com.android.systemui.statusbar.policy.DeviceProvisionedController.DeviceProvisionedListener
        public final void onUserSwitched() {
        }
    };

    /* JADX WARN: Type inference failed for: r2v19, types: [com.android.systemui.volume.SamsungVolumeDialogImpl$callbacks$1] */
    /* JADX WARN: Type inference failed for: r2v20, types: [com.android.systemui.volume.SamsungVolumeDialogImpl$deviceProvisionedListener$1] */
    public SamsungVolumeDialogImpl(Context context, VolumeDependency volumeDependency) {
        this.volumePanel = new VolumePanelImpl(context, volumeDependency);
        this.handlerWrapper = (HandlerWrapper) volumeDependency.get(HandlerWrapper.class);
        this.soundPoolWrapper = (SoundPoolWrapper) volumeDependency.get(SoundPoolWrapper.class);
        this.broadcastReceiverManager = (BroadcastReceiverManager) volumeDependency.get(BroadcastReceiverManager.class);
        this.keyguardUpdateMonitorWrapper = (KeyguardUpdateMonitorWrapper) volumeDependency.get(KeyguardUpdateMonitorWrapper.class);
        this.deviceProvisionedWrapper = (DeviceProvisionedWrapper) volumeDependency.get(DeviceProvisionedWrapper.class);
        if (DeviceType.isCoverSupported()) {
            this.coverUtilWrapper = (CoverUtilWrapper) volumeDependency.get(CoverUtilWrapper.class);
        }
        if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG) {
            this.deviceStateManagerWrapper = (DeviceStateManagerWrapper) volumeDependency.get(DeviceStateManagerWrapper.class);
        }
    }

    @Override // com.android.systemui.plugins.VolumeDialog
    public final void destroy() {
        DeviceStateManagerWrapper deviceStateManagerWrapper;
        DeviceStateManager.FoldStateListener foldStateListener;
        VolumeStarInteractor volumeStarInteractor = (VolumeStarInteractor) ((VolumeDependency) this.volumePanel.volDeps).get(VolumeStarInteractor.class);
        volumeStarInteractor.getClass();
        SPluginManager sPluginManager = (SPluginManager) Dependency.sDependency.getDependencyInner(SPluginManager.class);
        VolumeStarInteractor$start$1 volumeStarInteractor$start$1 = volumeStarInteractor.listener;
        if (volumeStarInteractor$start$1 == null) {
            volumeStarInteractor$start$1 = null;
        }
        sPluginManager.removePluginListener(volumeStarInteractor$start$1);
        this.volumeController.removeCallback(this.callbacks);
        ((HandlerThread) this.handlerWrapper.workerThread$delegate.getValue()).quitSafely();
        BroadcastReceiverManager broadcastReceiverManager = this.broadcastReceiverManager;
        for (BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem : broadcastReceiverManager.broadcastReceiverItemMap.values()) {
            BroadcastReceiver broadcastReceiver = broadcastReceiverItem.receiver;
            if (broadcastReceiver != null) {
                broadcastReceiverManager.broadcastDispatcher.unregisterReceiver(broadcastReceiver);
                broadcastReceiverItem.receiver = null;
            }
        }
        CoverUtilWrapper coverUtilWrapper = this.coverUtilWrapper;
        if (coverUtilWrapper != null) {
            ((HashMap) coverUtilWrapper.mListeners).remove(ModuleType.VOLUME);
        }
        if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG && (deviceStateManagerWrapper = this.deviceStateManagerWrapper) != null && (foldStateListener = deviceStateManagerWrapper.foldStateListener) != null) {
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = deviceStateManagerWrapper.context;
            systemServiceExtension.getClass();
            Object systemService = context.getSystemService((Class<Object>) DeviceStateManager.class);
            Intrinsics.checkNotNull(systemService);
            ((DeviceStateManager) systemService).unregisterCallback(foldStateListener);
            deviceStateManagerWrapper.foldStateListener = null;
        }
        KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper = this.keyguardUpdateMonitorWrapper;
        KeyguardUpdateMonitorWrapper$registerCallback$1 keyguardUpdateMonitorWrapper$registerCallback$1 = keyguardUpdateMonitorWrapper.callback;
        if (keyguardUpdateMonitorWrapper$registerCallback$1 != null) {
            keyguardUpdateMonitorWrapper.keyguardUpdateMonitor.removeCallback(keyguardUpdateMonitorWrapper$registerCallback$1);
            keyguardUpdateMonitorWrapper.callback = null;
        }
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedWrapper.deviceProvisionedController).removeCallback(this.deviceProvisionedListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v25, types: [com.android.keyguard.KeyguardUpdateMonitorCallback, com.android.systemui.volume.util.KeyguardUpdateMonitorWrapper$registerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.volume.VolumeStarInteractor$start$1] */
    @Override // com.android.systemui.plugins.VolumeDialog
    public final void init(int i, VolumeDialog.Callback callback) {
        final DeviceStateManagerWrapper deviceStateManagerWrapper;
        final VolumePanelImpl volumePanelImpl = this.volumePanel;
        final VolumeDependencyBase volumeDependencyBase = volumePanelImpl.volDeps;
        final VolumeStarInteractor volumeStarInteractor = (VolumeStarInteractor) ((VolumeDependency) volumeDependencyBase).get(VolumeStarInteractor.class);
        volumeStarInteractor.getClass();
        volumeStarInteractor.listener = new SPluginListener() { // from class: com.android.systemui.volume.VolumeStarInteractor$start$1
            @Override // com.samsung.systemui.splugins.SPluginListener
            public final void onPluginConnected(SPlugin sPlugin, Context context) {
                VolumeStar volumeStar = (VolumeStar) sPlugin;
                Log.d("VolumeStarInteractor", "onPluginConnected");
                VolumeStarInteractor volumeStarInteractor2 = VolumeStarInteractor.this;
                volumeStarInteractor2.volumeStar = volumeStar;
                volumeStar.init(volumeStarInteractor2.context, context, new VolumeStarDependencyImpl(volumeDependencyBase, volumePanelImpl));
                VolumeStar volumeStar2 = volumeStarInteractor2.volumeStar;
                if (volumeStar2 == null) {
                    volumeStar2 = null;
                }
                volumeStar2.start();
            }

            @Override // com.samsung.systemui.splugins.SPluginListener
            public final void onPluginDisconnected(SPlugin sPlugin, int i2) {
                Log.d("VolumeStarInteractor", "onPluginDisconnected");
                VolumeStar volumeStar = VolumeStarInteractor.this.volumeStar;
                if (volumeStar == null) {
                    volumeStar = null;
                }
                volumeStar.stop();
            }
        };
        SPluginManager sPluginManager = (SPluginManager) Dependency.sDependency.getDependencyInner(SPluginManager.class);
        VolumeStarInteractor$start$1 volumeStarInteractor$start$1 = volumeStarInteractor.listener;
        if (volumeStarInteractor$start$1 == null) {
            volumeStarInteractor$start$1 = null;
        }
        sPluginManager.addPluginListener((SPluginListener) volumeStarInteractor$start$1, VolumeStar.class, false);
        volumePanelImpl.soundAssistant.satMananger.setSoundAssistantProperty("volumestar_enable=0");
        volumePanelImpl.window.observeStore();
        VolumePanelAction.Builder builder = new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_INIT);
        VolumeInfraMediator volumeInfraMediator = volumePanelImpl.infraMediator;
        volumePanelImpl.dispatch(builder.isVoiceCapable(volumeInfraMediator.isVoiceCapable()).isHasVibrator(volumeInfraMediator.isHasVibrator()).isAllSoundOff(volumeInfraMediator.isAllSoundOff()).isSetupWizardComplete(volumeInfraMediator.isSetupWizardComplete()).isSupportSmartViewStream(true).isSupportWarningPopupWalletMini(true).isSupportWarningPopupSideView(true).isSupportBudsTogether(true).isSupportDualAudio(true).build(), false);
        Handler handler = new Handler(Looper.getMainLooper());
        SamsungVolumeDialogImpl$callbacks$1 samsungVolumeDialogImpl$callbacks$1 = this.callbacks;
        VolumeDialogController volumeDialogController = this.volumeController;
        volumeDialogController.addCallback(samsungVolumeDialogImpl$callbacks$1, handler);
        volumeDialogController.getState();
        SoundPoolWrapper soundPoolWrapper = this.soundPoolWrapper;
        soundPoolWrapper.getClass();
        soundPoolWrapper.handlerWrapper.postInBgThread(new SoundPoolWrapper$makeSound$1(soundPoolWrapper));
        final Consumer consumer = new Consumer() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                volumePanelImpl2.getClass();
                volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_ALL_SOUND_OFF_CHANGED).isAllSoundOff(booleanValue).isFromOutside(true).build(), false);
            }
        };
        BroadcastReceiverManager broadcastReceiverManager = this.broadcastReceiverManager;
        BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.ALLSOUND_OFF);
        if (broadcastReceiverItem != null) {
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerAllSoundOffAction$1$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action != null && action.hashCode() == -1413647469 && action.equals("android.settings.ALL_SOUND_MUTE")) {
                        consumer.accept(Boolean.valueOf(intent.getIntExtra("mute", 0) == 1));
                    }
                }
            };
            BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver, broadcastReceiverItem.intentFilter, null, null, 0, null, 60);
            broadcastReceiverItem.receiver = broadcastReceiver;
            Unit unit = Unit.INSTANCE;
        }
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$2
            @Override // java.lang.Runnable
            public final void run() {
                VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                volumePanelImpl2.getClass();
                volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_MIRROR_LINK_ON).isFromOutside(true).build(), false);
            }
        };
        BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem2 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.MIRROR_LINK);
        if (broadcastReceiverItem2 != null) {
            BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerMirrorLinkAction$1$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action != null && action.hashCode() == -2092605434 && action.equals("com.samsung.android.mirrorlink.ML_STATE")) {
                        runnable.run();
                    }
                }
            };
            BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver2, broadcastReceiverItem2.intentFilter, null, null, 0, null, 60);
            broadcastReceiverItem2.receiver = broadcastReceiver2;
            Unit unit2 = Unit.INSTANCE;
        }
        final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$3
            @Override // java.lang.Runnable
            public final void run() {
                VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                volumePanelImpl2.getClass();
                volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_OPEN_THEME_CHANGED).isFromOutside(true).build(), false);
            }
        };
        BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem3 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.OPEN_THEME);
        if (broadcastReceiverItem3 != null) {
            BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerOpenThemeChangedAction$1$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action != null && action.hashCode() == 1294398883 && action.equals("com.samsung.android.theme.themecenter.THEME_APPLY")) {
                        runnable2.run();
                    }
                }
            };
            BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver3, broadcastReceiverItem3.intentFilter, null, null, 0, null, 60);
            broadcastReceiverItem3.receiver = broadcastReceiver3;
            Unit unit3 = Unit.INSTANCE;
        }
        final Runnable runnable3 = new Runnable() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$4
            @Override // java.lang.Runnable
            public final void run() {
                VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                volumePanelImpl2.getClass();
                volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DUAL_PLAY_MODE_CHANGED).isFromOutside(true).build(), false);
            }
        };
        BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem4 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.DUAL_AUDIO_MODE);
        if (broadcastReceiverItem4 != null) {
            BroadcastReceiver broadcastReceiver4 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerDualPlayModeAction$1$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (action != null && action.hashCode() == 1484068483 && action.equals("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED")) {
                        runnable3.run();
                    }
                }
            };
            BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver4, broadcastReceiverItem4.intentFilter, null, null, 0, null, 60);
            broadcastReceiverItem4.receiver = broadcastReceiver4;
            Unit unit4 = Unit.INSTANCE;
        }
        broadcastReceiverManager.registerHeadsetConnectionAction(new Consumer() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Boolean bool = (Boolean) obj;
                VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                Intrinsics.checkNotNull(bool);
                boolean booleanValue = bool.booleanValue();
                volumePanelImpl2.getClass();
                volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_HEADSET_CONNECTION).isHeadsetConnected(booleanValue).isFromOutside(true).build(), false);
            }
        }, new Consumer() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$6
            @Override // java.util.function.Consumer
            public final /* bridge */ /* synthetic */ void accept(Object obj) {
            }
        });
        CoverUtilWrapper coverUtilWrapper = this.coverUtilWrapper;
        if (coverUtilWrapper != null) {
            ((HashMap) coverUtilWrapper.mListeners).put(ModuleType.VOLUME, new BiConsumer() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$7
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    int intValue = ((Number) obj2).intValue();
                    VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                    volumePanelImpl2.getClass();
                    volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_COVER_STATE_CHAGNED).isFromOutside(true).isCoverClosed(booleanValue).coverType(intValue).build(), false);
                }
            });
        }
        if (BasicRune.VOLUME_SUB_DISPLAY_VOLUME_DIALOG && (deviceStateManagerWrapper = this.deviceStateManagerWrapper) != null) {
            final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                    volumePanelImpl2.getClass();
                    volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_FOLDER_STATE_CHANGED).isFromOutside(true).isFolded(booleanValue).build(), true);
                }
            };
            DeviceStateManager.FoldStateListener foldStateListener = new DeviceStateManager.FoldStateListener(deviceStateManagerWrapper.context, new Consumer() { // from class: com.android.systemui.volume.util.DeviceStateManagerWrapper$registerFoldStateListener$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Boolean bool = (Boolean) obj;
                    boolean booleanValue = bool.booleanValue();
                    consumer2.accept(bool);
                    deviceStateManagerWrapper.isFolded = booleanValue;
                }
            });
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = deviceStateManagerWrapper.context;
            systemServiceExtension.getClass();
            Object systemService = context.getSystemService((Class<Object>) DeviceStateManager.class);
            Intrinsics.checkNotNull(systemService);
            VolumeExecutor.INSTANCE.getClass();
            ((DeviceStateManager) systemService).registerCallback((HandlerExecutor) VolumeExecutor.sExecutor$delegate.getValue(), foldStateListener);
            deviceStateManagerWrapper.foldStateListener = foldStateListener;
        }
        final Runnable runnable4 = new Runnable() { // from class: com.android.systemui.volume.SamsungVolumeDialogImpl$init$9
            @Override // java.lang.Runnable
            public final void run() {
                VolumePanelImpl volumePanelImpl2 = SamsungVolumeDialogImpl.this.volumePanel;
                volumePanelImpl2.getClass();
                volumePanelImpl2.dispatch(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_USER_SWITCHED).isFromOutside(true).build(), false);
            }
        };
        KeyguardUpdateMonitorWrapper keyguardUpdateMonitorWrapper = this.keyguardUpdateMonitorWrapper;
        keyguardUpdateMonitorWrapper.getClass();
        ?? r0 = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.volume.util.KeyguardUpdateMonitorWrapper$registerCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitchComplete(int i2) {
                runnable4.run();
            }
        };
        keyguardUpdateMonitorWrapper.callback = r0;
        keyguardUpdateMonitorWrapper.keyguardUpdateMonitor.registerCallback(r0);
        ((DeviceProvisionedControllerImpl) this.deviceProvisionedWrapper.deviceProvisionedController).addCallback(this.deviceProvisionedListener);
    }
}
