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

/* loaded from: classes3.dex */
public class CSSParser {
    public final MediaType deviceMediaType;
    public boolean inMediaRule;
    public final Source source;

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

    enum AttribOp {
        EXISTS,
        EQUALS,
        INCLUDES,
        DASHMATCH
    }

    public class CSSTextScanner extends SVGParser.TextScanner {

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
            int iHexChar;
            if (empty()) {
                return null;
            }
            char cCharAt = this.input.charAt(this.position);
            if (cCharAt != '\'' && cCharAt != '\"') {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            this.position++;
            int iIntValue = nextChar().intValue();
            while (iIntValue != -1 && iIntValue != cCharAt) {
                if (iIntValue == 92) {
                    iIntValue = nextChar().intValue();
                    if (iIntValue != -1) {
                        if (iIntValue == 10 || iIntValue == 13 || iIntValue == 12) {
                            iIntValue = nextChar().intValue();
                        } else {
                            int iHexChar2 = hexChar(iIntValue);
                            if (iHexChar2 != -1) {
                                for (int i = 1; i <= 5 && (iHexChar = hexChar((iIntValue = nextChar().intValue()))) != -1; i++) {
                                    iHexChar2 = (iHexChar2 * 16) + iHexChar;
                                }
                                sb.append((char) iHexChar2);
                            }
                        }
                    }
                }
                sb.append((char) iIntValue);
                iIntValue = nextChar().intValue();
            }
            return sb.toString();
        }

