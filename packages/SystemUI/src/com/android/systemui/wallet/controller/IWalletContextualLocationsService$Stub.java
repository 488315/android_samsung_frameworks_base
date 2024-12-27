package com.android.systemui.wallet.controller;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class IWalletContextualLocationsService$Stub extends Binder implements IInterface {
    public IWalletContextualLocationsService$Stub() {
        attachInterface(this, "com.android.systemui.wallet.controller.IWalletContextualLocationsService");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        IWalletCardsUpdatedListener iWalletCardsUpdatedListener$Stub$Proxy;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.wallet.controller.IWalletContextualLocationsService");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.systemui.wallet.controller.IWalletContextualLocationsService");
            return true;
        }
        if (i == 1) {
            IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder == null) {
                iWalletCardsUpdatedListener$Stub$Proxy = null;
            } else {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.wallet.controller.IWalletCardsUpdatedListener");
                iWalletCardsUpdatedListener$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IWalletCardsUpdatedListener)) ? new IWalletCardsUpdatedListener$Stub$Proxy(readStrongBinder) : (IWalletCardsUpdatedListener) queryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            ((WalletContextualLocationsService$binder$1) this).this$0.addWalletCardsUpdatedListenerInternal(iWalletCardsUpdatedListener$Stub$Proxy);
            parcel2.writeNoException();
        } else {
            if (i != 2) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            ArrayList<String> createStringArrayList = parcel.createStringArrayList();
            parcel.enforceNoDataAvail();
            ((WalletContextualLocationsService$binder$1) this).this$0.onWalletContextualLocationsStateUpdatedInternal(createStringArrayList);
            parcel2.writeNoException();
        }
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
