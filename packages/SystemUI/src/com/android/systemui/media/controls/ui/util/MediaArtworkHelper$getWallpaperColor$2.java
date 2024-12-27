package com.android.systemui.media.controls.ui.util;

import android.app.WallpaperColors;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class MediaArtworkHelper$getWallpaperColor$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $applicationContext;
    final /* synthetic */ Icon $artworkIcon;
    final /* synthetic */ String $tag;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaArtworkHelper$getWallpaperColor$2(Icon icon, Context context, String str, Continuation continuation) {
        super(2, continuation);
        this.$artworkIcon = icon;
        this.$applicationContext = context;
        this.$tag = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaArtworkHelper$getWallpaperColor$2(this.$artworkIcon, this.$applicationContext, this.$tag, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaArtworkHelper$getWallpaperColor$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        Icon icon = this.$artworkIcon;
        if (icon == null) {
            return null;
        }
        Context context = this.$applicationContext;
        String str = this.$tag;
        if (icon.getType() != 1 && icon.getType() != 5) {
            Drawable loadDrawable = icon.loadDrawable(context);
            if (loadDrawable != null) {
                return WallpaperColors.fromDrawable(loadDrawable);
            }
            return null;
        }
        Bitmap bitmap = icon.getBitmap();
        if (!bitmap.isRecycled()) {
            return WallpaperColors.fromBitmap(bitmap);
        }
        Log.d(str, "Cannot load wallpaper color from a recycled bitmap");
        return null;
    }
}
