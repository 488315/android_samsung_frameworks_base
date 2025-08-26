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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IAutoFillManagerClient)) {
                return (IAutoFillManagerClient) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setState(i3);
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(AutofillId.CREATOR);
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(AutofillValue.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    autofill(i4, arrayListCreateTypedArrayList, arrayListCreateTypedArrayList2, z);
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    AutofillId autofillId = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    GetCredentialResponse getCredentialResponse = (GetCredentialResponse) parcel.readTypedObject(GetCredentialResponse.CREATOR);
                    parcel.enforceNoDataAvail();
                    onGetCredentialResponse(i5, autofillId, getCredentialResponse);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    AutofillId autofillId2 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onGetCredentialException(i6, autofillId2, string, string2);
                    return true;
                case 5:
                    int i7 = parcel.readInt();
                    AutofillId autofillId3 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    ClipData clipData = (ClipData) parcel.readTypedObject(ClipData.CREATOR);
                    parcel.enforceNoDataAvail();
                    autofillContent(i7, autofillId3, clipData);
                    return true;
                case 6:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    IntentSender intentSender = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    authenticate(i8, i9, intentSender, intent, z2);
                    return true;
                case 7:
                    int i10 = parcel.readInt();
                    AutofillId[] autofillIdArr = (AutofillId[]) parcel.createTypedArray(AutofillId.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    boolean z4 = parcel.readBoolean();
                    AutofillId[] autofillIdArr2 = (AutofillId[]) parcel.createTypedArray(AutofillId.CREATOR);
                    AutofillId autofillId4 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTrackedViews(i10, autofillIdArr, z3, z4, autofillIdArr2, autofillId4, z5);
                    return true;
                case 8:
                    int i11 = parcel.readInt();
                    AutofillId autofillId5 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    Rect rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
                    IAutofillWindowPresenter iAutofillWindowPresenterAsInterface = IAutofillWindowPresenter.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestShowFillUi(i11, autofillId5, i12, i13, rect, iAutofillWindowPresenterAsInterface);
                    return true;
                case 9:
                    int i14 = parcel.readInt();
                    AutofillId autofillId6 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUi(i14, autofillId6);
                    return true;
                case 10:
                    int i15 = parcel.readInt();
                    AutofillId autofillId7 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestHideFillUiWhenDestroyed(i15, autofillId7);
                    return true;
                case 11:
                    int i16 = parcel.readInt();
                    AutofillId autofillId8 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyNoFillUi(i16, autofillId8, i17);
                    return true;
                case 12:
                    int i18 = parcel.readInt();
                    AutofillId autofillId9 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillUiShown(i18, autofillId9);
                    return true;
                case 13:
                    int i19 = parcel.readInt();
                    AutofillId autofillId10 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillUiHidden(i19, autofillId10);
                    return true;
                case 14:
                    int i20 = parcel.readInt();
                    AutofillId autofillId11 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    dispatchUnhandledKey(i20, autofillId11, keyEvent);
                    return true;
                case 15:
                    IntentSender intentSender2 = (IntentSender) parcel.readTypedObject(IntentSender.CREATOR);
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startIntentSender(intentSender2, intent2);
                    return true;
                case 16:
                    int i21 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSaveUiState(i21, z6);
                    return true;
                case 17:
                    int i22 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList3 = parcel.createTypedArrayList(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSessionFinished(i22, arrayListCreateTypedArrayList3);
                    return true;
                case 18:
                    IResultReceiver iResultReceiverAsInterface = IResultReceiver.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    getAugmentedAutofillClient(iResultReceiverAsInterface);
                    return true;
                case 19:
                    long j = parcel.readLong();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyDisableAutofill(j, componentName);
                    return true;
                case 20:
                    AutofillId autofillId12 = (AutofillId) parcel.readTypedObject(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestShowSoftInput(autofillId12);
                    return true;
                case 21:
                    ArrayList arrayListCreateTypedArrayList4 = parcel.createTypedArrayList(AutofillId.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyFillDialogTriggerIds(arrayListCreateTypedArrayList4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofill(int i, List<AutofillId> list, List<AutofillValue> list2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeTypedList(list2, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialResponse(int i, AutofillId autofillId, GetCredentialResponse getCredentialResponse) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeTypedObject(getCredentialResponse, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void onGetCredentialException(int i, AutofillId autofillId, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void autofillContent(int i, AutofillId autofillId, ClipData clipData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeTypedObject(clipData, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void authenticate(int i, int i2, IntentSender intentSender, Intent intent, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setTrackedViews(int i, AutofillId[] autofillIdArr, boolean z, boolean z2, AutofillId[] autofillIdArr2, AutofillId autofillId, boolean z3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(autofillIdArr, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    parcelObtain.writeTypedArray(autofillIdArr2, 0);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeBoolean(z3);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowFillUi(int i, AutofillId autofillId, int i2, int i3, Rect rect, IAutofillWindowPresenter iAutofillWindowPresenter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeTypedObject(rect, 0);
                    parcelObtain.writeStrongInterface(iAutofillWindowPresenter);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUi(int i, AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestHideFillUiWhenDestroyed(int i, AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyNoFillUi(int i, AutofillId autofillId, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiShown(int i, AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillUiHidden(int i, AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void dispatchUnhandledKey(int i, AutofillId autofillId, KeyEvent keyEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void startIntentSender(IntentSender intentSender, Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intentSender, 0);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSaveUiState(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void setSessionFinished(int i, List<AutofillId> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void getAugmentedAutofillClient(IResultReceiver iResultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iResultReceiver);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyDisableAutofill(long j, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void requestShowSoftInput(AutofillId autofillId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(autofillId, 0);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutoFillManagerClient
            public void notifyFillDialogTriggerIds(List<AutofillId> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
