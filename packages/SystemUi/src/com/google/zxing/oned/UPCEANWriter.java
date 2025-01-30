package com.google.zxing.oned;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class UPCEANWriter extends OneDimensionalCodeWriter {
    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public final int getDefaultMargin() {
        int[] iArr = UPCEANReader.START_END_PATTERN;
        return 3;
    }
}
