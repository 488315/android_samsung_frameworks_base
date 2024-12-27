package com.android.systemui.wallet.controller;

import android.os.IBinder;
import android.os.Parcel;
import java.util.List;

public final class IWalletCardsUpdatedListener$Stub$Proxy implements IWalletCardsUpdatedListener {
    public final IBinder mRemote;

    public IWalletCardsUpdatedListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void registerNewWalletCards(List list) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken("com.android.systemui.wallet.controller.IWalletCardsUpdatedListener");
            obtain.writeTypedList(list, 0);
            this.mRemote.transact(1, obtain, obtain2, 0);
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
