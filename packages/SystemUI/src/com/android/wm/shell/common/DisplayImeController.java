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
    public static boolean m3235$$Nest$smhaveSameLeash(InsetsSourceControl insetsSourceControl, InsetsSourceControl insetsSourceControl2) {
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
        InsetsSource insetsSourcePeekSource;
        PerDisplay perDisplay = (PerDisplay) this.mImePerDisplay.get(i);
        return (perDisplay == null || (insetsSourcePeekSource = perDisplay.mInsetsState.peekSource(InsetsSource.ID_IME)) == null || perDisplay.mImeSourceControl == null || !insetsSourcePeekSource.isVisible()) ? false : true;
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
            InsetsSource insetsSourcePeekSource = insetsState.peekSource(i);
            Rect frame = insetsSourcePeekSource != null ? insetsSourcePeekSource.getFrame() : null;
            boolean z = insetsSourcePeekSource != null && insetsSourcePeekSource.isVisible();
            InsetsSource insetsSourcePeekSource2 = this.mInsetsState.peekSource(i);
            Rect frame2 = insetsSourcePeekSource2 != null ? insetsSourcePeekSource2.getFrame() : null;
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
                    if (!DisplayImeController.m3235$$Nest$smhaveSameLeash(this.mImeSourceControl, insetsSourceControl)) {
                        Slog.d("DisplayImeController", "insetsControlChanged: leash changed during animation.");
                        z2 = true;
                    }
                    z = false;
                } else {
                    if (DisplayImeController.m3235$$Nest$smhaveSameLeash(this.mImeSourceControl, insetsSourceControl)) {
                        z = false;
                    } else {
                        boolean zIsInitiallyVisible = insetsSourceControl.isInitiallyVisible();
                        if (this.mImeShowing != zIsInitiallyVisible) {
                            this.mImeShowing = zIsInitiallyVisible;
                        }
                        SurfaceControl leash = insetsSourceControl.getLeash();
                        if (leash != null) {
                            DisplayImeController displayImeController2 = DisplayImeController.this;
                            SurfaceControl.Transaction transactionAcquire = displayImeController2.mTransactionPool.acquire();
                            if (this.mImeShowing) {
                                transactionAcquire.show(leash);
                            } else {
                                transactionAcquire.hide(leash);
                            }
                            transactionAcquire.apply();
                            displayImeController2.mTransactionPool.release(transactionAcquire);
                        }
                        z = true;
                    }
                    Point surfacePosition = z4 ? this.mImeSourceControl.getSurfacePosition() : null;
                    if (this.mImeShowing && surfacePosition != null) {
                        boolean zEquals = insetsSourceControl.getSurfacePosition().equals(surfacePosition);
                        z2 = !zEquals;
                        if (!zEquals) {
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

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void setImeInputTargetRequestedVisibility(boolean z, ImeTracker.Token token) {
            boolean z2;
            ImeTracker.forLogging().onProgress(token, 68);
            this.mImeRequestedVisible = z;
            DisplayImeController displayImeController = DisplayImeController.this;
            int i = this.mDisplayId;
            synchronized (displayImeController.mPositionProcessors) {
                try {
                    ArrayList arrayList = displayImeController.mPositionProcessors;
                    int size = arrayList.size();
                    z2 = false;
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((ImePositionProcessor) obj).onImeRequested(i, z);
                    }
                } finally {
                }
            }
            if (z || this.mImeShowing) {
                startAnimation(this.mImeRequestedVisible, false, token);
            }
            boolean z3 = this.mImeRequestedVisible;
            boolean z4 = true;
            boolean z5 = (z3 || this.mAnimation == null) ? false : true;
            if (!z3 && this.mAnimation == null) {
                z4 = false;
            }
            if (!z5 || !z4) {
                z2 = z4;
                break;
            }
            DisplayImeController displayImeController2 = DisplayImeController.this;
            synchronized (displayImeController2.mPositionProcessors) {
                try {
                    ArrayList arrayList2 = displayImeController2.mPositionProcessors;
                    int size2 = arrayList2.size();
                    int i3 = 0;
                    while (i3 < size2) {
                        Object obj2 = arrayList2.get(i3);
                        i3++;
                        if (((ImePositionProcessor) obj2).hasSplitImeFocus()) {
                            z2 = z4;
                            break;
                        }
                    }
                } finally {
                }
            }
            if (z5) {
                token = null;
            }
            setVisibleDirectly(z2, token);
        }

        public final void setVisibleDirectly(boolean z, ImeTracker.Token token) {
            this.mInsetsState.setSourceVisible(InsetsSource.ID_IME, z);
            int iIme = z ? WindowInsets.Type.ime() : 0;
            try {
                Slog.d("DisplayImeController", "setVisibleDirectly: " + z + ", t=" + token + "");
                DisplayImeController.this.mWmService.updateDisplayWindowRequestedVisibleTypes(this.mDisplayId, iIme, WindowInsets.Type.ime(), token);
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
            ImeTracker.Token tokenOnStart;
            if (this.mInsetsState.peekSource(InsetsSource.ID_IME) == null || (insetsSourceControl = this.mImeSourceControl) == null) {
                return;
            }
            if (insetsSourceControl.getImeStatsToken() != null) {
                tokenOnStart = this.mImeSourceControl.getImeStatsToken();
            } else {
                tokenOnStart = ImeTracker.forLogging().onStart(z ? 1 : 2, 8, i, false);
            }
            startAnimation(z, z2, tokenOnStart);
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
            float fFloatValue;
            boolean z3;
            InsetsSourceControl insetsSourceControl = this.mImeSourceControl;
            if (insetsSourceControl != null && insetsSourceControl.getLeash() != null) {
                if (!this.mImeRequestedVisible && z) {
                    Slog.e("DisplayImeController", "IME was not requested visible, not starting the show animation.");
                    return;
                }
                InsetsSource insetsSourcePeekSource = this.mInsetsState.peekSource(InsetsSource.ID_IME);
                if (insetsSourcePeekSource == null) {
                    ImeTracker.forLogging().onFailed(token, 26);
                    return;
                }
                Rect frame = insetsSourcePeekSource.getFrame();
                Rect frame2 = insetsSourcePeekSource.getFrame();
                int iHeight = frame2.height();
                int i = this.mDisplayId;
                DisplayImeController displayImeController = DisplayImeController.this;
                boolean z4 = (iHeight == 0 || frame2.height() <= displayImeController.mDisplayController.getDisplayLayout(i).mNavBarFrameHeight) && z;
                if (z4) {
                    this.mImeFrame.set(frame);
                    this.mImeFrame.bottom -= (int) (displayImeController.mDisplayController.getDisplayLayout(i).density() * (-80.0f));
                } else if (frame.height() != 0) {
                    this.mImeFrame.set(frame);
                }
                StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("Run startAnim  show:", "  was:", z);
                int i2 = this.mAnimationDirection;
                if (i2 == 1) {
                    str = "SHOW";
                } else {
                    str = i2 == 2 ? "HIDE" : PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE;
                }
                sbM.append(str);
                Slog.d("DisplayImeController", sbM.toString());
                if ((!z2 && this.mAnimationDirection == 1 && z) || (this.mAnimationDirection == 2 && !z)) {
                    ImeTracker.forLogging().onCancelled(token, 26);
                    return;
                }
                ValueAnimator valueAnimator = this.mAnimation;
                if (valueAnimator != null) {
                    if (valueAnimator.isRunning()) {
                        fFloatValue = ((Float) this.mAnimation.getAnimatedValue()).floatValue();
                        z3 = true;
                    } else {
                        fFloatValue = 0.0f;
                        z3 = false;
                    }
                    this.mAnimation.cancel();
                    this.mAnimationDirection = 0;
                } else {
                    fFloatValue = 0.0f;
                    z3 = false;
                }
                InsetsSourceControl insetsSourceControl2 = new InsetsSourceControl(this.mImeSourceControl);
                final boolean z5 = z4;
                final SurfaceControl leash = insetsSourceControl2.getLeash();
                final float f = insetsSourceControl2.getSurfacePosition().y;
                final float f2 = insetsSourceControl2.getSurfacePosition().x;
                final float fHeight = this.mImeFrame.height() + f;
                float f3 = z ? fHeight : f;
                float f4 = z ? f : fHeight;
                if (this.mAnimationDirection == 0 && this.mImeShowing && z) {
                    fFloatValue = f;
                    z3 = true;
                }
                this.mAnimationDirection = z ? 1 : 2;
                if (this.mImeShowing != z) {
                    this.mImeShowing = z;
                }
                updateImeVisibilityForDispatch(z);
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f3, f4);
                this.mAnimation = valueAnimatorOfFloat;
                valueAnimatorOfFloat.setDuration(z ? 275L : 340L);
                if (z3) {
                    this.mAnimation.setCurrentFraction((fFloatValue - f3) / (f4 - f3));
                } else {
                    this.mAnimation.setCurrentFraction(0.0f);
                }
                this.mAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.DisplayImeController$PerDisplay$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        DisplayImeController.PerDisplay perDisplay = this.f$0;
                        SurfaceControl surfaceControl = leash;
                        float f5 = f2;
                        boolean z6 = z5;
                        float f6 = fHeight;
                        float f7 = f;
                        float f8 = f;
                        SurfaceControl.Transaction transactionAcquire = DisplayImeController.this.mTransactionPool.acquire();
                        float fFloatValue2 = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        transactionAcquire.setPosition(surfaceControl, f5, fFloatValue2);
                        transactionAcquire.setAlpha(surfaceControl, (perDisplay.mAnimateAlpha || z6) ? (fFloatValue2 - f6) / (f7 - f6) : 1.0f);
                        DisplayImeController displayImeController2 = DisplayImeController.this;
                        int i3 = perDisplay.mDisplayId;
                        int iImeTop = perDisplay.imeTop(fFloatValue2, f8);
                        synchronized (displayImeController2.mPositionProcessors) {
                            try {
                                ArrayList arrayList = displayImeController2.mPositionProcessors;
                                int size = arrayList.size();
                                int i4 = 0;
                                while (i4 < size) {
                                    Object obj = arrayList.get(i4);
                                    i4++;
                                    ((DisplayImeController.ImePositionProcessor) obj).onImePositionChanged(i3, iImeTop, transactionAcquire);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        transactionAcquire.apply();
                        DisplayImeController.this.mTransactionPool.release(transactionAcquire);
                    }
                });
                this.mAnimation.setInterpolator(DisplayImeController.INTERPOLATOR);
                ImeTracker.forLogging().onProgress(token, 26);
                this.mAnimation.addListener(new AnimatorListenerAdapter(token, leash, f2, fHeight, f, f, z5, f4, insetsSourceControl2) { // from class: com.android.wm.shell.common.DisplayImeController.PerDisplay.1
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
                        this.val$x = f2;
                        this.val$hiddenY = fHeight;
                        this.val$defaultY = f;
                        this.val$shownY = f;
                        this.val$isFloating = z5;
                        this.val$endY = f4;
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
                        SurfaceControl.Transaction transactionAcquire = DisplayImeController.this.mTransactionPool.acquire();
                        if (!this.mCancelled) {
                            transactionAcquire.setPosition(this.val$animatingLeash, this.val$x, this.val$endY);
                            transactionAcquire.setAlpha(this.val$animatingLeash, 1.0f);
                        }
                        int i3 = PerDisplay.this.mAnimationDirection;
                        if (i3 == 2 && !this.mCancelled) {
                            ImeTracker.forLogging().onProgress(this.mStatsToken, 27);
                            transactionAcquire.hide(this.val$animatingLeash);
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
                                    ((ImePositionProcessor) obj).onImeEndPositioning(i4, z6, transactionAcquire);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        if (ImeTracker.DEBUG_IME_VISIBILITY) {
                            ImeTracker.Token token2 = this.mStatsToken;
                            EventLog.writeEvent(32010, token2 != null ? token2.getTag() : "TOKEN_NONE", Integer.valueOf(PerDisplay.this.mDisplayId), Integer.valueOf(PerDisplay.this.mAnimationDirection), Float.valueOf(this.val$endY), Objects.toString(this.val$animatingLeash), Objects.toString(this.val$animatingControl.getInsetsHint()), Objects.toString(this.val$animatingControl.getSurfacePosition()), Objects.toString(PerDisplay.this.mImeFrame));
                        }
                        transactionAcquire.apply();
                        DisplayImeController.this.mTransactionPool.release(transactionAcquire);
                        PerDisplay perDisplay2 = PerDisplay.this;
                        perDisplay2.mAnimationDirection = 0;
                        perDisplay2.mAnimation = null;
                        this.val$animatingControl.release(new DisplayImeController$PerDisplay$$ExternalSyntheticLambda0());
                        DisplayImeController.this.getClass();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                        int iOnImeStartPositioning;
                        float f5;
                        Float f6 = (Float) ((ValueAnimator) animator).getAnimatedValue();
                        float fFloatValue2 = f6.floatValue();
                        SurfaceControl.Transaction transactionAcquire = DisplayImeController.this.mTransactionPool.acquire();
                        transactionAcquire.setPosition(this.val$animatingLeash, this.val$x, fFloatValue2);
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
                        int i3 = perDisplay.mDisplayId;
                        int iImeTop = perDisplay.imeTop(this.val$hiddenY, this.val$defaultY);
                        int iImeTop2 = PerDisplay.this.imeTop(this.val$shownY, this.val$defaultY);
                        boolean z6 = PerDisplay.this.mAnimationDirection == 1;
                        boolean z7 = this.val$isFloating;
                        synchronized (displayImeController2.mPositionProcessors) {
                            try {
                                ArrayList arrayList = displayImeController2.mPositionProcessors;
                                int size = arrayList.size();
                                iOnImeStartPositioning = 0;
                                int i4 = 0;
                                while (i4 < size) {
                                    Object obj = arrayList.get(i4);
                                    i4++;
                                    boolean z8 = z7;
                                    iOnImeStartPositioning |= ((ImePositionProcessor) obj).onImeStartPositioning(i3, iImeTop, z6, z8, iImeTop2);
                                    z7 = z8;
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        PerDisplay perDisplay2 = PerDisplay.this;
                        boolean z9 = (iOnImeStartPositioning & 1) == 0;
                        perDisplay2.mAnimateAlpha = z9;
                        if (z9 || this.val$isFloating) {
                            float f7 = this.val$hiddenY;
                            f5 = (fFloatValue2 - f7) / (this.val$shownY - f7);
                        } else {
                            f5 = 1.0f;
                        }
                        transactionAcquire.setAlpha(this.val$animatingLeash, f5);
                        if (PerDisplay.this.mAnimationDirection == 1) {
                            ImeTracker.forLogging().onProgress(this.mStatsToken, 27);
                            transactionAcquire.show(this.val$animatingLeash);
                        }
                        if (ImeTracker.DEBUG_IME_VISIBILITY) {
                            ImeTracker.Token token2 = this.mStatsToken;
                            EventLog.writeEvent(32009, token2 != null ? token2.getTag() : "TOKEN_NONE", Integer.valueOf(PerDisplay.this.mDisplayId), Integer.valueOf(PerDisplay.this.mAnimationDirection), Float.valueOf(f5), f6, Float.valueOf(this.val$endY), Objects.toString(this.val$animatingLeash), Objects.toString(this.val$animatingControl.getInsetsHint()), Objects.toString(this.val$animatingControl.getSurfacePosition()), Objects.toString(PerDisplay.this.mImeFrame));
                        }
                        transactionAcquire.apply();
                        DisplayImeController.this.mTransactionPool.release(transactionAcquire);
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
