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
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IAutoFillManagerClient extends IInterface {

    public static class Default implements IAutoFillManagerClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void authenticate(int i, int i2, IntentSender intentSender, Intent intent, boolean z) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void autofillContent(int i, AutofillId autofillId, ClipData clipData) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void dispatchUnhandledKey(int i, AutofillId autofillId, KeyEvent keyEvent) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void getAugmentedAutofillClient(IResultReceiver iResultReceiver) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyDisableAutofill(long j, ComponentName componentName) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillDialogTriggerIds(List<AutofillId> list) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiHidden(int i, AutofillId autofillId) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyFillUiShown(int i, AutofillId autofillId) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void notifyNoFillUi(int i, AutofillId autofillId, int i2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialException(int i, AutofillId autofillId, String str, String str2) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void onGetCredentialResponse(int i, AutofillId autofillId, GetCredentialResponse getCredentialResponse) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestHideFillUiWhenDestroyed(int i, AutofillId autofillId) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void requestShowSoftInput(AutofillId autofillId) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSaveUiState(int i, boolean z) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setSessionFinished(int i, List<AutofillId> list) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setState(int i) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void setTrackedViews(int i, AutofillId[] autofillIdArr, boolean z, boolean z2, AutofillId[] autofillIdArr2, AutofillId autofillId, boolean z3) throws RemoteException {
        }

        @Override // android.view.autofill.IAutoFillManagerClient
        public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
        }
    }

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

    void setTrackedViews(int i, AutofillId[] autofillIdArr, boolean z, boolean z2, AutofillId[] autofillIdArr2, AutofillId autofillId, boolean z3) throws RemoteException;

    void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException;

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IAutoFillManagerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAutoFillManagerClient)) {
                return (IAutoFillManagerClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setState(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(AutofillValue.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    autofill(readInt2, createTypedArrayList, createTypedArrayList2, readBoolean);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    GetCredentialResponse getCredentialResponse = (GetCredentialResponse) parcel.readTypedObject(GetCredentialResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGetCredentialResponse(readInt3, autofillId, getCredentialResponse);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    AutofillId autofillId2 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onGetCredentialException(readInt4, autofillId2, readString, readString2);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    AutofillId autofillId3 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    ClipData clipData = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    autofillContent(readInt5, autofillId3, clipData);
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    authenticate(readInt6, readInt7, intentSender, intent, readBoolean2);
                    return true;
                case 7:
                    int readInt8 = parcel.readInt();
                    AutofillId[] autofillIdArr = (AutofillId[]) parcel.createTypedArray(AutofillId.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    boolean readBoolean4 = parcel.readBoolean();
                    AutofillId[] autofillIdArr2 = (AutofillId[]) parcel.createTypedArray(AutofillId.CREATOR);
                    AutofillId autofillId4 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTrackedViews(readInt8, autofillIdArr, readBoolean3, readBoolean4, autofillIdArr2, autofillId4, readBoolean5);
                    return true;
                case 8:
                    int readInt9 = parcel.readInt();
                    AutofillId autofillId5 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    IAutofillWindowPresenter asInterface = IAutofillWindowPresenter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestShowFillUi(readInt9, autofillId5, readInt10, readInt11, rect, asInterface);
                    return true;
                case 9:
                    int readInt12 = parcel.readInt();
                    AutofillId autofillId6 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUi(readInt12, autofillId6);
                    return true;
                case 10:
                    int readInt13 = parcel.readInt();
                    AutofillId autofillId7 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUiWhenDestroyed(readInt13, autofillId7);
                    return true;
                case 11:
                    int readInt14 = parcel.readInt();
                    AutofillId autofillId8 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyNoFillUi(readInt14, autofillId8, readInt15);
                    return true;
                case 12:
                    int readInt16 = parcel.readInt();
                    AutofillId autofillId9 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillUiShown(readInt16, autofillId9);
                    return true;
                case 13:
                    int readInt17 = parcel.readInt();
                    AutofillId autofillId10 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillUiHidden(readInt17, autofillId10);
                    return true;
                case 14:
                    int readInt18 = parcel.readInt();
                    AutofillId autofillId11 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchUnhandledKey(readInt18, autofillId11, keyEvent);
                    return true;
                case 15:
                    IntentSender intentSender2 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startIntentSender(intentSender2, intent2);
                    return true;
                case 16:
                    int readInt19 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSaveUiState(readInt19, readBoolean6);
                    return true;
                case 17:
                    int readInt20 = parcel.readInt();
                    ArrayList createTypedArrayList3 = parcel.createTypedArrayList(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSessionFinished(readInt20, createTypedArrayList3);
                    return true;
                case 18:
                    IResultReceiver asInterface2 = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAugmentedAutofillClient(asInterface2);
                    return true;
                case 19:
                    long readLong = parcel.readLong();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDisableAutofill(readLong, componentName);
                    return true;
                case 20:
                    AutofillId autofillId12 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestShowSoftInput(autofillId12);
                    return true;
                case 21:
                    ArrayList createTypedArrayList4 = parcel.createTypedArrayList(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillDialogTriggerIds(createTypedArrayList4);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IAutoFillManagerClient {
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

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setState(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeTypedList(list2, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialResponse(int i, AutofillId autofillId, GetCredentialResponse getCredentialResponse) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(getCredentialResponse, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialException(int i, AutofillId autofillId, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofillContent(int i, AutofillId autofillId, ClipData clipData) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void authenticate(int i, int i2, IntentSender intentSender, Intent intent, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setTrackedViews(int i, AutofillId[] autofillIdArr, boolean z, boolean z2, AutofillId[] autofillIdArr2, AutofillId autofillId, boolean z3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(autofillIdArr, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeTypedArray(autofillIdArr2, 0);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeBoolean(z3);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeStrongInterface(iAutofillWindowPresenter);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUiWhenDestroyed(int i, AutofillId autofillId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyNoFillUi(int i, AutofillId autofillId, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiShown(int i, AutofillId autofillId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiHidden(int i, AutofillId autofillId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void dispatchUnhandledKey(int i, AutofillId autofillId, KeyEvent keyEvent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(autofillId, 0);
                    obtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intentSender, 0);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSaveUiState(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSessionFinished(int i, List<AutofillId> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void getAugmentedAutofillClient(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyDisableAutofill(long j, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowSoftInput(AutofillId autofillId) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillDialogTriggerIds(List<AutofillId> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
