package com.android.server;

import android.util.Slog;

import libcore.io.IoUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.function.Function;

public final /* synthetic */ class ResourcePressureUtil$$ExternalSyntheticLambda0
        implements Function {
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
