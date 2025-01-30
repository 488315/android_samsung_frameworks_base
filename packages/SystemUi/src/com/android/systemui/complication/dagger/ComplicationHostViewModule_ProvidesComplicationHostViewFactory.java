package com.android.systemui.complication.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplicationHostViewModule_ProvidesComplicationHostViewFactory implements Provider {
    public final Provider layoutInflaterProvider;

    public ComplicationHostViewModule_ProvidesComplicationHostViewFactory(Provider provider) {
        this.layoutInflaterProvider = provider;
    }

    public static ConstraintLayout providesComplicationHostView(LayoutInflater layoutInflater) {
        ConstraintLayout constraintLayout = (ConstraintLayout) Preconditions.checkNotNull((ConstraintLayout) layoutInflater.inflate(R.layout.dream_overlay_complications_layer, (ViewGroup) null), "R.layout.dream_overlay_complications_layer did not properly inflated");
        dagger.internal.Preconditions.checkNotNullFromProvides(constraintLayout);
        return constraintLayout;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return providesComplicationHostView((LayoutInflater) this.layoutInflaterProvider.get());
    }
}
