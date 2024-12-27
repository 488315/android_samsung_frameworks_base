package com.android.server.am;

import android.app.IInstrumentationWatcher;
import android.app.IUiAutomationConnection;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ActiveInstrumentation {
    public Bundle mArguments;
    public ComponentName mClass;
    public Bundle mCurResults;
    public boolean mFinished;
    public boolean mHasBackgroundActivityStartsPermission;
    public boolean mHasBackgroundForegroundServiceStartsPermission;
    public boolean mIsSdkInSandbox;
    public boolean mNoRestart;
    public String mProfileFile;
    public final ArrayList mRunningProcesses = new ArrayList();
    public final ActivityManagerService mService;
    public int mSourceUid;
    public ApplicationInfo mTargetInfo;
    public String[] mTargetProcesses;
    public IUiAutomationConnection mUiAutomationConnection;
    public IInstrumentationWatcher mWatcher;

    public ActiveInstrumentation(ActivityManagerService activityManagerService) {
        this.mService = activityManagerService;
    }

    public final String toString() {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "ActiveInstrumentation{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        m.append(this.mClass.toShortString());
        if (this.mFinished) {
            m.append(" FINISHED");
        }
        m.append(" ");
        m.append(this.mRunningProcesses.size());
        m.append(" procs}");
        return m.toString();
    }
}
