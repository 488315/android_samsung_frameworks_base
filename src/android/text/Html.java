package android.text;

import android.app.ActivityThread;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.Layout;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

/* loaded from: classes4.dex */
public class Html {
    public static final int FROM_HTML_MODE_COMPACT = 63;
    public static final int FROM_HTML_MODE_LEGACY = 0;
    public static final int FROM_HTML_OPTION_USE_CSS_COLORS = 256;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE = 32;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_DIV = 16;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_HEADING = 2;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST = 8;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM = 4;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH = 1;
    private static final int TO_HTML_PARAGRAPH_FLAG = 1;
    public static final int TO_HTML_PARAGRAPH_LINES_CONSECUTIVE = 0;
    public static final int TO_HTML_PARAGRAPH_LINES_INDIVIDUAL = 1;

    public interface ImageGetter {
        Drawable getDrawable(String str);
    }

    public interface TagHandler {
        void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader);
    }

    private Html() {
    }

    @Deprecated
    public static Spanned fromHtml(String str) {
        return fromHtml(str, 0, null, null);
    }

    public static Spanned fromHtml(String str, int i) {
        return fromHtml(str, i, null, null);
    }

    private static class HtmlParser {
        private static final HTMLSchema schema = new HTMLSchema();

        private HtmlParser() {
        }
    }

    @Deprecated
    public static Spanned fromHtml(String str, ImageGetter imageGetter, TagHandler tagHandler) {
        return fromHtml(str, 0, imageGetter, tagHandler);
    }

    public static Spanned fromHtml(String str, int i, ImageGetter imageGetter, TagHandler tagHandler) {
        Parser parser = new Parser();
        try {
            parser.setProperty("http://www.ccil.org/~cowan/tagsoup/properties/schema", HtmlParser.schema);
            return new HtmlToSpannedConverter(str, imageGetter, tagHandler, parser, i).convert();
        } catch (SAXNotRecognizedException e) {
            throw new RuntimeException(e);
        } catch (SAXNotSupportedException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Deprecated
    public static String toHtml(Spanned spanned) {
        return toHtml(spanned, 0);
    }

    public static String toHtml(Spanned spanned, int i) {
        StringBuilder sb = new StringBuilder();
        withinHtml(sb, spanned, i);
        return sb.toString();
    }

    public static String escapeHtml(CharSequence charSequence) {
        StringBuilder sb = new StringBuilder();
        withinStyle(sb, charSequence, 0, charSequence.length());
        return sb.toString();
    }

    private static void withinHtml(StringBuilder sb, Spanned spanned, int i) {
        if ((i & 1) == 0) {
            encodeTextAlignmentByDiv(sb, spanned, i);
        } else {
            withinDiv(sb, spanned, 0, spanned.length(), i);
        }
    }

    private static void encodeTextAlignmentByDiv(StringBuilder sb, Spanned spanned, int i) {
        int length = spanned.length();
        int i2 = 0;
        while (i2 < length) {
            int iNextSpanTransition = spanned.nextSpanTransition(i2, length, ParagraphStyle.class);
            String str = " ";
            boolean z = false;
            for (ParagraphStyle paragraphStyle : (ParagraphStyle[]) spanned.getSpans(i2, iNextSpanTransition, ParagraphStyle.class)) {
                if (paragraphStyle instanceof AlignmentSpan) {
                    Layout.Alignment alignment = ((AlignmentSpan) paragraphStyle).getAlignment();
                    if (alignment == Layout.Alignment.ALIGN_CENTER) {
                        str = "align=\"center\" " + str;
                    } else if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
                        str = "align=\"right\" " + str;
                    } else {
                        str = "align=\"left\" " + str;
                    }
                    z = true;
                }
            }
            if (z) {
                sb.append("<div ");
                sb.append(str);
                sb.append(">");
            }
            withinDiv(sb, spanned, i2, iNextSpanTransition, i);
            if (z) {
                sb.append("</div>");
            }
            i2 = iNextSpanTransition;
        }
    }

    private static void withinDiv(StringBuilder sb, Spanned spanned, int i, int i2, int i3) {
        while (i < i2) {
            int iNextSpanTransition = spanned.nextSpanTransition(i, i2, QuoteSpan.class);
            QuoteSpan[] quoteSpanArr = (QuoteSpan[]) spanned.getSpans(i, iNextSpanTransition, QuoteSpan.class);
            for (QuoteSpan quoteSpan : quoteSpanArr) {
                sb.append("<blockquote>");
            }
            withinBlockquote(sb, spanned, i, iNextSpanTransition, i3);
            for (QuoteSpan quoteSpan2 : quoteSpanArr) {
                sb.append("</blockquote>\n");
            }
            i = iNextSpanTransition;
        }
    }

    private static String getTextDirection(Spanned spanned, int i, int i2) {
        if (TextDirectionHeuristics.FIRSTSTRONG_LTR.isRtl(spanned, i, i2 - i)) {
            return " dir=\"rtl\"";
        }
        return " dir=\"ltr\"";
    }

    private static String getTextStyles(Spanned spanned, int i, int i2, boolean z, boolean z2) {
        String str = null;
        String str2 = z ? "margin-top:0; margin-bottom:0;" : null;
        if (z2) {
            AlignmentSpan[] alignmentSpanArr = (AlignmentSpan[]) spanned.getSpans(i, i2, AlignmentSpan.class);
            int length = alignmentSpanArr.length - 1;
            while (true) {
                if (length < 0) {
                    break;
                }
                AlignmentSpan alignmentSpan = alignmentSpanArr[length];
                if ((spanned.getSpanFlags(alignmentSpan) & 51) == 51) {
                    Layout.Alignment alignment = alignmentSpan.getAlignment();
                    if (alignment == Layout.Alignment.ALIGN_NORMAL) {
                        str = "text-align:start;";
                    } else if (alignment == Layout.Alignment.ALIGN_CENTER) {
                        str = "text-align:center;";
                    } else if (alignment == Layout.Alignment.ALIGN_OPPOSITE) {
                        str = "text-align:end;";
                    }
                } else {
                    length--;
                }
            }
        }
        if (str2 == null && str == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(" style=\"");
        if (str2 != null && str != null) {
            sb.append(str2);
            sb.append(" ");
            sb.append(str);
        } else if (str2 != null) {
            sb.append(str2);
        } else if (str != null) {
            sb.append(str);
        }
        sb.append("\"");
        return sb.toString();
    }

    private static void withinBlockquote(StringBuilder sb, Spanned spanned, int i, int i2, int i3) {
        if ((i3 & 1) == 0) {
            withinBlockquoteConsecutive(sb, spanned, i, i2);
        } else {
            withinBlockquoteIndividual(sb, spanned, i, i2);
        }
    }

    private static void withinBlockquoteIndividual(StringBuilder sb, Spanned spanned, int i, int i2) {
        boolean z;
        boolean z2 = false;
        while (i <= i2) {
            int iIndexOf = TextUtils.indexOf((CharSequence) spanned, '\n', i, i2);
            if (iIndexOf < 0) {
                iIndexOf = i2;
            }
            if (iIndexOf == i) {
                if (z2) {
                    sb.append("</ul>\n");
                    z2 = false;
                }
                sb.append("<br>\n");
            } else {
                ParagraphStyle[] paragraphStyleArr = (ParagraphStyle[]) spanned.getSpans(i, iIndexOf, ParagraphStyle.class);
                int length = paragraphStyleArr.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        z = false;
                        break;
                    }
                    ParagraphStyle paragraphStyle = paragraphStyleArr[i3];
                    if ((spanned.getSpanFlags(paragraphStyle) & 51) == 51 && (paragraphStyle instanceof BulletSpan)) {
                        z = true;
                        break;
                    }
                    i3++;
                }
                if (z && !z2) {
                    sb.append("<ul");
                    sb.append(getTextStyles(spanned, i, iIndexOf, true, false));
                    sb.append(">\n");
                    z2 = true;
                }
                if (z2 && !z) {
                    sb.append("</ul>\n");
                    z2 = false;
                }
                String str = z ? "li" : "p";
                sb.append("<");
                sb.append(str);
                sb.append(getTextDirection(spanned, i, iIndexOf));
                sb.append(getTextStyles(spanned, i, iIndexOf, !z, true));
                sb.append(">");
                withinParagraph(sb, spanned, i, iIndexOf);
                sb.append("</");
                sb.append(str);
                sb.append(">\n");
                if (iIndexOf == i2 && z2) {
                    sb.append("</ul>\n");
                    z2 = false;
                }
            }
            i = iIndexOf + 1;
        }
    }

    private static void withinBlockquoteConsecutive(StringBuilder sb, Spanned spanned, int i, int i2) {
        sb.append("<p");
        sb.append(getTextDirection(spanned, i, i2));
        sb.append(">");
        int i3 = i;
        while (i3 < i2) {
            int iIndexOf = TextUtils.indexOf((CharSequence) spanned, '\n', i3, i2);
            if (iIndexOf < 0) {
                iIndexOf = i2;
            }
            int i4 = 0;
            while (iIndexOf < i2 && spanned.charAt(iIndexOf) == '\n') {
                i4++;
                iIndexOf++;
            }
            withinParagraph(sb, spanned, i3, iIndexOf - i4);
            if (i4 == 1) {
                sb.append("<br>\n");
            } else {
                for (int i5 = 2; i5 < i4; i5++) {
                    sb.append("<br>");
                }
                if (iIndexOf != i2) {
                    sb.append("</p>\n");
                    sb.append("<p");
                    sb.append(getTextDirection(spanned, i, i2));
                    sb.append(">");
                }
            }
            i3 = iIndexOf;
        }
        sb.append("</p>\n");
    }

    private static float getDisplayMetricsDensity() {
        return ActivityThread.currentApplication().getResources().getDisplayMetrics().density;
    }

    private static void withinParagraph(StringBuilder sb, Spanned spanned, int i, int i2) {
        while (i < i2) {
            int iNextSpanTransition = spanned.nextSpanTransition(i, i2, CharacterStyle.class);
            CharacterStyle[] characterStyleArr = (CharacterStyle[]) spanned.getSpans(i, iNextSpanTransition, CharacterStyle.class);
            for (int i3 = 0; i3 < characterStyleArr.length; i3++) {
                CharacterStyle characterStyle = characterStyleArr[i3];
                if (characterStyle instanceof StyleSpan) {
                    int style = ((StyleSpan) characterStyle).getStyle();
                    if ((style & 1) != 0) {
                        sb.append("<b>");
                    }
                    if ((style & 2) != 0) {
                        sb.append("<i>");
                    }
                }
                CharacterStyle characterStyle2 = characterStyleArr[i3];
                if ((characterStyle2 instanceof TypefaceSpan) && "monospace".equals(((TypefaceSpan) characterStyle2).getFamily())) {
                    sb.append("<tt>");
                }
                if (characterStyleArr[i3] instanceof SuperscriptSpan) {
                    sb.append("<sup>");
                }
                if (characterStyleArr[i3] instanceof SubscriptSpan) {
                    sb.append("<sub>");
                }
                if (characterStyleArr[i3] instanceof UnderlineSpan) {
                    sb.append("<u>");
                }
                if (characterStyleArr[i3] instanceof StrikethroughSpan) {
                    sb.append("<span style=\"text-decoration:line-through;\">");
                }
                if (characterStyleArr[i3] instanceof URLSpan) {
                    sb.append("<a href=\"");
                    sb.append(((URLSpan) characterStyleArr[i3]).getURL());
                    sb.append("\">");
                }
                if (characterStyleArr[i3] instanceof ImageSpan) {
                    sb.append("<img src=\"");
                    sb.append(((ImageSpan) characterStyleArr[i3]).getSource());
                    sb.append("\">");
                    i = iNextSpanTransition;
                }
                CharacterStyle characterStyle3 = characterStyleArr[i3];
                if (characterStyle3 instanceof AbsoluteSizeSpan) {
                    AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan) characterStyle3;
                    float size = absoluteSizeSpan.getSize();
                    if (!absoluteSizeSpan.getDip()) {
                        size /= getDisplayMetricsDensity();
                    }
                    sb.append(String.format("<span style=\"font-size:%.0fpx\";>", Float.valueOf(size)));
                }
                CharacterStyle characterStyle4 = characterStyleArr[i3];
                if (characterStyle4 instanceof RelativeSizeSpan) {
                    sb.append(String.format("<span style=\"font-size:%.2fem;\">", Float.valueOf(((RelativeSizeSpan) characterStyle4).getSizeChange())));
                }
                CharacterStyle characterStyle5 = characterStyleArr[i3];
                if (characterStyle5 instanceof ForegroundColorSpan) {
                    sb.append(String.format("<span style=\"color:#%06X;\">", Integer.valueOf(((ForegroundColorSpan) characterStyle5).getForegroundColor() & 16777215)));
                }
                CharacterStyle characterStyle6 = characterStyleArr[i3];
                if (characterStyle6 instanceof BackgroundColorSpan) {
                    sb.append(String.format("<span style=\"background-color:#%06X;\">", Integer.valueOf(((BackgroundColorSpan) characterStyle6).getBackgroundColor() & 16777215)));
                }
            }
            withinStyle(sb, spanned, i, iNextSpanTransition);
            for (int length = characterStyleArr.length - 1; length >= 0; length--) {
                if (characterStyleArr[length] instanceof BackgroundColorSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof ForegroundColorSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof RelativeSizeSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof AbsoluteSizeSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof URLSpan) {
                    sb.append("</a>");
                }
                if (characterStyleArr[length] instanceof StrikethroughSpan) {
                    sb.append("</span>");
                }
                if (characterStyleArr[length] instanceof UnderlineSpan) {
                    sb.append("</u>");
                }
                if (characterStyleArr[length] instanceof SubscriptSpan) {
                    sb.append("</sub>");
                }
                if (characterStyleArr[length] instanceof SuperscriptSpan) {
                    sb.append("</sup>");
                }
                CharacterStyle characterStyle7 = characterStyleArr[length];
                if ((characterStyle7 instanceof TypefaceSpan) && "monospace".equals(((TypefaceSpan) characterStyle7).getFamily())) {
                    sb.append("</tt>");
                }
                CharacterStyle characterStyle8 = characterStyleArr[length];
                if (characterStyle8 instanceof StyleSpan) {
                    int style2 = ((StyleSpan) characterStyle8).getStyle();
                    if ((style2 & 1) != 0) {
                        sb.append("</b>");
                    }
                    if ((style2 & 2) != 0) {
                        sb.append("</i>");
                    }
                }
            }
            i = iNextSpanTransition;
        }
    }

    private static void withinStyle(StringBuilder sb, CharSequence charSequence, int i, int i2) {
        int i3;
        char cCharAt;
        while (i < i2) {
            char cCharAt2 = charSequence.charAt(i);
            if (cCharAt2 == '<') {
                sb.append("&lt;");
            } else if (cCharAt2 == '>') {
                sb.append("&gt;");
            } else if (cCharAt2 == '&') {
                sb.append("&amp;");
            } else if (cCharAt2 < 55296 || cCharAt2 > 57343) {
                if (cCharAt2 > '~' || cCharAt2 < ' ') {
                    sb.append("&#");
                    sb.append((int) cCharAt2);
                    sb.append(NavigationBarInflaterView.GRAVITY_SEPARATOR);
                } else if (cCharAt2 == ' ') {
                    while (true) {
                        int i4 = i + 1;
                        if (i4 >= i2 || charSequence.charAt(i4) != ' ') {
                            break;
                        }
                        sb.append("&nbsp;");
                        i = i4;
                    }
                    sb.append(' ');
                } else {
                    sb.append(cCharAt2);
                }
            } else if (cCharAt2 < 56320 && (i3 = i + 1) < i2 && (cCharAt = charSequence.charAt(i3)) >= 56320 && cCharAt <= 57343) {
                sb.append("&#");
                sb.append(((cCharAt2 - 55296) << 10) | 65536 | (cCharAt - 56320));
                sb.append(NavigationBarInflaterView.GRAVITY_SEPARATOR);
                i = i3;
            }
            i++;
        }
    }
}
