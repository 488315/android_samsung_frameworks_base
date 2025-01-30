package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.IRttEventListener;
import com.sec.ims.ImsRegistration;
import com.sec.ims.volte2.IImsCallEventListener;
import com.sec.ims.volte2.IImsCallSession;
import com.sec.ims.volte2.IVolteServiceEventListener;
import com.sec.ims.volte2.data.CallProfile;
import com.sec.ims.volte2.data.ImsCallInfo;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IVolteService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IVolteService";

    void changeAudioPath(int i, int i2);

    CallProfile createCallProfile(int i, int i2);

    IImsCallSession createSession(CallProfile callProfile);

    IImsCallSession createSessionWithRegId(CallProfile callProfile, int i);

    void deRegisterForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener);

    void deregisterForCallStateEvent(IImsCallEventListener iImsCallEventListener);

    void deregisterForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener);

    void enableCallWaitingRule(boolean z);

    int[] getCallCount();

    ImsCallInfo[] getImsCallInfos(int i);

    int getNetworkType(int i);

    int getParticipantIdForMerge(int i, int i2);

    IImsCallSession getPendingSession(String str);

    ImsRegistration[] getRegistrationInfoByPhoneId(int i);

    int getRttMode();

    IImsCallSession getSession(int i);

    IImsCallSession getSessionByCallId(int i);

    String getTrn(String str, String str2);

    void notifyProgressIncomingCall(int i, Map map);

    void registerForCallStateEvent(IImsCallEventListener iImsCallEventListener);

    void registerForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener);

    void registerForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener);

    void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, boolean z, int i);

    void registerRttEventListener(int i, IRttEventListener iRttEventListener);

    void sendRttSessionModifyRequest(int i, boolean z);

    void sendRttSessionModifyResponse(int i, boolean z);

    void setAutomaticMode(int i, boolean z);

    void setTtyMode(int i);

    int startLocalRingBackTone(int i, int i2, int i3);

    int stopLocalRingBackTone();

    void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener);

    void unregisterRttEventListener(int i, IRttEventListener iRttEventListener);

    String updateEccUrn(int i, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IVolteService {
        static final int TRANSACTION_changeAudioPath = 29;
        static final int TRANSACTION_createCallProfile = 5;
        static final int TRANSACTION_createSession = 6;
        static final int TRANSACTION_createSessionWithRegId = 7;
        static final int TRANSACTION_deRegisterForVolteServiceEvent = 2;
        static final int TRANSACTION_deregisterForCallStateEvent = 12;
        static final int TRANSACTION_deregisterForCallStateEventForSlot = 14;
        static final int TRANSACTION_enableCallWaitingRule = 15;
        static final int TRANSACTION_getCallCount = 17;
        static final int TRANSACTION_getImsCallInfos = 33;
        static final int TRANSACTION_getNetworkType = 27;
        static final int TRANSACTION_getParticipantIdForMerge = 24;
        static final int TRANSACTION_getPendingSession = 8;
        static final int TRANSACTION_getRegistrationInfoByPhoneId = 26;
        static final int TRANSACTION_getRttMode = 18;
        static final int TRANSACTION_getSession = 9;
        static final int TRANSACTION_getSessionByCallId = 25;
        static final int TRANSACTION_getTrn = 32;
        static final int TRANSACTION_notifyProgressIncomingCall = 16;
        static final int TRANSACTION_registerForCallStateEvent = 11;
        static final int TRANSACTION_registerForCallStateEventForSlot = 13;
        static final int TRANSACTION_registerForVolteServiceEvent = 1;
        static final int TRANSACTION_registerImsRegistrationListener = 3;
        static final int TRANSACTION_registerRttEventListener = 22;
        static final int TRANSACTION_sendRttSessionModifyRequest = 21;
        static final int TRANSACTION_sendRttSessionModifyResponse = 20;
        static final int TRANSACTION_setAutomaticMode = 19;
        static final int TRANSACTION_setTtyMode = 10;
        static final int TRANSACTION_startLocalRingBackTone = 30;
        static final int TRANSACTION_stopLocalRingBackTone = 31;
        static final int TRANSACTION_unregisterImsRegistrationListener = 4;
        static final int TRANSACTION_unregisterRttEventListener = 23;
        static final int TRANSACTION_updateEccUrn = 28;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IVolteService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void changeAudioPath(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public CallProfile createCallProfile(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CallProfile) obtain2.readTypedObject(CallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession createSession(CallProfile callProfile) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession createSessionWithRegId(CallProfile callProfile, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeTypedObject(callProfile, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void deRegisterForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVolteServiceEventListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void deregisterForCallStateEvent(IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void deregisterForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void enableCallWaitingRule(boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int[] getCallCount() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public ImsCallInfo[] getImsCallInfos(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsCallInfo[]) obtain2.createTypedArray(ImsCallInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IVolteService.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int getNetworkType(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int getParticipantIdForMerge(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession getPendingSession(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public ImsRegistration[] getRegistrationInfoByPhoneId(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsRegistration[]) obtain2.createTypedArray(ImsRegistration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int getRttMode() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession getSession(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession getSessionByCallId(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public String getTrn(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void notifyProgressIncomingCall(int i, Map map) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeMap(map);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerForCallStateEvent(IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVolteServiceEventListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, boolean z, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerRttEventListener(int i, IRttEventListener iRttEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRttEventListener);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void sendRttSessionModifyRequest(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void sendRttSessionModifyResponse(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void setAutomaticMode(int i, boolean z) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void setTtyMode(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int startLocalRingBackTone(int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int stopLocalRingBackTone() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void unregisterRttEventListener(int i, IRttEventListener iRttEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iRttEventListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public String updateEccUrn(int i, String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IVolteService.DESCRIPTOR);
        }

        public static IVolteService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IVolteService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IVolteService)) ? new Proxy(iBinder) : (IVolteService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVolteService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVolteService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    IVolteServiceEventListener asInterface = IVolteServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForVolteServiceEvent(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    IVolteServiceEventListener asInterface2 = IVolteServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deRegisterForVolteServiceEvent(readInt2, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IImsRegistrationListener asInterface3 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationListener(asInterface3, readBoolean, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IImsRegistrationListener asInterface4 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CallProfile createCallProfile = createCallProfile(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createCallProfile, 1);
                    return true;
                case 6:
                    CallProfile callProfile = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSession createSession = createSession(callProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createSession);
                    return true;
                case 7:
                    CallProfile callProfile2 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsCallSession createSessionWithRegId = createSessionWithRegId(callProfile2, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createSessionWithRegId);
                    return true;
                case 8:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IImsCallSession pendingSession = getPendingSession(readString);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(pendingSession);
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsCallSession session = getSession(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(session);
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTtyMode(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IImsCallEventListener asInterface5 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForCallStateEvent(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IImsCallEventListener asInterface6 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deregisterForCallStateEvent(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int readInt9 = parcel.readInt();
                    IImsCallEventListener asInterface7 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForCallStateEventForSlot(readInt9, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt10 = parcel.readInt();
                    IImsCallEventListener asInterface8 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deregisterForCallStateEventForSlot(readInt10, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableCallWaitingRule(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt11 = parcel.readInt();
                    HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    notifyProgressIncomingCall(readInt11, readHashMap);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    int[] callCount = getCallCount();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(callCount);
                    return true;
                case 18:
                    int rttMode = getRttMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(rttMode);
                    return true;
                case 19:
                    int readInt12 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutomaticMode(readInt12, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int readInt13 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyResponse(readInt13, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt14 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyRequest(readInt14, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt15 = parcel.readInt();
                    IRttEventListener asInterface9 = IRttEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRttEventListener(readInt15, asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt16 = parcel.readInt();
                    IRttEventListener asInterface10 = IRttEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRttEventListener(readInt16, asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int participantIdForMerge = getParticipantIdForMerge(readInt17, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeInt(participantIdForMerge);
                    return true;
                case 25:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsCallSession sessionByCallId = getSessionByCallId(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(sessionByCallId);
                    return true;
                case 26:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsRegistration[] registrationInfoByPhoneId = getRegistrationInfoByPhoneId(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfoByPhoneId, 1);
                    return true;
                case 27:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int networkType = getNetworkType(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkType);
                    return true;
                case 28:
                    int readInt22 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String updateEccUrn = updateEccUrn(readInt22, readString2);
                    parcel2.writeNoException();
                    parcel2.writeString(updateEccUrn);
                    return true;
                case 29:
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(readInt23, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startLocalRingBackTone = startLocalRingBackTone(readInt25, readInt26, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeInt(startLocalRingBackTone);
                    return true;
                case 31:
                    int stopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopLocalRingBackTone);
                    return true;
                case 32:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String trn = getTrn(readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeString(trn);
                    return true;
                case 33:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsCallInfo[] imsCallInfos = getImsCallInfos(readInt28);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(imsCallInfos, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IVolteService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public CallProfile createCallProfile(int i, int i2) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession createSession(CallProfile callProfile) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession createSessionWithRegId(CallProfile callProfile, int i) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int[] getCallCount() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public ImsCallInfo[] getImsCallInfos(int i) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int getNetworkType(int i) {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int getParticipantIdForMerge(int i, int i2) {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession getPendingSession(String str) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public ImsRegistration[] getRegistrationInfoByPhoneId(int i) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int getRttMode() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession getSession(int i) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession getSessionByCallId(int i) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public String getTrn(String str, String str2) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int startLocalRingBackTone(int i, int i2, int i3) {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int stopLocalRingBackTone() {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public String updateEccUrn(int i, String str) {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void deregisterForCallStateEvent(IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void enableCallWaitingRule(boolean z) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerForCallStateEvent(IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void setTtyMode(int i) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void changeAudioPath(int i, int i2) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void deRegisterForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void deregisterForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void notifyProgressIncomingCall(int i, Map map) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerRttEventListener(int i, IRttEventListener iRttEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void sendRttSessionModifyRequest(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void sendRttSessionModifyResponse(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void setAutomaticMode(int i, boolean z) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void unregisterRttEventListener(int i, IRttEventListener iRttEventListener) {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, boolean z, int i) {
        }
    }
}
