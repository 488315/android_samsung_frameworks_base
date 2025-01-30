package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.SparseArray;
import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes2.dex */
public final class HdmiCecStandbyModeHandler {
    public final CecMessageHandler mAborterIncorrectMode;
    public final CecMessageHandler mAborterRefused;
    public final CecMessageHandler mAborterUnrecognizedOpcode;
    public final CecMessageHandler mAutoOnHandler;
    public final CecMessageHandler mBypasser;
    public final CecMessageHandler mBystander;
    public final SparseArray mCecMessageHandlers = new SparseArray();
    public final CecMessageHandler mDefaultHandler;
    public final HdmiCecLocalDevice mDevice;
    public final HdmiControlService mService;
    public final UserControlProcessedHandler mUserControlProcessedHandler;

    public final class Bypasser implements CecMessageHandler {
        public Bypasser() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(HdmiCecMessage hdmiCecMessage) {
            return false;
        }
    }

    public final class Bystander implements CecMessageHandler {
        public Bystander() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(HdmiCecMessage hdmiCecMessage) {
            return true;
        }
    }

    public interface CecMessageHandler {
        boolean handle(HdmiCecMessage hdmiCecMessage);
    }

    public final class Aborter implements CecMessageHandler {
        public final int mReason;

        public Aborter(int i) {
            this.mReason = i;
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(HdmiCecMessage hdmiCecMessage) {
            HdmiCecStandbyModeHandler.this.mService.maySendFeatureAbortCommand(hdmiCecMessage, this.mReason);
            return true;
        }
    }

    public final class AutoOnHandler implements CecMessageHandler {
        public AutoOnHandler() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(HdmiCecMessage hdmiCecMessage) {
            if (((HdmiCecLocalDeviceTv) HdmiCecStandbyModeHandler.this.mDevice).getAutoWakeup()) {
                return false;
            }
            HdmiCecStandbyModeHandler.this.mAborterRefused.handle(hdmiCecMessage);
            return true;
        }
    }

    public final class UserControlProcessedHandler implements CecMessageHandler {
        public UserControlProcessedHandler() {
        }

        @Override // com.android.server.hdmi.HdmiCecStandbyModeHandler.CecMessageHandler
        public boolean handle(HdmiCecMessage hdmiCecMessage) {
            if (HdmiCecLocalDevice.isPowerOnOrToggleCommand(hdmiCecMessage)) {
                return false;
            }
            if (HdmiCecLocalDevice.isPowerOffOrToggleCommand(hdmiCecMessage)) {
                return true;
            }
            return HdmiCecStandbyModeHandler.this.mAborterIncorrectMode.handle(hdmiCecMessage);
        }
    }

    public final void addCommonHandlers() {
        addHandler(68, this.mUserControlProcessedHandler);
    }

    public final void addTvHandlers() {
        addHandler(130, this.mBystander);
        addHandler(133, this.mBystander);
        addHandler(128, this.mBystander);
        addHandler(129, this.mBystander);
        addHandler(134, this.mBystander);
        addHandler(54, this.mBystander);
        addHandler(50, this.mBystander);
        addHandler(69, this.mBystander);
        addHandler(0, this.mBystander);
        addHandler(FrameworkStatsLog.f681x729e24be, this.mBystander);
        addHandler(126, this.mBystander);
        addHandler(122, this.mBystander);
        addHandler(131, this.mBypasser);
        addHandler(145, this.mBypasser);
        addHandler(132, this.mBypasser);
        addHandler(140, this.mBypasser);
        addHandler(70, this.mBypasser);
        addHandler(71, this.mBypasser);
        addHandler(135, this.mBypasser);
        addHandler(144, this.mBypasser);
        addHandler(FrameworkStatsLog.f643xde3a78eb, this.mBypasser);
        addHandler(143, this.mBypasser);
        addHandler(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, this.mBypasser);
        addHandler(FrameworkStatsLog.f680x89db317, this.mBypasser);
        addHandler(160, this.mAborterIncorrectMode);
        addHandler(114, this.mAborterIncorrectMode);
        addHandler(4, this.mAutoOnHandler);
        addHandler(13, this.mAutoOnHandler);
        addHandler(10, this.mBystander);
        addHandler(15, this.mAborterIncorrectMode);
        addHandler(192, this.mAborterIncorrectMode);
        addHandler(197, this.mAborterIncorrectMode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public HdmiCecStandbyModeHandler(HdmiControlService hdmiControlService, HdmiCecLocalDevice hdmiCecLocalDevice) {
        Aborter aborter = new Aborter(0);
        this.mAborterUnrecognizedOpcode = aborter;
        this.mAborterIncorrectMode = new Aborter(1);
        this.mAborterRefused = new Aborter(4);
        this.mAutoOnHandler = new AutoOnHandler();
        Bypasser bypasser = new Bypasser();
        this.mBypasser = bypasser;
        this.mBystander = new Bystander();
        this.mUserControlProcessedHandler = new UserControlProcessedHandler();
        this.mService = hdmiControlService;
        this.mDevice = hdmiCecLocalDevice;
        addCommonHandlers();
        if (hdmiCecLocalDevice.getType() == 0) {
            addTvHandlers();
            this.mDefaultHandler = aborter;
        } else {
            this.mDefaultHandler = bypasser;
        }
    }

    public final void addHandler(int i, CecMessageHandler cecMessageHandler) {
        this.mCecMessageHandlers.put(i, cecMessageHandler);
    }

    public boolean handleCommand(HdmiCecMessage hdmiCecMessage) {
        CecMessageHandler cecMessageHandler = (CecMessageHandler) this.mCecMessageHandlers.get(hdmiCecMessage.getOpcode());
        if (cecMessageHandler != null) {
            return cecMessageHandler.handle(hdmiCecMessage);
        }
        return this.mDefaultHandler.handle(hdmiCecMessage);
    }
}
