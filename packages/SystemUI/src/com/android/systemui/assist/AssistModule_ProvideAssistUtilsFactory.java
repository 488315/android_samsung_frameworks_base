package com.android.systemui.assist;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import dagger.internal.Provider;

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
