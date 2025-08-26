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

    public void parse(String str, long j) throws XmlPullParserException, IOException, NumberFormatException {
        this.mParser = null;
        this.mCurrentRunId = j;
        loadParser(str);
        parseTtml();
    }

    private void loadParser(String str) throws XmlPullParserException {
        XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
        xmlPullParserFactoryNewInstance.setNamespaceAware(false);
        this.mParser = xmlPullParserFactoryNewInstance.newPullParser();
        this.mParser.setInput(new StringReader(str));
    }

    private void extractAttribute(XmlPullParser xmlPullParser, int i, StringBuilder sb) {
        sb.append(" ");
        sb.append(xmlPullParser.getAttributeName(i));
        sb.append("=\"");
        sb.append(xmlPullParser.getAttributeValue(i));
        sb.append("\"");
    }

    private void parseTtml() throws XmlPullParserException, IOException, NumberFormatException {
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
                        TtmlNode node = parseNode(ttmlNode);
                        arrayDeque.addLast(node);
                        if (ttmlNode != null) {
                            ttmlNode.mChildren.add(node);
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

    private TtmlNode parseNode(TtmlNode ttmlNode) throws XmlPullParserException, NumberFormatException, IOException {
        long j;
        long timeExpression;
        long timeExpression2;
        long timeExpression3;
        if (this.mParser.getEventType() != 2) {
            return null;
        }
        if (this.mParser.getName().equals("p")) {
            timeExpression = 0;
            timeExpression2 = 0;
            timeExpression3 = Long.MAX_VALUE;
            for (int i = 0; i < this.mParser.getAttributeCount() && (timeExpression == 0 || ((timeExpression3 == 0 && timeExpression2 == 0) || i <= 1)); i++) {
                String attributeName = this.mParser.getAttributeName(i);
                String attributeValue = this.mParser.getAttributeValue(i);
                String strReplaceFirst = attributeName.replaceFirst("^.*:", "");
                if (strReplaceFirst.equals("begin")) {
                    timeExpression = TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
                } else if (strReplaceFirst.equals("end")) {
                    timeExpression3 = TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
                } else if (strReplaceFirst.equals(TtmlUtils.ATTR_DURATION)) {
                    timeExpression2 = TtmlUtils.parseTimeExpression(attributeValue, 30, 1, 1);
                }
            }
            j = Long.MAX_VALUE;
        } else {
            j = Long.MAX_VALUE;
            timeExpression = 0;
            timeExpression2 = 0;
            timeExpression3 = Long.MAX_VALUE;
        }
        if (ttmlNode != null) {
            timeExpression += ttmlNode.mStartTimeMs;
            if (timeExpression3 != j) {
                timeExpression3 += ttmlNode.mStartTimeMs;
            }
        }
        if (timeExpression2 > 0) {
            if (timeExpression3 != j) {
                Log.e(TAG, "'dur' and 'end' attributes are defined at the same time.'end' value is ignored.");
            }
            timeExpression3 = timeExpression + timeExpression2;
        }
        if (ttmlNode != null && timeExpression3 == j && ttmlNode.mEndTimeMs != j && timeExpression3 > ttmlNode.mEndTimeMs) {
            timeExpression3 = ttmlNode.mEndTimeMs;
        }
        return new TtmlNode(this.mParser.getName(), null, null, timeExpression, timeExpression3, ttmlNode, this.mCurrentRunId);
    }

    private boolean isEndOfDoc() throws XmlPullParserException {
        return this.mParser.getEventType() == 1;
    }

    private static boolean isSupportedTag(String str) {
        return str.equals(TtmlUtils.TAG_TT) || str.equals(TtmlUtils.TAG_HEAD) || str.equals("body") || str.equals(TtmlUtils.TAG_DIV) || str.equals("p") || str.equals(TtmlUtils.TAG_SPAN) || str.equals(TtmlUtils.TAG_BR);
    }
}
