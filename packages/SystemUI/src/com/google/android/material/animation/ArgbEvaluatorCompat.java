package com.google.android.material.animation;

import android.animation.TypeEvaluator;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class ArgbEvaluatorCompat implements TypeEvaluator {
    public static final ArgbEvaluatorCompat instance = new ArgbEvaluatorCompat();

    @Override // android.animation.TypeEvaluator
    public final /* bridge */ /* synthetic */ Object evaluate(float f, Object obj, Object obj2) {
        return evaluate(f, (Integer) obj, (Integer) obj2);
    }

    public static Integer evaluate(float f, Integer num, Integer num2) {
        int iIntValue = num.intValue();
        float f2 = ((iIntValue >> 24) & 255) / 255.0f;
        int iIntValue2 = num2.intValue();
        float f3 = ((iIntValue2 >> 24) & 255) / 255.0f;
        float fPow = (float) Math.pow(((iIntValue >> 16) & 255) / 255.0f, 2.2d);
        float fPow2 = (float) Math.pow(((iIntValue >> 8) & 255) / 255.0f, 2.2d);
        float fPow3 = (float) Math.pow((iIntValue & 255) / 255.0f, 2.2d);
        float fPow4 = (float) Math.pow(((iIntValue2 >> 16) & 255) / 255.0f, 2.2d);
        float fPow5 = (float) Math.pow(((iIntValue2 >> 8) & 255) / 255.0f, 2.2d);
        float fPow6 = (float) Math.pow((iIntValue2 & 255) / 255.0f, 2.2d);
        float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f3, f2, f, f2);
        float fM$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(fPow4, fPow, f, fPow);
        float fM$13 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(fPow5, fPow2, f, fPow2);
        float fM$14 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(fPow6, fPow3, f, fPow3);
        float fPow7 = ((float) Math.pow(fM$12, 0.45454545454545453d)) * 255.0f;
        float fPow8 = ((float) Math.pow(fM$13, 0.45454545454545453d)) * 255.0f;
        return Integer.valueOf(Math.round(((float) Math.pow(fM$14, 0.45454545454545453d)) * 255.0f) | (Math.round(fPow7) << 16) | (Math.round(fM$1 * 255.0f) << 24) | (Math.round(fPow8) << 8));
    }
}
