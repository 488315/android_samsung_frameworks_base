package com.android.systemui.statusbar.chips.ui.model;

import android.view.View;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.systemui.animation.ComposableControllerFactory;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.chips.ui.viewmodel.TimeSource;
import com.android.systemui.statusbar.core.StatusBarConnectedDisplays;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class OngoingActivityChipModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Active extends OngoingActivityChipModel {
        public final ClickBehavior clickBehavior;
        public final ColorsModel colors;
        public final ChipIcon icon;
        public final InstanceId instanceId;
        public final boolean isHidden;
        public final String key;
        public final View.OnClickListener onClickListenerLegacy;
        public final TransitionManager transitionManager;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Countdown extends Active {
            public final ColorsModel colors;
            public final InstanceId instanceId;
            public final boolean isHidden;
            public final boolean isImportantForPrivacy;
            public final String key;
            public final String logName;
            public final long secondsUntilStarted;
            public final boolean shouldAnimate;
            public final TransitionManager transitionManager;

            public /* synthetic */ Countdown(String str, boolean z, ColorsModel colorsModel, long j, TransitionManager transitionManager, boolean z2, boolean z3, InstanceId instanceId, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(str, (i & 2) != 0 ? false : z, colorsModel, j, (i & 16) != 0 ? null : transitionManager, (i & 32) != 0 ? false : z2, (i & 64) != 0 ? true : z3, (i & 128) != 0 ? null : instanceId);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Countdown)) {
                    return false;
                }
                Countdown countdown = (Countdown) obj;
                return Intrinsics.areEqual(this.key, countdown.key) && this.isImportantForPrivacy == countdown.isImportantForPrivacy && Intrinsics.areEqual(this.colors, countdown.colors) && this.secondsUntilStarted == countdown.secondsUntilStarted && Intrinsics.areEqual(this.transitionManager, countdown.transitionManager) && this.isHidden == countdown.isHidden && this.shouldAnimate == countdown.shouldAnimate && Intrinsics.areEqual(this.instanceId, countdown.instanceId);
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final InstanceId getInstanceId() {
                return this.instanceId;
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel
            public final String getLogName() {
                return this.logName;
            }

            public final int hashCode() {
                int m = MoveResult$$ExternalSyntheticOutline0.m((this.colors.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.isImportantForPrivacy)) * 31, 31, this.secondsUntilStarted);
                TransitionManager transitionManager = this.transitionManager;
                int m2 = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((m + (transitionManager == null ? 0 : transitionManager.hashCode())) * 31, 31, this.isHidden), 31, this.shouldAnimate);
                InstanceId instanceId = this.instanceId;
                return m2 + (instanceId != null ? instanceId.hashCode() : 0);
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final boolean isHidden() {
                return this.isHidden;
            }

            public final String toString() {
                return "Countdown(key=" + this.key + ", isImportantForPrivacy=" + this.isImportantForPrivacy + ", colors=" + this.colors + ", secondsUntilStarted=" + this.secondsUntilStarted + ", transitionManager=" + this.transitionManager + ", isHidden=" + this.isHidden + ", shouldAnimate=" + this.shouldAnimate + ", instanceId=" + this.instanceId + ")";
            }

            public Countdown(String str, boolean z, ColorsModel colorsModel, long j, TransitionManager transitionManager, boolean z2, boolean z3, InstanceId instanceId) {
                super(str, z, null, colorsModel, null, ClickBehavior.None.INSTANCE, transitionManager, z2, z3, instanceId, null);
                this.key = str;
                this.isImportantForPrivacy = z;
                this.colors = colorsModel;
                this.secondsUntilStarted = j;
                this.transitionManager = transitionManager;
                this.isHidden = z2;
                this.shouldAnimate = z3;
                this.instanceId = instanceId;
                this.logName = "Active.Countdown";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class IconOnly extends Active {
            public final ClickBehavior clickBehavior;
            public final ColorsModel colors;
            public final ChipIcon icon;
            public final InstanceId instanceId;
            public final boolean isHidden;
            public final boolean isImportantForPrivacy;
            public final String key;
            public final String logName;
            public final View.OnClickListener onClickListenerLegacy;
            public final boolean shouldAnimate;
            public final TransitionManager transitionManager;

            public /* synthetic */ IconOnly(String str, boolean z, ChipIcon chipIcon, ColorsModel colorsModel, View.OnClickListener onClickListener, ClickBehavior clickBehavior, TransitionManager transitionManager, boolean z2, boolean z3, InstanceId instanceId, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(str, (i & 2) != 0 ? false : z, chipIcon, colorsModel, onClickListener, clickBehavior, (i & 64) != 0 ? null : transitionManager, (i & 128) != 0 ? false : z2, (i & 256) != 0 ? true : z3, (i & 512) != 0 ? null : instanceId);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof IconOnly)) {
                    return false;
                }
                IconOnly iconOnly = (IconOnly) obj;
                return Intrinsics.areEqual(this.key, iconOnly.key) && this.isImportantForPrivacy == iconOnly.isImportantForPrivacy && Intrinsics.areEqual(this.icon, iconOnly.icon) && Intrinsics.areEqual(this.colors, iconOnly.colors) && Intrinsics.areEqual(this.onClickListenerLegacy, iconOnly.onClickListenerLegacy) && Intrinsics.areEqual(this.clickBehavior, iconOnly.clickBehavior) && Intrinsics.areEqual(this.transitionManager, iconOnly.transitionManager) && this.isHidden == iconOnly.isHidden && this.shouldAnimate == iconOnly.shouldAnimate && Intrinsics.areEqual(this.instanceId, iconOnly.instanceId);
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final InstanceId getInstanceId() {
                return this.instanceId;
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel
            public final String getLogName() {
                return this.logName;
            }

            public final int hashCode() {
                int hashCode = (this.colors.hashCode() + ((this.icon.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.isImportantForPrivacy)) * 31)) * 31;
                View.OnClickListener onClickListener = this.onClickListenerLegacy;
                int hashCode2 = (this.clickBehavior.hashCode() + ((hashCode + (onClickListener == null ? 0 : onClickListener.hashCode())) * 31)) * 31;
                TransitionManager transitionManager = this.transitionManager;
                int m = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (transitionManager == null ? 0 : transitionManager.hashCode())) * 31, 31, this.isHidden), 31, this.shouldAnimate);
                InstanceId instanceId = this.instanceId;
                return m + (instanceId != null ? instanceId.hashCode() : 0);
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final boolean isHidden() {
                return this.isHidden;
            }

            public final String toString() {
                return "IconOnly(key=" + this.key + ", isImportantForPrivacy=" + this.isImportantForPrivacy + ", icon=" + this.icon + ", colors=" + this.colors + ", onClickListenerLegacy=" + this.onClickListenerLegacy + ", clickBehavior=" + this.clickBehavior + ", transitionManager=" + this.transitionManager + ", isHidden=" + this.isHidden + ", shouldAnimate=" + this.shouldAnimate + ", instanceId=" + this.instanceId + ")";
            }

            public IconOnly(String str, boolean z, ChipIcon chipIcon, ColorsModel colorsModel, View.OnClickListener onClickListener, ClickBehavior clickBehavior, TransitionManager transitionManager, boolean z2, boolean z3, InstanceId instanceId) {
                super(str, z, chipIcon, colorsModel, onClickListener, clickBehavior, transitionManager, z2, z3, instanceId, null);
                this.key = str;
                this.isImportantForPrivacy = z;
                this.icon = chipIcon;
                this.colors = colorsModel;
                this.onClickListenerLegacy = onClickListener;
                this.clickBehavior = clickBehavior;
                this.transitionManager = transitionManager;
                this.isHidden = z2;
                this.shouldAnimate = z3;
                this.instanceId = instanceId;
                this.logName = "Active.Icon";
            }
        }

        public /* synthetic */ Active(String str, boolean z, ChipIcon chipIcon, ColorsModel colorsModel, View.OnClickListener onClickListener, ClickBehavior clickBehavior, TransitionManager transitionManager, boolean z2, boolean z3, InstanceId instanceId, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, z, chipIcon, colorsModel, onClickListener, clickBehavior, transitionManager, z2, z3, instanceId);
        }

        public InstanceId getInstanceId() {
            return this.instanceId;
        }

        public String getKey() {
            return this.key;
        }

        public boolean isHidden() {
            return this.isHidden;
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Timer extends Active {
            public final ClickBehavior clickBehavior;
            public final ColorsModel colors;
            public final ChipIcon icon;
            public final InstanceId instanceId;
            public final boolean isEventInFuture;
            public final boolean isHidden;
            public final boolean isImportantForPrivacy;
            public final String key;
            public final String logName;
            public final View.OnClickListener onClickListenerLegacy;
            public final boolean shouldAnimate;
            public final long startTimeMs;
            public final TimeSource timeSource;
            public final TransitionManager transitionManager;

            /*  JADX ERROR: NullPointerException in pass: InitCodeVariables
                java.lang.NullPointerException
                */
            public /* synthetic */ Timer(java.lang.String r19, boolean r20, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.ChipIcon r21, com.android.systemui.statusbar.chips.ui.model.ColorsModel r22, long r23, com.android.systemui.statusbar.chips.ui.viewmodel.TimeSource r25, boolean r26, android.view.View.OnClickListener r27, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.ClickBehavior r28, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.TransitionManager r29, boolean r30, boolean r31, com.android.internal.logging.InstanceId r32, int r33, kotlin.jvm.internal.DefaultConstructorMarker r34) {
                /*
                    r18 = this;
                    r0 = r33
                    r1 = r0 & 2
                    r2 = 0
                    if (r1 == 0) goto L9
                    r5 = r2
                    goto Lb
                L9:
                    r5 = r20
                Lb:
                    r1 = r0 & 32
                    if (r1 == 0) goto L13
                    com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer$1 r1 = new com.android.systemui.statusbar.chips.ui.viewmodel.TimeSource() { // from class: com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer.1
                        static {
                            /*
                                com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer$1 r0 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer$1
                                r0.<init>()
                                
                                // error: 0x0005: SPUT (r0 I:com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer$1) com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer.1.INSTANCE com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$Active$Timer$1
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer.AnonymousClass1.<clinit>():void");
                        }

                        {
                            /*
                                r0 = this;
                                r0.<init>()
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer.AnonymousClass1.<init>():void");
                        }
                    }
                    r10 = r1
                    goto L15
                L13:
                    r10 = r25
                L15:
                    r1 = r0 & 64
                    if (r1 == 0) goto L1b
                    r11 = r2
                    goto L1d
                L1b:
                    r11 = r26
                L1d:
                    r1 = r0 & 512(0x200, float:7.17E-43)
                    r3 = 0
                    if (r1 == 0) goto L24
                    r14 = r3
                    goto L26
                L24:
                    r14 = r29
                L26:
                    r1 = r0 & 1024(0x400, float:1.435E-42)
                    if (r1 == 0) goto L2c
                    r15 = r2
                    goto L2e
                L2c:
                    r15 = r30
                L2e:
                    r1 = r0 & 2048(0x800, float:2.87E-42)
                    if (r1 == 0) goto L36
                    r1 = 1
                    r16 = r1
                    goto L38
                L36:
                    r16 = r31
                L38:
                    r0 = r0 & 4096(0x1000, float:5.74E-42)
                    if (r0 == 0) goto L4d
                    r17 = r3
                    r4 = r19
                    r6 = r21
                    r7 = r22
                    r8 = r23
                    r12 = r27
                    r13 = r28
                    r3 = r18
                    goto L5d
                L4d:
                    r17 = r32
                    r3 = r18
                    r4 = r19
                    r6 = r21
                    r7 = r22
                    r8 = r23
                    r12 = r27
                    r13 = r28
                L5d:
                    r3.<init>(r4, r5, r6, r7, r8, r10, r11, r12, r13, r14, r15, r16, r17)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active.Timer.<init>(java.lang.String, boolean, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ChipIcon, com.android.systemui.statusbar.chips.ui.model.ColorsModel, long, com.android.systemui.statusbar.chips.ui.viewmodel.TimeSource, boolean, android.view.View$OnClickListener, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$ClickBehavior, com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager, boolean, boolean, com.android.internal.logging.InstanceId, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Timer)) {
                    return false;
                }
                Timer timer = (Timer) obj;
                return Intrinsics.areEqual(this.key, timer.key) && this.isImportantForPrivacy == timer.isImportantForPrivacy && Intrinsics.areEqual(this.icon, timer.icon) && Intrinsics.areEqual(this.colors, timer.colors) && this.startTimeMs == timer.startTimeMs && Intrinsics.areEqual(this.timeSource, timer.timeSource) && this.isEventInFuture == timer.isEventInFuture && Intrinsics.areEqual(this.onClickListenerLegacy, timer.onClickListenerLegacy) && Intrinsics.areEqual(this.clickBehavior, timer.clickBehavior) && Intrinsics.areEqual(this.transitionManager, timer.transitionManager) && this.isHidden == timer.isHidden && this.shouldAnimate == timer.shouldAnimate && Intrinsics.areEqual(this.instanceId, timer.instanceId);
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final InstanceId getInstanceId() {
                return this.instanceId;
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final String getKey() {
                return this.key;
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel
            public final String getLogName() {
                return this.logName;
            }

            public final int hashCode() {
                int m = TransitionData$$ExternalSyntheticOutline0.m((this.timeSource.hashCode() + MoveResult$$ExternalSyntheticOutline0.m((this.colors.hashCode() + ((this.icon.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.key.hashCode() * 31, 31, this.isImportantForPrivacy)) * 31)) * 31, 31, this.startTimeMs)) * 31, 31, this.isEventInFuture);
                View.OnClickListener onClickListener = this.onClickListenerLegacy;
                int hashCode = (this.clickBehavior.hashCode() + ((m + (onClickListener == null ? 0 : onClickListener.hashCode())) * 31)) * 31;
                TransitionManager transitionManager = this.transitionManager;
                int m2 = TransitionData$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m((hashCode + (transitionManager == null ? 0 : transitionManager.hashCode())) * 31, 31, this.isHidden), 31, this.shouldAnimate);
                InstanceId instanceId = this.instanceId;
                return m2 + (instanceId != null ? instanceId.hashCode() : 0);
            }

            @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.Active
            public final boolean isHidden() {
                return this.isHidden;
            }

            public final String toString() {
                return "Timer(key=" + this.key + ", isImportantForPrivacy=" + this.isImportantForPrivacy + ", icon=" + this.icon + ", colors=" + this.colors + ", startTimeMs=" + this.startTimeMs + ", timeSource=" + this.timeSource + ", isEventInFuture=" + this.isEventInFuture + ", onClickListenerLegacy=" + this.onClickListenerLegacy + ", clickBehavior=" + this.clickBehavior + ", transitionManager=" + this.transitionManager + ", isHidden=" + this.isHidden + ", shouldAnimate=" + this.shouldAnimate + ", instanceId=" + this.instanceId + ")";
            }

            public Timer(String str, boolean z, ChipIcon chipIcon, ColorsModel colorsModel, long j, TimeSource timeSource, boolean z2, View.OnClickListener onClickListener, ClickBehavior clickBehavior, TransitionManager transitionManager, boolean z3, boolean z4, InstanceId instanceId) {
                super(str, z, chipIcon, colorsModel, onClickListener, clickBehavior, transitionManager, z3, z4, instanceId, null);
                this.key = str;
                this.isImportantForPrivacy = z;
                this.icon = chipIcon;
                this.colors = colorsModel;
                this.startTimeMs = j;
                this.timeSource = timeSource;
                this.isEventInFuture = z2;
                this.onClickListenerLegacy = onClickListener;
                this.clickBehavior = clickBehavior;
                this.transitionManager = transitionManager;
                this.isHidden = z3;
                this.shouldAnimate = z4;
                this.instanceId = instanceId;
                this.logName = "Active.Timer";
            }
        }

        public /* synthetic */ Active(String str, boolean z, ChipIcon chipIcon, ColorsModel colorsModel, View.OnClickListener onClickListener, ClickBehavior clickBehavior, TransitionManager transitionManager, boolean z2, boolean z3, InstanceId instanceId, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, (i & 2) != 0 ? false : z, chipIcon, colorsModel, onClickListener, clickBehavior, transitionManager, z2, z3, (i & 512) != 0 ? null : instanceId, null);
        }

        private Active(String str, boolean z, ChipIcon chipIcon, ColorsModel colorsModel, View.OnClickListener onClickListener, ClickBehavior clickBehavior, TransitionManager transitionManager, boolean z2, boolean z3, InstanceId instanceId) {
            super(null);
            this.key = str;
            this.icon = chipIcon;
            this.colors = colorsModel;
            this.onClickListenerLegacy = onClickListener;
            this.clickBehavior = clickBehavior;
            this.transitionManager = transitionManager;
            this.isHidden = z2;
            this.instanceId = instanceId;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class ChipIcon {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class SingleColorIcon extends ChipIcon {
            public final Icon impl;

            public SingleColorIcon(Icon icon) {
                super(false, null);
                this.impl = icon;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof SingleColorIcon) && Intrinsics.areEqual(this.impl, ((SingleColorIcon) obj).impl);
            }

            public final int hashCode() {
                return this.impl.hashCode();
            }

            public final String toString() {
                return "SingleColorIcon(impl=" + this.impl + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class StatusBarView extends ChipIcon {
            public final ContentDescription contentDescription;
            public final StatusBarIconView impl;

            public StatusBarView(StatusBarIconView statusBarIconView, ContentDescription contentDescription) {
                super(true, null);
                this.impl = statusBarIconView;
                this.contentDescription = contentDescription;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = StatusBarConnectedDisplays.$r8$clinit;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof StatusBarView)) {
                    return false;
                }
                StatusBarView statusBarView = (StatusBarView) obj;
                return Intrinsics.areEqual(this.impl, statusBarView.impl) && Intrinsics.areEqual(this.contentDescription, statusBarView.contentDescription);
            }

            public final int hashCode() {
                return this.contentDescription.hashCode() + (this.impl.hashCode() * 31);
            }

            public final String toString() {
                return "StatusBarView(impl=" + this.impl + ", contentDescription=" + this.contentDescription + ")";
            }
        }

        public /* synthetic */ ChipIcon(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
            this(z);
        }

        private ChipIcon(boolean z) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ClickBehavior {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class ExpandAction implements ClickBehavior {
            public final Function1 onClick;

            public ExpandAction(Function1 function1) {
                this.onClick = function1;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof ExpandAction) && Intrinsics.areEqual(this.onClick, ((ExpandAction) obj).onClick);
            }

            public final int hashCode() {
                return this.onClick.hashCode();
            }

            public final String toString() {
                return "ExpandAction(onClick=" + this.onClick + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class None implements ClickBehavior {
            public static final None INSTANCE = new None();

            private None() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof None);
            }

            public final int hashCode() {
                return 441437691;
            }

            public final String toString() {
                return "None";
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Inactive extends OngoingActivityChipModel {
        public final String logName;
        public final boolean shouldAnimate;
        public final TransitionManager transitionManager;

        public Inactive() {
            this(false, null, 3, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Inactive)) {
                return false;
            }
            Inactive inactive = (Inactive) obj;
            return this.shouldAnimate == inactive.shouldAnimate && Intrinsics.areEqual(this.transitionManager, inactive.transitionManager);
        }

        @Override // com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel
        public final String getLogName() {
            return this.logName;
        }

        public final int hashCode() {
            int hashCode = Boolean.hashCode(this.shouldAnimate) * 31;
            TransitionManager transitionManager = this.transitionManager;
            return hashCode + (transitionManager == null ? 0 : transitionManager.hashCode());
        }

        public final String toString() {
            return "Inactive(shouldAnimate=" + this.shouldAnimate + ", transitionManager=" + this.transitionManager + ")";
        }

        public /* synthetic */ Inactive(boolean z, TransitionManager transitionManager, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? true : z, (i & 2) != 0 ? null : transitionManager);
        }

        public Inactive(boolean z, TransitionManager transitionManager) {
            super(null);
            this.shouldAnimate = z;
            this.transitionManager = transitionManager;
            this.logName = "Inactive(anim=" + z + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class TransitionManager {
        public final ComposableControllerFactory controllerFactory;
        public final boolean hideChipForTransition;
        public final Function0 registerTransition;
        public final Function0 unregisterTransition;

        public TransitionManager() {
            this(null, null, null, false, 15, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TransitionManager)) {
                return false;
            }
            TransitionManager transitionManager = (TransitionManager) obj;
            return Intrinsics.areEqual(this.controllerFactory, transitionManager.controllerFactory) && Intrinsics.areEqual(this.registerTransition, transitionManager.registerTransition) && Intrinsics.areEqual(this.unregisterTransition, transitionManager.unregisterTransition) && this.hideChipForTransition == transitionManager.hideChipForTransition;
        }

        public final int hashCode() {
            ComposableControllerFactory composableControllerFactory = this.controllerFactory;
            return Boolean.hashCode(this.hideChipForTransition) + ((this.unregisterTransition.hashCode() + ((this.registerTransition.hashCode() + ((composableControllerFactory == null ? 0 : composableControllerFactory.hashCode()) * 31)) * 31)) * 31);
        }

        public final String toString() {
            return "TransitionManager(controllerFactory=" + this.controllerFactory + ", registerTransition=" + this.registerTransition + ", unregisterTransition=" + this.unregisterTransition + ", hideChipForTransition=" + this.hideChipForTransition + ")";
        }

        public TransitionManager(ComposableControllerFactory composableControllerFactory, Function0 function0, Function0 function02, boolean z) {
            this.controllerFactory = composableControllerFactory;
            this.registerTransition = function0;
            this.unregisterTransition = function02;
            this.hideChipForTransition = z;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ TransitionManager(com.android.systemui.animation.ComposableControllerFactory r1, kotlin.jvm.functions.Function0 r2, kotlin.jvm.functions.Function0 r3, boolean r4, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
            /*
                r0 = this;
                r6 = r5 & 1
                if (r6 == 0) goto L5
                r1 = 0
            L5:
                r6 = r5 & 2
                if (r6 == 0) goto Lf
                com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager$$ExternalSyntheticLambda0 r2 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager$$ExternalSyntheticLambda0
                r6 = 0
                r2.<init>()
            Lf:
                r6 = r5 & 4
                if (r6 == 0) goto L19
                com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager$$ExternalSyntheticLambda0 r3 = new com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel$TransitionManager$$ExternalSyntheticLambda0
                r6 = 1
                r3.<init>()
            L19:
                r5 = r5 & 8
                if (r5 == 0) goto L1e
                r4 = 0
            L1e:
                r0.<init>(r1, r2, r3, r4)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.chips.ui.model.OngoingActivityChipModel.TransitionManager.<init>(com.android.systemui.animation.ComposableControllerFactory, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, boolean, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }

    public /* synthetic */ OngoingActivityChipModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract String getLogName();

    private OngoingActivityChipModel() {
    }
}
