package com.google.zxing.datamatrix.encoder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class DataMatrixSymbolInfo144 extends SymbolInfo {
    public DataMatrixSymbolInfo144() {
        super(false, 1558, 620, 22, 22, 36, -1, 62);
    }

    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public final int getDataLengthForInterleavedBlock(int i) {
        return i <= 8 ? 156 : 155;
    }

    @Override // com.google.zxing.datamatrix.encoder.SymbolInfo
    public final int getInterleavedBlockCount() {
        return 10;
    }
}
