package com.samsung.vekit.Control;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.samsung.vekit.Common.Object.AnalyzeInfo;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.AnalyzeSolutionErrorType;
import com.samsung.vekit.Common.Type.AnalyzeType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Listener.AnalyzeStatusListener;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class VEAnalyzer extends Element {
    private long analyzedTime;
    private final AnalyzeEventHandler eventHandler;
    private AnalyzeInfo info;
    private boolean isAnalyzing;
    AnalyzeStatusListener listener;
    HashMap<AnalyzeType, Boolean> solutionLoadChecker;
    AnalyzeStatus status;

    public enum AnalyzeStatus {
        INITIALIZED,
        STARTED,
        STOPPED,
        FAILED,
        COMPLETED,
        PAUSED,
        SOLUTION_LOADED,
        SOLUTION_UNLOADED,
        SOLUTION_FAILED
    }

    public VEAnalyzer(VEContext vEContext, Looper looper) {
        super(vEContext, ElementType.ANALYZER, 0, "Analyzer");
        this.isAnalyzing = false;
        this.analyzedTime = 0L;
        this.TAG = getClass().getSimpleName();
        if (looper != null) {
            this.eventHandler = new AnalyzeEventHandler(looper);
        } else {
            this.eventHandler = null;
        }
        this.status = AnalyzeStatus.INITIALIZED;
        this.solutionLoadChecker = new HashMap<>();
    }

    public AnalyzeInfo getInfo() {
        return this.info;
    }

    public void setInfo(AnalyzeInfo analyzeInfo) {
        this.info = analyzeInfo;
        this.status = AnalyzeStatus.INITIALIZED;
        this.context.getNativeInterface().setAnalyzeInfo(analyzeInfo);
    }

    public void setListener(AnalyzeStatusListener analyzeStatusListener) {
        this.listener = analyzeStatusListener;
    }

    public void start() {
        this.isAnalyzing = true;
        this.analyzedTime = 0L;
        this.context.getNativeInterface().startAnalyze();
    }

    public void stop() {
        this.context.getNativeInterface().stopAnalyze();
        this.isAnalyzing = false;
    }

    public void resume() {
        this.isAnalyzing = true;
        this.context.getNativeInterface().resumeAnalyze(this.analyzedTime);
    }

    public void pause() {
        this.isAnalyzing = false;
        this.analyzedTime = this.context.getNativeInterface().pauseAnalyze();
    }

    boolean isAnalyzing() {
        return this.isAnalyzing;
    }

    public long getCurrentAnalyzedPosition() {
        long currentAnalyzedPosition = this.context.getNativeInterface().getCurrentAnalyzedPosition();
        this.analyzedTime = currentAnalyzedPosition;
        return currentAnalyzedPosition;
    }

    public void onEvent(int i, int i2, int i3, int i4) {
        AnalyzeEventHandler analyzeEventHandler = this.eventHandler;
        if (analyzeEventHandler != null) {
            this.eventHandler.sendMessage(analyzeEventHandler.obtainMessage(i, i2, i3, Integer.valueOf(i4)));
        }
    }

    public void loadSolution(AnalyzeType analyzeType) {
        if (isSolutionLoaded(analyzeType)) {
            Log.i(this.TAG, "Solution is already loaded : " + analyzeType);
            return;
        }
        this.context.getNativeInterface().loadAnalyzeSolution(analyzeType);
    }

    public void unloadSolution(AnalyzeType analyzeType) {
        if (!isSolutionLoaded(analyzeType)) {
            Log.i(this.TAG, "Solution is already unloaded : " + analyzeType);
            return;
        }
        this.context.getNativeInterface().unloadAnalyzeSolution(analyzeType);
    }

    public boolean isSolutionLoaded(AnalyzeType analyzeType) {
        return this.solutionLoadChecker.containsKey(analyzeType) && this.solutionLoadChecker.get(analyzeType).booleanValue();
    }

    private class AnalyzeEventHandler extends Handler {
        public AnalyzeEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what < 0 || message.what >= AnalyzeStatus.values().length) {
                Log.e(VEAnalyzer.this.TAG, "Analyzer Status is invalid : " + message.what);
                return;
            }
            AnalyzeStatus analyzeStatus = AnalyzeStatus.values()[message.what];
            switch (analyzeStatus) {
                case STARTED:
                    if (VEAnalyzer.this.listener != null) {
                        if (VEAnalyzer.this.status == AnalyzeStatus.INITIALIZED) {
                            if (VEAnalyzer.this.info != null) {
                                VEAnalyzer.this.info.onAnalyzeStarted();
                            }
                            VEAnalyzer.this.listener.onAnalyzeStarted();
                            break;
                        } else if (VEAnalyzer.this.status == AnalyzeStatus.PAUSED) {
                            if (VEAnalyzer.this.info != null) {
                                VEAnalyzer.this.info.onAnalyzeResumed();
                            }
                            VEAnalyzer.this.listener.onAnalyzeResumed();
                            break;
                        }
                    }
                    break;
                case STOPPED:
                    if (VEAnalyzer.this.info != null) {
                        VEAnalyzer.this.info.onAnalyzeStopped();
                    }
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onAnalyzeStopped();
                        break;
                    }
                    break;
                case FAILED:
                    if (VEAnalyzer.this.info != null) {
                        VEAnalyzer.this.info.onAnalyzeFailed();
                    }
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onAnalyzeFailed();
                        break;
                    }
                    break;
                case COMPLETED:
                    if (VEAnalyzer.this.info != null) {
                        VEAnalyzer.this.info.onAnalyzeCompleted();
                    }
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onAnalyzeCompleted();
                        break;
                    }
                    break;
                case PAUSED:
                    if (VEAnalyzer.this.info != null) {
                        VEAnalyzer.this.info.onAnalyzePaused();
                    }
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onAnalyzePaused();
                        break;
                    }
                    break;
                case SOLUTION_LOADED:
                    AnalyzeType analyzeType = AnalyzeType.values()[message.arg1];
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onSolutionLoaded(analyzeType);
                    }
                    VEAnalyzer.this.solutionLoadChecker.put(analyzeType, true);
                    return;
                case SOLUTION_UNLOADED:
                    AnalyzeType analyzeType2 = AnalyzeType.values()[message.arg1];
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onSolutionUnloaded(analyzeType2);
                    }
                    VEAnalyzer.this.solutionLoadChecker.put(analyzeType2, false);
                    return;
                case SOLUTION_FAILED:
                    AnalyzeType analyzeType3 = AnalyzeType.values()[message.arg1];
                    AnalyzeSolutionErrorType analyzeSolutionErrorType = AnalyzeSolutionErrorType.values()[message.arg2];
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onSolutionError(analyzeType3, analyzeSolutionErrorType);
                        return;
                    }
                    return;
            }
            VEAnalyzer.this.status = analyzeStatus;
        }
    }
}
