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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IInlineSuggestionRenderService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IInlineSuggestionRenderService)) {
                return (IInlineSuggestionRenderService) queryLocalInterface;
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
                IInlineSuggestionUiCallback asInterface = IInlineSuggestionUiCallback.Stub.asInterface(parcel.readStrongBinder());
                InlinePresentation inlinePresentation = (InlinePresentation) parcel.readTypedObject(InlinePresentation.CREATOR);
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                IBinder readStrongBinder = parcel.readStrongBinder();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                renderSuggestion(asInterface, inlinePresentation, readInt, readInt2, readStrongBinder, readInt3, readInt4, readInt5);
            } else if (i == 2) {
                RemoteCallback remoteCallback = (RemoteCallback) parcel.readTypedObject(RemoteCallback.CREATOR);
                parcel.enforceNoDataAvail();
                getInlineSuggestionsRendererInfo(remoteCallback);
            } else if (i == 3) {
                int readInt6 = parcel.readInt();
                int readInt7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                destroySuggestionViews(readInt6, readInt7);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineSuggestionRenderService.DESCRIPTOR);
                    obtain.writeStrongInterface(iInlineSuggestionUiCallback);
                    obtain.writeTypedObject(inlinePresentation, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeInt(i5);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void getInlineSuggestionsRendererInfo(RemoteCallback remoteCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineSuggestionRenderService.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.autofill.IInlineSuggestionRenderService
            public void destroySuggestionViews(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IInlineSuggestionRenderService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
