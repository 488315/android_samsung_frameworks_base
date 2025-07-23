package com.android.internal.widget.remotecompose.player.platform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.AttributeSet;
import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.player.RemoteComposeDocument;
import com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas;
import java.util.Set;

/* loaded from: classes6.dex */
public class RemoteComposeCanvas extends FrameLayout implements View.OnAttachStateChangeListener {
    static final float DEFAULT_FRAME_RATE = 60.0f;
    static final float POST_TO_NEXT_FRAME_THRESHOLD = 60.0f;
    static final boolean USE_VIEW_AREA_CLICK = true;
    private static final float[] sScaleOutput = new float[2];
    AndroidRemoteContext mARContext;
    Point mActionDownPoint;
    private Choreographer mChoreographer;
    private int mCount;
    int mDebug;
    float mDensity;
    private boolean mDisable;
    RemoteComposeDocument mDocument;
    private long mDuration;
    private boolean mEvalTime;
    private Choreographer.FrameCallback mFrameCallback;
    boolean mHasClickAreas;
    boolean mInActionDown;
    private float mLastAnimationTime;
    long mLastFrameCall;
    long mLastFrameDelay;
    long mMaxFrameDelay;
    float mMaxFrameRate;
    long mStart;
    int mTheme;
    private long mTime;
    private VelocityTracker mVelocityTracker;

    public interface ClickCallbacks {
        void click(int i, String str);
    }

