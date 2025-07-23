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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        boolean isDeviceConnectedToVcp = isDeviceConnectedToVcp(bluetoothDevice);
        HashBiMap hashBiMap = this.mSideToDeviceMap;
        AmbientVolumeUi ambientVolumeUi = this.mAmbientLayout;
        if (isDeviceConnectedToVcp && !((AmbientVolumeLayout) ambientVolumeUi).mMuted) {
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
                        AmbientVolumeUiController.this.loadLocalDataToUi(bluetoothDevice);
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x00b4, code lost:
    
        if (r15 == Integer.MIN_VALUE) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x00b6, code lost:
    
        setVolumeIfValid(0, r14);
        setVolumeIfValid(r1, r15);
        ((com.android.systemui.accessibility.hearingaid.AmbientVolumeLayout) r5).setExpanded(r1);
        r2.forEach(new com.android.settingslib.bluetooth.AmbientVolumeUiController$$ExternalSyntheticLambda8(r17, r1));
        r7.flush();
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x00cd, code lost:
    
        r5 = com.android.settingslib.bluetooth.HearingDeviceLocalDataManager.Data.$r8$clinit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00cf, code lost:
    
        if (r14 == Integer.MIN_VALUE) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00d2, code lost:
    
        r14 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x00d3, code lost:
    
        setVolumeIfValid(999, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x00a0, code lost:
    
        r15 = com.android.settingslib.bluetooth.HearingDeviceLocalDataManager.Data.$r8$clinit;
        r15 = Integer.MIN_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0098, code lost:
    
        r14 = com.android.settingslib.bluetooth.HearingDeviceLocalDataManager.Data.$r8$clinit;
        r14 = Integer.MIN_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x004e, code lost:
    
        r3 = (com.android.systemui.accessibility.hearingaid.AmbientVolumeLayout) r5;
        r3.setVisibility(0);
        r4 = (android.bluetooth.BluetoothDevice) r2.get(0);
        r6 = r8.refreshAmbientState(r4);
        r12 = (android.bluetooth.BluetoothDevice) r2.get(java.lang.Integer.valueOf((int) r1));
        r13 = r8.refreshAmbientState(r12);
        android.util.Log.d("AmbientVolumeUiController", "loadRemoteDataToUi, left=" + r6 + ", right=" + r13);
        r2.forEach(new com.android.settingslib.bluetooth.AmbientVolumeUiController$$ExternalSyntheticLambda4(r17, r1));
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0093, code lost:
    
        if (r6 == null) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x0095, code lost:
    
        r14 = r6.gainSetting;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x009b, code lost:
    
        if (r13 == null) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x009d, code lost:
    
        r15 = r13.gainSetting;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00a5, code lost:
    
        if (r3.mExpanded == false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x00a7, code lost:
    
        setVolumeIfValid(0, r14);
        setVolumeIfValid(r1, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00d8, code lost:
    
        r9 = new com.google.common.collect.HashBiMap.View.AnonymousClass1((com.google.common.collect.HashBiMap.View) r2.values());
        r14 = Integer.MAX_VALUE;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00eb, code lost:
    
        if (r9.hasNext() == false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00ed, code lost:
    
        r15 = (android.bluetooth.BluetoothDevice) r9.next();
        r10 = r7.get(r15);
        r1 = r10.ambient;
        r16 = com.android.settingslib.bluetooth.HearingDeviceLocalDataManager.Data.$r8$clinit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00fb, code lost:
    
        if (r1 == Integer.MIN_VALUE) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00fd, code lost:
    
        r14 = java.lang.Math.min(r1, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0102, code lost:
    
        r1 = r10.groupAmbient;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0104, code lost:
    
        if (r1 == Integer.MIN_VALUE) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0106, code lost:
    
        r7.updateAmbient(r15, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x010c, code lost:
    
        if (r14 == Integer.MAX_VALUE) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x010e, code lost:
    
        r2 = new com.google.common.collect.HashBiMap.View.AnonymousClass1((com.google.common.collect.HashBiMap.View) r2.values());
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x011d, code lost:
    
        if (r2.hasNext() == false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x011f, code lost:
    
        r1 = (android.bluetooth.BluetoothDevice) r2.next();
        r5 = r7.get(r1).groupAmbient;
        r9 = com.android.settingslib.bluetooth.HearingDeviceLocalDataManager.Data.$r8$clinit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x012d, code lost:
    
        if (r5 != Integer.MIN_VALUE) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x012f, code lost:
    
        r7.updateGroupAmbient(r1, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0133, code lost:
    
        r7.flush();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x013a, code lost:
    
        if (isDeviceConnectedToVcp(r4) == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x013c, code lost:
    
        if (r6 == null) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x013e, code lost:
    
        r1 = r6.mute;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0141, code lost:
    
        if (r1 == 2) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0143, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0148, code lost:
    
        if (r1 != 1) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x014a, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0154, code lost:
    
        if (isDeviceConnectedToVcp(r12) == false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0156, code lost:
    
        if (r13 == null) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0158, code lost:
    
        r5 = r13.mute;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x015b, code lost:
    
        if (r5 == 2) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x015d, code lost:
    
        r7 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0160, code lost:
    
        r2 = r2 & r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0162, code lost:
    
        if (r5 != 1) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0164, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0167, code lost:
    
        r1 = r1 & r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0166, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x015f, code lost:
    
        r7 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0168, code lost:
    
        r3.mMutable = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x016a, code lost:
    
        if (r2 != false) goto L82;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x016c, code lost:
    
        r3.mVolumeLevel = 24;
        r5 = false;
        r3.setMuted(false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0176, code lost:
    
        r3.updateVolumeIcon();
        r3.setMuted(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0180, code lost:
    
        if (isDeviceConnectedToVcp(r4) == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0182, code lost:
    
        if (r6 == null) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0184, code lost:
    
        r2 = r6.mute;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0187, code lost:
    
        if (r2 == 2) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x018a, code lost:
    
        if (r2 != 1) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x018c, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x018f, code lost:
    
        if (r2 == r1) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0191, code lost:
    
        r8.setMuted(r4, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x018e, code lost:
    
        r2 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0198, code lost:
    
        if (isDeviceConnectedToVcp(r12) == false) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x019a, code lost:
    
        if (r13 == null) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x019c, code lost:
    
        r2 = r13.mute;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x019f, code lost:
    
        if (r2 == 2) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01a2, code lost:
    
        if (r2 != 1) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01a4, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x01a5, code lost:
    
        if (r5 == r1) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x01a7, code lost:
    
        r8.setMuted(r12, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01aa, code lost:
    
        updateSliderUi();
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01ad, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0175, code lost:
    
        r5 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x014c, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0146, code lost:
    
        r2 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x014e, code lost:
    
        r1 = true;
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00ae, code lost:
    
        if (r14 == r15) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00b0, code lost:
    
        r9 = com.android.settingslib.bluetooth.HearingDeviceLocalDataManager.Data.$r8$clinit;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00b2, code lost:
    
        if (r14 == Integer.MIN_VALUE) goto L33;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0 */
    /* JADX WARN: Type inference failed for: r1v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void refresh() {
        /*
            Method dump skipped, instructions count: 445
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.AmbientVolumeUiController.refresh():void");
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

    public final void updateSliderUi() {
        boolean z;
        HashBiMap.View.AnonymousClass1 anonymousClass1 = ((HashBiMap.View) this.mSideToDeviceMap.entrySet()).new AnonymousClass1();
        boolean z2 = false;
        while (true) {
            boolean hasNext = anonymousClass1.hasNext();
            AmbientVolumeUi ambientVolumeUi = this.mAmbientLayout;
            if (!hasNext) {
                AmbientVolumeLayout ambientVolumeLayout = (AmbientVolumeLayout) ambientVolumeUi;
                ambientVolumeLayout.setSliderEnabled(999, z2);
                ambientVolumeLayout.updateLayout();
                return;
            }
            Map.Entry entry = (Map.Entry) anonymousClass1.next();
            int intValue = ((Integer) entry.getKey()).intValue();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) entry.getValue();
            if (isDeviceConnectedToVcp(bluetoothDevice)) {
                AmbientVolumeController ambientVolumeController = this.mVolumeController;
                boolean isEmpty = ambientVolumeController.getAmbientControls(bluetoothDevice).isEmpty();
                z = true;
                boolean z3 = ambientVolumeController.mVolumeControlProfile.getConnectionStatus(bluetoothDevice) == 2;
                if (!isEmpty && z3) {
                    z2 |= z;
                    ((AmbientVolumeLayout) ambientVolumeUi).setSliderEnabled(intValue, z);
                }
            }
            z = false;
            z2 |= z;
            ((AmbientVolumeLayout) ambientVolumeUi).setSliderEnabled(intValue, z);
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
