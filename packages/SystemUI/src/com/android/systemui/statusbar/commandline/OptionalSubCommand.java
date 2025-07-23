package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import java.util.ListIterator;
import kotlin.properties.ReadOnlyProperty;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OptionalSubCommand implements SubCommand, ReadOnlyProperty {
    public final ParseableCommand cmd;
    public final String description;
    public boolean isPresent;
    public final String longName;
    public boolean validationStatus = true;

    public OptionalSubCommand(ParseableCommand parseableCommand) {
        this.cmd = parseableCommand;
        this.longName = parseableCommand.name;
        this.description = parseableCommand.description;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final void describe(IndentingPrintWriter indentingPrintWriter) {
        this.cmd.help(indentingPrintWriter);
    }

    @Override // com.android.systemui.statusbar.commandline.SubCommand
    public final ParseableCommand getCmd() {
        return this.cmd;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getLongName() {
        return this.longName;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getShortName() {
        return null;
    }

    @Override // com.android.systemui.statusbar.commandline.SubCommand
    public final boolean getValidationStatus() {
        return this.validationStatus;
    }

    public final ParseableCommand getValue() {
        if (this.isPresent) {
            return this.cmd;
        }
        return null;
    }

    @Override // kotlin.properties.ReadOnlyProperty
    public final /* bridge */ /* synthetic */ Object getValue(Object obj, KProperty kProperty) {
        throw null;
    }

    @Override // com.android.systemui.statusbar.commandline.SubCommand
    public final void parseSubCommandArgs(ListIterator listIterator) {
        this.validationStatus = this.cmd.parser.parseAsSubCommand(listIterator);
        this.isPresent = true;
    }
}
