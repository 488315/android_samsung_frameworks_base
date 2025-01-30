package com.android.server.integrity.serializer;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

/* loaded from: classes2.dex */
public interface RuleSerializer {
    void serialize(List list, Optional optional, OutputStream outputStream, OutputStream outputStream2);
}
