package com.android.internal.widget.remotecompose.core.operations.utilities;

import com.samsung.android.graphics.imagefilter.ShaderAssembler;

/* loaded from: classes6.dex */
public class StringSerializer {
    StringBuffer mBuffer = new StringBuffer();
    String mIndentBuffer = "                                                                      ";

    public void append(int i, String str) {
        String substring = this.mIndentBuffer.substring(0, i);
        this.mBuffer.append(substring);
        this.mBuffer.append(substring);
        this.mBuffer.append(str);
        this.mBuffer.append(ShaderAssembler.NEWLINE);
    }

    public void reset() {
        this.mBuffer = new StringBuffer();
    }

    public String toString() {
        return this.mBuffer.toString();
    }
}
