package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telecom.CallEndpoint;
import android.telecom.ConnectionRequest;
import android.telecom.DisconnectCause;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConference;
import android.telecom.ParcelableConnection;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IConnectionServiceAdapter extends IInterface {

    public static class Default implements IConnectionServiceAdapter {
        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void addConferenceCall(String str, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void addExistingConnection(String str, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConferenceComplete(String str, ConnectionRequest connectionRequest, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void handleCreateConnectionComplete(String str, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onConnectionEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onConnectionServiceFocusReleased(Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPhoneAccountChanged(String str, PhoneAccountHandle phoneAccountHandle, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialChar(String str, char c, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onPostDialWait(String str, String str2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRemoteRttRequest(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationFailure(String str, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttInitiationSuccess(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void onRttSessionRemotelyTerminated(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void putExtras(String str, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryLocation(String str, long j, String str2, ResultReceiver resultReceiver, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void removeCall(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void removeExtras(String str, List<String> list, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void requestCallEndpointChange(String str, CallEndpoint callEndpoint, ResultReceiver resultReceiver, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void resetConnectionTime(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setActive(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setAddress(String str, Uri uri, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setAudioRoute(String str, int i, String str2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setCallDirection(String str, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setCallerDisplayName(String str, String str2, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceMergeFailed(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceState(String str, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConferenceableConnections(String str, List<String> list, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionCapabilities(String str, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setConnectionProperties(String str, int i, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDialing(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setDisconnected(String str, DisconnectCause disconnectCause, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setIsConferenced(String str, String str2, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setIsVoipAudioMode(String str, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setOnHold(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setPulling(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRingbackRequested(String str, boolean z, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setRinging(String str, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setStatusHints(String str, StatusHints statusHints, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoProvider(String str, IVideoProvider iVideoProvider, Session.Info info) throws RemoteException {
        }

        @Override // com.android.internal.telecom.IConnectionServiceAdapter
        public void setVideoState(String str, int i, Session.Info info) throws RemoteException {
        }
    }

    void addConferenceCall(String str, ParcelableConference parcelableConference, Session.Info info) throws RemoteException;

    void addExistingConnection(String str, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException;

    void handleCreateConferenceComplete(String str, ConnectionRequest connectionRequest, ParcelableConference parcelableConference, Session.Info info) throws RemoteException;

    void handleCreateConnectionComplete(String str, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException;

    void onConnectionEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException;

    void onConnectionServiceFocusReleased(Session.Info info) throws RemoteException;

    void onPhoneAccountChanged(String str, PhoneAccountHandle phoneAccountHandle, Session.Info info) throws RemoteException;

    void onPostDialChar(String str, char c, Session.Info info) throws RemoteException;

    void onPostDialWait(String str, String str2, Session.Info info) throws RemoteException;

    void onRemoteRttRequest(String str, Session.Info info) throws RemoteException;

    void onRttInitiationFailure(String str, int i, Session.Info info) throws RemoteException;

    void onRttInitiationSuccess(String str, Session.Info info) throws RemoteException;

    void onRttSessionRemotelyTerminated(String str, Session.Info info) throws RemoteException;

    void putExtras(String str, Bundle bundle, Session.Info info) throws RemoteException;

    void queryLocation(String str, long j, String str2, ResultReceiver resultReceiver, Session.Info info) throws RemoteException;

    void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String str, Session.Info info) throws RemoteException;

    void removeCall(String str, Session.Info info) throws RemoteException;

    void removeExtras(String str, List<String> list, Session.Info info) throws RemoteException;

    void requestCallEndpointChange(String str, CallEndpoint callEndpoint, ResultReceiver resultReceiver, Session.Info info) throws RemoteException;

    void resetConnectionTime(String str, Session.Info info) throws RemoteException;

    void setActive(String str, Session.Info info) throws RemoteException;

    void setAddress(String str, Uri uri, int i, Session.Info info) throws RemoteException;

    void setAudioRoute(String str, int i, String str2, Session.Info info) throws RemoteException;

    void setCallDirection(String str, int i, Session.Info info) throws RemoteException;

    void setCallerDisplayName(String str, String str2, int i, Session.Info info) throws RemoteException;

    void setConferenceMergeFailed(String str, Session.Info info) throws RemoteException;

    void setConferenceState(String str, boolean z, Session.Info info) throws RemoteException;

    void setConferenceableConnections(String str, List<String> list, Session.Info info) throws RemoteException;

    void setConnectionCapabilities(String str, int i, Session.Info info) throws RemoteException;

    void setConnectionProperties(String str, int i, Session.Info info) throws RemoteException;

    void setDialing(String str, Session.Info info) throws RemoteException;

    void setDisconnected(String str, DisconnectCause disconnectCause, Session.Info info) throws RemoteException;

    void setIsConferenced(String str, String str2, Session.Info info) throws RemoteException;

    void setIsVoipAudioMode(String str, boolean z, Session.Info info) throws RemoteException;

    void setOnHold(String str, Session.Info info) throws RemoteException;

    void setPulling(String str, Session.Info info) throws RemoteException;

    void setRingbackRequested(String str, boolean z, Session.Info info) throws RemoteException;

    void setRinging(String str, Session.Info info) throws RemoteException;

    void setStatusHints(String str, StatusHints statusHints, Session.Info info) throws RemoteException;

    void setVideoProvider(String str, IVideoProvider iVideoProvider, Session.Info info) throws RemoteException;

    void setVideoState(String str, int i, Session.Info info) throws RemoteException;

    public static abstract class Stub extends Binder implements IConnectionServiceAdapter {
        public static final String DESCRIPTOR = "com.android.internal.telecom.IConnectionServiceAdapter";
        static final int TRANSACTION_addConferenceCall = 14;
        static final int TRANSACTION_addExistingConnection = 26;
        static final int TRANSACTION_handleCreateConferenceComplete = 2;
        static final int TRANSACTION_handleCreateConnectionComplete = 1;
        static final int TRANSACTION_onConnectionEvent = 31;
        static final int TRANSACTION_onConnectionServiceFocusReleased = 37;
        static final int TRANSACTION_onPhoneAccountChanged = 36;
        static final int TRANSACTION_onPostDialChar = 17;
        static final int TRANSACTION_onPostDialWait = 16;
        static final int TRANSACTION_onRemoteRttRequest = 35;
        static final int TRANSACTION_onRttInitiationFailure = 33;
        static final int TRANSACTION_onRttInitiationSuccess = 32;
        static final int TRANSACTION_onRttSessionRemotelyTerminated = 34;
        static final int TRANSACTION_putExtras = 27;
        static final int TRANSACTION_queryLocation = 41;
        static final int TRANSACTION_queryRemoteConnectionServices = 18;
        static final int TRANSACTION_removeCall = 15;
        static final int TRANSACTION_removeExtras = 28;
        static final int TRANSACTION_requestCallEndpointChange = 30;
        static final int TRANSACTION_resetConnectionTime = 38;
        static final int TRANSACTION_setActive = 3;
        static final int TRANSACTION_setAddress = 23;
        static final int TRANSACTION_setAudioRoute = 29;
        static final int TRANSACTION_setCallDirection = 40;
        static final int TRANSACTION_setCallerDisplayName = 24;
        static final int TRANSACTION_setConferenceMergeFailed = 13;
        static final int TRANSACTION_setConferenceState = 39;
        static final int TRANSACTION_setConferenceableConnections = 25;
        static final int TRANSACTION_setConnectionCapabilities = 10;
        static final int TRANSACTION_setConnectionProperties = 11;
        static final int TRANSACTION_setDialing = 5;
        static final int TRANSACTION_setDisconnected = 7;
        static final int TRANSACTION_setIsConferenced = 12;
        static final int TRANSACTION_setIsVoipAudioMode = 21;
        static final int TRANSACTION_setOnHold = 8;
        static final int TRANSACTION_setPulling = 6;
        static final int TRANSACTION_setRingbackRequested = 9;
        static final int TRANSACTION_setRinging = 4;
        static final int TRANSACTION_setStatusHints = 22;
        static final int TRANSACTION_setVideoProvider = 19;
        static final int TRANSACTION_setVideoState = 20;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 40;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IConnectionServiceAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IConnectionServiceAdapter)) {
                return (IConnectionServiceAdapter) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "handleCreateConnectionComplete";
                case 2:
                    return "handleCreateConferenceComplete";
                case 3:
                    return "setActive";
                case 4:
                    return "setRinging";
                case 5:
                    return "setDialing";
                case 6:
                    return "setPulling";
                case 7:
                    return "setDisconnected";
                case 8:
                    return "setOnHold";
                case 9:
                    return "setRingbackRequested";
                case 10:
                    return "setConnectionCapabilities";
                case 11:
                    return "setConnectionProperties";
                case 12:
                    return "setIsConferenced";
                case 13:
                    return "setConferenceMergeFailed";
                case 14:
                    return "addConferenceCall";
                case 15:
                    return "removeCall";
                case 16:
                    return "onPostDialWait";
                case 17:
                    return "onPostDialChar";
                case 18:
                    return "queryRemoteConnectionServices";
                case 19:
                    return "setVideoProvider";
                case 20:
                    return "setVideoState";
                case 21:
                    return "setIsVoipAudioMode";
                case 22:
                    return "setStatusHints";
                case 23:
                    return "setAddress";
                case 24:
                    return "setCallerDisplayName";
                case 25:
                    return "setConferenceableConnections";
                case 26:
                    return "addExistingConnection";
                case 27:
                    return "putExtras";
                case 28:
                    return "removeExtras";
                case 29:
                    return "setAudioRoute";
                case 30:
                    return "requestCallEndpointChange";
                case 31:
                    return "onConnectionEvent";
                case 32:
                    return "onRttInitiationSuccess";
                case 33:
                    return "onRttInitiationFailure";
                case 34:
                    return "onRttSessionRemotelyTerminated";
                case 35:
                    return "onRemoteRttRequest";
                case 36:
                    return "onPhoneAccountChanged";
                case 37:
                    return "onConnectionServiceFocusReleased";
                case 38:
                    return "resetConnectionTime";
                case 39:
                    return "setConferenceState";
                case 40:
                    return "setCallDirection";
                case 41:
                    return "queryLocation";
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
                    String readString = parcel.readString();
                    ConnectionRequest connectionRequest = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    ParcelableConnection parcelableConnection = (ParcelableConnection) parcel.readTypedObject(ParcelableConnection.CREATOR);
                    Session.Info info = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleCreateConnectionComplete(readString, connectionRequest, parcelableConnection, info);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    ConnectionRequest connectionRequest2 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    ParcelableConference parcelableConference = (ParcelableConference) parcel.readTypedObject(ParcelableConference.CREATOR);
                    Session.Info info2 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleCreateConferenceComplete(readString2, connectionRequest2, parcelableConference, info2);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    Session.Info info3 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActive(readString3, info3);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    Session.Info info4 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRinging(readString4, info4);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    Session.Info info5 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDialing(readString5, info5);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    Session.Info info6 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPulling(readString6, info6);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    DisconnectCause disconnectCause = (DisconnectCause) parcel.readTypedObject(DisconnectCause.CREATOR);
                    Session.Info info7 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisconnected(readString7, disconnectCause, info7);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    Session.Info info8 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnHold(readString8, info8);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    Session.Info info9 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRingbackRequested(readString9, readBoolean, info9);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    int readInt = parcel.readInt();
                    Session.Info info10 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConnectionCapabilities(readString10, readInt, info10);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    Session.Info info11 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConnectionProperties(readString11, readInt2, info11);
                    return true;
                case 12:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    Session.Info info12 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIsConferenced(readString12, readString13, info12);
                    return true;
                case 13:
                    String readString14 = parcel.readString();
                    Session.Info info13 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceMergeFailed(readString14, info13);
                    return true;
                case 14:
                    String readString15 = parcel.readString();
                    ParcelableConference parcelableConference2 = (ParcelableConference) parcel.readTypedObject(ParcelableConference.CREATOR);
                    Session.Info info14 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceCall(readString15, parcelableConference2, info14);
                    return true;
                case 15:
                    String readString16 = parcel.readString();
                    Session.Info info15 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeCall(readString16, info15);
                    return true;
                case 16:
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    Session.Info info16 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialWait(readString17, readString18, info16);
                    return true;
                case 17:
                    String readString19 = parcel.readString();
                    char readInt3 = (char) parcel.readInt();
                    Session.Info info17 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialChar(readString19, readInt3, info17);
                    return true;
                case 18:
                    RemoteServiceCallback asInterface = RemoteServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString20 = parcel.readString();
                    Session.Info info18 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryRemoteConnectionServices(asInterface, readString20, info18);
                    return true;
                case 19:
                    String readString21 = parcel.readString();
                    IVideoProvider asInterface2 = IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
                    Session.Info info19 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoProvider(readString21, asInterface2, info19);
                    return true;
                case 20:
                    String readString22 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    Session.Info info20 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoState(readString22, readInt4, info20);
                    return true;
                case 21:
                    String readString23 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    Session.Info info21 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIsVoipAudioMode(readString23, readBoolean2, info21);
                    return true;
                case 22:
                    String readString24 = parcel.readString();
                    StatusHints statusHints = (StatusHints) parcel.readTypedObject(StatusHints.CREATOR);
                    Session.Info info22 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatusHints(readString24, statusHints, info22);
                    return true;
                case 23:
                    String readString25 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int readInt5 = parcel.readInt();
                    Session.Info info23 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAddress(readString25, uri, readInt5, info23);
                    return true;
                case 24:
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    Session.Info info24 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallerDisplayName(readString26, readString27, readInt6, info24);
                    return true;
                case 25:
                    String readString28 = parcel.readString();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    Session.Info info25 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceableConnections(readString28, createStringArrayList, info25);
                    return true;
                case 26:
                    String readString29 = parcel.readString();
                    ParcelableConnection parcelableConnection2 = (ParcelableConnection) parcel.readTypedObject(ParcelableConnection.CREATOR);
                    Session.Info info26 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addExistingConnection(readString29, parcelableConnection2, info26);
                    return true;
                case 27:
                    String readString30 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info27 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    putExtras(readString30, bundle, info27);
                    return true;
                case 28:
                    String readString31 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    Session.Info info28 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeExtras(readString31, createStringArrayList2, info28);
                    return true;
                case 29:
                    String readString32 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    String readString33 = parcel.readString();
                    Session.Info info29 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAudioRoute(readString32, readInt7, readString33, info29);
                    return true;
                case 30:
                    String readString34 = parcel.readString();
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    Session.Info info30 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(readString34, callEndpoint, resultReceiver, info30);
                    return true;
                case 31:
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info31 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionEvent(readString35, readString36, bundle2, info31);
                    return true;
                case 32:
                    String readString37 = parcel.readString();
                    Session.Info info32 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttInitiationSuccess(readString37, info32);
                    return true;
                case 33:
                    String readString38 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    Session.Info info33 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttInitiationFailure(readString38, readInt8, info33);
                    return true;
                case 34:
                    String readString39 = parcel.readString();
                    Session.Info info34 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttSessionRemotelyTerminated(readString39, info34);
                    return true;
                case 35:
                    String readString40 = parcel.readString();
                    Session.Info info35 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRemoteRttRequest(readString40, info35);
                    return true;
                case 36:
                    String readString41 = parcel.readString();
                    PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    Session.Info info36 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPhoneAccountChanged(readString41, phoneAccountHandle, info36);
                    return true;
                case 37:
                    Session.Info info37 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionServiceFocusReleased(info37);
                    return true;
                case 38:
                    String readString42 = parcel.readString();
                    Session.Info info38 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetConnectionTime(readString42, info38);
                    return true;
                case 39:
                    String readString43 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    Session.Info info39 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceState(readString43, readBoolean3, info39);
                    return true;
                case 40:
                    String readString44 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    Session.Info info40 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallDirection(readString44, readInt9, info40);
                    return true;
                case 41:
                    String readString45 = parcel.readString();
                    long readLong = parcel.readLong();
                    String readString46 = parcel.readString();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    Session.Info info41 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryLocation(readString45, readLong, readString46, resultReceiver2, info41);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IConnectionServiceAdapter {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void handleCreateConnectionComplete(String str, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeTypedObject(parcelableConnection, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void handleCreateConferenceComplete(String str, ConnectionRequest connectionRequest, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(connectionRequest, 0);
                    obtain.writeTypedObject(parcelableConference, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setActive(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setRinging(String str, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setDialing(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setPulling(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setDisconnected(String str, DisconnectCause disconnectCause, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(disconnectCause, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setOnHold(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setRingbackRequested(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConnectionCapabilities(String str, int i, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConnectionProperties(String str, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setIsConferenced(String str, String str2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceMergeFailed(String str, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void addConferenceCall(String str, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelableConference, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void removeCall(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPostDialWait(String str, String str2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPostDialChar(String str, char c, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(c);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(remoteServiceCallback);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setVideoProvider(String str, IVideoProvider iVideoProvider, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iVideoProvider);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setVideoState(String str, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setIsVoipAudioMode(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setStatusHints(String str, StatusHints statusHints, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(statusHints, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setAddress(String str, Uri uri, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setCallerDisplayName(String str, String str2, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceableConnections(String str, List<String> list, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void addExistingConnection(String str, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelableConnection, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void putExtras(String str, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void removeExtras(String str, List<String> list, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setAudioRoute(String str, int i, String str2, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void requestCallEndpointChange(String str, CallEndpoint callEndpoint, ResultReceiver resultReceiver, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onConnectionEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttInitiationSuccess(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttInitiationFailure(String str, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttSessionRemotelyTerminated(String str, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRemoteRttRequest(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPhoneAccountChanged(String str, PhoneAccountHandle phoneAccountHandle, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(phoneAccountHandle, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onConnectionServiceFocusReleased(Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void resetConnectionTime(String str, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceState(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setCallDirection(String str, int i, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void queryLocation(String str, long j, String str2, ResultReceiver resultReceiver, Session.Info info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(resultReceiver, 0);
                    obtain.writeTypedObject(info, 0);
                    this.mRemote.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
