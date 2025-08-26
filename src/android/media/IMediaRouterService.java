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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMediaRouterService)) {
                return (IMediaRouterService) iInterfaceQueryLocalInterface;
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
                    IMediaRouterClient iMediaRouterClientAsInterface = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerClientAsUser(iMediaRouterClientAsInterface, string, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IMediaRouterClient iMediaRouterClientAsInterface2 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterClient(iMediaRouterClientAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    IMediaRouterClient iMediaRouterClientAsInterface3 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerClientGroupId(iMediaRouterClientAsInterface3, string2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IMediaRouterClient iMediaRouterClientAsInterface4 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    MediaRouterClientState state = getState(iMediaRouterClientAsInterface4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(state, 1);
                    return true;
                case 5:
                    IMediaRouterClient iMediaRouterClientAsInterface5 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zIsPlaybackActive = isPlaybackActive(iMediaRouterClientAsInterface5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPlaybackActive);
                    return true;
                case 6:
                    IMediaRouterClient iMediaRouterClientAsInterface6 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBluetoothA2dpOn(iMediaRouterClientAsInterface6, z);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IMediaRouterClient iMediaRouterClientAsInterface7 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    int i4 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDiscoveryRequest(iMediaRouterClientAsInterface7, i4, z2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IMediaRouterClient iMediaRouterClientAsInterface8 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSelectedRoute(iMediaRouterClientAsInterface8, string3, z3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IMediaRouterClient iMediaRouterClientAsInterface9 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String string4 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestSetVolume(iMediaRouterClientAsInterface9, string4, i5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IMediaRouterClient iMediaRouterClientAsInterface10 = IMediaRouterClient.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestUpdateVolume(iMediaRouterClientAsInterface10, string5, i6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string6 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<MediaRoute2Info> systemRoutes = getSystemRoutes(string6, z4);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(systemRoutes, 1);
                    return true;
                case 12:
                    RoutingSessionInfo systemSessionInfo = getSystemSessionInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemSessionInfo, 1);
                    return true;
                case 13:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zShowMediaOutputSwitcherWithRouter2 = showMediaOutputSwitcherWithRouter2(string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShowMediaOutputSwitcherWithRouter2);
                    return true;
                case 14:
                    IMediaRouter2 iMediaRouter2AsInterface = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerRouter2(iMediaRouter2AsInterface, string8);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IMediaRouter2 iMediaRouter2AsInterface2 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterRouter2(iMediaRouter2AsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    IMediaRouter2 iMediaRouter2AsInterface3 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateScanningStateWithRouter2(iMediaRouter2AsInterface3, i7);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IMediaRouter2 iMediaRouter2AsInterface4 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    RouteDiscoveryPreference routeDiscoveryPreference = (RouteDiscoveryPreference) parcel.readTypedObject(RouteDiscoveryPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDiscoveryRequestWithRouter2(iMediaRouter2AsInterface4, routeDiscoveryPreference);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    IMediaRouter2 iMediaRouter2AsInterface5 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    RouteListingPreference routeListingPreference = (RouteListingPreference) parcel.readTypedObject(RouteListingPreference.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRouteListingPreference(iMediaRouter2AsInterface5, routeListingPreference);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    IMediaRouter2 iMediaRouter2AsInterface6 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolumeWithRouter2(iMediaRouter2AsInterface6, mediaRoute2Info, i8);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    IMediaRouter2 iMediaRouter2AsInterface7 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    int i9 = parcel.readInt();
                    long j = parcel.readLong();
                    RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    MediaRoute2Info mediaRoute2Info2 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSessionWithRouter2(iMediaRouter2AsInterface7, i9, j, routingSessionInfo, mediaRoute2Info2, bundle);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    IMediaRouter2 iMediaRouter2AsInterface8 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String string9 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info3 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectRouteWithRouter2(iMediaRouter2AsInterface8, string9, mediaRoute2Info3);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    IMediaRouter2 iMediaRouter2AsInterface9 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String string10 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info4 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deselectRouteWithRouter2(iMediaRouter2AsInterface9, string10, mediaRoute2Info4);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    IMediaRouter2 iMediaRouter2AsInterface10 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String string11 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info5 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    transferToRouteWithRouter2(iMediaRouter2AsInterface10, string11, mediaRoute2Info5);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IMediaRouter2 iMediaRouter2AsInterface11 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String string12 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolumeWithRouter2(iMediaRouter2AsInterface11, string12, i10);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    IMediaRouter2 iMediaRouter2AsInterface12 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSessionWithRouter2(iMediaRouter2AsInterface12, string13);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    IMediaRouter2 iMediaRouter2AsInterface13 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceSuggestionsWithRouter2(iMediaRouter2AsInterface13, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    IMediaRouter2 iMediaRouter2AsInterface14 = IMediaRouter2.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Map<String, List<SuggestedDeviceInfo>> deviceSuggestionsWithRouter2 = getDeviceSuggestionsWithRouter2(iMediaRouter2AsInterface14);
                    parcel2.writeNoException();
                    if (deviceSuggestionsWithRouter2 == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(deviceSuggestionsWithRouter2.size());
                        deviceSuggestionsWithRouter2.forEach(new BiConsumer() { // from class: android.media.IMediaRouterService$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IMediaRouterService.Stub.lambda$onTransact$0(parcel2, (String) obj, (List) obj2);
                            }
                        });
                    }
                    return true;
                case 28:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    List<RoutingSessionInfo> remoteSessions = getRemoteSessions(iMediaRouter2ManagerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(remoteSessions, 1);
                    return true;
                case 29:
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RoutingSessionInfo systemSessionInfoForPackage = getSystemSessionInfoForPackage(string14, string15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(systemSessionInfoForPackage, 1);
                    return true;
                case 30:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface2 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerManager(iMediaRouter2ManagerAsInterface2, string16);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface3 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerProxyRouter(iMediaRouter2ManagerAsInterface3, string17, string18, userHandle);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface4 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterManager(iMediaRouter2ManagerAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface5 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i11 = parcel.readInt();
                    MediaRoute2Info mediaRoute2Info6 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRouteVolumeWithManager(iMediaRouter2ManagerAsInterface5, i11, mediaRoute2Info6, i12);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface6 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateScanningState(iMediaRouter2ManagerAsInterface6, i13);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface7 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i14 = parcel.readInt();
                    RoutingSessionInfo routingSessionInfo2 = (RoutingSessionInfo) parcel.readTypedObject(RoutingSessionInfo.CREATOR);
                    MediaRoute2Info mediaRoute2Info7 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCreateSessionWithManager(iMediaRouter2ManagerAsInterface7, i14, routingSessionInfo2, mediaRoute2Info7);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface8 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i15 = parcel.readInt();
                    String string19 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info8 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    selectRouteWithManager(iMediaRouter2ManagerAsInterface8, i15, string19, mediaRoute2Info8);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface9 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i16 = parcel.readInt();
                    String string20 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info9 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    parcel.enforceNoDataAvail();
                    deselectRouteWithManager(iMediaRouter2ManagerAsInterface9, i16, string20, mediaRoute2Info9);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface10 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i17 = parcel.readInt();
                    String string21 = parcel.readString();
                    MediaRoute2Info mediaRoute2Info10 = (MediaRoute2Info) parcel.readTypedObject(MediaRoute2Info.CREATOR);
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    transferToRouteWithManager(iMediaRouter2ManagerAsInterface10, i17, string21, mediaRoute2Info10, userHandle2, string22);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface11 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i18 = parcel.readInt();
                    String string23 = parcel.readString();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setSessionVolumeWithManager(iMediaRouter2ManagerAsInterface11, i18, string23, i19);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface12 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    int i20 = parcel.readInt();
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    releaseSessionWithManager(iMediaRouter2ManagerAsInterface12, i20, string24);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface13 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zShowMediaOutputSwitcherWithProxyRouter = showMediaOutputSwitcherWithProxyRouter(iMediaRouter2ManagerAsInterface13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShowMediaOutputSwitcherWithProxyRouter);
                    return true;
                case 42:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface14 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDeviceSuggestionsWithManager(iMediaRouter2ManagerAsInterface14, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    IMediaRouter2Manager iMediaRouter2ManagerAsInterface15 = IMediaRouter2Manager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Map<String, List<SuggestedDeviceInfo>> deviceSuggestionsWithManager = getDeviceSuggestionsWithManager(iMediaRouter2ManagerAsInterface15);
                    parcel2.writeNoException();
                    if (deviceSuggestionsWithManager == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(deviceSuggestionsWithManager.size());
                        deviceSuggestionsWithManager.forEach(new BiConsumer() { // from class: android.media.IMediaRouterService$Stub$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IMediaRouterService.Stub.lambda$onTransact$1(parcel2, (String) obj, (List) obj2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterClient(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MediaRouterClientState) parcelObtain2.readTypedObject(MediaRouterClientState.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setBluetoothA2dpOn(IMediaRouterClient iMediaRouterClient, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestSetVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouterClient);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public List<MediaRoute2Info> getSystemRoutes(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(MediaRoute2Info.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public RoutingSessionInfo getSystemSessionInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RoutingSessionInfo) parcelObtain2.readTypedObject(RoutingSessionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean showMediaOutputSwitcherWithRouter2(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningStateWithRouter2(IMediaRouter2 iMediaRouter2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequestWithRouter2(IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeTypedObject(routeDiscoveryPreference, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteListingPreference(IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeTypedObject(routeListingPreference, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithRouter2(IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithRouter2(IMediaRouter2 iMediaRouter2, int i, long j, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithRouter2(IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2, List<SuggestedDeviceInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.media.IMediaRouterService$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR));
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public List<RoutingSessionInfo> getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(RoutingSessionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public RoutingSessionInfo getSystemSessionInfoForPackage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RoutingSessionInfo) parcelObtain2.readTypedObject(RoutingSessionInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerProxyRouter(IMediaRouter2Manager iMediaRouter2Manager, String str, String str2, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, MediaRoute2Info mediaRoute2Info, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningState(IMediaRouter2Manager iMediaRouter2Manager, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(routingSessionInfo, 0);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, MediaRoute2Info mediaRoute2Info, UserHandle userHandle, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(mediaRoute2Info, 0);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean showMediaOutputSwitcherWithProxyRouter(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager, List<SuggestedDeviceInfo> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public Map<String, List<SuggestedDeviceInfo>> getDeviceSuggestionsWithManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMediaRouter2Manager);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    int i = parcelObtain2.readInt();
                    final HashMap map = i < 0 ? null : new HashMap();
                    IntStream.range(0, i).forEach(new IntConsumer() { // from class: android.media.IMediaRouterService$Stub$Proxy$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.createTypedArrayList(SuggestedDeviceInfo.CREATOR));
                        }
                    });
                    return map;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
