package com.android.systemui.keyguard;

import android.os.Debug;
import android.os.Looper;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewRootImpl;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.util.DeviceType;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KFunction;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardSurfaceControllerImpl {
    public final KeyguardViewController keyguardViewController;
    public final KeyguardVisibilityMonitor keyguardVisibilityMonitor;
    public SyncRtSurfaceTransactionApplier.SurfaceParams lastKeyguardSurfaceParams;
    public final Executor mainExecutor;
    public final KFunction isExpandedChangedListener = new KeyguardSurfaceControllerImpl$isExpandedChangedListener$1(this);
    public final Lazy viewRootImpl$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$viewRootImpl$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return KeyguardSurfaceControllerImpl.this.keyguardViewController.getViewRootImpl();
        }
    });
    public final Lazy surfaceTransactionApplier$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$surfaceTransactionApplier$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new SyncRtSurfaceTransactionApplier(KeyguardSurfaceControllerImpl.this.getViewRootImpl().getView());
        }
    });

    public KeyguardSurfaceControllerImpl(KeyguardViewController keyguardViewController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, dagger.Lazy lazy, Executor executor) {
        this.keyguardViewController = keyguardViewController;
        this.keyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.mainExecutor = executor;
    }

    public static boolean isValid(SurfaceControl surfaceControl, float f) {
        if (!surfaceControl.isValid()) {
            Log.w("KeyguardSurface", "invalid surface");
            return false;
        }
        if (f >= 0.0f && f <= 1.0f) {
            return true;
        }
        Log.w("KeyguardSurface", "wrong amount " + f);
        return false;
    }

    public final ViewRootImpl getViewRootImpl() {
        return (ViewRootImpl) this.viewRootImpl$delegate.getValue();
    }

    public final void internalRestoreKeyguardSurfaceIfVisible(boolean z) {
        Log.d("KeyguardSurface", "internalRestoreKeyguardSurfaceIfVisible " + z);
        if (z) {
            SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams = this.lastKeyguardSurfaceParams;
            boolean z2 = true;
            if (surfaceParams != null) {
                SurfaceControl surfaceControl = surfaceParams.surface;
                z2 = true ^ (surfaceControl != null ? surfaceControl.isValid() : false);
            }
            if (!z2) {
                restoreKeyguardSurface();
            }
            ((ArrayList) this.keyguardVisibilityMonitor.isExpandedChangedListeners).remove((Function1) this.isExpandedChangedListener);
        }
    }

    public final void restoreKeyguardSurface() {
        Boolean bool;
        SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams = this.lastKeyguardSurfaceParams;
        if (surfaceParams != null) {
            boolean z = false;
            if (isValid(surfaceParams.surface, 1.0f)) {
                ((SyncRtSurfaceTransactionApplier) this.surfaceTransactionApplier$delegate.getValue()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceParams.surface).withVisibility(true).withAlpha(1.0f).build()});
                z = true;
            }
            bool = Boolean.valueOf(z);
        } else {
            bool = null;
        }
        String str = "restoreKeyguardSurface surfaceParam=" + this.lastKeyguardSurfaceParams + " restored=" + bool;
        if (DeviceType.isDebuggable()) {
            str = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "\n", Debug.getCallers(3, " "));
        }
        Log.d("KeyguardSurface", str);
        this.lastKeyguardSurfaceParams = null;
    }

    public final void restoreKeyguardSurfaceIfVisible() {
        View view = getViewRootImpl().getView();
        Integer valueOf = view != null ? Integer.valueOf(view.getVisibility()) : null;
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = this.keyguardVisibilityMonitor;
        Log.d("KeyguardSurface", "restoreKeyguardSurfaceIfVisible viewRootImpl.view.visibility=" + valueOf + ", keyguardVisibilityMonitor.isVisible()=" + keyguardVisibilityMonitor.isVisible());
        View view2 = getViewRootImpl().getView();
        internalRestoreKeyguardSurfaceIfVisible((view2 != null && view2.getVisibility() == 0) || keyguardVisibilityMonitor.isVisible());
    }

    public final void setKeyguardSurfaceAppearAmount(final float f, final SurfaceControl.Transaction transaction) {
        final SurfaceControl surfaceControl = getViewRootImpl().getSurfaceControl();
        Intrinsics.checkNotNull(surfaceControl);
        if (isValid(surfaceControl, f)) {
            Log.d("KeyguardSurface", "setKeyguardSurfaceAppearAmount amount=" + f + " hasTransaction=" + (transaction != null));
            final long j = surfaceControl.mNativeObject;
            if (transaction != null) {
                try {
                    transaction.setAlpha(surfaceControl, f);
                } catch (Exception unused) {
                    String hexString = Long.toHexString(j);
                    KeyguardViewController keyguardViewController = this.keyguardViewController;
                    Log.d("KeyguardSurface", "setKeyguardSurfaceAppearAmount in transaction?.setAlpha previousSurface : " + surfaceControl + ", id : " + hexString + ", currentSurface : " + keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                }
            }
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyguardSurfaceControllerImpl.this.getClass();
                    KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl = KeyguardSurfaceControllerImpl.this;
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl).withAlpha(f).build();
                    SurfaceControl.Transaction transaction2 = transaction;
                    KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl2 = KeyguardSurfaceControllerImpl.this;
                    SurfaceControl surfaceControl2 = surfaceControl;
                    long j2 = j;
                    if (transaction2 == null) {
                        try {
                            Intrinsics.checkNotNull(build);
                            ((SyncRtSurfaceTransactionApplier) keyguardSurfaceControllerImpl2.surfaceTransactionApplier$delegate.getValue()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                        } catch (Exception unused2) {
                            Log.d("KeyguardSurface", "setKeyguardSurfaceAppearAmount in runOnMainThread previousSurface : " + surfaceControl2 + ", id : " + Long.toHexString(j2) + ", currentSurface : " + keyguardSurfaceControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardSurfaceControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                        }
                    }
                    keyguardSurfaceControllerImpl.lastKeyguardSurfaceParams = build;
                    if (f != 1.0f) {
                        KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl3 = KeyguardSurfaceControllerImpl.this;
                        KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardSurfaceControllerImpl3.keyguardVisibilityMonitor;
                        Function1 function1 = (Function1) keyguardSurfaceControllerImpl3.isExpandedChangedListener;
                        ArrayList arrayList = (ArrayList) keyguardVisibilityMonitor.isExpandedChangedListeners;
                        if (!arrayList.contains(function1)) {
                            arrayList.add(function1);
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            if (Looper.getMainLooper().isCurrentThread()) {
                function0.invoke();
            } else {
                this.mainExecutor.execute(new KeyguardSurfaceControllerImplKt$sam$java_lang_Runnable$0(function0));
            }
        }
    }

    public final void setKeyguardSurfaceVisible(final SurfaceControl.Transaction transaction) {
        final SurfaceControl surfaceControl = getViewRootImpl().getSurfaceControl();
        Intrinsics.checkNotNull(surfaceControl);
        if (isValid(surfaceControl, 1.0f)) {
            int visibility = getViewRootImpl().getView() == null ? 8 : getViewRootImpl().getView().getVisibility();
            if (visibility == 4 || visibility == 8) {
                Log.d("KeyguardSurface", "setKeyguardSurfaceVisible view is not visible");
                return;
            }
            Log.d("KeyguardSurface", "setKeyguardSurfaceVisible visible=false, hasTransaction=" + (transaction != null));
            final long j = surfaceControl.mNativeObject;
            final boolean z = false;
            if (transaction != null) {
                try {
                    transaction.setVisibility(surfaceControl, false);
                } catch (Exception unused) {
                    String hexString = Long.toHexString(j);
                    KeyguardViewController keyguardViewController = this.keyguardViewController;
                    Log.d("KeyguardSurface", "setKeyguardSurfaceVisible in transaction?.setVisibility previousSurface : " + surfaceControl + ", id : " + hexString + ", currentSurface : " + keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                }
            }
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$setKeyguardSurfaceVisible$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl = KeyguardSurfaceControllerImpl.this;
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl).withVisibility(z).build();
                    SurfaceControl.Transaction transaction2 = transaction;
                    KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl2 = KeyguardSurfaceControllerImpl.this;
                    SurfaceControl surfaceControl2 = surfaceControl;
                    long j2 = j;
                    if (transaction2 == null) {
                        try {
                            Intrinsics.checkNotNull(build);
                            ((SyncRtSurfaceTransactionApplier) keyguardSurfaceControllerImpl2.surfaceTransactionApplier$delegate.getValue()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                        } catch (Exception unused2) {
                            Log.d("KeyguardSurface", "setKeyguardSurfaceVisible in runOnMainThread previousSurface : " + surfaceControl2 + ", id : " + Long.toHexString(j2) + ", currentSurface : " + keyguardSurfaceControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardSurfaceControllerImpl2.keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                        }
                    }
                    keyguardSurfaceControllerImpl.lastKeyguardSurfaceParams = build;
                    if (!z) {
                        KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl3 = KeyguardSurfaceControllerImpl.this;
                        KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardSurfaceControllerImpl3.keyguardVisibilityMonitor;
                        Function1 function1 = (Function1) keyguardSurfaceControllerImpl3.isExpandedChangedListener;
                        ArrayList arrayList = (ArrayList) keyguardVisibilityMonitor.isExpandedChangedListeners;
                        if (!arrayList.contains(function1)) {
                            arrayList.add(function1);
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            if (Looper.getMainLooper().isCurrentThread()) {
                function0.invoke();
            } else {
                this.mainExecutor.execute(new KeyguardSurfaceControllerImplKt$sam$java_lang_Runnable$0(function0));
            }
        }
    }
}
