package com.android.systemui.notetask;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.UserHandle;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface INoteTaskBubblesService extends IInterface {
    boolean areBubblesAvailable();

    void showOrHideAppBubble(Intent intent, UserHandle userHandle, Icon icon);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract class Stub extends Binder implements INoteTaskBubblesService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Proxy implements INoteTaskBubblesService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.android.systemui.notetask.INoteTaskBubblesService
            public final boolean areBubblesAvailable() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.notetask.INoteTaskBubblesService");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.android.systemui.notetask.INoteTaskBubblesService
            public final void showOrHideAppBubble(Intent intent, UserHandle userHandle, Icon icon) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.android.systemui.notetask.INoteTaskBubblesService");
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(icon, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.android.systemui.notetask.INoteTaskBubblesService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.android.systemui.notetask.INoteTaskBubblesService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.android.systemui.notetask.INoteTaskBubblesService");
                return true;
            }
            if (i == 1) {
                boolean areBubblesAvailable = ((NoteTaskBubblesController$NoteTaskBubblesService$onBind$1) this).areBubblesAvailable();
                parcel2.writeNoException();
                parcel2.writeBoolean(areBubblesAvailable);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                Icon icon = (Icon) parcel.readTypedObject(Icon.CREATOR);
                parcel.enforceNoDataAvail();
                ((NoteTaskBubblesController$NoteTaskBubblesService$onBind$1) this).showOrHideAppBubble(intent, userHandle, icon);
                parcel2.writeNoException();
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
