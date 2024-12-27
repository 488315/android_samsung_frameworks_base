package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import java.util.ListIterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OptionalSubCommand implements SubCommand {
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

    @Override // com.android.systemui.statusbar.commandline.SubCommand
    public final void parseSubCommandArgs(ListIterator listIterator) {
        this.validationStatus = this.cmd.parser.parseAsSubCommand(listIterator);
        this.isPresent = true;
    }
}
