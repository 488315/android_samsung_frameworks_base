package com.android.server.input;

import android.content.res.Resources;
import android.hardware.input.KeyboardLayout;

import libcore.io.Streams;

import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class KeyboardLayoutManager$$ExternalSyntheticLambda1
        implements KeyboardLayoutManager.KeyboardLayoutVisitor {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String[] f$0;

    public /* synthetic */ KeyboardLayoutManager$$ExternalSyntheticLambda1(int i, String[] strArr) {
        this.$r8$classId = i;
        this.f$0 = strArr;
    }

    @Override // com.android.server.input.KeyboardLayoutManager.KeyboardLayoutVisitor
    public final void visitKeyboardLayout(
            Resources resources, int i, KeyboardLayout keyboardLayout) {
        int i2 = this.$r8$classId;
        String[] strArr = this.f$0;
        switch (i2) {
            case 0:
                try {
                    InputStreamReader inputStreamReader =
                            new InputStreamReader(resources.openRawResource(i));
                    try {
                        strArr[0] = keyboardLayout.getDescriptor();
                        strArr[1] = Streams.readFully(inputStreamReader);
                        inputStreamReader.close();
                        return;
                    } catch (Throwable th) {
                        try {
                            inputStreamReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (Resources.NotFoundException | IOException unused) {
                    return;
                }
            default:
                try {
                    InputStreamReader inputStreamReader2 =
                            new InputStreamReader(resources.openRawResource(i));
                    try {
                        strArr[0] = Streams.readFully(inputStreamReader2);
                        inputStreamReader2.close();
                        return;
                    } catch (Throwable th3) {
                        try {
                            inputStreamReader2.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                } catch (Resources.NotFoundException | IOException unused2) {
                    return;
                }
        }
    }
}
