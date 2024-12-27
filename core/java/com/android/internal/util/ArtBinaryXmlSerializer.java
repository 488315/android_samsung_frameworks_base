package com.android.internal.util;

import com.android.modules.utils.BinaryXmlSerializer;
import com.android.modules.utils.FastDataOutput;

import java.io.OutputStream;

public class ArtBinaryXmlSerializer extends BinaryXmlSerializer {
    @Override // com.android.modules.utils.BinaryXmlSerializer
    protected FastDataOutput obtainFastDataOutput(OutputStream os) {
        return ArtFastDataOutput.obtain(os);
    }
}
