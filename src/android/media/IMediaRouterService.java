package android.media;

import android.media.IMediaRouter2;
import android.media.IMediaRouter2Manager;
import android.media.IMediaRouterClient;
import android.media.IMediaRouterService;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes2.dex */
public interface IMediaRouterService extends IInterface {

    public static class Default implements IMediaRouterService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public void deselectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void deselectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public List<RoutingSessionInfo> getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public List<MediaRoute2Info> getSystemRoutes(String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public RoutingSessionInfo getSystemSessionInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public RoutingSessionInfo getSystemSessionInfoForPackage(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) throws RemoteException {
            return false;
        }

        @Override // android.media.IMediaRouterService
        public void registerClientAsUser(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String str) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void registerProxyRouter(IMediaRouter2Manager iMediaRouter2Manager, String str, String str2, UserHandle userHandle) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void registerRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void releaseSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestCreateSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestCreateSessionWithRouter2(IMediaRouter2 iMediaRouter2, int i, long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, Bundle bundle) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestSetVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void selectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void selectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setBluetoothA2dpOn(IMediaRouterClient iMediaRouterClient, boolean z) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager, List<SuggestedDeviceInfo> list) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2, List<SuggestedDeviceInfo> list) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int i, boolean z) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setDiscoveryRequestWithRouter2(IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setRouteListingPreference(IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setRouteVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, MediaRoute2Info mediaRoute2Info, int i2) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setRouteVolumeWithRouter2(IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String str, boolean z) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setSessionVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public boolean showMediaOutputSwitcherWithProxyRouter(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
            return false;
        }

        @Override // android.media.IMediaRouterService
        public boolean showMediaOutputSwitcherWithRouter2(String str) throws RemoteException {
            return false;
        }

        @Override // android.media.IMediaRouterService
        public void transferToRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info, UserHandle userHandle, String str2) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void transferToRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void unregisterClient(IMediaRouterClient iMediaRouterClient) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void unregisterRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void updateScanningState(IMediaRouter2Manager iMediaRouter2Manager, int i) throws RemoteException {
        }

        @Override // android.media.IMediaRouterService
        public void updateScanningStateWithRouter2(IMediaRouter2 iMediaRouter2, int i) throws RemoteException {
        }
    }

    void deselectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException;

    void deselectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException;

    Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException;

    Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException;

    List<RoutingSessionInfo> getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException;

    MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) throws RemoteException;

    List<MediaRoute2Info> getSystemRoutes(String str, boolean z) throws RemoteException;

    RoutingSessionInfo getSystemSessionInfo() throws RemoteException;

    RoutingSessionInfo getSystemSessionInfoForPackage(String str, String str2) throws RemoteException;

    boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) throws RemoteException;

    void registerClientAsUser(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException;

    void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String str) throws RemoteException;

    void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str) throws RemoteException;

    void registerProxyRouter(IMediaRouter2Manager iMediaRouter2Manager, String str, String str2, UserHandle userHandle) throws RemoteException;

    void registerRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException;

    void releaseSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str) throws RemoteException;

    void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException;

    void requestCreateSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) throws RemoteException;

    void requestCreateSessionWithRouter2(IMediaRouter2 iMediaRouter2, int i, long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, Bundle bundle) throws RemoteException;

    void requestSetVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException;

    void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException;

    void selectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException;

    void selectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException;

    void setBluetoothA2dpOn(IMediaRouterClient iMediaRouterClient, boolean z) throws RemoteException;

    void setDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager, List<SuggestedDeviceInfo> list) throws RemoteException;

    void setDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2, List<SuggestedDeviceInfo> list) throws RemoteException;

    void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int i, boolean z) throws RemoteException;

    void setDiscoveryRequestWithRouter2(IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException;

    void setRouteListingPreference(IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference) throws RemoteException;

    void setRouteVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, MediaRoute2Info mediaRoute2Info, int i2) throws RemoteException;

    void setRouteVolumeWithRouter2(IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) throws RemoteException;

    void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String str, boolean z) throws RemoteException;

    void setSessionVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) throws RemoteException;

    void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i) throws RemoteException;

    boolean showMediaOutputSwitcherWithProxyRouter(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException;

    boolean showMediaOutputSwitcherWithRouter2(String str) throws RemoteException;

    void transferToRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info, UserHandle userHandle, String str2) throws RemoteException;

    void transferToRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException;

    void unregisterClient(IMediaRouterClient iMediaRouterClient) throws RemoteException;

    void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException;

    void unregisterRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException;

    void updateScanningState(IMediaRouter2Manager iMediaRouter2Manager, int i) throws RemoteException;

    void updateScanningStateWithRouter2(IMediaRouter2 iMediaRouter2, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IMediaRouterService {
        public static final String DESCRIPTOR = "android.media.IMediaRouterService";
        static final int TRANSACTION_deselectRouteWithManager = 37;
        static final int TRANSACTION_deselectRouteWithRouter2 = 22;
        static final int TRANSACTION_getDeviceSuggestionsWithManager = 43;
        static final int TRANSACTION_getDeviceSuggestionsWithRouter2 = 27;
        static final int TRANSACTION_getRemoteSessions = 28;
        static final int TRANSACTION_getState = 4;
        static final int TRANSACTION_getSystemRoutes = 11;
        static final int TRANSACTION_getSystemSessionInfo = 12;
        static final int TRANSACTION_getSystemSessionInfoForPackage = 29;
        static final int TRANSACTION_isPlaybackActive = 5;
        static final int TRANSACTION_registerClientAsUser = 1;
        static final int TRANSACTION_registerClientGroupId = 3;
        static final int TRANSACTION_registerManager = 30;
        static final int TRANSACTION_registerProxyRouter = 31;
        static final int TRANSACTION_registerRouter2 = 14;
        static final int TRANSACTION_releaseSessionWithManager = 40;
        static final int TRANSACTION_releaseSessionWithRouter2 = 25;
        static final int TRANSACTION_requestCreateSessionWithManager = 35;
        static final int TRANSACTION_requestCreateSessionWithRouter2 = 20;
        static final int TRANSACTION_requestSetVolume = 9;
        static final int TRANSACTION_requestUpdateVolume = 10;
        static final int TRANSACTION_selectRouteWithManager = 36;
        static final int TRANSACTION_selectRouteWithRouter2 = 21;
        static final int TRANSACTION_setBluetoothA2dpOn = 6;
        static final int TRANSACTION_setDeviceSuggestionsWithManager = 42;
        static final int TRANSACTION_setDeviceSuggestionsWithRouter2 = 26;
        static final int TRANSACTION_setDiscoveryRequest = 7;
        static final int TRANSACTION_setDiscoveryRequestWithRouter2 = 17;
        static final int TRANSACTION_setRouteListingPreference = 18;
        static final int TRANSACTION_setRouteVolumeWithManager = 33;
        static final int TRANSACTION_setRouteVolumeWithRouter2 = 19;
        static final int TRANSACTION_setSelectedRoute = 8;
        static final int TRANSACTION_setSessionVolumeWithManager = 39;
        static final int TRANSACTION_setSessionVolumeWithRouter2 = 24;
        static final int TRANSACTION_showMediaOutputSwitcherWithProxyRouter = 41;
        static final int TRANSACTION_showMediaOutputSwitcherWithRouter2 = 13;
        static final int TRANSACTION_transferToRouteWithManager = 38;
        static final int TRANSACTION_transferToRouteWithRouter2 = 23;
        static final int TRANSACTION_unregisterClient = 2;
        static final int TRANSACTION_unregisterManager = 32;
        static final int TRANSACTION_unregisterRouter2 = 15;
        static final int TRANSACTION_updateScanningState = 34;
        static final int TRANSACTION_updateScanningStateWithRouter2 = 16;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 42;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaRouterService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMediaRouterService)) {
                return (IMediaRouterService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "registerClientAsUser";
                case 2:
                    return "unregisterClient";
                case 3:
                    return "registerClientGroupId";
                case 4:
                    return "getState";
                case 5:
                    return "isPlaybackActive";
                case 6:
                    return "setBluetoothA2dpOn";
                case 7:
                    return "setDiscoveryRequest";
                case 8:
                    return "setSelectedRoute";
                case 9:
                    return "requestSetVolume";
                case 10:
                    return "requestUpdateVolume";
                case 11:
                    return "getSystemRoutes";
                case 12:
                    return "getSystemSessionInfo";
                case 13:
                    return "showMediaOutputSwitcherWithRouter2";
                case 14:
                    return "registerRouter2";
                case 15:
                    return "unregisterRouter2";
                case 16:
                    return "updateScanningStateWithRouter2";
                case 17:
                    return "setDiscoveryRequestWithRouter2";
                case 18:
                    return "setRouteListingPreference";
                case 19:
                    return "setRouteVolumeWithRouter2";
                case 20:
                    return "requestCreateSessionWithRouter2";
                case 21:
                    return "selectRouteWithRouter2";
                case 22:
                    return "deselectRouteWithRouter2";
                case 23:
                    return "transferToRouteWithRouter2";
                case 24:
                    return "setSessionVolumeWithRouter2";
                case 25:
                    return "releaseSessionWithRouter2";
                case 26:
                    return "setDeviceSuggestionsWithRouter2";
                case 27:
                    return "getDeviceSuggestionsWithRouter2";
                case 28:
                    return "getRemoteSessions";
                case 29:
                    return "getSystemSessionInfoForPackage";
                case 30:
                    return "registerManager";
                case 31:
                    return "registerProxyRouter";
                case 32:
                    return "unregisterManager";
                case 33:
                    return "setRouteVolumeWithManager";
                case 34:
                    return "updateScanningState";
                case 35:
                    return "requestCreateSessionWithManager";
                case 36:
                    return "selectRouteWithManager";
                case 37:
                    return "deselectRouteWithManager";
                case 38:
                    return "transferToRouteWithManager";
                case 39:
                    return "setSessionVolumeWithManager";
                case 40:
                    return "releaseSessionWithManager";
                case 41:
                    return "showMediaOutputSwitcherWithProxyRouter";
                case 42:
                    return "setDeviceSuggestionsWithManager";
                case 43:
                    return "getDeviceSuggestionsWithManager";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IMediaRouterClient asInterface = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerClientAsUser(asInterface, readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IMediaRouterClient asInterface2 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterClient(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IMediaRouterClient asInterface3 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerClientGroupId(asInterface3, readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IMediaRouterClient asInterface4 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    MediaRouterClientState state = getState(asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 5:
                    IMediaRouterClient asInterface5 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean isPlaybackActive = isPlaybackActive(asInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPlaybackActive);
                    return true;
                case 6:
                    IMediaRouterClient asInterface6 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothA2dpOn(asInterface6, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IMediaRouterClient asInterface7 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDiscoveryRequest(asInterface7, readInt2, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IMediaRouterClient asInterface8 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSelectedRoute(asInterface8, readString3, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IMediaRouterClient asInterface9 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSetVolume(asInterface9, readString4, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IMediaRouterClient asInterface10 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestUpdateVolume(asInterface10, readString5, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString6 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<MediaRoute2Info> systemRoutes = getSystemRoutes(readString6, readBoolean4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemRoutes, 1);
                    return true;
                case 12:
                    RoutingSessionInfo systemSessionInfo = getSystemSessionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemSessionInfo, 1);
                    return true;
                case 13:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean showMediaOutputSwitcherWithRouter2 = showMediaOutputSwitcherWithRouter2(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showMediaOutputSwitcherWithRouter2);
                    return true;
                case 14:
                    IMediaRouter2 asInterface11 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerRouter2(asInterface11, readString8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IMediaRouter2 asInterface12 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRouter2(asInterface12);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IMediaRouter2 asInterface13 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateScanningStateWithRouter2(asInterface13, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IMediaRouter2 asInterface14 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    RouteDiscoveryPreference routeDiscoveryPreference = (RouteDiscoveryPreference) parcel.readTypedObject(RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDiscoveryRequestWithRouter2(asInterface14, routeDiscoveryPreference);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IMediaRouter2 asInterface15 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    RouteListingPreference routeListingPreference = (RouteListingPreference) parcel.readTypedObject(RouteListingPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRouteListingPreference(asInterface15, routeListingPreference);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IMediaRouter2 asInterface16 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolumeWithRouter2(asInterface16, mediaRoute2Info, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IMediaRouter2 asInterface17 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    int readInt7 = parcel.readInt();
                    long readLong = parcel.readLong();
                    RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSessionWithRouter2(asInterface17, readInt7, readLong, routingSessionInfo, mediaRoute2Info2, bundle);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IMediaRouter2 asInterface18 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String readString9 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info3 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectRouteWithRouter2(asInterface18, readString9, mediaRoute2Info3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IMediaRouter2 asInterface19 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String readString10 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info4 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deselectRouteWithRouter2(asInterface19, readString10, mediaRoute2Info4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IMediaRouter2 asInterface20 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String readString11 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info5 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    transferToRouteWithRouter2(asInterface20, readString11, mediaRoute2Info5);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IMediaRouter2 asInterface21 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String readString12 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolumeWithRouter2(asInterface21, readString12, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IMediaRouter2 asInterface22 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSessionWithRouter2(asInterface22, readString13);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IMediaRouter2 asInterface23 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceSuggestionsWithRouter2(asInterface23, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IMediaRouter2 asInterface24 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Map<String, List<SuggestedDeviceInfo>> deviceSuggestionsWithRouter2 = getDeviceSuggestionsWithRouter2(asInterface24);
                    parcel2.writeNoException();
                    if (deviceSuggestionsWithRouter2 == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(deviceSuggestionsWithRouter2.size());
                        deviceSuggestionsWithRouter2.forEach(new BiConsumer() { // from class: android.media.IMediaRouterService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IMediaRouterService.Stub.lambda$onTransact$0(Parcel.this, (String) obj, (List) obj2);
                            }
                        });
                    }
                    return true;
                case 28:
                    IMediaRouter2Manager asInterface25 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    List<RoutingSessionInfo> remoteSessions = getRemoteSessions(asInterface25);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(remoteSessions, 1);
                    return true;
                case 29:
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RoutingSessionInfo systemSessionInfoForPackage = getSystemSessionInfoForPackage(readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemSessionInfoForPackage, 1);
                    return true;
                case 30:
                    IMediaRouter2Manager asInterface26 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    String readString16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerManager(asInterface26, readString16);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IMediaRouter2Manager asInterface27 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerProxyRouter(asInterface27, readString17, readString18, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IMediaRouter2Manager asInterface28 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterManager(asInterface28);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IMediaRouter2Manager asInterface29 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt9 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info6 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolumeWithManager(asInterface29, readInt9, mediaRoute2Info6, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IMediaRouter2Manager asInterface30 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateScanningState(asInterface30, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    IMediaRouter2Manager asInterface31 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt12 = parcel.readInt();
                    RoutingSessionInfo routingSessionInfo2 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    MediaRoute2Info mediaRoute2Info7 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSessionWithManager(asInterface31, readInt12, routingSessionInfo2, mediaRoute2Info7);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    IMediaRouter2Manager asInterface32 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt13 = parcel.readInt();
                    String readString19 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info8 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectRouteWithManager(asInterface32, readInt13, readString19, mediaRoute2Info8);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IMediaRouter2Manager asInterface33 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt14 = parcel.readInt();
                    String readString20 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info9 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deselectRouteWithManager(asInterface33, readInt14, readString20, mediaRoute2Info9);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IMediaRouter2Manager asInterface34 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt15 = parcel.readInt();
                    String readString21 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info10 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferToRouteWithManager(asInterface34, readInt15, readString21, mediaRoute2Info10, userHandle2, readString22);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IMediaRouter2Manager asInterface35 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    String readString23 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolumeWithManager(asInterface35, readInt16, readString23, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IMediaRouter2Manager asInterface36 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int readInt18 = parcel.readInt();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSessionWithManager(asInterface36, readInt18, readString24);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IMediaRouter2Manager asInterface37 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean showMediaOutputSwitcherWithProxyRouter = showMediaOutputSwitcherWithProxyRouter(asInterface37);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(showMediaOutputSwitcherWithProxyRouter);
                    return true;
                case 42:
                    IMediaRouter2Manager asInterface38 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceSuggestionsWithManager(asInterface38, createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    IMediaRouter2Manager asInterface39 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Map<String, List<SuggestedDeviceInfo>> deviceSuggestionsWithManager = getDeviceSuggestionsWithManager(asInterface39);
                    parcel2.writeNoException();
                    if (deviceSuggestionsWithManager == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(deviceSuggestionsWithManager.size());
                        deviceSuggestionsWithManager.forEach(new BiConsumer() { // from class: android.media.IMediaRouterService$Stub$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IMediaRouterService.Stub.lambda$onTransact$1(Parcel.this, (String) obj, (List) obj2);
                            }
                        });
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel parcel, String str, List list) {
            parcel.writeString(str);
            parcel.writeTypedList(list, 1);
        }

        static /* synthetic */ void lambda$onTransact$1(Parcel parcel, String str, List list) {
            parcel.writeString(str);
            parcel.writeTypedList(list, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IMediaRouterService {
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

            @Override // android.media.IMediaRouterService
            public void registerClientAsUser(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterClient(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MediaRouterClientState) obtain2.readTypedObject(MediaRouterClientState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setBluetoothA2dpOn(IMediaRouterClient iMediaRouterClient, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestSetVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouterClient);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public List<MediaRoute2Info> getSystemRoutes(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(MediaRoute2Info.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public RoutingSessionInfo getSystemSessionInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RoutingSessionInfo) obtain2.readTypedObject(RoutingSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean showMediaOutputSwitcherWithRouter2(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningStateWithRouter2(IMediaRouter2 iMediaRouter2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequestWithRouter2(IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteListingPreference(IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeTypedObject(routeListingPreference, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithRouter2(IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithRouter2(IMediaRouter2 iMediaRouter2, int i, long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2, List<SuggestedDeviceInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.media.IMediaRouterService$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), Parcel.this.createTypedArrayList(SuggestedDeviceInfo.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public List<RoutingSessionInfo> getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(RoutingSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public RoutingSessionInfo getSystemSessionInfoForPackage(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RoutingSessionInfo) obtain2.readTypedObject(RoutingSessionInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerProxyRouter(IMediaRouter2Manager iMediaRouter2Manager, String str, String str2, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, MediaRoute2Info mediaRoute2Info, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningState(IMediaRouter2Manager iMediaRouter2Manager, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(routingSessionInfo, 0);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info, UserHandle userHandle, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(mediaRoute2Info, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean showMediaOutputSwitcherWithProxyRouter(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager, List<SuggestedDeviceInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final HashMap hashMap = readInt < 0 ? null : new HashMap();
                    IntStream.range(0, readInt).forEach(new IntConsumer() { // from class: android.media.IMediaRouterService$Stub$Proxy$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            hashMap.put(r0.readString(), Parcel.this.createTypedArrayList(SuggestedDeviceInfo.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
