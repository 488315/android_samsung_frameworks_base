package com.android.systemui.complication.dagger;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextClock;
import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import com.android.systemui.complication.dagger.DreamClockTimeComplicationComponent;
import dagger.internal.Provider;

/* loaded from: classes2.dex */
public final class DreamClockTimeComplicationComponent_DreamClockTimeComplicationModule_Companion_ProvideComplicationViewFactory implements Provider {
    public final Provider layoutInflaterProvider;

    public DreamClockTimeComplicationComponent_DreamClockTimeComplicationModule_Companion_ProvideComplicationViewFactory(Provider provider) {
        this.layoutInflaterProvider = provider;
    }

    public static TextClock provideComplicationView(LayoutInflater layoutInflater) {
        DreamClockTimeComplicationComponent.DreamClockTimeComplicationModule.Companion.getClass();
        TextClock textClock = (TextClock) Preconditions.checkNotNull((TextClock) layoutInflater.inflate(R.layout.dream_overlay_complication_clock_time, (ViewGroup) null, false), "R.layout.dream_overlay_complication_clock_time did not properly inflate", new Object[0]);
        textClock.setFontVariationSettings("'wght' 400");
        return textClock;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideComplicationView((LayoutInflater) this.layoutInflaterProvider.get());
    }
}
