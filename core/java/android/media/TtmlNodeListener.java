package android.media;

interface TtmlNodeListener {
    void onRootNodeParsed(TtmlNode ttmlNode);

    void onTtmlNodeParsed(TtmlNode ttmlNode);
}
