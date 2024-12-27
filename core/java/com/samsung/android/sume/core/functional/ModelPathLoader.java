package com.samsung.android.sume.core.functional;

import android.util.Pair;
import java.util.regex.Pattern;

@FunctionalInterface
/* loaded from: classes6.dex */
public interface ModelPathLoader {
    Pair<String, Pattern> load(String str);
}
