package android.view.autofill;

import android.content.ComponentName;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.autofill.UserData;
import android.view.autofill.IAutoFillManagerClient;
import com.android.internal.os.IResultReceiver;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAutoFillManager extends IInterface {

    public static class Default implements IAutoFillManager {
        @Override // android.view.autofill.IAutoFillManager
        public void addClient(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, int i, IResultReceiver iResultReceiver, boolean z) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.autofill.IAutoFillManager
        public void cancelSession(int i, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void disableOwnedAutofillServices(int i) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void finishSession(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getAutofillServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getFillEventHistory(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getUserData(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void getUserDataId(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void isFieldClassificationEnabled(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void isServiceEnabled(int i, String str, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void isServiceSupported(int i, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void notifyImeAnimationEnd(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void notifyImeAnimationStart(int i, long j, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void notifyNotExpiringResponseDuringAuth(int i, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void notifyViewEnteredIgnoredDuringAuthCount(int i, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void onPendingSaveUi(int i, IBinder iBinder) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int i) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void restoreSession(int i, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setAugmentedAutofillWhitelist(List<String> list, List<ComponentName> list2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setAuthenticationResult(Bundle bundle, int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setAutofillFailure(int i, List<AutofillId> list, boolean z, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setAutofillIdsAttemptedForRefill(int i, List<AutofillId> list, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setHasCallback(int i, int i2, boolean z) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setUserData(UserData userData) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void setViewAutofilled(int i, AutofillId autofillId, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void startSession(IBinder iBinder, IBinder iBinder2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i, boolean z, int i2, ComponentName componentName, boolean z2, IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManager
        public void updateSession(int i, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i2, int i3, int i4) throws RemoteException {
        }
    }

    void addClient(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, int i, IResultReceiver iResultReceiver, boolean z) throws RemoteException;

    void cancelSession(int i, int i2) throws RemoteException;

    void disableOwnedAutofillServices(int i) throws RemoteException;

    void finishSession(int i, int i2, int i3) throws RemoteException;

    void getAutofillServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException;

    void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) throws RemoteException;

    void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) throws RemoteException;

    void getFillEventHistory(IResultReceiver iResultReceiver) throws RemoteException;

    void getUserData(IResultReceiver iResultReceiver) throws RemoteException;

    void getUserDataId(IResultReceiver iResultReceiver) throws RemoteException;

    void isFieldClassificationEnabled(IResultReceiver iResultReceiver) throws RemoteException;

    void isServiceEnabled(int i, String str, IResultReceiver iResultReceiver) throws RemoteException;

    void isServiceSupported(int i, IResultReceiver iResultReceiver) throws RemoteException;

    void notifyImeAnimationEnd(int i, long j, int i2) throws RemoteException;

    void notifyImeAnimationStart(int i, long j, int i2) throws RemoteException;

    void notifyNotExpiringResponseDuringAuth(int i, int i2) throws RemoteException;

    void notifyViewEnteredIgnoredDuringAuthCount(int i, int i2) throws RemoteException;

    void onPendingSaveUi(int i, IBinder iBinder) throws RemoteException;

    void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int i) throws RemoteException;

    void restoreSession(int i, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) throws RemoteException;

    void setAugmentedAutofillWhitelist(List<String> list, List<ComponentName> list2, IResultReceiver iResultReceiver) throws RemoteException;

    void setAuthenticationResult(Bundle bundle, int i, int i2, int i3) throws RemoteException;

    void setAutofillFailure(int i, List<AutofillId> list, boolean z, int i2) throws RemoteException;

    void setAutofillIdsAttemptedForRefill(int i, List<AutofillId> list, int i2) throws RemoteException;

    void setHasCallback(int i, int i2, boolean z) throws RemoteException;

    void setUserData(UserData userData) throws RemoteException;

    void setViewAutofilled(int i, AutofillId autofillId, int i2) throws RemoteException;

    void startSession(IBinder iBinder, IBinder iBinder2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i, boolean z, int i2, ComponentName componentName, boolean z2, IResultReceiver iResultReceiver) throws RemoteException;

    void updateSession(int i, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i2, int i3, int i4) throws RemoteException;

    public static abstract class Stub extends Binder implements IAutoFillManager {
        public static final String DESCRIPTOR = "android.view.autofill.IAutoFillManager";
        static final int TRANSACTION_addClient = 1;
        static final int TRANSACTION_cancelSession = 10;
        static final int TRANSACTION_disableOwnedAutofillServices = 13;
        static final int TRANSACTION_finishSession = 9;
        static final int TRANSACTION_getAutofillServiceComponentName = 21;
        static final int TRANSACTION_getAvailableFieldClassificationAlgorithms = 22;
        static final int TRANSACTION_getDefaultFieldClassificationAlgorithm = 23;
        static final int TRANSACTION_getFillEventHistory = 4;
        static final int TRANSACTION_getUserData = 17;
        static final int TRANSACTION_getUserDataId = 18;
        static final int TRANSACTION_isFieldClassificationEnabled = 20;
        static final int TRANSACTION_isServiceEnabled = 15;
        static final int TRANSACTION_isServiceSupported = 14;
        static final int TRANSACTION_notifyImeAnimationEnd = 29;
        static final int TRANSACTION_notifyImeAnimationStart = 28;
        static final int TRANSACTION_notifyNotExpiringResponseDuringAuth = 25;
        static final int TRANSACTION_notifyViewEnteredIgnoredDuringAuthCount = 26;
        static final int TRANSACTION_onPendingSaveUi = 16;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_restoreSession = 5;
        static final int TRANSACTION_setAugmentedAutofillWhitelist = 24;
        static final int TRANSACTION_setAuthenticationResult = 11;
        static final int TRANSACTION_setAutofillFailure = 7;
        static final int TRANSACTION_setAutofillIdsAttemptedForRefill = 27;
        static final int TRANSACTION_setHasCallback = 12;
        static final int TRANSACTION_setUserData = 19;
        static final int TRANSACTION_setViewAutofilled = 8;
        static final int TRANSACTION_startSession = 3;
        static final int TRANSACTION_updateSession = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 28;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAutoFillManager)) {
                return (IAutoFillManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addClient";
                case 2:
                    return "removeClient";
                case 3:
                    return "startSession";
                case 4:
                    return "getFillEventHistory";
                case 5:
                    return "restoreSession";
                case 6:
                    return "updateSession";
                case 7:
                    return "setAutofillFailure";
                case 8:
                    return "setViewAutofilled";
                case 9:
                    return "finishSession";
                case 10:
                    return "cancelSession";
                case 11:
                    return "setAuthenticationResult";
                case 12:
                    return "setHasCallback";
                case 13:
                    return "disableOwnedAutofillServices";
                case 14:
                    return "isServiceSupported";
                case 15:
                    return "isServiceEnabled";
                case 16:
                    return "onPendingSaveUi";
                case 17:
                    return "getUserData";
                case 18:
                    return "getUserDataId";
                case 19:
                    return "setUserData";
                case 20:
                    return "isFieldClassificationEnabled";
                case 21:
                    return "getAutofillServiceComponentName";
                case 22:
                    return "getAvailableFieldClassificationAlgorithms";
                case 23:
                    return "getDefaultFieldClassificationAlgorithm";
                case 24:
                    return "setAugmentedAutofillWhitelist";
                case 25:
                    return "notifyNotExpiringResponseDuringAuth";
                case 26:
                    return "notifyViewEnteredIgnoredDuringAuthCount";
                case 27:
                    return "setAutofillIdsAttemptedForRefill";
                case 28:
                    return "notifyImeAnimationStart";
                case 29:
                    return "notifyImeAnimationEnd";
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
                    IAutoFillManagerClient asInterface = IAutoFillManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    IResultReceiver asInterface2 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    addClient(asInterface, componentName, readInt, asInterface2, readBoolean);
                    return true;
                case 2:
                    IAutoFillManagerClient asInterface3 = IAutoFillManagerClient.Stub.asInterface(parcel.readStrongBinder());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeClient(asInterface3, readInt2);
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    AutofillValue autofillValue = (AutofillValue) parcel.readTypedObject(AutofillValue.CREATOR);
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    IResultReceiver asInterface4 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startSession(readStrongBinder, readStrongBinder2, autofillId, rect, autofillValue, readInt3, readBoolean2, readInt4, componentName2, readBoolean3, asInterface4);
                    return true;
                case 4:
                    IResultReceiver asInterface5 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getFillEventHistory(asInterface5);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    IResultReceiver asInterface6 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    restoreSession(readInt5, readStrongBinder3, readStrongBinder4, asInterface6);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    AutofillId autofillId2 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    Rect rect2 = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    AutofillValue autofillValue2 = (AutofillValue) parcel.readTypedObject(AutofillValue.CREATOR);
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateSession(readInt6, autofillId2, rect2, autofillValue2, readInt7, readInt8, readInt9);
                    return true;
                case 7:
                    int readInt10 = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutofillFailure(readInt10, createTypedArrayList, readBoolean4, readInt11);
                    return true;
                case 8:
                    int readInt12 = parcel.readInt();
                    AutofillId autofillId3 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setViewAutofilled(readInt12, autofillId3, readInt13);
                    return true;
                case 9:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    finishSession(readInt14, readInt15, readInt16);
                    return true;
                case 10:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelSession(readInt17, readInt18);
                    return true;
                case 11:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAuthenticationResult(bundle, readInt19, readInt20, readInt21);
                    return true;
                case 12:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHasCallback(readInt22, readInt23, readBoolean5);
                    return true;
                case 13:
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    disableOwnedAutofillServices(readInt24);
                    return true;
                case 14:
                    int readInt25 = parcel.readInt();
                    IResultReceiver asInterface7 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isServiceSupported(readInt25, asInterface7);
                    return true;
                case 15:
                    int readInt26 = parcel.readInt();
                    String readString = parcel.readString();
                    IResultReceiver asInterface8 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isServiceEnabled(readInt26, readString, asInterface8);
                    return true;
                case 16:
                    int readInt27 = parcel.readInt();
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    onPendingSaveUi(readInt27, readStrongBinder5);
                    return true;
                case 17:
                    IResultReceiver asInterface9 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getUserData(asInterface9);
                    return true;
                case 18:
                    IResultReceiver asInterface10 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getUserDataId(asInterface10);
                    return true;
                case 19:
                    UserData userData = (UserData) parcel.readTypedObject(UserData.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUserData(userData);
                    return true;
                case 20:
                    IResultReceiver asInterface11 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    isFieldClassificationEnabled(asInterface11);
                    return true;
                case 21:
                    IResultReceiver asInterface12 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAutofillServiceComponentName(asInterface12);
                    return true;
                case 22:
                    IResultReceiver asInterface13 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAvailableFieldClassificationAlgorithms(asInterface13);
                    return true;
                case 23:
                    IResultReceiver asInterface14 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getDefaultFieldClassificationAlgorithm(asInterface14);
                    return true;
                case 24:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(ComponentName.CREATOR);
                    IResultReceiver asInterface15 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setAugmentedAutofillWhitelist(createStringArrayList, createTypedArrayList2, asInterface15);
                    return true;
                case 25:
                    int readInt28 = parcel.readInt();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyNotExpiringResponseDuringAuth(readInt28, readInt29);
                    return true;
                case 26:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyViewEnteredIgnoredDuringAuthCount(readInt30, readInt31);
                    return true;
                case 27:
                    int readInt32 = parcel.readInt();
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(AutofillId.CREATOR);
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAutofillIdsAttemptedForRefill(readInt32, createTypedArrayList3, readInt33);
                    return true;
                case 28:
                    int readInt34 = parcel.readInt();
                    long readLong = parcel.readLong();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyImeAnimationStart(readInt34, readLong, readInt35);
                    return true;
                case 29:
                    int readInt36 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyImeAnimationEnd(readInt36, readLong2, readInt37);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAutoFillManager {
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

            @Override // android.view.autofill.IAutoFillManager
            public void addClient(IAutoFillManagerClient iAutoFillManagerClient, ComponentName componentName, int i, IResultReceiver iResultReceiver, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAutoFillManagerClient);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iAutoFillManagerClient);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void startSession(IBinder iBinder, IBinder iBinder2, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i, boolean z, int i2, ComponentName componentName, boolean z2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(autofillValue, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z2);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getFillEventHistory(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void restoreSession(int i, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void updateSession(int i, AutofillId autofillId, Rect rect, AutofillValue autofillValue, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(autofillValue, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAutofillFailure(int i, List<AutofillId> list, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setViewAutofilled(int i, AutofillId autofillId, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void finishSession(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void cancelSession(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAuthenticationResult(Bundle bundle, int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setHasCallback(int i, int i2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void disableOwnedAutofillServices(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isServiceSupported(int i, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isServiceEnabled(int i, String str, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void onPendingSaveUi(int i, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getUserData(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getUserDataId(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setUserData(UserData userData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userData, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isFieldClassificationEnabled(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getAutofillServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAugmentedAutofillWhitelist(List<String> list, List<ComponentName> list2, IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void notifyNotExpiringResponseDuringAuth(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void notifyViewEnteredIgnoredDuringAuthCount(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAutofillIdsAttemptedForRefill(int i, List<AutofillId> list, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void notifyImeAnimationStart(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void notifyImeAnimationEnd(int i, long j, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeInt(i2);
                    this.mRemote.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
