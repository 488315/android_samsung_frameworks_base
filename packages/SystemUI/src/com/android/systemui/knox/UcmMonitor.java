package com.android.systemui.knox;

import com.android.systemui.Dumpable;
import com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class UcmMonitor extends ICredentialManagerServiceSystemUICallback.Stub implements Dumpable {
    public String mUCMVendor = null;

    @Override // com.samsung.android.knox.ucm.core.ICredentialManagerServiceSystemUICallback
    public final void setUCMKeyguardVendor(String str) {
        this.mUCMVendor = str;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
