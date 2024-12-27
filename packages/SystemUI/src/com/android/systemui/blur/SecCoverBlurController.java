package com.android.systemui.blur;

import android.content.Context;
import android.view.View;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecCoverBlurController {
    public final QSColorCurve mColorCurve;
    public final View mRootView;

    public SecCoverBlurController(Context context, View view) {
        this.mColorCurve = new QSColorCurve(context);
        this.mRootView = view;
    }
}
