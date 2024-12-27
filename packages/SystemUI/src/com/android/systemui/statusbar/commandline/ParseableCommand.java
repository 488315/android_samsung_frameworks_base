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
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.text.StringsKt__StringsJVMKt;

public abstract class ParseableCommand implements Command {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public static final Companion Companion;
    public final String description;
    public final Flag help$delegate;
    public final String name;
    public final CommandParser parser;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
            boolean parse = commandParser.parse(list);
            List list2 = commandParser.subCommands;
            ArrayList arrayList = new ArrayList();
            Iterator it = ((ArrayList) list2).iterator();
            while (true) {
                boolean hasNext = it.hasNext();
                kPropertyArr = $$delegatedProperties;
                if (!hasNext) {
                    break;
                }
                Object next = it.next();
                ParseableCommand cmd = ((SubCommand) next).getCmd();
                cmd.getClass();
                KProperty kProperty = kPropertyArr[0];
                if (cmd.help$delegate.inner) {
                    arrayList.add(next);
                }
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                arrayList2.add(((SubCommand) it2.next()).getCmd());
            }
            KProperty kProperty2 = kPropertyArr[0];
            if (this.help$delegate.inner) {
                help(printWriter);
                return;
            }
            if (!arrayList2.isEmpty()) {
                Iterator it3 = arrayList2.iterator();
                while (it3.hasNext()) {
                    ((ParseableCommand) it3.next()).help(printWriter);
                }
                return;
            }
            if (parse) {
                execute(printWriter);
                return;
            }
            ArrayList arrayList3 = new ArrayList();
            if (!((ArrayList) commandParser.getUnhandledParams()).isEmpty()) {
                List unhandledParams = commandParser.getUnhandledParams();
                ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(unhandledParams, 10));
                Iterator it4 = ((ArrayList) unhandledParams).iterator();
                while (it4.hasNext()) {
                    arrayList4.add(((Param) it4.next()).getLongName());
                }
                arrayList3.add("No values passed for required params: " + arrayList4);
            }
            if (!((ArrayList) commandParser.getUnhandledSubCmds()).isEmpty()) {
                List unhandledSubCmds = commandParser.getUnhandledSubCmds();
                ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(unhandledSubCmds, 10));
                Iterator it5 = ((ArrayList) unhandledSubCmds).iterator();
                while (it5.hasNext()) {
                    arrayList5.add(((SubCommand) it5.next()).getLongName());
                }
                arrayList3.addAll(arrayList5);
                List unhandledSubCmds2 = commandParser.getUnhandledSubCmds();
                ArrayList arrayList6 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(unhandledSubCmds2, 10));
                Iterator it6 = ((ArrayList) unhandledSubCmds2).iterator();
                while (it6.hasNext()) {
                    arrayList6.add(((SubCommand) it6.next()).getShortName());
                }
                arrayList3.add("No values passed for required sub-commands: " + arrayList6);
            }
            Iterator it7 = arrayList3.iterator();
            while (it7.hasNext()) {
                printWriter.println((String) it7.next());
            }
        } catch (ArgParseError e) {
            printWriter.println(e.getMessage());
        } catch (Exception e2) {
            printWriter.println("Unknown exception encountered during parse");
            printWriter.println(e2);
        }
    }

    public final Flag flag(String str, String str2, String str3) {
        if (!(str2 == null || str2.length() == 1)) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Flag short name must be one character long, or null. Got (", str2, ")"));
        }
        if (!(true ^ str.startsWith("-"))) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Flags must not start with '-'. Got $(", str, ")"));
        }
        String concat = str2 != null ? "-".concat(str2) : null;
        String concat2 = "--".concat(str);
        CommandParser commandParser = this.parser;
        String checkCliNames = commandParser.checkCliNames(concat, concat2);
        if (checkCliNames != null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Detected reused flag name (", checkCliNames, ")"));
        }
        if (concat != null) {
            commandParser.tokenSet.add(concat);
        }
        commandParser.tokenSet.add(concat2);
        Flag flag = new Flag(concat, concat2, str3);
        ((ArrayList) commandParser._flags).add(flag);
        return flag;
    }

    public final void help(PrintWriter printWriter) {
        final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        String str = this.name;
        int length = str.length() + 2;
        indentingPrintWriter.println("┌" + StringsKt__StringsJVMKt.repeat(length, "─") + "┐");
        indentingPrintWriter.println("│ " + str + " │");
        indentingPrintWriter.println("└" + StringsKt__StringsJVMKt.repeat(length, "─") + "┘");
        indentingPrintWriter.println();
        ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.ParseableCommand$help$3
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ParseableCommand.this.usage(indentingPrintWriter);
                return Unit.INSTANCE;
            }
        });
        if (this.description != null) {
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.ParseableCommand$help$4
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    indentingPrintWriter.println(this.description);
                    return Unit.INSTANCE;
                }
            });
            indentingPrintWriter.println();
        }
        CommandParser commandParser = this.parser;
        final ArrayList arrayList = (ArrayList) commandParser.flags;
        if (!arrayList.isEmpty()) {
            indentingPrintWriter.println("FLAGS:");
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.ParseableCommand$help$5
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    List<Flag> list = arrayList;
                    IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        ((Flag) it.next()).describe(indentingPrintWriter2);
                        indentingPrintWriter2.println();
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        List list = commandParser.params;
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (((Param) next) instanceof SingleArgParam) {
                arrayList2.add(next);
            } else {
                arrayList3.add(next);
            }
        }
        Pair pair = new Pair(arrayList2, arrayList3);
        List list2 = (List) pair.component1();
        List list3 = (List) pair.component2();
        boolean z = !list2.isEmpty();
        Companion companion = Companion;
        if (z) {
            indentingPrintWriter.println("REQUIRED PARAMS:");
            final List list4 = list2;
            companion.getClass();
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.ParseableCommand$Companion$describe$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Iterable<Describable> iterable = list4;
                    IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                    Iterator<Describable> it2 = iterable.iterator();
                    while (it2.hasNext()) {
                        it2.next().describe(indentingPrintWriter2);
                        indentingPrintWriter2.println();
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        if (!list3.isEmpty()) {
            indentingPrintWriter.println("OPTIONAL PARAMS:");
            final List list5 = list3;
            companion.getClass();
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.ParseableCommand$Companion$describe$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Iterable<Describable> iterable = list5;
                    IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                    Iterator<Describable> it2 = iterable.iterator();
                    while (it2.hasNext()) {
                        it2.next().describe(indentingPrintWriter2);
                        indentingPrintWriter2.println();
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        List list6 = commandParser.subCommands;
        ArrayList arrayList4 = new ArrayList();
        ArrayList arrayList5 = new ArrayList();
        Iterator it2 = ((ArrayList) list6).iterator();
        while (it2.hasNext()) {
            Object next2 = it2.next();
            if (((SubCommand) next2) instanceof RequiredSubCommand) {
                arrayList4.add(next2);
            } else {
                arrayList5.add(next2);
            }
        }
        Pair pair2 = new Pair(arrayList4, arrayList5);
        List list7 = (List) pair2.component1();
        List list8 = (List) pair2.component2();
        if (!list7.isEmpty()) {
            indentingPrintWriter.println("REQUIRED SUBCOMMANDS:");
            final List list9 = list7;
            companion.getClass();
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.ParseableCommand$Companion$describe$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Iterable<Describable> iterable = list9;
                    IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                    Iterator<Describable> it22 = iterable.iterator();
                    while (it22.hasNext()) {
                        it22.next().describe(indentingPrintWriter2);
                        indentingPrintWriter2.println();
                    }
                    return Unit.INSTANCE;
                }
            });
        }
        if (!list8.isEmpty()) {
            indentingPrintWriter.println("OPTIONAL SUBCOMMANDS:");
            final List list10 = list8;
            companion.getClass();
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.ParseableCommand$Companion$describe$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Iterable<Describable> iterable = list10;
                    IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                    Iterator<Describable> it22 = iterable.iterator();
                    while (it22.hasNext()) {
                        it22.next().describe(indentingPrintWriter2);
                        indentingPrintWriter2.println();
                    }
                    return Unit.INSTANCE;
                }
            });
        }
    }

    public final SingleArgParamOptional param(String str, String str2, String str3, ValueParser valueParser) {
        if (!(str2 == null || str2.length() == 1)) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Parameter short name must be one character long, or null. Got (", str2, ")"));
        }
        if (!(true ^ str.startsWith("-"))) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Parameters must not start with '-'. Got $(", str, ")"));
        }
        String concat = str2 != null ? "-".concat(str2) : null;
        String concat2 = "--".concat(str);
        CommandParser commandParser = this.parser;
        String checkCliNames = commandParser.checkCliNames(concat, concat2);
        if (checkCliNames != null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Detected reused param name (", checkCliNames, ")"));
        }
        if (concat != null) {
            commandParser.tokenSet.add(concat);
        }
        commandParser.tokenSet.add(concat2);
        SingleArgParamOptional singleArgParamOptional = new SingleArgParamOptional(concat2, concat, str3, valueParser);
        ((ArrayList) commandParser._params).add(singleArgParamOptional);
        return singleArgParamOptional;
    }

    public final SingleArgParam required(SingleArgParamOptional singleArgParamOptional) {
        CommandParser commandParser = this.parser;
        commandParser.getClass();
        SingleArgParam singleArgParam = new SingleArgParam(singleArgParamOptional.longName, singleArgParamOptional.shortName, singleArgParamOptional.description, singleArgParamOptional.valueParser);
        ((ArrayList) commandParser._params).remove(singleArgParamOptional);
        ((ArrayList) commandParser._params).add(singleArgParam);
        return singleArgParam;
    }

    public final OptionalSubCommand subCommand(ParseableCommand parseableCommand) {
        CommandParser commandParser = this.parser;
        commandParser.getClass();
        String str = parseableCommand.name;
        String checkCliNames = commandParser.checkCliNames(null, str);
        if (checkCliNames != null) {
            throw new IllegalArgumentException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot re-use name for subcommand (", checkCliNames, ")"));
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
