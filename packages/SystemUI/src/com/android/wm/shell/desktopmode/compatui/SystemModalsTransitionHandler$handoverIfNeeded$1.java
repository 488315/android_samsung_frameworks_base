package com.android.wm.shell.desktopmode.compatui;

import java.util.function.Function;

/* loaded from: classes3.dex */
public final class SystemModalsTransitionHandler$handoverIfNeeded$1 implements Function {
    public static final SystemModalsTransitionHandler$handoverIfNeeded$1 INSTANCE = new SystemModalsTransitionHandler$handoverIfNeeded$1();

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((String) obj).equals("QuickstepLaunchHomeFromRecents"));
    }
}
