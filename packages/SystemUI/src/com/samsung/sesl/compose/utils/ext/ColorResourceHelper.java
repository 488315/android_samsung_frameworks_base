package com.samsung.sesl.compose.utils.ext;

import android.content.Context;
import android.content.res.Resources;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;

/* loaded from: classes4.dex */
public final class ColorResourceHelper {
    public static final ColorResourceHelper INSTANCE = new ColorResourceHelper();

    private ColorResourceHelper() {
    }

    /* renamed from: getColor-6MYuD4A, reason: not valid java name */
    public final Color m3359getColor6MYuD4A(Context context, int i) {
        try {
            return Color.m456boximpl(ColorKt.Color(context.getResources().getColor(i, context.getTheme())));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ClassCastException e2) {
            e2.printStackTrace();
            return null;
        } catch (Exception e3) {
            e3.printStackTrace();
            return null;
        }
    }
}
