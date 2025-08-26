package android.service.settings.preferences;

import android.Manifest;
import android.app.ActivityThread;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.service.settings.preferences.IGetValueCallback;
import android.service.settings.preferences.IMetadataCallback;
import android.service.settings.preferences.ISetValueCallback;

/* loaded from: classes3.dex */
public interface ISettingsPreferenceService extends IInterface {
    public static final String DESCRIPTOR = "android.service.settings.preferences.ISettingsPreferenceService";

    public static class Default implements ISettingsPreferenceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.settings.preferences.ISettingsPreferenceService
        public void getAllPreferenceMetadata(MetadataRequest metadataRequest, IMetadataCallback iMetadataCallback) throws RemoteException {
        }

        @Override // android.service.settings.preferences.ISettingsPreferenceService
        public void getPreferenceValue(GetValueRequest getValueRequest, IGetValueCallback iGetValueCallback) throws RemoteException {
        }

        @Override // android.service.settings.preferences.ISettingsPreferenceService
        public void setPreferenceValue(SetValueRequest setValueRequest, ISetValueCallback iSetValueCallback) throws RemoteException {
        }
    }

    void getAllPreferenceMetadata(MetadataRequest metadataRequest, IMetadataCallback iMetadataCallback) throws RemoteException;

    void getPreferenceValue(GetValueRequest getValueRequest, IGetValueCallback iGetValueCallback) throws RemoteException;

    void setPreferenceValue(SetValueRequest setValueRequest, ISetValueCallback iSetValueCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements ISettingsPreferenceService {
        static final String[] PERMISSIONS_setPreferenceValue = {Manifest.permission.READ_SYSTEM_PREFERENCES, Manifest.permission.WRITE_SYSTEM_PREFERENCES};
        static final int TRANSACTION_getAllPreferenceMetadata = 2;
        static final int TRANSACTION_getPreferenceValue = 3;
        static final int TRANSACTION_setPreferenceValue = 4;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, ISettingsPreferenceService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ISettingsPreferenceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISettingsPreferenceService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISettingsPreferenceService)) {
                return (ISettingsPreferenceService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 2) {
                return "getAllPreferenceMetadata";
            }
            if (i == 3) {
                return "getPreferenceValue";
            }
            if (i != 4) {
                return null;
            }
            return "setPreferenceValue";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISettingsPreferenceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISettingsPreferenceService.DESCRIPTOR);
                return true;
            }
            if (i == 2) {
                MetadataRequest metadataRequest = (MetadataRequest) parcel.readTypedObject(MetadataRequest.CREATOR);
                IMetadataCallback iMetadataCallbackAsInterface = IMetadataCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getAllPreferenceMetadata(metadataRequest, iMetadataCallbackAsInterface);
            } else if (i == 3) {
                GetValueRequest getValueRequest = (GetValueRequest) parcel.readTypedObject(GetValueRequest.CREATOR);
                IGetValueCallback iGetValueCallbackAsInterface = IGetValueCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getPreferenceValue(getValueRequest, iGetValueCallbackAsInterface);
            } else if (i == 4) {
                SetValueRequest setValueRequest = (SetValueRequest) parcel.readTypedObject(SetValueRequest.CREATOR);
                ISetValueCallback iSetValueCallbackAsInterface = ISetValueCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setPreferenceValue(setValueRequest, iSetValueCallbackAsInterface);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISettingsPreferenceService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISettingsPreferenceService.DESCRIPTOR;
            }

            @Override // android.service.settings.preferences.ISettingsPreferenceService
            public void getAllPreferenceMetadata(MetadataRequest metadataRequest, IMetadataCallback iMetadataCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISettingsPreferenceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(metadataRequest, 0);
                    parcelObtain.writeStrongInterface(iMetadataCallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.settings.preferences.ISettingsPreferenceService
            public void getPreferenceValue(GetValueRequest getValueRequest, IGetValueCallback iGetValueCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISettingsPreferenceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(getValueRequest, 0);
                    parcelObtain.writeStrongInterface(iGetValueCallback);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.settings.preferences.ISettingsPreferenceService
            public void setPreferenceValue(SetValueRequest setValueRequest, ISetValueCallback iSetValueCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISettingsPreferenceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(setValueRequest, 0);
                    parcelObtain.writeStrongInterface(iSetValueCallback);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        protected void getAllPreferenceMetadata_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_SYSTEM_PREFERENCES, getCallingPid(), getCallingUid());
        }

        protected void getPreferenceValue_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.READ_SYSTEM_PREFERENCES, getCallingPid(), getCallingUid());
        }

        protected void setPreferenceValue_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_setPreferenceValue, getCallingPid(), getCallingUid());
        }
    }
}
