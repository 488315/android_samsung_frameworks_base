package com.android.systemui.wallet.controller;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class IWalletContextualLocationsService$Stub extends Binder implements IInterface {
    public IWalletContextualLocationsService$Stub() {
        attachInterface(this, "com.android.systemui.wallet.controller.IWalletContextualLocationsService");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        IWalletCardsUpdatedListener iWalletCardsUpdatedListener;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.systemui.wallet.controller.IWalletContextualLocationsService");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.systemui.wallet.controller.IWalletContextualLocationsService");
            return true;
        }
        if (i == 1) {
            final IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder == null) {
                iWalletCardsUpdatedListener = null;
            } else {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.systemui.wallet.controller.IWalletCardsUpdatedListener");
                iWalletCardsUpdatedListener = (queryLocalInterface == null || !(queryLocalInterface instanceof IWalletCardsUpdatedListener)) ? new IWalletCardsUpdatedListener(readStrongBinder) { // from class: com.android.systemui.wallet.controller.IWalletCardsUpdatedListener$Stub$Proxy
                    public final IBinder mRemote;

                    {
                        this.mRemote = readStrongBinder;
                    }

                    @Override // android.os.IInterface
                    public final IBinder asBinder() {
                        return this.mRemote;
                    }
                } : (IWalletCardsUpdatedListener) queryLocalInterface;
            }
            parcel.enforceNoDataAvail();
            ((WalletContextualLocationsService$binder$1) this).this$0.addWalletCardsUpdatedListenerInternal(iWalletCardsUpdatedListener);
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
