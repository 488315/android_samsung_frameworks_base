package com.android.systemui.knox;

import com.android.systemui.Dumpable;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;
import java.io.PrintWriter;

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
