package com.android.systemui.shade;

import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepository;
import com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl;
import com.android.systemui.shade.display.ShadeDisplayPolicy;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.util.settings.GlobalSettings;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class ShadePrimaryDisplayCommand implements Command, CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CommandRegistry commandRegistry;
    public final ShadeDisplayPolicy defaultPolicy;
    public final DisplayRepository displaysRepository;
    public final GlobalSettings globalSettings;
    public final Set policies;
    public final ShadeDisplaysRepository positionRepository;

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
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00be, code lost:
    
        if (r6.equals(com.sec.ims.IMSParameter.CALL.STATUS) == false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00f8, code lost:
    
        r6 = (java.util.Set) ((com.android.systemui.display.data.repository.DisplayRepositoryImpl) r7.displaysRepository).displayRepositoryFromLib.getDisplays().getValue();
        r7 = ((java.lang.Number) ((com.android.systemui.shade.data.repository.ShadeDisplaysRepositoryImpl) r7.positionRepository).displayId.getValue()).intValue();
        r0.pw.println("Available displays: ");
        r6 = r6.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0129, code lost:
    
        if (r6.hasNext() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x012b, code lost:
    
        r8 = (android.view.Display) r6.next();
        r0.pw.print(" - " + r8.getDisplayId());
        r1 = r0.pw;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x014c, code lost:
    
        if (r8.getDisplayId() != r7) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x014e, code lost:
    
        r8 = " (Shade window is here)";
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0151, code lost:
    
        r8 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0152, code lost:
    
        r1.println(r8);
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00dc  */
    @Override // com.android.systemui.statusbar.commandline.Command
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void execute(PrintWriter printWriter, List list) {
        Set set;
        Iterator it;
        CommandHandler commandHandler = new CommandHandler(printWriter, list);
        String str = (String) CollectionsKt___CollectionsKt.getOrNull(0, commandHandler.args);
        String lowerCase = str != null ? str.toLowerCase(Locale.ROOT) : null;
        ShadePrimaryDisplayCommand shadePrimaryDisplayCommand = ShadePrimaryDisplayCommand.this;
        if (lowerCase == null) {
            shadePrimaryDisplayCommand.help(commandHandler.pw);
            return;
        }
        switch (lowerCase.hashCode()) {
            case -892481550:
                break;
            case 3322014:
                break;
            case 108404047:
                if (lowerCase.equals(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR)) {
                    GlobalSettings globalSettings = shadePrimaryDisplayCommand.globalSettings;
                    ShadeDisplayPolicy shadeDisplayPolicy = shadePrimaryDisplayCommand.defaultPolicy;
                    globalSettings.putString("shade_display_awareness", shadeDisplayPolicy.getName());
                    ActionReceiver$$ExternalSyntheticOutline0.m(commandHandler.pw, "Reset shade display policy to default policy: ", shadeDisplayPolicy.getName());
                    break;
                }
                set = shadePrimaryDisplayCommand.policies;
                if (!(set instanceof Collection) || !set.isEmpty()) {
                    it = set.iterator();
                    while (it.hasNext()) {
                        if (Intrinsics.areEqual(((ShadeDisplayPolicy) it.next()).getName(), lowerCase)) {
                            shadePrimaryDisplayCommand.globalSettings.putString("shade_display_awareness", lowerCase);
                            break;
                        }
                    }
                }
                shadePrimaryDisplayCommand.help(commandHandler.pw);
                break;
            case 546894160:
                if (lowerCase.equals("policies")) {
                    String name = ((ShadeDisplayPolicy) ((ShadeDisplaysRepositoryImpl) shadePrimaryDisplayCommand.positionRepository).policy.$$delegate_0.getValue()).getName();
                    commandHandler.pw.println("Available policies: ");
                    for (ShadeDisplayPolicy shadeDisplayPolicy2 : shadePrimaryDisplayCommand.policies) {
                        commandHandler.pw.print(" - " + shadeDisplayPolicy2.getName());
                        commandHandler.pw.println(Intrinsics.areEqual(name, shadeDisplayPolicy2.getName()) ? " (Current policy)" : "");
                    }
                    break;
                }
                set = shadePrimaryDisplayCommand.policies;
                if (!(set instanceof Collection)) {
                    it = set.iterator();
                    while (it.hasNext()) {
                    }
                    break;
                }
                shadePrimaryDisplayCommand.help(commandHandler.pw);
                break;
            default:
                set = shadePrimaryDisplayCommand.policies;
                if (!(set instanceof Collection)) {
                }
                shadePrimaryDisplayCommand.help(commandHandler.pw);
                break;
        }
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
                return this.f$0;
            }
        });
    }
}
