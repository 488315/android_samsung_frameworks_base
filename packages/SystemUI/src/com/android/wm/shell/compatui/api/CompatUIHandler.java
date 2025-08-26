package com.android.wm.shell.compatui.api;

import com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda0;
import com.android.wm.shell.compatui.impl.CompatUIRequests;

/* loaded from: classes3.dex */
public interface CompatUIHandler {
    void onCompatInfoChanged(CompatUIInfo compatUIInfo);

    void sendCompatUIRequest(CompatUIRequests compatUIRequests);

    void setCallback(ShellTaskOrganizer$$ExternalSyntheticLambda0 shellTaskOrganizer$$ExternalSyntheticLambda0);
}
