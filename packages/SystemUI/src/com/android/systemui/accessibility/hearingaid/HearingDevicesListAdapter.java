package com.android.systemui.accessibility.hearingaid;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate;
import com.android.systemui.accessibility.hearingaid.HearingDevicesListAdapter;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.bluetooth.qsdialog.DeviceItem;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.List;
import kotlin.Pair;

/* loaded from: classes.dex */
public class HearingDevicesListAdapter extends RecyclerView.Adapter {
    public final HearingDeviceItemCallback mCallback;
    public final List mItemList;

    public class DeviceItemViewHolder extends RecyclerView.ViewHolder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final View mContainer;
        public final Context mContext;
        public final View mDividerView;
        public final ImageView mGearIcon;
        public final View mGearView;
        public final ImageView mIconView;
        public final TextView mNameView;
        public final TextView mSummaryView;

        public DeviceItemViewHolder(View view, Context context) {
            super(view);
            this.mContext = context;
            this.mContainer = view.requireViewById(R.id.bluetooth_device_row);
            this.mNameView = (TextView) view.requireViewById(R.id.bluetooth_device_name);
            this.mSummaryView = (TextView) view.requireViewById(R.id.bluetooth_device_summary);
            this.mIconView = (ImageView) view.requireViewById(R.id.bluetooth_device_icon);
            this.mGearIcon = (ImageView) view.requireViewById(R.id.gear_icon_image);
            this.mGearView = view.requireViewById(R.id.gear_icon);
            this.mDividerView = view.requireViewById(R.id.divider);
        }
    }

    public interface HearingDeviceItemCallback {
    }

    public HearingDevicesListAdapter(List<DeviceItem> list, HearingDeviceItemCallback hearingDeviceItemCallback) {
        this.mItemList = list;
        this.mCallback = hearingDeviceItemCallback;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mItemList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final DeviceItem deviceItem = (DeviceItem) this.mItemList.get(i);
        DeviceItemViewHolder deviceItemViewHolder = (DeviceItemViewHolder) viewHolder;
        deviceItemViewHolder.mContainer.setEnabled(deviceItem.isEnabled);
        View view = deviceItemViewHolder.mContainer;
        final HearingDeviceItemCallback hearingDeviceItemCallback = this.mCallback;
        final int i2 = 0;
        view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesListAdapter$DeviceItemViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                boolean zRemoveActiveDevice;
                switch (i2) {
                    case 0:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback2 = hearingDeviceItemCallback;
                        DeviceItem deviceItem2 = deviceItem;
                        int i3 = HearingDevicesListAdapter.DeviceItemViewHolder.$r8$clinit;
                        HearingDevicesDialogDelegate hearingDevicesDialogDelegate = (HearingDevicesDialogDelegate) hearingDeviceItemCallback2;
                        hearingDevicesDialogDelegate.getClass();
                        int i4 = HearingDevicesDialogDelegate.AnonymousClass6.$SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType[deviceItem2.type.ordinal()];
                        int i5 = 1;
                        CachedBluetoothDevice cachedBluetoothDevice = deviceItem2.cachedBluetoothDevice;
                        int i6 = hearingDevicesDialogDelegate.mLaunchSourceId;
                        HearingDevicesUiEventLogger hearingDevicesUiEventLogger = hearingDevicesDialogDelegate.mUiEventLogger;
                        if (i4 != 1 && i4 != 2) {
                            if (i4 == 3) {
                                hearingDevicesUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_SET_ACTIVE, i6, null);
                                LocalBluetoothProfileManager localBluetoothProfileManager = cachedBluetoothDevice.mProfileManager;
                                if (localBluetoothProfileManager != null) {
                                    A2dpProfile a2dpProfile = localBluetoothProfileManager.mA2dpProfile;
                                    if (a2dpProfile != null && cachedBluetoothDevice.isConnectedProfile(a2dpProfile)) {
                                        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                                        BluetoothAdapter bluetoothAdapter = a2dpProfile.mBluetoothAdapter;
                                        if (bluetoothAdapter == null ? false : bluetoothDevice == null ? bluetoothAdapter.removeActiveDevice(0) : bluetoothAdapter.setActiveDevice(bluetoothDevice, 0)) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: A2DP active device=" + cachedBluetoothDevice);
                                        }
                                    }
                                    HeadsetProfile headsetProfile = cachedBluetoothDevice.mProfileManager.mHeadsetProfile;
                                    if (headsetProfile != null && cachedBluetoothDevice.isConnectedProfile(headsetProfile)) {
                                        BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
                                        BluetoothAdapter bluetoothAdapter2 = headsetProfile.mBluetoothAdapter;
                                        if (bluetoothAdapter2 == null ? false : bluetoothDevice2 == null ? bluetoothAdapter2.removeActiveDevice(1) : bluetoothAdapter2.setActiveDevice(bluetoothDevice2, 1)) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: Headset active device=" + cachedBluetoothDevice);
                                        }
                                    }
                                    HearingAidProfile hearingAidProfile = cachedBluetoothDevice.mProfileManager.mHearingAidProfile;
                                    if (hearingAidProfile != null && cachedBluetoothDevice.isConnectedProfile(hearingAidProfile)) {
                                        BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
                                        if (hearingAidProfile.mBluetoothAdapter == null) {
                                            zRemoveActiveDevice = false;
                                        } else {
                                            int mode = ((AudioManager) hearingAidProfile.mContext.getSystemService(AudioManager.class)).getMode();
                                            if (mode != 1 && mode != 2 && mode != 3) {
                                                i5 = 0;
                                            }
                                            zRemoveActiveDevice = bluetoothDevice3 == null ? hearingAidProfile.mBluetoothAdapter.removeActiveDevice(i5) : hearingAidProfile.mBluetoothAdapter.setActiveDevice(bluetoothDevice3, i5);
                                        }
                                        if (zRemoveActiveDevice) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: Hearing Aid active device=" + cachedBluetoothDevice);
                                        }
                                    }
                                    LeAudioProfile leAudioProfile = cachedBluetoothDevice.mProfileManager.mLeAudioProfile;
                                    if (leAudioProfile != null && cachedBluetoothDevice.isConnectedProfile(leAudioProfile)) {
                                        BluetoothDevice bluetoothDevice4 = cachedBluetoothDevice.mDevice;
                                        BluetoothAdapter bluetoothAdapter3 = leAudioProfile.mBluetoothAdapter;
                                        if (bluetoothAdapter3 != null ? bluetoothDevice4 == null ? bluetoothAdapter3.removeActiveDevice(2) : bluetoothAdapter3.setActiveDevice(bluetoothDevice4, 2) : false) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: LeAudio active device=" + cachedBluetoothDevice);
                                            break;
                                        }
                                    }
                                }
                            } else if (i4 == 4) {
                                hearingDevicesUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_CONNECT, i6, null);
                                cachedBluetoothDevice.connect$1();
                                break;
                            }
                        } else {
                            hearingDevicesUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_DISCONNECT, i6, null);
                            cachedBluetoothDevice.disconnect();
                            break;
                        }
                        break;
                    default:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback3 = hearingDeviceItemCallback;
                        DeviceItem deviceItem3 = deviceItem;
                        int i7 = HearingDevicesListAdapter.DeviceItemViewHolder.$r8$clinit;
                        HearingDevicesDialogDelegate hearingDevicesDialogDelegate2 = (HearingDevicesDialogDelegate) hearingDeviceItemCallback3;
                        hearingDevicesDialogDelegate2.getClass();
                        hearingDevicesDialogDelegate2.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_GEAR_CLICK, hearingDevicesDialogDelegate2.mLaunchSourceId, null);
                        SystemUIDialog systemUIDialog = hearingDevicesDialogDelegate2.mDialog;
                        if (systemUIDialog != null) {
                            systemUIDialog.dismiss();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("device_address", deviceItem3.cachedBluetoothDevice.mDevice.getAddress());
                        Intent intentPutExtra = new Intent("com.android.settings.BLUETOOTH_DEVICE_DETAIL_SETTINGS").setPackage(hearingDevicesDialogDelegate2.mQSSettingsPackageRepository.getSettingsPackageName()).putExtra(":settings:show_fragment_args", bundle);
                        DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate2.mDialogTransitionAnimator;
                        dialogTransitionAnimator.getClass();
                        hearingDevicesDialogDelegate2.mActivityStarter.postStartActivityDismissingKeyguard(intentPutExtra, 0, DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view2));
                        break;
                }
            }
        });
        Integer num = deviceItem.background;
        if (num != null) {
            deviceItemViewHolder.mContainer.setBackground(deviceItemViewHolder.mContext.getDrawable(num.intValue()));
        }
        boolean z = deviceItem.isActive;
        int color = z ? deviceItemViewHolder.mContext.getColor(android.R.color.resolver_text_color_secondary_dark) : deviceItemViewHolder.mContext.getColor(android.R.color.search_url_text_material_light);
        Pair pair = deviceItem.iconWithDescription;
        if (pair != null) {
            deviceItemViewHolder.mIconView.setImageDrawable((Drawable) pair.getFirst());
            deviceItemViewHolder.mIconView.setContentDescription((CharSequence) pair.getSecond());
        }
        TextView textView = deviceItemViewHolder.mNameView;
        int i3 = R.style.TextAppearance_BluetoothTileDialog;
        textView.setTextAppearance(z ? 2132018425 : 2132018424);
        deviceItemViewHolder.mNameView.setText(deviceItem.deviceName);
        TextView textView2 = deviceItemViewHolder.mSummaryView;
        if (z) {
            i3 = 2132018425;
        }
        textView2.setTextAppearance(i3);
        deviceItemViewHolder.mSummaryView.setText(deviceItem.connectionSummary);
        deviceItemViewHolder.mGearIcon.getDrawable().mutate().setTint(color);
        final int i4 = 1;
        deviceItemViewHolder.mGearView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesListAdapter$DeviceItemViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                boolean zRemoveActiveDevice;
                switch (i4) {
                    case 0:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback2 = hearingDeviceItemCallback;
                        DeviceItem deviceItem2 = deviceItem;
                        int i32 = HearingDevicesListAdapter.DeviceItemViewHolder.$r8$clinit;
                        HearingDevicesDialogDelegate hearingDevicesDialogDelegate = (HearingDevicesDialogDelegate) hearingDeviceItemCallback2;
                        hearingDevicesDialogDelegate.getClass();
                        int i42 = HearingDevicesDialogDelegate.AnonymousClass6.$SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType[deviceItem2.type.ordinal()];
                        int i5 = 1;
                        CachedBluetoothDevice cachedBluetoothDevice = deviceItem2.cachedBluetoothDevice;
                        int i6 = hearingDevicesDialogDelegate.mLaunchSourceId;
                        HearingDevicesUiEventLogger hearingDevicesUiEventLogger = hearingDevicesDialogDelegate.mUiEventLogger;
                        if (i42 != 1 && i42 != 2) {
                            if (i42 == 3) {
                                hearingDevicesUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_SET_ACTIVE, i6, null);
                                LocalBluetoothProfileManager localBluetoothProfileManager = cachedBluetoothDevice.mProfileManager;
                                if (localBluetoothProfileManager != null) {
                                    A2dpProfile a2dpProfile = localBluetoothProfileManager.mA2dpProfile;
                                    if (a2dpProfile != null && cachedBluetoothDevice.isConnectedProfile(a2dpProfile)) {
                                        BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                                        BluetoothAdapter bluetoothAdapter = a2dpProfile.mBluetoothAdapter;
                                        if (bluetoothAdapter == null ? false : bluetoothDevice == null ? bluetoothAdapter.removeActiveDevice(0) : bluetoothAdapter.setActiveDevice(bluetoothDevice, 0)) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: A2DP active device=" + cachedBluetoothDevice);
                                        }
                                    }
                                    HeadsetProfile headsetProfile = cachedBluetoothDevice.mProfileManager.mHeadsetProfile;
                                    if (headsetProfile != null && cachedBluetoothDevice.isConnectedProfile(headsetProfile)) {
                                        BluetoothDevice bluetoothDevice2 = cachedBluetoothDevice.mDevice;
                                        BluetoothAdapter bluetoothAdapter2 = headsetProfile.mBluetoothAdapter;
                                        if (bluetoothAdapter2 == null ? false : bluetoothDevice2 == null ? bluetoothAdapter2.removeActiveDevice(1) : bluetoothAdapter2.setActiveDevice(bluetoothDevice2, 1)) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: Headset active device=" + cachedBluetoothDevice);
                                        }
                                    }
                                    HearingAidProfile hearingAidProfile = cachedBluetoothDevice.mProfileManager.mHearingAidProfile;
                                    if (hearingAidProfile != null && cachedBluetoothDevice.isConnectedProfile(hearingAidProfile)) {
                                        BluetoothDevice bluetoothDevice3 = cachedBluetoothDevice.mDevice;
                                        if (hearingAidProfile.mBluetoothAdapter == null) {
                                            zRemoveActiveDevice = false;
                                        } else {
                                            int mode = ((AudioManager) hearingAidProfile.mContext.getSystemService(AudioManager.class)).getMode();
                                            if (mode != 1 && mode != 2 && mode != 3) {
                                                i5 = 0;
                                            }
                                            zRemoveActiveDevice = bluetoothDevice3 == null ? hearingAidProfile.mBluetoothAdapter.removeActiveDevice(i5) : hearingAidProfile.mBluetoothAdapter.setActiveDevice(bluetoothDevice3, i5);
                                        }
                                        if (zRemoveActiveDevice) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: Hearing Aid active device=" + cachedBluetoothDevice);
                                        }
                                    }
                                    LeAudioProfile leAudioProfile = cachedBluetoothDevice.mProfileManager.mLeAudioProfile;
                                    if (leAudioProfile != null && cachedBluetoothDevice.isConnectedProfile(leAudioProfile)) {
                                        BluetoothDevice bluetoothDevice4 = cachedBluetoothDevice.mDevice;
                                        BluetoothAdapter bluetoothAdapter3 = leAudioProfile.mBluetoothAdapter;
                                        if (bluetoothAdapter3 != null ? bluetoothDevice4 == null ? bluetoothAdapter3.removeActiveDevice(2) : bluetoothAdapter3.setActiveDevice(bluetoothDevice4, 2) : false) {
                                            Log.i("CachedBluetoothDevice", "OnPreferenceClickListener: LeAudio active device=" + cachedBluetoothDevice);
                                            break;
                                        }
                                    }
                                }
                            } else if (i42 == 4) {
                                hearingDevicesUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_CONNECT, i6, null);
                                cachedBluetoothDevice.connect$1();
                                break;
                            }
                        } else {
                            hearingDevicesUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_DISCONNECT, i6, null);
                            cachedBluetoothDevice.disconnect();
                            break;
                        }
                        break;
                    default:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback3 = hearingDeviceItemCallback;
                        DeviceItem deviceItem3 = deviceItem;
                        int i7 = HearingDevicesListAdapter.DeviceItemViewHolder.$r8$clinit;
                        HearingDevicesDialogDelegate hearingDevicesDialogDelegate2 = (HearingDevicesDialogDelegate) hearingDeviceItemCallback3;
                        hearingDevicesDialogDelegate2.getClass();
                        hearingDevicesDialogDelegate2.mUiEventLogger.log(HearingDevicesUiEvent.HEARING_DEVICES_GEAR_CLICK, hearingDevicesDialogDelegate2.mLaunchSourceId, null);
                        SystemUIDialog systemUIDialog = hearingDevicesDialogDelegate2.mDialog;
                        if (systemUIDialog != null) {
                            systemUIDialog.dismiss();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("device_address", deviceItem3.cachedBluetoothDevice.mDevice.getAddress());
                        Intent intentPutExtra = new Intent("com.android.settings.BLUETOOTH_DEVICE_DETAIL_SETTINGS").setPackage(hearingDevicesDialogDelegate2.mQSSettingsPackageRepository.getSettingsPackageName()).putExtra(":settings:show_fragment_args", bundle);
                        DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate2.mDialogTransitionAnimator;
                        dialogTransitionAnimator.getClass();
                        hearingDevicesDialogDelegate2.mActivityStarter.postStartActivityDismissingKeyguard(intentPutExtra, 0, DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view2));
                        break;
                }
            }
        });
        deviceItemViewHolder.mDividerView.setBackgroundColor(color);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DeviceItemViewHolder(KeyguardSecurityContainer$UserSwitcherViewMode$2$$ExternalSyntheticOutline0.m(viewGroup, R.layout.bluetooth_device_item, viewGroup, false), viewGroup.getContext());
    }
}
