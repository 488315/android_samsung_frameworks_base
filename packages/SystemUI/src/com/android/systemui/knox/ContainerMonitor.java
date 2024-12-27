package com.android.systemui.knox;

import android.os.SystemProperties;
import android.os.UserManager;
import com.android.systemui.Dumpable;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ContainerMonitor implements Dumpable {
    public final boolean KNOX_DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public List mPersonas = null;
    public final UserManager mUserManager;

    public ContainerMonitor(KnoxStateMonitorImpl knoxStateMonitorImpl) {
        this.mUserManager = (UserManager) knoxStateMonitorImpl.mContext.getSystemService("user");
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
    }
}
