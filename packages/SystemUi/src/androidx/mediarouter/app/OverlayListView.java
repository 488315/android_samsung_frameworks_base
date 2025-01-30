package androidx.mediarouter.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.ListView;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
final class OverlayListView extends ListView {
    public final List mOverlayObjects;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OverlayObject {
        public final BitmapDrawable mBitmap;
        public final Rect mCurrentBounds;
        public int mDeltaY;
        public long mDuration;
        public Interpolator mInterpolator;
        public boolean mIsAnimationEnded;
        public boolean mIsAnimationStarted;
        public MediaRouteControllerDialog.C031310 mListener;
        public final Rect mStartRect;
        public long mStartTime;
        public float mCurrentAlpha = 1.0f;
        public float mStartAlpha = 1.0f;
        public float mEndAlpha = 1.0f;

        public OverlayObject(BitmapDrawable bitmapDrawable, Rect rect) {
            this.mBitmap = bitmapDrawable;
            this.mStartRect = rect;
            Rect rect2 = new Rect(rect);
            this.mCurrentBounds = rect2;
            if (bitmapDrawable != null) {
                bitmapDrawable.setAlpha((int) (this.mCurrentAlpha * 255.0f));
                bitmapDrawable.setBounds(rect2);
            }
        }
    }

    public OverlayListView(Context context) {
        super(context);
        this.mOverlayObjects = new ArrayList();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        boolean z;
        super.onDraw(canvas);
        if (((ArrayList) this.mOverlayObjects).size() > 0) {
            Iterator it = ((ArrayList) this.mOverlayObjects).iterator();
            while (it.hasNext()) {
                OverlayObject overlayObject = (OverlayObject) it.next();
                BitmapDrawable bitmapDrawable = overlayObject.mBitmap;
                if (bitmapDrawable != null) {
                    bitmapDrawable.draw(canvas);
                }
                long drawingTime = getDrawingTime();
                if (overlayObject.mIsAnimationEnded) {
                    z = false;
                } else {
                    float max = overlayObject.mIsAnimationStarted ? Math.max(0.0f, Math.min(1.0f, (drawingTime - overlayObject.mStartTime) / overlayObject.mDuration)) : 0.0f;
                    Interpolator interpolator = overlayObject.mInterpolator;
                    float interpolation = interpolator == null ? max : interpolator.getInterpolation(max);
                    int i = (int) (overlayObject.mDeltaY * interpolation);
                    Rect rect = overlayObject.mStartRect;
                    int i2 = rect.top + i;
                    Rect rect2 = overlayObject.mCurrentBounds;
                    rect2.top = i2;
                    rect2.bottom = rect.bottom + i;
                    float f = overlayObject.mStartAlpha;
                    float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(overlayObject.mEndAlpha, f, interpolation, f);
                    overlayObject.mCurrentAlpha = m20m;
                    BitmapDrawable bitmapDrawable2 = overlayObject.mBitmap;
                    if (bitmapDrawable2 != null) {
                        bitmapDrawable2.setAlpha((int) (m20m * 255.0f));
                        bitmapDrawable2.setBounds(rect2);
                    }
                    if (overlayObject.mIsAnimationStarted && max >= 1.0f) {
                        overlayObject.mIsAnimationEnded = true;
                        MediaRouteControllerDialog.C031310 c031310 = overlayObject.mListener;
                        if (c031310 != null) {
                            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                            ((HashSet) mediaRouteControllerDialog.mGroupMemberRoutesAnimatingWithBitmap).remove(c031310.val$route);
                            mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
                        }
                    }
                    z = !overlayObject.mIsAnimationEnded;
                }
                if (!z) {
                    it.remove();
                }
            }
        }
    }

    public OverlayListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOverlayObjects = new ArrayList();
    }

    public OverlayListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mOverlayObjects = new ArrayList();
    }
}
