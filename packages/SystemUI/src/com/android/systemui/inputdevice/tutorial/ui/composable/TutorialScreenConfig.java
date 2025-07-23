package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import com.airbnb.lottie.compose.LottieDynamicProperties;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TutorialScreenConfig {
    public final Animations animations;
    public final Colors colors;
    public final Strings strings;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Animations {
        public final int educationResId;

        public Animations(int i) {
            this.educationResId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Animations) && this.educationResId == ((Animations) obj).educationResId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.educationResId);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.educationResId, ")", new StringBuilder("Animations(educationResId="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Colors {
        public static final Companion Companion = new Companion(null);
        public final LottieDynamicProperties animationColors;
        public final long background;
        public final long bodyText;
        public final long title;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        public /* synthetic */ Colors(long j, long j2, long j3, LottieDynamicProperties lottieDynamicProperties, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, j2, j3, lottieDynamicProperties);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Colors)) {
                return false;
            }
            Colors colors = (Colors) obj;
            long j = colors.background;
            Color.Companion companion = Color.Companion;
            return ULong.m3427equalsimpl0(this.background, j) && ULong.m3427equalsimpl0(this.title, colors.title) && ULong.m3427equalsimpl0(this.bodyText, colors.bodyText) && Intrinsics.areEqual(this.animationColors, colors.animationColors);
        }

        public final int hashCode() {
            Color.Companion companion = Color.Companion;
            int i = ULong.$r8$clinit;
            return this.animationColors.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.background) * 31, 31, this.title), 31, this.bodyText);
        }

        public final String toString() {
            String m462toStringimpl = Color.m462toStringimpl(this.background);
            String m462toStringimpl2 = Color.m462toStringimpl(this.title);
            String m462toStringimpl3 = Color.m462toStringimpl(this.bodyText);
            StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Colors(background=", m462toStringimpl, ", title=", m462toStringimpl2, ", bodyText=");
            m.append(m462toStringimpl3);
            m.append(", animationColors=");
            m.append(this.animationColors);
            m.append(")");
            return m.toString();
        }

        public /* synthetic */ Colors(long j, long j2, LottieDynamicProperties lottieDynamicProperties, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, j2, lottieDynamicProperties);
        }

        private Colors(long j, long j2, long j3, LottieDynamicProperties lottieDynamicProperties) {
            this.background = j;
            this.title = j2;
            this.bodyText = j3;
            this.animationColors = lottieDynamicProperties;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private Colors(long r17, long r19, com.airbnb.lottie.compose.LottieDynamicProperties r21) {
            /*
                r16 = this;
                int r0 = androidx.compose.ui.graphics.ColorKt.m467toArgb8_81llA(r17)
                com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig$Colors$Companion r1 = com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig.Colors.Companion
                r1.getClass()
                androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
                r1.getClass()
                long r1 = androidx.compose.ui.graphics.Color.White
                int r3 = androidx.compose.ui.graphics.ColorKt.m467toArgb8_81llA(r1)
                double r3 = androidx.core.graphics.ColorUtils.calculateContrast(r3, r0)
                long r5 = androidx.compose.ui.graphics.Color.Black
                int r7 = androidx.compose.ui.graphics.ColorKt.m467toArgb8_81llA(r5)
                double r7 = androidx.core.graphics.ColorUtils.calculateContrast(r7, r0)
                int r0 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
                if (r0 < 0) goto L28
                r12 = r1
                goto L29
            L28:
                r12 = r5
            L29:
                r15 = 0
                r7 = r16
                r8 = r17
                r10 = r19
                r14 = r21
                r7.<init>(r8, r10, r12, r14, r15)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.ui.composable.TutorialScreenConfig.Colors.<init>(long, long, com.airbnb.lottie.compose.LottieDynamicProperties):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Strings {
        public final int bodyErrorResId;
        public final int bodyResId;
        public final int bodySuccessResId;
        public final int titleErrorResId;
        public final int titleResId;
        public final int titleSuccessResId;

        public Strings(int i, int i2, int i3, int i4, int i5, int i6) {
            this.titleResId = i;
            this.bodyResId = i2;
            this.titleSuccessResId = i3;
            this.bodySuccessResId = i4;
            this.titleErrorResId = i5;
            this.bodyErrorResId = i6;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Strings)) {
                return false;
            }
            Strings strings = (Strings) obj;
            return this.titleResId == strings.titleResId && this.bodyResId == strings.bodyResId && this.titleSuccessResId == strings.titleSuccessResId && this.bodySuccessResId == strings.bodySuccessResId && this.titleErrorResId == strings.titleErrorResId && this.bodyErrorResId == strings.bodyErrorResId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.bodyErrorResId) + ReorderTile$$ExternalSyntheticOutline0.m(this.titleErrorResId, ReorderTile$$ExternalSyntheticOutline0.m(this.bodySuccessResId, ReorderTile$$ExternalSyntheticOutline0.m(this.titleSuccessResId, ReorderTile$$ExternalSyntheticOutline0.m(this.bodyResId, Integer.hashCode(this.titleResId) * 31, 31), 31), 31), 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Strings(titleResId=");
            sb.append(this.titleResId);
            sb.append(", bodyResId=");
            sb.append(this.bodyResId);
            sb.append(", titleSuccessResId=");
            sb.append(this.titleSuccessResId);
            sb.append(", bodySuccessResId=");
            sb.append(this.bodySuccessResId);
            sb.append(", titleErrorResId=");
            sb.append(this.titleErrorResId);
            sb.append(", bodyErrorResId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.bodyErrorResId, ")", sb);
        }
    }

    public TutorialScreenConfig(Colors colors, Strings strings, Animations animations) {
        this.colors = colors;
        this.strings = strings;
        this.animations = animations;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TutorialScreenConfig)) {
            return false;
        }
        TutorialScreenConfig tutorialScreenConfig = (TutorialScreenConfig) obj;
        return Intrinsics.areEqual(this.colors, tutorialScreenConfig.colors) && Intrinsics.areEqual(this.strings, tutorialScreenConfig.strings) && Intrinsics.areEqual(this.animations, tutorialScreenConfig.animations);
    }

    public final int hashCode() {
        return Integer.hashCode(this.animations.educationResId) + ((this.strings.hashCode() + (this.colors.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "TutorialScreenConfig(colors=" + this.colors + ", strings=" + this.strings + ", animations=" + this.animations + ")";
    }
}
