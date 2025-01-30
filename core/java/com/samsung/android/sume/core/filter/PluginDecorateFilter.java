package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.functional.ExecuteDelegator;
import com.samsung.android.sume.core.plugin.NNPlugin;
import com.samsung.android.sume.core.plugin.PluginFixture;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public abstract class PluginDecorateFilter<T extends PluginFixture<?>> extends DecorateFilter {
  protected T plugin;

  PluginDecorateFilter(T plugin, MediaFilter filter) {
    super(filter);
    final ExecuteDelegator delegator;
    this.plugin = plugin;
    if ((plugin instanceof NNPlugin)
        && (delegator = ((NNPlugin) plugin).getExecuteDelegator()) != null) {
      MediaFilter enclosedFilter = filter;
      enclosedFilter =
          filter instanceof DecorateFilter
              ? ((DecorateFilter) filter).getEnclosedFilter()
              : enclosedFilter;
      if (enclosedFilter instanceof NNFWFilter) {
        ((NNFWFilter) enclosedFilter).setExecuteDelegator(delegator);
      } else if (enclosedFilter instanceof MediaFilterPlaceHolder) {
        ((MediaFilterPlaceHolder) enclosedFilter)
            .setMediaFilterUpdater(
                new Consumer() { // from class:
                                 // com.samsung.android.sume.core.filter.PluginDecorateFilter$$ExternalSyntheticLambda0
                  @Override // java.util.function.Consumer
                  public final void accept(Object obj) {
                    PluginDecorateFilter.lambda$new$0(ExecuteDelegator.this, (MediaFilter) obj);
                  }
                });
      }
    }
  }

  static /* synthetic */ void lambda$new$0(ExecuteDelegator delegator, MediaFilter mediaFilter) {
    if (mediaFilter instanceof NNFWFilter) {
      ((NNFWFilter) mediaFilter).setExecuteDelegator(delegator);
    }
  }
}
