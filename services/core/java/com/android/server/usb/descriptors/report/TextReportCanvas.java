package com.android.server.usb.descriptors.report;

import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.usb.descriptors.UsbDescriptorParser;

public final class TextReportCanvas {
    public int mListIndent;
    public final UsbDescriptorParser mParser;
    public final StringBuilder mStringBuilder;

    public TextReportCanvas(UsbDescriptorParser usbDescriptorParser, StringBuilder sb) {
        this.mParser = usbDescriptorParser;
        this.mStringBuilder = sb;
    }

    public static String getBCDString(int i) {
        return "" + ((i >> 8) & 15) + "." + ((i >> 4) & 15) + (i & 15);
    }

    public static String getHexString(byte b) {
        return "0x" + Integer.toHexString(b & 255).toUpperCase();
    }

    public static String getHexString(int i) {
        return "0x" + Integer.toHexString(i & GnssNative.GNSS_AIDING_TYPE_ALL).toUpperCase();
    }

    public final void closeList() {
        this.mListIndent -= 2;
    }

    public final void closeListItem() {
        this.mStringBuilder.append("\n");
    }

    public final void openList() {
        this.mListIndent += 2;
    }

    public final void openListItem() {
        writeListIndent();
        this.mStringBuilder.append("- ");
    }

    public final void write(String str) {
        this.mStringBuilder.append(str);
    }

    public final void writeHeader(String str) {
        writeListIndent();
        StringBuilder sb = this.mStringBuilder;
        sb.append("[");
        write(str);
        sb.append("]\n");
    }

    public final void writeListIndent() {
        for (int i = 0; i < this.mListIndent; i++) {
            this.mStringBuilder.append(" ");
        }
    }

    public final void writeListItem(String str) {
        openListItem();
        write(str);
        closeListItem();
    }

    public final void writeParagraph(String str, boolean z) {
        writeListIndent();
        StringBuilder sb = this.mStringBuilder;
        if (z) {
            sb.append("*" + str + "*");
        } else {
            sb.append(str);
        }
        sb.append("\n");
    }
}
