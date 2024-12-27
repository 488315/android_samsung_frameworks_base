package com.android.server.wm;

import android.os.IBinder;

import com.android.server.inputmethod.ImeVisibilityStateComputer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class WindowManagerService$$ExternalSyntheticLambda21
        implements Runnable {
    public final /* synthetic */ WindowManagerService f$0;
    public final /* synthetic */ IBinder f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ WindowManagerService$$ExternalSyntheticLambda21(
            WindowManagerService windowManagerService,
            IBinder iBinder,
            int i,
            boolean z,
            boolean z2) {
        this.f$0 = windowManagerService;
        this.f$1 = iBinder;
        this.f$2 = i;
        this.f$3 = z;
        this.f$4 = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        WindowManagerService windowManagerService = this.f$0;
        IBinder iBinder = this.f$1;
        int i = this.f$2;
        boolean z = this.f$3;
        boolean z2 = this.f$4;
        ImeVisibilityStateComputer.AnonymousClass1 anonymousClass1 =
                (ImeVisibilityStateComputer.AnonymousClass1)
                        windowManagerService.mImeTargetChangeListener;
        anonymousClass1.getClass();
        if (!z || z2 || i == 3) {
            iBinder = null;
        }
        ImeVisibilityStateComputer.this.mCurVisibleImeLayeringOverlay = iBinder;
    }
}
