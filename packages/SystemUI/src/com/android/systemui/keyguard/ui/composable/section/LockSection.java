package com.android.systemui.keyguard.ui.composable.section;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.layout.LayoutModifierKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import com.android.compose.animation.scene.ContentScope;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.customization.R$dimen;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder;
import com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryBackgroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.TouchHandlingViewLogger;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.VibratorHelper;
import com.google.android.msdl.domain.MSDLPlayer;
import dagger.Lazy;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class LockSection {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineScope applicationScope;
    public final AuthController authController;
    public final Lazy deviceEntryBackgroundViewModel;
    public final Lazy deviceEntryForegroundViewModel;
    public final Lazy deviceEntryIconViewModel;
    public final Lazy falsingManager;
    public final FeatureFlagsClassic featureFlags;
    public final LogBuffer logBuffer;
    public final CoroutineDispatcher mainDispatcher;
    public final Lazy msdlPlayer;
    public final Lazy vibratorHelper;
    public final WindowManager windowManager;

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

    public LockSection(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, WindowManager windowManager, AuthController authController, FeatureFlagsClassic featureFlagsClassic, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, LogBuffer logBuffer) {
        this.applicationScope = coroutineScope;
        this.mainDispatcher = coroutineDispatcher;
        this.windowManager = windowManager;
        this.authController = authController;
        this.featureFlags = featureFlagsClassic;
        this.deviceEntryIconViewModel = lazy;
        this.deviceEntryForegroundViewModel = lazy2;
        this.deviceEntryBackgroundViewModel = lazy3;
        this.falsingManager = lazy4;
        this.vibratorHelper = lazy5;
        this.msdlPlayer = lazy6;
        this.logBuffer = logBuffer;
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c7  */
    /* renamed from: LockIcon-BAq54LU, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m2618LockIconBAq54LU(final ContentScope contentScope, final Color color, Modifier modifier, Composer composer, final int i, final int i2) {
        int i3;
        int i4;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(957501896);
        int i5 = i | (composerImpl.changed(contentScope) ? 4 : 2);
        int i6 = i2 & 1;
        if (i6 != 0) {
            i3 = i5 | 48;
        } else {
            i3 = i5 | (composerImpl.changed(color) ? 32 : 16);
        }
        int i7 = i2 & 2;
        if (i7 != 0) {
            i4 = i3 | 384;
        } else {
            i4 = i3 | (composerImpl.changed(modifier) ? 256 : 128);
        }
        int i8 = i4 | (composerImpl.changedInstance(this) ? 2048 : 1024);
        if ((i8 & 1171) == 1170 && composerImpl.getSkipping()) {
            composerImpl.skipToGroupEnd();
        } else {
            if (i6 != 0) {
                color = null;
            }
            if (i7 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("com.android.systemui.keyguard.ui.composable.section.LockSection.LockIcon (LockSection.kt:73)");
            }
            final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
            composerImpl.startReplaceGroup(1731651852);
            boolean zChangedInstance = ((i8 & 112) == 32) | composerImpl.changedInstance(this);
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion companion = Composer.Companion;
            if (!zChangedInstance) {
                companion.getClass();
                if (objRememberedValue == Composer.Companion.Empty) {
                    objRememberedValue = new Function1() { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj) {
                            Context context2 = (Context) obj;
                            int i9 = LockSection.$r8$clinit;
                            LockSection lockSection = this.f$0;
                            DeviceEntryIconView deviceEntryIconView = new DeviceEntryIconView(context2, null, 0, new TouchHandlingViewLogger(lockSection.logBuffer, "LockSection"), 4, null);
                            deviceEntryIconView.setId(R.id.device_entry_icon_view);
                            DeviceEntryIconViewModel deviceEntryIconViewModel = (DeviceEntryIconViewModel) lockSection.deviceEntryIconViewModel.get();
                            DeviceEntryForegroundViewModel deviceEntryForegroundViewModel = (DeviceEntryForegroundViewModel) lockSection.deviceEntryForegroundViewModel.get();
                            DeviceEntryBackgroundViewModel deviceEntryBackgroundViewModel = (DeviceEntryBackgroundViewModel) lockSection.deviceEntryBackgroundViewModel.get();
                            FalsingManager falsingManager = (FalsingManager) lockSection.falsingManager.get();
                            VibratorHelper vibratorHelper = (VibratorHelper) lockSection.vibratorHelper.get();
                            MSDLPlayer mSDLPlayer = (MSDLPlayer) lockSection.msdlPlayer.get();
                            DeviceEntryIconViewBinder.m2617bind7D8XEZs(lockSection.applicationScope, lockSection.mainDispatcher, deviceEntryIconView, deviceEntryIconViewModel, deviceEntryForegroundViewModel, deviceEntryBackgroundViewModel, falsingManager, vibratorHelper, mSDLPlayer, color);
                            return deviceEntryIconView;
                        }
                    };
                    composerImpl.updateRememberedValue(objRememberedValue);
                }
                Function1 function1 = (Function1) objRememberedValue;
                composerImpl.end(false);
                Modifier modifierElement = contentScope.element(modifier, LockSectionKt.LockIconElementKey);
                composerImpl.startReplaceGroup(1731684302);
                boolean zChangedInstance2 = composerImpl.changedInstance(this) | composerImpl.changedInstance(context);
                Object objRememberedValue2 = composerImpl.rememberedValue();
                if (!zChangedInstance2) {
                    companion.getClass();
                    if (objRememberedValue2 == Composer.Companion.Empty) {
                        objRememberedValue2 = new Function3() { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$$ExternalSyntheticLambda1
                            @Override // kotlin.jvm.functions.Function3
                            public final Object invoke(Object obj, Object obj2, Object obj3) {
                                Pair pair;
                                Rect rect;
                                Context context2 = context;
                                MeasureScope measureScope = (MeasureScope) obj;
                                Measurable measurable = (Measurable) obj2;
                                LockSection lockSection = this.f$0;
                                float f = lockSection.windowManager.getCurrentWindowMetrics().getBounds().right;
                                Flags flags = Flags.INSTANCE;
                                lockSection.featureFlags.getClass();
                                int i9 = (int) ((DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f) * 36);
                                AuthController authController = lockSection.authController;
                                Point udfpsLocation = authController.getUdfpsLocation();
                                if (!authController.isUdfpsSupported() || udfpsLocation == null) {
                                    pair = new Pair(IntOffset.m849boximpl((((int) (f / 2)) << 32) | (((int) (r13.bottom - ((context2.getResources().getDimensionPixelSize(R$dimen.lock_icon_margin_bottom) + i9) * r10))) & 4294967295L)), Integer.valueOf((int) (i9 * authController.mScaleFactor)));
                                } else {
                                    pair = new Pair(IntOffset.m849boximpl((udfpsLocation.y & 4294967295L) | (udfpsLocation.x << 32)), Integer.valueOf((int) ((authController.mUdfpsController == null || (rect = authController.mUdfpsBounds) == null) ? -1.0f : rect.height() / 2.0f)));
                                }
                                long j = ((IntOffset) pair.component1()).packedValue;
                                int iIntValue = ((Number) pair.component2()).intValue();
                                int i10 = (int) (j >> 32);
                                int i11 = (int) (j & 4294967295L);
                                IntRect intRect = new IntRect(i10 - iIntValue, i11 - iIntValue, i10 + iIntValue, i11 + iIntValue);
                                Constraints.Companion companion2 = Constraints.Companion;
                                int width = intRect.getWidth();
                                int height = intRect.getHeight();
                                companion2.getClass();
                                final Placeable placeableMo610measureBRTryo0 = measurable.mo610measureBRTryo0(Constraints.Companion.m829fixedJhjzzOo(width, height));
                                int i12 = placeableMo610measureBRTryo0.width;
                                int i13 = placeableMo610measureBRTryo0.height;
                                BlueprintAlignmentLines$LockIcon.INSTANCE.getClass();
                                return measureScope.layout$1(i12, i13, MapsKt__MapsKt.mapOf(new Pair(BlueprintAlignmentLines$LockIcon.Left, Integer.valueOf(intRect.left)), new Pair(BlueprintAlignmentLines$LockIcon.Top, Integer.valueOf(intRect.top)), new Pair(BlueprintAlignmentLines$LockIcon.Right, Integer.valueOf(intRect.right)), new Pair(BlueprintAlignmentLines$LockIcon.Bottom, Integer.valueOf(intRect.bottom))), new Function1() { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$$ExternalSyntheticLambda3
                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj4) {
                                        int i14 = LockSection.$r8$clinit;
                                        ((Placeable.PlacementScope) obj4).place(placeableMo610measureBRTryo0, 0, 0, 0.0f);
                                        return Unit.INSTANCE;
                                    }
                                });
                            }
                        };
                        composerImpl.updateRememberedValue(objRememberedValue2);
                    }
                    composerImpl.end(false);
                    AndroidView_androidKt.AndroidView(function1, LayoutModifierKt.layout(modifierElement, (Function3) objRememberedValue2), null, composerImpl, 0, 4);
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventEnd();
                    }
                }
            }
        }
        final Color color2 = color;
        final Modifier modifier2 = modifier;
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2(contentScope, color2, modifier2, i, i2) { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$$ExternalSyntheticLambda2
                public final /* synthetic */ ContentScope f$1;
                public final /* synthetic */ Color f$2;
                public final /* synthetic */ Modifier f$3;
                public final /* synthetic */ int f$5;

                {
                    this.f$5 = i2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    Composer composer2 = (Composer) obj;
                    ((Integer) obj2).getClass();
                    int i9 = LockSection.$r8$clinit;
                    int iUpdateChangedFlags = RecomposeScopeImplKt.updateChangedFlags(1);
                    this.f$0.m2618LockIconBAq54LU(this.f$1, this.f$2, this.f$3, composer2, iUpdateChangedFlags, this.f$5);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
