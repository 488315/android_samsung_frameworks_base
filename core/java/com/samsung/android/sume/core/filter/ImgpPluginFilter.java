package com.samsung.android.sume.core.filter;

import com.samsung.android.sume.core.plugin.ImgpPlugin;

public class ImgpPluginFilter extends PluginDecorateFilter<ImgpPlugin> {
    ImgpPluginFilter(ImgpPlugin plugin, MediaFilter filter) {
        super(plugin, filter);
    }
}
