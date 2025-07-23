package com.android.systemui.wallpapers.domain.interactor;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.RectF;
import android.util.Log;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.wallpapers.domain.interactor.WallpaperFocalAreaInteractor;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class WallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1 extends SuspendLambda implements Function5 {
    /* synthetic */ float F$0;
    /* synthetic */ float F$1;
    /* synthetic */ float F$2;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ WallpaperFocalAreaInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1(WallpaperFocalAreaInteractor wallpaperFocalAreaInteractor, Continuation continuation) {
        super(5, continuation);
        this.this$0 = wallpaperFocalAreaInteractor;
    }

    @Override // kotlin.jvm.functions.Function5
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        float floatValue = ((Number) obj2).floatValue();
        float floatValue2 = ((Number) obj3).floatValue();
        float floatValue3 = ((Number) obj4).floatValue();
        WallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1 wallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1 = new WallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1(this.this$0, (Continuation) obj5);
        wallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1.Z$0 = booleanValue;
        wallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1.F$0 = floatValue;
        wallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1.F$1 = floatValue2;
        wallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1.F$2 = floatValue3;
        return wallpaperFocalAreaInteractor$wallpaperFocalAreaBounds$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Pair pair;
        float f;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        float f2 = this.F$0;
        float f3 = this.F$1;
        float f4 = this.F$2;
        WallpaperFocalAreaInteractor.Companion companion = WallpaperFocalAreaInteractor.Companion;
        Context context = this.this$0.context;
        companion.getClass();
        float f5 = context.getResources().getFloat(Resources.getSystem().getIdentifier("config_wallpaperMaxScale", "dimen", "android"));
        if (f5 == 0.0f) {
            f5 = 1.0f;
        }
        RectF rectF = new RectF(0.0f, 0.0f, this.this$0.context.getResources().getDisplayMetrics().widthPixels, this.this$0.context.getResources().getDisplayMetrics().heightPixels);
        RectF rectF2 = new RectF(rectF.centerX() - ((rectF.width() / 2.0f) / f5), rectF.centerY() - ((rectF.height() / 2.0f) / f5), ((rectF.width() / 2.0f) / f5) + rectF.centerX(), ((rectF.height() / 2.0f) / f5) + rectF.centerY());
        float m = ActionRow$$ExternalSyntheticOutline0.m(this.this$0.context, 1, this.this$0.context.getResources().getBoolean(R.bool.center_align_focal_area_shape) ? 500 : 400);
        if (z) {
            float f6 = m / 2.0f;
            pair = new Pair(new Float(rectF2.centerX() - f6), new Float(rectF2.centerX() + f6));
        } else {
            float min = Math.min(rectF2.width(), m) / 2.0f;
            pair = new Pair(new Float(rectF2.centerX() - min), new Float(rectF2.centerX() + min));
        }
        float floatValue = ((Number) pair.component1()).floatValue();
        float floatValue2 = ((Number) pair.component2()).floatValue();
        float f7 = (this.this$0.context.getResources().getDisplayMetrics().heightPixels - f3) / f5;
        if (this.this$0.context.getResources().getBoolean(R.bool.center_align_focal_area_shape)) {
            f = rectF2.top + f7;
        } else if (z) {
            f = rectF2.top + (f4 / f5);
        } else {
            float f8 = rectF2.top;
            if (f4 > f2) {
                f2 = f4;
            }
            f = f8 + (f2 / f5);
        }
        RectF rectF3 = new RectF(floatValue, f, floatValue2, rectF2.bottom - f7);
        Log.d(WallpaperFocalAreaInteractor.TAG, "Focal area changes to " + rectF3);
        return rectF3;
    }
}
