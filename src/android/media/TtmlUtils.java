package android.media;

import android.app.backup.FullBackup;
import android.app.blob.XmlTags;
import android.hardware.scontext.SContextConstants;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
final class TtmlUtils {
    public static final String ATTR_BEGIN = "begin";
    public static final String ATTR_DURATION = "dur";
    public static final String ATTR_END = "end";
    public static final String ATTR_EXTENT = "extent";
    public static final String ATTR_ID = "id";
    public static final String ATTR_ORIGIN = "origin";
    public static final String ATTR_REGION = "region";
    public static final long INVALID_TIMESTAMP = Long.MAX_VALUE;
    public static final String PCDATA = "#pcdata";
    public static final String TAG_BODY = "body";
    public static final String TAG_BR = "br";
    public static final String TAG_DIV = "div";
    public static final String TAG_HEAD = "head";
    public static final String TAG_LAYOUT = "layout";
    public static final String TAG_METADATA = "metadata";
    public static final String TAG_P = "p";
    public static final String TAG_REGION = "region";
    public static final String TAG_SMPTE_DATA = "smpte:data";
    public static final String TAG_SMPTE_IMAGE = "smpte:image";
    public static final String TAG_SMPTE_INFORMATION = "smpte:information";
    public static final String TAG_SPAN = "span";
    public static final String TAG_STYLE = "style";
    public static final String TAG_STYLING = "styling";
    public static final String TAG_TT = "tt";
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern REGION_LENGTH = Pattern.compile("^([0-9][0-9]*.?[0-9]*)(%|px|c)(\\s*)([0-9][0-9]*.?[0-9]*)(%|px|c)$");

    private TtmlUtils() {
    }

    public static long parseTimeExpression(String str, int i, int i2, int i3) throws NumberFormatException {
        double d;
        double d2;
        Matcher matcher = CLOCK_TIME.matcher(str);
        if (matcher.matches()) {
            double parseLong = (Long.parseLong(matcher.group(1)) * 3600) + (Long.parseLong(matcher.group(2)) * 60) + Long.parseLong(matcher.group(3));
            String group = matcher.group(4);
            double d3 = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            double parseDouble = parseLong + (group != null ? Double.parseDouble(group) : 0.0d) + (matcher.group(5) != null ? Long.parseLong(r14) / i : 0.0d);
            if (matcher.group(6) != null) {
                d3 = (Long.parseLong(r14) / i2) / i;
            }
            return (long) ((parseDouble + d3) * 1000.0d);
        }
        Matcher matcher2 = OFFSET_TIME.matcher(str);
        if (matcher2.matches()) {
            double parseDouble2 = Double.parseDouble(matcher2.group(1));
            String group2 = matcher2.group(2);
            if (group2.equals("h")) {
                d2 = 3.6E9d;
            } else {
                if (!group2.equals("m")) {
                    if (!group2.equals(XmlTags.TAG_SESSION)) {
                        if (group2.equals("ms")) {
                            parseDouble2 *= 1000.0d;
                        } else {
                            if (group2.equals(FullBackup.FILES_TREE_TOKEN)) {
                                d = i;
                            } else if (group2.equals("t")) {
                                d = i3;
                            }
                            parseDouble2 /= d;
                        }
                        return (long) parseDouble2;
                    }
                    parseDouble2 *= 1000000.0d;
                    return (long) parseDouble2;
                }
                d2 = 6.0E7d;
            }
            parseDouble2 *= d2;
            return (long) parseDouble2;
        }
        throw new NumberFormatException("Malformed time expression : " + str);
    }

    public static String applyDefaultSpacePolicy(String str) {
        return applySpacePolicy(str, true);
    }

    public static String applySpacePolicy(String str, boolean z) {
        String replaceAll = str.replaceAll("\n$", "").replaceAll("\r\n", ShaderAssembler.NEWLINE).replaceAll(" *\n *", ShaderAssembler.NEWLINE);
        if (z) {
            replaceAll = replaceAll.replaceAll(ShaderAssembler.NEWLINE, " ");
        }
        return replaceAll.replaceAll("[ \t\\x0B\f\r]+", " ");
    }

    public static String extractText(TtmlNode ttmlNode, long j, long j2) {
        StringBuilder sb = new StringBuilder();
        extractText(ttmlNode, j, j2, sb, false);
        return sb.toString().replaceAll("\n$", "");
    }

    private static void extractText(TtmlNode ttmlNode, long j, long j2, StringBuilder sb, boolean z) {
        boolean z2;
        long j3;
        long j4;
        if (ttmlNode.mName.equals(PCDATA) && z) {
            sb.append(ttmlNode.mText);
            return;
        }
        if (ttmlNode.mName.equals(TAG_BR) && z) {
            sb.append(ShaderAssembler.NEWLINE);
            return;
        }
        if (!ttmlNode.mName.equals(TAG_METADATA) && ttmlNode.isActive(j, j2)) {
            boolean equals = ttmlNode.mName.equals("p");
            int length = sb.length();
            for (int i = 0; i < ttmlNode.mChildren.size(); i++) {
                TtmlNode ttmlNode2 = ttmlNode.mChildren.get(i);
                if (equals || z) {
                    z2 = true;
                    j3 = j2;
                    j4 = j;
                } else {
                    z2 = false;
                    j4 = j;
                    j3 = j2;
                }
                extractText(ttmlNode2, j4, j3, sb, z2);
            }
            if (!equals || length == sb.length()) {
                return;
            }
            sb.append(ShaderAssembler.NEWLINE);
        }
    }

    public static String extractTtmlFragment(TtmlNode ttmlNode, long j, long j2) {
        StringBuilder sb = new StringBuilder();
        extractTtmlFragment(ttmlNode, j, j2, sb);
        return sb.toString();
    }

    private static void extractTtmlFragment(TtmlNode ttmlNode, long j, long j2, StringBuilder sb) {
        if (ttmlNode.mName.equals(PCDATA)) {
            sb.append(ttmlNode.mText);
            return;
        }
        if (ttmlNode.mName.equals(TAG_BR)) {
            sb.append("<br/>");
            return;
        }
        if (ttmlNode.isActive(j, j2)) {
            sb.append("<");
            sb.append(ttmlNode.mName);
            sb.append(ttmlNode.mAttributes);
            sb.append(">");
            for (int i = 0; i < ttmlNode.mChildren.size(); i++) {
                extractTtmlFragment(ttmlNode.mChildren.get(i), j, j2, sb);
            }
            sb.append("</");
            sb.append(ttmlNode.mName);
            sb.append(">");
        }
    }
}
