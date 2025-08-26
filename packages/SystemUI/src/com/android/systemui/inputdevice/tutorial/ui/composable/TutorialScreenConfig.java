package com.android.systemui.inputdevice.tutorial.ui.composable;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.core.graphics.ColorUtils;
import com.airbnb.lottie.compose.LottieDynamicProperties;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class TutorialScreenConfig {
    public final Animations animations;
    public final Colors colors;
    public final Strings strings;

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

    public final class Colors {
        public static final Companion Companion = new Companion(null);
        public final LottieDynamicProperties animationColors;
        public final long background;
        public final long bodyText;
        public final long title;

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
            return ULong.m3447equalsimpl0(this.background, j) && ULong.m3447equalsimpl0(this.title, colors.title) && ULong.m3447equalsimpl0(this.bodyText, colors.bodyText) && Intrinsics.areEqual(this.animationColors, colors.animationColors);
        }

        public final int hashCode() {
            Color.Companion companion = Color.Companion;
            int i = ULong.$r8$clinit;
            return this.animationColors.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.background) * 31, 31, this.title), 31, this.bodyText);
        }

        public final String toString() {
            String strM464toStringimpl = Color.m464toStringimpl(this.background);
            String strM464toStringimpl2 = Color.m464toStringimpl(this.title);
            String strM464toStringimpl3 = Color.m464toStringimpl(this.bodyText);
            StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Colors(background=", strM464toStringimpl, ", title=", strM464toStringimpl2, ", bodyText=");
            sbM.append(strM464toStringimpl3);
            sbM.append(", animationColors=");
            sbM.append(this.animationColors);
            sbM.append(")");
            return sbM.toString();
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
        private Colors(long j, long j2, LottieDynamicProperties lottieDynamicProperties) {
            int iM469toArgb8_81llA = ColorKt.m469toArgb8_81llA(j);
            Companion.getClass();
            Color.Companion.getClass();
            long j3 = Color.White;
            double dCalculateContrast = ColorUtils.calculateContrast(ColorKt.m469toArgb8_81llA(j3), iM469toArgb8_81llA);
            long j4 = Color.Black;
            this(j, j2, dCalculateContrast >= ColorUtils.calculateContrast(ColorKt.m469toArgb8_81llA(j4), iM469toArgb8_81llA) ? j3 : j4, lottieDynamicProperties, null);
        }
    }

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
