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

    public InlineContentView(Context context, AttributeSet attributeSet, int i, int i2) throws Throwable {
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
            public void onDraw() throws Throwable {
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
    protected void onAttachedToWindow() throws Throwable {
        super.onAttachedToWindow();
        SurfacePackageUpdater surfacePackageUpdater = this.mSurfacePackageUpdater;
        if (surfacePackageUpdater != null) {
            surfacePackageUpdater.getSurfacePackage(new Consumer() { // from class: android.widget.inline.InlineContentView$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$onAttachedToWindow$0((SurfaceControlViewHost.SurfacePackage) obj);
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
    protected void onDetachedFromWindow() throws Throwable {
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
    /* JADX WARN: Removed duplicated region for block: B:40:0x009f A[PHI: r3
      0x009f: PHI (r3v1 boolean) = (r3v0 boolean), (r3v4 boolean), (r3v4 boolean) binds: [B:38:0x009c, B:31:0x0089, B:33:0x0093] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void computeParentPositionAndScale() {
        WeakReference<SurfaceView> weakReference = this.mParentSurfaceOwnerView;
        SurfaceView surfaceView = weakReference != null ? weakReference.get() : null;
        boolean z = true;
        if (surfaceView != null) {
            if (this.mParentPosition == null) {
                this.mParentPosition = new int[2];
            }
            int[] iArr = this.mParentPosition;
            int i = iArr[0];
            int i2 = iArr[1];
            surfaceView.getLocationInSurface(iArr);
            int[] iArr2 = this.mParentPosition;
            z = (i == iArr2[0] && i2 == iArr2[1]) ? false : true;
            if (this.mParentScale == null) {
                this.mParentScale = new PointF();
            }
            float fWidth = surfaceView.getSurfaceRenderPosition().width();
            float f = this.mParentScale.x;
            if (fWidth > 0.0f) {
                this.mParentScale.x = fWidth / surfaceView.getWidth();
            } else {
                this.mParentScale.x = 1.0f;
            }
            if (!z && Float.compare(f, this.mParentScale.x) != 0) {
                z = true;
            }
            float fHeight = surfaceView.getSurfaceRenderPosition().height();
            float f2 = this.mParentScale.y;
            if (fHeight > 0.0f) {
                this.mParentScale.y = fHeight / surfaceView.getHeight();
            } else {
                this.mParentScale.y = 1.0f;
            }
            if (z || Float.compare(f2, this.mParentScale.y) == 0) {
                z = z;
            }
        } else if (this.mParentPosition != null || this.mParentScale != null) {
            this.mParentPosition = null;
            this.mParentScale = null;
        }
        if (z) {
            this.mSurfaceView.requestUpdateSurfacePositionAndScale();
        }
    }
}
