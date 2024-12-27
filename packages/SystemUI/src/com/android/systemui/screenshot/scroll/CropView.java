package com.android.systemui.screenshot.scroll;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MathUtils;
import android.util.Range;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityEvent;
import android.widget.SeekBar;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.widget.ExploreByTouchHelper;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.R;
import com.android.systemui.res.R$styleable;
import java.util.ArrayList;
import java.util.List;

public class CropView extends View {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int mActivePointerId;
    public final Paint mContainerBackgroundPaint;
    public RectF mCrop;
    public CropInteractionListener mCropInteractionListener;
    public final float mCropTouchMargin;
    public CropBoundary mCurrentDraggingBoundary;
    public final float mEntranceInterpolation;
    public final AccessibilityHelper mExploreByTouchHelper;
    public int mExtraBottomPadding;
    public int mExtraTopPadding;
    public final Paint mHandlePaint;
    public int mImageWidth;
    public Range mMotionRange;
    public float mMovementStartValue;
    public final Paint mShadePaint;
    public float mStartingX;
    public float mStartingY;

    public final class AccessibilityHelper extends ExploreByTouchHelper {
        public AccessibilityHelper() {
            super(CropView.this);
        }

        public static CropBoundary viewIdToBoundary(int i) {
            return i != 1 ? i != 2 ? i != 3 ? i != 4 ? CropBoundary.NONE : CropBoundary.RIGHT : CropBoundary.LEFT : CropBoundary.BOTTOM : CropBoundary.TOP;
        }

