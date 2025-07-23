package android.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import com.android.internal.R;

@Deprecated
/* loaded from: classes5.dex */
public class ZoomButtonsController implements View.OnTouchListener {
    private static final int MSG_DISMISS_ZOOM_CONTROLS = 3;
    private static final int MSG_POST_CONFIGURATION_CHANGED = 2;
    private static final int MSG_POST_SET_VISIBLE = 4;
    private static final String TAG = "ZoomButtonsController";
    private static final int ZOOM_CONTROLS_TIMEOUT = (int) ViewConfiguration.getZoomControlsTimeout();
    private static final int ZOOM_CONTROLS_TOUCH_PADDING = 20;
    private OnZoomListener mCallback;
    private final FrameLayout mContainer;
    private WindowManager.LayoutParams mContainerLayoutParams;
    private final Context mContext;
    private ZoomControls mControls;
    private boolean mIsVisible;
    private final View mOwnerView;
    private Runnable mPostedVisibleInitializer;
    private boolean mReleaseTouchListenerOnUp;
    private int mTouchPaddingScaledSq;
    private View mTouchTargetView;
    private final WindowManager mWindowManager;
    private boolean mAutoDismissControls = true;
    private final int[] mOwnerViewRawLocation = new int[2];
    private final int[] mContainerRawLocation = new int[2];
    private final int[] mTouchTargetWindowLocation = new int[2];
    private final Rect mTempRect = new Rect();
    private final int[] mTempIntArray = new int[2];
    private final IntentFilter mConfigurationChangedFilter = new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED);
    private final BroadcastReceiver mConfigurationChangedReceiver = new BroadcastReceiver() { // from class: android.widget.ZoomButtonsController.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (ZoomButtonsController.this.mIsVisible) {
                ZoomButtonsController.this.mHandler.removeMessages(2);
                ZoomButtonsController.this.mHandler.sendEmptyMessage(2);
            }
        }
    };
    private final Handler mHandler = new Handler() { // from class: android.widget.ZoomButtonsController.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 2) {
                ZoomButtonsController.this.onPostConfigurationChanged();
                return;
            }
            if (i == 3) {
                ZoomButtonsController.this.setVisible(false);
            } else {
                if (i != 4) {
                    return;
                }
                if (ZoomButtonsController.this.mOwnerView.getWindowToken() == null) {
                    Log.e(ZoomButtonsController.TAG, "Cannot make the zoom controller visible if the owner view is not attached to a window.");
                } else {
                    ZoomButtonsController.this.setVisible(true);
                }
            }
        }
    };

    public interface OnZoomListener {
        void onVisibilityChanged(boolean z);

        void onZoom(boolean z);
    }

    private boolean isInterestingKey(int i) {
        if (i == 4 || i == 66) {
            return true;
        }
        switch (i) {
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                return true;
            default:
                return false;
        }
    }

    public ZoomButtonsController(View view) {
        Context context = view.getContext();
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mOwnerView = view;
        int i = (int) (context.getResources().getDisplayMetrics().density * 20.0f);
        this.mTouchPaddingScaledSq = i * i;
        this.mContainer = createContainer();
    }

    public void setZoomInEnabled(boolean z) {
        this.mControls.setIsZoomInEnabled(z);
    }

    public void setZoomOutEnabled(boolean z) {
        this.mControls.setIsZoomOutEnabled(z);
    }

    public void setZoomSpeed(long j) {
        this.mControls.setZoomSpeed(j);
    }

    private FrameLayout createContainer() {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2);
        layoutParams.gravity = 8388659;
        layoutParams.flags = 131608;
        layoutParams.height = -2;
        layoutParams.width = -1;
        layoutParams.type = 1000;
        layoutParams.format = -3;
        layoutParams.windowAnimations = R.style.Animation_ZoomButtons;
        this.mContainerLayoutParams = layoutParams;
        Container container = new Container(this.mContext);
        container.setLayoutParams(layoutParams);
        container.setMeasureAllChildren(true);
        ((LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_container, container);
        ZoomControls zoomControls = (ZoomControls) container.findViewById(R.id.zoomControls);
        this.mControls = zoomControls;
        zoomControls.setOnZoomInClickListener(new View.OnClickListener() { // from class: android.widget.ZoomButtonsController.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ZoomButtonsController.this.dismissControlsDelayed(ZoomButtonsController.ZOOM_CONTROLS_TIMEOUT);
                if (ZoomButtonsController.this.mCallback != null) {
                    ZoomButtonsController.this.mCallback.onZoom(true);
                }
            }
        });
        this.mControls.setOnZoomOutClickListener(new View.OnClickListener() { // from class: android.widget.ZoomButtonsController.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ZoomButtonsController.this.dismissControlsDelayed(ZoomButtonsController.ZOOM_CONTROLS_TIMEOUT);
                if (ZoomButtonsController.this.mCallback != null) {
                    ZoomButtonsController.this.mCallback.onZoom(false);
                }
            }
        });
        return container;
    }

    public void setOnZoomListener(OnZoomListener onZoomListener) {
        this.mCallback = onZoomListener;
    }

    public void setFocusable(boolean z) {
        int i = this.mContainerLayoutParams.flags;
        if (z) {
            this.mContainerLayoutParams.flags &= -9;
        } else {
            this.mContainerLayoutParams.flags |= 8;
        }
        if (this.mContainerLayoutParams.flags == i || !this.mIsVisible) {
            return;
        }
        this.mWindowManager.updateViewLayout(this.mContainer, this.mContainerLayoutParams);
    }

    public boolean isAutoDismissed() {
        return this.mAutoDismissControls;
    }

    public void setAutoDismissed(boolean z) {
        if (this.mAutoDismissControls == z) {
            return;
        }
        this.mAutoDismissControls = z;
    }

    public boolean isVisible() {
        return this.mIsVisible;
    }

    public void setVisible(boolean z) {
        if (z) {
            if (this.mOwnerView.getWindowToken() == null) {
                if (this.mHandler.hasMessages(4)) {
                    return;
                }
                this.mHandler.sendEmptyMessage(4);
                return;
            }
            dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        }
        if (this.mIsVisible == z) {
            return;
        }
        this.mIsVisible = z;
        if (z) {
            if (this.mContainerLayoutParams.token == null) {
                this.mContainerLayoutParams.token = this.mOwnerView.getWindowToken();
            }
            this.mWindowManager.addView(this.mContainer, this.mContainerLayoutParams);
            if (this.mPostedVisibleInitializer == null) {
                this.mPostedVisibleInitializer = new Runnable() { // from class: android.widget.ZoomButtonsController.5
                    @Override // java.lang.Runnable
                    public void run() {
                        ZoomButtonsController.this.refreshPositioningVariables();
                        if (ZoomButtonsController.this.mCallback != null) {
                            ZoomButtonsController.this.mCallback.onVisibilityChanged(true);
                        }
                    }
                };
            }
            this.mHandler.post(this.mPostedVisibleInitializer);
            this.mContext.registerReceiver(this.mConfigurationChangedReceiver, this.mConfigurationChangedFilter);
            this.mOwnerView.setOnTouchListener(this);
            this.mReleaseTouchListenerOnUp = false;
            return;
        }
        if (this.mTouchTargetView != null) {
            this.mReleaseTouchListenerOnUp = true;
        } else {
            this.mOwnerView.setOnTouchListener(null);
        }
        this.mContext.unregisterReceiver(this.mConfigurationChangedReceiver);
        this.mWindowManager.removeViewImmediate(this.mContainer);
        this.mHandler.removeCallbacks(this.mPostedVisibleInitializer);
        OnZoomListener onZoomListener = this.mCallback;
        if (onZoomListener != null) {
            onZoomListener.onVisibilityChanged(false);
        }
    }

    public ViewGroup getContainer() {
        return this.mContainer;
    }

    public View getZoomControls() {
        return this.mControls;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissControlsDelayed(int i) {
        if (this.mAutoDismissControls) {
            this.mHandler.removeMessages(3);
            this.mHandler.sendEmptyMessageDelayed(3, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshPositioningVariables() {
        if (this.mOwnerView.getWindowToken() == null) {
            return;
        }
        int height = this.mOwnerView.getHeight();
        int width = this.mOwnerView.getWidth();
        int height2 = height - this.mContainer.getHeight();
        this.mOwnerView.getLocationOnScreen(this.mOwnerViewRawLocation);
        int[] iArr = this.mContainerRawLocation;
        int[] iArr2 = this.mOwnerViewRawLocation;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1] + height2;
        int[] iArr3 = this.mTempIntArray;
        this.mOwnerView.getLocationInWindow(iArr3);
        this.mContainerLayoutParams.x = iArr3[0];
        this.mContainerLayoutParams.width = width;
        this.mContainerLayoutParams.y = iArr3[1] + height2;
        if (this.mIsVisible) {
            this.mWindowManager.updateViewLayout(this.mContainer, this.mContainerLayoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onContainerKey(KeyEvent keyEvent) {
        KeyEvent.DispatcherState keyDispatcherState;
        int keyCode = keyEvent.getKeyCode();
        if (!isInterestingKey(keyCode)) {
            ViewRootImpl viewRootImpl = this.mOwnerView.getViewRootImpl();
            if (viewRootImpl != null) {
                viewRootImpl.dispatchInputEvent(keyEvent);
            }
            return true;
        }
        if (keyCode == 4) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                View view = this.mOwnerView;
                if (view != null && (keyDispatcherState = view.getKeyDispatcherState()) != null) {
                    keyDispatcherState.startTracking(keyEvent, this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1 && keyEvent.isTracking() && !keyEvent.isCanceled()) {
                setVisible(false);
                return true;
            }
        } else {
            dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        }
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (motionEvent.getPointerCount() > 1) {
            return false;
        }
        if (this.mReleaseTouchListenerOnUp) {
            if (action == 1 || action == 3) {
                this.mOwnerView.setOnTouchListener(null);
                setTouchTargetView(null);
                this.mReleaseTouchListenerOnUp = false;
            }
            return true;
        }
        dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        View view2 = this.mTouchTargetView;
        if (action == 0) {
            view2 = findViewForTouch((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
            setTouchTargetView(view2);
        } else if (action == 1 || action == 3) {
            setTouchTargetView(null);
        }
        if (view2 == null) {
            return false;
        }
        int[] iArr = this.mContainerRawLocation;
        int i = iArr[0];
        int[] iArr2 = this.mTouchTargetWindowLocation;
        int i2 = i + iArr2[0];
        int i3 = iArr[1] + iArr2[1];
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int[] iArr3 = this.mOwnerViewRawLocation;
        obtain.offsetLocation(iArr3[0] - i2, iArr3[1] - i3);
        float x = obtain.getX();
        float y = obtain.getY();
        if (x < 0.0f && x > -20.0f) {
            obtain.offsetLocation(-x, 0.0f);
        }
        if (y < 0.0f && y > -20.0f) {
            obtain.offsetLocation(0.0f, -y);
        }
        boolean dispatchTouchEvent = view2.dispatchTouchEvent(obtain);
        obtain.recycle();
        return dispatchTouchEvent;
    }

    private void setTouchTargetView(View view) {
        this.mTouchTargetView = view;
        if (view != null) {
            view.getLocationInWindow(this.mTouchTargetWindowLocation);
        }
    }

    private View findViewForTouch(int i, int i2) {
        int[] iArr = this.mContainerRawLocation;
        int i3 = i - iArr[0];
        int i4 = i2 - iArr[1];
        Rect rect = this.mTempRect;
        View view = null;
        int i5 = Integer.MAX_VALUE;
        for (int childCount = this.mContainer.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mContainer.getChildAt(childCount);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(rect);
                if (rect.contains(i3, i4)) {
                    return childAt;
                }
                int min = (i3 < rect.left || i3 > rect.right) ? Math.min(Math.abs(rect.left - i3), Math.abs(i3 - rect.right)) : 0;
                int min2 = (i4 < rect.top || i4 > rect.bottom) ? Math.min(Math.abs(rect.top - i4), Math.abs(i4 - rect.bottom)) : 0;
                int i6 = (min * min) + (min2 * min2);
                if (i6 < this.mTouchPaddingScaledSq && i6 < i5) {
                    view = childAt;
                    i5 = i6;
                }
            }
        }
        return view;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPostConfigurationChanged() {
        dismissControlsDelayed(ZOOM_CONTROLS_TIMEOUT);
        refreshPositioningVariables();
    }

    private class Container extends FrameLayout {
        public Container(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (ZoomButtonsController.this.onContainerKey(keyEvent)) {
                return true;
            }
            return super.dispatchKeyEvent(keyEvent);
        }
    }
}
