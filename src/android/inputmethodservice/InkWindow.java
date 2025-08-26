package android.inputmethodservice;

import android.content.Context;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import com.android.internal.policy.PhoneWindow;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* loaded from: classes2.dex */
final class InkWindow extends PhoneWindow {
    private ViewTreeObserver.OnGlobalLayoutListener mGlobalLayoutListener;
    private View mInkView;
    private InkVisibilityListener mInkViewVisibilityListener;
    private boolean mIsViewAdded;
    private final WindowManager mWindowManager;

    interface InkVisibilityListener {
        void onInkViewVisible();
    }

    public InkWindow(Context context) {
        super(context);
        setType(2011);
        WindowManager.LayoutParams attributes = getAttributes();
        if (CoreRune.DIRECT_WRITING) {
            attributes.setTitle("InkWindow");
        }
        attributes.layoutInDisplayCutoutMode = 3;
        attributes.setFitInsetsTypes(0);
        attributes.windowAnimations = -1;
        setAttributes(attributes);
        addFlags(792);
        setBackgroundDrawableResource(17170445);
        setLayout(-1, -1);
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        if (CoreRune.DIRECT_WRITING) {
            setDecorFitsSystemWindows(false);
        }
    }

    void initOnly() {
        show(true);
    }

    void show() {
        show(false);
    }

    private void show(boolean z) {
        if (getDecorView() == null) {
            Slog.i("InputMethodService", "DecorView is not set for InkWindow. show() failed.");
            return;
        }
        getDecorView().setVisibility(z ? 4 : 0);
        if (this.mIsViewAdded) {
            return;
        }
        this.mWindowManager.addView(getDecorView(), getAttributes());
        this.mIsViewAdded = true;
    }

    void hide(boolean z) {
        if (getDecorView() != null) {
            if (z) {
                this.mWindowManager.removeViewImmediate(getDecorView());
            } else {
                getDecorView().setVisibility(4);
            }
        }
    }

    void setToken(IBinder iBinder) {
        WindowManager.LayoutParams attributes = getAttributes();
        attributes.token = iBinder;
        setAttributes(attributes);
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        View view2 = this.mInkView;
        if (view2 == null) {
            this.mInkView = view;
        } else if (view2 != view) {
            throw new IllegalStateException("Only one Child Inking view is permitted.");
        }
        super.addContentView(view, layoutParams);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) throws Resources.NotFoundException {
        this.mInkView = view;
        super.setContentView(view, layoutParams);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void setContentView(View view) throws Resources.NotFoundException {
        this.mInkView = view;
        super.setContentView(view);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void clearContentView() {
        View view;
        if (this.mGlobalLayoutListener != null && (view = this.mInkView) != null) {
            view.getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        this.mGlobalLayoutListener = null;
        this.mInkView = null;
        super.clearContentView();
    }

    void setInkViewVisibilityListener(InkVisibilityListener inkVisibilityListener) {
        this.mInkViewVisibilityListener = inkVisibilityListener;
        initInkViewVisibilityListener();
    }

    void initInkViewVisibilityListener() {
        if (this.mInkView == null || this.mInkViewVisibilityListener == null || this.mGlobalLayoutListener != null) {
            return;
        }
        this.mGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: android.inputmethodservice.InkWindow.1
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                if (InkWindow.this.mInkView != null && InkWindow.this.mInkView.isVisibleToUser()) {
                    if (InkWindow.this.mInkViewVisibilityListener != null) {
                        InkWindow.this.mInkViewVisibilityListener.onInkViewVisible();
                    }
                    InkWindow.this.mInkView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    InkWindow.this.mGlobalLayoutListener = null;
                }
            }
        };
        this.mInkView.getViewTreeObserver().addOnGlobalLayoutListener(this.mGlobalLayoutListener);
    }

    boolean isInkViewVisible() {
        View view;
        return getDecorView().getVisibility() == 0 && (view = this.mInkView) != null && view.isVisibleToUser();
    }

    void dispatchHandwritingEvent(MotionEvent motionEvent) {
        View decorView = getDecorView();
        Objects.requireNonNull(decorView);
        ViewRootImpl viewRootImpl = decorView.getViewRootImpl();
        Objects.requireNonNull(viewRootImpl);
        viewRootImpl.enqueueInputEvent(MotionEvent.obtain(motionEvent));
    }
}
