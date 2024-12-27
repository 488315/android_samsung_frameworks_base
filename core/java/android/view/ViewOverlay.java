package android.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Iterator;

public class ViewOverlay {
    OverlayViewGroup mOverlayViewGroup;

    ViewOverlay(Context context, View hostView) {
        this.mOverlayViewGroup = new OverlayViewGroup(context, hostView);
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

        OverlayViewGroup(Context context, View hostView) {
            super(context);
            this.mDrawables = null;
            this.mHostView = hostView;
            this.mAttachInfo = this.mHostView.mAttachInfo;
            this.mRight = hostView.getWidth();
            this.mBottom = hostView.getHeight();
            this.mRenderNode.setLeftTopRightBottom(0, 0, this.mRight, this.mBottom);
        }

        public void add(Drawable drawable) {
            if (drawable == null) {
                throw new IllegalArgumentException("drawable must be non-null");
            }
            if (this.mDrawables == null) {
                this.mDrawables = new ArrayList<>();
            }
            if (!this.mDrawables.contains(drawable)) {
                this.mDrawables.add(drawable);
                invalidate(drawable.getBounds());
                drawable.setCallback(this);
            }
        }

        public void remove(Drawable drawable) {
            if (drawable == null) {
                throw new IllegalArgumentException("drawable must be non-null");
            }
            if (this.mDrawables != null) {
                this.mDrawables.remove(drawable);
                invalidate(drawable.getBounds());
                drawable.setCallback(null);
            }
        }

        @Override // android.view.View
        protected boolean verifyDrawable(Drawable who) {
            return super.verifyDrawable(who)
                    || (this.mDrawables != null && this.mDrawables.contains(who));
        }

        public void add(View child) {
            if (child == null) {
                throw new IllegalArgumentException("view must be non-null");
            }
            if (child.getParent() instanceof ViewGroup) {
                ViewGroup parent = (ViewGroup) child.getParent();
                if (parent != this.mHostView
                        && parent.getParent() != null
                        && parent.mAttachInfo != null) {
                    int[] parentLocation = new int[2];
                    int[] hostViewLocation = new int[2];
                    parent.getLocationOnScreen(parentLocation);
                    this.mHostView.getLocationOnScreen(hostViewLocation);
                    child.offsetLeftAndRight(parentLocation[0] - hostViewLocation[0]);
                    child.offsetTopAndBottom(parentLocation[1] - hostViewLocation[1]);
                }
                parent.removeView(child);
                if (parent.getLayoutTransition() != null) {
                    parent.getLayoutTransition().cancel(3);
                }
                if (child.getParent() != null) {
                    child.mParent = null;
                }
            }
            super.addView(child);
        }

        public void remove(View view) {
            if (view == null) {
                throw new IllegalArgumentException("view must be non-null");
            }
            super.removeView(view);
        }

        public void clear() {
            removeAllViews();
            if (this.mDrawables != null) {
                Iterator<Drawable> it = this.mDrawables.iterator();
                while (it.hasNext()) {
                    Drawable drawable = it.next();
                    drawable.setCallback(null);
                }
                this.mDrawables.clear();
            }
        }

        boolean isEmpty() {
            if (getChildCount() == 0) {
                if (this.mDrawables == null || this.mDrawables.size() == 0) {
                    return true;
                }
                return false;
            }
            return false;
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
            int numDrawables = this.mDrawables == null ? 0 : this.mDrawables.size();
            for (int i = 0; i < numDrawables; i++) {
                this.mDrawables.get(i).draw(canvas);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onLayout(boolean changed, int l, int t, int r, int b) {}

        @Override // android.view.View
        public void invalidate(Rect dirty) {
            super.invalidate(dirty);
            if (this.mHostView != null) {
                this.mHostView.invalidate(dirty);
            }
        }

        @Override // android.view.View
        public void invalidate(int l, int t, int r, int b) {
            super.invalidate(l, t, r, b);
            if (this.mHostView != null) {
                this.mHostView.invalidate(l, t, r, b);
            }
        }

        @Override // android.view.View
        public void invalidate() {
            super.invalidate();
            if (this.mHostView != null) {
                this.mHostView.invalidate();
            }
        }

        @Override // android.view.View
        public void invalidate(boolean invalidateCache) {
            super.invalidate(invalidateCache);
            if (this.mHostView != null) {
                this.mHostView.invalidate(invalidateCache);
            }
        }

        @Override // android.view.View
        void invalidateViewProperty(boolean invalidateParent, boolean forceRedraw) {
            super.invalidateViewProperty(invalidateParent, forceRedraw);
            if (this.mHostView != null) {
                this.mHostView.invalidateViewProperty(invalidateParent, forceRedraw);
            }
        }

        @Override // android.view.View
        protected void invalidateParentCaches() {
            super.invalidateParentCaches();
            if (this.mHostView != null) {
                this.mHostView.invalidateParentCaches();
            }
        }

        @Override // android.view.View
        protected void invalidateParentIfNeeded() {
            super.invalidateParentIfNeeded();
            if (this.mHostView != null) {
                this.mHostView.invalidateParentIfNeeded();
            }
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public void onDescendantInvalidated(View child, View target) {
            if (this.mHostView != null) {
                if (this.mHostView instanceof ViewGroup) {
                    ((ViewGroup) this.mHostView).onDescendantInvalidated(this.mHostView, target);
                    super.onDescendantInvalidated(child, target);
                } else {
                    invalidate();
                }
            }
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
            if (this.mHostView != null) {
                dirty.offset(location[0], location[1]);
                if (this.mHostView instanceof ViewGroup) {
                    location[0] = 0;
                    location[1] = 0;
                    super.invalidateChildInParent(location, dirty);
                    return ((ViewGroup) this.mHostView).invalidateChildInParent(location, dirty);
                }
                invalidate(dirty);
                return null;
            }
            return null;
        }

        @Override // android.view.View
        public float getFrameContentVelocity() {
            if (this.mHostView != null) {
                return this.mHostView.getFrameContentVelocity();
            }
            return super.getFrameContentVelocity();
        }
    }
}
