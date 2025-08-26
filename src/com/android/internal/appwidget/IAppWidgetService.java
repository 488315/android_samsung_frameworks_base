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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAppWidgetService)) {
                return (IAppWidgetService) iInterfaceQueryLocalInterface;
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
                    IAppWidgetHost iAppWidgetHostAsInterface = IAppWidgetHost.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice parceledListSliceStartListening = startListening(iAppWidgetHostAsInterface, string, i3, iArrCreateIntArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(parceledListSliceStartListening, 1);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    stopListening(string2, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iAllocateAppWidgetId = allocateAppWidgetId(string3, i5);
                    parcel2.writeNoException();
                    parcel2.writeInt(iAllocateAppWidgetId);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteAppWidgetId(string4, i6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteHost(string5, i7);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    deleteAllHosts();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RemoteViews appWidgetViews = getAppWidgetViews(string6, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetViews, 1);
                    return true;
                case 8:
                    String string7 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] appWidgetIdsForHost = getAppWidgetIdsForHost(string7, i9);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appWidgetIdsForHost);
                    return true;
                case 9:
                    String string8 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppWidgetHidden(string8, i10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string9 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IntentSender intentSenderCreateAppWidgetConfigIntentSender = createAppWidgetConfigIntentSender(string9, i11, i12);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentSenderCreateAppWidgetConfigIntentSender, 1);
                    return true;
                case 11:
                    String string10 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IntentSender intentSenderSemCreateAppWidgetConfigIntentSender = semCreateAppWidgetConfigIntentSender(string10, i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(intentSenderSemCreateAppWidgetConfigIntentSender, 1);
                    return true;
                case 12:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    semSetSkipPackageChanged(string11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string12 = parcel.readString();
                    int[] iArrCreateIntArray2 = parcel.createIntArray();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeHostIds(string12, iArrCreateIntArray2, i15);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string13 = parcel.readString();
                    int[] iArrCreateIntArray3 = parcel.createIntArray();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetIds(string13, iArrCreateIntArray3, remoteViews);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string14 = parcel.readString();
                    int i16 = parcel.readInt();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateAppWidgetOptions(string14, i16, bundle);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string15 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle appWidgetOptions = getAppWidgetOptions(string15, i17);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetOptions, 1);
                    return true;
                case 17:
                    String string16 = parcel.readString();
                    int[] iArrCreateIntArray4 = parcel.createIntArray();
                    RemoteViews remoteViews2 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    partiallyUpdateAppWidgetIds(string16, iArrCreateIntArray4, remoteViews2);
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
                    String string17 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    updateAppWidgetProviderInfo(componentName2, string17);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string18 = parcel.readString();
                    int[] iArrCreateIntArray5 = parcel.createIntArray();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppWidgetViewDataChanged(string18, iArrCreateIntArray5, i18);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice installedProvidersForProfile = getInstalledProvidersForProfile(i19, i20, string19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(installedProvidersForProfile, 1);
                    return true;
                case 22:
                    String string20 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    AppWidgetProviderInfo appWidgetInfo = getAppWidgetInfo(string20, i21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appWidgetInfo, 1);
                    return true;
                case 23:
                    String string21 = parcel.readString();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasBindAppWidgetPermission = hasBindAppWidgetPermission(string21, i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasBindAppWidgetPermission);
                    return true;
                case 24:
                    String string22 = parcel.readString();
                    int i23 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setBindAppWidgetPermission(string22, i23, z);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String string23 = parcel.readString();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zBindAppWidgetId = bindAppWidgetId(string23, i24, i25, componentName3, bundle2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBindAppWidgetId);
                    return true;
                case 26:
                    String string24 = parcel.readString();
                    int i26 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    IServiceConnection iServiceConnectionAsInterface = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean zBindRemoteViewsService = bindRemoteViewsService(string24, i26, intent, iApplicationThreadAsInterface, strongBinder, iServiceConnectionAsInterface, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBindRemoteViewsService);
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
                    String string25 = parcel.readString();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsBoundWidgetPackage = isBoundWidgetPackage(string25, i27);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBoundWidgetPackage);
                    return true;
                case 31:
                    String string26 = parcel.readString();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zRequestPinAppWidget = requestPinAppWidget(string26, componentName5, bundle3, intentSender);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestPinAppWidget);
                    return true;
                case 32:
                    boolean zIsRequestPinAppWidgetSupported = isRequestPinAppWidgetSupported();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRequestPinAppWidgetSupported);
                    return true;
                case 33:
                    String string27 = parcel.readString();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    noteAppWidgetTapped(string27, i28);
                    return true;
                case 34:
                    String string28 = parcel.readString();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Map allWidgets = getAllWidgets(string28, i29);
                    parcel2.writeNoException();
                    parcel2.writeMap(allWidgets);
                    return true;
                case 35:
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<AppWidgetProviderInfo> allProvidersForProfile = getAllProvidersForProfile(i30, i31, z2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allProvidersForProfile, 1);
                    return true;
                case 36:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i32 = parcel.readInt();
                    RemoteViews remoteViews4 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean widgetPreview = setWidgetPreview(componentName6, i32, remoteViews4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(widgetPreview);
                    return true;
                case 37:
                    String string29 = parcel.readString();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    RemoteViews widgetPreview2 = getWidgetPreview(string29, componentName7, i33, i34);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(widgetPreview2, 1);
                    return true;
                case 38:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeWidgetPreview(componentName8, i35);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    RemoteViews[] remoteViewsArr = (RemoteViews[]) parcel.createTypedArray(RemoteViews.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean templateWidgetPreview = setTemplateWidgetPreview(componentName9, i36, i37, remoteViewsArr);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(templateWidgetPreview);
                    return true;
                case 40:
                    String string30 = parcel.readString();
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Bundle templateWidgetPreview2 = getTemplateWidgetPreview(string30, componentName10, i38, i39, i40);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(templateWidgetPreview2, 1);
                    return true;
                case 41:
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i41 = parcel.readInt();
                    int i42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeTemplateWidgetPreview(componentName11, i41, i42);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsTemplatePreviewUpdateAvailable = isTemplatePreviewUpdateAvailable(componentName12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTemplatePreviewUpdateAvailable);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAppWidgetHost);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void stopListening(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int allocateAppWidgetId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAppWidgetId(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteHost(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void deleteAllHosts() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public RemoteViews getAppWidgetViews(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RemoteViews) parcelObtain2.readTypedObject(RemoteViews.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIdsForHost(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setAppWidgetHidden(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public IntentSender createAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IntentSender) parcelObtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public IntentSender semCreateAppWidgetConfigIntentSender(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (IntentSender) parcelObtain2.readTypedObject(IntentSender.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void semSetSkipPackageChanged(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void changeHostIds(String str, int[] iArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetOptions(String str, int i, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Bundle getAppWidgetOptions(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void partiallyUpdateAppWidgetIds(String str, int[] iArr, RemoteViews remoteViews) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProvider(ComponentName componentName, RemoteViews remoteViews) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void updateAppWidgetProviderInfo(ComponentName componentName, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyAppWidgetViewDataChanged(String str, int[] iArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public ParceledListSlice getInstalledProvidersForProfile(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public AppWidgetProviderInfo getAppWidgetInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AppWidgetProviderInfo) parcelObtain2.readTypedObject(AppWidgetProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean hasBindAppWidgetPermission(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void setBindAppWidgetPermission(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindAppWidgetId(String str, int i, int i2, ComponentName componentName, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void notifyProviderInheritance(ComponentName[] componentNameArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int getMaxBitmapMemory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public int[] getAppWidgetIds(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isBoundWidgetPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean requestPinAppWidget(String str, ComponentName componentName, Bundle bundle, IntentSender intentSender) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isRequestPinAppWidgetSupported() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void noteAppWidgetTapped(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Map getAllWidgets(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public List<AppWidgetProviderInfo> getAllProvidersForProfile(int i, int i2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(AppWidgetProviderInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean setWidgetPreview(ComponentName componentName, int i, RemoteViews remoteViews) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public RemoteViews getWidgetPreview(String str, ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (RemoteViews) parcelObtain2.readTypedObject(RemoteViews.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void removeWidgetPreview(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean setTemplateWidgetPreview(ComponentName componentName, int i, int i2, RemoteViews[] remoteViewsArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedArray(remoteViewsArr, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public Bundle getTemplateWidgetPreview(String str, ComponentName componentName, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public void removeTemplateWidgetPreview(ComponentName componentName, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.appwidget.IAppWidgetService
            public boolean isTemplatePreviewUpdateAvailable(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
