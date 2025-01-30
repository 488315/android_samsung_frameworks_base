package com.android.systemui.keyguard.domain.interactor;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class TransitionInteractor {
    public final String name;

    public /* synthetic */ TransitionInteractor(String str, DefaultConstructorMarker defaultConstructorMarker) {
        this(str);
    }

    public abstract void start();

    private TransitionInteractor(String str) {
        this.name = str;
    }
}