        public final String nextIdentifier() {
            int i;
            int i2;
            boolean zEmpty = empty();
            String str = this.input;
            if (zEmpty) {
                i2 = this.position;
            } else {
                int i3 = this.position;
                int iCharAt = str.charAt(i3);
                if (iCharAt == 45) {
                    iCharAt = advanceChar();
                }
                if ((iCharAt < 65 || iCharAt > 90) && ((iCharAt < 97 || iCharAt > 122) && iCharAt != 95)) {
                    i = i3;
                } else {
                    int iAdvanceChar = advanceChar();
                    while (true) {
                        if ((iAdvanceChar < 65 || iAdvanceChar > 90) && ((iAdvanceChar < 97 || iAdvanceChar > 122) && !((iAdvanceChar >= 48 && iAdvanceChar <= 57) || iAdvanceChar == 45 || iAdvanceChar == 95))) {
                            break;
                        }
                        iAdvanceChar = advanceChar();
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
            String strSubstring = str.substring(i4, i2);
            this.position = i2;
            return strSubstring;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:131:0x01f6  */
        /* JADX WARN: Removed duplicated region for block: B:161:0x027d  */
        /* JADX WARN: Removed duplicated region for block: B:186:0x02d2  */
        /* JADX WARN: Removed duplicated region for block: B:240:0x03a2  */
        /* JADX WARN: Removed duplicated region for block: B:242:0x03a7  */
        /* JADX WARN: Removed duplicated region for block: B:256:0x045f  */
        /* JADX WARN: Removed duplicated region for block: B:261:0x047d  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x0053  */
        /* JADX WARN: Removed duplicated region for block: B:285:0x02a4 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:286:0x03c0 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:287:0x04a3 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:29:0x005c  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x007b  */
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
        */
        public final List nextSelectorGroup() throws CSSParseException {
            Combinator combinator;
            SimpleSelector simpleSelector;
            String strNextQuotedString;
            Combinator combinator2;
            char c;
            PseudoClassAnPlusB pseudoClassAnPlusB;
            PseudoClassAnPlusB pseudoClassAnPlusB2;
            PseudoClassAnPlusB pseudoClassAnPlusB3;
            int i;
            IntegerParser integerParser;
            AnPlusB anPlusB;
            List listNextSelectorGroup;
            PseudoClassAnPlusB pseudoClassAnPlusB4;
            ?? r2 = 0;
            boolean z = false;
            if (empty()) {
                return null;
            }
            int i2 = 1;
            ArrayList arrayList = new ArrayList(1);
            Selector selector = new Selector(z ? 1 : 0);
            while (true) {
                if (!empty() && !empty()) {
                    int i3 = this.position;
                    List list = selector.simpleSelectors;
                    char c2 = '+';
                    if (((list == null || ((ArrayList) list).isEmpty()) ? i2 : 0) != 0) {
                        combinator = r2;
                        if (consume('*')) {
                            String strNextIdentifier = nextIdentifier();
                            if (strNextIdentifier != null) {
                                SimpleSelector simpleSelector2 = new SimpleSelector(combinator, strNextIdentifier);
                                selector.specificity += i2;
                                simpleSelector = simpleSelector2;
                            } else {
                                simpleSelector = r2;
                            }
                        } else {
                            simpleSelector = new SimpleSelector(combinator, r2);
                        }
                        while (!empty()) {
                            if (consume('.')) {
                                if (simpleSelector == null) {
                                    simpleSelector = new SimpleSelector(combinator, r2);
                                }
                                String strNextIdentifier2 = nextIdentifier();
                                if (strNextIdentifier2 == null) {
                                    throw new CSSParseException("Invalid \".class\" simpleSelectors");
                                }
                                simpleSelector.addAttrib("class", AttribOp.EQUALS, strNextIdentifier2);
                                selector.addedAttributeOrPseudo();
                            } else if (consume('#')) {
                                if (simpleSelector == null) {
                                    simpleSelector = new SimpleSelector(combinator, r2);
                                }
                                String strNextIdentifier3 = nextIdentifier();
                                if (strNextIdentifier3 == null) {
                                    throw new CSSParseException("Invalid \"#id\" simpleSelectors");
                                }
                                simpleSelector.addAttrib("id", AttribOp.EQUALS, strNextIdentifier3);
                                selector.specificity += 1000000;
                            } else if (consume('[')) {
                                if (simpleSelector == null) {
                                    simpleSelector = new SimpleSelector(combinator, r2);
                                }
                                skipWhitespace();
                                String strNextIdentifier4 = nextIdentifier();
                                if (strNextIdentifier4 == null) {
                                    throw new CSSParseException("Invalid attribute simpleSelectors");
                                }
                                skipWhitespace();
                                AttribOp attribOp = consume('=') ? AttribOp.EQUALS : consume("~=") ? AttribOp.INCLUDES : consume("|=") ? AttribOp.DASHMATCH : r2;
                                if (attribOp != null) {
                                    skipWhitespace();
                                    if (empty()) {
                                        strNextQuotedString = r2;
                                    } else {
                                        strNextQuotedString = nextQuotedString();
                                        if (strNextQuotedString == null) {
                                            strNextQuotedString = nextIdentifier();
                                        }
                                    }
                                    if (strNextQuotedString == null) {
                                        throw new CSSParseException("Invalid attribute simpleSelectors");
                                    }
                                    skipWhitespace();
                                } else {
                                    strNextQuotedString = r2;
                                }
                                if (!consume(']')) {
                                    throw new CSSParseException("Invalid attribute simpleSelectors");
                                }
                                if (attribOp == null) {
                                    attribOp = AttribOp.EXISTS;
                                }
                                simpleSelector.addAttrib(strNextIdentifier4, attribOp, strNextQuotedString);
                                selector.addedAttributeOrPseudo();
                            } else if (consume(':')) {
                                if (simpleSelector == null) {
                                    simpleSelector = new SimpleSelector(combinator, r2);
                                }
                                String strNextIdentifier5 = nextIdentifier();
                                if (strNextIdentifier5 == null) {
                                    throw new CSSParseException("Invalid pseudo class");
                                }
                                PseudoClassIdents pseudoClassIdents = (PseudoClassIdents) ((HashMap) PseudoClassIdents.cache).get(strNextIdentifier5);
                                if (pseudoClassIdents == null) {
                                    pseudoClassIdents = PseudoClassIdents.UNSUPPORTED;
                                }
                                switch (AnonymousClass1.$SwitchMap$com$caverock$androidsvg$CSSParser$PseudoClassIdents[pseudoClassIdents.ordinal()]) {
                                    case 1:
                                        combinator2 = combinator;
                                        c = c2;
                                        PseudoClassAnPlusB pseudoClassAnPlusB5 = new PseudoClassAnPlusB(0, 1, true, false, null);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB = pseudoClassAnPlusB5;
                                        if (simpleSelector.pseudos == null) {
                                            simpleSelector.pseudos = new ArrayList();
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 2:
                                        combinator2 = combinator;
                                        c = c2;
                                        PseudoClassAnPlusB pseudoClassAnPlusB6 = new PseudoClassAnPlusB(0, 1, false, false, null);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB = pseudoClassAnPlusB6;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 3:
                                        combinator2 = combinator;
                                        c = c2;
                                        ?? pseudoClassOnlyChild = new PseudoClassOnlyChild(false, null);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB = pseudoClassOnlyChild;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 4:
                                        combinator2 = combinator;
                                        c = c2;
                                        PseudoClassAnPlusB pseudoClassAnPlusB7 = new PseudoClassAnPlusB(0, 1, true, true, simpleSelector.tag);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB2 = pseudoClassAnPlusB7;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 5:
                                        combinator2 = combinator;
                                        c = c2;
                                        PseudoClassAnPlusB pseudoClassAnPlusB8 = new PseudoClassAnPlusB(0, 1, false, true, simpleSelector.tag);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB2 = pseudoClassAnPlusB8;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 6:
                                        combinator2 = combinator;
                                        c = c2;
                                        i2 = 1;
                                        ?? pseudoClassOnlyChild2 = new PseudoClassOnlyChild(true, simpleSelector.tag);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB2 = pseudoClassOnlyChild2;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 7:
                                        combinator2 = combinator;
                                        c = c2;
                                        ?? pseudoClassRoot = new PseudoClassRoot(r2);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB3 = pseudoClassRoot;
                                        i2 = 1;
                                        pseudoClassAnPlusB2 = pseudoClassAnPlusB3;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 8:
                                        combinator2 = combinator;
                                        c = c2;
                                        ?? pseudoClassEmpty = new PseudoClassEmpty(null);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB3 = pseudoClassEmpty;
                                        i2 = 1;
                                        pseudoClassAnPlusB2 = pseudoClassAnPlusB3;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 9:
                                    case 10:
                                    case 11:
                                    case 12:
                                        combinator2 = combinator;
                                        boolean z2 = pseudoClassIdents == PseudoClassIdents.nth_child || pseudoClassIdents == PseudoClassIdents.nth_of_type;
                                        boolean z3 = pseudoClassIdents == PseudoClassIdents.nth_of_type || pseudoClassIdents == PseudoClassIdents.nth_last_of_type;
                                        if (empty()) {
                                            anPlusB = null;
                                            c = '+';
                                            if (anPlusB != null) {
                                                throw new CSSParseException("Invalid or missing parameter section for pseudo class: ".concat(strNextIdentifier5));
                                            }
                                            PseudoClassAnPlusB pseudoClassAnPlusB9 = new PseudoClassAnPlusB(anPlusB.a, anPlusB.b, z2, z3, simpleSelector.tag);
                                            selector.addedAttributeOrPseudo();
                                            pseudoClassAnPlusB3 = pseudoClassAnPlusB9;
                                            i2 = 1;
                                            pseudoClassAnPlusB2 = pseudoClassAnPlusB3;
                                            pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                            if (simpleSelector.pseudos == null) {
                                            }
                                            ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                            c2 = c;
                                            combinator = combinator2;
                                            r2 = 0;
                                            break;
                                        } else {
                                            int i4 = this.position;
                                            if (consume('(')) {
                                                skipWhitespace();
                                                if (consume("odd")) {
                                                    anPlusB = new AnPlusB(2, 1);
                                                } else if (consume("even")) {
                                                    anPlusB = new AnPlusB(2, 0);
                                                } else {
                                                    int i5 = (!consume('+') && consume('-')) ? -1 : 1;
                                                    int i6 = this.position;
                                                    String str = this.input;
                                                    int i7 = this.inputLength;
                                                    IntegerParser integerParser2 = IntegerParser.parseInt(i6, i7, str);
                                                    if (integerParser2 != null) {
                                                        this.position = integerParser2.pos;
                                                    }
                                                    if (consume('n') || consume('N')) {
                                                        if (integerParser2 == null) {
                                                            integerParser2 = new IntegerParser(1L, this.position);
                                                        }
                                                        skipWhitespace();
                                                        c = '+';
                                                        boolean zConsume = consume('+');
                                                        int i8 = (zConsume || !(zConsume = consume('-'))) ? 1 : -1;
                                                        if (zConsume) {
                                                            skipWhitespace();
                                                            IntegerParser integerParser3 = IntegerParser.parseInt(this.position, i7, str);
                                                            if (integerParser3 != null) {
                                                                this.position = integerParser3.pos;
                                                                integerParser = integerParser3;
                                                                i = i5;
                                                                i5 = i8;
                                                            } else {
                                                                this.position = i4;
                                                                anPlusB = null;
                                                            }
                                                        } else {
                                                            i = i5;
                                                            i5 = i8;
                                                            integerParser = null;
                                                        }
                                                    } else {
                                                        integerParser = integerParser2;
                                                        i = 1;
                                                        c = '+';
                                                        integerParser2 = null;
                                                    }
                                                    anPlusB = new AnPlusB(integerParser2 == null ? 0 : i * ((int) integerParser2.value), integerParser == null ? 0 : i5 * ((int) integerParser.value));
                                                    skipWhitespace();
                                                    if (!consume(')')) {
                                                        this.position = i4;
                                                        anPlusB = null;
                                                    }
                                                }
                                                c = '+';
                                                skipWhitespace();
                                                if (!consume(')')) {
                                                }
                                            }
                                            if (anPlusB != null) {
                                            }
                                        }
                                        break;
                                    case 13:
                                        if (empty()) {
                                            listNextSelectorGroup = r2;
                                            combinator2 = combinator;
                                            if (listNextSelectorGroup == null) {
                                                throw new CSSParseException("Invalid or missing parameter section for pseudo class: ".concat(strNextIdentifier5));
                                            }
                                            ?? pseudoClassNot = new PseudoClassNot(listNextSelectorGroup);
                                            Iterator it = pseudoClassNot.selectorGroup.iterator();
                                            int i9 = Integer.MIN_VALUE;
                                            while (it.hasNext()) {
                                                int i10 = ((Selector) it.next()).specificity;
                                                if (i10 > i9) {
                                                    i9 = i10;
                                                }
                                            }
                                            selector.specificity = i9;
                                            i2 = 1;
                                            c = '+';
                                            pseudoClassAnPlusB = pseudoClassNot;
                                            if (simpleSelector.pseudos == null) {
                                            }
                                            ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                            c2 = c;
                                            combinator = combinator2;
                                            r2 = 0;
                                            break;
                                        } else {
                                            int i11 = this.position;
                                            if (consume('(')) {
                                                skipWhitespace();
                                                listNextSelectorGroup = nextSelectorGroup();
                                                if (listNextSelectorGroup != null && consume(')')) {
                                                    ArrayList arrayList2 = (ArrayList) listNextSelectorGroup;
                                                    int size = arrayList2.size();
                                                    int i12 = 0;
                                                    while (i12 < size) {
                                                        Object obj = arrayList2.get(i12);
                                                        i12++;
                                                        List list2 = ((Selector) obj).simpleSelectors;
                                                        if (list2 == null) {
                                                            combinator2 = combinator;
                                                            if (listNextSelectorGroup == null) {
                                                            }
                                                        } else {
                                                            ArrayList arrayList3 = (ArrayList) list2;
                                                            int size2 = arrayList3.size();
                                                            int i13 = 0;
                                                            while (i13 < size2) {
                                                                Object obj2 = arrayList3.get(i13);
                                                                i13++;
                                                                List list3 = ((SimpleSelector) obj2).pseudos;
                                                                if (list3 == null) {
                                                                    combinator = combinator;
                                                                } else {
                                                                    ArrayList arrayList4 = (ArrayList) list3;
                                                                    ArrayList arrayList5 = arrayList3;
                                                                    int size3 = arrayList4.size();
                                                                    combinator2 = combinator;
                                                                    int i14 = 0;
                                                                    while (i14 < size3) {
                                                                        Object obj3 = arrayList4.get(i14);
                                                                        i14++;
                                                                        int i15 = size3;
                                                                        if (((PseudoClass) obj3) instanceof PseudoClassNot) {
                                                                            listNextSelectorGroup = null;
                                                                            if (listNextSelectorGroup == null) {
                                                                            }
                                                                        } else {
                                                                            size3 = i15;
                                                                        }
                                                                    }
                                                                    arrayList3 = arrayList5;
                                                                    combinator = combinator2;
                                                                }
                                                            }
                                                            combinator = combinator;
                                                        }
                                                    }
                                                    combinator2 = combinator;
                                                    if (listNextSelectorGroup == null) {
                                                    }
                                                } else {
                                                    this.position = i11;
                                                    listNextSelectorGroup = r2;
                                                    combinator2 = combinator;
                                                    if (listNextSelectorGroup == null) {
                                                    }
                                                }
                                            }
                                        }
                                        break;
                                    case 14:
                                        ?? pseudoClassTarget = new PseudoClassTarget(r2);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB4 = pseudoClassTarget;
                                        combinator2 = combinator;
                                        c = c2;
                                        pseudoClassAnPlusB2 = pseudoClassAnPlusB4;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 15:
                                        if (!empty()) {
                                            int i16 = this.position;
                                            if (consume('(')) {
                                                skipWhitespace();
                                                ArrayList arrayList6 = r2;
                                                while (true) {
                                                    String strNextIdentifier6 = nextIdentifier();
                                                    if (strNextIdentifier6 == null) {
                                                        this.position = i16;
                                                    } else {
                                                        if (arrayList6 == null) {
                                                            arrayList6 = new ArrayList();
                                                        }
                                                        arrayList6.add(strNextIdentifier6);
                                                        skipWhitespace();
                                                        if (!skipCommaWhitespace()) {
                                                            if (!consume(')')) {
                                                                this.position = i16;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        ?? pseudoClassNotSupported = new PseudoClassNotSupported(strNextIdentifier5);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB4 = pseudoClassNotSupported;
                                        combinator2 = combinator;
                                        c = c2;
                                        pseudoClassAnPlusB2 = pseudoClassAnPlusB4;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    case 16:
                                    case 17:
                                    case 18:
                                    case 19:
                                    case 20:
                                    case 21:
                                    case 22:
                                    case 23:
                                    case 24:
                                        ?? pseudoClassNotSupported2 = new PseudoClassNotSupported(strNextIdentifier5);
                                        selector.addedAttributeOrPseudo();
                                        pseudoClassAnPlusB4 = pseudoClassNotSupported2;
                                        combinator2 = combinator;
                                        c = c2;
                                        pseudoClassAnPlusB2 = pseudoClassAnPlusB4;
                                        pseudoClassAnPlusB = pseudoClassAnPlusB2;
                                        if (simpleSelector.pseudos == null) {
                                        }
                                        ((ArrayList) simpleSelector.pseudos).add(pseudoClassAnPlusB);
                                        c2 = c;
                                        combinator = combinator2;
                                        r2 = 0;
                                        break;
                                    default:
                                        throw new CSSParseException("Unsupported pseudo class: ".concat(strNextIdentifier5));
                                }
                            } else if (simpleSelector != null) {
                                if (selector.simpleSelectors == null) {
                                    selector.simpleSelectors = new ArrayList();
                                }
                                ((ArrayList) selector.simpleSelectors).add(simpleSelector);
                                if (skipCommaWhitespace()) {
                                    arrayList.add(selector);
                                    r2 = 0;
                                    selector = new Selector(false ? 1 : 0);
                                } else {
                                    r2 = 0;
                                }
                            } else {
                                this.position = i3;
                            }
                        }
                        if (simpleSelector != null) {
                        }
                    } else {
                        if (consume('>')) {
                            combinator = Combinator.CHILD;
                            skipWhitespace();
                        } else if (consume('+')) {
                            combinator = Combinator.FOLLOWS;
                            skipWhitespace();
                        }
                        if (consume('*')) {
                        }
                        while (!empty()) {
                        }
                        if (simpleSelector != null) {
                        }
                    }
                }
            }
            List list4 = selector.simpleSelectors;
            if (list4 != null && !((ArrayList) list4).isEmpty()) {
                arrayList.add(selector);
            }
            return arrayList;
        }
    }

    enum Combinator {
        DESCENDANT,
        CHILD,
        FOLLOWS
    }

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

    public interface PseudoClass {
        boolean matches(SVG.SvgElementBase svgElementBase);
    }

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

        /* JADX WARN: Removed duplicated region for block: B:33:0x0064 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x0065 A[RETURN] */
        @Override // com.caverock.androidsvg.CSSParser.PseudoClass
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean matches(SVG.SvgElementBase svgElementBase) {
            int i;
            int i2;
            boolean z = this.isOfType;
            String nodeName = this.nodeName;
            if (z && nodeName == null) {
                nodeName = svgElementBase.getNodeName();
            }
            SVG.SvgContainer svgContainer = svgElementBase.parent;
            if (svgContainer != null) {
                Iterator it = svgContainer.getChildren().iterator();
                i2 = 0;
                i = 0;
                while (it.hasNext()) {
                    SVG.SvgElementBase svgElementBase2 = (SVG.SvgElementBase) ((SVG.SvgObject) it.next());
                    if (svgElementBase2 == svgElementBase) {
                        i2 = i;
                    }
                    if (nodeName == null || svgElementBase2.getNodeName().equals(nodeName)) {
                        i++;
                    }
                }
            } else {
                i = 1;
                i2 = 0;
            }
            int i3 = this.isFromStart ? i2 + 1 : i - i2;
            int i4 = this.a;
            int i5 = this.b;
            if (i4 == 0) {
                return i3 == i5;
            }
            int i6 = i3 - i5;
            if (i6 % i4 != 0 || (Integer.signum(i6) != 0 && Integer.signum(i6) != Integer.signum(i4))) {
            }
        }

        public final String toString() {
            String str = this.isFromStart ? "" : "last-";
            boolean z = this.isOfType;
            int i = this.b;
            int i2 = this.a;
            return z ? String.format("nth-%schild(%dn%+d of type <%s>)", str, Integer.valueOf(i2), Integer.valueOf(i), this.nodeName) : String.format("nth-%schild(%dn%+d)", str, Integer.valueOf(i2), Integer.valueOf(i));
        }
    }

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
            String nodeName = this.nodeName;
            if (z && nodeName == null) {
                nodeName = svgElementBase.getNodeName();
            }
            SVG.SvgContainer svgContainer = svgElementBase.parent;
            if (svgContainer != null) {
                Iterator it = svgContainer.getChildren().iterator();
                i = 0;
                while (it.hasNext()) {
                    SVG.SvgElementBase svgElementBase2 = (SVG.SvgElementBase) ((SVG.SvgObject) it.next());
                    if (nodeName == null || svgElementBase2.getNodeName().equals(nodeName)) {
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
            String strSubstring = null;
            if (!cSSTextScanner.empty()) {
                int i = cSSTextScanner.position;
                String str = cSSTextScanner.input;
                char cCharAt = str.charAt(i);
                if ((cCharAt < 'A' || cCharAt > 'Z') && (cCharAt < 'a' || cCharAt > 'z')) {
                    cSSTextScanner.position = i;
                } else {
                    int iAdvanceChar = cSSTextScanner.advanceChar();
                    while (true) {
                        if ((iAdvanceChar < 65 || iAdvanceChar > 90) && (iAdvanceChar < 97 || iAdvanceChar > 122)) {
                            break;
                        }
                        iAdvanceChar = cSSTextScanner.advanceChar();
                    }
                    strSubstring = str.substring(i, cSSTextScanner.position);
                }
            }
            if (strSubstring == null) {
                break;
            }
            try {
                arrayList.add(MediaType.valueOf(strSubstring));
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

    /* JADX WARN: Removed duplicated region for block: B:27:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean selectorMatch(SimpleSelector simpleSelector, SVG.SvgElementBase svgElementBase) {
        List list;
        List list2;
        String str = simpleSelector.tag;
        if (str == null || str.equals(svgElementBase.getNodeName().toLowerCase(Locale.US))) {
            List list3 = simpleSelector.attribs;
            if (list3 == null) {
                list = simpleSelector.pseudos;
                if (list != null) {
                }
                return true;
            }
            ArrayList arrayList = (ArrayList) list3;
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
                    if (!str2.equals("class") || (list2 = svgElementBase.classNames) == null || !list2.contains(str3)) {
                        break;
                    }
                } else if (!str3.equals(svgElementBase.id)) {
                    break;
                }
            }
            list = simpleSelector.pseudos;
            if (list != null) {
                ArrayList arrayList2 = (ArrayList) list;
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

    public final void parseAtRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) throws CSSParseException {
        int iIntValue;
        int iHexChar;
        String strNextIdentifier = cSSTextScanner.nextIdentifier();
        cSSTextScanner.skipWhitespace();
        if (strNextIdentifier == null) {
            throw new CSSParseException("Invalid '@' rule");
        }
        int i = 0;
        if (!this.inMediaRule && strNextIdentifier.equals("media")) {
            List mediaList = parseMediaList(cSSTextScanner);
            if (!cSSTextScanner.consume('{')) {
                throw new CSSParseException("Invalid @media rule: missing rule set");
            }
            cSSTextScanner.skipWhitespace();
            MediaType mediaType = this.deviceMediaType;
            ArrayList arrayList = (ArrayList) mediaList;
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
        } else if (this.inMediaRule || !strNextIdentifier.equals("import")) {
            Log.w("CSSParser", "Ignoring @" + strNextIdentifier + " rule");
            while (!cSSTextScanner.empty() && ((iIntValue = cSSTextScanner.nextChar().intValue()) != 59 || i != 0)) {
                if (iIntValue != 123) {
                    if (iIntValue == 125 && i > 0 && i - 1 == 0) {
                        break;
                    }
                } else {
                    i++;
                }
            }
        } else {
            String strNextCSSString = null;
            if (!cSSTextScanner.empty()) {
                int i3 = cSSTextScanner.position;
                if (cSSTextScanner.consume("url(")) {
                    cSSTextScanner.skipWhitespace();
                    String strNextCSSString2 = cSSTextScanner.nextCSSString();
                    if (strNextCSSString2 == null) {
                        StringBuilder sb = new StringBuilder();
                        while (!cSSTextScanner.empty()) {
                            int i4 = cSSTextScanner.position;
                            String str = cSSTextScanner.input;
                            char cCharAt = str.charAt(i4);
                            if (cCharAt == '\'' || cCharAt == '\"' || cCharAt == '(' || cCharAt == ')' || SVGParser.TextScanner.isWhitespace(cCharAt) || Character.isISOControl((int) cCharAt)) {
                                break;
                            }
                            cSSTextScanner.position++;
                            if (cCharAt == '\\') {
                                if (!cSSTextScanner.empty()) {
                                    int i5 = cSSTextScanner.position;
                                    cSSTextScanner.position = i5 + 1;
                                    cCharAt = str.charAt(i5);
                                    if (cCharAt != '\n' && cCharAt != '\r' && cCharAt != '\f') {
                                        int iHexChar2 = CSSTextScanner.hexChar(cCharAt);
                                        if (iHexChar2 != -1) {
                                            for (int i6 = 1; i6 <= 5 && !cSSTextScanner.empty() && (iHexChar = CSSTextScanner.hexChar(str.charAt(cSSTextScanner.position))) != -1; i6++) {
                                                cSSTextScanner.position++;
                                                iHexChar2 = (iHexChar2 * 16) + iHexChar;
                                            }
                                            sb.append((char) iHexChar2);
                                        }
                                    }
                                }
                            }
                            sb.append(cCharAt);
                        }
                        strNextCSSString2 = sb.length() == 0 ? null : sb.toString();
                    }
                    if (strNextCSSString2 == null) {
                        cSSTextScanner.position = i3;
                    } else {
                        cSSTextScanner.skipWhitespace();
                        if (cSSTextScanner.empty() || cSSTextScanner.consume(")")) {
                            strNextCSSString = strNextCSSString2;
                        } else {
                            cSSTextScanner.position = i3;
                        }
                    }
                }
            }
            if (strNextCSSString == null) {
                strNextCSSString = cSSTextScanner.nextCSSString();
            }
            if (strNextCSSString == null) {
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

    public final boolean parseRule(Ruleset ruleset, CSSTextScanner cSSTextScanner) throws CSSParseException {
        List listNextSelectorGroup = cSSTextScanner.nextSelectorGroup();
        int i = 0;
        if (listNextSelectorGroup != null) {
            ArrayList arrayList = (ArrayList) listNextSelectorGroup;
            if (!arrayList.isEmpty()) {
                if (!cSSTextScanner.consume('{')) {
                    throw new CSSParseException("Malformed rule block: expected '{'");
                }
                cSSTextScanner.skipWhitespace();
                SVG.Style style = new SVG.Style();
                do {
                    String strNextIdentifier = cSSTextScanner.nextIdentifier();
                    cSSTextScanner.skipWhitespace();
                    if (!cSSTextScanner.consume(':')) {
                        throw new CSSParseException("Expected ':'");
                    }
                    cSSTextScanner.skipWhitespace();
                    String strSubstring = null;
                    if (!cSSTextScanner.empty()) {
                        int i2 = cSSTextScanner.position;
                        String str = cSSTextScanner.input;
                        int iCharAt = str.charAt(i2);
                        int i3 = i2;
                        while (iCharAt != -1 && iCharAt != 59 && iCharAt != 125 && iCharAt != 33 && iCharAt != 10 && iCharAt != 13) {
                            if (!SVGParser.TextScanner.isWhitespace(iCharAt)) {
                                i3 = cSSTextScanner.position + 1;
                            }
                            iCharAt = cSSTextScanner.advanceChar();
                        }
                        if (cSSTextScanner.position > i2) {
                            strSubstring = str.substring(i2, i3);
                        } else {
                            cSSTextScanner.position = i2;
                        }
                    }
                    if (strSubstring == null) {
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
                    SVGParser.processStyleProperty(style, strNextIdentifier, strSubstring);
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
