package com.android.systemui.controls.controller;

import android.service.controls.IControlsProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ServiceWrapper {
    public final IControlsProvider service;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ServiceWrapper(IControlsProvider iControlsProvider) {
        this.service = iControlsProvider;
    }
}
