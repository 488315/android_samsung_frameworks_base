package com.android.systemui.wallet.controller;

import android.os.IBinder;
import android.os.Parcel;
import java.util.List;

/* loaded from: classes3.dex */
public class IWalletCardsUpdatedListener$Stub$Proxy implements IWalletCardsUpdatedListener {
    public final IBinder mRemote;

    public IWalletCardsUpdatedListener$Stub$Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final void registerNewWalletCards(List list) {
        Parcel parcelObtain = Parcel.obtain(this.mRemote);
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.android.systemui.wallet.controller.IWalletCardsUpdatedListener");
            parcelObtain.writeTypedList(list, 0);
            this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
