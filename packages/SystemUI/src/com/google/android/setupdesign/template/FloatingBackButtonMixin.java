package com.google.android.setupdesign.template;

import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.Button;
import com.android.systemui.R;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;

/* loaded from: classes4.dex */
public class FloatingBackButtonMixin implements Mixin {
    static final String KEY_BACK_BUTTON_ON_CLICK_COUNT = "BackButton_onClickCount";
    public final TemplateLayout templateLayout;
    boolean tryInflatingBackButton = false;
    public int clickCount = 0;

    public FloatingBackButtonMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
    }

    public Button getBackButton() {
        TemplateLayout templateLayout = this.templateLayout;
        Button button = (Button) templateLayout.findManagedViewById(R.id.sud_floating_back_button);
        if (button == null) {
            Log.w("FloatingBackButtonMixin", "Can't find the back button.");
        }
        if (button != null) {
            return button;
        }
        if (!this.tryInflatingBackButton) {
            this.tryInflatingBackButton = true;
            ViewStub viewStub = (ViewStub) templateLayout.findManagedViewById(R.id.sud_floating_back_button_stub);
            if (viewStub != null) {
                try {
                    inflateButton(viewStub);
                } catch (InflateException e) {
                    Log.w("FloatingBackButtonMixin", "Incorrect theme:" + e.toString());
                    return null;
                }
            }
        }
        Button button2 = (Button) templateLayout.findManagedViewById(R.id.sud_floating_back_button);
        if (button2 == null) {
            Log.w("FloatingBackButtonMixin", "Can't find the back button.");
        }
        return button2;
    }

    public void inflateButton(ViewStub viewStub) {
        viewStub.setLayoutInflater(LayoutInflater.from(this.templateLayout.getContext()));
        viewStub.inflate();
    }
}
