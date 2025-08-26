package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.ArraySet;
import android.util.Log;
import android.widget.LinearLayout;
import com.android.settingslib.bluetooth.AmbientVolumeController;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingDeviceLocalDataManager;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.accessibility.hearingaid.AmbientVolumeLayout;
import com.android.systemui.accessibility.hearingaid.AmbientVolumeLayout$$ExternalSyntheticLambda3;
import com.android.systemui.accessibility.hearingaid.AmbientVolumeSlider;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashBiMap.View.AnonymousClass1;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class AmbientVolumeUiController implements AmbientVolumeController.AmbientVolumeControlCallback, BluetoothCallback, CachedBluetoothDevice.Callback {
    public final AmbientVolumeUi mAmbientLayout;
    public CachedBluetoothDevice mCachedDevice;
    public final Context mContext;
    public final BluetoothEventManager mEventManager;
    public final HearingDeviceLocalDataManager mLocalDataManager;
    public final LocalBluetoothProfileManager mProfileManager;
    public final AmbientVolumeController mVolumeController;
    public final Set mCachedDevices = new ArraySet();
    public final HashBiMap mSideToDeviceMap = HashBiMap.create();
    public boolean mShowUiWhenLocalDataExist = true;

    public AmbientVolumeUiController(Context context, LocalBluetoothManager localBluetoothManager, AmbientVolumeUi ambientVolumeUi) {
        this.mContext = context;
        LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
        this.mProfileManager = localBluetoothProfileManager;
        this.mEventManager = localBluetoothManager.mEventManager;
        this.mAmbientLayout = ambientVolumeUi;
        ((AmbientVolumeLayout) ambientVolumeUi).mListener = this;
        this.mVolumeController = new AmbientVolumeController(localBluetoothProfileManager, this);
        HearingDeviceLocalDataManager hearingDeviceLocalDataManager = new HearingDeviceLocalDataManager(context);
        this.mLocalDataManager = hearingDeviceLocalDataManager;
        ListeningExecutorService backgroundExecutor = ThreadUtils.getBackgroundExecutor();
        hearingDeviceLocalDataManager.mListener = this;
        hearingDeviceLocalDataManager.mListenerExecutor = backgroundExecutor;
        hearingDeviceLocalDataManager.start();
    }

    public final boolean isDeviceConnectedToVcp(BluetoothDevice bluetoothDevice) {
        return bluetoothDevice != null && bluetoothDevice.isConnected() && this.mProfileManager.mVolumeControlProfile.getConnectionStatus(bluetoothDevice) == 2;
    }

    public final void loadDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        Log.d("AmbientVolumeUiController", "loadDevice, device=" + cachedBluetoothDevice);
        this.mCachedDevice = cachedBluetoothDevice;
        HashBiMap hashBiMap = this.mSideToDeviceMap;
        hashBiMap.clear();
        ((ArraySet) this.mCachedDevices).clear();
        AmbientVolumeUi ambientVolumeUi = this.mAmbientLayout;
        if (cachedBluetoothDevice == null || !cachedBluetoothDevice.getProfiles().stream().anyMatch(new AmbientVolumeUiController$$ExternalSyntheticLambda10())) {
            AmbientVolumeLayout ambientVolumeLayout = (AmbientVolumeLayout) ambientVolumeUi;
            ambientVolumeLayout.getClass();
            ambientVolumeLayout.setVisibility(8);
            return;
        }
        if (AmbientVolumeUi.VALID_SIDES.contains(Integer.valueOf(cachedBluetoothDevice.getDeviceSide())) && cachedBluetoothDevice.mBondState == 12) {
            hashBiMap.put(Integer.valueOf(cachedBluetoothDevice.getDeviceSide()), cachedBluetoothDevice.mDevice);
            ((ArraySet) this.mCachedDevices).add(cachedBluetoothDevice);
        }
        Iterator it = ((HashSet) cachedBluetoothDevice.mMemberDevices).iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
            if (AmbientVolumeUi.VALID_SIDES.contains(Integer.valueOf(cachedBluetoothDevice2.getDeviceSide())) && cachedBluetoothDevice2.mBondState == 12) {
                hashBiMap.put(Integer.valueOf(cachedBluetoothDevice2.getDeviceSide()), cachedBluetoothDevice2.mDevice);
                ((ArraySet) this.mCachedDevices).add(cachedBluetoothDevice2);
            }
        }
        boolean z = hashBiMap.size > 1;
        AmbientVolumeLayout ambientVolumeLayout2 = (AmbientVolumeLayout) ambientVolumeUi;
        ambientVolumeLayout2.mExpandable = z;
        if (!z) {
            ambientVolumeLayout2.setExpanded(false);
        }
        ambientVolumeLayout2.updateExpandIcon();
        ambientVolumeLayout2.getClass();
        hashBiMap.forEach(new AmbientVolumeLayout$$ExternalSyntheticLambda3(ambientVolumeLayout2, 1));
        ambientVolumeLayout2.createSlider(999);
        LinearLayout linearLayout = (LinearLayout) ambientVolumeLayout2.requireViewById(R.id.ambient_control_container);
        linearLayout.removeAllViews();
        if (!ambientVolumeLayout2.mSideToSliderMap.isEmpty()) {
            for (Integer num : AmbientVolumeUi.VALID_SIDES) {
                num.getClass();
                AmbientVolumeSlider ambientVolumeSlider = (AmbientVolumeSlider) ambientVolumeLayout2.mSideToSliderMap.get(num);
                if (ambientVolumeSlider != null) {
                    linearLayout.addView(ambientVolumeSlider);
                }
            }
        }
        ambientVolumeLayout2.updateLayout();
        refresh();
    }

    public final void loadLocalDataToUi(BluetoothDevice bluetoothDevice) {
        HearingDeviceLocalDataManager hearingDeviceLocalDataManager = this.mLocalDataManager;
        HearingDeviceLocalDataManager.Data data = hearingDeviceLocalDataManager.get(bluetoothDevice);
        Log.d("AmbientVolumeUiController", "loadLocalDataToUi, data=" + data + ", device=" + bluetoothDevice);
        boolean zIsDeviceConnectedToVcp = isDeviceConnectedToVcp(bluetoothDevice);
        HashBiMap hashBiMap = this.mSideToDeviceMap;
        AmbientVolumeUi ambientVolumeUi = this.mAmbientLayout;
        if (zIsDeviceConnectedToVcp && !((AmbientVolumeLayout) ambientVolumeUi).mMuted) {
            setVolumeIfValid(((Integer) hashBiMap.inverse().getOrDefault(bluetoothDevice, -1)).intValue(), data.ambient);
            setVolumeIfValid(999, data.groupAmbient);
        }
        boolean z = data.ambientControlExpanded;
        ((AmbientVolumeLayout) ambientVolumeUi).setExpanded(z);
        hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda8(this, z));
        hearingDeviceLocalDataManager.flush();
        updateSliderUi();
    }

    public final void onAmbientChanged(BluetoothDevice bluetoothDevice, int i) {
        Log.d("AmbientVolumeUiController", "onAmbientChanged, value:" + i + ", device:" + bluetoothDevice);
        HearingDeviceLocalDataManager.Data data = this.mLocalDataManager.get(bluetoothDevice);
        boolean z = ((AmbientVolumeLayout) this.mAmbientLayout).mExpanded;
        if (z && data.ambient == i) {
            return;
        }
        if (z || data.groupAmbient != i) {
            this.mContext.getMainThreadHandler().postDelayed(new AmbientVolumeUiController$$ExternalSyntheticLambda3(this, 2), 1200L);
        }
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        ((ArraySet) this.mCachedDevices).forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda0(this, 2));
        this.mContext.getMainThreadHandler().post(new AmbientVolumeUiController$$ExternalSyntheticLambda3(this, 0));
    }

    public final void onDeviceLocalDataChange(String str, HearingDeviceLocalDataManager.Data data) {
        if (data == null) {
            return;
        }
        Log.d("AmbientVolumeUiController", "onDeviceLocalDataChange, address:" + str + ", data:" + data);
        HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) this.mSideToDeviceMap.values()).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            final BluetoothDevice bluetoothDevice = (BluetoothDevice) anonymousClass1.next();
            if (bluetoothDevice.getAnonymizedAddress().equals(str)) {
                this.mContext.getMainThreadHandler().post(new Runnable() { // from class: com.android.settingslib.bluetooth.AmbientVolumeUiController$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.loadLocalDataToUi(bluetoothDevice);
                    }
                });
                return;
            }
        }
    }

    public final void onMuteChanged(BluetoothDevice bluetoothDevice, int i) {
        Log.d("AmbientVolumeUiController", "onMuteChanged, mute:" + i + ", device:" + bluetoothDevice);
        boolean z = ((AmbientVolumeLayout) this.mAmbientLayout).mMuted;
        if (z && i == 1) {
            return;
        }
        if (z || i != 0) {
            this.mContext.getMainThreadHandler().postDelayed(new AmbientVolumeUiController$$ExternalSyntheticLambda3(this, 2), 1200L);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        if (i2 == 23 && i == 2 && ((ArraySet) this.mCachedDevices).contains(cachedBluetoothDevice)) {
            this.mContext.getMainThreadHandler().postDelayed(new AmbientVolumeUiController$$ExternalSyntheticLambda3(this, 2), 1000L);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cd  */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void refresh() {
        AmbientVolumeUi ambientVolumeUi;
        HearingDeviceLocalDataManager hearingDeviceLocalDataManager;
        AmbientVolumeController ambientVolumeController;
        int i;
        int i2;
        boolean z;
        boolean z2;
        boolean z3;
        int i3;
        int i4;
        boolean z4 = 1;
        HashBiMap hashBiMap = this.mSideToDeviceMap;
        HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) hashBiMap.values()).new AnonymousClass1();
        while (true) {
            boolean zHasNext = anonymousClass1.hasNext();
            ambientVolumeUi = this.mAmbientLayout;
            if (!zHasNext) {
                AmbientVolumeLayout ambientVolumeLayout = (AmbientVolumeLayout) ambientVolumeUi;
                ambientVolumeLayout.getClass();
                ambientVolumeLayout.setVisibility(8);
                return;
            }
            BluetoothDevice bluetoothDevice = (BluetoothDevice) anonymousClass1.next();
            boolean z5 = this.mShowUiWhenLocalDataExist;
            hearingDeviceLocalDataManager = this.mLocalDataManager;
            ambientVolumeController = this.mVolumeController;
            if (z5) {
                HearingDeviceLocalDataManager.Data data = hearingDeviceLocalDataManager.get(bluetoothDevice);
                if (data.ambient != Integer.MIN_VALUE || data.groupAmbient != Integer.MIN_VALUE) {
                    break;
                }
                boolean zIsEmpty = ambientVolumeController.getAmbientControls(bluetoothDevice).isEmpty();
                boolean z6 = ambientVolumeController.mVolumeControlProfile.getConnectionStatus(bluetoothDevice) == 2 ? z4 : false;
                if (!zIsEmpty && z6) {
                    break;
                } else {
                    z4 = z4;
                }
            }
        }
        AmbientVolumeLayout ambientVolumeLayout2 = (AmbientVolumeLayout) ambientVolumeUi;
        ambientVolumeLayout2.setVisibility(0);
        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) hashBiMap.get(0);
        AmbientVolumeController.RemoteAmbientState remoteAmbientStateRefreshAmbientState = ambientVolumeController.refreshAmbientState(bluetoothDevice2);
        BluetoothDevice bluetoothDevice3 = (BluetoothDevice) hashBiMap.get(Integer.valueOf((int) z4));
        AmbientVolumeController.RemoteAmbientState remoteAmbientStateRefreshAmbientState2 = ambientVolumeController.refreshAmbientState(bluetoothDevice3);
        Log.d("AmbientVolumeUiController", "loadRemoteDataToUi, left=" + remoteAmbientStateRefreshAmbientState + ", right=" + remoteAmbientStateRefreshAmbientState2);
        hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda4(this, z4));
        if (remoteAmbientStateRefreshAmbientState != null) {
            i = remoteAmbientStateRefreshAmbientState.gainSetting;
        } else {
            int i5 = HearingDeviceLocalDataManager.Data.$r8$clinit;
            i = Integer.MIN_VALUE;
        }
        if (remoteAmbientStateRefreshAmbientState2 != null) {
            i2 = remoteAmbientStateRefreshAmbientState2.gainSetting;
        } else {
            int i6 = HearingDeviceLocalDataManager.Data.$r8$clinit;
            i2 = Integer.MIN_VALUE;
        }
        if (ambientVolumeLayout2.mExpanded) {
            setVolumeIfValid(0, i);
            setVolumeIfValid(z4, i2);
        } else if (i != i2) {
            int i7 = HearingDeviceLocalDataManager.Data.$r8$clinit;
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                int i8 = HearingDeviceLocalDataManager.Data.$r8$clinit;
                if (i == Integer.MIN_VALUE) {
                    i = i2;
                }
                setVolumeIfValid(999, i);
            } else {
                setVolumeIfValid(0, i);
                setVolumeIfValid(z4, i2);
                ((AmbientVolumeLayout) ambientVolumeUi).setExpanded(z4);
                hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda8(this, z4));
                hearingDeviceLocalDataManager.flush();
            }
        }
        HashBiMap.View.AnonymousClass1 anonymousClass12 = ((HashBiMap.View) hashBiMap.values()).new AnonymousClass1();
        int iMin = Integer.MAX_VALUE;
        while (anonymousClass12.hasNext()) {
            BluetoothDevice bluetoothDevice4 = (BluetoothDevice) anonymousClass12.next();
            HearingDeviceLocalDataManager.Data data2 = hearingDeviceLocalDataManager.get(bluetoothDevice4);
            int i9 = data2.ambient;
            int i10 = HearingDeviceLocalDataManager.Data.$r8$clinit;
            if (i9 != Integer.MIN_VALUE) {
                iMin = Math.min(i9, iMin);
            } else {
                int i11 = data2.groupAmbient;
                if (i11 != Integer.MIN_VALUE) {
                    hearingDeviceLocalDataManager.updateAmbient(bluetoothDevice4, i11);
                }
            }
        }
        if (iMin != Integer.MAX_VALUE) {
            HashBiMap.View.AnonymousClass1 anonymousClass13 = ((HashBiMap.View) hashBiMap.values()).new AnonymousClass1();
            while (anonymousClass13.hasNext()) {
                BluetoothDevice bluetoothDevice5 = (BluetoothDevice) anonymousClass13.next();
                int i12 = hearingDeviceLocalDataManager.get(bluetoothDevice5).groupAmbient;
                int i13 = HearingDeviceLocalDataManager.Data.$r8$clinit;
                if (i12 == Integer.MIN_VALUE) {
                    hearingDeviceLocalDataManager.updateGroupAmbient(bluetoothDevice5, iMin);
                }
            }
        }
        hearingDeviceLocalDataManager.flush();
        if (!isDeviceConnectedToVcp(bluetoothDevice2) || remoteAmbientStateRefreshAmbientState == null) {
            z = true;
            z2 = true;
        } else {
            int i14 = remoteAmbientStateRefreshAmbientState.mute;
            z2 = i14 != 2;
            z = i14 == 1;
        }
        if (isDeviceConnectedToVcp(bluetoothDevice3) && remoteAmbientStateRefreshAmbientState2 != null) {
            int i15 = remoteAmbientStateRefreshAmbientState2.mute;
            z2 &= i15 != 2;
            z &= i15 == 1;
        }
        ambientVolumeLayout2.mMutable = z2;
        if (z2) {
            z3 = false;
        } else {
            ambientVolumeLayout2.mVolumeLevel = 24;
            z3 = false;
            ambientVolumeLayout2.setMuted(false);
        }
        ambientVolumeLayout2.updateVolumeIcon();
        ambientVolumeLayout2.setMuted(z);
        if (isDeviceConnectedToVcp(bluetoothDevice2) && remoteAmbientStateRefreshAmbientState != null && (i4 = remoteAmbientStateRefreshAmbientState.mute) != 2) {
            if ((i4 == 1 ? true : z3) != z) {
                ambientVolumeController.setMuted(bluetoothDevice2, z);
            }
        }
        if (isDeviceConnectedToVcp(bluetoothDevice3) && remoteAmbientStateRefreshAmbientState2 != null && (i3 = remoteAmbientStateRefreshAmbientState2.mute) != 2) {
            if (i3 == 1) {
                z3 = true;
            }
            if (z3 != z) {
                ambientVolumeController.setMuted(bluetoothDevice3, z);
            }
        }
        updateSliderUi();
    }

    public final void setVolumeIfValid(int i, int i2) {
        int i3 = HearingDeviceLocalDataManager.Data.$r8$clinit;
        if (i2 == Integer.MIN_VALUE) {
            return;
        }
        AmbientVolumeLayout ambientVolumeLayout = (AmbientVolumeLayout) this.mAmbientLayout;
        AmbientVolumeSlider ambientVolumeSlider = (AmbientVolumeSlider) ambientVolumeLayout.mSideToSliderMap.get(Integer.valueOf(i));
        if (ambientVolumeSlider != null) {
            float f = i2;
            if (ambientVolumeSlider.mSlider.getValue() != f) {
                ambientVolumeSlider.mSlider.setValues(Float.valueOf(f));
                ambientVolumeLayout.updateVolumeLevel();
            }
        }
        HearingDeviceLocalDataManager hearingDeviceLocalDataManager = this.mLocalDataManager;
        HashBiMap hashBiMap = this.mSideToDeviceMap;
        if (i == 999) {
            hashBiMap.forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda14(this, i2, 0));
        } else {
            hearingDeviceLocalDataManager.updateAmbient((BluetoothDevice) hashBiMap.get(Integer.valueOf(i)), i2);
        }
        hearingDeviceLocalDataManager.flush();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSliderUi() {
        boolean z;
        HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) this.mSideToDeviceMap.entrySet()).new AnonymousClass1();
        boolean z2 = false;
        while (true) {
            boolean zHasNext = anonymousClass1.hasNext();
            AmbientVolumeUi ambientVolumeUi = this.mAmbientLayout;
            if (!zHasNext) {
                AmbientVolumeLayout ambientVolumeLayout = (AmbientVolumeLayout) ambientVolumeUi;
                ambientVolumeLayout.setSliderEnabled(999, z2);
                ambientVolumeLayout.updateLayout();
                return;
            }
            Map.Entry entry = (Map.Entry) anonymousClass1.next();
            int iIntValue = ((Integer) entry.getKey()).intValue();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) entry.getValue();
            if (isDeviceConnectedToVcp(bluetoothDevice)) {
                AmbientVolumeController ambientVolumeController = this.mVolumeController;
                boolean zIsEmpty = ambientVolumeController.getAmbientControls(bluetoothDevice).isEmpty();
                z = true;
                boolean z3 = ambientVolumeController.mVolumeControlProfile.getConnectionStatus(bluetoothDevice) == 2;
                if (zIsEmpty || !z3) {
                    z = false;
                }
            }
            z2 |= z;
            ((AmbientVolumeLayout) ambientVolumeUi).setSliderEnabled(iIntValue, z);
        }
    }

    public AmbientVolumeUiController(Context context, LocalBluetoothManager localBluetoothManager, AmbientVolumeUi ambientVolumeUi, AmbientVolumeController ambientVolumeController, HearingDeviceLocalDataManager hearingDeviceLocalDataManager) {
        this.mContext = context;
        this.mProfileManager = localBluetoothManager.mProfileManager;
        this.mEventManager = localBluetoothManager.mEventManager;
        this.mAmbientLayout = ambientVolumeUi;
        this.mVolumeController = ambientVolumeController;
        this.mLocalDataManager = hearingDeviceLocalDataManager;
    }
}
