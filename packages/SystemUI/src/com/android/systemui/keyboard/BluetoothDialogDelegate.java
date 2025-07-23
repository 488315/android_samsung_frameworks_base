package com.android.systemui.keyboard;

import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class BluetoothDialogDelegate implements SystemUIDialog.Delegate {
    public final SystemUIDialog.Factory mSystemUIDialogFactory;

    public BluetoothDialogDelegate(SystemUIDialog.Factory factory) {
        this.mSystemUIDialogFactory = factory;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.mSystemUIDialogFactory;
        SystemUIDialog create = factory.create(this, factory.mContext);
        create.getWindow().setType(2008);
        SystemUIDialog.setShowForAllUsers(create);
        return create;
    }
}
