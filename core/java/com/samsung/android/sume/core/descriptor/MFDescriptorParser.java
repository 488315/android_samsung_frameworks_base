package com.samsung.android.sume.core.descriptor;

import java.io.InputStream;

/* loaded from: classes6.dex */
public interface MFDescriptorParser {

    public enum Type {
        JSON
    }

    MFDescriptor parse(InputStream inputStream);

    static MFDescriptorParser of(Type type) {
        switch (type) {
            case JSON:
                return new MFDescriptorJsonParser();
            default:
                throw new UnsupportedOperationException("unknown type");
        }
    }
}
