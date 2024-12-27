package com.android.server.hdmi;

import android.hardware.tv.hdmi.earc.IEArc;
import android.hardware.tv.hdmi.earc.IEArcCallback;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.util.Slog;

import java.util.ArrayList;

public final class HdmiEarcController {
    public Handler mControlHandler;
    public final EarcNativeWrapper mEarcNativeWrapperImpl;
    public final HdmiControlService mService;

    public final class EarcAidlCallback extends Binder implements IEArcCallback {
        public EarcAidlCallback() {
            markVintfStability();
            attachInterface(this, IEArcCallback.DESCRIPTOR);
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            String str = IEArcCallback.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(1);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                synchronized (this) {
                }
                parcel2.writeString("101230f18c7b8438921e517e80eea4ccc7c1e463");
                return true;
            }
            if (i == 1) {
                final byte readByte = parcel.readByte();
                final int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                HdmiEarcController.this.runOnServiceThread(
                        new Runnable() { // from class:
                            // com.android.server.hdmi.HdmiEarcController$EarcAidlCallback$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                int i3;
                                int i4;
                                HdmiEarcController.EarcAidlCallback earcAidlCallback =
                                        HdmiEarcController.EarcAidlCallback.this;
                                byte b = readByte;
                                int i5 = readInt;
                                HdmiControlService hdmiControlService =
                                        HdmiEarcController.this.mService;
                                hdmiControlService.assertRunOnServiceThread();
                                hdmiControlService.assertRunOnServiceThread();
                                if (hdmiControlService.mEarcLocalDevice != null) {
                                    synchronized (hdmiControlService.mLock) {
                                        i3 = hdmiControlService.mEarcLocalDevice.mEarcStatus;
                                    }
                                } else {
                                    i3 = -1;
                                }
                                if (!hdmiControlService
                                        .mHdmiCecNetwork
                                        .getPortInfo(i5)
                                        .isEarcSupported()) {
                                    Slog.w(
                                            "HdmiControlService",
                                            "Tried to update eARC status on a port that doesn't"
                                                + " support eARC.");
                                    HdmiCecAtomWriter atomWriter =
                                            hdmiControlService.getAtomWriter();
                                    boolean isEarcSupported = hdmiControlService.isEarcSupported();
                                    boolean isEarcEnabled = hdmiControlService.isEarcEnabled();
                                    atomWriter.getClass();
                                    HdmiCecAtomWriter.earcStatusChanged(
                                            i3, b, 3, isEarcSupported, isEarcEnabled);
                                    return;
                                }
                                HdmiEarcLocalDevice hdmiEarcLocalDevice =
                                        hdmiControlService.mEarcLocalDevice;
                                if (hdmiEarcLocalDevice == null) {
                                    if (b != 2) {
                                        HdmiCecAtomWriter atomWriter2 =
                                                hdmiControlService.getAtomWriter();
                                        boolean isEarcSupported2 =
                                                hdmiControlService.isEarcSupported();
                                        boolean isEarcEnabled2 = hdmiControlService.isEarcEnabled();
                                        atomWriter2.getClass();
                                        HdmiCecAtomWriter.earcStatusChanged(
                                                i3, b, 4, isEarcSupported2, isEarcEnabled2);
                                        return;
                                    }
                                    HdmiLogger.debug(
                                            "eARC state change [new:"
                                                + " HDMI_EARC_STATUS_ARC_PENDING(2)]",
                                            new Object[0]);
                                    hdmiControlService.notifyEarcStatusToAudioService(
                                            new ArrayList(), false);
                                    hdmiControlService.mHandler.postDelayed(
                                            new HdmiControlService.AnonymousClass30(
                                                    hdmiControlService, 2),
                                            500L);
                                    HdmiCecAtomWriter atomWriter3 =
                                            hdmiControlService.getAtomWriter();
                                    boolean isEarcSupported3 = hdmiControlService.isEarcSupported();
                                    boolean isEarcEnabled3 = hdmiControlService.isEarcEnabled();
                                    atomWriter3.getClass();
                                    HdmiCecAtomWriter.earcStatusChanged(
                                            i3, b, 2, isEarcSupported3, isEarcEnabled3);
                                    return;
                                }
                                HdmiEarcLocalDeviceTx hdmiEarcLocalDeviceTx =
                                        (HdmiEarcLocalDeviceTx) hdmiEarcLocalDevice;
                                synchronized (hdmiEarcLocalDeviceTx.mLock) {
                                    int i6 = hdmiEarcLocalDeviceTx.mEarcStatus;
                                    String[] strArr = HdmiEarcLocalDeviceTx.earcStatusNames;
                                    HdmiLogger.debug(
                                            "eARC state change [old: %s(%d) new: %s(%d)]",
                                            strArr[i6],
                                            Integer.valueOf(i6),
                                            strArr[b],
                                            Integer.valueOf(b));
                                    i4 = hdmiEarcLocalDeviceTx.mEarcStatus;
                                    hdmiEarcLocalDeviceTx.mEarcStatus = b;
                                }
                                hdmiEarcLocalDeviceTx.mReportCapsHandler.removeCallbacksAndMessages(
                                        null);
                                if (b == 0) {
                                    hdmiEarcLocalDeviceTx.mService.notifyEarcStatusToAudioService(
                                            new ArrayList(), false);
                                    hdmiEarcLocalDeviceTx.mService.startArcAction(false, null);
                                } else if (b == 2) {
                                    hdmiEarcLocalDeviceTx.mService.notifyEarcStatusToAudioService(
                                            new ArrayList(), false);
                                    hdmiEarcLocalDeviceTx.mService.startArcAction(true, null);
                                } else if (b == 1 && i4 == 2) {
                                    hdmiEarcLocalDeviceTx.mService.startArcAction(false, null);
                                } else if (b == 3) {
                                    if (i4 == 2) {
                                        hdmiEarcLocalDeviceTx.mService.startArcAction(false, null);
                                    }
                                    hdmiEarcLocalDeviceTx.mReportCapsHandler.postDelayed(
                                            hdmiEarcLocalDeviceTx.mReportCapsRunnable, 2000L);
                                }
                                HdmiCecAtomWriter atomWriter4 = hdmiControlService.getAtomWriter();
                                boolean isEarcSupported4 = hdmiControlService.isEarcSupported();
                                boolean isEarcEnabled4 = hdmiControlService.isEarcEnabled();
                                atomWriter4.getClass();
                                HdmiCecAtomWriter.earcStatusChanged(
                                        i3, b, 2, isEarcSupported4, isEarcEnabled4);
                            }
                        });
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                final byte[] createByteArray = parcel.createByteArray();
                final int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                HdmiEarcController.this.runOnServiceThread(
                        new Runnable() { // from class:
                            // com.android.server.hdmi.HdmiEarcController$EarcAidlCallback$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                HdmiEarcController.EarcAidlCallback earcAidlCallback =
                                        HdmiEarcController.EarcAidlCallback.this;
                                byte[] bArr = createByteArray;
                                int i3 = readInt2;
                                HdmiControlService hdmiControlService =
                                        HdmiEarcController.this.mService;
                                hdmiControlService.assertRunOnServiceThread();
                                if (!hdmiControlService
                                        .mHdmiCecNetwork
                                        .getPortInfo(i3)
                                        .isEarcSupported()) {
                                    Slog.w(
                                            "HdmiControlService",
                                            "Tried to process eARC capabilities from a port that"
                                                + " doesn't support eARC.");
                                    return;
                                }
                                HdmiEarcLocalDevice hdmiEarcLocalDevice =
                                        hdmiControlService.mEarcLocalDevice;
                                if (hdmiEarcLocalDevice != null) {
                                    HdmiEarcLocalDeviceTx hdmiEarcLocalDeviceTx =
                                            (HdmiEarcLocalDeviceTx) hdmiEarcLocalDevice;
                                    synchronized (hdmiEarcLocalDeviceTx.mLock) {
                                        try {
                                            if (hdmiEarcLocalDeviceTx.mEarcStatus == 3
                                                    && hdmiEarcLocalDeviceTx.mReportCapsHandler
                                                            .hasCallbacks(
                                                                    hdmiEarcLocalDeviceTx
                                                                            .mReportCapsRunnable)) {
                                                hdmiEarcLocalDeviceTx.mReportCapsHandler
                                                        .removeCallbacksAndMessages(null);
                                                hdmiEarcLocalDeviceTx.mService
                                                        .notifyEarcStatusToAudioService(
                                                                HdmiEarcLocalDeviceTx
                                                                        .parseCapabilities(bArr),
                                                                true);
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            }
                        });
            }
            return true;
        }
    }

