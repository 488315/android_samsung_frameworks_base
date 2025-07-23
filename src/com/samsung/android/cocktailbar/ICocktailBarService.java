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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICocktailBarService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICocktailBarService)) {
                return (ICocktailBarService) queryLocalInterface;
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
                    ICocktailHost asInterface = ICocktailHost.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCocktailHostCallbacks(asInterface, readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ICocktailHost asInterface2 = ICocktailHost.Stub.asInterface(parcel.readStrongBinder());
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    startListening(asInterface2, readString2, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopListening(readString3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    setEnabledCocktailIds(createIntArray);
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
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Cocktail cocktail = getCocktail(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cocktail, 1);
                    return true;
                case 8:
                    String readString4 = parcel.readString();
                    CocktailInfo cocktailInfo = (CocktailInfo) parcel.readTypedObject(CocktailInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktail(readString4, cocktailInfo, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString5 = parcel.readString();
                    RemoteViews remoteViews = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateCocktail(readString5, remoteViews, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString6 = parcel.readString();
                    RemoteViews remoteViews2 = (RemoteViews) parcel.readTypedObject(RemoteViews.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    partiallyUpdateHelpView(readString6, remoteViews2, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString7 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showCocktail(readString7, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString8 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    closeCocktail(readString8, readInt8, readInt9);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    String readString9 = parcel.readString();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int cocktailId = getCocktailId(readString9, componentName);
                    parcel2.writeNoException();
                    parcel2.writeInt(cocktailId);
                    return true;
                case 14:
                    String readString10 = parcel.readString();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    disableCocktail(readString10, componentName2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString11 = parcel.readString();
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int[] cocktailIds = getCocktailIds(readString11, componentName3);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(cocktailIds);
                    return true;
                case 16:
                    String readString12 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isBoundCocktailPackage = isBoundCocktailPackage(readString12, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isBoundCocktailPackage);
                    return true;
                case 17:
                    String readString13 = parcel.readString();
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isEnabledCocktail = isEnabledCocktail(readString13, componentName4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnabledCocktail);
                    return true;
                case 18:
                    String readString14 = parcel.readString();
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCocktailEnabled = isCocktailEnabled(readString14, componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCocktailEnabled);
                    return true;
                case 19:
                    String readString15 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCocktailViewDataChanged(readString15, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    String readString16 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    setOnPullPendingIntent(readString16, readInt13, readInt14, pendingIntent);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    String readString17 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    IApplicationThread asInterface3 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IServiceConnection asInterface4 = IServiceConnection.Stub.asInterface(parcel.readStrongBinder());
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean bindRemoteViewsService = bindRemoteViewsService(readString17, readInt15, intent, asInterface3, readStrongBinder, asInterface4, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(bindRemoteViewsService);
                    return true;
                case 22:
                    String readString18 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    unbindRemoteViewsService(readString18, readInt17, intent2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestToUpdateCocktail = requestToUpdateCocktail(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestToUpdateCocktail);
                    return true;
                case 24:
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestToDisableCocktail = requestToDisableCocktail(readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestToDisableCocktail);
                    return true;
                case 25:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestToUpdateCocktailByCategory = requestToUpdateCocktailByCategory(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestToUpdateCocktailByCategory);
                    return true;
                case 26:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestToDisableCocktailByCategory = requestToDisableCocktailByCategory(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestToDisableCocktailByCategory);
                    return true;
                case 27:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyKeyguardState(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCocktailVisibiltyChanged(readInt22, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktailBarVisibility(readInt24);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktailBarPosition(readInt25);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerCocktailBarStateListenerCallback(readStrongBinder2, componentName6);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterCocktailBarStateListenerCallback(readStrongBinder3);
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
                    String readString19 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCocktailBarWindowType(readString19, readInt26);
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
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerSystemUiVisibilityListenerCallback(readStrongBinder4, componentName7);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    unregisterSystemUiVisibilityListenerCallback(readStrongBinder5);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt27 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateWakeupGesture(readInt27, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateWakeupArea(readInt28);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCocktailBarWakeUpState(readBoolean3);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCocktailHost);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void startListening(ICocktailHost iCocktailHost, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCocktailHost);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void stopListening(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setEnabledCocktailIds(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getEnabledCocktailIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getAllCocktailIds() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public Cocktail getCocktail(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Cocktail) obtain2.readTypedObject(Cocktail.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktail(String str, CocktailInfo cocktailInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(cocktailInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void partiallyUpdateCocktail(String str, RemoteViews remoteViews, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteViews, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void partiallyUpdateHelpView(String str, RemoteViews remoteViews, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(remoteViews, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void showCocktail(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void closeCocktail(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getCocktailId(String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void disableCocktail(String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getCocktailIds(String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isBoundCocktailPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isEnabledCocktail(String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isCocktailEnabled(String str, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyCocktailViewDataChanged(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setOnPullPendingIntent(String str, int i, int i2, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean bindRemoteViewsService(String str, int i, Intent intent, IApplicationThread iApplicationThread, IBinder iBinder, IServiceConnection iServiceConnection, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iServiceConnection);
                    obtain.writeInt(i2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unbindRemoteViewsService(String str, int i, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToUpdateCocktail(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToDisableCocktail(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToUpdateCocktailByCategory(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToDisableCocktailByCategory(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyKeyguardState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyCocktailVisibiltyChanged(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarVisibility(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarPosition(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void registerCocktailBarStateListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unregisterCocktailBarStateListenerCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getCocktailBarVisibility() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public CocktailBarStateInfo getCocktailBarStateInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CocktailBarStateInfo) obtain2.readTypedObject(CocktailBarStateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarWindowType(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getWindowType() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void activateCocktailBar() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void deactivateCocktailBar() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void registerSystemUiVisibilityListenerCallback(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unregisterSystemUiVisibilityListenerCallback(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateWakeupGesture(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateWakeupArea(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setCocktailBarWakeUpState(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean getCocktaiBarWakeUpState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void sendExtraDataToCocktailBar(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void removeCocktailUIService() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getConfigVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getPreferWidth() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public String getCategoryFilterStr() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getSystemBarAppearance() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public String getHideEdgeListStr() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
