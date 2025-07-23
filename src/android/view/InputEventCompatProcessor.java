package android.view;

import android.content.Context;
import android.os.Handler;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class InputEventCompatProcessor {
    protected Context mContext;
    private final LetterboxScrollProcessor mLetterboxScrollProcessor;
    private final List<InputEvent> mProcessedEvents;
    protected int mTargetSdkVersion;

    public InputEventCompatProcessor(Context context) {
        this(context, null);
    }

    public InputEventCompatProcessor(Context context, Handler handler) {
        this.mContext = context;
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        if (Flags.scrollingFromLetterbox()) {
            this.mLetterboxScrollProcessor = new LetterboxScrollProcessor(this.mContext, handler);
        } else {
            this.mLetterboxScrollProcessor = null;
        }
        this.mProcessedEvents = new ArrayList();
    }

    public List<InputEvent> processInputEventForCompatibility(InputEvent inputEvent) {
        this.mProcessedEvents.clear();
        InputEvent processStylusButtonCompatibility = processStylusButtonCompatibility(inputEvent);
        if (processStylusButtonCompatibility != null) {
            inputEvent = processStylusButtonCompatibility;
        }
        List<MotionEvent> processLetterboxScrollCompatibility = processLetterboxScrollCompatibility(inputEvent);
        if (processLetterboxScrollCompatibility != null) {
            this.mProcessedEvents.addAll(processLetterboxScrollCompatibility);
            return this.mProcessedEvents;
        }
        if (processStylusButtonCompatibility == null) {
            return null;
        }
        this.mProcessedEvents.add(processStylusButtonCompatibility);
        return this.mProcessedEvents;
    }

    public InputEvent processInputEventBeforeFinish(InputEvent inputEvent) {
        LetterboxScrollProcessor letterboxScrollProcessor = this.mLetterboxScrollProcessor;
        return (letterboxScrollProcessor == null || !(inputEvent instanceof MotionEvent)) ? inputEvent : letterboxScrollProcessor.processMotionEventBeforeFinish((MotionEvent) inputEvent);
    }

    private List<MotionEvent> processLetterboxScrollCompatibility(InputEvent inputEvent) {
        if (this.mLetterboxScrollProcessor == null || !(inputEvent instanceof MotionEvent)) {
            return null;
        }
        MotionEvent motionEvent = (MotionEvent) inputEvent;
        if (motionEvent.getAction() != 4) {
            return this.mLetterboxScrollProcessor.processMotionEvent(motionEvent);
        }
        return null;
    }

    private InputEvent processStylusButtonCompatibility(InputEvent inputEvent) {
        if (this.mTargetSdkVersion >= 23 || !(inputEvent instanceof MotionEvent)) {
            return null;
        }
        MotionEvent motionEvent = (MotionEvent) inputEvent;
        int buttonState = motionEvent.getButtonState();
        int i = (buttonState & 96) >> 4;
        if (i != 0) {
            motionEvent.setButtonState(buttonState | i);
        }
        return motionEvent;
    }
}
