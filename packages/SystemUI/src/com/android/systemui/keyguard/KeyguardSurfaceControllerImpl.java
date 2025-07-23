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
import kotlin.reflect.KFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardSurfaceControllerImpl {
    public final KFunction isExpandedChangedListener = new KeyguardSurfaceControllerImpl$isExpandedChangedListener$1(this);
    public final KeyguardViewController keyguardViewController;
    public final KeyguardVisibilityMonitor keyguardVisibilityMonitor;
    public SyncRtSurfaceTransactionApplier.SurfaceParams lastKeyguardSurfaceParams;
    public final Executor mainExecutor;
    public final Lazy surfaceTransactionApplier$delegate;
    public final Lazy viewRootImpl$delegate;

    public KeyguardSurfaceControllerImpl(KeyguardViewController keyguardViewController, KeyguardVisibilityMonitor keyguardVisibilityMonitor, dagger.Lazy lazy, Executor executor) {
        this.keyguardViewController = keyguardViewController;
        this.keyguardVisibilityMonitor = keyguardVisibilityMonitor;
        this.mainExecutor = executor;
        final int i = 0;
        this.viewRootImpl$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardSurfaceControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return this.f$0.keyguardViewController.getViewRootImpl();
                    default:
                        return new SyncRtSurfaceTransactionApplier(this.f$0.getViewRootImpl().getView());
                }
            }
        });
        final int i2 = 1;
        this.surfaceTransactionApplier$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardSurfaceControllerImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return this.f$0.keyguardViewController.getViewRootImpl();
                    default:
                        return new SyncRtSurfaceTransactionApplier(this.f$0.getViewRootImpl().getView());
                }
            }
        });
    }

    public static final void access$onPanelStateChanged(KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl, int i, int i2) {
        keyguardSurfaceControllerImpl.getClass();
        Log.d("KeyguardSurface", "onPanelStateChanged oldState = " + i + ", state = " + i2);
        if (i == 0 && i2 == 1) {
            keyguardSurfaceControllerImpl.internalRestoreKeyguardSurfaceIfVisible(true);
        }
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

    public static /* synthetic */ void setKeyguardSurfaceAppearAmount$default(KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl, SurfaceControl.Transaction transaction, int i) {
        if ((i & 2) != 0) {
            transaction = null;
        }
        keyguardSurfaceControllerImpl.setKeyguardSurfaceAppearAmount(transaction, false);
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
            Function1 function1 = (Function1) this.isExpandedChangedListener;
            KeyguardVisibilityMonitor keyguardVisibilityMonitor = this.keyguardVisibilityMonitor;
            ((ArrayList) keyguardVisibilityMonitor.isExpandedChangedListeners).remove(function1);
            ((ArrayList) keyguardVisibilityMonitor.panelStateChangedListeners).remove(new KeyguardSurfaceControllerImpl$internalRestoreKeyguardSurfaceIfVisible$1(this));
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

    public final void restoreKeyguardSurfaceByTransaction() {
        View view = getViewRootImpl().getView();
        Integer valueOf = view != null ? Integer.valueOf(view.getVisibility()) : null;
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = this.keyguardVisibilityMonitor;
        Log.d("KeyguardSurface", "restoreKeyguardSurfaceByTransaction viewRootImpl.view.visibility=" + valueOf + ", keyguardVisibilityMonitor.isVisible()=" + keyguardVisibilityMonitor.isVisible());
        try {
            View view2 = getViewRootImpl().getView();
            if ((view2 == null || view2.getVisibility() != 0) && !keyguardVisibilityMonitor.isVisible()) {
                return;
            }
            SurfaceControl surfaceControl = getViewRootImpl().getSurfaceControl();
            if (surfaceControl == null || !surfaceControl.isValid()) {
                return;
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setAlpha(surfaceControl, 1.0f);
            Log.i("KeyguardSurface", "restoreKeyguardSurfaceByTransaction restore KeyguardSurface alpha");
            transaction.apply();
            transaction.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void restoreKeyguardSurfaceIfVisible() {
        View view = getViewRootImpl().getView();
        Integer valueOf = view != null ? Integer.valueOf(view.getVisibility()) : null;
        KeyguardVisibilityMonitor keyguardVisibilityMonitor = this.keyguardVisibilityMonitor;
        Log.d("KeyguardSurface", "restoreKeyguardSurfaceIfVisible viewRootImpl.view.visibility=" + valueOf + ", keyguardVisibilityMonitor.isVisible()=" + keyguardVisibilityMonitor.isVisible());
        View view2 = getViewRootImpl().getView();
        internalRestoreKeyguardSurfaceIfVisible((view2 != null && view2.getVisibility() == 0) || keyguardVisibilityMonitor.isVisible());
    }

    public final void setKeyguardSurfaceAppearAmount(final SurfaceControl.Transaction transaction, final boolean z) {
        final SurfaceControl surfaceControl = getViewRootImpl().getSurfaceControl();
        surfaceControl.getClass();
        if (isValid(surfaceControl, 0.001f)) {
            Log.d("KeyguardSurface", "setKeyguardSurfaceAppearAmount amount=0.001 hasTransaction=" + (transaction != null) + " registerPanelState=" + z);
            final long j = surfaceControl.mNativeObject;
            if (transaction != null) {
                try {
                    transaction.setAlpha(surfaceControl, 0.001f);
                } catch (Exception unused) {
                    String hexString = Long.toHexString(j);
                    KeyguardViewController keyguardViewController = this.keyguardViewController;
                    Log.d("KeyguardSurface", "setKeyguardSurfaceAppearAmount in transaction?.setAlpha previousSurface : " + surfaceControl + ", id : " + hexString + ", currentSurface : " + keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                }
            }
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$$ExternalSyntheticLambda3
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SurfaceControl surfaceControl2 = surfaceControl;
                    SurfaceControl.Transaction transaction2 = transaction;
                    KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl = KeyguardSurfaceControllerImpl.this;
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl2).withAlpha(0.001f).build();
                    if (transaction2 == null) {
                        try {
                            build.getClass();
                            ((SyncRtSurfaceTransactionApplier) keyguardSurfaceControllerImpl.surfaceTransactionApplier$delegate.getValue()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                        } catch (Exception unused2) {
                            String hexString2 = Long.toHexString(j);
                            KeyguardViewController keyguardViewController2 = keyguardSurfaceControllerImpl.keyguardViewController;
                            Log.d("KeyguardSurface", "setKeyguardSurfaceAppearAmount in runOnMainThread previousSurface : " + surfaceControl2 + ", id : " + hexString2 + ", currentSurface : " + keyguardViewController2.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardViewController2.getViewRootImpl().getSurfaceControl().mNativeObject));
                        }
                    }
                    keyguardSurfaceControllerImpl.lastKeyguardSurfaceParams = build;
                    Function1 function1 = (Function1) keyguardSurfaceControllerImpl.isExpandedChangedListener;
                    KeyguardVisibilityMonitor keyguardVisibilityMonitor = keyguardSurfaceControllerImpl.keyguardVisibilityMonitor;
                    ArrayList arrayList = (ArrayList) keyguardVisibilityMonitor.isExpandedChangedListeners;
                    if (!arrayList.contains(function1)) {
                        arrayList.add(function1);
                    }
                    if (z) {
                        KeyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1$2 keyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1$2 = new KeyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1$2(keyguardSurfaceControllerImpl);
                        ArrayList arrayList2 = (ArrayList) keyguardVisibilityMonitor.panelStateChangedListeners;
                        if (!arrayList2.contains(keyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1$2)) {
                            arrayList2.add(keyguardSurfaceControllerImpl$setKeyguardSurfaceAppearAmount$1$2);
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
        surfaceControl.getClass();
        if (isValid(surfaceControl, 1.0f)) {
            int visibility = getViewRootImpl().getView() == null ? 8 : getViewRootImpl().getView().getVisibility();
            if (visibility == 4 || visibility == 8) {
                Log.d("KeyguardSurface", "setKeyguardSurfaceVisible view is not visible");
                return;
            }
            Log.d("KeyguardSurface", "setKeyguardSurfaceVisible visible=false, hasTransaction=" + (transaction != null));
            final long j = surfaceControl.mNativeObject;
            if (transaction != null) {
                try {
                    transaction.setVisibility(surfaceControl, false);
                } catch (Exception unused) {
                    String hexString = Long.toHexString(j);
                    KeyguardViewController keyguardViewController = this.keyguardViewController;
                    Log.d("KeyguardSurface", "setKeyguardSurfaceVisible in transaction?.setVisibility previousSurface : " + surfaceControl + ", id : " + hexString + ", currentSurface : " + keyguardViewController.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardViewController.getViewRootImpl().getSurfaceControl().mNativeObject));
                }
            }
            Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardSurfaceControllerImpl$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    SurfaceControl surfaceControl2 = surfaceControl;
                    SurfaceControl.Transaction transaction2 = transaction;
                    SyncRtSurfaceTransactionApplier.SurfaceParams build = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(surfaceControl2).withVisibility(false).build();
                    KeyguardSurfaceControllerImpl keyguardSurfaceControllerImpl = KeyguardSurfaceControllerImpl.this;
                    if (transaction2 == null) {
                        try {
                            build.getClass();
                            ((SyncRtSurfaceTransactionApplier) keyguardSurfaceControllerImpl.surfaceTransactionApplier$delegate.getValue()).scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{build});
                        } catch (Exception unused2) {
                            String hexString2 = Long.toHexString(j);
                            KeyguardViewController keyguardViewController2 = keyguardSurfaceControllerImpl.keyguardViewController;
                            Log.d("KeyguardSurface", "setKeyguardSurfaceVisible in runOnMainThread previousSurface : " + surfaceControl2 + ", id : " + hexString2 + ", currentSurface : " + keyguardViewController2.getViewRootImpl().getSurfaceControl() + ", id : " + Long.toHexString(keyguardViewController2.getViewRootImpl().getSurfaceControl().mNativeObject));
                        }
                    }
                    keyguardSurfaceControllerImpl.lastKeyguardSurfaceParams = build;
                    Function1 function1 = (Function1) keyguardSurfaceControllerImpl.isExpandedChangedListener;
                    ArrayList arrayList = (ArrayList) keyguardSurfaceControllerImpl.keyguardVisibilityMonitor.isExpandedChangedListeners;
                    if (!arrayList.contains(function1)) {
                        arrayList.add(function1);
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
