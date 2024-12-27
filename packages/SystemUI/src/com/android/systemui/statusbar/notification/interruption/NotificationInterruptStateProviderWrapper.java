package com.android.systemui.statusbar.notification.interruption;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import java.util.ArrayList;
import kotlin.Unit;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationInterruptStateProviderWrapper implements VisualInterruptionDecisionProvider {
    public final NotificationInterruptStateProvider wrapped;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DecisionImpl implements VisualInterruptionDecisionProvider.Decision {
        public static final /* synthetic */ DecisionImpl[] $VALUES;
        public static final Companion Companion;
        public static final DecisionImpl SHOULD_INTERRUPT;
        public static final DecisionImpl SHOULD_NOT_INTERRUPT;
        private final String logReason = "unknown";
        private final boolean shouldInterrupt;

        /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        static {
            DecisionImpl decisionImpl = new DecisionImpl("SHOULD_INTERRUPT", 0, true);
            SHOULD_INTERRUPT = decisionImpl;
            DecisionImpl decisionImpl2 = new DecisionImpl("SHOULD_NOT_INTERRUPT", 1, false);
            SHOULD_NOT_INTERRUPT = decisionImpl2;
            DecisionImpl[] decisionImplArr = {decisionImpl, decisionImpl2};
            $VALUES = decisionImplArr;
            EnumEntriesKt.enumEntries(decisionImplArr);
            Companion = new Companion(null);
        }

        private DecisionImpl(String str, int i, boolean z) {
            this.shouldInterrupt = z;
        }

        public static DecisionImpl valueOf(String str) {
            return (DecisionImpl) Enum.valueOf(DecisionImpl.class, str);
        }

        public static DecisionImpl[] values() {
            return (DecisionImpl[]) $VALUES.clone();
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public final String getLogReason() {
            return this.logReason;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public final boolean getShouldInterrupt() {
            return this.shouldInterrupt;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FullScreenIntentDecisionImpl implements VisualInterruptionDecisionProvider.Decision {
        public final String logReason;
        public final NotificationInterruptStateProvider.FullScreenIntentDecision originalDecision;
        public final NotificationEntry originalEntry;
        public final boolean shouldInterrupt;
        public final boolean wouldInterruptWithoutDnd;

        public FullScreenIntentDecisionImpl(NotificationEntry notificationEntry, NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision) {
            this.originalEntry = notificationEntry;
            this.originalDecision = fullScreenIntentDecision;
            this.shouldInterrupt = fullScreenIntentDecision.shouldLaunch;
            this.wouldInterruptWithoutDnd = fullScreenIntentDecision == NotificationInterruptStateProvider.FullScreenIntentDecision.NO_FSI_SUPPRESSED_ONLY_BY_DND;
            this.logReason = fullScreenIntentDecision.name();
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public final String getLogReason() {
            return this.logReason;
        }

        @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider.Decision
        public final boolean getShouldInterrupt() {
            return this.shouldInterrupt;
        }

        public final boolean getWouldInterruptWithoutDnd() {
            return this.wouldInterruptWithoutDnd;
        }
    }

    public NotificationInterruptStateProviderWrapper(NotificationInterruptStateProvider notificationInterruptStateProvider) {
        this.wrapped = notificationInterruptStateProvider;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = VisualInterruptionRefactor.$r8$clinit;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void addLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor) {
        ((ArrayList) ((NotificationInterruptStateProviderImpl) this.wrapped).mSuppressors).add(notificationInterruptSuppressor);
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void logFullScreenIntentDecision(FullScreenIntentDecisionImpl fullScreenIntentDecisionImpl) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#logFullScreenIntentDecision");
        }
        try {
            ((NotificationInterruptStateProviderImpl) this.wrapped).logFullScreenIntentDecision(fullScreenIntentDecisionImpl.originalEntry, fullScreenIntentDecisionImpl.originalDecision);
            Unit unit = Unit.INSTANCE;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final VisualInterruptionDecisionProvider.Decision makeAndLogBubbleDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#makeAndLogBubbleDecision");
        }
        try {
            boolean shouldBubbleUp = ((NotificationInterruptStateProviderImpl) this.wrapped).shouldBubbleUp(notificationEntry);
            DecisionImpl.Companion.getClass();
            return shouldBubbleUp ? DecisionImpl.SHOULD_INTERRUPT : DecisionImpl.SHOULD_NOT_INTERRUPT;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final VisualInterruptionDecisionProvider.Decision makeAndLogHeadsUpDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#makeAndLogHeadsUpDecision");
        }
        try {
            boolean checkHeadsUp = ((NotificationInterruptStateProviderImpl) this.wrapped).checkHeadsUp(notificationEntry, true);
            DecisionImpl.Companion.getClass();
            return checkHeadsUp ? DecisionImpl.SHOULD_INTERRUPT : DecisionImpl.SHOULD_NOT_INTERRUPT;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final FullScreenIntentDecisionImpl makeUnloggedFullScreenIntentDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#makeUnloggedFullScreenIntentDecision");
        }
        try {
            NotificationInterruptStateProvider.FullScreenIntentDecision fullScreenIntentDecision = ((NotificationInterruptStateProviderImpl) this.wrapped).getFullScreenIntentDecision(notificationEntry);
            Intrinsics.checkNotNull(fullScreenIntentDecision);
            return new FullScreenIntentDecisionImpl(notificationEntry, fullScreenIntentDecision);
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final VisualInterruptionDecisionProvider.Decision makeUnloggedHeadsUpDecision(NotificationEntry notificationEntry) {
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("NotificationInterruptStateProviderWrapper#makeUnloggedHeadsUpDecision");
        }
        try {
            boolean checkHeadsUp = ((NotificationInterruptStateProviderImpl) this.wrapped).checkHeadsUp(notificationEntry, false);
            DecisionImpl.Companion.getClass();
            return checkHeadsUp ? DecisionImpl.SHOULD_INTERRUPT : DecisionImpl.SHOULD_NOT_INTERRUPT;
        } finally {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void removeCondition(VisualInterruptionCondition visualInterruptionCondition) {
        RefactorFlagUtils.INSTANCE.getClass();
        RefactorFlagUtils.assertOnEngBuild("This method is only implemented in VisualInterruptionDecisionProviderImpl, and so should only be called when FLAG_VISUAL_INTERRUPTIONS_REFACTOR is enabled.");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void removeFilter(VisualInterruptionFilter visualInterruptionFilter) {
        RefactorFlagUtils.INSTANCE.getClass();
        RefactorFlagUtils.assertOnEngBuild("This method is only implemented in VisualInterruptionDecisionProviderImpl, and so should only be called when FLAG_VISUAL_INTERRUPTIONS_REFACTOR is enabled.");
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider
    public final void removeLegacySuppressor(NotificationInterruptSuppressor notificationInterruptSuppressor) {
        ((ArrayList) ((NotificationInterruptStateProviderImpl) this.wrapped).mSuppressors).remove(notificationInterruptSuppressor);
    }
}
