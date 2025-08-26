package com.android.systemui.lowlightclock;

import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.biometrics.AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0;
import com.android.systemui.shared.condition.Condition;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class ForceLowLightCondition extends Condition {
    public static final boolean DEBUG;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        DEBUG = Log.isLoggable("ForceLowLightCondition", 3);
    }

    public ForceLowLightCondition(CoroutineScope coroutineScope, CommandRegistry commandRegistry) {
        super(coroutineScope, null, true);
        if (DEBUG) {
            Log.d("ForceLowLightCondition", "registering commands");
        }
        commandRegistry.registerCommand("low-light", new Function0() { // from class: com.android.systemui.lowlightclock.ForceLowLightCondition$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                boolean z = ForceLowLightCondition.DEBUG;
                final ForceLowLightCondition forceLowLightCondition = this.f$0;
                return new Command() { // from class: com.android.systemui.lowlightclock.ForceLowLightCondition$1$1
                    @Override // com.android.systemui.statusbar.commandline.Command
                    public final void execute(PrintWriter printWriter, List list) {
                        if (list.size() != 1) {
                            printWriter.println("no command specified");
                            help(printWriter);
                            return;
                        }
                        String str = (String) list.get(0);
                        boolean zEquals = TextUtils.equals(str, "enable");
                        ForceLowLightCondition forceLowLightCondition2 = forceLowLightCondition;
                        if (zEquals) {
                            printWriter.println("forcing low light");
                            if (ForceLowLightCondition.DEBUG) {
                                Log.d("ForceLowLightCondition", "forcing low light");
                            }
                            forceLowLightCondition2.updateCondition(true);
                            return;
                        }
                        if (TextUtils.equals(str, "disable")) {
                            printWriter.println("forcing to not enter low light");
                            if (ForceLowLightCondition.DEBUG) {
                                Log.d("ForceLowLightCondition", "forcing to not enter low light");
                            }
                            forceLowLightCondition2.updateCondition(false);
                            return;
                        }
                        if (!TextUtils.equals(str, "clear")) {
                            printWriter.println("invalid command");
                            help(printWriter);
                            return;
                        }
                        printWriter.println("clearing any forced low light");
                        if (ForceLowLightCondition.DEBUG) {
                            Log.d("ForceLowLightCondition", "clearing any forced low light");
                        }
                        if (forceLowLightCondition2._isConditionMet == null) {
                            return;
                        }
                        String str2 = forceLowLightCondition2.mTag;
                        if (Log.isLoggable(str2, 3)) {
                            Log.d(str2, "clearing condition");
                        }
                        forceLowLightCondition2._isConditionMet = null;
                        forceLowLightCondition2.sendUpdate();
                    }

                    public final void help(PrintWriter printWriter) {
                        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "Usage: adb shell cmd statusbar low-light <cmd>", "Supported commands:", "  - enable", "    forces device into low-light");
                        AuthRippleController$AuthRippleCommand$$ExternalSyntheticOutline0.m(printWriter, "  - disable", "    forces device to not enter low-light", "  - clear", "    clears any previously forced state");
                    }
                };
            }
        });
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final int getStartStrategy() {
        return 0;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final Object start(Continuation continuation) {
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.shared.condition.Condition
    public final void stop() {
    }
}
