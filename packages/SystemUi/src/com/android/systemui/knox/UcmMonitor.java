package com.android.systemui.knox;

import com.android.systemui.Dumpable;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class UcmMonitor extends ICredentialManagerServiceSystemUICallback.Stub implements Dumpable {
    public String mUCMVendor = null;

    @Override // com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback
    public final void setUCMKeyguardVendor(String str) {
        this.mUCMVendor = str;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
