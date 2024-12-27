package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import java.util.ListIterator;

public final class RequiredSubCommand implements SubCommand {
    public final ParseableCommand cmd;
    public final String description;
    public boolean handled;
    public final String longName;
    public boolean validationStatus = true;

    public RequiredSubCommand(ParseableCommand parseableCommand) {
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

    @Override // com.android.systemui.statusbar.commandline.SubCommand
    public final void parseSubCommandArgs(ListIterator listIterator) {
        this.validationStatus = this.cmd.parser.parseAsSubCommand(listIterator);
        this.handled = true;
    }
}
