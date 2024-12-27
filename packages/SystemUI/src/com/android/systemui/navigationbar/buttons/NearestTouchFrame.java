package com.android.systemui.navigationbar.buttons;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class NearestTouchFrame extends FrameLayout {
    public final List mAttachedChildren;
    public final NearestTouchFrame$$ExternalSyntheticLambda0 mChildRegionComparator;
    public final List mClickableChildren;
    public final boolean mIsActive;
    public boolean mIsVertical;
    public final int[] mOffset;
    public final int[] mTmpInt;
    public final Map mTouchableRegions;
    public View mTouchingChild;

    public NearestTouchFrame(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, context.getResources().getConfiguration());
    }

    public final void addClickableChildren(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.isClickable()) {
                ((ArrayList) this.mClickableChildren).add(childAt);
            } else if (childAt instanceof ViewGroup) {
                addClickableChildren((ViewGroup) childAt);
            }
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        ((ArrayList) this.mClickableChildren).clear();
        ((ArrayList) this.mAttachedChildren).clear();
        ((HashMap) this.mTouchableRegions).clear();
        addClickableChildren(this);
        getLocationInWindow(this.mOffset);
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        ((ArrayList) this.mClickableChildren).sort(this.mChildRegionComparator);
        Stream filter = this.mClickableChildren.stream().filter(new NearestTouchFrame$$ExternalSyntheticLambda1());
        final List list = this.mAttachedChildren;
        Objects.requireNonNull(list);
        filter.forEachOrdered(new Consumer() { // from class: com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                list.add((View) obj);
            }
        });
        for (int i5 = 0; i5 < ((ArrayList) this.mAttachedChildren).size(); i5++) {
            View view = (View) ((ArrayList) this.mAttachedChildren).get(i5);
            if (view.isAttachedToWindow()) {
                view.getLocationInWindow(this.mTmpInt);
                int[] iArr = this.mTmpInt;
                int i6 = iArr[0];
                int[] iArr2 = this.mOffset;
                int i7 = i6 - iArr2[0];
                int i8 = iArr[1] - iArr2[1];
                Rect rect = new Rect(i7, i8, view.getWidth() + i7, view.getHeight() + i8);
                if (i5 == 0) {
                    if (this.mIsVertical) {
                        rect.top = 0;
                    } else {
                        rect.left = 0;
                    }
                    ((HashMap) this.mTouchableRegions).put(view, rect);
                } else {
                    Rect rect2 = (Rect) ((HashMap) this.mTouchableRegions).get((View) ((ArrayList) this.mAttachedChildren).get(i5 - 1));
                    if (this.mIsVertical) {
                        int i9 = rect.top;
                        int i10 = rect2.bottom;
                        int i11 = i9 - i10;
                        int i12 = i11 / 2;
                        rect.top = i9 - i12;
                        rect2.bottom = (i12 - (i11 % 2 == 0 ? 1 : 0)) + i10;
                    } else {
                        int i13 = rect.left;
                        int i14 = rect2.right;
                        int i15 = i13 - i14;
                        int i16 = i15 / 2;
                        rect.left = i13 - i16;
                        rect2.right = (i16 - (i15 % 2 == 0 ? 1 : 0)) + i14;
                    }
                    if (i5 == ((ArrayList) this.mClickableChildren).size() - 1) {
                        if (this.mIsVertical) {
                            rect.bottom = getHeight();
                        } else {
                            rect.right = getWidth();
                        }
                    }
                    ((HashMap) this.mTouchableRegions).put(view, rect);
                }
            }
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mIsActive) {
            final int x = (int) motionEvent.getX();
            final int y = (int) motionEvent.getY();
            if (motionEvent.getAction() == 0) {
                this.mTouchingChild = (View) this.mClickableChildren.stream().filter(new NearestTouchFrame$$ExternalSyntheticLambda1()).filter(new Predicate() { // from class: com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        NearestTouchFrame nearestTouchFrame = NearestTouchFrame.this;
                        return ((Rect) ((HashMap) nearestTouchFrame.mTouchableRegions).get((View) obj)).contains(x, y);
                    }
                }).findFirst().orElse(null);
            }
            if (this.mTouchingChild != null) {
                motionEvent.offsetLocation((r2.getWidth() / 2) - x, (this.mTouchingChild.getHeight() / 2) - y);
                return this.mTouchingChild.getVisibility() == 0 && this.mTouchingChild.dispatchTouchEvent(motionEvent);
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setIsVertical(boolean z) {
        this.mIsVertical = z;
    }

    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda0] */
    public NearestTouchFrame(Context context, AttributeSet attributeSet, Configuration configuration) {
        super(context, attributeSet);
        this.mClickableChildren = new ArrayList();
        this.mAttachedChildren = new ArrayList();
        this.mTmpInt = new int[2];
        this.mOffset = new int[2];
        this.mTouchableRegions = new HashMap();
        this.mChildRegionComparator = new Comparator() { // from class: com.android.systemui.navigationbar.buttons.NearestTouchFrame$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                NearestTouchFrame nearestTouchFrame = NearestTouchFrame.this;
                boolean z = nearestTouchFrame.mIsVertical;
                ((View) obj).getLocationInWindow(nearestTouchFrame.mTmpInt);
                int[] iArr = nearestTouchFrame.mTmpInt;
                int i = iArr[z ? 1 : 0] - nearestTouchFrame.mOffset[z ? 1 : 0];
                ((View) obj2).getLocationInWindow(iArr);
                return i - (nearestTouchFrame.mTmpInt[z ? 1 : 0] - nearestTouchFrame.mOffset[z ? 1 : 0]);
            }
        };
        this.mIsActive = !BasicRune.NAVBAR_ENABLED && configuration.smallestScreenWidthDp < 600;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{R.attr.isVertical});
        this.mIsVertical = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
    }
}
