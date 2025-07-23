package com.android.wm.shell.common;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.util.EventLog;
import android.util.Log;
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
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda9;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DisplayImeController implements DisplayController.OnDisplaysChangedListener {
    public static final Interpolator INTERPOLATOR = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final Executor mMainExecutor;
    public final TransactionPool mTransactionPool;
    public final Lazy mTransitionsLazy;
    public final IWindowManager mWmService;
    public final SparseArray mImePerDisplay = new SparseArray();
    public final ArrayList mPositionProcessors = new ArrayList();

    /* renamed from: -$$Nest$smhaveSameLeash, reason: not valid java name */
    public static boolean m3219$$Nest$smhaveSameLeash(InsetsSourceControl insetsSourceControl, InsetsSourceControl insetsSourceControl2) {
        if (insetsSourceControl == insetsSourceControl2) {
            return true;
        }
        if (insetsSourceControl == null || insetsSourceControl2 == null) {
            return false;
        }
        if (insetsSourceControl.getLeash() == insetsSourceControl2.getLeash()) {
            return true;
        }
        if (insetsSourceControl.getLeash() == null || insetsSourceControl2.getLeash() == null) {
            return false;
        }
        return insetsSourceControl.getLeash().isSameSurface(insetsSourceControl2.getLeash());
    }

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
            try {
                if (this.mPositionProcessors.contains(imePositionProcessor)) {
                    return;
                }
                this.mPositionProcessors.add(imePositionProcessor);
            } catch (Throwable th) {
                throw th;
            }
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
        perDisplay.startAnimation(47, true, false);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        PerDisplay perDisplay = (PerDisplay) this.mImePerDisplay.get(i);
        if (perDisplay == null) {
            return;
        }
        DisplayImeController.this.mDisplayInsetsController.removeInsetsChangedListener(perDisplay.mDisplayId, perDisplay);
        this.mImePerDisplay.remove(i);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PerDisplay implements DisplayInsetsController.OnInsetsChangedListener {
        public boolean mAnimateAlpha;
        public ValueAnimator mAnimation;
        public int mAnimationDirection;
        public final int mDisplayId;
        public boolean mHasImeLeash;
        public final Rect mImeFrame;
        public boolean mImeRequestedVisible;
        public boolean mImeShowing;
        public boolean mImeShowingState;
        public InsetsSourceControl mImeSourceControl;
        public final InsetsState mInsetsState = new InsetsState();
        public final int mRotation;

        public PerDisplay(int i, int i2) {
            this.mImeRequestedVisible = (WindowInsets.Type.defaultVisible() & WindowInsets.Type.ime()) != 0;
            this.mImeSourceControl = null;
            this.mAnimationDirection = 0;
            this.mAnimation = null;
            this.mRotation = 0;
            this.mImeShowing = false;
            this.mImeFrame = new Rect();
            this.mAnimateAlpha = true;
            this.mImeShowingState = false;
            this.mHasImeLeash = false;
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

        public final int imeTop(float f, float f2) {
            return this.mImeFrame.top + ((int) (f - f2));
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            if (this.mInsetsState.equals(insetsState)) {
                return;
            }
            int i = InsetsSource.ID_IME;
            updateImeVisibilityForDispatch(insetsState.isSourceOrDefaultVisible(i, WindowInsets.Type.ime()));
            InsetsSource peekSource = insetsState.peekSource(i);
            Rect frame = peekSource != null ? peekSource.getFrame() : null;
            boolean z = peekSource != null && peekSource.isVisible();
            InsetsSource peekSource2 = this.mInsetsState.peekSource(i);
            Rect frame2 = peekSource2 != null ? peekSource2.getFrame() : null;
            this.mInsetsState.set(insetsState, true);
            if (this.mImeShowing && !Objects.equals(frame2, frame) && z) {
                Slog.d("DisplayImeController", "insetsChanged when IME showing, restart animation");
                startAnimation(48, this.mImeShowing, true);
            }
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
            InsetsSourceControl insetsSourceControl;
            boolean z;
            InsetsSourceControl insetsSourceControl2;
            InsetsSourceControl insetsSourceControl3;
            ValueAnimator valueAnimator;
            boolean z2;
            insetsChanged(insetsState);
            boolean z3 = false;
            if (insetsSourceControlArr != null) {
                insetsSourceControl = null;
                for (InsetsSourceControl insetsSourceControl4 : insetsSourceControlArr) {
                    if (insetsSourceControl4 != null && insetsSourceControl4.getType() == WindowInsets.Type.ime()) {
                        insetsSourceControl = insetsSourceControl4;
                    }
                }
            } else {
                insetsSourceControl = null;
            }
            boolean z4 = this.mImeSourceControl != null;
            boolean z5 = insetsSourceControl != null;
            if (z4 != z5) {
                DisplayImeController displayImeController = DisplayImeController.this;
                int i = this.mDisplayId;
                synchronized (displayImeController.mPositionProcessors) {
                    try {
                        ArrayList arrayList = displayImeController.mPositionProcessors;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            ((ImePositionProcessor) obj).onImeControlTargetChanged(i, z5);
                        }
                    } finally {
                    }
                }
            }
            boolean z6 = z5 && insetsSourceControl.getLeash() != null;
            this.mHasImeLeash = z6;
            if (z6) {
                if (this.mAnimation != null) {
                    DisplayImeController.this.getClass();
                    z2 = !insetsSourceControl.getSurfacePosition().equals(z4 ? this.mImeSourceControl.getSurfacePosition() : null);
                    if (!DisplayImeController.m3219$$Nest$smhaveSameLeash(this.mImeSourceControl, insetsSourceControl)) {
                        Slog.d("DisplayImeController", "insetsControlChanged: leash changed during animation.");
                        z2 = true;
                    }
                    z = false;
                } else {
                    if (DisplayImeController.m3219$$Nest$smhaveSameLeash(this.mImeSourceControl, insetsSourceControl)) {
                        z = false;
                    } else {
                        boolean isInitiallyVisible = insetsSourceControl.isInitiallyVisible();
                        if (this.mImeShowing != isInitiallyVisible) {
                            this.mImeShowing = isInitiallyVisible;
                        }
                        SurfaceControl leash = insetsSourceControl.getLeash();
                        if (leash != null) {
                            DisplayImeController displayImeController2 = DisplayImeController.this;
                            SurfaceControl.Transaction acquire = displayImeController2.mTransactionPool.acquire();
                            if (this.mImeShowing) {
                                acquire.show(leash);
                            } else {
                                acquire.hide(leash);
                            }
                            acquire.apply();
                            displayImeController2.mTransactionPool.release(acquire);
                        }
                        z = true;
                    }
                    Point surfacePosition = z4 ? this.mImeSourceControl.getSurfacePosition() : null;
                    if (this.mImeShowing && surfacePosition != null) {
                        boolean equals = insetsSourceControl.getSurfacePosition().equals(surfacePosition);
                        z2 = !equals;
                        if (!equals) {
                            Slog.d("DisplayImeController", "insetsControlChanged: no anim but positionChanged.");
                        }
                    }
                }
                z3 = z2;
            } else {
                boolean z7 = this.mImeShowing;
                if (z7 && this.mAnimation == null) {
                    if (z7) {
                        this.mImeShowing = false;
                    }
                    updateImeVisibilityForDispatch(false);
                } else if (z7 && this.mAnimation == null && z7) {
                    this.mImeShowing = false;
                }
                z = false;
            }
            if (z4 && (insetsSourceControl3 = this.mImeSourceControl) != insetsSourceControl) {
                insetsSourceControl3.release(new DisplayImeController$PerDisplay$$ExternalSyntheticLambda0());
                if (!z6 && (valueAnimator = this.mAnimation) != null) {
                    valueAnimator.cancel();
                }
            }
            this.mImeSourceControl = insetsSourceControl;
            if (z3) {
                z = true;
            }
            this.mImeSourceControl = insetsSourceControl;
            if (z) {
                boolean z8 = this.mImeRequestedVisible;
                if (this.mInsetsState.peekSource(InsetsSource.ID_IME) == null || (insetsSourceControl2 = this.mImeSourceControl) == null) {
                    return;
                }
                startAnimation(z8, true, insetsSourceControl2.getImeStatsToken());
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x0075  */
        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void setImeInputTargetRequestedVisibility(boolean r8, android.view.inputmethod.ImeTracker.Token r9) {
            /*
                r7 = this;
                android.view.inputmethod.ImeTracker r0 = android.view.inputmethod.ImeTracker.forLogging()
                r1 = 68
                r0.onProgress(r9, r1)
                r7.mImeRequestedVisible = r8
                com.android.wm.shell.common.DisplayImeController r0 = com.android.wm.shell.common.DisplayImeController.this
                int r1 = r7.mDisplayId
                java.util.ArrayList r2 = r0.mPositionProcessors
                monitor-enter(r2)
                java.util.ArrayList r0 = r0.mPositionProcessors     // Catch: java.lang.Throwable -> L28
                int r3 = r0.size()     // Catch: java.lang.Throwable -> L28
                r4 = 0
                r5 = r4
            L1a:
                if (r5 >= r3) goto L2a
                java.lang.Object r6 = r0.get(r5)     // Catch: java.lang.Throwable -> L28
                int r5 = r5 + 1
                com.android.wm.shell.common.DisplayImeController$ImePositionProcessor r6 = (com.android.wm.shell.common.DisplayImeController.ImePositionProcessor) r6     // Catch: java.lang.Throwable -> L28
                r6.onImeRequested(r1, r8)     // Catch: java.lang.Throwable -> L28
                goto L1a
            L28:
                r7 = move-exception
                goto L7a
            L2a:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L28
                if (r8 != 0) goto L31
                boolean r8 = r7.mImeShowing
                if (r8 == 0) goto L36
            L31:
                boolean r8 = r7.mImeRequestedVisible
                r7.startAnimation(r8, r4, r9)
            L36:
                boolean r8 = r7.mImeRequestedVisible
                r0 = 1
                if (r8 != 0) goto L41
                android.animation.ValueAnimator r1 = r7.mAnimation
                if (r1 == 0) goto L41
                r1 = r0
                goto L42
            L41:
                r1 = r4
            L42:
                if (r8 != 0) goto L4a
                android.animation.ValueAnimator r8 = r7.mAnimation
                if (r8 == 0) goto L49
                goto L4a
            L49:
                r0 = r4
            L4a:
                if (r1 == 0) goto L72
                if (r0 == 0) goto L72
                com.android.wm.shell.common.DisplayImeController r8 = com.android.wm.shell.common.DisplayImeController.this
                java.util.ArrayList r2 = r8.mPositionProcessors
                monitor-enter(r2)
                java.util.ArrayList r8 = r8.mPositionProcessors     // Catch: java.lang.Throwable -> L6c
                int r3 = r8.size()     // Catch: java.lang.Throwable -> L6c
                r5 = r4
            L5a:
                if (r5 >= r3) goto L6e
                java.lang.Object r6 = r8.get(r5)     // Catch: java.lang.Throwable -> L6c
                int r5 = r5 + 1
                com.android.wm.shell.common.DisplayImeController$ImePositionProcessor r6 = (com.android.wm.shell.common.DisplayImeController.ImePositionProcessor) r6     // Catch: java.lang.Throwable -> L6c
                boolean r6 = r6.hasSplitImeFocus()     // Catch: java.lang.Throwable -> L6c
                if (r6 == 0) goto L5a
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L6c
                goto L72
            L6c:
                r7 = move-exception
                goto L70
            L6e:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L6c
                goto L73
            L70:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L6c
                throw r7
            L72:
                r4 = r0
            L73:
                if (r1 == 0) goto L76
                r9 = 0
            L76:
                r7.setVisibleDirectly(r4, r9)
                return
            L7a:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L28
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.DisplayImeController.PerDisplay.setImeInputTargetRequestedVisibility(boolean, android.view.inputmethod.ImeTracker$Token):void");
        }

        public final void setVisibleDirectly(boolean z, ImeTracker.Token token) {
            this.mInsetsState.setSourceVisible(InsetsSource.ID_IME, z);
            int ime = z ? WindowInsets.Type.ime() : 0;
            try {
                Slog.d("DisplayImeController", "setVisibleDirectly: " + z + ", t=" + token + "");
                DisplayImeController.this.mWmService.updateDisplayWindowRequestedVisibleTypes(this.mDisplayId, ime, WindowInsets.Type.ime(), token);
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

        public final void startAnimation(int i, boolean z, boolean z2) {
            InsetsSourceControl insetsSourceControl;
            ImeTracker.Token onStart;
            if (this.mInsetsState.peekSource(InsetsSource.ID_IME) == null || (insetsSourceControl = this.mImeSourceControl) == null) {
                return;
            }
            if (insetsSourceControl.getImeStatsToken() != null) {
                onStart = this.mImeSourceControl.getImeStatsToken();
            } else {
                onStart = ImeTracker.forLogging().onStart(z ? 1 : 2, 8, i, false);
            }
            startAnimation(z, z2, onStart);
        }

        public final void updateImeVisibilityForDispatch(boolean z) {
            if (this.mImeShowingState != z) {
                this.mImeShowingState = z;
                DisplayImeController displayImeController = DisplayImeController.this;
                int i = this.mDisplayId;
                synchronized (displayImeController.mPositionProcessors) {
                    try {
                        ArrayList arrayList = displayImeController.mPositionProcessors;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            ((ImePositionProcessor) obj).onImeVisibilityChanged(i, z);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void startAnimation(boolean z, boolean z2, ImeTracker.Token token) {
            String str;
            float f;
            boolean z3;
            InsetsSourceControl insetsSourceControl = this.mImeSourceControl;
            if (insetsSourceControl != null && insetsSourceControl.getLeash() != null) {
                if (!this.mImeRequestedVisible && z) {
                    Slog.e("DisplayImeController", "IME was not requested visible, not starting the show animation.");
                    return;
                }
                InsetsSource peekSource = this.mInsetsState.peekSource(InsetsSource.ID_IME);
                if (peekSource == null) {
                    ImeTracker.forLogging().onFailed(token, 26);
                    return;
                }
                Rect frame = peekSource.getFrame();
                Rect frame2 = peekSource.getFrame();
                int height = frame2.height();
                int i = this.mDisplayId;
                DisplayImeController displayImeController = DisplayImeController.this;
                boolean z4 = (height == 0 || frame2.height() <= displayImeController.mDisplayController.getDisplayLayout(i).mNavBarFrameHeight) && z;
                if (z4) {
                    this.mImeFrame.set(frame);
                    this.mImeFrame.bottom -= (int) (displayImeController.mDisplayController.getDisplayLayout(i).density() * (-80.0f));
                } else if (frame.height() != 0) {
                    this.mImeFrame.set(frame);
                }
                StringBuilder m = RowView$$ExternalSyntheticOutline0.m("Run startAnim  show:", "  was:", z);
                int i2 = this.mAnimationDirection;
                if (i2 == 1) {
                    str = "SHOW";
                } else {
                    str = i2 == 2 ? "HIDE" : PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE;
                }
                m.append(str);
                Slog.d("DisplayImeController", m.toString());
                if ((!z2 && this.mAnimationDirection == 1 && z) || (this.mAnimationDirection == 2 && !z)) {
                    ImeTracker.forLogging().onCancelled(token, 26);
                    return;
                }
                ValueAnimator valueAnimator = this.mAnimation;
                if (valueAnimator != null) {
                    if (valueAnimator.isRunning()) {
                        f = ((Float) this.mAnimation.getAnimatedValue()).floatValue();
                        z3 = true;
                    } else {
                        f = 0.0f;
                        z3 = false;
                    }
                    this.mAnimation.cancel();
                    this.mAnimationDirection = 0;
                } else {
                    f = 0.0f;
                    z3 = false;
                }
                InsetsSourceControl insetsSourceControl2 = new InsetsSourceControl(this.mImeSourceControl);
                final boolean z5 = z4;
                final SurfaceControl leash = insetsSourceControl2.getLeash();
                final float f2 = insetsSourceControl2.getSurfacePosition().y;
                final float f3 = insetsSourceControl2.getSurfacePosition().x;
                final float height2 = this.mImeFrame.height() + f2;
                float f4 = z ? height2 : f2;
                float f5 = z ? f2 : height2;
                if (this.mAnimationDirection == 0 && this.mImeShowing && z) {
                    f = f2;
                    z3 = true;
                }
                this.mAnimationDirection = z ? 1 : 2;
                if (this.mImeShowing != z) {
                    this.mImeShowing = z;
                }
                updateImeVisibilityForDispatch(z);
                ValueAnimator ofFloat = ValueAnimator.ofFloat(f4, f5);
                this.mAnimation = ofFloat;
                ofFloat.setDuration(z ? 275L : 340L);
                if (z3) {
                    this.mAnimation.setCurrentFraction((f - f4) / (f5 - f4));
                } else {
                    this.mAnimation.setCurrentFraction(0.0f);
                }
                this.mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.DisplayImeController$PerDisplay$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DisplayImeController.PerDisplay perDisplay = DisplayImeController.PerDisplay.this;
                        SurfaceControl surfaceControl = leash;
                        float f6 = f3;
                        boolean z6 = z5;
                        float f7 = height2;
                        float f8 = f2;
                        float f9 = f2;
                        SurfaceControl.Transaction acquire = DisplayImeController.this.mTransactionPool.acquire();
                        float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        acquire.setPosition(surfaceControl, f6, floatValue);
                        acquire.setAlpha(surfaceControl, (perDisplay.mAnimateAlpha || z6) ? (floatValue - f7) / (f8 - f7) : 1.0f);
                        DisplayImeController displayImeController2 = DisplayImeController.this;
                        int i3 = perDisplay.mDisplayId;
                        int imeTop = perDisplay.imeTop(floatValue, f9);
                        synchronized (displayImeController2.mPositionProcessors) {
                            try {
                                ArrayList arrayList = displayImeController2.mPositionProcessors;
                                int size = arrayList.size();
                                int i4 = 0;
                                while (i4 < size) {
                                    Object obj = arrayList.get(i4);
                                    i4++;
                                    ((DisplayImeController.ImePositionProcessor) obj).onImePositionChanged(i3, imeTop, acquire);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        acquire.apply();
                        DisplayImeController.this.mTransactionPool.release(acquire);
                    }
                });
                this.mAnimation.setInterpolator(DisplayImeController.INTERPOLATOR);
                ImeTracker.forLogging().onProgress(token, 26);
                this.mAnimation.addListener(new AnimatorListenerAdapter(token, leash, f3, height2, f2, f2, z5, f5, insetsSourceControl2) { // from class: com.android.wm.shell.common.DisplayImeController.PerDisplay.1
                    public boolean mCancelled = false;
                    public final ImeTracker.Token mStatsToken;
                    public final /* synthetic */ InsetsSourceControl val$animatingControl;
                    public final /* synthetic */ SurfaceControl val$animatingLeash;
                    public final /* synthetic */ float val$defaultY;
                    public final /* synthetic */ float val$endY;
                    public final /* synthetic */ float val$hiddenY;
                    public final /* synthetic */ boolean val$isFloating;
                    public final /* synthetic */ float val$shownY;
                    public final /* synthetic */ ImeTracker.Token val$statsToken;
                    public final /* synthetic */ float val$x;

                    {
                        this.val$statsToken = token;
                        this.val$animatingLeash = leash;
                        this.val$x = f3;
                        this.val$hiddenY = height2;
                        this.val$defaultY = f2;
                        this.val$shownY = f2;
                        this.val$isFloating = z5;
                        this.val$endY = f5;
                        this.val$animatingControl = insetsSourceControl2;
                        this.mStatsToken = token;
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                        this.mCancelled = true;
                        if (ImeTracker.DEBUG_IME_VISIBILITY) {
                            ImeTracker.Token token2 = this.mStatsToken;
                            EventLog.writeEvent(32011, token2 != null ? token2.getTag() : "TOKEN_NONE", Integer.valueOf(PerDisplay.this.mDisplayId), Objects.toString(this.val$animatingControl.getInsetsHint()));
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        Slog.d("DisplayImeController", "onAnimationEnd " + this.mCancelled);
                        SurfaceControl.Transaction acquire = DisplayImeController.this.mTransactionPool.acquire();
                        if (!this.mCancelled) {
                            acquire.setPosition(this.val$animatingLeash, this.val$x, this.val$endY);
                            acquire.setAlpha(this.val$animatingLeash, 1.0f);
                        }
                        int i3 = PerDisplay.this.mAnimationDirection;
                        if (i3 == 2 && !this.mCancelled) {
                            ImeTracker.forLogging().onProgress(this.mStatsToken, 27);
                            acquire.hide(this.val$animatingLeash);
                            PerDisplay.this.setVisibleDirectly(false, this.val$statsToken);
                        } else if (i3 == 1 && !this.mCancelled) {
                            ImeTracker.forLogging().onShown(this.mStatsToken);
                        } else if (this.mCancelled) {
                            ImeTracker.forLogging().onCancelled(this.mStatsToken, 27);
                        }
                        PerDisplay perDisplay = PerDisplay.this;
                        DisplayImeController displayImeController2 = DisplayImeController.this;
                        int i4 = perDisplay.mDisplayId;
                        boolean z6 = this.mCancelled;
                        synchronized (displayImeController2.mPositionProcessors) {
                            try {
                                ArrayList arrayList = displayImeController2.mPositionProcessors;
                                int size = arrayList.size();
                                int i5 = 0;
                                while (i5 < size) {
                                    Object obj = arrayList.get(i5);
                                    i5++;
                                    ((ImePositionProcessor) obj).onImeEndPositioning(i4, z6, acquire);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (ImeTracker.DEBUG_IME_VISIBILITY) {
                            ImeTracker.Token token2 = this.mStatsToken;
                            EventLog.writeEvent(32010, token2 != null ? token2.getTag() : "TOKEN_NONE", Integer.valueOf(PerDisplay.this.mDisplayId), Integer.valueOf(PerDisplay.this.mAnimationDirection), Float.valueOf(this.val$endY), Objects.toString(this.val$animatingLeash), Objects.toString(this.val$animatingControl.getInsetsHint()), Objects.toString(this.val$animatingControl.getSurfacePosition()), Objects.toString(PerDisplay.this.mImeFrame));
                        }
                        acquire.apply();
                        DisplayImeController.this.mTransactionPool.release(acquire);
                        PerDisplay perDisplay2 = PerDisplay.this;
                        perDisplay2.mAnimationDirection = 0;
                        perDisplay2.mAnimation = null;
                        this.val$animatingControl.release(new DisplayImeController$PerDisplay$$ExternalSyntheticLambda0());
                        DisplayImeController.this.getClass();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        int i3;
                        float f6;
                        Float f7 = (Float) ((ValueAnimator) animator).getAnimatedValue();
                        float floatValue = f7.floatValue();
                        SurfaceControl.Transaction acquire = DisplayImeController.this.mTransactionPool.acquire();
                        acquire.setPosition(this.val$animatingLeash, this.val$x, floatValue);
                        StringBuilder sb = new StringBuilder("onAnimationStart d:");
                        sb.append(PerDisplay.this.mDisplayId);
                        sb.append(" top:");
                        sb.append(PerDisplay.this.imeTop(this.val$hiddenY, this.val$defaultY));
                        sb.append("->");
                        sb.append(PerDisplay.this.imeTop(this.val$shownY, this.val$defaultY));
                        sb.append(" showing:");
                        sb.append(PerDisplay.this.mAnimationDirection == 1);
                        Slog.d("DisplayImeController", sb.toString());
                        PerDisplay perDisplay = PerDisplay.this;
                        DisplayImeController displayImeController2 = DisplayImeController.this;
                        int i4 = perDisplay.mDisplayId;
                        int imeTop = perDisplay.imeTop(this.val$hiddenY, this.val$defaultY);
                        int imeTop2 = PerDisplay.this.imeTop(this.val$shownY, this.val$defaultY);
                        boolean z6 = PerDisplay.this.mAnimationDirection == 1;
                        boolean z7 = this.val$isFloating;
                        synchronized (displayImeController2.mPositionProcessors) {
                            try {
                                ArrayList arrayList = displayImeController2.mPositionProcessors;
                                int size = arrayList.size();
                                i3 = 0;
                                int i5 = 0;
                                while (i5 < size) {
                                    Object obj = arrayList.get(i5);
                                    i5++;
                                    boolean z8 = z7;
                                    i3 |= ((ImePositionProcessor) obj).onImeStartPositioning(i4, imeTop, z6, z8, imeTop2);
                                    z7 = z8;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        PerDisplay perDisplay2 = PerDisplay.this;
                        boolean z9 = (i3 & 1) == 0;
                        perDisplay2.mAnimateAlpha = z9;
                        if (z9 || this.val$isFloating) {
                            float f8 = this.val$hiddenY;
                            f6 = (floatValue - f8) / (this.val$shownY - f8);
                        } else {
                            f6 = 1.0f;
                        }
                        acquire.setAlpha(this.val$animatingLeash, f6);
                        if (PerDisplay.this.mAnimationDirection == 1) {
                            ImeTracker.forLogging().onProgress(this.mStatsToken, 27);
                            acquire.show(this.val$animatingLeash);
                        }
                        if (ImeTracker.DEBUG_IME_VISIBILITY) {
                            ImeTracker.Token token2 = this.mStatsToken;
                            EventLog.writeEvent(32009, token2 != null ? token2.getTag() : "TOKEN_NONE", Integer.valueOf(PerDisplay.this.mDisplayId), Integer.valueOf(PerDisplay.this.mAnimationDirection), Float.valueOf(f6), f7, Float.valueOf(this.val$endY), Objects.toString(this.val$animatingLeash), Objects.toString(this.val$animatingControl.getInsetsHint()), Objects.toString(this.val$animatingControl.getSurfacePosition()), Objects.toString(PerDisplay.this.mImeFrame));
                        }
                        acquire.apply();
                        DisplayImeController.this.mTransactionPool.release(acquire);
                    }
                });
                if (z) {
                    Transitions transitions = (Transitions) displayImeController.mTransitionsLazy.get();
                    DisplayImeController$$ExternalSyntheticLambda0 displayImeController$$ExternalSyntheticLambda0 = new DisplayImeController$$ExternalSyntheticLambda0(this, 1);
                    ArrayList arrayList = transitions.mPendingTransitions;
                    int size = arrayList.size();
                    ArrayList arrayList2 = null;
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        Transitions.ActiveTransition activeTransition = (Transitions.ActiveTransition) obj;
                        if (activeTransition.isTimeout()) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            arrayList2.add(activeTransition);
                        }
                    }
                    if (arrayList2 != null) {
                        Log.e("ShellTransitions", "runOnIdleSafely: execute immediately, reason=timeout");
                        arrayList2.forEach(new Transitions$$ExternalSyntheticLambda9());
                        displayImeController$$ExternalSyntheticLambda0.run();
                        return;
                    }
                    transitions.runOnIdle(displayImeController$$ExternalSyntheticLambda0);
                    return;
                }
                this.mAnimation.start();
                return;
            }
            Slog.d("DisplayImeController", "No leash available, not starting the animation.");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ImePositionProcessor {
        default boolean hasSplitImeFocus() {
            return false;
        }

        default int onImeStartPositioning(int i, int i2, boolean z, boolean z2, int i3) {
            return 0;
        }

        default void onImeControlTargetChanged(int i, boolean z) {
        }

        default void onImeRequested(int i, boolean z) {
        }

        default void onImeVisibilityChanged(int i, boolean z) {
        }

        default void onImeEndPositioning(int i, boolean z, SurfaceControl.Transaction transaction) {
        }

        default void onImePositionChanged(int i, int i2, SurfaceControl.Transaction transaction) {
        }
    }
}