    public RemoteComposeCanvas(Context context) {
        super(context);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = 0;
        this.mHasClickAreas = false;
        this.mActionDownPoint = new Point(0, 0);
        this.mARContext = new AndroidRemoteContext();
        this.mDensity = Float.NaN;
        this.mStart = System.nanoTime();
        this.mLastFrameDelay = 1L;
        this.mMaxFrameRate = 60.0f;
        this.mMaxFrameDelay = (long) (1000.0f / 60.0f);
        this.mLastFrameCall = System.currentTimeMillis();
        this.mFrameCallback = new Choreographer.FrameCallback() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                RemoteComposeCanvas.this.mARContext.currentTime = j / 1000000;
                RemoteComposeCanvas.this.mARContext.setDebug(RemoteComposeCanvas.this.mDebug);
                RemoteComposeCanvas.this.postInvalidateOnAnimation();
            }
        };
        this.mVelocityTracker = null;
        this.mTime = System.nanoTime();
        this.mEvalTime = false;
        this.mLastAnimationTime = 0.1f;
        this.mDisable = false;
        addOnAttachStateChangeListener(this);
    }

    public RemoteComposeCanvas(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = 0;
        this.mHasClickAreas = false;
        this.mActionDownPoint = new Point(0, 0);
        this.mARContext = new AndroidRemoteContext();
        this.mDensity = Float.NaN;
        this.mStart = System.nanoTime();
        this.mLastFrameDelay = 1L;
        this.mMaxFrameRate = 60.0f;
        this.mMaxFrameDelay = (long) (1000.0f / 60.0f);
        this.mLastFrameCall = System.currentTimeMillis();
        this.mFrameCallback = new Choreographer.FrameCallback() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                RemoteComposeCanvas.this.mARContext.currentTime = j / 1000000;
                RemoteComposeCanvas.this.mARContext.setDebug(RemoteComposeCanvas.this.mDebug);
                RemoteComposeCanvas.this.postInvalidateOnAnimation();
            }
        };
        this.mVelocityTracker = null;
        this.mTime = System.nanoTime();
        this.mEvalTime = false;
        this.mLastAnimationTime = 0.1f;
        this.mDisable = false;
        addOnAttachStateChangeListener(this);
    }

    public RemoteComposeCanvas(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = 0;
        this.mHasClickAreas = false;
        this.mActionDownPoint = new Point(0, 0);
        this.mARContext = new AndroidRemoteContext();
        this.mDensity = Float.NaN;
        this.mStart = System.nanoTime();
        this.mLastFrameDelay = 1L;
        this.mMaxFrameRate = 60.0f;
        this.mMaxFrameDelay = (long) (1000.0f / 60.0f);
        this.mLastFrameCall = System.currentTimeMillis();
        this.mFrameCallback = new Choreographer.FrameCallback() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas.1
            @Override // android.view.Choreographer.FrameCallback
            public void doFrame(long j) {
                RemoteComposeCanvas.this.mARContext.currentTime = j / 1000000;
                RemoteComposeCanvas.this.mARContext.setDebug(RemoteComposeCanvas.this.mDebug);
                RemoteComposeCanvas.this.postInvalidateOnAnimation();
            }
        };
        this.mVelocityTracker = null;
        this.mTime = System.nanoTime();
        this.mEvalTime = false;
        this.mLastAnimationTime = 0.1f;
        this.mDisable = false;
        setBackgroundColor(-1);
        addOnAttachStateChangeListener(this);
    }

    public void setDebug(int i) {
        if (this.mDebug != i) {
            this.mDebug = i;
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (childAt instanceof ClickAreaView) {
                    ((ClickAreaView) childAt).setDebug(this.mDebug == 1);
                }
            }
            invalidate();
        }
    }

    public void setDocument(RemoteComposeDocument remoteComposeDocument) {
        this.mDocument = remoteComposeDocument;
        this.mMaxFrameRate = 60.0f;
        remoteComposeDocument.initializeContext(this.mARContext);
        this.mDisable = false;
        this.mARContext.setDocLoadTime();
        this.mARContext.setAnimationEnabled(true);
        this.mARContext.setDensity(this.mDensity);
        this.mARContext.setUseChoreographer(true);
        setContentDescription(this.mDocument.getDocument().getContentDescription());
        updateClickAreas();
        requestLayout();
        this.mARContext.loadFloat(29, -3.4028235E38f);
        this.mARContext.loadFloat(33, getDefaultTextSize());
        invalidate();
        Integer num = (Integer) this.mDocument.getDocument().getProperty((short) 8);
        if (num == null || num.intValue() <= 0) {
            return;
        }
        float intValue = num.intValue();
        this.mMaxFrameRate = intValue;
        this.mMaxFrameDelay = (long) (1000.0f / intValue);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        if (this.mChoreographer == null) {
            Choreographer choreographer = Choreographer.getInstance();
            this.mChoreographer = choreographer;
            choreographer.postFrameCallback(this.mFrameCallback);
        }
        float f = getContext().getResources().getDisplayMetrics().density;
        this.mDensity = f;
        this.mARContext.setDensity(f);
        if (this.mDocument == null) {
            return;
        }
        updateClickAreas();
    }

    private void updateClickAreas() {
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            this.mHasClickAreas = false;
            Set<CoreDocument.ClickAreaRepresentation> clickAreas = remoteComposeDocument.getDocument().getClickAreas();
            removeAllViews();
            for (final CoreDocument.ClickAreaRepresentation clickAreaRepresentation : clickAreas) {
                View clickAreaView = new ClickAreaView(getContext(), this.mDebug == 1, clickAreaRepresentation.getId(), clickAreaRepresentation.getContentDescription(), clickAreaRepresentation.getMetadata());
                int width = (int) clickAreaRepresentation.width();
                int height = (int) clickAreaRepresentation.height();
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
                layoutParams.width = width;
                layoutParams.height = height;
                layoutParams.leftMargin = (int) clickAreaRepresentation.getLeft();
                layoutParams.topMargin = (int) clickAreaRepresentation.getTop();
                clickAreaView.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        RemoteComposeCanvas.this.lambda$updateClickAreas$0(clickAreaRepresentation, view);
                    }
                });
                addView(clickAreaView, layoutParams);
            }
            if (clickAreas.isEmpty()) {
                return;
            }
            this.mHasClickAreas = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateClickAreas$0(CoreDocument.ClickAreaRepresentation clickAreaRepresentation, View view) {
        this.mDocument.getDocument().performClick(this.mARContext, clickAreaRepresentation.getId(), clickAreaRepresentation.getMetadata());
    }

    public void setHapticEngine(CoreDocument.HapticEngine hapticEngine) {
        this.mDocument.getDocument().setHapticEngine(hapticEngine);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        Choreographer choreographer = this.mChoreographer;
        if (choreographer != null) {
            choreographer.removeFrameCallback(this.mFrameCallback);
            this.mChoreographer = null;
        }
        removeAllViews();
    }

    public String[] getNamedColors() {
        return this.mDocument.getNamedColors();
    }

    public String[] getNamedVariables(int i) {
        return this.mDocument.getNamedVariables(i);
    }

    public void setColor(String str, int i) {
        this.mARContext.setNamedColorOverride(str, i);
    }

    public void setLong(String str, long j) {
        this.mARContext.setNamedLong(str, j);
    }

    public RemoteComposeDocument getDocument() {
        return this.mDocument;
    }

    public void setLocalString(String str, String str2) {
        this.mARContext.setNamedStringOverride(str, str2);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void clearLocalString(String str) {
        this.mARContext.clearNamedStringOverride(str);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void setLocalInt(String str, int i) {
        this.mARContext.setNamedIntegerOverride(str, i);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void clearLocalInt(String str) {
        this.mARContext.clearNamedIntegerOverride(str);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void setLocalColor(String str, int i) {
        this.mARContext.setNamedColorOverride(str, i);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void clearLocalColor(String str) {
        this.mARContext.clearNamedDataOverride(str);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void setLocalFloat(String str, Float f) {
        this.mARContext.setNamedFloatOverride(str, f.floatValue());
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void clearLocalFloat(String str) {
        this.mARContext.clearNamedFloatOverride(str);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void setLocalBitmap(String str, Bitmap bitmap) {
        this.mARContext.setNamedDataOverride(str, bitmap);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public void clearLocalBitmap(String str) {
        this.mARContext.clearNamedDataOverride(str);
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument != null) {
            remoteComposeDocument.invalidate();
        }
    }

    public int hasSensorListeners(int[] iArr) {
        int i = 0;
        for (int i2 = 17; i2 <= 26; i2++) {
            if (this.mARContext.mRemoteComposeState.hasListener(i2)) {
                iArr[i] = i2;
                i++;
            }
        }
        return i;
    }

    public void setExternalFloat(int i, float f) {
        this.mARContext.loadFloat(i, f);
    }

    public boolean isDraggable() {
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument == null) {
            return false;
        }
        return remoteComposeDocument.getDocument().hasTouchListener();
    }

    public void checkShaders(CoreDocument.ShaderControl shaderControl) {
        this.mDocument.getDocument().checkShaders(this.mARContext, shaderControl);
    }

    public void setUseChoreographer(boolean z) {
        this.mARContext.setUseChoreographer(z);
    }

    public RemoteContext getRemoteContext() {
        return this.mARContext;
    }

    public void addIdActionListener(final ClickCallbacks clickCallbacks) {
        RemoteComposeDocument remoteComposeDocument = this.mDocument;
        if (remoteComposeDocument == null) {
            return;
        }
        remoteComposeDocument.getDocument().addIdActionListener(new CoreDocument.IdActionCallback() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas$$ExternalSyntheticLambda0
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.IdActionCallback
            public final void onAction(int i, String str) {
                RemoteComposeCanvas.ClickCallbacks.this.click(i, str);
            }
        });
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setTheme(int i) {
        this.mTheme = i;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        motionEvent.getActionMasked();
        int pointerId = motionEvent.getPointerId(actionIndex);
        if (this.mHasClickAreas) {
            return super.onTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mActionDownPoint.x = (int) motionEvent.getX();
            this.mActionDownPoint.y = (int) motionEvent.getY();
            CoreDocument document = this.mDocument.getDocument();
            if (!document.hasTouchListener()) {
                return false;
            }
            AndroidRemoteContext androidRemoteContext = this.mARContext;
            androidRemoteContext.loadFloat(29, androidRemoteContext.getAnimationTime());
            this.mInActionDown = true;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            } else {
                velocityTracker.clear();
            }
            this.mVelocityTracker.addMovement(motionEvent);
            document.touchDown(this.mARContext, motionEvent.getX(), motionEvent.getY());
            invalidate();
            return true;
        }
        if (actionMasked == 1) {
            this.mInActionDown = false;
            performClick();
            CoreDocument document2 = this.mDocument.getDocument();
            if (!document2.hasTouchListener()) {
                return false;
            }
            AndroidRemoteContext androidRemoteContext2 = this.mARContext;
            androidRemoteContext2.loadFloat(29, androidRemoteContext2.getAnimationTime());
            this.mVelocityTracker.computeCurrentVelocity(1000);
            document2.touchUp(this.mARContext, motionEvent.getX(), motionEvent.getY(), this.mVelocityTracker.getXVelocity(pointerId), this.mVelocityTracker.getYVelocity(pointerId));
            invalidate();
            return true;
        }
        if (actionMasked == 2) {
            if (!this.mInActionDown) {
                return false;
            }
            if (this.mVelocityTracker != null) {
                AndroidRemoteContext androidRemoteContext3 = this.mARContext;
                androidRemoteContext3.loadFloat(29, androidRemoteContext3.getAnimationTime());
                this.mVelocityTracker.addMovement(motionEvent);
                if (this.mDocument.getDocument().touchDrag(this.mARContext, motionEvent.getX(), motionEvent.getY())) {
                    invalidate();
                }
            }
            return true;
        }
        if (actionMasked != 3) {
            return false;
        }
        this.mInActionDown = false;
        CoreDocument document3 = this.mDocument.getDocument();
        if (!document3.hasTouchListener()) {
            return false;
        }
        this.mVelocityTracker.computeCurrentVelocity(1000);
        document3.touchCancel(this.mARContext, motionEvent.getX(), motionEvent.getY(), this.mVelocityTracker.getXVelocity(pointerId), this.mVelocityTracker.getYVelocity(pointerId));
        invalidate();
        return true;
    }

    @Override // android.view.View
    public boolean performClick() {
        if (this.mHasClickAreas) {
            return super.performClick();
        }
        this.mDocument.getDocument().onClick(this.mARContext, this.mActionDownPoint.x, this.mActionDownPoint.y);
        super.performClick();
        invalidate();
        return true;
    }

    public int measureDimension(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode != Integer.MIN_VALUE) {
            return mode != 1073741824 ? i2 : size;
        }
        return Integer.min(size, i2);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mDocument == null) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int measureDimension = measureDimension(i, this.mDocument.getWidth());
        int measureDimension2 = measureDimension(i2, this.mDocument.getHeight());
        setMeasuredDimension(measureDimension, measureDimension2);
        if (width == measureDimension && height == measureDimension2) {
            return;
        }
        this.mDocument.getDocument().invalidateMeasure();
    }

    public float getEvalTime() {
        if (!this.mEvalTime) {
            this.mEvalTime = true;
            return 0.0f;
        }
        long j = this.mDuration;
        int i = this.mCount;
        double d = j / i;
        if (i > 100) {
            this.mDuration = j / 2;
            this.mCount = i / 2;
        }
        return (float) (d * 1.0E-6d);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDocument == null) {
            return;
        }
        if (this.mDisable) {
            drawDisable(canvas);
            return;
        }
        try {
            long nanoTime = this.mEvalTime ? System.nanoTime() : 0L;
            float nanoTime2 = (System.nanoTime() - this.mStart) * 1.0E-9f;
            this.mARContext.setAnimationTime(nanoTime2);
            this.mARContext.loadFloat(30, nanoTime2);
            this.mARContext.loadFloat(31, nanoTime2 - this.mLastAnimationTime);
            this.mLastAnimationTime = nanoTime2;
            this.mARContext.setAnimationEnabled(true);
            this.mARContext.currentTime = System.currentTimeMillis();
            this.mARContext.setDebug(this.mDebug);
            float f = getContext().getResources().getDisplayMetrics().density;
            this.mARContext.useCanvas(canvas);
            this.mARContext.mWidth = getWidth();
            this.mARContext.mHeight = getHeight();
            this.mDocument.paint(this.mARContext, this.mTheme);
            if (this.mDebug == 1) {
                this.mCount++;
                if (System.nanoTime() - this.mTime > 1000000000) {
                    System.out.println(" count " + this.mCount + " fps");
                    this.mCount = 0;
                    this.mTime = System.nanoTime();
                }
            }
            int needsRepaint = this.mDocument.needsRepaint();
            if (needsRepaint > 0) {
                if (this.mMaxFrameRate >= 60.0f) {
                    this.mLastFrameDelay = needsRepaint;
                } else {
                    this.mLastFrameDelay = Math.max(this.mMaxFrameDelay, needsRepaint);
                }
                if (this.mChoreographer != null) {
                    if (this.mDebug == 1) {
                        System.err.println("RC : POST CHOREOGRAPHER WITH " + this.mLastFrameDelay + " (nextFrame was " + needsRepaint + ", max delay " + this.mMaxFrameDelay + ",  max framerate is " + this.mMaxFrameRate + NavigationBarInflaterView.KEY_CODE_END);
                    }
                    this.mChoreographer.postFrameCallbackDelayed(this.mFrameCallback, this.mLastFrameDelay);
                }
                if (!this.mARContext.useChoreographer()) {
                    invalidate();
                }
            } else {
                Choreographer choreographer = this.mChoreographer;
                if (choreographer != null) {
                    choreographer.removeFrameCallback(this.mFrameCallback);
                }
            }
            if (this.mEvalTime) {
                this.mDuration += System.nanoTime() - nanoTime;
                this.mCount++;
            }
        } catch (Exception unused) {
            this.mARContext.getLastOpCount();
            this.mDisable = true;
            invalidate();
        }
        if (this.mDebug == 1) {
            long currentTimeMillis = System.currentTimeMillis() - this.mLastFrameCall;
            System.err.println("RC : Delay since last frame " + currentTimeMillis + " ms (" + (1000.0f / currentTimeMillis) + " fps)");
            this.mLastFrameCall = System.currentTimeMillis();
        }
    }

    private void drawDisable(Canvas canvas) {
        Rect rect = new Rect();
        canvas.drawColor(-16777216);
        Paint paint = new Paint();
        paint.setTextSize(128.0f);
        paint.setColor(-65536);
        int width = getWidth();
        int height = getHeight();
        paint.getTextBounds("⚠", 0, 1, rect);
        canvas.drawText("⚠", ((width / 2.0f) - (rect.width() / 2.0f)) - rect.left, ((height / 2.0f) + (rect.height() / 2.0f)) - rect.bottom, paint);
    }

    private float getDefaultTextSize() {
        return new TextView(getContext()).getTextSize();
    }
}
