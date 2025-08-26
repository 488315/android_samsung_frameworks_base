package com.sec.ims.volte2;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
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

/* loaded from: classes4.dex */
public interface IVolteService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.volte2.IVolteService";

    void changeAudioPath(int i, int i2) throws RemoteException;

    CallProfile createCallProfile(int i, int i2) throws RemoteException;

    IImsCallSession createSession(CallProfile callProfile) throws RemoteException;

    IImsCallSession createSessionWithRegId(CallProfile callProfile, int i) throws RemoteException;

    void deRegisterForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) throws RemoteException;

    void deregisterForCallStateEvent(IImsCallEventListener iImsCallEventListener) throws RemoteException;

    void deregisterForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) throws RemoteException;

    void enableCallWaitingRule(boolean z) throws RemoteException;

    int[] getCallCount() throws RemoteException;

    ImsCallInfo[] getImsCallInfos(int i) throws RemoteException;

    int getNetworkType(int i) throws RemoteException;

    int getParticipantIdForMerge(int i, int i2) throws RemoteException;

    IImsCallSession getPendingSession(String str) throws RemoteException;

    ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException;

    int getRttMode() throws RemoteException;

    IImsCallSession getSession(int i) throws RemoteException;

    IImsCallSession getSessionByCallId(int i) throws RemoteException;

    String getTrn(String str, String str2) throws RemoteException;

    void notifyProgressIncomingCall(int i, Map map) throws RemoteException;

    void registerForCallStateEvent(IImsCallEventListener iImsCallEventListener) throws RemoteException;

    void registerForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) throws RemoteException;

    void registerForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) throws RemoteException;

    void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, boolean z, int i) throws RemoteException;

    void registerRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException;

    void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException;

    void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException;

    void setAutomaticMode(int i, boolean z) throws RemoteException;

    void setTtyMode(int i) throws RemoteException;

    int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

    int stopLocalRingBackTone() throws RemoteException;

    void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void unregisterRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException;

    String updateEccUrn(int i, String str) throws RemoteException;

    public abstract class Stub extends Binder implements IVolteService {
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

        class Proxy implements IVolteService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void changeAudioPath(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public CallProfile createCallProfile(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CallProfile) parcelObtain2.readTypedObject(CallProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession createSession(CallProfile callProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession createSessionWithRegId(CallProfile callProfile, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callProfile, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void deRegisterForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVolteServiceEventListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void deregisterForCallStateEvent(IImsCallEventListener iImsCallEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void deregisterForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void enableCallWaitingRule(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int[] getCallCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public ImsCallInfo[] getImsCallInfos(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsCallInfo[]) parcelObtain2.createTypedArray(ImsCallInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IVolteService.DESCRIPTOR;
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int getNetworkType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int getParticipantIdForMerge(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession getPendingSession(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsRegistration[]) parcelObtain2.createTypedArray(ImsRegistration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int getRttMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession getSession(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public IImsCallSession getSessionByCallId(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public String getTrn(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void notifyProgressIncomingCall(int i, Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerForCallStateEvent(IImsCallEventListener iImsCallEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iVolteServiceEventListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void registerRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRttEventListener);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void setAutomaticMode(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void setTtyMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public void unregisterRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iRttEventListener);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.volte2.IVolteService
            public String updateEccUrn(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IVolteService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IVolteService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVolteService)) ? new Proxy(iBinder) : (IVolteService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IVolteService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IVolteService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    IVolteServiceEventListener iVolteServiceEventListenerAsInterface = IVolteServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForVolteServiceEvent(i3, iVolteServiceEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    IVolteServiceEventListener iVolteServiceEventListenerAsInterface2 = IVolteServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deRegisterForVolteServiceEvent(i4, iVolteServiceEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    boolean z = parcel.readBoolean();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationListener(iImsRegistrationListenerAsInterface, z, i5);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface2 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListener(iImsRegistrationListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    CallProfile callProfileCreateCallProfile = createCallProfile(i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(callProfileCreateCallProfile, 1);
                    return true;
                case 6:
                    CallProfile callProfile = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSession iImsCallSessionCreateSession = createSession(callProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsCallSessionCreateSession);
                    return true;
                case 7:
                    CallProfile callProfile2 = (CallProfile) parcel.readTypedObject(CallProfile.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsCallSession iImsCallSessionCreateSessionWithRegId = createSessionWithRegId(callProfile2, i8);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsCallSessionCreateSessionWithRegId);
                    return true;
                case 8:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IImsCallSession pendingSession = getPendingSession(string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(pendingSession);
                    return true;
                case 9:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsCallSession session = getSession(i9);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(session);
                    return true;
                case 10:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTtyMode(i10);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IImsCallEventListener iImsCallEventListenerAsInterface = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForCallStateEvent(iImsCallEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IImsCallEventListener iImsCallEventListenerAsInterface2 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deregisterForCallStateEvent(iImsCallEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i11 = parcel.readInt();
                    IImsCallEventListener iImsCallEventListenerAsInterface3 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForCallStateEventForSlot(i11, iImsCallEventListenerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i12 = parcel.readInt();
                    IImsCallEventListener iImsCallEventListenerAsInterface4 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deregisterForCallStateEventForSlot(i12, iImsCallEventListenerAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    enableCallWaitingRule(z2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i13 = parcel.readInt();
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    notifyProgressIncomingCall(i13, hashMap);
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
                    int i14 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAutomaticMode(i14, z3);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    int i15 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyResponse(i15, z4);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i16 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendRttSessionModifyRequest(i16, z5);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i17 = parcel.readInt();
                    IRttEventListener iRttEventListenerAsInterface = IRttEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerRttEventListener(i17, iRttEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i18 = parcel.readInt();
                    IRttEventListener iRttEventListenerAsInterface2 = IRttEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRttEventListener(i18, iRttEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int participantIdForMerge = getParticipantIdForMerge(i19, i20);
                    parcel2.writeNoException();
                    parcel2.writeInt(participantIdForMerge);
                    return true;
                case 25:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsCallSession sessionByCallId = getSessionByCallId(i21);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(sessionByCallId);
                    return true;
                case 26:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsRegistration[] registrationInfoByPhoneId = getRegistrationInfoByPhoneId(i22);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(registrationInfoByPhoneId, 1);
                    return true;
                case 27:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int networkType = getNetworkType(i23);
                    parcel2.writeNoException();
                    parcel2.writeInt(networkType);
                    return true;
                case 28:
                    int i24 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strUpdateEccUrn = updateEccUrn(i24, string2);
                    parcel2.writeNoException();
                    parcel2.writeString(strUpdateEccUrn);
                    return true;
                case 29:
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(i25, i26);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartLocalRingBackTone = startLocalRingBackTone(i27, i28, i29);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartLocalRingBackTone);
                    return true;
                case 31:
                    int iStopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopLocalRingBackTone);
                    return true;
                case 32:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String trn = getTrn(string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeString(trn);
                    return true;
                case 33:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsCallInfo[] imsCallInfos = getImsCallInfos(i30);
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

    public class Default implements IVolteService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public CallProfile createCallProfile(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession createSession(CallProfile callProfile) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession createSessionWithRegId(CallProfile callProfile, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int[] getCallCount() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public ImsCallInfo[] getImsCallInfos(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int getNetworkType(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int getParticipantIdForMerge(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession getPendingSession(String str) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public ImsRegistration[] getRegistrationInfoByPhoneId(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int getRttMode() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession getSession(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public IImsCallSession getSessionByCallId(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public String getTrn(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public int stopLocalRingBackTone() throws RemoteException {
            return 0;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public String updateEccUrn(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void deregisterForCallStateEvent(IImsCallEventListener iImsCallEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void enableCallWaitingRule(boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerForCallStateEvent(IImsCallEventListener iImsCallEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void setTtyMode(int i) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void changeAudioPath(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void deRegisterForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void deregisterForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void notifyProgressIncomingCall(int i, Map map) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerForCallStateEventForSlot(int i, IImsCallEventListener iImsCallEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerForVolteServiceEvent(int i, IVolteServiceEventListener iVolteServiceEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void sendRttSessionModifyRequest(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void sendRttSessionModifyResponse(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void setAutomaticMode(int i, boolean z) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void unregisterRttEventListener(int i, IRttEventListener iRttEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.volte2.IVolteService
        public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, boolean z, int i) throws RemoteException {
        }
    }
}
