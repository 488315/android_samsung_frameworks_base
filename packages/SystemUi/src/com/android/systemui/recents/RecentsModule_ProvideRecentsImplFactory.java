package com.android.systemui.recents;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.dagger.ContextComponentResolver;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RecentsModule_ProvideRecentsImplFactory implements Provider {
    public final Provider componentHelperProvider;
    public final Provider contextProvider;

    public RecentsModule_ProvideRecentsImplFactory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.componentHelperProvider = provider2;
    }

    public static RecentsImplementation provideRecentsImpl(Context context, ContextComponentHelper contextComponentHelper) {
        String string = context.getString(R.string.config_recentsComponent);
        if (string == null || string.length() == 0) {
            throw new RuntimeException("No recents component configured", null);
        }
        RecentsImplementation recentsImplementation = (RecentsImplementation) ContextComponentResolver.resolve(string, ((ContextComponentResolver) contextComponentHelper).mRecentsCreators);
        if (recentsImplementation == null) {
            try {
                try {
                    recentsImplementation = (RecentsImplementation) context.getClassLoader().loadClass(string).newInstance();
                } catch (Throwable th) {
                    throw new RuntimeException("Error creating recents component: ".concat(string), th);
                }
            } catch (Throwable th2) {
                throw new RuntimeException("Error loading recents component: ".concat(string), th2);
            }
        }
        Preconditions.checkNotNullFromProvides(recentsImplementation);
        return recentsImplementation;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideRecentsImpl((Context) this.contextProvider.get(), (ContextComponentHelper) this.componentHelperProvider.get());
    }
}
