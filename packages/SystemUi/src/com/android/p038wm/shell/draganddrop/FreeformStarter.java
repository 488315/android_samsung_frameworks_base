package com.android.p038wm.shell.draganddrop;

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
import com.android.p038wm.shell.draganddrop.DragAndDropPolicy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformStarter implements DragAndDropPolicy.Starter {
    public final Context mContext;

    public FreeformStarter(Context context) {
        this.mContext = context;
    }

    public static ActivityOptions overrideFreeformWindowingMode(Bundle bundle) {
        if (bundle == null) {
            bundle = new Bundle();
        }
        ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
        fromBundle.setLaunchWindowingMode(5);
        fromBundle.setPendingIntentBackgroundActivityStartMode(1);
        fromBundle.setLaunchedFromDnD(true);
        return fromBundle;
    }

    @Override // com.android.wm.shell.draganddrop.DragAndDropPolicy.Starter
    public final void startDragAndSplit(Intent intent, int i, Bundle bundle, int i2, int i3, int i4) {
        Context context = this.mContext;
        try {
            ActivityTaskManager.getService().startActivityAsUser((IApplicationThread) null, context.getPackageName(), (String) null, intent, intent.resolveTypeIfNeeded(context.getContentResolver()), (IBinder) null, (String) null, 0, 0, (ProfilerInfo) null, overrideFreeformWindowingMode(bundle).toBundle(), i2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.wm.shell.draganddrop.DragAndDropPolicy.Starter
    public final void startIntent(int i, int i2, PendingIntent pendingIntent, Intent intent, Bundle bundle) {
        try {
            pendingIntent.send(this.mContext, 0, intent, null, null, null, overrideFreeformWindowingMode(bundle).toBundle());
        } catch (PendingIntent.CanceledException e) {
            Slog.e("FreeformStarter", "Failed to launch activity", e);
        }
    }

    @Override // com.android.wm.shell.draganddrop.DragAndDropPolicy.Starter
    public final void startShortcut(String str, String str2, int i, Bundle bundle, UserHandle userHandle) {
        try {
            ((LauncherApps) this.mContext.getSystemService(LauncherApps.class)).startShortcut(str, str2, null, overrideFreeformWindowingMode(bundle).toBundle(), userHandle);
        } catch (ActivityNotFoundException e) {
            Slog.e("FreeformStarter", "Failed to launch shortcut", e);
        }
    }

    @Override // com.android.wm.shell.draganddrop.DragAndDropPolicy.Starter
    public final void startTask(int i, int i2, Bundle bundle) {
        try {
            ActivityTaskManager.getService().startActivityFromRecents(i, overrideFreeformWindowingMode(bundle).toBundle());
        } catch (RemoteException e) {
            Slog.e("FreeformStarter", "Failed to launch task", e);
        }
    }
}
