package com.android.internal.graphics.fonts;

import android.Manifest;
import android.app.ActivityThread;
import android.graphics.fonts.FontUpdateRequest;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.text.FontConfig;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public interface IFontManager extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.graphics.fonts.IFontManager";

    public static class Default implements IFontManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.graphics.fonts.IFontManager
        public FontConfig getFontConfig() throws RemoteException {
            return null;
        }

        @Override // com.android.internal.graphics.fonts.IFontManager
        public int updateFontFamily(List<FontUpdateRequest> list, int i) throws RemoteException {
            return 0;
        }
    }

    FontConfig getFontConfig() throws RemoteException;

    int updateFontFamily(List<FontUpdateRequest> list, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IFontManager {
        static final int TRANSACTION_getFontConfig = 1;
        static final int TRANSACTION_updateFontFamily = 2;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IFontManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IFontManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFontManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFontManager)) {
                return (IFontManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getFontConfig";
            }
            if (i != 2) {
                return null;
            }
            return "updateFontFamily";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFontManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFontManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                FontConfig fontConfig = getFontConfig();
                parcel2.writeNoException();
                parcel2.writeTypedObject(fontConfig, 1);
            } else if (i == 2) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(FontUpdateRequest.CREATOR);
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int updateFontFamily = updateFontFamily(createTypedArrayList, readInt);
                parcel2.writeNoException();
                parcel2.writeInt(updateFontFamily);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFontManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFontManager.DESCRIPTOR;
            }

            @Override // com.android.internal.graphics.fonts.IFontManager
            public FontConfig getFontConfig() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFontManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FontConfig) obtain2.readTypedObject(FontConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.graphics.fonts.IFontManager
            public int updateFontFamily(List<FontUpdateRequest> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFontManager.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getFontConfig_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.UPDATE_FONTS, getCallingPid(), getCallingUid());
        }
    }
}
