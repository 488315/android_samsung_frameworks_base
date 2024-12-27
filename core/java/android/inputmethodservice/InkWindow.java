package android.inputmethodservice;

import android.content.Context;
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
        WindowManager.LayoutParams attrs = getAttributes();
        if (CoreRune.DIRECT_WRITING) {
            attrs.setTitle("InkWindow");
        }
        attrs.layoutInDisplayCutoutMode = 3;
        attrs.setFitInsetsTypes(0);
        attrs.windowAnimations = -1;
        setAttributes(attrs);
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

    private void show(boolean keepInvisible) {
        if (getDecorView() == null) {
            Slog.i("InputMethodService", "DecorView is not set for InkWindow. show() failed.");
            return;
        }
        getDecorView().setVisibility(keepInvisible ? 4 : 0);
        if (!this.mIsViewAdded) {
            this.mWindowManager.addView(getDecorView(), getAttributes());
            this.mIsViewAdded = true;
        }
    }

    void hide(boolean remove) {
        if (getDecorView() != null) {
            if (remove) {
                this.mWindowManager.removeViewImmediate(getDecorView());
            } else {
                getDecorView().setVisibility(4);
            }
        }
    }

    void setToken(IBinder token) {
        WindowManager.LayoutParams lp = getAttributes();
        lp.token = token;
        setAttributes(lp);
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        if (this.mInkView == null) {
            this.mInkView = view;
        } else if (this.mInkView != view) {
            throw new IllegalStateException("Only one Child Inking view is permitted.");
        }
        super.addContentView(view, params);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        this.mInkView = view;
        super.setContentView(view, params);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void setContentView(View view) {
        this.mInkView = view;
        super.setContentView(view);
        initInkViewVisibilityListener();
    }

    @Override // com.android.internal.policy.PhoneWindow, android.view.Window
    public void clearContentView() {
        if (this.mGlobalLayoutListener != null && this.mInkView != null) {
            this.mInkView.getViewTreeObserver().removeOnGlobalLayoutListener(this.mGlobalLayoutListener);
        }
        this.mGlobalLayoutListener = null;
        this.mInkView = null;
        super.clearContentView();
    }

    void setInkViewVisibilityListener(InkVisibilityListener listener) {
        this.mInkViewVisibilityListener = listener;
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
        return getDecorView().getVisibility() == 0 && this.mInkView != null && this.mInkView.isVisibleToUser();
    }

    void dispatchHandwritingEvent(MotionEvent event) {
        View decor = getDecorView();
        Objects.requireNonNull(decor);
        ViewRootImpl viewRoot = decor.getViewRootImpl();
        Objects.requireNonNull(viewRoot);
        viewRoot.enqueueInputEvent(MotionEvent.obtain(event));
    }
}
