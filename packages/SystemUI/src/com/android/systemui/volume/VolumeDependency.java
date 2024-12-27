package com.android.systemui.volume;

import android.content.Context;
import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.android.systemui.basic.util.CoverUtilWrapper;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.basic.util.ModuleType;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.volume.config.VolumeConfigs;
import com.android.systemui.volume.soundassistant.SoundAssistantChecker;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.testinfra.FakeVolumeInfraMediator;
import com.android.systemui.volume.util.AccessibilityManagerWrapper;
import com.android.systemui.volume.util.ActivityManagerWrapper;
import com.android.systemui.volume.util.AudioManagerWrapper;
import com.android.systemui.volume.util.BixbyServiceManager;
import com.android.systemui.volume.util.BluetoothAdapterWrapper;
import com.android.systemui.volume.util.BluetoothAudioCastWrapper;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.BroadcastSender;
import com.android.systemui.volume.util.ConfigurationWrapper;
import com.android.systemui.volume.util.DesktopManagerWrapper;
import com.android.systemui.volume.util.DeviceProvisionedWrapper;
import com.android.systemui.volume.util.DeviceStateManagerWrapper;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.IDisplayManagerWrapper;
import com.android.systemui.volume.util.KeyguardManagerWrapper;
import com.android.systemui.volume.util.KeyguardUpdateMonitorWrapper;
import com.android.systemui.volume.util.PluginAODManagerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.util.SALoggingWrapper;
import com.android.systemui.volume.util.SemPersonaManagerWrapper;
import com.android.systemui.volume.util.SemWindowManagerWrapper;
import com.android.systemui.volume.util.SoundAssistantManagerWrapper;
import com.android.systemui.volume.util.SoundPoolWrapper;
import com.android.systemui.volume.util.StatusBarStateControllerWrapper;
import com.android.systemui.volume.util.StatusBarWrapper;
import com.android.systemui.volume.util.SystemClockWrapper;
import com.android.systemui.volume.util.ToastWrapper;
import com.android.systemui.volume.util.VibratorWrapper;
import com.android.systemui.volume.util.ZenModeHelper;
import com.android.systemui.volume.view.VolumePanelMotion;
import com.android.systemui.volume.view.standard.VolumePanelWindow;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelMotion;
import com.android.systemui.volume.view.subscreen.full.SubFullLayoutVolumePanelWindow;
import com.android.systemui.volume.view.subscreen.simple.SubDisplayVolumePanelPresentation;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumeDependency implements VolumeDependencyBase {
    public static VolumeDependency sInstance;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Lazy centralSurfacesLazy;
    public final Lazy pluginAODManagerLazy;
    public final SamsungServiceLogger volumePanelLogger;
    public static final Companion Companion = new Companion(null);
    public static final ArrayMap sProvider = new ArrayMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public VolumeDependency(Context context, SamsungServiceLogger samsungServiceLogger, BroadcastDispatcher broadcastDispatcher, Lazy lazy, Lazy lazy2) {
        this.volumePanelLogger = samsungServiceLogger;
        this.broadcastDispatcher = broadcastDispatcher;
        this.pluginAODManagerLazy = lazy;
        this.centralSurfacesLazy = lazy2;
        ArrayMap arrayMap = sProvider;
        if (arrayMap.get(Context.class) == null) {
            arrayMap.put(Context.class, context);
            Unit unit = Unit.INSTANCE;
        }
        sInstance = this;
    }

    public final Object createDependency(Class cls) {
        if (cls.equals(VolumePanelStore.class)) {
            return new VolumePanelStore(this);
        }
        if (cls.equals(VolumeInfraMediator.class)) {
            return new FakeVolumeInfraMediator(new VolumeInfraMediatorImpl(this), (LogWrapper) get(LogWrapper.class));
        }
        if (cls.equals(VolumeDialogController.class)) {
            return Dependency.sDependency.getDependencyInner(VolumeDialogController.class);
        }
        if (cls.equals(AudioManagerWrapper.class)) {
            return new AudioManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(BluetoothAdapterWrapper.class)) {
            return new BluetoothAdapterWrapper((Context) get(Context.class));
        }
        if (cls.equals(BixbyServiceManager.class)) {
            return new BixbyServiceManager((Context) get(Context.class), (LogWrapper) get(LogWrapper.class), (ActivityManagerWrapper) get(ActivityManagerWrapper.class));
        }
        if (cls.equals(LogWrapper.class)) {
            return new LogWrapper(ModuleType.VOLUME, this.volumePanelLogger);
        }
        if (cls.equals(ActivityManagerWrapper.class)) {
            return new ActivityManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(DisplayManagerWrapper.class)) {
            return new DisplayManagerWrapper((Context) get(Context.class), (LogWrapper) get(LogWrapper.class));
        }
        if (cls.equals(ZenModeHelper.class)) {
            return new ZenModeHelper();
        }
        if (cls.equals(HandlerWrapper.class)) {
            return new HandlerWrapper();
        }
        if (cls.equals(SoundPoolWrapper.class)) {
            return new SoundPoolWrapper((Context) get(Context.class), (HandlerWrapper) get(HandlerWrapper.class));
        }
        if (cls.equals(AccessibilityManagerWrapper.class)) {
            return new AccessibilityManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(StatusBarStateControllerWrapper.class)) {
            return new StatusBarStateControllerWrapper();
        }
        if (cls.equals(SoundAssistantManagerWrapper.class)) {
            return new SoundAssistantManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(StatusBarWrapper.class)) {
            return new StatusBarWrapper((Context) get(Context.class), (LogWrapper) get(LogWrapper.class), (KeyguardManagerWrapper) get(KeyguardManagerWrapper.class), this.centralSurfacesLazy);
        }
        if (cls.equals(KeyguardManagerWrapper.class)) {
            return new KeyguardManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(VolumePanelWindow.class)) {
            return new VolumePanelWindow(this);
        }
        if (cls.equals(SubFullLayoutVolumePanelWindow.class)) {
            return new SubFullLayoutVolumePanelWindow(this);
        }
        if (cls.equals(SystemClockWrapper.class)) {
            return new SystemClockWrapper();
        }
        if (cls.equals(KeyguardUpdateMonitorWrapper.class)) {
            return new KeyguardUpdateMonitorWrapper();
        }
        if (cls.equals(ConfigurationWrapper.class)) {
            return new ConfigurationWrapper(this);
        }
        if (cls.equals(SettingsHelper.class)) {
            return Dependency.sDependency.getDependencyInner(SettingsHelper.class);
        }
        if (cls.equals(BroadcastReceiverManager.class)) {
            return new BroadcastReceiverManager((Context) get(Context.class), (LogWrapper) get(LogWrapper.class), this.broadcastDispatcher);
        }
        if (cls.equals(BroadcastSender.class)) {
            return new BroadcastSender((Context) get(Context.class));
        }
        if (cls.equals(DesktopManagerWrapper.class)) {
            return new DesktopManagerWrapper();
        }
        if (cls.equals(PowerManagerWrapper.class)) {
            return new PowerManagerWrapper();
        }
        if (cls.equals(CoverUtilWrapper.class)) {
            return Dependency.sDependency.getDependencyInner(CoverUtilWrapper.class);
        }
        if (cls.equals(ToastWrapper.class)) {
            return new ToastWrapper();
        }
        if (cls.equals(SALoggingWrapper.class)) {
            return new SALoggingWrapper();
        }
        if (cls.equals(SemPersonaManagerWrapper.class)) {
            return new SemPersonaManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(VolumePanelMotion.class)) {
            return new VolumePanelMotion();
        }
        if (cls.equals(SubFullLayoutVolumePanelMotion.class)) {
            return new SubFullLayoutVolumePanelMotion();
        }
        if (cls.equals(BluetoothAudioCastWrapper.class)) {
            return new BluetoothAudioCastWrapper((Context) get(Context.class));
        }
        if (cls.equals(DeviceProvisionedWrapper.class)) {
            return new DeviceProvisionedWrapper((LogWrapper) get(LogWrapper.class));
        }
        if (cls.equals(VolumeStarInteractor.class)) {
            return new VolumeStarInteractor((Context) get(Context.class));
        }
        if (cls.equals(SemWindowManagerWrapper.class)) {
            return new SemWindowManagerWrapper();
        }
        if (cls.equals(SubDisplayVolumePanelPresentation.class)) {
            return new SubDisplayVolumePanelPresentation(this);
        }
        if (cls.equals(IDisplayManagerWrapper.class)) {
            return new IDisplayManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(DeviceStateManagerWrapper.class)) {
            return new DeviceStateManagerWrapper((Context) get(Context.class));
        }
        if (cls.equals(VibratorWrapper.class)) {
            return new VibratorWrapper((Context) get(Context.class));
        }
        if (cls.equals(PluginAODManagerWrapper.class)) {
            return new PluginAODManagerWrapper(this.pluginAODManagerLazy);
        }
        if (cls.equals(VolumeConfigs.class)) {
            return new VolumeConfigs((Context) get(Context.class));
        }
        if (cls.equals(SoundAssistantChecker.class)) {
            return new SoundAssistantChecker((Context) get(Context.class));
        }
        return null;
    }

    public final Object get(Class cls) {
        ArrayMap arrayMap = sProvider;
        Object obj = arrayMap.get(cls);
        if (obj != null) {
            return obj;
        }
        arrayMap.put(cls, createDependency(cls));
        return arrayMap.get(cls);
    }

    public final Object getNewObject(Class cls) {
        ArrayMap arrayMap = sProvider;
        if (arrayMap.containsKey(cls)) {
            arrayMap.remove(cls);
        }
        arrayMap.put(cls, createDependency(cls));
        return arrayMap.get(cls);
    }
}
