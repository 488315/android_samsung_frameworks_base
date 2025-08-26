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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IConnectionServiceAdapter)) {
                return (IConnectionServiceAdapter) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    ConnectionRequest connectionRequest = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    ParcelableConnection parcelableConnection = (ParcelableConnection) parcel.readTypedObject(ParcelableConnection.CREATOR);
                    Session.Info info = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleCreateConnectionComplete(string, connectionRequest, parcelableConnection, info);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    ConnectionRequest connectionRequest2 = (ConnectionRequest) parcel.readTypedObject(ConnectionRequest.CREATOR);
                    ParcelableConference parcelableConference = (ParcelableConference) parcel.readTypedObject(ParcelableConference.CREATOR);
                    Session.Info info2 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleCreateConferenceComplete(string2, connectionRequest2, parcelableConference, info2);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    Session.Info info3 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActive(string3, info3);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    Session.Info info4 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRinging(string4, info4);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    Session.Info info5 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDialing(string5, info5);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    Session.Info info6 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPulling(string6, info6);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    DisconnectCause disconnectCause = (DisconnectCause) parcel.readTypedObject(DisconnectCause.CREATOR);
                    Session.Info info7 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisconnected(string7, disconnectCause, info7);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    Session.Info info8 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnHold(string8, info8);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    Session.Info info9 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRingbackRequested(string9, z, info9);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    int i3 = parcel.readInt();
                    Session.Info info10 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConnectionCapabilities(string10, i3, info10);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    int i4 = parcel.readInt();
                    Session.Info info11 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConnectionProperties(string11, i4, info11);
                    return true;
                case 12:
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    Session.Info info12 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIsConferenced(string12, string13, info12);
                    return true;
                case 13:
                    String string14 = parcel.readString();
                    Session.Info info13 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceMergeFailed(string14, info13);
                    return true;
                case 14:
                    String string15 = parcel.readString();
                    ParcelableConference parcelableConference2 = (ParcelableConference) parcel.readTypedObject(ParcelableConference.CREATOR);
                    Session.Info info14 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addConferenceCall(string15, parcelableConference2, info14);
                    return true;
                case 15:
                    String string16 = parcel.readString();
                    Session.Info info15 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeCall(string16, info15);
                    return true;
                case 16:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    Session.Info info16 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialWait(string17, string18, info16);
                    return true;
                case 17:
                    String string19 = parcel.readString();
                    char c = (char) parcel.readInt();
                    Session.Info info17 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPostDialChar(string19, c, info17);
                    return true;
                case 18:
                    RemoteServiceCallback remoteServiceCallbackAsInterface = RemoteServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string20 = parcel.readString();
                    Session.Info info18 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryRemoteConnectionServices(remoteServiceCallbackAsInterface, string20, info18);
                    return true;
                case 19:
                    String string21 = parcel.readString();
                    IVideoProvider iVideoProviderAsInterface = IVideoProvider.Stub.asInterface(parcel.readStrongBinder());
                    Session.Info info19 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoProvider(string21, iVideoProviderAsInterface, info19);
                    return true;
                case 20:
                    String string22 = parcel.readString();
                    int i5 = parcel.readInt();
                    Session.Info info20 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setVideoState(string22, i5, info20);
                    return true;
                case 21:
                    String string23 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    Session.Info info21 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setIsVoipAudioMode(string23, z2, info21);
                    return true;
                case 22:
                    String string24 = parcel.readString();
                    StatusHints statusHints = (StatusHints) parcel.readTypedObject(StatusHints.CREATOR);
                    Session.Info info22 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setStatusHints(string24, statusHints, info22);
                    return true;
                case 23:
                    String string25 = parcel.readString();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    int i6 = parcel.readInt();
                    Session.Info info23 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAddress(string25, uri, i6, info23);
                    return true;
                case 24:
                    String string26 = parcel.readString();
                    String string27 = parcel.readString();
                    int i7 = parcel.readInt();
                    Session.Info info24 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallerDisplayName(string26, string27, i7, info24);
                    return true;
                case 25:
                    String string28 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    Session.Info info25 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceableConnections(string28, arrayListCreateStringArrayList, info25);
                    return true;
                case 26:
                    String string29 = parcel.readString();
                    ParcelableConnection parcelableConnection2 = (ParcelableConnection) parcel.readTypedObject(ParcelableConnection.CREATOR);
                    Session.Info info26 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    addExistingConnection(string29, parcelableConnection2, info26);
                    return true;
                case 27:
                    String string30 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info27 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    putExtras(string30, bundle, info27);
                    return true;
                case 28:
                    String string31 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    Session.Info info28 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeExtras(string31, arrayListCreateStringArrayList2, info28);
                    return true;
                case 29:
                    String string32 = parcel.readString();
                    int i8 = parcel.readInt();
                    String string33 = parcel.readString();
                    Session.Info info29 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAudioRoute(string32, i8, string33, info29);
                    return true;
                case 30:
                    String string34 = parcel.readString();
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    Session.Info info30 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(string34, callEndpoint, resultReceiver, info30);
                    return true;
                case 31:
                    String string35 = parcel.readString();
                    String string36 = parcel.readString();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    Session.Info info31 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionEvent(string35, string36, bundle2, info31);
                    return true;
                case 32:
                    String string37 = parcel.readString();
                    Session.Info info32 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttInitiationSuccess(string37, info32);
                    return true;
                case 33:
                    String string38 = parcel.readString();
                    int i9 = parcel.readInt();
                    Session.Info info33 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttInitiationFailure(string38, i9, info33);
                    return true;
                case 34:
                    String string39 = parcel.readString();
                    Session.Info info34 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRttSessionRemotelyTerminated(string39, info34);
                    return true;
                case 35:
                    String string40 = parcel.readString();
                    Session.Info info35 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onRemoteRttRequest(string40, info35);
                    return true;
                case 36:
                    String string41 = parcel.readString();
                    PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle) parcel.readTypedObject(PhoneAccountHandle.CREATOR);
                    Session.Info info36 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onPhoneAccountChanged(string41, phoneAccountHandle, info36);
                    return true;
                case 37:
                    Session.Info info37 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConnectionServiceFocusReleased(info37);
                    return true;
                case 38:
                    String string42 = parcel.readString();
                    Session.Info info38 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetConnectionTime(string42, info38);
                    return true;
                case 39:
                    String string43 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    Session.Info info39 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setConferenceState(string43, z3, info39);
                    return true;
                case 40:
                    String string44 = parcel.readString();
                    int i10 = parcel.readInt();
                    Session.Info info40 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    setCallDirection(string44, i10, info40);
                    return true;
                case 41:
                    String string45 = parcel.readString();
                    long j = parcel.readLong();
                    String string46 = parcel.readString();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    Session.Info info41 = (Session.Info) parcel.readTypedObject(Session.Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    queryLocation(string45, j, string46, resultReceiver2, info41);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(connectionRequest, 0);
                    parcelObtain.writeTypedObject(parcelableConnection, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void handleCreateConferenceComplete(String str, ConnectionRequest connectionRequest, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(connectionRequest, 0);
                    parcelObtain.writeTypedObject(parcelableConference, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setActive(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setRinging(String str, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setDialing(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setPulling(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setDisconnected(String str, DisconnectCause disconnectCause, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(disconnectCause, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setOnHold(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setRingbackRequested(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConnectionCapabilities(String str, int i, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConnectionProperties(String str, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setIsConferenced(String str, String str2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceMergeFailed(String str, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void addConferenceCall(String str, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelableConference, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void removeCall(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPostDialWait(String str, String str2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPostDialChar(String str, char c, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(c);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(remoteServiceCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setVideoProvider(String str, IVideoProvider iVideoProvider, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iVideoProvider);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setVideoState(String str, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setIsVoipAudioMode(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setStatusHints(String str, StatusHints statusHints, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(statusHints, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setAddress(String str, Uri uri, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(23, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setCallerDisplayName(String str, String str2, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceableConnections(String str, List<String> list, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void addExistingConnection(String str, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parcelableConnection, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void putExtras(String str, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void removeExtras(String str, List<String> list, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setAudioRoute(String str, int i, String str2, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(29, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void requestCallEndpointChange(String str, CallEndpoint callEndpoint, ResultReceiver resultReceiver, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(callEndpoint, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onConnectionEvent(String str, String str2, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttInitiationSuccess(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(32, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttInitiationFailure(String str, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRttSessionRemotelyTerminated(String str, Session.Info info) throws RemoteException {
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

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onRemoteRttRequest(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(35, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onPhoneAccountChanged(String str, PhoneAccountHandle phoneAccountHandle, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(phoneAccountHandle, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(36, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void onConnectionServiceFocusReleased(Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(37, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void resetConnectionTime(String str, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(38, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setConferenceState(String str, boolean z, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(39, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void setCallDirection(String str, int i, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(40, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IConnectionServiceAdapter
            public void queryLocation(String str, long j, String str2, ResultReceiver resultReceiver, Session.Info info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    parcelObtain.writeTypedObject(info, 0);
                    this.mRemote.transact(41, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
