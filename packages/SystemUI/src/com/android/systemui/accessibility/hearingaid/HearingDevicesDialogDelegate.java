package com.android.systemui.accessibility.hearingaid;

import android.app.Dialog;
import android.bluetooth.BluetoothHapPresetInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.settingslib.bluetooth.AmbientVolumeUiController;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.HearingDevicesInputRoutingController;
import com.android.systemui.accessibility.hearingaid.HearingDevicesListAdapter;
import com.android.systemui.accessibility.hearingaid.HearingDevicesPresetsController;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.bluetooth.qsdialog.ActiveHearingDeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.AvailableHearingDeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.ConnectedHearingDeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.DeviceItemFactory;
import com.android.systemui.bluetooth.qsdialog.DeviceItemType;
import com.android.systemui.bluetooth.qsdialog.SavedHearingDeviceItemFactory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.shared.QSSettingsPackageRepository;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class HearingDevicesDialogDelegate implements SystemUIDialog.Delegate, HearingDevicesListAdapter.HearingDeviceItemCallback, BluetoothCallback {
    static final String ACTION_BLUETOOTH_DEVICE_DETAILS = "com.android.settings.BLUETOOTH_DEVICE_DETAIL_SETTINGS";
    static final Intent LIVE_CAPTION_INTENT = new Intent("com.android.settings.action.live_caption");
    public final ActivityStarter mActivityStarter;
    public AmbientVolumeUiController mAmbientController;
    public final AudioManager mAudioManager;
    public final Executor mBgExecutor;
    public HearingDevicesListAdapter mDeviceListAdapter;
    public SystemUIDialog mDialog;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public HearingDevicesSpinnerAdapter mInputRoutingAdapter;
    public HearingDevicesInputRoutingController mInputRoutingController;
    public final HearingDevicesInputRoutingController.Factory mInputRoutingControllerFactory;
    public View mInputRoutingLayout;
    public Spinner mInputRoutingSpinner;
    public final int mLaunchSourceId;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final Executor mMainExecutor;
    public HearingDevicesPresetsController mPresetController;
    public HearingDevicesSpinnerAdapter mPresetInfoAdapter;
    public View mPresetLayout;
    public Spinner mPresetSpinner;
    public final LocalBluetoothProfileManager mProfileManager;
    public final QSSettingsPackageRepository mQSSettingsPackageRepository;
    public final boolean mShowPairNewDevice;
    public final SystemUIDialog.Factory mSystemUIDialogFactory;
    public final HearingDevicesUiEventLogger mUiEventLogger;
    public final AnonymousClass1 mPresetCallback = new AnonymousClass1();
    public final List mHearingDeviceItemFactoryList = List.of(new ActiveHearingDeviceItemFactory(), new AvailableHearingDeviceItemFactory(), new ConnectedHearingDeviceItemFactory(), new SavedHearingDeviceItemFactory());

    /* renamed from: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$1, reason: invalid class name */
    public class AnonymousClass1 implements HearingDevicesPresetsController.PresetCallback {
        public AnonymousClass1() {
        }

        public final void onPresetCommandFailed() {
            HearingDevicesDialogDelegate hearingDevicesDialogDelegate = HearingDevicesDialogDelegate.this;
            hearingDevicesDialogDelegate.mPresetController.refreshPresetInfo();
            hearingDevicesDialogDelegate.mMainExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda2(this, 3));
        }
    }

    /* renamed from: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$6, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass6 {
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

    public interface Factory {
        HearingDevicesDialogDelegate create(boolean z, int i);
    }

    public HearingDevicesDialogDelegate(boolean z, int i, SystemUIDialog.Factory factory, ActivityStarter activityStarter, DialogTransitionAnimator dialogTransitionAnimator, LocalBluetoothManager localBluetoothManager, Executor executor, Executor executor2, AudioManager audioManager, HearingDevicesUiEventLogger hearingDevicesUiEventLogger, QSSettingsPackageRepository qSSettingsPackageRepository, HearingDevicesInputRoutingController.Factory factory2) {
        this.mShowPairNewDevice = z;
        this.mSystemUIDialogFactory = factory;
        this.mActivityStarter = activityStarter;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mAudioManager = audioManager;
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mUiEventLogger = hearingDevicesUiEventLogger;
        this.mLaunchSourceId = i;
        this.mQSSettingsPackageRepository = qSSettingsPackageRepository;
        this.mInputRoutingControllerFactory = factory2;
    }

    public static CachedBluetoothDevice getActiveHearingDevice(List list) {
        return (CachedBluetoothDevice) list.stream().filter(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda8(1)).map(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda12(0)).findFirst().orElse(null);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        final SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        systemUIDialog.setTitle(R.string.quick_settings_hearing_devices_dialog_title);
        systemUIDialog.setView(LayoutInflater.from(systemUIDialog.getContext()).inflate(R.layout.hearing_devices_tile_dialog, (ViewGroup) null));
        systemUIDialog.setNegativeButton(R.string.hearing_devices_settings_button, new DialogInterface.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate = this.f$0;
                SystemUIDialog systemUIDialog2 = systemUIDialog;
                String str = HearingDevicesDialogDelegate.ACTION_BLUETOOTH_DEVICE_DETAILS;
                hearingDevicesDialogDelegate.getClass();
                hearingDevicesDialogDelegate.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_SETTINGS_CLICK, hearingDevicesDialogDelegate.mLaunchSourceId, null);
                Intent intent = new Intent("android.settings.ACCESSIBILITY_DETAILS_SETTINGS").putExtra("android.intent.extra.COMPONENT_NAME", AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME.flattenToString()).setPackage(hearingDevicesDialogDelegate.mQSSettingsPackageRepository.getSettingsPackageName());
                DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate.mDialogTransitionAnimator;
                dialogTransitionAnimator.getClass();
                hearingDevicesDialogDelegate.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, DialogTransitionAnimator.createActivityTransitionController$default(systemUIDialog2, dialogTransitionAnimator));
            }
        });
        systemUIDialog.setButton(-1, R.string.quick_settings_done, null, true);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.mSystemUIDialogFactory;
        SystemUIDialog systemUIDialogCreate = factory.create(this, factory.mContext);
        SystemUIDialog systemUIDialog = this.mDialog;
        if (systemUIDialog != null) {
            systemUIDialog.dismiss();
        }
        this.mDialog = systemUIDialogCreate;
        return systemUIDialogCreate;
    }

    public final List getHearingDeviceItemList() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        return (localBluetoothManager == null || !localBluetoothManager.mLocalAdapter.mAdapter.isEnabled()) ? Collections.EMPTY_LIST : (List) localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy().stream().map(new Function() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate$$ExternalSyntheticLambda7
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                HearingDevicesDialogDelegate hearingDevicesDialogDelegate = this.f$0;
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                Context context = hearingDevicesDialogDelegate.mDialog.getContext();
                if (cachedBluetoothDevice == null) {
                    return null;
                }
                int mode = hearingDevicesDialogDelegate.mAudioManager.getMode();
                boolean z = true;
                if (mode != 1 && mode != 2 && mode != 3) {
                    z = false;
                }
                for (DeviceItemFactory deviceItemFactory : hearingDevicesDialogDelegate.mHearingDeviceItemFactoryList) {
                    if (deviceItemFactory.isFilterMatched(context, cachedBluetoothDevice, z)) {
                        return deviceItemFactory.create(context, cachedBluetoothDevice);
                    }
                }
                return null;
            }
        }).filter(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda8(0)).collect(Collectors.toList());
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        this.mMainExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda3(this, getHearingDeviceItemList(), 1));
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        List hearingDeviceItemList = getHearingDeviceItemList();
        this.mMainExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda3(this, hearingDeviceItemList, 1));
        this.mMainExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda3(this, hearingDeviceItemList, 0));
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        if (this.mLocalBluetoothManager == null) {
            return;
        }
        View viewFindViewById = systemUIDialog.findViewById(android.R.id.custom);
        if (viewFindViewById != null && viewFindViewById.getParent() != null) {
            ((View) viewFindViewById.getParent()).setPadding(0, 0, 0, 0);
        }
        this.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_DIALOG_SHOW, this.mLaunchSourceId, null);
        this.mBgExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda3(this, systemUIDialog, 2));
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        this.mMainExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda3(this, getHearingDeviceItemList(), 1));
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStop(Dialog dialog) {
        this.mBgExecutor.execute(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda2(this, 0));
    }

    public final void refreshPresetUi(int i, List list) {
        this.mPresetInfoAdapter.clear();
        this.mPresetInfoAdapter.addAll(list.stream().map(new HearingDevicesDialogDelegate$$ExternalSyntheticLambda12(1)).toList());
        if (i != 0) {
            int count = this.mPresetInfoAdapter.getCount();
            for (int i2 = 0; i2 < count; i2++) {
                if (((BluetoothHapPresetInfo) list.get(i2)).getIndex() == i) {
                    this.mPresetSpinner.setSelection(i2, false);
                    HearingDevicesSpinnerAdapter hearingDevicesSpinnerAdapter = this.mPresetInfoAdapter;
                    hearingDevicesSpinnerAdapter.mSelectedPosition = i2;
                    hearingDevicesSpinnerAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
