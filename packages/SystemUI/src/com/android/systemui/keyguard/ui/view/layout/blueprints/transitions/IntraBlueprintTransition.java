package com.android.systemui.keyguard.ui.view.layout.blueprints.transitions;

import android.transition.TransitionSet;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.keyguard.ui.view.layout.sections.transitions.ClockSizeTransition;
import com.android.systemui.keyguard.ui.view.layout.sections.transitions.DefaultClockSteppingTransition;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.plugins.clocks.ClockController;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IntraBlueprintTransition extends TransitionSet {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Type {
        public static final /* synthetic */ Type[] $VALUES;
        public static final Type ClockSize;
        public static final Type DefaultClockStepping;
        public static final Type DefaultTransition;
        public static final Type Init;
        public static final Type NoTransition;
        public static final Type SmartspaceVisibility;
        private final boolean animateNotifChanges;
        private final int priority;

        static {
            Type type = new Type("ClockSize", 0, 100, true);
            ClockSize = type;
            Type type2 = new Type("ClockCenter", 1, 99, false);
            Type type3 = new Type("DefaultClockStepping", 2, 98, false);
            DefaultClockStepping = type3;
            Type type4 = new Type("SmartspaceVisibility", 3, 3, true);
            SmartspaceVisibility = type4;
            Type type5 = new Type("DefaultTransition", 4, 2, false);
            DefaultTransition = type5;
            Type type6 = new Type("NoTransition", 5, 1, false);
            NoTransition = type6;
            Type type7 = new Type("Init", 6, 0, false);
            Init = type7;
            Type[] typeArr = {type, type2, type3, type4, type5, type6, type7};
            $VALUES = typeArr;
            EnumEntriesKt.enumEntries(typeArr);
        }

        private Type(String str, int i, int i2, boolean z) {
            this.priority = i2;
            this.animateNotifChanges = z;
        }

        public static Type valueOf(String str) {
            return (Type) Enum.valueOf(Type.class, str);
        }

        public static Type[] values() {
            return (Type[]) $VALUES.clone();
        }

        public final boolean getAnimateNotifChanges() {
            return this.animateNotifChanges;
        }

        public final int getPriority() {
            return this.priority;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Type.values().length];
            try {
                iArr[Type.Init.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Type.NoTransition.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Type.DefaultClockStepping.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public IntraBlueprintTransition(Config config, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer) {
        setOrdering(0);
        int i = WhenMappings.$EnumSwitchMapping$0[config.type.ordinal()];
        if (i == 1 || i == 2) {
            return;
        }
        if (i != 3) {
            addTransition(new ClockSizeTransition(config, keyguardClockViewModel, logBuffer));
        } else {
            ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
            addTransition(clockController != null ? new DefaultClockSteppingTransition(clockController) : null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Config {
        public static final Companion Companion = new Companion(null);
        public static final Config DEFAULT = new Config(Type.NoTransition, false, false, null, 14, null);
        public final boolean checkPriority;
        public final List rebuildSections;
        public final boolean terminatePrevious;
        public final Type type;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public Config(Type type, boolean z, boolean z2, List<? extends KeyguardSection> list) {
            this.type = type;
            this.checkPriority = z;
            this.terminatePrevious = z2;
            this.rebuildSections = list;
        }

        public static Config copy$default(Config config, Type type) {
            boolean z = config.checkPriority;
            boolean z2 = config.terminatePrevious;
            List list = config.rebuildSections;
            config.getClass();
            return new Config(type, z, z2, list);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Config)) {
                return false;
            }
            Config config = (Config) obj;
            return this.type == config.type && this.checkPriority == config.checkPriority && this.terminatePrevious == config.terminatePrevious && Intrinsics.areEqual(this.rebuildSections, config.rebuildSections);
        }

        public final int hashCode() {
            return this.rebuildSections.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(this.type.hashCode() * 31, 31, this.checkPriority), 31, this.terminatePrevious);
        }

        public final String toString() {
            return "Config(type=" + this.type + ", checkPriority=" + this.checkPriority + ", terminatePrevious=" + this.terminatePrevious + ", rebuildSections=" + this.rebuildSections + ")";
        }

        public Config(Type type, boolean z, boolean z2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(type, (i & 2) != 0 ? true : z, (i & 4) != 0 ? true : z2, (i & 8) != 0 ? EmptyList.INSTANCE : list);
        }
    }
}
