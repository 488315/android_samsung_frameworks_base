package com.android.systemui.recents;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.dagger.ContextComponentHelper;
import com.android.systemui.dagger.ContextComponentResolver;
import dagger.internal.Preconditions;
import dagger.internal.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class RecentsModule_ProvideRecentsImplFactory implements Provider {
    public final javax.inject.Provider componentHelperProvider;
    public final javax.inject.Provider contextProvider;

    public RecentsModule_ProvideRecentsImplFactory(javax.inject.Provider provider, javax.inject.Provider provider2) {
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
