package com.airbnb.lottie.model;

import android.graphics.PointF;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DocumentData {
    public float baselineShift;
    public PointF boxPosition;
    public PointF boxSize;
    public int color;
    public String fontName;
    public Justification justification;
    public float lineHeight;
    public float size;
    public int strokeColor;
    public boolean strokeOverFill;
    public float strokeWidth;
    public String text;
    public int tracking;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Justification {
        LEFT_ALIGN,
        RIGHT_ALIGN,
        CENTER
    }

    public DocumentData(String str, String str2, float f, Justification justification, int i, float f2, float f3, int i2, int i3, float f4, boolean z, PointF pointF, PointF pointF2) {
        this.text = str;
        this.fontName = str2;
        this.size = f;
        this.justification = justification;
        this.tracking = i;
        this.lineHeight = f2;
        this.baselineShift = f3;
        this.color = i2;
        this.strokeColor = i3;
        this.strokeWidth = f4;
        this.strokeOverFill = z;
        this.boxPosition = pointF;
        this.boxSize = pointF2;
    }

    public final int hashCode() {
        int ordinal = ((this.justification.ordinal() + (((int) (AppInfo$$ExternalSyntheticOutline0.m41m(this.fontName, this.text.hashCode() * 31, 31) + this.size)) * 31)) * 31) + this.tracking;
        long floatToRawIntBits = Float.floatToRawIntBits(this.lineHeight);
        return (((ordinal * 31) + ((int) (floatToRawIntBits ^ (floatToRawIntBits >>> 32)))) * 31) + this.color;
    }

    public DocumentData() {
    }
}
