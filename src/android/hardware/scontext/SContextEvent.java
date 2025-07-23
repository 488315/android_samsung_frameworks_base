package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEvent implements Parcelable {
    static final Parcelable.Creator<SContextEvent> CREATOR = new Parcelable.Creator<SContextEvent>() { // from class: android.hardware.scontext.SContextEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEvent createFromParcel(Parcel parcel) {
            return new SContextEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEvent[] newArray(int i) {
            return new SContextEvent[i];
        }
    };
    private SContextEventContext mDuplicatedEventContext;
    private SContextEventContext mEventContext;
    public SContext scontext;
    public long timestamp;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SContextEvent() {
        this.scontext = new SContext();
        this.timestamp = 0L;
    }

    public SContextEvent(Parcel parcel) {
        readFromParcel(parcel);
    }

    public SContextApproach getApproachContext() {
        return (SContextApproach) this.mEventContext;
    }

    public SContextPedometer getPedometerContext() {
        return (SContextPedometer) this.mEventContext;
    }

    SContextStepCountAlert getStepCountAlertContext() {
        return (SContextStepCountAlert) this.mEventContext;
    }

    @Deprecated
    public SContextMotion getMotionContext() {
        return (SContextMotion) this.mEventContext;
    }

    public SContextMovement getMovementContext() {
        return (SContextMovement) this.mEventContext;
    }

    public SContextAutoRotation getAutoRotationContext() {
        return (SContextAutoRotation) this.mEventContext;
    }

    public SContextAirMotion getAirMotionContext() {
        return (SContextAirMotion) this.mEventContext;
    }

    @Deprecated
    public SContextEnvironment getEnvironmentContext() {
        return (SContextEnvironment) this.mEventContext;
    }

    @Deprecated
    public SContextMovementForPositioning getMovementForPositioningContext() {
        return (SContextMovementForPositioning) this.mEventContext;
    }

    @Deprecated
    public SContextCurrentStatusForPositioning getCurrentStatusForPositioningContext() {
        return (SContextCurrentStatusForPositioning) this.mEventContext;
    }

    public SContextCallPose getCallPoseContext() {
        return (SContextCallPose) this.mEventContext;
    }

    public SContextShakeMotion getShakeMotionContext() {
        return (SContextShakeMotion) this.mEventContext;
    }

    public SContextFlipCoverAction getFlipCoverActionContext() {
        return (SContextFlipCoverAction) this.mEventContext;
    }

    public SContextGyroTemperature getGyroTemperatureContext() {
        return (SContextGyroTemperature) this.mEventContext;
    }

    public SContextPutDownMotion getPutDownMotionContext() {
        return (SContextPutDownMotion) this.mEventContext;
    }

    public SContextWakeUpVoice getWakeUpVoiceContext() {
        return (SContextWakeUpVoice) this.mEventContext;
    }

    public SContextBounceShortMotion getBounceShortMotionContext() {
        return (SContextBounceShortMotion) this.mEventContext;
    }

    public SContextBounceLongMotion getBounceLongMotionContext() {
        return (SContextBounceLongMotion) this.mEventContext;
    }

    @Deprecated
    public SContextWristUpMotion getWristUpMotionContext() {
        return (SContextWristUpMotion) this.mEventContext;
    }

    public SContextFlatMotion getFlatMotionContext() {
        return (SContextFlatMotion) this.mEventContext;
    }

    @Deprecated
    public SContextMovementAlert getMovementAlertContext() {
        return (SContextMovementAlert) this.mEventContext;
    }

    @Deprecated
    public SContextTestFlatMotion getTestFlatMotionContext() {
        return (SContextTestFlatMotion) this.mEventContext;
    }

    public SContextDevicePosition getDevicePositionContext() {
        return (SContextDevicePosition) this.mEventContext;
    }

    @Deprecated
    SContextTemperatureAlert getTemperatureAlertContext() {
        return (SContextTemperatureAlert) this.mEventContext;
    }

    public SContextActivityLocationLogging getActivityLocationLoggingContext() {
        return (SContextActivityLocationLogging) this.mEventContext;
    }

    public SContextActivityTracker getActivityTrackerContext() {
        return (SContextActivityTracker) this.mEventContext;
    }

    public SContextActivityBatch getActivityBatchContext() {
        return (SContextActivityBatch) this.mEventContext;
    }

    public SContextActivityNotification getActivityNotificationContext() {
        return (SContextActivityNotification) this.mEventContext;
    }

    public SContextSpecificPoseAlert getSpecificPoseAlertContext() {
        return (SContextSpecificPoseAlert) this.mEventContext;
    }

    @Deprecated
    public SContextSleepMonitor getSleepMonitorContext() {
        return (SContextSleepMonitor) this.mEventContext;
    }

    public SContextActivityNotificationEx getActivityNotificationExContext() {
        return (SContextActivityNotificationEx) this.mEventContext;
    }

    @Deprecated
    SContextCaptureMotion getCaptureMotionContext() {
        return (SContextCaptureMotion) this.mEventContext;
    }

    SContextCallMotion getCallMotionContext() {
        return (SContextCallMotion) this.mEventContext;
    }

    public SContextStepLevelMonitor getStepLevelMonitorContext() {
        return (SContextStepLevelMonitor) this.mEventContext;
    }

    public SContextActiveTimeMonitor getActiveTimeMonitorContext() {
        return (SContextActiveTimeMonitor) this.mEventContext;
    }

    public SContextInactiveTimer getInactiveTimerContext() {
        return (SContextInactiveTimer) this.mEventContext;
    }

    public SContextFlatMotionForTableMode getFlatMotioForTableModeContext() {
        return (SContextFlatMotionForTableMode) this.mEventContext;
    }

    public SContextAutoBrightness getAutoBrightnessContext() {
        return (SContextAutoBrightness) this.mEventContext;
    }

    public SContextExercise getExerciseContext() {
        return (SContextExercise) this.mEventContext;
    }

    public SContextAbnormalPressure getAbnormalPressureContext() {
        return (SContextAbnormalPressure) this.mEventContext;
    }

    public SContextPhoneStatusMonitor getPhoneStatusMonitorContext() {
        return (SContextPhoneStatusMonitor) this.mEventContext;
    }

    public SContextHallSensor getHallSensorContext() {
        return (SContextHallSensor) this.mEventContext;
    }

    public SContextEnvironmentAdaptiveDisplay getEnvironmentAdaptiveDisplayContext() {
        return (SContextEnvironmentAdaptiveDisplay) this.mEventContext;
    }

    public SContextDualDisplayAngle getDualDisplayAngleContext() {
        return (SContextDualDisplayAngle) this.mEventContext;
    }

    public SContextWirelessChargingDetection getWirelessChargingDetectionContext() {
        return (SContextWirelessChargingDetection) this.mEventContext;
    }

    public SContextSLocationCore getSLocationCoreContext() {
        return (SContextSLocationCore) this.mEventContext;
    }

    public SContextMainScreenDetection getMainScreenDetectionContext() {
        return (SContextMainScreenDetection) this.mDuplicatedEventContext;
    }

    public SContextFlipMotion getFlipMotionContext() {
        return (SContextFlipMotion) this.mEventContext;
    }

    public SContextAnyMotionDetector getAnyMotionDetectorContext() {
        return (SContextAnyMotionDetector) this.mEventContext;
    }

    public SContextDevicePhysicalContextMonitor getDevicePhysicalContextMonitorContext() {
        return (SContextDevicePhysicalContextMonitor) this.mEventContext;
    }

    public SContextSensorStatusCheck getSensorStatusCheckContext() {
        return (SContextSensorStatusCheck) this.mEventContext;
    }

    public void setSContextEvent(int i, Bundle bundle) {
        this.scontext.setType(i);
        this.timestamp = System.nanoTime();
        switch (i) {
            case 1:
                SContextApproach sContextApproach = new SContextApproach();
                this.mEventContext = sContextApproach;
                sContextApproach.setValues(bundle);
                break;
            case 2:
                SContextPedometer sContextPedometer = new SContextPedometer();
                this.mEventContext = sContextPedometer;
                sContextPedometer.setValues(bundle);
                break;
            case 3:
                SContextStepCountAlert sContextStepCountAlert = new SContextStepCountAlert();
                this.mEventContext = sContextStepCountAlert;
                sContextStepCountAlert.setValues(bundle);
                break;
            case 4:
                SContextMotion sContextMotion = new SContextMotion();
                this.mEventContext = sContextMotion;
                sContextMotion.setValues(bundle);
                break;
            case 5:
                SContextMovement sContextMovement = new SContextMovement();
                this.mEventContext = sContextMovement;
                sContextMovement.setValues(bundle);
                break;
            case 6:
                SContextAutoRotation sContextAutoRotation = new SContextAutoRotation();
                this.mEventContext = sContextAutoRotation;
                sContextAutoRotation.setValues(bundle);
                break;
            case 7:
                SContextAirMotion sContextAirMotion = new SContextAirMotion();
                this.mEventContext = sContextAirMotion;
                sContextAirMotion.setValues(bundle);
                break;
            case 8:
                SContextEnvironment sContextEnvironment = new SContextEnvironment();
                this.mEventContext = sContextEnvironment;
                sContextEnvironment.setValues(bundle);
                break;
            case 9:
                SContextMovementForPositioning sContextMovementForPositioning = new SContextMovementForPositioning();
                this.mEventContext = sContextMovementForPositioning;
                sContextMovementForPositioning.setValues(bundle);
                break;
            case 10:
                SContextCurrentStatusForPositioning sContextCurrentStatusForPositioning = new SContextCurrentStatusForPositioning();
                this.mEventContext = sContextCurrentStatusForPositioning;
                sContextCurrentStatusForPositioning.setValues(bundle);
                break;
            case 11:
                SContextCallPose sContextCallPose = new SContextCallPose();
                this.mEventContext = sContextCallPose;
                sContextCallPose.setValues(bundle);
                break;
            case 12:
                SContextShakeMotion sContextShakeMotion = new SContextShakeMotion();
                this.mEventContext = sContextShakeMotion;
                sContextShakeMotion.setValues(bundle);
                break;
            case 13:
                SContextFlipCoverAction sContextFlipCoverAction = new SContextFlipCoverAction();
                this.mEventContext = sContextFlipCoverAction;
                sContextFlipCoverAction.setValues(bundle);
                break;
            case 14:
                SContextGyroTemperature sContextGyroTemperature = new SContextGyroTemperature();
                this.mEventContext = sContextGyroTemperature;
                sContextGyroTemperature.setValues(bundle);
                break;
            case 15:
                SContextPutDownMotion sContextPutDownMotion = new SContextPutDownMotion();
                this.mEventContext = sContextPutDownMotion;
                sContextPutDownMotion.setValues(bundle);
                break;
            case 16:
                SContextWakeUpVoice sContextWakeUpVoice = new SContextWakeUpVoice();
                this.mEventContext = sContextWakeUpVoice;
                sContextWakeUpVoice.setValues(bundle);
                break;
            case 17:
                SContextBounceShortMotion sContextBounceShortMotion = new SContextBounceShortMotion();
                this.mEventContext = sContextBounceShortMotion;
                sContextBounceShortMotion.setValues(bundle);
                break;
            case 18:
                SContextBounceLongMotion sContextBounceLongMotion = new SContextBounceLongMotion();
                this.mEventContext = sContextBounceLongMotion;
                sContextBounceLongMotion.setValues(bundle);
                break;
            case 19:
                SContextWristUpMotion sContextWristUpMotion = new SContextWristUpMotion();
                this.mEventContext = sContextWristUpMotion;
                sContextWristUpMotion.setValues(bundle);
                break;
            case 20:
                SContextFlatMotion sContextFlatMotion = new SContextFlatMotion();
                this.mEventContext = sContextFlatMotion;
                sContextFlatMotion.setValues(bundle);
                break;
            case 21:
                SContextMovementAlert sContextMovementAlert = new SContextMovementAlert();
                this.mEventContext = sContextMovementAlert;
                sContextMovementAlert.setValues(bundle);
                break;
            case 22:
                SContextDevicePosition sContextDevicePosition = new SContextDevicePosition();
                this.mEventContext = sContextDevicePosition;
                sContextDevicePosition.setValues(bundle);
                break;
            case 23:
                SContextTemperatureAlert sContextTemperatureAlert = new SContextTemperatureAlert();
                this.mEventContext = sContextTemperatureAlert;
                sContextTemperatureAlert.setValues(bundle);
                break;
            case 24:
                SContextActivityLocationLogging sContextActivityLocationLogging = new SContextActivityLocationLogging();
                this.mEventContext = sContextActivityLocationLogging;
                sContextActivityLocationLogging.setValues(bundle);
                break;
            case 25:
                SContextActivityTracker sContextActivityTracker = new SContextActivityTracker();
                this.mEventContext = sContextActivityTracker;
                sContextActivityTracker.setValues(bundle);
                break;
            case 26:
                SContextActivityBatch sContextActivityBatch = new SContextActivityBatch();
                this.mEventContext = sContextActivityBatch;
                sContextActivityBatch.setValues(bundle);
                break;
            case 27:
                SContextActivityNotification sContextActivityNotification = new SContextActivityNotification();
                this.mEventContext = sContextActivityNotification;
                sContextActivityNotification.setValues(bundle);
                break;
            case 28:
                SContextSpecificPoseAlert sContextSpecificPoseAlert = new SContextSpecificPoseAlert();
                this.mEventContext = sContextSpecificPoseAlert;
                sContextSpecificPoseAlert.setValues(bundle);
                break;
            case 29:
                SContextSleepMonitor sContextSleepMonitor = new SContextSleepMonitor();
                this.mEventContext = sContextSleepMonitor;
                sContextSleepMonitor.setValues(bundle);
                break;
            case 30:
                SContextActivityNotificationEx sContextActivityNotificationEx = new SContextActivityNotificationEx();
                this.mEventContext = sContextActivityNotificationEx;
                sContextActivityNotificationEx.setValues(bundle);
                break;
            case 31:
                SContextCaptureMotion sContextCaptureMotion = new SContextCaptureMotion();
                this.mEventContext = sContextCaptureMotion;
                sContextCaptureMotion.setValues(bundle);
                break;
            case 32:
                SContextCallMotion sContextCallMotion = new SContextCallMotion();
                this.mEventContext = sContextCallMotion;
                sContextCallMotion.setValues(bundle);
                break;
            case 33:
                SContextStepLevelMonitor sContextStepLevelMonitor = new SContextStepLevelMonitor();
                this.mEventContext = sContextStepLevelMonitor;
                sContextStepLevelMonitor.setValues(bundle);
                break;
            case 34:
                SContextActiveTimeMonitor sContextActiveTimeMonitor = new SContextActiveTimeMonitor();
                this.mEventContext = sContextActiveTimeMonitor;
                sContextActiveTimeMonitor.setValues(bundle);
                break;
            case 35:
                SContextInactiveTimer sContextInactiveTimer = new SContextInactiveTimer();
                this.mEventContext = sContextInactiveTimer;
                sContextInactiveTimer.setValues(bundle);
                break;
            case 36:
                SContextFlatMotionForTableMode sContextFlatMotionForTableMode = new SContextFlatMotionForTableMode();
                this.mEventContext = sContextFlatMotionForTableMode;
                sContextFlatMotionForTableMode.setValues(bundle);
                break;
            case 39:
                SContextAutoBrightness sContextAutoBrightness = new SContextAutoBrightness();
                this.mEventContext = sContextAutoBrightness;
                sContextAutoBrightness.setValues(bundle);
                break;
            case 40:
                SContextExercise sContextExercise = new SContextExercise();
                this.mEventContext = sContextExercise;
                sContextExercise.setValues(bundle);
                break;
            case 41:
                SContextAbnormalPressure sContextAbnormalPressure = new SContextAbnormalPressure();
                this.mEventContext = sContextAbnormalPressure;
                sContextAbnormalPressure.setValues(bundle);
                break;
            case 42:
                SContextPhoneStatusMonitor sContextPhoneStatusMonitor = new SContextPhoneStatusMonitor();
                this.mEventContext = sContextPhoneStatusMonitor;
                sContextPhoneStatusMonitor.setValues(bundle);
                break;
            case 43:
                SContextHallSensor sContextHallSensor = new SContextHallSensor();
                this.mEventContext = sContextHallSensor;
                sContextHallSensor.setValues(bundle);
                break;
            case 44:
                SContextEnvironmentAdaptiveDisplay sContextEnvironmentAdaptiveDisplay = new SContextEnvironmentAdaptiveDisplay();
                this.mEventContext = sContextEnvironmentAdaptiveDisplay;
                sContextEnvironmentAdaptiveDisplay.setValues(bundle);
                break;
            case 45:
                SContextDualDisplayAngle sContextDualDisplayAngle = new SContextDualDisplayAngle();
                this.mEventContext = sContextDualDisplayAngle;
                sContextDualDisplayAngle.setValues(bundle);
                break;
            case 46:
                SContextWirelessChargingDetection sContextWirelessChargingDetection = new SContextWirelessChargingDetection();
                this.mEventContext = sContextWirelessChargingDetection;
                sContextWirelessChargingDetection.setValues(bundle);
                break;
            case 47:
                SContextSLocationCore sContextSLocationCore = new SContextSLocationCore();
                this.mEventContext = sContextSLocationCore;
                sContextSLocationCore.setValues(bundle);
                break;
            case 49:
                SContextFlipMotion sContextFlipMotion = new SContextFlipMotion();
                this.mEventContext = sContextFlipMotion;
                sContextFlipMotion.setValues(bundle);
                SContextMainScreenDetection sContextMainScreenDetection = new SContextMainScreenDetection();
                this.mDuplicatedEventContext = sContextMainScreenDetection;
                sContextMainScreenDetection.setValues(bundle);
                break;
            case 50:
                SContextAnyMotionDetector sContextAnyMotionDetector = new SContextAnyMotionDetector();
                this.mEventContext = sContextAnyMotionDetector;
                sContextAnyMotionDetector.setValues(bundle);
                break;
            case 51:
                SContextDevicePhysicalContextMonitor sContextDevicePhysicalContextMonitor = new SContextDevicePhysicalContextMonitor();
                this.mEventContext = sContextDevicePhysicalContextMonitor;
                sContextDevicePhysicalContextMonitor.setValues(bundle);
                break;
            case 52:
                SContextSensorStatusCheck sContextSensorStatusCheck = new SContextSensorStatusCheck();
                this.mEventContext = sContextSensorStatusCheck;
                sContextSensorStatusCheck.setValues(bundle);
                break;
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.timestamp);
        parcel.writeParcelable(this.scontext, i);
        parcel.writeParcelable(this.mEventContext, i);
        if (this.scontext.getType() == 49) {
            parcel.writeParcelable(this.mDuplicatedEventContext, i);
        }
    }

    private void readFromParcel(Parcel parcel) {
        this.timestamp = parcel.readLong();
        this.scontext = (SContext) parcel.readParcelable(SContext.class.getClassLoader());
        this.mEventContext = (SContextEventContext) parcel.readParcelable(SContextEventContext.class.getClassLoader());
        if (this.scontext.getType() == 49) {
            this.mDuplicatedEventContext = (SContextEventContext) parcel.readParcelable(SContextEventContext.class.getClassLoader());
        }
    }
}
