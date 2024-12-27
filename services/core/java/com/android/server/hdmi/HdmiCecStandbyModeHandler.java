package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class HdmiCecStandbyModeHandler {
    public final Aborter mAborterIncorrectMode;
    public final Aborter mAborterRefused;
    public final Aborter mAborterUnrecognizedOpcode;
    public final AutoOnHandler mAutoOnHandler;
    public final Bypasser mBypasser;
    public final Bypasser mBystander;
    public final SparseArray mCecMessageHandlers = new SparseArray();
    public final CecMessageHandler mDefaultHandler;
    public final HdmiCecLocalDevice mDevice;
    public final HdmiControlService mService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Aborter implements CecMessageHandler {
        public final int mReason;

        public Aborter(int i) {
            this.mReason = i;
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public final boolean handle(HdmiCecMessage hdmiCecMessage) {
            HdmiControlService hdmiControlService = HdmiCecStandbyModeHandler.this.mService;
            hdmiControlService.assertRunOnServiceThread();
            hdmiControlService.mCecController.maySendFeatureAbortCommand(
                    this.mReason, hdmiCecMessage);
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AutoOnHandler implements CecMessageHandler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ HdmiCecStandbyModeHandler this$0;

        public /* synthetic */ AutoOnHandler(
                HdmiCecStandbyModeHandler hdmiCecStandbyModeHandler, int i) {
            this.$r8$classId = i;
            this.this$0 = hdmiCecStandbyModeHandler;
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public final boolean handle(HdmiCecMessage hdmiCecMessage) {
            switch (this.$r8$classId) {
                case 0:
                    HdmiCecStandbyModeHandler hdmiCecStandbyModeHandler = this.this$0;
                    if (!((HdmiCecLocalDeviceTv) hdmiCecStandbyModeHandler.mDevice)
                            .getAutoWakeup()) {
                        hdmiCecStandbyModeHandler.mAborterRefused.handle(hdmiCecMessage);
                        break;
                    }
                    break;
                default:
                    if (!HdmiCecLocalDevice.isPowerOnOrToggleCommand(hdmiCecMessage)) {
                        if (!HdmiCecLocalDevice.isPowerOffOrToggleCommand(hdmiCecMessage)) {
                            this.this$0.mAborterIncorrectMode.handle(hdmiCecMessage);
                        }
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Bypasser implements CecMessageHandler {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ Bypasser(int i) {
            this.$r8$classId = i;
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public final boolean handle(HdmiCecMessage hdmiCecMessage) {
            switch (this.$r8$classId) {
                case 0:
                    return false;
                default:
                    return true;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface CecMessageHandler {
        boolean handle(HdmiCecMessage hdmiCecMessage);
    }

    public HdmiCecStandbyModeHandler(
            HdmiControlService hdmiControlService, HdmiCecLocalDevice hdmiCecLocalDevice) {
        Aborter aborter = new Aborter(0);
        Aborter aborter2 = new Aborter(1);
        this.mAborterIncorrectMode = aborter2;
        this.mAborterRefused = new Aborter(4);
        CecMessageHandler autoOnHandler = new AutoOnHandler(this, 0);
        CecMessageHandler bypasser = new Bypasser(0);
        CecMessageHandler bypasser2 = new Bypasser(1);
        CecMessageHandler autoOnHandler2 = new AutoOnHandler(this, 1);
        this.mService = hdmiControlService;
        this.mDevice = hdmiCecLocalDevice;
        addHandler(68, autoOnHandler2);
        if (hdmiCecLocalDevice.mDeviceType != 0) {
            this.mDefaultHandler = bypasser;
            return;
        }
        addHandler(130, bypasser2);
        addHandler(133, bypasser2);
        addHandler(128, bypasser2);
        addHandler(129, bypasser2);
        addHandler(134, bypasser2);
        addHandler(54, bypasser2);
        addHandler(50, bypasser2);
        addHandler(69, bypasser2);
        addHandler(0, bypasser2);
        addHandler(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_WORK_APPS_DISABLED,
                bypasser2);
        addHandler(126, bypasser2);
        addHandler(122, bypasser2);
        addHandler(131, bypasser);
        addHandler(145, bypasser);
        addHandler(132, bypasser);
        addHandler(140, bypasser);
        addHandler(70, bypasser);
        addHandler(71, bypasser);
        addHandler(135, bypasser);
        addHandler(144, bypasser);
        addHandler(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__CROSS_PROFILE_SETTINGS_PAGE_MISSING_WORK_APP,
                bypasser);
        addHandler(143, bypasser);
        addHandler(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, bypasser);
        addHandler(
                FrameworkStatsLog
                        .DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_EMPTY_STATE_NO_SHARING_TO_WORK,
                bypasser);
        addHandler(160, aborter2);
        addHandler(114, aborter2);
        addHandler(4, autoOnHandler);
        addHandler(13, autoOnHandler);
        addHandler(10, bypasser2);
        addHandler(15, aborter2);
        addHandler(192, aborter2);
        addHandler(197, aborter2);
        this.mDefaultHandler = aborter;
    }

    public final void addHandler(int i, CecMessageHandler cecMessageHandler) {
        this.mCecMessageHandlers.put(i, cecMessageHandler);
    }
}
