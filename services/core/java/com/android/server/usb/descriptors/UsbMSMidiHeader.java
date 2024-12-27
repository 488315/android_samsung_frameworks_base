package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

public final class UsbMSMidiHeader extends UsbACInterface {
    public int mMidiStreamingClass;

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mMidiStreamingClass = byteStream.unpackUsbShort();
        int i = byteStream.mReadCount;
        int i2 = this.mLength;
        byteStream.advance(i2 - i);
        return i2;
    }

    @Override // com.android.server.usb.descriptors.UsbACInterface,
              // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.writeHeader(
                "MS Midi Header: "
                        + TextReportCanvas.getHexString(this.mType)
                        + " SubType: "
                        + TextReportCanvas.getHexString(this.mSubclass)
                        + " Length: "
                        + this.mLength
                        + " MidiStreamingClass :"
                        + this.mMidiStreamingClass);
    }
}
