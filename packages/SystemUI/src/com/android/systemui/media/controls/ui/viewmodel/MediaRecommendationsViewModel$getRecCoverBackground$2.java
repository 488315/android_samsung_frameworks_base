package com.android.systemui.media.controls.ui.viewmodel;

import android.app.WallpaperColors;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.appcompat.content.res.AppCompatResources;
import com.android.systemui.R;
import com.android.systemui.media.controls.ui.util.MediaArtworkHelper;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.util.ColorUtilKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

final class MediaRecommendationsViewModel$getRecCoverBackground$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $height;
    final /* synthetic */ Icon $icon;
    final /* synthetic */ int $width;
    int label;
    final /* synthetic */ MediaRecommendationsViewModel this$0;

    public MediaRecommendationsViewModel$getRecCoverBackground$2(MediaRecommendationsViewModel mediaRecommendationsViewModel, Icon icon, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaRecommendationsViewModel;
        this.$icon = icon;
        this.$width = i;
        this.$height = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaRecommendationsViewModel$getRecCoverBackground$2(this.this$0, this.$icon, this.$width, this.$height, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaRecommendationsViewModel$getRecCoverBackground$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Bitmap bitmap;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MediaArtworkHelper mediaArtworkHelper = MediaArtworkHelper.INSTANCE;
            MediaRecommendationsViewModel mediaRecommendationsViewModel = this.this$0;
            Context context = mediaRecommendationsViewModel.applicationContext;
            Icon icon = this.$icon;
            this.label = 1;
            mediaArtworkHelper.getClass();
            obj = MediaArtworkHelper.getWallpaperColor(context, mediaRecommendationsViewModel.backgroundDispatcher, icon, "MediaRecommendationsViewModel", this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        WallpaperColors wallpaperColors = (WallpaperColors) obj;
        if (wallpaperColors == null) {
            return new ColorDrawable(0);
        }
        MediaRecommendationsViewModel mediaRecommendationsViewModel2 = this.this$0;
        Icon icon2 = this.$icon;
        int i2 = this.$width;
        int i3 = this.$height;
        Intrinsics.checkNotNull(icon2);
        ColorScheme colorScheme = new ColorScheme(wallpaperColors, true, Style.CONTENT);
        int i4 = MediaRecommendationsViewModel.$r8$clinit;
        mediaRecommendationsViewModel2.getClass();
        if (i2 <= 0) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "Width must be a positive number but was ").toString());
        }
        if (i3 <= 0) {
            throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i3, "Height must be a positive number but was ").toString());
        }
        Drawable bitmapDrawable = ((icon2.getType() == 1 || icon2.getType() == 5) && (bitmap = icon2.getBitmap()) != null) ? new BitmapDrawable(mediaRecommendationsViewModel2.applicationContext.getResources(), Bitmap.createScaledBitmap(bitmap, i2, i3, false)) : null;
        if (bitmapDrawable == null) {
            MediaArtworkHelper mediaArtworkHelper2 = MediaArtworkHelper.INSTANCE;
            Context context2 = mediaRecommendationsViewModel2.applicationContext;
            mediaArtworkHelper2.getClass();
            bitmapDrawable = icon2.loadDrawable(context2);
            Rect rect = new Rect(0, 0, i2, i3);
            if (rect.width() > i2 || rect.height() > i3) {
                rect.offset(-((int) ((rect.width() - i2) / 2.0f)), -((int) ((rect.height() - i3) / 2.0f)));
            }
            if (bitmapDrawable != null) {
                bitmapDrawable.setBounds(rect);
            }
        }
        Drawable drawable = AppCompatResources.getDrawable(R.drawable.qs_media_rec_scrim, mediaRecommendationsViewModel2.applicationContext);
        GradientDrawable gradientDrawable = (GradientDrawable) (drawable != null ? drawable.mutate() : null);
        MediaArtworkHelper.INSTANCE.getClass();
        gradientDrawable.setColors(new int[]{ColorUtilKt.getColorWithAlpha(colorScheme.mAccent2.getS700(), 0.15f), ColorUtilKt.getColorWithAlpha(colorScheme.mAccent1.getS700(), 1.0f)});
        return new LayerDrawable(new Drawable[]{bitmapDrawable, gradientDrawable});
    }
}
