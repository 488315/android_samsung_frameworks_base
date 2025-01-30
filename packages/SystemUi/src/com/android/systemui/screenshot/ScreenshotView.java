package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.BroadcastOptions;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.net.Uri;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Choreographer;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissHandler.C23291;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotView;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;
import com.android.systemui.shared.system.InputChannelCompat$InputEventReceiver;
import com.android.systemui.shared.system.InputMonitorCompat;
import com.android.systemui.shared.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ScreenshotView extends FrameLayout implements ViewTreeObserver.OnComputeInternalInsetsListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ActionIntentExecutor mActionExecutor;
    public HorizontalScrollView mActionsContainer;
    public ImageView mActionsContainerBackground;
    public LinearLayout mActionsView;
    public ScreenshotController.C23436 mCallbacks;
    public int mDefaultDisplay;
    public FrameLayout mDismissButton;
    public final DisplayMetrics mDisplayMetrics;
    public OverlayActionChip mEditChip;
    public final float mFixedSize;
    public FeatureFlags mFlags;
    public InputChannelCompat$InputEventReceiver mInputEventReceiver;
    public InputMonitorCompat mInputMonitor;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public int mNavMode;
    public boolean mOrientationPortrait;
    public String mPackageName;
    public PendingInteraction mPendingInteraction;
    public boolean mPendingSharedTransition;
    public OverlayActionChip mQuickShareChip;
    public ImageView mScreenshotBadge;
    public ImageView mScreenshotPreview;
    public View mScreenshotPreviewBorder;
    public DraggableConstraintLayout mScreenshotStatic;
    public ImageView mScrollablePreview;
    public ImageView mScrollingScrim;
    public OverlayActionChip mShareChip;
    public final ArrayList mSmartChips;
    public UiEventLogger mUiEventLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.screenshot.ScreenshotView$10 */
    public abstract /* synthetic */ class AbstractC234810 {

        /* renamed from: $SwitchMap$com$android$systemui$screenshot$ScreenshotView$PendingInteraction */
        public static final /* synthetic */ int[] f337x2e594f1f;

        static {
            int[] iArr = new int[PendingInteraction.values().length];
            f337x2e594f1f = iArr;
            try {
                iArr[PendingInteraction.PREVIEW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f337x2e594f1f[PendingInteraction.SHARE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f337x2e594f1f[PendingInteraction.EDIT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f337x2e594f1f[PendingInteraction.QUICK_SHARE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum PendingInteraction {
        PREVIEW,
        EDIT,
        SHARE,
        QUICK_SHARE
    }

    public static /* synthetic */ void $r8$lambda$S5f0w1hapcLjtmpENdQZIE3mjNI(ScreenshotView screenshotView, ScreenshotController.SavedImageData savedImageData) {
        screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_PREVIEW_TAPPED, 0, screenshotView.mPackageName);
        screenshotView.prepareSharedTransition();
        ActionIntentExecutor actionIntentExecutor = screenshotView.mActionExecutor;
        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
        Uri uri = savedImageData.uri;
        Context context = ((FrameLayout) screenshotView).mContext;
        actionIntentCreator.getClass();
        actionIntentExecutor.launchIntentAsync(ActionIntentCreator.createEditIntent(context, uri), ((ScreenshotController.SavedImageData.ActionTransition) savedImageData.editTransition.get()).bundle, savedImageData.owner.getIdentifier(), true);
    }

    public static /* synthetic */ void $r8$lambda$Y5uU3_TFURY3QbHulb0fUowG8lM(ScreenshotView screenshotView, ScreenshotController.SavedImageData savedImageData) {
        screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_EDIT_TAPPED, 0, screenshotView.mPackageName);
        screenshotView.prepareSharedTransition();
        ActionIntentExecutor actionIntentExecutor = screenshotView.mActionExecutor;
        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
        Uri uri = savedImageData.uri;
        Context context = ((FrameLayout) screenshotView).mContext;
        actionIntentCreator.getClass();
        actionIntentExecutor.launchIntentAsync(ActionIntentCreator.createEditIntent(context, uri), ((ScreenshotController.SavedImageData.ActionTransition) savedImageData.editTransition.get()).bundle, savedImageData.owner.getIdentifier(), true);
    }

    public ScreenshotView(Context context) {
        this(context, null);
    }

    public final void addQuickShareChip(Notification.Action action) {
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            this.mSmartChips.remove(overlayActionChip);
            this.mActionsView.removeView(this.mQuickShareChip);
        }
        if (this.mPendingInteraction == PendingInteraction.QUICK_SHARE) {
            this.mPendingInteraction = null;
        }
        if (this.mPendingInteraction == null) {
            OverlayActionChip overlayActionChip2 = (OverlayActionChip) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.overlay_action_chip, (ViewGroup) this.mActionsView, false);
            this.mQuickShareChip = overlayActionChip2;
            overlayActionChip2.setText(action.title);
            this.mQuickShareChip.setIcon(action.getIcon(), false);
            this.mQuickShareChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    ScreenshotView screenshotView = ScreenshotView.this;
                    OverlayActionChip overlayActionChip3 = screenshotView.mShareChip;
                    overlayActionChip3.mIsPending = false;
                    overlayActionChip3.setPressed(false);
                    OverlayActionChip overlayActionChip4 = screenshotView.mEditChip;
                    overlayActionChip4.mIsPending = false;
                    overlayActionChip4.setPressed(false);
                    OverlayActionChip overlayActionChip5 = screenshotView.mQuickShareChip;
                    overlayActionChip5.mIsPending = true;
                    overlayActionChip5.setPressed(true);
                    screenshotView.mPendingInteraction = ScreenshotView.PendingInteraction.QUICK_SHARE;
                }
            });
            this.mQuickShareChip.setAlpha(1.0f);
            this.mActionsView.addView(this.mQuickShareChip);
            this.mSmartChips.add(this.mQuickShareChip);
        }
    }

    public final Region getTouchRegion(boolean z) {
        Region region = new Region();
        Rect rect = new Rect();
        this.mScreenshotPreview.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mActionsContainerBackground.getBoundsOnScreen(rect);
        rect.inset((int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f), (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, -12.0f));
        region.op(rect, Region.Op.UNION);
        this.mDismissButton.getBoundsOnScreen(rect);
        region.op(rect, Region.Op.UNION);
        View findViewById = findViewById(R.id.message_dismiss_button);
        if (findViewById != null) {
            findViewById.getBoundsOnScreen(rect);
            region.op(rect, Region.Op.UNION);
        }
        if (z && this.mScrollingScrim.getVisibility() == 0) {
            Rect rect2 = new Rect();
            this.mScrollingScrim.getBoundsOnScreen(rect2);
            region.op(rect2, Region.Op.UNION);
        }
        if (QuickStepContract.isGesturalMode(this.mNavMode)) {
            Insets insets = ((WindowManager) ((FrameLayout) this).mContext.getSystemService(WindowManager.class)).getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.systemGestures());
            Rect rect3 = new Rect(0, 0, insets.left, this.mDisplayMetrics.heightPixels);
            region.op(rect3, Region.Op.UNION);
            DisplayMetrics displayMetrics = this.mDisplayMetrics;
            int i = displayMetrics.widthPixels;
            rect3.set(i - insets.right, 0, i, displayMetrics.heightPixels);
            region.op(rect3, Region.Op.UNION);
        }
        return region;
    }

    public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(getTouchRegion(true));
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ImageView imageView = (ImageView) findViewById(R.id.screenshot_scrolling_scrim);
        Objects.requireNonNull(imageView);
        this.mScrollingScrim = imageView;
        DraggableConstraintLayout draggableConstraintLayout = (DraggableConstraintLayout) findViewById(R.id.screenshot_static);
        Objects.requireNonNull(draggableConstraintLayout);
        this.mScreenshotStatic = draggableConstraintLayout;
        ImageView imageView2 = (ImageView) findViewById(R.id.screenshot_preview);
        Objects.requireNonNull(imageView2);
        this.mScreenshotPreview = imageView2;
        View findViewById = findViewById(R.id.screenshot_preview_border);
        Objects.requireNonNull(findViewById);
        this.mScreenshotPreviewBorder = findViewById;
        this.mScreenshotPreview.setClipToOutline(true);
        ImageView imageView3 = (ImageView) findViewById(R.id.screenshot_badge);
        Objects.requireNonNull(imageView3);
        this.mScreenshotBadge = imageView3;
        ImageView imageView4 = (ImageView) findViewById(R.id.actions_container_background);
        Objects.requireNonNull(imageView4);
        this.mActionsContainerBackground = imageView4;
        HorizontalScrollView horizontalScrollView = (HorizontalScrollView) findViewById(R.id.actions_container);
        Objects.requireNonNull(horizontalScrollView);
        this.mActionsContainer = horizontalScrollView;
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.screenshot_actions);
        Objects.requireNonNull(linearLayout);
        this.mActionsView = linearLayout;
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.screenshot_dismiss_button);
        Objects.requireNonNull(frameLayout);
        this.mDismissButton = frameLayout;
        ImageView imageView5 = (ImageView) findViewById(R.id.screenshot_scrollable_preview);
        Objects.requireNonNull(imageView5);
        this.mScrollablePreview = imageView5;
        Objects.requireNonNull((ImageView) findViewById(R.id.screenshot_flash));
        OverlayActionChip overlayActionChip = (OverlayActionChip) this.mActionsContainer.findViewById(R.id.screenshot_share_chip);
        Objects.requireNonNull(overlayActionChip);
        this.mShareChip = overlayActionChip;
        OverlayActionChip overlayActionChip2 = (OverlayActionChip) this.mActionsContainer.findViewById(R.id.screenshot_edit_chip);
        Objects.requireNonNull(overlayActionChip2);
        this.mEditChip = overlayActionChip2;
        Objects.requireNonNull((OverlayActionChip) this.mActionsContainer.findViewById(R.id.screenshot_scroll_chip));
        int dpToPx = (int) FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 12.0f);
        this.mScreenshotPreview.setTouchDelegate(new TouchDelegate(new Rect(dpToPx, dpToPx, dpToPx, dpToPx), this.mScreenshotPreview));
        this.mActionsContainerBackground.setTouchDelegate(new TouchDelegate(new Rect(dpToPx, dpToPx, dpToPx, dpToPx), this.mActionsContainerBackground));
        setFocusable(true);
        this.mActionsContainer.setScrollX(0);
        this.mNavMode = getResources().getInteger(android.R.integer.config_notificationsBatteryFullARGB);
        this.mOrientationPortrait = getResources().getConfiguration().orientation == 1;
        getResources().getConfiguration().getLayoutDirection();
        setFocusableInTouchMode(true);
        requestFocus();
        this.mScreenshotStatic.setCallbacks(new DraggableConstraintLayout.SwipeDismissCallbacks() { // from class: com.android.systemui.screenshot.ScreenshotView.3
            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public final void onDismissComplete() {
                ScreenshotView screenshotView = ScreenshotView.this;
                if (screenshotView.mInteractionJankMonitor.isInstrumenting(54)) {
                    screenshotView.mInteractionJankMonitor.end(54);
                }
                ScreenshotController.m1637$$Nest$mfinishDismiss(ScreenshotController.this);
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public final void onInteraction() {
                ScreenshotController.this.mScreenshotHandler.resetTimeout();
            }

            @Override // com.android.systemui.screenshot.DraggableConstraintLayout.SwipeDismissCallbacks
            public final void onSwipeDismissInitiated(Animator animator) {
                ScreenshotView screenshotView = ScreenshotView.this;
                screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SWIPE_DISMISSED, 0, screenshotView.mPackageName);
            }
        });
    }

    public final void prepareSharedTransition() {
        this.mPendingSharedTransition = true;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ScreenshotView screenshotView = ScreenshotView.this;
                int i = ScreenshotView.$r8$clinit;
                screenshotView.getClass();
                float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                screenshotView.mDismissButton.setAlpha(animatedFraction);
                screenshotView.mActionsContainerBackground.setAlpha(animatedFraction);
                screenshotView.mActionsContainer.setAlpha(animatedFraction);
                screenshotView.mScreenshotPreviewBorder.setAlpha(animatedFraction);
                screenshotView.mScreenshotBadge.setAlpha(animatedFraction);
            }
        });
        ofFloat.setDuration(600L);
        ofFloat.start();
    }

    public final void reset() {
        DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler = this.mScreenshotStatic.mSwipeDismissHandler;
        ValueAnimator valueAnimator = swipeDismissHandler.mDismissAnimation;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            swipeDismissHandler.mDismissAnimation.cancel();
        }
        getViewTreeObserver().removeOnComputeInternalInsetsListener(this);
        this.mScreenshotPreview.setImageDrawable(null);
        this.mScreenshotPreview.setVisibility(4);
        this.mScreenshotPreview.setAlpha(1.0f);
        this.mScreenshotPreviewBorder.setAlpha(0.0f);
        this.mScreenshotBadge.setAlpha(0.0f);
        this.mScreenshotBadge.setVisibility(8);
        this.mScreenshotBadge.setImageDrawable(null);
        this.mPendingSharedTransition = false;
        this.mActionsContainerBackground.setVisibility(4);
        this.mActionsContainer.setVisibility(8);
        this.mDismissButton.setVisibility(8);
        this.mScrollingScrim.setVisibility(8);
        this.mScrollablePreview.setVisibility(8);
        this.mScreenshotStatic.setTranslationX(0.0f);
        this.mScreenshotPreview.setContentDescription(((FrameLayout) this).mContext.getResources().getString(R.string.screenshot_preview_description));
        this.mScreenshotPreview.setOnClickListener(null);
        this.mShareChip.setOnClickListener(null);
        this.mScrollingScrim.setVisibility(8);
        this.mEditChip.setOnClickListener(null);
        OverlayActionChip overlayActionChip = this.mShareChip;
        overlayActionChip.mIsPending = false;
        overlayActionChip.setPressed(false);
        OverlayActionChip overlayActionChip2 = this.mEditChip;
        overlayActionChip2.mIsPending = false;
        overlayActionChip2.setPressed(false);
        this.mPendingInteraction = null;
        Iterator it = this.mSmartChips.iterator();
        while (it.hasNext()) {
            this.mActionsView.removeView((OverlayActionChip) it.next());
        }
        this.mSmartChips.clear();
        this.mQuickShareChip = null;
        setAlpha(1.0f);
        this.mScreenshotStatic.setAlpha(1.0f);
    }

    public final void setChipIntents(final ScreenshotController.SavedImageData savedImageData) {
        final int i = 0;
        this.mShareChip.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda2
            public final /* synthetic */ ScreenshotView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        ScreenshotView screenshotView = this.f$0;
                        ScreenshotController.SavedImageData savedImageData2 = savedImageData;
                        screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SHARE_TAPPED, 0, screenshotView.mPackageName);
                        screenshotView.prepareSharedTransition();
                        FeatureFlags featureFlags = screenshotView.mFlags;
                        Flags flags = Flags.INSTANCE;
                        featureFlags.getClass();
                        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
                        Uri uri = savedImageData2.uri;
                        String str = savedImageData2.subject;
                        actionIntentCreator.getClass();
                        screenshotView.mActionExecutor.launchIntentAsync(ActionIntentCreator.createShareIntent(uri, str, null), ((ScreenshotController.SavedImageData.ActionTransition) savedImageData2.shareTransition.get()).bundle, savedImageData2.owner.getIdentifier(), false);
                        break;
                    case 1:
                        ScreenshotView.$r8$lambda$Y5uU3_TFURY3QbHulb0fUowG8lM(this.f$0, savedImageData);
                        break;
                    default:
                        ScreenshotView.$r8$lambda$S5f0w1hapcLjtmpENdQZIE3mjNI(this.f$0, savedImageData);
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mEditChip.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda2
            public final /* synthetic */ ScreenshotView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        ScreenshotView screenshotView = this.f$0;
                        ScreenshotController.SavedImageData savedImageData2 = savedImageData;
                        screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SHARE_TAPPED, 0, screenshotView.mPackageName);
                        screenshotView.prepareSharedTransition();
                        FeatureFlags featureFlags = screenshotView.mFlags;
                        Flags flags = Flags.INSTANCE;
                        featureFlags.getClass();
                        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
                        Uri uri = savedImageData2.uri;
                        String str = savedImageData2.subject;
                        actionIntentCreator.getClass();
                        screenshotView.mActionExecutor.launchIntentAsync(ActionIntentCreator.createShareIntent(uri, str, null), ((ScreenshotController.SavedImageData.ActionTransition) savedImageData2.shareTransition.get()).bundle, savedImageData2.owner.getIdentifier(), false);
                        break;
                    case 1:
                        ScreenshotView.$r8$lambda$Y5uU3_TFURY3QbHulb0fUowG8lM(this.f$0, savedImageData);
                        break;
                    default:
                        ScreenshotView.$r8$lambda$S5f0w1hapcLjtmpENdQZIE3mjNI(this.f$0, savedImageData);
                        break;
                }
            }
        });
        final int i3 = 2;
        this.mScreenshotPreview.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda2
            public final /* synthetic */ ScreenshotView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i3) {
                    case 0:
                        ScreenshotView screenshotView = this.f$0;
                        ScreenshotController.SavedImageData savedImageData2 = savedImageData;
                        screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SHARE_TAPPED, 0, screenshotView.mPackageName);
                        screenshotView.prepareSharedTransition();
                        FeatureFlags featureFlags = screenshotView.mFlags;
                        Flags flags = Flags.INSTANCE;
                        featureFlags.getClass();
                        ActionIntentCreator actionIntentCreator = ActionIntentCreator.INSTANCE;
                        Uri uri = savedImageData2.uri;
                        String str = savedImageData2.subject;
                        actionIntentCreator.getClass();
                        screenshotView.mActionExecutor.launchIntentAsync(ActionIntentCreator.createShareIntent(uri, str, null), ((ScreenshotController.SavedImageData.ActionTransition) savedImageData2.shareTransition.get()).bundle, savedImageData2.owner.getIdentifier(), false);
                        break;
                    case 1:
                        ScreenshotView.$r8$lambda$Y5uU3_TFURY3QbHulb0fUowG8lM(this.f$0, savedImageData);
                        break;
                    default:
                        ScreenshotView.$r8$lambda$S5f0w1hapcLjtmpENdQZIE3mjNI(this.f$0, savedImageData);
                        break;
                }
            }
        });
        OverlayActionChip overlayActionChip = this.mQuickShareChip;
        if (overlayActionChip != null) {
            Notification.Action action = savedImageData.quickShareAction;
            if (action != null) {
                final PendingIntent pendingIntent = action.actionIntent;
                final Runnable runnable = new Runnable(this) { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda3
                    public final /* synthetic */ ScreenshotView f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i) {
                            case 0:
                                ScreenshotView screenshotView = this.f$0;
                                screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SMART_ACTION_TAPPED, 0, screenshotView.mPackageName);
                                DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler = screenshotView.mScreenshotStatic.mSwipeDismissHandler;
                                ValueAnimator createSwipeDismissAnimation = swipeDismissHandler.createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(swipeDismissHandler.mDisplayMetrics, 1.0f));
                                swipeDismissHandler.mDismissAnimation = createSwipeDismissAnimation;
                                createSwipeDismissAnimation.addListener(swipeDismissHandler.new C23291());
                                swipeDismissHandler.mDismissAnimation.start();
                                break;
                            default:
                                ScreenshotView screenshotView2 = this.f$0;
                                screenshotView2.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SMART_ACTION_TAPPED, 0, screenshotView2.mPackageName);
                                DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler2 = screenshotView2.mScreenshotStatic.mSwipeDismissHandler;
                                ValueAnimator createSwipeDismissAnimation2 = swipeDismissHandler2.createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(swipeDismissHandler2.mDisplayMetrics, 1.0f));
                                swipeDismissHandler2.mDismissAnimation = createSwipeDismissAnimation2;
                                createSwipeDismissAnimation2.addListener(swipeDismissHandler2.new C23291());
                                swipeDismissHandler2.mDismissAnimation.start();
                                break;
                        }
                    }
                };
                overlayActionChip.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.OverlayActionChip$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        PendingIntent pendingIntent2 = pendingIntent;
                        Runnable runnable2 = runnable;
                        int i4 = OverlayActionChip.$r8$clinit;
                        try {
                            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                            makeBasic.setInteractive(true);
                            pendingIntent2.send(makeBasic.toBundle());
                            runnable2.run();
                        } catch (PendingIntent.CanceledException e) {
                            Log.e("ScreenshotActionChip", "Intent cancelled", e);
                        }
                    }
                });
            } else {
                Log.wtf("Screenshot", "Showed quick share chip, but quick share intent was null");
                if (this.mPendingInteraction == PendingInteraction.QUICK_SHARE) {
                    this.mPendingInteraction = null;
                }
                this.mQuickShareChip.setVisibility(8);
            }
        }
        PendingInteraction pendingInteraction = this.mPendingInteraction;
        if (pendingInteraction != null) {
            int i4 = AbstractC234810.f337x2e594f1f[pendingInteraction.ordinal()];
            if (i4 == 1) {
                this.mScreenshotPreview.callOnClick();
                return;
            }
            if (i4 == 2) {
                this.mShareChip.callOnClick();
                return;
            } else if (i4 == 3) {
                this.mEditChip.callOnClick();
                return;
            } else {
                if (i4 != 4) {
                    return;
                }
                this.mQuickShareChip.callOnClick();
                return;
            }
        }
        LayoutInflater from = LayoutInflater.from(((FrameLayout) this).mContext);
        Iterator it = ((ArrayList) savedImageData.smartActions).iterator();
        while (it.hasNext()) {
            Notification.Action action2 = (Notification.Action) it.next();
            OverlayActionChip overlayActionChip2 = (OverlayActionChip) from.inflate(R.layout.overlay_action_chip, (ViewGroup) this.mActionsView, false);
            overlayActionChip2.setText(action2.title);
            overlayActionChip2.setIcon(action2.getIcon(), false);
            final PendingIntent pendingIntent2 = action2.actionIntent;
            final Runnable runnable2 = new Runnable(this) { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda3
                public final /* synthetic */ ScreenshotView f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            ScreenshotView screenshotView = this.f$0;
                            screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SMART_ACTION_TAPPED, 0, screenshotView.mPackageName);
                            DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler = screenshotView.mScreenshotStatic.mSwipeDismissHandler;
                            ValueAnimator createSwipeDismissAnimation = swipeDismissHandler.createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(swipeDismissHandler.mDisplayMetrics, 1.0f));
                            swipeDismissHandler.mDismissAnimation = createSwipeDismissAnimation;
                            createSwipeDismissAnimation.addListener(swipeDismissHandler.new C23291());
                            swipeDismissHandler.mDismissAnimation.start();
                            break;
                        default:
                            ScreenshotView screenshotView2 = this.f$0;
                            screenshotView2.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SMART_ACTION_TAPPED, 0, screenshotView2.mPackageName);
                            DraggableConstraintLayout.SwipeDismissHandler swipeDismissHandler2 = screenshotView2.mScreenshotStatic.mSwipeDismissHandler;
                            ValueAnimator createSwipeDismissAnimation2 = swipeDismissHandler2.createSwipeDismissAnimation(FloatingWindowUtil.dpToPx(swipeDismissHandler2.mDisplayMetrics, 1.0f));
                            swipeDismissHandler2.mDismissAnimation = createSwipeDismissAnimation2;
                            createSwipeDismissAnimation2.addListener(swipeDismissHandler2.new C23291());
                            swipeDismissHandler2.mDismissAnimation.start();
                            break;
                    }
                }
            };
            overlayActionChip2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.screenshot.OverlayActionChip$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PendingIntent pendingIntent22 = pendingIntent2;
                    Runnable runnable22 = runnable2;
                    int i42 = OverlayActionChip.$r8$clinit;
                    try {
                        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
                        makeBasic.setInteractive(true);
                        pendingIntent22.send(makeBasic.toBundle());
                        runnable22.run();
                    } catch (PendingIntent.CanceledException e) {
                        Log.e("ScreenshotActionChip", "Intent cancelled", e);
                    }
                }
            });
            overlayActionChip2.setAlpha(1.0f);
            LinearLayout linearLayout = this.mActionsView;
            linearLayout.addView(overlayActionChip2, linearLayout.getChildCount() - 1);
            this.mSmartChips.add(overlayActionChip2);
        }
    }

    public final void stopInputListening() {
        InputMonitorCompat inputMonitorCompat = this.mInputMonitor;
        if (inputMonitorCompat != null) {
            inputMonitorCompat.mInputMonitor.dispose();
            this.mInputMonitor = null;
        }
        InputChannelCompat$InputEventReceiver inputChannelCompat$InputEventReceiver = this.mInputEventReceiver;
        if (inputChannelCompat$InputEventReceiver != null) {
            inputChannelCompat$InputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
    }

    public final void updateOrientation(WindowInsets windowInsets) {
        this.mOrientationPortrait = ((FrameLayout) this).mContext.getResources().getConfiguration().orientation == 1;
        this.mOrientationPortrait = ((FrameLayout) this).mContext.getResources().getConfiguration().orientation == 1;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mScreenshotStatic.getLayoutParams();
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        if (displayCutout == null) {
            layoutParams.setMargins(0, 0, 0, insets.bottom);
        } else {
            Insets waterfallInsets = displayCutout.getWaterfallInsets();
            if (this.mOrientationPortrait) {
                layoutParams.setMargins(waterfallInsets.left, Math.max(displayCutout.getSafeInsetTop(), waterfallInsets.top), waterfallInsets.right, Math.max(displayCutout.getSafeInsetBottom(), Math.max(insets.bottom, waterfallInsets.bottom)));
            } else {
                layoutParams.setMargins(Math.max(displayCutout.getSafeInsetLeft(), waterfallInsets.left), waterfallInsets.top, Math.max(displayCutout.getSafeInsetRight(), waterfallInsets.right), Math.max(insets.bottom, waterfallInsets.bottom));
            }
        }
        this.mScreenshotStatic.setLayoutParams(layoutParams);
        this.mScreenshotStatic.requestLayout();
        ViewGroup.LayoutParams layoutParams2 = this.mScreenshotPreview.getLayoutParams();
        if (this.mOrientationPortrait) {
            layoutParams2.width = (int) this.mFixedSize;
            layoutParams2.height = -2;
            this.mScreenshotPreview.setScaleType(ImageView.ScaleType.FIT_START);
        } else {
            layoutParams2.width = -2;
            layoutParams2.height = (int) this.mFixedSize;
            this.mScreenshotPreview.setScaleType(ImageView.ScaleType.FIT_END);
        }
        this.mScreenshotPreview.setLayoutParams(layoutParams2);
    }

    public ScreenshotView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ScreenshotView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ScreenshotView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDefaultDisplay = 0;
        this.mPackageName = "";
        this.mSmartChips = new ArrayList();
        Resources resources = ((FrameLayout) this).mContext.getResources();
        this.mInteractionJankMonitor = InteractionJankMonitor.getInstance();
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setInteractive(true);
        makeBasic.toBundle();
        this.mFixedSize = resources.getDimensionPixelSize(R.dimen.overlay_x_scale);
        AnimationUtils.loadInterpolator(((FrameLayout) this).mContext, android.R.interpolator.fast_out_slow_in);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        ((FrameLayout) this).mContext.getDisplay().getRealMetrics(displayMetrics);
        AccessibilityManager.getInstance(((FrameLayout) this).mContext);
        new GestureDetector(((FrameLayout) this).mContext, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.screenshot.ScreenshotView.1
            public final Rect mActionsRect = new Rect();

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                ScreenshotView.this.mActionsContainer.getBoundsOnScreen(this.mActionsRect);
                return (this.mActionsRect.contains((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY()) && ScreenshotView.this.mActionsContainer.canScrollHorizontally((int) f)) ? false : true;
            }
        }).setIsLongpressEnabled(false);
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.screenshot.ScreenshotView.2
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                final ScreenshotView screenshotView = ScreenshotView.this;
                int i3 = ScreenshotView.$r8$clinit;
                screenshotView.stopInputListening();
                InputMonitorCompat inputMonitorCompat = new InputMonitorCompat("Screenshot", screenshotView.mDefaultDisplay);
                screenshotView.mInputMonitor = inputMonitorCompat;
                screenshotView.mInputEventReceiver = new InputChannelCompat$InputEventReceiver(inputMonitorCompat.mInputMonitor.getInputChannel(), Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat$InputEventListener() { // from class: com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda0
                    @Override // com.android.systemui.shared.system.InputChannelCompat$InputEventListener
                    public final void onInputEvent(InputEvent inputEvent) {
                        View peekDecorView;
                        int i4 = ScreenshotView.$r8$clinit;
                        ScreenshotView screenshotView2 = ScreenshotView.this;
                        screenshotView2.getClass();
                        if (inputEvent instanceof MotionEvent) {
                            MotionEvent motionEvent = (MotionEvent) inputEvent;
                            if (motionEvent.getActionMasked() != 0 || screenshotView2.getTouchRegion(false).contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                                return;
                            }
                            ScreenshotController screenshotController = ScreenshotController.this;
                            WindowManager.LayoutParams layoutParams = screenshotController.mWindowLayoutParams;
                            int i5 = layoutParams.flags;
                            int i6 = i5 | 8;
                            layoutParams.flags = i6;
                            if (i6 == i5 || (peekDecorView = screenshotController.mWindow.peekDecorView()) == null || !peekDecorView.isAttachedToWindow()) {
                                return;
                            }
                            screenshotController.mWindowManager.updateViewLayout(peekDecorView, layoutParams);
                        }
                    }
                });
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
                ScreenshotView.this.stopInputListening();
            }
        });
    }
}
