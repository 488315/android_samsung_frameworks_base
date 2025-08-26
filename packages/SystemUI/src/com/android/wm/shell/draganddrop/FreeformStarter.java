package com.android.wm.shell.draganddrop;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.ProfilerInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.window.WindowContainerToken;
import com.android.wm.shell.draganddrop.SplitDragPolicy;

/* loaded from: classes3.dex */
public class FreeformStarter implements SplitDragPolicy.Starter {
    public final Context mContext;

    public FreeformStarter(Context context) {
        this.mContext = context;
    }

    public static ActivityOptions overrideFreeformWindowingMode(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        ActivityOptions activityOptionsFromBundle = ActivityOptions.fromBundle(bundle);
        activityOptionsFromBundle.setLaunchWindowingMode(5);
        activityOptionsFromBundle.setPendingIntentBackgroundActivityStartMode(1);
        activityOptionsFromBundle.setLaunchedFromDnD(true);
        return activityOptionsFromBundle;
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startDragAndSplit(Intent intent, int i, int i2, Bundle bundle, int i3, int i4, int i5, boolean z) {
        try {
            ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, this.mContext.getPackageName(), (String) null, intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 0, (ProfilerInfo) null, overrideFreeformWindowingMode(bundle).toBundle(), i3);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startIntent(PendingIntent pendingIntent, int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken, int i3) throws PendingIntent.CanceledException {
        try {
            pendingIntent.send(this.mContext, 0, null, null, null, null, overrideFreeformWindowingMode(bundle).toBundle());
        } catch (PendingIntent.CanceledException e) {
            Slog.e("FreeformStarter", "Failed to launch activity", e);
        }
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle) {
        try {
            ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).startShortcut(str, str2, null, overrideFreeformWindowingMode(bundle).toBundle(), userHandle);
        } catch (ActivityNotFoundException e) {
            Slog.e("FreeformStarter", "Failed to launch shortcut", e);
        }
    }

    @Override // com.android.wm.shell.draganddrop.SplitDragPolicy.Starter
    public final void startTask(int i, int i2, Bundle bundle, WindowContainerToken windowContainerToken) {
        try {
            ActivityTaskManager.getService().startActivityFromRecents(i, overrideFreeformWindowingMode(bundle).toBundle());
        } catch (RemoteException e) {
            Slog.e("FreeformStarter", "Failed to launch task", e);
        }
    }
}
