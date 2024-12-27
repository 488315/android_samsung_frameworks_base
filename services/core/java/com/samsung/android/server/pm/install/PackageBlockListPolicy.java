package com.samsung.android.server.pm.install;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PackageBlockListPolicy {
    public static final AtomicBoolean sIsRduDevice = new AtomicBoolean(false);
    public static HashSet sLduBlocklist;
}
