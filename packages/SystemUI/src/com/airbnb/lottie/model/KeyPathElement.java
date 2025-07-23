package com.airbnb.lottie.model;

import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface KeyPathElement {
    void addValueCallback(LottieValueCallback lottieValueCallback, Object obj);

    void resolveKeyPath(KeyPath keyPath, int i, List list, KeyPath keyPath2);
}
