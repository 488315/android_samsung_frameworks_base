package com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service.ISimCardManagerServiceCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ISimCardManagerService extends IInterface {
    int GetCurrentVoiceCall();

    boolean isDefaultDataSlotAllowed(int i);

    boolean isDualSimReadyAndTurnedOn();

    boolean registerSimCardManagerServiceCallback(ISimCardManagerServiceCallback iSimCardManagerServiceCallback);

    boolean unregisterSimCardManagerServiceCallback(ISimCardManagerServiceCallback iSimCardManagerServiceCallback);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements ISimCardManagerService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Proxy implements ISimCardManagerService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service.ISimCardManagerService
            public final int GetCurrentVoiceCall() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service.ISimCardManagerService
            public final boolean isDefaultDataSlotAllowed(int i) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service.ISimCardManagerService
            public final boolean registerSimCardManagerServiceCallback(ISimCardManagerServiceCallback iSimCardManagerServiceCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    obtain.writeStrongBinder(iSimCardManagerServiceCallback != null ? iSimCardManagerServiceCallback.asBinder() : null);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.p044ui.simcardmanager.service.ISimCardManagerService
            public final boolean unregisterSimCardManagerServiceCallback(ISimCardManagerServiceCallback iSimCardManagerServiceCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    obtain.writeStrongBinder(iSimCardManagerServiceCallback != null ? iSimCardManagerServiceCallback.asBinder() : null);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                boolean isDualSimReadyAndTurnedOn = isDualSimReadyAndTurnedOn();
                parcel2.writeNoException();
                parcel2.writeInt(isDualSimReadyAndTurnedOn ? 1 : 0);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                boolean isDefaultDataSlotAllowed = isDefaultDataSlotAllowed(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(isDefaultDataSlotAllowed ? 1 : 0);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                int GetCurrentVoiceCall = GetCurrentVoiceCall();
                parcel2.writeNoException();
                parcel2.writeInt(GetCurrentVoiceCall);
                return true;
            }
            ISimCardManagerServiceCallback iSimCardManagerServiceCallback = null;
            if (i == 4) {
                parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
                    iSimCardManagerServiceCallback = (queryLocalInterface == null || !(queryLocalInterface instanceof ISimCardManagerServiceCallback)) ? new ISimCardManagerServiceCallback.Stub.Proxy(readStrongBinder) : (ISimCardManagerServiceCallback) queryLocalInterface;
                }
                boolean registerSimCardManagerServiceCallback = registerSimCardManagerServiceCallback(iSimCardManagerServiceCallback);
                parcel2.writeNoException();
                parcel2.writeInt(registerSimCardManagerServiceCallback ? 1 : 0);
                return true;
            }
            if (i != 5) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                return true;
            }
            parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
            IBinder readStrongBinder2 = parcel.readStrongBinder();
            if (readStrongBinder2 != null) {
                IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
                iSimCardManagerServiceCallback = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof ISimCardManagerServiceCallback)) ? new ISimCardManagerServiceCallback.Stub.Proxy(readStrongBinder2) : (ISimCardManagerServiceCallback) queryLocalInterface2;
            }
            boolean unregisterSimCardManagerServiceCallback = unregisterSimCardManagerServiceCallback(iSimCardManagerServiceCallback);
            parcel2.writeNoException();
            parcel2.writeInt(unregisterSimCardManagerServiceCallback ? 1 : 0);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
