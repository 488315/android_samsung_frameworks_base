package com.android.internal.appwidget;

import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public interface IAppWidgetService extends IInterface {
    int allocateAppWidgetId(String str, int i) throws RemoteException;

    boolean bindAppWidgetId(String str, int i, int i2, ComponentName componentName, Bundle bundle)
            throws RemoteException;

    boolean bindRemoteViewsService(
            String str,
            int i,
            Intent intent,
            IApplicationThread iApplicationThread,
            IBinder iBinder,
            IServiceConnection iServiceConnection,
            long j)
            throws RemoteException;

    void changeHostIds(String str, int[] iArr, int i) throws RemoteException;

    IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2)
            throws RemoteException;

    void deleteAllHosts() throws RemoteException;

    void deleteAppWidgetId(String str, int i) throws RemoteException;

    void deleteHost(String str, int i) throws RemoteException;

    List<AppWidgetProviderInfo> getAllProvidersForProfile(int i, int i2, boolean z)
            throws RemoteException;

    Map getAllWidgets(String str, int i) throws RemoteException;

    int[] getAppWidgetIds(ComponentName componentName) throws RemoteException;

    int[] getAppWidgetIdsForHost(String str, int i) throws RemoteException;

    AppWidgetProviderInfo getAppWidgetInfo(String str, int i) throws RemoteException;

    Bundle getAppWidgetOptions(String str, int i) throws RemoteException;

    RemoteViews getAppWidgetViews(String str, int i) throws RemoteException;

    ParceledListSlice getInstalledProvidersForProfile(int i, int i2, String str)
            throws RemoteException;

    Bundle getTemplateWidgetPreview(String str, ComponentName componentName, int i, int i2, int i3)
            throws RemoteException;

    RemoteViews getWidgetPreview(String str, ComponentName componentName, int i, int i2)
            throws RemoteException;

    boolean hasBindAppWidgetPermission(String str, int i) throws RemoteException;

    boolean isBoundWidgetPackage(String str, int i) throws RemoteException;

    boolean isRequestPinAppWidgetSupported() throws RemoteException;

    boolean isTemplatePreviewUpdateAvailable(ComponentName componentName) throws RemoteException;

    void noteAppWidgetTapped(String str, int i) throws RemoteException;

    void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) throws RemoteException;

    void notifyProviderInheritance(ComponentName[] componentNameArr) throws RemoteException;

    void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews)
            throws RemoteException;

    void removeTemplateWidgetPreview(ComponentName componentName, int i, int i2)
            throws RemoteException;

    void removeWidgetPreview(ComponentName componentName, int i) throws RemoteException;

    boolean requestPinAppWidget(
            String str, ComponentName componentName, Bundle bundle, IntentSender intentSender)
            throws RemoteException;

    IntentSender semCreateAppWidgetConfigIntentSender(String str, int i, int i2)
            throws RemoteException;

    void semSetSkipPackageChanged(String str) throws RemoteException;

    void setAppWidgetHidden(String str, int i) throws RemoteException;

    void setBindAppWidgetPermission(String str, int i, boolean z) throws RemoteException;

    boolean setTemplateWidgetPreview(
            ComponentName componentName, int i, int i2, RemoteViews[] remoteViewsArr)
            throws RemoteException;

    boolean setWidgetPreview(ComponentName componentName, int i, RemoteViews remoteViews)
            throws RemoteException;

    ParceledListSlice startListening(IAppWidgetHost iAppWidgetHost, String str, int i, int[] iArr)
            throws RemoteException;

    void stopListening(String str, int i) throws RemoteException;

    void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException;

    void updateAppWidgetOptions(String str, int i, Bundle bundle) throws RemoteException;

    void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews)
            throws RemoteException;

    void updateAppWidgetProviderInfo(ComponentName componentName, String str)
            throws RemoteException;

    public static class Default implements IAppWidgetService {
        @Override // com.android.internal.appwidget.IAppWidgetService
        public ParceledListSlice startListening(
                IAppWidgetHost host, String callingPackage, int hostId, int[] appWidgetIds)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void stopListening(String callingPackage, int hostId) throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int allocateAppWidgetId(String callingPackage, int hostId) throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteAppWidgetId(String callingPackage, int appWidgetId)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteHost(String packageName, int hostId) throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteAllHosts() throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public RemoteViews getAppWidgetViews(String callingPackage, int appWidgetId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int[] getAppWidgetIdsForHost(String callingPackage, int hostId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void setAppWidgetHidden(String callingPackage, int hostId) throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public IntentSender createAppWidgetConfigIntentSender(
                String callingPackage, int appWidgetId, int intentFlags) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public IntentSender semCreateAppWidgetConfigIntentSender(
                String callingPackage, int appWidgetId, int intentFlags) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void semSetSkipPackageChanged(String packageName) throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void changeHostIds(String callingPackage, int[] appWidgetIds, int hostId)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetIds(String callingPackage, int[] appWidgetIds, RemoteViews views)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetOptions(String callingPackage, int appWidgetId, Bundle extras)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public Bundle getAppWidgetOptions(String callingPackage, int appWidgetId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void partiallyUpdateAppWidgetIds(
                String callingPackage, int[] appWidgetIds, RemoteViews views)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetProvider(ComponentName provider, RemoteViews views)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetProviderInfo(ComponentName provider, String metadataKey)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void notifyAppWidgetViewDataChanged(
                String packageName, int[] appWidgetIds, int viewId) throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public ParceledListSlice getInstalledProvidersForProfile(
                int categoryFilter, int profileId, String packageName) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public AppWidgetProviderInfo getAppWidgetInfo(String callingPackage, int appWidgetId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean hasBindAppWidgetPermission(String packageName, int userId)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void setBindAppWidgetPermission(String packageName, int userId, boolean permission)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean bindAppWidgetId(
                String callingPackage,
                int appWidgetId,
                int providerProfileId,
                ComponentName providerComponent,
                Bundle options)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean bindRemoteViewsService(
                String callingPackage,
                int appWidgetId,
                Intent intent,
                IApplicationThread caller,
                IBinder token,
                IServiceConnection connection,
                long flags)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void notifyProviderInheritance(ComponentName[] componentNames)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int[] getAppWidgetIds(ComponentName providerComponent) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isBoundWidgetPackage(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean requestPinAppWidget(
                String packageName,
                ComponentName providerComponent,
                Bundle extras,
                IntentSender resultIntent)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isRequestPinAppWidgetSupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void noteAppWidgetTapped(String callingPackage, int appWidgetId)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public Map getAllWidgets(String pkgName, int userId) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public List<AppWidgetProviderInfo> getAllProvidersForProfile(
                int categoryFilter, int profileId, boolean checkCrossProfile)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean setWidgetPreview(
                ComponentName providerComponent, int widgetCategories, RemoteViews preview)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public RemoteViews getWidgetPreview(
                String callingPackage,
                ComponentName providerComponent,
                int profileId,
                int widgetCategory)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void removeWidgetPreview(ComponentName providerComponent, int widgetCategories)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean setTemplateWidgetPreview(
                ComponentName providerComponent,
                int templateSize,
                int templateStyle,
                RemoteViews[] preview)
                throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public Bundle getTemplateWidgetPreview(
                String callingPackage,
                ComponentName providerComponent,
                int profileId,
                int templateSize,
                int templateStyle)
                throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void removeTemplateWidgetPreview(
                ComponentName providerComponent, int templateSize, int templateStyle)
                throws RemoteException {}

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isTemplatePreviewUpdateAvailable(ComponentName providerComponent)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAppWidgetService {
        public static final String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetService";
        static final int TRANSACTION_allocateAppWidgetId = 3;
        static final int TRANSACTION_bindAppWidgetId = 25;
        static final int TRANSACTION_bindRemoteViewsService = 26;
        static final int TRANSACTION_changeHostIds = 13;
        static final int TRANSACTION_createAppWidgetConfigIntentSender = 10;
        static final int TRANSACTION_deleteAllHosts = 6;
        static final int TRANSACTION_deleteAppWidgetId = 4;
        static final int TRANSACTION_deleteHost = 5;
        static final int TRANSACTION_getAllProvidersForProfile = 34;
        static final int TRANSACTION_getAllWidgets = 33;
        static final int TRANSACTION_getAppWidgetIds = 28;
        static final int TRANSACTION_getAppWidgetIdsForHost = 8;
        static final int TRANSACTION_getAppWidgetInfo = 22;
        static final int TRANSACTION_getAppWidgetOptions = 16;
        static final int TRANSACTION_getAppWidgetViews = 7;
        static final int TRANSACTION_getInstalledProvidersForProfile = 21;
        static final int TRANSACTION_getTemplateWidgetPreview = 39;
        static final int TRANSACTION_getWidgetPreview = 36;
        static final int TRANSACTION_hasBindAppWidgetPermission = 23;
        static final int TRANSACTION_isBoundWidgetPackage = 29;
        static final int TRANSACTION_isRequestPinAppWidgetSupported = 31;
        static final int TRANSACTION_isTemplatePreviewUpdateAvailable = 41;
        static final int TRANSACTION_noteAppWidgetTapped = 32;
        static final int TRANSACTION_notifyAppWidgetViewDataChanged = 20;
        static final int TRANSACTION_notifyProviderInheritance = 27;
        static final int TRANSACTION_partiallyUpdateAppWidgetIds = 17;
        static final int TRANSACTION_removeTemplateWidgetPreview = 40;
        static final int TRANSACTION_removeWidgetPreview = 37;
        static final int TRANSACTION_requestPinAppWidget = 30;
        static final int TRANSACTION_semCreateAppWidgetConfigIntentSender = 11;
        static final int TRANSACTION_semSetSkipPackageChanged = 12;
        static final int TRANSACTION_setAppWidgetHidden = 9;
        static final int TRANSACTION_setBindAppWidgetPermission = 24;
        static final int TRANSACTION_setTemplateWidgetPreview = 38;
        static final int TRANSACTION_setWidgetPreview = 35;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;
        static final int TRANSACTION_updateAppWidgetIds = 14;
        static final int TRANSACTION_updateAppWidgetOptions = 15;
        static final int TRANSACTION_updateAppWidgetProvider = 18;
        static final int TRANSACTION_updateAppWidgetProviderInfo = 19;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppWidgetService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAppWidgetService)) {
                return (IAppWidgetService) iin;
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
                    return "startListening";
                case 2:
                    return "stopListening";
                case 3:
                    return "allocateAppWidgetId";
                case 4:
                    return "deleteAppWidgetId";
                case 5:
                    return "deleteHost";
                case 6:
                    return "deleteAllHosts";
                case 7:
                    return "getAppWidgetViews";
                case 8:
                    return "getAppWidgetIdsForHost";
                case 9:
                    return "setAppWidgetHidden";
                case 10:
                    return "createAppWidgetConfigIntentSender";
                case 11:
                    return "semCreateAppWidgetConfigIntentSender";
                case 12:
                    return "semSetSkipPackageChanged";
                case 13:
                    return "changeHostIds";
                case 14:
                    return "updateAppWidgetIds";
                case 15:
                    return "updateAppWidgetOptions";
                case 16:
                    return "getAppWidgetOptions";
                case 17:
                    return "partiallyUpdateAppWidgetIds";
                case 18:
                    return "updateAppWidgetProvider";
                case 19:
                    return "updateAppWidgetProviderInfo";
                case 20:
                    return "notifyAppWidgetViewDataChanged";
                case 21:
                    return "getInstalledProvidersForProfile";
                case 22:
                    return "getAppWidgetInfo";
                case 23:
                    return "hasBindAppWidgetPermission";
                case 24:
                    return "setBindAppWidgetPermission";
                case 25:
                    return "bindAppWidgetId";
                case 26:
                    return "bindRemoteViewsService";
                case 27:
                    return "notifyProviderInheritance";
                case 28:
                    return "getAppWidgetIds";
                case 29:
                    return "isBoundWidgetPackage";
                case 30:
                    return "requestPinAppWidget";
                case 31:
                    return "isRequestPinAppWidgetSupported";
                case 32:
                    return "noteAppWidgetTapped";
                case 33:
                    return "getAllWidgets";
                case 34:
                    return "getAllProvidersForProfile";
                case 35:
                    return "setWidgetPreview";
                case 36:
                    return "getWidgetPreview";
                case 37:
                    return "removeWidgetPreview";
                case 38:
                    return "setTemplateWidgetPreview";
                case 39:
                    return "getTemplateWidgetPreview";
                case 40:
                    return "removeTemplateWidgetPreview";
                case 41:
                    return "isTemplatePreviewUpdateAvailable";
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
                    IAppWidgetHost _arg0 = IAppWidgetHost.Stub.asInterface(data.readStrongBinder());
                    String _arg1 = data.readString();
                    int _arg2 = data.readInt();
                    int[] _arg3 = data.createIntArray();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result = startListening(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeTypedObject(_result, 1);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    stopListening(_arg02, _arg12);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result2 = allocateAppWidgetId(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    deleteAppWidgetId(_arg04, _arg14);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    deleteHost(_arg05, _arg15);
                    reply.writeNoException();
                    return true;
                case 6:
                    deleteAllHosts();
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg06 = data.readString();
                    int _arg16 = data.readInt();
                    data.enforceNoDataAvail();
                    RemoteViews _result3 = getAppWidgetViews(_arg06, _arg16);
                    reply.writeNoException();
                    reply.writeTypedObject(_result3, 1);
                    return true;
                case 8:
                    String _arg07 = data.readString();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result4 = getAppWidgetIdsForHost(_arg07, _arg17);
                    reply.writeNoException();
                    reply.writeIntArray(_result4);
                    return true;
                case 9:
                    String _arg08 = data.readString();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    setAppWidgetHidden(_arg08, _arg18);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg09 = data.readString();
                    int _arg19 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    IntentSender _result5 =
                            createAppWidgetConfigIntentSender(_arg09, _arg19, _arg22);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 11:
                    String _arg010 = data.readString();
                    int _arg110 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    IntentSender _result6 =
                            semCreateAppWidgetConfigIntentSender(_arg010, _arg110, _arg23);
                    reply.writeNoException();
                    reply.writeTypedObject(_result6, 1);
                    return true;
                case 12:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    semSetSkipPackageChanged(_arg011);
                    reply.writeNoException();
                    return true;
                case 13:
                    String _arg012 = data.readString();
                    int[] _arg111 = data.createIntArray();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    changeHostIds(_arg012, _arg111, _arg24);
                    reply.writeNoException();
                    return true;
                case 14:
                    String _arg013 = data.readString();
                    int[] _arg112 = data.createIntArray();
                    RemoteViews _arg25 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                    data.enforceNoDataAvail();
                    updateAppWidgetIds(_arg013, _arg112, _arg25);
                    reply.writeNoException();
                    return true;
                case 15:
                    String _arg014 = data.readString();
                    int _arg113 = data.readInt();
                    Bundle _arg26 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    updateAppWidgetOptions(_arg014, _arg113, _arg26);
                    reply.writeNoException();
                    return true;
                case 16:
                    String _arg015 = data.readString();
                    int _arg114 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result7 = getAppWidgetOptions(_arg015, _arg114);
                    reply.writeNoException();
                    reply.writeTypedObject(_result7, 1);
                    return true;
                case 17:
                    String _arg016 = data.readString();
                    int[] _arg115 = data.createIntArray();
                    RemoteViews _arg27 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                    data.enforceNoDataAvail();
                    partiallyUpdateAppWidgetIds(_arg016, _arg115, _arg27);
                    reply.writeNoException();
                    return true;
                case 18:
                    ComponentName _arg017 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    RemoteViews _arg116 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                    data.enforceNoDataAvail();
                    updateAppWidgetProvider(_arg017, _arg116);
                    reply.writeNoException();
                    return true;
                case 19:
                    ComponentName _arg018 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    String _arg117 = data.readString();
                    data.enforceNoDataAvail();
                    updateAppWidgetProviderInfo(_arg018, _arg117);
                    reply.writeNoException();
                    return true;
                case 20:
                    String _arg019 = data.readString();
                    int[] _arg118 = data.createIntArray();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyAppWidgetViewDataChanged(_arg019, _arg118, _arg28);
                    reply.writeNoException();
                    return true;
                case 21:
                    int _arg020 = data.readInt();
                    int _arg119 = data.readInt();
                    String _arg29 = data.readString();
                    data.enforceNoDataAvail();
                    ParceledListSlice _result8 =
                            getInstalledProvidersForProfile(_arg020, _arg119, _arg29);
                    reply.writeNoException();
                    reply.writeTypedObject(_result8, 1);
                    return true;
                case 22:
                    String _arg021 = data.readString();
                    int _arg120 = data.readInt();
                    data.enforceNoDataAvail();
                    AppWidgetProviderInfo _result9 = getAppWidgetInfo(_arg021, _arg120);
                    reply.writeNoException();
                    reply.writeTypedObject(_result9, 1);
                    return true;
                case 23:
                    String _arg022 = data.readString();
                    int _arg121 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result10 = hasBindAppWidgetPermission(_arg022, _arg121);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 24:
                    String _arg023 = data.readString();
                    int _arg122 = data.readInt();
                    boolean _arg210 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setBindAppWidgetPermission(_arg023, _arg122, _arg210);
                    reply.writeNoException();
                    return true;
                case 25:
                    String _arg024 = data.readString();
                    int _arg123 = data.readInt();
                    int _arg211 = data.readInt();
                    ComponentName _arg32 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    Bundle _arg4 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result11 = bindAppWidgetId(_arg024, _arg123, _arg211, _arg32, _arg4);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 26:
                    String _arg025 = data.readString();
                    int _arg124 = data.readInt();
                    Intent _arg212 = (Intent) data.readTypedObject(Intent.CREATOR);
                    IApplicationThread _arg33 =
                            IApplicationThread.Stub.asInterface(data.readStrongBinder());
                    IBinder _arg42 = data.readStrongBinder();
                    IServiceConnection _arg5 =
                            IServiceConnection.Stub.asInterface(data.readStrongBinder());
                    long _arg6 = data.readLong();
                    data.enforceNoDataAvail();
                    boolean _result12 =
                            bindRemoteViewsService(
                                    _arg025, _arg124, _arg212, _arg33, _arg42, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 27:
                    ComponentName[] _arg026 =
                            (ComponentName[]) data.createTypedArray(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    notifyProviderInheritance(_arg026);
                    reply.writeNoException();
                    return true;
                case 28:
                    ComponentName _arg027 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    int[] _result13 = getAppWidgetIds(_arg027);
                    reply.writeNoException();
                    reply.writeIntArray(_result13);
                    return true;
                case 29:
                    String _arg028 = data.readString();
                    int _arg125 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result14 = isBoundWidgetPackage(_arg028, _arg125);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 30:
                    String _arg029 = data.readString();
                    ComponentName _arg126 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    Bundle _arg213 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    IntentSender _arg34 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result15 = requestPinAppWidget(_arg029, _arg126, _arg213, _arg34);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 31:
                    boolean _result16 = isRequestPinAppWidgetSupported();
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 32:
                    String _arg030 = data.readString();
                    int _arg127 = data.readInt();
                    data.enforceNoDataAvail();
                    noteAppWidgetTapped(_arg030, _arg127);
                    return true;
                case 33:
                    String _arg031 = data.readString();
                    int _arg128 = data.readInt();
                    data.enforceNoDataAvail();
                    Map _result17 = getAllWidgets(_arg031, _arg128);
                    reply.writeNoException();
                    reply.writeMap(_result17);
                    return true;
                case 34:
                    int _arg032 = data.readInt();
                    int _arg129 = data.readInt();
                    boolean _arg214 = data.readBoolean();
                    data.enforceNoDataAvail();
                    List<AppWidgetProviderInfo> _result18 =
                            getAllProvidersForProfile(_arg032, _arg129, _arg214);
                    reply.writeNoException();
                    reply.writeTypedList(_result18, 1);
                    return true;
                case 35:
                    ComponentName _arg033 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg130 = data.readInt();
                    RemoteViews _arg215 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result19 = setWidgetPreview(_arg033, _arg130, _arg215);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 36:
                    String _arg034 = data.readString();
                    ComponentName _arg131 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg216 = data.readInt();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    RemoteViews _result20 = getWidgetPreview(_arg034, _arg131, _arg216, _arg35);
                    reply.writeNoException();
                    reply.writeTypedObject(_result20, 1);
                    return true;
                case 37:
                    ComponentName _arg035 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg132 = data.readInt();
                    data.enforceNoDataAvail();
                    removeWidgetPreview(_arg035, _arg132);
                    reply.writeNoException();
                    return true;
                case 38:
                    ComponentName _arg036 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg133 = data.readInt();
                    int _arg217 = data.readInt();
                    RemoteViews[] _arg36 =
                            (RemoteViews[]) data.createTypedArray(RemoteViews.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result21 = setTemplateWidgetPreview(_arg036, _arg133, _arg217, _arg36);
                    reply.writeNoException();
                    reply.writeBoolean(_result21);
                    return true;
                case 39:
                    String _arg037 = data.readString();
                    ComponentName _arg134 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg218 = data.readInt();
                    int _arg37 = data.readInt();
                    int _arg43 = data.readInt();
                    data.enforceNoDataAvail();
                    Bundle _result22 =
                            getTemplateWidgetPreview(_arg037, _arg134, _arg218, _arg37, _arg43);
                    reply.writeNoException();
                    reply.writeTypedObject(_result22, 1);
                    return true;
                case 40:
                    ComponentName _arg038 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg135 = data.readInt();
                    int _arg219 = data.readInt();
                    data.enforceNoDataAvail();
                    removeTemplateWidgetPreview(_arg038, _arg135, _arg219);
                    reply.writeNoException();
                    return true;
                case 41:
                    ComponentName _arg039 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result23 = isTemplatePreviewUpdateAvailable(_arg039);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAppWidgetService {
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

            @Override // com.android.internal.appwidget.IAppWidgetService
            public ParceledListSlice startListening(
                    IAppWidgetHost host, String callingPackage, int hostId, int[] appWidgetIds)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(host);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    _data.writeIntArray(appWidgetIds);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void stopListening(String callingPackage, int hostId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int allocateAppWidgetId(String callingPackage, int hostId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAppWidgetId(String callingPackage, int appWidgetId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteHost(String packageName, int hostId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(hostId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAllHosts() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public RemoteViews getAppWidgetViews(String callingPackage, int appWidgetId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    RemoteViews _result = (RemoteViews) _reply.readTypedObject(RemoteViews.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIdsForHost(String callingPackage, int hostId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setAppWidgetHidden(String callingPackage, int hostId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(hostId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public IntentSender createAppWidgetConfigIntentSender(
                    String callingPackage, int appWidgetId, int intentFlags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    _data.writeInt(intentFlags);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    IntentSender _result =
                            (IntentSender) _reply.readTypedObject(IntentSender.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public IntentSender semCreateAppWidgetConfigIntentSender(
                    String callingPackage, int appWidgetId, int intentFlags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    _data.writeInt(intentFlags);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    IntentSender _result =
                            (IntentSender) _reply.readTypedObject(IntentSender.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void semSetSkipPackageChanged(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void changeHostIds(String callingPackage, int[] appWidgetIds, int hostId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeIntArray(appWidgetIds);
                    _data.writeInt(hostId);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetIds(
                    String callingPackage, int[] appWidgetIds, RemoteViews views)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeIntArray(appWidgetIds);
                    _data.writeTypedObject(views, 0);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetOptions(
                    String callingPackage, int appWidgetId, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Bundle getAppWidgetOptions(String callingPackage, int appWidgetId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void partiallyUpdateAppWidgetIds(
                    String callingPackage, int[] appWidgetIds, RemoteViews views)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeIntArray(appWidgetIds);
                    _data.writeTypedObject(views, 0);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProvider(ComponentName provider, RemoteViews views)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(provider, 0);
                    _data.writeTypedObject(views, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProviderInfo(ComponentName provider, String metadataKey)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(provider, 0);
                    _data.writeString(metadataKey);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyAppWidgetViewDataChanged(
                    String packageName, int[] appWidgetIds, int viewId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeIntArray(appWidgetIds);
                    _data.writeInt(viewId);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public ParceledListSlice getInstalledProvidersForProfile(
                    int categoryFilter, int profileId, String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(categoryFilter);
                    _data.writeInt(profileId);
                    _data.writeString(packageName);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    ParceledListSlice _result =
                            (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public AppWidgetProviderInfo getAppWidgetInfo(String callingPackage, int appWidgetId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    AppWidgetProviderInfo _result =
                            (AppWidgetProviderInfo)
                                    _reply.readTypedObject(AppWidgetProviderInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean hasBindAppWidgetPermission(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setBindAppWidgetPermission(
                    String packageName, int userId, boolean permission) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeBoolean(permission);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindAppWidgetId(
                    String callingPackage,
                    int appWidgetId,
                    int providerProfileId,
                    ComponentName providerComponent,
                    Bundle options)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    _data.writeInt(providerProfileId);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeTypedObject(options, 0);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindRemoteViewsService(
                    String callingPackage,
                    int appWidgetId,
                    Intent intent,
                    IApplicationThread caller,
                    IBinder token,
                    IServiceConnection connection,
                    long flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongInterface(caller);
                    _data.writeStrongBinder(token);
                    _data.writeStrongInterface(connection);
                    _data.writeLong(flags);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyProviderInheritance(ComponentName[] componentNames)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(componentNames, 0);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIds(ComponentName providerComponent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(providerComponent, 0);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isBoundWidgetPackage(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean requestPinAppWidget(
                    String packageName,
                    ComponentName providerComponent,
                    Bundle extras,
                    IntentSender resultIntent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeTypedObject(extras, 0);
                    _data.writeTypedObject(resultIntent, 0);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isRequestPinAppWidgetSupported() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void noteAppWidgetTapped(String callingPackage, int appWidgetId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(appWidgetId);
                    this.mRemote.transact(32, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Map getAllWidgets(String pkgName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeInt(userId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    ClassLoader cl = getClass().getClassLoader();
                    Map _result = _reply.readHashMap(cl);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public List<AppWidgetProviderInfo> getAllProvidersForProfile(
                    int categoryFilter, int profileId, boolean checkCrossProfile)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(categoryFilter);
                    _data.writeInt(profileId);
                    _data.writeBoolean(checkCrossProfile);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    List<AppWidgetProviderInfo> _result =
                            _reply.createTypedArrayList(AppWidgetProviderInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean setWidgetPreview(
                    ComponentName providerComponent, int widgetCategories, RemoteViews preview)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeInt(widgetCategories);
                    _data.writeTypedObject(preview, 0);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public RemoteViews getWidgetPreview(
                    String callingPackage,
                    ComponentName providerComponent,
                    int profileId,
                    int widgetCategory)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeInt(profileId);
                    _data.writeInt(widgetCategory);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    RemoteViews _result = (RemoteViews) _reply.readTypedObject(RemoteViews.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void removeWidgetPreview(ComponentName providerComponent, int widgetCategories)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeInt(widgetCategories);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean setTemplateWidgetPreview(
                    ComponentName providerComponent,
                    int templateSize,
                    int templateStyle,
                    RemoteViews[] preview)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeInt(templateSize);
                    _data.writeInt(templateStyle);
                    _data.writeTypedArray(preview, 0);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Bundle getTemplateWidgetPreview(
                    String callingPackage,
                    ComponentName providerComponent,
                    int profileId,
                    int templateSize,
                    int templateStyle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeInt(profileId);
                    _data.writeInt(templateSize);
                    _data.writeInt(templateStyle);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void removeTemplateWidgetPreview(
                    ComponentName providerComponent, int templateSize, int templateStyle)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(providerComponent, 0);
                    _data.writeInt(templateSize);
                    _data.writeInt(templateStyle);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isTemplatePreviewUpdateAvailable(ComponentName providerComponent)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(providerComponent, 0);
                    this.mRemote.transact(41, _data, _reply, 0);
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
            return 40;
        }
    }
}
