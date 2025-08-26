package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes3.dex */
public abstract class ParseableCommand implements Command {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public static final Companion Companion;
    public final String description;
    public final Flag help$delegate;
    public final String name;
    public final CommandParser parser;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(ParseableCommand.class, "help", "getHelp()Z", 0);
        Reflection.factory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl};
        Companion = new Companion(null);
    }

    public ParseableCommand(String str, String str2) {
        this.name = str;
        this.description = str2;
        this.parser = new CommandParser();
        this.help$delegate = flag("help", "h", "Print help and return");
    }

    public abstract void execute(PrintWriter printWriter);

    @Override // com.android.systemui.statusbar.commandline.Command
    public final void execute(PrintWriter printWriter, List list) {
        KProperty[] kPropertyArr;
        CommandParser commandParser = this.parser;
        try {
            boolean z = commandParser.parse(list);
            List list2 = commandParser.subCommands;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = (ArrayList) list2;
            int size = arrayList2.size();
            int i = 0;
            int i2 = 0;
            while (true) {
                kPropertyArr = $$delegatedProperties;
                if (i2 >= size) {
                    break;
                }
                Object obj = arrayList2.get(i2);
                i2++;
                ParseableCommand cmd = ((SubCommand) obj).getCmd();
                cmd.getClass();
                KProperty kProperty = kPropertyArr[0];
                if (cmd.help$delegate.inner) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            int size2 = arrayList.size();
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                arrayList3.add(((SubCommand) obj2).getCmd());
            }
            KProperty kProperty2 = kPropertyArr[0];
            if (this.help$delegate.inner) {
                help(printWriter);
                return;
            }
            if (!arrayList3.isEmpty()) {
                int size3 = arrayList3.size();
                while (i < size3) {
                    Object obj3 = arrayList3.get(i);
                    i++;
                    ((ParseableCommand) obj3).help(printWriter);
                }
                return;
            }
            if (z) {
                execute(printWriter);
                return;
            }
            ArrayList arrayList4 = new ArrayList();
            if (!((ArrayList) commandParser.getUnhandledParams()).isEmpty()) {
                List unhandledParams = commandParser.getUnhandledParams();
                ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(unhandledParams, 10));
                ArrayList arrayList6 = (ArrayList) unhandledParams;
                int size4 = arrayList6.size();
                int i4 = 0;
                while (i4 < size4) {
                    Object obj4 = arrayList6.get(i4);
                    i4++;
                    arrayList5.add(((Param) obj4).getLongName());
                }
                arrayList4.add("No values passed for required params: " + arrayList5);
            }
            if (!((ArrayList) commandParser.getUnhandledSubCmds()).isEmpty()) {
                List unhandledSubCmds = commandParser.getUnhandledSubCmds();
                ArrayList arrayList7 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(unhandledSubCmds, 10));
                ArrayList arrayList8 = (ArrayList) unhandledSubCmds;
                int size5 = arrayList8.size();
                int i5 = 0;
                while (i5 < size5) {
                    Object obj5 = arrayList8.get(i5);
                    i5++;
                    arrayList7.add(((SubCommand) obj5).getLongName());
                }
                arrayList4.addAll(arrayList7);
                List unhandledSubCmds2 = commandParser.getUnhandledSubCmds();
                ArrayList arrayList9 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(unhandledSubCmds2, 10));
                ArrayList arrayList10 = (ArrayList) unhandledSubCmds2;
                int size6 = arrayList10.size();
                int i6 = 0;
                while (i6 < size6) {
                    Object obj6 = arrayList10.get(i6);
                    i6++;
                    arrayList9.add(((SubCommand) obj6).getShortName());
                }
                arrayList4.add("No values passed for required sub-commands: " + arrayList9);
            }
            int size7 = arrayList4.size();
            while (i < size7) {
                Object obj7 = arrayList4.get(i);
                i++;
                printWriter.println((String) obj7);
            }
        } catch (ArgParseError e) {
            printWriter.println(e.getMessage());
        } catch (Exception e2) {
            printWriter.println("Unknown exception encountered during parse");
            printWriter.println(e2);
        }
    }

    public final Flag flag(String str, String str2, String str3) {
        boolean z = true;
        if (str2 != null && str2.length() != 1) {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Flag short name must be one character long, or null. Got (", str2, ")"));
        }
        if (str.startsWith("-")) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Flags must not start with '-'. Got $(", str, ")"));
        }
        String strConcat = str2 != null ? "-".concat(str2) : null;
        String strConcat2 = "--".concat(str);
        CommandParser commandParser = this.parser;
        String strCheckCliNames = commandParser.checkCliNames(strConcat, strConcat2);
        if (strCheckCliNames != null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Detected reused flag name (", strCheckCliNames, ")"));
        }
        if (strConcat != null) {
            commandParser.tokenSet.add(strConcat);
        }
        commandParser.tokenSet.add(strConcat2);
        Flag flag = new Flag(strConcat, strConcat2, str3);
        ((ArrayList) commandParser._flags).add(flag);
        return flag;
    }

    public final void help(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        String str = this.name;
        int length = str.length() + 2;
        indentingPrintWriter.println("┌" + StringsKt__StringsJVMKt.repeat(length, "─") + "┐");
        indentingPrintWriter.println("│ " + str + " │");
        indentingPrintWriter.println("└" + StringsKt__StringsJVMKt.repeat(length, "─") + "┘");
        indentingPrintWriter.println();
        indentingPrintWriter.increaseIndent();
        usage(indentingPrintWriter);
        Unit unit = Unit.INSTANCE;
        indentingPrintWriter.decreaseIndent();
        String str2 = this.description;
        if (str2 != null) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(str2);
            Unit unit2 = Unit.INSTANCE;
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
        }
        CommandParser commandParser = this.parser;
        ArrayList arrayList = (ArrayList) commandParser.flags;
        if (!arrayList.isEmpty()) {
            indentingPrintWriter.println("FLAGS:");
            indentingPrintWriter.increaseIndent();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((Flag) it.next()).describe(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            Unit unit3 = Unit.INSTANCE;
            indentingPrintWriter.decreaseIndent();
        }
        List list = commandParser.params;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = (ArrayList) list;
        int size = arrayList4.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList4.get(i2);
            i2++;
            if (((Param) obj) instanceof SingleArgParam) {
                arrayList2.add(obj);
            } else {
                arrayList3.add(obj);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        List list2 = (List) pair.component1();
        List list3 = (List) pair.component2();
        boolean zIsEmpty = list2.isEmpty();
        Companion companion = Companion;
        if (!zIsEmpty) {
            indentingPrintWriter.println("REQUIRED PARAMS:");
            companion.getClass();
            indentingPrintWriter.increaseIndent();
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                ((Describable) it2.next()).describe(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            Unit unit4 = Unit.INSTANCE;
            indentingPrintWriter.decreaseIndent();
        }
        if (!list3.isEmpty()) {
            indentingPrintWriter.println("OPTIONAL PARAMS:");
            companion.getClass();
            indentingPrintWriter.increaseIndent();
            Iterator it3 = list3.iterator();
            while (it3.hasNext()) {
                ((Describable) it3.next()).describe(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            Unit unit5 = Unit.INSTANCE;
            indentingPrintWriter.decreaseIndent();
        }
        List list4 = commandParser.subCommands;
        ArrayList arrayList5 = new ArrayList();
        ArrayList arrayList6 = new ArrayList();
        ArrayList arrayList7 = (ArrayList) list4;
        int size2 = arrayList7.size();
        while (i < size2) {
            Object obj2 = arrayList7.get(i);
            i++;
            if (((SubCommand) obj2) instanceof RequiredSubCommand) {
                arrayList5.add(obj2);
            } else {
                arrayList6.add(obj2);
            }
        }
        Pair pair2 = new Pair(arrayList5, arrayList6);
        List list5 = (List) pair2.component1();
        List list6 = (List) pair2.component2();
        if (!list5.isEmpty()) {
            indentingPrintWriter.println("REQUIRED SUBCOMMANDS:");
            companion.getClass();
            indentingPrintWriter.increaseIndent();
            Iterator it4 = list5.iterator();
            while (it4.hasNext()) {
                ((Describable) it4.next()).describe(indentingPrintWriter);
                indentingPrintWriter.println();
            }
            Unit unit6 = Unit.INSTANCE;
            indentingPrintWriter.decreaseIndent();
        }
        if (list6.isEmpty()) {
            return;
        }
        indentingPrintWriter.println("OPTIONAL SUBCOMMANDS:");
        companion.getClass();
        indentingPrintWriter.increaseIndent();
        Iterator it5 = list6.iterator();
        while (it5.hasNext()) {
            ((Describable) it5.next()).describe(indentingPrintWriter);
            indentingPrintWriter.println();
        }
        Unit unit7 = Unit.INSTANCE;
        indentingPrintWriter.decreaseIndent();
    }

    public final SingleArgParamOptional param(String str, String str2, String str3, ValueParser valueParser) {
        boolean z = true;
        if (str2 != null && str2.length() != 1) {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Parameter short name must be one character long, or null. Got (", str2, ")"));
        }
        if (str.startsWith("-")) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Parameters must not start with '-'. Got $(", str, ")"));
        }
        String strConcat = str2 != null ? "-".concat(str2) : null;
        String strConcat2 = "--".concat(str);
        CommandParser commandParser = this.parser;
        String strCheckCliNames = commandParser.checkCliNames(strConcat, strConcat2);
        if (strCheckCliNames != null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Detected reused param name (", strCheckCliNames, ")"));
        }
        if (strConcat != null) {
            commandParser.tokenSet.add(strConcat);
        }
        commandParser.tokenSet.add(strConcat2);
        SingleArgParamOptional singleArgParamOptional = new SingleArgParamOptional(strConcat2, strConcat, str3, valueParser);
        ((ArrayList) commandParser._params).add(singleArgParamOptional);
        return singleArgParamOptional;
    }

    public final SingleArgParam required(SingleArgParamOptional singleArgParamOptional) {
        CommandParser commandParser = this.parser;
        commandParser.getClass();
        ValueParser valueParser = singleArgParamOptional.valueParser;
        SingleArgParam singleArgParam = new SingleArgParam(singleArgParamOptional.longName, singleArgParamOptional.shortName, singleArgParamOptional.description, valueParser);
        ((ArrayList) commandParser._params).remove(singleArgParamOptional);
        ((ArrayList) commandParser._params).add(singleArgParam);
        return singleArgParam;
    }

    public final OptionalSubCommand subCommand(ParseableCommand parseableCommand) {
        CommandParser commandParser = this.parser;
        commandParser.getClass();
        String str = parseableCommand.name;
        String strCheckCliNames = commandParser.checkCliNames(null, str);
        if (strCheckCliNames != null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot re-use name for subcommand (", strCheckCliNames, ")"));
        }
        if (!((ArrayList) parseableCommand.parser.subCommands).isEmpty()) {
            throw new IllegalArgumentException("SubCommands may not contain other SubCommands. " + parseableCommand);
        }
        commandParser.tokenSet.add(str);
        OptionalSubCommand optionalSubCommand = new OptionalSubCommand(parseableCommand);
        ((ArrayList) commandParser._subCommands).add(optionalSubCommand);
        return optionalSubCommand;
    }

    public /* synthetic */ ParseableCommand(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2);
    }

    public void usage(IndentingPrintWriter indentingPrintWriter) {
    }
}
