package com.android.systemui.accessibility.hearingaid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.HearingDevicesDialogDelegate;
import com.android.systemui.accessibility.hearingaid.HearingDevicesListAdapter;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.bluetooth.qsdialog.DeviceItem;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.List;
import kotlin.Pair;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class HearingDevicesListAdapter extends RecyclerView.Adapter {
    public final HearingDeviceItemCallback mCallback;
    public final List mItemList;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DeviceItemViewHolder extends RecyclerView.ViewHolder {
        public final View mContainer;
        public final Context mContext;
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
            this.mGearView = view.requireViewById(R.id.gear_icon);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                switch (i2) {
                    case 0:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback2 = hearingDeviceItemCallback;
                        DeviceItem deviceItem2 = deviceItem;
                        ((HearingDevicesDialogDelegate) hearingDeviceItemCallback2).getClass();
                        CachedBluetoothDevice cachedBluetoothDevice = deviceItem2.cachedBluetoothDevice;
                        int i3 = HearingDevicesDialogDelegate.AnonymousClass4.$SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType[deviceItem2.type.ordinal()];
                        if (i3 != 1 && i3 != 2) {
                            if (i3 == 3) {
                                cachedBluetoothDevice.setActive();
                                break;
                            } else if (i3 == 4) {
                                cachedBluetoothDevice.connect$1();
                                break;
                            }
                        } else {
                            cachedBluetoothDevice.disconnect();
                            break;
                        }
                        break;
                    default:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback3 = hearingDeviceItemCallback;
                        DeviceItem deviceItem3 = deviceItem;
                        HearingDevicesDialogDelegate hearingDevicesDialogDelegate = (HearingDevicesDialogDelegate) hearingDeviceItemCallback3;
                        SystemUIDialog systemUIDialog = hearingDevicesDialogDelegate.mDialog;
                        if (systemUIDialog != null) {
                            systemUIDialog.dismiss();
                        }
                        Intent intent = new Intent("com.android.settings.BLUETOOTH_DEVICE_DETAIL_SETTINGS");
                        Bundle bundle = new Bundle();
                        bundle.putString("device_address", deviceItem3.cachedBluetoothDevice.mDevice.getAddress());
                        intent.putExtra(":settings:show_fragment_args", bundle);
                        intent.addFlags(268468224);
                        DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate.mDialogTransitionAnimator;
                        dialogTransitionAnimator.getClass();
                        hearingDevicesDialogDelegate.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view2));
                        break;
                }
            }
        });
        Integer num = deviceItem.background;
        if (num != null) {
            deviceItemViewHolder.mContainer.setBackground(deviceItemViewHolder.mContext.getDrawable(num.intValue()));
        }
        deviceItemViewHolder.mNameView.setText(deviceItem.deviceName);
        deviceItemViewHolder.mSummaryView.setText(deviceItem.connectionSummary);
        Pair pair = deviceItem.iconWithDescription;
        if (pair != null) {
            deviceItemViewHolder.mIconView.setImageDrawable((Drawable) pair.getFirst());
            deviceItemViewHolder.mIconView.setContentDescription((CharSequence) pair.getSecond());
        }
        final int i3 = 1;
        deviceItemViewHolder.mGearView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.hearingaid.HearingDevicesListAdapter$DeviceItemViewHolder$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                switch (i3) {
                    case 0:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback2 = hearingDeviceItemCallback;
                        DeviceItem deviceItem2 = deviceItem;
                        ((HearingDevicesDialogDelegate) hearingDeviceItemCallback2).getClass();
                        CachedBluetoothDevice cachedBluetoothDevice = deviceItem2.cachedBluetoothDevice;
                        int i32 = HearingDevicesDialogDelegate.AnonymousClass4.$SwitchMap$com$android$systemui$bluetooth$qsdialog$DeviceItemType[deviceItem2.type.ordinal()];
                        if (i32 != 1 && i32 != 2) {
                            if (i32 == 3) {
                                cachedBluetoothDevice.setActive();
                                break;
                            } else if (i32 == 4) {
                                cachedBluetoothDevice.connect$1();
                                break;
                            }
                        } else {
                            cachedBluetoothDevice.disconnect();
                            break;
                        }
                        break;
                    default:
                        HearingDevicesListAdapter.HearingDeviceItemCallback hearingDeviceItemCallback3 = hearingDeviceItemCallback;
                        DeviceItem deviceItem3 = deviceItem;
                        HearingDevicesDialogDelegate hearingDevicesDialogDelegate = (HearingDevicesDialogDelegate) hearingDeviceItemCallback3;
                        SystemUIDialog systemUIDialog = hearingDevicesDialogDelegate.mDialog;
                        if (systemUIDialog != null) {
                            systemUIDialog.dismiss();
                        }
                        Intent intent = new Intent("com.android.settings.BLUETOOTH_DEVICE_DETAIL_SETTINGS");
                        Bundle bundle = new Bundle();
                        bundle.putString("device_address", deviceItem3.cachedBluetoothDevice.mDevice.getAddress());
                        intent.putExtra(":settings:show_fragment_args", bundle);
                        intent.addFlags(268468224);
                        DialogTransitionAnimator dialogTransitionAnimator = hearingDevicesDialogDelegate.mDialogTransitionAnimator;
                        dialogTransitionAnimator.getClass();
                        hearingDevicesDialogDelegate.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, DialogTransitionAnimator.createActivityTransitionController$default(dialogTransitionAnimator, view2));
                        break;
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(int i, ViewGroup viewGroup) {
        return new DeviceItemViewHolder(MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(viewGroup, R.layout.bluetooth_device_item, viewGroup, false), viewGroup.getContext());
    }
}
