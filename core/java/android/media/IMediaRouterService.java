package android.media;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;

import java.util.List;

/* loaded from: classes2.dex */
public interface IMediaRouterService extends IInterface {
    void deselectRouteWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info)
            throws RemoteException;

    void deselectRouteWithRouter2(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info)
            throws RemoteException;

    List<RoutingSessionInfo> getRemoteSessions(IMediaRouter2Manager iMediaRouter2Manager)
            throws RemoteException;

    MediaRouterClientState getState(IMediaRouterClient iMediaRouterClient) throws RemoteException;

    List<MediaRoute2Info> getSystemRoutes(String str, boolean z) throws RemoteException;

    RoutingSessionInfo getSystemSessionInfo() throws RemoteException;

    RoutingSessionInfo getSystemSessionInfoForPackage(String str, String str2)
            throws RemoteException;

    boolean isPlaybackActive(IMediaRouterClient iMediaRouterClient) throws RemoteException;

    void registerClientAsUser(IMediaRouterClient iMediaRouterClient, String str, int i)
            throws RemoteException;

    void registerClientGroupId(IMediaRouterClient iMediaRouterClient, String str)
            throws RemoteException;

    void registerManager(IMediaRouter2Manager iMediaRouter2Manager, String str)
            throws RemoteException;

    void registerProxyRouter(
            IMediaRouter2Manager iMediaRouter2Manager,
            String str,
            String str2,
            UserHandle userHandle)
            throws RemoteException;

    void registerRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException;

    void releaseSessionWithManager(IMediaRouter2Manager iMediaRouter2Manager, int i, String str)
            throws RemoteException;

    void releaseSessionWithRouter2(IMediaRouter2 iMediaRouter2, String str) throws RemoteException;

    void requestCreateSessionWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            RoutingSessionInfo routingSessionInfo,
            MediaRoute2Info mediaRoute2Info,
            UserHandle userHandle,
            String str)
            throws RemoteException;

    void requestCreateSessionWithRouter2(
            IMediaRouter2 iMediaRouter2,
            int i,
            long j,
            RoutingSessionInfo routingSessionInfo,
            MediaRoute2Info mediaRoute2Info,
            Bundle bundle)
            throws RemoteException;

    void requestSetVolume(IMediaRouterClient iMediaRouterClient, String str, int i)
            throws RemoteException;

    void requestUpdateVolume(IMediaRouterClient iMediaRouterClient, String str, int i)
            throws RemoteException;

    void selectRouteWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info)
            throws RemoteException;

    void selectRouteWithRouter2(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info)
            throws RemoteException;

    void setBluetoothA2dpOn(IMediaRouterClient iMediaRouterClient, boolean z)
            throws RemoteException;

    void setDiscoveryRequest(IMediaRouterClient iMediaRouterClient, int i, boolean z)
            throws RemoteException;

    void setDiscoveryRequestWithRouter2(
            IMediaRouter2 iMediaRouter2, RouteDiscoveryPreference routeDiscoveryPreference)
            throws RemoteException;

    void setRouteListingPreference(
            IMediaRouter2 iMediaRouter2, RouteListingPreference routeListingPreference)
            throws RemoteException;

    void setRouteVolumeWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            MediaRoute2Info mediaRoute2Info,
            int i2)
            throws RemoteException;

    void setRouteVolumeWithRouter2(
            IMediaRouter2 iMediaRouter2, MediaRoute2Info mediaRoute2Info, int i)
            throws RemoteException;

    void setSelectedRoute(IMediaRouterClient iMediaRouterClient, String str, boolean z)
            throws RemoteException;

    void setSessionVolumeWithManager(
            IMediaRouter2Manager iMediaRouter2Manager, int i, String str, int i2)
            throws RemoteException;

    void setSessionVolumeWithRouter2(IMediaRouter2 iMediaRouter2, String str, int i)
            throws RemoteException;

    boolean showMediaOutputSwitcherWithProxyRouter(IMediaRouter2Manager iMediaRouter2Manager)
            throws RemoteException;

    boolean showMediaOutputSwitcherWithRouter2(String str) throws RemoteException;

    void transferToRouteWithManager(
            IMediaRouter2Manager iMediaRouter2Manager,
            int i,
            String str,
            MediaRoute2Info mediaRoute2Info,
            UserHandle userHandle,
            String str2)
            throws RemoteException;

    void transferToRouteWithRouter2(
            IMediaRouter2 iMediaRouter2, String str, MediaRoute2Info mediaRoute2Info)
            throws RemoteException;

    void unregisterClient(IMediaRouterClient iMediaRouterClient) throws RemoteException;

    void unregisterManager(IMediaRouter2Manager iMediaRouter2Manager) throws RemoteException;

    void unregisterRouter2(IMediaRouter2 iMediaRouter2) throws RemoteException;

    void updateScanningState(IMediaRouter2Manager iMediaRouter2Manager, int i)
            throws RemoteException;

    void updateScanningStateWithRouter2(IMediaRouter2 iMediaRouter2, int i) throws RemoteException;

    public static class Default implements IMediaRouterService {
        @Override // android.media.IMediaRouterService
        public void registerClientAsUser(IMediaRouterClient client, String packageName, int userId)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void unregisterClient(IMediaRouterClient client) throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void registerClientGroupId(IMediaRouterClient client, String groupId)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public MediaRouterClientState getState(IMediaRouterClient client) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public boolean isPlaybackActive(IMediaRouterClient client) throws RemoteException {
            return false;
        }

        @Override // android.media.IMediaRouterService
        public void setBluetoothA2dpOn(IMediaRouterClient client, boolean on)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setDiscoveryRequest(
                IMediaRouterClient client, int routeTypes, boolean activeScan)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setSelectedRoute(IMediaRouterClient client, String routeId, boolean explicit)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void requestSetVolume(IMediaRouterClient client, String routeId, int volume)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void requestUpdateVolume(IMediaRouterClient client, String routeId, int direction)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public List<MediaRoute2Info> getSystemRoutes(
                String callerPackageName, boolean isProxyRouter) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public RoutingSessionInfo getSystemSessionInfo() throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public boolean showMediaOutputSwitcherWithRouter2(String packageName)
                throws RemoteException {
            return false;
        }

        @Override // android.media.IMediaRouterService
        public void registerRouter2(IMediaRouter2 router, String packageName)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void unregisterRouter2(IMediaRouter2 router) throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void updateScanningStateWithRouter2(IMediaRouter2 router, int scanningState)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setDiscoveryRequestWithRouter2(
                IMediaRouter2 router, RouteDiscoveryPreference preference) throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setRouteListingPreference(
                IMediaRouter2 router, RouteListingPreference routeListingPreference)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setRouteVolumeWithRouter2(
                IMediaRouter2 router, MediaRoute2Info route, int volume) throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void requestCreateSessionWithRouter2(
                IMediaRouter2 router,
                int requestId,
                long managerRequestId,
                RoutingSessionInfo oldSession,
                MediaRoute2Info route,
                Bundle sessionHints)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void selectRouteWithRouter2(
                IMediaRouter2 router, String sessionId, MediaRoute2Info route)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void deselectRouteWithRouter2(
                IMediaRouter2 router, String sessionId, MediaRoute2Info route)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void transferToRouteWithRouter2(
                IMediaRouter2 router, String sessionId, MediaRoute2Info route)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setSessionVolumeWithRouter2(IMediaRouter2 router, String sessionId, int volume)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void releaseSessionWithRouter2(IMediaRouter2 router, String sessionId)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public List<RoutingSessionInfo> getRemoteSessions(IMediaRouter2Manager manager)
                throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public RoutingSessionInfo getSystemSessionInfoForPackage(
                String callerPackageName, String targetPackageName) throws RemoteException {
            return null;
        }

        @Override // android.media.IMediaRouterService
        public void registerManager(IMediaRouter2Manager manager, String packageName)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void registerProxyRouter(
                IMediaRouter2Manager manager,
                String callingPackageName,
                String targetPackageName,
                UserHandle targetUser)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void unregisterManager(IMediaRouter2Manager manager) throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setRouteVolumeWithManager(
                IMediaRouter2Manager manager, int requestId, MediaRoute2Info route, int volume)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void updateScanningState(IMediaRouter2Manager manager, int scanningState)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void requestCreateSessionWithManager(
                IMediaRouter2Manager manager,
                int requestId,
                RoutingSessionInfo oldSession,
                MediaRoute2Info route,
                UserHandle transferInitiatorUserHandle,
                String transferInitiatorPackageName)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void selectRouteWithManager(
                IMediaRouter2Manager manager,
                int requestId,
                String sessionId,
                MediaRoute2Info route)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void deselectRouteWithManager(
                IMediaRouter2Manager manager,
                int requestId,
                String sessionId,
                MediaRoute2Info route)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void transferToRouteWithManager(
                IMediaRouter2Manager manager,
                int requestId,
                String sessionId,
                MediaRoute2Info route,
                UserHandle transferInitiatorUserHandle,
                String transferInitiatorPackageName)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void setSessionVolumeWithManager(
                IMediaRouter2Manager manager, int requestId, String sessionId, int volume)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public void releaseSessionWithManager(
                IMediaRouter2Manager manager, int requestId, String sessionId)
                throws RemoteException {}

        @Override // android.media.IMediaRouterService
        public boolean showMediaOutputSwitcherWithProxyRouter(IMediaRouter2Manager manager)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IMediaRouterService {
        public static final String DESCRIPTOR = "android.media.IMediaRouterService";
        static final int TRANSACTION_deselectRouteWithManager = 35;
        static final int TRANSACTION_deselectRouteWithRouter2 = 22;
        static final int TRANSACTION_getRemoteSessions = 26;
        static final int TRANSACTION_getState = 4;
        static final int TRANSACTION_getSystemRoutes = 11;
        static final int TRANSACTION_getSystemSessionInfo = 12;
        static final int TRANSACTION_getSystemSessionInfoForPackage = 27;
        static final int TRANSACTION_isPlaybackActive = 5;
        static final int TRANSACTION_registerClientAsUser = 1;
        static final int TRANSACTION_registerClientGroupId = 3;
        static final int TRANSACTION_registerManager = 28;
        static final int TRANSACTION_registerProxyRouter = 29;
        static final int TRANSACTION_registerRouter2 = 14;
        static final int TRANSACTION_releaseSessionWithManager = 38;
        static final int TRANSACTION_releaseSessionWithRouter2 = 25;
        static final int TRANSACTION_requestCreateSessionWithManager = 33;
        static final int TRANSACTION_requestCreateSessionWithRouter2 = 20;
        static final int TRANSACTION_requestSetVolume = 9;
        static final int TRANSACTION_requestUpdateVolume = 10;
        static final int TRANSACTION_selectRouteWithManager = 34;
        static final int TRANSACTION_selectRouteWithRouter2 = 21;
        static final int TRANSACTION_setBluetoothA2dpOn = 6;
        static final int TRANSACTION_setDiscoveryRequest = 7;
        static final int TRANSACTION_setDiscoveryRequestWithRouter2 = 17;
        static final int TRANSACTION_setRouteListingPreference = 18;
        static final int TRANSACTION_setRouteVolumeWithManager = 31;
        static final int TRANSACTION_setRouteVolumeWithRouter2 = 19;
        static final int TRANSACTION_setSelectedRoute = 8;
        static final int TRANSACTION_setSessionVolumeWithManager = 37;
        static final int TRANSACTION_setSessionVolumeWithRouter2 = 24;
        static final int TRANSACTION_showMediaOutputSwitcherWithProxyRouter = 39;
        static final int TRANSACTION_showMediaOutputSwitcherWithRouter2 = 13;
        static final int TRANSACTION_transferToRouteWithManager = 36;
        static final int TRANSACTION_transferToRouteWithRouter2 = 23;
        static final int TRANSACTION_unregisterClient = 2;
        static final int TRANSACTION_unregisterManager = 30;
        static final int TRANSACTION_unregisterRouter2 = 15;
        static final int TRANSACTION_updateScanningState = 32;
        static final int TRANSACTION_updateScanningStateWithRouter2 = 16;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMediaRouterService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IMediaRouterService)) {
                return (IMediaRouterService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
                    return "getRemoteSessions";
                case 27:
                    return "getSystemSessionInfoForPackage";
                case 28:
                    return "registerManager";
                case 29:
                    return "registerProxyRouter";
                case 30:
                    return "unregisterManager";
                case 31:
                    return "setRouteVolumeWithManager";
                case 32:
                    return "updateScanningState";
                case 33:
                    return "requestCreateSessionWithManager";
                case 34:
                    return "selectRouteWithManager";
                case 35:
                    return "deselectRouteWithManager";
                case 36:
                    return "transferToRouteWithManager";
                case 37:
                    return "setSessionVolumeWithManager";
                case 38:
                    return "releaseSessionWithManager";
                case 39:
                    return "showMediaOutputSwitcherWithProxyRouter";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IMediaRouterClient _arg0 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    registerClientAsUser(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    IMediaRouterClient _arg02 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterClient(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    IMediaRouterClient _arg03 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    registerClientGroupId(_arg03, _arg12);
                    reply.writeNoException();
                    return true;
                case 4:
                    IMediaRouterClient _arg04 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    MediaRouterClientState _result = getState(_arg04);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 5:
                    IMediaRouterClient _arg05 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result2 = isPlaybackActive(_arg05);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 6:
                    IMediaRouterClient _arg06 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    boolean _arg13 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBluetoothA2dpOn(_arg06, _arg13);
                    reply.writeNoException();
                    return true;
                case 7:
                    IMediaRouterClient _arg07 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    int _arg14 = data.readInt();
                    boolean _arg22 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setDiscoveryRequest(_arg07, _arg14, _arg22);
                    reply.writeNoException();
                    return true;
                case 8:
                    IMediaRouterClient _arg08 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    String _arg15 = data.readString();
                    boolean _arg23 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSelectedRoute(_arg08, _arg15, _arg23);
                    reply.writeNoException();
                    return true;
                case 9:
                    IMediaRouterClient _arg09 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    String _arg16 = data.readString();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    requestSetVolume(_arg09, _arg16, _arg24);
                    reply.writeNoException();
                    return true;
                case 10:
                    IMediaRouterClient _arg010 =
                            IMediaRouterClient.Stub.asInterface(data.readStrongBinder());
                    String _arg17 = data.readString();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    requestUpdateVolume(_arg010, _arg17, _arg25);
                    reply.writeNoException();
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    boolean _arg18 = data.readBoolean();
                    data.enforceNoDataAvail();
                    List<MediaRoute2Info> _result3 = getSystemRoutes(_arg011, _arg18);
                    reply.writeNoException();
                    reply.writeTypedList(_result3, 1);
                    return true;
                case 12:
                    RoutingSessionInfo _result4 = getSystemSessionInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 13:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result5 = showMediaOutputSwitcherWithRouter2(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 14:
                    IMediaRouter2 _arg013 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    String _arg19 = data.readString();
                    data.enforceNoDataAvail();
                    registerRouter2(_arg013, _arg19);
                    reply.writeNoException();
                    return true;
                case 15:
                    IMediaRouter2 _arg014 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterRouter2(_arg014);
                    reply.writeNoException();
                    return true;
                case 16:
                    IMediaRouter2 _arg015 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    updateScanningStateWithRouter2(_arg015, _arg110);
                    reply.writeNoException();
                    return true;
                case 17:
                    IMediaRouter2 _arg016 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    RouteDiscoveryPreference _arg111 =
                            (RouteDiscoveryPreference)
                                    data.readTypedObject(RouteDiscoveryPreference.CREATOR);
                    data.enforceNoDataAvail();
                    setDiscoveryRequestWithRouter2(_arg016, _arg111);
                    reply.writeNoException();
                    return true;
                case 18:
                    IMediaRouter2 _arg017 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    RouteListingPreference _arg112 =
                            (RouteListingPreference)
                                    data.readTypedObject(RouteListingPreference.CREATOR);
                    data.enforceNoDataAvail();
                    setRouteListingPreference(_arg017, _arg112);
                    reply.writeNoException();
                    return true;
                case 19:
                    IMediaRouter2 _arg018 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    MediaRoute2Info _arg113 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    setRouteVolumeWithRouter2(_arg018, _arg113, _arg26);
                    reply.writeNoException();
                    return true;
                case 20:
                    IMediaRouter2 _arg019 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    int _arg114 = data.readInt();
                    long _arg27 = data.readLong();
                    RoutingSessionInfo _arg3 =
                            (RoutingSessionInfo) data.readTypedObject(RoutingSessionInfo.CREATOR);
                    MediaRoute2Info _arg4 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    Bundle _arg5 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    requestCreateSessionWithRouter2(_arg019, _arg114, _arg27, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    return true;
                case 21:
                    IMediaRouter2 _arg020 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    String _arg115 = data.readString();
                    MediaRoute2Info _arg28 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    data.enforceNoDataAvail();
                    selectRouteWithRouter2(_arg020, _arg115, _arg28);
                    reply.writeNoException();
                    return true;
                case 22:
                    IMediaRouter2 _arg021 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    String _arg116 = data.readString();
                    MediaRoute2Info _arg29 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    data.enforceNoDataAvail();
                    deselectRouteWithRouter2(_arg021, _arg116, _arg29);
                    reply.writeNoException();
                    return true;
                case 23:
                    IMediaRouter2 _arg022 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    String _arg117 = data.readString();
                    MediaRoute2Info _arg210 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    data.enforceNoDataAvail();
                    transferToRouteWithRouter2(_arg022, _arg117, _arg210);
                    reply.writeNoException();
                    return true;
                case 24:
                    IMediaRouter2 _arg023 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    String _arg118 = data.readString();
                    int _arg211 = data.readInt();
                    data.enforceNoDataAvail();
                    setSessionVolumeWithRouter2(_arg023, _arg118, _arg211);
                    reply.writeNoException();
                    return true;
                case 25:
                    IMediaRouter2 _arg024 = IMediaRouter2.Stub.asInterface(data.readStrongBinder());
                    String _arg119 = data.readString();
                    data.enforceNoDataAvail();
                    releaseSessionWithRouter2(_arg024, _arg119);
                    reply.writeNoException();
                    return true;
                case 26:
                    IMediaRouter2Manager _arg025 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    List<RoutingSessionInfo> _result6 = getRemoteSessions(_arg025);
                    reply.writeNoException();
                    reply.writeTypedList(_result6, 1);
                    return true;
                case 27:
                    String _arg026 = data.readString();
                    String _arg120 = data.readString();
                    data.enforceNoDataAvail();
                    RoutingSessionInfo _result7 = getSystemSessionInfoForPackage(_arg026, _arg120);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 28:
                    IMediaRouter2Manager _arg027 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    String _arg121 = data.readString();
                    data.enforceNoDataAvail();
                    registerManager(_arg027, _arg121);
                    reply.writeNoException();
                    return true;
                case 29:
                    IMediaRouter2Manager _arg028 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    String _arg122 = data.readString();
                    String _arg212 = data.readString();
                    UserHandle _arg32 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    data.enforceNoDataAvail();
                    registerProxyRouter(_arg028, _arg122, _arg212, _arg32);
                    reply.writeNoException();
                    return true;
                case 30:
                    IMediaRouter2Manager _arg029 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterManager(_arg029);
                    reply.writeNoException();
                    return true;
                case 31:
                    IMediaRouter2Manager _arg030 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg123 = data.readInt();
                    MediaRoute2Info _arg213 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    setRouteVolumeWithManager(_arg030, _arg123, _arg213, _arg33);
                    reply.writeNoException();
                    return true;
                case 32:
                    IMediaRouter2Manager _arg031 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg124 = data.readInt();
                    data.enforceNoDataAvail();
                    updateScanningState(_arg031, _arg124);
                    reply.writeNoException();
                    return true;
                case 33:
                    IMediaRouter2Manager _arg032 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg125 = data.readInt();
                    RoutingSessionInfo _arg214 =
                            (RoutingSessionInfo) data.readTypedObject(RoutingSessionInfo.CREATOR);
                    MediaRoute2Info _arg34 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    UserHandle _arg42 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    String _arg52 = data.readString();
                    data.enforceNoDataAvail();
                    requestCreateSessionWithManager(
                            _arg032, _arg125, _arg214, _arg34, _arg42, _arg52);
                    reply.writeNoException();
                    return true;
                case 34:
                    IMediaRouter2Manager _arg033 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg126 = data.readInt();
                    String _arg215 = data.readString();
                    MediaRoute2Info _arg35 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    data.enforceNoDataAvail();
                    selectRouteWithManager(_arg033, _arg126, _arg215, _arg35);
                    reply.writeNoException();
                    return true;
                case 35:
                    IMediaRouter2Manager _arg034 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg127 = data.readInt();
                    String _arg216 = data.readString();
                    MediaRoute2Info _arg36 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    data.enforceNoDataAvail();
                    deselectRouteWithManager(_arg034, _arg127, _arg216, _arg36);
                    reply.writeNoException();
                    return true;
                case 36:
                    IMediaRouter2Manager _arg035 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg128 = data.readInt();
                    String _arg217 = data.readString();
                    MediaRoute2Info _arg37 =
                            (MediaRoute2Info) data.readTypedObject(MediaRoute2Info.CREATOR);
                    UserHandle _arg43 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
                    String _arg53 = data.readString();
                    data.enforceNoDataAvail();
                    transferToRouteWithManager(_arg035, _arg128, _arg217, _arg37, _arg43, _arg53);
                    reply.writeNoException();
                    return true;
                case 37:
                    IMediaRouter2Manager _arg036 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg129 = data.readInt();
                    String _arg218 = data.readString();
                    int _arg38 = data.readInt();
                    data.enforceNoDataAvail();
                    setSessionVolumeWithManager(_arg036, _arg129, _arg218, _arg38);
                    reply.writeNoException();
                    return true;
                case 38:
                    IMediaRouter2Manager _arg037 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    int _arg130 = data.readInt();
                    String _arg219 = data.readString();
                    data.enforceNoDataAvail();
                    releaseSessionWithManager(_arg037, _arg130, _arg219);
                    reply.writeNoException();
                    return true;
                case 39:
                    IMediaRouter2Manager _arg038 =
                            IMediaRouter2Manager.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result8 = showMediaOutputSwitcherWithProxyRouter(_arg038);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IMediaRouterService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.IMediaRouterService
            public void registerClientAsUser(
                    IMediaRouterClient client, String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterClient(IMediaRouterClient client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerClientGroupId(IMediaRouterClient client, String groupId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeString(groupId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public MediaRouterClientState getState(IMediaRouterClient client)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    MediaRouterClientState _result =
                            (MediaRouterClientState)
                                    _reply.readTypedObject(MediaRouterClientState.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean isPlaybackActive(IMediaRouterClient client) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setBluetoothA2dpOn(IMediaRouterClient client, boolean on)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeBoolean(on);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequest(
                    IMediaRouterClient client, int routeTypes, boolean activeScan)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(routeTypes);
                    _data.writeBoolean(activeScan);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSelectedRoute(
                    IMediaRouterClient client, String routeId, boolean explicit)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeString(routeId);
                    _data.writeBoolean(explicit);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestSetVolume(IMediaRouterClient client, String routeId, int volume)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeString(routeId);
                    _data.writeInt(volume);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestUpdateVolume(
                    IMediaRouterClient client, String routeId, int direction)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeString(routeId);
                    _data.writeInt(direction);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public List<MediaRoute2Info> getSystemRoutes(
                    String callerPackageName, boolean isProxyRouter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeBoolean(isProxyRouter);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    List<MediaRoute2Info> _result =
                            _reply.createTypedArrayList(MediaRoute2Info.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public RoutingSessionInfo getSystemSessionInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    RoutingSessionInfo _result =
                            (RoutingSessionInfo) _reply.readTypedObject(RoutingSessionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean showMediaOutputSwitcherWithRouter2(String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerRouter2(IMediaRouter2 router, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeString(packageName);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterRouter2(IMediaRouter2 router) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningStateWithRouter2(IMediaRouter2 router, int scanningState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeInt(scanningState);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setDiscoveryRequestWithRouter2(
                    IMediaRouter2 router, RouteDiscoveryPreference preference)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeTypedObject(preference, 0);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteListingPreference(
                    IMediaRouter2 router, RouteListingPreference routeListingPreference)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeTypedObject(routeListingPreference, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithRouter2(
                    IMediaRouter2 router, MediaRoute2Info route, int volume)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeTypedObject(route, 0);
                    _data.writeInt(volume);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithRouter2(
                    IMediaRouter2 router,
                    int requestId,
                    long managerRequestId,
                    RoutingSessionInfo oldSession,
                    MediaRoute2Info route,
                    Bundle sessionHints)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeInt(requestId);
                    _data.writeLong(managerRequestId);
                    _data.writeTypedObject(oldSession, 0);
                    _data.writeTypedObject(route, 0);
                    _data.writeTypedObject(sessionHints, 0);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithRouter2(
                    IMediaRouter2 router, String sessionId, MediaRoute2Info route)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeString(sessionId);
                    _data.writeTypedObject(route, 0);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithRouter2(
                    IMediaRouter2 router, String sessionId, MediaRoute2Info route)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeString(sessionId);
                    _data.writeTypedObject(route, 0);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithRouter2(
                    IMediaRouter2 router, String sessionId, MediaRoute2Info route)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeString(sessionId);
                    _data.writeTypedObject(route, 0);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithRouter2(
                    IMediaRouter2 router, String sessionId, int volume) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeString(sessionId);
                    _data.writeInt(volume);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithRouter2(IMediaRouter2 router, String sessionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(router);
                    _data.writeString(sessionId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public List<RoutingSessionInfo> getRemoteSessions(IMediaRouter2Manager manager)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    List<RoutingSessionInfo> _result =
                            _reply.createTypedArrayList(RoutingSessionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public RoutingSessionInfo getSystemSessionInfoForPackage(
                    String callerPackageName, String targetPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callerPackageName);
                    _data.writeString(targetPackageName);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    RoutingSessionInfo _result =
                            (RoutingSessionInfo) _reply.readTypedObject(RoutingSessionInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerManager(IMediaRouter2Manager manager, String packageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeString(packageName);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void registerProxyRouter(
                    IMediaRouter2Manager manager,
                    String callingPackageName,
                    String targetPackageName,
                    UserHandle targetUser)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeString(callingPackageName);
                    _data.writeString(targetPackageName);
                    _data.writeTypedObject(targetUser, 0);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void unregisterManager(IMediaRouter2Manager manager) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setRouteVolumeWithManager(
                    IMediaRouter2Manager manager, int requestId, MediaRoute2Info route, int volume)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(requestId);
                    _data.writeTypedObject(route, 0);
                    _data.writeInt(volume);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void updateScanningState(IMediaRouter2Manager manager, int scanningState)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(scanningState);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void requestCreateSessionWithManager(
                    IMediaRouter2Manager manager,
                    int requestId,
                    RoutingSessionInfo oldSession,
                    MediaRoute2Info route,
                    UserHandle transferInitiatorUserHandle,
                    String transferInitiatorPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(requestId);
                    _data.writeTypedObject(oldSession, 0);
                    _data.writeTypedObject(route, 0);
                    _data.writeTypedObject(transferInitiatorUserHandle, 0);
                    _data.writeString(transferInitiatorPackageName);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void selectRouteWithManager(
                    IMediaRouter2Manager manager,
                    int requestId,
                    String sessionId,
                    MediaRoute2Info route)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(requestId);
                    _data.writeString(sessionId);
                    _data.writeTypedObject(route, 0);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void deselectRouteWithManager(
                    IMediaRouter2Manager manager,
                    int requestId,
                    String sessionId,
                    MediaRoute2Info route)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(requestId);
                    _data.writeString(sessionId);
                    _data.writeTypedObject(route, 0);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void transferToRouteWithManager(
                    IMediaRouter2Manager manager,
                    int requestId,
                    String sessionId,
                    MediaRoute2Info route,
                    UserHandle transferInitiatorUserHandle,
                    String transferInitiatorPackageName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(requestId);
                    _data.writeString(sessionId);
                    _data.writeTypedObject(route, 0);
                    _data.writeTypedObject(transferInitiatorUserHandle, 0);
                    _data.writeString(transferInitiatorPackageName);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void setSessionVolumeWithManager(
                    IMediaRouter2Manager manager, int requestId, String sessionId, int volume)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(requestId);
                    _data.writeString(sessionId);
                    _data.writeInt(volume);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public void releaseSessionWithManager(
                    IMediaRouter2Manager manager, int requestId, String sessionId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    _data.writeInt(requestId);
                    _data.writeString(sessionId);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.media.IMediaRouterService
            public boolean showMediaOutputSwitcherWithProxyRouter(IMediaRouter2Manager manager)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(manager);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }
    }
}
