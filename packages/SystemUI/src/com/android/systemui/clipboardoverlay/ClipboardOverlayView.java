package com.android.systemui.clipboardoverlay;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.ui.binder.ActionButtonViewBinder;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonViewModel;
import java.util.ArrayList;
import kotlin.jvm.functions.Function0;

public class ClipboardOverlayView extends DraggableConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public final ActionButtonViewBinder mActionButtonViewBinder;
    public final ArrayList mActionChips;
    public LinearLayout mActionContainer;
    public View mActionContainerBackground;
    public ClipboardOverlayCallbacks mClipboardCallbacks;
    public View mClipboardPreview;
    public View mDismissButton;
    public final DisplayMetrics mDisplayMetrics;
    public TextView mHiddenPreview;
    public ImageView mImagePreview;
    public LinearLayout mMinimizedPreview;
    public View mPreviewBorder;
    public View mRemoteCopyChip;
    public View mShareChip;
    public TextView mTextPreview;

    public interface ClipboardOverlayCallbacks extends DraggableConstraintLayout.SwipeDismissCallbacks {
        void onDismissButtonTapped();

        void onMinimizedViewTapped();

        void onPreviewTapped();

        void onRemoteCopyButtonTapped();

        void onShareButtonTapped();
    }

    public ClipboardOverlayView(Context context) {
        this(context, null);
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout, android.view.View
    public final void onFinishInflate() {
        this.mActionContainerBackground = requireViewById(R.id.actions_container_background);
        this.mActionContainer = (LinearLayout) requireViewById(R.id.actions);
        this.mClipboardPreview = requireViewById(R.id.clipboard_preview);
        this.mPreviewBorder = requireViewById(R.id.preview_border);
        this.mTextPreview = (TextView) requireViewById(R.id.text_preview);
        this.mMinimizedPreview = (LinearLayout) requireViewById(R.id.minimized_preview);
        this.mShareChip = requireViewById(R.id.share_chip);
        this.mRemoteCopyChip = requireViewById(R.id.remote_copy_chip);
        this.mDismissButton = requireViewById(R.id.dismiss_button);
        Flags.screenshotShelfUi2();
        ActionButtonViewBinder actionButtonViewBinder = this.mActionButtonViewBinder;
        View view = this.mRemoteCopyChip;
        ActionButtonViewModel.Companion companion = ActionButtonViewModel.Companion;
        ActionButtonAppearance actionButtonAppearance = new ActionButtonAppearance(Icon.createWithResource(((ViewGroup) this).mContext, R.drawable.ic_baseline_devices_24).loadDrawable(((ViewGroup) this).mContext), null, ((ViewGroup) this).mContext.getString(R.string.clipboard_send_nearby_description), true);
        Function0 function0 = new Function0() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ClipboardOverlayCallbacks clipboardOverlayCallbacks = ClipboardOverlayView.this.mClipboardCallbacks;
                if (clipboardOverlayCallbacks == null) {
                    return null;
                }
                clipboardOverlayCallbacks.onRemoteCopyButtonTapped();
                return null;
            }
        };
        companion.getClass();
        ActionButtonViewModel withNextId = ActionButtonViewModel.Companion.withNextId(actionButtonAppearance, function0);
        actionButtonViewBinder.getClass();
        ActionButtonViewBinder.bind(view, withNextId);
        ActionButtonViewBinder actionButtonViewBinder2 = this.mActionButtonViewBinder;
        View view2 = this.mShareChip;
        ActionButtonViewModel withNextId2 = ActionButtonViewModel.Companion.withNextId(new ActionButtonAppearance(Icon.createWithResource(((ViewGroup) this).mContext, R.drawable.ic_screenshot_share).loadDrawable(((ViewGroup) this).mContext), null, ((ViewGroup) this).mContext.getString(17043010), true), new Function0() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView.2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ClipboardOverlayCallbacks clipboardOverlayCallbacks = ClipboardOverlayView.this.mClipboardCallbacks;
                if (clipboardOverlayCallbacks == null) {
                    return null;
                }
                clipboardOverlayCallbacks.onShareButtonTapped();
                return null;
            }
        });
        actionButtonViewBinder2.getClass();
        ActionButtonViewBinder.bind(view2, withNextId2);
        this.mTextPreview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                ClipboardOverlayView clipboardOverlayView = ClipboardOverlayView.this;
                int height = clipboardOverlayView.mTextPreview.getHeight() - (clipboardOverlayView.mTextPreview.getPaddingBottom() + clipboardOverlayView.mTextPreview.getPaddingTop());
                TextView textView = clipboardOverlayView.mTextPreview;
                textView.setMaxLines(Math.max(height / textView.getLineHeight(), 1));
                return true;
            }
        });
        super.onFinishInflate();
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout
    public final void setCallbacks(DraggableConstraintLayout.SwipeDismissCallbacks swipeDismissCallbacks) {
        this.mCallbacks = swipeDismissCallbacks;
        final ClipboardOverlayCallbacks clipboardOverlayCallbacks = (ClipboardOverlayCallbacks) swipeDismissCallbacks;
        Flags.screenshotShelfUi2();
        final int i = 0;
        this.mDismissButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks2 = clipboardOverlayCallbacks;
                switch (i2) {
                    case 0:
                        int i3 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onDismissButtonTapped();
                        break;
                    case 1:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onPreviewTapped();
                        break;
                    default:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onMinimizedViewTapped();
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mClipboardPreview.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i2;
                ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks2 = clipboardOverlayCallbacks;
                switch (i22) {
                    case 0:
                        int i3 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onDismissButtonTapped();
                        break;
                    case 1:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onPreviewTapped();
                        break;
                    default:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onMinimizedViewTapped();
                        break;
                }
            }
        });
        final int i3 = 2;
        this.mMinimizedPreview.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i22 = i3;
                ClipboardOverlayView.ClipboardOverlayCallbacks clipboardOverlayCallbacks2 = clipboardOverlayCallbacks;
                switch (i22) {
                    case 0:
                        int i32 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onDismissButtonTapped();
                        break;
                    case 1:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onPreviewTapped();
                        break;
                    default:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayCallbacks2.onMinimizedViewTapped();
                        break;
                }
            }
        });
        this.mClipboardCallbacks = clipboardOverlayCallbacks;
    }

    public final void setInsets(WindowInsets windowInsets, int i) {
        Rect rect;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        Insets insets2 = windowInsets.getInsets(WindowInsets.Type.ime());
        if (displayCutout == null) {
            rect = new Rect(0, 0, 0, Math.max(insets2.bottom, insets.bottom));
        } else {
            Insets waterfallInsets = displayCutout.getWaterfallInsets();
            rect = i == 1 ? new Rect(waterfallInsets.left, Math.max(displayCutout.getSafeInsetTop(), waterfallInsets.top), waterfallInsets.right, Math.max(insets2.bottom, Math.max(displayCutout.getSafeInsetBottom(), Math.max(insets.bottom, waterfallInsets.bottom)))) : new Rect(waterfallInsets.left, waterfallInsets.top, waterfallInsets.right, Math.max(insets2.bottom, Math.max(insets.bottom, waterfallInsets.bottom)));
        }
        layoutParams.setMargins(rect.left, rect.top, rect.right, rect.bottom);
        setLayoutParams(layoutParams);
        requestLayout();
    }

    public final void setMinimized(boolean z) {
        if (!z) {
            this.mMinimizedPreview.setVisibility(8);
            this.mClipboardPreview.setVisibility(0);
            this.mPreviewBorder.setVisibility(0);
            this.mActionContainer.setVisibility(0);
            return;
        }
        this.mMinimizedPreview.setVisibility(0);
        this.mClipboardPreview.setVisibility(8);
        this.mPreviewBorder.setVisibility(8);
        this.mActionContainer.setVisibility(8);
        this.mActionContainerBackground.setVisibility(8);
    }

    public ClipboardOverlayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClipboardOverlayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        new ArrayList();
        this.mActionButtonViewBinder = new ActionButtonViewBinder();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        ((ViewGroup) this).mContext.getDisplay().getRealMetrics(displayMetrics);
        AccessibilityManager.getInstance(((ViewGroup) this).mContext);
    }
}
