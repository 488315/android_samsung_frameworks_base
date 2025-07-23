package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.functional.ExecuteDelegator;
import com.samsung.android.sume.core.plugin.NNPlugin;
import com.samsung.android.sume.core.plugin.PluginFixture;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public abstract class PluginDecorateFilter<T extends PluginFixture<?>> extends DecorateFilter {
    protected T plugin;

    PluginDecorateFilter(T t, MediaFilter mediaFilter) {
        super(mediaFilter);
        final ExecuteDelegator executeDelegator;
        this.plugin = t;
        if (!(t instanceof NNPlugin) || (executeDelegator = ((NNPlugin) t).getExecuteDelegator()) == null) {
            return;
        }
        mediaFilter = mediaFilter instanceof DecorateFilter ? ((DecorateFilter) mediaFilter).getEnclosedFilter() : mediaFilter;
        if (mediaFilter instanceof NNFWFilter) {
            ((NNFWFilter) mediaFilter).setExecuteDelegator(executeDelegator);
        } else if (mediaFilter instanceof MediaFilterPlaceHolder) {
            ((MediaFilterPlaceHolder) mediaFilter).setMediaFilterUpdater(new Consumer() { // from class: com.samsung.android.sume.core.filter.PluginDecorateFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PluginDecorateFilter.lambda$new$0(ExecuteDelegator.this, (MediaFilter) obj);
                }
            });
        }
    }

    static /* synthetic */ void lambda$new$0(ExecuteDelegator executeDelegator, MediaFilter mediaFilter) {
        if (mediaFilter instanceof NNFWFilter) {
            ((NNFWFilter) mediaFilter).setExecuteDelegator(executeDelegator);
        }
    }
}
