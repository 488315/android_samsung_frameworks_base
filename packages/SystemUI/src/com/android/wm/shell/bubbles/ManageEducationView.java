package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.wm.shell.shared.TypefaceUtils;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.bubbles.DeviceConfig;
import com.android.wm.shell.taskview.TaskView;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class ManageEducationView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BubbleExpandedView bubbleExpandedView;
    public final Lazy gotItButton$delegate;
    public boolean isHiding;
    public final Lazy manageButton$delegate;
    public final Lazy manageView$delegate;
    public final BubblePositioner positioner;
    public final Rect realManageButtonRect;

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

    public ManageEducationView(Context context, BubblePositioner bubblePositioner) {
        super(context);
        this.positioner = bubblePositioner;
        final int i = 0;
        this.manageView$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.wm.shell.bubbles.ManageEducationView$$ExternalSyntheticLambda0
            public final /* synthetic */ ManageEducationView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ManageEducationView manageEducationView = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = ManageEducationView.$r8$clinit;
                        return (ViewGroup) manageEducationView.requireViewById(R.id.manage_education_view);
                    case 1:
                        int i3 = ManageEducationView.$r8$clinit;
                        return (Button) manageEducationView.requireViewById(R.id.settings_button);
                    default:
                        int i4 = ManageEducationView.$r8$clinit;
                        return (Button) manageEducationView.requireViewById(R.id.got_it);
                }
            }
        });
        final int i2 = 1;
        this.manageButton$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.wm.shell.bubbles.ManageEducationView$$ExternalSyntheticLambda0
            public final /* synthetic */ ManageEducationView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ManageEducationView manageEducationView = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = ManageEducationView.$r8$clinit;
                        return (ViewGroup) manageEducationView.requireViewById(R.id.manage_education_view);
                    case 1:
                        int i3 = ManageEducationView.$r8$clinit;
                        return (Button) manageEducationView.requireViewById(R.id.settings_button);
                    default:
                        int i4 = ManageEducationView.$r8$clinit;
                        return (Button) manageEducationView.requireViewById(R.id.got_it);
                }
            }
        });
        final int i3 = 2;
        Lazy lazy = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.wm.shell.bubbles.ManageEducationView$$ExternalSyntheticLambda0
            public final /* synthetic */ ManageEducationView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ManageEducationView manageEducationView = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = ManageEducationView.$r8$clinit;
                        return (ViewGroup) manageEducationView.requireViewById(R.id.manage_education_view);
                    case 1:
                        int i32 = ManageEducationView.$r8$clinit;
                        return (Button) manageEducationView.requireViewById(R.id.settings_button);
                    default:
                        int i4 = ManageEducationView.$r8$clinit;
                        return (Button) manageEducationView.requireViewById(R.id.got_it);
                }
            }
        });
        this.gotItButton$delegate = lazy;
        this.realManageButtonRect = new Rect();
        LayoutInflater.from(context).inflate(R.layout.bubbles_manage_button_education, this);
        TypefaceUtils.Companion companion = TypefaceUtils.Companion;
        TypefaceUtils.FontFamily fontFamily = TypefaceUtils.FontFamily.GSF_TITLE_MEDIUM;
        companion.getClass();
        getManageButton();
        setVisibility(8);
        setElevation(getResources().getDimensionPixelSize(R.dimen.bubble_elevation));
        setLayoutDirection(3);
    }

    public final Button getManageButton() {
        return (Button) this.manageButton$delegate.getValue();
    }

    public final void hide() {
        TaskView taskView;
        BubbleExpandedView bubbleExpandedView = this.bubbleExpandedView;
        if (bubbleExpandedView != null && (taskView = bubbleExpandedView.mTaskView) != null) {
            taskView.mObscuredTouchRegion = null;
            taskView.invalidate();
        }
        if (getVisibility() != 0 || this.isHiding) {
            return;
        }
        animate().withStartAction(new Runnable() { // from class: com.android.wm.shell.bubbles.ManageEducationView.hide.1
            @Override // java.lang.Runnable
            public final void run() {
                ManageEducationView.this.isHiding = true;
            }
        }).alpha(0.0f).setDuration(200L).withEndAction(new Runnable() { // from class: com.android.wm.shell.bubbles.ManageEducationView.hide.2
            @Override // java.lang.Runnable
            public final void run() {
                ManageEducationView manageEducationView = ManageEducationView.this;
                manageEducationView.isHiding = false;
                manageEducationView.setVisibility(8);
            }
        });
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setLayoutDirection(getResources().getConfiguration().getLayoutDirection());
    }

    @Override // android.view.View
    public final void setLayoutDirection(int i) {
        super.setLayoutDirection(i);
        ((ViewGroup) this.manageView$delegate.getValue()).setBackgroundResource(i == 0 ? R.drawable.bubble_stack_user_education_bg : R.drawable.bubble_stack_user_education_bg_rtl);
    }

    public final void show(final BubbleExpandedView bubbleExpandedView, boolean z) {
        int dimensionPixelSize;
        TypedArray typedArrayObtainStyledAttributes = ((LinearLayout) this).mContext.obtainStyledAttributes(new int[]{android.R.^attr-private.closeItemLayout});
        int color = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        getManageButton().setTextColor(((LinearLayout) this).mContext.getColor(android.R.color.system_neutral1_900));
        getManageButton().setBackgroundDrawable(new ColorDrawable(color));
        ((Button) this.gotItButton$delegate.getValue()).setBackgroundDrawable(new ColorDrawable(color));
        if (getVisibility() == 0) {
            return;
        }
        this.bubbleExpandedView = bubbleExpandedView;
        TaskView taskView = bubbleExpandedView.mTaskView;
        if (taskView != null) {
            taskView.mObscuredTouchRegion = new Region(new Rect(this.positioner.mScreenRect));
            taskView.invalidate();
        }
        setAlpha(0.0f);
        setVisibility(0);
        bubbleExpandedView.mManageButton.getBoundsOnScreen(this.realManageButtonRect);
        Rect rect = this.realManageButtonRect;
        int marginStart = ((LinearLayout.LayoutParams) bubbleExpandedView.mManageButton.getLayoutParams()).getMarginStart();
        boolean z2 = getResources().getConfiguration().getLayoutDirection() == 0;
        if (!this.positioner.mDeviceConfig.isLargeScreen) {
            z = z2;
        }
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.bubble_user_education_padding_horizontal);
        ((ViewGroup) this.manageView$delegate.getValue()).setBackgroundResource(z ? R.drawable.bubble_stack_user_education_bg : R.drawable.bubble_stack_user_education_bg_rtl);
        setGravity(z ? 3 : 5);
        ViewGroup.LayoutParams layoutParams = ((ViewGroup) this.manageView$delegate.getValue()).getLayoutParams();
        if (z2 && !z) {
            dimensionPixelSize = this.positioner.mScreenRect.right - ((rect.left - marginStart) - dimensionPixelSize2);
        } else if (z2 || !z) {
            DeviceConfig deviceConfig = this.positioner.mDeviceConfig;
            dimensionPixelSize = deviceConfig.isLargeScreen ? -2 : deviceConfig.isLandscape ? getResources().getDimensionPixelSize(R.dimen.bubbles_user_education_width) : -1;
        } else {
            dimensionPixelSize = rect.right + marginStart + dimensionPixelSize2;
        }
        layoutParams.width = dimensionPixelSize;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) ((ViewGroup) this.manageView$delegate.getValue()).getLayoutParams();
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.bubble_user_education_margin_horizontal);
        marginLayoutParams.leftMargin = z ? 0 : dimensionPixelSize3;
        if (!z) {
            dimensionPixelSize3 = 0;
        }
        marginLayoutParams.rightMargin = dimensionPixelSize3;
        ViewGroup viewGroup = (ViewGroup) this.manageView$delegate.getValue();
        int i = (z2 && z) ? rect.left - marginStart : dimensionPixelSize2;
        if (!z2 && !z) {
            dimensionPixelSize2 = (this.positioner.mScreenRect.right - rect.right) - marginStart;
        }
        viewGroup.setPadding(i, viewGroup.getPaddingTop(), dimensionPixelSize2, viewGroup.getPaddingBottom());
        post(new Runnable() { // from class: com.android.wm.shell.bubbles.ManageEducationView.show.1
            @Override // java.lang.Runnable
            public final void run() {
                ManageEducationView manageEducationView = ManageEducationView.this;
                int i2 = ManageEducationView.$r8$clinit;
                Button manageButton = manageEducationView.getManageButton();
                final ManageEducationView manageEducationView2 = ManageEducationView.this;
                final BubbleExpandedView bubbleExpandedView2 = bubbleExpandedView;
                manageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.ManageEducationView.show.1.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        manageEducationView2.hide();
                        bubbleExpandedView2.requireViewById(R.id.settings_button).performClick();
                    }
                });
                Button button = (Button) ManageEducationView.this.gotItButton$delegate.getValue();
                final ManageEducationView manageEducationView3 = ManageEducationView.this;
                button.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.ManageEducationView.show.1.2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        manageEducationView3.hide();
                    }
                });
                final ManageEducationView manageEducationView4 = ManageEducationView.this;
                manageEducationView4.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.bubbles.ManageEducationView.show.1.3
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        manageEducationView4.hide();
                    }
                });
                Rect rect2 = new Rect();
                ManageEducationView.this.getManageButton().getDrawingRect(rect2);
                ((ViewGroup) ManageEducationView.this.manageView$delegate.getValue()).offsetDescendantRectToMyCoords(ManageEducationView.this.getManageButton(), rect2);
                ManageEducationView.this.setTranslationY(r1.realManageButtonRect.top - rect2.top);
                ManageEducationView.this.bringToFront();
                ManageEducationView.this.animate().setDuration(200L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f);
            }
        });
        getContext().getSharedPreferences(getContext().getPackageName(), 0).edit().putBoolean("HasSeenBubblesManageOnboarding", true).apply();
    }
}
