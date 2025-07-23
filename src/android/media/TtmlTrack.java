package android.media;

import android.media.SubtitleTrack;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: TtmlRenderer.java */
/* loaded from: classes2.dex */
class TtmlTrack extends SubtitleTrack implements TtmlNodeListener {
    private static final String TAG = "TtmlTrack";
    private Long mCurrentRunID;
    private final TtmlParser mParser;
    private String mParsingData;
    private final TtmlRenderingWidget mRenderingWidget;
    private TtmlNode mRootNode;
    private final TreeSet<Long> mTimeEvents;
    private final ArrayList<TtmlNode> mTtmlNodes;

    TtmlTrack(TtmlRenderingWidget ttmlRenderingWidget, MediaFormat mediaFormat) {
        super(mediaFormat);
        this.mParser = new TtmlParser(this);
        this.mTtmlNodes = new ArrayList<>();
        this.mTimeEvents = new TreeSet<>();
        this.mRenderingWidget = ttmlRenderingWidget;
        this.mParsingData = "";
    }

    @Override // android.media.SubtitleTrack
    public TtmlRenderingWidget getRenderingWidget() {
        return this.mRenderingWidget;
    }

    @Override // android.media.SubtitleTrack
    public void onData(byte[] bArr, boolean z, long j) {
        try {
            String str = new String(bArr, "UTF-8");
            synchronized (this.mParser) {
                Long l = this.mCurrentRunID;
                if (l != null && j != l.longValue()) {
                    throw new IllegalStateException("Run #" + this.mCurrentRunID + " in progress.  Cannot process run #" + j);
                }
                this.mCurrentRunID = Long.valueOf(j);
                String str2 = this.mParsingData + str;
                this.mParsingData = str2;
                if (z) {
                    try {
                        try {
                            this.mParser.parse(str2, this.mCurrentRunID.longValue());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (XmlPullParserException e2) {
                        e2.printStackTrace();
                    }
                    finishedRun(j);
                    this.mParsingData = "";
                    this.mCurrentRunID = null;
                }
            }
        } catch (UnsupportedEncodingException e3) {
            Log.w(TAG, "subtitle data is not UTF-8 encoded: " + e3);
        }
    }

    @Override // android.media.TtmlNodeListener
    public void onTtmlNodeParsed(TtmlNode ttmlNode) {
        this.mTtmlNodes.add(ttmlNode);
        addTimeEvents(ttmlNode);
    }

    @Override // android.media.TtmlNodeListener
    public void onRootNodeParsed(TtmlNode ttmlNode) {
        this.mRootNode = ttmlNode;
        while (true) {
            TtmlCue nextResult = getNextResult();
            if (nextResult != null) {
                addCue(nextResult);
            } else {
                this.mRootNode = null;
                this.mTtmlNodes.clear();
                this.mTimeEvents.clear();
                return;
            }
        }
    }

    @Override // android.media.SubtitleTrack
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
        if (this.mVisible) {
            if (this.DEBUG && this.mTimeProvider != null) {
                try {
                    Log.d(TAG, "at " + (this.mTimeProvider.getCurrentTimeUs(false, true) / 1000) + " ms the active cues are:");
                } catch (IllegalStateException unused) {
                    Log.d(TAG, "at (illegal state) the active cues are:");
                }
            }
            this.mRenderingWidget.setActiveCues(vector);
        }
    }

    public TtmlCue getNextResult() {
        while (this.mTimeEvents.size() >= 2) {
            long longValue = this.mTimeEvents.pollFirst().longValue();
            long longValue2 = this.mTimeEvents.first().longValue();
            if (!getActiveNodes(longValue, longValue2).isEmpty()) {
                return new TtmlCue(longValue, longValue2, TtmlUtils.extractText(this.mRootNode, longValue, longValue2), null, this.mCurrentRunID.longValue());
            }
        }
        return null;
    }

    private void addTimeEvents(TtmlNode ttmlNode) {
        this.mTimeEvents.add(Long.valueOf(ttmlNode.mStartTimeMs));
        this.mTimeEvents.add(Long.valueOf(ttmlNode.mEndTimeMs));
        for (int i = 0; i < ttmlNode.mChildren.size(); i++) {
            addTimeEvents(ttmlNode.mChildren.get(i));
        }
    }

    private List<TtmlNode> getActiveNodes(long j, long j2) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.mTtmlNodes.size(); i++) {
            TtmlNode ttmlNode = this.mTtmlNodes.get(i);
            if (ttmlNode.isActive(j, j2)) {
                arrayList.add(ttmlNode);
            }
        }
        return arrayList;
    }
}