        public final CharSequence getBoundaryContentDescription(CropBoundary cropBoundary) {
            int i;
            int ordinal = cropBoundary.ordinal();
            if (ordinal == 1) {
                i = R.string.screenshot_top_boundary_pct;
            } else if (ordinal == 2) {
                i = R.string.screenshot_bottom_boundary_pct;
            } else if (ordinal == 3) {
                i = R.string.screenshot_left_boundary_pct;
            } else {
                if (ordinal != 4) {
                    return "";
                }
                i = R.string.screenshot_right_boundary_pct;
            }
            CropView cropView = CropView.this;
            Resources resources = cropView.getResources();
            int i2 = CropView.$r8$clinit;
            return resources.getString(i, Integer.valueOf(Math.round(cropView.getBoundaryPosition(cropBoundary) * 100.0f)));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            CropView cropView = CropView.this;
            if (Math.abs(f2 - cropView.fractionToVerticalPixels(cropView.mCrop.top)) < cropView.mCropTouchMargin) {
                return 1;
            }
            if (Math.abs(f2 - cropView.fractionToVerticalPixels(cropView.mCrop.bottom)) < cropView.mCropTouchMargin) {
                return 2;
            }
            if (f2 <= cropView.fractionToVerticalPixels(cropView.mCrop.top) || f2 >= cropView.fractionToVerticalPixels(cropView.mCrop.bottom)) {
                return -1;
            }
            if (Math.abs(f - cropView.fractionToHorizontalPixels(cropView.mCrop.left)) < cropView.mCropTouchMargin) {
                return 3;
            }
            return Math.abs(f - ((float) cropView.fractionToHorizontalPixels(cropView.mCrop.right))) < cropView.mCropTouchMargin ? 4 : -1;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            ArrayList arrayList = (ArrayList) list;
            arrayList.add(1);
            arrayList.add(3);
            arrayList.add(4);
            arrayList.add(2);
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2) {
            ViewParent parent;
            if (i2 != 4096 && i2 != 8192) {
                return false;
            }
            CropBoundary viewIdToBoundary = viewIdToBoundary(i);
            CropView cropView = CropView.this;
            float pixelDistanceToFraction = cropView.pixelDistanceToFraction(cropView.mCropTouchMargin, viewIdToBoundary);
            if (i2 == 4096) {
                pixelDistanceToFraction = -pixelDistanceToFraction;
            }
            cropView.setBoundaryPosition(cropView.getBoundaryPosition(viewIdToBoundary) + pixelDistanceToFraction, viewIdToBoundary);
            if (i != Integer.MIN_VALUE && this.mManager.isEnabled() && (parent = this.mHost.getParent()) != null) {
                AccessibilityEvent createEvent$1 = createEvent$1(i, 2048);
                createEvent$1.setContentChangeTypes(0);
                parent.requestSendAccessibilityEvent(this.mHost, createEvent$1);
            }
            sendEventForVirtualView(i, 4);
            return true;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setContentDescription(getBoundaryContentDescription(viewIdToBoundary(i)));
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Rect rect;
            CropBoundary viewIdToBoundary = viewIdToBoundary(i);
            accessibilityNodeInfoCompat.setContentDescription(getBoundaryContentDescription(viewIdToBoundary));
            boolean isVertical = CropView.isVertical(viewIdToBoundary);
            CropView cropView = CropView.this;
            if (isVertical) {
                float fractionToVerticalPixels = cropView.fractionToVerticalPixels(cropView.getBoundaryPosition(viewIdToBoundary));
                rect = new Rect(0, (int) (fractionToVerticalPixels - cropView.mCropTouchMargin), cropView.getWidth(), (int) (fractionToVerticalPixels + cropView.mCropTouchMargin));
                int i2 = rect.top;
                if (i2 < 0) {
                    rect.offset(0, -i2);
                }
            } else {
                float fractionToHorizontalPixels = cropView.fractionToHorizontalPixels(cropView.getBoundaryPosition(viewIdToBoundary));
                int i3 = (int) (fractionToHorizontalPixels - cropView.mCropTouchMargin);
                float fractionToVerticalPixels2 = cropView.fractionToVerticalPixels(cropView.mCrop.top);
                float f = cropView.mCropTouchMargin;
                rect = new Rect(i3, (int) (fractionToVerticalPixels2 + f), (int) (fractionToHorizontalPixels + f), (int) (cropView.fractionToVerticalPixels(cropView.mCrop.bottom) - cropView.mCropTouchMargin));
            }
            accessibilityNodeInfoCompat.setBoundsInParent(rect);
            int[] iArr = new int[2];
            cropView.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            accessibilityNodeInfoCompat.mInfo.setBoundsInScreen(rect);
            accessibilityNodeInfoCompat.setClassName(SeekBar.class.getName());
            accessibilityNodeInfoCompat.addAction(4096);
            accessibilityNodeInfoCompat.addAction(8192);
        }
    }

    public enum CropBoundary {
        NONE,
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public interface CropInteractionListener {
    }

    public final class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: com.android.systemui.screenshot.scroll.CropView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, 0);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public RectF mCrop;