    public interface EarcNativeWrapper {
        byte[] nativeGetLastReportedAudioCapabilities(int i);

        byte nativeGetState(int i);

        boolean nativeInit();

        boolean nativeIsEarcEnabled();

        void nativeSetCallback(EarcAidlCallback earcAidlCallback);

        void nativeSetEarcEnabled(boolean z);
    }

    public final class EarcNativeWrapperImpl implements EarcNativeWrapper, IBinder.DeathRecipient {
        public IEArc mEarc;
        public EarcAidlCallback mEarcCallback;

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            ((IEArc.Stub.Proxy) this.mEarc).mRemote.unlinkToDeath(this, 0);
            connectToHal();
            EarcAidlCallback earcAidlCallback = this.mEarcCallback;
            if (earcAidlCallback != null) {
                nativeSetCallback(earcAidlCallback);
            }
        }

        public final boolean connectToHal() {
            IEArc iEArc;
            StringBuilder sb = new StringBuilder();
            String str = IEArc.DESCRIPTOR;
            sb.append(str);
            sb.append("/default");
            IBinder service = ServiceManager.getService(sb.toString());
            int i = IEArc.Stub.$r8$clinit;
            if (service == null) {
                iEArc = null;
            } else {
                IInterface queryLocalInterface = service.queryLocalInterface(str);
                if (queryLocalInterface == null || !(queryLocalInterface instanceof IEArc)) {
                    IEArc.Stub.Proxy proxy = new IEArc.Stub.Proxy();
                    proxy.mRemote = service;
                    iEArc = proxy;
                } else {
                    iEArc = (IEArc) queryLocalInterface;
                }
            }
            this.mEarc = iEArc;
            if (iEArc == null) {
                return false;
            }
            try {
                ((IEArc.Stub.Proxy) iEArc).mRemote.linkToDeath(this, 0);
                return true;
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Couldn't link callback object: ", new Object[0]);
                return true;
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public final byte[] nativeGetLastReportedAudioCapabilities(int i) {
            try {
                return ((IEArc.Stub.Proxy) this.mEarc).getLastReportedAudioCapabilities(i);
            } catch (RemoteException e) {
                HdmiLogger.error(
                        e,
                        "Could not read last reported audio capabilities. Exception: ",
                        new Object[0]);
                return null;
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public final byte nativeGetState(int i) {
            try {
                return ((IEArc.Stub.Proxy) this.mEarc).getState(i);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Could not get eARC state. Exception: ", new Object[0]);
                return (byte) -1;
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public final boolean nativeInit() {
            return connectToHal();
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public final boolean nativeIsEarcEnabled() {
            try {
                return ((IEArc.Stub.Proxy) this.mEarc).isEArcEnabled();
            } catch (RemoteException e) {
                HdmiLogger.error(
                        e, "Could not read if eARC is enabled. Exception: ", new Object[0]);
                return false;
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public final void nativeSetCallback(EarcAidlCallback earcAidlCallback) {
            this.mEarcCallback = earcAidlCallback;
            try {
                ((IEArc.Stub.Proxy) this.mEarc).setCallback(earcAidlCallback);
            } catch (RemoteException e) {
                HdmiLogger.error(e, "Could not set callback. Exception: ", new Object[0]);
            }
        }

        @Override // com.android.server.hdmi.HdmiEarcController.EarcNativeWrapper
        public final void nativeSetEarcEnabled(boolean z) {
            try {
                ((IEArc.Stub.Proxy) this.mEarc).setEArcEnabled(z);
            } catch (ServiceSpecificException e) {
                HdmiLogger.error(
                        "Could not set eARC enabled to " + z + ". Error: ",
                        Integer.valueOf(e.errorCode));
            } catch (RemoteException e2) {
                HdmiLogger.error(
                        e2, "Could not set eARC enabled to " + z + ":. Exception: ", new Object[0]);
            }
        }
    }

    public HdmiEarcController(
            HdmiControlService hdmiControlService, EarcNativeWrapperImpl earcNativeWrapperImpl) {
        this.mService = hdmiControlService;
        this.mEarcNativeWrapperImpl = earcNativeWrapperImpl;
    }

    public void runOnServiceThread(Runnable runnable) {
        this.mControlHandler.post(new WorkSourceUidPreservingRunnable(runnable));
    }
}
