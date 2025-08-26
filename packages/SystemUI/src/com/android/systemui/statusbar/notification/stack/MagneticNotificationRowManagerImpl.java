package com.android.systemui.statusbar.notification.stack;

import android.os.VibrationAttributes;
import androidx.core.view.ViewGroupKt$children$1;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationRowLogger;
import com.android.systemui.statusbar.notification.row.NotificationRowLogger$$ExternalSyntheticLambda0;
import com.google.android.msdl.data.model.MSDLToken;
import com.google.android.msdl.domain.MSDLPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.ArrayDeque;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final class MagneticNotificationRowManagerImpl implements MagneticNotificationRowManager {
    public static final List MAGNETIC_TRANSLATION_MULTIPLIERS;
    public final DirectionEstimator detachDirectionEstimator;
    public final NotificationRowLogger logger;
    public float magneticAttachThreshold;
    public final MSDLPlayer msdlPlayer;
    public final NotificationRoundnessManager notificationRoundnessManager;
    public final NotificationTargetsHelper notificationTargetsHelper;
    public final float swipedRowMultiplier;
    public float translationOffset;
    public State currentState = State.IDLE;
    public List currentMagneticListeners = EmptyList.INSTANCE;
    public float magneticDetachThreshold = Float.POSITIVE_INFINITY;
    public final SpringForce detachForce = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(800.0f, 0.95f);
    public final SpringForce snapForce = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(550.0f, 0.6f);
    public final SpringForce attachForce = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(800.0f, 0.95f);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class DirectionEstimator {
        public final ArrayDeque translationBuffer = new ArrayDeque();
        public boolean acceptTranslations = true;

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

        public final void recordTranslation(float f) {
            if (this.acceptTranslations) {
                ArrayDeque arrayDeque = this.translationBuffer;
                if (arrayDeque.size == 10) {
                    arrayDeque.removeFirst();
                }
                arrayDeque.addLast(Float.valueOf(f));
            }
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class State {
        public static final /* synthetic */ State[] $VALUES;
        public static final State DETACHED;
        public static final State IDLE;
        public static final State PULLING;
        public static final State TARGETS_SET;

        static {
            State state = new State("IDLE", 0);
            IDLE = state;
            State state2 = new State("TARGETS_SET", 1);
            TARGETS_SET = state2;
            State state3 = new State("PULLING", 2);
            PULLING = state3;
            State state4 = new State("DETACHED", 3);
            DETACHED = state4;
            State[] stateArr = {state, state2, state3, state4};
            $VALUES = stateArr;
            EnumEntriesKt.enumEntries(stateArr);
        }

        private State(String str, int i) {
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[State.values().length];
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[State.TARGETS_SET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[State.PULLING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[State.DETACHED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
        Float fValueOf = Float.valueOf(0.04f);
        Float fValueOf2 = Float.valueOf(0.12f);
        MAGNETIC_TRANSLATION_MULTIPLIERS = Arrays.asList(fValueOf, fValueOf2, Float.valueOf(0.5f), fValueOf2, fValueOf);
        new VibrationAttributes.Builder().setUsage(18).setFlags(8).build();
    }

    public MagneticNotificationRowManagerImpl(MSDLPlayer mSDLPlayer, NotificationTargetsHelper notificationTargetsHelper, NotificationRoundnessManager notificationRoundnessManager, NotificationRowLogger notificationRowLogger) {
        this.msdlPlayer = mSDLPlayer;
        this.notificationTargetsHelper = notificationTargetsHelper;
        this.notificationRoundnessManager = notificationRoundnessManager;
        this.logger = notificationRowLogger;
        List list = MAGNETIC_TRANSLATION_MULTIPLIERS;
        this.swipedRowMultiplier = ((Number) list.get(list.size() / 2)).floatValue();
        this.detachDirectionEstimator = new DirectionEstimator();
    }

    public static MagneticRowListener swipedListener(List list) {
        return (MagneticRowListener) CollectionsKt___CollectionsKt.getOrNull(list.size() / 2, list);
    }

    @Override // com.android.systemui.statusbar.notification.stack.MagneticNotificationRowManager
    public final void onDensityChange(float f) {
        this.magneticDetachThreshold = 56 * f;
        this.magneticAttachThreshold = f * 40;
    }

    @Override // com.android.systemui.statusbar.notification.stack.MagneticNotificationRowManager
    public final void onMagneticInteractionEnd(ExpandableNotificationRow expandableNotificationRow, Float f) {
        Object next;
        this.translationOffset = 0.0f;
        DirectionEstimator directionEstimator = this.detachDirectionEstimator;
        directionEstimator.translationBuffer.clear();
        directionEstimator.acceptTranslations = true;
        if (!Intrinsics.areEqual(expandableNotificationRow.mMagneticRowListener, swipedListener(this.currentMagneticListeners))) {
            Iterator it = this.currentMagneticListeners.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                } else {
                    next = it.next();
                    if (Intrinsics.areEqual((MagneticRowListener) next, expandableNotificationRow.mMagneticRowListener)) {
                        break;
                    }
                }
            }
            MagneticRowListener magneticRowListener = (MagneticRowListener) next;
            if (magneticRowListener != null) {
                ((ExpandableView.AnonymousClass2) magneticRowListener).cancelMagneticAnimations();
                return;
            }
            return;
        }
        int i = WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()];
        if (i == 2) {
            this.currentState = State.IDLE;
            return;
        }
        if (i == 3) {
            snapNeighborsBack(f);
            this.currentState = State.IDLE;
        } else {
            if (i != 4) {
                return;
            }
            MagneticRowListener magneticRowListenerSwipedListener = swipedListener(this.currentMagneticListeners);
            if (magneticRowListenerSwipedListener != null) {
                ((ExpandableView.AnonymousClass2) magneticRowListenerSwipedListener).cancelMagneticAnimations();
            }
            this.currentState = State.IDLE;
        }
    }

    public final void pullTargets(float f, boolean z) {
        int i = 0;
        for (Object obj : this.currentMagneticListeners) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            MagneticRowListener magneticRowListener = (MagneticRowListener) obj;
            if (magneticRowListener != null) {
                ((ExpandableView.AnonymousClass2) magneticRowListener).setMagneticTranslation((z && ExpandableView.this.canExpandableViewBeDismissed()) ? ((Number) MAGNETIC_TRANSLATION_MULTIPLIERS.get(i)).floatValue() * f : ((Number) MAGNETIC_TRANSLATION_MULTIPLIERS.get(i)).floatValue() * f * 0.65f, true);
            }
            i = i2;
        }
    }

    @Override // com.android.systemui.statusbar.notification.stack.MagneticNotificationRowManager
    public final void reset() {
        this.translationOffset = 0.0f;
        DirectionEstimator directionEstimator = this.detachDirectionEstimator;
        directionEstimator.translationBuffer.clear();
        directionEstimator.acceptTranslations = true;
        for (MagneticRowListener magneticRowListener : this.currentMagneticListeners) {
            if (magneticRowListener != null) {
                ((ExpandableView.AnonymousClass2) magneticRowListener).cancelMagneticAnimations();
            }
            if (magneticRowListener != null) {
                ExpandableView.this.cancelTranslationAnimations();
            }
        }
        this.currentState = State.IDLE;
        this.currentMagneticListeners = EmptyList.INSTANCE;
        this.notificationRoundnessManager.setViewsAffectedBySwipe(null, null, null);
    }

    @Override // com.android.systemui.statusbar.notification.stack.MagneticNotificationRowManager
    public final void resetRoundness() {
        this.notificationRoundnessManager.setViewsAffectedBySwipe(null, null, null);
    }

    @Override // com.android.systemui.statusbar.notification.stack.MagneticNotificationRowManager
    public final void setMagneticAndRoundableTargets(ExpandableNotificationRow expandableNotificationRow, NotificationStackScrollLayout notificationStackScrollLayout, NotificationSectionsManager notificationSectionsManager) {
        State state = this.currentState;
        if (state != State.IDLE) {
            String str = expandableNotificationRow.mLoggingKey;
            NotificationRowLogger notificationRowLogger = this.logger;
            notificationRowLogger.getClass();
            LogLevel logLevel = LogLevel.ERROR;
            NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(11);
            LogBuffer logBuffer = notificationRowLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = str;
            logMessageImpl.str2 = state.name();
            logBuffer.commit(logMessageObtain);
            return;
        }
        this.translationOffset = 0.0f;
        DirectionEstimator directionEstimator = this.detachDirectionEstimator;
        directionEstimator.translationBuffer.clear();
        boolean z = true;
        directionEstimator.acceptTranslations = true;
        NotificationRoundnessManager notificationRoundnessManager = this.notificationRoundnessManager;
        notificationRoundnessManager.setViewsAffectedBySwipe(null, null, null);
        this.notificationTargetsHelper.getClass();
        RoundableTargets roundableTargetsFindRoundableTargets = NotificationTargetsHelper.findRoundableTargets(expandableNotificationRow, notificationStackScrollLayout, notificationSectionsManager);
        notificationRoundnessManager.setViewsAffectedBySwipe(roundableTargetsFindRoundableTargets.before, roundableTargetsFindRoundableTargets.swiped, roundableTargetsFindRoundableTargets.after);
        int size = MAGNETIC_TRANSLATION_MULTIPLIERS.size();
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow.mNotificationParent;
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2 != null ? expandableNotificationRow2.mChildrenContainer : null;
        List list = SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(new ViewGroupKt$children$1(notificationStackScrollLayout), new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper$findMagneticTargets$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return Boolean.valueOf(obj instanceof ExpandableView);
            }
        }), new NotificationTargetsHelper$$ExternalSyntheticLambda0(0)));
        int i = 0;
        if (expandableNotificationRow2 != null && notificationChildrenContainer != null) {
            List list2 = notificationChildrenContainer.mAttachedChildren;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list2;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj = arrayList2.get(i2);
                i2++;
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) obj;
                expandableNotificationRow3.getClass();
                if (expandableNotificationRow3.getVisibility() == 0) {
                    arrayList.add(obj);
                }
            }
            list = arrayList;
        }
        ArrayList arrayList3 = new ArrayList(size);
        for (int i3 = 0; i3 < size; i3++) {
            arrayList3.add(null);
        }
        int i4 = size / 2;
        arrayList3.set(i4, expandableNotificationRow.mMagneticRowListener);
        int iIndexOf = list.indexOf(expandableNotificationRow);
        int size3 = (arrayList3.size() / 2) - 1;
        int size4 = (arrayList3.size() / 2) + 1;
        if (1 <= i4) {
            int i5 = size3;
            int i6 = size4;
            int i7 = 1;
            boolean z2 = true;
            while (true) {
                if (z) {
                    ExpandableView expandableView = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(iIndexOf - i7, list);
                    if (expandableView == null || (!NotificationTargetsHelper.isValidMagneticBoundary(expandableView) && notificationSectionsManager.beginsSection(expandableNotificationRow, expandableView))) {
                        expandableView = null;
                    }
                    if (expandableView instanceof ExpandableNotificationRow) {
                        arrayList3.set(i5, ((ExpandableNotificationRow) expandableView).mMagneticRowListener);
                        i5--;
                    } else {
                        if (NotificationTargetsHelper.isValidMagneticBoundary(expandableView)) {
                            arrayList3.set(i5, expandableView != null ? expandableView.mMagneticRowListener : null);
                        }
                        z = false;
                    }
                }
                if (z2) {
                    ExpandableView expandableView2 = (ExpandableView) CollectionsKt___CollectionsKt.getOrNull(iIndexOf + i7, list);
                    if (expandableView2 == null || (!NotificationTargetsHelper.isValidMagneticBoundary(expandableView2) && notificationSectionsManager.beginsSection(expandableView2, expandableNotificationRow))) {
                        expandableView2 = null;
                    }
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                        arrayList3.set(i6, ((ExpandableNotificationRow) expandableView2).mMagneticRowListener);
                        i6++;
                    } else {
                        if (NotificationTargetsHelper.isValidMagneticBoundary(expandableView2)) {
                            arrayList3.set(i6, expandableView2 != null ? expandableView2.mMagneticRowListener : null);
                        }
                        z2 = false;
                    }
                }
                if (i7 == i4) {
                    break;
                } else {
                    i7++;
                }
            }
        }
        int size5 = arrayList3.size();
        while (i < size5) {
            Object obj2 = arrayList3.get(i);
            i++;
            MagneticRowListener magneticRowListener = (MagneticRowListener) obj2;
            if (this.currentMagneticListeners.contains(magneticRowListener)) {
                if (magneticRowListener != null) {
                    ((ExpandableView.AnonymousClass2) magneticRowListener).cancelMagneticAnimations();
                }
                if (Intrinsics.areEqual(magneticRowListener, swipedListener(this.currentMagneticListeners)) && magneticRowListener != null) {
                    ExpandableView.this.cancelTranslationAnimations();
                }
            }
        }
        this.currentMagneticListeners = arrayList3;
        this.currentState = State.TARGETS_SET;
    }

    @Override // com.android.systemui.statusbar.notification.stack.MagneticNotificationRowManager
    public final boolean setMagneticRowTranslation(ExpandableNotificationRow expandableNotificationRow, float f) {
        if (!Intrinsics.areEqual(expandableNotificationRow.mMagneticRowListener, swipedListener(this.currentMagneticListeners))) {
            return false;
        }
        MagneticRowListener magneticRowListenerSwipedListener = swipedListener(this.currentMagneticListeners);
        boolean zCanExpandableViewBeDismissed = magneticRowListenerSwipedListener != null ? ExpandableView.this.canExpandableViewBeDismissed() : false;
        float f2 = f - this.translationOffset;
        int i = WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()];
        if (i == 1) {
            State state = this.currentState;
            String str = expandableNotificationRow.mLoggingKey;
            NotificationRowLogger notificationRowLogger = this.logger;
            notificationRowLogger.getClass();
            LogLevel logLevel = LogLevel.ERROR;
            NotificationRowLogger$$ExternalSyntheticLambda0 notificationRowLogger$$ExternalSyntheticLambda0 = new NotificationRowLogger$$ExternalSyntheticLambda0(13);
            LogBuffer logBuffer = notificationRowLogger.buffer;
            LogMessage logMessageObtain = logBuffer.obtain("NotifRow", logLevel, notificationRowLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.str1 = str;
            logMessageImpl.str2 = state.name();
            logBuffer.commit(logMessageObtain);
            return false;
        }
        DirectionEstimator directionEstimator = this.detachDirectionEstimator;
        if (i == 2) {
            directionEstimator.recordTranslation(f2);
            pullTargets(f2, zCanExpandableViewBeDismissed);
            this.currentState = State.PULLING;
            return true;
        }
        float f3 = this.swipedRowMultiplier;
        NotificationRoundnessManager notificationRoundnessManager = this.notificationRoundnessManager;
        MSDLPlayer mSDLPlayer = this.msdlPlayer;
        if (i != 3) {
            if (i != 4) {
                throw new NoWhenBranchMatchedException();
            }
            directionEstimator.recordTranslation(f2);
            if (Math.abs(f2) > this.magneticAttachThreshold) {
                MagneticRowListener magneticRowListenerSwipedListener2 = swipedListener(this.currentMagneticListeners);
                if (magneticRowListenerSwipedListener2 != null) {
                    ((ExpandableView.AnonymousClass2) magneticRowListenerSwipedListener2).setMagneticTranslation(f2, false);
                }
                return true;
            }
            this.translationOffset += f2;
            directionEstimator.translationBuffer.clear();
            directionEstimator.acceptTranslations = true;
            float fCoerceIn = RangesKt___RangesKt.coerceIn(Math.abs(f3 * 0.0f) / this.magneticDetachThreshold, 0.0f, 0.8f);
            Roundable roundable = notificationRoundnessManager.mViewBeforeSwipedView;
            SourceType$Companion$from$1 sourceType$Companion$from$1 = NotificationRoundnessManager.DISMISS_ANIMATION;
            if (roundable != null) {
                roundable.requestBottomRoundness(fCoerceIn, sourceType$Companion$from$1, true);
            }
            ExpandableNotificationRow expandableNotificationRow2 = notificationRoundnessManager.mSwipedView;
            if (expandableNotificationRow2 != null) {
                expandableNotificationRow2.requestRoundness(fCoerceIn, fCoerceIn, sourceType$Companion$from$1, true);
            }
            Roundable roundable2 = notificationRoundnessManager.mViewAfterSwipedView;
            if (roundable2 != null) {
                roundable2.requestTopRoundness(fCoerceIn, sourceType$Companion$from$1, true);
            }
            MagneticRowListener magneticRowListenerSwipedListener3 = swipedListener(this.currentMagneticListeners);
            if (magneticRowListenerSwipedListener3 != null) {
                ExpandableView.AnonymousClass2 anonymousClass2 = (ExpandableView.AnonymousClass2) magneticRowListenerSwipedListener3;
                anonymousClass2.cancelMagneticAnimations();
                ExpandableView expandableView = ExpandableView.this;
                expandableView.cancelTranslationAnimations();
                SpringAnimation springAnimation = expandableView.mMagneticAnimator;
                springAnimation.mSpring = this.attachForce;
                springAnimation.mVelocity = 0.0f;
                springAnimation.animateToFinalPosition(0.0f);
                MSDLToken mSDLToken = MSDLToken.SWIPE_THRESHOLD_INDICATOR;
                MSDLPlayer.Companion companion = MSDLPlayer.Companion;
                mSDLPlayer.playToken(mSDLToken, null);
            }
            this.currentState = State.PULLING;
            return true;
        }
        directionEstimator.recordTranslation(f2);
        float fCoerceIn2 = RangesKt___RangesKt.coerceIn(Math.abs(f3 * f2) / this.magneticDetachThreshold, 0.0f, 0.8f);
        Roundable roundable3 = notificationRoundnessManager.mViewBeforeSwipedView;
        SourceType$Companion$from$1 sourceType$Companion$from$12 = NotificationRoundnessManager.DISMISS_ANIMATION;
        if (roundable3 != null) {
            roundable3.requestBottomRoundness(fCoerceIn2, sourceType$Companion$from$12, false);
        }
        ExpandableNotificationRow expandableNotificationRow3 = notificationRoundnessManager.mSwipedView;
        if (expandableNotificationRow3 != null) {
            expandableNotificationRow3.requestRoundness(fCoerceIn2, fCoerceIn2, sourceType$Companion$from$12, false);
        }
        Roundable roundable4 = notificationRoundnessManager.mViewAfterSwipedView;
        if (roundable4 != null) {
            roundable4.requestTopRoundness(fCoerceIn2, sourceType$Companion$from$12, false);
        }
        if (!zCanExpandableViewBeDismissed) {
            pullTargets(f2, false);
            return true;
        }
        if (Math.abs(f2) < this.magneticDetachThreshold) {
            pullTargets(f2, true);
            return true;
        }
        directionEstimator.acceptTranslations = false;
        ArrayDeque arrayDeque = directionEstimator.translationBuffer;
        if (!arrayDeque.isEmpty()) {
            Iterator it = arrayDeque.iterator();
            float fFloatValue = 0.0f;
            while (it.hasNext()) {
                fFloatValue += ((Number) it.next()).floatValue();
            }
            Math.signum(fFloatValue / arrayDeque.size);
        }
        snapNeighborsBack(null);
        MagneticRowListener magneticRowListenerSwipedListener4 = swipedListener(this.currentMagneticListeners);
        if (magneticRowListenerSwipedListener4 != null) {
            ExpandableView.AnonymousClass2 anonymousClass22 = (ExpandableView.AnonymousClass2) magneticRowListenerSwipedListener4;
            anonymousClass22.cancelMagneticAnimations();
            ExpandableView expandableView2 = ExpandableView.this;
            expandableView2.cancelTranslationAnimations();
            SpringAnimation springAnimation2 = expandableView2.mMagneticAnimator;
            springAnimation2.mSpring = this.detachForce;
            springAnimation2.mVelocity = 0.0f;
            springAnimation2.animateToFinalPosition(f2);
            Roundable roundable5 = notificationRoundnessManager.mViewBeforeSwipedView;
            SourceType$Companion$from$1 sourceType$Companion$from$13 = NotificationRoundnessManager.DISMISS_ANIMATION;
            if (roundable5 != null) {
                roundable5.requestBottomRoundness(1.0f, sourceType$Companion$from$13, true);
            }
            ExpandableNotificationRow expandableNotificationRow4 = notificationRoundnessManager.mSwipedView;
            if (expandableNotificationRow4 != null) {
                expandableNotificationRow4.requestRoundness(1.0f, 1.0f, sourceType$Companion$from$13, true);
            }
            Roundable roundable6 = notificationRoundnessManager.mViewAfterSwipedView;
            if (roundable6 != null) {
                roundable6.requestTopRoundness(1.0f, sourceType$Companion$from$13, true);
            }
            MSDLToken mSDLToken2 = MSDLToken.SWIPE_THRESHOLD_INDICATOR;
            MSDLPlayer.Companion companion2 = MSDLPlayer.Companion;
            mSDLPlayer.playToken(mSDLToken2, null);
        }
        this.currentState = State.DETACHED;
        return true;
    }

    public final void snapNeighborsBack(Float f) {
        int i = 0;
        for (Object obj : this.currentMagneticListeners) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            MagneticRowListener magneticRowListener = (MagneticRowListener) obj;
            if (magneticRowListener != null && i != this.currentMagneticListeners.size() / 2) {
                Float fValueOf = f != null ? Float.valueOf(f.floatValue() * ((Number) MAGNETIC_TRANSLATION_MULTIPLIERS.get(i)).floatValue()) : null;
                ExpandableView.AnonymousClass2 anonymousClass2 = (ExpandableView.AnonymousClass2) magneticRowListener;
                anonymousClass2.cancelMagneticAnimations();
                float fFloatValue = fValueOf != null ? fValueOf.floatValue() : 0.0f;
                ExpandableView expandableView = ExpandableView.this;
                expandableView.cancelTranslationAnimations();
                SpringAnimation springAnimation = expandableView.mMagneticAnimator;
                springAnimation.mSpring = this.snapForce;
                springAnimation.mVelocity = fFloatValue;
                springAnimation.animateToFinalPosition(0.0f);
            }
            i = i2;
        }
    }
}
