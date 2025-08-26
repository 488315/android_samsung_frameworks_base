package com.android.systemui.edgelighting.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.edgelighting.SystemUIConditionListenerService;

/* loaded from: classes2.dex */
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

    public abstract class Stub extends Binder implements ISystemUIConditionListener {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements ISystemUIConditionListener {
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
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isInterrupted(String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isNeedToSanitize(int i, int i2, String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isOngoingAcitivty(String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isPanelsEnabled() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isRowPinned(String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isSensitiveStateActive() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean isSupportAppLock() {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void requestDozeStateSubScreen(boolean z) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void sendClickEvent(String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void setInterruption(String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final boolean shouldHideNotiForAppLockByPackage(String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener
            public final void turnToHeadsUp(String str) {
                Parcel parcelObtain = Parcel.obtain(this.mRemote);
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsInterrupted = ((SystemUIConditionListenerService.AnonymousClass1) this).isInterrupted(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInterrupted);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).setInterruption(string2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNeedToSanitize = ((SystemUIConditionListenerService.AnonymousClass1) this).isNeedToSanitize(i3, i4, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNeedToSanitize);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).turnToHeadsUp(string4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsRowPinned = ((SystemUIConditionListenerService.AnonymousClass1) this).isRowPinned(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRowPinned);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).sendClickEvent(string6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean zIsPanelsEnabled = ((SystemUIConditionListenerService.AnonymousClass1) this).isPanelsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPanelsEnabled);
                    return true;
                case 8:
                    boolean zIsSensitiveStateActive = ((SystemUIConditionListenerService.AnonymousClass1) this).isSensitiveStateActive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSensitiveStateActive);
                    return true;
                case 9:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsOngoingAcitivty = ((SystemUIConditionListenerService.AnonymousClass1) this).isOngoingAcitivty(string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOngoingAcitivty);
                    return true;
                case 10:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.AnonymousClass1) this).requestDozeStateSubScreen(z);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    boolean zIsSupportAppLock = ((SystemUIConditionListenerService.AnonymousClass1) this).isSupportAppLock();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSupportAppLock);
                    return true;
                case 12:
                    boolean zIsAppLockEnabled = ((SystemUIConditionListenerService.AnonymousClass1) this).isAppLockEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAppLockEnabled);
                    return true;
                case 13:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zShouldHideNotiForAppLockByPackage = ((SystemUIConditionListenerService.AnonymousClass1) this).shouldHideNotiForAppLockByPackage(string8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldHideNotiForAppLockByPackage);
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
