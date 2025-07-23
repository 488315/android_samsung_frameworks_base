package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface TutorialActionState {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Error implements TutorialActionState {
        public static final Error INSTANCE = new Error();

        private Error() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Error);
        }

        public final int hashCode() {
            return -1127423507;
        }

        public final String toString() {
            return "Error";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Finished implements TutorialActionState {
        public final int successAnimation;

        public Finished(int i) {
            this.successAnimation = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Finished) && this.successAnimation == ((Finished) obj).successAnimation;
        }

        public final int hashCode() {
            return Integer.hashCode(this.successAnimation);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.successAnimation, ")", new StringBuilder("Finished(successAnimation="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InProgress implements TutorialActionState, Progress {
        public final String endMarker;
        public final float progress;
        public final String startMarker;

        public InProgress() {
            this(0.0f, null, null, 7, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof InProgress)) {
                return false;
            }
            InProgress inProgress = (InProgress) obj;
            return Float.compare(this.progress, inProgress.progress) == 0 && Intrinsics.areEqual(this.startMarker, inProgress.startMarker) && Intrinsics.areEqual(this.endMarker, inProgress.endMarker);
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.composable.Progress
        public final String getEndMarker() {
            return this.endMarker;
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.composable.Progress
        public final float getProgress() {
            return this.progress;
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.composable.Progress
        public final String getStartMarker() {
            return this.startMarker;
        }

        public final int hashCode() {
            int hashCode = Float.hashCode(this.progress) * 31;
            String str = this.startMarker;
            int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
            String str2 = this.endMarker;
            return hashCode2 + (str2 != null ? str2.hashCode() : 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("InProgress(progress=");
            sb.append(this.progress);
            sb.append(", startMarker=");
            sb.append(this.startMarker);
            sb.append(", endMarker=");
            return TransitionKt$$ExternalSyntheticOutline0.m(sb, this.endMarker, ")");
        }

        public InProgress(float f, String str, String str2) {
            this.progress = f;
            this.startMarker = str;
            this.endMarker = str2;
        }

        public /* synthetic */ InProgress(float f, String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? 0.0f : f, (i & 2) != 0 ? null : str, (i & 4) != 0 ? null : str2);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InProgressAfterError implements TutorialActionState, Progress {
        public final InProgress inProgress;

        public InProgressAfterError(InProgress inProgress) {
            this.inProgress = inProgress;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof InProgressAfterError) && Intrinsics.areEqual(this.inProgress, ((InProgressAfterError) obj).inProgress);
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.composable.Progress
        public final String getEndMarker() {
            return this.inProgress.endMarker;
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.composable.Progress
        public final float getProgress() {
            return this.inProgress.progress;
        }

        @Override // com.android.systemui.inputdevice.tutorial.ui.composable.Progress
        public final String getStartMarker() {
            return this.inProgress.startMarker;
        }

        public final int hashCode() {
            return this.inProgress.hashCode();
        }

        public final String toString() {
            return "InProgressAfterError(inProgress=" + this.inProgress + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotStarted implements TutorialActionState {
        public static final NotStarted INSTANCE = new NotStarted();

        private NotStarted() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof NotStarted);
        }

        public final int hashCode() {
            return -757726871;
        }

        public final String toString() {
            return "NotStarted";
        }
    }
}
