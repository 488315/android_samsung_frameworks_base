package com.android.wm.shell.draganddrop;

import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class BaseAppResult implements AppResult {
    public final Map mBlockDropTargetClassNameMap = Map.ofEntries(Map.entry("com.google.android.apps.bard.shellapp.BardEntryPointActivity", "com.google.android.apps.search.assistant.surfaces.voice.robin.main.MainActivity"));
    public final String mContentType;
    public final ExecutableAppHolder.MultiInstanceAllowList mMultiInstanceAllowList;
    public final ExecutableAppHolder.MultiInstanceBlockList mMultiInstanceBlockList;

    public BaseAppResult(ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, String str) {
        this.mMultiInstanceBlockList = multiInstanceBlockList;
        this.mMultiInstanceAllowList = multiInstanceAllowList;
        this.mContentType = str;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final String getContentType() {
        return this.mContentType;
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x0293, code lost:
    
        return true;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0081  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isVisibleSingleInstance(java.util.List r12, android.content.pm.ActivityInfo r13, boolean r14) {
        /*
            Method dump skipped, instructions count: 661
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.draganddrop.BaseAppResult.isVisibleSingleInstance(java.util.List, android.content.pm.ActivityInfo, boolean):boolean");
    }
}
