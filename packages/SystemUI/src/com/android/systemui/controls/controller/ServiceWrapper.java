package com.android.systemui.controls.controller;

import android.service.controls.IControlsProvider;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ServiceWrapper {
    public final IControlsProvider service;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public ServiceWrapper(IControlsProvider iControlsProvider) {
        this.service = iControlsProvider;
    }
}
