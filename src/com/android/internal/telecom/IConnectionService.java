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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IConnectionService)) {
                return (IConnectionService) iInterfaceQueryLocalInterface;
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
                    IConnectionServiceAdapter iConnectionServiceAdapterAsInterface = IConnectionServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    Session.Info info = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConnectionServiceAdapter(iConnectionServiceAdapterAsInterface, info);
                    return true;
                case 2:
                    IConnectionServiceAdapter iConnectionServiceAdapterAsInterface2 = IConnectionServiceAdapter.Stub.asInterface(parcel.readStrongBinder());
                    Session.Info info2 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeConnectionServiceAdapter(iConnectionServiceAdapterAsInterface2, info2);
                    return true;
                case 3:
                    PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string = parcel.readString();
                    ConnectionRequest connectionRequest = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean z = parcel.readBoolean();
                    boolean z2 = parcel.readBoolean();
                    Session.Info info3 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnection(phoneAccountHandle, string, connectionRequest, z, z2, info3);
                    return true;
                case 4:
                    String string2 = parcel.readString();
                    Session.Info info4 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnectionComplete(string2, info4);
                    return true;
                case 5:
                    PhoneAccountHandle phoneAccountHandle2 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string3 = parcel.readString();
                    ConnectionRequest connectionRequest2 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    Session.Info info5 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConnectionFailed(phoneAccountHandle2, string3, connectionRequest2, z3, info5);
                    return true;
                case 6:
                    PhoneAccountHandle phoneAccountHandle3 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string4 = parcel.readString();
                    ConnectionRequest connectionRequest3 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    Session.Info info6 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConference(phoneAccountHandle3, string4, connectionRequest3, z4, z5, info6);
                    return true;
                case 7:
                    String string5 = parcel.readString();
                    Session.Info info7 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConferenceComplete(string5, info7);
                    return true;
                case 8:
                    PhoneAccountHandle phoneAccountHandle4 = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    String string6 = parcel.readString();
                    ConnectionRequest connectionRequest4 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    boolean z6 = parcel.readBoolean();
                    Session.Info info8 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    createConferenceFailed(phoneAccountHandle4, string6, connectionRequest4, z6, info8);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    Session.Info info9 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    abort(string7, info9);
                    return true;
                case 10:
                    String string8 = parcel.readString();
                    int i3 = parcel.readInt();
                    Session.Info info10 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    answerVideo(string8, i3, info10);
                    return true;
                case 11:
                    String string9 = parcel.readString();
                    Session.Info info11 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    answer(string9, info11);
                    return true;
                case 12:
                    String string10 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    Session.Info info12 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deflect(string10, uri, info12);
                    return true;
                case 13:
                    String string11 = parcel.readString();
                    Session.Info info13 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    reject(string11, info13);
                    return true;
                case 14:
                    String string12 = parcel.readString();
                    int i4 = parcel.readInt();
                    Session.Info info14 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectWithReason(string12, i4, info14);
                    return true;
                case 15:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    Session.Info info15 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    rejectWithMessage(string13, string14, info15);
                    return true;
                case 16:
                    String string15 = parcel.readString();
                    Uri uri2 = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    Session.Info info16 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    transfer(string15, uri2, z7, info16);
                    return true;
                case 17:
                    String string16 = parcel.readString();
                    String string17 = parcel.readString();
                    Session.Info info17 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    consultativeTransfer(string16, string17, info17);
                    return true;
                case 18:
                    String string18 = parcel.readString();
                    Session.Info info18 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnect(string18, info18);
                    return true;
                case 19:
                    String string19 = parcel.readString();
                    Session.Info info19 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    silence(string19, info19);
                    return true;
                case 20:
                    String string20 = parcel.readString();
                    Session.Info info20 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    hold(string20, info20);
                    return true;
                case 21:
                    String string21 = parcel.readString();
                    Session.Info info21 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    unhold(string21, info21);
                    return true;
                case 22:
                    String string22 = parcel.readString();
                    CallAudioState callAudioState = (CallAudioState) parcel.readTypedObject(CallAudioState.CREATOR);
                    Session.Info info22 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallAudioStateChanged(string22, callAudioState, info22);
                    return true;
                case 23:
                    String string23 = parcel.readString();
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    Session.Info info23 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(string23, callEndpoint, info23);
                    return true;
                case 24:
                    String string24 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CallEndpoint.CREATOR);
                    Session.Info info24 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(string24, arrayListCreateTypedArrayList, info24);
                    return true;
                case 25:
                    String string25 = parcel.readString();
                    boolean z8 = parcel.readBoolean();
                    Session.Info info25 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(string25, z8, info25);
                    return true;
                case 26:
                    String string26 = parcel.readString();
                    char c = (char) parcel.readInt();
                    Session.Info info26 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    playDtmfTone(string26, c, info26);
                    return true;
                case 27:
                    String string27 = parcel.readString();
                    Session.Info info27 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopDtmfTone(string27, info27);
                    return true;
                case 28:
                    String string28 = parcel.readString();
                    String string29 = parcel.readString();
                    Session.Info info28 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    conference(string28, string29, info28);
                    return true;
                case 29:
                    String string30 = parcel.readString();
                    Session.Info info29 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    splitFromConference(string30, info29);
                    return true;
                case 30:
                    String string31 = parcel.readString();
                    Session.Info info30 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    mergeConference(string31, info30);
                    return true;
                case 31:
                    String string32 = parcel.readString();
                    Session.Info info31 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    swapConference(string32, info31);
                    return true;
                case 32:
                    String string33 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(Uri.CREATOR);
                    Session.Info info32 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceParticipants(string33, arrayListCreateTypedArrayList2, info32);
                    return true;
                case 33:
                    String string34 = parcel.readString();
                    boolean z9 = parcel.readBoolean();
                    Session.Info info33 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialContinue(string34, z9, info33);
                    return true;
                case 34:
                    String string35 = parcel.readString();
                    Session.Info info34 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    pullExternalCall(string35, info34);
                    return true;
                case 35:
                    String string36 = parcel.readString();
                    String string37 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info35 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendCallEvent(string36, string37, bundle, info35);
                    return true;
                case 36:
                    String string38 = parcel.readString();
                    Connection.CallFilteringCompletionInfo callFilteringCompletionInfo = (Connection.CallFilteringCompletionInfo) parcel.readTypedObject(Connection.CallFilteringCompletionInfo.CREATOR);
                    Session.Info info36 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallFilteringCompleted(string38, callFilteringCompletionInfo, info36);
                    return true;
                case 37:
                    String string39 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info37 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onExtrasChanged(string39, bundle2, info37);
                    return true;
                case 38:
                    String string40 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor2 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Session.Info info38 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    startRtt(string40, parcelFileDescriptor, parcelFileDescriptor2, info38);
                    return true;
                case 39:
                    String string41 = parcel.readString();
                    Session.Info info39 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    stopRtt(string41, info39);
                    return true;
                case 40:
                    String string42 = parcel.readString();
                    ParcelFileDescriptor parcelFileDescriptor3 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    ParcelFileDescriptor parcelFileDescriptor4 = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
                    Session.Info info40 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    respondToRttUpgradeRequest(string42, parcelFileDescriptor3, parcelFileDescriptor4, info40);
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
                    String string43 = parcel.readString();
                    ConnectionRequest connectionRequest5 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    int i5 = parcel.readInt();
                    Session.Info info43 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverFailed(string43, connectionRequest5, i5, info43);
                    return true;
                case 44:
                    String string44 = parcel.readString();
                    Session.Info info44 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handoverComplete(string44, info44);
                    return true;
                case 45:
                    String string45 = parcel.readString();
                    boolean z10 = parcel.readBoolean();
                    Session.Info info45 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onUsingAlternativeUi(string45, z10, info45);
                    return true;
                case 46:
                    String string46 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    Session.Info info46 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTrackedByNonUiService(string46, z11, info46);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iConnectionServiceAdapter);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iConnectionServiceAdapter);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnection(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(connectionRequest, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionComplete(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(connectionRequest, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConference(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, boolean z2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(connectionRequest, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConferenceComplete(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void createConferenceFailed(PhoneAccountHandle phoneAccountHandle, String str, ConnectionRequest connectionRequest, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(connectionRequest, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void abort(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answerVideo(String str, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void answer(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void deflect(String str, Uri uri, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void reject(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void rejectWithReason(String str, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void rejectWithMessage(String str, String str2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void transfer(String str, Uri uri, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void consultativeTransfer(String str, String str2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void disconnect(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void silence(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void hold(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void unhold(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallAudioStateChanged(String str, CallAudioState callAudioState, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(callAudioState, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallEndpointChanged(String str, CallEndpoint callEndpoint, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(callEndpoint, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onMuteStateChanged(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void playDtmfTone(String str, char c, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(c);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopDtmfTone(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void conference(String str, String str2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void splitFromConference(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void mergeConference(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void swapConference(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void addConferenceParticipants(String str, List<Uri> list, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onPostDialContinue(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void pullExternalCall(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(34, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void sendCallEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(35, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onCallFilteringCompleted(String str, Connection.CallFilteringCompletionInfo callFilteringCompletionInfo, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(callFilteringCompletionInfo, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onExtrasChanged(String str, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void startRtt(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(38, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void stopRtt(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void respondToRttUpgradeRequest(String str, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelFileDescriptor, 0);
                    parcelObtain.writeTypedObject(parcelFileDescriptor2, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(40, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusLost(Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(41, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void connectionServiceFocusGained(Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(42, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverFailed(String str, ConnectionRequest connectionRequest, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(connectionRequest, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(43, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void handoverComplete(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(44, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onUsingAlternativeUi(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(45, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionService
            public void onTrackedByNonUiService(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(46, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
