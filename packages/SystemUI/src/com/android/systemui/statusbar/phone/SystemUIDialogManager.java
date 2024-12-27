package com.android.systemui.statusbar.phone;

import com.android.keyguard.KeyguardViewController;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SystemUIDialogManager implements Dumpable {
    public final KeyguardViewController mKeyguardViewController;
    public final Set mDialogsShowing = new HashSet();
    public final Set mListeners = new HashSet();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Listener {
        void shouldHideAffordances(boolean z);
    }

    public SystemUIDialogManager(DumpManager dumpManager, KeyguardViewController keyguardViewController) {
        dumpManager.registerDumpable(this);
        this.mKeyguardViewController = keyguardViewController;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("listeners:");
        Iterator it = ((HashSet) this.mListeners).iterator();
        while (it.hasNext()) {
            printWriter.println("\t" + ((Listener) it.next()));
        }
        printWriter.println("dialogs tracked:");
        Iterator it2 = ((HashSet) this.mDialogsShowing).iterator();
        while (it2.hasNext()) {
            printWriter.println("\t" + ((SystemUIDialog) it2.next()));
        }
    }

    public final void setShowing(SystemUIDialog systemUIDialog, boolean z) {
        boolean shouldHideAffordance = shouldHideAffordance();
        if (z) {
            ((HashSet) this.mDialogsShowing).add(systemUIDialog);
        } else {
            ((HashSet) this.mDialogsShowing).remove(systemUIDialog);
        }
        if (shouldHideAffordance != shouldHideAffordance()) {
            if (shouldHideAffordance()) {
                this.mKeyguardViewController.hideAlternateBouncer(true);
            }
            Iterator it = ((HashSet) this.mListeners).iterator();
            while (it.hasNext()) {
                ((Listener) it.next()).shouldHideAffordances(shouldHideAffordance());
            }
        }
    }

    public final boolean shouldHideAffordance() {
        return !((HashSet) this.mDialogsShowing).isEmpty();
    }
}
