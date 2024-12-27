package com.android.server.infra;

import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;

import java.io.PrintWriter;

public final class SecureSettingsServiceNameResolver extends ServiceNameBaseResolver {
    public final String mProperty;
    public final TextUtils.SimpleStringSplitter mStringColonSplitter;

    public SecureSettingsServiceNameResolver(Context context, String str, boolean z) {
        super(context, z);
        this.mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        this.mProperty = str;
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final void dumpShort(int i, PrintWriter printWriter) {
        printWriter.print("defaultService=");
        printWriter.print(getDefaultServiceName(i));
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final void dumpShort(PrintWriter printWriter) {
        printWriter.print("SecureSettingsServiceNamer: prop=");
        printWriter.print(this.mProperty);
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final String readServiceName(int i) {
        return Settings.Secure.getStringForUser(
                this.mContext.getContentResolver(), this.mProperty, i);
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final String[] readServiceNameList(int i) {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        this.mContext.getContentResolver(), this.mProperty, i);
        ArraySet arraySet = new ArraySet();
        if (!TextUtils.isEmpty(stringForUser)) {
            TextUtils.SimpleStringSplitter simpleStringSplitter = this.mStringColonSplitter;
            simpleStringSplitter.setString(stringForUser);
            while (simpleStringSplitter.hasNext()) {
                String next = simpleStringSplitter.next();
                if (!TextUtils.isEmpty(next)) {
                    arraySet.add(next);
                }
            }
        }
        return (String[]) arraySet.toArray(new String[arraySet.size()]);
    }

    @Override // com.android.server.infra.ServiceNameBaseResolver
    public final String toString() {
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                new StringBuilder("SecureSettingsServiceNameResolver["), this.mProperty, "]");
    }
}
