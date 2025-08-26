package com.android.systemui.education.ui.view;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.education.ui.viewmodel.ContextualEduToastViewModel;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* loaded from: classes2.dex */
public final class ContextualEduDialog extends Dialog {
    public final AccessibilityManager accessibilityManager;
    public final ContextualEduToastViewModel model;

    public ContextualEduDialog(Context context, ContextualEduToastViewModel contextualEduToastViewModel, AccessibilityManager accessibilityManager) {
        super(context);
        this.model = contextualEduToastViewModel;
        this.accessibilityManager = accessibilityManager;
    }

    @Override // android.app.Dialog
    public final void onCreate(Bundle bundle) {
        Window window = getWindow();
        if (window != null) {
            window.requestFeature(1);
            window.setType(2008);
            window.addFlags(40);
            window.clearFlags(2);
            window.setBackgroundDrawableResource(R.color.transparent);
        }
        setCanceledOnTouchOutside(false);
        Window window2 = getWindow();
        if (window2 != null) {
            window2.setGravity(81);
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.width = -2;
            layoutParams.height = -2;
            layoutParams.copyFrom(window2.getAttributes());
            layoutParams.y = window2.getContext().getResources().getDimensionPixelSize(com.android.systemui.R.dimen.contextual_edu_dialog_bottom_margin);
            window2.setAttributes(layoutParams);
        }
        Window window3 = getWindow();
        if (window3 != null) {
            window3.setTitle(getContext().getString(com.android.systemui.R.string.contextual_education_dialog_title));
        }
        setContentView(com.android.systemui.R.layout.contextual_edu_dialog);
        TextView textView = (TextView) findViewById(com.android.systemui.R.id.edu_message);
        if (textView != null) {
            textView.setText(this.model.message);
        }
        ImageView imageView = (ImageView) findViewById(com.android.systemui.R.id.edu_icon);
        if (imageView != null) {
            imageView.setImageResource(this.model.icon);
        }
        if (this.accessibilityManager.isEnabled()) {
            AccessibilityManager accessibilityManager = this.accessibilityManager;
            AccessibilityEvent accessibilityEvent = new AccessibilityEvent(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            accessibilityEvent.getText().add(this.model.message);
            accessibilityManager.sendAccessibilityEvent(accessibilityEvent);
        }
        super.onCreate(bundle);
    }
}
