package com.samsung.android.sume.core.functional;

import android.content.Context;

@FunctionalInterface
/* loaded from: classes4.dex */
public interface ModelLoader<T> {
  T load(Context context, String str);
}
