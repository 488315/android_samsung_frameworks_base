package com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider;

/* loaded from: classes4.dex */
public interface ISimCardManagerService extends IInterface {
    int GetCurrentVoiceCall();

    boolean isDefaultDataSlotAllowed(int i);

    boolean isDualSimReadyAndTurnedOn();

    boolean registerSimCardManagerServiceCallback(SimCardManagerServiceProvider.ServiceBindHelper.AnonymousClass2 anonymousClass2);

    boolean unregisterSimCardManagerServiceCallback(SimCardManagerServiceProvider.ServiceBindHelper.AnonymousClass2 anonymousClass2);

    public abstract class Stub extends Binder implements ISimCardManagerService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements ISimCardManagerService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService
            public final int GetCurrentVoiceCall() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService
            public final boolean isDefaultDataSlotAllowed(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService
            public final boolean registerSimCardManagerServiceCallback(SimCardManagerServiceProvider.ServiceBindHelper.AnonymousClass2 anonymousClass2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    parcelObtain.writeStrongBinder(anonymousClass2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService
            public final boolean unregisterSimCardManagerServiceCallback(SimCardManagerServiceProvider.ServiceBindHelper.AnonymousClass2 anonymousClass2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                    if (anonymousClass2 == null) {
                        anonymousClass2 = null;
                    }
                    parcelObtain.writeStrongBinder(anonymousClass2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
                boolean zIsDualSimReadyAndTurnedOn = isDualSimReadyAndTurnedOn();
                parcel2.writeNoException();
                parcel2.writeInt(zIsDualSimReadyAndTurnedOn ? 1 : 0);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                boolean zIsDefaultDataSlotAllowed = isDefaultDataSlotAllowed(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zIsDefaultDataSlotAllowed ? 1 : 0);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                int iGetCurrentVoiceCall = GetCurrentVoiceCall();
                parcel2.writeNoException();
                parcel2.writeInt(iGetCurrentVoiceCall);
                return true;
            }
            IInterface proxy = null;
            if (i == 4) {
                parcel.enforceInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
                    proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISimCardManagerServiceCallback)) ? new ISimCardManagerServiceCallback.Stub.Proxy(strongBinder) : (ISimCardManagerServiceCallback) iInterfaceQueryLocalInterface;
                }
                boolean zRegisterSimCardManagerServiceCallback = registerSimCardManagerServiceCallback((SimCardManagerServiceProvider.ServiceBindHelper.AnonymousClass2) proxy);
                parcel2.writeNoException();
                parcel2.writeInt(zRegisterSimCardManagerServiceCallback ? 1 : 0);
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
            IBinder strongBinder2 = parcel.readStrongBinder();
            if (strongBinder2 != null) {
                IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback");
                proxy = (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof ISimCardManagerServiceCallback)) ? new ISimCardManagerServiceCallback.Stub.Proxy(strongBinder2) : (ISimCardManagerServiceCallback) iInterfaceQueryLocalInterface2;
            }
            boolean zUnregisterSimCardManagerServiceCallback = unregisterSimCardManagerServiceCallback((SimCardManagerServiceProvider.ServiceBindHelper.AnonymousClass2) proxy);
            parcel2.writeNoException();
            parcel2.writeInt(zUnregisterSimCardManagerServiceCallback ? 1 : 0);
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
