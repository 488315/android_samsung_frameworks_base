package com.android.systemui.qs.tiles.base.shared.model;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.Switch;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import java.util.Collections;
import java.util.Set;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSTileState {
    public static final Companion Companion = new Companion(null);
    public final ActivationState activationState;
    public final CharSequence contentDescription;
    public final EnabledState enabledState;
    public final String expandedAccessibilityClassName;
    public final Icon icon;
    public final CharSequence label;
    public final CharSequence secondaryLabel;
    public final SideViewIcon sideViewIcon;
    public final CharSequence stateDescription;
    public final Set supportedActions;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ActivationState {
        public static final /* synthetic */ ActivationState[] $VALUES;
        public static final ActivationState ACTIVE;
        public static final Companion Companion;
        public static final ActivationState INACTIVE;
        public static final ActivationState UNAVAILABLE;
        private final int legacyState;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        static {
            ActivationState activationState = new ActivationState("UNAVAILABLE", 0, 0);
            UNAVAILABLE = activationState;
            ActivationState activationState2 = new ActivationState("ACTIVE", 1, 2);
            ACTIVE = activationState2;
            ActivationState activationState3 = new ActivationState("INACTIVE", 2, 1);
            INACTIVE = activationState3;
            ActivationState[] activationStateArr = {activationState, activationState2, activationState3};
            $VALUES = activationStateArr;
            EnumEntriesKt.enumEntries(activationStateArr);
            Companion = new Companion(null);
        }

        private ActivationState(String str, int i, int i2) {
            this.legacyState = i2;
        }

        public static ActivationState valueOf(String str) {
            return (ActivationState) Enum.valueOf(ActivationState.class, str);
        }

        public static ActivationState[] values() {
            return (ActivationState[]) $VALUES.clone();
        }

        public final int getLegacyState() {
            return this.legacyState;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public CharSequence contentDescription;
        public Icon icon;
        public CharSequence label;
        public CharSequence secondaryLabel;
        public CharSequence stateDescription;
        public ActivationState activationState = ActivationState.INACTIVE;
        public Set supportedActions = Collections.singleton(UserAction.CLICK);
        public SideViewIcon sideViewIcon = SideViewIcon.None.INSTANCE;
        public final EnabledState enabledState = EnabledState.ENABLED;
        public ClassReference expandedAccessibilityClass = Reflection.getOrCreateKotlinClass(Switch.class);

        public Builder(Icon icon, CharSequence charSequence) {
            this.icon = icon;
            this.label = charSequence;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static QSTileState build(Resources resources, Resources.Theme theme, QSTileUIConfig qSTileUIConfig, Function1 function1) {
            Drawable drawable = resources.getDrawable(qSTileUIConfig.getIconRes(), theme);
            drawable.getClass();
            return build(new Icon.Loaded(drawable, null, Integer.valueOf(qSTileUIConfig.getIconRes())), resources.getString(qSTileUIConfig.getLabelRes()), function1);
        }

        private Companion() {
        }

        public static QSTileState build(Icon icon, CharSequence charSequence, Function1 function1) {
            Builder builder = new Builder(icon, charSequence);
            function1.mo779invoke(builder);
            Icon icon2 = builder.icon;
            CharSequence charSequence2 = builder.label;
            ActivationState activationState = builder.activationState;
            CharSequence charSequence3 = builder.secondaryLabel;
            Set set = builder.supportedActions;
            CharSequence charSequence4 = builder.contentDescription;
            CharSequence charSequence5 = builder.stateDescription;
            SideViewIcon sideViewIcon = builder.sideViewIcon;
            ClassReference classReference = builder.expandedAccessibilityClass;
            return new QSTileState(icon2, charSequence2, activationState, charSequence3, set, charSequence4, charSequence5, sideViewIcon, builder.enabledState, classReference != null ? classReference.getQualifiedName() : null);
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EnabledState {
        public static final /* synthetic */ EnabledState[] $VALUES;
        public static final EnabledState DISABLED;
        public static final EnabledState ENABLED;

        static {
            EnabledState enabledState = new EnabledState("ENABLED", 0);
            ENABLED = enabledState;
            EnabledState enabledState2 = new EnabledState("DISABLED", 1);
            DISABLED = enabledState2;
            EnabledState[] enabledStateArr = {enabledState, enabledState2};
            $VALUES = enabledStateArr;
            EnumEntriesKt.enumEntries(enabledStateArr);
        }

        private EnabledState(String str, int i) {
        }

        public static EnabledState valueOf(String str) {
            return (EnabledState) Enum.valueOf(EnabledState.class, str);
        }

        public static EnabledState[] values() {
            return (EnabledState[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SideViewIcon {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Chevron implements SideViewIcon {
            public static final Chevron INSTANCE = new Chevron();

            private Chevron() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Chevron);
            }

            public final int hashCode() {
                return -923439722;
            }

            public final String toString() {
                return "Chevron";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Custom implements SideViewIcon {
            public final Icon icon;

            public Custom(Icon icon) {
                this.icon = icon;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Custom) && Intrinsics.areEqual(this.icon, ((Custom) obj).icon);
            }

            public final int hashCode() {
                return this.icon.hashCode();
            }

            public final String toString() {
                return "Custom(icon=" + this.icon + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class None implements SideViewIcon {
            public static final None INSTANCE = new None();

            private None() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof None);
            }

            public final int hashCode() {
                return -1521842755;
            }

            public final String toString() {
                return "None";
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class UserAction {
        public static final /* synthetic */ UserAction[] $VALUES;
        public static final UserAction CLICK;
        public static final UserAction LONG_CLICK;
        public static final UserAction TOGGLE_CLICK;

        static {
            UserAction userAction = new UserAction("CLICK", 0);
            CLICK = userAction;
            UserAction userAction2 = new UserAction("TOGGLE_CLICK", 1);
            TOGGLE_CLICK = userAction2;
            UserAction userAction3 = new UserAction("LONG_CLICK", 2);
            LONG_CLICK = userAction3;
            UserAction[] userActionArr = {userAction, userAction2, userAction3};
            $VALUES = userActionArr;
            EnumEntriesKt.enumEntries(userActionArr);
        }

        private UserAction(String str, int i) {
        }

        public static UserAction valueOf(String str) {
            return (UserAction) Enum.valueOf(UserAction.class, str);
        }

        public static UserAction[] values() {
            return (UserAction[]) $VALUES.clone();
        }
    }

    public QSTileState(Icon icon, CharSequence charSequence, ActivationState activationState, CharSequence charSequence2, Set<? extends UserAction> set, CharSequence charSequence3, CharSequence charSequence4, SideViewIcon sideViewIcon, EnabledState enabledState, String str) {
        this.icon = icon;
        this.label = charSequence;
        this.activationState = activationState;
        this.secondaryLabel = charSequence2;
        this.supportedActions = set;
        this.contentDescription = charSequence3;
        this.stateDescription = charSequence4;
        this.sideViewIcon = sideViewIcon;
        this.enabledState = enabledState;
        this.expandedAccessibilityClassName = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof QSTileState)) {
            return false;
        }
        QSTileState qSTileState = (QSTileState) obj;
        return Intrinsics.areEqual(this.icon, qSTileState.icon) && Intrinsics.areEqual(this.label, qSTileState.label) && this.activationState == qSTileState.activationState && Intrinsics.areEqual(this.secondaryLabel, qSTileState.secondaryLabel) && Intrinsics.areEqual(this.supportedActions, qSTileState.supportedActions) && Intrinsics.areEqual(this.contentDescription, qSTileState.contentDescription) && Intrinsics.areEqual(this.stateDescription, qSTileState.stateDescription) && Intrinsics.areEqual(this.sideViewIcon, qSTileState.sideViewIcon) && this.enabledState == qSTileState.enabledState && Intrinsics.areEqual(this.expandedAccessibilityClassName, qSTileState.expandedAccessibilityClassName);
    }

    public final int hashCode() {
        Icon icon = this.icon;
        int hashCode = (this.activationState.hashCode() + ControlInfo$$ExternalSyntheticOutline0.m((icon == null ? 0 : icon.hashCode()) * 31, 31, this.label)) * 31;
        CharSequence charSequence = this.secondaryLabel;
        int hashCode2 = (this.supportedActions.hashCode() + ((hashCode + (charSequence == null ? 0 : charSequence.hashCode())) * 31)) * 31;
        CharSequence charSequence2 = this.contentDescription;
        int hashCode3 = (hashCode2 + (charSequence2 == null ? 0 : charSequence2.hashCode())) * 31;
        CharSequence charSequence3 = this.stateDescription;
        int hashCode4 = (this.enabledState.hashCode() + ((this.sideViewIcon.hashCode() + ((hashCode3 + (charSequence3 == null ? 0 : charSequence3.hashCode())) * 31)) * 31)) * 31;
        String str = this.expandedAccessibilityClassName;
        return hashCode4 + (str != null ? str.hashCode() : 0);
    }

    public final String toString() {
        CharSequence charSequence = this.label;
        CharSequence charSequence2 = this.secondaryLabel;
        return "QSTileState(icon=" + this.icon + ", label=" + ((Object) charSequence) + ", activationState=" + this.activationState + ", secondaryLabel=" + ((Object) charSequence2) + ", supportedActions=" + this.supportedActions + ", contentDescription=" + ((Object) this.contentDescription) + ", stateDescription=" + ((Object) this.stateDescription) + ", sideViewIcon=" + this.sideViewIcon + ", enabledState=" + this.enabledState + ", expandedAccessibilityClassName=" + this.expandedAccessibilityClassName + ")";
    }
}
