package com.android.systemui.knox;

import com.android.systemui.Dumpable;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
