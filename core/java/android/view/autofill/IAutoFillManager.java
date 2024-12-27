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

import com.android.internal.os.IResultReceiver;

import java.util.List;

public interface IAutoFillManager extends IInterface {
    void addClient(
            IAutoFillManagerClient iAutoFillManagerClient,
            ComponentName componentName,
            int i,
            IResultReceiver iResultReceiver,
            boolean z)
            throws RemoteException;

    void cancelSession(int i, int i2) throws RemoteException;

    void disableOwnedAutofillServices(int i) throws RemoteException;

    void finishSession(int i, int i2, int i3) throws RemoteException;

    void getAutofillServiceComponentName(IResultReceiver iResultReceiver) throws RemoteException;

    void getAvailableFieldClassificationAlgorithms(IResultReceiver iResultReceiver)
            throws RemoteException;

    void getDefaultFieldClassificationAlgorithm(IResultReceiver iResultReceiver)
            throws RemoteException;

    void getFillEventHistory(IResultReceiver iResultReceiver) throws RemoteException;

    void getUserData(IResultReceiver iResultReceiver) throws RemoteException;

    void getUserDataId(IResultReceiver iResultReceiver) throws RemoteException;

    void isFieldClassificationEnabled(IResultReceiver iResultReceiver) throws RemoteException;

    void isServiceEnabled(int i, String str, IResultReceiver iResultReceiver)
            throws RemoteException;

    void isServiceSupported(int i, IResultReceiver iResultReceiver) throws RemoteException;

    void onPendingSaveUi(int i, IBinder iBinder) throws RemoteException;

    void removeClient(IAutoFillManagerClient iAutoFillManagerClient, int i) throws RemoteException;

    void restoreSession(int i, IBinder iBinder, IBinder iBinder2, IResultReceiver iResultReceiver)
            throws RemoteException;

    void setAugmentedAutofillWhitelist(
            List<String> list, List<ComponentName> list2, IResultReceiver iResultReceiver)
            throws RemoteException;

    void setAuthenticationResult(Bundle bundle, int i, int i2, int i3) throws RemoteException;

    void setAutofillFailure(int i, List<AutofillId> list, int i2) throws RemoteException;

    void setHasCallback(int i, int i2, boolean z) throws RemoteException;

    void setUserData(UserData userData) throws RemoteException;

    void setViewAutofilled(int i, AutofillId autofillId, int i2) throws RemoteException;

    void startSession(
            IBinder iBinder,
            IBinder iBinder2,
            AutofillId autofillId,
            Rect rect,
            AutofillValue autofillValue,
            int i,
            boolean z,
            int i2,
            ComponentName componentName,
            boolean z2,
            IResultReceiver iResultReceiver)
            throws RemoteException;

    void updateSession(
            int i,
            AutofillId autofillId,
            Rect rect,
            AutofillValue autofillValue,
            int i2,
            int i3,
            int i4)
            throws RemoteException;

    public static class Default implements IAutoFillManager {
        @Override // android.view.autofill.IAutoFillManager
        public void addClient(
                IAutoFillManagerClient client,
                ComponentName componentName,
                int userId,
                IResultReceiver result,
                boolean credmanRequested)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void removeClient(IAutoFillManagerClient client, int userId)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void startSession(
                IBinder activityToken,
                IBinder appCallback,
                AutofillId autoFillId,
                Rect bounds,
                AutofillValue value,
                int userId,
                boolean hasCallback,
                int flags,
                ComponentName componentName,
                boolean compatMode,
                IResultReceiver result)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void getFillEventHistory(IResultReceiver result) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void restoreSession(
                int sessionId, IBinder activityToken, IBinder appCallback, IResultReceiver result)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void updateSession(
                int sessionId,
                AutofillId id,
                Rect bounds,
                AutofillValue value,
                int action,
                int flags,
                int userId)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void setAutofillFailure(int sessionId, List<AutofillId> ids, int userId)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void setViewAutofilled(int sessionId, AutofillId id, int userId)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void finishSession(int sessionId, int userId, int commitReason)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void cancelSession(int sessionId, int userId) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void setAuthenticationResult(
                Bundle data, int sessionId, int authenticationId, int userId)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void setHasCallback(int sessionId, int userId, boolean hasIt)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void disableOwnedAutofillServices(int userId) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void isServiceSupported(int userId, IResultReceiver result) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void isServiceEnabled(int userId, String packageName, IResultReceiver result)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void onPendingSaveUi(int operation, IBinder token) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void getUserData(IResultReceiver result) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void getUserDataId(IResultReceiver result) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void setUserData(UserData userData) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void isFieldClassificationEnabled(IResultReceiver result) throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void getAutofillServiceComponentName(IResultReceiver result)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void getAvailableFieldClassificationAlgorithms(IResultReceiver result)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void getDefaultFieldClassificationAlgorithm(IResultReceiver result)
                throws RemoteException {}

