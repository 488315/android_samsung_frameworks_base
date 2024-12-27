package com.android.systemui.accessibility.hearingaid;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHapClient;
import android.bluetooth.BluetoothHapPresetInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HapClientProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.HearingDevicesListAdapter;
import com.android.systemui.accessibility.hearingaid.HearingDevicesPresetsController;
import com.android.systemui.animation.AnimatedDialog;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.bluetooth.qsdialog.ActiveHearingDeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.AvailableHearingDeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.ConnectedDeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.DeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.DeviceItemType;
import com.android.systemui.bluetooth.qsdialog.SavedHearingDeviceItemFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HearingDevicesDialogDelegate implements SystemUIDialog.Delegate, HearingDevicesListAdapter.HearingDeviceItemCallback, BluetoothCallback {
    static final String ACTION_BLUETOOTH_DEVICE_DETAILS = "com.android.settings.BLUETOOTH_DEVICE_DETAIL_SETTINGS";
    static final Intent LIVE_CAPTION_INTENT = new Intent("com.android.settings.action.live_caption");
    public final ActivityStarter mActivityStarter;
    public final Context mApplicationContext;
    public final AudioManager mAudioManager;
    public RecyclerView mDeviceList;
    public HearingDevicesListAdapter mDeviceListAdapter;
    public SystemUIDialog mDialog;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final HapClientProfile mHapClientProfile;
    public List mHearingDeviceItemList;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final Handler mMainHandler;
    public Button mPairButton;
    public ArrayAdapter mPresetInfoAdapter;
    public Spinner mPresetSpinner;
    public HearingDevicesPresetsController mPresetsController;
    public final LocalBluetoothProfileManager mProfileManager;
    public LinearLayout mRelatedToolsContainer;
    public final boolean mShowPairNewDevice;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
    public final AnonymousClass1 mPresetCallback = new AnonymousClass1();
    public final List mHearingDeviceItemFactoryList = List.of(new ActiveHearingDeviceItemFactory(), new AvailableHearingDeviceItemFactory(), new ConnectedDeviceItemFactory(), new SavedHearingDeviceItemFactory());

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$1, reason: invalid class name */
    public final class AnonymousClass1 implements HearingDevicesPresetsController.PresetCallback {
        public AnonymousClass1() {
        }

        public final void onPresetCommandFailed() {
            HearingDevicesDialogDelegate hearingDevicesDialogDelegate = HearingDevicesDialogDelegate.this;
            hearingDevicesDialogDelegate.mMainHandler.post(new HearingDevicesDialogDelegate$1$$ExternalSyntheticLambda0(this, hearingDevicesDialogDelegate.mPresetsController.getAllPresetInfo(), hearingDevicesDialogDelegate.mPresetsController.getActivePresetIndex(), 0));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$4, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType;

        static {
            int[] iArr = new int[DeviceItemType.values().length];
            $SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType = iArr;
            try {
                iArr[DeviceItemType.ACTIVE_MEDIA_BLUETOOTH_DEVICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType[DeviceItemType.CONNECTED_BLUETOOTH_DEVICE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType[DeviceItemType.AVAILABLE_MEDIA_BLUETOOTH_DEVICE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType[DeviceItemType.SAVED_BLUETOOTH_DEVICE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        HearingDevicesDialogDelegate create(boolean z);
    }

    public HearingDevicesDialogDelegate(Context context, boolean z, SystemUIDialog.Factory factory, ActivityStarter activityStarter, DialogTransitionAnimator dialogTransitionAnimator, LocalBluetoothManager localBluetoothManager, Handler handler, AudioManager audioManager) {
        this.mApplicationContext = context;
        this.mShowPairNewDevice = z;
        this.mSystemUIDialogFactory = factory;
        this.mActivityStarter = activityStarter;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mMainHandler = handler;
        this.mAudioManager = audioManager;
        LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mHapClientProfile = localBluetoothProfileManager.mHapClientProfile;
    }

    public static CachedBluetoothDevice getActiveHearingDevice(List list) {
        return (CachedBluetoothDevice) list.stream().filter(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda4(0)).map(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda1(1)).findFirst().orElse(null);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        systemUIDialog.setTitle(R.string.quick_settings_hearing_devices_dialog_title);
        systemUIDialog.setView(LayoutInflater.from(systemUIDialog.getContext()).inflate(R.layout.hearing_devices_tile_dialog, (ViewGroup) null));
        systemUIDialog.setButton(-1, R.string.quick_settings_done, null, true);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.mSystemUIDialogFactory;
        SystemUIDialog create = factory.create(this, factory.mContext);
        SystemUIDialog systemUIDialog = this.mDialog;
        if (systemUIDialog != null) {
            systemUIDialog.dismiss();
        }
        this.mDialog = create;
        return create;
    }

    public final List getHearingDevicesList() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        return (localBluetoothManager == null || !localBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) ? Collections.emptyList() : (List) localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy().stream().map(new Function() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate = HearingDevicesDialogDelegate.this;
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                Context context = hearingDevicesDialogDelegate.mDialog.getContext();
                if (cachedBluetoothDevice == null) {
                    return null;
                }
                for (DeviceItemFactory deviceItemFactory : hearingDevicesDialogDelegate.mHearingDeviceItemFactoryList) {
                    if (deviceItemFactory.isFilterMatched(context, cachedBluetoothDevice, hearingDevicesDialogDelegate.mAudioManager)) {
                        return deviceItemFactory.create(context, cachedBluetoothDevice);
                    }
                }
                return null;
            }
        }).filter(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda4(1)).collect(Collectors.toList());
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        this.mHearingDeviceItemList = getHearingDevicesList();
        this.mMainHandler.post(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda8(this, 0));
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        final CachedBluetoothDevice cachedBluetoothDevice2;
        List hearingDevicesList = getHearingDevicesList();
        this.mHearingDeviceItemList = hearingDevicesList;
        if (this.mPresetsController != null) {
            cachedBluetoothDevice2 = getActiveHearingDevice(hearingDevicesList);
            this.mPresetsController.mActiveHearingDevice = cachedBluetoothDevice2;
        } else {
            cachedBluetoothDevice2 = null;
        }
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate = HearingDevicesDialogDelegate.this;
                CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice2;
                HearingDevicesListAdapter hearingDevicesListAdapter = hearingDevicesDialogDelegate.mDeviceListAdapter;
                List list = hearingDevicesDialogDelegate.mHearingDeviceItemList;
                hearingDevicesListAdapter.mItemList.clear();
                hearingDevicesListAdapter.mItemList.addAll(list);
                hearingDevicesListAdapter.notifyDataSetChanged();
                hearingDevicesDialogDelegate.refreshPresetInfoAdapter(hearingDevicesDialogDelegate.mPresetsController.getActivePresetIndex(), hearingDevicesDialogDelegate.mPresetsController.getAllPresetInfo());
                hearingDevicesDialogDelegate.mPresetSpinner.setVisibility((cachedBluetoothDevice3 == null || hearingDevicesDialogDelegate.mPresetInfoAdapter.isEmpty()) ? 8 : 0);
            }
        });
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        final SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        if (this.mLocalBluetoothManager == null) {
            return;
        }
        this.mPairButton = (Button) systemUIDialog.requireViewById(R.id.pair_new_device_button);
        this.mDeviceList = (RecyclerView) systemUIDialog.requireViewById(R.id.device_list);
        this.mPresetSpinner = (Spinner) systemUIDialog.requireViewById(R.id.preset_spinner);
        this.mRelatedToolsContainer = (LinearLayout) systemUIDialog.requireViewById(R.id.related_tools_container);
        this.mDeviceList.setLayoutManager(new LinearLayoutManager(systemUIDialog.getContext()));
        List hearingDevicesList = getHearingDevicesList();
        this.mHearingDeviceItemList = hearingDevicesList;
        HearingDevicesListAdapter hearingDevicesListAdapter = new HearingDevicesListAdapter(hearingDevicesList, this);
        this.mDeviceListAdapter = hearingDevicesListAdapter;
        this.mDeviceList.setAdapter(hearingDevicesListAdapter);
        this.mPresetsController = new HearingDevicesPresetsController(this.mProfileManager, this.mPresetCallback);
        CachedBluetoothDevice activeHearingDevice = getActiveHearingDevice(this.mHearingDeviceItemList);
        this.mPresetsController.mActiveHearingDevice = activeHearingDevice;
        ArrayAdapter arrayAdapter = new ArrayAdapter(systemUIDialog.getContext(), R.layout.hearing_devices_preset_spinner_selected, R.id.hearing_devices_preset_option_text);
        this.mPresetInfoAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(R.layout.hearing_devices_preset_dropdown_item);
        this.mPresetSpinner.setAdapter((SpinnerAdapter) this.mPresetInfoAdapter);
        this.mPresetSpinner.setAccessibilityDelegate(new View.AccessibilityDelegate(this) { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.2
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            }
        });
        this.mPresetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate.3
            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
                int hapGroup;
                HearingDevicesPresetsController hearingDevicesPresetsController = HearingDevicesDialogDelegate.this.mPresetsController;
                int index = ((BluetoothHapPresetInfo) hearingDevicesPresetsController.getAllPresetInfo().get(i)).getIndex();
                CachedBluetoothDevice cachedBluetoothDevice = hearingDevicesPresetsController.mActiveHearingDevice;
                if (cachedBluetoothDevice != null) {
                    hearingDevicesPresetsController.mSelectedPresetIndex = index;
                    HapClientProfile hapClientProfile = hearingDevicesPresetsController.mHapClientProfile;
                    BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                    BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                    boolean supportsSynchronizedPresets = bluetoothHapClient == null ? false : bluetoothHapClient.supportsSynchronizedPresets(bluetoothDevice);
                    HapClientProfile hapClientProfile2 = hearingDevicesPresetsController.mHapClientProfile;
                    BluetoothDevice bluetoothDevice2 = hearingDevicesPresetsController.mActiveHearingDevice.mDevice;
                    BluetoothHapClient bluetoothHapClient2 = hapClientProfile2.mService;
                    if (bluetoothHapClient2 == null) {
                        Log.w("HapClientProfile", "Proxy not attached to service. Cannot get hap group.");
                        hapGroup = -1;
                    } else {
                        hapGroup = bluetoothHapClient2.getHapGroup(bluetoothDevice2);
                    }
                    if (!supportsSynchronizedPresets) {
                        hearingDevicesPresetsController.selectPresetIndependently(index);
                    } else if (hapGroup == -1) {
                        Log.w("HearingDevicesPresetsController", "supportSynchronizedPresets but hapGroupId is invalid.");
                        hearingDevicesPresetsController.selectPresetIndependently(index);
                    } else if (hearingDevicesPresetsController.mActiveHearingDevice != null) {
                        StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(index, hapGroup, "selectPresetSynchronously, presetIndex: ", ", groupId: ", ", device: ");
                        m.append(hearingDevicesPresetsController.mActiveHearingDevice.mDevice.getAddress());
                        Log.d("HearingDevicesPresetsController", m.toString());
                        BluetoothHapClient bluetoothHapClient3 = hearingDevicesPresetsController.mHapClientProfile.mService;
                        if (bluetoothHapClient3 == null) {
                            Log.w("HapClientProfile", "Proxy not attached to service. Cannot select preset for group.");
                        } else {
                            bluetoothHapClient3.selectPresetForGroup(hapGroup, index);
                        }
                    }
                }
                HearingDevicesDialogDelegate.this.mPresetSpinner.setSelection(i);
            }

            @Override // android.widget.AdapterView.OnItemSelectedListener
            public final void onNothingSelected(AdapterView adapterView) {
            }
        });
        refreshPresetInfoAdapter(this.mPresetsController.getActivePresetIndex(), this.mPresetsController.getAllPresetInfo());
        this.mPresetSpinner.setVisibility((activeHearingDevice == null || this.mPresetInfoAdapter.isEmpty()) ? 8 : 0);
        if (this.mShowPairNewDevice) {
            this.mPairButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Object obj;
                    HearingDevicesDialogDelegate hearingDevicesDialogDelegate = HearingDevicesDialogDelegate.this;
                    SystemUIDialog systemUIDialog2 = systemUIDialog;
                    SystemUIDialog systemUIDialog3 = hearingDevicesDialogDelegate.mDialog;
                    if (systemUIDialog3 != null) {
                        systemUIDialog3.dismiss();
                    }
                    Intent intent = new Intent("android.settings.HEARING_DEVICES_PAIRING_SETTINGS");
                    intent.addFlags(268468224);
                    DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate.mDialogTransitionAnimator;
                    Iterator it = dialogTransitionAnimator.openedDialogs.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            obj = null;
                            break;
                        } else {
                            obj = it.next();
                            if (Intrinsics.areEqual(((AnimatedDialog) obj).dialog, systemUIDialog2)) {
                                break;
                            }
                        }
                    }
                    AnimatedDialog animatedDialog = (AnimatedDialog) obj;
                    hearingDevicesDialogDelegate.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, animatedDialog != null ? dialogTransitionAnimator.createActivityTransitionController(animatedDialog, null) : null);
                }
            });
        } else {
            this.mPairButton.setVisibility(8);
        }
        Flags.FEATURE_FLAGS.getClass();
        Context context = systemUIDialog.getContext();
        ArrayList arrayList = new ArrayList();
        PackageManager packageManager = context.getPackageManager();
        Intent intent = LIVE_CAPTION_INTENT;
        intent.setPackage(packageManager.getSystemCaptionsServicePackageName());
        ToolItem toolItem = !packageManager.queryIntentActivities(intent, 0).isEmpty() ? new ToolItem(context.getString(R.string.live_caption_title), context.getDrawable(R.drawable.ic_volume_odi_captions), intent) : null;
        if (toolItem != null) {
            arrayList.add(toolItem);
        }
        try {
            arrayList.addAll(HearingDevicesToolItemParser.parseStringArray(context, context.getResources().getStringArray(R.array.config_quickSettingsHearingDevicesRelatedToolName), context.getResources().getStringArray(R.array.config_quickSettingsHearingDevicesRelatedToolIcon)));
        } catch (Resources.NotFoundException unused) {
            Log.i("HearingDevicesDialogDelegate", "No hearing devices related tool config resource");
        }
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ToolItem toolItem2 = (ToolItem) arrayList.get(i);
            final View inflate = LayoutInflater.from(context).inflate(R.layout.hearing_tool_item, (ViewGroup) this.mRelatedToolsContainer, false);
            ImageView imageView = (ImageView) inflate.requireViewById(R.id.tool_icon);
            TextView textView = (TextView) inflate.requireViewById(R.id.tool_name);
            inflate.setContentDescription(toolItem2.toolName);
            imageView.setImageDrawable(toolItem2.toolIcon);
            textView.setText(toolItem2.toolName);
            final Intent intent2 = toolItem2.toolIntent;
            intent2.addFlags(268468224);
            inflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    HearingDevicesDialogDelegate hearingDevicesDialogDelegate = HearingDevicesDialogDelegate.this;
                    Intent intent3 = intent2;
                    View view2 = inflate;
                    SystemUIDialog systemUIDialog2 = hearingDevicesDialogDelegate.mDialog;
                    if (systemUIDialog2 != null) {
                        systemUIDialog2.dismiss();
                    }
                    DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate.mDialogTransitionAnimator;
                    dialogTransitionAnimator.getClass();
                    hearingDevicesDialogDelegate.mActivityStarter.postStartActivityDismissingKeyguard(intent3, 0, DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view2));
                }
            });
            this.mRelatedToolsContainer.addView(inflate);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        this.mHearingDeviceItemList = getHearingDevicesList();
        this.mMainHandler.post(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda8(this, 1));
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStart(Dialog dialog) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        localBluetoothManager.mEventManager.registerCallback(this);
        HearingDevicesPresetsController hearingDevicesPresetsController = this.mPresetsController;
        if (hearingDevicesPresetsController != null) {
            hearingDevicesPresetsController.registerHapCallback();
            HapClientProfile hapClientProfile = this.mHapClientProfile;
            if (hapClientProfile == null || hapClientProfile.mIsProfileReady) {
                return;
            }
            ((CopyOnWriteArrayList) this.mProfileManager.mServiceListeners).add(this.mPresetsController);
        }
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStop(Dialog dialog) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        HearingDevicesPresetsController hearingDevicesPresetsController = this.mPresetsController;
        if (hearingDevicesPresetsController != null) {
            HapClientProfile hapClientProfile = hearingDevicesPresetsController.mHapClientProfile;
            if (hapClientProfile != null) {
                try {
                    BluetoothHapClient bluetoothHapClient = hapClientProfile.mService;
                    if (bluetoothHapClient == null) {
                        Log.w("HapClientProfile", "Proxy not attached to service. Cannot unregister callback.");
                    } else {
                        bluetoothHapClient.unregisterCallback(hearingDevicesPresetsController);
                    }
                } catch (IllegalArgumentException e) {
                    Log.w("HearingDevicesPresetsController", "Cannot unregister callback: " + e.getMessage());
                }
            }
            ((CopyOnWriteArrayList) this.mProfileManager.mServiceListeners).remove(this.mPresetsController);
        }
        ((CopyOnWriteArrayList) localBluetoothManager.mEventManager.mCallbacks).remove(this);
    }

    public final void refreshPresetInfoAdapter(int i, List list) {
        this.mPresetInfoAdapter.clear();
        this.mPresetInfoAdapter.addAll(list.stream().map(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda1(0)).toList());
        if (i != 0) {
            int count = this.mPresetInfoAdapter.getCount();
            for (int i2 = 0; i2 < count; i2++) {
                if (((BluetoothHapPresetInfo) list.get(i2)).getIndex() == i) {
                    this.mPresetSpinner.setSelection(i2);
                }
            }
        }
    }
}
