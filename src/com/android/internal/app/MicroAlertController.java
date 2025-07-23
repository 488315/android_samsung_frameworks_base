package com.android.internal.app;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class MicroAlertController extends AlertController {
    public MicroAlertController(Context context, DialogInterface dialogInterface, Window window) {
        super(context, dialogInterface, window);
    }

    @Override // com.android.internal.app.AlertController
    protected void setupContent(ViewGroup viewGroup) {
        this.mScrollView = (ScrollView) this.mWindow.findViewById(R.id.scrollView);
        this.mMessageView = (TextView) viewGroup.findViewById(16908299);
        if (this.mMessageView == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mMessageView.lambda$setTextAsync$0(this.mMessage);
            return;
        }
        this.mMessageView.setVisibility(8);
        viewGroup.removeView(this.mMessageView);
        if (this.mListView != null) {
            View findViewById = this.mScrollView.findViewById(R.id.topPanel);
            ((ViewGroup) findViewById.getParent()).removeView(findViewById);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(findViewById.getLayoutParams());
            layoutParams.gravity = 48;
            findViewById.setLayoutParams(layoutParams);
            View findViewById2 = this.mScrollView.findViewById(R.id.buttonPanel);
            ((ViewGroup) findViewById2.getParent()).removeView(findViewById2);
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(findViewById2.getLayoutParams());
            layoutParams2.gravity = 80;
            findViewById2.setLayoutParams(layoutParams2);
            ViewGroup viewGroup2 = (ViewGroup) this.mScrollView.getParent();
            viewGroup2.removeViewAt(viewGroup2.indexOfChild(this.mScrollView));
            viewGroup2.addView(this.mListView, new ViewGroup.LayoutParams(-1, -1));
            viewGroup2.addView(findViewById);
            viewGroup2.addView(findViewById2);
            return;
        }
        viewGroup.setVisibility(8);
    }

    @Override // com.android.internal.app.AlertController
    protected void setupTitle(ViewGroup viewGroup) {
        super.setupTitle(viewGroup);
        if (viewGroup.getVisibility() == 8) {
            viewGroup.setVisibility(4);
        }
    }

    @Override // com.android.internal.app.AlertController
    protected void setupButtons(ViewGroup viewGroup) {
        super.setupButtons(viewGroup);
        if (viewGroup.getVisibility() == 8) {
            viewGroup.setVisibility(4);
        }
    }
}
