package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.view.View;
import android.view.ViewOutlineProvider;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ExpandableOutlineView extends ExpandableView {
    public static final Path EMPTY_PATH = new Path();
    public boolean mAlwaysRoundBothCorners;
    public boolean mCustomOutline;
    public boolean mDismissUsingRowTranslationX;
    public float mDistanceToTopRoundness;
    public float mOutlineAlpha;
    public final Rect mOutlineRect;
    public final C28741 mProvider;
    public RoundableState mRoundableState;
    public final float[] mTmpCornerRadii;
    public final Path mTmpPath;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.view.ViewOutlineProvider, com.android.systemui.statusbar.notification.row.ExpandableOutlineView$1] */
    public ExpandableOutlineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOutlineRect = new Rect();
        this.mOutlineAlpha = -1.0f;
        this.mTmpPath = new Path();
        this.mDismissUsingRowTranslationX = true;
        this.mTmpCornerRadii = new float[8];
        this.mDistanceToTopRoundness = -1.0f;
        ?? r1 = new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.notification.row.ExpandableOutlineView.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                ExpandableOutlineView expandableOutlineView = ExpandableOutlineView.this;
                if (!expandableOutlineView.mCustomOutline && !expandableOutlineView.hasRoundedCorner()) {
                    ExpandableOutlineView expandableOutlineView2 = ExpandableOutlineView.this;
                    if (!expandableOutlineView2.mAlwaysRoundBothCorners) {
                        int translation = !expandableOutlineView2.mDismissUsingRowTranslationX ? (int) expandableOutlineView2.getTranslation() : 0;
                        int max = Math.max(translation, 0);
                        ExpandableOutlineView expandableOutlineView3 = ExpandableOutlineView.this;
                        int i = expandableOutlineView3.mClipTopAmount;
                        int min = Math.min(translation, 0) + expandableOutlineView3.getWidth();
                        ExpandableOutlineView expandableOutlineView4 = ExpandableOutlineView.this;
                        outline.setRect(max, i, min, Math.max(expandableOutlineView4.mActualHeight - expandableOutlineView4.mClipBottomAmount, i));
                        outline.setAlpha(ExpandableOutlineView.this.mOutlineAlpha);
                    }
                }
                Path clipPath = ExpandableOutlineView.this.getClipPath(false);
                if (clipPath != null) {
                    outline.setPath(clipPath);
                }
                outline.setAlpha(ExpandableOutlineView.this.mOutlineAlpha);
            }
        };
        this.mProvider = r1;
        setOutlineProvider(r1);
        initDimens();
    }

    private void initDimens() {
        Resources resources = getResources();
        boolean z = resources.getBoolean(R.bool.config_clipNotificationsToOutline);
        this.mAlwaysRoundBothCorners = z;
        float dimension = z ? resources.getDimension(R.dimen.notification_shadow_radius) : resources.getDimensionPixelSize(R.dimen.notification_corner_radius);
        RoundableState roundableState = this.mRoundableState;
        if (roundableState == null) {
            this.mRoundableState = new RoundableState(this, this, dimension);
        } else {
            if (!(roundableState.maxRadius == dimension)) {
                roundableState.maxRadius = dimension;
                roundableState.roundable.applyRoundnessAndInvalidate();
            }
        }
        setClipToOutline(this.mAlwaysRoundBothCorners);
    }

    public void applyRoundnessAndInvalidate() {
        invalidateOutline();
        super.applyRoundnessAndInvalidate();
    }

    public boolean childNeedsClipping(View view) {
        return false;
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j) {
        Path path;
        canvas.save();
        Path path2 = null;
        if (childNeedsClipping(view)) {
            path = getCustomClipPath(view);
            if (path == null) {
                path = getClipPath(false);
            }
            if (this.mDismissUsingRowTranslationX && (view instanceof NotificationChildrenContainer)) {
                path2 = path;
                path = null;
            }
        } else {
            path = null;
        }
        if (view instanceof NotificationChildrenContainer) {
            NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
            notificationChildrenContainer.mChildClipPath = path2;
            notificationChildrenContainer.invalidate();
        }
        if (path != null) {
            canvas.clipPath(path);
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return drawChild;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        final IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        super.dump(asIndenting, strArr);
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableOutlineView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                asIndenting.println(ExpandableOutlineView.this.mRoundableState.debugString());
            }
        });
    }

    public final Path getClipPath(boolean z) {
        int i;
        int i2;
        int i3;
        int i4;
        float maxRadius = this.mAlwaysRoundBothCorners ? getMaxRadius() : getTopCornerRadius();
        if (this.mCustomOutline) {
            Rect rect = this.mOutlineRect;
            int i5 = rect.left;
            int i6 = rect.top;
            int i7 = rect.right;
            i = rect.bottom;
            i2 = i7;
            i3 = i5;
            i4 = i6;
        } else {
            int translation = (this.mDismissUsingRowTranslationX || z) ? 0 : (int) getTranslation();
            int i8 = (int) (this.mExtraWidthForClipping / 2.0f);
            int max = Math.max(translation, 0) - i8;
            i2 = Math.min(translation, 0) + getWidth() + i8;
            i = this.mActualHeight;
            i3 = max;
            i4 = 0;
        }
        int i9 = i - i4;
        if (i9 == 0) {
            return EMPTY_PATH;
        }
        if (maxRadius + (this.mAlwaysRoundBothCorners ? getMaxRadius() : getBottomCornerRadius()) > i9) {
            getTopRoundness();
            getBottomRoundness();
        }
        float maxRadius2 = getMaxRadius();
        float maxRadius3 = getMaxRadius();
        Path path = this.mTmpPath;
        path.reset();
        float[] fArr = this.mTmpCornerRadii;
        fArr[0] = maxRadius2;
        fArr[1] = maxRadius2;
        fArr[2] = maxRadius2;
        fArr[3] = maxRadius2;
        fArr[4] = maxRadius3;
        fArr[5] = maxRadius3;
        fArr[6] = maxRadius3;
        fArr[7] = maxRadius3;
        path.addRoundRect(i3, i4, i2, i, fArr, Path.Direction.CW);
        return this.mTmpPath;
    }

    public Path getCustomClipPath(View view) {
        return null;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final float getOutlineAlpha() {
        return this.mOutlineAlpha;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final int getOutlineTranslation() {
        if (this.mCustomOutline) {
            return this.mOutlineRect.left;
        }
        if (this.mDismissUsingRowTranslationX) {
            return 0;
        }
        return (int) getTranslation();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.statusbar.notification.Roundable
    public final RoundableState getRoundableState() {
        return this.mRoundableState;
    }

    public final boolean isClippingNeeded() {
        return this.mAlwaysRoundBothCorners || this.mCustomOutline || ((getTranslation() > 0.0f ? 1 : (getTranslation() == 0.0f ? 0 : -1)) != 0 && !this.mDismissUsingRowTranslationX);
    }

    public boolean needsOutline() {
        if (isChildInGroup()) {
            return isGroupExpanded() && !isGroupExpansionChanging();
        }
        if (isSummaryWithChildren()) {
            return !isGroupExpanded() || isGroupExpansionChanging();
        }
        return true;
    }

    public void onDensityOrFontScaleChanged() {
        initDimens();
        applyRoundnessAndInvalidate();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setActualHeight(int i, boolean z) {
        int i2 = this.mActualHeight;
        super.setActualHeight(i, z);
        if (i2 != i) {
            applyRoundnessAndInvalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipBottomAmount(int i) {
        int i2 = this.mClipBottomAmount;
        super.setClipBottomAmount(i);
        if (i2 != i) {
            applyRoundnessAndInvalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setClipTopAmount(int i) {
        int i2 = this.mClipTopAmount;
        super.setClipTopAmount(i);
        if (i2 != i) {
            applyRoundnessAndInvalidate();
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public void setDistanceToTopRoundness(float f) {
        if (f != this.mDistanceToTopRoundness) {
            this.mDistanceToTopRoundness = f;
            applyRoundnessAndInvalidate();
        }
    }

    public final void setOutlineRect(float f, float f2, float f3, float f4) {
        this.mCustomOutline = true;
        this.mOutlineRect.set((int) f, (int) f2, (int) f3, (int) f4);
        this.mOutlineRect.bottom = (int) Math.max(f2, r6.bottom);
        this.mOutlineRect.right = (int) Math.max(f, r5.right);
        applyRoundnessAndInvalidate();
    }
}
