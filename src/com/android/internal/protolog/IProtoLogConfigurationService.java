package com.android.internal.protolog;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.protolog.IProtoLogClient;

/* loaded from: classes3.dex */
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProtoLogConfigurationService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProtoLogConfigurationService)) {
                return (IProtoLogConfigurationService) queryLocalInterface;
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
                IProtoLogClient asInterface = IProtoLogClient.Stub.asInterface(parcel.readStrongBinder());
                RegisterClientArgs registerClientArgs = (RegisterClientArgs) parcel.readTypedObject(RegisterClientArgs.CREATOR);
                parcel.enforceNoDataAvail();
                registerClient(asInterface, registerClientArgs);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IProtoLogConfigurationService.DESCRIPTOR);
                    obtain.writeStrongInterface(iProtoLogClient);
                    obtain.writeTypedObject(registerClientArgs, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
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
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeStringArray(this.groups);
            parcel.writeBooleanArray(this.groupsDefaultLogcatStatus);
            parcel.writeString(this.viewerConfigFile);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.groups = parcel.createStringArray();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.groupsDefaultLogcatStatus = parcel.createBooleanArray();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.viewerConfigFile = parcel.readString();
                            if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }
}
