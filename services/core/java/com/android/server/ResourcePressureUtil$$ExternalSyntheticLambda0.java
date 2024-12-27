package com.android.server;

import android.util.Slog;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.function.Function;
import libcore.io.IoUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class ResourcePressureUtil$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String str = (String) obj;
        StringWriter stringWriter = new StringWriter();
        try {
            if (new File(str).exists()) {
                stringWriter.append((CharSequence) ("----- Output from " + str + " -----\n"));
                stringWriter.append((CharSequence) IoUtils.readFileAsString(str));
                stringWriter.append((CharSequence) ("----- End output from " + str + " -----\n"));
            }
        } catch (IOException e) {
            Slog.e("ResourcePressureUtil", " could not read " + str, e);
        }
        return stringWriter.toString();
    }
}
