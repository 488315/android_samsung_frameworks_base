package com.android.systemui.statusbar.notification.row;

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
import com.android.systemui.R;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FooterView extends StackScrollerDecorView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FooterViewButton mClearAllButton;
    public FooterViewButton mManageButton;
    public String mManageNotificationText;
    public Drawable mSeenNotifsFilteredIcon;
    public String mSeenNotifsFilteredText;
    public TextView mSeenNotifsFooterTextView;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FooterViewState extends ExpandableViewState {
        public boolean hideContent;

        public FooterViewState(FooterView footerView) {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof FooterView) {
                ((FooterView) view).setContentVisible(null, !this.hideContent, true);
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void copyFrom(ViewState viewState) {
            throw null;
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
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.FooterView$$ExternalSyntheticLambda0
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
        super.onConfigurationChanged(configuration);
        updateColors();
        this.mClearAllButton.setText(R.string.clear_all_notifications_text);
        this.mClearAllButton.setContentDescription(((FrameLayout) this).mContext.getString(R.string.accessibility_clear_all));
        updateResources();
        updateContent();
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mClearAllButton = (FooterViewButton) findSecondaryView();
        this.mManageButton = (FooterViewButton) findViewById(R.id.manage_text);
        this.mSeenNotifsFooterTextView = (TextView) findViewById(R.id.unlock_prompt_footer);
        updateResources();
        updateContent();
        updateColors();
    }

    public final void updateColors() {
        Resources.Theme theme = ((FrameLayout) this).mContext.getTheme();
        int color = getResources().getColor(R.color.notif_pill_text, theme);
        Drawable drawable = theme.getDrawable(R.drawable.notif_footer_btn_background);
        Drawable drawable2 = theme.getDrawable(R.drawable.notif_footer_btn_background);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(android.R.^attr-private.colorSurface, ((FrameLayout) this).mContext, 0);
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(colorAttrDefaultColor, PorterDuff.Mode.SRC_ATOP);
        if (colorAttrDefaultColor != 0) {
            drawable.setColorFilter(porterDuffColorFilter);
            drawable2.setColorFilter(porterDuffColorFilter);
        }
        this.mClearAllButton.setBackground(drawable);
        this.mClearAllButton.setTextColor(color);
        this.mManageButton.setBackground(drawable2);
        this.mManageButton.setTextColor(color);
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(android.R.attr.textColorPrimary, ((FrameLayout) this).mContext, 0);
        this.mSeenNotifsFooterTextView.setTextColor(colorAttrDefaultColor2);
        this.mSeenNotifsFooterTextView.setCompoundDrawableTintList(ColorStateList.valueOf(colorAttrDefaultColor2));
    }

    public final void updateContent() {
        this.mManageButton.setText(this.mManageNotificationText);
        this.mManageButton.setContentDescription(this.mManageNotificationText);
        this.mSeenNotifsFooterTextView.setText(this.mSeenNotifsFilteredText);
        this.mSeenNotifsFooterTextView.setCompoundDrawablesRelative(this.mSeenNotifsFilteredIcon, null, null, null);
    }

    public final void updateResources() {
        this.mManageNotificationText = getContext().getString(R.string.manage_notifications_text);
        getContext().getString(R.string.manage_notifications_history_text);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notifications_unseen_footer_icon_size);
        this.mSeenNotifsFilteredText = getContext().getString(R.string.unlock_to_see_notif_text);
        Drawable drawable = getContext().getDrawable(R.drawable.ic_friction_lock_closed);
        this.mSeenNotifsFilteredIcon = drawable;
        drawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
    }
}
