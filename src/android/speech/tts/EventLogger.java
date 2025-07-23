package android.speech.tts;

import android.text.TextUtils;

/* loaded from: classes3.dex */
class EventLogger extends AbstractEventLogger {
    private final SynthesisRequest mRequest;

    EventLogger(SynthesisRequest synthesisRequest, int i, int i2, String str) {
        super(i, i2, str);
        this.mRequest = synthesisRequest;
    }

    @Override // android.speech.tts.AbstractEventLogger
    protected void logFailure(int i) {
        if (i != -2) {
            EventLogTags.writeTtsSpeakFailure(this.mServiceApp, this.mCallerUid, this.mCallerPid, getUtteranceLength(), getLocaleString(), this.mRequest.getSpeechRate(), this.mRequest.getPitch());
        }
    }

    @Override // android.speech.tts.AbstractEventLogger
    protected void logSuccess(long j, long j2, long j3) {
        EventLogTags.writeTtsSpeakSuccess(this.mServiceApp, this.mCallerUid, this.mCallerPid, getUtteranceLength(), getLocaleString(), this.mRequest.getSpeechRate(), this.mRequest.getPitch(), j2, j3, j);
    }

    private int getUtteranceLength() {
        String text = this.mRequest.getText();
        if (text == null) {
            return 0;
        }
        return text.length();
    }

    private String getLocaleString() {
        StringBuilder sb = new StringBuilder(this.mRequest.getLanguage());
        if (!TextUtils.isEmpty(this.mRequest.getCountry())) {
            sb.append('-');
            sb.append(this.mRequest.getCountry());
            if (!TextUtils.isEmpty(this.mRequest.getVariant())) {
                sb.append('-');
                sb.append(this.mRequest.getVariant());
            }
        }
        return sb.toString();
    }
}
