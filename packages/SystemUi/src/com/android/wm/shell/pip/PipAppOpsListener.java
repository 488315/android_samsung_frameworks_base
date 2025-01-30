package com.android.wm.shell.pip;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Pair;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipAppOpsListener {
    public final C40371 mAppOpsChangedListener = new C40371();
    public final AppOpsManager mAppOpsManager;
    public final Callback mCallback;
    public final Context mContext;
    public final ShellExecutor mMainExecutor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.pip.PipAppOpsListener$1 */
    public final class C40371 implements AppOpsManager.OnOpChangedListener {
        public C40371() {
        }

        @Override // android.app.AppOpsManager.OnOpChangedListener
        public final void onOpChanged(String str, String str2) {
            try {
                Pair topPipActivity = PipUtils.getTopPipActivity(PipAppOpsListener.this.mContext);
                if (topPipActivity.first != null) {
                    ApplicationInfo applicationInfoAsUser = PipAppOpsListener.this.mContext.getPackageManager().getApplicationInfoAsUser(str2, 0, ((Integer) topPipActivity.second).intValue());
                    if (!applicationInfoAsUser.packageName.equals(((ComponentName) topPipActivity.first).getPackageName()) || PipAppOpsListener.this.mAppOpsManager.checkOpNoThrow(67, applicationInfoAsUser.uid, str2) == 0) {
                        return;
                    }
                    ((HandlerExecutor) PipAppOpsListener.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.pip.PipAppOpsListener$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            PipAppOpsListener.this.mCallback.dismissPip();
                        }
                    });
                }
            } catch (PackageManager.NameNotFoundException unused) {
                PipAppOpsListener pipAppOpsListener = PipAppOpsListener.this;
                pipAppOpsListener.mAppOpsManager.stopWatchingMode(pipAppOpsListener.mAppOpsChangedListener);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void dismissPip();
    }

    public PipAppOpsListener(Context context, Callback callback, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
        this.mCallback = callback;
    }
}
