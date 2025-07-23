package com.android.internal.telecom;

import android.media.MediaMetrics;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.CallEndpoint;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.Logging.Session;
import android.telecom.PhoneAccountHandle;
import android.telephony.ims.ImsCallProfile;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telephony.SemRILConstants;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IConnectionService extends IInterface {

    public static class Default implements IConnectionService {
        @Override // com.android.internal.telecom.IConnectionService
        public void abort(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void addConferenceParticipants(String str, List<Uri> list, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answer(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void answerVideo(String str, int i, Session.Info info) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void conference(String str, String str2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusGained(Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void connectionServiceFocusLost(Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void consultativeTransfer(String str, String str2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConference(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceComplete(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConferenceFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnection(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionComplete(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void deflect(String str, Uri uri, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void disconnect(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverComplete(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void handoverFailed(String str, ConnectionRequest connectionRequest, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void hold(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void mergeConference(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallAudioStateChanged(String str, CallAudioState callAudioState, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallEndpointChanged(String str, CallEndpoint callEndpoint, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onCallFilteringCompleted(String str, Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onExtrasChanged(String str, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onMuteStateChanged(String str, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onPostDialContinue(String str, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onTrackedByNonUiService(String str, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void onUsingAlternativeUi(String str, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void playDtmfTone(String str, char c, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void pullExternalCall(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void reject(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithMessage(String str, String str2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void rejectWithReason(String str, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void respondToRttUpgradeRequest(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void sendCallEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void silence(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void splitFromConference(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void startRtt(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopDtmfTone(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void stopRtt(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void swapConference(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void transfer(String str, Uri uri, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionService
        public void unhold(String str, Session.Info info) throws RemoteException {
        }
    }

    void abort(String str, Session.Info info) throws RemoteException;

    void addConferenceParticipants(String str, List<Uri> list, Session.Info info) throws RemoteException;

    void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException;

    void answer(String str, Session.Info info) throws RemoteException;

    void answerVideo(String str, int i, Session.Info info) throws RemoteException;

    void conference(String str, String str2, Session.Info info) throws RemoteException;

    void connectionServiceFocusGained(Session.Info info) throws RemoteException;

    void connectionServiceFocusLost(Session.Info info) throws RemoteException;

    void consultativeTransfer(String str, String str2, Session.Info info) throws RemoteException;

    void createConference(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException;

    void createConferenceComplete(String str, Session.Info info) throws RemoteException;

    void createConferenceFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException;

    void createConnection(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException;

    void createConnectionComplete(String str, Session.Info info) throws RemoteException;

    void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException;

    void deflect(String str, Uri uri, Session.Info info) throws RemoteException;

    void disconnect(String str, Session.Info info) throws RemoteException;

    void handoverComplete(String str, Session.Info info) throws RemoteException;

    void handoverFailed(String str, ConnectionRequest connectionRequest, int i, Session.Info info) throws RemoteException;

    void hold(String str, Session.Info info) throws RemoteException;

    void mergeConference(String str, Session.Info info) throws RemoteException;

    void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list, Session.Info info) throws RemoteException;

    void onCallAudioStateChanged(String str, CallAudioState callAudioState, Session.Info info) throws RemoteException;

    void onCallEndpointChanged(String str, CallEndpoint callEndpoint, Session.Info info) throws RemoteException;

    void onCallFilteringCompleted(String str, Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, Session.Info info) throws RemoteException;

    void onExtrasChanged(String str, Bundle bundle, Session.Info info) throws RemoteException;

    void onMuteStateChanged(String str, boolean z, Session.Info info) throws RemoteException;

    void onPostDialContinue(String str, boolean z, Session.Info info) throws RemoteException;

    void onTrackedByNonUiService(String str, boolean z, Session.Info info) throws RemoteException;

    void onUsingAlternativeUi(String str, boolean z, Session.Info info) throws RemoteException;

    void playDtmfTone(String str, char c, Session.Info info) throws RemoteException;

    void pullExternalCall(String str, Session.Info info) throws RemoteException;

    void reject(String str, Session.Info info) throws RemoteException;

    void rejectWithMessage(String str, String str2, Session.Info info) throws RemoteException;

    void rejectWithReason(String str, int i, Session.Info info) throws RemoteException;

    void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException;

    void respondToRttUpgradeRequest(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException;

    void sendCallEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException;

    void silence(String str, Session.Info info) throws RemoteException;

    void splitFromConference(String str, Session.Info info) throws RemoteException;

    void startRtt(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException;

    void stopDtmfTone(String str, Session.Info info) throws RemoteException;

    void stopRtt(String str, Session.Info info) throws RemoteException;

    void swapConference(String str, Session.Info info) throws RemoteException;

    void transfer(String str, Uri uri, boolean z, Session.Info info) throws RemoteException;

    void unhold(String str, Session.Info info) throws RemoteException;

    public static abstract class Stub extends Binder implements IConnectionService {
        public static final String DESCRIPTOR = "com.android.internal.telecom.IConnectionService";
        static final int TRANSACTION_abort = 9;
        static final int TRANSACTION_addConferenceParticipants = 32;
        static final int TRANSACTION_addConnectionServiceAdapter = 1;
        static final int TRANSACTION_answer = 11;
        static final int TRANSACTION_answerVideo = 10;
        static final int TRANSACTION_conference = 28;
        static final int TRANSACTION_connectionServiceFocusGained = 42;
        static final int TRANSACTION_connectionServiceFocusLost = 41;
        static final int TRANSACTION_consultativeTransfer = 17;
        static final int TRANSACTION_createConference = 6;
        static final int TRANSACTION_createConferenceComplete = 7;
        static final int TRANSACTION_createConferenceFailed = 8;
        static final int TRANSACTION_createConnection = 3;
        static final int TRANSACTION_createConnectionComplete = 4;
        static final int TRANSACTION_createConnectionFailed = 5;
        static final int TRANSACTION_deflect = 12;
        static final int TRANSACTION_disconnect = 18;
        static final int TRANSACTION_handoverComplete = 44;
        static final int TRANSACTION_handoverFailed = 43;
        static final int TRANSACTION_hold = 20;
        static final int TRANSACTION_mergeConference = 30;
        static final int TRANSACTION_onAvailableCallEndpointsChanged = 24;
        static final int TRANSACTION_onCallAudioStateChanged = 22;
        static final int TRANSACTION_onCallEndpointChanged = 23;
        static final int TRANSACTION_onCallFilteringCompleted = 36;
        static final int TRANSACTION_onExtrasChanged = 37;
        static final int TRANSACTION_onMuteStateChanged = 25;
        static final int TRANSACTION_onPostDialContinue = 33;
        static final int TRANSACTION_onTrackedByNonUiService = 46;
        static final int TRANSACTION_onUsingAlternativeUi = 45;
        static final int TRANSACTION_playDtmfTone = 26;
        static final int TRANSACTION_pullExternalCall = 34;
        static final int TRANSACTION_reject = 13;
        static final int TRANSACTION_rejectWithMessage = 15;
        static final int TRANSACTION_rejectWithReason = 14;
        static final int TRANSACTION_removeConnectionServiceAdapter = 2;
        static final int TRANSACTION_respondToRttUpgradeRequest = 40;
        static final int TRANSACTION_sendCallEvent = 35;
        static final int TRANSACTION_silence = 19;
        static final int TRANSACTION_splitFromConference = 29;
        static final int TRANSACTION_startRtt = 38;
        static final int TRANSACTION_stopDtmfTone = 27;
        static final int TRANSACTION_stopRtt = 39;
        static final int TRANSACTION_swapConference = 31;
        static final int TRANSACTION_transfer = 16;
        static final int TRANSACTION_unhold = 21;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IConnectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IConnectionService)) {
                return (IConnectionService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addConnectionServiceAdapter";
                case 2:
                    return "removeConnectionServiceAdapter";
                case 3:
                    return "createConnection";
                case 4:
                    return "createConnectionComplete";
                case 5:
                    return "createConnectionFailed";
                case 6:
                    return "createConference";
                case 7:
                    return "createConferenceComplete";
                case 8:
                    return "createConferenceFailed";
                case 9:
                    return "abort";
                case 10:
                    return "answerVideo";
                case 11:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER;
                case 12:
                    return "deflect";
                case 13:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_REJECT;
                case 14:
                    return "rejectWithReason";
                case 15:
                    return "rejectWithMessage";
                case 16:
                    return "transfer";
                case 17:
                    return "consultativeTransfer";
                case 18:
                    return MediaMetrics.Value.DISCONNECT;
                case 19:
                    return "silence";
                case 20:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_HOLD;
                case 21:
                    return "unhold";
                case 22:
                    return "onCallAudioStateChanged";
                case 23:
                    return "onCallEndpointChanged";
                case 24:
                    return "onAvailableCallEndpointsChanged";
                case 25:
                    return "onMuteStateChanged";
                case 26:
                    return "playDtmfTone";
                case 27:
                    return "stopDtmfTone";
                case 28:
                    return ImsCallProfile.EXTRA_CONFERENCE_DEPRECATED;
                case 29:
                    return "splitFromConference";
                case 30:
                    return "mergeConference";
                case 31:
                    return "swapConference";
                case 32:
                    return "addConferenceParticipants";
                case 33:
                    return "onPostDialContinue";
                case 34:
                    return "pullExternalCall";
                case 35:
                    return "sendCallEvent";
                case 36:
                    return "onCallFilteringCompleted";
                case 37:
                    return "onExtrasChanged";
                case 38:
                    return "startRtt";
                case 39:
                    return "stopRtt";
                case 40:
                    return "respondToRttUpgradeRequest";
                case 41:
                    return "connectionServiceFocusLost";
                case 42:
                    return "connectionServiceFocusGained";
                case 43:
                    return "handoverFailed";
                case 44:
                    return "handoverComplete";
                case 45:
                    return "onUsingAlternativeUi";
                case 46:
                    return "onTrackedByNonUiService";
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
                    IConnectionServiceAdapter asInterface = IConnectionServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    Session.Info info = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConnectionServiceAdapter(asInterface, info);
                    return true;
                case 2:
                    IConnectionServiceAdapter asInterface2 = IConnectionServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    Session.Info info2 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeConnectionServiceAdapter(asInterface2, info2);
                    return true;
                case 3:
                    PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString = parcel.readString();
                    ConnectionRequest connectionRequest = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    Session.Info info3 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnection(phoneAccountHandle, readString, connectionRequest, readBoolean, readBoolean2, info3);
                    return true;
                case 4:
                    String readString2 = parcel.readString();
                    Session.Info info4 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnectionComplete(readString2, info4);
                    return true;
                case 5:
                    PhoneAccountHandle phoneAccountHandle2 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString3 = parcel.readString();
                    ConnectionRequest connectionRequest2 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    Session.Info info5 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnectionFailed(phoneAccountHandle2, readString3, connectionRequest2, readBoolean3, info5);
                    return true;
                case 6:
                    PhoneAccountHandle phoneAccountHandle3 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString4 = parcel.readString();
                    ConnectionRequest connectionRequest3 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    Session.Info info6 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConference(phoneAccountHandle3, readString4, connectionRequest3, readBoolean4, readBoolean5, info6);
                    return true;
                case 7:
                    String readString5 = parcel.readString();
                    Session.Info info7 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConferenceComplete(readString5, info7);
                    return true;
                case 8:
                    PhoneAccountHandle phoneAccountHandle4 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String readString6 = parcel.readString();
                    ConnectionRequest connectionRequest4 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    Session.Info info8 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConferenceFailed(phoneAccountHandle4, readString6, connectionRequest4, readBoolean6, info8);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    Session.Info info9 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    abort(readString7, info9);
                    return true;
                case 10:
                    String readString8 = parcel.readString();
                    int readInt = parcel.readInt();
                    Session.Info info10 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    answerVideo(readString8, readInt, info10);
                    return true;
                case 11:
                    String readString9 = parcel.readString();
                    Session.Info info11 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    answer(readString9, info11);
                    return true;
                case 12:
                    String readString10 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Session.Info info12 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deflect(readString10, uri, info12);
                    return true;
                case 13:
                    String readString11 = parcel.readString();
                    Session.Info info13 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    reject(readString11, info13);
                    return true;
                case 14:
                    String readString12 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    Session.Info info14 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectWithReason(readString12, readInt2, info14);
                    return true;
                case 15:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    Session.Info info15 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectWithMessage(readString13, readString14, info15);
                    return true;
                case 16:
                    String readString15 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    Session.Info info16 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    transfer(readString15, uri2, readBoolean7, info16);
                    return true;
                case 17:
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    Session.Info info17 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    consultativeTransfer(readString16, readString17, info17);
                    return true;
                case 18:
                    String readString18 = parcel.readString();
                    Session.Info info18 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnect(readString18, info18);
                    return true;
                case 19:
                    String readString19 = parcel.readString();
                    Session.Info info19 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    silence(readString19, info19);
                    return true;
                case 20:
                    String readString20 = parcel.readString();
                    Session.Info info20 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    hold(readString20, info20);
                    return true;
                case 21:
                    String readString21 = parcel.readString();
                    Session.Info info21 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    unhold(readString21, info21);
                    return true;
                case 22:
                    String readString22 = parcel.readString();
                    CallAudioState callAudioState = (CallAudioState) parcel.readTypedObject(CallAudioState.CREATOR);
                    Session.Info info22 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallAudioStateChanged(readString22, callAudioState, info22);
                    return true;
                case 23:
                    String readString23 = parcel.readString();
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    Session.Info info23 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(readString23, callEndpoint, info23);
                    return true;
                case 24:
                    String readString24 = parcel.readString();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(CallEndpoint.CREATOR);
                    Session.Info info24 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(readString24, createTypedArrayList, info24);
                    return true;
                case 25:
                    String readString25 = parcel.readString();
                    boolean readBoolean8 = parcel.readBoolean();
                    Session.Info info25 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(readString25, readBoolean8, info25);
                    return true;
                case 26:
                    String readString26 = parcel.readString();
                    char readInt3 = (char) parcel.readInt();
                    Session.Info info26 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    playDtmfTone(readString26, readInt3, info26);
                    return true;
                case 27:
                    String readString27 = parcel.readString();
                    Session.Info info27 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopDtmfTone(readString27, info27);
                    return true;
                case 28:
                    String readString28 = parcel.readString();
                    String readString29 = parcel.readString();
                    Session.Info info28 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    conference(readString28, readString29, info28);
                    return true;
                case 29:
                    String readString30 = parcel.readString();
                    Session.Info info29 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    splitFromConference(readString30, info29);
                    return true;
                case 30:
                    String readString31 = parcel.readString();
                    Session.Info info30 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    mergeConference(readString31, info30);
                    return true;
                case 31:
                    String readString32 = parcel.readString();
                    Session.Info info31 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    swapConference(readString32, info31);
                    return true;
                case 32:
                    String readString33 = parcel.readString();
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(Uri.CREATOR);
                    Session.Info info32 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceParticipants(readString33, createTypedArrayList2, info32);
                    return true;
                case 33:
                    String readString34 = parcel.readString();
                    boolean readBoolean9 = parcel.readBoolean();
                    Session.Info info33 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialContinue(readString34, readBoolean9, info33);
                    return true;
                case 34:
                    String readString35 = parcel.readString();
                    Session.Info info34 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    pullExternalCall(readString35, info34);
                    return true;
                case 35:
                    String readString36 = parcel.readString();
                    String readString37 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info35 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCallEvent(readString36, readString37, bundle, info35);
                    return true;
                case 36:
                    String readString38 = parcel.readString();
                    Connection.CallFilteringCompletionInfo callFilteringCompletionInfo = (Connection.CallFilteringCompletionInfo) parcel.readTypedObject(Connection.CallFilteringCompletionInfo.CREATOR);
                    Session.Info info36 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallFilteringCompleted(readString38, callFilteringCompletionInfo, info36);
                    return true;
                case 37:
                    String readString39 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info37 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onExtrasChanged(readString39, bundle2, info37);
                    return true;
                case 38:
                    String readString40 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Session.Info info38 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRtt(readString40, parcelFileDescriptor, parcelFileDescriptor2, info38);
                    return true;
                case 39:
                    String readString41 = parcel.readString();
                    Session.Info info39 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopRtt(readString41, info39);
                    return true;
                case 40:
                    String readString42 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Session.Info info40 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    respondToRttUpgradeRequest(readString42, parcelFileDescriptor3, parcelFileDescriptor4, info40);
                    return true;
                case 41:
                    Session.Info info41 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectionServiceFocusLost(info41);
                    return true;
                case 42:
                    Session.Info info42 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    connectionServiceFocusGained(info42);
                    return true;
                case 43:
                    String readString43 = parcel.readString();
                    ConnectionRequest connectionRequest5 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    int readInt4 = parcel.readInt();
                    Session.Info info43 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverFailed(readString43, connectionRequest5, readInt4, info43);
                    return true;
                case 44:
                    String readString44 = parcel.readString();
                    Session.Info info44 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverComplete(readString44, info44);
                    return true;
                case 45:
                    String readString45 = parcel.readString();
                    boolean readBoolean10 = parcel.readBoolean();
                    Session.Info info45 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUsingAlternativeUi(readString45, readBoolean10, info45);
                    return true;
                case 46:
                    String readString46 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    Session.Info info46 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTrackedByNonUiService(readString46, readBoolean11, info46);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IConnectionService {
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

            @Override // com.android.internal.telecom.IConnectionService
            public void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iConnectionServiceAdapter);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iConnectionServiceAdapter);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnection(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionComplete(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConference(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConferenceComplete(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConferenceFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void abort(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answerVideo(String str, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answer(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void deflect(String str, Uri uri, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void reject(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void rejectWithReason(String str, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void rejectWithMessage(String str, String str2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void transfer(String str, Uri uri, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void consultativeTransfer(String str, String str2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void disconnect(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void silence(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void hold(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void unhold(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallAudioStateChanged(String str, CallAudioState callAudioState, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callAudioState, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallEndpointChanged(String str, CallEndpoint callEndpoint, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onMuteStateChanged(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void playDtmfTone(String str, char c, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopDtmfTone(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void conference(String str, String str2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void splitFromConference(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void mergeConference(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void swapConference(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void addConferenceParticipants(String str, List<Uri> list, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onPostDialContinue(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void pullExternalCall(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void sendCallEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallFilteringCompleted(String str, Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callFilteringCompletionInfo, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onExtrasChanged(String str, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void startRtt(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopRtt(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void respondToRttUpgradeRequest(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeTypedObject(parcelFileDescriptor2, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusLost(Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusGained(Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverFailed(String str, ConnectionRequest connectionRequest, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverComplete(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onUsingAlternativeUi(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onTrackedByNonUiService(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
