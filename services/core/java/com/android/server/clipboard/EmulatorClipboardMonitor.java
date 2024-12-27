package com.android.server.clipboard;

import android.content.ClipData;
import android.os.SystemProperties;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.VmSocketAddress;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.EOFException;
import java.io.FileDescriptor;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EmulatorClipboardMonitor implements Consumer {
    public static final boolean LOG_CLIBOARD_ACCESS =
            SystemProperties.getBoolean("ro.boot.qemu.log_clipboard_access", false);
    public FileDescriptor mPipe;

    public static FileDescriptor openPipe() {
        FileDescriptor openPipeImpl = openPipeImpl();
        while (openPipeImpl == null) {
            Thread.sleep(100L);
            openPipeImpl = openPipeImpl();
        }
        return openPipeImpl;
    }

    public static FileDescriptor openPipeImpl() {
        try {
            FileDescriptor socket = Os.socket(OsConstants.AF_VSOCK, OsConstants.SOCK_STREAM, 0);
            try {
                Os.connect(socket, new VmSocketAddress(5000, OsConstants.VMADDR_CID_HOST));
                byte[] copyOf = Arrays.copyOf("pipe:clipboard".getBytes(), 15);
                copyOf[14] = 0;
                writeFully(socket, copyOf, copyOf.length);
                return socket;
            } catch (ErrnoException | InterruptedIOException | SocketException unused) {
                Os.close(socket);
                return null;
            }
        } catch (ErrnoException unused2) {
            return null;
        }
    }

    public static byte[] receiveMessage(FileDescriptor fileDescriptor) {
        int i = 4;
        byte[] bArr = new byte[4];
        int i2 = 0;
        int i3 = 0;
        while (i > 0) {
            int read = Os.read(fileDescriptor, bArr, i3, i);
            if (read <= 0) {
                throw new EOFException();
            }
            i3 += read;
            i -= read;
        }
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        int i4 = wrap.getInt();
        if (i4 < 0 || i4 > 134217728) {
            throw new ProtocolException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i4, "Clipboard message length: ", " out of bounds."));
        }
        byte[] bArr2 = new byte[i4];
        while (i4 > 0) {
            int read2 = Os.read(fileDescriptor, bArr2, i2, i4);
            if (read2 <= 0) {
                throw new EOFException();
            }
            i2 += read2;
            i4 -= read2;
        }
        return bArr2;
    }

    public static void sendMessage(FileDescriptor fileDescriptor, byte[] bArr) {
        byte[] bArr2 = new byte[4];
        ByteBuffer wrap = ByteBuffer.wrap(bArr2);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        wrap.putInt(bArr.length);
        writeFully(fileDescriptor, bArr2, 4);
        writeFully(fileDescriptor, bArr, bArr.length);
    }

    public static void writeFully(FileDescriptor fileDescriptor, byte[] bArr, int i) {
        int i2 = 0;
        while (i > 0) {
            int write = Os.write(fileDescriptor, bArr, i2, i);
            if (write <= 0) {
                throw new ErrnoException("write", OsConstants.EIO);
            }
            i2 += write;
            i -= write;
        }
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        FileDescriptor fileDescriptor;
        CharSequence text;
        ClipData clipData = (ClipData) obj;
        synchronized (this) {
            fileDescriptor = this.mPipe;
        }
        if (fileDescriptor != null) {
            String str = "";
            if (clipData != null
                    && clipData.getItemCount() != 0
                    && (text = clipData.getItemAt(0).getText()) != null) {
                str = text.toString();
            }
            new Thread(
                            new EmulatorClipboardMonitor$$ExternalSyntheticLambda0(
                                    1, str, fileDescriptor))
                    .start();
        }
    }
}
