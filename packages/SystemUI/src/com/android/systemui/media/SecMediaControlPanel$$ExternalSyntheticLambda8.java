package com.android.systemui.media;

import android.util.Log;
import android.widget.ImageButton;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.MediaLogWriter;
import com.android.systemui.log.MediaLogWriter$$ExternalSyntheticLambda0;
import com.android.systemui.log.MediaLoggerImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaControlPanel$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ SecMediaControlPanel f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ ImageButton f$3;

    public /* synthetic */ SecMediaControlPanel$$ExternalSyntheticLambda8(SecMediaControlPanel secMediaControlPanel, int i, String str, ImageButton imageButton) {
        this.f$0 = secMediaControlPanel;
        this.f$1 = i;
        this.f$2 = str;
        this.f$3 = imageButton;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        String string;
        SecMediaControlPanel secMediaControlPanel = this.f$0;
        int i = this.f$1;
        String str = this.f$2;
        ImageButton imageButton = this.f$3;
        if (secMediaControlPanel.mType.getSupportCoverQuickPanelMedia()) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QP_MEDIA_CONTROLLER_COVER);
        } else {
            SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_QPNE_BUTTONS_MEDIA, "type", Integer.toString(i), SystemUIAnalytics.QPNE_KEY_APP, str);
        }
        String strName = secMediaControlPanel.mType.name();
        String str2 = secMediaControlPanel.mPlayerKey;
        int id = imageButton.getId();
        CharSequence contentDescription = imageButton.getContentDescription();
        MediaLoggerImpl mediaLoggerImpl = (MediaLoggerImpl) secMediaControlPanel.mLogger;
        if (contentDescription != null) {
            mediaLoggerImpl.getClass();
            string = contentDescription.toString();
        } else {
            string = null;
        }
        MediaLogWriter mediaLogWriter = mediaLoggerImpl.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$$ExternalSyntheticLambda0 mediaLogWriter$$ExternalSyntheticLambda0 = new MediaLogWriter$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str2;
        logMessageImpl.int1 = id;
        logMessageImpl.str2 = string;
        logBuffer.commit(logMessageObtain);
        Log.d("MediaLogger", "[" + strName + "] Media action clicked [" + str2 + "][" + id + "][" + ((Object) contentDescription) + "]");
    }
}
