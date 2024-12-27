package com.android.systemui.edgelighting.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.edgelighting.SystemUIConditionListenerService;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface ISystemUIConditionListener extends IInterface {
    boolean isAppLockEnabled();

    boolean isInterrupted(String str);

    boolean isNeedToSanitize(int i, int i2, String str);

    boolean isOngoingAcitivty(String str);

    boolean isPanelsEnabled();

    boolean isRowPinned(String str);

    boolean isSensitiveStateActive();

    boolean isSupportAppLock();

    void requestDozeStateSubScreen(boolean z);

    void sendClickEvent(String str);

    void setInterruption(String str);

    boolean shouldHideNotiForAppLockByPackage(String str);

    void turnToHeadsUp(String str);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Stub extends Binder implements ISystemUIConditionListener {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Proxy implements ISystemUIConditionListener {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isAppLockEnabled() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isInterrupted(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isNeedToSanitize(int i, int i2, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isOngoingAcitivty(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isPanelsEnabled() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isRowPinned(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isSensitiveStateActive() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isSupportAppLock() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void requestDozeStateSubScreen(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void sendClickEvent(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void setInterruption(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean shouldHideNotiForAppLockByPackage(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void turnToHeadsUp(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isInterrupted = ((SystemUIConditionListenerService.AnonymousClass1) this).isInterrupted(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInterrupted);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).setInterruption(readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNeedToSanitize = ((SystemUIConditionListenerService.AnonymousClass1) this).isNeedToSanitize(readInt, readInt2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNeedToSanitize);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).turnToHeadsUp(readString4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isRowPinned = ((SystemUIConditionListenerService.AnonymousClass1) this).isRowPinned(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRowPinned);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).sendClickEvent(readString6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean isPanelsEnabled = ((SystemUIConditionListenerService.AnonymousClass1) this).isPanelsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPanelsEnabled);
                    return true;
                case 8:
                    boolean isSensitiveStateActive = ((SystemUIConditionListenerService.AnonymousClass1) this).isSensitiveStateActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSensitiveStateActive);
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isOngoingAcitivty = ((SystemUIConditionListenerService.AnonymousClass1) this).isOngoingAcitivty(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOngoingAcitivty);
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).requestDozeStateSubScreen(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean isSupportAppLock = ((SystemUIConditionListenerService.AnonymousClass1) this).isSupportAppLock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSupportAppLock);
                    return true;
                case 12:
                    boolean isAppLockEnabled = ((SystemUIConditionListenerService.AnonymousClass1) this).isAppLockEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppLockEnabled);
                    return true;
                case 13:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean shouldHideNotiForAppLockByPackage = ((SystemUIConditionListenerService.AnonymousClass1) this).shouldHideNotiForAppLockByPackage(readString8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldHideNotiForAppLockByPackage);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
