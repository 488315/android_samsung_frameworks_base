package com.android.systemui.shade.domain.interactor;

import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public interface ShadeExpandedStateInteractor {

    public abstract class ShadeElement {
        public /* synthetic */ ShadeElement(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public abstract Unit collapse();

        public abstract Unit expand();

        private ShadeElement() {
        }
    }
}
