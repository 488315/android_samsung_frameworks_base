package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.media.SecPlayerViewHolder;
import com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.util.ColorUtilKt;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecColorSchemeTransition {
    public final boolean DEBUG;
    public final String TAG;
    public final int bgColor;
    public final SecAnimatingColorTransition bgGradientEnd;
    public final SecAnimatingColorTransition bgGradientStart;
    public final SecAnimatingColorTransition[] colorTransitions;
    public final Context context;
    public boolean isGradientEnabled;
    public final SecPlayerViewHolder mediaViewHolder;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.media.controls.ui.SecColorSchemeTransition$1, reason: invalid class name */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function3 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(3, SecAnimatingColorTransition.class, "<init>", "<init>(ILkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", 0);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return new SecAnimatingColorTransition(((Number) obj).intValue(), (Function1) obj2, (Function1) obj3);
        }
    }

    public SecColorSchemeTransition(Context context, SecPlayerViewHolder secPlayerViewHolder, Function3 function3) {
        this.context = context;
        this.mediaViewHolder = secPlayerViewHolder;
        this.TAG = Reflection.getOrCreateKotlinClass(SecColorSchemeTransition.class).getSimpleName();
        this.DEBUG = true;
        this.isGradientEnabled = true;
        int color = context.getColor(R.color.material_dynamic_neutral20);
        this.bgColor = color;
        final int i = 0;
        SecAnimatingColorTransition secAnimatingColorTransition = (SecAnimatingColorTransition) function3.invoke(Integer.valueOf(color), SecColorSchemeTransition$surfaceColor$1.INSTANCE, new Function1(this) { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$$ExternalSyntheticLambda0
            public final /* synthetic */ SecColorSchemeTransition f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Drawable drawable;
                Integer num = (Integer) obj;
                switch (i) {
                    case 0:
                        ColorStateList valueOf = ColorStateList.valueOf(num.intValue());
                        ImageView imageView = this.f$0.mediaViewHolder.albumView;
                        if (imageView == null) {
                            imageView = null;
                        }
                        imageView.setBackgroundTintList(valueOf);
                        break;
                    case 1:
                        int intValue = num.intValue();
                        ColorStateList.valueOf(intValue);
                        SecColorSchemeTransition secColorSchemeTransition = this.f$0;
                        SeekBar seekBar = secColorSchemeTransition.mediaViewHolder.seekBar;
                        if (seekBar == null) {
                            seekBar = null;
                        }
                        if (seekBar.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
                            SecPlayerViewHolder secPlayerViewHolder2 = secColorSchemeTransition.mediaViewHolder;
                            SeekBar seekBar2 = secPlayerViewHolder2.seekBar;
                            if (seekBar2 == null) {
                                seekBar2 = null;
                            }
                            ((AudioVisSeekBarProgressDrawable) seekBar2.getProgressDrawable()).config.primaryColor = intValue;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarPrimaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder2.progressBarPrimaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder2.progressBarPrimaryColor = intValue;
                            SeekBar seekBar3 = secPlayerViewHolder2.seekBar;
                            (seekBar3 != null ? seekBar3 : null).getThumb().setColorFilter(intValue, PorterDuff.Mode.MULTIPLY);
                        }
                        break;
                    case 2:
                        int intValue2 = num.intValue();
                        ColorStateList valueOf2 = ColorStateList.valueOf(intValue2);
                        SecColorSchemeTransition secColorSchemeTransition2 = this.f$0;
                        SeekBar seekBar4 = secColorSchemeTransition2.mediaViewHolder.seekBar;
                        if (seekBar4 == null) {
                            seekBar4 = null;
                        }
                        boolean z = seekBar4.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable;
                        SecPlayerViewHolder secPlayerViewHolder3 = secColorSchemeTransition2.mediaViewHolder;
                        if (z) {
                            SeekBar seekBar5 = secPlayerViewHolder3.seekBar;
                            ((AudioVisSeekBarProgressDrawable) (seekBar5 != null ? seekBar5 : null).getProgressDrawable()).config.secondaryColor = intValue2;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarSecondaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder3.progressBarSecondaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder3.progressBarSecondaryColor = intValue2;
                        } else {
                            SeekBar seekBar6 = secPlayerViewHolder3.seekBar;
                            (seekBar6 != null ? seekBar6 : null).setProgressTintList(valueOf2);
                        }
                        LayerDrawable layerDrawable = secPlayerViewHolder3.dummyProgressDrawable;
                        if (layerDrawable != null && (drawable = layerDrawable.getDrawable(2)) != null) {
                            drawable.setColorFilter(intValue2, PorterDuff.Mode.SRC_ATOP);
                        }
                        break;
                    case 3:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                    default:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i2 = 1;
        SecAnimatingColorTransition secAnimatingColorTransition2 = (SecAnimatingColorTransition) function3.invoke(Integer.valueOf(Utils.getColorAttr(android.R.attr.textColorPrimary, context).getDefaultColor()), SecColorSchemeTransition$accentPrimary$1.INSTANCE, new Function1(this) { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$$ExternalSyntheticLambda0
            public final /* synthetic */ SecColorSchemeTransition f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Drawable drawable;
                Integer num = (Integer) obj;
                switch (i2) {
                    case 0:
                        ColorStateList valueOf = ColorStateList.valueOf(num.intValue());
                        ImageView imageView = this.f$0.mediaViewHolder.albumView;
                        if (imageView == null) {
                            imageView = null;
                        }
                        imageView.setBackgroundTintList(valueOf);
                        break;
                    case 1:
                        int intValue = num.intValue();
                        ColorStateList.valueOf(intValue);
                        SecColorSchemeTransition secColorSchemeTransition = this.f$0;
                        SeekBar seekBar = secColorSchemeTransition.mediaViewHolder.seekBar;
                        if (seekBar == null) {
                            seekBar = null;
                        }
                        if (seekBar.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
                            SecPlayerViewHolder secPlayerViewHolder2 = secColorSchemeTransition.mediaViewHolder;
                            SeekBar seekBar2 = secPlayerViewHolder2.seekBar;
                            if (seekBar2 == null) {
                                seekBar2 = null;
                            }
                            ((AudioVisSeekBarProgressDrawable) seekBar2.getProgressDrawable()).config.primaryColor = intValue;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarPrimaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder2.progressBarPrimaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder2.progressBarPrimaryColor = intValue;
                            SeekBar seekBar3 = secPlayerViewHolder2.seekBar;
                            (seekBar3 != null ? seekBar3 : null).getThumb().setColorFilter(intValue, PorterDuff.Mode.MULTIPLY);
                        }
                        break;
                    case 2:
                        int intValue2 = num.intValue();
                        ColorStateList valueOf2 = ColorStateList.valueOf(intValue2);
                        SecColorSchemeTransition secColorSchemeTransition2 = this.f$0;
                        SeekBar seekBar4 = secColorSchemeTransition2.mediaViewHolder.seekBar;
                        if (seekBar4 == null) {
                            seekBar4 = null;
                        }
                        boolean z = seekBar4.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable;
                        SecPlayerViewHolder secPlayerViewHolder3 = secColorSchemeTransition2.mediaViewHolder;
                        if (z) {
                            SeekBar seekBar5 = secPlayerViewHolder3.seekBar;
                            ((AudioVisSeekBarProgressDrawable) (seekBar5 != null ? seekBar5 : null).getProgressDrawable()).config.secondaryColor = intValue2;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarSecondaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder3.progressBarSecondaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder3.progressBarSecondaryColor = intValue2;
                        } else {
                            SeekBar seekBar6 = secPlayerViewHolder3.seekBar;
                            (seekBar6 != null ? seekBar6 : null).setProgressTintList(valueOf2);
                        }
                        LayerDrawable layerDrawable = secPlayerViewHolder3.dummyProgressDrawable;
                        if (layerDrawable != null && (drawable = layerDrawable.getDrawable(2)) != null) {
                            drawable.setColorFilter(intValue2, PorterDuff.Mode.SRC_ATOP);
                        }
                        break;
                    case 3:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                    default:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        final int i3 = 2;
        SecAnimatingColorTransition secAnimatingColorTransition3 = (SecAnimatingColorTransition) function3.invoke(Integer.valueOf(Utils.getColorAttr(android.R.attr.textColorPrimary, context).getDefaultColor()), SecColorSchemeTransition$accentSecondary$1.INSTANCE, new Function1(this) { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$$ExternalSyntheticLambda0
            public final /* synthetic */ SecColorSchemeTransition f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Drawable drawable;
                Integer num = (Integer) obj;
                switch (i3) {
                    case 0:
                        ColorStateList valueOf = ColorStateList.valueOf(num.intValue());
                        ImageView imageView = this.f$0.mediaViewHolder.albumView;
                        if (imageView == null) {
                            imageView = null;
                        }
                        imageView.setBackgroundTintList(valueOf);
                        break;
                    case 1:
                        int intValue = num.intValue();
                        ColorStateList.valueOf(intValue);
                        SecColorSchemeTransition secColorSchemeTransition = this.f$0;
                        SeekBar seekBar = secColorSchemeTransition.mediaViewHolder.seekBar;
                        if (seekBar == null) {
                            seekBar = null;
                        }
                        if (seekBar.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
                            SecPlayerViewHolder secPlayerViewHolder2 = secColorSchemeTransition.mediaViewHolder;
                            SeekBar seekBar2 = secPlayerViewHolder2.seekBar;
                            if (seekBar2 == null) {
                                seekBar2 = null;
                            }
                            ((AudioVisSeekBarProgressDrawable) seekBar2.getProgressDrawable()).config.primaryColor = intValue;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarPrimaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder2.progressBarPrimaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder2.progressBarPrimaryColor = intValue;
                            SeekBar seekBar3 = secPlayerViewHolder2.seekBar;
                            (seekBar3 != null ? seekBar3 : null).getThumb().setColorFilter(intValue, PorterDuff.Mode.MULTIPLY);
                        }
                        break;
                    case 2:
                        int intValue2 = num.intValue();
                        ColorStateList valueOf2 = ColorStateList.valueOf(intValue2);
                        SecColorSchemeTransition secColorSchemeTransition2 = this.f$0;
                        SeekBar seekBar4 = secColorSchemeTransition2.mediaViewHolder.seekBar;
                        if (seekBar4 == null) {
                            seekBar4 = null;
                        }
                        boolean z = seekBar4.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable;
                        SecPlayerViewHolder secPlayerViewHolder3 = secColorSchemeTransition2.mediaViewHolder;
                        if (z) {
                            SeekBar seekBar5 = secPlayerViewHolder3.seekBar;
                            ((AudioVisSeekBarProgressDrawable) (seekBar5 != null ? seekBar5 : null).getProgressDrawable()).config.secondaryColor = intValue2;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarSecondaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder3.progressBarSecondaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder3.progressBarSecondaryColor = intValue2;
                        } else {
                            SeekBar seekBar6 = secPlayerViewHolder3.seekBar;
                            (seekBar6 != null ? seekBar6 : null).setProgressTintList(valueOf2);
                        }
                        LayerDrawable layerDrawable = secPlayerViewHolder3.dummyProgressDrawable;
                        if (layerDrawable != null && (drawable = layerDrawable.getDrawable(2)) != null) {
                            drawable.setColorFilter(intValue2, PorterDuff.Mode.SRC_ATOP);
                        }
                        break;
                    case 3:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                    default:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        Integer valueOf = Integer.valueOf(color);
        final SecColorSchemeTransition$bgGradientStart$1 secColorSchemeTransition$bgGradientStart$1 = SecColorSchemeTransition$bgGradientStart$1.INSTANCE;
        final float f = 0.45f;
        Function1 function1 = new Function1(secColorSchemeTransition$bgGradientStart$1, f) { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$$ExternalSyntheticLambda5
            public final /* synthetic */ FunctionReferenceImpl f$1;
            public final /* synthetic */ float f$2;

            /* JADX WARN: Multi-variable type inference failed */
            {
                this.f$1 = (FunctionReferenceImpl) secColorSchemeTransition$bgGradientStart$1;
                this.f$2 = f;
            }

            /* JADX WARN: Type inference failed for: r0v2, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.FunctionReferenceImpl] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i4;
                ColorScheme colorScheme = (ColorScheme) obj;
                if (SecColorSchemeTransition.this.isGradientEnabled) {
                    int intValue = ((Number) this.f$1.mo779invoke(colorScheme)).intValue();
                    float[] fArr = new float[3];
                    Color.colorToHSV(intValue, fArr);
                    if (fArr[2] > 0.2f) {
                        fArr[2] = 0.2f;
                        intValue = Color.HSVToColor(fArr);
                    }
                    i4 = ColorUtilKt.getColorWithAlpha(intValue, this.f$2);
                } else {
                    i4 = 0;
                }
                return Integer.valueOf(i4);
            }
        };
        final int i4 = 3;
        SecAnimatingColorTransition secAnimatingColorTransition4 = (SecAnimatingColorTransition) function3.invoke(valueOf, function1, new Function1(this) { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$$ExternalSyntheticLambda0
            public final /* synthetic */ SecColorSchemeTransition f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Drawable drawable;
                Integer num = (Integer) obj;
                switch (i4) {
                    case 0:
                        ColorStateList valueOf2 = ColorStateList.valueOf(num.intValue());
                        ImageView imageView = this.f$0.mediaViewHolder.albumView;
                        if (imageView == null) {
                            imageView = null;
                        }
                        imageView.setBackgroundTintList(valueOf2);
                        break;
                    case 1:
                        int intValue = num.intValue();
                        ColorStateList.valueOf(intValue);
                        SecColorSchemeTransition secColorSchemeTransition = this.f$0;
                        SeekBar seekBar = secColorSchemeTransition.mediaViewHolder.seekBar;
                        if (seekBar == null) {
                            seekBar = null;
                        }
                        if (seekBar.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
                            SecPlayerViewHolder secPlayerViewHolder2 = secColorSchemeTransition.mediaViewHolder;
                            SeekBar seekBar2 = secPlayerViewHolder2.seekBar;
                            if (seekBar2 == null) {
                                seekBar2 = null;
                            }
                            ((AudioVisSeekBarProgressDrawable) seekBar2.getProgressDrawable()).config.primaryColor = intValue;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarPrimaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder2.progressBarPrimaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder2.progressBarPrimaryColor = intValue;
                            SeekBar seekBar3 = secPlayerViewHolder2.seekBar;
                            (seekBar3 != null ? seekBar3 : null).getThumb().setColorFilter(intValue, PorterDuff.Mode.MULTIPLY);
                        }
                        break;
                    case 2:
                        int intValue2 = num.intValue();
                        ColorStateList valueOf22 = ColorStateList.valueOf(intValue2);
                        SecColorSchemeTransition secColorSchemeTransition2 = this.f$0;
                        SeekBar seekBar4 = secColorSchemeTransition2.mediaViewHolder.seekBar;
                        if (seekBar4 == null) {
                            seekBar4 = null;
                        }
                        boolean z = seekBar4.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable;
                        SecPlayerViewHolder secPlayerViewHolder3 = secColorSchemeTransition2.mediaViewHolder;
                        if (z) {
                            SeekBar seekBar5 = secPlayerViewHolder3.seekBar;
                            ((AudioVisSeekBarProgressDrawable) (seekBar5 != null ? seekBar5 : null).getProgressDrawable()).config.secondaryColor = intValue2;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarSecondaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder3.progressBarSecondaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder3.progressBarSecondaryColor = intValue2;
                        } else {
                            SeekBar seekBar6 = secPlayerViewHolder3.seekBar;
                            (seekBar6 != null ? seekBar6 : null).setProgressTintList(valueOf22);
                        }
                        LayerDrawable layerDrawable = secPlayerViewHolder3.dummyProgressDrawable;
                        if (layerDrawable != null && (drawable = layerDrawable.getDrawable(2)) != null) {
                            drawable.setColorFilter(intValue2, PorterDuff.Mode.SRC_ATOP);
                        }
                        break;
                    case 3:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                    default:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.bgGradientStart = secAnimatingColorTransition4;
        Integer valueOf2 = Integer.valueOf(color);
        final SecColorSchemeTransition$bgGradientEnd$1 secColorSchemeTransition$bgGradientEnd$1 = SecColorSchemeTransition$bgGradientEnd$1.INSTANCE;
        final float f2 = 1.0f;
        Function1 function12 = new Function1(secColorSchemeTransition$bgGradientEnd$1, f2) { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$$ExternalSyntheticLambda5
            public final /* synthetic */ FunctionReferenceImpl f$1;
            public final /* synthetic */ float f$2;

            /* JADX WARN: Multi-variable type inference failed */
            {
                this.f$1 = (FunctionReferenceImpl) secColorSchemeTransition$bgGradientEnd$1;
                this.f$2 = f2;
            }

            /* JADX WARN: Type inference failed for: r0v2, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.FunctionReferenceImpl] */
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int i42;
                ColorScheme colorScheme = (ColorScheme) obj;
                if (SecColorSchemeTransition.this.isGradientEnabled) {
                    int intValue = ((Number) this.f$1.mo779invoke(colorScheme)).intValue();
                    float[] fArr = new float[3];
                    Color.colorToHSV(intValue, fArr);
                    if (fArr[2] > 0.2f) {
                        fArr[2] = 0.2f;
                        intValue = Color.HSVToColor(fArr);
                    }
                    i42 = ColorUtilKt.getColorWithAlpha(intValue, this.f$2);
                } else {
                    i42 = 0;
                }
                return Integer.valueOf(i42);
            }
        };
        final int i5 = 4;
        SecAnimatingColorTransition secAnimatingColorTransition5 = (SecAnimatingColorTransition) function3.invoke(valueOf2, function12, new Function1(this) { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$$ExternalSyntheticLambda0
            public final /* synthetic */ SecColorSchemeTransition f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Drawable drawable;
                Integer num = (Integer) obj;
                switch (i5) {
                    case 0:
                        ColorStateList valueOf22 = ColorStateList.valueOf(num.intValue());
                        ImageView imageView = this.f$0.mediaViewHolder.albumView;
                        if (imageView == null) {
                            imageView = null;
                        }
                        imageView.setBackgroundTintList(valueOf22);
                        break;
                    case 1:
                        int intValue = num.intValue();
                        ColorStateList.valueOf(intValue);
                        SecColorSchemeTransition secColorSchemeTransition = this.f$0;
                        SeekBar seekBar = secColorSchemeTransition.mediaViewHolder.seekBar;
                        if (seekBar == null) {
                            seekBar = null;
                        }
                        if (seekBar.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
                            SecPlayerViewHolder secPlayerViewHolder2 = secColorSchemeTransition.mediaViewHolder;
                            SeekBar seekBar2 = secPlayerViewHolder2.seekBar;
                            if (seekBar2 == null) {
                                seekBar2 = null;
                            }
                            ((AudioVisSeekBarProgressDrawable) seekBar2.getProgressDrawable()).config.primaryColor = intValue;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarPrimaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder2.progressBarPrimaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder2.progressBarPrimaryColor = intValue;
                            SeekBar seekBar3 = secPlayerViewHolder2.seekBar;
                            (seekBar3 != null ? seekBar3 : null).getThumb().setColorFilter(intValue, PorterDuff.Mode.MULTIPLY);
                        }
                        break;
                    case 2:
                        int intValue2 = num.intValue();
                        ColorStateList valueOf222 = ColorStateList.valueOf(intValue2);
                        SecColorSchemeTransition secColorSchemeTransition2 = this.f$0;
                        SeekBar seekBar4 = secColorSchemeTransition2.mediaViewHolder.seekBar;
                        if (seekBar4 == null) {
                            seekBar4 = null;
                        }
                        boolean z = seekBar4.getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable;
                        SecPlayerViewHolder secPlayerViewHolder3 = secColorSchemeTransition2.mediaViewHolder;
                        if (z) {
                            SeekBar seekBar5 = secPlayerViewHolder3.seekBar;
                            ((AudioVisSeekBarProgressDrawable) (seekBar5 != null ? seekBar5 : null).getProgressDrawable()).config.secondaryColor = intValue2;
                            if (SecPlayerViewHolder.DEBUG) {
                                Log.d(SecPlayerViewHolder.TAG, AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("ColorUpdate progressBarSecondaryColor changed from ", String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secPlayerViewHolder3.progressBarSecondaryColor)}, 1)), " to ", String.format("0x%08X", Arrays.copyOf(new Object[]{num}, 1))));
                            }
                            secPlayerViewHolder3.progressBarSecondaryColor = intValue2;
                        } else {
                            SeekBar seekBar6 = secPlayerViewHolder3.seekBar;
                            (seekBar6 != null ? seekBar6 : null).setProgressTintList(valueOf222);
                        }
                        LayerDrawable layerDrawable = secPlayerViewHolder3.dummyProgressDrawable;
                        if (layerDrawable != null && (drawable = layerDrawable.getDrawable(2)) != null) {
                            drawable.setColorFilter(intValue2, PorterDuff.Mode.SRC_ATOP);
                        }
                        break;
                    case 3:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                    default:
                        num.intValue();
                        this.f$0.updateAlbumGradient();
                        break;
                }
                return Unit.INSTANCE;
            }
        });
        this.bgGradientEnd = secAnimatingColorTransition5;
        this.colorTransitions = new SecAnimatingColorTransition[]{secAnimatingColorTransition, secAnimatingColorTransition2, secAnimatingColorTransition3, secAnimatingColorTransition4, secAnimatingColorTransition5};
    }

    public final void updateAlbumGradient() {
        ImageView imageView = this.mediaViewHolder.albumView;
        if (imageView == null) {
            imageView = null;
        }
        Drawable foreground = imageView.getForeground();
        Drawable mutate = foreground != null ? foreground.mutate() : null;
        if (mutate instanceof GradientDrawable) {
            boolean z = this.DEBUG;
            SecAnimatingColorTransition secAnimatingColorTransition = this.bgGradientEnd;
            SecAnimatingColorTransition secAnimatingColorTransition2 = this.bgGradientStart;
            if (z) {
                String format = String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(this.bgColor)}, 1));
                String format2 = String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secAnimatingColorTransition2 != null ? secAnimatingColorTransition2.currentColor : 0)}, 1));
                String format3 = String.format("0x%08X", Arrays.copyOf(new Object[]{Integer.valueOf(secAnimatingColorTransition != null ? secAnimatingColorTransition.currentColor : 0)}, 1));
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ColorUpdate updateAlbumGradient bgColor ", format, " bgGradientStart ", format2, " bgGradientEnd ");
                m.append(format3);
                Log.d(this.TAG, m.toString());
            }
            ((GradientDrawable) mutate).setColors(new int[]{secAnimatingColorTransition2 != null ? secAnimatingColorTransition2.currentColor : 0, secAnimatingColorTransition != null ? secAnimatingColorTransition.currentColor : 0});
        }
    }

    public SecColorSchemeTransition(Context context, SecPlayerViewHolder secPlayerViewHolder) {
        this(context, secPlayerViewHolder, AnonymousClass1.INSTANCE);
    }
}
