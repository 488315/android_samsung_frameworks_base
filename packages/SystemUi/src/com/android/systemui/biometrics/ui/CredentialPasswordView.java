package com.android.systemui.biometrics.ui;

import android.content.Context;
import android.graphics.Insets;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthPanelController;
import com.android.systemui.biometrics.ui.CredentialView;
import com.android.systemui.biometrics.ui.binder.CredentialViewBinder;
import com.android.systemui.biometrics.ui.viewmodel.CredentialViewModel;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CredentialPasswordView extends LinearLayout implements CredentialView, View.OnApplyWindowInsetsListener {
    public final Lazy accessibilityManager$delegate;
    public int bottomInset;
    public View credentialHeader;
    public View credentialInput;
    public TextView descriptionView;
    public ImageView iconView;
    public TextView subtitleView;
    public TextView titleView;

    public CredentialPasswordView(final Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.accessibilityManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.biometrics.ui.CredentialPasswordView$accessibilityManager$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
            }
        });
    }

    @Override // com.android.systemui.biometrics.ui.CredentialView
    public final void init(CredentialViewModel credentialViewModel, CredentialView.Host host, AuthPanelController authPanelController, boolean z) {
        CredentialViewBinder.bind$default(this, host, credentialViewModel, authPanelController, z);
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
        int i = this.bottomInset;
        int i2 = insets.bottom;
        if (i != i2) {
            this.bottomInset = i2;
            if (i2 <= 0 || getResources().getConfiguration().orientation != 2) {
                TextView textView = this.titleView;
                if (textView == null) {
                    textView = null;
                }
                textView.setSingleLine(false);
                TextView textView2 = this.titleView;
                if (textView2 == null) {
                    textView2 = null;
                }
                textView2.setEllipsize(null);
                TextView textView3 = this.titleView;
                (textView3 != null ? textView3 : null).setSelected(false);
            } else {
                TextView textView4 = this.titleView;
                if (textView4 == null) {
                    textView4 = null;
                }
                textView4.setSingleLine(true);
                TextView textView5 = this.titleView;
                if (textView5 == null) {
                    textView5 = null;
                }
                textView5.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                TextView textView6 = this.titleView;
                if (textView6 == null) {
                    textView6 = null;
                }
                textView6.setMarqueeRepeatLimit(-1);
                TextView textView7 = this.titleView;
                TextView textView8 = textView7 != null ? textView7 : null;
                AccessibilityManager accessibilityManager = (AccessibilityManager) this.accessibilityManager$delegate.getValue();
                textView8.setSelected((accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) ? false : true);
            }
            requestLayout();
        }
        return windowInsets;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.titleView = (TextView) requireViewById(R.id.title);
        this.subtitleView = (TextView) requireViewById(R.id.subtitle);
        this.descriptionView = (TextView) requireViewById(R.id.description);
        this.iconView = (ImageView) requireViewById(R.id.icon);
        this.subtitleView = (TextView) requireViewById(R.id.subtitle);
        requireViewById(R.id.lockPassword);
        this.credentialHeader = requireViewById(R.id.auth_credential_header);
        this.credentialInput = requireViewById(R.id.auth_credential_input);
        setOnApplyWindowInsetsListener(this);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        TextView textView;
        int width;
        int i5;
        super.onLayout(z, getLeft(), getTop(), getRight(), getBottom());
        int right = getRight();
        int top = getTop();
        int bottom = getBottom();
        TextView textView2 = this.subtitleView;
        if (textView2 == null) {
            textView2 = null;
        }
        if (!(textView2.getVisibility() == 8) ? (textView = this.subtitleView) == null : (textView = this.titleView) == null) {
            textView = null;
        }
        int bottom2 = textView.getBottom();
        TextView textView3 = this.descriptionView;
        if (textView3 == null) {
            textView3 = null;
        }
        if (!(textView3.getVisibility() == 8)) {
            TextView textView4 = this.descriptionView;
            if (textView4 == null) {
                textView4 = null;
            }
            bottom2 = textView4.getBottom();
        }
        if (getResources().getConfiguration().orientation == 2) {
            int bottom3 = getBottom();
            View view = this.credentialInput;
            if (view == null) {
                view = null;
            }
            i5 = (bottom3 - view.getHeight()) / 2;
            width = (getRight() - getLeft()) / 2;
            TextView textView5 = this.descriptionView;
            if (textView5 == null) {
                textView5 = null;
            }
            if (textView5.getBottom() > bottom) {
                ImageView imageView = this.iconView;
                if (imageView == null) {
                    imageView = null;
                }
                int bottom4 = imageView.getBottom();
                int i6 = this.bottomInset;
                if (bottom4 > i6) {
                    bottom4 = i6;
                }
                int i7 = top - bottom4;
                View view2 = this.credentialHeader;
                if (view2 == null) {
                    view2 = null;
                }
                view2.layout(getLeft(), i7, width, getBottom());
            }
        } else {
            int bottom5 = getBottom() - bottom2;
            View view3 = this.credentialInput;
            if (view3 == null) {
                view3 = null;
            }
            int height = ((bottom5 - view3.getHeight()) / 2) + bottom2;
            int right2 = getRight() - getLeft();
            View view4 = this.credentialInput;
            if (view4 == null) {
                view4 = null;
            }
            width = (right2 - view4.getWidth()) / 2;
            int bottom6 = getBottom() - height;
            View view5 = this.credentialInput;
            if (view5 == null) {
                view5 = null;
            }
            if (bottom6 < view5.getHeight()) {
                int bottom7 = getBottom();
                View view6 = this.credentialInput;
                if (view6 == null) {
                    view6 = null;
                }
                height = bottom7 - view6.getHeight();
            }
            TextView textView6 = this.descriptionView;
            if (textView6 == null) {
                textView6 = null;
            }
            if (textView6.getBottom() > height) {
                View view7 = this.credentialHeader;
                if (view7 == null) {
                    view7 = null;
                }
                view7.layout(getLeft(), top, right, height);
            }
            i5 = height;
        }
        View view8 = this.credentialInput;
        (view8 != null ? view8 : null).layout(width, i5, getRight(), getBottom());
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2) - this.bottomInset;
        setMeasuredDimension(size, size2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getWidth() / 2, VideoPlayer.MEDIA_ERROR_SYSTEM);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2, 0);
        if (getResources().getConfiguration().orientation == 2) {
            measureChildren(makeMeasureSpec, makeMeasureSpec2);
        } else {
            measureChildren(i, makeMeasureSpec2);
        }
    }
}
