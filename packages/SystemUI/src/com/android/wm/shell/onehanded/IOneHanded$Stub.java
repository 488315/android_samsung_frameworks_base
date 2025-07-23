package com.android.wm.shell.onehanded;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.onehanded.OneHandedController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class IOneHanded$Stub extends Binder implements IInterface {
    public IOneHanded$Stub() {
        attachInterface(this, "com.android.wm.shell.onehanded.IOneHanded");
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i >= 1 && i <= 16777215) {
            parcel.enforceInterface("com.android.wm.shell.onehanded.IOneHanded");
        }
        if (i == 1598968902) {
            parcel2.writeString("com.android.wm.shell.onehanded.IOneHanded");
            return true;
        }
        if (i == 2) {
            final int i3 = 0;
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((OneHandedController.IOneHandedImpl) this).mController, "startOneHanded", new Consumer() { // from class: com.android.wm.shell.onehanded.OneHandedController$IOneHandedImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OneHandedController oneHandedController = (OneHandedController) obj;
                    switch (i3) {
                        case 0:
                            int i4 = OneHandedController.IOneHandedImpl.$r8$clinit;
                            oneHandedController.startOneHanded();
                            break;
                        default:
                            int i5 = OneHandedController.IOneHandedImpl.$r8$clinit;
                            oneHandedController.stopOneHanded();
                            break;
                    }
                }
            }, false);
            return true;
        }
        if (i != 3) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        final int i4 = 1;
        ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(((OneHandedController.IOneHandedImpl) this).mController, "stopOneHanded", new Consumer() { // from class: com.android.wm.shell.onehanded.OneHandedController$IOneHandedImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                OneHandedController oneHandedController = (OneHandedController) obj;
                switch (i4) {
                    case 0:
                        int i42 = OneHandedController.IOneHandedImpl.$r8$clinit;
                        oneHandedController.startOneHanded();
                        break;
                    default:
                        int i5 = OneHandedController.IOneHandedImpl.$r8$clinit;
                        oneHandedController.stopOneHanded();
                        break;
                }
            }
        }, false);
        return true;
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }
}
