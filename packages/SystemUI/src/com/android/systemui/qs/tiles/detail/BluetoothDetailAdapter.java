package com.android.systemui.qs.tiles.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tiles.SBluetoothTile;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class BluetoothDetailAdapter implements DetailAdapter, QSDetailItems.Callback {
    public final ActivityStarter mActivityStarter;
    public ViewGroup mAvailable;
    public QSDetailItems mAvailableDevicesItems;
    public View mAvailableDevicesTitle;
    public final ArrayList mAvailableItemList;
    public final Context mContext;
    public final SBluetoothController mController;
    public final boolean mDetailListening;
    public final Handler mHandler;
    public boolean mIsHavingConvertView;
    public boolean mIsSatelliteModeOn;
    public QSDetailItems mItems;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public ViewGroup mMusicShare;
    public QSDetailItems mMusicShareItems;
    public View mMusicShareTitleDivider;
    public ViewGroup mPairedDevices;
    public final ArrayList mPairedItemList = new ArrayList();
    public final PanelInteractor mPanelInteractor;
    private final SettingsHelper mSettingsHelper;
    public boolean mTransientEnabling;
    public final SBluetoothTile sBluetoothTile;

    public BluetoothDetailAdapter(Context context, Handler handler, ActivityStarter activityStarter, SettingsHelper settingsHelper, boolean z, ArrayList<QSDetailItems.Item> arrayList, PanelInteractor panelInteractor, SBluetoothController sBluetoothController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, SBluetoothTile sBluetoothTile) {
        new ArrayList();
        this.mTransientEnabling = false;
        this.mIsSatelliteModeOn = false;
        this.mContext = context;
        this.mHandler = handler;
        this.mActivityStarter = activityStarter;
        this.mSettingsHelper = settingsHelper;
        this.mDetailListening = z;
        this.mAvailableItemList = arrayList;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mPanelInteractor = panelInteractor;
        this.mController = sBluetoothController;
        this.mIsHavingConvertView = false;
        this.sBluetoothTile = sBluetoothTile;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
        if (!this.mIsHavingConvertView) {
            view = null;
        }
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.qs_detail_bluetooth, viewGroup, false);
            ViewGroup viewGroup2 = (ViewGroup) view.findViewById(R.id.paired_devices);
            this.mPairedDevices = viewGroup2;
            QSDetailItems convertOrInflate = QSDetailItems.convertOrInflate(context, viewGroup2);
            this.mItems = convertOrInflate;
            this.mPairedDevices.addView(convertOrInflate);
            ViewGroup viewGroup3 = (ViewGroup) view.findViewById(R.id.available_devices);
            this.mAvailable = viewGroup3;
            QSDetailItems convertOrInflate2 = QSDetailItems.convertOrInflate(context, viewGroup3);
            this.mAvailableDevicesItems = convertOrInflate2;
            convertOrInflate2.setTagSuffix("Bluetooth.Available");
            this.mAvailable.addView(this.mAvailableDevicesItems);
            this.mAvailableDevicesTitle = this.mAvailable.findViewById(R.id.available_devices_title);
            if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                ViewGroup viewGroup4 = (ViewGroup) view.findViewById(R.id.music_share_devices);
                this.mMusicShare = viewGroup4;
                QSDetailItems convertOrInflate3 = QSDetailItems.convertOrInflate(context, viewGroup4);
                this.mMusicShareItems = convertOrInflate3;
                this.mMusicShare.addView(convertOrInflate3);
                this.mMusicShareTitleDivider = this.mMusicShare.findViewById(R.id.music_share_title_divider);
            } else {
                ViewGroup viewGroup5 = (ViewGroup) view.findViewById(R.id.music_share_devices);
                this.mMusicShare = viewGroup5;
                viewGroup5.setVisibility(8);
            }
            this.mIsHavingConvertView = true;
        }
        QSDetailItems qSDetailItems = this.mAvailableDevicesItems;
        SBluetoothTile sBluetoothTile = this.sBluetoothTile;
        qSDetailItems.setEmptyState(((QSTile.BooleanState) sBluetoothTile.mState).value ? R.string.quick_settings_bluetooth_scanning : R.string.quick_settings_bluetooth_detail_off_text);
        this.mAvailableDevicesItems.setCallback(this);
        this.mItems.setTagSuffix("Bluetooth");
        this.mItems.setEmptyState(R.string.quick_settings_bluetooth_detail_empty_text);
        this.mItems.setCallback(this);
        if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
            this.mMusicShareItems.setTagSuffix("Bluetooth.InstantSession");
            this.mMusicShareItems.setCallback(this);
        }
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.BluetoothDetailAdapter.1
            @Override // java.lang.Runnable
            public final void run() {
                if (BluetoothDetailAdapter.this.getToggleState().booleanValue()) {
                    Collection devices$1 = ((SBluetoothControllerImpl) BluetoothDetailAdapter.this.mController).getDevices$1();
                    if (devices$1 != null) {
                        Iterator it = devices$1.iterator();
                        while (it.hasNext()) {
                            ((CachedBluetoothDevice) it.next()).mErrorMsg = null;
                        }
                    }
                    BluetoothDetailAdapter.this.updateItems$1();
                    if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                        BluetoothDetailAdapter.this.updateMusicShareItems();
                    }
                }
            }
        });
        this.mTransientEnabling = false;
        setItemsVisible(((QSTile.BooleanState) sBluetoothTile.mState).value);
        return view;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final boolean disallowStartSettingsIntent() {
        Log.d("BluetoothDetailAdapter", "disallowStartSettingsIntent");
        return false;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final int getMetricsCategory() {
        return 150;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Intent getSettingsIntent() {
        return SBluetoothTile.BLUETOOTH_SETTINGS;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final CharSequence getTitle() {
        return this.mContext.getString(R.string.quick_settings_bluetooth_label);
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final boolean getToggleEnabled() {
        SBluetoothController sBluetoothController = this.mController;
        return ((((SBluetoothControllerImpl) sBluetoothController).mState != 10 && ((SBluetoothControllerImpl) sBluetoothController).mState != 12) || this.mTransientEnabling || this.mIsSatelliteModeOn) ? false : true;
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final Boolean getToggleState() {
        return Boolean.valueOf(((QSTile.BooleanState) this.sBluetoothTile.mState).value);
    }

    @Override // com.android.systemui.qs.QSDetailItems.Callback
    public final void onDetailItemClick(QSDetailItems.Item item) {
        Object obj;
        if (item == null || (obj = item.tag) == null) {
            return;
        }
        if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE && (obj instanceof CachedBluetoothCastDevice)) {
            CachedBluetoothCastDevice cachedBluetoothCastDevice = (CachedBluetoothCastDevice) obj;
            if (cachedBluetoothCastDevice.isConnected()) {
                cachedBluetoothCastDevice.disconnect();
                return;
            } else {
                cachedBluetoothCastDevice.connect();
                return;
            }
        }
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        boolean isConnected = cachedBluetoothDevice.isConnected();
        SBluetoothController sBluetoothController = this.mController;
        if (isConnected) {
            SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) sBluetoothController;
            if (sBluetoothControllerImpl.mLocalBluetoothManager != null) {
                sBluetoothControllerImpl.scan(false);
                cachedBluetoothDevice.disconnect();
                return;
            }
            return;
        }
        if (cachedBluetoothDevice.getMaxConnectionState() == 0) {
            SBluetoothControllerImpl sBluetoothControllerImpl2 = (SBluetoothControllerImpl) sBluetoothController;
            if (sBluetoothControllerImpl2.mLocalBluetoothManager != null) {
                sBluetoothControllerImpl2.scan(false);
                cachedBluetoothDevice.connect$1();
            }
        }
    }

    public final void setItemsVisible(boolean z) {
        if (this.mItems == null) {
            return;
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setItemsVisible = ", "BluetoothDetailAdapter", z);
        if (z) {
            return;
        }
        this.mItems.setItems(null);
        this.mAvailableDevicesItems.setItems(null);
        if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
            this.mMusicShareItems.setItems(null);
        }
        this.mHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.BluetoothDetailAdapter.2
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothDetailAdapter.this.mPairedDevices.setVisibility(8);
                BluetoothDetailAdapter.this.mAvailable.findViewById(R.id.available_devices_group).setVisibility(8);
                if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                    BluetoothDetailAdapter.this.mMusicShareTitleDivider.setVisibility(8);
                    BluetoothDetailAdapter.this.mMusicShareItems.setVisibility(8);
                }
            }
        });
    }

    @Override // com.android.systemui.plugins.qs.DetailAdapter
    public final void setToggleState(boolean z) {
        MetricsLogger.action(this.mContext, 154, z);
        Log.d("BluetoothDetailAdapter", "setToggleState state = " + z);
        if (this.mIsSatelliteModeOn) {
            return;
        }
        boolean isBluetoothTileBlocked = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isBluetoothTileBlocked();
        SBluetoothTile sBluetoothTile = this.sBluetoothTile;
        if (isBluetoothTileBlocked || sBluetoothTile.isBlockedByEASPolicy$1()) {
            sBluetoothTile.showItPolicyToast();
            return;
        }
        if (((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                this.mPanelInteractor.forceCollapsePanels();
                this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.BluetoothDetailAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BluetoothDetailAdapter.this.sBluetoothTile.onToggleStateChange(!r1.getToggleState().booleanValue());
                    }
                });
                sBluetoothTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
        }
        SBluetoothController sBluetoothController = this.mController;
        if (!z) {
            ((SBluetoothControllerImpl) sBluetoothController).scan(false);
            if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                ((SBluetoothControllerImpl) sBluetoothController).scanMusicShareDevices(false, this.mDetailListening);
            }
        }
        this.mTransientEnabling = true;
        sBluetoothTile.onToggleStateChange(z);
        ((SBluetoothControllerImpl) sBluetoothController).setBluetoothEnabled(z, false);
        if (sBluetoothTile.isBlockedByEASPolicy$1()) {
            sBluetoothTile.onToggleStateChange(getToggleState().booleanValue());
        } else {
            this.mAvailableDevicesItems.setEmptyState(z ? R.string.quick_settings_bluetooth_scanning : R.string.quick_settings_bluetooth_detail_off_text);
        }
    }

    public final void updateItems$1() {
        boolean z;
        if (this.mItems == null) {
            return;
        }
        Log.d("BluetoothDetailAdapter", "updateItems");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Collection<CachedBluetoothDevice> devices$1 = ((SBluetoothControllerImpl) this.mController).getDevices$1();
        if (devices$1 != null) {
            Log.d("BluetoothDetailAdapter", "mController.getDevices() list: " + devices$1.size());
            if (devices$1 instanceof ArrayList) {
                Collections.sort((ArrayList) devices$1);
            }
            for (CachedBluetoothDevice cachedBluetoothDevice : devices$1) {
                int i = cachedBluetoothDevice.mBondState;
                QSDetailItems.Item item = new QSDetailItems.Item();
                if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                    Context context = this.mContext;
                    boolean z2 = BluetoothUtils.DEBUG;
                    int color = "com.android.systemui".equals(context.getPackageName().toLowerCase()) ? context.getResources().getColor(R.color.qs_detail_item_device_bt_icon_tint_color) : context.getResources().getColor(R.color.bt_device_icon_tint_color);
                    Drawable iconDrawable = cachedBluetoothDevice.getIconDrawable(true);
                    if (BluetoothUtils.isBtCastConnectedAsHost(context, cachedBluetoothDevice.mDevice.getAddress())) {
                        iconDrawable = BluetoothUtils.getOverlayIconTintableDrawable(iconDrawable, context, R.drawable.sharing_ic_overlay, R.drawable.sharing_ic_tintable);
                    } else {
                        iconDrawable.setTint(color);
                    }
                    item.overlay = iconDrawable;
                    iconDrawable.setTint(this.mContext.getResources().getColor(R.color.qs_detail_item_device_bt_icon_tint_color));
                } else {
                    item.overlay = null;
                    item.overlay = this.mContext.getResources().getDrawable(cachedBluetoothDevice.getBtClassDrawable());
                }
                item.updateIconSize = true;
                CachedBluetoothDevice deviceForGroupConnectionState = BluetoothUtils.getDeviceForGroupConnectionState(cachedBluetoothDevice);
                StringBuilder sb = new StringBuilder();
                String str = cachedBluetoothDevice.mPrefixName;
                if (str == null) {
                    str = "";
                }
                sb.append(str);
                sb.append(cachedBluetoothDevice.getName());
                item.line1 = sb.toString();
                int maxConnectionState = deviceForGroupConnectionState.getMaxConnectionState();
                if (!cachedBluetoothDevice.isConnected()) {
                    Iterator it = ((HashSet) cachedBluetoothDevice.mMemberDevices).iterator();
                    while (it.hasNext()) {
                        if (((CachedBluetoothDevice) it.next()).isConnected()) {
                        }
                    }
                    z = false;
                    item.isActive = z;
                    item.line2 = deviceForGroupConnectionState.getConnectionSummary();
                    item.tag = cachedBluetoothDevice;
                    if (i != 12 || cachedBluetoothDevice.mIsRestored) {
                        arrayList.add(item);
                    } else {
                        arrayList2.add(item);
                    }
                    if (maxConnectionState != 1 || maxConnectionState == 3) {
                        item.isInProgress = true;
                    }
                }
                z = true;
                item.isActive = z;
                item.line2 = deviceForGroupConnectionState.getConnectionSummary();
                item.tag = cachedBluetoothDevice;
                if (i != 12) {
                }
                arrayList.add(item);
                if (maxConnectionState != 1) {
                }
                item.isInProgress = true;
            }
        }
        this.mItems.setItems((QSDetailItems.Item[]) arrayList.toArray(new QSDetailItems.Item[arrayList.size()]));
        this.mItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.BluetoothDetailAdapter.4
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothDetailAdapter bluetoothDetailAdapter = BluetoothDetailAdapter.this;
                bluetoothDetailAdapter.mPairedDevices.setVisibility(bluetoothDetailAdapter.mItems.mAdapter.getCount() > 0 ? 0 : 8);
            }
        });
        this.mAvailableDevicesItems.setItems((QSDetailItems.Item[]) arrayList2.toArray(new QSDetailItems.Item[arrayList2.size()]));
        this.mAvailableDevicesItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.BluetoothDetailAdapter.5
            @Override // java.lang.Runnable
            public final void run() {
                BluetoothDetailAdapter.this.mAvailable.findViewById(R.id.available_devices_group).setVisibility(BluetoothDetailAdapter.this.mAvailableDevicesItems.mAdapter.getCount() > 0 ? 0 : 8);
            }
        });
        this.mPairedItemList.clear();
        this.mPairedItemList.addAll(arrayList);
        this.mAvailableItemList.clear();
        this.mAvailableItemList.addAll(arrayList2);
    }

    public final void updateMusicShareItems() {
        Log.d("BluetoothDetailAdapter", "updateMusicShareItems()");
        if (!QpRune.QUICK_BLUETOOTH_MUSIC_SHARE || this.mMusicShareItems == null) {
            return;
        }
        LocalBluetoothManager localBluetoothManager = ((SBluetoothControllerImpl) this.mController).mLocalBluetoothManager;
        Collection<CachedBluetoothCastDevice> cachedCastDevicesCopy = localBluetoothManager != null ? localBluetoothManager.mCachedCastDeviceManager.getCachedCastDevicesCopy() : null;
        if (cachedCastDevicesCopy == null) {
            Log.d("BluetoothDetailAdapter", "getCachedCastDevices() is null.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        Collections.sort((ArrayList) cachedCastDevicesCopy);
        for (CachedBluetoothCastDevice cachedBluetoothCastDevice : cachedCastDevicesCopy) {
            QSDetailItems.Item item = new QSDetailItems.Item();
            item.overlay = cachedBluetoothCastDevice.getBtCastDrawable();
            item.line1 = cachedBluetoothCastDevice.getName();
            int maxConnectionState = cachedBluetoothCastDevice.getMaxConnectionState();
            item.isActive = cachedBluetoothCastDevice.isConnected();
            item.line2 = cachedBluetoothCastDevice.getConnectionSummary();
            item.tag = cachedBluetoothCastDevice;
            item.updateIconSize = true;
            if (maxConnectionState == 1 || maxConnectionState == 3) {
                item.isInProgress = true;
            }
            arrayList.add(item);
        }
        if (arrayList.size() > 0) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Objects.toString(((QSDetailItems.Item) it.next()).line1);
            }
        }
        this.mMusicShareItems.setItems((QSDetailItems.Item[]) arrayList.toArray(new QSDetailItems.Item[arrayList.size()]));
        this.mMusicShareItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.detail.BluetoothDetailAdapter.3
            @Override // java.lang.Runnable
            public final void run() {
                int i = BluetoothDetailAdapter.this.mMusicShareItems.mAdapter.getCount() > 0 ? 0 : 8;
                BluetoothDetailAdapter.this.mMusicShareTitleDivider.setVisibility(i);
                BluetoothDetailAdapter.this.mMusicShareItems.setVisibility(i);
            }
        });
    }
}
