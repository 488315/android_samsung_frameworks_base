package android.media;

import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayDeque;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlParser {
    private static final int DEFAULT_FRAMERATE = 30;
    private static final int DEFAULT_SUBFRAMERATE = 1;
    private static final int DEFAULT_TICKRATE = 1;
    static final String TAG = "TtmlParser";
    private long mCurrentRunId;
    private final TtmlNodeListener mListener;
    private XmlPullParser mParser;

    public TtmlParser(TtmlNodeListener ttmlNodeListener) {
        this.mListener = ttmlNodeListener;
    }

    public void parse(String str, long j) throws XmlPullParserException, IOException {
        this.mParser = null;
        this.mCurrentRunId = j;
        loadParser(str);
        parseTtml();
    }

    private void loadParser(String str) throws XmlPullParserException {
        XmlPullParserFactory newInstance = XmlPullParserFactory.newInstance();
        newInstance.setNamespaceAware(false);
        this.mParser = newInstance.newPullParser();
        this.mParser.setInput(new StringReader(str));
    }

    private void extractAttribute(XmlPullParser xmlPullParser, int i, StringBuilder sb) {
        sb.append(" ");
        sb.append(xmlPullParser.getAttributeName(i));
        sb.append("=\"");
        sb.append(xmlPullParser.getAttributeValue(i));
        sb.append("\"");
    }

    private void parseTtml() throws XmlPullParserException, IOException {
        ArrayDeque arrayDeque = new ArrayDeque();
        int i = 0;
        boolean z = true;
        while (!isEndOfDoc()) {
            int eventType = this.mParser.getEventType();
            TtmlNode ttmlNode = (TtmlNode) arrayDeque.peekLast();
            if (z) {
                if (eventType == 2) {
                    if (!isSupportedTag(this.mParser.getName())) {
                        Log.w(TAG, "Unsupported tag " + this.mParser.getName() + " is ignored.");
                        i++;
                        z = false;
                    } else {
                        TtmlNode parseNode = parseNode(ttmlNode);
                        arrayDeque.addLast(parseNode);
                        if (ttmlNode != null) {
                            ttmlNode.mChildren.add(parseNode);
                        }
                    }
                } else if (eventType == 4) {
                    String text = this.mParser.getText();
                    if (!TextUtils.isEmpty(text) && ttmlNode != null) {
                        ttmlNode.mChildren.add(new TtmlNode(TtmlUtils.PCDATA, "", text, 0L, Long.MAX_VALUE, ttmlNode, this.mCurrentRunId));
                    }
                } else if (eventType == 3) {
                    if (this.mParser.getName().equals("p")) {
                        this.mListener.onTtmlNodeParsed((TtmlNode) arrayDeque.getLast());
                    } else if (this.mParser.getName().equals(TtmlUtils.TAG_TT)) {
                        this.mListener.onRootNodeParsed((TtmlNode) arrayDeque.getLast());
                    }
                    arrayDeque.removeLast();
                }
            } else if (eventType == 2) {
                i++;
            } else if (eventType == 3 && i - 1 == 0) {
                z = true;
            }
            this.mParser.next();
        }
    }

    private TtmlNode parseNode(TtmlNode ttmlNode) throws XmlPullParserException, IOException {
        long j;
        long j2;
        long j3;
        long j4;
        if (this.mParser.getEventType() != 2) {
            return null;
        }
        if (this.mParser.getName().equals("p")) {
            j2 = 0;
            j3 = 0;
            j4 = Long.MAX_VALUE;
            for (int i = 0; i < this.mParser.getAttributeCount() && (j2 == 0 || ((j4 == 0 && j3 == 0) || i <= 1)); i++) {
                String attributeName = this.mParser.getAttributeName(i);
                String attributeValue = this.mParser.getAttributeValue(i);
                String replaceFirst = attributeName.replaceFirst("^.*:", "");
                if (replaceFirst.equals("begin")) {
                    j2 = TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
                } else if (replaceFirst.equals("end")) {
                    j4 = TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
                } else if (replaceFirst.equals(TtmlUtils.ATTR_DURATION)) {
                    j3 = TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
                }
            }
            j = Long.MAX_VALUE;
        } else {
            j = Long.MAX_VALUE;
            j2 = 0;
            j3 = 0;
            j4 = Long.MAX_VALUE;
        }
        if (ttmlNode != null) {
            j2 += ttmlNode.mStartTimeMs;
            if (j4 != j) {
                j4 += ttmlNode.mStartTimeMs;
            }
        }
        if (j3 > 0) {
            if (j4 != j) {
                Log.e(TAG, "'dur' and 'end' attributes are defined at the same time.'end' value is ignored.");
            }
            j4 = j2 + j3;
        }
        if (ttmlNode != null && j4 == j && ttmlNode.mEndTimeMs != j && j4 > ttmlNode.mEndTimeMs) {
            j4 = ttmlNode.mEndTimeMs;
        }
        return new TtmlNode(this.mParser.getName(), null, null, j2, j4, ttmlNode, this.mCurrentRunId);
    }

    private boolean isEndOfDoc() throws XmlPullParserException {
        return this.mParser.getEventType() == 1;
    }

    private static boolean isSupportedTag(String str) {
        return str.equals(TtmlUtils.TAG_TT) || str.equals(TtmlUtils.TAG_HEAD) || str.equals("body") || str.equals(TtmlUtils.TAG_DIV) || str.equals("p") || str.equals(TtmlUtils.TAG_SPAN) || str.equals(TtmlUtils.TAG_BR);
    }
}
