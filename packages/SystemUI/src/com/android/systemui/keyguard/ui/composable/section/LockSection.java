package com.android.systemui.keyguard.ui.composable.section;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
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
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.viewinterop.AndroidView_androidKt;
import com.android.compose.animation.scene.SceneScope;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.keyguard.ui.binder.DeviceEntryIconViewBinder;
import com.android.systemui.keyguard.ui.composable.blueprint.BlueprintAlignmentLines$LockIcon;
import com.android.systemui.keyguard.ui.view.DeviceEntryIconView;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryBackgroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryForegroundViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DeviceEntryIconViewModel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.statusbar.VibratorHelper;
import dagger.Lazy;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockSection {
    public final CoroutineScope applicationScope;
    public final AuthController authController;
    public final Lazy deviceEntryBackgroundViewModel;
    public final Lazy deviceEntryForegroundViewModel;
    public final Lazy deviceEntryIconViewModel;
    public final Lazy falsingManager;
    public final FeatureFlagsClassic featureFlags;
    public final NotificationPanelView notificationPanelView;
    public final Lazy vibratorHelper;
    public final WindowManager windowManager;

    public LockSection(CoroutineScope coroutineScope, WindowManager windowManager, AuthController authController, FeatureFlagsClassic featureFlagsClassic, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, NotificationPanelView notificationPanelView) {
        this.applicationScope = coroutineScope;
        this.windowManager = windowManager;
        this.authController = authController;
        this.featureFlags = featureFlagsClassic;
        this.deviceEntryIconViewModel = lazy2;
        this.deviceEntryForegroundViewModel = lazy3;
        this.deviceEntryBackgroundViewModel = lazy4;
        this.falsingManager = lazy5;
        this.vibratorHelper = lazy6;
        this.notificationPanelView = notificationPanelView;
    }

    /* renamed from: LockIcon-BAq54LU, reason: not valid java name */
    public final void m1966LockIconBAq54LU(final SceneScope sceneScope, final Color color, Modifier modifier, Composer composer, final int i, final int i2) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1522483803);
        if ((i2 & 1) != 0) {
            color = null;
        }
        if ((i2 & 2) != 0) {
            modifier = Modifier.Companion;
        }
        OpaqueKey opaqueKey = ComposerKt.invocation;
        Flags.keyguardBottomAreaRefactor();
        Flags.deviceEntryUdfpsRefactor();
        NotificationPanelView notificationPanelView = this.notificationPanelView;
        View findViewById = notificationPanelView.findViewById(R.id.lock_icon_view);
        if (findViewById != null) {
            notificationPanelView.removeView(findViewById);
        }
        final Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        AndroidView_androidKt.AndroidView(new Function1() { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$LockIcon$3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Flags.deviceEntryUdfpsRefactor();
                DeviceEntryIconView deviceEntryIconView = new DeviceEntryIconView((Context) obj, null, 0, 4, null);
                LockSection lockSection = LockSection.this;
                Color color2 = color;
                deviceEntryIconView.setId(R.id.device_entry_icon_view);
                DeviceEntryIconViewBinder.m1965bind9Oi015Q(lockSection.applicationScope, deviceEntryIconView, (DeviceEntryIconViewModel) lockSection.deviceEntryIconViewModel.get(), (DeviceEntryForegroundViewModel) lockSection.deviceEntryForegroundViewModel.get(), (DeviceEntryBackgroundViewModel) lockSection.deviceEntryBackgroundViewModel.get(), (FalsingManager) lockSection.falsingManager.get(), (VibratorHelper) lockSection.vibratorHelper.get(), color2);
                return deviceEntryIconView;
            }
        }, LayoutModifierKt.layout(sceneScope.element(modifier, LockSectionKt.LockIconElementKey), new Function3() { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$LockIcon$4
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                Pair pair;
                MeasureScope measureScope = (MeasureScope) obj;
                Measurable measurable = (Measurable) obj2;
                long j = ((Constraints) obj3).value;
                LockSection lockSection = LockSection.this;
                Context context2 = context;
                float f = lockSection.windowManager.getCurrentWindowMetrics().getBounds().right;
                com.android.systemui.flags.Flags flags = com.android.systemui.flags.Flags.INSTANCE;
                lockSection.featureFlags.getClass();
                int i3 = (int) ((DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f) * 36);
                AuthController authController = lockSection.authController;
                Point udfpsLocation = authController.getUdfpsLocation();
                if (!authController.isUdfpsSupported() || udfpsLocation == null) {
                    float f2 = authController.mScaleFactor;
                    pair = new Pair(IntOffset.m749boximpl(IntOffsetKt.IntOffset((int) (f / 2), (int) (r0.bottom - ((context2.getResources().getDimensionPixelSize(R.dimen.lock_icon_margin_bottom) + i3) * f2)))), Integer.valueOf((int) (i3 * f2)));
                } else {
                    pair = new Pair(IntOffset.m749boximpl(IntOffsetKt.IntOffset(udfpsLocation.x, udfpsLocation.y)), Integer.valueOf((int) authController.getUdfpsRadius()));
                }
                long j2 = ((IntOffset) pair.component1()).packedValue;
                int intValue = ((Number) pair.component2()).intValue();
                int i4 = (int) (j2 >> 32);
                int i5 = (int) (j2 & 4294967295L);
                IntRect intRect = new IntRect(i4 - intValue, i5 - intValue, i4 + intValue, i5 + intValue);
                Constraints.Companion companion = Constraints.Companion;
                int width = intRect.getWidth();
                int height = intRect.getHeight();
                companion.getClass();
                final Placeable mo528measureBRTryo0 = measurable.mo528measureBRTryo0(Constraints.Companion.m729fixedJhjzzOo(width, height));
                int i6 = mo528measureBRTryo0.width;
                int i7 = mo528measureBRTryo0.height;
                BlueprintAlignmentLines$LockIcon.INSTANCE.getClass();
                return measureScope.layout$1(i6, i7, MapsKt__MapsKt.mapOf(new Pair(BlueprintAlignmentLines$LockIcon.Left, Integer.valueOf(intRect.left)), new Pair(BlueprintAlignmentLines$LockIcon.Top, Integer.valueOf(intRect.top)), new Pair(BlueprintAlignmentLines$LockIcon.Right, Integer.valueOf(intRect.right)), new Pair(BlueprintAlignmentLines$LockIcon.Bottom, Integer.valueOf(intRect.bottom))), new Function1() { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$LockIcon$4.1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj4) {
                        Placeable.PlacementScope.place$default((Placeable.PlacementScope) obj4, Placeable.this, 0, 0);
                        return Unit.INSTANCE;
                    }
                });
            }
        }), null, composerImpl, 0, 4);
        RecomposeScopeImpl endRestartGroup = composerImpl.endRestartGroup();
        if (endRestartGroup != null) {
            final Color color2 = color;
            final Modifier modifier2 = modifier;
            endRestartGroup.block = new Function2() { // from class: com.android.systemui.keyguard.ui.composable.section.LockSection$LockIcon$5
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    ((Number) obj2).intValue();
                    LockSection.this.m1966LockIconBAq54LU(sceneScope, color2, modifier2, (Composer) obj, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
                    return Unit.INSTANCE;
                }
            };
        }
    }
}
