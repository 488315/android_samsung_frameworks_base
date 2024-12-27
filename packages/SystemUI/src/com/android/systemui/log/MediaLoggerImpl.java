package com.android.systemui.log;

import android.util.Log;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaLoggerImpl implements MediaLogger {
    public final MediaLogWriter writer;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public MediaLoggerImpl(MediaLogWriter mediaLogWriter) {
        this.writer = mediaLogWriter;
    }

    public final void addPlayer(String str, boolean z) {
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logAddPlayer$2 mediaLogWriter$logAddPlayer$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logAddPlayer$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return " Media player added [" + logMessage.getStr1() + "] isPlaying[" + logMessage.getBool1() + "]";
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logAddPlayer$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media player added [" + str + "] isPlaying[" + z + "]");
    }

    public final void onActionClicked(CharSequence charSequence, String str, int i) {
        String obj = charSequence != null ? charSequence.toString() : null;
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logOnActionClicked$2 mediaLogWriter$logOnActionClicked$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logOnActionClicked$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(int1, " Media action clicked [", str1, "][", "]["), logMessage.getStr2(), "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logOnActionClicked$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logMessageImpl.str2 = obj;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media action clicked [" + str + "][" + i + "][" + ((Object) charSequence) + "]");
    }

    public final void onConfigChanged(int i) {
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logOnConfigChanged$2 mediaLogWriter$logOnConfigChanged$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logOnConfigChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), " Media config changed data size is [", "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logOnConfigChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media config changed data size is [" + i + "]");
    }

    public final void onMediaDataLoaded(String str, String str2, CharSequence charSequence, boolean z, final String str3) {
        String obj = charSequence != null ? charSequence.toString() : null;
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        Function1 function1 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logOnMediaDataLoaded$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                LogMessage logMessage = (LogMessage) obj2;
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                String str32 = logMessage.getStr3();
                boolean bool1 = logMessage.getBool1();
                String str4 = str3;
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m(" Media data loaded key[", str1, "] oldKey[", str22, "] title[");
                m.append(str32);
                m.append("] active?[");
                m.append(bool1);
                m.append("]\n callStack[");
                return ComponentActivity$1$$ExternalSyntheticOutline0.m(m, str4, "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, function1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logMessageImpl.str3 = obj;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media data loaded key[" + str + "] oldKey[" + str2 + "] title[" + ((Object) charSequence) + "] isActive[" + z + "]");
    }

    public final void onMediaDataRemoved(String str, String str2) {
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logOnMediaDataRemoved$2 mediaLogWriter$logOnMediaDataRemoved$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logOnMediaDataRemoved$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return MotionLayout$$ExternalSyntheticOutline0.m(" Media data removed[", logMessage.getStr1(), "] \n callStack[", logMessage.getStr2(), "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logOnMediaDataRemoved$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.str2 = str2;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media data removed [" + str + "]");
    }

    public final void onRemoveClicked(String str) {
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logOnRemoveClicked$2 mediaLogWriter$logOnRemoveClicked$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logOnRemoveClicked$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" Media remove clicked [", ((LogMessage) obj).getStr1(), "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logOnRemoveClicked$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media remove clicked [" + str + "]");
    }

    public final void removePlayer(String str) {
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logRemovePlayer$2 mediaLogWriter$logRemovePlayer$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logRemovePlayer$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" Media player removed [", ((LogMessage) obj).getStr1(), "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logRemovePlayer$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media player removed [" + str + "]");
    }

    public final void removePlayerError(String str) {
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logRemovePlayerError$2 mediaLogWriter$logRemovePlayerError$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logRemovePlayerError$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" Media player removed error [", ((LogMessage) obj).getStr1(), "]");
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logRemovePlayerError$2, null);
        ((LogMessageImpl) obtain).str1 = str;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media player removed error [" + str + "]");
    }

    public final void updatePlayer(String str, boolean z) {
        MediaLogWriter mediaLogWriter = this.writer;
        mediaLogWriter.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        MediaLogWriter$logUpdatePlayer$2 mediaLogWriter$logUpdatePlayer$2 = new Function1() { // from class: com.android.systemui.log.MediaLogWriter$logUpdatePlayer$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return " Media player updated [" + logMessage.getStr1() + "] isPlaying[" + logMessage.getBool1() + "]";
            }
        };
        LogBuffer logBuffer = mediaLogWriter.buffer;
        LogMessage obtain = logBuffer.obtain("MediaLogger", logLevel, mediaLogWriter$logUpdatePlayer$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = str;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
        Log.d("MediaLogger", "Media player updated [" + str + "] isPlaying[" + z + "]");
    }
}
