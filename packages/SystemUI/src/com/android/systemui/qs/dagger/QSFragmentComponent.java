package com.android.systemui.qs.dagger;

import android.view.View;

/* loaded from: classes2.dex */
public interface QSFragmentComponent extends QSComponent {

    public interface Factory {
        QSFragmentComponent create(View view);
    }
}
