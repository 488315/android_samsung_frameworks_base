package com.android.systemui.knox;

import android.os.SystemProperties;
import android.os.UserManager;
import com.android.systemui.Dumpable;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
