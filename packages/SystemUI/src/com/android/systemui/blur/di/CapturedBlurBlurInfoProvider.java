package com.android.systemui.blur.di;

import com.android.systemui.blur.data.repository.SecBouncerColorCurveRepository;
import com.android.systemui.blur.data.repository.SecQsColorCurveRepository;
import com.android.systemui.blur.di.SecPanelBlurBinding;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CapturedBlurBlurInfoProvider {
    public final SecBouncerColorCurveRepository secBouncerColorCurveRepository;
    public final SecQsColorCurveRepository secQsColorCurveRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SecPanelBlurBinding.BlurType.values().length];
            try {
                iArr[SecPanelBlurBinding.BlurType.QUICK_PANEL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SecPanelBlurBinding.BlurType.BOUNCER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SecPanelBlurBinding.BlurType.FULL_SCREEN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SecPanelBlurBinding.BlurType.ALT_VIEW.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public CapturedBlurBlurInfoProvider(SecQsColorCurveRepository secQsColorCurveRepository, SecBouncerColorCurveRepository secBouncerColorCurveRepository) {
        this.secQsColorCurveRepository = secQsColorCurveRepository;
        this.secBouncerColorCurveRepository = secBouncerColorCurveRepository;
    }
}
