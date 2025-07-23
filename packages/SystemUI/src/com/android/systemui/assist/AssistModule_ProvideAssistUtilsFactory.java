package com.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AssistModule_ProvideAssistUtilsFactory implements Provider {
    public final Provider contextProvider;

    public AssistModule_ProvideAssistUtilsFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static AssistUtils provideAssistUtils(Context context) {
        return new AssistUtils(context);
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return new AssistUtils((Context) this.contextProvider.get());
    }
}
