package com.android.systemui.keyboard;

import com.android.systemui.statusbar.phone.SystemUIDialog;

public final class BluetoothDialogDelegate implements SystemUIDialog.Delegate {
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
