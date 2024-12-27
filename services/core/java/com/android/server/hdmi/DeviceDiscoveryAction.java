package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.Preconditions;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.PendingIntentController$$ExternalSyntheticOutline0;
import com.android.server.hdmi.HdmiCecFeatureAction;
import com.android.server.hdmi.HdmiControlService;
import com.android.server.location.gnss.hal.GnssNative;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceDiscoveryAction extends HdmiCecFeatureAction {
    public final DeviceDiscoveryCallback mCallback;
    public final ArrayList mDevices;
    public final boolean mIsTvDevice;
    public int mProcessedDeviceCount;
    public int mTimeoutRetry;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface DeviceDiscoveryCallback {
        void onDeviceDiscoveryDone(List list);
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceInfo {
        public final int mLogicalAddress;
        public int mPhysicalAddress = GnssNative.GNSS_AIDING_TYPE_ALL;
        public int mPortId = -1;
        public int mVendorId = 16777215;
        public int mPowerStatus = -1;
        public String mDisplayName = "";
        public int mDeviceType = -1;

        public DeviceInfo(int i) {
            this.mLogicalAddress = i;
        }
    }

    public DeviceDiscoveryAction(HdmiCecLocalDevice hdmiCecLocalDevice, DeviceDiscoveryCallback deviceDiscoveryCallback) {
        super(hdmiCecLocalDevice);
        this.mDevices = new ArrayList();
        this.mProcessedDeviceCount = 0;
        this.mTimeoutRetry = 0;
        this.mIsTvDevice = this.mSource.mService.isTvDevice();
        this.mCallback = deviceDiscoveryCallback;
    }

    public final void checkAndProceedStage() {
        if (this.mDevices.isEmpty()) {
            wrapUpAndFinish();
            return;
        }
        if (this.mProcessedDeviceCount != this.mDevices.size()) {
            sendQueryCommand();
            return;
        }
        this.mProcessedDeviceCount = 0;
        int i = this.mState;
        if (i == 2) {
            Slog.v("DeviceDiscoveryAction", "Start [Osd Name Stage]:" + this.mDevices.size());
            this.mProcessedDeviceCount = 0;
            this.mState = 3;
            checkAndProceedStage();
            return;
        }
        if (i == 3) {
            Slog.v("DeviceDiscoveryAction", "Start [Vendor Id Stage]:" + this.mDevices.size());
            this.mProcessedDeviceCount = 0;
            this.mState = 4;
            checkAndProceedStage();
            return;
        }
        if (i != 4) {
            if (i != 6) {
                return;
            }
            wrapUpAndFinish();
        } else {
            Slog.v("DeviceDiscoveryAction", "Start [Power Status Stage]:" + this.mDevices.size());
            this.mProcessedDeviceCount = 0;
            this.mState = 6;
            checkAndProceedStage();
        }
    }

    public final void handleReportPowerStatus(HdmiCecMessage hdmiCecMessage) {
        Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.mSource) {
            StringBuilder sb = new StringBuilder("Unmatched address[expected:");
            sb.append(deviceInfo.mLogicalAddress);
            sb.append(", actual:");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, hdmiCecMessage.mSource, "DeviceDiscoveryAction");
            return;
        }
        if (hdmiCecMessage.mOpcode != 0) {
            deviceInfo.mPowerStatus = hdmiCecMessage.mParams[0] & 255;
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    public final void handleSetOsdName(HdmiCecMessage hdmiCecMessage) {
        Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.mSource) {
            StringBuilder sb = new StringBuilder("Unmatched address[expected:");
            sb.append(deviceInfo.mLogicalAddress);
            sb.append(", actual:");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, hdmiCecMessage.mSource, "DeviceDiscoveryAction");
            return;
        }
        String str = "";
        try {
            if (hdmiCecMessage.mOpcode != 0) {
                str = new String(hdmiCecMessage.mParams, "US-ASCII");
            }
        } catch (UnsupportedEncodingException unused) {
            Slog.w("DeviceDiscoveryAction", "Failed to decode display name: " + hdmiCecMessage.toString());
        }
        deviceInfo.mDisplayName = str;
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == 0 || i2 != i) {
            return;
        }
        if (i2 == 5) {
            startPhysicalAddressStage();
            return;
        }
        int i3 = this.mTimeoutRetry + 1;
        this.mTimeoutRetry = i3;
        if (i3 < 5) {
            sendQueryCommand();
            return;
        }
        this.mTimeoutRetry = 0;
        StringBuilder sb = new StringBuilder("Timeout[State=");
        sb.append(this.mState);
        sb.append(", Processed=");
        GmsAlarmManager$$ExternalSyntheticOutline0.m(sb, this.mProcessedDeviceCount, "DeviceDiscoveryAction");
        int i4 = this.mState;
        if (i4 == 6 || i4 == 3) {
            increaseProcessedDeviceCount();
        } else {
            this.mDevices.remove(this.mProcessedDeviceCount);
        }
        checkAndProceedStage();
    }

    public final void handleVendorId(HdmiCecMessage hdmiCecMessage) {
        Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
        DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
        if (deviceInfo.mLogicalAddress != hdmiCecMessage.mSource) {
            StringBuilder sb = new StringBuilder("Unmatched address[expected:");
            sb.append(deviceInfo.mLogicalAddress);
            sb.append(", actual:");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(sb, hdmiCecMessage.mSource, "DeviceDiscoveryAction");
            return;
        }
        if (hdmiCecMessage.mOpcode != 0) {
            deviceInfo.mVendorId = HdmiUtils.threeBytesToInt(hdmiCecMessage.mParams);
        }
        increaseProcessedDeviceCount();
        checkAndProceedStage();
    }

    public final void increaseProcessedDeviceCount() {
        this.mProcessedDeviceCount++;
        this.mTimeoutRetry = 0;
    }

    public final boolean mayProcessMessageIfCached(int i, int i2) {
        HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
        hdmiCecLocalDevice.assertRunOnServiceThread();
        SparseArray sparseArray = (SparseArray) hdmiCecLocalDevice.mCecMessageCache.mCache.get(i);
        HdmiCecMessage hdmiCecMessage = sparseArray == null ? null : (HdmiCecMessage) sparseArray.get(i2);
        if (hdmiCecMessage == null) {
            return false;
        }
        processCommand(hdmiCecMessage);
        return true;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        int i = this.mState;
        if (i == 2) {
            if (hdmiCecMessage.mOpcode != 132) {
                return false;
            }
            Preconditions.checkState(this.mProcessedDeviceCount < this.mDevices.size());
            DeviceInfo deviceInfo = (DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount);
            int i2 = deviceInfo.mLogicalAddress;
            int i3 = hdmiCecMessage.mSource;
            if (i2 != i3) {
                PendingIntentController$$ExternalSyntheticOutline0.m(i2, i3, "Unmatched address[expected:", ", actual:", "DeviceDiscoveryAction");
            } else {
                byte[] bArr = hdmiCecMessage.mParams;
                int twoBytesToInt = HdmiUtils.twoBytesToInt(bArr);
                deviceInfo.mPhysicalAddress = twoBytesToInt;
                boolean z = this.mIsTvDevice;
                HdmiCecLocalDevice hdmiCecLocalDevice = this.mSource;
                deviceInfo.mPortId = z ? ((HdmiCecLocalDeviceTv) hdmiCecLocalDevice).mService.mHdmiCecNetwork.physicalAddressToPortId(twoBytesToInt) : ((HdmiCecLocalDeviceSource) hdmiCecLocalDevice).mService.mHdmiCecNetwork.physicalAddressToPortId(twoBytesToInt);
                int i4 = bArr[2] & 255;
                deviceInfo.mDeviceType = i4;
                deviceInfo.mDisplayName = "";
                if (z) {
                    hdmiCecLocalDevice.mService.mHdmiCecNetwork.updateCecSwitchInfo(i2, i4, deviceInfo.mPhysicalAddress);
                }
                increaseProcessedDeviceCount();
                checkAndProceedStage();
            }
            return true;
        }
        if (i == 3) {
            int i5 = hdmiCecMessage.mOpcode;
            if (i5 == 71) {
                handleSetOsdName(hdmiCecMessage);
                return true;
            }
            if (i5 != 0 || (hdmiCecMessage.mParams[0] & 255) != 70) {
                return false;
            }
            handleSetOsdName(hdmiCecMessage);
            return true;
        }
        if (i == 4) {
            int i6 = hdmiCecMessage.mOpcode;
            if (i6 == 135) {
                handleVendorId(hdmiCecMessage);
                return true;
            }
            if (i6 != 0 || (hdmiCecMessage.mParams[0] & 255) != 140) {
                return false;
            }
            handleVendorId(hdmiCecMessage);
            return true;
        }
        if (i != 6) {
            return false;
        }
        int i7 = hdmiCecMessage.mOpcode;
        if (i7 == 144) {
            handleReportPowerStatus(hdmiCecMessage);
            return true;
        }
        if (i7 != 0 || (hdmiCecMessage.mParams[0] & 255) != 144) {
            return false;
        }
        handleReportPowerStatus(hdmiCecMessage);
        return true;
    }

    public final void sendQueryCommand() {
        int i = ((DeviceInfo) this.mDevices.get(this.mProcessedDeviceCount)).mLogicalAddress;
        int i2 = this.mState;
        HdmiControlService hdmiControlService = this.mService;
        if (i2 == 2) {
            if (i < 0 || i >= 15) {
                checkAndProceedStage();
                return;
            }
            ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
            if (mayProcessMessageIfCached(i, 132)) {
                return;
            }
            hdmiControlService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), i, 131), null);
            addTimer(this.mState, 2000);
            return;
        }
        if (i2 == 3) {
            if (i < 0 || i >= 15) {
                checkAndProceedStage();
                return;
            }
            ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
            if (mayProcessMessageIfCached(i, 71)) {
                return;
            }
            hdmiControlService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), i, 70), null);
            addTimer(this.mState, 2000);
            return;
        }
        if (i2 == 4) {
            if (i < 0 || i >= 15) {
                checkAndProceedStage();
                return;
            }
            ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
            if (mayProcessMessageIfCached(i, 135)) {
                return;
            }
            hdmiControlService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), i, 140), null);
            addTimer(this.mState, 2000);
            return;
        }
        if (i2 == 5) {
            ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
            addTimer(this.mState, 0);
            return;
        }
        if (i2 != 6) {
            return;
        }
        if (i < 0 || i >= 15) {
            checkAndProceedStage();
            return;
        }
        ((HdmiCecFeatureAction.ActionTimerHandler) this.mActionTimer).clearTimerMessage();
        if (mayProcessMessageIfCached(i, 144)) {
            return;
        }
        hdmiControlService.sendCecCommand(HdmiCecMessage.build(getSourceAddress(), i, 143), null);
        addTimer(this.mState, 2000);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        this.mDevices.clear();
        this.mState = 1;
        pollDevices(new HdmiControlService.DevicePollingCallback() { // from class: com.android.server.hdmi.DeviceDiscoveryAction.1
            @Override // com.android.server.hdmi.HdmiControlService.DevicePollingCallback
            public final void onPollingFinished(List list) {
                boolean isEmpty = list.isEmpty();
                DeviceDiscoveryAction deviceDiscoveryAction = DeviceDiscoveryAction.this;
                if (isEmpty) {
                    Slog.v("DeviceDiscoveryAction", "No device is detected.");
                    deviceDiscoveryAction.wrapUpAndFinish();
                    return;
                }
                if (deviceDiscoveryAction.mState == 0) {
                    Slog.v("DeviceDiscoveryAction", "Action was already finished before the callback was called.");
                    deviceDiscoveryAction.wrapUpAndFinish();
                    return;
                }
                Slog.v("DeviceDiscoveryAction", "Device detected: " + list);
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    deviceDiscoveryAction.mDevices.add(new DeviceInfo(((Integer) it.next()).intValue()));
                }
                deviceDiscoveryAction.startPhysicalAddressStage();
            }
        }, 131073, 0L);
    }

    public final void startPhysicalAddressStage() {
        Slog.v("DeviceDiscoveryAction", "Start [Physical Address Stage]:" + this.mDevices.size());
        this.mProcessedDeviceCount = 0;
        this.mState = 2;
        checkAndProceedStage();
    }

    public final void wrapUpAndFinish() {
        Slog.v("DeviceDiscoveryAction", "---------Wrap up Device Discovery:[" + this.mDevices.size() + "]---------");
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mDevices.iterator();
        while (it.hasNext()) {
            DeviceInfo deviceInfo = (DeviceInfo) it.next();
            deviceInfo.getClass();
            HdmiDeviceInfo build = HdmiDeviceInfo.cecDeviceBuilder().setLogicalAddress(deviceInfo.mLogicalAddress).setPhysicalAddress(deviceInfo.mPhysicalAddress).setPortId(deviceInfo.mPortId).setVendorId(deviceInfo.mVendorId).setDeviceType(deviceInfo.mDeviceType).setDisplayName(deviceInfo.mDisplayName).setDevicePowerStatus(deviceInfo.mPowerStatus).build();
            Slog.v("DeviceDiscoveryAction", " DeviceInfo: " + build);
            arrayList.add(build);
        }
        Slog.v("DeviceDiscoveryAction", "--------------------------------------------");
        this.mCallback.onDeviceDiscoveryDone(arrayList);
        finish(true);
        if (this.mIsTvDevice) {
            HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv = (HdmiCecLocalDeviceTv) this.mSource;
            hdmiCecLocalDeviceTv.assertRunOnServiceThread();
            DelayedMessageBuffer delayedMessageBuffer = hdmiCecLocalDeviceTv.mDelayedMessageBuffer;
            delayedMessageBuffer.getClass();
            ArrayList arrayList2 = new ArrayList(delayedMessageBuffer.mBuffer);
            delayedMessageBuffer.mBuffer.clear();
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                HdmiCecMessage hdmiCecMessage = (HdmiCecMessage) it2.next();
                delayedMessageBuffer.mDevice.onMessage(hdmiCecMessage);
                HdmiLogger.debug("Processing message:" + hdmiCecMessage, new Object[0]);
            }
        }
    }
}
