package com.android.systemui.communal.ui.compose.section;

import com.android.systemui.communal.ui.viewmodel.HubOnboardingViewModel;
import com.android.systemui.statusbar.phone.SystemUIDialogFactory;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class HubOnboardingSection {
    public static final Companion Companion = new Companion(null);
    public static final long SHOW_BOTTOMSHEET_DELAY_MS;
    public final SystemUIDialogFactory dialogFactory;
    public final HubOnboardingViewModel.Factory viewModelFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        Duration.Companion companion = Duration.Companion;
        SHOW_BOTTOMSHEET_DELAY_MS = DurationKt.toDuration(1000, DurationUnit.MILLISECONDS);
    }

    public HubOnboardingSection(HubOnboardingViewModel.Factory factory, SystemUIDialogFactory systemUIDialogFactory) {
        this.viewModelFactory = factory;
        this.dialogFactory = systemUIDialogFactory;
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0046, code lost:
    
        if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void BottomSheet(final int r8, androidx.compose.runtime.Composer r9) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.communal.ui.compose.section.HubOnboardingSection.BottomSheet(int, androidx.compose.runtime.Composer):void");
    }
}
