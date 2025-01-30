package com.android.systemui.statusbar.notification.row;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.AlphaOptimizedLinearLayout;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.ViewTransformationHelper;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.TransformState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class HybridNotificationView extends AlphaOptimizedLinearLayout implements TransformableView, NotificationFadeAware {
    public int mPrimaryTextColor;
    public int mSecondaryTextColor;
    public TextView mTextView;
    public TextView mTitleView;
    public final ViewTransformationHelper mTransformationHelper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FadeOutAndDownWithTitleTransformation extends ViewTransformationHelper.CustomTransformation {
        public final View mView;

        public FadeOutAndDownWithTitleTransformation(View view) {
            this.mView = view;
        }

        @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
        public final boolean transformFrom(TransformState transformState, TransformableView transformableView, float f) {
            TransformState currentState = transformableView.getCurrentState(1);
            CrossFadeHelper.fadeIn(this.mView, f, true);
            if (currentState != null) {
                transformState.transformViewFrom(currentState, 16, null, f);
                currentState.recycle();
            }
            return true;
        }

        @Override // com.android.systemui.statusbar.ViewTransformationHelper.CustomTransformation
        public final boolean transformTo(TransformState transformState, TransformableView transformableView, float f) {
            TransformState currentState = transformableView.getCurrentState(1);
            CrossFadeHelper.fadeOut(this.mView, f, true);
            if (currentState != null) {
                transformState.transformViewTo(currentState, 16, null, f);
                currentState.recycle();
            }
            return true;
        }
    }

    public HybridNotificationView(Context context) {
        this(context, null);
    }

    public void bind(CharSequence charSequence, CharSequence charSequence2, View view) {
        this.mTitleView.setText(charSequence != null ? charSequence.toString() : charSequence);
        this.mTitleView.setVisibility(TextUtils.isEmpty(charSequence) ? 8 : 0);
        if (TextUtils.isEmpty(charSequence2)) {
            this.mTextView.setVisibility(8);
            this.mTextView.setText((CharSequence) null);
        } else {
            this.mTextView.setVisibility(0);
            this.mTextView.setText(charSequence2.toString());
        }
        requestLayout();
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final TransformState getCurrentState(int i) {
        return this.mTransformationHelper.getCurrentState(i);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        TypedArray obtainStyledAttributes = ((LinearLayout) this).mContext.getTheme().obtainStyledAttributes(R.style.Theme.DeviceDefault.DayNight, new int[]{R.attr.textColorPrimary, R.attr.textColorSecondary});
        if (obtainStyledAttributes != null) {
            try {
                this.mPrimaryTextColor = obtainStyledAttributes.getColor(0, this.mPrimaryTextColor);
                this.mSecondaryTextColor = obtainStyledAttributes.getColor(1, this.mSecondaryTextColor);
            } catch (Throwable th) {
                try {
                    obtainStyledAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (obtainStyledAttributes != null) {
            obtainStyledAttributes.close();
        }
        this.mTitleView = (TextView) findViewById(com.android.systemui.R.id.notification_title);
        TextView textView = (TextView) findViewById(com.android.systemui.R.id.notification_text);
        this.mTextView = textView;
        this.mTransformationHelper.setCustomTransformation(new FadeOutAndDownWithTitleTransformation(textView), 2);
        this.mTransformationHelper.addTransformedView(this.mTitleView, 1);
        this.mTransformationHelper.addTransformedView(this.mTextView, 2);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void setVisible(boolean z) {
        setVisibility(z ? 0 : 4);
        this.mTransformationHelper.setVisible(z);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformFrom(TransformableView transformableView) {
        this.mTransformationHelper.transformFrom(transformableView);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformTo(TransformableView transformableView, Runnable runnable) {
        this.mTransformationHelper.transformTo(transformableView, runnable);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformFrom(float f, TransformableView transformableView) {
        this.mTransformationHelper.transformFrom(f, transformableView);
    }

    @Override // com.android.systemui.statusbar.TransformableView
    public final void transformTo(float f, TransformableView transformableView) {
        this.mTransformationHelper.transformTo(f, transformableView);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public HybridNotificationView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTransformationHelper = new ViewTransformationHelper();
        this.mPrimaryTextColor = 1;
        this.mSecondaryTextColor = 1;
    }

    public void setNotificationFaded(boolean z) {
    }
}
