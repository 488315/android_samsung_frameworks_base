package android.service.textclassifier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.textclassifier.ITextClassifierCallback;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;

/* loaded from: classes3.dex */
public interface ITextClassifierService extends IInterface {

    public static class Default implements ITextClassifierService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onConnectedStateChanged(int i) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override // android.service.textclassifier.ITextClassifierService
        public void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) throws RemoteException {
        }
    }

    void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onConnectedStateChanged(int i) throws RemoteException;

    void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) throws RemoteException;

    void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) throws RemoteException;

    void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) throws RemoteException;

    void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException;

    void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) throws RemoteException;

    public static abstract class Stub extends Binder implements ITextClassifierService {
        public static final String DESCRIPTOR = "android.service.textclassifier.ITextClassifierService";
        static final int TRANSACTION_onClassifyText = 2;
        static final int TRANSACTION_onConnectedStateChanged = 10;
        static final int TRANSACTION_onCreateTextClassificationSession = 6;
        static final int TRANSACTION_onDestroyTextClassificationSession = 7;
        static final int TRANSACTION_onDetectLanguage = 8;
        static final int TRANSACTION_onGenerateLinks = 3;
        static final int TRANSACTION_onSelectionEvent = 4;
        static final int TRANSACTION_onSuggestConversationActions = 9;
        static final int TRANSACTION_onSuggestSelection = 1;
        static final int TRANSACTION_onTextClassifierEvent = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITextClassifierService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITextClassifierService)) {
                return (ITextClassifierService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onSuggestSelection";
                case 2:
                    return "onClassifyText";
                case 3:
                    return "onGenerateLinks";
                case 4:
                    return "onSelectionEvent";
                case 5:
                    return "onTextClassifierEvent";
                case 6:
                    return "onCreateTextClassificationSession";
                case 7:
                    return "onDestroyTextClassificationSession";
                case 8:
                    return "onDetectLanguage";
                case 9:
                    return "onSuggestConversationActions";
                case 10:
                    return "onConnectedStateChanged";
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
                    TextClassificationSessionId textClassificationSessionId = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    TextSelection.Request request = (TextSelection.Request) parcel.readTypedObject(TextSelection.Request.CREATOR);
                    ITextClassifierCallback iTextClassifierCallbackAsInterface = ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSuggestSelection(textClassificationSessionId, request, iTextClassifierCallbackAsInterface);
                    return true;
                case 2:
                    TextClassificationSessionId textClassificationSessionId2 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    TextClassification.Request request2 = (TextClassification.Request) parcel.readTypedObject(TextClassification.Request.CREATOR);
                    ITextClassifierCallback iTextClassifierCallbackAsInterface2 = ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onClassifyText(textClassificationSessionId2, request2, iTextClassifierCallbackAsInterface2);
                    return true;
                case 3:
                    TextClassificationSessionId textClassificationSessionId3 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    TextLinks.Request request3 = (TextLinks.Request) parcel.readTypedObject(TextLinks.Request.CREATOR);
                    ITextClassifierCallback iTextClassifierCallbackAsInterface3 = ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onGenerateLinks(textClassificationSessionId3, request3, iTextClassifierCallbackAsInterface3);
                    return true;
                case 4:
                    TextClassificationSessionId textClassificationSessionId4 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    SelectionEvent selectionEvent = (SelectionEvent) parcel.readTypedObject(SelectionEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSelectionEvent(textClassificationSessionId4, selectionEvent);
                    return true;
                case 5:
                    TextClassificationSessionId textClassificationSessionId5 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    TextClassifierEvent textClassifierEvent = (TextClassifierEvent) parcel.readTypedObject(TextClassifierEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTextClassifierEvent(textClassificationSessionId5, textClassifierEvent);
                    return true;
                case 6:
                    TextClassificationContext textClassificationContext = (TextClassificationContext) parcel.readTypedObject(TextClassificationContext.CREATOR);
                    TextClassificationSessionId textClassificationSessionId6 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId6);
                    return true;
                case 7:
                    TextClassificationSessionId textClassificationSessionId7 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDestroyTextClassificationSession(textClassificationSessionId7);
                    return true;
                case 8:
                    TextClassificationSessionId textClassificationSessionId8 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    TextLanguage.Request request4 = (TextLanguage.Request) parcel.readTypedObject(TextLanguage.Request.CREATOR);
                    ITextClassifierCallback iTextClassifierCallbackAsInterface4 = ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onDetectLanguage(textClassificationSessionId8, request4, iTextClassifierCallbackAsInterface4);
                    return true;
                case 9:
                    TextClassificationSessionId textClassificationSessionId9 = (TextClassificationSessionId) parcel.readTypedObject(TextClassificationSessionId.CREATOR);
                    ConversationActions.Request request5 = (ConversationActions.Request) parcel.readTypedObject(ConversationActions.Request.CREATOR);
                    ITextClassifierCallback iTextClassifierCallbackAsInterface5 = ITextClassifierCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    onSuggestConversationActions(textClassificationSessionId9, request5, iTextClassifierCallbackAsInterface5);
                    return true;
                case 10:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onConnectedStateChanged(i3);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITextClassifierService {
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

            @Override // android.service.textclassifier.ITextClassifierService
            public void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    parcelObtain.writeTypedObject(request, 0);
                    parcelObtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    parcelObtain.writeTypedObject(request, 0);
                    parcelObtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    parcelObtain.writeTypedObject(request, 0);
                    parcelObtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    parcelObtain.writeTypedObject(selectionEvent, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    parcelObtain.writeTypedObject(textClassifierEvent, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationContext, 0);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    parcelObtain.writeTypedObject(request, 0);
                    parcelObtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(textClassificationSessionId, 0);
                    parcelObtain.writeTypedObject(request, 0);
                    parcelObtain.writeStrongInterface(iTextClassifierCallback);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.textclassifier.ITextClassifierService
            public void onConnectedStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
