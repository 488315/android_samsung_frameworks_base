package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$styleable;
import androidx.appcompat.animation.SeslRecoilAnimator;
import androidx.appcompat.util.SeslRoundedCorner;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslLinearLayoutCompat extends LinearLayoutCompat {
    public final ItemBackgroundHolder mItemBackgroundHolder;
    public final SeslRecoilAnimator.Holder mRecoilAnimatorHolder;
    public final SeslRoundedCorner mRoundedCorner;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ItemBackgroundHolder {
        public Drawable activeBg = null;

        public ItemBackgroundHolder(SeslLinearLayoutCompat seslLinearLayoutCompat) {
        }
    }

    public SeslLinearLayoutCompat(Context context) {
        this(context, null);
    }

    public static View findChildViewUnder(View view, int i, int i2) {
        View view2 = null;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int[] iArr = {i - viewGroup.getLeft(), i2 - viewGroup.getTop()};
            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                View childAt = viewGroup.getChildAt(i3);
                if (new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()).contains(iArr[0], iArr[1]) && (view2 = findChildViewUnder(childAt, iArr[0], iArr[1])) != null) {
                    break;
                }
            }
        }
        return (view2 == null && view.isClickable() && view.getVisibility() == 0 && view.isEnabled()) ? view : view2;
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

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0014, code lost:
    
        if (r0 != 212) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00b0, code lost:
    
        if ((r0.getHeight() * r0.getWidth()) < ((r3.getHeight() * r3.getWidth()) * 0.5d)) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b5  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean dispatchTouchEvent(android.view.MotionEvent r12) {
        /*
            r11 = this;
            int r0 = r12.getAction()
            r1 = 0
            r2 = 0
            if (r0 == 0) goto L4c
            r3 = 1
            if (r0 == r3) goto L38
            r3 = 3
            if (r0 == r3) goto L18
            r3 = 211(0xd3, float:2.96E-43)
            if (r0 == r3) goto L4c
            r3 = 212(0xd4, float:2.97E-43)
            if (r0 == r3) goto L38
            goto Ld9
        L18:
            androidx.appcompat.widget.SeslLinearLayoutCompat$ItemBackgroundHolder r0 = r11.mItemBackgroundHolder
            android.graphics.drawable.Drawable r3 = r0.activeBg
            if (r3 == 0) goto L31
            boolean r4 = r3 instanceof androidx.appcompat.graphics.drawable.SeslRecoilDrawable
            if (r4 == 0) goto L2a
            androidx.appcompat.graphics.drawable.SeslRecoilDrawable r3 = (androidx.appcompat.graphics.drawable.SeslRecoilDrawable) r3
            int[] r2 = new int[r2]
            r3.setState(r2)
            goto L2f
        L2a:
            int[] r2 = new int[r2]
            r3.setState(r2)
        L2f:
            r0.activeBg = r1
        L31:
            androidx.appcompat.animation.SeslRecoilAnimator$Holder r0 = r11.mRecoilAnimatorHolder
            r0.setRelease()
            goto Ld9
        L38:
            androidx.appcompat.widget.SeslLinearLayoutCompat$ItemBackgroundHolder r0 = r11.mItemBackgroundHolder
            android.graphics.drawable.Drawable r3 = r0.activeBg
            if (r3 == 0) goto L45
            int[] r2 = new int[r2]
            r3.setState(r2)
            r0.activeBg = r1
        L45:
            androidx.appcompat.animation.SeslRecoilAnimator$Holder r0 = r11.mRecoilAnimatorHolder
            r0.setRelease()
            goto Ld9
        L4c:
            r0 = r2
        L4d:
            int r3 = r11.getChildCount()
            if (r0 >= r3) goto L80
            android.view.View r3 = r11.getChildAt(r0)
            float r4 = r12.getX()
            int r4 = (int) r4
            float r5 = r12.getY()
            int r5 = (int) r5
            int r6 = r3.getLeft()
            int r7 = r3.getTop()
            int r8 = r3.getRight()
            int r9 = r3.getBottom()
            android.graphics.Rect r10 = new android.graphics.Rect
            r10.<init>(r6, r7, r8, r9)
            boolean r4 = r10.contains(r4, r5)
            if (r4 == 0) goto L7d
            goto L81
        L7d:
            int r0 = r0 + 1
            goto L4d
        L80:
            r3 = r1
        L81:
            if (r3 != 0) goto L85
        L83:
            r0 = r1
            goto Lb3
        L85:
            float r0 = r12.getX()
            int r0 = (int) r0
            float r4 = r12.getY()
            int r4 = (int) r4
            android.view.View r0 = findChildViewUnder(r3, r0, r4)
            if (r0 == 0) goto Lb3
            if (r0 == r3) goto Lb3
            int r4 = r3.getWidth()
            int r3 = r3.getHeight()
            int r3 = r3 * r4
            int r4 = r0.getWidth()
            int r5 = r0.getHeight()
            int r5 = r5 * r4
            double r4 = (double) r5
            double r6 = (double) r3
            r8 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            double r6 = r6 * r8
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 >= 0) goto Lb3
            goto L83
        Lb3:
            if (r0 == 0) goto Ld9
            androidx.appcompat.widget.SeslLinearLayoutCompat$ItemBackgroundHolder r3 = r11.mItemBackgroundHolder
            android.graphics.drawable.Drawable r4 = r3.activeBg
            if (r4 == 0) goto Lc2
            int[] r2 = new int[r2]
            r4.setState(r2)
            r3.activeBg = r1
        Lc2:
            android.graphics.drawable.Drawable r1 = r0.getBackground()
            r3.activeBg = r1
            if (r1 == 0) goto Ld4
            r2 = 16843623(0x1010367, float:2.3696E-38)
            int[] r2 = new int[]{r2}
            r1.setState(r2)
        Ld4:
            androidx.appcompat.animation.SeslRecoilAnimator$Holder r1 = r11.mRecoilAnimatorHolder
            r1.setPress(r0)
        Ld9:
            boolean r11 = super.dispatchTouchEvent(r12)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SeslLinearLayoutCompat.dispatchTouchEvent(android.view.MotionEvent):boolean");
    }

    public SeslLinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SeslLinearLayoutCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int[] iArr = R$styleable.SeslLayout;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i, 0);
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, i, 0);
        int i2 = obtainStyledAttributes.mWrapped.getInt(1, 0);
        obtainStyledAttributes.recycle();
        SeslRoundedCorner seslRoundedCorner = new SeslRoundedCorner(context);
        this.mRoundedCorner = seslRoundedCorner;
        seslRoundedCorner.setRoundedCorners(i2);
        this.mItemBackgroundHolder = new ItemBackgroundHolder(this);
        this.mRecoilAnimatorHolder = new SeslRecoilAnimator.Holder(context);
    }
}
