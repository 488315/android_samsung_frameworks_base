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
import com.android.internal.appwidget.IAppWidgetHost;
import java.util.List;
import java.util.Map;

/* loaded from: classes5.dex */
public interface IAppWidgetService extends IInterface {

    public static class Default implements IAppWidgetService {
        @Override // com.android.internal.appwidget.IAppWidgetService
        public int allocateAppWidgetId(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean bindAppWidgetId(String str, int i, int i2, ComponentName componentName, Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, long j) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void changeHostIds(String str, int[] iArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteAllHosts() throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteAppWidgetId(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void deleteHost(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public List<AppWidgetProviderInfo> getAllProvidersForProfile(int i, int i2, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public Map getAllWidgets(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int[] getAppWidgetIds(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int[] getAppWidgetIdsForHost(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public AppWidgetProviderInfo getAppWidgetInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public Bundle getAppWidgetOptions(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public RemoteViews getAppWidgetViews(String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public ParceledListSlice getInstalledProvidersForProfile(int i, int i2, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public int getMaxBitmapMemory() throws RemoteException {
            return 0;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public Bundle getTemplateWidgetPreview(String str, ComponentName componentName, int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public RemoteViews getWidgetPreview(String str, ComponentName componentName, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean hasBindAppWidgetPermission(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isBoundWidgetPackage(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isRequestPinAppWidgetSupported() throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean isTemplatePreviewUpdateAvailable(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void noteAppWidgetTapped(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void notifyProviderInheritance(ComponentName[] componentNameArr) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void removeTemplateWidgetPreview(ComponentName componentName, int i, int i2) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void removeWidgetPreview(ComponentName componentName, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean requestPinAppWidget(String str, ComponentName componentName, Bundle bundle, IntentSender intentSender) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public IntentSender semCreateAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void semSetSkipPackageChanged(String str) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void setAppWidgetHidden(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void setBindAppWidgetPermission(String str, int i, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean setTemplateWidgetPreview(ComponentName componentName, int i, int i2, RemoteViews[] remoteViewsArr) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public boolean setWidgetPreview(ComponentName componentName, int i, RemoteViews remoteViews) throws RemoteException {
            return false;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public ParceledListSlice startListening(IAppWidgetHost iAppWidgetHost, String str, int i, int[] iArr) throws RemoteException {
            return null;
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void stopListening(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetOptions(String str, int i, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews) throws RemoteException {
        }

        @Override // com.android.internal.appwidget.IAppWidgetService
        public void updateAppWidgetProviderInfo(ComponentName componentName, String str) throws RemoteException {
        }
    }

    int allocateAppWidgetId(String str, int i) throws RemoteException;

    boolean bindAppWidgetId(String str, int i, int i2, ComponentName componentName, Bundle bundle) throws RemoteException;

    boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, long j) throws RemoteException;

    void changeHostIds(String str, int[] iArr, int i) throws RemoteException;

    IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException;

    void deleteAllHosts() throws RemoteException;

    void deleteAppWidgetId(String str, int i) throws RemoteException;

    void deleteHost(String str, int i) throws RemoteException;

    List<AppWidgetProviderInfo> getAllProvidersForProfile(int i, int i2, boolean z) throws RemoteException;

    Map getAllWidgets(String str, int i) throws RemoteException;

    int[] getAppWidgetIds(ComponentName componentName) throws RemoteException;

    int[] getAppWidgetIdsForHost(String str, int i) throws RemoteException;

    AppWidgetProviderInfo getAppWidgetInfo(String str, int i) throws RemoteException;

    Bundle getAppWidgetOptions(String str, int i) throws RemoteException;

    RemoteViews getAppWidgetViews(String str, int i) throws RemoteException;

    ParceledListSlice getInstalledProvidersForProfile(int i, int i2, String str) throws RemoteException;

    int getMaxBitmapMemory() throws RemoteException;

    Bundle getTemplateWidgetPreview(String str, ComponentName componentName, int i, int i2, int i3) throws RemoteException;

    RemoteViews getWidgetPreview(String str, ComponentName componentName, int i, int i2) throws RemoteException;

    boolean hasBindAppWidgetPermission(String str, int i) throws RemoteException;

    boolean isBoundWidgetPackage(String str, int i) throws RemoteException;

    boolean isRequestPinAppWidgetSupported() throws RemoteException;

    boolean isTemplatePreviewUpdateAvailable(ComponentName componentName) throws RemoteException;

    void noteAppWidgetTapped(String str, int i) throws RemoteException;

    void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) throws RemoteException;

    void notifyProviderInheritance(ComponentName[] componentNameArr) throws RemoteException;

    void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException;

    void removeTemplateWidgetPreview(ComponentName componentName, int i, int i2) throws RemoteException;

    void removeWidgetPreview(ComponentName componentName, int i) throws RemoteException;

    boolean requestPinAppWidget(String str, ComponentName componentName, Bundle bundle, IntentSender intentSender) throws RemoteException;

    IntentSender semCreateAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException;

    void semSetSkipPackageChanged(String str) throws RemoteException;

    void setAppWidgetHidden(String str, int i) throws RemoteException;

    void setBindAppWidgetPermission(String str, int i, boolean z) throws RemoteException;

    boolean setTemplateWidgetPreview(ComponentName componentName, int i, int i2, RemoteViews[] remoteViewsArr) throws RemoteException;

    boolean setWidgetPreview(ComponentName componentName, int i, RemoteViews remoteViews) throws RemoteException;

    ParceledListSlice startListening(IAppWidgetHost iAppWidgetHost, String str, int i, int[] iArr) throws RemoteException;

    void stopListening(String str, int i) throws RemoteException;

    void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException;

    void updateAppWidgetOptions(String str, int i, Bundle bundle) throws RemoteException;

    void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews) throws RemoteException;

    void updateAppWidgetProviderInfo(ComponentName componentName, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IAppWidgetService {
        public static final String DESCRIPTOR = "com.android.internal.appwidget.IAppWidgetService";
        static final int TRANSACTION_allocateAppWidgetId = 3;
        static final int TRANSACTION_bindAppWidgetId = 25;
        static final int TRANSACTION_bindRemoteViewsService = 26;
        static final int TRANSACTION_changeHostIds = 13;
        static final int TRANSACTION_createAppWidgetConfigIntentSender = 10;
        static final int TRANSACTION_deleteAllHosts = 6;
        static final int TRANSACTION_deleteAppWidgetId = 4;
        static final int TRANSACTION_deleteHost = 5;
        static final int TRANSACTION_getAllProvidersForProfile = 35;
        static final int TRANSACTION_getAllWidgets = 34;
        static final int TRANSACTION_getAppWidgetIds = 29;
        static final int TRANSACTION_getAppWidgetIdsForHost = 8;
        static final int TRANSACTION_getAppWidgetInfo = 22;
        static final int TRANSACTION_getAppWidgetOptions = 16;
        static final int TRANSACTION_getAppWidgetViews = 7;
        static final int TRANSACTION_getInstalledProvidersForProfile = 21;
        static final int TRANSACTION_getMaxBitmapMemory = 28;
        static final int TRANSACTION_getTemplateWidgetPreview = 40;
        static final int TRANSACTION_getWidgetPreview = 37;
        static final int TRANSACTION_hasBindAppWidgetPermission = 23;
        static final int TRANSACTION_isBoundWidgetPackage = 30;
        static final int TRANSACTION_isRequestPinAppWidgetSupported = 32;
        static final int TRANSACTION_isTemplatePreviewUpdateAvailable = 42;
        static final int TRANSACTION_noteAppWidgetTapped = 33;
        static final int TRANSACTION_notifyAppWidgetViewDataChanged = 20;
        static final int TRANSACTION_notifyProviderInheritance = 27;
        static final int TRANSACTION_partiallyUpdateAppWidgetIds = 17;
        static final int TRANSACTION_removeTemplateWidgetPreview = 41;
        static final int TRANSACTION_removeWidgetPreview = 38;
        static final int TRANSACTION_requestPinAppWidget = 31;
        static final int TRANSACTION_semCreateAppWidgetConfigIntentSender = 11;
        static final int TRANSACTION_semSetSkipPackageChanged = 12;
        static final int TRANSACTION_setAppWidgetHidden = 9;
        static final int TRANSACTION_setBindAppWidgetPermission = 24;
        static final int TRANSACTION_setTemplateWidgetPreview = 39;
        static final int TRANSACTION_setWidgetPreview = 36;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;
        static final int TRANSACTION_updateAppWidgetIds = 14;
        static final int TRANSACTION_updateAppWidgetOptions = 15;
        static final int TRANSACTION_updateAppWidgetProvider = 18;
        static final int TRANSACTION_updateAppWidgetProviderInfo = 19;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 41;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAppWidgetService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAppWidgetService)) {
                return (IAppWidgetService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "getMaxBitmapMemory";
                case 29:
                    return "getAppWidgetIds";
                case 30:
                    return "isBoundWidgetPackage";
                case 31:
                    return "requestPinAppWidget";
                case 32:
                    return "isRequestPinAppWidgetSupported";
                case 33:
                    return "noteAppWidgetTapped";
                case 34:
                    return "getAllWidgets";
                case 35:
                    return "getAllProvidersForProfile";
                case 36:
                    return "setWidgetPreview";
                case 37:
                    return "getWidgetPreview";
                case 38:
                    return "removeWidgetPreview";
                case 39:
                    return "setTemplateWidgetPreview";
                case 40:
                    return "getTemplateWidgetPreview";
                case 41:
                    return "removeTemplateWidgetPreview";
                case 42:
                    return "isTemplatePreviewUpdateAvailable";
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
                    IAppWidgetHost asInterface = IAppWidgetHost.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice startListening = startListening(asInterface, readString, readInt, createIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startListening, 1);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopListening(readString2, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int allocateAppWidgetId = allocateAppWidgetId(readString3, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(allocateAppWidgetId);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteAppWidgetId(readString4, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteHost(readString5, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    deleteAllHosts();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RemoteViews appWidgetViews = getAppWidgetViews(readString6, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetViews, 1);
                    return true;
                case 8:
                    String readString7 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] appWidgetIdsForHost = getAppWidgetIdsForHost(readString7, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appWidgetIdsForHost);
                    return true;
                case 9:
                    String readString8 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppWidgetHidden(readString8, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString9 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IntentSender createAppWidgetConfigIntentSender = createAppWidgetConfigIntentSender(readString9, readInt9, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createAppWidgetConfigIntentSender, 1);
                    return true;
                case 11:
                    String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IntentSender semCreateAppWidgetConfigIntentSender = semCreateAppWidgetConfigIntentSender(readString10, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(semCreateAppWidgetConfigIntentSender, 1);
                    return true;
                case 12:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semSetSkipPackageChanged(readString11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString12 = parcel.readString();
                    int[] createIntArray2 = parcel.createIntArray();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeHostIds(readString12, createIntArray2, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString13 = parcel.readString();
                    int[] createIntArray3 = parcel.createIntArray();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetIds(readString13, createIntArray3, remoteViews);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString14 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetOptions(readString14, readInt14, bundle);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString15 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle appWidgetOptions = getAppWidgetOptions(readString15, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetOptions, 1);
                    return true;
                case 17:
                    String readString16 = parcel.readString();
                    int[] createIntArray4 = parcel.createIntArray();
                    RemoteViews remoteViews2 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    partiallyUpdateAppWidgetIds(readString16, createIntArray4, remoteViews2);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    RemoteViews remoteViews3 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetProvider(componentName, remoteViews3);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    String readString17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAppWidgetProviderInfo(componentName2, readString17);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString18 = parcel.readString();
                    int[] createIntArray5 = parcel.createIntArray();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppWidgetViewDataChanged(readString18, createIntArray5, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    String readString19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice installedProvidersForProfile = getInstalledProvidersForProfile(readInt17, readInt18, readString19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedProvidersForProfile, 1);
                    return true;
                case 22:
                    String readString20 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AppWidgetProviderInfo appWidgetInfo = getAppWidgetInfo(readString20, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetInfo, 1);
                    return true;
                case 23:
                    String readString21 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasBindAppWidgetPermission = hasBindAppWidgetPermission(readString21, readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasBindAppWidgetPermission);
                    return true;
                case 24:
                    String readString22 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBindAppWidgetPermission(readString22, readInt21, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String readString23 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean bindAppWidgetId = bindAppWidgetId(readString23, readInt22, readInt23, componentName3, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindAppWidgetId);
                    return true;
                case 26:
                    String readString24 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IApplicationThread asInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IServiceConnection asInterface3 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean bindRemoteViewsService = bindRemoteViewsService(readString24, readInt24, intent, asInterface2, readStrongBinder, asInterface3, readLong);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindRemoteViewsService);
                    return true;
                case 27:
                    ComponentName[] componentNameArr = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyProviderInheritance(componentNameArr);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int maxBitmapMemory = getMaxBitmapMemory();
                    parcel2.writeNoException();
                    parcel2.writeInt(maxBitmapMemory);
                    return true;
                case 29:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int[] appWidgetIds = getAppWidgetIds(componentName4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appWidgetIds);
                    return true;
                case 30:
                    String readString25 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBoundWidgetPackage = isBoundWidgetPackage(readString25, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBoundWidgetPackage);
                    return true;
                case 31:
                    String readString26 = parcel.readString();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean requestPinAppWidget = requestPinAppWidget(readString26, componentName5, bundle3, intentSender);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestPinAppWidget);
                    return true;
                case 32:
                    boolean isRequestPinAppWidgetSupported = isRequestPinAppWidgetSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRequestPinAppWidgetSupported);
                    return true;
                case 33:
                    String readString27 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteAppWidgetTapped(readString27, readInt26);
                    return true;
                case 34:
                    String readString28 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map allWidgets = getAllWidgets(readString28, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeMap(allWidgets);
                    return true;
                case 35:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<AppWidgetProviderInfo> allProvidersForProfile = getAllProvidersForProfile(readInt28, readInt29, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allProvidersForProfile, 1);
                    return true;
                case 36:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt30 = parcel.readInt();
                    RemoteViews remoteViews4 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean widgetPreview = setWidgetPreview(componentName6, readInt30, remoteViews4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(widgetPreview);
                    return true;
                case 37:
                    String readString29 = parcel.readString();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RemoteViews widgetPreview2 = getWidgetPreview(readString29, componentName7, readInt31, readInt32);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(widgetPreview2, 1);
                    return true;
                case 38:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeWidgetPreview(componentName8, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    RemoteViews[] remoteViewsArr = (RemoteViews[]) parcel.createTypedArray(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean templateWidgetPreview = setTemplateWidgetPreview(componentName9, readInt34, readInt35, remoteViewsArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(templateWidgetPreview);
                    return true;
                case 40:
                    String readString30 = parcel.readString();
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle templateWidgetPreview2 = getTemplateWidgetPreview(readString30, componentName10, readInt36, readInt37, readInt38);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(templateWidgetPreview2, 1);
                    return true;
                case 41:
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt39 = parcel.readInt();
                    int readInt40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeTemplateWidgetPreview(componentName11, readInt39, readInt40);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isTemplatePreviewUpdateAvailable = isTemplatePreviewUpdateAvailable(componentName12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTemplatePreviewUpdateAvailable);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAppWidgetService {
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

            @Override // com.android.internal.appwidget.IAppWidgetService
            public ParceledListSlice startListening(IAppWidgetHost iAppWidgetHost, String str, int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAppWidgetHost);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void stopListening(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int allocateAppWidgetId(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAppWidgetId(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteHost(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAllHosts() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public RemoteViews getAppWidgetViews(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RemoteViews) obtain2.readTypedObject(RemoteViews.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIdsForHost(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setAppWidgetHidden(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentSender) obtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public IntentSender semCreateAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (IntentSender) obtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void semSetSkipPackageChanged(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void changeHostIds(String str, int[] iArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetOptions(String str, int i, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Bundle getAppWidgetOptions(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProviderInfo(ComponentName componentName, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public ParceledListSlice getInstalledProvidersForProfile(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public AppWidgetProviderInfo getAppWidgetInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AppWidgetProviderInfo) obtain2.readTypedObject(AppWidgetProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean hasBindAppWidgetPermission(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setBindAppWidgetPermission(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindAppWidgetId(String str, int i, int i2, ComponentName componentName, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeLong(j);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyProviderInheritance(ComponentName[] componentNameArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int getMaxBitmapMemory() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIds(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isBoundWidgetPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean requestPinAppWidget(String str, ComponentName componentName, Bundle bundle, IntentSender intentSender) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isRequestPinAppWidgetSupported() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void noteAppWidgetTapped(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Map getAllWidgets(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public List<AppWidgetProviderInfo> getAllProvidersForProfile(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(AppWidgetProviderInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean setWidgetPreview(ComponentName componentName, int i, RemoteViews remoteViews) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public RemoteViews getWidgetPreview(String str, ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RemoteViews) obtain2.readTypedObject(RemoteViews.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void removeWidgetPreview(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean setTemplateWidgetPreview(ComponentName componentName, int i, int i2, RemoteViews[] remoteViewsArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedArray(remoteViewsArr, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Bundle getTemplateWidgetPreview(String str, ComponentName componentName, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void removeTemplateWidgetPreview(ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isTemplatePreviewUpdateAvailable(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
