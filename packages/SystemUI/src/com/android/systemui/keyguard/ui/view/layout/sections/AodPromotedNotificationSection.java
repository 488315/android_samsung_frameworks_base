package com.android.systemui.keyguard.ui.view.layout.sections;

import android.content.Context;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardSection;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.shade.domain.interactor.ShadeModeInteractor;
import com.android.systemui.statusbar.notification.promoted.PromotedNotificationLogger;
import com.android.systemui.statusbar.notification.promoted.ui.viewmodel.AODPromotedNotificationViewModel;
import java.util.Arrays;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.HexExtensionsKt;
import kotlin.text.HexFormat;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes2.dex */
public final class AodPromotedNotificationSection extends KeyguardSection {
    public static final Companion Companion = new Companion(null);
    public static final int viewId = R.id.aod_promoted_notification_frame;
    public final AODPromotedNotificationViewModel.Factory viewModelFactory;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public AodPromotedNotificationSection(Context context, AODPromotedNotificationViewModel.Factory factory, ShadeModeInteractor shadeModeInteractor, PromotedNotificationLogger promotedNotificationLogger) {
        String str;
        char c = 2;
        this.viewModelFactory = factory;
        promotedNotificationLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        int iIdentityHashCode = System.identityHashCode(this);
        int[] iArr = HexExtensionsKt.BYTE_TO_LOWER_CASE_HEX_DIGITS;
        HexFormat.Companion.getClass();
        HexFormat hexFormat = HexFormat.Default;
        String str2 = hexFormat.upperCase ? "0123456789ABCDEF" : "0123456789abcdef";
        HexFormat.NumberHexFormat numberHexFormat = hexFormat.number;
        boolean z = numberHexFormat.isDigitsOnlyAndNoPadding;
        boolean z2 = numberHexFormat.removeLeadingZeros;
        if (z) {
            char[] cArr = {str2.charAt((iIdentityHashCode >> 28) & 15), str2.charAt((iIdentityHashCode >> 24) & 15), str2.charAt((iIdentityHashCode >> 20) & 15), str2.charAt((iIdentityHashCode >> 16) & 15), str2.charAt((iIdentityHashCode >> 12) & 15), str2.charAt((iIdentityHashCode >> 8) & 15), str2.charAt((iIdentityHashCode >> 4) & 15), str2.charAt(iIdentityHashCode & 15)};
            if (z2) {
                int iNumberOfLeadingZeros = Integer.numberOfLeadingZeros(iIdentityHashCode) >> 2;
                str = StringsKt__StringsJVMKt.concatToString$default(cArr, iNumberOfLeadingZeros <= 7 ? iNumberOfLeadingZeros : 7, 0, 2);
            } else {
                str = new String(cArr);
            }
        } else {
            long j = iIdentityHashCode;
            int i = numberHexFormat.minLength;
            int i2 = i - 8;
            i2 = i2 < 0 ? 0 : i2;
            String str3 = numberHexFormat.prefix;
            String str4 = numberHexFormat.suffix;
            long length = str3.length() + i2 + 8 + str4.length();
            if (0 > length || length > 2147483647L) {
                throw new IllegalArgumentException("The resulting string length is too big: " + ((Object) ULong.m3447toStringimpl(length)));
            }
            int i3 = (int) length;
            char[] cArr2 = new char[i3];
            int length2 = str3.length();
            if (length2 != 0) {
                if (length2 != 1) {
                    str3.getChars(0, str3.length(), cArr2, 0);
                } else {
                    cArr2[0] = str3.charAt(0);
                }
            }
            int length3 = str3.length();
            if (i2 > 0) {
                int i4 = i2 + length3;
                Arrays.fill(cArr2, length3, i4, str2.charAt(0));
                length3 = i4;
            }
            int i5 = 32;
            int i6 = 0;
            while (i6 < 8) {
                i5 -= 4;
                char c2 = c;
                long j2 = j;
                int i7 = (int) ((j >> i5) & 15);
                z2 = z2 && i7 == 0 && (i5 >> 2) >= i;
                if (!z2) {
                    cArr2[length3] = str2.charAt(i7);
                    length3++;
                }
                i6++;
                c = c2;
                j = j2;
            }
            int length4 = str4.length();
            if (length4 != 0) {
                if (length4 != 1) {
                    str4.getChars(0, str4.length(), cArr2, length3);
                } else {
                    cArr2[length3] = str4.charAt(0);
                }
            }
            int length5 = str4.length() + length3;
            str = length5 == i3 ? new String(cArr2) : StringsKt__StringsJVMKt.concatToString$default(cArr2, 0, length5, 1);
        }
        LogBuffer.log$default(promotedNotificationLogger.buffer, "AodPromotedNotificationSection", logLevel, ContentInViewNode$Request$$ExternalSyntheticOutline0.m("section ", str, " created"));
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void addViews(ConstraintLayout constraintLayout) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void applyConstraints(ConstraintSet constraintSet) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void bindData(ConstraintLayout constraintLayout) {
    }

    @Override // com.android.systemui.keyguard.shared.model.KeyguardSection
    public final void removeViews(ConstraintLayout constraintLayout) {
    }
}
