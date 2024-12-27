package com.android.server;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class FactoryResetter {
    public static final AtomicBoolean sFactoryResetting = new AtomicBoolean(false);
}
