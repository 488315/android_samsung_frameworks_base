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
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VolumeDependency implements VolumeDependencyBase {
    public static VolumeDependency sInstance;
    public final BroadcastDispatcher broadcastDispatcher;
    public final Lazy centralSurfacesLazy;
    public final Lazy pluginAODManagerLazy;
    public final SamsungServiceLogger volumePanelLogger;
    public static final Companion Companion = new Companion(null);
    public static final ArrayMap sProvider = new ArrayMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        if (Intrinsics.areEqual(cls, VolumePanelStore.class)) {
            return new VolumePanelStore(this);
        }
        if (Intrinsics.areEqual(cls, VolumeInfraMediator.class)) {
            return new FakeVolumeInfraMediator(new VolumeInfraMediatorImpl(this), (LogWrapper) get(LogWrapper.class));
        }
        if (Intrinsics.areEqual(cls, VolumeDialogController.class)) {
            return Dependency.get(VolumeDialogController.class);
        }
        if (Intrinsics.areEqual(cls, AudioManagerWrapper.class)) {
            return new AudioManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, BluetoothAdapterWrapper.class)) {
            return new BluetoothAdapterWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, BixbyServiceManager.class)) {
            return new BixbyServiceManager((Context) get(Context.class), (LogWrapper) get(LogWrapper.class), (ActivityManagerWrapper) get(ActivityManagerWrapper.class));
        }
        if (Intrinsics.areEqual(cls, LogWrapper.class)) {
            return new LogWrapper(ModuleType.VOLUME, this.volumePanelLogger);
        }
        if (Intrinsics.areEqual(cls, ActivityManagerWrapper.class)) {
            return new ActivityManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, DisplayManagerWrapper.class)) {
            return new DisplayManagerWrapper((Context) get(Context.class), (LogWrapper) get(LogWrapper.class));
        }
        if (Intrinsics.areEqual(cls, ZenModeHelper.class)) {
            return new ZenModeHelper();
        }
        if (Intrinsics.areEqual(cls, HandlerWrapper.class)) {
            return new HandlerWrapper();
        }
        if (Intrinsics.areEqual(cls, SoundPoolWrapper.class)) {
            return new SoundPoolWrapper((Context) get(Context.class), (HandlerWrapper) get(HandlerWrapper.class));
        }
        if (Intrinsics.areEqual(cls, AccessibilityManagerWrapper.class)) {
            return new AccessibilityManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, StatusBarStateControllerWrapper.class)) {
            return new StatusBarStateControllerWrapper();
        }
        if (Intrinsics.areEqual(cls, SoundAssistantManagerWrapper.class)) {
            return new SoundAssistantManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, StatusBarWrapper.class)) {
            return new StatusBarWrapper((Context) get(Context.class), (LogWrapper) get(LogWrapper.class), (KeyguardManagerWrapper) get(KeyguardManagerWrapper.class), this.centralSurfacesLazy);
        }
        if (Intrinsics.areEqual(cls, KeyguardManagerWrapper.class)) {
            return new KeyguardManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, VolumePanelWindow.class)) {
            return new VolumePanelWindow(this);
        }
        if (Intrinsics.areEqual(cls, SubFullLayoutVolumePanelWindow.class)) {
            return new SubFullLayoutVolumePanelWindow(this);
        }
        if (Intrinsics.areEqual(cls, SystemClockWrapper.class)) {
            return new SystemClockWrapper();
        }
        if (Intrinsics.areEqual(cls, KeyguardUpdateMonitorWrapper.class)) {
            return new KeyguardUpdateMonitorWrapper();
        }
        if (Intrinsics.areEqual(cls, ConfigurationWrapper.class)) {
            return new ConfigurationWrapper(this);
        }
        if (Intrinsics.areEqual(cls, SettingsHelper.class)) {
            return Dependency.get(SettingsHelper.class);
        }
        if (Intrinsics.areEqual(cls, BroadcastReceiverManager.class)) {
            return new BroadcastReceiverManager((Context) get(Context.class), (LogWrapper) get(LogWrapper.class), this.broadcastDispatcher);
        }
        if (Intrinsics.areEqual(cls, BroadcastSender.class)) {
            return new BroadcastSender((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, DesktopManagerWrapper.class)) {
            return new DesktopManagerWrapper();
        }
        if (Intrinsics.areEqual(cls, PowerManagerWrapper.class)) {
            return new PowerManagerWrapper();
        }
        if (Intrinsics.areEqual(cls, CoverUtilWrapper.class)) {
            return Dependency.get(CoverUtilWrapper.class);
        }
        if (Intrinsics.areEqual(cls, ToastWrapper.class)) {
            return new ToastWrapper();
        }
        if (Intrinsics.areEqual(cls, SALoggingWrapper.class)) {
            return new SALoggingWrapper();
        }
        if (Intrinsics.areEqual(cls, SemPersonaManagerWrapper.class)) {
            return new SemPersonaManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, VolumePanelMotion.class)) {
            return new VolumePanelMotion();
        }
        if (Intrinsics.areEqual(cls, SubFullLayoutVolumePanelMotion.class)) {
            return new SubFullLayoutVolumePanelMotion();
        }
        if (Intrinsics.areEqual(cls, BluetoothAudioCastWrapper.class)) {
            return new BluetoothAudioCastWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, DeviceProvisionedWrapper.class)) {
            return new DeviceProvisionedWrapper((LogWrapper) get(LogWrapper.class));
        }
        if (Intrinsics.areEqual(cls, VolumeStarInteractor.class)) {
            return new VolumeStarInteractor((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, SemWindowManagerWrapper.class)) {
            return new SemWindowManagerWrapper();
        }
        if (Intrinsics.areEqual(cls, SubDisplayVolumePanelPresentation.class)) {
            return new SubDisplayVolumePanelPresentation(this);
        }
        if (Intrinsics.areEqual(cls, IDisplayManagerWrapper.class)) {
            return new IDisplayManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, DeviceStateManagerWrapper.class)) {
            return new DeviceStateManagerWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, VibratorWrapper.class)) {
            return new VibratorWrapper((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, PluginAODManagerWrapper.class)) {
            return new PluginAODManagerWrapper(this.pluginAODManagerLazy);
        }
        if (Intrinsics.areEqual(cls, VolumeConfigs.class)) {
            return new VolumeConfigs((Context) get(Context.class));
        }
        if (Intrinsics.areEqual(cls, SoundAssistantChecker.class)) {
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
