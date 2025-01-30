package com.android.systemui.media.controls.p010ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.media.SecPlayerViewHolder;
import com.android.systemui.media.audiovisseekbar.AudioVisSeekBarProgressDrawable;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.util.ColorUtilKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SecColorSchemeTransition {
    public final SecAnimatingColorTransition bgGradientEnd;
    public final SecAnimatingColorTransition bgGradientStart;
    public final SecAnimatingColorTransition[] colorTransitions;
    public final Context context;
    public boolean isGradientEnabled;
    public final SecPlayerViewHolder mediaViewHolder;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.media.controls.ui.SecColorSchemeTransition$1 */
    public /* synthetic */ class C18071 extends FunctionReferenceImpl implements Function3 {
        public static final C18071 INSTANCE = new C18071();

        public C18071() {
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
        this.isGradientEnabled = true;
        int color = context.getColor(R.color.material_dynamic_secondary95);
        SecAnimatingColorTransition secAnimatingColorTransition = (SecAnimatingColorTransition) function3.invoke(Integer.valueOf(color), SecColorSchemeTransition$surfaceColor$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$surfaceColor$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SecColorSchemeTransition.this.mediaViewHolder.getAlbumView().setBackgroundTintList(ColorStateList.valueOf(((Number) obj).intValue()));
                return Unit.INSTANCE;
            }
        });
        SecAnimatingColorTransition secAnimatingColorTransition2 = (SecAnimatingColorTransition) function3.invoke(Integer.valueOf(Utils.getColorAttr(android.R.attr.textColorPrimary, context).getDefaultColor()), SecColorSchemeTransition$accentPrimary$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$accentPrimary$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int intValue = ((Number) obj).intValue();
                ColorStateList.valueOf(intValue);
                if (SecColorSchemeTransition.this.mediaViewHolder.getSeekBar().getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
                    ((AudioVisSeekBarProgressDrawable) SecColorSchemeTransition.this.mediaViewHolder.getSeekBar().getProgressDrawable()).config.primaryColor = intValue;
                    SecPlayerViewHolder secPlayerViewHolder2 = SecColorSchemeTransition.this.mediaViewHolder;
                    secPlayerViewHolder2.progressBarPrimaryColor = intValue;
                    secPlayerViewHolder2.getSeekBar().getThumb().setColorFilter(intValue, PorterDuff.Mode.MULTIPLY);
                }
                return Unit.INSTANCE;
            }
        });
        SecAnimatingColorTransition secAnimatingColorTransition3 = (SecAnimatingColorTransition) function3.invoke(Integer.valueOf(Utils.getColorAttr(android.R.attr.textColorPrimary, context).getDefaultColor()), SecColorSchemeTransition$accentSecondary$1.INSTANCE, new Function1() { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$accentSecondary$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                Drawable drawable;
                int intValue = ((Number) obj).intValue();
                ColorStateList valueOf = ColorStateList.valueOf(intValue);
                if (SecColorSchemeTransition.this.mediaViewHolder.getSeekBar().getProgressDrawable() instanceof AudioVisSeekBarProgressDrawable) {
                    ((AudioVisSeekBarProgressDrawable) SecColorSchemeTransition.this.mediaViewHolder.getSeekBar().getProgressDrawable()).config.secondaryColor = intValue;
                    SecColorSchemeTransition.this.mediaViewHolder.progressBarSecondaryColor = intValue;
                } else {
                    SecColorSchemeTransition.this.mediaViewHolder.getSeekBar().setProgressTintList(valueOf);
                }
                LayerDrawable layerDrawable = SecColorSchemeTransition.this.mediaViewHolder.dummyProgressDrawable;
                if (layerDrawable != null && (drawable = layerDrawable.getDrawable(2)) != null) {
                    drawable.setColorFilter(intValue, PorterDuff.Mode.SRC_ATOP);
                }
                return Unit.INSTANCE;
            }
        });
        Integer valueOf = Integer.valueOf(color);
        final SecColorSchemeTransition$bgGradientStart$1 secColorSchemeTransition$bgGradientStart$1 = SecColorSchemeTransition$bgGradientStart$1.INSTANCE;
        final float f = 0.45f;
        SecAnimatingColorTransition secAnimatingColorTransition4 = (SecAnimatingColorTransition) function3.invoke(valueOf, new Function1() { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$albumGradientPicker$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i;
                ColorScheme colorScheme = (ColorScheme) obj;
                if (SecColorSchemeTransition.this.isGradientEnabled) {
                    int intValue = ((Number) secColorSchemeTransition$bgGradientStart$1.invoke(colorScheme)).intValue();
                    float[] fArr = new float[3];
                    Color.colorToHSV(intValue, fArr);
                    if (fArr[2] > 0.2f) {
                        fArr[2] = 0.2f;
                        intValue = Color.HSVToColor(fArr);
                    }
                    i = ColorUtilKt.getColorWithAlpha(f, intValue);
                } else {
                    i = 0;
                }
                return Integer.valueOf(i);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$bgGradientStart$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Number) obj).intValue();
                SecColorSchemeTransition.access$updateAlbumGradient(SecColorSchemeTransition.this);
                return Unit.INSTANCE;
            }
        });
        this.bgGradientStart = secAnimatingColorTransition4;
        Integer valueOf2 = Integer.valueOf(color);
        final SecColorSchemeTransition$bgGradientEnd$1 secColorSchemeTransition$bgGradientEnd$1 = SecColorSchemeTransition$bgGradientEnd$1.INSTANCE;
        final float f2 = 1.0f;
        SecAnimatingColorTransition secAnimatingColorTransition5 = (SecAnimatingColorTransition) function3.invoke(valueOf2, new Function1() { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$albumGradientPicker$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                int i;
                ColorScheme colorScheme = (ColorScheme) obj;
                if (SecColorSchemeTransition.this.isGradientEnabled) {
                    int intValue = ((Number) secColorSchemeTransition$bgGradientEnd$1.invoke(colorScheme)).intValue();
                    float[] fArr = new float[3];
                    Color.colorToHSV(intValue, fArr);
                    if (fArr[2] > 0.2f) {
                        fArr[2] = 0.2f;
                        intValue = Color.HSVToColor(fArr);
                    }
                    i = ColorUtilKt.getColorWithAlpha(f2, intValue);
                } else {
                    i = 0;
                }
                return Integer.valueOf(i);
            }
        }, new Function1() { // from class: com.android.systemui.media.controls.ui.SecColorSchemeTransition$bgGradientEnd$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Number) obj).intValue();
                SecColorSchemeTransition.access$updateAlbumGradient(SecColorSchemeTransition.this);
                return Unit.INSTANCE;
            }
        });
        this.bgGradientEnd = secAnimatingColorTransition5;
        this.colorTransitions = new SecAnimatingColorTransition[]{secAnimatingColorTransition, secAnimatingColorTransition2, secAnimatingColorTransition3, secAnimatingColorTransition4, secAnimatingColorTransition5};
    }

    public static final void access$updateAlbumGradient(SecColorSchemeTransition secColorSchemeTransition) {
        Drawable foreground = secColorSchemeTransition.mediaViewHolder.getAlbumView().getForeground();
        Drawable mutate = foreground != null ? foreground.mutate() : null;
        if (mutate instanceof GradientDrawable) {
            GradientDrawable gradientDrawable = (GradientDrawable) mutate;
            int[] iArr = new int[2];
            SecAnimatingColorTransition secAnimatingColorTransition = secColorSchemeTransition.bgGradientStart;
            iArr[0] = secAnimatingColorTransition != null ? secAnimatingColorTransition.currentColor : 0;
            SecAnimatingColorTransition secAnimatingColorTransition2 = secColorSchemeTransition.bgGradientEnd;
            iArr[1] = secAnimatingColorTransition2 != null ? secAnimatingColorTransition2.currentColor : 0;
            gradientDrawable.setColors(iArr);
        }
    }

    public SecColorSchemeTransition(Context context, SecPlayerViewHolder secPlayerViewHolder) {
        this(context, secPlayerViewHolder, C18071.INSTANCE);
    }
}
