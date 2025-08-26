package com.android.systemui.qs.bar.repository;

import android.content.res.Resources;
import android.util.Log;
import com.android.systemui.R;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.qs.bar.domain.interactor.ColoredBackgroundWallpaperInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function6;

/* loaded from: classes2.dex */
final class ColoredBackgroundRepository$currentExtractedBackgroundColor$1 extends SuspendLambda implements Function6 {
    final /* synthetic */ ColoredBackgroundWallpaperInteractor $coloredBackgroundWallpaperInteractor;
    /* synthetic */ int I$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ ColoredBackgroundRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ColoredBackgroundRepository$currentExtractedBackgroundColor$1(ColoredBackgroundWallpaperInteractor coloredBackgroundWallpaperInteractor, ColoredBackgroundRepository coloredBackgroundRepository, Continuation continuation) {
        super(6, continuation);
        this.$coloredBackgroundWallpaperInteractor = coloredBackgroundWallpaperInteractor;
        this.this$0 = coloredBackgroundRepository;
    }

    @Override // kotlin.jvm.functions.Function6
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        int iIntValue = ((Number) obj3).intValue();
        ColoredBackgroundRepository$currentExtractedBackgroundColor$1 coloredBackgroundRepository$currentExtractedBackgroundColor$1 = new ColoredBackgroundRepository$currentExtractedBackgroundColor$1(this.$coloredBackgroundWallpaperInteractor, this.this$0, (Continuation) obj6);
        coloredBackgroundRepository$currentExtractedBackgroundColor$1.Z$0 = zBooleanValue;
        coloredBackgroundRepository$currentExtractedBackgroundColor$1.Z$1 = zBooleanValue2;
        coloredBackgroundRepository$currentExtractedBackgroundColor$1.I$0 = iIntValue;
        return coloredBackgroundRepository$currentExtractedBackgroundColor$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Resources.NotFoundException {
        int color;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        int i = this.I$0;
        ColoredBackgroundWallpaperInteractor coloredBackgroundWallpaperInteractor = this.$coloredBackgroundWallpaperInteractor;
        coloredBackgroundWallpaperInteractor.homeMatchingColor = coloredBackgroundWallpaperInteractor.extractColor(1, BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN);
        coloredBackgroundWallpaperInteractor.lockMatchingColor = coloredBackgroundWallpaperInteractor.extractColor(2, "lock");
        if (z || z2) {
            color = this.this$0.context.getResources().getColor(R.color.qs_tile_coloring_container_bg_ultra_mode);
        } else {
            ColoredBackgroundRepository coloredBackgroundRepository = this.this$0;
            String str = ColoredBackgroundRepository.TAG;
            color = coloredBackgroundRepository.getBgColorOverlaid() ? this.$coloredBackgroundWallpaperInteractor.adjustLegibility(this.this$0.getResourceColor()) : i != 0 ? this.$coloredBackgroundWallpaperInteractor.getWallpaperColor(ColoredBackgroundWallpaperInteractor.WallPaperStatus.LOCK) : i == 0 ? this.$coloredBackgroundWallpaperInteractor.getWallpaperColor(ColoredBackgroundWallpaperInteractor.WallPaperStatus.HOME) : this.this$0.getResourceColor();
        }
        Log.d(ColoredBackgroundRepository.TAG, "bgColorOverlaid = " + this.this$0.getBgColorOverlaid() + ", extractColor = 0x" + Integer.toHexString(color) + ", alpha = " + this.this$0.getAlpha() + ", alphaAppliedColor = 0x" + Integer.toHexString(ColoredBackgroundRepository.toARGB(color, this.this$0.getAlpha())));
        return new Integer(ColoredBackgroundRepository.toARGB(color, this.this$0.getAlpha()));
    }
}
