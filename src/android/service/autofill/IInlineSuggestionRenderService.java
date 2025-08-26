package android.service.autofill;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.autofill.IInlineSuggestionUiCallback;

/* loaded from: classes3.dex */
public interface IInlineSuggestionRenderService extends IInterface {
    public static final String DESCRIPTOR = "android.service.autofill.IInlineSuggestionRenderService";

    public static class Default implements IInlineSuggestionRenderService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void destroySuggestionViews(int i, int i2) throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void getInlineSuggestionsRendererInfo(RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override // android.service.autofill.IInlineSuggestionRenderService
        public void renderSuggestion(IInlineSuggestionUiCallback iInlineSuggestionUiCallback, InlinePresentation inlinePresentation, int i, int i2, IBinder iBinder, int i3, int i4, int i5) throws RemoteException {
        }
    }

    void destroySuggestionViews(int i, int i2) throws RemoteException;

    void getInlineSuggestionsRendererInfo(RemoteCallback remoteCallback) throws RemoteException;

    void renderSuggestion(IInlineSuggestionUiCallback iInlineSuggestionUiCallback, InlinePresentation inlinePresentation, int i, int i2, IBinder iBinder, int i3, int i4, int i5) throws RemoteException;

    public static abstract class Stub extends Binder implements IInlineSuggestionRenderService {
        static final int TRANSACTION_destroySuggestionViews = 3;
        static final int TRANSACTION_getInlineSuggestionsRendererInfo = 2;
        static final int TRANSACTION_renderSuggestion = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IInlineSuggestionRenderService.DESCRIPTOR);
        }

        public static IInlineSuggestionRenderService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInlineSuggestionRenderService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInlineSuggestionRenderService)) {
                return (IInlineSuggestionRenderService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "renderSuggestion";
            }
            if (i == 2) {
                return "getInlineSuggestionsRendererInfo";
            }
            if (i != 3) {
                return null;
            }
            return "destroySuggestionViews";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInlineSuggestionRenderService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInlineSuggestionRenderService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IInlineSuggestionUiCallback iInlineSuggestionUiCallbackAsInterface = IInlineSuggestionUiCallback.Stub.asInterface(parcel.readStrongBinder());
                InlinePresentation inlinePresentation = (InlinePresentation) parcel.readTypedObject(InlinePresentation.CREATOR);
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                IBinder strongBinder = parcel.readStrongBinder();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                renderSuggestion(iInlineSuggestionUiCallbackAsInterface, inlinePresentation, i3, i4, strongBinder, i5, i6, i7);
            } else if (i == 2) {
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                getInlineSuggestionsRendererInfo(remoteCallback);
            } else if (i == 3) {
                int i8 = parcel.readInt();
                int i9 = parcel.readInt();
                parcel.enforceNoDataAvail();
                destroySuggestionViews(i8, i9);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInlineSuggestionRenderService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInlineSuggestionRenderService.DESCRIPTOR;
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void renderSuggestion(IInlineSuggestionUiCallback iInlineSuggestionUiCallback, InlinePresentation inlinePresentation, int i, int i2, IBinder iBinder, int i3, int i4, int i5) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionRenderService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iInlineSuggestionUiCallback);
                    parcelObtain.writeTypedObject(inlinePresentation, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeInt(i5);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void getInlineSuggestionsRendererInfo(RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionRenderService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void destroySuggestionViews(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInlineSuggestionRenderService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
