package com.android.systemui.knox;

import android.os.UserManager;
import com.android.systemui.Dumpable;
import java.io.PrintWriter;
import java.util.List;

/* loaded from: classes2.dex */
public class ContainerMonitor implements Dumpable {
    public List mPersonas = null;
    public final UserManager mUserManager;

    public ContainerMonitor(KnoxStateMonitorImpl knoxStateMonitorImpl) {
        this.mUserManager = (UserManager) knoxStateMonitorImpl.mContext.getSystemService("user");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
