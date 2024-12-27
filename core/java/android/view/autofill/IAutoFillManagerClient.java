package android.view.autofill;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.credentials.GetCredentialResponse;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.autofill.IAutofillWindowPresenter;
import com.android.internal.os.IResultReceiver;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAutoFillManagerClient extends IInterface {
    void authenticate(int i, int i2, IntentSender intentSender, Intent intent, boolean z) throws RemoteException;

    void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) throws RemoteException;

    void autofillContent(int i, AutofillId autofillId, ClipData clipData) throws RemoteException;

    void dispatchUnhandledKey(int i, AutofillId autofillId, KeyEvent keyEvent) throws RemoteException;

    void getAugmentedAutofillClient(IResultReceiver iResultReceiver) throws RemoteException;

    void notifyDisableAutofill(long j, ComponentName componentName) throws RemoteException;

    void notifyFillDialogTriggerIds(List<AutofillId> list) throws RemoteException;

    void notifyFillUiHidden(int i, AutofillId autofillId) throws RemoteException;

    void notifyFillUiShown(int i, AutofillId autofillId) throws RemoteException;

    void notifyNoFillUi(int i, AutofillId autofillId, int i2) throws RemoteException;

    void onGetCredentialException(int i, AutofillId autofillId, String str, String str2) throws RemoteException;

    void onGetCredentialResponse(int i, AutofillId autofillId, GetCredentialResponse getCredentialResponse) throws RemoteException;

    void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException;

    void requestHideFillUiWhenDestroyed(int i, AutofillId autofillId) throws RemoteException;

    void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException;

    void requestShowSoftInput(AutofillId autofillId) throws RemoteException;

    void setSaveUiState(int i, boolean z) throws RemoteException;

    void setSessionFinished(int i, List<AutofillId> list) throws RemoteException;

    void setState(int i) throws RemoteException;

    void setTrackedViews(int i, AutofillId[] autofillIdArr, boolean z, boolean z2, AutofillId[] autofillIdArr2, AutofillId autofillId) throws RemoteException;

    void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException;

    public static class Default implements IAutoFillManagerClient {
        @Override // android.view.autofill.IAutoFillManagerClient
        public void setState(int flags) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values, boolean hideHighlight) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialResponse(int sessionId, AutofillId id, GetCredentialResponse response) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialException(int sessionId, AutofillId id, String errorType, String errorMsg) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofillContent(int sessionId, AutofillId id, ClipData content) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void authenticate(int sessionId, int authenticationId, IntentSender intent, Intent fillInIntent, boolean authenticateInline) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setTrackedViews(int sessionId, AutofillId[] savableIds, boolean saveOnAllViewsInvisible, boolean saveOnFinish, AutofillId[] fillableIds, AutofillId saveTriggerId) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUi(int sessionId, AutofillId id) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUiWhenDestroyed(int sessionId, AutofillId id) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyNoFillUi(int sessionId, AutofillId id, int sessionFinishedState) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiShown(int sessionId, AutofillId id) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiHidden(int sessionId, AutofillId id) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void dispatchUnhandledKey(int sessionId, AutofillId id, KeyEvent keyEvent) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSaveUiState(int sessionId, boolean shown) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSessionFinished(int newState, List<AutofillId> autofillableIds) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void getAugmentedAutofillClient(IResultReceiver result) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyDisableAutofill(long disableDuration, ComponentName componentName) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowSoftInput(AutofillId id) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillDialogTriggerIds(List<AutofillId> ids) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAutoFillManagerClient {
        public static final String DESCRIPTOR = "android.view.autofill.IAutoFillManagerClient";
        static final int TRANSACTION_authenticate = 6;
        static final int TRANSACTION_autofill = 2;
        static final int TRANSACTION_autofillContent = 5;
        static final int TRANSACTION_dispatchUnhandledKey = 14;
        static final int TRANSACTION_getAugmentedAutofillClient = 18;
        static final int TRANSACTION_notifyDisableAutofill = 19;
        static final int TRANSACTION_notifyFillDialogTriggerIds = 21;
        static final int TRANSACTION_notifyFillUiHidden = 13;
        static final int TRANSACTION_notifyFillUiShown = 12;
        static final int TRANSACTION_notifyNoFillUi = 11;
        static final int TRANSACTION_onGetCredentialException = 4;
        static final int TRANSACTION_onGetCredentialResponse = 3;
        static final int TRANSACTION_requestHideFillUi = 9;
        static final int TRANSACTION_requestHideFillUiWhenDestroyed = 10;
        static final int TRANSACTION_requestShowFillUi = 8;
        static final int TRANSACTION_requestShowSoftInput = 20;
        static final int TRANSACTION_setSaveUiState = 16;
        static final int TRANSACTION_setSessionFinished = 17;
        static final int TRANSACTION_setState = 1;
        static final int TRANSACTION_setTrackedViews = 7;
        static final int TRANSACTION_startIntentSender = 15;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManagerClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IAutoFillManagerClient)) {
                return (IAutoFillManagerClient) iin;
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
                    return "setState";
                case 2:
                    return Context.AUTOFILL_MANAGER_SERVICE;
                case 3:
                    return "onGetCredentialResponse";
                case 4:
                    return "onGetCredentialException";
                case 5:
                    return "autofillContent";
                case 6:
                    return "authenticate";
                case 7:
                    return "setTrackedViews";
                case 8:
                    return "requestShowFillUi";
                case 9:
                    return "requestHideFillUi";
                case 10:
                    return "requestHideFillUiWhenDestroyed";
                case 11:
                    return "notifyNoFillUi";
                case 12:
                    return "notifyFillUiShown";
                case 13:
                    return "notifyFillUiHidden";
                case 14:
                    return "dispatchUnhandledKey";
                case 15:
                    return "startIntentSender";
                case 16:
                    return "setSaveUiState";
                case 17:
                    return "setSessionFinished";
                case 18:
                    return "getAugmentedAutofillClient";
                case 19:
                    return "notifyDisableAutofill";
                case 20:
                    return "requestShowSoftInput";
                case 21:
                    return "notifyFillDialogTriggerIds";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    int _arg0 = data.readInt();
                    data.enforceNoDataAvail();
                    setState(_arg0);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    List<AutofillId> _arg1 = data.createTypedArrayList(AutofillId.CREATOR);
                    List<AutofillValue> _arg2 = data.createTypedArrayList(AutofillValue.CREATOR);
                    boolean _arg3 = data.readBoolean();
                    data.enforceNoDataAvail();
                    autofill(_arg02, _arg1, _arg2, _arg3);
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    AutofillId _arg12 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    GetCredentialResponse _arg22 = (GetCredentialResponse) data.readTypedObject(GetCredentialResponse.CREATOR);
                    data.enforceNoDataAvail();
                    onGetCredentialResponse(_arg03, _arg12, _arg22);
                    return true;
                case 4:
                    int _arg04 = data.readInt();
                    AutofillId _arg13 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    String _arg23 = data.readString();
                    String _arg32 = data.readString();
                    data.enforceNoDataAvail();
                    onGetCredentialException(_arg04, _arg13, _arg23, _arg32);
                    return true;
                case 5:
                    int _arg05 = data.readInt();
                    AutofillId _arg14 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    ClipData _arg24 = (ClipData) data.readTypedObject(ClipData.CREATOR);
                    data.enforceNoDataAvail();
                    autofillContent(_arg05, _arg14, _arg24);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    int _arg15 = data.readInt();
                    IntentSender _arg25 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    Intent _arg33 = (Intent) data.readTypedObject(Intent.CREATOR);
                    boolean _arg4 = data.readBoolean();
                    data.enforceNoDataAvail();
                    authenticate(_arg06, _arg15, _arg25, _arg33, _arg4);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    AutofillId[] _arg16 = (AutofillId[]) data.createTypedArray(AutofillId.CREATOR);
                    boolean _arg26 = data.readBoolean();
                    boolean _arg34 = data.readBoolean();
                    AutofillId[] _arg42 = (AutofillId[]) data.createTypedArray(AutofillId.CREATOR);
                    AutofillId _arg5 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    setTrackedViews(_arg07, _arg16, _arg26, _arg34, _arg42, _arg5);
                    return true;
                case 8:
                    int _arg08 = data.readInt();
                    AutofillId _arg17 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    int _arg27 = data.readInt();
                    int _arg35 = data.readInt();
                    Rect _arg43 = (Rect) data.readTypedObject(Rect.CREATOR);
                    IAutofillWindowPresenter _arg52 = IAutofillWindowPresenter.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    requestShowFillUi(_arg08, _arg17, _arg27, _arg35, _arg43, _arg52);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    AutofillId _arg18 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    requestHideFillUi(_arg09, _arg18);
                    return true;
                case 10:
                    int _arg010 = data.readInt();
                    AutofillId _arg19 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    requestHideFillUiWhenDestroyed(_arg010, _arg19);
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    AutofillId _arg110 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyNoFillUi(_arg011, _arg110, _arg28);
                    return true;
                case 12:
                    int _arg012 = data.readInt();
                    AutofillId _arg111 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    notifyFillUiShown(_arg012, _arg111);
                    return true;
                case 13:
                    int _arg013 = data.readInt();
                    AutofillId _arg112 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    notifyFillUiHidden(_arg013, _arg112);
                    return true;
                case 14:
                    int _arg014 = data.readInt();
                    AutofillId _arg113 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    KeyEvent _arg29 = (KeyEvent) data.readTypedObject(KeyEvent.CREATOR);
                    data.enforceNoDataAvail();
                    dispatchUnhandledKey(_arg014, _arg113, _arg29);
                    return true;
                case 15:
                    IntentSender _arg015 = (IntentSender) data.readTypedObject(IntentSender.CREATOR);
                    Intent _arg114 = (Intent) data.readTypedObject(Intent.CREATOR);
                    data.enforceNoDataAvail();
                    startIntentSender(_arg015, _arg114);
                    return true;
                case 16:
                    int _arg016 = data.readInt();
                    boolean _arg115 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setSaveUiState(_arg016, _arg115);
                    return true;
                case 17:
                    int _arg017 = data.readInt();
                    List<AutofillId> _arg116 = data.createTypedArrayList(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    setSessionFinished(_arg017, _arg116);
                    return true;
                case 18:
                    IResultReceiver _arg018 = IResultReceiver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    getAugmentedAutofillClient(_arg018);
                    return true;
                case 19:
                    long _arg019 = data.readLong();
                    ComponentName _arg117 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
                    data.enforceNoDataAvail();
                    notifyDisableAutofill(_arg019, _arg117);
                    return true;
                case 20:
                    AutofillId _arg020 = (AutofillId) data.readTypedObject(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    requestShowSoftInput(_arg020);
                    return true;
                case 21:
                    List<AutofillId> _arg021 = data.createTypedArrayList(AutofillId.CREATOR);
                    data.enforceNoDataAvail();
                    notifyFillDialogTriggerIds(_arg021);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IAutoFillManagerClient {
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

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setState(int flags) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(flags);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofill(int sessionId, List<AutofillId> ids, List<AutofillValue> values, boolean hideHighlight) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedList(ids, 0);
                    _data.writeTypedList(values, 0);
                    _data.writeBoolean(hideHighlight);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialResponse(int sessionId, AutofillId id, GetCredentialResponse response) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeTypedObject(response, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialException(int sessionId, AutofillId id, String errorType, String errorMsg) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeString(errorType);
                    _data.writeString(errorMsg);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofillContent(int sessionId, AutofillId id, ClipData content) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeTypedObject(content, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void authenticate(int sessionId, int authenticationId, IntentSender intent, Intent fillInIntent, boolean authenticateInline) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(authenticationId);
                    _data.writeTypedObject(intent, 0);
                    _data.writeTypedObject(fillInIntent, 0);
                    _data.writeBoolean(authenticateInline);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setTrackedViews(int sessionId, AutofillId[] savableIds, boolean saveOnAllViewsInvisible, boolean saveOnFinish, AutofillId[] fillableIds, AutofillId saveTriggerId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedArray(savableIds, 0);
                    _data.writeBoolean(saveOnAllViewsInvisible);
                    _data.writeBoolean(saveOnFinish);
                    _data.writeTypedArray(fillableIds, 0);
                    _data.writeTypedObject(saveTriggerId, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowFillUi(int sessionId, AutofillId id, int width, int height, Rect anchorBounds, IAutofillWindowPresenter presenter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeInt(width);
                    _data.writeInt(height);
                    _data.writeTypedObject(anchorBounds, 0);
                    _data.writeStrongInterface(presenter);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUi(int sessionId, AutofillId id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUiWhenDestroyed(int sessionId, AutofillId id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    this.mRemote.transact(10, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyNoFillUi(int sessionId, AutofillId id, int sessionFinishedState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeInt(sessionFinishedState);
                    this.mRemote.transact(11, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiShown(int sessionId, AutofillId id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    this.mRemote.transact(12, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiHidden(int sessionId, AutofillId id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    this.mRemote.transact(13, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void dispatchUnhandledKey(int sessionId, AutofillId id, KeyEvent keyEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(id, 0);
                    _data.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(14, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(intentSender, 0);
                    _data.writeTypedObject(intent, 0);
                    this.mRemote.transact(15, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSaveUiState(int sessionId, boolean shown) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeBoolean(shown);
                    this.mRemote.transact(16, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSessionFinished(int newState, List<AutofillId> autofillableIds) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(newState);
                    _data.writeTypedList(autofillableIds, 0);
                    this.mRemote.transact(17, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void getAugmentedAutofillClient(IResultReceiver result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(result);
                    this.mRemote.transact(18, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyDisableAutofill(long disableDuration, ComponentName componentName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeLong(disableDuration);
                    _data.writeTypedObject(componentName, 0);
                    this.mRemote.transact(19, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowSoftInput(AutofillId id) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(id, 0);
                    this.mRemote.transact(20, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillDialogTriggerIds(List<AutofillId> ids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(ids, 0);
                    this.mRemote.transact(21, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }
    }
}
