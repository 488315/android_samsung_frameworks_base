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
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import java.io.PrintWriter;

/* loaded from: classes3.dex */
public abstract class ExpandableOutlineView extends ExpandableView {
    public static final Path EMPTY_PATH = new Path();
    public boolean mAlwaysRoundBothCorners;
    public boolean mCustomOutline;
    public boolean mDismissUsingRowTranslationX;
    public float mOutlineAlpha;
    public final Rect mOutlineRect;
    public final AnonymousClass1 mProvider;
    public RoundableState mRoundableState;
    public final float[] mTmpCornerRadii;
    public final Path mTmpPath;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v7, types: [android.view.ViewOutlineProvider, com.android.systemui.statusbar.notification.row.ExpandableOutlineView$1] */
    public ExpandableOutlineView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mOutlineRect = new Rect();
        this.mOutlineAlpha = -1.0f;
        this.mTmpPath = new Path();
        this.mDismissUsingRowTranslationX = true;
        this.mTmpCornerRadii = new float[8];
        ?? r1 = new ViewOutlineProvider() { // from class: com.android.systemui.statusbar.notification.row.ExpandableOutlineView.1
            /* JADX WARN: Removed duplicated region for block: B:13:0x003e  */
            @Override // android.view.ViewOutlineProvider
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void getOutline(View view, Outline outline) {
                ExpandableOutlineView expandableOutlineView = ExpandableOutlineView.this;
                if (expandableOutlineView.mCustomOutline || expandableOutlineView.hasRoundedCorner()) {
                    Path clipPath = ExpandableOutlineView.this.getClipPath(false);
                    if (clipPath != null) {
                        outline.setPath(clipPath);
                    }
                } else {
                    ExpandableOutlineView expandableOutlineView2 = ExpandableOutlineView.this;
                    if (!expandableOutlineView2.mAlwaysRoundBothCorners) {
                        int translation = !expandableOutlineView2.mDismissUsingRowTranslationX ? (int) expandableOutlineView2.getTranslation() : 0;
                        int iMax = Math.max(translation, 0);
                        ExpandableOutlineView expandableOutlineView3 = ExpandableOutlineView.this;
                        int i = expandableOutlineView3.mClipTopAmount;
                        int iMin = Math.min(translation, 0) + expandableOutlineView3.getWidth();
                        ExpandableOutlineView expandableOutlineView4 = ExpandableOutlineView.this;
                        outline.setRect(iMax, i, iMin, Math.max(expandableOutlineView4.mActualHeight - expandableOutlineView4.mClipBottomAmount, i));
                    }
                }
                outline.setAlpha(ExpandableOutlineView.this.mOutlineAlpha);
            }
        };
        this.mProvider = r1;
        setOutlineProvider(r1);
        initDimens$3();
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
        Path customClipPath;
        canvas.save();
        Path path = null;
        if (childNeedsClipping(view)) {
            customClipPath = getCustomClipPath(view);
            if (customClipPath == null) {
                customClipPath = getClipPath(false);
            }
            if (this.mDismissUsingRowTranslationX && (view instanceof NotificationChildrenContainer)) {
                path = customClipPath;
                customClipPath = null;
            }
        } else {
            customClipPath = null;
        }
        if (view instanceof NotificationChildrenContainer) {
            NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
            notificationChildrenContainer.mChildClipPath = path;
            notificationChildrenContainer.invalidate();
        }
        if (customClipPath != null) {
            canvas.clipPath(customClipPath);
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return zDrawChild;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, final String[] strArr) {
        final IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        super.dump(indentingPrintWriterAsIndenting, strArr);
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriterAsIndenting, new Runnable(indentingPrintWriterAsIndenting, strArr) { // from class: com.android.systemui.statusbar.notification.row.ExpandableOutlineView$$ExternalSyntheticLambda0
            public final /* synthetic */ IndentingPrintWriter f$1;

            @Override // java.lang.Runnable
            public final void run() {
                this.f$1.println(this.f$0.mRoundableState.debugString());
            }
        });
    }

    public final Path getClipPath(boolean z) {
        int i;
        int iMin;
        int i2;
        int i3;
        char c;
        char c2;
        char c3;
        float fMin;
        float f = this.mAlwaysRoundBothCorners ? this.mRoundableState.maxRadius : getRoundableState().topRoundness * getRoundableState().maxRadius;
        if (this.mCustomOutline) {
            Rect rect = this.mOutlineRect;
            int i4 = rect.left;
            int i5 = rect.top;
            int i6 = rect.right;
            i = rect.bottom;
            iMin = i6;
            i2 = i4;
            i3 = i5;
        } else {
            int translation = (this.mDismissUsingRowTranslationX || z) ? 0 : (int) getTranslation();
            int i7 = (int) (this.mExtraWidthForClipping / 2.0f);
            int iMax = Math.max(translation, 0) - i7;
            iMin = Math.min(translation, 0) + getWidth() + i7;
            i = this.mActualHeight;
            i2 = iMax;
            i3 = 0;
        }
        if (i - i3 == 0) {
            return EMPTY_PATH;
        }
        float f2 = this.mAlwaysRoundBothCorners ? this.mRoundableState.maxRadius : getRoundableState().bottomRoundness * getRoundableState().maxRadius;
        float translation2 = getTranslation();
        if (NotiRune.NOTI_STYLE_POP_OVER_DISMISS_CLIP_VIEW) {
            c = 0;
            if (((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && !this.mDismissUsingRowTranslationX && translation2 != 0.0f && canExpandableViewBeDismissed()) {
                boolean z2 = translation2 > 0.0f;
                if (translation2 == 0.0f) {
                    fMin = 0.0f;
                    c2 = 7;
                    c3 = 6;
                } else {
                    c2 = 7;
                    c3 = 6;
                    fMin = (float) (1.0d - Math.min(Math.max(0.0f, Math.abs(translation2 / this.mRoundableState.maxRadius)), 0.9d));
                }
                float f3 = f * fMin;
                float f4 = fMin * f2;
                Path path = this.mTmpPath;
                path.reset();
                float[] fArr = this.mTmpCornerRadii;
                fArr[0] = z2 ? f : f3;
                fArr[1] = z2 ? f : f3;
                fArr[2] = z2 ? f3 : f;
                if (z2) {
                    f = f3;
                }
                fArr[3] = f;
                fArr[4] = z2 ? f4 : f2;
                fArr[5] = z2 ? f4 : f2;
                fArr[c3] = z2 ? f2 : f4;
                if (!z2) {
                    f2 = f4;
                }
                fArr[c2] = f2;
                path.addRoundRect(i2, i3, iMin, i, fArr, Path.Direction.CW);
            }
            return this.mTmpPath;
        }
        c = 0;
        float f5 = this.mRoundableState.maxRadius;
        Path path2 = this.mTmpPath;
        path2.reset();
        float[] fArr2 = this.mTmpCornerRadii;
        fArr2[c] = f5;
        fArr2[1] = f5;
        fArr2[2] = f5;
        fArr2[3] = f5;
        fArr2[4] = f5;
        fArr2[5] = f5;
        fArr2[6] = f5;
        fArr2[7] = f5;
        path2.addRoundRect(i2, i3, iMin, i, fArr2, Path.Direction.CW);
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

    public final void initDimens$3() throws Resources.NotFoundException {
        Resources resources = getResources();
        boolean z = resources.getBoolean(R.bool.config_clipNotificationsToOutline);
        this.mAlwaysRoundBothCorners = z;
        float dimension = z ? resources.getDimension(R.dimen.notification_shadow_radius) : resources.getDimensionPixelSize(R.dimen.notification_corner_radius);
        RoundableState roundableState = this.mRoundableState;
        if (roundableState == null) {
            this.mRoundableState = new RoundableState(this, this, dimension);
        } else if (roundableState.maxRadius != dimension) {
            roundableState.maxRadius = dimension;
            roundableState.roundable.applyRoundnessAndInvalidate();
        }
        setClipToOutline(this.mAlwaysRoundBothCorners);
    }

    public final boolean isClippingNeeded() {
        return this.mAlwaysRoundBothCorners || this.mCustomOutline || ((getTranslation() > 0.0f ? 1 : (getTranslation() == 0.0f ? 0 : -1)) != 0 && !this.mDismissUsingRowTranslationX);
    }

    public boolean needsOutline() {
        if (isChildInGroup()) {
            if (isGroupExpanded$1()) {
                isGroupExpansionChanging();
            }
            return false;
        }
        if (!isSummaryWithChildren() || !isGroupExpanded$1()) {
            return true;
        }
        isGroupExpansionChanging();
        return false;
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

    public final void setOutlineRect(float f, float f2, float f3, float f4) {
        this.mCustomOutline = true;
        this.mOutlineRect.set((int) f, (int) f2, (int) f3, (int) f4);
        this.mOutlineRect.bottom = (int) Math.max(f2, r6.bottom);
        this.mOutlineRect.right = (int) Math.max(f, r5.right);
        applyRoundnessAndInvalidate();
    }
}
