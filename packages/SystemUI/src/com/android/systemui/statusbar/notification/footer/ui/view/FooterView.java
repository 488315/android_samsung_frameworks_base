package com.android.systemui.statusbar.notification.footer.ui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.row.FooterViewButton;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.StackStateAnimator;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class FooterView extends StackScrollerDecorView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FooterViewButton mClearAllButton;
    public String mManageNotificationText;
    public FooterViewButton mManageOrHistoryButton;
    public Drawable mSeenNotifsFilteredIcon;
    public String mSeenNotifsFilteredText;
    public TextView mSeenNotifsFooterTextView;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FooterViewState extends ExpandableViewState {
        public boolean hideContent;
        public boolean resetY = false;

        public FooterViewState(FooterView footerView) {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void animateTo(View view, AnimationProperties animationProperties) {
            if ((view instanceof FooterView) && this.resetY) {
                StackStateAnimator.this.mAnimationFilter.animateY = false;
                this.resetY = false;
            }
            super.animateTo(view, animationProperties);
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof FooterView) {
                ((FooterView) view).setContentVisibleAnimated(!this.hideContent);
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void copyFrom(ViewState viewState) {
            super.copyFrom(viewState);
            if (viewState instanceof FooterViewState) {
                this.hideContent = ((FooterViewState) viewState).hideContent;
            }
        }
    }

    public FooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final ExpandableViewState createExpandableViewState() {
        return new FooterViewState(this);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        final IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        super.dump(asIndenting, strArr);
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.footer.ui.view.FooterView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FooterView footerView = FooterView.this;
                IndentingPrintWriter indentingPrintWriter = asIndenting;
                int i = FooterView.$r8$clinit;
                indentingPrintWriter.println("visibility: " + DumpUtilsKt.visibilityString(footerView.getVisibility()));
                indentingPrintWriter.println("manageButton showHistory: false");
                indentingPrintWriter.println("manageButton visibility: " + DumpUtilsKt.visibilityString(footerView.mClearAllButton.getVisibility()));
                indentingPrintWriter.println("dismissButton visibility: " + DumpUtilsKt.visibilityString(footerView.mClearAllButton.getVisibility()));
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findContentView() {
        return findViewById(R.id.content);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findSecondaryView() {
        return findViewById(R.id.dismiss_text);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        ColorUpdateLogger.Companion.getClass();
        super.onConfigurationChanged(configuration);
        updateColors$2();
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        updateResources$4();
        updateContent();
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        ColorUpdateLogger.Companion.getClass();
        super.onFinishInflate();
        this.mClearAllButton = (FooterViewButton) findViewById(R.id.dismiss_text);
        this.mManageOrHistoryButton = (FooterViewButton) findViewById(R.id.manage_text);
        this.mSeenNotifsFooterTextView = (TextView) findViewById(R.id.unlock_prompt_footer);
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        updateResources$4();
        updateContent();
        updateColors$2();
    }

    public final void updateColors$2() {
        Resources.Theme theme = ((FrameLayout) this).mContext.getTheme();
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, android.R.^attr-private.materialColorOnTertiary, 0);
        Drawable drawable = theme.getDrawable(R.drawable.notif_footer_btn_background);
        Drawable drawable2 = theme.getDrawable(R.drawable.notif_footer_btn_background);
        Flags.FEATURE_FLAGS.getClass();
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, android.R.^attr-private.materialColorSurfaceContainerLowest, 0);
        if (colorAttrDefaultColor2 != 0) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(colorAttrDefaultColor2, PorterDuff.Mode.SRC_ATOP);
            drawable.setColorFilter(porterDuffColorFilter);
            drawable2.setColorFilter(porterDuffColorFilter);
        }
        this.mClearAllButton.setBackground(drawable);
        this.mClearAllButton.setTextColor(colorAttrDefaultColor);
        this.mManageOrHistoryButton.setBackground(drawable2);
        this.mManageOrHistoryButton.setTextColor(colorAttrDefaultColor);
        this.mSeenNotifsFooterTextView.setTextColor(colorAttrDefaultColor);
        this.mSeenNotifsFooterTextView.setCompoundDrawableTintList(ColorStateList.valueOf(colorAttrDefaultColor));
        ColorUpdateLogger.Companion.getClass();
    }

    public final void updateContent() {
        int i = FooterViewRefactor.$r8$clinit;
        Flags.notificationsFooterViewRefactor();
        this.mManageOrHistoryButton.setText(this.mManageNotificationText);
        this.mManageOrHistoryButton.setContentDescription(this.mManageNotificationText);
        this.mClearAllButton.setText(R.string.clear_all_notifications_text);
        this.mClearAllButton.setContentDescription(((FrameLayout) this).mContext.getString(R.string.accessibility_clear_all));
        this.mSeenNotifsFooterTextView.setText(this.mSeenNotifsFilteredText);
        this.mSeenNotifsFooterTextView.setCompoundDrawablesRelative(this.mSeenNotifsFilteredIcon, null, null, null);
    }

    public final void updateResources$4() {
        FooterViewRefactor.assertInLegacyMode();
        this.mManageNotificationText = getContext().getString(R.string.manage_notifications_text);
        getContext().getString(R.string.manage_notifications_history_text);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notifications_unseen_footer_icon_size);
        this.mSeenNotifsFilteredText = getContext().getString(R.string.unlock_to_see_notif_text);
        Drawable drawable = getContext().getDrawable(R.drawable.ic_friction_lock_closed);
        this.mSeenNotifsFilteredIcon = drawable;
        drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
    }
}
