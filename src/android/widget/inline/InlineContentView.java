package android.widget.inline;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* loaded from: classes5.dex */
public class InlineContentView extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final String TAG = "InlineContentView";
    private final ViewTreeObserver.OnDrawListener mOnDrawListener;
    private final SurfaceControl.OnReparentListener mOnReparentListener;
    private int[] mParentPosition;
    private PointF mParentScale;
    private WeakReference<SurfaceView> mParentSurfaceOwnerView;
    private final SurfaceHolder.Callback mSurfaceCallback;
    private SurfaceControlCallback mSurfaceControlCallback;
    private SurfacePackageUpdater mSurfacePackageUpdater;
    private final SurfaceView mSurfaceView;

    public interface SurfaceControlCallback {
        void onCreated(SurfaceControl surfaceControl);

        void onDestroyed(SurfaceControl surfaceControl);
    }

    public interface SurfacePackageUpdater {
        void getSurfacePackage(Consumer<SurfaceControlViewHost.SurfacePackage> consumer);

        void onSurfacePackageReleased();
    }

    public InlineContentView(Context context) {
        this(context, null);
    }

    public InlineContentView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public InlineContentView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
        this.mSurfaceView.setEnableSurfaceClipping(true);
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceView.getSurfaceControl();
    }

    @Override // android.view.View
    public void setClipBounds(Rect rect) {
        super.setClipBounds(rect);
        this.mSurfaceView.setClipBounds(rect);
    }

    public InlineContentView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSurfaceCallback = new SurfaceHolder.Callback() { // from class: android.widget.inline.InlineContentView.1
            @Override // android.view.SurfaceHolder.Callback
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i3, int i4, int i5) {
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                SurfaceControl surfaceControl = InlineContentView.this.mSurfaceView.getSurfaceControl();
                surfaceControl.addOnReparentListener(InlineContentView.this.mOnReparentListener);
                InlineContentView.this.mSurfaceControlCallback.onCreated(surfaceControl);
            }

            @Override // android.view.SurfaceHolder.Callback
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                SurfaceControl surfaceControl = InlineContentView.this.mSurfaceView.getSurfaceControl();
                surfaceControl.removeOnReparentListener(InlineContentView.this.mOnReparentListener);
                InlineContentView.this.mSurfaceControlCallback.onDestroyed(surfaceControl);
            }
        };
        this.mOnReparentListener = new SurfaceControl.OnReparentListener() { // from class: android.widget.inline.InlineContentView.2
            @Override // android.view.SurfaceControl.OnReparentListener
            public void onReparent(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
                View localOwnerView = surfaceControl != null ? surfaceControl.getLocalOwnerView() : null;
                if (localOwnerView instanceof SurfaceView) {
                    InlineContentView.this.mParentSurfaceOwnerView = new WeakReference((SurfaceView) localOwnerView);
                } else {
                    InlineContentView.this.mParentSurfaceOwnerView = null;
                }
            }
        };
        this.mOnDrawListener = new ViewTreeObserver.OnDrawListener() { // from class: android.widget.inline.InlineContentView.3
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public void onDraw() {
                InlineContentView.this.computeParentPositionAndScale();
                InlineContentView.this.mSurfaceView.setVisibility(InlineContentView.this.isShown() ? 0 : 8);
            }
        };
        SurfaceView surfaceView = new SurfaceView(context, attributeSet, i, i2) { // from class: android.widget.inline.InlineContentView.4
            @Override // android.view.SurfaceView
            protected void onSetSurfacePositionAndScale(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i3, int i4, float f, float f2) {
                if (InlineContentView.this.mParentPosition != null) {
                    i3 = (int) ((i3 - InlineContentView.this.mParentPosition[0]) / InlineContentView.this.mParentScale.x);
                    i4 = (int) ((i4 - InlineContentView.this.mParentPosition[1]) / InlineContentView.this.mParentScale.y);
                }
                super.onSetSurfacePositionAndScale(transaction, surfaceControl, i3, i4, InlineContentView.this.getScaleX(), InlineContentView.this.getScaleY());
            }
        };
        this.mSurfaceView = surfaceView;
        surfaceView.setZOrderOnTop(true);
        surfaceView.getHolder().setFormat(-2);
        addView(surfaceView);
        setImportantForAccessibility(2);
    }

    public void setChildSurfacePackageUpdater(SurfacePackageUpdater surfacePackageUpdater) {
        this.mSurfacePackageUpdater = surfacePackageUpdater;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        SurfacePackageUpdater surfacePackageUpdater = this.mSurfacePackageUpdater;
        if (surfacePackageUpdater != null) {
            surfacePackageUpdater.getSurfacePackage(new Consumer() { // from class: android.widget.inline.InlineContentView$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    InlineContentView.this.lambda$onAttachedToWindow$0((SurfaceControlViewHost.SurfacePackage) obj);
                }
            });
        }
        this.mSurfaceView.setVisibility(getVisibility());
        getViewTreeObserver().addOnDrawListener(this.mOnDrawListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$0(SurfaceControlViewHost.SurfacePackage surfacePackage) {
        if (getViewRootImpl() != null) {
            this.mSurfaceView.setChildSurfacePackage(surfacePackage);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SurfacePackageUpdater surfacePackageUpdater = this.mSurfacePackageUpdater;
        if (surfacePackageUpdater != null) {
            surfacePackageUpdater.onSurfacePackageReleased();
        }
        getViewTreeObserver().removeOnDrawListener(this.mOnDrawListener);
        this.mSurfaceView.setVisibility(8);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.mSurfaceView.layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    public void setSurfaceControlCallback(SurfaceControlCallback surfaceControlCallback) {
        if (this.mSurfaceControlCallback != null) {
            this.mSurfaceView.getHolder().removeCallback(this.mSurfaceCallback);
        }
        this.mSurfaceControlCallback = surfaceControlCallback;
        if (surfaceControlCallback != null) {
            this.mSurfaceView.getHolder().addCallback(this.mSurfaceCallback);
        }
    }

    public boolean isZOrderedOnTop() {
        return this.mSurfaceView.isZOrderedOnTop();
    }

    public boolean setZOrderedOnTop(boolean z) {
        return this.mSurfaceView.setZOrderedOnTop(z, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0093, code lost:
    
        if (java.lang.Float.compare(r4, r9.mParentScale.y) != 0) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void computeParentPositionAndScale() {
        /*
            r9 = this;
            java.lang.ref.WeakReference<android.view.SurfaceView> r0 = r9.mParentSurfaceOwnerView
            r1 = 0
            if (r0 == 0) goto Lc
            java.lang.Object r0 = r0.get()
            android.view.SurfaceView r0 = (android.view.SurfaceView) r0
            goto Ld
        Lc:
            r0 = r1
        Ld:
            r2 = 1
            r3 = 0
            if (r0 == 0) goto L96
            int[] r1 = r9.mParentPosition
            if (r1 != 0) goto L1a
            r1 = 2
            int[] r1 = new int[r1]
            r9.mParentPosition = r1
        L1a:
            int[] r1 = r9.mParentPosition
            r4 = r1[r3]
            r5 = r1[r2]
            r0.getLocationInSurface(r1)
            int[] r1 = r9.mParentPosition
            r6 = r1[r3]
            if (r4 != r6) goto L2d
            r1 = r1[r2]
            if (r5 == r1) goto L2e
        L2d:
            r3 = r2
        L2e:
            android.graphics.PointF r1 = r9.mParentScale
            if (r1 != 0) goto L39
            android.graphics.PointF r1 = new android.graphics.PointF
            r1.<init>()
            r9.mParentScale = r1
        L39:
            android.graphics.Rect r1 = r0.getSurfaceRenderPosition()
            int r1 = r1.width()
            float r1 = (float) r1
            android.graphics.PointF r4 = r9.mParentScale
            float r4 = r4.x
            r5 = 0
            int r6 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            r7 = 1065353216(0x3f800000, float:1.0)
            if (r6 <= 0) goto L58
            android.graphics.PointF r6 = r9.mParentScale
            int r8 = r0.getWidth()
            float r8 = (float) r8
            float r1 = r1 / r8
            r6.x = r1
            goto L5c
        L58:
            android.graphics.PointF r1 = r9.mParentScale
            r1.x = r7
        L5c:
            if (r3 != 0) goto L69
            android.graphics.PointF r1 = r9.mParentScale
            float r1 = r1.x
            int r1 = java.lang.Float.compare(r4, r1)
            if (r1 == 0) goto L69
            r3 = r2
        L69:
            android.graphics.Rect r1 = r0.getSurfaceRenderPosition()
            int r1 = r1.height()
            float r1 = (float) r1
            android.graphics.PointF r4 = r9.mParentScale
            float r4 = r4.y
            int r5 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r5 <= 0) goto L85
            android.graphics.PointF r5 = r9.mParentScale
            int r0 = r0.getHeight()
            float r0 = (float) r0
            float r1 = r1 / r0
            r5.y = r1
            goto L89
        L85:
            android.graphics.PointF r0 = r9.mParentScale
            r0.y = r7
        L89:
            if (r3 != 0) goto L9f
            android.graphics.PointF r0 = r9.mParentScale
            float r0 = r0.y
            int r0 = java.lang.Float.compare(r4, r0)
            if (r0 == 0) goto L9f
            goto La5
        L96:
            int[] r0 = r9.mParentPosition
            if (r0 != 0) goto La1
            android.graphics.PointF r0 = r9.mParentScale
            if (r0 == 0) goto L9f
            goto La1
        L9f:
            r2 = r3
            goto La5
        La1:
            r9.mParentPosition = r1
            r9.mParentScale = r1
        La5:
            if (r2 == 0) goto Lac
            android.view.SurfaceView r9 = r9.mSurfaceView
            r9.requestUpdateSurfacePositionAndScale()
        Lac:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.inline.InlineContentView.computeParentPositionAndScale():void");
    }
}
