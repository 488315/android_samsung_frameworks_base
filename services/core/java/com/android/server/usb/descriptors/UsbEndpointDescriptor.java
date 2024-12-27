package com.android.server.usb.descriptors;

import com.android.server.usb.descriptors.report.TextReportCanvas;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsbEndpointDescriptor extends UsbDescriptor {
    public int mAttributes;
    public UsbDescriptor mClassSpecificEndpointDescriptor;
    public int mEndpointAddress;
    public int mInterval;
    public int mPacketSize;

    public final int getDirection() {
        return this.mEndpointAddress & (-128);
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final int parseRawDescriptors(ByteStream byteStream) {
        this.mEndpointAddress = byteStream.getUnsignedByte();
        this.mAttributes = byteStream.getUnsignedByte();
        this.mPacketSize = byteStream.unpackUsbShort();
        this.mInterval = byteStream.getUnsignedByte();
        int i = this.mLength;
        if (i == 9) {
            byteStream.getByte();
            byteStream.getByte();
        }
        return i;
    }

    @Override // com.android.server.usb.descriptors.UsbDescriptor
    public final void report(TextReportCanvas textReportCanvas) {
        super.report(textReportCanvas);
        textReportCanvas.openList();
        StringBuilder sb = new StringBuilder("Address: ");
        sb.append(TextReportCanvas.getHexString(this.mEndpointAddress & 15));
        sb.append(getDirection() == 0 ? " [out]" : " [in]");
        textReportCanvas.writeListItem(sb.toString());
        int i = this.mAttributes;
        textReportCanvas.openListItem();
        textReportCanvas.write("Attributes: " + TextReportCanvas.getHexString(i) + " ");
        int i2 = i & 3;
        if (i2 == 0) {
            textReportCanvas.write("Control");
        } else if (i2 == 1) {
            textReportCanvas.write("Iso");
        } else if (i2 == 2) {
            textReportCanvas.write("Bulk");
        } else if (i2 == 3) {
            textReportCanvas.write("Interrupt");
        }
        textReportCanvas.closeListItem();
        if (i2 == 1) {
            textReportCanvas.openListItem();
            textReportCanvas.write("Aync: ");
            int i3 = i & 12;
            if (i3 == 0) {
                textReportCanvas.write("NONE");
            } else if (i3 == 4) {
                textReportCanvas.write("ASYNC");
            } else if (i3 == 8) {
                textReportCanvas.write("ADAPTIVE ASYNC");
            }
            textReportCanvas.closeListItem();
            textReportCanvas.openListItem();
            textReportCanvas.write("Useage: ");
            int i4 = i & 48;
            if (i4 == 0) {
                textReportCanvas.write("DATA");
            } else if (i4 == 16) {
                textReportCanvas.write("FEEDBACK");
            } else if (i4 == 32) {
                textReportCanvas.write("EXPLICIT FEEDBACK");
            } else if (i4 == 48) {
                textReportCanvas.write("RESERVED");
            }
            textReportCanvas.closeListItem();
        }
        textReportCanvas.writeListItem("Package Size: " + this.mPacketSize);
        textReportCanvas.writeListItem("Interval: " + this.mInterval);
        textReportCanvas.closeList();
    }
}