        @Override // android.view.autofill.IAutoFillManager
        public void setAugmentedAutofillWhitelist(
                List<String> packages, List<ComponentName> activities, IResultReceiver result)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IAutoFillManager {
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
        static final int TRANSACTION_onPendingSaveUi = 16;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_restoreSession = 5;
        static final int TRANSACTION_setAugmentedAutofillWhitelist = 24;
        static final int TRANSACTION_setAuthenticationResult = 11;
        static final int TRANSACTION_setAutofillFailure = 7;
        static final int TRANSACTION_setHasCallback = 12;
        static final int TRANSACTION_setUserData = 19;
        static final int TRANSACTION_setViewAutofilled = 8;
        static final int TRANSACTION_startSession = 3;
        static final int TRANSACTION_updateSession = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAutoFillManager)) {
                return (IAutoFillManager) iin;
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
                    IAutoFillManagerClient _arg0 =
                            IAutoFillManagerClient.Stub.asInterface(data.readStrongBinder());
                    ComponentName _arg1 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    int _arg2 = data.readInt();
                    IResultReceiver _arg3 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    boolean _arg4 = data.readBoolean();
                    data.enforceNoDataAvail();
                    addClient(_arg0, _arg1, _arg2, _arg3, _arg4);
                    return true;
                case 2:
                    IAutoFillManagerClient _arg02 =
                            IAutoFillManagerClient.Stub.asInterface(data.readStrongBinder());
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    removeClient(_arg02, _arg12);
                    return true;
                case 3:
                    IBinder _arg03 = data.readStrongBinder();
                    IBinder _arg13 = data.readStrongBinder();
                    AutofillId _arg22 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    Rect _arg32 = (Rect) data.readTypedObject(Rect.CREATOR);
                    AutofillValue _arg42 =
                            (AutofillValue) data.readTypedObject(AutofillValue.CREATOR);
                    int _arg5 = data.readInt();
                    boolean _arg6 = data.readBoolean();
                    int _arg7 = data.readInt();
                    ComponentName _arg8 =
                            (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    boolean _arg9 = data.readBoolean();
                    IResultReceiver _arg10 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    startSession(
                            _arg03, _arg13, _arg22, _arg32, _arg42, _arg5, _arg6, _arg7, _arg8,
                            _arg9, _arg10);
                    return true;
                case 4:
                    IResultReceiver _arg04 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getFillEventHistory(_arg04);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    IBinder _arg14 = data.readStrongBinder();
                    IBinder _arg23 = data.readStrongBinder();
                    IResultReceiver _arg33 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    restoreSession(_arg05, _arg14, _arg23, _arg33);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    AutofillId _arg15 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    Rect _arg24 = (Rect) data.readTypedObject(Rect.CREATOR);
                    AutofillValue _arg34 =
                            (AutofillValue) data.readTypedObject(AutofillValue.CREATOR);
                    int _arg43 = data.readInt();
                    int _arg52 = data.readInt();
                    int _arg62 = data.readInt();
                    data.enforceNoDataAvail();
                    updateSession(_arg06, _arg15, _arg24, _arg34, _arg43, _arg52, _arg62);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    List<AutofillId> _arg16 = data.createTypedArrayList(AutofillId.CREATOR);
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    setAutofillFailure(_arg07, _arg16, _arg25);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    AutofillId _arg17 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    setViewAutofilled(_arg08, _arg17, _arg26);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    int _arg18 = data.readInt();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    finishSession(_arg09, _arg18, _arg27);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    cancelSession(_arg010, _arg19);
                    return true;
                case 11:
                    Bundle _arg011 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    int _arg110 = data.readInt();
                    int _arg28 = data.readInt();
                    int _arg35 = data.readInt();
                    data.enforceNoDataAvail();
                    setAuthenticationResult(_arg011, _arg110, _arg28, _arg35);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    int _arg111 = data.readInt();
                    boolean _arg29 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setHasCallback(_arg012, _arg111, _arg29);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    disableOwnedAutofillServices(_arg013);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    IResultReceiver _arg112 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    isServiceSupported(_arg014, _arg112);
                    return true;
                case 15:
                    int _arg015 = data.readInt();
                    String _arg113 = data.readString();
                    IResultReceiver _arg210 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    isServiceEnabled(_arg015, _arg113, _arg210);
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    IBinder _arg114 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    onPendingSaveUi(_arg016, _arg114);
                    return true;
                case 17:
                    IResultReceiver _arg017 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getUserData(_arg017);
                    return true;
                case 18:
                    IResultReceiver _arg018 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getUserDataId(_arg018);
                    return true;
                case 19:
                    UserData _arg019 = (UserData) data.readTypedObject(UserData.CREATOR);
                    data.enforceNoDataAvail();
                    setUserData(_arg019);
                    return true;
                case 20:
                    IResultReceiver _arg020 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    isFieldClassificationEnabled(_arg020);
                    return true;
                case 21:
                    IResultReceiver _arg021 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getAutofillServiceComponentName(_arg021);
                    return true;
                case 22:
                    IResultReceiver _arg022 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getAvailableFieldClassificationAlgorithms(_arg022);
                    return true;
                case 23:
                    IResultReceiver _arg023 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getDefaultFieldClassificationAlgorithm(_arg023);
                    return true;
                case 24:
                    List<String> _arg024 = data.createStringArrayList();
                    List<ComponentName> _arg115 = data.createTypedArrayList(ComponentName.CREATOR);
                    IResultReceiver _arg211 =
                            IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setAugmentedAutofillWhitelist(_arg024, _arg115, _arg211);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAutoFillManager {
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

            @Override // android.view.autofill.IAutoFillManager
            public void addClient(
                    IAutoFillManagerClient client,
                    ComponentName componentName,
                    int userId,
                    IResultReceiver result,
                    boolean credmanRequested)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeTypedObject(componentName, 0);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(result);
                    _data.writeBoolean(credmanRequested);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void removeClient(IAutoFillManagerClient client, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(client);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void startSession(
                    IBinder activityToken,
                    IBinder appCallback,
                    AutofillId autoFillId,
                    Rect bounds,
                    AutofillValue value,
                    int userId,
                    boolean hasCallback,
                    int flags,
                    ComponentName componentName,
                    boolean compatMode,
                    IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(activityToken);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStrongBinder(appCallback);
                } catch (Throwable th2) {
                    th = th2;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(autoFillId, 0);
                } catch (Throwable th3) {
                    th = th3;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedObject(bounds, 0);
                    try {
                        _data.writeTypedObject(value, 0);
                    } catch (Throwable th4) {
                        th = th4;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th5) {
                        th = th5;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(hasCallback);
                    } catch (Throwable th6) {
                        th = th6;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeInt(flags);
                    try {
                        _data.writeTypedObject(componentName, 0);
                    } catch (Throwable th8) {
                        th = th8;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeBoolean(compatMode);
                        try {
                            _data.writeStrongInterface(result);
                        } catch (Throwable th9) {
                            th = th9;
                        }
                    } catch (Throwable th10) {
                        th = th10;
                        _data.recycle();
                        throw th;
                    }
                    try {
                        this.mRemote.transact(3, _data, null, 1);
                        _data.recycle();
                    } catch (Throwable th11) {
                        th = th11;
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getFillEventHistory(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void restoreSession(
                    int sessionId,
                    IBinder activityToken,
                    IBinder appCallback,
                    IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeStrongBinder(activityToken);
                    _data.writeStrongBinder(appCallback);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void updateSession(
                    int sessionId,
                    AutofillId id,
                    Rect bounds,
                    AutofillValue value,
                    int action,
                    int flags,
                    int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeTypedObject(bounds, 0);
                    _data.writeTypedObject(value, 0);
                    _data.writeInt(action);
                    _data.writeInt(flags);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAutofillFailure(int sessionId, List<AutofillId> ids, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedList(ids, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setViewAutofilled(int sessionId, AutofillId id, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void finishSession(int sessionId, int userId, int commitReason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(userId);
                    _data.writeInt(commitReason);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void cancelSession(int sessionId, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAuthenticationResult(
                    Bundle data, int sessionId, int authenticationId, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(data, 0);
                    _data.writeInt(sessionId);
                    _data.writeInt(authenticationId);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setHasCallback(int sessionId, int userId, boolean hasIt)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(userId);
                    _data.writeBoolean(hasIt);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void disableOwnedAutofillServices(int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isServiceSupported(int userId, IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isServiceEnabled(int userId, String packageName, IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(userId);
                    _data.writeString(packageName);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void onPendingSaveUi(int operation, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(operation);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getUserData(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getUserDataId(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setUserData(UserData userData) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(userData, 0);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void isFieldClassificationEnabled(IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getAutofillServiceComponentName(IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getAvailableFieldClassificationAlgorithms(IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(22, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void getDefaultFieldClassificationAlgorithm(IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(23, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManager
            public void setAugmentedAutofillWhitelist(
                    List<String> packages, List<ComponentName> activities, IResultReceiver result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(packages);
                    _data.writeTypedList(activities, 0);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(24, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 23;
        }
    }
}
