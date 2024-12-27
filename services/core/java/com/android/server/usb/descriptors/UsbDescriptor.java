package com.android.server.usb.descriptors;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.audio.AudioService$$ExternalSyntheticOutline0;
import com.android.server.usb.descriptors.report.TextReportCanvas;
import com.android.server.usb.descriptors.report.UsbStrings;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class UsbDescriptor {
    public static final String[] sStatusStrings = {
        "UNPARSED", "PARSED - OK", "PARSED - UNDERRUN", "PARSED - OVERRUN"
    };
    public int mHierarchyLevel;
    public final int mLength;
    public int mOverUnderRunCount;
    public byte[] mRawData;
    public int mStatus = 0;
    public final byte mType;

    public UsbDescriptor(byte b, int i) {
        if (i < 2) {
            throw new IllegalArgumentException();
        }
        this.mLength = i;
        this.mType = b;
    }

    public int parseRawDescriptors(ByteStream byteStream) {
        int i = byteStream.mReadCount;
        int i2 = this.mLength;
        int i3 = i2 - i;
        if (i3 > 0) {
            this.mRawData = new byte[i3];
            for (int i4 = 0; i4 < i3; i4++) {
                this.mRawData[i4] = byteStream.getByte();
            }
        }
        return i2;
    }

    public final void postParse(ByteStream byteStream) {
        int i = byteStream.mReadCount;
        byte b = this.mType;
        int i2 = this.mLength;
        if (i < i2) {
            int i3 = i2 - i;
            byteStream.advance(i3);
            this.mStatus = 2;
            this.mOverUnderRunCount = i3;
            StringBuilder sb = new StringBuilder("UNDERRUN t:0x");
            sb.append(Integer.toHexString(b));
            sb.append(" r: ");
            sb.append(i);
            sb.append(" < l: ");
            AudioService$$ExternalSyntheticOutline0.m(sb, i2, "UsbDescriptor");
            return;
        }
        if (i <= i2) {
            this.mStatus = 1;
            return;
        }
        int i4 = i - i2;
        if (i4 < 0) {
            throw new IllegalArgumentException();
        }
        int i5 = byteStream.mIndex;
        if (i5 < i4) {
            byteStream.mIndex = 0;
            throw new IndexOutOfBoundsException();
        }
        byteStream.mReadCount = i - i4;
        byteStream.mIndex = i5 - i4;
        this.mStatus = 3;
        this.mOverUnderRunCount = i4;
        StringBuilder sb2 = new StringBuilder("OVERRRUN t:0x");
        sb2.append(Integer.toHexString(b));
        sb2.append(" r: ");
        sb2.append(i);
        sb2.append(" > l: ");
        AudioService$$ExternalSyntheticOutline0.m(sb2, i2, "UsbDescriptor");
    }

    public void report(TextReportCanvas textReportCanvas) {
        HashMap hashMap = UsbStrings.sDescriptorNames;
        byte b = this.mType;
        String str = (String) hashMap.get(Byte.valueOf(b));
        int i = b & 255;
        if (str == null) {
            str = "Unknown Descriptor [0x" + Integer.toHexString(i) + ":" + i + "]";
        }
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, ": ");
        m.append(TextReportCanvas.getHexString(b));
        m.append(" Len: ");
        m.append(this.mLength);
        String sb = m.toString();
        if (this.mHierarchyLevel != 0) {
            textReportCanvas.writeHeader(sb);
        } else {
            textReportCanvas.writeParagraph(sb, false);
        }
        int i2 = this.mStatus;
        if (i2 != 1) {
            if (i2 == 0 || i2 == 2 || i2 == 3) {
                textReportCanvas.writeParagraph(
                        "status: "
                                + sStatusStrings[this.mStatus]
                                + " ["
                                + this.mOverUnderRunCount
                                + "]",
                        true);
            }
        }
    }
}
