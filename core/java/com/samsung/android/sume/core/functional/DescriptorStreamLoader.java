package com.samsung.android.sume.core.functional;

import android.content.Context;

import java.io.InputStream;

@FunctionalInterface
public interface DescriptorStreamLoader {
    InputStream load(Context context);
}
