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

    public VEAnalyzer(VEContext context, Looper looper) {
        super(context, ElementType.ANALYZER, 0, "Analyzer");
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

    public void setInfo(AnalyzeInfo info) {
        this.info = info;
        this.status = AnalyzeStatus.INITIALIZED;
        this.context.getNativeInterface().setAnalyzeInfo(info);
    }

    public void setListener(AnalyzeStatusListener listener) {
        this.listener = listener;
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
        this.analyzedTime = this.context.getNativeInterface().getCurrentAnalyzedPosition();
        return this.analyzedTime;
    }

    public void onEvent(int event, int arg1, int arg2, int arg3) {
        if (this.eventHandler != null) {
            Message m = this.eventHandler.obtainMessage(event, arg1, arg2, Integer.valueOf(arg3));
            this.eventHandler.sendMessage(m);
        }
    }

    public void loadSolution(AnalyzeType type) {
        if (isSolutionLoaded(type)) {
            Log.i(this.TAG, "Solution is already loaded : " + type);
        } else {
            this.context.getNativeInterface().loadAnalyzeSolution(type);
        }
    }

    public void unloadSolution(AnalyzeType type) {
        if (!isSolutionLoaded(type)) {
            Log.i(this.TAG, "Solution is already unloaded : " + type);
        } else {
            this.context.getNativeInterface().unloadAnalyzeSolution(type);
        }
    }

    public boolean isSolutionLoaded(AnalyzeType type) {
        if (!this.solutionLoadChecker.containsKey(type)
                || !this.solutionLoadChecker.get(type).booleanValue()) {
            return false;
        }
        return true;
    }

    private class AnalyzeEventHandler extends Handler {
        public AnalyzeEventHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what < 0 || msg.what >= AnalyzeStatus.values().length) {
                Log.e(VEAnalyzer.this.TAG, "Analyzer Status is invalid : " + msg.what);
                return;
            }
            AnalyzeStatus status = AnalyzeStatus.values()[msg.what];
            switch (status) {
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
                    AnalyzeType analyzeType = AnalyzeType.values()[msg.arg1];
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onSolutionLoaded(analyzeType);
                    }
                    VEAnalyzer.this.solutionLoadChecker.put(analyzeType, true);
                    return;
                case SOLUTION_UNLOADED:
                    AnalyzeType analyzeType2 = AnalyzeType.values()[msg.arg1];
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onSolutionUnloaded(analyzeType2);
                    }
                    VEAnalyzer.this.solutionLoadChecker.put(analyzeType2, false);
                    return;
                case SOLUTION_FAILED:
                    AnalyzeType analyzeType3 = AnalyzeType.values()[msg.arg1];
                    AnalyzeSolutionErrorType analyzeSolutionErrorType =
                            AnalyzeSolutionErrorType.values()[msg.arg2];
                    if (VEAnalyzer.this.listener != null) {
                        VEAnalyzer.this.listener.onSolutionError(
                                analyzeType3, analyzeSolutionErrorType);
                        return;
                    }
                    return;
            }
            VEAnalyzer.this.status = status;
        }
    }
}
