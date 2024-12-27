package com.android.server.wm;

public abstract class DisplayAreaPolicy {
    public final RootDisplayArea mRoot;

    public final class DefaultProvider {}

    public DisplayAreaPolicy(RootDisplayArea rootDisplayArea) {
        this.mRoot = rootDisplayArea;
    }
}
