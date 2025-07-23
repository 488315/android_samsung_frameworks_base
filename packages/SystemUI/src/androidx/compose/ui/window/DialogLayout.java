package androidx.compose.ui.window;

import android.content.Context;
import android.view.View;
import android.view.Window;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.platform.AbstractComposeView;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsAnimationCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DialogLayout extends AbstractComposeView implements OnApplyWindowInsetsListener {
    public final MutableState content$delegate;
    public boolean decorFitsSystemWindows;
    public boolean hasCalledSetLayout;
    public boolean shouldCreateCompositionOnAttachedToWindow;
    public boolean usePlatformDefaultWidth;
    public final Window window;

    public DialogLayout(Context context, Window window) {
        super(context, null, 0, 6, null);
        this.window = window;
        ComposableSingletons$AndroidDialog_androidKt.INSTANCE.getClass();
        this.content$delegate = SnapshotStateKt.mutableStateOf$default(ComposableSingletons$AndroidDialog_androidKt.f21lambda1);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, this);
        ViewCompat.setWindowInsetsAnimationCallback(this, new WindowInsetsAnimationCompat.Callback() { // from class: androidx.compose.ui.window.DialogLayout.1
            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public final WindowInsetsCompat onProgress(WindowInsetsCompat windowInsetsCompat, List list) {
                DialogLayout dialogLayout = DialogLayout.this;
                if (!dialogLayout.decorFitsSystemWindows) {
                    View childAt = dialogLayout.getChildAt(0);
                    int max = Math.max(0, childAt.getLeft());
                    int max2 = Math.max(0, childAt.getTop());
                    int max3 = Math.max(0, dialogLayout.getWidth() - childAt.getRight());
                    int max4 = Math.max(0, dialogLayout.getHeight() - childAt.getBottom());
                    if (max != 0 || max2 != 0 || max3 != 0 || max4 != 0) {
                        return windowInsetsCompat.mImpl.inset(max, max2, max3, max4);
                    }
                }
                return windowInsetsCompat;
            }

            @Override // androidx.core.view.WindowInsetsAnimationCompat.Callback
            public final WindowInsetsAnimationCompat.BoundsCompat onStart(WindowInsetsAnimationCompat windowInsetsAnimationCompat, WindowInsetsAnimationCompat.BoundsCompat boundsCompat) {
                DialogLayout dialogLayout = DialogLayout.this;
                if (!dialogLayout.decorFitsSystemWindows) {
                    View childAt = dialogLayout.getChildAt(0);
                    int max = Math.max(0, childAt.getLeft());
                    int max2 = Math.max(0, childAt.getTop());
                    int max3 = Math.max(0, dialogLayout.getWidth() - childAt.getRight());
                    int max4 = Math.max(0, dialogLayout.getHeight() - childAt.getBottom());
                    if (max != 0 || max2 != 0 || max3 != 0 || max4 != 0) {
                        Insets of = Insets.of(max, max2, max3, max4);
                        boundsCompat.getClass();
                        int i = of.left;
                        Insets insets = boundsCompat.mLowerBound;
                        int i2 = of.top;
                        int i3 = of.right;
                        int i4 = of.bottom;
                        return new WindowInsetsAnimationCompat.BoundsCompat(WindowInsetsCompat.insetInsets(insets, i, i2, i3, i4), WindowInsetsCompat.insetInsets(boundsCompat.mUpperBound, of.left, i2, i3, i4));
                    }
                }
                return boundsCompat;
            }
        });
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void Content(ComposerImpl composerImpl) {
        composerImpl.startReplaceGroup(1735448596);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.window.DialogLayout.Content (AndroidDialog.android.kt:408)");
        }
        ((Function2) ((SnapshotMutableStateImpl) this.content$delegate).getValue()).invoke(composerImpl, 0);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final boolean getShouldCreateCompositionOnAttachedToWindow() {
        return this.shouldCreateCompositionOnAttachedToWindow;
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void internalOnLayout$ui_release(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt == null) {
            return;
        }
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int measuredWidth = childAt.getMeasuredWidth();
        int measuredHeight = childAt.getMeasuredHeight();
        int paddingLeft = (((i5 - measuredWidth) - paddingRight) / 2) + getPaddingLeft();
        int paddingTop = (((i6 - measuredHeight) - paddingBottom) / 2) + getPaddingTop();
        childAt.layout(paddingLeft, paddingTop, measuredWidth + paddingLeft, measuredHeight + paddingTop);
    }

    @Override // androidx.compose.ui.platform.AbstractComposeView
    public final void internalOnMeasure$ui_release(int i, int i2) {
        View childAt = getChildAt(0);
        if (childAt == null) {
            super.internalOnMeasure$ui_release(i, i2);
            return;
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        int i3 = (mode != Integer.MIN_VALUE || this.usePlatformDefaultWidth || this.decorFitsSystemWindows || this.window.getAttributes().height != -2) ? size2 : size2 + 1;
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int i4 = size - paddingRight;
        if (i4 < 0) {
            i4 = 0;
        }
        int i5 = i3 - paddingBottom;
        int i6 = i5 >= 0 ? i5 : 0;
        int mode2 = View.MeasureSpec.getMode(i);
        if (mode2 != 0) {
            i = View.MeasureSpec.makeMeasureSpec(i4, Integer.MIN_VALUE);
        }
        if (mode != 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(i6, Integer.MIN_VALUE);
        }
        childAt.measure(i, i2);
        if (mode2 == Integer.MIN_VALUE) {
            size = Math.min(size, childAt.getMeasuredWidth() + paddingRight);
        } else if (mode2 != 1073741824) {
            size = childAt.getMeasuredWidth() + paddingRight;
        }
        setMeasuredDimension(size, mode != Integer.MIN_VALUE ? mode != 1073741824 ? childAt.getMeasuredHeight() + paddingBottom : size2 : Math.min(size2, childAt.getMeasuredHeight() + paddingBottom));
        if (this.usePlatformDefaultWidth || this.decorFitsSystemWindows || childAt.getMeasuredHeight() + paddingBottom <= size2 || this.window.getAttributes().height != -2) {
            return;
        }
        this.window.setLayout(-1, -1);
    }

    @Override // androidx.core.view.OnApplyWindowInsetsListener
    public final WindowInsetsCompat onApplyWindowInsets(WindowInsetsCompat windowInsetsCompat, View view) {
        if (!this.decorFitsSystemWindows) {
            View childAt = getChildAt(0);
            int max = Math.max(0, childAt.getLeft());
            int max2 = Math.max(0, childAt.getTop());
            int max3 = Math.max(0, getWidth() - childAt.getRight());
            int max4 = Math.max(0, getHeight() - childAt.getBottom());
            if (max != 0 || max2 != 0 || max3 != 0 || max4 != 0) {
                return windowInsetsCompat.mImpl.inset(max, max2, max3, max4);
            }
        }
        return windowInsetsCompat;
    }
}
