package com.samsung.android.cocktailbar;

import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;
import com.samsung.android.cocktailbar.ICocktailHost;

/* loaded from: classes6.dex */
public interface ICocktailBarService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailBarService";

    public static class Default implements ICocktailBarService {
        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void activateCocktailBar() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, int i2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void closeCocktail(String str, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void deactivateCocktailBar() throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void disableCocktail(String str, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int[] getAllCocktailIds() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public String getCategoryFilterStr() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean getCocktaiBarWakeUpState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public Cocktail getCocktail(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public CocktailBarStateInfo getCocktailBarStateInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getCocktailBarVisibility() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getCocktailId(String str, ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int[] getCocktailIds(String str, ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getConfigVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int[] getEnabledCocktailIds() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public String getHideEdgeListStr() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getPreferWidth() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getSystemBarAppearance() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getWindowType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean isBoundCocktailPackage(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean isCocktailEnabled(String str, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean isEnabledCocktail(String str, ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void notifyCocktailViewDataChanged(String str, int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void notifyCocktailVisibiltyChanged(int i, int i2) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void notifyKeyguardState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void partiallyUpdateCocktail(String str, RemoteViews remoteViews, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void partiallyUpdateHelpView(String str, RemoteViews remoteViews, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void registerCocktailBarStateListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void registerSystemUiVisibilityListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void removeCocktailUIService() throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToDisableCocktail(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToDisableCocktailByCategory(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToUpdateCocktail(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToUpdateCocktailByCategory(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void sendExtraDataToCocktailBar(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setCocktailBarWakeUpState(boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setCocktailHostCallbacks(ICocktailHost iCocktailHost, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setEnabledCocktailIds(int[] iArr) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setOnPullPendingIntent(String str, int i, int i2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void showCocktail(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void startListening(ICocktailHost iCocktailHost, String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void stopListening(String str) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void unbindRemoteViewsService(String str, int i, Intent intent) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void unregisterCocktailBarStateListenerCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void unregisterSystemUiVisibilityListenerCallback(IBinder iBinder) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktail(String str, CocktailInfo cocktailInfo, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktailBarPosition(int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktailBarVisibility(int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktailBarWindowType(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateWakeupArea(int i) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateWakeupGesture(int i, boolean z) throws RemoteException {
        }
    }

    void activateCocktailBar() throws RemoteException;

    boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, int i2) throws RemoteException;

    void closeCocktail(String str, int i, int i2) throws RemoteException;

    void deactivateCocktailBar() throws RemoteException;

    void disableCocktail(String str, ComponentName componentName) throws RemoteException;

    int[] getAllCocktailIds() throws RemoteException;

    String getCategoryFilterStr() throws RemoteException;

    boolean getCocktaiBarWakeUpState() throws RemoteException;

    Cocktail getCocktail(int i) throws RemoteException;

    CocktailBarStateInfo getCocktailBarStateInfo() throws RemoteException;

    int getCocktailBarVisibility() throws RemoteException;

    int getCocktailId(String str, ComponentName componentName) throws RemoteException;

    int[] getCocktailIds(String str, ComponentName componentName) throws RemoteException;

    int getConfigVersion() throws RemoteException;

    int[] getEnabledCocktailIds() throws RemoteException;

    String getHideEdgeListStr() throws RemoteException;

    int getPreferWidth() throws RemoteException;

    int getSystemBarAppearance() throws RemoteException;

    int getWindowType() throws RemoteException;

    boolean isBoundCocktailPackage(String str, int i) throws RemoteException;

    boolean isCocktailEnabled(String str, ComponentName componentName) throws RemoteException;

    boolean isEnabledCocktail(String str, ComponentName componentName) throws RemoteException;

    void notifyCocktailViewDataChanged(String str, int i, int i2) throws RemoteException;

    void notifyCocktailVisibiltyChanged(int i, int i2) throws RemoteException;

    void notifyKeyguardState(boolean z) throws RemoteException;

    void partiallyUpdateCocktail(String str, RemoteViews remoteViews, int i) throws RemoteException;

    void partiallyUpdateHelpView(String str, RemoteViews remoteViews, int i) throws RemoteException;

    void registerCocktailBarStateListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException;

    void registerSystemUiVisibilityListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException;

    void removeCocktailUIService() throws RemoteException;

    boolean requestToDisableCocktail(int i) throws RemoteException;

    boolean requestToDisableCocktailByCategory(int i) throws RemoteException;

    boolean requestToUpdateCocktail(int i) throws RemoteException;

    boolean requestToUpdateCocktailByCategory(int i) throws RemoteException;

    void sendExtraDataToCocktailBar(Bundle bundle) throws RemoteException;

    void setCocktailBarWakeUpState(boolean z) throws RemoteException;

    void setCocktailHostCallbacks(ICocktailHost iCocktailHost, String str, int i) throws RemoteException;

    void setEnabledCocktailIds(int[] iArr) throws RemoteException;

    void setOnPullPendingIntent(String str, int i, int i2, PendingIntent pendingIntent) throws RemoteException;

    void showCocktail(String str, int i) throws RemoteException;

    void startListening(ICocktailHost iCocktailHost, String str, int i) throws RemoteException;

    void stopListening(String str) throws RemoteException;

    void unbindRemoteViewsService(String str, int i, Intent intent) throws RemoteException;

    void unregisterCocktailBarStateListenerCallback(IBinder iBinder) throws RemoteException;

    void unregisterSystemUiVisibilityListenerCallback(IBinder iBinder) throws RemoteException;

    void updateCocktail(String str, CocktailInfo cocktailInfo, int i) throws RemoteException;

    void updateCocktailBarPosition(int i) throws RemoteException;

    void updateCocktailBarVisibility(int i) throws RemoteException;

    void updateCocktailBarWindowType(String str, int i) throws RemoteException;

    void updateWakeupArea(int i) throws RemoteException;

    void updateWakeupGesture(int i, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICocktailBarService {
        static final int TRANSACTION_activateCocktailBar = 37;
        static final int TRANSACTION_bindRemoteViewsService = 21;
        static final int TRANSACTION_closeCocktail = 12;
        static final int TRANSACTION_deactivateCocktailBar = 38;
        static final int TRANSACTION_disableCocktail = 14;
        static final int TRANSACTION_getAllCocktailIds = 6;
        static final int TRANSACTION_getCategoryFilterStr = 49;
        static final int TRANSACTION_getCocktaiBarWakeUpState = 44;
        static final int TRANSACTION_getCocktail = 7;
        static final int TRANSACTION_getCocktailBarStateInfo = 34;
        static final int TRANSACTION_getCocktailBarVisibility = 33;
        static final int TRANSACTION_getCocktailId = 13;
        static final int TRANSACTION_getCocktailIds = 15;
        static final int TRANSACTION_getConfigVersion = 47;
        static final int TRANSACTION_getEnabledCocktailIds = 5;
        static final int TRANSACTION_getHideEdgeListStr = 51;
        static final int TRANSACTION_getPreferWidth = 48;
        static final int TRANSACTION_getSystemBarAppearance = 50;
        static final int TRANSACTION_getWindowType = 36;
        static final int TRANSACTION_isBoundCocktailPackage = 16;
        static final int TRANSACTION_isCocktailEnabled = 18;
        static final int TRANSACTION_isEnabledCocktail = 17;
        static final int TRANSACTION_notifyCocktailViewDataChanged = 19;
        static final int TRANSACTION_notifyCocktailVisibiltyChanged = 28;
        static final int TRANSACTION_notifyKeyguardState = 27;
        static final int TRANSACTION_partiallyUpdateCocktail = 9;
        static final int TRANSACTION_partiallyUpdateHelpView = 10;
        static final int TRANSACTION_registerCocktailBarStateListenerCallback = 31;
        static final int TRANSACTION_registerSystemUiVisibilityListenerCallback = 39;
        static final int TRANSACTION_removeCocktailUIService = 46;
        static final int TRANSACTION_requestToDisableCocktail = 24;
        static final int TRANSACTION_requestToDisableCocktailByCategory = 26;
        static final int TRANSACTION_requestToUpdateCocktail = 23;
        static final int TRANSACTION_requestToUpdateCocktailByCategory = 25;
        static final int TRANSACTION_sendExtraDataToCocktailBar = 45;
        static final int TRANSACTION_setCocktailBarWakeUpState = 43;
        static final int TRANSACTION_setCocktailHostCallbacks = 1;
        static final int TRANSACTION_setEnabledCocktailIds = 4;
        static final int TRANSACTION_setOnPullPendingIntent = 20;
        static final int TRANSACTION_showCocktail = 11;
        static final int TRANSACTION_startListening = 2;
        static final int TRANSACTION_stopListening = 3;
        static final int TRANSACTION_unbindRemoteViewsService = 22;
        static final int TRANSACTION_unregisterCocktailBarStateListenerCallback = 32;
        static final int TRANSACTION_unregisterSystemUiVisibilityListenerCallback = 40;
        static final int TRANSACTION_updateCocktail = 8;
        static final int TRANSACTION_updateCocktailBarPosition = 30;
        static final int TRANSACTION_updateCocktailBarVisibility = 29;
        static final int TRANSACTION_updateCocktailBarWindowType = 35;
        static final int TRANSACTION_updateWakeupArea = 42;
        static final int TRANSACTION_updateWakeupGesture = 41;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 50;
        }

        public Stub() {
            attachInterface(this, ICocktailBarService.DESCRIPTOR);
        }

        public static ICocktailBarService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICocktailBarService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICocktailBarService)) {
                return (ICocktailBarService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCocktailHostCallbacks";
                case 2:
                    return "startListening";
                case 3:
                    return "stopListening";
                case 4:
                    return "setEnabledCocktailIds";
                case 5:
                    return "getEnabledCocktailIds";
                case 6:
                    return "getAllCocktailIds";
                case 7:
                    return "getCocktail";
                case 8:
                    return "updateCocktail";
                case 9:
                    return "partiallyUpdateCocktail";
                case 10:
                    return "partiallyUpdateHelpView";
                case 11:
                    return "showCocktail";
                case 12:
                    return "closeCocktail";
                case 13:
                    return "getCocktailId";
                case 14:
                    return "disableCocktail";
                case 15:
                    return "getCocktailIds";
                case 16:
                    return "isBoundCocktailPackage";
                case 17:
                    return "isEnabledCocktail";
                case 18:
                    return "isCocktailEnabled";
                case 19:
                    return "notifyCocktailViewDataChanged";
                case 20:
                    return "setOnPullPendingIntent";
                case 21:
                    return "bindRemoteViewsService";
                case 22:
                    return "unbindRemoteViewsService";
                case 23:
                    return "requestToUpdateCocktail";
                case 24:
                    return "requestToDisableCocktail";
                case 25:
                    return "requestToUpdateCocktailByCategory";
                case 26:
                    return "requestToDisableCocktailByCategory";
                case 27:
                    return "notifyKeyguardState";
                case 28:
                    return "notifyCocktailVisibiltyChanged";
                case 29:
                    return "updateCocktailBarVisibility";
                case 30:
                    return "updateCocktailBarPosition";
                case 31:
                    return "registerCocktailBarStateListenerCallback";
                case 32:
                    return "unregisterCocktailBarStateListenerCallback";
                case 33:
                    return "getCocktailBarVisibility";
                case 34:
                    return "getCocktailBarStateInfo";
                case 35:
                    return "updateCocktailBarWindowType";
                case 36:
                    return "getWindowType";
                case 37:
                    return "activateCocktailBar";
                case 38:
                    return "deactivateCocktailBar";
                case 39:
                    return "registerSystemUiVisibilityListenerCallback";
                case 40:
                    return "unregisterSystemUiVisibilityListenerCallback";
                case 41:
                    return "updateWakeupGesture";
                case 42:
                    return "updateWakeupArea";
                case 43:
                    return "setCocktailBarWakeUpState";
                case 44:
                    return "getCocktaiBarWakeUpState";
                case 45:
                    return "sendExtraDataToCocktailBar";
                case 46:
                    return "removeCocktailUIService";
                case 47:
                    return "getConfigVersion";
                case 48:
                    return "getPreferWidth";
                case 49:
                    return "getCategoryFilterStr";
                case 50:
                    return "getSystemBarAppearance";
                case 51:
                    return "getHideEdgeListStr";
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
                parcel.enforceInterface(ICocktailBarService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICocktailBarService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ICocktailHost iCocktailHostAsInterface = ICocktailHost.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCocktailHostCallbacks(iCocktailHostAsInterface, string, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ICocktailHost iCocktailHostAsInterface2 = ICocktailHost.Stub.asInterface(parcel.readStrongBinder());
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startListening(iCocktailHostAsInterface2, string2, i4);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopListening(string3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEnabledCocktailIds(iArrCreateIntArray);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int[] enabledCocktailIds = getEnabledCocktailIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(enabledCocktailIds);
                    return true;
                case 6:
                    int[] allCocktailIds = getAllCocktailIds();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allCocktailIds);
                    return true;
                case 7:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Cocktail cocktail = getCocktail(i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cocktail, 1);
                    return true;
                case 8:
                    String string4 = parcel.readString();
                    CocktailInfo cocktailInfo = (CocktailInfo) parcel.readTypedObject(CocktailInfo.CREATOR);
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktail(string4, cocktailInfo, i6);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string5 = parcel.readString();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateCocktail(string5, remoteViews, i7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string6 = parcel.readString();
                    RemoteViews remoteViews2 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateHelpView(string6, remoteViews2, i8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string7 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showCocktail(string7, i9);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string8 = parcel.readString();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeCocktail(string8, i10, i11);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String string9 = parcel.readString();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int cocktailId = getCocktailId(string9, componentName);
                    parcel2.writeNoException();
                    parcel2.writeInt(cocktailId);
                    return true;
                case 14:
                    String string10 = parcel.readString();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    disableCocktail(string10, componentName2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String string11 = parcel.readString();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int[] cocktailIds = getCocktailIds(string11, componentName3);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(cocktailIds);
                    return true;
                case 16:
                    String string12 = parcel.readString();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsBoundCocktailPackage = isBoundCocktailPackage(string12, i12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsBoundCocktailPackage);
                    return true;
                case 17:
                    String string13 = parcel.readString();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsEnabledCocktail = isEnabledCocktail(string13, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnabledCocktail);
                    return true;
                case 18:
                    String string14 = parcel.readString();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCocktailEnabled = isCocktailEnabled(string14, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCocktailEnabled);
                    return true;
                case 19:
                    String string15 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCocktailViewDataChanged(string15, i13, i14);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String string16 = parcel.readString();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnPullPendingIntent(string16, i15, i16, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String string17 = parcel.readString();
                    int i17 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder strongBinder = parcel.readStrongBinder();
                    IServiceConnection iServiceConnectionAsInterface = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zBindRemoteViewsService = bindRemoteViewsService(string17, i17, intent, iApplicationThreadAsInterface, strongBinder, iServiceConnectionAsInterface, i18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zBindRemoteViewsService);
                    return true;
                case 22:
                    String string18 = parcel.readString();
                    int i19 = parcel.readInt();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    unbindRemoteViewsService(string18, i19, intent2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestToUpdateCocktail = requestToUpdateCocktail(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestToUpdateCocktail);
                    return true;
                case 24:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestToDisableCocktail = requestToDisableCocktail(i21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestToDisableCocktail);
                    return true;
                case 25:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestToUpdateCocktailByCategory = requestToUpdateCocktailByCategory(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestToUpdateCocktailByCategory);
                    return true;
                case 26:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestToDisableCocktailByCategory = requestToDisableCocktailByCategory(i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestToDisableCocktailByCategory);
                    return true;
                case 27:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyKeyguardState(z);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCocktailVisibiltyChanged(i24, i25);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktailBarVisibility(i26);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktailBarPosition(i27);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerCocktailBarStateListenerCallback(strongBinder2, componentName6);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterCocktailBarStateListenerCallback(strongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int cocktailBarVisibility = getCocktailBarVisibility();
                    parcel2.writeNoException();
                    parcel2.writeInt(cocktailBarVisibility);
                    return true;
                case 34:
                    CocktailBarStateInfo cocktailBarStateInfo = getCocktailBarStateInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cocktailBarStateInfo, 1);
                    return true;
                case 35:
                    String string19 = parcel.readString();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktailBarWindowType(string19, i28);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int windowType = getWindowType();
                    parcel2.writeNoException();
                    parcel2.writeInt(windowType);
                    return true;
                case 37:
                    activateCocktailBar();
                    parcel2.writeNoException();
                    return true;
                case 38:
                    deactivateCocktailBar();
                    parcel2.writeNoException();
                    return true;
                case 39:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerSystemUiVisibilityListenerCallback(strongBinder4, componentName7);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterSystemUiVisibilityListenerCallback(strongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i29 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateWakeupGesture(i29, z2);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateWakeupArea(i30);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCocktailBarWakeUpState(z3);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    boolean cocktaiBarWakeUpState = getCocktaiBarWakeUpState();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cocktaiBarWakeUpState);
                    return true;
                case 45:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendExtraDataToCocktailBar(bundle);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    removeCocktailUIService();
                    parcel2.writeNoException();
                    return true;
                case 47:
                    int configVersion = getConfigVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(configVersion);
                    return true;
                case 48:
                    int preferWidth = getPreferWidth();
                    parcel2.writeNoException();
                    parcel2.writeInt(preferWidth);
                    return true;
                case 49:
                    String categoryFilterStr = getCategoryFilterStr();
                    parcel2.writeNoException();
                    parcel2.writeString(categoryFilterStr);
                    return true;
                case 50:
                    int systemBarAppearance = getSystemBarAppearance();
                    parcel2.writeNoException();
                    parcel2.writeInt(systemBarAppearance);
                    return true;
                case 51:
                    String hideEdgeListStr = getHideEdgeListStr();
                    parcel2.writeNoException();
                    parcel2.writeString(hideEdgeListStr);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICocktailBarService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICocktailBarService.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setCocktailHostCallbacks(ICocktailHost iCocktailHost, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCocktailHost);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void startListening(ICocktailHost iCocktailHost, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCocktailHost);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void stopListening(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setEnabledCocktailIds(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getEnabledCocktailIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getAllCocktailIds() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public Cocktail getCocktail(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Cocktail) parcelObtain2.readTypedObject(Cocktail.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktail(String str, CocktailInfo cocktailInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(cocktailInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void partiallyUpdateCocktail(String str, RemoteViews remoteViews, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void partiallyUpdateHelpView(String str, RemoteViews remoteViews, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(remoteViews, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void showCocktail(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void closeCocktail(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getCocktailId(String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void disableCocktail(String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getCocktailIds(String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isBoundCocktailPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isEnabledCocktail(String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isCocktailEnabled(String str, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyCocktailViewDataChanged(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setOnPullPendingIntent(String str, int i, int i2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iServiceConnection);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unbindRemoteViewsService(String str, int i, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToUpdateCocktail(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToDisableCocktail(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToUpdateCocktailByCategory(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToDisableCocktailByCategory(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyKeyguardState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyCocktailVisibiltyChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarVisibility(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarPosition(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void registerCocktailBarStateListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unregisterCocktailBarStateListenerCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getCocktailBarVisibility() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public CocktailBarStateInfo getCocktailBarStateInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CocktailBarStateInfo) parcelObtain2.readTypedObject(CocktailBarStateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarWindowType(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getWindowType() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void activateCocktailBar() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void deactivateCocktailBar() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void registerSystemUiVisibilityListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unregisterSystemUiVisibilityListenerCallback(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateWakeupGesture(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateWakeupArea(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setCocktailBarWakeUpState(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean getCocktaiBarWakeUpState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void sendExtraDataToCocktailBar(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void removeCocktailUIService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getConfigVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getPreferWidth() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public String getCategoryFilterStr() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getSystemBarAppearance() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public String getHideEdgeListStr() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
