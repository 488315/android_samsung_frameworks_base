package com.android.wm.shell.draganddrop;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.draganddrop.DragAndDropController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IDragAndDrop$Stub extends Binder implements IInterface {
    public IDragAndDrop$Stub() {
        attachInterface(this, "com.android.wm.shell.draganddrop.IDragAndDrop");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.draganddrop.IDragAndDrop");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.draganddrop.IDragAndDrop");
            return true;
        }
        if (i != 2) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        final boolean[] zArr = new boolean[1];
        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((DragAndDropController.IDragAndDropImpl) this).mController, "isReadyToHandleDrag", new Consumer() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$IDragAndDropImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z = true;
                boolean[] zArr2 = zArr;
                DragAndDropController dragAndDropController = (DragAndDropController) obj;
                int i3 = DragAndDropController.IDragAndDropImpl.$r8$clinit;
                int i4 = 0;
                while (true) {
                    if (i4 >= dragAndDropController.mDisplayDropTargets.size()) {
                        z = false;
                        break;
                    } else if (((DragAndDropController.PerDisplay) dragAndDropController.mDisplayDropTargets.valueAt(i4)).hasDrawn) {
                        break;
                    } else {
                        i4++;
                    }
                }
                zArr2[0] = z;
            }
        }, true);
        boolean z = zArr[0];
        parcel2.writeNoException();
        parcel2.writeBoolean(z);
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
