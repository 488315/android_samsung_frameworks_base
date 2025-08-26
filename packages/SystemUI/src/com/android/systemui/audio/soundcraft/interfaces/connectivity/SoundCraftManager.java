package com.android.systemui.audio.soundcraft.interfaces.connectivity;

import android.app.ActivityManager;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettingConstants;
import com.android.systemui.audio.soundcraft.interfaces.settings.SoundCraftSettings;
import com.android.systemui.audio.soundcraft.interfaces.wearable.WearableManager;
import com.android.systemui.audio.soundcraft.interfaces.wearable.stub.GWStubPluginStateRequester;
import com.android.systemui.audio.soundcraft.model.buds.plugin.GWPluginModel;
import com.android.systemui.audio.soundcraft.view.SoundCraftQpDetailAdapter;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.volume.util.BroadcastReceiverManager;
import com.android.systemui.volume.util.BroadcastReceiverType;
import com.android.systemui.volume.util.SystemServiceExtension;
import dagger.Lazy;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: classes.dex */
public final class SoundCraftManager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final BluetoothDeviceManager bluetoothDeviceManager;
    public final Context context;
    public boolean isBudsManagerFront;
    public final Lazy qsDetailControllerLazy;
    public final SoundCraftQpDetailAdapter soundCraftQpDetailAdapter;
    public final SoundCraftSettings soundCraftSettings;
    public final WearableManager wearableManager;

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

    public SoundCraftManager(Context context, BluetoothDeviceManager bluetoothDeviceManager, BroadcastReceiverManager broadcastReceiverManager, WearableManager wearableManager, TaskStackChangeListeners taskStackChangeListeners, SoundCraftSettings soundCraftSettings, Lazy lazy, SoundCraftQpDetailAdapter soundCraftQpDetailAdapter) {
        this.context = context;
        this.bluetoothDeviceManager = bluetoothDeviceManager;
        this.wearableManager = wearableManager;
        this.soundCraftSettings = soundCraftSettings;
        this.qsDetailControllerLazy = lazy;
        this.soundCraftQpDetailAdapter = soundCraftQpDetailAdapter;
        Log.d("SoundCraft.SoundCraftManager", "init");
        final Consumer consumer = new Consumer() { // from class: com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager.1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                SoundCraftManager.access$clearSettings(SoundCraftManager.this);
                if (bluetoothDevice == null) {
                    Log.d("SoundCraft.SoundCraftManager", "onA2dpActiveDeviceChanged : device is null.");
                    return;
                }
                SoundCraftManager.this.bluetoothDeviceManager.getClass();
                boolean zIsSupportedSoundCraft = BluetoothDeviceManager.isSupportedSoundCraft(bluetoothDevice);
                Log.d("SoundCraft.SoundCraftManager", "onA2dpActiveDeviceChanged : name=" + bluetoothDevice.getName() + ", isBuds3OrNextModel=" + zIsSupportedSoundCraft);
                if (zIsSupportedSoundCraft) {
                    SoundCraftManager.this.requestGWPluginModel(bluetoothDevice);
                }
            }
        };
        BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.A2DP_ACTIVE_DEVICE_CHANGE);
        if (broadcastReceiverItem != null) {
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerA2dpActiveDeviceChange$1$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    Log.d("vol.BroadcastManager", "action=" + intent.getAction());
                    String action = intent.getAction();
                    if (action != null && action.hashCode() == 487423555 && action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                        consumer.accept(intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE"));
                    }
                }
            };
            BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver, broadcastReceiverItem.intentFilter, null, null, 0, null, 60);
            broadcastReceiverItem.receiver = broadcastReceiver;
            Unit unit = Unit.INSTANCE;
        }
        final Consumer consumer2 = new Consumer() { // from class: com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager.2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) throws Resources.NotFoundException {
                BluetoothDevice activeDevice;
                Integer num = (Integer) obj;
                Log.i("SoundCraft.SoundCraftManager", "onStreamDevicesChanged devices = " + num);
                SoundCraftManager.access$clearSettings(SoundCraftManager.this);
                if (num.intValue() != 128 || (activeDevice = SoundCraftManager.this.bluetoothDeviceManager.getActiveDevice()) == null) {
                    return;
                }
                SoundCraftManager.this.bluetoothDeviceManager.getClass();
                if (!BluetoothDeviceManager.isSupportedSoundCraft(activeDevice)) {
                    activeDevice = null;
                }
                if (activeDevice != null) {
                    SoundCraftManager.this.requestGWPluginModel(activeDevice);
                }
            }
        };
        BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem2 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.STREAM_DEVICES_CHANGED);
        if (broadcastReceiverItem2 != null) {
            BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerStreamDevicesChanged$1$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    Log.d("vol.BroadcastManager", "action=" + intent.getAction());
                    String action = intent.getAction();
                    if (action != null && action.hashCode() == -1315844839 && action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                        int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                        int intExtra2 = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_DEVICES", -1);
                        if (intExtra == 3) {
                            consumer2.accept(Integer.valueOf(intExtra2));
                        }
                    }
                }
            };
            BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver2, broadcastReceiverItem2.intentFilter, null, null, 0, null, 60);
            broadcastReceiverItem2.receiver = broadcastReceiver2;
            Unit unit2 = Unit.INSTANCE;
        }
        final Consumer consumer3 = new Consumer() { // from class: com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager.3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String str = (String) obj;
                SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
                Context context2 = SoundCraftManager.this.context;
                soundCraftSettingConstants.getClass();
                String string = Settings.System.getString(context2.getContentResolver(), "buds_plugin_package_name");
                if (string == null) {
                    string = "";
                }
                if (Intrinsics.areEqual(str, string) || Intrinsics.areEqual(str, "com.samsung.android.app.watchmanager")) {
                    Log.d("SoundCraft.SoundCraftManager", str + " is removed. plugin disconnected");
                    SoundCraftSettingConstants.budsPluginPackageName(SoundCraftManager.this.context, "");
                    SoundCraftSettingConstants.isBudsPluginConnected(SoundCraftManager.this.context, false);
                    SoundCraftManager.this.soundCraftSettings.update();
                }
            }
        };
        BroadcastReceiverManager.BroadcastReceiverItem broadcastReceiverItem3 = (BroadcastReceiverManager.BroadcastReceiverItem) broadcastReceiverManager.broadcastReceiverItemMap.get(BroadcastReceiverType.UNINSTALL_PACKAGE);
        if (broadcastReceiverItem3 != null) {
            BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: com.android.systemui.volume.util.BroadcastReceiverManager$registerUninstallPackage$1$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    String action = intent.getAction();
                    if (action == null || action.hashCode() != 525384130 || !action.equals("android.intent.action.PACKAGE_REMOVED") || intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        return;
                    }
                    Uri data = intent.getData();
                    String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                    if (schemeSpecificPart != null) {
                        consumer3.accept(schemeSpecificPart);
                    }
                }
            };
            BroadcastDispatcher.registerReceiver$default(broadcastReceiverManager.broadcastDispatcher, broadcastReceiver3, broadcastReceiverItem3.intentFilter, null, null, 0, null, 60);
            broadcastReceiverItem3.receiver = broadcastReceiver3;
        }
        taskStackChangeListeners.registerTaskStackListener(new TaskStackChangeListener() { // from class: com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager.4
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
                String packageName;
                ComponentName componentName = runningTaskInfo.baseActivity;
                boolean zContains = false;
                if (componentName != null && (packageName = componentName.getPackageName()) != null) {
                    zContains = StringsKt__StringsKt.contains(packageName, "com.samsung.accessory", false);
                }
                SoundCraftManager.this.isBudsManagerFront = zContains;
                if (zContains) {
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("onTaskMoveToFront : isBudsManagerFront=", "SoundCraft.SoundCraftManager", zContains);
                }
            }

            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskStackChanged() {
                String className;
                BluetoothDeviceManager bluetoothDeviceManager2;
                BluetoothDevice activeDevice;
                ComponentName componentName;
                SoundCraftManager soundCraftManager = SoundCraftManager.this;
                if (soundCraftManager.isBudsManagerFront) {
                    SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
                    Context context2 = soundCraftManager.context;
                    systemServiceExtension.getClass();
                    Object systemService = context2.getSystemService((Class<Object>) ActivityManager.class);
                    systemService.getClass();
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull((List) ((ActivityManager) systemService).getRunningTasks(3));
                    if (runningTaskInfo == null || (componentName = runningTaskInfo.topActivity) == null || (className = componentName.getClassName()) == null) {
                        className = "";
                    }
                    boolean zContains = StringsKt__StringsKt.contains(className, "com.samsung.accessory.hearablemgr.module.home.activity", false);
                    EmergencyButtonController$$ExternalSyntheticOutline0.m("onTaskStackChanged : isBudsMainPageFront=", "SoundCraft.SoundCraftManager", zContains);
                    if (!zContains || (activeDevice = (bluetoothDeviceManager2 = soundCraftManager.bluetoothDeviceManager).getActiveDevice()) == null) {
                        return;
                    }
                    bluetoothDeviceManager2.getClass();
                    if (!BluetoothDeviceManager.isSupportedSoundCraft(activeDevice)) {
                        activeDevice = null;
                    }
                    if (activeDevice != null) {
                        soundCraftManager.requestGWPluginModel(activeDevice);
                    }
                }
            }
        });
    }

    public static final void access$clearSettings(SoundCraftManager soundCraftManager) throws Resources.NotFoundException {
        ((SecQSDetailController) soundCraftManager.qsDetailControllerLazy.get()).closeTargetDetail(soundCraftManager.soundCraftQpDetailAdapter);
        SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
        Context context = soundCraftManager.context;
        soundCraftSettingConstants.getClass();
        SoundCraftSettingConstants.isBudsConnected(context, false);
        SoundCraftSettingConstants.budsPluginPackageName(soundCraftManager.context, "");
        soundCraftManager.soundCraftSettings.update();
    }

    public final void requestGWPluginModel(final BluetoothDevice bluetoothDevice) {
        Intent intent;
        Function1 function1 = new Function1() { // from class: com.android.systemui.audio.soundcraft.interfaces.connectivity.SoundCraftManager$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Object next;
                BluetoothDevice bluetoothDevice2 = bluetoothDevice;
                List list = (List) obj;
                int i = SoundCraftManager.$r8$clinit;
                ListPopupWindow$$ExternalSyntheticOutline0.m(list.size(), "onGWPluginModelReceived: modelList size=", "SoundCraft.SoundCraftManager");
                Iterator it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                    GWPluginModel gWPluginModel = (GWPluginModel) next;
                    Log.d("SoundCraft.SoundCraftManager", "onGWPluginModelReceived: model.packageName=" + gWPluginModel.packageName);
                    if (Intrinsics.areEqual(gWPluginModel.deviceId, bluetoothDevice2.getAddress())) {
                        break;
                    }
                }
                GWPluginModel gWPluginModel2 = (GWPluginModel) next;
                SoundCraftManager soundCraftManager = this.f$0;
                if (gWPluginModel2 != null) {
                    SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
                    Context context = soundCraftManager.context;
                    soundCraftSettingConstants.getClass();
                    SoundCraftSettingConstants.isBudsConnected(context, true);
                    SoundCraftSettingConstants.budsPluginPackageName(soundCraftManager.context, gWPluginModel2.packageName);
                } else {
                    SoundCraftSettingConstants soundCraftSettingConstants2 = SoundCraftSettingConstants.INSTANCE;
                    Context context2 = soundCraftManager.context;
                    soundCraftSettingConstants2.getClass();
                    SoundCraftSettingConstants.isBudsConnected(context2, true);
                    SoundCraftSettingConstants.budsPluginPackageName(soundCraftManager.context, "");
                }
                return Unit.INSTANCE;
            }
        };
        WearableManager wearableManager = this.wearableManager;
        wearableManager.getClass();
        GWStubPluginStateRequester gWStubPluginStateRequester = new GWStubPluginStateRequester(wearableManager.context, function1);
        Intent intent2 = new Intent("com.samsung.accessory.hearablemgr.action.PLUGIN_INFO_SERVICE");
        boolean z = false;
        List<ResolveInfo> listQueryIntentServices = gWStubPluginStateRequester.context.getPackageManager().queryIntentServices(intent2, 0);
        if (listQueryIntentServices.size() == 0) {
            intent = null;
        } else {
            Intent intent3 = new Intent(intent2);
            intent3.setComponent(new ComponentName(listQueryIntentServices.get(0).serviceInfo.packageName, listQueryIntentServices.get(0).serviceInfo.name));
            intent = intent3;
        }
        if (intent == null) {
            Log.e("SoundCraft.wearable.GWStubServiceRequester", "bindService : intent is null.");
        } else {
            try {
                boolean zBindService = gWStubPluginStateRequester.context.bindService(intent, gWStubPluginStateRequester.serviceConnection, 1);
                Log.d("SoundCraft.wearable.GWStubServiceRequester", "isSuccess=" + zBindService);
                z = zBindService;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!z) {
            SoundCraftSettingConstants soundCraftSettingConstants = SoundCraftSettingConstants.INSTANCE;
            Context context = this.context;
            soundCraftSettingConstants.getClass();
            SoundCraftSettingConstants.isBudsConnected(context, true);
            SoundCraftSettingConstants.budsPluginPackageName(this.context, "");
        }
        this.soundCraftSettings.update();
    }
}
