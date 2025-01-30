package com.facebook.rebound;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface SpringListener {
    void onSpringActivate(Spring spring);

    void onSpringAtRest(Spring spring);

    void onSpringEndStateChange(Spring spring);

    void onSpringUpdate(Spring spring);
}
