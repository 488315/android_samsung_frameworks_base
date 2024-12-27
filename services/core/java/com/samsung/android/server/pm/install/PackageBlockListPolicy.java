package com.samsung.android.server.pm.install;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class PackageBlockListPolicy {
    public static final AtomicBoolean sIsRduDevice = new AtomicBoolean(false);
    public static HashSet sLduBlocklist;
}
