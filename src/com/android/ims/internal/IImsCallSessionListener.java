package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import com.android.ims.internal.IImsCallSession;

/* loaded from: classes5.dex */
public interface IImsCallSessionListener extends IInterface {

    public static class Default implements IImsCallSessionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandover(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandoverFailed(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMayHandover(IImsCallSession iImsCallSession, int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean z) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttMessageReceived(String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSendAnbrQuery(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferred() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int i, String str) throws RemoteException {
        }
    }

    void callQualityChanged(CallQuality callQuality) throws RemoteException;

    void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException;

    void callSessionHandover(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHandoverFailed(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException;

    void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionMayHandover(IImsCallSession iImsCallSession, int i, int i2) throws RemoteException;

    void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException;

    void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean z) throws RemoteException;

    void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException;

    void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRttMessageReceived(String str) throws RemoteException;

    void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRttModifyResponseReceived(int i) throws RemoteException;

    void callSessionSendAnbrQuery(int i, int i2, int i3) throws RemoteException;

    void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException;

    void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionTransferred() throws RemoteException;

    void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int i) throws RemoteException;

    void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsCallSessionListener {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsCallSessionListener";
        static final int TRANSACTION_callQualityChanged = 38;
        static final int TRANSACTION_callSessionConferenceExtendFailed = 18;
        static final int TRANSACTION_callSessionConferenceExtendReceived = 19;
        static final int TRANSACTION_callSessionConferenceExtended = 17;
        static final int TRANSACTION_callSessionConferenceStateUpdated = 24;
        static final int TRANSACTION_callSessionHandover = 26;
        static final int TRANSACTION_callSessionHandoverFailed = 27;
        static final int TRANSACTION_callSessionHeld = 5;
        static final int TRANSACTION_callSessionHoldFailed = 6;
        static final int TRANSACTION_callSessionHoldReceived = 7;
        static final int TRANSACTION_callSessionInviteParticipantsRequestDelivered = 20;
        static final int TRANSACTION_callSessionInviteParticipantsRequestFailed = 21;
        static final int TRANSACTION_callSessionMayHandover = 28;
        static final int TRANSACTION_callSessionMergeComplete = 12;
        static final int TRANSACTION_callSessionMergeFailed = 13;
        static final int TRANSACTION_callSessionMergeStarted = 11;
        static final int TRANSACTION_callSessionMultipartyStateChanged = 30;
        static final int TRANSACTION_callSessionProgressing = 1;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestDelivered = 22;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestFailed = 23;
        static final int TRANSACTION_callSessionResumeFailed = 9;
        static final int TRANSACTION_callSessionResumeReceived = 10;
        static final int TRANSACTION_callSessionResumed = 8;
        static final int TRANSACTION_callSessionRttAudioIndicatorChanged = 35;
        static final int TRANSACTION_callSessionRttMessageReceived = 34;
        static final int TRANSACTION_callSessionRttModifyRequestReceived = 32;
        static final int TRANSACTION_callSessionRttModifyResponseReceived = 33;
        static final int TRANSACTION_callSessionSendAnbrQuery = 39;
        static final int TRANSACTION_callSessionStartFailed = 3;
        static final int TRANSACTION_callSessionStarted = 2;
        static final int TRANSACTION_callSessionSuppServiceReceived = 31;
        static final int TRANSACTION_callSessionTerminated = 4;
        static final int TRANSACTION_callSessionTransferFailed = 37;
        static final int TRANSACTION_callSessionTransferred = 36;
        static final int TRANSACTION_callSessionTtyModeReceived = 29;
        static final int TRANSACTION_callSessionUpdateFailed = 15;
        static final int TRANSACTION_callSessionUpdateReceived = 16;
        static final int TRANSACTION_callSessionUpdated = 14;
        static final int TRANSACTION_callSessionUssdMessageReceived = 25;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsCallSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsCallSessionListener)) {
                return (IImsCallSessionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "callSessionProgressing";
                case 2:
                    return "callSessionStarted";
                case 3:
                    return "callSessionStartFailed";
                case 4:
                    return "callSessionTerminated";
                case 5:
                    return "callSessionHeld";
                case 6:
                    return "callSessionHoldFailed";
                case 7:
                    return "callSessionHoldReceived";
                case 8:
                    return "callSessionResumed";
                case 9:
                    return "callSessionResumeFailed";
                case 10:
                    return "callSessionResumeReceived";
                case 11:
                    return "callSessionMergeStarted";
                case 12:
                    return "callSessionMergeComplete";
                case 13:
                    return "callSessionMergeFailed";
                case 14:
                    return "callSessionUpdated";
                case 15:
                    return "callSessionUpdateFailed";
                case 16:
                    return "callSessionUpdateReceived";
                case 17:
                    return "callSessionConferenceExtended";
                case 18:
                    return "callSessionConferenceExtendFailed";
                case 19:
                    return "callSessionConferenceExtendReceived";
                case 20:
                    return "callSessionInviteParticipantsRequestDelivered";
                case 21:
                    return "callSessionInviteParticipantsRequestFailed";
                case 22:
                    return "callSessionRemoveParticipantsRequestDelivered";
                case 23:
                    return "callSessionRemoveParticipantsRequestFailed";
                case 24:
                    return "callSessionConferenceStateUpdated";
                case 25:
                    return "callSessionUssdMessageReceived";
                case 26:
                    return "callSessionHandover";
                case 27:
                    return "callSessionHandoverFailed";
                case 28:
                    return "callSessionMayHandover";
                case 29:
                    return "callSessionTtyModeReceived";
                case 30:
                    return "callSessionMultipartyStateChanged";
                case 31:
                    return "callSessionSuppServiceReceived";
                case 32:
                    return "callSessionRttModifyRequestReceived";
                case 33:
                    return "callSessionRttModifyResponseReceived";
                case 34:
                    return "callSessionRttMessageReceived";
                case 35:
                    return "callSessionRttAudioIndicatorChanged";
                case 36:
                    return "callSessionTransferred";
                case 37:
                    return "callSessionTransferFailed";
                case 38:
                    return "callQualityChanged";
                case 39:
                    return "callSessionSendAnbrQuery";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IImsCallSession asInterface = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsStreamMediaProfile imsStreamMediaProfile = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionProgressing(asInterface, imsStreamMediaProfile);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IImsCallSession asInterface2 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionStarted(asInterface2, imsCallProfile);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IImsCallSession asInterface3 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionStartFailed(asInterface3, imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IImsCallSession asInterface4 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo2 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTerminated(asInterface4, imsReasonInfo2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IImsCallSession asInterface5 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile2 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHeld(asInterface5, imsCallProfile2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IImsCallSession asInterface6 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo3 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldFailed(asInterface6, imsReasonInfo3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IImsCallSession asInterface7 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile3 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldReceived(asInterface7, imsCallProfile3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IImsCallSession asInterface8 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile4 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumed(asInterface8, imsCallProfile4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IImsCallSession asInterface9 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo4 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeFailed(asInterface9, imsReasonInfo4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IImsCallSession asInterface10 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile5 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeReceived(asInterface10, imsCallProfile5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IImsCallSession asInterface11 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    IImsCallSession asInterface12 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile6 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeStarted(asInterface11, asInterface12, imsCallProfile6);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IImsCallSession asInterface13 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionMergeComplete(asInterface13);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IImsCallSession asInterface14 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo5 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeFailed(asInterface14, imsReasonInfo5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IImsCallSession asInterface15 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile7 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdated(asInterface15, imsCallProfile7);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IImsCallSession asInterface16 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo6 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateFailed(asInterface16, imsReasonInfo6);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IImsCallSession asInterface17 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile8 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateReceived(asInterface17, imsCallProfile8);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IImsCallSession asInterface18 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    IImsCallSession asInterface19 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile9 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtended(asInterface18, asInterface19, imsCallProfile9);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IImsCallSession asInterface20 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo7 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendFailed(asInterface20, imsReasonInfo7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IImsCallSession asInterface21 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    IImsCallSession asInterface22 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile10 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendReceived(asInterface21, asInterface22, imsCallProfile10);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IImsCallSession asInterface23 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionInviteParticipantsRequestDelivered(asInterface23);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IImsCallSession asInterface24 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo8 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInviteParticipantsRequestFailed(asInterface24, imsReasonInfo8);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IImsCallSession asInterface25 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionRemoveParticipantsRequestDelivered(asInterface25);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IImsCallSession asInterface26 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsReasonInfo imsReasonInfo9 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRemoveParticipantsRequestFailed(asInterface26, imsReasonInfo9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IImsCallSession asInterface27 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsConferenceState imsConferenceState = (ImsConferenceState) parcel.readTypedObject(ImsConferenceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceStateUpdated(asInterface27, imsConferenceState);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IImsCallSession asInterface28 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionUssdMessageReceived(asInterface28, readInt, readString);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IImsCallSession asInterface29 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo10 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandover(asInterface29, readInt2, readInt3, imsReasonInfo10);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IImsCallSession asInterface30 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo11 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandoverFailed(asInterface30, readInt4, readInt5, imsReasonInfo11);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    IImsCallSession asInterface31 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionMayHandover(asInterface31, readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    IImsCallSession asInterface32 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionTtyModeReceived(asInterface32, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    IImsCallSession asInterface33 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    callSessionMultipartyStateChanged(asInterface33, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IImsCallSession asInterface34 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsSuppServiceNotification imsSuppServiceNotification = (ImsSuppServiceNotification) parcel.readTypedObject(ImsSuppServiceNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionSuppServiceReceived(asInterface34, imsSuppServiceNotification);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IImsCallSession asInterface35 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile11 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyRequestReceived(asInterface35, imsCallProfile11);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyResponseReceived(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionRttMessageReceived(readString2);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    ImsStreamMediaProfile imsStreamMediaProfile2 = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttAudioIndicatorChanged(imsStreamMediaProfile2);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    callSessionTransferred();
                    parcel2.writeNoException();
                    return true;
                case 37:
                    ImsReasonInfo imsReasonInfo12 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTransferFailed(imsReasonInfo12);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    CallQuality callQuality = (CallQuality) parcel.readTypedObject(CallQuality.CREATOR);
                    parcel.enforceNoDataAvail();
                    callQualityChanged(callQuality);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionSendAnbrQuery(readInt10, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsCallSessionListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeStrongInterface(iImsCallSession2);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeStrongInterface(iImsCallSession2);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeStrongInterface(iImsCallSession2);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsConferenceState, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHandover(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionHandoverFailed(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMayHandover(IImsCallSession iImsCallSession, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsSuppServiceNotification, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttModifyResponseReceived(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttMessageReceived(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTransferred() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callQualityChanged(CallQuality callQuality) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(callQuality, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSessionListener
            public void callSessionSendAnbrQuery(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
