package com.android.server.clipboard;

import android.content.ClipData;
import android.os.PersistableBundle;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Slog;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;

import java.io.EOFException;
import java.io.FileDescriptor;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.util.function.Consumer;

public final /* synthetic */ class EmulatorClipboardMonitor$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ EmulatorClipboardMonitor$$ExternalSyntheticLambda0(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                EmulatorClipboardMonitor emulatorClipboardMonitor =
                        (EmulatorClipboardMonitor) this.f$0;
                Consumer consumer = (Consumer) this.f$1;
                emulatorClipboardMonitor.getClass();
                while (true) {
                    FileDescriptor fileDescriptor = null;
                    while (!Thread.interrupted()) {
                        if (fileDescriptor == null) {
                            try {
                                fileDescriptor = EmulatorClipboardMonitor.openPipe();
                                synchronized (emulatorClipboardMonitor) {
                                    emulatorClipboardMonitor.mPipe = fileDescriptor;
                                }
                            } catch (ErrnoException
                                    | EOFException
                                    | InterruptedIOException
                                    | InterruptedException
                                    | OutOfMemoryError
                                    | ProtocolException e) {
                                Slog.w(
                                        "EmulatorClipboardMonitor",
                                        "Failure to read from host clipboard",
                                        e);
                                synchronized (emulatorClipboardMonitor) {
                                    emulatorClipboardMonitor.mPipe = null;
                                    try {
                                        Os.close(fileDescriptor);
                                    } catch (ErrnoException unused) {
                                    }
                                }
                            }
                        }
                        String str =
                                new String(EmulatorClipboardMonitor.receiveMessage(fileDescriptor));
                        ClipData clipData =
                                new ClipData(
                                        "host clipboard",
                                        new String[] {"text/plain"},
                                        new ClipData.Item(str));
                        PersistableBundle persistableBundle = new PersistableBundle();
                        persistableBundle.putBoolean(
                                "com.android.systemui.SUPPRESS_CLIPBOARD_OVERLAY", true);
                        clipData.getDescription().setExtras(persistableBundle);
                        if (EmulatorClipboardMonitor.LOG_CLIBOARD_ACCESS) {
                            Slog.i(
                                    "EmulatorClipboardMonitor",
                                    "Setting the guest clipboard to '" + str + "'");
                        }
                        consumer.accept(clipData);
                    }
                    return;
                    break;
                }
            default:
                String str2 = (String) this.f$0;
                FileDescriptor fileDescriptor2 = (FileDescriptor) this.f$1;
                if (EmulatorClipboardMonitor.LOG_CLIBOARD_ACCESS) {
                    BootReceiver$$ExternalSyntheticOutline0.m58m(
                            "Setting the host clipboard to '",
                            str2,
                            "'",
                            "EmulatorClipboardMonitor");
                }
                try {
                    EmulatorClipboardMonitor.sendMessage(fileDescriptor2, str2.getBytes());
                    return;
                } catch (ErrnoException | InterruptedIOException e2) {
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            e2,
                            new StringBuilder("Failed to set host clipboard "),
                            "EmulatorClipboardMonitor");
                    return;
                } catch (IllegalArgumentException unused2) {
                    return;
                }
        }
    }
}
