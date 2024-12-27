package com.android.systemui.blur;

import android.content.Context;
import android.view.View;

public final class SecCoverBlurController {
    public final QSColorCurve mColorCurve;
    public final View mRootView;

    public SecCoverBlurController(Context context, View view) {
        this.mColorCurve = new QSColorCurve(context);
        this.mRootView = view;
    }
}
