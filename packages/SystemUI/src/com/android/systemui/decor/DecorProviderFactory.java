package com.android.systemui.decor;

import java.util.List;

public abstract class DecorProviderFactory {
    public abstract boolean getHasProviders();

    public abstract List getProviders();
}
