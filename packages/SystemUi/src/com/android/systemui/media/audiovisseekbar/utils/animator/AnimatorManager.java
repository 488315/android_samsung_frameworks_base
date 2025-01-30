package com.android.systemui.media.audiovisseekbar.utils.animator;

import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AnimatorManager {
    public static final AnimatorManager INSTANCE = new AnimatorManager();
    public static final ConcurrentHashMap animatorMap = new ConcurrentHashMap();

    private AnimatorManager() {
    }
}
