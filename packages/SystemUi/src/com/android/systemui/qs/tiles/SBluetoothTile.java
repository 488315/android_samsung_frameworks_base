package com.android.systemui.qs.tiles;

import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CsipSetCoordinatorProfile;
import com.android.settingslib.bluetooth.HeadsetProfile;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.HidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.bluetooth.PanProfile;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSDetailItems;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice.ToastRunnable;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDeviceManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SBluetoothTile extends SQSTileImpl {
    public boolean isHavingConvertView;
    public final ActivityStarter mActivityStarter;
    public final ArrayList mAvailableItemList;
    public int mBlueToothState;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C22663 mCallback;
    public final SBluetoothController mController;
    public final BluetoothDetailAdapter mDetailAdapter;
    public boolean mDetailListening;
    public final DisplayLifecycle mDisplayLifecycle;
    public boolean mDoStopScan;
    public final C22641 mFoldStateChangedListener;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final ArrayList mPairedItemList;
    public final PanelInteractor mPanelInteractor;
    public final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;
    public SubscreenBluetoothTileReceiver mSubscreenBlueotoothTileReceiver;
    public final SubscreenQsPanelController mSubscreenQsPanelController;
    public static final boolean DEBUG = Log.isLoggable("SBluetoothTile", 3);
    public static final Intent BLUETOOTH_SETTINGS = new Intent("android.settings.BLUETOOTH_SETTINGS");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BluetoothDetailAdapter implements DetailAdapter, QSDetailItems.Callback {
        public static final /* synthetic */ int $r8$clinit = 0;
        public ViewGroup mAvailable;
        public QSDetailItems mAvailableDevicesItems;
        public View mAvailableDevicesTitle;
        public QSDetailItems mItems;
        public ViewGroup mMusicShare;
        public QSDetailItems mMusicShareItems;
        public View mMusicShareTitleDivider;
        public ViewGroup mPairedDevices;

        /* renamed from: -$$Nest$mupdateMusicShareItems, reason: not valid java name */
        public static void m1630$$Nest$mupdateMusicShareItems(BluetoothDetailAdapter bluetoothDetailAdapter) {
            ArrayList<CachedBluetoothCastDevice> arrayList;
            String string;
            bluetoothDetailAdapter.getClass();
            Log.d("SBluetoothTile", "updateMusicShareItems()");
            if (!QpRune.QUICK_BLUETOOTH_MUSIC_SHARE || bluetoothDetailAdapter.mMusicShareItems == null) {
                return;
            }
            LocalBluetoothManager localBluetoothManager = ((SBluetoothControllerImpl) SBluetoothTile.this.mController).mLocalBluetoothManager;
            if (localBluetoothManager != null) {
                CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager = localBluetoothManager.mCachedCastDeviceManager;
                synchronized (cachedBluetoothCastDeviceManager) {
                    arrayList = new ArrayList(cachedBluetoothCastDeviceManager.mCachedCastDevices);
                }
            } else {
                arrayList = null;
            }
            if (arrayList == null) {
                Log.d("SBluetoothTile", "getCachedCastDevices() is null.");
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            Collections.sort(arrayList);
            for (CachedBluetoothCastDevice cachedBluetoothCastDevice : arrayList) {
                QSDetailItems.Item item = new QSDetailItems.Item();
                item.overlay = cachedBluetoothCastDevice.getBtCastDrawable();
                item.line1 = cachedBluetoothCastDevice.getName();
                int maxConnectionState = cachedBluetoothCastDevice.getMaxConnectionState();
                item.isActive = cachedBluetoothCastDevice.isConnected();
                ArrayList arrayList3 = new ArrayList();
                synchronized (cachedBluetoothCastDevice.mCastProfiles) {
                    arrayList3.addAll(cachedBluetoothCastDevice.mCastProfiles);
                }
                List unmodifiableList = Collections.unmodifiableList(arrayList3);
                int i = 0;
                boolean z = false;
                boolean z2 = false;
                while (true) {
                    if (i < unmodifiableList.size()) {
                        LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) unmodifiableList.get(i);
                        if (localBluetoothCastProfile == null) {
                            Log.d(cachedBluetoothCastDevice.TAG, "getConnectionSummary :: profile is null");
                        } else {
                            int castProfileConnectionState = cachedBluetoothCastDevice.getCastProfileConnectionState(localBluetoothCastProfile);
                            if (castProfileConnectionState == 0) {
                                String str = cachedBluetoothCastDevice.mErrorMsg;
                                string = (str == null || TextUtils.isEmpty(str)) ? cachedBluetoothCastDevice.mContext.getString(R.string.bluetooth_cast_via, cachedBluetoothCastDevice.mCastDevice.getPeerName()) : cachedBluetoothCastDevice.mErrorMsg;
                            } else {
                                if (castProfileConnectionState == 1) {
                                    string = cachedBluetoothCastDevice.mContext.getString(R.string.bluetooth_cast_waiting_auth, cachedBluetoothCastDevice.mCastDevice.getPeerName());
                                    break;
                                }
                                if (castProfileConnectionState != 2) {
                                    if (castProfileConnectionState == 3) {
                                        string = cachedBluetoothCastDevice.mContext.getString(BluetoothUtils.getConnectionStateSummary(castProfileConnectionState));
                                        break;
                                    }
                                } else if (localBluetoothCastProfile instanceof AudioCastProfile) {
                                    z = true;
                                    z2 = true;
                                } else {
                                    z = true;
                                }
                            }
                        }
                        i++;
                    } else {
                        string = (z && z2) ? cachedBluetoothCastDevice.mContext.getString(R.string.bluetooth_cast_connected_via, cachedBluetoothCastDevice.mCastDevice.getPeerName()) : null;
                    }
                }
                item.line2 = string;
                item.tag = cachedBluetoothCastDevice;
                item.updateIconSize = true;
                if (maxConnectionState == 1 || maxConnectionState == 3) {
                    item.isInProgress = true;
                }
                arrayList2.add(item);
            }
            if (arrayList2.size() > 0) {
                Iterator it = arrayList2.iterator();
                while (it.hasNext()) {
                    Objects.toString(((QSDetailItems.Item) it.next()).line1);
                }
            }
            bluetoothDetailAdapter.mMusicShareItems.setItems((QSDetailItems.Item[]) arrayList2.toArray(new QSDetailItems.Item[arrayList2.size()]));
            bluetoothDetailAdapter.mMusicShareItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.BluetoothDetailAdapter.3
                @Override // java.lang.Runnable
                public final void run() {
                    int i2 = BluetoothDetailAdapter.this.mMusicShareItems.getItemCount() > 0 ? 0 : 8;
                    BluetoothDetailAdapter.this.mMusicShareTitleDivider.setVisibility(i2);
                    BluetoothDetailAdapter.this.mMusicShareItems.setVisibility(i2);
                }
            });
        }

        public BluetoothDetailAdapter() {
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final View createDetailView(Context context, View view, ViewGroup viewGroup) {
            SBluetoothTile sBluetoothTile = SBluetoothTile.this;
            if (!sBluetoothTile.isHavingConvertView) {
                view = null;
            }
            if (view == null) {
                view = LayoutInflater.from(sBluetoothTile.mContext).inflate(R.layout.qs_detail_bluetooth, viewGroup, false);
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
                sBluetoothTile.isHavingConvertView = true;
            }
            this.mAvailableDevicesItems.setEmptyState(((QSTile.BooleanState) sBluetoothTile.mState).value ? R.string.quick_settings_bluetooth_scanning : R.string.quick_settings_bluetooth_detail_off_text);
            this.mAvailableDevicesItems.setCallback(this);
            this.mItems.setTagSuffix("Bluetooth");
            this.mItems.setEmptyState(R.string.quick_settings_bluetooth_detail_empty_text);
            this.mItems.setCallback(this);
            if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                this.mMusicShareItems.setTagSuffix("Bluetooth.InstantSession");
                this.mMusicShareItems.setCallback(this);
            }
            ((SQSTileImpl) sBluetoothTile).mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.BluetoothDetailAdapter.1
                @Override // java.lang.Runnable
                public final void run() {
                    if (SBluetoothTile.this.mDetailAdapter.getToggleState().booleanValue()) {
                        SBluetoothTile.this.mDetailAdapter.updateItems();
                        if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                            BluetoothDetailAdapter.m1630$$Nest$mupdateMusicShareItems(SBluetoothTile.this.mDetailAdapter);
                        }
                    }
                }
            });
            setItemsVisible(((QSTile.BooleanState) sBluetoothTile.mState).value);
            return view;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final boolean disallowStartSettingsIntent() {
            Log.d("SBluetoothTile", "disallowStartSettingsIntent");
            SBluetoothTile.this.mDoStopScan = false;
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
            boolean z = SBluetoothTile.DEBUG;
            return SBluetoothTile.this.mContext.getString(R.string.quick_settings_bluetooth_label);
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final boolean getToggleEnabled() {
            SBluetoothController sBluetoothController = SBluetoothTile.this.mController;
            return ((SBluetoothControllerImpl) sBluetoothController).mState == 10 || ((SBluetoothControllerImpl) sBluetoothController).mState == 12;
        }

        @Override // com.android.systemui.plugins.qs.DetailAdapter
        public final Boolean getToggleState() {
            boolean z = SBluetoothTile.DEBUG;
            return Boolean.valueOf(((QSTile.BooleanState) SBluetoothTile.this.mState).value);
        }

        @Override // com.android.systemui.qs.QSDetailItems.Callback
        public final void onDetailItemClick(QSDetailItems.Item item) {
            Object obj;
            if (item == null || (obj = item.tag) == null) {
                return;
            }
            if (!QpRune.QUICK_BLUETOOTH_MUSIC_SHARE || !(obj instanceof CachedBluetoothCastDevice)) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                boolean isConnected = cachedBluetoothDevice.isConnected();
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                if (isConnected) {
                    SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) sBluetoothTile.mController;
                    if (sBluetoothControllerImpl.mLocalBluetoothManager != null) {
                        sBluetoothControllerImpl.scan(false);
                        cachedBluetoothDevice.disconnect();
                        return;
                    }
                    return;
                }
                if (cachedBluetoothDevice.getMaxConnectionState() == 0) {
                    SBluetoothControllerImpl sBluetoothControllerImpl2 = (SBluetoothControllerImpl) sBluetoothTile.mController;
                    if (sBluetoothControllerImpl2.mLocalBluetoothManager != null) {
                        sBluetoothControllerImpl2.scan(false);
                        cachedBluetoothDevice.connect$1();
                        return;
                    }
                    return;
                }
                return;
            }
            CachedBluetoothCastDevice cachedBluetoothCastDevice = (CachedBluetoothCastDevice) obj;
            if (cachedBluetoothCastDevice.isConnected()) {
                cachedBluetoothCastDevice.disconnect();
                return;
            }
            if (Settings.System.getInt(cachedBluetoothCastDevice.mContext.getContentResolver(), "dexonpc_connection_state", 0) == 3) {
                Log.d(cachedBluetoothCastDevice.TAG, "Dex is enabled, so connect request will be rejected");
                cachedBluetoothCastDevice.toastHandler.post(cachedBluetoothCastDevice.new ToastRunnable(cachedBluetoothCastDevice.mContext.getString(R.string.bluetoothcast_warn_dlg_msg_dex), 0));
                cachedBluetoothCastDevice.dispatchAttributesChanged();
                return;
            }
            Iterator it = cachedBluetoothCastDevice.mCastProfiles.iterator();
            while (it.hasNext()) {
                LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) it.next();
                String str = cachedBluetoothCastDevice.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(cachedBluetoothCastDevice.mCastProfiles.size()));
                sb.append("/");
                sb.append(String.valueOf(localBluetoothCastProfile == null));
                Log.d(str, sb.toString());
                if (localBluetoothCastProfile != null) {
                    SemBluetoothCastDevice semBluetoothCastDevice = cachedBluetoothCastDevice.mCastDevice;
                    AudioCastProfile audioCastProfile = (AudioCastProfile) localBluetoothCastProfile;
                    Log.d(audioCastProfile.TAG, "connect");
                    SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                    if (semBluetoothAudioCast != null) {
                        semBluetoothAudioCast.connect(semBluetoothCastDevice);
                    }
                }
            }
        }

        public final void setItemsVisible(boolean z) {
            if (this.mItems == null) {
                return;
            }
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setItemsVisible = ", z, "SBluetoothTile");
            if (z) {
                return;
            }
            this.mItems.setItems(null);
            this.mAvailableDevicesItems.setItems(null);
            if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                this.mMusicShareItems.setItems(null);
            }
            boolean z2 = SBluetoothTile.DEBUG;
            SBluetoothTile.this.mUiHandler.postAtFrontOfQueue(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.BluetoothDetailAdapter.2
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
            boolean z2 = SBluetoothTile.DEBUG;
            SBluetoothTile sBluetoothTile = SBluetoothTile.this;
            MetricsLogger.action(sBluetoothTile.mContext, 154, z);
            Log.d("SBluetoothTile", "setToggleState state = " + z);
            if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBluetoothTileBlocked() || sBluetoothTile.isBlockedByEASPolicy()) {
                sBluetoothTile.showItPolicyToast();
                return;
            }
            if (((KeyguardStateControllerImpl) sBluetoothTile.mKeyguardStateController).mShowing && sBluetoothTile.mKeyguardUpdateMonitor.isSecure() && !sBluetoothTile.mKeyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && sBluetoothTile.mSettingsHelper.isLockFunctionsEnabled()) {
                sBluetoothTile.mPanelInteractor.forceCollapsePanels();
                sBluetoothTile.mActivityStarter.postQSRunnableDismissingKeyguard(new SBluetoothTile$$ExternalSyntheticLambda0(this, 2));
                sBluetoothTile.fireToggleStateChanged(getToggleState().booleanValue());
                return;
            }
            if (!z) {
                ((SBluetoothControllerImpl) sBluetoothTile.mController).scan(false);
                if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                    ((SBluetoothControllerImpl) sBluetoothTile.mController).scanMusicShareDevices(false, sBluetoothTile.mDetailListening);
                }
            }
            sBluetoothTile.onToggleStateChange(z);
            ((SBluetoothControllerImpl) sBluetoothTile.mController).setBluetoothEnabled(z, false);
            if (sBluetoothTile.isBlockedByEASPolicy()) {
                sBluetoothTile.onToggleStateChange(getToggleState().booleanValue());
            } else {
                this.mAvailableDevicesItems.setEmptyState(z ? R.string.quick_settings_bluetooth_scanning : R.string.quick_settings_bluetooth_detail_off_text);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:139:0x0239  */
        /* JADX WARN: Type inference failed for: r0v129 */
        /* JADX WARN: Type inference failed for: r0v130 */
        /* JADX WARN: Type inference failed for: r0v25 */
        /* JADX WARN: Type inference failed for: r0v36 */
        /* JADX WARN: Type inference failed for: r0v37 */
        /* JADX WARN: Type inference failed for: r0v38 */
        /* JADX WARN: Type inference failed for: r8v10, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r8v16 */
        /* JADX WARN: Type inference failed for: r8v17 */
        /* JADX WARN: Type inference failed for: r8v18 */
        /* JADX WARN: Type inference failed for: r8v27 */
        /* JADX WARN: Type inference failed for: r8v40 */
        /* JADX WARN: Type inference failed for: r8v41 */
        /* JADX WARN: Type inference failed for: r8v42 */
        /* JADX WARN: Type inference failed for: r8v44, types: [java.lang.CharSequence] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void updateItems() {
            ArrayList arrayList;
            Iterator it;
            int i;
            String string;
            Drawable drawable;
            ?? r0;
            String string2;
            BluetoothA2dp bluetoothA2dp;
            BluetoothA2dp bluetoothA2dp2;
            Iterator it2;
            int i2;
            Object string3;
            Object obj;
            ArrayList arrayList2;
            BluetoothDetailAdapter bluetoothDetailAdapter = this;
            if (bluetoothDetailAdapter.mItems == null) {
                return;
            }
            Log.d("SBluetoothTile", "updateItems");
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            LocalBluetoothManager localBluetoothManager = ((SBluetoothControllerImpl) SBluetoothTile.this.mController).mLocalBluetoothManager;
            Drawable drawable2 = null;
            Collection cachedDevicesCopy = localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : null;
            if (cachedDevicesCopy != null) {
                Log.d("SBluetoothTile", "mController.getDevices() list: " + cachedDevicesCopy.size());
                if (cachedDevicesCopy instanceof ArrayList) {
                    Collections.sort((ArrayList) cachedDevicesCopy);
                }
                Iterator it3 = cachedDevicesCopy.iterator();
                while (it3.hasNext()) {
                    CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it3.next();
                    int i3 = cachedBluetoothDevice.mBondState;
                    QSDetailItems.Item item = new QSDetailItems.Item();
                    if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                        Drawable hostOverlayIconDrawable = BluetoothUtils.getHostOverlayIconDrawable(SBluetoothTile.this.mContext, cachedBluetoothDevice);
                        item.overlay = hostOverlayIconDrawable;
                        hostOverlayIconDrawable.setTint(SBluetoothTile.this.mContext.getResources().getColor(R.color.qs_detail_item_device_bt_icon_tint_color));
                    } else {
                        item.overlay = drawable2;
                        item.overlay = SBluetoothTile.this.mContext.getResources().getDrawable(cachedBluetoothDevice.getBtClassDrawable());
                        SBluetoothTile.this.mContext.getResources().getColor(R.color.qs_detail_item_device_bt_icon_tint_color);
                    }
                    boolean z = true;
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
                        Iterator it4 = ((HashSet) cachedBluetoothDevice.mMemberDevices).iterator();
                        while (true) {
                            if (!it4.hasNext()) {
                                z = false;
                                break;
                            } else if (((CachedBluetoothDevice) it4.next()).isConnected()) {
                                break;
                            }
                        }
                    }
                    item.isActive = z;
                    ?? r8 = deviceForGroupConnectionState.mBluetoothCastMsg;
                    if (r8 != null) {
                        arrayList = arrayList3;
                        it = it3;
                        i = maxConnectionState;
                        drawable = drawable2;
                    } else {
                        if (deviceForGroupConnectionState.mDevice == null) {
                            Log.e("CachedBluetoothDevice", "getConnectionSummary :: mDevice is null");
                            obj = drawable2;
                        } else if (deviceForGroupConnectionState.mIsRestored && deviceForGroupConnectionState.mBondState != 11 && TextUtils.isEmpty(deviceForGroupConnectionState.mErrorMsg)) {
                            obj = drawable2;
                            if (!deviceForGroupConnectionState.mIsSynced) {
                                obj = deviceForGroupConnectionState.mIsAddrSwitched ? deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_summary_addr_switched_device) : deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_summary_restored_device);
                            }
                        } else {
                            synchronized (deviceForGroupConnectionState.mProfileLock) {
                                Iterator it5 = deviceForGroupConnectionState.getProfiles().iterator();
                                boolean z2 = false;
                                boolean z3 = false;
                                boolean z4 = false;
                                boolean z5 = false;
                                boolean z6 = false;
                                boolean z7 = false;
                                boolean z8 = false;
                                boolean z9 = false;
                                while (true) {
                                    if (it5.hasNext()) {
                                        it = it3;
                                        LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it5.next();
                                        if (localBluetoothProfile == null) {
                                            it2 = it5;
                                            Log.d("CachedBluetoothDevice", "getConnectionSummary :: profile is null");
                                        } else {
                                            it2 = it5;
                                            if (localBluetoothProfile instanceof CsipSetCoordinatorProfile) {
                                                Log.d("CachedBluetoothDevice", "getConnectionSummary :: csip profile is excluded from the summary");
                                            } else {
                                                int profileConnectionState = deviceForGroupConnectionState.getProfileConnectionState(localBluetoothProfile);
                                                i = maxConnectionState;
                                                StringBuilder sb2 = new StringBuilder();
                                                arrayList = arrayList3;
                                                sb2.append("getConnectionSummary :: profile ::");
                                                sb2.append(localBluetoothProfile);
                                                sb2.append("  connectionStatus::");
                                                sb2.append(profileConnectionState);
                                                Log.d("CachedBluetoothDevice", sb2.toString());
                                                if (profileConnectionState != 0) {
                                                    if (profileConnectionState == 1) {
                                                        Iterator it6 = deviceForGroupConnectionState.getProfiles().iterator();
                                                        boolean z10 = false;
                                                        boolean z11 = false;
                                                        while (it6.hasNext()) {
                                                            LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) it6.next();
                                                            if (localBluetoothProfile2 != null) {
                                                                int profileConnectionState2 = deviceForGroupConnectionState.getProfileConnectionState(localBluetoothProfile2);
                                                                Iterator it7 = it6;
                                                                if (profileConnectionState2 != 1 && profileConnectionState2 != 3) {
                                                                    i2 = 2;
                                                                    if (profileConnectionState2 == i2) {
                                                                        z11 = true;
                                                                    }
                                                                    it6 = it7;
                                                                }
                                                                i2 = 2;
                                                                z10 = true;
                                                                if (profileConnectionState2 == i2) {
                                                                }
                                                                it6 = it7;
                                                            }
                                                        }
                                                        if ((z10 && !z11) || deviceForGroupConnectionState.mBondState == 11) {
                                                            string2 = deviceForGroupConnectionState.mContext.getString(BluetoothUtils.getConnectionStateSummary(profileConnectionState));
                                                            break;
                                                        }
                                                    } else if (profileConnectionState == 2) {
                                                        if (localBluetoothProfile instanceof A2dpProfile) {
                                                            z3 = true;
                                                        }
                                                        if (localBluetoothProfile instanceof HeadsetProfile) {
                                                            z4 = true;
                                                        }
                                                        if (localBluetoothProfile instanceof HidProfile) {
                                                            z6 = true;
                                                        }
                                                        if (localBluetoothProfile instanceof PanProfile) {
                                                            BluetoothDevice bluetoothDevice = deviceForGroupConnectionState.mDevice;
                                                            HashMap hashMap = ((PanProfile) localBluetoothProfile).mDeviceRoleMap;
                                                            if (hashMap.containsKey(bluetoothDevice) && ((Integer) hashMap.get(bluetoothDevice)).intValue() == 1) {
                                                                z7 = true;
                                                            }
                                                        }
                                                        if (localBluetoothProfile instanceof PanProfile) {
                                                            z8 = true;
                                                        }
                                                        if (localBluetoothProfile instanceof SppProfile) {
                                                            z9 = true;
                                                        }
                                                        if (localBluetoothProfile instanceof LeAudioProfile) {
                                                            z5 = true;
                                                        }
                                                        z2 = true;
                                                    } else if (profileConnectionState == 3) {
                                                        string3 = deviceForGroupConnectionState.mContext.getString(BluetoothUtils.getConnectionStateSummary(profileConnectionState));
                                                        break;
                                                    }
                                                } else if (localBluetoothProfile.isProfileReady() && !(localBluetoothProfile instanceof HearingAidProfile) && (localBluetoothProfile instanceof LeAudioProfile)) {
                                                    z5 = false;
                                                }
                                                it5 = it2;
                                                it3 = it;
                                                maxConnectionState = i;
                                                arrayList3 = arrayList;
                                            }
                                        }
                                        arrayList = arrayList3;
                                        i = maxConnectionState;
                                        it5 = it2;
                                        it3 = it;
                                        maxConnectionState = i;
                                        arrayList3 = arrayList;
                                    } else {
                                        arrayList = arrayList3;
                                        it = it3;
                                        i = maxConnectionState;
                                        int batteryLevel = deviceForGroupConnectionState.mDevice.getBatteryLevel();
                                        if (!z2) {
                                            String str2 = deviceForGroupConnectionState.mErrorMsg;
                                            if (str2 == null || str2.isEmpty()) {
                                                r8 = null;
                                                if (deviceForGroupConnectionState.mIsHearingAidDeviceByUUID) {
                                                    string = deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_hearingaid_subtext);
                                                } else {
                                                    int i4 = deviceForGroupConnectionState.mBondState;
                                                    if (i4 != 10) {
                                                        if (i4 == 11) {
                                                            string = deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_pairing);
                                                        }
                                                        drawable = null;
                                                    } else {
                                                        if (deviceForGroupConnectionState.mLocalAdapter != null && deviceForGroupConnectionState.getName().equals(deviceForGroupConnectionState.mDevice.getAddress())) {
                                                            string = deviceForGroupConnectionState.mLocalAdapter.isDiscovering() ? deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_getting_remote_device_name) : deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_display_remote_device_name_after_pair);
                                                        }
                                                        drawable = null;
                                                    }
                                                }
                                                r8 = string;
                                                r0 = null;
                                            } else {
                                                if ((deviceForGroupConnectionState.mErrorMsg.equals(deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_tethering_error_with_wifi_tablet_summary)) || deviceForGroupConnectionState.mErrorMsg.equals(deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_tethering_error_with_wifi_phone_summary))) && ((ConnectivityManager) deviceForGroupConnectionState.mContext.getSystemService("connectivity")).getNetworkInfo(1).getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                                                    r0 = null;
                                                    deviceForGroupConnectionState.mErrorMsg = null;
                                                } else {
                                                    r0 = null;
                                                }
                                                r8 = deviceForGroupConnectionState.mErrorMsg;
                                            }
                                        } else if (z3 && z4) {
                                            LocalBluetoothProfileManager localBluetoothProfileManager = deviceForGroupConnectionState.mProfileManager;
                                            A2dpProfile a2dpProfile = localBluetoothProfileManager == null ? null : localBluetoothProfileManager.mA2dpProfile;
                                            boolean semIsDualPlayMode = (a2dpProfile == null || (bluetoothA2dp2 = a2dpProfile.mService) == null) ? false : bluetoothA2dp2.semIsDualPlayMode();
                                            if (semIsDualPlayMode && batteryLevel != -1) {
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append(deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset_battery, Integer.valueOf(batteryLevel)));
                                                string2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(deviceForGroupConnectionState.mContext, R.string.bluetooth_summary_connected_to_a2dp_headset_dual_audio, sb3);
                                            } else if (batteryLevel != -1) {
                                                string2 = deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset_battery, Integer.valueOf(batteryLevel));
                                            } else if (semIsDualPlayMode) {
                                                StringBuilder sb4 = new StringBuilder();
                                                sb4.append(deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset));
                                                string2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(deviceForGroupConnectionState.mContext, R.string.bluetooth_summary_connected_to_a2dp_headset_dual_audio, sb4);
                                            } else {
                                                string2 = deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset);
                                            }
                                        } else if (z5) {
                                            string2 = deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset);
                                        } else if (z3) {
                                            LocalBluetoothProfileManager localBluetoothProfileManager2 = deviceForGroupConnectionState.mProfileManager;
                                            A2dpProfile a2dpProfile2 = localBluetoothProfileManager2 == null ? null : localBluetoothProfileManager2.mA2dpProfile;
                                            if ((a2dpProfile2 == null || (bluetoothA2dp = a2dpProfile2.mService) == null) ? false : bluetoothA2dp.semIsDualPlayMode()) {
                                                StringBuilder sb5 = new StringBuilder();
                                                sb5.append(deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_a2dp_profile_summary_connected));
                                                string2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(deviceForGroupConnectionState.mContext, R.string.bluetooth_summary_connected_to_a2dp_headset_dual_audio, sb5);
                                            } else {
                                                string2 = deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_a2dp_profile_summary_connected);
                                            }
                                        } else {
                                            string2 = z4 ? batteryLevel != -1 ? deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_headset_profile_summary_connected_battery, Integer.valueOf(batteryLevel)) : deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_headset_profile_summary_connected) : z6 ? deviceForGroupConnectionState.mContext.getString(R.string.sec_bluetooth_connected) : z7 ? deviceForGroupConnectionState.mContext.getString(R.string.sec_bluetooth_connected) : z8 ? deviceForGroupConnectionState.mContext.getString(R.string.sec_bluetooth_connected) : z9 ? deviceForGroupConnectionState.mContext.getString(R.string.sec_bluetooth_connected) : batteryLevel != -1 ? deviceForGroupConnectionState.mContext.getString(R.string.bluetooth_connected_battery, Integer.valueOf(batteryLevel)) : deviceForGroupConnectionState.mContext.getString(R.string.sec_bluetooth_connected);
                                        }
                                    }
                                }
                                r8 = string2;
                            }
                            r0 = null;
                            drawable = r0;
                        }
                        arrayList = arrayList3;
                        it = it3;
                        i = maxConnectionState;
                        string3 = obj;
                        r8 = string3;
                        r0 = null;
                        drawable = r0;
                    }
                    item.line2 = r8;
                    item.tag = cachedBluetoothDevice;
                    if (i3 == 12 || cachedBluetoothDevice.mIsRestored) {
                        arrayList2 = arrayList;
                        arrayList2.add(item);
                    } else {
                        arrayList4.add(item);
                        arrayList2 = arrayList;
                    }
                    int i5 = i;
                    if (i5 == 1 || i5 == 3) {
                        item.isInProgress = true;
                    }
                    arrayList3 = arrayList2;
                    it3 = it;
                    bluetoothDetailAdapter = this;
                    drawable2 = drawable;
                }
            }
            ArrayList arrayList5 = arrayList3;
            this.mItems.setItems((QSDetailItems.Item[]) arrayList5.toArray(new QSDetailItems.Item[arrayList5.size()]));
            this.mItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.BluetoothDetailAdapter.4
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothDetailAdapter bluetoothDetailAdapter2 = BluetoothDetailAdapter.this;
                    bluetoothDetailAdapter2.mPairedDevices.setVisibility(bluetoothDetailAdapter2.mItems.getItemCount() > 0 ? 0 : 8);
                }
            });
            this.mAvailableDevicesItems.setItems((QSDetailItems.Item[]) arrayList4.toArray(new QSDetailItems.Item[arrayList4.size()]));
            this.mAvailableDevicesItems.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.BluetoothDetailAdapter.5
                @Override // java.lang.Runnable
                public final void run() {
                    BluetoothDetailAdapter.this.mAvailable.findViewById(R.id.available_devices_group).setVisibility(BluetoothDetailAdapter.this.mAvailableDevicesItems.getItemCount() > 0 ? 0 : 8);
                }
            });
            SBluetoothTile.this.mPairedItemList.clear();
            SBluetoothTile.this.mPairedItemList.addAll(arrayList5);
            SBluetoothTile.this.mAvailableItemList.clear();
            SBluetoothTile.this.mAvailableItemList.addAll(arrayList4);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SubscreenBluetoothTileReceiver extends BroadcastReceiver {
        public SubscreenBluetoothTileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("BLUETOOTH_STATE_CHANGE")) {
                SBluetoothTile.this.handleClick(null);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.qs.tiles.SBluetoothTile$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.SBluetoothTile$3, java.lang.Object] */
    public SBluetoothTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, SettingsHelper settingsHelper, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, SBluetoothController sBluetoothController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, PanelInteractor panelInteractor, DisplayLifecycle displayLifecycle, BroadcastDispatcher broadcastDispatcher) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mDoStopScan = true;
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        this.mStateBeforeClick = booleanState;
        this.mSubscreenQsPanelController = null;
        this.mPairedItemList = new ArrayList();
        this.mAvailableItemList = new ArrayList();
        ?? r2 = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.1
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                if (QpRune.QUICK_PANEL_SUBSCREEN) {
                    return;
                }
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                if (z) {
                    sBluetoothTile.mSubscreenQsPanelController.getInstance(2).registerReceiver(false);
                } else {
                    sBluetoothTile.mSubscreenQsPanelController.getInstance(2).unRegisterReceiver(false);
                }
            }
        };
        this.mFoldStateChangedListener = r2;
        ?? r3 = new SBluetoothController.SCallback() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.3
            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public final void onBluetoothDevicesChanged() {
                boolean z = SBluetoothTile.DEBUG;
                ((SQSTileImpl) SBluetoothTile.this).mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.3.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (SBluetoothTile.DEBUG) {
                            Log.d("SBluetoothTile", "onBluetoothDevicesChanged ");
                        }
                        SBluetoothTile.this.refreshState(null);
                        SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                        if (sBluetoothTile.mDetailListening) {
                            boolean booleanValue = sBluetoothTile.mDetailAdapter.getToggleState().booleanValue();
                            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onBluetoothDevicesChanged update: ", booleanValue, "SBluetoothTile");
                            if (booleanValue) {
                                SBluetoothTile.this.mDetailAdapter.updateItems();
                                if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                                    BluetoothDetailAdapter.m1630$$Nest$mupdateMusicShareItems(SBluetoothTile.this.mDetailAdapter);
                                }
                            }
                        }
                    }
                });
            }

            @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
            public final void onBluetoothScanStateChanged(boolean z) {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                sBluetoothTile.fireScanStateChanged(z);
                if (sBluetoothTile.mDetailListening && !z) {
                    ((SQSTileImpl) sBluetoothTile).mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.3.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean booleanValue = SBluetoothTile.this.mDetailAdapter.getToggleState().booleanValue();
                            if (SBluetoothTile.DEBUG) {
                                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onBluetoothScanStateChanged update = ", booleanValue, "SBluetoothTile");
                            }
                            if (booleanValue) {
                                SBluetoothTile.this.mDetailAdapter.updateItems();
                            }
                        }
                    });
                }
                if (z) {
                    return;
                }
                SystemUIAnalytics.sendEventLog(sBluetoothTile.mAvailableItemList.size(), SystemUIAnalytics.sCurrentScreenID, "4919");
            }

            @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
            public final void onBluetoothStateChange(boolean z) {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                sBluetoothTile.refreshState(null);
                StringBuilder sb = new StringBuilder("onBluetoothStateChange enabled: ");
                sb.append(z);
                sb.append(" isShowingDetail(): ");
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, sBluetoothTile.mDetailListening, "SBluetoothTile");
                SBluetoothController sBluetoothController2 = sBluetoothTile.mController;
                int i = ((SBluetoothControllerImpl) sBluetoothController2).mState;
                boolean z2 = i == 12;
                if (sBluetoothTile.mDetailListening) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("onBluetoothStateChange isShowingDetail bluetoothState: ", i, "SBluetoothTile");
                    if (i == 12 || i == 10 || i == 13) {
                        if (z2) {
                            ((SBluetoothControllerImpl) sBluetoothController2).setScanMode(23);
                        }
                        ((SBluetoothControllerImpl) sBluetoothController2).scan(z2);
                        sBluetoothTile.mDetailAdapter.setItemsVisible(z2);
                        if (i == 10) {
                            sBluetoothTile.fireScanStateChanged(false);
                        }
                    }
                }
            }

            @Override // com.android.systemui.statusbar.policy.SBluetoothController.SCallback
            public final void onMusicShareDiscoveryStateChanged(boolean z) {
                SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                if (sBluetoothTile.mDetailListening) {
                    if (SBluetoothTile.DEBUG) {
                        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onMusicShareDiscoveryStateChanged() : ", z, "SBluetoothTile");
                    }
                    ((SBluetoothControllerImpl) sBluetoothTile.mController).scanMusicShareDevices(z, sBluetoothTile.mDetailListening);
                }
            }
        };
        this.mCallback = r3;
        this.mController = sBluetoothController;
        this.mActivityStarter = activityStarter;
        this.mSettingsHelper = settingsHelper;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        this.mDetailAdapter = new BluetoothDetailAdapter();
        sBluetoothController.observe(((QSTileImpl) this).mLifecycle, r3);
        booleanState.spec = "bluetooth";
        this.mBlueToothState = ((SBluetoothControllerImpl) sBluetoothController).mState;
        this.isHavingConvertView = false;
        this.mPanelInteractor = panelInteractor;
        if (QpRune.QUICK_SETTINGS_SUBSCREEN) {
            this.mDisplayLifecycle = displayLifecycle;
            this.mSubscreenQsPanelController = (SubscreenQsPanelController) Dependency.get(SubscreenQsPanelController.class);
            displayLifecycle.addObserver(r2);
        } else {
            this.mFoldStateChangedListener = null;
        }
        if (QpRune.QUICK_PANEL_SUBSCREEN) {
            this.mBroadcastDispatcher = broadcastDispatcher;
            if (this.mSubscreenBlueotoothTileReceiver != null || broadcastDispatcher == null) {
                return;
            }
            SubscreenBluetoothTileReceiver subscreenBluetoothTileReceiver = new SubscreenBluetoothTileReceiver();
            this.mSubscreenBlueotoothTileReceiver = subscreenBluetoothTileReceiver;
            broadcastDispatcher.registerReceiver(subscreenBluetoothTileReceiver, new IntentFilter("BLUETOOTH_STATE_CHANGE"), null, UserHandle.ALL, 2, "com.samsung.systemui.permission.BLUETOOTH_STATE_CHANGE");
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBluetoothTileBlocked() || isBlockedByEASPolicy()) {
            showItPolicyToast();
            return null;
        }
        Log.d("SBluetoothTile", " getLongClickIntent is called:++++ ");
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 113;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_bluetooth_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        Log.d("SBluetoothTile", " handleClick is called:++++ ");
        QSTile.BooleanState booleanState = (QSTile.BooleanState) this.mState;
        boolean z = booleanState.value;
        SBluetoothController sBluetoothController = this.mController;
        if (!z && booleanState.state == 2) {
            booleanState.value = ((SBluetoothControllerImpl) sBluetoothController).mEnabled;
        }
        boolean z2 = booleanState.value;
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBluetoothTileBlocked() || isBlockedByEASPolicy()) {
            if (QpRune.QUICK_PANEL_SUBSCREEN) {
                showItPolicyToastOnSubScreen(getSubScreenContext());
                return;
            } else {
                showItPolicyToast();
                return;
            }
        }
        boolean z3 = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
        ActivityStarter activityStarter = this.mActivityStarter;
        if (z3) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
                    activityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            SBluetoothTile.this.handleClick(view);
                        }
                    });
                    return;
                } else {
                    ((SubscreenUtil) Dependency.get(SubscreenUtil.class)).showLockscreenOnCoverScreen(this.mContext, "BLUETOOTH_STATE_CHANGE");
                    return;
                }
            }
        }
        SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) sBluetoothController;
        if (!sBluetoothControllerImpl.canConfigBluetooth()) {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
            return;
        }
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(z2 ? null : SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        sBluetoothControllerImpl.setBluetoothEnabled(!z2, true);
        if (!QpRune.QUICK_PANEL_SUBSCREEN || displayLifecycle == null || displayLifecycle.mIsFolderOpened) {
            return;
        }
        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPBE2017");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        SubscreenBluetoothTileReceiver subscreenBluetoothTileReceiver;
        BroadcastDispatcher broadcastDispatcher;
        super.handleDestroy();
        Log.d("SBluetoothTile", "handleDestroy");
        try {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SBluetoothTile.2
                @Override // java.lang.Runnable
                public final void run() {
                    DisplayLifecycle displayLifecycle;
                    SBluetoothTile sBluetoothTile = SBluetoothTile.this;
                    C22641 c22641 = sBluetoothTile.mFoldStateChangedListener;
                    if (c22641 == null || (displayLifecycle = sBluetoothTile.mDisplayLifecycle) == null) {
                        return;
                    }
                    displayLifecycle.removeObserver(c22641);
                }
            });
        } catch (Exception e) {
            AbstractC0000x2c234b15.m3m("destroy exception:", Log.getStackTraceString(e), "SBluetoothTile");
        }
        if (!QpRune.QUICK_PANEL_SUBSCREEN || (subscreenBluetoothTileReceiver = this.mSubscreenBlueotoothTileReceiver) == null || (broadcastDispatcher = this.mBroadcastDispatcher) == null) {
            return;
        }
        broadcastDispatcher.unregisterReceiver(subscreenBluetoothTileReceiver);
        this.mSubscreenBlueotoothTileReceiver = null;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(View view) {
        handleSecondaryClick(Boolean.FALSE);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0280  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0251  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ec  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdateState(QSTile.State state, Object obj) {
        List list;
        int i;
        String string;
        String lastDeviceName;
        List<SemBluetoothCastDevice> list2;
        String deviceName;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_bluetooth");
        boolean z = obj == SQSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) this.mController;
        int i2 = sBluetoothControllerImpl.mConnectionState;
        boolean z2 = i2 == 2;
        boolean z3 = i2 == 1;
        boolean z4 = sBluetoothControllerImpl.mEnabled;
        int i3 = sBluetoothControllerImpl.mState;
        boolean z5 = booleanState.value != z4;
        boolean z6 = this.mBlueToothState != i3;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m(" handleUpdateState enabled = ", z4, " connected = ", z2, " connecting = ");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m69m, z3, " changedState =", z6, " bluetoothState = ");
        m69m.append(i3);
        m69m.append(" enabledChanging = ");
        m69m.append(z5);
        m69m.append(" state = ");
        m69m.append(booleanState);
        Log.d("SBluetoothTile", m69m.toString());
        if ((z5 || z6) && this.mDetailListening) {
            onToggleStateChange(z4);
        }
        this.mBlueToothState = i3;
        boolean z7 = z || z3 || sBluetoothControllerImpl.mState == 11;
        booleanState.dualTarget = true;
        booleanState.value = z4;
        booleanState.label = getTileLabel();
        boolean z8 = QpRune.QUICK_BLUETOOTH_MUSIC_SHARE;
        LocalBluetoothManager localBluetoothManager = sBluetoothControllerImpl.mLocalBluetoothManager;
        if (z8) {
            try {
                SemBluetoothAudioCast semBluetoothAudioCast = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile.mService;
                list = semBluetoothAudioCast == null ? new ArrayList() : semBluetoothAudioCast.getConnectedDevices();
            } catch (Exception unused) {
                list = null;
            }
            if (list != null) {
                Iterator it = list.iterator();
                i = 0;
                while (it.hasNext()) {
                    if (((SemBluetoothCastDevice) it.next()).getLocalDeviceRole() == 1) {
                        i++;
                    }
                }
                Context context = this.mContext;
                String str = "";
                if (!z7) {
                    booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_connecting);
                    booleanState.contentDescription = context.getString(R.string.accessibility_quick_settings_bluetooth_connecting);
                    if (z || sBluetoothControllerImpl.mState == 11) {
                        booleanState.state = 0;
                    }
                } else if (z4) {
                    if (z2 || (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE && i > 0)) {
                        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_connected);
                        List connectedDevicesForGroup = sBluetoothControllerImpl.getConnectedDevicesForGroup();
                        int size = connectedDevicesForGroup != null ? connectedDevicesForGroup.size() : 0;
                        boolean z9 = QpRune.QUICK_BLUETOOTH_MUSIC_SHARE;
                        if (z9) {
                            if (DEBUG) {
                                SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("connectedDeviceCount = ", size, ", musicShareConnectedCount = ", i, "SBluetoothTile");
                            }
                            int i4 = size - i;
                            int i5 = i + i4;
                            if (i5 > 1) {
                                string = context.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(i5));
                                booleanState.contentDescription = context.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(i5));
                            } else if (i == 1) {
                                if (z9) {
                                    try {
                                        SemBluetoothAudioCast semBluetoothAudioCast2 = localBluetoothManager.mLocalCastProfileManager.mAudioCastProfile.mService;
                                        list2 = semBluetoothAudioCast2 == null ? new ArrayList() : semBluetoothAudioCast2.getConnectedDevices();
                                    } catch (Exception unused2) {
                                        list2 = null;
                                    }
                                    if (list2 != null) {
                                        for (SemBluetoothCastDevice semBluetoothCastDevice : list2) {
                                            if (semBluetoothCastDevice.getLocalDeviceRole() == 1) {
                                                deviceName = semBluetoothCastDevice.getDeviceName();
                                                break;
                                            }
                                        }
                                    } else {
                                        Log.d("SBluetoothTile", "getCastDeviceConnectedList return null : label");
                                    }
                                }
                                deviceName = null;
                                string = deviceName.toString();
                                if (string == null) {
                                    Log.d("SBluetoothTile", "getMusicShareLabel return null.");
                                    string = context.getString(R.string.quick_settings_bluetooth_label);
                                }
                                booleanState.contentDescription = context.getString(R.string.accessibility_bluetooth_name, string);
                            } else if (i4 == 1) {
                                lastDeviceName = sBluetoothControllerImpl.getLastDeviceName();
                                booleanState.contentDescription = context.getString(R.string.accessibility_bluetooth_name, lastDeviceName);
                                string = lastDeviceName;
                            } else {
                                Log.d("SBluetoothTile", "no connected device");
                            }
                            str = string;
                        } else if (size == 1) {
                            lastDeviceName = sBluetoothControllerImpl.getLastDeviceName();
                            booleanState.contentDescription = context.getString(R.string.accessibility_bluetooth_name, lastDeviceName);
                            string = lastDeviceName;
                            str = string;
                        } else if (size >= 2) {
                            string = context.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(size));
                            booleanState.contentDescription = context.getString(R.string.quick_settings_bluetooth_connected_devices, Integer.valueOf(size));
                            str = string;
                        } else {
                            Log.d("SBluetoothTile", "no connected device");
                        }
                    } else {
                        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_on);
                        booleanState.contentDescription = context.getString(R.string.accessibility_quick_settings_bluetooth_on) + "," + context.getString(R.string.accessibility_not_connected);
                    }
                    booleanState.state = 2;
                } else {
                    booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_bluetooth_on);
                    booleanState.contentDescription = context.getString(R.string.accessibility_quick_settings_bluetooth_off);
                    booleanState.state = 1;
                }
                booleanState.secondaryLabel = str;
                booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_bluetooth);
                CharSequence charSequence = booleanState.secondaryLabel;
                if (z2) {
                    charSequence = context.getString(R.string.accessibility_bluetooth_name, charSequence);
                    booleanState.dualLabelContentDescription = charSequence;
                }
                String string2 = context.getString(!booleanState.value ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
                booleanState.contentDescription = context.getString(R.string.quick_settings_bluetooth_label);
                if (z2) {
                    booleanState.contentDescription = ((Object) booleanState.contentDescription) + "," + string2;
                    return;
                }
                booleanState.contentDescription = ((Object) booleanState.contentDescription) + "," + string2 + "," + ((Object) charSequence);
                return;
            }
            Log.d("SBluetoothTile", "getCastDeviceConnectedList return null : count");
        }
        i = 0;
        Context context2 = this.mContext;
        String str2 = "";
        if (!z7) {
        }
        booleanState.secondaryLabel = str2;
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_bluetooth);
        CharSequence charSequence2 = booleanState.secondaryLabel;
        if (z2) {
        }
        String string22 = context2.getString(!booleanState.value ? R.string.accessibility_desc_on : R.string.accessibility_desc_off);
        booleanState.contentDescription = context2.getString(R.string.quick_settings_bluetooth_label);
        if (z2) {
        }
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final boolean isAvailable() {
        if (((SBluetoothControllerImpl) this.mController).mLocalBluetoothManager != null) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isBlockedByEASPolicy() {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getApplicationContext().getSystemService("device_policy");
        return devicePolicyManager != null && devicePolicyManager.semGetAllowBluetoothMode(null) == 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    public final void onToggleStateChange(boolean z) {
        if (((SBluetoothControllerImpl) this.mController).mState != 11) {
            fireToggleStateChanged(z);
        } else {
            ((SQSTileImpl) this).mHandler.postDelayed(new SBluetoothTile$$ExternalSyntheticLambda0(this, 0), 500L);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void setDetailListening(boolean z) {
        if (this.mDetailListening == z) {
            return;
        }
        this.mDetailListening = z;
        LocalBluetoothManager localBluetoothManager = ((SBluetoothControllerImpl) this.mController).mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            synchronized (localBluetoothManager) {
                Log.d("LocalBluetoothManager", "QP setForegroundActivity :: isForeground = " + z);
                Settings.Secure.putIntForUser(localBluetoothManager.mContext.getContentResolver(), "bluetooth_settings_foreground_qp", z ? 1 : 0, -2);
            }
        }
        SBluetoothController sBluetoothController = this.mController;
        if (((SBluetoothControllerImpl) sBluetoothController).mState != 12) {
            fireScanStateChanged(false);
        } else if (this.mDetailListening) {
            SBluetoothControllerImpl sBluetoothControllerImpl = (SBluetoothControllerImpl) sBluetoothController;
            sBluetoothControllerImpl.getClass();
            Log.d("SBluetoothControllerImpl", " updateListDevices ");
            LocalBluetoothManager localBluetoothManager2 = sBluetoothControllerImpl.mLocalBluetoothManager;
            if (localBluetoothManager2 != null) {
                sBluetoothControllerImpl.stopScan();
                localBluetoothManager2.mCachedDeviceManager.clearNonBondedDevices();
                localBluetoothManager2.mEventManager.readRestoredDevices();
            }
            ((SBluetoothControllerImpl) this.mController).setScanMode(23);
            ((SBluetoothControllerImpl) this.mController).scan(true);
            if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                ((SBluetoothControllerImpl) this.mController).scanMusicShareDevices(true, this.mDetailListening);
            }
        } else {
            ((SBluetoothControllerImpl) sBluetoothController).setScanMode(21);
            if (this.mDoStopScan) {
                LocalBluetoothManager localBluetoothManager3 = ((SBluetoothControllerImpl) this.mController).mLocalBluetoothManager;
                if (!(localBluetoothManager3 != null ? localBluetoothManager3.semIsForegroundActivity() : false)) {
                    ((SBluetoothControllerImpl) this.mController).scan(false);
                    if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                        ((SBluetoothControllerImpl) this.mController).scanMusicShareDevices(false, this.mDetailListening);
                    }
                }
            }
        }
        this.mDoStopScan = true;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl
    public final boolean shouldAnnouncementBeDelayed() {
        return this.mStateBeforeClick.value == ((QSTile.BooleanState) this.mState).value;
    }

    public final void handleSecondaryClick(Boolean bool) {
        boolean z = ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
        ActivityStarter activityStarter = this.mActivityStarter;
        if (z) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isSecure() && !keyguardUpdateMonitor.getUserCanSkipBouncer(KeyguardUpdateMonitor.getCurrentUser()) && this.mSettingsHelper.isLockFunctionsEnabled()) {
                activityStarter.postQSRunnableDismissingKeyguard(new SBluetoothTile$$ExternalSyntheticLambda0(this, 1));
                return;
            }
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isBluetoothTileBlocked() || isBlockedByEASPolicy()) {
            showItPolicyToast();
            return;
        }
        if (!((SBluetoothControllerImpl) this.mController).canConfigBluetooth()) {
            activityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
        } else if (bool.booleanValue()) {
            ((CentralSurfacesImpl) ((CentralSurfaces) Dependency.get(CentralSurfaces.class))).openQSPanelWithDetail("Bluetooth");
        } else {
            showDetail(true);
        }
    }
}
