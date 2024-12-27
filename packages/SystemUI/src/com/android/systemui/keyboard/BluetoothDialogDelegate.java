package com.android.systemui.keyboard;

import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
