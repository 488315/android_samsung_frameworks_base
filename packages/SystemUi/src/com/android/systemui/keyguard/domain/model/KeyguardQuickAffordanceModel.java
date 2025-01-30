package com.android.systemui.keyguard.domain.model;

import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardQuickAffordanceModel {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Hidden extends KeyguardQuickAffordanceModel {
        public static final Hidden INSTANCE = new Hidden();

        private Hidden() {
            super(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Visible extends KeyguardQuickAffordanceModel {
        public final ActivationState activationState;
        public final String configKey;
        public final Icon icon;

        public Visible(String str, Icon icon, ActivationState activationState) {
            super(null);
            this.configKey = str;
            this.icon = icon;
            this.activationState = activationState;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Visible)) {
                return false;
            }
            Visible visible = (Visible) obj;
            return Intrinsics.areEqual(this.configKey, visible.configKey) && Intrinsics.areEqual(this.icon, visible.icon) && Intrinsics.areEqual(this.activationState, visible.activationState);
        }

        public final int hashCode() {
            return this.activationState.hashCode() + ((this.icon.hashCode() + (this.configKey.hashCode() * 31)) * 31);
        }

        public final String toString() {
            return "Visible(configKey=" + this.configKey + ", icon=" + this.icon + ", activationState=" + this.activationState + ")";
        }
    }

    private KeyguardQuickAffordanceModel() {
    }

    public /* synthetic */ KeyguardQuickAffordanceModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
