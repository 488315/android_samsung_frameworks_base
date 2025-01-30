package com.android.wm.shell.bubbles;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.common.HandlerExecutor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class IBubbles$Stub extends Binder implements IInterface {
    public IBubbles$Stub() {
        attachInterface(this, "com.android.wm.shell.bubbles.IBubbles");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        IBubblesListener iBubblesListener$Stub$Proxy;
        final int i3 = 1;
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.bubbles.IBubbles");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.bubbles.IBubbles");
            return true;
        }
        switch (i) {
            case 2:
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    iBubblesListener$Stub$Proxy = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.android.wm.shell.bubbles.IBubblesListener");
                    iBubblesListener$Stub$Proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof IBubblesListener)) ? new IBubblesListener$Stub$Proxy(readStrongBinder) : (IBubblesListener) queryLocalInterface;
                }
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl = (BubbleController.IBubblesImpl) this;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda15(7, iBubblesImpl, iBubblesListener$Stub$Proxy));
                return true;
            case 3:
                IBinder readStrongBinder2 = parcel.readStrongBinder();
                if (readStrongBinder2 != null) {
                    IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.android.wm.shell.bubbles.IBubblesListener");
                    if (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IBubblesListener)) {
                        new IBubblesListener$Stub$Proxy(readStrongBinder2);
                    }
                }
                parcel.enforceNoDataAvail();
                final BubbleController.IBubblesImpl iBubblesImpl2 = (BubbleController.IBubblesImpl) this;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$IBubblesImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                iBubblesImpl2.mController.collapseStack();
                                break;
                            default:
                                iBubblesImpl2.mListener.unregister();
                                break;
                        }
                    }
                });
                return true;
            case 4:
                String readString = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                BubbleController.IBubblesImpl iBubblesImpl3 = (BubbleController.IBubblesImpl) this;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda17(iBubblesImpl3, readBoolean, readString));
                return true;
            case 5:
                parcel.readString();
                parcel.readInt();
                parcel.enforceNoDataAvail();
                return true;
            case 6:
                final BubbleController.IBubblesImpl iBubblesImpl4 = (BubbleController.IBubblesImpl) this;
                final int i4 = 0;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$IBubblesImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i4) {
                            case 0:
                                iBubblesImpl4.mController.collapseStack();
                                break;
                            default:
                                iBubblesImpl4.mListener.unregister();
                                break;
                        }
                    }
                });
                return true;
            case 7:
                parcel.readInt();
                parcel.enforceNoDataAvail();
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
