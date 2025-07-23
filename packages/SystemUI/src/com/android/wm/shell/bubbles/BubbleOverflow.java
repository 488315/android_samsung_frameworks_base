package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.util.PathParser;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.systemui.R;
import com.android.wm.shell.bubbles.BadgedImageView;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubbleOverflow implements BubbleViewProvider {
    public Bitmap bitmap;
    public BubbleBarExpandedView bubbleBarExpandedView;
    public final Context context;
    public int dotColor;
    public Path dotPath;
    public BubbleExpandedView expandedView;
    public final LayoutInflater inflater;
    public BadgedImageView overflowBtn;
    public final BubblePositioner positioner;
    public boolean showDot;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public BubbleOverflow(Context context, BubblePositioner bubblePositioner) {
        this.context = context;
        this.positioner = bubblePositioner;
        this.inflater = LayoutInflater.from(context);
        updateResources();
        this.expandedView = null;
        this.overflowBtn = null;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final Bitmap getAppBadge() {
        return null;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final BubbleBarExpandedView getBubbleBarExpandedView() {
        return this.bubbleBarExpandedView;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final Bitmap getBubbleIcon() {
        Bitmap bitmap = this.bitmap;
        if (bitmap == null) {
            return null;
        }
        return bitmap;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final int getDotColor() {
        return this.dotColor;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final Path getDotPath() {
        Path path = this.dotPath;
        if (path == null) {
            return null;
        }
        return path;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final BubbleExpandedView getExpandedView() {
        return this.expandedView;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    /* renamed from: getIconView, reason: merged with bridge method [inline-methods] */
    public final BadgedImageView getIconView$1() {
        if (this.overflowBtn == null) {
            BadgedImageView badgedImageView = (BadgedImageView) this.inflater.inflate(R.layout.bubble_overflow_button, (ViewGroup) null, false);
            this.overflowBtn = badgedImageView;
            BubblePositioner bubblePositioner = this.positioner;
            if (badgedImageView != null) {
                badgedImageView.initialize(bubblePositioner);
            }
            BadgedImageView badgedImageView2 = this.overflowBtn;
            if (badgedImageView2 != null) {
                badgedImageView2.setContentDescription(this.context.getResources().getString(R.string.bubble_overflow_button_content_description));
            }
            int i = bubblePositioner.mBubbleSize;
            BadgedImageView badgedImageView3 = this.overflowBtn;
            if (badgedImageView3 != null) {
                badgedImageView3.setLayoutParams(new FrameLayout.LayoutParams(i, i));
            }
            updateBtnTheme();
        }
        return this.overflowBtn;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final String getKey() {
        return "Overflow";
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final int getTaskId() {
        BubbleExpandedView bubbleExpandedView = this.expandedView;
        if (bubbleExpandedView == null) {
            return -1;
        }
        bubbleExpandedView.getClass();
        return bubbleExpandedView.mTaskId;
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final boolean showDot() {
        return this.showDot;
    }

    public final void updateBtnTheme() {
        Resources resources = this.context.getResources();
        this.context.getColor(android.R.color.secondary_text_material_dark);
        this.context.getColor(android.R.color.ripple_material_dark);
        this.dotColor = resources.getColor(R.color.sec_bubble_badge_color);
        BubbleIconFactory bubbleIconFactory = new BubbleIconFactory(this.context);
        BadgedImageView badgedImageView = this.overflowBtn;
        this.bitmap = bubbleIconFactory.getCircledBubble(badgedImageView != null ? badgedImageView.mBubbleIcon.getDrawable() : null, true);
        this.dotPath = PathParser.createPathFromPathData(resources.getString(android.R.string.eventTypeAnniversary));
        float f = new float[1][0];
        Matrix matrix = new Matrix();
        matrix.setScale(f, f, 50.0f, 50.0f);
        Path path = this.dotPath;
        (path != null ? path : null).transform(matrix);
        BadgedImageView badgedImageView2 = this.overflowBtn;
        if (badgedImageView2 != null) {
            badgedImageView2.setRenderedBubble(this);
        }
        BadgedImageView badgedImageView3 = this.overflowBtn;
        if (badgedImageView3 != null) {
            badgedImageView3.removeDotSuppressionFlag(BadgedImageView.SuppressionFlag.FLYOUT_VISIBLE);
        }
    }

    public final void updateResources() {
        this.context.getResources().getDimensionPixelSize(R.dimen.bubble_overflow_icon_inset);
        BadgedImageView badgedImageView = this.overflowBtn;
        if (badgedImageView != null) {
            int i = this.positioner.mBubbleSize;
            badgedImageView.setLayoutParams(new FrameLayout.LayoutParams(i, i));
        }
        BubbleExpandedView bubbleExpandedView = this.expandedView;
        if (bubbleExpandedView != null) {
            bubbleExpandedView.updateDimensions();
        }
    }

    @Override // com.android.wm.shell.bubbles.BubbleViewProvider
    public final void setTaskViewVisibility() {
    }
}
