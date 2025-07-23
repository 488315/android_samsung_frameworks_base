package com.android.systemui.shade;

import com.android.systemui.CoreStartable;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.display.ShadeDisplayPolicy;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.util.settings.GlobalSettings;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadePrimaryDisplayCommand implements Command, CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandRegistry commandRegistry;
    public final ShadeDisplayPolicy defaultPolicy;
    public final DisplayRepository displaysRepository;
    public final GlobalSettings globalSettings;
    public final Set policies;
    public final ShadeDisplaysRepository positionRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class CommandHandler {
        public final List args;
        public final PrintWriter pw;

        public CommandHandler(PrintWriter printWriter, List<String> list) {
            this.pw = printWriter;
            this.args = list;
        }
    }

    public ShadePrimaryDisplayCommand(GlobalSettings globalSettings, CommandRegistry commandRegistry, DisplayRepository displayRepository, ShadeDisplaysRepository shadeDisplaysRepository, Set<ShadeDisplayPolicy> set, ShadeDisplayPolicy shadeDisplayPolicy) {
        this.globalSettings = globalSettings;
        this.commandRegistry = commandRegistry;
        this.displaysRepository = displayRepository;
        this.positionRepository = shadeDisplaysRepository;
        this.policies = set;
        this.defaultPolicy = shadeDisplayPolicy;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00b4, code lost:
    
        if (r6.equals("list") == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00f8, code lost:
    
        r6 = (java.util.Set) ((com.android.systemui.display.data.repository.DisplayRepositoryImpl) r7.displaysRepository).displayRepositoryFromLib.getDisplays().getValue();
        r7 = ((java.lang.Number) ((com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl) r7.positionRepository).displayId.getValue()).intValue();
        r0.pw.println("Available displays: ");
        r6 = r6.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0129, code lost:
    
        if (r6.hasNext() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x012b, code lost:
    
        r8 = (android.view.Display) r6.next();
        r0.pw.print(" - " + r8.getDisplayId());
        r1 = r0.pw;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x014c, code lost:
    
        if (r8.getDisplayId() != r7) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x014e, code lost:
    
        r8 = " (Shade window is here)";
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0152, code lost:
    
        r1.println(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0151, code lost:
    
        r8 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00be, code lost:
    
        if (r6.equals(com.sec.ims.IMSParameter.CALL.STATUS) == false) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00dc  */
    @Override // com.android.systemui.statusbar.commandline.Command
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void execute(java.io.PrintWriter r7, java.util.List r8) {
        /*
            Method dump skipped, instructions count: 368
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.shade.ShadePrimaryDisplayCommand.execute(java.io.PrintWriter, java.util.List):void");
    }

    public final void help(PrintWriter printWriter) {
        printWriter.println("shade_display_override <policyName> ");
        printWriter.println("Set the display which is holding the shade, or the policy that defines it.");
        printWriter.println();
        printWriter.println("shade_display_override policies");
        printWriter.println("Lists available policies");
        printWriter.println();
        printWriter.println("shade_display_override reset ");
        printWriter.println("Reset the display which is holding the shade.");
        printWriter.println();
        printWriter.println("shade_display_override (list|status) ");
        printWriter.println("Lists available displays and which has the shade");
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.commandRegistry.registerCommand("shade_display_override", new Function0() { // from class: com.android.systemui.shade.ShadePrimaryDisplayCommand$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = ShadePrimaryDisplayCommand.$r8$clinit;
                return ShadePrimaryDisplayCommand.this;
            }
        });
    }
}
