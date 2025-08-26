package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$styleable;
import androidx.appcompat.animation.SeslRecoilAnimator;
import androidx.appcompat.graphics.drawable.SeslRecoilDrawable;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class SeslLinearLayoutCompat extends LinearLayoutCompat {
    public final ItemBackgroundHolder mItemBackgroundHolder;
    public final SeslRecoilAnimator.Holder mRecoilAnimatorHolder;
    public final SeslRoundedCorner mRoundedCorner;

    public class ItemBackgroundHolder {
        public Drawable activeBg = null;

        public ItemBackgroundHolder(SeslLinearLayoutCompat seslLinearLayoutCompat) {
        }
    }

    public SeslLinearLayoutCompat(Context context) {
        this(context, null);
    }

    public static View findChildViewUnder(View view, int i, int i2) {
        View viewFindChildViewUnder = null;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int[] iArr = {i - viewGroup.getLeft(), i2 - viewGroup.getTop()};
            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                View childAt = viewGroup.getChildAt(i3);
                if (new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()).contains(iArr[0], iArr[1]) && (viewFindChildViewUnder = findChildViewUnder(childAt, iArr[0], iArr[1])) != null) {
                    break;
                }
            }
        }
        return (viewFindChildViewUnder == null && view.isClickable() && view.getVisibility() == 0 && view.isEnabled()) ? view : viewFindChildViewUnder;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        SeslRoundedCorner seslRoundedCorner = this.mRoundedCorner;
        canvas.getClipBounds(seslRoundedCorner.mRoundedCornerBounds);
        seslRoundedCorner.drawRoundedCornerInternal$1(canvas);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 66) {
            if (keyEvent.getAction() == 0) {
                View focusedChild = getFocusedChild();
                if (focusedChild != null) {
                    this.mRecoilAnimatorHolder.setPress(focusedChild);
                }
            } else {
                this.mRecoilAnimatorHolder.setRelease();
            }
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0083  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        View childAt;
        int action = motionEvent.getAction();
        if (action == 0) {
            int i = 0;
            while (true) {
                if (i >= getChildCount()) {
                    childAt = null;
                    break;
                }
                childAt = getChildAt(i);
                if (new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    break;
                }
                i++;
            }
            if (childAt != null) {
                View viewFindChildViewUnder = findChildViewUnder(childAt, (int) motionEvent.getX(), (int) motionEvent.getY());
                if (viewFindChildViewUnder != null && viewFindChildViewUnder != childAt) {
                    if (viewFindChildViewUnder.getHeight() * viewFindChildViewUnder.getWidth() < childAt.getHeight() * childAt.getWidth() * 0.5d) {
                        viewFindChildViewUnder = null;
                    }
                }
                if (viewFindChildViewUnder != null) {
                    ItemBackgroundHolder itemBackgroundHolder = this.mItemBackgroundHolder;
                    Drawable drawable = itemBackgroundHolder.activeBg;
                    if (drawable != null) {
                        drawable.setState(new int[0]);
                        itemBackgroundHolder.activeBg = null;
                    }
                    Drawable background = viewFindChildViewUnder.getBackground();
                    itemBackgroundHolder.activeBg = background;
                    if (background != null) {
                        background.setState(new int[]{R.attr.state_hovered});
                    }
                    this.mRecoilAnimatorHolder.setPress(viewFindChildViewUnder);
                }
            }
        } else if (action == 1) {
            ItemBackgroundHolder itemBackgroundHolder2 = this.mItemBackgroundHolder;
            Drawable drawable2 = itemBackgroundHolder2.activeBg;
            if (drawable2 != null) {
                drawable2.setState(new int[0]);
                itemBackgroundHolder2.activeBg = null;
            }
            this.mRecoilAnimatorHolder.setRelease();
        } else if (action == 3) {
            ItemBackgroundHolder itemBackgroundHolder3 = this.mItemBackgroundHolder;
            Drawable drawable3 = itemBackgroundHolder3.activeBg;
            if (drawable3 != null) {
                if (drawable3 instanceof SeslRecoilDrawable) {
                    ((SeslRecoilDrawable) drawable3).setState(new int[0]);
                } else {
                    drawable3.setState(new int[0]);
                }
                itemBackgroundHolder3.activeBg = null;
            }
            this.mRecoilAnimatorHolder.setRelease();
        } else if (action != 211) {
            if (action == 212) {
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public SeslLinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslLinearLayoutCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int[] iArr = R$styleable.SeslLayout;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        TypedArray typedArray = tintTypedArrayObtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, i, 0);
        int i2 = tintTypedArrayObtainStyledAttributes.mWrapped.getInt(1, 0);
        tintTypedArrayObtainStyledAttributes.recycle();
        SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(context);
        this.mRoundedCorner = seslRoundedCorner;
        seslRoundedCorner.setRoundedCorners(i2);
        this.mItemBackgroundHolder = new ItemBackgroundHolder(this);
        this.mRecoilAnimatorHolder = new SeslRecoilAnimator.Holder(context);
    }
}
