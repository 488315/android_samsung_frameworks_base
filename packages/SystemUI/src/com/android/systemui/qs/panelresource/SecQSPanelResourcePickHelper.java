package com.android.systemui.qs.panelresource;

import android.util.Log;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecQSPanelResourcePickHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Lazy largePicker$delegate;
    public final Lazy narrowPicker$delegate;
    public final Lazy normalPicker$delegate;
    public final Lazy widePicker$delegate;
    public final Lazy uiDisplayModeInteractor$delegate = LazyKt__LazyJVMKt.lazy(new SecQSPanelResourcePickHelper$$ExternalSyntheticLambda0());
    public final SecQSPanelResourceCommon common = new SecQSPanelResourceCommon();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SecQsUiDisplayModeInteractor.UiDisplayMode.values().length];
            try {
                iArr[SecQsUiDisplayModeInteractor.UiDisplayMode.LARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SecQsUiDisplayModeInteractor.UiDisplayMode.NARROW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SecQsUiDisplayModeInteractor.UiDisplayMode.NORMAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[SecQsUiDisplayModeInteractor.UiDisplayMode.WIDE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public SecQSPanelResourcePickHelper() {
        final int i = 0;
        this.largePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecQSPanelResourcePickHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = this.f$0;
                switch (i) {
                    case 0:
                        int i2 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceLargePicker(secQSPanelResourcePickHelper.common);
                    case 1:
                        int i3 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNarrowPicker(secQSPanelResourcePickHelper.common);
                    case 2:
                        int i4 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNormalPicker(secQSPanelResourcePickHelper.common);
                    default:
                        int i5 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceWidePicker(secQSPanelResourcePickHelper.common);
                }
            }
        });
        final int i2 = 1;
        this.narrowPicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecQSPanelResourcePickHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = this.f$0;
                switch (i2) {
                    case 0:
                        int i22 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceLargePicker(secQSPanelResourcePickHelper.common);
                    case 1:
                        int i3 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNarrowPicker(secQSPanelResourcePickHelper.common);
                    case 2:
                        int i4 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNormalPicker(secQSPanelResourcePickHelper.common);
                    default:
                        int i5 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceWidePicker(secQSPanelResourcePickHelper.common);
                }
            }
        });
        final int i3 = 2;
        this.normalPicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecQSPanelResourcePickHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = this.f$0;
                switch (i3) {
                    case 0:
                        int i22 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceLargePicker(secQSPanelResourcePickHelper.common);
                    case 1:
                        int i32 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNarrowPicker(secQSPanelResourcePickHelper.common);
                    case 2:
                        int i4 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNormalPicker(secQSPanelResourcePickHelper.common);
                    default:
                        int i5 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceWidePicker(secQSPanelResourcePickHelper.common);
                }
            }
        });
        final int i4 = 3;
        this.widePicker$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.qs.panelresource.SecQSPanelResourcePickHelper$$ExternalSyntheticLambda1
            public final /* synthetic */ SecQSPanelResourcePickHelper f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                SecQSPanelResourcePickHelper secQSPanelResourcePickHelper = this.f$0;
                switch (i4) {
                    case 0:
                        int i22 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceLargePicker(secQSPanelResourcePickHelper.common);
                    case 1:
                        int i32 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNarrowPicker(secQSPanelResourcePickHelper.common);
                    case 2:
                        int i42 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceNormalPicker(secQSPanelResourcePickHelper.common);
                    default:
                        int i5 = SecQSPanelResourcePickHelper.$r8$clinit;
                        return new SecQSPanelResourceWidePicker(secQSPanelResourcePickHelper.common);
                }
            }
        });
    }

    public final SecQSPanelResourceNormalPicker getTargetPicker() {
        Lazy lazy = this.uiDisplayModeInteractor$delegate;
        int i = WhenMappings.$EnumSwitchMapping$0[((SecQsUiDisplayModeInteractor.UiDisplayMode) ((SecQsUiDisplayModeInteractor) lazy.getValue()).getUiDisplayMode().getValue()).ordinal()];
        if (i == 1) {
            return (SecQSPanelResourceLargePicker) this.largePicker$delegate.getValue();
        }
        if (i == 2) {
            return (SecQSPanelResourceNarrowPicker) this.narrowPicker$delegate.getValue();
        }
        Lazy lazy2 = this.normalPicker$delegate;
        if (i == 3) {
            return (SecQSPanelResourceNormalPicker) lazy2.getValue();
        }
        if (i == 4) {
            return (SecQSPanelResourceWidePicker) this.widePicker$delegate.getValue();
        }
        SecQSPanelResourceNormalPicker secQSPanelResourceNormalPicker = (SecQSPanelResourceNormalPicker) lazy2.getValue();
        Log.e("SecQSPanelResourcePickHelper", "need to prepare suitable picker for " + ((SecQsUiDisplayModeInteractor) lazy.getValue()).getUiDisplayMode().getValue());
        return secQSPanelResourceNormalPicker;
    }
}
