package com.android.p038wm.shell.bubbles;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.util.PathParser;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.launcher3.icons.IconNormalizer;
import com.android.p038wm.shell.bubbles.BadgedImageView;
import com.android.systemui.R;
import com.samsung.android.knox.lockscreen.EmergencyPhoneWidget;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubbleOverflow implements BubbleViewProvider {
    public Bitmap bitmap;
    public final Context context;
    public int dotColor;
    public Path dotPath;
    public BubbleExpandedView expandedView;
    public final LayoutInflater inflater;
    public BadgedImageView overflowBtn;
    public final BubblePositioner positioner;
    public boolean showDot;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final Bitmap getAppBadge() {
        return null;
    }

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final Bitmap getBubbleIcon() {
        Bitmap bitmap = this.bitmap;
        if (bitmap == null) {
            return null;
        }
        return bitmap;
    }

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final int getDotColor() {
        return this.dotColor;
    }

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final Path getDotPath() {
        Path path = this.dotPath;
        if (path == null) {
            return null;
        }
        return path;
    }

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final BubbleExpandedView getExpandedView() {
        return this.expandedView;
    }

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    /* renamed from: getIconView, reason: merged with bridge method [inline-methods] */
    public final BadgedImageView getIconView$1() {
        if (this.overflowBtn == null) {
            BadgedImageView badgedImageView = (BadgedImageView) this.inflater.inflate(R.layout.bubble_overflow_button, (ViewGroup) null, false);
            this.overflowBtn = badgedImageView;
            BubblePositioner bubblePositioner = this.positioner;
            badgedImageView.initialize(bubblePositioner);
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

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final String getKey() {
        return "Overflow";
    }

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final int getTaskId() {
        BubbleExpandedView bubbleExpandedView = this.expandedView;
        if (bubbleExpandedView == null) {
            return -1;
        }
        Intrinsics.checkNotNull(bubbleExpandedView);
        return bubbleExpandedView.mTaskId;
    }

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final boolean showDot() {
        return this.showDot;
    }

    public final void updateBtnTheme() {
        Context context = this.context;
        Resources resources = context.getResources();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{android.R.^attr-private.materialColorOnError, android.R.^attr-private.lightZ});
        obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.getColor(1, EmergencyPhoneWidget.BG_COLOR);
        obtainStyledAttributes.recycle();
        this.dotColor = resources.getColor(R.color.sec_bubble_badge_color);
        BubbleIconFactory bubbleIconFactory = new BubbleIconFactory(context);
        BadgedImageView badgedImageView = this.overflowBtn;
        this.bitmap = bubbleIconFactory.getCircledBubble(badgedImageView != null ? badgedImageView.mBubbleIcon.getDrawable() : null, true);
        this.dotPath = PathParser.createPathFromPathData(resources.getString(android.R.string.dump_heap_ready_text));
        IconNormalizer normalizer = bubbleIconFactory.getNormalizer();
        BadgedImageView iconView$1 = getIconView$1();
        Intrinsics.checkNotNull(iconView$1);
        float scale = normalizer.getScale(iconView$1.mBubbleIcon.getDrawable(), null, null, null);
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale, 50.0f, 50.0f);
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

    @Override // com.android.p038wm.shell.bubbles.BubbleViewProvider
    public final void setTaskViewVisibility() {
    }
}
