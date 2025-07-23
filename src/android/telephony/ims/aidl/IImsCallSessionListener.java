package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.Bundle;
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
import android.telephony.ims.RtpHeaderExtension;
import com.android.ims.internal.IImsCallSession;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IImsCallSessionListener extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsCallSessionListener";

    public static class Default implements IImsCallSessionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionCancelTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionCancelTransferred() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionDtmfReceived(char c) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandover(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandoverFailed(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHeld(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionImsCallEvent(String str, Bundle bundle) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiated(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiating(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatingFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMayHandover(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumed(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRtpHeaderExtensionsReceived(List<RtpHeaderExtension> list) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttMessageReceived(String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSendAnbrQuery(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTerminated(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferred() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTtyModeReceived(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdated(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUssdMessageReceived(int i, String str) throws RemoteException {
        }
    }

    void callQualityChanged(CallQuality callQuality) throws RemoteException;

    void callSessionCancelTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionCancelTransferred() throws RemoteException;

    void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) throws RemoteException;

    void callSessionDtmfReceived(char c) throws RemoteException;

    void callSessionHandover(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHandoverFailed(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHeld(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionHoldReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionImsCallEvent(String str, Bundle bundle) throws RemoteException;

    void callSessionInitiated(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionInitiating(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionInitiatingFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionInviteParticipantsRequestDelivered() throws RemoteException;

    void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionMayHandover(int i, int i2) throws RemoteException;

    void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException;

    void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionMultipartyStateChanged(boolean z) throws RemoteException;

    void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRemoveParticipantsRequestDelivered() throws RemoteException;

    void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionResumeReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionResumed(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRtpHeaderExtensionsReceived(List<RtpHeaderExtension> list) throws RemoteException;

    void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionRttMessageReceived(String str) throws RemoteException;

    void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionRttModifyResponseReceived(int i) throws RemoteException;

    void callSessionSendAnbrQuery(int i, int i2, int i3) throws RemoteException;

    void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException;

    void callSessionTerminated(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionTransferred() throws RemoteException;

    void callSessionTtyModeReceived(int i) throws RemoteException;

    void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) throws RemoteException;

    void callSessionUpdateReceived(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionUpdated(ImsCallProfile imsCallProfile) throws RemoteException;

    void callSessionUssdMessageReceived(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsCallSessionListener {
        static final int TRANSACTION_callQualityChanged = 44;
        static final int TRANSACTION_callSessionCancelTransferFailed = 41;
        static final int TRANSACTION_callSessionCancelTransferred = 40;
        static final int TRANSACTION_callSessionConferenceExtendFailed = 20;
        static final int TRANSACTION_callSessionConferenceExtendReceived = 21;
        static final int TRANSACTION_callSessionConferenceExtended = 19;
        static final int TRANSACTION_callSessionConferenceStateUpdated = 26;
        static final int TRANSACTION_callSessionDtmfReceived = 43;
        static final int TRANSACTION_callSessionHandover = 28;
        static final int TRANSACTION_callSessionHandoverFailed = 29;
        static final int TRANSACTION_callSessionHeld = 7;
        static final int TRANSACTION_callSessionHoldFailed = 8;
        static final int TRANSACTION_callSessionHoldReceived = 9;
        static final int TRANSACTION_callSessionImsCallEvent = 42;
        static final int TRANSACTION_callSessionInitiated = 4;
        static final int TRANSACTION_callSessionInitiatedFailed = 5;
        static final int TRANSACTION_callSessionInitiating = 1;
        static final int TRANSACTION_callSessionInitiatingFailed = 2;
        static final int TRANSACTION_callSessionInviteParticipantsRequestDelivered = 22;
        static final int TRANSACTION_callSessionInviteParticipantsRequestFailed = 23;
        static final int TRANSACTION_callSessionMayHandover = 30;
        static final int TRANSACTION_callSessionMergeComplete = 14;
        static final int TRANSACTION_callSessionMergeFailed = 15;
        static final int TRANSACTION_callSessionMergeStarted = 13;
        static final int TRANSACTION_callSessionMultipartyStateChanged = 32;
        static final int TRANSACTION_callSessionProgressing = 3;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestDelivered = 24;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestFailed = 25;
        static final int TRANSACTION_callSessionResumeFailed = 11;
        static final int TRANSACTION_callSessionResumeReceived = 12;
        static final int TRANSACTION_callSessionResumed = 10;
        static final int TRANSACTION_callSessionRtpHeaderExtensionsReceived = 45;
        static final int TRANSACTION_callSessionRttAudioIndicatorChanged = 37;
        static final int TRANSACTION_callSessionRttMessageReceived = 36;
        static final int TRANSACTION_callSessionRttModifyRequestReceived = 34;
        static final int TRANSACTION_callSessionRttModifyResponseReceived = 35;
        static final int TRANSACTION_callSessionSendAnbrQuery = 46;
        static final int TRANSACTION_callSessionSuppServiceReceived = 33;
        static final int TRANSACTION_callSessionTerminated = 6;
        static final int TRANSACTION_callSessionTransferFailed = 39;
        static final int TRANSACTION_callSessionTransferred = 38;
        static final int TRANSACTION_callSessionTtyModeReceived = 31;
        static final int TRANSACTION_callSessionUpdateFailed = 17;
        static final int TRANSACTION_callSessionUpdateReceived = 18;
        static final int TRANSACTION_callSessionUpdated = 16;
        static final int TRANSACTION_callSessionUssdMessageReceived = 27;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }

        public Stub() {
            attachInterface(this, IImsCallSessionListener.DESCRIPTOR);
        }

        public static IImsCallSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsCallSessionListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsCallSessionListener)) {
                return (IImsCallSessionListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "callSessionInitiating";
                case 2:
                    return "callSessionInitiatingFailed";
                case 3:
                    return "callSessionProgressing";
                case 4:
                    return "callSessionInitiated";
                case 5:
                    return "callSessionInitiatedFailed";
                case 6:
                    return "callSessionTerminated";
                case 7:
                    return "callSessionHeld";
                case 8:
                    return "callSessionHoldFailed";
                case 9:
                    return "callSessionHoldReceived";
                case 10:
                    return "callSessionResumed";
                case 11:
                    return "callSessionResumeFailed";
                case 12:
                    return "callSessionResumeReceived";
                case 13:
                    return "callSessionMergeStarted";
                case 14:
                    return "callSessionMergeComplete";
                case 15:
                    return "callSessionMergeFailed";
                case 16:
                    return "callSessionUpdated";
                case 17:
                    return "callSessionUpdateFailed";
                case 18:
                    return "callSessionUpdateReceived";
                case 19:
                    return "callSessionConferenceExtended";
                case 20:
                    return "callSessionConferenceExtendFailed";
                case 21:
                    return "callSessionConferenceExtendReceived";
                case 22:
                    return "callSessionInviteParticipantsRequestDelivered";
                case 23:
                    return "callSessionInviteParticipantsRequestFailed";
                case 24:
                    return "callSessionRemoveParticipantsRequestDelivered";
                case 25:
                    return "callSessionRemoveParticipantsRequestFailed";
                case 26:
                    return "callSessionConferenceStateUpdated";
                case 27:
                    return "callSessionUssdMessageReceived";
                case 28:
                    return "callSessionHandover";
                case 29:
                    return "callSessionHandoverFailed";
                case 30:
                    return "callSessionMayHandover";
                case 31:
                    return "callSessionTtyModeReceived";
                case 32:
                    return "callSessionMultipartyStateChanged";
                case 33:
                    return "callSessionSuppServiceReceived";
                case 34:
                    return "callSessionRttModifyRequestReceived";
                case 35:
                    return "callSessionRttModifyResponseReceived";
                case 36:
                    return "callSessionRttMessageReceived";
                case 37:
                    return "callSessionRttAudioIndicatorChanged";
                case 38:
                    return "callSessionTransferred";
                case 39:
                    return "callSessionTransferFailed";
                case 40:
                    return "callSessionCancelTransferred";
                case 41:
                    return "callSessionCancelTransferFailed";
                case 42:
                    return "callSessionImsCallEvent";
                case 43:
                    return "callSessionDtmfReceived";
                case 44:
                    return "callQualityChanged";
                case 45:
                    return "callSessionRtpHeaderExtensionsReceived";
                case 46:
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
                parcel.enforceInterface(IImsCallSessionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsCallSessionListener.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiating(imsCallProfile);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ImsReasonInfo imsReasonInfo = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiatingFailed(imsReasonInfo);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ImsStreamMediaProfile imsStreamMediaProfile = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionProgressing(imsStreamMediaProfile);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ImsCallProfile imsCallProfile2 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiated(imsCallProfile2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ImsReasonInfo imsReasonInfo2 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInitiatedFailed(imsReasonInfo2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    ImsReasonInfo imsReasonInfo3 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTerminated(imsReasonInfo3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    ImsCallProfile imsCallProfile3 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHeld(imsCallProfile3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    ImsReasonInfo imsReasonInfo4 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldFailed(imsReasonInfo4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ImsCallProfile imsCallProfile4 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHoldReceived(imsCallProfile4);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ImsCallProfile imsCallProfile5 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumed(imsCallProfile5);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    ImsReasonInfo imsReasonInfo5 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeFailed(imsReasonInfo5);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    ImsCallProfile imsCallProfile6 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionResumeReceived(imsCallProfile6);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IImsCallSession asInterface = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile7 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeStarted(asInterface, imsCallProfile7);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IImsCallSession asInterface2 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    callSessionMergeComplete(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    ImsReasonInfo imsReasonInfo6 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionMergeFailed(imsReasonInfo6);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ImsCallProfile imsCallProfile8 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdated(imsCallProfile8);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ImsReasonInfo imsReasonInfo7 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateFailed(imsReasonInfo7);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ImsCallProfile imsCallProfile9 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionUpdateReceived(imsCallProfile9);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IImsCallSession asInterface3 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile10 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtended(asInterface3, imsCallProfile10);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    ImsReasonInfo imsReasonInfo8 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendFailed(imsReasonInfo8);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IImsCallSession asInterface4 = IImsCallSession.Stub.asInterface(parcel.readStrongBinder());
                    ImsCallProfile imsCallProfile11 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceExtendReceived(asInterface4, imsCallProfile11);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    callSessionInviteParticipantsRequestDelivered();
                    parcel2.writeNoException();
                    return true;
                case 23:
                    ImsReasonInfo imsReasonInfo9 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionInviteParticipantsRequestFailed(imsReasonInfo9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    callSessionRemoveParticipantsRequestDelivered();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ImsReasonInfo imsReasonInfo10 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRemoveParticipantsRequestFailed(imsReasonInfo10);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    ImsConferenceState imsConferenceState = (ImsConferenceState) parcel.readTypedObject(ImsConferenceState.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionConferenceStateUpdated(imsConferenceState);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionUssdMessageReceived(readInt, readString);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo11 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandover(readInt2, readInt3, imsReasonInfo11);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    ImsReasonInfo imsReasonInfo12 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionHandoverFailed(readInt4, readInt5, imsReasonInfo12);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionMayHandover(readInt6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionTtyModeReceived(readInt8);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    callSessionMultipartyStateChanged(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    ImsSuppServiceNotification imsSuppServiceNotification = (ImsSuppServiceNotification) parcel.readTypedObject(ImsSuppServiceNotification.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionSuppServiceReceived(imsSuppServiceNotification);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    ImsCallProfile imsCallProfile12 = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyRequestReceived(imsCallProfile12);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionRttModifyResponseReceived(readInt9);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    callSessionRttMessageReceived(readString2);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    ImsStreamMediaProfile imsStreamMediaProfile2 = (ImsStreamMediaProfile) parcel.readTypedObject(ImsStreamMediaProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRttAudioIndicatorChanged(imsStreamMediaProfile2);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    callSessionTransferred();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    ImsReasonInfo imsReasonInfo13 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionTransferFailed(imsReasonInfo13);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    callSessionCancelTransferred();
                    parcel2.writeNoException();
                    return true;
                case 41:
                    ImsReasonInfo imsReasonInfo14 = (ImsReasonInfo) parcel.readTypedObject(ImsReasonInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionCancelTransferFailed(imsReasonInfo14);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String readString3 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionImsCallEvent(readString3, bundle);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    char readInt10 = (char) parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionDtmfReceived(readInt10);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    CallQuality callQuality = (CallQuality) parcel.readTypedObject(CallQuality.CREATOR);
                    parcel.enforceNoDataAvail();
                    callQualityChanged(callQuality);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(RtpHeaderExtension.CREATOR);
                    parcel.enforceNoDataAvail();
                    callSessionRtpHeaderExtensionsReceived(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    callSessionSendAnbrQuery(readInt11, readInt12, readInt13);
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
                return IImsCallSessionListener.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiating(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiatingFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiated(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTerminated(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHeld(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHoldReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionResumed(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionResumeReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUpdated(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUpdateReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallSession);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsConferenceState, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionUssdMessageReceived(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHandover(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionHandoverFailed(int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMayHandover(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTtyModeReceived(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionMultipartyStateChanged(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsSuppServiceNotification, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttModifyResponseReceived(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttMessageReceived(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsStreamMediaProfile, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTransferred() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionCancelTransferred() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionCancelTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(imsReasonInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionImsCallEvent(String str, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionDtmfReceived(char c) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(c);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callQualityChanged(CallQuality callQuality) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedObject(callQuality, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionRtpHeaderExtensionsReceived(List<RtpHeaderExtension> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsCallSessionListener
            public void callSessionSendAnbrQuery(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsCallSessionListener.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
