package com.android.systemui.qs.dagger;

import android.view.View;

public interface QSFragmentComponent extends QSComponent {

    public interface Factory {
        QSFragmentComponent create(View view);
    }
}
