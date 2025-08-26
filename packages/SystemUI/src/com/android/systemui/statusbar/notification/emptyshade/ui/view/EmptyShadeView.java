package com.android.systemui.statusbar.notification.emptyshade.ui.view;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.animation.LaunchableView;
import com.android.systemui.animation.LaunchableViewDelegate;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.notification.row.StackScrollerDecorView;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class EmptyShadeView extends StackScrollerDecorView implements LaunchableView {
    public TextView mEmptyFooterText;
    public TextView mEmptyText;
    public int mFooterIcon;
    public int mFooterText;
    public int mFooterVisibility;
    public final LaunchableViewDelegate mLaunchableViewDelegate;
    public int mSize;
    public int mTextId;

    public class EmptyShadeViewState extends ExpandableViewState {
        public EmptyShadeViewState() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            super.applyToView(view);
            if (view instanceof EmptyShadeView) {
                EmptyShadeView emptyShadeView = (EmptyShadeView) view;
                emptyShadeView.setContentVisibleAnimated(((float) this.clipTopAmount) <= ((float) EmptyShadeView.this.mEmptyText.getPaddingTop()) * 0.6f && emptyShadeView.mIsVisible);
            }
        }
    }

    public static /* synthetic */ Unit $r8$lambda$f6a1cyY_134d03mX3sLFgRPDHuk(EmptyShadeView emptyShadeView, Integer num) {
        super.setVisibility(num.intValue());
        return Unit.INSTANCE;
    }

    public EmptyShadeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTextId = R.string.empty_shade_text;
        this.mFooterVisibility = 8;
        this.mLaunchableViewDelegate = new LaunchableViewDelegate(this, new Function1() { // from class: com.android.systemui.statusbar.notification.emptyshade.ui.view.EmptyShadeView$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return EmptyShadeView.$r8$lambda$f6a1cyY_134d03mX3sLFgRPDHuk(this.f$0, (Integer) obj);
            }
        });
        this.mSize = getResources().getDimensionPixelSize(R.dimen.notifications_unseen_footer_icon_size);
        int i = ModesEmptyShadeFix.$r8$clinit;
        this.mFooterIcon = R.drawable.ic_friction_lock_closed;
        this.mFooterText = R.string.unlock_to_see_notif_text;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final ExpandableViewState createExpandableViewState() {
        return new EmptyShadeViewState();
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findContentView() {
        return findViewById(R.id.no_notifications);
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView
    public final View findSecondaryView() {
        return findViewById(R.id.no_notifications_footer);
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final Rect getPaddingForLaunchAnimation() {
        int i = ModesEmptyShadeFix.$r8$clinit;
        RefactorFlagUtils.INSTANCE.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
        return new Rect();
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void onActivityLaunchAnimationEnd() {
        int i = ModesEmptyShadeFix.$r8$clinit;
        RefactorFlagUtils.INSTANCE.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        this.mSize = getResources().getDimensionPixelSize(R.dimen.notifications_unseen_footer_icon_size);
        int i = ModesEmptyShadeFix.$r8$clinit;
        this.mEmptyText.setText(this.mTextId);
        this.mEmptyFooterText.setVisibility(this.mFooterVisibility);
        int i2 = this.mFooterText;
        this.mFooterText = i2;
        if (i2 != 0) {
            this.mEmptyFooterText.setText(i2);
        } else {
            this.mEmptyFooterText.setText((CharSequence) null);
        }
        setFooterIcon(this.mFooterIcon);
        this.mEmptyText.setTextColor(getResources().getColor(R.color.sec_no_notification_text_color));
    }

    @Override // com.android.systemui.statusbar.notification.row.StackScrollerDecorView, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mEmptyText = (TextView) findViewById(R.id.no_notifications);
        TextView textView = (TextView) findViewById(R.id.no_notifications_footer);
        this.mEmptyFooterText = textView;
        textView.setCompoundDrawableTintList(textView.getTextColors());
    }

    public final void setFooterIcon(int i) {
        Drawable drawable;
        int i2 = ModesEmptyShadeFix.$r8$clinit;
        this.mFooterIcon = i;
        if (i == 0) {
            drawable = null;
        } else {
            drawable = getContext().getDrawable(i);
            if (drawable != null) {
                int i3 = this.mSize;
                drawable.setBounds(0, 0, i3, i3);
            } else {
                Log.w("EmptyShadeView", "Invalid footer icon resource ID");
            }
        }
        this.mEmptyFooterText.setCompoundDrawablesRelative(drawable, null, null, null);
    }

    @Override // com.android.systemui.animation.LaunchableView
    public final void setShouldBlockVisibilityChanges(boolean z) {
        int i = ModesEmptyShadeFix.$r8$clinit;
        RefactorFlagUtils.INSTANCE.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
        this.mLaunchableViewDelegate.setShouldBlockVisibilityChanges(z);
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        this.mLaunchableViewDelegate.setVisibility(i);
    }
}
