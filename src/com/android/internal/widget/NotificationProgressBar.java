package com.android.internal.widget;

import android.app.Notification;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.RemotableViewMethod;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.NotificationProgressDrawable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public final class NotificationProgressBar extends ProgressBar implements NotificationProgressDrawable.BoundsChangeListener {
    private static final boolean DEBUG = false;
    private static final float FADED_OPACITY = 0.5f;
    private static final String TAG = "NotificationProgressBar";
    private float mAdjustedProgressFraction;
    private boolean mHasTrackerIcon;
    private Animatable2.AnimationCallback mIndeterminateAnimationCallback;
    private final Matrix mMatrix;
    private NotificationProgressDrawable mNotificationProgressDrawable;
    private List<Part> mParts;
    private final Rect mProgressDrawableBounds;
    private List<NotificationProgressDrawable.DrawablePart> mProgressDrawableParts;
    private float mProgressFraction;
    private NotificationProgressModel mProgressModel;
    private final float mSegMinWidth;
    private final float mSegPointGap;
    private final float mSegSegGap;
    private Drawable mTracker;
    private Matrix mTrackerDrawMatrix;
    private int mTrackerDrawWidth;
    private final int mTrackerHeight;
    private int mTrackerPos;
    private boolean mTrackerPosIsDirty;

    public interface Part {
    }

    private static float getProgressFraction(int i, int i2) {
        if (i > 0) {
            return i2 / i;
        }
        return 0.0f;
    }

    public NotificationProgressBar(Context context) {
        this(context, null);
    }

    public NotificationProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842871);
    }

    public NotificationProgressBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationProgressBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIndeterminateAnimationCallback = null;
        this.mProgressDrawableBounds = new Rect();
        this.mParts = null;
        this.mProgressDrawableParts = null;
        this.mTracker = null;
        this.mHasTrackerIcon = false;
        this.mTrackerDrawWidth = 0;
        this.mMatrix = new Matrix();
        this.mTrackerDrawMatrix = null;
        this.mProgressFraction = 0.0f;
        this.mAdjustedProgressFraction = 0.0f;
        this.mTrackerPosIsDirty = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.NotificationProgressBar, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.NotificationProgressBar, attributeSet, obtainStyledAttributes, i, i2);
        try {
            NotificationProgressDrawable notificationProgressDrawable = getNotificationProgressDrawable();
            this.mNotificationProgressDrawable = notificationProgressDrawable;
            notificationProgressDrawable.setBoundsChangeListener(this);
        } catch (IllegalStateException e) {
            Log.e(TAG, "Can't get NotificationProgressDrawable", e);
        }
        this.mSegMinWidth = obtainStyledAttributes.getDimension(0, 0.0f);
        this.mSegSegGap = obtainStyledAttributes.getDimension(2, 0.0f);
        this.mSegPointGap = obtainStyledAttributes.getDimension(1, 0.0f);
        lambda$setProgressTrackerIconAsync$0(obtainStyledAttributes.getDrawable(3));
        this.mTrackerHeight = obtainStyledAttributes.getDimensionPixelSize(4, 0);
    }

    @Override // android.widget.ProgressBar
    public void setIndeterminateDrawable(Drawable drawable) {
        Drawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable != drawable) {
            Animatable2.AnimationCallback animationCallback = this.mIndeterminateAnimationCallback;
            if (animationCallback != null) {
                ((AnimatedVectorDrawable) indeterminateDrawable).unregisterAnimationCallback(animationCallback);
                this.mIndeterminateAnimationCallback = null;
            }
            if (drawable instanceof AnimatedVectorDrawable) {
                Animatable2.AnimationCallback animationCallback2 = new Animatable2.AnimationCallback() { // from class: com.android.internal.widget.NotificationProgressBar.1
                    @Override // android.graphics.drawable.Animatable2.AnimationCallback
                    public void onAnimationEnd(Drawable drawable2) {
                        super.onAnimationEnd(drawable2);
                        if (NotificationProgressBar.this.shouldLoopIndeterminateAnimation()) {
                            ((AnimatedVectorDrawable) drawable2).start();
                        }
                    }
                };
                this.mIndeterminateAnimationCallback = animationCallback2;
                ((AnimatedVectorDrawable) drawable).registerAnimationCallback(animationCallback2);
            }
        }
        super.setIndeterminateDrawable(drawable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldLoopIndeterminateAnimation() {
        return isIndeterminate() && isAttachedToWindow() && isAggregatedVisible();
    }

    @RemotableViewMethod
    public void setProgressModel(Bundle bundle) {
        Preconditions.checkArgument(bundle != null, "Bundle shouldn't be null");
        NotificationProgressModel fromBundle = NotificationProgressModel.fromBundle(bundle);
        this.mProgressModel = fromBundle;
        boolean isIndeterminate = fromBundle.isIndeterminate();
        setIndeterminate(isIndeterminate);
        if (isIndeterminate) {
            setIndeterminateTintList(ColorStateList.valueOf(this.mProgressModel.getIndeterminateColor()));
            return;
        }
        int progress = this.mProgressModel.getProgress();
        int progressMax = this.mProgressModel.getProgressMax();
        this.mParts = processModelAndConvertToViewParts(this.mProgressModel.getSegments(), this.mProgressModel.getPoints(), progress, progressMax);
        setMax(progressMax);
        setProgress(progress);
        NotificationProgressDrawable notificationProgressDrawable = this.mNotificationProgressDrawable;
        if (notificationProgressDrawable == null || notificationProgressDrawable.getBounds().width() == 0) {
            return;
        }
        updateDrawableParts();
    }

    private NotificationProgressDrawable getNotificationProgressDrawable() {
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable == null) {
            throw new IllegalStateException("getProgressDrawable() returns null");
        }
        if (!(progressDrawable instanceof LayerDrawable)) {
            throw new IllegalStateException("getProgressDrawable() doesn't return a LayerDrawable");
        }
        Drawable findDrawableByLayerId = ((LayerDrawable) progressDrawable).findDrawableByLayerId(16908288);
        if (!(findDrawableByLayerId instanceof NotificationProgressDrawable)) {
            StringBuilder sb = new StringBuilder("Couldn't get NotificationProgressDrawable, retrieved drawable is: ");
            sb.append(findDrawableByLayerId != null ? findDrawableByLayerId.toString() : null);
            throw new IllegalStateException(sb.toString());
        }
        return (NotificationProgressDrawable) findDrawableByLayerId;
    }

    @RemotableViewMethod(asyncImpl = "setProgressTrackerIconAsync")
    public void setProgressTrackerIcon(Icon icon) {
        lambda$setProgressTrackerIconAsync$0(icon != null ? icon.loadDrawable(getContext()) : null);
    }

    public Runnable setProgressTrackerIconAsync(Icon icon) {
        final Drawable loadDrawable = icon != null ? icon.loadDrawable(getContext()) : null;
        return new Runnable() { // from class: com.android.internal.widget.NotificationProgressBar$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationProgressBar.this.lambda$setProgressTrackerIconAsync$0(loadDrawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setTracker, reason: merged with bridge method [inline-methods] */
    public void lambda$setProgressTrackerIconAsync$0(Drawable drawable) {
        Drawable drawable2 = this.mTracker;
        if (drawable == drawable2) {
            return;
        }
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        if (drawable != null) {
            drawable.setCallback(this);
            if (getMirrorForRtl()) {
                drawable.setAutoMirrored(true);
            }
            if (canResolveLayoutDirection()) {
                drawable.setLayoutDirection(getLayoutDirection());
            }
        }
        boolean trackerSizeChanged = trackerSizeChanged(drawable, this.mTracker);
        this.mTracker = drawable;
        boolean z = drawable != null;
        if (this.mHasTrackerIcon != z) {
            this.mHasTrackerIcon = z;
            NotificationProgressDrawable notificationProgressDrawable = this.mNotificationProgressDrawable;
            if (notificationProgressDrawable != null && notificationProgressDrawable.getBounds().width() != 0 && this.mProgressModel.isStyledByProgress()) {
                updateDrawableParts();
            }
        }
        configureTrackerBounds();
        updateTrackerAndBarPos(getWidth(), getHeight());
        if (trackerSizeChanged) {
            requestLayout();
        }
        invalidate();
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    private static boolean trackerSizeChanged(Drawable drawable, Drawable drawable2) {
        if (drawable == null && drawable2 == null) {
            return false;
        }
        if (drawable != null || drawable2 == null) {
            return ((drawable == null || drawable2 != null) && drawable.getIntrinsicWidth() == drawable2.getIntrinsicWidth() && drawable.getIntrinsicHeight() == drawable2.getIntrinsicHeight()) ? false : true;
        }
        return true;
    }

    private void configureTrackerBounds() {
        float f;
        float f2;
        float f3;
        this.mTrackerDrawMatrix = null;
        this.mTrackerDrawWidth = 0;
        Drawable drawable = this.mTracker;
        if (drawable == null) {
            return;
        }
        if (this.mTrackerHeight <= 0) {
            this.mTrackerDrawWidth = drawable.getIntrinsicWidth();
            return;
        }
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = this.mTracker.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return;
        }
        int i = intrinsicHeight * 2;
        int i2 = intrinsicWidth * 2;
        Matrix matrix = this.mMatrix;
        this.mTrackerDrawMatrix = matrix;
        float f4 = 0.0f;
        if (intrinsicWidth > i) {
            f = this.mTrackerHeight / intrinsicHeight;
            float f5 = i * f;
            this.mTrackerDrawWidth = (int) f5;
            f3 = 0.0f;
            f4 = (f5 - (intrinsicWidth * f)) * 0.5f;
        } else if (intrinsicHeight > i2) {
            int i3 = this.mTrackerHeight;
            float f6 = (i3 * 0.5f) / intrinsicWidth;
            this.mTrackerDrawWidth = i3 / 2;
            f3 = ((i2 * f6) - (intrinsicHeight * f6)) * 0.5f;
            f = f6;
        } else {
            f = this.mTrackerHeight / intrinsicHeight;
            this.mTrackerDrawWidth = (int) (intrinsicWidth * f);
            f2 = 0.0f;
            matrix.setScale(f, f);
            this.mTrackerDrawMatrix.postTranslate(Math.round(f4), Math.round(f2));
        }
        f2 = f3;
        matrix.setScale(f, f);
        this.mTrackerDrawMatrix.postTranslate(Math.round(f4), Math.round(f2));
    }

    @Override // android.widget.ProgressBar
    public synchronized void setProgress(int i) {
        super.setProgress(i);
        onMaybeVisualProgressChanged();
    }

    @Override // android.widget.ProgressBar
    public void setProgress(int i, boolean z) {
        super.setProgress(i, false);
        onMaybeVisualProgressChanged();
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMin(int i) {
        super.setMin(i);
        onMaybeVisualProgressChanged();
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMax(int i) {
        super.setMax(i);
        onMaybeVisualProgressChanged();
    }

    private void onMaybeVisualProgressChanged() {
        float progressFraction = getProgressFraction();
        if (this.mProgressFraction == progressFraction) {
            return;
        }
        this.mProgressFraction = progressFraction;
        this.mTrackerPosIsDirty = true;
        invalidate();
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mTracker || super.verifyDrawable(drawable);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mTracker;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mTracker;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mTracker;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateTrackerAndBarPos(i, i2);
    }

    @Override // com.android.internal.widget.NotificationProgressDrawable.BoundsChangeListener
    public void onDrawableBoundsChanged() {
        Rect bounds = this.mNotificationProgressDrawable.getBounds();
        if (this.mProgressDrawableBounds.equals(bounds)) {
            return;
        }
        if (this.mProgressDrawableBounds.width() != bounds.width()) {
            updateDrawableParts();
        }
        this.mProgressDrawableBounds.set(bounds);
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0160  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void updateDrawableParts() {
        /*
            Method dump skipped, instructions count: 435
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.widget.NotificationProgressBar.updateDrawableParts():void");
    }

    private int getEndDotColor(List<Notification.ProgressStyle.Segment> list) {
        if (!this.mProgressModel.isStyledByProgress() || this.mProgressModel.getProgress() == this.mProgressModel.getProgressMax()) {
            return 0;
        }
        if (list == null) {
            return ((Notification.ProgressStyle.Segment) this.mProgressModel.getSegments().getLast()).getColor();
        }
        return ((Notification.ProgressStyle.Segment) list.getLast()).getColor();
    }

    private void updateTrackerAndBarPos(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6 = (i2 - this.mPaddingTop) - this.mPaddingBottom;
        Drawable currentDrawable = getCurrentDrawable();
        Drawable drawable = this.mTracker;
        int min = Math.min(getMaxHeight(), i6);
        if (drawable == null) {
            i3 = 0;
        } else {
            i3 = this.mTrackerHeight;
            if (i3 <= 0) {
                i3 = drawable.getIntrinsicHeight();
            }
        }
        if (i3 > min) {
            i5 = (i6 - i3) / 2;
            i4 = ((i3 - min) / 2) + i5;
        } else {
            int i7 = (i6 - min) / 2;
            int i8 = ((min - i3) / 2) + i7;
            i4 = i7;
            i5 = i8;
        }
        if (currentDrawable != null) {
            currentDrawable.setBounds(0, i4, (i - this.mPaddingRight) - this.mPaddingLeft, min + i4);
        }
        if (drawable != null) {
            setTrackerPos(i, drawable, this.mAdjustedProgressFraction, i5);
        }
    }

    private float getProgressFraction() {
        int min = getMin();
        return getProgressFraction(getMax() - min, getProgress() - min);
    }

    private void setTrackerPos(int i, Drawable drawable, float f, int i2) {
        int i3;
        int i4 = (i - this.mPaddingLeft) - this.mPaddingRight;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int i5 = i4 - this.mTrackerDrawWidth;
        int i6 = (int) ((f * i5) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            int i7 = bounds.top;
            i3 = bounds.bottom;
            i2 = i7;
        } else {
            i3 = intrinsicHeight + i2;
        }
        if (isLayoutRtl() && getMirrorForRtl()) {
            i6 = i5 - i6;
        }
        this.mTrackerPos = i6;
        Drawable background = getBackground();
        if (background != null) {
            int i8 = this.mPaddingLeft;
            int i9 = this.mPaddingTop;
            background.setHotspotBounds(i8, i2 + i9, intrinsicWidth + i8, i9 + i3);
        }
        drawable.setBounds(0, i2, intrinsicWidth, i3);
        this.mTrackerPosIsDirty = false;
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        Drawable drawable = this.mTracker;
        if (drawable != null) {
            drawable.setLayoutDirection(i);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isIndeterminate()) {
            return;
        }
        drawTracker(canvas);
    }

    private void drawTracker(Canvas canvas) {
        if (this.mTracker == null) {
            return;
        }
        if (this.mTrackerPosIsDirty) {
            setTrackerPos(getWidth(), this.mTracker, this.mAdjustedProgressFraction, Integer.MIN_VALUE);
        }
        int save = canvas.save();
        canvas.translate(this.mPaddingLeft + this.mTrackerPos, this.mPaddingTop);
        int i = this.mTrackerHeight;
        if (i > 0) {
            canvas.clipRect(0, 0, this.mTrackerDrawWidth, i);
        }
        Matrix matrix = this.mTrackerDrawMatrix;
        if (matrix != null) {
            canvas.concat(matrix);
        }
        this.mTracker.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        Drawable currentDrawable = getCurrentDrawable();
        Drawable drawable = this.mTracker;
        int intrinsicHeight = drawable == null ? 0 : drawable.getIntrinsicHeight();
        if (currentDrawable != null) {
            i4 = Math.max(getMinWidth(), Math.min(getMaxWidth(), currentDrawable.getIntrinsicWidth()));
            i3 = Math.max(intrinsicHeight, Math.max(getMinHeight(), Math.min(getMaxHeight(), currentDrawable.getIntrinsicHeight())));
        } else {
            i3 = 0;
            i4 = 0;
        }
        setMeasuredDimension(resolveSizeAndState(i4 + this.mPaddingLeft + this.mPaddingRight, i, 0), resolveSizeAndState(i3 + this.mPaddingTop + this.mPaddingBottom, i2, 0));
    }

    @Override // android.widget.ProgressBar, android.view.View
    public CharSequence getAccessibilityClassName() {
        return NotificationProgressBar.class.getName();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        Drawable drawable = this.mTracker;
        if (drawable != null) {
            setTrackerPos(getWidth(), drawable, this.mAdjustedProgressFraction, Integer.MIN_VALUE);
            invalidate();
        }
    }

    public static List<Part> processModelAndConvertToViewParts(List<Notification.ProgressStyle.Segment> list, List<Notification.ProgressStyle.Point> list2, int i, final int i2) {
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List of segments shouldn't be empty");
        }
        if (i2 != list.stream().mapToInt(new NotificationProgressBar$$ExternalSyntheticLambda1()).sum()) {
            throw new IllegalArgumentException("Invalid progressMax : " + i2);
        }
        Iterator<Notification.ProgressStyle.Segment> it = list.iterator();
        while (it.hasNext()) {
            int length = it.next().getLength();
            if (length <= 0) {
                throw new IllegalArgumentException("Invalid segment length : " + length);
            }
        }
        if (i < 0 || i > i2) {
            throw new IllegalArgumentException("Invalid progress : " + i);
        }
        Iterator<Notification.ProgressStyle.Point> it2 = list2.iterator();
        while (it2.hasNext()) {
            int position = it2.next().getPosition();
            if (position < 0 || position > i2) {
                throw new IllegalArgumentException("Invalid Point position : " + position);
            }
        }
        list2.removeIf(new Predicate() { // from class: com.android.internal.widget.NotificationProgressBar$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NotificationProgressBar.lambda$processModelAndConvertToViewParts$1(i2, (Notification.ProgressStyle.Point) obj);
            }
        });
        Map<Integer, Notification.ProgressStyle.Segment> generateStartToSegmentMap = generateStartToSegmentMap(list);
        Map<Integer, Notification.ProgressStyle.Point> generatePositionToPointMap = generatePositionToPointMap(list2);
        SortedSet<Integer> generateSortedPositionSet = generateSortedPositionSet(generateStartToSegmentMap, generatePositionToPointMap);
        return convertToViewParts(splitSegmentsByPoints(generateStartToSegmentMap, generateSortedPositionSet, i2), generatePositionToPointMap, generateSortedPositionSet, i2);
    }

    static /* synthetic */ boolean lambda$processModelAndConvertToViewParts$1(int i, Notification.ProgressStyle.Point point) {
        int position = point.getPosition();
        if (position == 0) {
            Log.w(TAG, "Dropping point at start");
            return true;
        }
        if (position != i) {
            return false;
        }
        Log.w(TAG, "Dropping point at end");
        return true;
    }

    private static Map<Integer, Notification.ProgressStyle.Segment> splitSegmentsByPoints(Map<Integer, Notification.ProgressStyle.Segment> map, SortedSet<Integer> sortedSet, int i) {
        int i2 = 0;
        for (Integer num : sortedSet) {
            if (num.intValue() != 0 && num.intValue() != i) {
                if (map.containsKey(num)) {
                    i2 = num.intValue();
                } else {
                    Notification.ProgressStyle.Segment segment = map.get(Integer.valueOf(i2));
                    Notification.ProgressStyle.Segment color = new Notification.ProgressStyle.Segment(num.intValue() - i2).setColor(segment.getColor());
                    Notification.ProgressStyle.Segment color2 = new Notification.ProgressStyle.Segment((segment.getLength() + i2) - num.intValue()).setColor(segment.getColor());
                    map.put(Integer.valueOf(i2), color);
                    map.put(num, color2);
                    i2 = num.intValue();
                }
            }
        }
        return map;
    }

    private static List<Part> convertToViewParts(Map<Integer, Notification.ProgressStyle.Segment> map, Map<Integer, Notification.ProgressStyle.Point> map2, SortedSet<Integer> sortedSet, int i) {
        ArrayList arrayList = new ArrayList();
        for (Integer num : sortedSet) {
            if (map2.containsKey(num)) {
                arrayList.add(new Point(map2.get(num).getColor()));
            }
            if (map.containsKey(num)) {
                arrayList.add(new Segment(r1.getLength() / i, map.get(num).getColor()));
            }
        }
        return arrayList;
    }

    private static int maybeGetFadedColor(int i, boolean z) {
        return !z ? i : getFadedColor(i);
    }

    static int getFadedColor(int i) {
        return Color.argb((int) ((Color.alpha(i) * 0.5f) + 0.5f), Color.red(i), Color.green(i), Color.blue(i));
    }

    private static Map<Integer, Notification.ProgressStyle.Segment> generateStartToSegmentMap(List<Notification.ProgressStyle.Segment> list) {
        HashMap hashMap = new HashMap();
        int i = 0;
        for (Notification.ProgressStyle.Segment segment : list) {
            hashMap.put(Integer.valueOf(i), segment);
            i += segment.getLength();
        }
        return hashMap;
    }

    private static Map<Integer, Notification.ProgressStyle.Point> generatePositionToPointMap(List<Notification.ProgressStyle.Point> list) {
        HashMap hashMap = new HashMap();
        for (Notification.ProgressStyle.Point point : list) {
            hashMap.put(Integer.valueOf(point.getPosition()), point);
        }
        return hashMap;
    }

    private static SortedSet<Integer> generateSortedPositionSet(Map<Integer, Notification.ProgressStyle.Segment> map, Map<Integer, Notification.ProgressStyle.Point> map2) {
        TreeSet treeSet = new TreeSet(map.keySet());
        treeSet.addAll(map2.keySet());
        return treeSet;
    }

    public static List<NotificationProgressDrawable.DrawablePart> processPartsAndConvertToDrawableParts(List<Part> list, float f, float f2, float f3, float f4, boolean z, int i) {
        float f5 = f4;
        ArrayList arrayList = new ArrayList();
        float f6 = i;
        float f7 = f - f6;
        int size = list.size();
        int i2 = 0;
        float f8 = f6 / 2.0f;
        while (i2 < size) {
            Part part = list.get(i2);
            Part part2 = i2 == 0 ? null : list.get(i2 - 1);
            int i3 = i2 + 1;
            Part part3 = i3 != size ? list.get(i3) : null;
            if (part instanceof Segment) {
                Segment segment = (Segment) part;
                float f9 = (segment.mFraction * f7) + f8;
                arrayList.add(new NotificationProgressDrawable.DrawableSegment(f8 + getSegStartOffset(part2, f5, f3), f9 - getSegEndOffset(segment, part3, f5, f3, f2, z), segment.mColor, segment.mFaded));
                segment.mStart = f8;
                segment.mEnd = f9;
                f8 = f9;
            } else if (part instanceof Point) {
                arrayList.add(new NotificationProgressDrawable.DrawablePoint(f8 - f4, f8 + f4, ((Point) part).mColor));
            }
            f5 = f4;
            i2 = i3;
        }
        return arrayList;
    }

    private static float getSegStartOffset(Part part, float f, float f2) {
        if (part instanceof Point) {
            return f + f2;
        }
        return 0.0f;
    }

    private static float getSegEndOffset(Segment segment, Part part, float f, float f2, float f3, boolean z) {
        if (part == null) {
            return 0.0f;
        }
        if (!(part instanceof Segment)) {
            return f2 + f;
        }
        Segment segment2 = (Segment) part;
        if (!segment.mFaded && segment2.mFaded && z) {
            return 0.0f;
        }
        return f3;
    }

    public static Pair<List<NotificationProgressDrawable.DrawablePart>, Float> maybeStretchAndRescaleSegments(List<Part> list, List<NotificationProgressDrawable.DrawablePart> list2, float f, float f2, float f3, boolean z, float f4) throws NotEnoughWidthToFitAllPartsException {
        final Class<NotificationProgressDrawable.DrawableSegment> cls = NotificationProgressDrawable.DrawableSegment.class;
        Stream<NotificationProgressDrawable.DrawablePart> filter = list2.stream().filter(new Predicate() { // from class: com.android.internal.widget.NotificationProgressBar$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return cls.isInstance((NotificationProgressDrawable.DrawablePart) obj);
            }
        });
        final Class<NotificationProgressDrawable.DrawableSegment> cls2 = NotificationProgressDrawable.DrawableSegment.class;
        Iterator it = filter.map(new Function() { // from class: com.android.internal.widget.NotificationProgressBar$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (NotificationProgressDrawable.DrawableSegment) cls2.cast((NotificationProgressDrawable.DrawablePart) obj);
            }
        }).toList().iterator();
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (it.hasNext()) {
            float width = ((NotificationProgressDrawable.DrawableSegment) it.next()).getWidth() - f;
            f6 += width;
            if (width > 0.0f) {
                f7 += width;
            }
        }
        if (f6 == f7) {
            return maybeSplitDrawableSegmentsByProgress(list, list2, f3, z, f4);
        }
        if (f6 < 0.0f) {
            throw new NotEnoughWidthToFitAllPartsException("Not enough width to satisfy the minimum width for segments.");
        }
        int size = list2.size();
        for (int i = 0; i < size; i++) {
            NotificationProgressDrawable.DrawablePart drawablePart = list2.get(i);
            if (drawablePart instanceof NotificationProgressDrawable.DrawableSegment) {
                NotificationProgressDrawable.DrawableSegment drawableSegment = (NotificationProgressDrawable.DrawableSegment) drawablePart;
                float width2 = drawableSegment.getWidth();
                float width3 = (width2 > f ? (((width2 - f) * f6) / f7) + f : f) - drawableSegment.getWidth();
                drawableSegment.setStart(drawableSegment.getStart() + f5);
                drawableSegment.setEnd(drawableSegment.getStart() + width2 + width3);
                Segment segment = (Segment) list.get(i);
                float width4 = segment.getWidth();
                segment.mStart += f5;
                segment.mEnd = segment.mStart + width4 + width3;
                f5 += width3;
            } else if (drawablePart instanceof NotificationProgressDrawable.DrawablePoint) {
                NotificationProgressDrawable.DrawablePoint drawablePoint = (NotificationProgressDrawable.DrawablePoint) drawablePart;
                drawablePoint.setStart(drawablePoint.getStart() + f5);
                drawablePoint.setEnd(drawablePoint.getStart() + (2.0f * f2));
            }
        }
        return maybeSplitDrawableSegmentsByProgress(list, list2, f3, z, f4);
    }

    private static Pair<List<NotificationProgressDrawable.DrawablePart>, Float> maybeSplitDrawableSegmentsByProgress(List<Part> list, List<NotificationProgressDrawable.DrawablePart> list2, float f, boolean z, float f2) {
        int i;
        if (f == 1.0f) {
            return new Pair<>(list2, Float.valueOf(((NotificationProgressDrawable.DrawablePart) list2.getLast()).getEnd()));
        }
        int size = list.size();
        float f3 = 0.0f;
        float f4 = 0.0f;
        int i2 = 0;
        while (true) {
            i = -1;
            if (i2 >= size) {
                i2 = -1;
                break;
            }
            Part part = list.get(i2);
            if (part instanceof Segment) {
                Segment segment = (Segment) part;
                if (f4 == f) {
                    f3 = segment.mStart;
                    break;
                }
                if (f4 < f && f < segment.mFraction + f4) {
                    f3 = segment.mStart + (((f - f4) / segment.mFraction) * segment.getWidth());
                    i = i2;
                    i2 = -1;
                    break;
                }
                f4 += segment.mFraction;
            }
            i2++;
        }
        if (!z) {
            return new Pair<>(list2, Float.valueOf(f3));
        }
        ArrayList arrayList = new ArrayList();
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            NotificationProgressDrawable.DrawablePart drawablePart = list2.get(i3);
            if (drawablePart instanceof NotificationProgressDrawable.DrawablePoint) {
                NotificationProgressDrawable.DrawablePoint drawablePoint = (NotificationProgressDrawable.DrawablePoint) drawablePart;
                arrayList.add(new NotificationProgressDrawable.DrawablePoint(drawablePoint.getStart(), drawablePoint.getEnd(), maybeGetFadedColor(drawablePoint.getColor(), z2)));
            }
            if (i3 == i2) {
                z2 = true;
            }
            if (drawablePart instanceof NotificationProgressDrawable.DrawableSegment) {
                NotificationProgressDrawable.DrawableSegment drawableSegment = (NotificationProgressDrawable.DrawableSegment) drawablePart;
                if (i3 == i) {
                    if (f3 <= drawableSegment.getStart()) {
                        arrayList.add(new NotificationProgressDrawable.DrawableSegment(drawableSegment.getStart(), drawableSegment.getEnd(), maybeGetFadedColor(drawableSegment.getColor(), true), true));
                    } else if (drawableSegment.getStart() < f3 && f3 < drawableSegment.getEnd()) {
                        arrayList.add(new NotificationProgressDrawable.DrawableSegment(drawableSegment.getStart(), f3 - f2, drawableSegment.getColor()));
                        arrayList.add(new NotificationProgressDrawable.DrawableSegment(f3, drawableSegment.getEnd(), maybeGetFadedColor(drawableSegment.getColor(), true), true));
                    } else {
                        arrayList.add(new NotificationProgressDrawable.DrawableSegment(drawableSegment.getStart(), drawableSegment.getEnd(), drawableSegment.getColor()));
                    }
                    z2 = true;
                } else {
                    arrayList.add(new NotificationProgressDrawable.DrawableSegment(drawableSegment.getStart(), drawableSegment.getEnd(), maybeGetFadedColor(drawableSegment.getColor(), z2), z2));
                }
            }
        }
        return new Pair<>(arrayList, Float.valueOf(f3));
    }

    public static Pair<List<NotificationProgressDrawable.DrawablePart>, Float> processModelAndConvertToFinalDrawableParts(List<Notification.ProgressStyle.Segment> list, List<Notification.ProgressStyle.Point> list2, int i, int i2, float f, float f2, float f3, float f4, boolean z, float f5, boolean z2, int i3) throws NotEnoughWidthToFitAllPartsException {
        List<Part> processModelAndConvertToViewParts = processModelAndConvertToViewParts(list, list2, i, i2);
        List<NotificationProgressDrawable.DrawablePart> processPartsAndConvertToDrawableParts = processPartsAndConvertToDrawableParts(processModelAndConvertToViewParts, f, f2, f3, f4, z, i3);
        float progressFraction = getProgressFraction(i2, i);
        if (z) {
            f2 = 0.0f;
        }
        return maybeStretchAndRescaleSegments(processModelAndConvertToViewParts, processPartsAndConvertToDrawableParts, f5, f4, progressFraction, z2, f2);
    }

    public static final class Segment implements Part {
        private final int mColor;
        private float mEnd;
        private final boolean mFaded;
        private final float mFraction;
        private float mStart;

        public Segment(float f, int i) {
            this(f, i, false);
        }

        public Segment(float f, int i, boolean z) {
            this.mFraction = f;
            this.mColor = i;
            this.mFaded = z;
        }

        public float getWidth() {
            return this.mEnd - this.mStart;
        }

        public String toString() {
            return "Segment(fraction=" + this.mFraction + ", color=" + this.mColor + ", faded=" + this.mFaded + "), mStart = " + this.mStart + ", mEnd = " + this.mEnd;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Segment segment = (Segment) obj;
                if (Float.compare(this.mFraction, segment.mFraction) == 0 && this.mColor == segment.mColor && this.mFaded == segment.mFaded) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mFraction), Integer.valueOf(this.mColor), Boolean.valueOf(this.mFaded));
        }
    }

    public static final class Point implements Part {
        private final int mColor;

        public Point(int i) {
            this.mColor = i;
        }

        public String toString() {
            return "Point(color=" + this.mColor + NavigationBarInflaterView.KEY_CODE_END;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && this.mColor == ((Point) obj).mColor;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mColor));
        }
    }

    public static class NotEnoughWidthToFitAllPartsException extends Exception {
        public NotEnoughWidthToFitAllPartsException(String str) {
            super(str);
        }
    }
}
