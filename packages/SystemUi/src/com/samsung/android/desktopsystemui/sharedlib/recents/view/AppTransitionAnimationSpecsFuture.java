package com.samsung.android.desktopsystemui.sharedlib.recents.view;

import android.os.Handler;
import android.os.Looper;
import android.view.AppTransitionAnimationSpec;
import android.view.IAppTransitionAnimationSpecsFuture;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class AppTransitionAnimationSpecsFuture {
    private FutureTask<List<AppTransitionAnimationSpecCompat>> mComposeTask = new FutureTask<>(new Callable<List<AppTransitionAnimationSpecCompat>>() { // from class: com.samsung.android.desktopsystemui.sharedlib.recents.view.AppTransitionAnimationSpecsFuture.1
        @Override // java.util.concurrent.Callable
        public List<AppTransitionAnimationSpecCompat> call() {
            return AppTransitionAnimationSpecsFuture.this.composeSpecs();
        }
    });
    private final IAppTransitionAnimationSpecsFuture mFuture = new IAppTransitionAnimationSpecsFuture.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.recents.view.AppTransitionAnimationSpecsFuture.2
        public AppTransitionAnimationSpec[] get() {
            try {
                if (!AppTransitionAnimationSpecsFuture.this.mComposeTask.isDone()) {
                    AppTransitionAnimationSpecsFuture.this.mHandler.post(AppTransitionAnimationSpecsFuture.this.mComposeTask);
                }
                List list = (List) AppTransitionAnimationSpecsFuture.this.mComposeTask.get();
                AppTransitionAnimationSpecsFuture.this.mComposeTask = null;
                if (list == null) {
                    return null;
                }
                AppTransitionAnimationSpec[] appTransitionAnimationSpecArr = new AppTransitionAnimationSpec[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    appTransitionAnimationSpecArr[i] = ((AppTransitionAnimationSpecCompat) list.get(i)).toAppTransitionAnimationSpec();
                }
                return appTransitionAnimationSpecArr;
            } catch (Exception unused) {
                return null;
            }
        }
    };
    private final Handler mHandler;

    public AppTransitionAnimationSpecsFuture(Handler handler) {
        this.mHandler = handler;
    }

    public abstract List<AppTransitionAnimationSpecCompat> composeSpecs();

    public final void composeSpecsSynchronous() {
        if (Looper.myLooper() != this.mHandler.getLooper()) {
            throw new RuntimeException("composeSpecsSynchronous() called from wrong looper");
        }
        this.mComposeTask.run();
    }

    public final IAppTransitionAnimationSpecsFuture getFuture() {
        return this.mFuture;
    }
}
