package com.android.wm.shell.pip;

import android.app.RemoteAction;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PipController;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipParamsChangedForwarder {
    public final List mPipParamsChangedListeners = new ArrayList();

    public final void notifyActionsChanged(List list, RemoteAction remoteAction) {
        ArrayList arrayList = (ArrayList) this.mPipParamsChangedListeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            PhonePipMenuController phonePipMenuController = PipController.this.mMenuController;
            phonePipMenuController.mAppActions = list;
            phonePipMenuController.mCloseAction = remoteAction;
            phonePipMenuController.updateMenuActions$1();
        }
    }
}
