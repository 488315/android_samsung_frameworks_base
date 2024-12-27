package com.android.server.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.util.Slog;

import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HotplugDetectionAction extends HdmiCecFeatureAction {
    public int mAvrStatusCount;
    public final boolean mIsTvDevice;
    public int mTimeoutCount;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.hdmi.HotplugDetectionAction$1, reason: invalid class name */
    public final class AnonymousClass1 implements HdmiControlService.DevicePollingCallback {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HotplugDetectionAction this$0;

        public /* synthetic */ AnonymousClass1(
                HotplugDetectionAction hotplugDetectionAction, int i) {
            this.$r8$classId = i;
            this.this$0 = hotplugDetectionAction;
        }

        @Override // com.android.server.hdmi.HdmiControlService.DevicePollingCallback
        public final void onPollingFinished(List list) {
            switch (this.$r8$classId) {
                case 0:
                    HotplugDetectionAction.m578$$Nest$mcheckHotplug(this.this$0, list, false);
                    Slog.v("HotPlugDetectionAction", "Finish poll all devices.");
                    break;
                default:
                    HotplugDetectionAction.m578$$Nest$mcheckHotplug(this.this$0, list, true);
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mcheckHotplug, reason: not valid java name */
    public static void m578$$Nest$mcheckHotplug(
            HotplugDetectionAction hotplugDetectionAction, List list, boolean z) {
        HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv;
        HdmiDeviceInfo avrDeviceInfo;
        HdmiCecLocalDevice hdmiCecLocalDevice = hotplugDetectionAction.mSource;
        List deviceInfoList = hdmiCecLocalDevice.mService.mHdmiCecNetwork.getDeviceInfoList();
        BitSet infoListToBitSet = infoListToBitSet(deviceInfoList, z, false);
        BitSet bitSet = new BitSet(15);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            bitSet.set(((Integer) it.next()).intValue());
        }
        BitSet bitSet2 = (BitSet) infoListToBitSet.clone();
        bitSet2.andNot(bitSet);
        int i = -1;
        while (true) {
            i = bitSet2.nextSetBit(i + 1);
            if (i == -1) {
                break;
            }
            boolean z2 = hotplugDetectionAction.mIsTvDevice;
            if (z2
                    && i == 5
                    && (avrDeviceInfo =
                                    (hdmiCecLocalDeviceTv =
                                                    (HdmiCecLocalDeviceTv) hdmiCecLocalDevice)
                                            .getAvrDeviceInfo())
                            != null) {
                int portId = avrDeviceInfo.getPortId();
                hdmiCecLocalDeviceTv.assertRunOnServiceThread();
                HdmiControlService hdmiControlService = hdmiCecLocalDeviceTv.mService;
                hdmiControlService.assertRunOnServiceThread();
                HdmiCecController hdmiCecController = hdmiControlService.mCecController;
                hdmiCecController.assertRunOnServiceThread();
                if (hdmiCecController.mNativeWrapperImpl.nativeIsConnected(portId)) {
                    hotplugDetectionAction.mAvrStatusCount++;
                    HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Ack not returned from AVR. count: "),
                            hotplugDetectionAction.mAvrStatusCount,
                            "HotPlugDetectionAction");
                    if (hotplugDetectionAction.mAvrStatusCount < 3) {}
                }
            }
            ProxyManager$$ExternalSyntheticOutline0.m(
                    i, "Remove device by hot-plug detection:", "HotPlugDetectionAction");
            HdmiCecLocalDevice hdmiCecLocalDevice2 = hotplugDetectionAction.mSource;
            HdmiControlService hdmiControlService2 = hdmiCecLocalDevice.mService;
            if (z2) {
                HdmiDeviceInfo cecDeviceInfo =
                        hdmiControlService2.mHdmiCecNetwork.getCecDeviceInfo(i);
                if (cecDeviceInfo != null) {
                    HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv2 =
                            (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
                    int physicalAddress = cecDeviceInfo.getPhysicalAddress();
                    hdmiCecLocalDeviceTv2.assertRunOnServiceThread();
                    if (HdmiCecLocalDeviceTv.isTailOfActivePath(
                            physicalAddress, hdmiCecLocalDeviceTv2.getActivePath())) {
                        hdmiCecLocalDeviceTv2.startRoutingControl(
                                hdmiCecLocalDeviceTv2.getActivePath(),
                                hdmiCecLocalDeviceTv2.mService.portIdToPath(
                                        hdmiCecLocalDeviceTv2.getActivePortId()),
                                null);
                    }
                }
                for (OneTouchRecordAction oneTouchRecordAction :
                        hdmiCecLocalDevice.getActions(OneTouchRecordAction.class)) {
                    if (oneTouchRecordAction.mRecorderAddress == i) {
                        hdmiCecLocalDevice2.assertRunOnServiceThread();
                        oneTouchRecordAction.finish(false);
                        hdmiCecLocalDevice2.mActions.remove(oneTouchRecordAction);
                        hdmiCecLocalDevice2.checkIfPendingActionsCleared();
                    }
                }
                if (HdmiUtils.isEligibleAddressForDevice(5, i)) {
                    HdmiCecLocalDeviceTv hdmiCecLocalDeviceTv3 =
                            (HdmiCecLocalDeviceTv) hdmiCecLocalDevice;
                    hdmiCecLocalDeviceTv3.setSystemAudioMode$1(false);
                    if (hdmiCecLocalDeviceTv3.isArcEstablished()) {
                        hdmiCecLocalDeviceTv3.enableAudioReturnChannel$1(false);
                        hdmiCecLocalDevice2.addAndStartAction(
                                new RequestArcTerminationAction(hdmiCecLocalDevice, i, null));
                    }
                }
            }
            Iterator it2 = hdmiCecLocalDevice.getActions(DeviceSelectActionFromTv.class).iterator();
            while (it2.hasNext()) {
                if (((DeviceSelectActionFromTv) it2.next()).mTarget.getLogicalAddress() == i) {
                    hdmiCecLocalDevice2.removeActionExcept(DeviceSelectActionFromTv.class, null);
                }
            }
            Iterator it3 =
                    hdmiCecLocalDevice.getActions(DeviceSelectActionFromPlayback.class).iterator();
            while (it3.hasNext()) {
                if (((DeviceSelectActionFromPlayback) it3.next()).mTarget.getLogicalAddress()
                        == i) {
                    hdmiCecLocalDevice2.removeActionExcept(DeviceSelectActionFromTv.class, null);
                }
            }
            hdmiControlService2.mHdmiCecNetwork.removeCecDevice(hdmiCecLocalDevice, i);
        }
        if (!bitSet2.get(5)) {
            hotplugDetectionAction.mAvrStatusCount = 0;
        }
        BitSet infoListToBitSet2 = infoListToBitSet(deviceInfoList, z, true);
        BitSet bitSet3 = (BitSet) bitSet.clone();
        bitSet3.andNot(infoListToBitSet2);
        int i2 = -1;
        while (true) {
            i2 = bitSet3.nextSetBit(i2 + 1);
            if (i2 == -1) {
                return;
            }
            Slog.v("HotPlugDetectionAction", "Add device by hot-plug detection:" + i2);
            hotplugDetectionAction.mService.sendCecCommand(
                    HdmiCecMessage.build(hotplugDetectionAction.getSourceAddress(), i2, 131), null);
        }
    }

    public HotplugDetectionAction(HdmiCecLocalDevice hdmiCecLocalDevice) {
        super(hdmiCecLocalDevice);
        this.mTimeoutCount = 0;
        this.mAvrStatusCount = 0;
        this.mIsTvDevice = this.mSource.mService.isTvDevice();
    }

    public static BitSet infoListToBitSet(List list, boolean z, boolean z2) {
        BitSet bitSet = new BitSet(15);
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            HdmiDeviceInfo hdmiDeviceInfo = (HdmiDeviceInfo) it.next();
            boolean z3 = true;
            boolean z4 = !z || hdmiDeviceInfo.getDeviceType() == 5;
            if (z2 && hdmiDeviceInfo.getPhysicalAddress() == 65535) {
                z3 = false;
            }
            if (z4 && z3) {
                bitSet.set(hdmiDeviceInfo.getLogicalAddress());
            }
        }
        return bitSet;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void handleTimerEvent(int i) {
        int i2 = this.mState;
        if (i2 == i && i2 == 1) {
            if (!this.mIsTvDevice) {
                pollAllDevices();
                addTimer(this.mState, 60000);
                return;
            }
            int i3 = (this.mTimeoutCount + 1) % 3;
            this.mTimeoutCount = i3;
            if (i3 == 0) {
                pollAllDevices();
            } else if (((HdmiCecLocalDeviceTv) this.mSource).isSystemAudioActivated()) {
                Slog.v("HotPlugDetectionAction", "Poll audio system.");
                pollDevices(new AnonymousClass1(this, 1), 65538, 0L);
            }
            addTimer(this.mState, 5000);
        }
    }

    public final void pollAllDevices() {
        Slog.v("HotPlugDetectionAction", "Poll all devices.");
        pollDevices(new AnonymousClass1(this, 0), 65537, this.mIsTvDevice ? 0L : 500L);
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final boolean processCommand(HdmiCecMessage hdmiCecMessage) {
        return false;
    }

    @Override // com.android.server.hdmi.HdmiCecFeatureAction
    public final void start() {
        Slog.v("HotPlugDetectionAction", "Hot-plug detection started.");
        this.mState = 1;
        this.mTimeoutCount = 0;
        addTimer(1, this.mIsTvDevice ? 5000 : 60000);
    }
}