        public /* synthetic */ SavedState(Parcel parcel, int i) {
            this(parcel);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.mCrop, 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.mCrop = (RectF) parcel.readParcelable(ClassLoader.getSystemClassLoader());
        }
    }

    public CropView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public static boolean isVertical(CropBoundary cropBoundary) {
        return cropBoundary == CropBoundary.TOP || cropBoundary == CropBoundary.BOTTOM;
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return this.mExploreByTouchHelper.dispatchHoverEvent(motionEvent) || super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        boolean z;
        AccessibilityHelper accessibilityHelper = this.mExploreByTouchHelper;
        accessibilityHelper.getClass();
        if (keyEvent.getAction() != 1) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 61) {
                int i = 66;
                if (keyCode != 66) {
                    switch (keyCode) {
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                            if (keyEvent.hasNoModifiers()) {
                                if (keyCode == 19) {
                                    i = 33;
                                } else if (keyCode == 21) {
                                    i = 17;
                                } else if (keyCode != 22) {
                                    i = 130;
                                }
                                int repeatCount = keyEvent.getRepeatCount() + 1;
                                int i2 = 0;
                                z = false;
                                while (i2 < repeatCount && accessibilityHelper.moveFocus(i, null)) {
                                    i2++;
                                    z = true;
                                }
                            }
                            break;
                    }
                }
                if (keyEvent.hasNoModifiers() && keyEvent.getRepeatCount() == 0) {
                    int i3 = accessibilityHelper.mKeyboardFocusedVirtualViewId;
                    if (i3 != Integer.MIN_VALUE) {
                        accessibilityHelper.onPerformActionForVirtualView(i3, 16);
                    }
                    z = true;
                }
            } else if (keyEvent.hasNoModifiers()) {
                z = accessibilityHelper.moveFocus(2, null);
            } else if (keyEvent.hasModifiers(1)) {
                z = accessibilityHelper.moveFocus(1, null);
            }
            return !z || super.dispatchKeyEvent(keyEvent);
        }
        z = false;
        if (z) {
        }
    }

    public final void drawHorizontalHandle(Canvas canvas, float f, boolean z) {
        float fractionToVerticalPixels = fractionToVerticalPixels(f);
        canvas.drawLine(fractionToHorizontalPixels(this.mCrop.left), fractionToVerticalPixels, fractionToHorizontalPixels(this.mCrop.right), fractionToVerticalPixels, this.mHandlePaint);
        float f2 = getResources().getDisplayMetrics().density * 8.0f;
        float fractionToHorizontalPixels = (fractionToHorizontalPixels(this.mCrop.right) + fractionToHorizontalPixels(this.mCrop.left)) / 2;
        canvas.drawArc(fractionToHorizontalPixels - f2, fractionToVerticalPixels - f2, fractionToHorizontalPixels + f2, fractionToVerticalPixels + f2, z ? 180.0f : 0.0f, 180.0f, true, this.mHandlePaint);
    }

    public final void drawShade(Canvas canvas, float f, float f2, float f3, float f4) {
        canvas.drawRect(fractionToHorizontalPixels(f), fractionToVerticalPixels(f2), fractionToHorizontalPixels(f3), fractionToVerticalPixels(f4), this.mShadePaint);
    }

    public final void drawVerticalHandle(Canvas canvas, float f, boolean z) {
        float fractionToHorizontalPixels = fractionToHorizontalPixels(f);
        canvas.drawLine(fractionToHorizontalPixels, fractionToVerticalPixels(this.mCrop.top), fractionToHorizontalPixels, fractionToVerticalPixels(this.mCrop.bottom), this.mHandlePaint);
        float f2 = getResources().getDisplayMetrics().density * 8.0f;
        float f3 = fractionToHorizontalPixels - f2;
        float fractionToVerticalPixels = (fractionToVerticalPixels(getBoundaryPosition(CropBoundary.BOTTOM)) + fractionToVerticalPixels(getBoundaryPosition(CropBoundary.TOP))) / 2;
        canvas.drawArc(f3, fractionToVerticalPixels - f2, fractionToHorizontalPixels + f2, fractionToVerticalPixels + f2, z ? 90.0f : 270.0f, 180.0f, true, this.mHandlePaint);
    }

    public final int fractionToHorizontalPixels(float f) {
        int width = getWidth();
        return (int) ((f * this.mImageWidth) + ((width - r1) / 2));
    }

    public final int fractionToVerticalPixels(float f) {
        return (int) ((f * ((getHeight() - this.mExtraTopPadding) - this.mExtraBottomPadding)) + this.mExtraTopPadding);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.util.Range getAllowedValues(com.android.systemui.screenshot.scroll.CropView.CropBoundary r4) {
        /*
            r3 = this;
            int r4 = r4.ordinal()
            r0 = 1
            r1 = 0
            if (r4 == r0) goto L41
            r0 = 2
            r2 = 1065353216(0x3f800000, float:1.0)
            if (r4 == r0) goto L34
            r0 = 3
            if (r4 == r0) goto L26
            r0 = 4
            if (r4 == r0) goto L16
            r4 = r1
            r1 = r2
            goto L4e
        L16:
            android.graphics.RectF r4 = r3.mCrop
            float r4 = r4.left
            float r0 = r3.mCropTouchMargin
            com.android.systemui.screenshot.scroll.CropView$CropBoundary r1 = com.android.systemui.screenshot.scroll.CropView.CropBoundary.LEFT
            float r3 = r3.pixelDistanceToFraction(r0, r1)
        L22:
            float r1 = r3 + r4
            r4 = r2
            goto L4e
        L26:
            android.graphics.RectF r4 = r3.mCrop
            float r4 = r4.right
            float r0 = r3.mCropTouchMargin
            com.android.systemui.screenshot.scroll.CropView$CropBoundary r2 = com.android.systemui.screenshot.scroll.CropView.CropBoundary.RIGHT
            float r3 = r3.pixelDistanceToFraction(r0, r2)
        L32:
            float r4 = r4 - r3
            goto L4e
        L34:
            android.graphics.RectF r4 = r3.mCrop
            float r4 = r4.top
            float r0 = r3.mCropTouchMargin
            com.android.systemui.screenshot.scroll.CropView$CropBoundary r1 = com.android.systemui.screenshot.scroll.CropView.CropBoundary.TOP
            float r3 = r3.pixelDistanceToFraction(r0, r1)
            goto L22
        L41:
            android.graphics.RectF r4 = r3.mCrop
            float r4 = r4.bottom
            float r0 = r3.mCropTouchMargin
            com.android.systemui.screenshot.scroll.CropView$CropBoundary r2 = com.android.systemui.screenshot.scroll.CropView.CropBoundary.BOTTOM
            float r3 = r3.pixelDistanceToFraction(r0, r2)
            goto L32
        L4e:
            int r3 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r3 < 0) goto L7c
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r0 = "getAllowedValues computed an invalid range ["
            r3.<init>(r0)
            r3.append(r1)
            java.lang.String r0 = ", "
            r3.append(r0)
            r3.append(r4)
            java.lang.String r0 = "]"
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            java.lang.String r0 = "CropView"
            android.util.Log.wtf(r0, r3)
            com.android.systemui.FeatureFlagsImpl r3 = com.android.systemui.Flags.FEATURE_FLAGS
            r3.getClass()
            float r1 = java.lang.Math.min(r1, r4)
            r4 = r1
        L7c:
            android.util.Range r3 = new android.util.Range
            java.lang.Float r0 = java.lang.Float.valueOf(r1)
            java.lang.Float r4 = java.lang.Float.valueOf(r4)
            r3.<init>(r0, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.scroll.CropView.getAllowedValues(com.android.systemui.screenshot.scroll.CropView$CropBoundary):android.util.Range");
    }

    public final float getBoundaryPosition(CropBoundary cropBoundary) {
        int ordinal = cropBoundary.ordinal();
        if (ordinal == 1) {
            return this.mCrop.top;
        }
        if (ordinal == 2) {
            return this.mCrop.bottom;
        }
        if (ordinal == 3) {
            return this.mCrop.left;
        }
        if (ordinal != 4) {
            return 0.0f;
        }
        return this.mCrop.right;
    }

    public final Rect getCropBoundaries(int i, int i2) {
        RectF rectF = this.mCrop;
        float f = i;
        float f2 = i2;
        return new Rect((int) (rectF.left * f), (int) (rectF.top * f2), (int) (rectF.right * f), (int) (rectF.bottom * f2));
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float lerp = MathUtils.lerp(this.mCrop.top, 0.0f, this.mEntranceInterpolation);
        float lerp2 = MathUtils.lerp(this.mCrop.bottom, 1.0f, this.mEntranceInterpolation);
        drawShade(canvas, 0.0f, lerp, 1.0f, this.mCrop.top);
        drawShade(canvas, 0.0f, this.mCrop.bottom, 1.0f, lerp2);
        RectF rectF = this.mCrop;
        drawShade(canvas, 0.0f, rectF.top, rectF.left, rectF.bottom);
        RectF rectF2 = this.mCrop;
        drawShade(canvas, rectF2.right, rectF2.top, 1.0f, rectF2.bottom);
        canvas.drawRect(fractionToHorizontalPixels(0.0f), fractionToVerticalPixels(0.0f), fractionToHorizontalPixels(1.0f), fractionToVerticalPixels(lerp), this.mContainerBackgroundPaint);
        canvas.drawRect(fractionToHorizontalPixels(0.0f), fractionToVerticalPixels(lerp2), fractionToHorizontalPixels(1.0f), fractionToVerticalPixels(1.0f), this.mContainerBackgroundPaint);
        this.mHandlePaint.setAlpha((int) (this.mEntranceInterpolation * 255.0f));
        drawHorizontalHandle(canvas, this.mCrop.top, true);
        drawHorizontalHandle(canvas, this.mCrop.bottom, false);
        drawVerticalHandle(canvas, this.mCrop.left, true);
        drawVerticalHandle(canvas, this.mCrop.right, false);
    }

    @Override // android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        AccessibilityHelper accessibilityHelper = this.mExploreByTouchHelper;
        int i2 = accessibilityHelper.mKeyboardFocusedVirtualViewId;
        if (i2 != Integer.MIN_VALUE) {
            accessibilityHelper.clearKeyboardFocusForVirtualView(i2);
        }
        if (z) {
            accessibilityHelper.moveFocus(i, rect);
        }
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        Log.d("CropView", "onRestoreInstanceState");
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        Log.d("CropView", "restoring mCrop=" + savedState.mCrop + " (was " + this.mCrop + ")");
        this.mCrop = savedState.mCrop;
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        Log.d("CropView", "onSaveInstanceState");
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mCrop = this.mCrop;
        Log.d("CropView", "saving mCrop=" + this.mCrop);
        return savedState;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        CropBoundary cropBoundary;
        float x;
        float f;
        int fractionToVerticalPixels = fractionToVerticalPixels(this.mCrop.top);
        int fractionToVerticalPixels2 = fractionToVerticalPixels(this.mCrop.bottom);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            int fractionToHorizontalPixels = fractionToHorizontalPixels(this.mCrop.left);
            int fractionToHorizontalPixels2 = fractionToHorizontalPixels(this.mCrop.right);
            float f2 = fractionToVerticalPixels;
            if (Math.abs(motionEvent.getY() - f2) < this.mCropTouchMargin) {
                cropBoundary = CropBoundary.TOP;
            } else {
                float f3 = fractionToVerticalPixels2;
                if (Math.abs(motionEvent.getY() - f3) < this.mCropTouchMargin) {
                    cropBoundary = CropBoundary.BOTTOM;
                } else {
                    if (motionEvent.getY() > f2 || motionEvent.getY() < f3) {
                        if (Math.abs(motionEvent.getX() - fractionToHorizontalPixels) < this.mCropTouchMargin) {
                            cropBoundary = CropBoundary.LEFT;
                        } else if (Math.abs(motionEvent.getX() - fractionToHorizontalPixels2) < this.mCropTouchMargin) {
                            cropBoundary = CropBoundary.RIGHT;
                        }
                    }
                    cropBoundary = CropBoundary.NONE;
                }
            }
            this.mCurrentDraggingBoundary = cropBoundary;
            if (cropBoundary != CropBoundary.NONE) {
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mStartingY = motionEvent.getY();
                this.mStartingX = motionEvent.getX();
                this.mMovementStartValue = getBoundaryPosition(this.mCurrentDraggingBoundary);
                updateListener(motionEvent.getX(), 0);
                this.mMotionRange = getAllowedValues(this.mCurrentDraggingBoundary);
            }
            return true;
        }
        if (actionMasked != 1) {
            if (actionMasked != 2) {
                if (actionMasked != 3) {
                    if (actionMasked != 5) {
                        if (actionMasked == 6 && this.mActivePointerId == motionEvent.getPointerId(motionEvent.getActionIndex()) && this.mCurrentDraggingBoundary != CropBoundary.NONE) {
                            updateListener(motionEvent.getX(motionEvent.getActionIndex()), 1);
                            return true;
                        }
                    } else if (this.mActivePointerId == motionEvent.getPointerId(motionEvent.getActionIndex()) && this.mCurrentDraggingBoundary != CropBoundary.NONE) {
                        updateListener(motionEvent.getX(motionEvent.getActionIndex()), 0);
                        return true;
                    }
                }
            } else if (this.mCurrentDraggingBoundary != CropBoundary.NONE) {
                int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (findPointerIndex >= 0) {
                    if (isVertical(this.mCurrentDraggingBoundary)) {
                        x = motionEvent.getY(findPointerIndex);
                        f = this.mStartingY;
                    } else {
                        x = motionEvent.getX(findPointerIndex);
                        f = this.mStartingX;
                    }
                    setBoundaryPosition(((Float) this.mMotionRange.clamp(Float.valueOf(this.mMovementStartValue + pixelDistanceToFraction((int) (x - f), this.mCurrentDraggingBoundary)))).floatValue(), this.mCurrentDraggingBoundary);
                    updateListener(motionEvent.getX(findPointerIndex), 2);
                    invalidate();
                }
                return true;
            }
            return super.onTouchEvent(motionEvent);
        }
        if (this.mCurrentDraggingBoundary != CropBoundary.NONE) {
            int i = this.mActivePointerId;
            if (i == motionEvent.getPointerId(i)) {
                updateListener(motionEvent.getX(0), 1);
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public final float pixelDistanceToFraction(float f, CropBoundary cropBoundary) {
        return f / (isVertical(cropBoundary) ? (getHeight() - this.mExtraTopPadding) - this.mExtraBottomPadding : this.mImageWidth);
    }

    public final void setBoundaryPosition(float f, CropBoundary cropBoundary) {
        Log.i("CropView", "setBoundaryPosition: " + cropBoundary + ", position=" + f);
        float floatValue = ((Float) getAllowedValues(cropBoundary).clamp(Float.valueOf(f))).floatValue();
        int ordinal = cropBoundary.ordinal();
        if (ordinal == 0) {
            Log.w("CropView", "No boundary selected");
        } else if (ordinal == 1) {
            this.mCrop.top = floatValue;
        } else if (ordinal == 2) {
            this.mCrop.bottom = floatValue;
        } else if (ordinal == 3) {
            this.mCrop.left = floatValue;
        } else if (ordinal == 4) {
            this.mCrop.right = floatValue;
        }
        Log.i("CropView", "Updated mCrop: " + this.mCrop);
        invalidate();
    }

    public final void updateListener(float f, int i) {
        if (this.mCropInteractionListener == null || !isVertical(this.mCurrentDraggingBoundary)) {
            return;
        }
        float boundaryPosition = getBoundaryPosition(this.mCurrentDraggingBoundary);
        if (i == 0) {
            CropInteractionListener cropInteractionListener = this.mCropInteractionListener;
            CropBoundary cropBoundary = this.mCurrentDraggingBoundary;
            int fractionToVerticalPixels = fractionToVerticalPixels(boundaryPosition);
            RectF rectF = this.mCrop;
            float f2 = (rectF.left + rectF.right) / 2.0f;
            MagnifierView magnifierView = (MagnifierView) cropInteractionListener;
            magnifierView.mCropBoundary = cropBoundary;
            magnifierView.mLastCenter = f2;
            float parentWidth = f > ((float) (magnifierView.getParentWidth() / 2)) ? 0.0f : magnifierView.getParentWidth() - magnifierView.getWidth();
            magnifierView.mLastCropPosition = boundaryPosition;
            magnifierView.setTranslationY(fractionToVerticalPixels - (magnifierView.getHeight() / 2));
            magnifierView.setPivotX(magnifierView.getWidth() / 2);
            magnifierView.setPivotY(magnifierView.getHeight() / 2);
            magnifierView.setScaleX(0.2f);
            magnifierView.setScaleY(0.2f);
            magnifierView.setAlpha(0.0f);
            magnifierView.setTranslationX((magnifierView.getParentWidth() - magnifierView.getWidth()) / 2);
            magnifierView.setVisibility(0);
            ViewPropertyAnimator scaleY = magnifierView.animate().alpha(1.0f).translationX(parentWidth).scaleX(1.0f).scaleY(1.0f);
            magnifierView.mTranslationAnimator = scaleY;
            scaleY.setListener(magnifierView.mTranslationAnimatorListener);
            magnifierView.mTranslationAnimator.start();
            return;
        }
        if (i == 1) {
            final MagnifierView magnifierView2 = (MagnifierView) this.mCropInteractionListener;
            magnifierView2.animate().alpha(0.0f).translationX((magnifierView2.getParentWidth() - magnifierView2.getWidth()) / 2).scaleX(0.2f).scaleY(0.2f).withEndAction(new Runnable() { // from class: com.android.systemui.screenshot.scroll.MagnifierView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MagnifierView magnifierView3 = MagnifierView.this;
                    int i2 = MagnifierView.$r8$clinit;
                    magnifierView3.setVisibility(4);
                }
            }).start();
            return;
        }
        if (i != 2) {
            return;
        }
        CropInteractionListener cropInteractionListener2 = this.mCropInteractionListener;
        int fractionToVerticalPixels2 = fractionToVerticalPixels(boundaryPosition);
        float f3 = this.mCrop.left;
        MagnifierView magnifierView3 = (MagnifierView) cropInteractionListener2;
        boolean z = f > ((float) (magnifierView3.getParentWidth() / 2));
        float parentWidth2 = z ? 0.0f : magnifierView3.getParentWidth() - magnifierView3.getWidth();
        boolean z2 = Math.abs(f - ((float) (magnifierView3.getParentWidth() / 2))) < ((float) magnifierView3.getParentWidth()) / 10.0f;
        boolean z3 = magnifierView3.getTranslationX() < ((float) ((magnifierView3.getParentWidth() - magnifierView3.getWidth()) / 2));
        if (!z2 && z3 != z && magnifierView3.mTranslationAnimator == null) {
            ViewPropertyAnimator translationX = magnifierView3.animate().translationX(parentWidth2);
            magnifierView3.mTranslationAnimator = translationX;
            translationX.setListener(magnifierView3.mTranslationAnimatorListener);
            magnifierView3.mTranslationAnimator.start();
        }
        magnifierView3.mLastCropPosition = boundaryPosition;
        magnifierView3.setTranslationY(fractionToVerticalPixels2 - (magnifierView3.getHeight() / 2));
        magnifierView3.invalidate();
    }

    public CropView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCrop = new RectF(0.0f, 0.0f, 1.0f, 1.0f);
        this.mCurrentDraggingBoundary = CropBoundary.NONE;
        this.mEntranceInterpolation = 1.0f;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.CropView, 0, 0);
        Paint paint = new Paint();
        this.mShadePaint = paint;
        paint.setColor(ColorUtils.setAlphaComponent(obtainStyledAttributes.getColor(4, 0), obtainStyledAttributes.getInteger(3, 255)));
        Paint paint2 = new Paint();
        this.mContainerBackgroundPaint = paint2;
        paint2.setColor(obtainStyledAttributes.getColor(0, 0));
        Paint paint3 = new Paint();
        this.mHandlePaint = paint3;
        paint3.setColor(obtainStyledAttributes.getColor(1, -16777216));
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStrokeWidth(obtainStyledAttributes.getDimensionPixelSize(2, 20));
        obtainStyledAttributes.recycle();
        this.mCropTouchMargin = getResources().getDisplayMetrics().density * 24.0f;
        AccessibilityHelper accessibilityHelper = new AccessibilityHelper();
        this.mExploreByTouchHelper = accessibilityHelper;
        ViewCompat.setAccessibilityDelegate(this, accessibilityHelper);
    }
}
