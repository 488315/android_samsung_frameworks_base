package com.airbnb.lottie.model;

import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface KeyPathElement {
    void addValueCallback(LottieValueCallback lottieValueCallback, Object obj);

    void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2);
}
