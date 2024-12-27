package com.android.server.usb.descriptors;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UsbMidiBlockParser {
    public ArrayList mGroupTerminalBlocks;
    public int mHeaderDescriptorSubtype;
    public int mHeaderDescriptorType;
    public int mHeaderLength;
    public int mTotalLength;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class GroupTerminalBlock {
        public int mBlockItem;
        public int mDescriptorSubtype;
        public int mDescriptorType;
        public int mGroupBlockId;
        public int mGroupTerminal;
        public int mGroupTerminalBlockType;
        public int mLength;
        public int mMaxInputBandwidth;
        public int mMaxOutputBandwidth;
        public int mMidiProtocol;
        public int mNumGroupTerminals;
    }

    public final void parseRawDescriptors(ByteStream byteStream) {
        this.mHeaderLength = byteStream.getUnsignedByte();
        this.mHeaderDescriptorType = byteStream.getUnsignedByte();
        this.mHeaderDescriptorSubtype = byteStream.getUnsignedByte();
        this.mTotalLength = byteStream.unpackUsbShort();
        while (byteStream.available() >= 13) {
            GroupTerminalBlock groupTerminalBlock = new GroupTerminalBlock();
            groupTerminalBlock.mLength = byteStream.getUnsignedByte();
            groupTerminalBlock.mDescriptorType = byteStream.getUnsignedByte();
            groupTerminalBlock.mDescriptorSubtype = byteStream.getUnsignedByte();
            groupTerminalBlock.mGroupBlockId = byteStream.getUnsignedByte();
            groupTerminalBlock.mGroupTerminalBlockType = byteStream.getUnsignedByte();
            groupTerminalBlock.mGroupTerminal = byteStream.getUnsignedByte();
            groupTerminalBlock.mNumGroupTerminals = byteStream.getUnsignedByte();
            groupTerminalBlock.mBlockItem = byteStream.getUnsignedByte();
            groupTerminalBlock.mMidiProtocol = byteStream.getUnsignedByte();
            groupTerminalBlock.mMaxInputBandwidth = byteStream.unpackUsbShort();
            groupTerminalBlock.mMaxOutputBandwidth = byteStream.unpackUsbShort();
            this.mGroupTerminalBlocks.add(groupTerminalBlock);
        }
    }
}
