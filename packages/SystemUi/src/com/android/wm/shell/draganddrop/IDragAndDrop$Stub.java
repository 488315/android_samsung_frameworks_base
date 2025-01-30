package com.android.wm.shell.draganddrop;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.common.ExecutorUtils;
import com.android.wm.shell.draganddrop.DragAndDropController;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        ExecutorUtils.executeRemoteCallWithTaskPermission(((DragAndDropController.IDragAndDropImpl) this).mController, "isReadyToHandleDrag", new Consumer() { // from class: com.android.wm.shell.draganddrop.DragAndDropController$IDragAndDropImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                boolean z;
                boolean[] zArr2 = zArr;
                DragAndDropController dragAndDropController = (DragAndDropController) obj;
                int i3 = 0;
                while (true) {
                    if (i3 >= dragAndDropController.mDisplayDropTargets.size()) {
                        z = false;
                        break;
                    } else {
                        if (((DragAndDropController.PerDisplay) dragAndDropController.mDisplayDropTargets.valueAt(i3)).mHasDrawn) {
                            z = true;
                            break;
                        }
                        i3++;
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
