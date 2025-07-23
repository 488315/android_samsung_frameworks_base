package android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class ViewOverlay {
    OverlayViewGroup mOverlayViewGroup;

    ViewOverlay(Context context, View view) {
        this.mOverlayViewGroup = new OverlayViewGroup(context, view);
    }

    ViewGroup getOverlayView() {
        return this.mOverlayViewGroup;
    }

    public void add(Drawable drawable) {
        this.mOverlayViewGroup.add(drawable);
    }

    public void remove(Drawable drawable) {
        this.mOverlayViewGroup.remove(drawable);
    }

    public void clear() {
        this.mOverlayViewGroup.clear();
    }

    boolean isEmpty() {
        return this.mOverlayViewGroup.isEmpty();
    }

    static class OverlayViewGroup extends ViewGroup {
        ArrayList<Drawable> mDrawables;
        final View mHostView;

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        }

        OverlayViewGroup(Context context, View view) {
            super(context);
            this.mDrawables = null;
            this.mHostView = view;
            this.mAttachInfo = view.mAttachInfo;
            this.mRight = view.getWidth();
            this.mBottom = view.getHeight();
            this.mRenderNode.setLeftTopRightBottom(0, 0, this.mRight, this.mBottom);
        }

        public void add(Drawable drawable) {
            if (drawable == null) {
                throw new IllegalArgumentException("drawable must be non-null");
            }
            if (this.mDrawables == null) {
                this.mDrawables = new ArrayList<>();
            }
            if (this.mDrawables.contains(drawable)) {
                return;
            }
            this.mDrawables.add(drawable);
            invalidate(drawable.getBounds());
            drawable.setCallback(this);
        }

        public void remove(Drawable drawable) {
            if (drawable == null) {
                throw new IllegalArgumentException("drawable must be non-null");
            }
            ArrayList<Drawable> arrayList = this.mDrawables;
            if (arrayList != null) {
                arrayList.remove(drawable);
                invalidate(drawable.getBounds());
                drawable.setCallback(null);
            }
        }

        @Override // android.view.View
        protected boolean verifyDrawable(Drawable drawable) {
            if (super.verifyDrawable(drawable)) {
                return true;
            }
            ArrayList<Drawable> arrayList = this.mDrawables;
            return arrayList != null && arrayList.contains(drawable);
        }

        public void add(View view) {
            if (view == null) {
                throw new IllegalArgumentException("view must be non-null");
            }
            if (view.getParent() instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view.getParent();
                if (viewGroup != this.mHostView && viewGroup.getParent() != null && viewGroup.mAttachInfo != null) {
                    int[] iArr = new int[2];
                    int[] iArr2 = new int[2];
                    viewGroup.getLocationOnScreen(iArr);
                    this.mHostView.getLocationOnScreen(iArr2);
                    view.offsetLeftAndRight(iArr[0] - iArr2[0]);
                    view.offsetTopAndBottom(iArr[1] - iArr2[1]);
                }
                viewGroup.removeView(view);
                if (viewGroup.getLayoutTransition() != null) {
                    viewGroup.getLayoutTransition().cancel(3);
                }
                if (view.getParent() != null) {
                    view.mParent = null;
                }
            }
            super.addView(view);
        }

        public void remove(View view) {
            if (view == null) {
                throw new IllegalArgumentException("view must be non-null");
            }
            super.removeView(view);
        }

        public void clear() {
            removeAllViews();
            ArrayList<Drawable> arrayList = this.mDrawables;
            if (arrayList != null) {
                Iterator<Drawable> it = arrayList.iterator();
                while (it.hasNext()) {
                    it.next().setCallback(null);
                }
                this.mDrawables.clear();
            }
        }

        boolean isEmpty() {
            if (getChildCount() != 0) {
                return false;
            }
            ArrayList<Drawable> arrayList = this.mDrawables;
            return arrayList == null || arrayList.size() == 0;
        }

        @Override // android.view.View, android.graphics.drawable.Drawable.Callback
        public void invalidateDrawable(Drawable drawable) {
            invalidate(drawable.getBounds());
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            canvas.enableZ();
            super.dispatchDraw(canvas);
            canvas.disableZ();
            ArrayList<Drawable> arrayList = this.mDrawables;
            int size = arrayList == null ? 0 : arrayList.size();
            for (int i = 0; i < size; i++) {
                this.mDrawables.get(i).draw(canvas);
            }
        }

        @Override // android.view.View
        public void invalidate(Rect rect) {
            super.invalidate(rect);
            View view = this.mHostView;
            if (view != null) {
                view.invalidate(rect);
            }
        }

        @Override // android.view.View
        public void invalidate(int i, int i2, int i3, int i4) {
            super.invalidate(i, i2, i3, i4);
            View view = this.mHostView;
            if (view != null) {
                view.invalidate(i, i2, i3, i4);
            }
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            View view = this.mHostView;
            if (view != null) {
                view.invalidate();
            }
        }

        @Override // android.view.View
        public void invalidate(boolean z) {
            super.invalidate(z);
            View view = this.mHostView;
            if (view != null) {
                view.invalidate(z);
            }
        }

        @Override // android.view.View
        void invalidateViewProperty(boolean z, boolean z2) {
            super.invalidateViewProperty(z, z2);
            View view = this.mHostView;
            if (view != null) {
                view.invalidateViewProperty(z, z2);
            }
        }

        @Override // android.view.View
        protected void invalidateParentCaches() {
            super.invalidateParentCaches();
            View view = this.mHostView;
            if (view != null) {
                view.invalidateParentCaches();
            }
        }

        @Override // android.view.View
        protected void invalidateParentIfNeeded() {
            super.invalidateParentIfNeeded();
            View view = this.mHostView;
            if (view != null) {
                view.invalidateParentIfNeeded();
            }
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public void onDescendantInvalidated(View view, View view2) {
            View view3 = this.mHostView;
            if (view3 != null) {
                if (view3 instanceof ViewGroup) {
                    ((ViewGroup) view3).onDescendantInvalidated(view3, view2);
                    super.onDescendantInvalidated(view, view2);
                } else {
                    invalidate();
                }
            }
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public ViewParent invalidateChildInParent(int[] iArr, Rect rect) {
            if (this.mHostView == null) {
                return null;
            }
            rect.offset(iArr[0], iArr[1]);
            if (this.mHostView instanceof ViewGroup) {
                iArr[0] = 0;
                iArr[1] = 0;
                super.invalidateChildInParent(iArr, rect);
                return ((ViewGroup) this.mHostView).invalidateChildInParent(iArr, rect);
            }
            invalidate(rect);
            return null;
        }

        @Override // android.view.View
        public float getFrameContentVelocity() {
            View view = this.mHostView;
            if (view != null) {
                return view.getFrameContentVelocity();
            }
            return super.getFrameContentVelocity();
        }
    }
}
