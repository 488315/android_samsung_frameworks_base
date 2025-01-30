package com.samsung.android.cocktailbar;

import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.widget.RemoteViews;
import com.samsung.android.cocktailbar.ICocktailHost;

/* loaded from: classes5.dex */
public interface ICocktailBarService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailBarService";

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

    public static class Default implements ICocktailBarService {
        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setCocktailHostCallbacks(ICocktailHost host, String packageName, int category) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void startListening(ICocktailHost host, String packageName, int category) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void stopListening(String callingPackage) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setEnabledCocktailIds(int[] cocktailIds) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int[] getEnabledCocktailIds() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int[] getAllCocktailIds() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public Cocktail getCocktail(int cocktailId) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktail(String callingPackage, CocktailInfo cocktailInfo, int cocktailId) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void partiallyUpdateCocktail(String callingPackage, RemoteViews contentView, int cocktailId) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void partiallyUpdateHelpView(String callingPackage, RemoteViews helpView, int cocktailId) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void showCocktail(String callingPackage, int cocktailId) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void closeCocktail(String callingPackage, int cocktailId, int category) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getCocktailId(String callingPackage, ComponentName provider) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void disableCocktail(String callingPackage, ComponentName provider) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int[] getCocktailIds(String callingPackage, ComponentName provider) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean isBoundCocktailPackage(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean isEnabledCocktail(String callingPackage, ComponentName provider) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean isCocktailEnabled(String callingPackage, ComponentName provider) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void notifyCocktailViewDataChanged(String callingPackage, int cocktailId, int viewId) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setOnPullPendingIntent(String callingPackage, int cocktailId, int viewId, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean bindRemoteViewsService(String callingPakcage, int cocktailId, Intent intent, IApplicationThread caller, IBinder activityToken, IServiceConnection connection, int flags) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void unbindRemoteViewsService(String callingPackage, int cocktailId, Intent intent) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToUpdateCocktail(int cocktailId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToDisableCocktail(int cocktailId) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToUpdateCocktailByCategory(int category) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean requestToDisableCocktailByCategory(int category) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void notifyKeyguardState(boolean enable) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void notifyCocktailVisibiltyChanged(int cocktailId, int visibility) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktailBarVisibility(int visibility) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktailBarPosition(int position) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void registerCocktailBarStateListenerCallback(IBinder callback, ComponentName cm) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void unregisterCocktailBarStateListenerCallback(IBinder callback) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getCocktailBarVisibility() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public CocktailBarStateInfo getCocktailBarStateInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateCocktailBarWindowType(String callingPackage, int windowType) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getWindowType() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void activateCocktailBar() throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void deactivateCocktailBar() throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void registerSystemUiVisibilityListenerCallback(IBinder callback, ComponentName cm) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void unregisterSystemUiVisibilityListenerCallback(IBinder callback) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateWakeupGesture(int gestureType, boolean bEnable) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void updateWakeupArea(int area) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void setCocktailBarWakeUpState(boolean enable) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public boolean getCocktaiBarWakeUpState() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void sendExtraDataToCocktailBar(Bundle extraData) throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public void removeCocktailUIService() throws RemoteException {
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getConfigVersion() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getPreferWidth() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public String getCategoryFilterStr() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public int getSystemBarAppearance() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarService
        public String getHideEdgeListStr() throws RemoteException {
            return null;
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, ICocktailBarService.DESCRIPTOR);
        }

        public static ICocktailBarService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICocktailBarService.DESCRIPTOR);
            if (iin != null && (iin instanceof ICocktailBarService)) {
                return (ICocktailBarService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICocktailBarService.DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(ICocktailBarService.DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            ICocktailHost _arg0 = ICocktailHost.Stub.asInterface(data.readStrongBinder());
                            String _arg1 = data.readString();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            setCocktailHostCallbacks(_arg0, _arg1, _arg2);
                            reply.writeNoException();
                            return true;
                        case 2:
                            ICocktailHost _arg02 = ICocktailHost.Stub.asInterface(data.readStrongBinder());
                            String _arg12 = data.readString();
                            int _arg22 = data.readInt();
                            data.enforceNoDataAvail();
                            startListening(_arg02, _arg12, _arg22);
                            reply.writeNoException();
                            return true;
                        case 3:
                            String _arg03 = data.readString();
                            data.enforceNoDataAvail();
                            stopListening(_arg03);
                            reply.writeNoException();
                            return true;
                        case 4:
                            int[] _arg04 = data.createIntArray();
                            data.enforceNoDataAvail();
                            setEnabledCocktailIds(_arg04);
                            reply.writeNoException();
                            return true;
                        case 5:
                            int[] _result = getEnabledCocktailIds();
                            reply.writeNoException();
                            reply.writeIntArray(_result);
                            return true;
                        case 6:
                            int[] _result2 = getAllCocktailIds();
                            reply.writeNoException();
                            reply.writeIntArray(_result2);
                            return true;
                        case 7:
                            int _arg05 = data.readInt();
                            data.enforceNoDataAvail();
                            Cocktail _result3 = getCocktail(_arg05);
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        case 8:
                            String _arg06 = data.readString();
                            CocktailInfo _arg13 = (CocktailInfo) data.readTypedObject(CocktailInfo.CREATOR);
                            int _arg23 = data.readInt();
                            data.enforceNoDataAvail();
                            updateCocktail(_arg06, _arg13, _arg23);
                            reply.writeNoException();
                            return true;
                        case 9:
                            String _arg07 = data.readString();
                            RemoteViews _arg14 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                            int _arg24 = data.readInt();
                            data.enforceNoDataAvail();
                            partiallyUpdateCocktail(_arg07, _arg14, _arg24);
                            reply.writeNoException();
                            return true;
                        case 10:
                            String _arg08 = data.readString();
                            RemoteViews _arg15 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
                            int _arg25 = data.readInt();
                            data.enforceNoDataAvail();
                            partiallyUpdateHelpView(_arg08, _arg15, _arg25);
                            reply.writeNoException();
                            return true;
                        case 11:
                            String _arg09 = data.readString();
                            int _arg16 = data.readInt();
                            data.enforceNoDataAvail();
                            showCocktail(_arg09, _arg16);
                            reply.writeNoException();
                            return true;
                        case 12:
                            String _arg010 = data.readString();
                            int _arg17 = data.readInt();
                            int _arg26 = data.readInt();
                            data.enforceNoDataAvail();
                            closeCocktail(_arg010, _arg17, _arg26);
                            reply.writeNoException();
                            return true;
                        case 13:
                            String _arg011 = data.readString();
                            ComponentName _arg18 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            int _result4 = getCocktailId(_arg011, _arg18);
                            reply.writeNoException();
                            reply.writeInt(_result4);
                            return true;
                        case 14:
                            String _arg012 = data.readString();
                            ComponentName _arg19 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            disableCocktail(_arg012, _arg19);
                            reply.writeNoException();
                            return true;
                        case 15:
                            String _arg013 = data.readString();
                            ComponentName _arg110 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            int[] _result5 = getCocktailIds(_arg013, _arg110);
                            reply.writeNoException();
                            reply.writeIntArray(_result5);
                            return true;
                        case 16:
                            String _arg014 = data.readString();
                            int _arg111 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result6 = isBoundCocktailPackage(_arg014, _arg111);
                            reply.writeNoException();
                            reply.writeBoolean(_result6);
                            return true;
                        case 17:
                            String _arg015 = data.readString();
                            ComponentName _arg112 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result7 = isEnabledCocktail(_arg015, _arg112);
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 18:
                            String _arg016 = data.readString();
                            ComponentName _arg113 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            boolean _result8 = isCocktailEnabled(_arg016, _arg113);
                            reply.writeNoException();
                            reply.writeBoolean(_result8);
                            return true;
                        case 19:
                            String _arg017 = data.readString();
                            int _arg114 = data.readInt();
                            int _arg27 = data.readInt();
                            data.enforceNoDataAvail();
                            notifyCocktailViewDataChanged(_arg017, _arg114, _arg27);
                            reply.writeNoException();
                            return true;
                        case 20:
                            String _arg018 = data.readString();
                            int _arg115 = data.readInt();
                            int _arg28 = data.readInt();
                            PendingIntent _arg3 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                            data.enforceNoDataAvail();
                            setOnPullPendingIntent(_arg018, _arg115, _arg28, _arg3);
                            reply.writeNoException();
                            return true;
                        case 21:
                            String _arg019 = data.readString();
                            int _arg116 = data.readInt();
                            Intent _arg29 = (Intent) data.readTypedObject(Intent.CREATOR);
                            IApplicationThread _arg32 = IApplicationThread.Stub.asInterface(data.readStrongBinder());
                            IBinder _arg4 = data.readStrongBinder();
                            IServiceConnection _arg5 = IServiceConnection.Stub.asInterface(data.readStrongBinder());
                            int _arg6 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result9 = bindRemoteViewsService(_arg019, _arg116, _arg29, _arg32, _arg4, _arg5, _arg6);
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 22:
                            String _arg020 = data.readString();
                            int _arg117 = data.readInt();
                            Intent _arg210 = (Intent) data.readTypedObject(Intent.CREATOR);
                            data.enforceNoDataAvail();
                            unbindRemoteViewsService(_arg020, _arg117, _arg210);
                            reply.writeNoException();
                            return true;
                        case 23:
                            int _arg021 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result10 = requestToUpdateCocktail(_arg021);
                            reply.writeNoException();
                            reply.writeBoolean(_result10);
                            return true;
                        case 24:
                            int _arg022 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result11 = requestToDisableCocktail(_arg022);
                            reply.writeNoException();
                            reply.writeBoolean(_result11);
                            return true;
                        case 25:
                            int _arg023 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result12 = requestToUpdateCocktailByCategory(_arg023);
                            reply.writeNoException();
                            reply.writeBoolean(_result12);
                            return true;
                        case 26:
                            int _arg024 = data.readInt();
                            data.enforceNoDataAvail();
                            boolean _result13 = requestToDisableCocktailByCategory(_arg024);
                            reply.writeNoException();
                            reply.writeBoolean(_result13);
                            return true;
                        case 27:
                            boolean _arg025 = data.readBoolean();
                            data.enforceNoDataAvail();
                            notifyKeyguardState(_arg025);
                            reply.writeNoException();
                            return true;
                        case 28:
                            int _arg026 = data.readInt();
                            int _arg118 = data.readInt();
                            data.enforceNoDataAvail();
                            notifyCocktailVisibiltyChanged(_arg026, _arg118);
                            reply.writeNoException();
                            return true;
                        case 29:
                            int _arg027 = data.readInt();
                            data.enforceNoDataAvail();
                            updateCocktailBarVisibility(_arg027);
                            reply.writeNoException();
                            return true;
                        case 30:
                            int _arg028 = data.readInt();
                            data.enforceNoDataAvail();
                            updateCocktailBarPosition(_arg028);
                            reply.writeNoException();
                            return true;
                        case 31:
                            IBinder _arg029 = data.readStrongBinder();
                            ComponentName _arg119 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            registerCocktailBarStateListenerCallback(_arg029, _arg119);
                            reply.writeNoException();
                            return true;
                        case 32:
                            IBinder _arg030 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            unregisterCocktailBarStateListenerCallback(_arg030);
                            reply.writeNoException();
                            return true;
                        case 33:
                            int _result14 = getCocktailBarVisibility();
                            reply.writeNoException();
                            reply.writeInt(_result14);
                            return true;
                        case 34:
                            CocktailBarStateInfo _result15 = getCocktailBarStateInfo();
                            reply.writeNoException();
                            reply.writeTypedObject(_result15, 1);
                            return true;
                        case 35:
                            String _arg031 = data.readString();
                            int _arg120 = data.readInt();
                            data.enforceNoDataAvail();
                            updateCocktailBarWindowType(_arg031, _arg120);
                            reply.writeNoException();
                            return true;
                        case 36:
                            int _result16 = getWindowType();
                            reply.writeNoException();
                            reply.writeInt(_result16);
                            return true;
                        case 37:
                            activateCocktailBar();
                            reply.writeNoException();
                            return true;
                        case 38:
                            deactivateCocktailBar();
                            reply.writeNoException();
                            return true;
                        case 39:
                            IBinder _arg032 = data.readStrongBinder();
                            ComponentName _arg121 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                            data.enforceNoDataAvail();
                            registerSystemUiVisibilityListenerCallback(_arg032, _arg121);
                            reply.writeNoException();
                            return true;
                        case 40:
                            IBinder _arg033 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            unregisterSystemUiVisibilityListenerCallback(_arg033);
                            reply.writeNoException();
                            return true;
                        case 41:
                            int _arg034 = data.readInt();
                            boolean _arg122 = data.readBoolean();
                            data.enforceNoDataAvail();
                            updateWakeupGesture(_arg034, _arg122);
                            reply.writeNoException();
                            return true;
                        case 42:
                            int _arg035 = data.readInt();
                            data.enforceNoDataAvail();
                            updateWakeupArea(_arg035);
                            reply.writeNoException();
                            return true;
                        case 43:
                            boolean _arg036 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setCocktailBarWakeUpState(_arg036);
                            reply.writeNoException();
                            return true;
                        case 44:
                            boolean _result17 = getCocktaiBarWakeUpState();
                            reply.writeNoException();
                            reply.writeBoolean(_result17);
                            return true;
                        case 45:
                            Bundle _arg037 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            sendExtraDataToCocktailBar(_arg037);
                            reply.writeNoException();
                            return true;
                        case 46:
                            removeCocktailUIService();
                            reply.writeNoException();
                            return true;
                        case 47:
                            int _result18 = getConfigVersion();
                            reply.writeNoException();
                            reply.writeInt(_result18);
                            return true;
                        case 48:
                            int _result19 = getPreferWidth();
                            reply.writeNoException();
                            reply.writeInt(_result19);
                            return true;
                        case 49:
                            String _result20 = getCategoryFilterStr();
                            reply.writeNoException();
                            reply.writeString(_result20);
                            return true;
                        case 50:
                            int _result21 = getSystemBarAppearance();
                            reply.writeNoException();
                            reply.writeInt(_result21);
                            return true;
                        case 51:
                            String _result22 = getHideEdgeListStr();
                            reply.writeNoException();
                            reply.writeString(_result22);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ICocktailBarService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICocktailBarService.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setCocktailHostCallbacks(ICocktailHost host, String packageName, int category) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeStrongInterface(host);
                    _data.writeString(packageName);
                    _data.writeInt(category);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void startListening(ICocktailHost host, String packageName, int category) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeStrongInterface(host);
                    _data.writeString(packageName);
                    _data.writeInt(category);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void stopListening(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setEnabledCocktailIds(int[] cocktailIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeIntArray(cocktailIds);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getEnabledCocktailIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getAllCocktailIds() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public Cocktail getCocktail(int cocktailId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(cocktailId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    Cocktail _result = (Cocktail) _reply.readTypedObject(Cocktail.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktail(String callingPackage, CocktailInfo cocktailInfo, int cocktailId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(cocktailInfo, 0);
                    _data.writeInt(cocktailId);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void partiallyUpdateCocktail(String callingPackage, RemoteViews contentView, int cocktailId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(contentView, 0);
                    _data.writeInt(cocktailId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void partiallyUpdateHelpView(String callingPackage, RemoteViews helpView, int cocktailId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(helpView, 0);
                    _data.writeInt(cocktailId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void showCocktail(String callingPackage, int cocktailId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(cocktailId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void closeCocktail(String callingPackage, int cocktailId, int category) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(cocktailId);
                    _data.writeInt(category);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getCocktailId(String callingPackage, ComponentName provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(provider, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void disableCocktail(String callingPackage, ComponentName provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(provider, 0);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int[] getCocktailIds(String callingPackage, ComponentName provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(provider, 0);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isBoundCocktailPackage(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isEnabledCocktail(String callingPackage, ComponentName provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(provider, 0);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean isCocktailEnabled(String callingPackage, ComponentName provider) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeTypedObject(provider, 0);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyCocktailViewDataChanged(String callingPackage, int cocktailId, int viewId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(cocktailId);
                    _data.writeInt(viewId);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setOnPullPendingIntent(String callingPackage, int cocktailId, int viewId, PendingIntent pendingIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(cocktailId);
                    _data.writeInt(viewId);
                    _data.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean bindRemoteViewsService(String callingPakcage, int cocktailId, Intent intent, IApplicationThread caller, IBinder activityToken, IServiceConnection connection, int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPakcage);
                    _data.writeInt(cocktailId);
                    _data.writeTypedObject(intent, 0);
                    _data.writeStrongInterface(caller);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongInterface(connection);
                    _data.writeInt(flags);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unbindRemoteViewsService(String callingPackage, int cocktailId, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(cocktailId);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToUpdateCocktail(int cocktailId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(cocktailId);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToDisableCocktail(int cocktailId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(cocktailId);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToUpdateCocktailByCategory(int category) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(category);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean requestToDisableCocktailByCategory(int category) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(category);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyKeyguardState(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void notifyCocktailVisibiltyChanged(int cocktailId, int visibility) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(cocktailId);
                    _data.writeInt(visibility);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarVisibility(int visibility) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(visibility);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarPosition(int position) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(position);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void registerCocktailBarStateListenerCallback(IBinder callback, ComponentName cm) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    _data.writeTypedObject(cm, 0);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unregisterCocktailBarStateListenerCallback(IBinder callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getCocktailBarVisibility() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public CocktailBarStateInfo getCocktailBarStateInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    CocktailBarStateInfo _result = (CocktailBarStateInfo) _reply.readTypedObject(CocktailBarStateInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateCocktailBarWindowType(String callingPackage, int windowType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(windowType);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getWindowType() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void activateCocktailBar() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void deactivateCocktailBar() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void registerSystemUiVisibilityListenerCallback(IBinder callback, ComponentName cm) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    _data.writeTypedObject(cm, 0);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void unregisterSystemUiVisibilityListenerCallback(IBinder callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeStrongBinder(callback);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateWakeupGesture(int gestureType, boolean bEnable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(gestureType);
                    _data.writeBoolean(bEnable);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void updateWakeupArea(int area) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeInt(area);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void setCocktailBarWakeUpState(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public boolean getCocktaiBarWakeUpState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void sendExtraDataToCocktailBar(Bundle extraData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    _data.writeTypedObject(extraData, 0);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public void removeCocktailUIService() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getConfigVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getPreferWidth() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public String getCategoryFilterStr() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public int getSystemBarAppearance() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cocktailbar.ICocktailBarService
            public String getHideEdgeListStr() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICocktailBarService.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 50;
        }
    }
}
