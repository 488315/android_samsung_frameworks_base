package com.samsung.android.desktopsystemui.sharedlib.system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ContextWrapper {
    Context mBase;

    public ContextWrapper(Context context) {
        this.mBase = context;
    }

    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        this.mBase.startActivityAsUser(intent, bundle, userHandle);
    }
}
