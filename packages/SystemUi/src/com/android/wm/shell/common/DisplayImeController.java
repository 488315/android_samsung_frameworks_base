package com.android.wm.shell.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.EventLog;
import android.util.Slog;
import android.util.SparseArray;
import android.view.IWindowManager;
import android.view.InsetsSource;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.ImeTracker;
import android.view.inputmethod.InputMethodManagerGlobal;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.rune.CoreRune;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisplayImeController implements DisplayController.OnDisplaysChangedListener {
    public static final Interpolator INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final Executor mMainExecutor;
    public final TransactionPool mTransactionPool;
    public final Lazy mTransitionsLazy;
    public final IWindowManager mWmService;
    public final SparseArray mImePerDisplay = new SparseArray();
    public final ArrayList mPositionProcessors = new ArrayList();
    public Runnable mAnimationFinishedCallback = null;

    public DisplayImeController(IWindowManager iWindowManager, ShellInit shellInit, DisplayController displayController, DisplayInsetsController displayInsetsController, TransactionPool transactionPool, Executor executor, Lazy lazy) {
        this.mWmService = iWindowManager;
        this.mDisplayController = displayController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mMainExecutor = executor;
        this.mTransactionPool = transactionPool;
        this.mTransitionsLazy = lazy;
        shellInit.addInitCallback(new DisplayImeController$$ExternalSyntheticLambda0(this, 0), this);
    }

    public final void addPositionProcessor(ImePositionProcessor imePositionProcessor) {
        synchronized (this.mPositionProcessors) {
            if (this.mPositionProcessors.contains(imePositionProcessor)) {
                return;
            }
            this.mPositionProcessors.add(imePositionProcessor);
        }
    }

    public final boolean isImeShowing(int i) {
        InsetsSource peekSource;
        PerDisplay perDisplay = (PerDisplay) this.mImePerDisplay.get(i);
        return (perDisplay == null || (peekSource = perDisplay.mInsetsState.peekSource(InsetsSource.ID_IME)) == null || perDisplay.mImeSourceControl == null || !peekSource.isVisible()) ? false : true;
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        PerDisplay perDisplay = new PerDisplay(i, this.mDisplayController.getDisplayLayout(i).mRotation);
        DisplayImeController.this.mDisplayInsetsController.addInsetsChangedListener(perDisplay.mDisplayId, perDisplay);
        this.mImePerDisplay.put(i, perDisplay);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        PerDisplay perDisplay = (PerDisplay) this.mImePerDisplay.get(i);
        if (perDisplay == null || this.mDisplayController.getDisplayLayout(i).mRotation == perDisplay.mRotation || !isImeShowing(i)) {
            return;
        }
        perDisplay.startAnimation(true, false, null);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        SparseArray sparseArray = this.mImePerDisplay;
        PerDisplay perDisplay = (PerDisplay) sparseArray.get(i);
        if (perDisplay == null) {
            return;
        }
        DisplayImeController.this.mDisplayInsetsController.removeInsetsChangedListener(perDisplay.mDisplayId, perDisplay);
        sparseArray.remove(i);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PerDisplay implements DisplayInsetsController.OnInsetsChangedListener {
        public final int mDisplayId;
        public final int mRotation;
        public final InsetsState mInsetsState = new InsetsState();
        public int mRequestedVisibleTypes = WindowInsets.Type.defaultVisible();
        public InsetsSourceControl mImeSourceControl = null;
        public int mAnimationDirection = 0;
        public ValueAnimator mAnimation = null;
        public boolean mImeShowing = false;
        public final Rect mImeFrame = new Rect();
        public boolean mAnimateAlpha = true;

        public PerDisplay(int i, int i2) {
            this.mRotation = 0;
            this.mDisplayId = i;
            this.mRotation = i2;
        }

        public InsetsSourceControl getImeSourceControl() {
            return this.mImeSourceControl;
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void hideInsets(int i, ImeTracker.Token token) {
            if ((i & WindowInsets.Type.ime()) == 0) {
                return;
            }
            Slog.d("DisplayImeController", "Got hideInsets for ime");
            startAnimation(false, false, token);
        }

        public final int imeTop(float f) {
            return this.mImeFrame.top + ((int) f);
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            InsetsState insetsState2 = this.mInsetsState;
            if (insetsState2.equals(insetsState)) {
                return;
            }
            updateImeVisibility(insetsState.isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime()));
            InsetsSource peekSource = insetsState.peekSource(InsetsSource.ID_IME);
            Rect frame = peekSource != null ? peekSource.getFrame() : null;
            boolean z = peekSource != null && peekSource.isVisible();
            InsetsSource peekSource2 = insetsState2.peekSource(InsetsSource.ID_IME);
            Rect frame2 = peekSource2 != null ? peekSource2.getFrame() : null;
            insetsState2.set(insetsState, true);
            if (this.mImeShowing && !Objects.equals(frame2, frame) && z) {
                Slog.d("DisplayImeController", "insetsChanged when IME showing, restart animation");
                startAnimation(this.mImeShowing, true, null);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:71:0x00a9  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x00bb  */
        /* JADX WARN: Removed duplicated region for block: B:77:0x00bf  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x00ce  */
        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
            InsetsSourceControl insetsSourceControl;
            boolean z;
            InsetsSourceControl insetsSourceControl2;
            boolean z2;
            SurfaceControl leash;
            insetsChanged(insetsState);
            boolean z3 = false;
            if (insetsSourceControlArr != null) {
                insetsSourceControl = null;
                for (InsetsSourceControl insetsSourceControl3 : insetsSourceControlArr) {
                    if (insetsSourceControl3 != null && insetsSourceControl3.getType() == WindowInsets.Type.ime()) {
                        insetsSourceControl = insetsSourceControl3;
                    }
                }
            } else {
                insetsSourceControl = null;
            }
            final boolean z4 = this.mImeSourceControl != null;
            boolean z5 = insetsSourceControl != null;
            if (z4 != z5) {
                DisplayImeController displayImeController = DisplayImeController.this;
                int i = this.mDisplayId;
                synchronized (displayImeController.mPositionProcessors) {
                    Iterator it = displayImeController.mPositionProcessors.iterator();
                    while (it.hasNext()) {
                        ((ImePositionProcessor) it.next()).onImeControlTargetChanged(i, z5);
                    }
                }
            }
            if (!z5) {
                if (this.mAnimation != null) {
                    z = false;
                    z3 = true;
                }
                z = false;
            } else if (this.mAnimation != null) {
                DisplayImeController displayImeController2 = DisplayImeController.this;
                if (displayImeController2.mAnimationFinishedCallback != null) {
                    displayImeController2.mAnimationFinishedCallback = null;
                }
                z = !insetsSourceControl.getSurfacePosition().equals(z4 ? this.mImeSourceControl.getSurfacePosition() : null);
            } else {
                InsetsSourceControl insetsSourceControl4 = this.mImeSourceControl;
                Interpolator interpolator = DisplayImeController.INTERPOLATOR;
                if (insetsSourceControl4 != insetsSourceControl) {
                    if (insetsSourceControl4 != null && insetsSourceControl != null) {
                        if (insetsSourceControl4.getLeash() != insetsSourceControl.getLeash()) {
                            if (insetsSourceControl4.getLeash() != null && insetsSourceControl.getLeash() != null) {
                                z2 = insetsSourceControl4.getLeash().isSameSurface(insetsSourceControl.getLeash());
                                if (!z2 && (leash = insetsSourceControl.getLeash()) != null) {
                                    DisplayImeController displayImeController3 = DisplayImeController.this;
                                    SurfaceControl.Transaction acquire = displayImeController3.mTransactionPool.acquire();
                                    if (this.mImeShowing) {
                                        acquire.hide(leash);
                                    } else {
                                        acquire.show(leash);
                                    }
                                    acquire.apply();
                                    displayImeController3.mTransactionPool.release(acquire);
                                }
                                if (!this.mImeShowing) {
                                    DisplayImeController.this.getClass();
                                    InputMethodManagerGlobal.removeImeSurface(new DisplayImeController$$ExternalSyntheticLambda1(0));
                                }
                                z = false;
                            }
                        }
                    }
                    z2 = false;
                    if (!z2) {
                        DisplayImeController displayImeController32 = DisplayImeController.this;
                        SurfaceControl.Transaction acquire2 = displayImeController32.mTransactionPool.acquire();
                        if (this.mImeShowing) {
                        }
                        acquire2.apply();
                        displayImeController32.mTransactionPool.release(acquire2);
                    }
                    if (!this.mImeShowing) {
                    }
                    z = false;
                }
                z2 = true;
                if (!z2) {
                }
                if (!this.mImeShowing) {
                }
                z = false;
            }
            if (z3 && insetsSourceControl == null) {
                DisplayImeController.this.mAnimationFinishedCallback = new Runnable() { // from class: com.android.wm.shell.common.DisplayImeController$PerDisplay$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        InsetsSourceControl insetsSourceControl5;
                        DisplayImeController.PerDisplay perDisplay = DisplayImeController.PerDisplay.this;
                        if (z4 && (insetsSourceControl5 = perDisplay.mImeSourceControl) != null) {
                            insetsSourceControl5.release(new DisplayImeController$$ExternalSyntheticLambda1(2));
                        }
                        perDisplay.mImeSourceControl = null;
                    }
                };
                return;
            }
            if (z4 && (insetsSourceControl2 = this.mImeSourceControl) != insetsSourceControl) {
                insetsSourceControl2.release(new DisplayImeController$$ExternalSyntheticLambda1(1));
            }
            this.mImeSourceControl = insetsSourceControl;
            if (z) {
                startAnimation(this.mImeShowing, true, null);
            }
        }

        public final void setVisibleDirectly(boolean z) {
            this.mInsetsState.setSourceVisible(InsetsSource.ID_IME, z);
            int ime = z ? this.mRequestedVisibleTypes | WindowInsets.Type.ime() : this.mRequestedVisibleTypes & (~WindowInsets.Type.ime());
            this.mRequestedVisibleTypes = ime;
            try {
                DisplayImeController.this.mWmService.updateDisplayWindowRequestedVisibleTypes(this.mDisplayId, ime);
            } catch (RemoteException unused) {
            }
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void showInsets(int i, ImeTracker.Token token) {
            if ((i & WindowInsets.Type.ime()) == 0) {
                return;
            }
            Slog.d("DisplayImeController", "Got showInsets for ime");
            startAnimation(true, false, token);
        }

        public final void startAnimation(boolean z, boolean z2, ImeTracker.Token token) {
            boolean z3;
            InsetsSource peekSource = this.mInsetsState.peekSource(InsetsSource.ID_IME);
            if (peekSource == null || this.mImeSourceControl == null) {
                ImeTracker.forLogging().onFailed(token, 26);
                return;
            }
            Rect frame = peekSource.getFrame();
            Rect frame2 = peekSource.getFrame();
            int height = frame2.height();
            int i = this.mDisplayId;
            DisplayImeController displayImeController = DisplayImeController.this;
            boolean z4 = (height == 0 || frame2.height() <= displayImeController.mDisplayController.getDisplayLayout(i).mNavBarFrameHeight) && z;
            Rect rect = this.mImeFrame;
            if (z4) {
                rect.set(frame);
                rect.bottom -= (int) (displayImeController.mDisplayController.getDisplayLayout(i).density() * (-80.0f));
            } else if (frame.height() != 0) {
                rect.set(frame);
            }
            StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("Run startAnim  show:", z, "  was:");
            int i2 = this.mAnimationDirection;
            m49m.append(i2 == 1 ? "SHOW" : i2 == 2 ? "HIDE" : PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE);
            Slog.d("DisplayImeController", m49m.toString());
            if ((!z2 && this.mAnimationDirection == 1 && z) || (this.mAnimationDirection == 2 && !z)) {
                ImeTracker.forLogging().onCancelled(token, 26);
                return;
            }
            ValueAnimator valueAnimator = this.mAnimation;
            float f = 0.0f;
            if (valueAnimator != null) {
                if (valueAnimator.isRunning()) {
                    f = ((Float) this.mAnimation.getAnimatedValue()).floatValue();
                    z3 = true;
                } else {
                    z3 = false;
                }
                this.mAnimation.cancel();
                if (CoreRune.MW_SHELL_TRANSITION) {
                    this.mAnimationDirection = 0;
                }
            } else {
                z3 = false;
            }
            final float f2 = this.mImeSourceControl.getSurfacePosition().y;
            final float f3 = this.mImeSourceControl.getSurfacePosition().x;
            final float height2 = f2 + rect.height();
            float f4 = z ? height2 : f2;
            float f5 = z ? f2 : height2;
            if (this.mAnimationDirection == 0 && this.mImeShowing && z) {
                z3 = true;
                f = f2;
            }
            this.mAnimationDirection = z ? 1 : 2;
            updateImeVisibility(z);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(f4, f5);
            this.mAnimation = ofFloat;
            ofFloat.setDuration(z ? 275L : 340L);
            if (z3) {
                this.mAnimation.setCurrentFraction((f - f4) / (f5 - f4));
            }
            final boolean z5 = z4;
            this.mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.DisplayImeController$PerDisplay$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    DisplayImeController.PerDisplay perDisplay = DisplayImeController.PerDisplay.this;
                    float f6 = f3;
                    boolean z6 = z5;
                    float f7 = height2;
                    float f8 = f2;
                    SurfaceControl.Transaction acquire = DisplayImeController.this.mTransactionPool.acquire();
                    float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    acquire.setPosition(perDisplay.mImeSourceControl.getLeash(), f6, floatValue);
                    acquire.setAlpha(perDisplay.mImeSourceControl.getLeash(), (perDisplay.mAnimateAlpha || z6) ? (floatValue - f7) / (f8 - f7) : 1.0f);
                    DisplayImeController displayImeController2 = DisplayImeController.this;
                    int i3 = perDisplay.mDisplayId;
                    int imeTop = perDisplay.imeTop(floatValue);
                    synchronized (displayImeController2.mPositionProcessors) {
                        Iterator it = displayImeController2.mPositionProcessors.iterator();
                        while (it.hasNext()) {
                            ((DisplayImeController.ImePositionProcessor) it.next()).onImePositionChanged(i3, imeTop);
                        }
                    }
                    acquire.apply();
                    DisplayImeController.this.mTransactionPool.release(acquire);
                }
            });
            this.mAnimation.setInterpolator(DisplayImeController.INTERPOLATOR);
            ImeTracker.forLogging().onProgress(token, 26);
            this.mAnimation.addListener(new AnimatorListenerAdapter(token, f3, f4, height2, f2, z4, f5) { // from class: com.android.wm.shell.common.DisplayImeController.PerDisplay.1
                public boolean mCancelled = false;
                public final ImeTracker.Token mStatsToken;
                public final /* synthetic */ float val$endY;
                public final /* synthetic */ float val$hiddenY;
                public final /* synthetic */ boolean val$isFloating;
                public final /* synthetic */ float val$shownY;
                public final /* synthetic */ ImeTracker.Token val$statsToken;
                public final /* synthetic */ float val$x;

                {
                    this.val$statsToken = token;
                    this.val$x = f3;
                    this.val$hiddenY = height2;
                    this.val$shownY = f2;
                    this.val$isFloating = z4;
                    this.val$endY = f5;
                    this.mStatsToken = token;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                    if (ImeTracker.DEBUG_IME_VISIBILITY) {
                        Object[] objArr = new Object[3];
                        ImeTracker.Token token2 = this.val$statsToken;
                        objArr[0] = token2 != null ? token2.getTag() : "TOKEN_NONE";
                        objArr[1] = Integer.valueOf(PerDisplay.this.mDisplayId);
                        objArr[2] = Objects.toString(PerDisplay.this.mImeSourceControl.getInsetsHint());
                        EventLog.writeEvent(32011, objArr);
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    Slog.d("DisplayImeController", "onAnimationEnd " + this.mCancelled);
                    SurfaceControl.Transaction acquire = DisplayImeController.this.mTransactionPool.acquire();
                    if (!this.mCancelled) {
                        acquire.setPosition(PerDisplay.this.mImeSourceControl.getLeash(), this.val$x, this.val$endY);
                        acquire.setAlpha(PerDisplay.this.mImeSourceControl.getLeash(), 1.0f);
                    }
                    PerDisplay perDisplay = PerDisplay.this;
                    DisplayImeController displayImeController2 = DisplayImeController.this;
                    int i3 = perDisplay.mDisplayId;
                    boolean z6 = this.mCancelled;
                    synchronized (displayImeController2.mPositionProcessors) {
                        Iterator it = displayImeController2.mPositionProcessors.iterator();
                        while (it.hasNext()) {
                            ((ImePositionProcessor) it.next()).onImeEndPositioning(i3, z6);
                        }
                    }
                    int i4 = PerDisplay.this.mAnimationDirection;
                    if (i4 == 2 && !this.mCancelled) {
                        ImeTracker.forLogging().onProgress(this.mStatsToken, 27);
                        acquire.hide(PerDisplay.this.mImeSourceControl.getLeash());
                        DisplayImeController.this.getClass();
                        InputMethodManagerGlobal.removeImeSurface(new DisplayImeController$$ExternalSyntheticLambda1(0));
                        ImeTracker.forLogging().onHidden(this.mStatsToken);
                    } else if (i4 == 1 && !this.mCancelled) {
                        ImeTracker.forLogging().onShown(this.mStatsToken);
                    } else if (this.mCancelled) {
                        ImeTracker.forLogging().onCancelled(this.mStatsToken, 27);
                    }
                    if (ImeTracker.DEBUG_IME_VISIBILITY) {
                        Object[] objArr = new Object[8];
                        ImeTracker.Token token2 = this.val$statsToken;
                        objArr[0] = token2 != null ? token2.getTag() : "TOKEN_NONE";
                        objArr[1] = Integer.valueOf(PerDisplay.this.mDisplayId);
                        objArr[2] = Integer.valueOf(PerDisplay.this.mAnimationDirection);
                        objArr[3] = Float.valueOf(this.val$endY);
                        objArr[4] = Objects.toString(PerDisplay.this.mImeSourceControl.getLeash());
                        objArr[5] = Objects.toString(PerDisplay.this.mImeSourceControl.getInsetsHint());
                        objArr[6] = Objects.toString(PerDisplay.this.mImeSourceControl.getSurfacePosition());
                        objArr[7] = Objects.toString(PerDisplay.this.mImeFrame);
                        EventLog.writeEvent(32010, objArr);
                    }
                    acquire.apply();
                    DisplayImeController.this.mTransactionPool.release(acquire);
                    PerDisplay perDisplay2 = PerDisplay.this;
                    perDisplay2.mAnimationDirection = 0;
                    perDisplay2.mAnimation = null;
                    Runnable runnable = DisplayImeController.this.mAnimationFinishedCallback;
                    if (runnable == null || this.mCancelled) {
                        return;
                    }
                    runnable.run();
                    DisplayImeController.this.mAnimationFinishedCallback = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    ArrayList arrayList;
                    float f6;
                    float floatValue = ((Float) PerDisplay.this.mAnimation.getAnimatedValue()).floatValue();
                    SurfaceControl.Transaction acquire = DisplayImeController.this.mTransactionPool.acquire();
                    acquire.setPosition(PerDisplay.this.mImeSourceControl.getLeash(), this.val$x, floatValue);
                    StringBuilder sb = new StringBuilder("onAnimationStart d:");
                    sb.append(PerDisplay.this.mDisplayId);
                    sb.append(" top:");
                    sb.append(PerDisplay.this.imeTop(this.val$hiddenY));
                    sb.append("->");
                    sb.append(PerDisplay.this.imeTop(this.val$shownY));
                    sb.append(" showing:");
                    sb.append(PerDisplay.this.mAnimationDirection == 1);
                    Slog.d("DisplayImeController", sb.toString());
                    PerDisplay perDisplay = PerDisplay.this;
                    DisplayImeController displayImeController2 = DisplayImeController.this;
                    int i3 = perDisplay.mDisplayId;
                    int imeTop = perDisplay.imeTop(this.val$hiddenY);
                    int imeTop2 = PerDisplay.this.imeTop(this.val$shownY);
                    boolean z6 = PerDisplay.this.mAnimationDirection == 1;
                    boolean z7 = this.val$isFloating;
                    ArrayList arrayList2 = displayImeController2.mPositionProcessors;
                    synchronized (arrayList2) {
                        try {
                            Iterator it = displayImeController2.mPositionProcessors.iterator();
                            int i4 = 0;
                            while (it.hasNext()) {
                                arrayList = arrayList2;
                                boolean z8 = z7;
                                try {
                                    i4 |= ((ImePositionProcessor) it.next()).onImeStartPositioning(i3, imeTop, imeTop2, z6, z7);
                                    arrayList2 = arrayList;
                                    z7 = z8;
                                } catch (Throwable th) {
                                    th = th;
                                    while (true) {
                                        try {
                                            throw th;
                                        } catch (Throwable th2) {
                                            th = th2;
                                        }
                                    }
                                }
                            }
                            arrayList = arrayList2;
                            PerDisplay perDisplay2 = PerDisplay.this;
                            boolean z9 = (i4 & 1) == 0;
                            perDisplay2.mAnimateAlpha = z9;
                            if (z9 || this.val$isFloating) {
                                float f7 = this.val$hiddenY;
                                f6 = (floatValue - f7) / (this.val$shownY - f7);
                            } else {
                                f6 = 1.0f;
                            }
                            acquire.setAlpha(perDisplay2.mImeSourceControl.getLeash(), f6);
                            if (PerDisplay.this.mAnimationDirection == 1) {
                                ImeTracker.forLogging().onProgress(this.mStatsToken, 27);
                                acquire.show(PerDisplay.this.mImeSourceControl.getLeash());
                            }
                            if (ImeTracker.DEBUG_IME_VISIBILITY) {
                                Object[] objArr = new Object[10];
                                ImeTracker.Token token2 = this.val$statsToken;
                                objArr[0] = token2 != null ? token2.getTag() : "TOKEN_NONE";
                                objArr[1] = Integer.valueOf(PerDisplay.this.mDisplayId);
                                objArr[2] = Integer.valueOf(PerDisplay.this.mAnimationDirection);
                                objArr[3] = Float.valueOf(f6);
                                objArr[4] = Float.valueOf(floatValue);
                                objArr[5] = Float.valueOf(this.val$endY);
                                objArr[6] = Objects.toString(PerDisplay.this.mImeSourceControl.getLeash());
                                objArr[7] = Objects.toString(PerDisplay.this.mImeSourceControl.getInsetsHint());
                                objArr[8] = Objects.toString(PerDisplay.this.mImeSourceControl.getSurfacePosition());
                                objArr[9] = Objects.toString(PerDisplay.this.mImeFrame);
                                EventLog.writeEvent(32009, objArr);
                            }
                            acquire.apply();
                            DisplayImeController.this.mTransactionPool.release(acquire);
                        } catch (Throwable th3) {
                            th = th3;
                            arrayList = arrayList2;
                        }
                    }
                }
            });
            if (!z) {
                setVisibleDirectly(false);
            }
            if (CoreRune.MW_SHELL_TRANSITION && z) {
                Transitions transitions = (Transitions) displayImeController.mTransitionsLazy.get();
                DisplayImeController$$ExternalSyntheticLambda0 displayImeController$$ExternalSyntheticLambda0 = new DisplayImeController$$ExternalSyntheticLambda0(this, 1);
                ArrayList arrayList = transitions.mPendingTransitions;
                if ((arrayList.isEmpty() || Transitions.isEmptyExceptZombie(arrayList)) && !transitions.isAnimating()) {
                    displayImeController$$ExternalSyntheticLambda0.run();
                } else {
                    transitions.mRunWhenIdleQueue.add(displayImeController$$ExternalSyntheticLambda0);
                }
            } else {
                this.mAnimation.start();
            }
            if (z) {
                setVisibleDirectly(true);
            }
        }

        public final void updateImeVisibility(boolean z) {
            if (this.mImeShowing != z) {
                this.mImeShowing = z;
                DisplayImeController displayImeController = DisplayImeController.this;
                int i = this.mDisplayId;
                synchronized (displayImeController.mPositionProcessors) {
                    Iterator it = displayImeController.mPositionProcessors.iterator();
                    while (it.hasNext()) {
                        ((ImePositionProcessor) it.next()).onImeVisibilityChanged(i, z);
                    }
                }
            }
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void topFocusedWindowChanged() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ImePositionProcessor {
        default int onImeStartPositioning(int i, int i2, int i3, boolean z, boolean z2) {
            return 0;
        }

        default void onImeControlTargetChanged(int i, boolean z) {
        }

        default void onImeEndPositioning(int i, boolean z) {
        }

        default void onImePositionChanged(int i, int i2) {
        }

        default void onImeVisibilityChanged(int i, boolean z) {
        }
    }
}
