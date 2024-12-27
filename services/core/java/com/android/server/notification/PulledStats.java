package com.android.server.notification;

import android.os.ParcelFileDescriptor;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PulledStats {
    public long mTimePeriodEndMs;
    public final long mTimePeriodStartMs;
    public final List mUndecoratedPackageNames = new ArrayList();

    public PulledStats(long j) {
        this.mTimePeriodStartMs = j;
        this.mTimePeriodEndMs = j;
    }

    public final ParcelFileDescriptor toParcelFileDescriptor(final int i) {
        final ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
        if (i != 1) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Unknown pulled stats request: ", "PulledStats");
        } else {
            new Thread() { // from class: com.android.server.notification.PulledStats.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super("NotificationManager pulled metric output");
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    try {
                        ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(createPipe[1]);
                        ProtoOutputStream protoOutputStream = new ProtoOutputStream(autoCloseOutputStream);
                        PulledStats.this.writeToProto(i, protoOutputStream);
                        protoOutputStream.flush();
                        autoCloseOutputStream.close();
                    } catch (IOException e) {
                        Slog.w("PulledStats", "Failure writing pipe", e);
                    }
                }
            }.start();
        }
        return createPipe[0];
    }

    public void writeToProto(int i, ProtoOutputStream protoOutputStream) {
        if (i != 1) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(i, "Unknown pulled stats request: ", "PulledStats");
            return;
        }
        Iterator it = ((ArrayList) this.mUndecoratedPackageNames).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            long start = protoOutputStream.start(2246267895809L);
            protoOutputStream.write(1138166333441L, str);
            protoOutputStream.end(start);
        }
    }
}
