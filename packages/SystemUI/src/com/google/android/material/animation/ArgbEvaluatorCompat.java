package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ArgbEvaluatorCompat implements TypeEvaluator {
    public static final ArgbEvaluatorCompat instance = new ArgbEvaluatorCompat();

    @Override // android.animation.TypeEvaluator
    public final /* bridge */ /* synthetic */ Object evaluate(float f, Object obj, Object obj2) {
        return evaluate(f, (Integer) obj, (Integer) obj2);
    }

    public static Integer evaluate(float f, Integer num, Integer num2) {
        int intValue = num.intValue();
        float f2 = ((intValue >> 24) & 255) / 255.0f;
        int intValue2 = num2.intValue();
        float f3 = ((intValue2 >> 24) & 255) / 255.0f;
        float pow = (float) Math.pow(((intValue >> 16) & 255) / 255.0f, 2.2d);
        float pow2 = (float) Math.pow(((intValue >> 8) & 255) / 255.0f, 2.2d);
        float pow3 = (float) Math.pow((intValue & 255) / 255.0f, 2.2d);
        float pow4 = (float) Math.pow(((intValue2 >> 16) & 255) / 255.0f, 2.2d);
        float pow5 = (float) Math.pow(((intValue2 >> 8) & 255) / 255.0f, 2.2d);
        float pow6 = (float) Math.pow((intValue2 & 255) / 255.0f, 2.2d);
        float m$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f3, f2, f, f2);
        float m$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pow4, pow, f, pow);
        float m$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pow5, pow2, f, pow2);
        float m$14 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pow6, pow3, f, pow3);
        float pow7 = ((float) Math.pow(m$12, 0.45454545454545453d)) * 255.0f;
        float pow8 = ((float) Math.pow(m$13, 0.45454545454545453d)) * 255.0f;
        return Integer.valueOf(Math.round(((float) Math.pow(m$14, 0.45454545454545453d)) * 255.0f) | (Math.round(pow7) << 16) | (Math.round(m$1 * 255.0f) << 24) | (Math.round(pow8) << 8));
    }
}
