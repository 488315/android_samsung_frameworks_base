package com.android.systemui.edgelighting.interfaces;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.systemui.edgelighting.SystemUIConditionListenerService;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ISystemUIConditionListener extends IInterface {
    boolean isAlertingHeadsUp(String str);

    boolean isInterrupted(String str);

    boolean isNeedToSanitize(int i, int i2, String str);

    boolean isPanelsEnabled();

    void requestDozeStateSubScreen(boolean z);

    void sendClickEvent(String str);

    void setInterruption(String str);

    void turnToHeadsUp(String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class Stub extends Binder implements ISystemUIConditionListener {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            public final boolean isAlertingHeadsUp(String str) {
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
            public final void requestDozeStateSubScreen(boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.edgelighting.interfaces.ISystemUIConditionListener");
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, obtain2, 0);
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
                    boolean isInterrupted = ((SystemUIConditionListenerService.BinderC13051) this).isInterrupted(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInterrupted);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.BinderC13051) this).setInterruption(readString2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNeedToSanitize = ((SystemUIConditionListenerService.BinderC13051) this).isNeedToSanitize(readInt, readInt2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNeedToSanitize);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.BinderC13051) this).turnToHeadsUp(readString4);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAlertingHeadsUp = ((SystemUIConditionListenerService.BinderC13051) this).isAlertingHeadsUp(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlertingHeadsUp);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.BinderC13051) this).sendClickEvent(readString6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean isPanelsEnabled = ((SystemUIConditionListenerService.BinderC13051) this).isPanelsEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPanelsEnabled);
                    return true;
                case 8:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ((SystemUIConditionListenerService.BinderC13051) this).requestDozeStateSubScreen(readBoolean);
                    parcel2.writeNoException();
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
