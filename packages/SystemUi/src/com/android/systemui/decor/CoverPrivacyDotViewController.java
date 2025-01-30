package com.android.systemui.decor;

import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.animation.Animator;
import com.android.app.animation.Interpolators;
import com.android.systemui.CameraAvailabilityListener;
import com.android.systemui.R;
import com.android.systemui.decor.CoverPrivacyDotViewController;
import com.android.systemui.statusbar.events.SystemStatusAnimationCallback;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CoverPrivacyDotViewController {
    public final SystemStatusAnimationScheduler animationScheduler;
    public boolean applyDelayNextViewState;

    /* renamed from: bl */
    public View f278bl;

    /* renamed from: br */
    public View f279br;
    public CameraAvailabilityListener cameraListener;
    public final CoverPrivacyDotViewController$cameraTransitionCallback$1 cameraTransitionCallback;
    public ExecutorImpl.ExecutionToken cancelRunnable;
    public final ConfigurationController configurationController;
    public CoverViewState currentViewState;
    public int cutoutHeight;
    public int dotContainerHeight;
    public int dotContainerWidth;
    public Handler handler;
    public final Object lock;
    public final Executor mainExecutor;
    public CoverViewState nextViewState;
    public final CoverPrivacyDotViewController$systemStatusAnimationCallback$1 systemStatusAnimationCallback;

    /* renamed from: tl */
    public View f280tl;

    /* renamed from: tr */
    public View f281tr;
    public DelayableExecutor uiExecutor;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.decor.CoverPrivacyDotViewController$systemStatusAnimationCallback$1] */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.decor.CoverPrivacyDotViewController$cameraTransitionCallback$1] */
    public CoverPrivacyDotViewController(Executor executor, ConfigurationController configurationController, SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.mainExecutor = executor;
        this.configurationController = configurationController;
        this.animationScheduler = systemStatusAnimationScheduler;
        CoverViewState coverViewState = new CoverViewState(false, false, false, false, 0, 0, null, null, 255, null);
        this.currentViewState = coverViewState;
        this.nextViewState = CoverViewState.copy$default(coverViewState, false, false, false, false, 0, 0, null, null, 255);
        this.lock = new Object();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.decor.CoverPrivacyDotViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onLayoutDirectionChanged(final boolean z) {
                final CoverPrivacyDotViewController coverPrivacyDotViewController = CoverPrivacyDotViewController.this;
                DelayableExecutor delayableExecutor = coverPrivacyDotViewController.uiExecutor;
                if (delayableExecutor != null) {
                    ((ExecutorImpl) delayableExecutor).execute(new Runnable() { // from class: com.android.systemui.decor.CoverPrivacyDotViewController$1$onLayoutDirectionChanged$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Iterator it = CoverPrivacyDotViewController.this.getViews().iterator();
                            while (it.hasNext()) {
                                ((View) it.next()).setVisibility(4);
                            }
                            CoverPrivacyDotViewController.C12301 c12301 = this;
                            CoverPrivacyDotViewController coverPrivacyDotViewController2 = CoverPrivacyDotViewController.this;
                            boolean z2 = z;
                            synchronized (c12301) {
                                coverPrivacyDotViewController2.setNextViewState(CoverViewState.copy$default(coverPrivacyDotViewController2.nextViewState, false, false, false, z2, 0, 0, coverPrivacyDotViewController2.selectDesignatedCorner(coverPrivacyDotViewController2.nextViewState.rotation, z2), null, 183));
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                    });
                }
            }
        });
        this.systemStatusAnimationCallback = new SystemStatusAnimationCallback() { // from class: com.android.systemui.decor.CoverPrivacyDotViewController$systemStatusAnimationCallback$1
            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onHidePersistentDot(boolean z) {
                CoverPrivacyDotViewController coverPrivacyDotViewController = CoverPrivacyDotViewController.this;
                synchronized (coverPrivacyDotViewController.lock) {
                    coverPrivacyDotViewController.setNextViewState(CoverViewState.copy$default(coverPrivacyDotViewController.nextViewState, false, false, false, false, 0, 0, null, null, IKnoxCustomManager.Stub.TRANSACTION_getDexForegroundModePackageList));
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationBegin(boolean z) {
                return null;
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final Animator onSystemEventAnimationFinish(boolean z, boolean z2) {
                return null;
            }

            @Override // com.android.systemui.statusbar.events.SystemStatusAnimationCallback
            public final void onSystemStatusAnimationTransitionToPersistentDot(String str) {
                CoverPrivacyDotViewController coverPrivacyDotViewController = CoverPrivacyDotViewController.this;
                synchronized (coverPrivacyDotViewController.lock) {
                    coverPrivacyDotViewController.setNextViewState(CoverViewState.copy$default(coverPrivacyDotViewController.nextViewState, false, true, false, false, 0, 0, null, str, 125));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
        this.cameraTransitionCallback = new CameraAvailabilityListener.CameraTransitionCallback() { // from class: com.android.systemui.decor.CoverPrivacyDotViewController$cameraTransitionCallback$1
            @Override // com.android.systemui.CameraAvailabilityListener.CameraTransitionCallback
            public final void onApplyCameraProtection(Path path, Rect rect) {
                CoverPrivacyDotViewController coverPrivacyDotViewController = CoverPrivacyDotViewController.this;
                synchronized (coverPrivacyDotViewController.lock) {
                    coverPrivacyDotViewController.setNextViewState(CoverViewState.copy$default(coverPrivacyDotViewController.nextViewState, false, false, true, false, 0, 0, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut));
                    Unit unit = Unit.INSTANCE;
                }
            }

            @Override // com.android.systemui.CameraAvailabilityListener.CameraTransitionCallback
            public final void onHideCameraProtection() {
                CoverPrivacyDotViewController coverPrivacyDotViewController = CoverPrivacyDotViewController.this;
                synchronized (coverPrivacyDotViewController.lock) {
                    coverPrivacyDotViewController.setNextViewState(CoverViewState.copy$default(coverPrivacyDotViewController.nextViewState, false, false, false, false, 0, 0, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexURLShortcut));
                    Unit unit = Unit.INSTANCE;
                }
            }
        };
    }

    public final int cornerForView(View view) {
        View view2 = this.f280tl;
        if (view2 == null) {
            view2 = null;
        }
        if (Intrinsics.areEqual(view, view2)) {
            return 0;
        }
        View view3 = this.f281tr;
        if (view3 == null) {
            view3 = null;
        }
        if (Intrinsics.areEqual(view, view3)) {
            return 1;
        }
        View view4 = this.f278bl;
        if (view4 == null) {
            view4 = null;
        }
        if (Intrinsics.areEqual(view, view4)) {
            return 3;
        }
        View view5 = this.f279br;
        if (Intrinsics.areEqual(view, view5 != null ? view5 : null)) {
            return 2;
        }
        throw new IllegalArgumentException("not a corner view");
    }

    public final Sequence getViews() {
        View view = this.f280tl;
        if (view == null) {
            return SequencesKt__SequencesKt.sequenceOf(new View[0]);
        }
        View[] viewArr = new View[4];
        if (view == null) {
            view = null;
        }
        viewArr[0] = view;
        View view2 = this.f281tr;
        if (view2 == null) {
            view2 = null;
        }
        viewArr[1] = view2;
        View view3 = this.f279br;
        if (view3 == null) {
            view3 = null;
        }
        viewArr[2] = view3;
        View view4 = this.f278bl;
        viewArr[3] = view4 != null ? view4 : null;
        return SequencesKt__SequencesKt.sequenceOf(viewArr);
    }

    public final View selectDesignatedCorner(int i, boolean z) {
        View view = this.f280tl;
        if (view == null) {
            return null;
        }
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalStateException("unknown rotation");
                    }
                    if (z) {
                        View view2 = this.f278bl;
                        if (view2 != null) {
                            return view2;
                        }
                    } else if (view != null) {
                        return view;
                    }
                } else if (z) {
                    View view3 = this.f279br;
                    if (view3 != null) {
                        return view3;
                    }
                } else {
                    View view4 = this.f278bl;
                    if (view4 != null) {
                        return view4;
                    }
                }
            } else if (z) {
                View view5 = this.f281tr;
                if (view5 != null) {
                    return view5;
                }
            } else {
                View view6 = this.f279br;
                if (view6 != null) {
                    return view6;
                }
            }
        } else if (!z) {
            View view7 = this.f281tr;
            if (view7 != null) {
                return view7;
            }
        } else if (view != null) {
            return view;
        }
        return null;
    }

    public final void setCornerSizes(CoverViewState coverViewState) {
        int i = coverViewState.rotation;
        if (i == 0 || i == 2) {
            View view = this.f280tl;
            if (view == null) {
                view = null;
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            layoutParams.width = this.dotContainerWidth;
            layoutParams.height = this.dotContainerHeight;
        } else {
            View view2 = this.f280tl;
            if (view2 == null) {
                view2 = null;
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view2.getLayoutParams();
            layoutParams2.width = this.dotContainerHeight;
            layoutParams2.height = this.dotContainerWidth;
        }
        if (i == 0 || i == 2) {
            View view3 = this.f281tr;
            if (view3 == null) {
                view3 = null;
            }
            FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) view3.getLayoutParams();
            layoutParams3.width = this.dotContainerWidth;
            layoutParams3.height = this.dotContainerHeight;
        } else {
            View view4 = this.f281tr;
            if (view4 == null) {
                view4 = null;
            }
            FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) view4.getLayoutParams();
            layoutParams4.width = this.dotContainerHeight;
            layoutParams4.height = this.dotContainerWidth;
        }
        if (i == 0 || i == 2) {
            View view5 = this.f278bl;
            if (view5 == null) {
                view5 = null;
            }
            FrameLayout.LayoutParams layoutParams5 = (FrameLayout.LayoutParams) view5.getLayoutParams();
            layoutParams5.width = this.dotContainerWidth;
            int i2 = this.dotContainerHeight;
            int i3 = this.cutoutHeight;
            layoutParams5.height = i2 + i3;
            View view6 = this.f278bl;
            if (view6 == null) {
                view6 = null;
            }
            view6.setPadding(0, i3, 0, 0);
        } else {
            View view7 = this.f278bl;
            if (view7 == null) {
                view7 = null;
            }
            FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) view7.getLayoutParams();
            int i4 = this.dotContainerHeight;
            int i5 = this.cutoutHeight;
            layoutParams6.width = i4 + i5;
            layoutParams6.height = this.dotContainerWidth;
            View view8 = this.f278bl;
            if (view8 == null) {
                view8 = null;
            }
            view8.setPadding(i5, 0, 0, 0);
        }
        if (i == 0 || i == 2) {
            View view9 = this.f279br;
            if (view9 == null) {
                view9 = null;
            }
            FrameLayout.LayoutParams layoutParams7 = (FrameLayout.LayoutParams) view9.getLayoutParams();
            layoutParams7.width = this.dotContainerWidth;
            int i6 = this.dotContainerHeight;
            int i7 = this.cutoutHeight;
            layoutParams7.height = i6 + i7;
            View view10 = this.f279br;
            (view10 != null ? view10 : null).setPadding(0, i7, 0, 0);
            return;
        }
        View view11 = this.f279br;
        if (view11 == null) {
            view11 = null;
        }
        FrameLayout.LayoutParams layoutParams8 = (FrameLayout.LayoutParams) view11.getLayoutParams();
        int i8 = this.dotContainerHeight;
        int i9 = this.cutoutHeight;
        layoutParams8.width = i8 + i9;
        layoutParams8.height = this.dotContainerWidth;
        View view12 = this.f279br;
        (view12 != null ? view12 : null).setPadding(0, 0, i9, 0);
    }

    public final void setNextViewState(CoverViewState coverViewState) {
        ExecutorImpl.ExecutionToken executionToken;
        if (this.nextViewState.rotation != coverViewState.rotation) {
            this.applyDelayNextViewState = true;
        }
        this.nextViewState = coverViewState;
        ExecutorImpl.ExecutionToken executionToken2 = this.cancelRunnable;
        if (executionToken2 != null) {
            executionToken2.run();
        }
        DelayableExecutor delayableExecutor = this.uiExecutor;
        if (delayableExecutor != null) {
            executionToken = delayableExecutor.executeDelayed(this.applyDelayNextViewState ? 300L : 0L, new Runnable() { // from class: com.android.systemui.decor.CoverPrivacyDotViewController$scheduleUpdate$1
                @Override // java.lang.Runnable
                public final void run() {
                    CoverViewState copy$default;
                    CoverPrivacyDotViewController coverPrivacyDotViewController = CoverPrivacyDotViewController.this;
                    if (coverPrivacyDotViewController.applyDelayNextViewState) {
                        coverPrivacyDotViewController.applyDelayNextViewState = false;
                    }
                    synchronized (coverPrivacyDotViewController.lock) {
                        copy$default = CoverViewState.copy$default(coverPrivacyDotViewController.nextViewState, false, false, false, false, 0, 0, null, null, 255);
                        Unit unit = Unit.INSTANCE;
                    }
                    Objects.toString(copy$default);
                    if (copy$default.viewInitialized && !Intrinsics.areEqual(copy$default, coverPrivacyDotViewController.currentViewState)) {
                        int i = coverPrivacyDotViewController.currentViewState.rotation;
                        int i2 = copy$default.rotation;
                        if (i2 != i) {
                            coverPrivacyDotViewController.updateRotations(i2);
                        }
                        if (i2 != coverPrivacyDotViewController.currentViewState.rotation) {
                            coverPrivacyDotViewController.setCornerSizes(copy$default);
                            Iterator it = coverPrivacyDotViewController.getViews().iterator();
                            while (it.hasNext()) {
                                ((View) it.next()).requestLayout();
                            }
                        }
                        View view = coverPrivacyDotViewController.currentViewState.designatedCorner;
                        final View view2 = copy$default.designatedCorner;
                        boolean areEqual = Intrinsics.areEqual(view2, view);
                        String str = copy$default.contentDescription;
                        if (!areEqual) {
                            View view3 = coverPrivacyDotViewController.currentViewState.designatedCorner;
                            if (view3 != null) {
                                view3.setContentDescription(null);
                            }
                            if (view2 != null) {
                                view2.setContentDescription(str);
                            }
                            if ((copy$default.systemPrivacyEventIsActive && !copy$default.isDotBlocked) && view2 != null) {
                                view2.clearAnimation();
                                view2.setVisibility(0);
                                view2.setAlpha(0.0f);
                                view2.animate().alpha(1.0f).setDuration(300L).start();
                            }
                        } else if (!Intrinsics.areEqual(str, coverPrivacyDotViewController.currentViewState.contentDescription) && view2 != null) {
                            view2.setContentDescription(str);
                        }
                        boolean z = copy$default.systemPrivacyEventIsActive && !copy$default.isDotBlocked;
                        CoverViewState coverViewState2 = coverPrivacyDotViewController.currentViewState;
                        if (z != (coverViewState2.systemPrivacyEventIsActive && !coverViewState2.isDotBlocked)) {
                            if (z && view2 != null) {
                                view2.clearAnimation();
                                view2.setVisibility(0);
                                view2.setAlpha(0.0f);
                                view2.animate().alpha(1.0f).setDuration(160L).setInterpolator(Interpolators.ALPHA_IN).start();
                            } else if (!z && view2 != null) {
                                view2.clearAnimation();
                                view2.animate().setDuration(160L).setInterpolator(Interpolators.ALPHA_OUT).alpha(0.0f).withEndAction(new Runnable() { // from class: com.android.systemui.decor.CoverPrivacyDotViewController$hideDotView$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        view2.setVisibility(4);
                                    }
                                }).start();
                            }
                        }
                        coverPrivacyDotViewController.currentViewState = copy$default;
                    }
                }
            });
        } else {
            executionToken = null;
        }
        this.cancelRunnable = executionToken;
    }

    public final void updateRotations(int i) {
        int i2;
        for (View view : getViews()) {
            int cornerForView = cornerForView(view) - i;
            if (cornerForView < 0) {
                cornerForView += 4;
            }
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            int i3 = 83;
            if (cornerForView == 0) {
                i2 = 51;
            } else if (cornerForView == 1) {
                i2 = 53;
            } else if (cornerForView == 2) {
                i2 = 85;
            } else {
                if (cornerForView != 3) {
                    throw new IllegalArgumentException("Not a corner");
                }
                i2 = 83;
            }
            layoutParams.gravity = i2;
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) view.findViewById(R.id.privacy_dot).getLayoutParams();
            if (cornerForView == 0) {
                i3 = 85;
            } else if (cornerForView == 1) {
                continue;
            } else if (cornerForView == 2) {
                i3 = 51;
            } else {
                if (cornerForView != 3) {
                    throw new IllegalArgumentException("Not a corner");
                }
                i3 = 53;
            }
            layoutParams2.gravity = i3;
        }
    }
}
