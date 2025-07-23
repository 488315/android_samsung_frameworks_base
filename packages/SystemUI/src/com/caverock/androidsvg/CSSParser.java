package com.caverock.androidsvg;

import android.util.Log;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CSSParser {
    public final MediaType deviceMediaType;
    public boolean inMediaRule;
    public final Source source;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.caverock.androidsvg.CSSParser$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$CSSParser$AttribOp;
        public static final /* synthetic */ int[] $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents;

        static {
            int[] iArr = new int[PseudoClassIdents.values().length];
            $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents = iArr;
            try {
                iArr[PseudoClassIdents.first_child.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.last_child.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.only_child.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.first_of_type.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.last_of_type.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.only_of_type.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.root.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.empty.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.nth_child.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.nth_last_child.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.nth_of_type.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.nth_last_of_type.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.not.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.target.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.lang.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.link.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.visited.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.hover.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.active.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.focus.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.enabled.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.disabled.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.checked.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[PseudoClassIdents.indeterminate.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            int[] iArr2 = new int[AttribOp.values().length];
            $SwitchMap$com$caverock$androidsvg$CSSParser$AttribOp = iArr2;
            try {
                iArr2[AttribOp.EQUALS.ordinal()] = 1;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$AttribOp[AttribOp.INCLUDES.ordinal()] = 2;
            } catch (NoSuchFieldError unused26) {
            }
            try {
                $SwitchMap$com$caverock$androidsvg$CSSParser$AttribOp[AttribOp.DASHMATCH.ordinal()] = 3;
            } catch (NoSuchFieldError unused27) {
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Attrib {
        public final String name;
        public final AttribOp operation;
        public final String value;

        public Attrib(String str, AttribOp attribOp, String str2) {
            this.name = str;
            this.operation = attribOp;
            this.value = str2;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum AttribOp {
        EXISTS,
        EQUALS,
        INCLUDES,
        DASHMATCH
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CSSTextScanner extends SVGParser.TextScanner {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class AnPlusB {
            public final int a;
            public final int b;

            public AnPlusB(int i, int i2) {
                this.a = i;
                this.b = i2;
            }
        }

        public CSSTextScanner(String str) {
            super(str.replaceAll("(?s)/\\*.*?\\*/", ""));
        }

        public static int hexChar(int i) {
            if (i >= 48 && i <= 57) {
                return i - 48;
            }
            if (i >= 65 && i <= 70) {
                return i - 55;
            }
            if (i < 97 || i > 102) {
                return -1;
            }
            return i - 87;
        }

        public final String nextCSSString() {
            int hexChar;
            if (empty()) {
                return null;
            }
            char charAt = this.input.charAt(this.position);
            if (charAt != '\'' && charAt != '\"') {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            this.position++;
            int intValue = nextChar().intValue();
            while (intValue != -1 && intValue != charAt) {
                if (intValue == 92) {
                    intValue = nextChar().intValue();
                    if (intValue != -1) {
                        if (intValue == 10 || intValue == 13 || intValue == 12) {
                            intValue = nextChar().intValue();
                        } else {
                            int hexChar2 = hexChar(intValue);
                            if (hexChar2 != -1) {
                                for (int i = 1; i <= 5 && (hexChar = hexChar((intValue = nextChar().intValue()))) != -1; i++) {
                                    hexChar2 = (hexChar2 * 16) + hexChar;
                                }
                                sb.append((char) hexChar2);
                            }
                        }
                    }
                }
                sb.append((char) intValue);
                intValue = nextChar().intValue();
            }
            return sb.toString();
        }

        public final String nextIdentifier() {
            int i;
            int i2;
            boolean empty = empty();
            String str = this.input;
            if (empty) {
                i2 = this.position;
            } else {
                int i3 = this.position;
                int charAt = str.charAt(i3);
                if (charAt == 45) {
                    charAt = advanceChar();
                }
                if ((charAt < 65 || charAt > 90) && ((charAt < 97 || charAt > 122) && charAt != 95)) {
                    i = i3;
                } else {
                    int advanceChar = advanceChar();
                    while (true) {
                        if ((advanceChar < 65 || advanceChar > 90) && ((advanceChar < 97 || advanceChar > 122) && !((advanceChar >= 48 && advanceChar <= 57) || advanceChar == 45 || advanceChar == 95))) {
                            break;
                        }
                        advanceChar = advanceChar();
                    }
                    i = this.position;
                }
                this.position = i3;
                i2 = i;
            }
            int i4 = this.position;
            if (i2 == i4) {
                return null;
            }
            String substring = str.substring(i4, i2);
            this.position = i2;
            return substring;
        }

        /* JADX WARN: Code restructure failed: missing block: B:226:0x04a5, code lost:
        
            r0 = r4.simpleSelectors;
         */
        /* JADX WARN: Code restructure failed: missing block: B:227:0x04a7, code lost:
        
            if (r0 == null) goto L275;
         */
        /* JADX WARN: Code restructure failed: missing block: B:229:0x04af, code lost:
        
            if (((java.util.ArrayList) r0).isEmpty() == false) goto L274;
         */
        /* JADX WARN: Code restructure failed: missing block: B:230:0x04b2, code lost:
        
            r1.add(r4);
         */
        /* JADX WARN: Code restructure failed: missing block: B:231:0x04b5, code lost:
        
            return r1;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:140:0x03a7  */
        /* JADX WARN: Removed duplicated region for block: B:142:0x03c0 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:153:0x03a2  */
        /* JADX WARN: Removed duplicated region for block: B:212:0x047d  */
        /* JADX WARN: Removed duplicated region for block: B:224:0x04a3 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x005c  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x007b  */
        /* JADX WARN: Removed duplicated region for block: B:293:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x045f  */
        /* JADX WARN: Removed duplicated region for block: B:80:0x027d  */
        /* JADX WARN: Removed duplicated region for block: B:92:0x02a4 A[SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r11v12, types: [com.caverock.androidsvg.CSSParser$PseudoClassOnlyChild] */
        /* JADX WARN: Type inference failed for: r11v16, types: [com.caverock.androidsvg.CSSParser$PseudoClassOnlyChild] */
        /* JADX WARN: Type inference failed for: r11v17, types: [com.caverock.androidsvg.CSSParser$PseudoClassRoot] */
        /* JADX WARN: Type inference failed for: r11v18, types: [com.caverock.androidsvg.CSSParser$PseudoClassEmpty] */
        /* JADX WARN: Type inference failed for: r11v31, types: [com.caverock.androidsvg.CSSParser$PseudoClassNot] */
        /* JADX WARN: Type inference failed for: r11v32, types: [com.caverock.androidsvg.CSSParser$PseudoClassTarget] */
        /* JADX WARN: Type inference failed for: r11v34, types: [com.caverock.androidsvg.CSSParser$PseudoClassNotSupported] */
        /* JADX WARN: Type inference failed for: r11v39, types: [com.caverock.androidsvg.CSSParser$PseudoClassNotSupported] */
        /* JADX WARN: Type inference failed for: r2v0 */
        /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v11 */
        /* JADX WARN: Type inference failed for: r2v2, types: [com.caverock.androidsvg.CSSParser$1, java.lang.String] */
        /* JADX WARN: Type inference failed for: r2v41 */
        /* JADX WARN: Type inference failed for: r2v7 */
        /* JADX WARN: Type inference failed for: r2v8 */
        /* JADX WARN: Type inference failed for: r2v9 */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List nextSelectorGroup() {
            /*
                Method dump skipped, instructions count: 1258
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.CSSParser.CSSTextScanner.nextSelectorGroup():java.util.List");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum Combinator {
        DESCENDANT,
        CHILD,
        FOLLOWS
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum MediaType {
        all,
        /* JADX INFO: Fake field, exist only in values array */
        aural,
        /* JADX INFO: Fake field, exist only in values array */
        braille,
        /* JADX INFO: Fake field, exist only in values array */
        embossed,
        /* JADX INFO: Fake field, exist only in values array */
        handheld,
        /* JADX INFO: Fake field, exist only in values array */
        print,
        /* JADX INFO: Fake field, exist only in values array */
        projection,
        screen,
        /* JADX INFO: Fake field, exist only in values array */
        speech,
        /* JADX INFO: Fake field, exist only in values array */
        tty,
        /* JADX INFO: Fake field, exist only in values array */
        tv
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface PseudoClass {
        boolean matches(SVG.SvgElementBase svgElementBase);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PseudoClassAnPlusB implements PseudoClass {
        public final int a;
        public final int b;
        public final boolean isFromStart;
        public final boolean isOfType;
        public final String nodeName;

        public PseudoClassAnPlusB(int i, int i2, boolean z, boolean z2, String str) {
            this.a = i;
            this.b = i2;
            this.isFromStart = z;
            this.isOfType = z2;
            this.nodeName = str;
        }

        /* JADX WARN: Removed duplicated region for block: B:29:0x0065 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0064 A[RETURN] */
        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean matches(com.caverock.androidsvg.SVG.SvgElementBase r8) {
            /*
                r7 = this;
                boolean r0 = r7.isOfType
                java.lang.String r1 = r7.nodeName
                if (r0 == 0) goto Lc
                if (r1 != 0) goto Lc
                java.lang.String r1 = r8.getNodeName()
            Lc:
                com.caverock.androidsvg.SVG$SvgContainer r0 = r8.parent
                r2 = 1
                r3 = 0
                if (r0 == 0) goto L3c
                java.util.List r0 = r0.getChildren()
                java.util.Iterator r0 = r0.iterator()
                r4 = r3
                r5 = r4
            L1c:
                boolean r6 = r0.hasNext()
                if (r6 == 0) goto L3e
                java.lang.Object r6 = r0.next()
                com.caverock.androidsvg.SVG$SvgObject r6 = (com.caverock.androidsvg.SVG.SvgObject) r6
                com.caverock.androidsvg.SVG$SvgElementBase r6 = (com.caverock.androidsvg.SVG.SvgElementBase) r6
                if (r6 != r8) goto L2d
                r4 = r5
            L2d:
                if (r1 == 0) goto L39
                java.lang.String r6 = r6.getNodeName()
                boolean r6 = r6.equals(r1)
                if (r6 == 0) goto L1c
            L39:
                int r5 = r5 + 1
                goto L1c
            L3c:
                r5 = r2
                r4 = r3
            L3e:
                boolean r8 = r7.isFromStart
                if (r8 == 0) goto L44
                int r4 = r4 + r2
                goto L46
            L44:
                int r4 = r5 - r4
            L46:
                int r8 = r7.a
                int r7 = r7.b
                if (r8 != 0) goto L4f
                if (r4 != r7) goto L65
                goto L64
            L4f:
                int r4 = r4 - r7
                int r7 = r4 % r8
                if (r7 != 0) goto L65
                int r7 = java.lang.Integer.signum(r4)
                if (r7 == 0) goto L64
                int r7 = java.lang.Integer.signum(r4)
                int r8 = java.lang.Integer.signum(r8)
                if (r7 != r8) goto L65
            L64:
                return r2
            L65:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.caverock.androidsvg.CSSParser.PseudoClassAnPlusB.matches(com.caverock.androidsvg.SVG$SvgElementBase):boolean");
        }

        public final String toString() {
            String str = this.isFromStart ? "" : "last-";
            boolean z = this.isOfType;
            int i = this.b;
            int i2 = this.a;
            return z ? String.format("nth-%schild(%dn%+d of type <%s>)", str, Integer.valueOf(i2), Integer.valueOf(i), this.nodeName) : String.format("nth-%schild(%dn%+d)", str, Integer.valueOf(i2), Integer.valueOf(i));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PseudoClassEmpty implements PseudoClass {
        private PseudoClassEmpty() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(SVG.SvgElementBase svgElementBase) {
            return !(svgElementBase instanceof SVG.SvgContainer) || ((SVG.SvgContainer) svgElementBase).getChildren().size() == 0;
        }

        public final String toString() {
            return "empty";
        }

        public /* synthetic */ PseudoClassEmpty(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum PseudoClassIdents {
        target,
        root,
        nth_child,
        nth_last_child,
        nth_of_type,
        nth_last_of_type,
        first_child,
        last_child,
        first_of_type,
        last_of_type,
        only_child,
        only_of_type,
        empty,
        not,
        lang,
        link,
        visited,
        hover,
        active,
        focus,
        enabled,
        disabled,
        checked,
        indeterminate,
        UNSUPPORTED;

        public static final Map cache = new HashMap();

        static {
            for (PseudoClassIdents pseudoClassIdents : values()) {
                if (pseudoClassIdents != UNSUPPORTED) {
                    cache.put(pseudoClassIdents.name().replace('_', '-'), pseudoClassIdents);
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PseudoClassNot implements PseudoClass {
        public final List selectorGroup;

        public PseudoClassNot(List<Selector> list) {
            this.selectorGroup = list;
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(SVG.SvgElementBase svgElementBase) {
            Iterator it = this.selectorGroup.iterator();
            while (it.hasNext()) {
                if (CSSParser.ruleMatch((Selector) it.next(), svgElementBase)) {
                    return false;
                }
            }
            return true;
        }

        public final String toString() {
            return "not(" + this.selectorGroup + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PseudoClassNotSupported implements PseudoClass {
        public final String clazz;

        public PseudoClassNotSupported(String str) {
            this.clazz = str;
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(SVG.SvgElementBase svgElementBase) {
            return false;
        }

        public final String toString() {
            return this.clazz;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PseudoClassOnlyChild implements PseudoClass {
        public final boolean isOfType;
        public final String nodeName;

        public PseudoClassOnlyChild(boolean z, String str) {
            this.isOfType = z;
            this.nodeName = str;
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(SVG.SvgElementBase svgElementBase) {
            int i;
            boolean z = this.isOfType;
            String str = this.nodeName;
            if (z && str == null) {
                str = svgElementBase.getNodeName();
            }
            SVG.SvgContainer svgContainer = svgElementBase.parent;
            if (svgContainer != null) {
                Iterator it = svgContainer.getChildren().iterator();
                i = 0;
                while (it.hasNext()) {
                    SVG.SvgElementBase svgElementBase2 = (SVG.SvgElementBase) ((SVG.SvgObject) it.next());
                    if (str == null || svgElementBase2.getNodeName().equals(str)) {
                        i++;
                    }
                }
            } else {
                i = 1;
            }
            return i == 1;
        }

        public final String toString() {
            return this.isOfType ? TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("only-of-type <"), this.nodeName, ">") : "only-child";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PseudoClassRoot implements PseudoClass {
        private PseudoClassRoot() {
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(SVG.SvgElementBase svgElementBase) {
            return svgElementBase.parent == null;
        }

        public final String toString() {
            return "root";
        }

        public /* synthetic */ PseudoClassRoot(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PseudoClassTarget implements PseudoClass {
        private PseudoClassTarget() {
        }

        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        public final boolean matches(SVG.SvgElementBase svgElementBase) {
            return false;
        }

        public final String toString() {
            return "target";
        }

        public /* synthetic */ PseudoClassTarget(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Rule {
        public final Selector selector;
        public final Source source;
        public final SVG.Style style;

        public Rule(Selector selector, SVG.Style style, Source source) {
            this.selector = selector;
            this.style = style;
            this.source = source;
        }

        public final String toString() {
            return String.valueOf(this.selector) + " {...} (src=" + this.source + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Ruleset {
        public List rules = null;

        public final void add(Rule rule) {
            if (this.rules == null) {
                this.rules = new ArrayList();
            }
            for (int i = 0; i < this.rules.size(); i++) {
                if (((Rule) this.rules.get(i)).selector.specificity > rule.selector.specificity) {
                    this.rules.add(i, rule);
                    return;
                }
            }
            this.rules.add(rule);
        }

        public final void addAll(Ruleset ruleset) {
            if (ruleset.rules == null) {
                return;
            }
            if (this.rules == null) {
                this.rules = new ArrayList(((ArrayList) ruleset.rules).size());
            }
            ArrayList arrayList = (ArrayList) ruleset.rules;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                add((Rule) obj);
            }
        }

        public final String toString() {
            if (this.rules == null) {
                return "";
            }
            StringBuilder sb = new StringBuilder();
            Iterator it = this.rules.iterator();
            while (it.hasNext()) {
                sb.append(((Rule) it.next()).toString());
                sb.append('\n');
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SimpleSelector {
        public final Combinator combinator;
        public final String tag;
        public List attribs = null;
        public List pseudos = null;

        public SimpleSelector(Combinator combinator, String str) {
            this.combinator = null;
            this.tag = null;
            this.combinator = combinator == null ? Combinator.DESCENDANT : combinator;
            this.tag = str;
        }

        public final void addAttrib(String str, AttribOp attribOp, String str2) {
            if (this.attribs == null) {
                this.attribs = new ArrayList();
            }
            ((ArrayList) this.attribs).add(new Attrib(str, attribOp, str2));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Combinator combinator = Combinator.CHILD;
            Combinator combinator2 = this.combinator;
            if (combinator2 == combinator) {
                sb.append("> ");
            } else if (combinator2 == Combinator.FOLLOWS) {
                sb.append("+ ");
            }
            String str = this.tag;
            if (str == null) {
                str = "*";
            }
            sb.append(str);
            List list = this.attribs;
            int i = 0;
            if (list != null) {
                ArrayList arrayList = (ArrayList) list;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    Attrib attrib = (Attrib) obj;
                    sb.append('[');
                    sb.append(attrib.name);
                    int i3 = AnonymousClass1.$SwitchMap$com$caverock$androidsvg$CSSParser$AttribOp[attrib.operation.ordinal()];
                    String str2 = attrib.value;
                    if (i3 == 1) {
                        sb.append('=');
                        sb.append(str2);
                    } else if (i3 == 2) {
                        sb.append("~=");
                        sb.append(str2);
                    } else if (i3 == 3) {
                        sb.append("|=");
                        sb.append(str2);
                    }
                    sb.append(']');
                }
            }
            List list2 = this.pseudos;
            if (list2 != null) {
                ArrayList arrayList2 = (ArrayList) list2;
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    sb.append(':');
                    sb.append((PseudoClass) obj2);
                }
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    enum Source {
        Document,
        /* JADX INFO: Fake field, exist only in values array */
        RenderOptions;

        public static final Source RenderOptions = null;
    }

    public CSSParser() {
        this(MediaType.screen, Source.Document);
    }

    public static int getChildPosition(List list, int i, SVG.SvgElementBase svgElementBase) {
        int i2 = 0;
        if (i < 0) {
            return 0;
        }
        Object obj = ((ArrayList) list).get(i);
        SVG.SvgContainer svgContainer = svgElementBase.parent;
        if (obj != svgContainer) {
            return -1;
        }
        Iterator it = svgContainer.getChildren().iterator();
        while (it.hasNext()) {
            if (((SVG.SvgObject) it.next()) == svgElementBase) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public static List parseMediaList(CSSTextScanner cSSTextScanner) {
        ArrayList arrayList = new ArrayList();
        while (!cSSTextScanner.empty()) {
            String str = null;
            if (!cSSTextScanner.empty()) {
                int i = cSSTextScanner.position;
                String str2 = cSSTextScanner.input;
                char charAt = str2.charAt(i);
                if ((charAt < 'A' || charAt > 'Z') && (charAt < 'a' || charAt > 'z')) {
                    cSSTextScanner.position = i;
                } else {
                    int advanceChar = cSSTextScanner.advanceChar();
                    while (true) {
                        if ((advanceChar < 65 || advanceChar > 90) && (advanceChar < 97 || advanceChar > 122)) {
                            break;
                        }
                        advanceChar = cSSTextScanner.advanceChar();
                    }
                    str = str2.substring(i, cSSTextScanner.position);
                }
            }
            if (str == null) {
                break;
            }
            try {
                arrayList.add(MediaType.valueOf(str));
            } catch (IllegalArgumentException unused) {
            }
            if (!cSSTextScanner.skipCommaWhitespace()) {
                break;
            }
        }
        return arrayList;
    }

    public static boolean ruleMatch(Selector selector, int i, List list, int i2, SVG.SvgElementBase svgElementBase) {
        SimpleSelector simpleSelector = (SimpleSelector) ((ArrayList) selector.simpleSelectors).get(i);
        if (!selectorMatch(simpleSelector, svgElementBase)) {
            return false;
        }
        Combinator combinator = Combinator.DESCENDANT;
        Combinator combinator2 = simpleSelector.combinator;
        if (combinator2 == combinator) {
            if (i != 0) {
                while (i2 >= 0) {
                    if (!ruleMatchOnAncestors(selector, i - 1, list, i2)) {
                        i2--;
                    }
                }
                return false;
            }
            return true;
        }
        if (combinator2 == Combinator.CHILD) {
            return ruleMatchOnAncestors(selector, i - 1, list, i2);
        }
        int childPosition = getChildPosition(list, i2, svgElementBase);
        if (childPosition <= 0) {
            return false;
        }
        return ruleMatch(selector, i - 1, list, i2, (SVG.SvgElementBase) svgElementBase.parent.getChildren().get(childPosition - 1));
    }

    public static boolean ruleMatchOnAncestors(Selector selector, int i, List list, int i2) {
        SimpleSelector simpleSelector = (SimpleSelector) ((ArrayList) selector.simpleSelectors).get(i);
        SVG.SvgElementBase svgElementBase = (SVG.SvgElementBase) ((ArrayList) list).get(i2);
        if (!selectorMatch(simpleSelector, svgElementBase)) {
            return false;
        }
        Combinator combinator = Combinator.DESCENDANT;
        Combinator combinator2 = simpleSelector.combinator;
        if (combinator2 == combinator) {
            if (i != 0) {
                while (i2 > 0) {
                    i2--;
                    if (ruleMatchOnAncestors(selector, i - 1, list, i2)) {
                    }
                }
                return false;
            }
            return true;
        }
        if (combinator2 == Combinator.CHILD) {
            return ruleMatchOnAncestors(selector, i - 1, list, i2 - 1);
        }
        int childPosition = getChildPosition(list, i2, svgElementBase);
        if (childPosition <= 0) {
            return false;
        }
        return ruleMatch(selector, i - 1, list, i2, (SVG.SvgElementBase) svgElementBase.parent.getChildren().get(childPosition - 1));
    }

    public static boolean selectorMatch(SimpleSelector simpleSelector, SVG.SvgElementBase svgElementBase) {
        List list;
        String str = simpleSelector.tag;
        if (str == null || str.equals(svgElementBase.getNodeName().toLowerCase(Locale.US))) {
            List list2 = simpleSelector.attribs;
            if (list2 != null) {
                ArrayList arrayList = (ArrayList) list2;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    Attrib attrib = (Attrib) obj;
                    String str2 = attrib.name;
                    str2.getClass();
                    String str3 = attrib.value;
                    if (!str2.equals("id")) {
                        if (!str2.equals("class") || (list = svgElementBase.classNames) == null || !list.contains(str3)) {
                            break;
                        }
                    } else if (!str3.equals(svgElementBase.id)) {
                        break;
                    }
                }
            }
            List list3 = simpleSelector.pseudos;
            if (list3 != null) {
                ArrayList arrayList2 = (ArrayList) list3;
                int size2 = arrayList2.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    if (!((PseudoClass) obj2).matches(svgElementBase)) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    public final void parseAtRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) {
        int intValue;
        int hexChar;
        String nextIdentifier = cSSTextScanner.nextIdentifier();
        cSSTextScanner.skipWhitespace();
        if (nextIdentifier == null) {
            throw new CSSParseException("Invalid '@' rule");
        }
        int i = 0;
        if (!this.inMediaRule && nextIdentifier.equals("media")) {
            List parseMediaList = parseMediaList(cSSTextScanner);
            if (!cSSTextScanner.consume('{')) {
                throw new CSSParseException("Invalid @media rule: missing rule set");
            }
            cSSTextScanner.skipWhitespace();
            MediaType mediaType = this.deviceMediaType;
            ArrayList arrayList = (ArrayList) parseMediaList;
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                MediaType mediaType2 = (MediaType) obj;
                if (mediaType2 == MediaType.all || mediaType2 == mediaType) {
                    this.inMediaRule = true;
                    ruleset.addAll(parseRuleset(cSSTextScanner));
                    this.inMediaRule = false;
                    break;
                }
            }
            parseRuleset(cSSTextScanner);
            if (!cSSTextScanner.empty() && !cSSTextScanner.consume('}')) {
                throw new CSSParseException("Invalid @media rule: expected '}' at end of rule set");
            }
        } else if (this.inMediaRule || !nextIdentifier.equals("import")) {
            Log.w("CSSParser", "Ignoring @" + nextIdentifier + " rule");
            while (!cSSTextScanner.empty() && ((intValue = cSSTextScanner.nextChar().intValue()) != 59 || i != 0)) {
                if (intValue == 123) {
                    i++;
                } else if (intValue == 125 && i > 0 && i - 1 == 0) {
                    break;
                }
            }
        } else {
            String str = null;
            if (!cSSTextScanner.empty()) {
                int i3 = cSSTextScanner.position;
                if (cSSTextScanner.consume("url(")) {
                    cSSTextScanner.skipWhitespace();
                    String nextCSSString = cSSTextScanner.nextCSSString();
                    if (nextCSSString == null) {
                        StringBuilder sb = new StringBuilder();
                        while (!cSSTextScanner.empty()) {
                            int i4 = cSSTextScanner.position;
                            String str2 = cSSTextScanner.input;
                            char charAt = str2.charAt(i4);
                            if (charAt == '\'' || charAt == '\"' || charAt == '(' || charAt == ')' || SVGParser.TextScanner.isWhitespace(charAt) || Character.isISOControl((int) charAt)) {
                                break;
                            }
                            cSSTextScanner.position++;
                            if (charAt == '\\') {
                                if (!cSSTextScanner.empty()) {
                                    int i5 = cSSTextScanner.position;
                                    cSSTextScanner.position = i5 + 1;
                                    charAt = str2.charAt(i5);
                                    if (charAt != '\n' && charAt != '\r' && charAt != '\f') {
                                        int hexChar2 = CSSTextScanner.hexChar(charAt);
                                        if (hexChar2 != -1) {
                                            for (int i6 = 1; i6 <= 5 && !cSSTextScanner.empty() && (hexChar = CSSTextScanner.hexChar(str2.charAt(cSSTextScanner.position))) != -1; i6++) {
                                                cSSTextScanner.position++;
                                                hexChar2 = (hexChar2 * 16) + hexChar;
                                            }
                                            sb.append((char) hexChar2);
                                        }
                                    }
                                }
                            }
                            sb.append(charAt);
                        }
                        nextCSSString = sb.length() == 0 ? null : sb.toString();
                    }
                    if (nextCSSString == null) {
                        cSSTextScanner.position = i3;
                    } else {
                        cSSTextScanner.skipWhitespace();
                        if (cSSTextScanner.empty() || cSSTextScanner.consume(")")) {
                            str = nextCSSString;
                        } else {
                            cSSTextScanner.position = i3;
                        }
                    }
                }
            }
            if (str == null) {
                str = cSSTextScanner.nextCSSString();
            }
            if (str == null) {
                throw new CSSParseException("Invalid @import rule: expected string or url()");
            }
            cSSTextScanner.skipWhitespace();
            parseMediaList(cSSTextScanner);
            if (!cSSTextScanner.empty() && !cSSTextScanner.consume(';')) {
                throw new CSSParseException("Invalid @media rule: expected '}' at end of rule set");
            }
        }
        cSSTextScanner.skipWhitespace();
    }

    public final boolean parseRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) {
        List nextSelectorGroup = cSSTextScanner.nextSelectorGroup();
        int i = 0;
        if (nextSelectorGroup != null) {
            ArrayList arrayList = (ArrayList) nextSelectorGroup;
            if (!arrayList.isEmpty()) {
                if (!cSSTextScanner.consume('{')) {
                    throw new CSSParseException("Malformed rule block: expected '{'");
                }
                cSSTextScanner.skipWhitespace();
                SVG.Style style = new SVG.Style();
                do {
                    String nextIdentifier = cSSTextScanner.nextIdentifier();
                    cSSTextScanner.skipWhitespace();
                    if (!cSSTextScanner.consume(':')) {
                        throw new CSSParseException("Expected ':'");
                    }
                    cSSTextScanner.skipWhitespace();
                    String str = null;
                    if (!cSSTextScanner.empty()) {
                        int i2 = cSSTextScanner.position;
                        String str2 = cSSTextScanner.input;
                        int charAt = str2.charAt(i2);
                        int i3 = i2;
                        while (charAt != -1 && charAt != 59 && charAt != 125 && charAt != 33 && charAt != 10 && charAt != 13) {
                            if (!SVGParser.TextScanner.isWhitespace(charAt)) {
                                i3 = cSSTextScanner.position + 1;
                            }
                            charAt = cSSTextScanner.advanceChar();
                        }
                        if (cSSTextScanner.position > i2) {
                            str = str2.substring(i2, i3);
                        } else {
                            cSSTextScanner.position = i2;
                        }
                    }
                    if (str == null) {
                        throw new CSSParseException("Expected property value");
                    }
                    cSSTextScanner.skipWhitespace();
                    if (cSSTextScanner.consume('!')) {
                        cSSTextScanner.skipWhitespace();
                        if (!cSSTextScanner.consume("important")) {
                            throw new CSSParseException("Malformed rule set: found unexpected '!'");
                        }
                        cSSTextScanner.skipWhitespace();
                    }
                    cSSTextScanner.consume(';');
                    SVGParser.processStyleProperty(style, nextIdentifier, str);
                    cSSTextScanner.skipWhitespace();
                    if (cSSTextScanner.empty()) {
                        break;
                    }
                } while (!cSSTextScanner.consume('}'));
                cSSTextScanner.skipWhitespace();
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ruleset.add(new Rule((Selector) obj, style, this.source));
                }
                return true;
            }
        }
        return false;
    }

    public final Ruleset parseRuleset(CSSTextScanner cSSTextScanner) {
        Ruleset ruleset = new Ruleset();
        while (!cSSTextScanner.empty()) {
            try {
                if (!cSSTextScanner.consume("<!--") && !cSSTextScanner.consume("-->")) {
                    if (!cSSTextScanner.consume('@')) {
                        if (!parseRule(ruleset, cSSTextScanner)) {
                            break;
                        }
                    } else {
                        parseAtRule(ruleset, cSSTextScanner);
                    }
                }
            } catch (CSSParseException e) {
                Log.e("CSSParser", "CSS parser terminated early due to error: " + e.getMessage());
            }
        }
        return ruleset;
    }

    public CSSParser(Source source) {
        this(MediaType.screen, source);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Selector {
        public List simpleSelectors;
        public int specificity;

        private Selector() {
            this.simpleSelectors = null;
            this.specificity = 0;
        }

        public final void addedAttributeOrPseudo() {
            this.specificity += 1000;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator it = this.simpleSelectors.iterator();
            while (it.hasNext()) {
                sb.append((SimpleSelector) it.next());
                sb.append(' ');
            }
            sb.append('[');
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.specificity, ']');
        }

        public /* synthetic */ Selector(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public CSSParser(MediaType mediaType, Source source) {
        this.inMediaRule = false;
        this.deviceMediaType = mediaType;
        this.source = source;
    }

    public static boolean ruleMatch(Selector selector, SVG.SvgElementBase svgElementBase) {
        ArrayList arrayList = new ArrayList();
        Object obj = svgElementBase.parent;
        while (true) {
            if (obj == null) {
                break;
            }
            arrayList.add(0, obj);
            obj = ((SVG.SvgObject) obj).parent;
        }
        int size = arrayList.size() - 1;
        List list = selector.simpleSelectors;
        if ((list == null ? 0 : ((ArrayList) list).size()) == 1) {
            return selectorMatch((SimpleSelector) ((ArrayList) selector.simpleSelectors).get(0), svgElementBase);
        }
        List list2 = selector.simpleSelectors;
        return ruleMatch(selector, (list2 != null ? ((ArrayList) list2).size() : 0) - 1, arrayList, size, svgElementBase);
    }
}
