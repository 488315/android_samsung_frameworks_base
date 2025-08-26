package com.samsung.android.settingslib.bluetooth.scsp;

import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final /* synthetic */ class ScspUtils$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Collection f$0;

    public /* synthetic */ ScspUtils$$ExternalSyntheticLambda1(Collection collection, int i) {
        this.$r8$classId = i;
        this.f$0 = collection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Collection collection = this.f$0;
                String str = ScspUtils.FILE_PATH_ROOT;
                synchronized (collection) {
                    try {
                        Iterator it = collection.iterator();
                        while (it.hasNext()) {
                            ((SBluetoothControllerImpl) it.next()).getClass();
                        }
                    } finally {
                    }
                }
                return;
            case 1:
                Collection collection2 = this.f$0;
                String str2 = ScspUtils.FILE_PATH_ROOT;
                synchronized (collection2) {
                    try {
                        Iterator it2 = collection2.iterator();
                        while (it2.hasNext()) {
                            ((SBluetoothControllerImpl) it2.next()).getClass();
                        }
                    } finally {
                    }
                }
                return;
            default:
                Collection collection3 = this.f$0;
                String str3 = ScspUtils.FILE_PATH_ROOT;
                synchronized (collection3) {
                    try {
                        Iterator it3 = collection3.iterator();
                        while (it3.hasNext()) {
                            ((SBluetoothControllerImpl) it3.next()).getClass();
                        }
                    } finally {
                    }
                }
                return;
        }
    }
}
