package com.android.internal.protolog;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.protolog.IProtoLogClient;

/* loaded from: classes4.dex */
public interface IProtoLogConfigurationService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.protolog.IProtoLogConfigurationService";

    public static class Default implements IProtoLogConfigurationService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.protolog.IProtoLogConfigurationService
        public void registerClient(IProtoLogClient iProtoLogClient, RegisterClientArgs registerClientArgs) throws RemoteException {
        }
    }

    void registerClient(IProtoLogClient iProtoLogClient, RegisterClientArgs registerClientArgs) throws RemoteException;

    public static abstract class Stub extends Binder implements IProtoLogConfigurationService {
        static final int TRANSACTION_registerClient = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IProtoLogConfigurationService.DESCRIPTOR);
        }

        public static IProtoLogConfigurationService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IProtoLogConfigurationService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IProtoLogConfigurationService)) {
                return (IProtoLogConfigurationService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "registerClient";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProtoLogConfigurationService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProtoLogConfigurationService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IProtoLogClient iProtoLogClientAsInterface = IProtoLogClient.Stub.asInterface(parcel.readStrongBinder());
                RegisterClientArgs registerClientArgs = (RegisterClientArgs) parcel.readTypedObject(RegisterClientArgs.CREATOR);
                parcel.enforceNoDataAvail();
                registerClient(iProtoLogClientAsInterface, registerClientArgs);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IProtoLogConfigurationService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProtoLogConfigurationService.DESCRIPTOR;
            }

            @Override // com.android.internal.protolog.IProtoLogConfigurationService
            public void registerClient(IProtoLogClient iProtoLogClient, RegisterClientArgs registerClientArgs) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IProtoLogConfigurationService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iProtoLogClient);
                    parcelObtain.writeTypedObject(registerClientArgs, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class RegisterClientArgs implements Parcelable {
        public static final Parcelable.Creator<RegisterClientArgs> CREATOR = new Parcelable.Creator<RegisterClientArgs>() { // from class: com.android.internal.protolog.IProtoLogConfigurationService.RegisterClientArgs.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RegisterClientArgs createFromParcel(Parcel parcel) {
                RegisterClientArgs registerClientArgs = new RegisterClientArgs();
                registerClientArgs.readFromParcel(parcel);
                return registerClientArgs;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public RegisterClientArgs[] newArray(int i) {
                return new RegisterClientArgs[i];
            }
        };
        public String[] groups;
        public boolean[] groupsDefaultLogcatStatus;
        public String viewerConfigFile;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStringArray(this.groups);
            parcel.writeBooleanArray(this.groupsDefaultLogcatStatus);
            parcel.writeString(this.viewerConfigFile);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.groups = parcel.createStringArray();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.groupsDefaultLogcatStatus = parcel.createBooleanArray();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.viewerConfigFile = parcel.readString();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }
}
