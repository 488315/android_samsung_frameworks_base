package com.android.server.pm;

import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public final /* synthetic */ class ShortcutDumpFiles$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ byte[] f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((PrintWriter) obj)
                .println(StandardCharsets.UTF_8.decode(ByteBuffer.wrap(this.f$0)).toString());
    }
}
