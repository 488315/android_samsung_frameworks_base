package com.android.systemui.compose;

import com.android.systemui.statusbar.commandline.OptionalSubCommand;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import java.io.PrintWriter;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class CompositionTracingCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final OptionalSubCommand disable$delegate;
    public final OptionalSubCommand enable$delegate;

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(CompositionTracingCommand.class, "enable", "getEnable()Lcom/android/systemui/compose/EnableCommand;", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl2 = new PropertyReference1Impl(CompositionTracingCommand.class, "disable", "getDisable()Lcom/android/systemui/compose/DisableCommand;", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl, propertyReference1Impl2};
    }

    public CompositionTracingCommand() {
        super("composition-tracing", null, 2, 0 == true ? 1 : 0);
        this.enable$delegate = subCommand(new EnableCommand());
        this.disable$delegate = subCommand(new DisableCommand());
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        KProperty[] kPropertyArr = $$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        OptionalSubCommand optionalSubCommand = this.enable$delegate;
        boolean z = ((EnableCommand) optionalSubCommand.getValue()) != null;
        KProperty kProperty2 = kPropertyArr[1];
        OptionalSubCommand optionalSubCommand2 = this.disable$delegate;
        if (!(z ^ (((DisableCommand) optionalSubCommand2.getValue()) != null))) {
            help(printWriter);
            return;
        }
        KProperty kProperty3 = kPropertyArr[0];
        EnableCommand enableCommand = (EnableCommand) optionalSubCommand.getValue();
        if (enableCommand != null) {
            enableCommand.execute(printWriter);
        }
        KProperty kProperty4 = kPropertyArr[1];
        DisableCommand disableCommand = (DisableCommand) optionalSubCommand2.getValue();
        if (disableCommand != null) {
            disableCommand.execute(printWriter);
        }
    }
}
