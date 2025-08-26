package com.android.systemui.keyboard;

import com.android.systemui.statusbar.phone.SystemUIDialog;

/* loaded from: classes2.dex */
public class BluetoothDialogDelegate implements SystemUIDialog.Delegate {
    public final SystemUIDialog.Factory mSystemUIDialogFactory;

    public BluetoothDialogDelegate(SystemUIDialog.Factory factory) {
        this.mSystemUIDialogFactory = factory;
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.mSystemUIDialogFactory;
        SystemUIDialog systemUIDialogCreate = factory.create(this, factory.mContext);
        systemUIDialogCreate.getWindow().setType(2008);
        SystemUIDialog.setShowForAllUsers(systemUIDialogCreate);
        return systemUIDialogCreate;
    }
}
