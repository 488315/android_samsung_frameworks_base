package com.android.server.media;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class VolumeCtrl {
    public static final String USAGE =
            new String(
                    "the options are as follows: \n"
                        + "\t\t--stream STREAM selects the stream to control, see"
                        + " AudioManager.STREAM_*\n"
                        + "\t\t                controls AudioManager.STREAM_MUSIC if no stream is"
                        + " specified\n"
                        + "\t\t--set INDEX     sets the volume index value\n"
                        + "\t\t--adj DIRECTION adjusts the volume, use raise|same|lower for the"
                        + " direction\n"
                        + "\t\t--get           outputs the current volume\n"
                        + "\t\t--show          shows the UI during the volume change\n"
                        + "\texamples:\n"
                        + "\t\tadb shell media_session volume --show --stream 3 --set 11\n"
                        + "\t\tadb shell media_session volume --stream 0 --adj lower\n"
                        + "\t\tadb shell media_session volume --stream 3 --get\n\n"
                        + "\t\tSamsung custom options are as follows: \n"
                        + "\t\t--setfine INDEX sets the fine volume index value\n"
                        + "\t\t--getfine       outputs the current fine volume\n"
                        + "\texamples:\n"
                        + "\t\tadb shell media_session volume --show --setfine 127\n"
                        + "\t\tadb shell media_session volume --getfine\n");
}
