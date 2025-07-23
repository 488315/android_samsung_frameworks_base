package com.android.wm.shell.common.pip;

import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Pair;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PipAppOpsListener {
    public final AppOpsManager mAppOpsManager;
    public Callback mCallback;
    public final Context mContext;
    public final ShellExecutor mMainExecutor;
    public Function1 mTopPipActivityInfoSupplier = new PipAppOpsListener$mTopPipActivityInfoSupplier$1(PipUtils.INSTANCE);
    public final PipAppOpsListener$mAppOpsChangedListener$1 mAppOpsChangedListener = new AppOpsManager.OnOpChangedListener() { // from class: com.android.wm.shell.common.pip.PipAppOpsListener$mAppOpsChangedListener$1
        @Override // android.app.AppOpsManager.OnOpChangedListener
        public final void onOpChanged(String str, String str2) {
            PipAppOpsListener pipAppOpsListener;
            final PipAppOpsListener.Callback callback;
            try {
                PipAppOpsListener pipAppOpsListener2 = PipAppOpsListener.this;
                Pair pair = (Pair) pipAppOpsListener2.mTopPipActivityInfoSupplier.mo779invoke(pipAppOpsListener2.mContext);
                ComponentName componentName = (ComponentName) pair.first;
                if (componentName == null) {
                    return;
                }
                Integer num = (Integer) pair.second;
                PackageManager packageManager = PipAppOpsListener.this.mContext.getPackageManager();
                num.getClass();
                ApplicationInfo applicationInfoAsUser = packageManager.getApplicationInfoAsUser(str2, 0, num.intValue());
                if (!Intrinsics.areEqual(applicationInfoAsUser.packageName, componentName.getPackageName()) || PipAppOpsListener.this.mAppOpsManager.checkOpNoThrow(67, applicationInfoAsUser.uid, str2) == 0 || (callback = (pipAppOpsListener = PipAppOpsListener.this).mCallback) == null) {
                    return;
                }
                pipAppOpsListener.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.common.pip.PipAppOpsListener$mAppOpsChangedListener$1$1$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipAppOpsListener.Callback.this.dismissPip();
                    }
                });
            } catch (PackageManager.NameNotFoundException unused) {
                PipAppOpsListener pipAppOpsListener3 = PipAppOpsListener.this;
                pipAppOpsListener3.mAppOpsManager.stopWatchingMode(pipAppOpsListener3.mAppOpsChangedListener);
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void dismissPip();
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.wm.shell.common.pip.PipAppOpsListener$mAppOpsChangedListener$1] */
    public PipAppOpsListener(Context context, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mAppOpsManager = (AppOpsManager) context.getSystemService("appops");
    }

    public final void setTopPipActivityInfoSupplier(Function1 function1) {
        this.mTopPipActivityInfoSupplier = function1;
    }
}
