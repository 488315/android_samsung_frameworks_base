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
import com.android.systemui.R;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.footer.shared.NotifRedesignFooter;
import com.android.systemui.statusbar.notification.row.FooterViewButton;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FooterView extends StackScrollerDecorView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public FooterViewButton mClearAllButton;
    public FooterViewButton mManageOrHistoryButton;
    public TextView mSeenNotifsFooterTextView;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FooterViewState extends ExpandableViewState {
        public boolean hideContent;

        public FooterViewState(FooterView footerView) {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void animateTo(View view, AnimationProperties animationProperties) {
            boolean z = view instanceof FooterView;
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
        DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.footer.ui.view.FooterView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FooterView footerView = FooterView.this;
                IndentingPrintWriter indentingPrintWriter = asIndenting;
                int i = FooterView.$r8$clinit;
                indentingPrintWriter.println("visibility: " + DumpUtilsKt.visibilityString(footerView.getVisibility()));
                if (footerView.mManageOrHistoryButton != null) {
                    indentingPrintWriter.println("mManageOrHistoryButton visibility: " + DumpUtilsKt.visibilityString(footerView.mManageOrHistoryButton.getVisibility()));
                }
                if (footerView.mClearAllButton != null) {
                    indentingPrintWriter.println("mClearAllButton visibility: " + DumpUtilsKt.visibilityString(footerView.mClearAllButton.getVisibility()));
                }
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
        updateColors$1();
        int i = NotifRedesignFooter.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        ColorUpdateLogger.Companion.getClass();
        super.onFinishInflate();
        this.mClearAllButton = (FooterViewButton) findViewById(R.id.dismiss_text);
        int i = NotifRedesignFooter.$r8$clinit;
        this.mManageOrHistoryButton = (FooterViewButton) findViewById(R.id.manage_text);
        this.mSeenNotifsFooterTextView = (TextView) findViewById(R.id.unlock_prompt_footer);
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        updateColors$1();
    }

    public final void updateColors$1() {
        Resources.Theme theme = ((FrameLayout) this).mContext.getTheme();
        int color = ((FrameLayout) this).mContext.getColor(android.R.color.search_url_text_material_light);
        Drawable drawable = theme.getDrawable(R.drawable.notif_footer_btn_background);
        Drawable drawable2 = theme.getDrawable(R.drawable.notif_footer_btn_background);
        int i = NotifRedesignFooter.$r8$clinit;
        int color2 = ((FrameLayout) this).mContext.getColor(android.R.color.sliding_tab_text_color_shadow);
        if (color2 != 0) {
            PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_ATOP);
            drawable.setColorFilter(porterDuffColorFilter);
            drawable2.setColorFilter(porterDuffColorFilter);
        }
        this.mClearAllButton.setBackground(drawable);
        this.mClearAllButton.setTextColor(color);
        this.mManageOrHistoryButton.setBackground(drawable2);
        this.mManageOrHistoryButton.setTextColor(color);
        this.mSeenNotifsFooterTextView.setTextColor(color);
        this.mSeenNotifsFooterTextView.setCompoundDrawableTintList(ColorStateList.valueOf(color));
        ColorUpdateLogger.Companion.getClass();
    }
}
