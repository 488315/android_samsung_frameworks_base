package com.android.systemui.haptics.msdl;

import com.android.systemui.CoreStartable;
import com.google.android.msdl.domain.MSDLPlayer;
import com.google.android.msdl.logging.MSDLEvent;
import java.io.PrintWriter;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MSDLCoreStartable implements CoreStartable {
    public final MSDLPlayer msdlPlayer;

    public MSDLCoreStartable(MSDLPlayer mSDLPlayer) {
        this.msdlPlayer = mSDLPlayer;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        MSDLPlayer mSDLPlayer = this.msdlPlayer;
        printWriter.println(mSDLPlayer);
        printWriter.println("MSDL player history of the last 20 events:");
        Iterator it = mSDLPlayer.getHistory().iterator();
        while (it.hasNext()) {
            printWriter.println(String.valueOf((MSDLEvent) it.next()));
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
