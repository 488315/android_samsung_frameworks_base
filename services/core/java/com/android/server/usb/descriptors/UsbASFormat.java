package com.android.server.usb.descriptors;

import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.report.UsbStrings;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class UsbASFormat extends UsbACInterface {
    public final byte mFormatType;

    public UsbASFormat(int i, byte b, byte b2, byte b3, int i2) {
        super(i, b, b2, i2);
        this.mFormatType = b3;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface, com.android.server.usb.descriptors.UsbDescriptor
    public void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        HashMap hashMap = UsbStrings.sFormatNames;
        byte b = this.mFormatType;
        String str = (String) hashMap.get(Integer.valueOf(b));
        if (str == null) {
            str = AudioChannelMask$$ExternalSyntheticOutline0.m(new StringBuilder("Unknown Format Type 0x"), b);
        }
        textReportCanvas.writeParagraph(str, false);
    }
}
