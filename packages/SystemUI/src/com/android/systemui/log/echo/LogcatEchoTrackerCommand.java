package com.android.systemui.log.echo;

import android.util.IndentingPrintWriter;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.echo.Outcome;
import com.android.systemui.statusbar.commandline.Flag;
import com.android.systemui.statusbar.commandline.ParseableCommand;
import com.android.systemui.statusbar.commandline.SingleArgParamOptional;
import com.android.systemui.statusbar.commandline.Type;
import com.android.systemui.statusbar.commandline.ValueParserKt$parseString$1;
import com.samsung.android.knox.zt.config.BuildConfig;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.ReflectionFactory;
import kotlin.reflect.KProperty;
import kotlin.text.MatcherMatchResult;
import kotlin.text.MatcherMatchResult$groupValues$1;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LogcatEchoTrackerCommand extends ParseableCommand {
    public static final /* synthetic */ KProperty[] $$delegatedProperties;
    public final SingleArgParamOptional buffer$delegate;
    public final Flag clearAll$delegate;
    public final LogcatEchoTrackerDebug echoTracker;
    public final Flag list$delegate;
    public final SingleArgParamOptional tag$delegate;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        PropertyReference1Impl propertyReference1Impl = new PropertyReference1Impl(LogcatEchoTrackerCommand.class, "buffer", "getBuffer()Ljava/lang/String;", 0);
        ReflectionFactory reflectionFactory = Reflection.factory;
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl2 = new PropertyReference1Impl(LogcatEchoTrackerCommand.class, "tag", "getTag()Ljava/lang/String;", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl3 = new PropertyReference1Impl(LogcatEchoTrackerCommand.class, "clearAll", "getClearAll()Z", 0);
        reflectionFactory.getClass();
        PropertyReference1Impl propertyReference1Impl4 = new PropertyReference1Impl(LogcatEchoTrackerCommand.class, "list", "getList()Z", 0);
        reflectionFactory.getClass();
        $$delegatedProperties = new KProperty[]{propertyReference1Impl, propertyReference1Impl2, propertyReference1Impl3, propertyReference1Impl4};
        new Companion(null);
    }

    public LogcatEchoTrackerCommand(LogcatEchoTrackerDebug logcatEchoTrackerDebug) {
        super("echo", null, 2, 0 == true ? 1 : 0);
        this.echoTracker = logcatEchoTrackerDebug;
        Type.INSTANCE.getClass();
        ValueParserKt$parseString$1 valueParserKt$parseString$1 = Type.String;
        this.buffer$delegate = param("buffer", "b", "Modifies the echo level of a buffer. Use the form <name>:<level>, e.g. 'Foo:V'. Valid levels are V,D,I,W,E, and -. The - level clears any pre-existing override.", valueParserKt$parseString$1);
        this.tag$delegate = param("tag", "t", "Modifies the echo level of a tag. Use the form <name>:<level>, e.g. 'Foo:V'. Valid levels are V,D,I,W,E, and -. The - level clears any pre-existing override.", valueParserKt$parseString$1);
        this.clearAll$delegate = flag("clear-all", null, "Removes all local echo level overrides");
        this.list$delegate = flag("list", null, "Lists all local echo level overrides");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Type inference failed for: r0v6, types: [kotlin.text.MatcherMatchResult$groupValues$1] */
    /* JADX WARN: Type inference failed for: r1v23, types: [kotlin.text.MatcherMatchResult$groupValues$1] */
    public static Outcome parseTagStructure(String str, EchoOverrideType echoOverrideType) {
        final MatcherMatchResult matchEntire = LogcatEchoTrackerCommandKt.OVERRIDE_PATTERN.matchEntire(str);
        if (matchEntire == null) {
            return new Outcome.Failure("Cannot parse override format, must be `<name>:<level>`");
        }
        if (matchEntire.groupValues_ == null) {
            matchEntire.groupValues_ = new AbstractList() { // from class: kotlin.text.MatcherMatchResult$groupValues$1
                @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
                public final /* bridge */ boolean contains(Object obj) {
                    if (obj instanceof String) {
                        return super.contains((String) obj);
                    }
                    return false;
                }

                @Override // java.util.List
                public final Object get(int i) {
                    String group = MatcherMatchResult.this.matcher.group(i);
                    return group == null ? "" : group;
                }

                @Override // kotlin.collections.AbstractCollection
                public final int getSize() {
                    return MatcherMatchResult.this.matcher.groupCount() + 1;
                }

                @Override // kotlin.collections.AbstractList, java.util.List
                public final /* bridge */ int indexOf(Object obj) {
                    if (obj instanceof String) {
                        return super.indexOf((String) obj);
                    }
                    return -1;
                }

                @Override // kotlin.collections.AbstractList, java.util.List
                public final /* bridge */ int lastIndexOf(Object obj) {
                    if (obj instanceof String) {
                        return super.lastIndexOf((String) obj);
                    }
                    return -1;
                }
            };
        }
        MatcherMatchResult$groupValues$1 matcherMatchResult$groupValues$1 = matchEntire.groupValues_;
        Intrinsics.checkNotNull(matcherMatchResult$groupValues$1);
        String str2 = (String) matcherMatchResult$groupValues$1.get(1);
        if (matchEntire.groupValues_ == null) {
            matchEntire.groupValues_ = new AbstractList() { // from class: kotlin.text.MatcherMatchResult$groupValues$1
                @Override // kotlin.collections.AbstractCollection, java.util.Collection, java.util.List
                public final /* bridge */ boolean contains(Object obj) {
                    if (obj instanceof String) {
                        return super.contains((String) obj);
                    }
                    return false;
                }

                @Override // java.util.List
                public final Object get(int i) {
                    String group = MatcherMatchResult.this.matcher.group(i);
                    return group == null ? "" : group;
                }

                @Override // kotlin.collections.AbstractCollection
                public final int getSize() {
                    return MatcherMatchResult.this.matcher.groupCount() + 1;
                }

                @Override // kotlin.collections.AbstractList, java.util.List
                public final /* bridge */ int indexOf(Object obj) {
                    if (obj instanceof String) {
                        return super.indexOf((String) obj);
                    }
                    return -1;
                }

                @Override // kotlin.collections.AbstractList, java.util.List
                public final /* bridge */ int lastIndexOf(Object obj) {
                    if (obj instanceof String) {
                        return super.lastIndexOf((String) obj);
                    }
                    return -1;
                }
            };
        }
        MatcherMatchResult$groupValues$1 matcherMatchResult$groupValues$12 = matchEntire.groupValues_;
        Intrinsics.checkNotNull(matcherMatchResult$groupValues$12);
        String str3 = (String) matcherMatchResult$groupValues$12.get(2);
        LogLevel logLevel = null;
        if (Intrinsics.areEqual(str3, "-")) {
            return new Outcome.Success(new ParsedOverride(echoOverrideType, str2, null));
        }
        String lowerCase = str3.toLowerCase(Locale.ROOT);
        switch (lowerCase.hashCode()) {
            case -1408208058:
                if (lowerCase.equals("assert")) {
                    logLevel = LogLevel.WTF;
                    break;
                }
                break;
            case 100:
                if (lowerCase.equals("d")) {
                    logLevel = LogLevel.DEBUG;
                    break;
                }
                break;
            case 101:
                if (lowerCase.equals("e")) {
                    logLevel = LogLevel.ERROR;
                    break;
                }
                break;
            case 105:
                if (lowerCase.equals("i")) {
                    logLevel = LogLevel.INFO;
                    break;
                }
                break;
            case 118:
                if (lowerCase.equals("v")) {
                    logLevel = LogLevel.VERBOSE;
                    break;
                }
                break;
            case 119:
                if (lowerCase.equals("w")) {
                    logLevel = LogLevel.WARNING;
                    break;
                }
                break;
            case 118057:
                if (lowerCase.equals("wtf")) {
                    logLevel = LogLevel.WTF;
                    break;
                }
                break;
            case 3237038:
                if (lowerCase.equals("info")) {
                    logLevel = LogLevel.INFO;
                    break;
                }
                break;
            case 3641990:
                if (lowerCase.equals("warn")) {
                    logLevel = LogLevel.WARNING;
                    break;
                }
                break;
            case 95458899:
                if (lowerCase.equals(BuildConfig.BUILD_TYPE)) {
                    logLevel = LogLevel.DEBUG;
                    break;
                }
                break;
            case 96784904:
                if (lowerCase.equals("error")) {
                    logLevel = LogLevel.ERROR;
                    break;
                }
                break;
            case 351107458:
                if (lowerCase.equals("verbose")) {
                    logLevel = LogLevel.VERBOSE;
                    break;
                }
                break;
            case 1124446108:
                if (lowerCase.equals("warning")) {
                    logLevel = LogLevel.WARNING;
                    break;
                }
                break;
        }
        return logLevel == null ? new Outcome.Failure(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Unrecognized level ", str3, ". Must be one of 'v,d,i,w,e,-'")) : new Outcome.Success(new ParsedOverride(echoOverrideType, str2, logLevel));
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void execute(PrintWriter printWriter) {
        KProperty[] kPropertyArr = $$delegatedProperties;
        KProperty kProperty = kPropertyArr[0];
        String str = (String) this.buffer$delegate.getValue();
        KProperty kProperty2 = kPropertyArr[1];
        String str2 = (String) this.tag$delegate.getValue();
        LogcatEchoTrackerDebug logcatEchoTrackerDebug = this.echoTracker;
        if (str != null) {
            Outcome parseTagStructure = parseTagStructure(str, EchoOverrideType.BUFFER);
            if (!(parseTagStructure instanceof Outcome.Success)) {
                if (parseTagStructure instanceof Outcome.Failure) {
                    printWriter.println(((Outcome.Failure) parseTagStructure).message);
                    return;
                }
                return;
            } else {
                ParsedOverride parsedOverride = (ParsedOverride) ((Outcome.Success) parseTagStructure).value;
                EchoOverrideType echoOverrideType = parsedOverride.type;
                logcatEchoTrackerDebug.getClass();
                LogcatEchoTrackerDebug$setEchoLevel$1 logcatEchoTrackerDebug$setEchoLevel$1 = new LogcatEchoTrackerDebug$setEchoLevel$1(logcatEchoTrackerDebug, echoOverrideType, parsedOverride.level, parsedOverride.name, null);
                BuildersKt.launch$default(logcatEchoTrackerDebug.applicationScope, logcatEchoTrackerDebug.sequentialBgDispatcher, null, logcatEchoTrackerDebug$setEchoLevel$1, 2);
                return;
            }
        }
        if (str2 != null) {
            Outcome parseTagStructure2 = parseTagStructure(str2, EchoOverrideType.TAG);
            if (!(parseTagStructure2 instanceof Outcome.Success)) {
                if (parseTagStructure2 instanceof Outcome.Failure) {
                    printWriter.println(((Outcome.Failure) parseTagStructure2).message);
                    return;
                }
                return;
            } else {
                ParsedOverride parsedOverride2 = (ParsedOverride) ((Outcome.Success) parseTagStructure2).value;
                EchoOverrideType echoOverrideType2 = parsedOverride2.type;
                logcatEchoTrackerDebug.getClass();
                LogcatEchoTrackerDebug$setEchoLevel$1 logcatEchoTrackerDebug$setEchoLevel$12 = new LogcatEchoTrackerDebug$setEchoLevel$1(logcatEchoTrackerDebug, echoOverrideType2, parsedOverride2.level, parsedOverride2.name, null);
                BuildersKt.launch$default(logcatEchoTrackerDebug.applicationScope, logcatEchoTrackerDebug.sequentialBgDispatcher, null, logcatEchoTrackerDebug$setEchoLevel$12, 2);
                return;
            }
        }
        KProperty kProperty3 = kPropertyArr[2];
        if (this.clearAll$delegate.inner) {
            logcatEchoTrackerDebug.getClass();
            LogcatEchoTrackerDebug$clearAllOverrides$1 logcatEchoTrackerDebug$clearAllOverrides$1 = new LogcatEchoTrackerDebug$clearAllOverrides$1(logcatEchoTrackerDebug, null);
            BuildersKt.launch$default(logcatEchoTrackerDebug.applicationScope, logcatEchoTrackerDebug.sequentialBgDispatcher, null, logcatEchoTrackerDebug$clearAllOverrides$1, 2);
            return;
        }
        KProperty kProperty4 = kPropertyArr[3];
        if (!this.list$delegate.inner) {
            printWriter.println("You must specify one of --buffer, --tag, --list, or --clear-all");
            return;
        }
        Iterator it = ((ArrayList) logcatEchoTrackerDebug.listEchoOverrides()).iterator();
        while (it.hasNext()) {
            LogcatEchoOverride logcatEchoOverride = (LogcatEchoOverride) it.next();
            printWriter.print(StringsKt__StringsKt.padEnd$default(8, logcatEchoOverride.type.toString()));
            printWriter.print(StringsKt__StringsKt.padEnd$default(10, logcatEchoOverride.level.toString()));
            printWriter.print(logcatEchoOverride.name);
            printWriter.println();
        }
    }

    @Override // com.android.systemui.statusbar.commandline.ParseableCommand
    public final void usage(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Usage:");
        indentingPrintWriter.println();
        indentingPrintWriter.println("echo -b MyBufferName:V    // Set echo level of a buffer to verbose");
        indentingPrintWriter.println("echo -t MyTagName:V       // Set echo level of a tag to verbose");
        indentingPrintWriter.println();
        indentingPrintWriter.println("echo -b MyBufferName:-    // Clear any echo overrides for a buffer");
        indentingPrintWriter.println("echo -t MyTagName:-       // Clear any echo overrides for a tag");
        indentingPrintWriter.println();
        indentingPrintWriter.println("echo --list               // List all current echo overrides");
        indentingPrintWriter.println("echo --clear-all          // Clear all echo overrides");
        indentingPrintWriter.println();
    }
}
