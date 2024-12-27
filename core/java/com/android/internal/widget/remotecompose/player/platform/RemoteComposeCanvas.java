package com.android.internal.widget.remotecompose.player.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.android.internal.widget.remotecompose.core.CoreDocument;
import com.android.internal.widget.remotecompose.player.RemoteComposeDocument;
import com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas;
import java.util.Set;

/* loaded from: classes5.dex */
public class RemoteComposeCanvas extends FrameLayout implements View.OnAttachStateChangeListener {
    static final boolean USE_VIEW_AREA_CLICK = true;
    private static final float[] sScaleOutput = new float[2];
    AndroidRemoteContext mARContext;
    Point mActionDownPoint;
    private int mCount;
    boolean mDebug;
    RemoteComposeDocument mDocument;
    boolean mInActionDown;
    int mTheme;
    private long mTime;

    public interface ClickCallbacks {
        void click(int i, String str);
    }

    public RemoteComposeCanvas(Context context) {
        super(context);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = false;
        this.mActionDownPoint = new Point(0, 0);
        this.mARContext = new AndroidRemoteContext();
        this.mTime = System.nanoTime();
        addOnAttachStateChangeListener(this);
    }

    public RemoteComposeCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = false;
        this.mActionDownPoint = new Point(0, 0);
        this.mARContext = new AndroidRemoteContext();
        this.mTime = System.nanoTime();
        addOnAttachStateChangeListener(this);
    }

    public RemoteComposeCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mDocument = null;
        this.mTheme = -3;
        this.mInActionDown = false;
        this.mDebug = false;
        this.mActionDownPoint = new Point(0, 0);
        this.mARContext = new AndroidRemoteContext();
        this.mTime = System.nanoTime();
        setBackgroundColor(-1);
        addOnAttachStateChangeListener(this);
    }

    public void setDebug(boolean value) {
        if (this.mDebug != value) {
            this.mDebug = value;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child instanceof ClickAreaView) {
                    ((ClickAreaView) child).setDebug(this.mDebug);
                }
            }
            invalidate();
        }
    }

    public void setDocument(RemoteComposeDocument value) {
        this.mDocument = value;
        this.mDocument.initializeContext(this.mARContext);
        setContentDescription(this.mDocument.getDocument().getContentDescription());
        requestLayout();
        invalidate();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        if (this.mDocument == null) {
            return;
        }
        Set<CoreDocument.ClickAreaRepresentation> clickAreas = this.mDocument.getDocument().getClickAreas();
        removeAllViews();
        for (final CoreDocument.ClickAreaRepresentation area : clickAreas) {
            View viewArea = new ClickAreaView(getContext(), this.mDebug, area.getId(), area.getContentDescription(), area.getMetadata());
            int w = (int) area.width();
            int h = (int) area.height();
            FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(w, h);
            param.width = w;
            param.height = h;
            param.leftMargin = (int) area.getLeft();
            param.topMargin = (int) area.getTop();
            viewArea.setOnClickListener(new View.OnClickListener() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    RemoteComposeCanvas.this.lambda$onViewAttachedToWindow$0(area, view2);
                }
            });
            addView(viewArea, param);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onViewAttachedToWindow$0(CoreDocument.ClickAreaRepresentation area, View view1) {
        this.mDocument.getDocument().performClick(area.getId());
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        removeAllViews();
    }

    public void addClickListener(final ClickCallbacks callback) {
        if (this.mDocument == null) {
            return;
        }
        this.mDocument.getDocument().addClickListener(new CoreDocument.ClickCallbacks() { // from class: com.android.internal.widget.remotecompose.player.platform.RemoteComposeCanvas$$ExternalSyntheticLambda1
            @Override // com.android.internal.widget.remotecompose.core.CoreDocument.ClickCallbacks
            public final void click(int i, String str) {
                RemoteComposeCanvas.ClickCallbacks.this.click(i, str);
            }
        });
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setTheme(int theme) {
        this.mTheme = theme;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }

    public int measureDimension(int measureSpec, int intrinsicSize) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        switch (mode) {
            case Integer.MIN_VALUE:
                int result = Integer.min(size, intrinsicSize);
                return result;
            case 0:
                return intrinsicSize;
            case 1073741824:
                return size;
            default:
                return intrinsicSize;
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.mDocument == null) {
            return;
        }
        int w = measureDimension(widthMeasureSpec, this.mDocument.getWidth());
        int h = measureDimension(heightMeasureSpec, this.mDocument.getHeight());
        setMeasuredDimension(w, h);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mDocument == null) {
            return;
        }
        this.mARContext.setDebug(this.mDebug);
        this.mARContext.useCanvas(canvas);
        this.mARContext.mWidth = getWidth();
        this.mARContext.mHeight = getHeight();
        this.mDocument.paint(this.mARContext, this.mTheme);
        if (this.mDebug) {
            this.mCount++;
            if (System.nanoTime() - this.mTime > 1000000000) {
                System.out.println(" count " + this.mCount + " fps");
                this.mCount = 0;
                this.mTime = System.nanoTime();
            }
        }
        if (this.mDocument.needsRepaint() > 0) {
            invalidate();
        }
    }
}
