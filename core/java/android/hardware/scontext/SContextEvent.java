package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextEvent implements Parcelable {
  static final Parcelable.Creator<SContextEvent> CREATOR =
      new Parcelable.Creator<
          SContextEvent>() { // from class: android.hardware.scontext.SContextEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEvent createFromParcel(Parcel in) {
          return new SContextEvent(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextEvent[] newArray(int size) {
          return new SContextEvent[size];
        }
      };
  private SContextEventContext mDuplicatedEventContext;
  private SContextEventContext mEventContext;
  public SContext scontext;
  public long timestamp;

  public SContextEvent() {
    this.scontext = new SContext();
    this.timestamp = 0L;
  }

  public SContextEvent(Parcel src) {
    readFromParcel(src);
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

  public void setSContextEvent(int event, Bundle context) {
    this.scontext.setType(event);
    this.timestamp = System.nanoTime();
    switch (event) {
      case 1:
        SContextApproach sContextApproach = new SContextApproach();
        this.mEventContext = sContextApproach;
        sContextApproach.setValues(context);
        break;
      case 2:
        SContextPedometer sContextPedometer = new SContextPedometer();
        this.mEventContext = sContextPedometer;
        sContextPedometer.setValues(context);
        break;
      case 3:
        SContextStepCountAlert sContextStepCountAlert = new SContextStepCountAlert();
        this.mEventContext = sContextStepCountAlert;
        sContextStepCountAlert.setValues(context);
        break;
      case 4:
        SContextMotion sContextMotion = new SContextMotion();
        this.mEventContext = sContextMotion;
        sContextMotion.setValues(context);
        break;
      case 5:
        SContextMovement sContextMovement = new SContextMovement();
        this.mEventContext = sContextMovement;
        sContextMovement.setValues(context);
        break;
      case 6:
        SContextAutoRotation sContextAutoRotation = new SContextAutoRotation();
        this.mEventContext = sContextAutoRotation;
        sContextAutoRotation.setValues(context);
        break;
      case 7:
        SContextAirMotion sContextAirMotion = new SContextAirMotion();
        this.mEventContext = sContextAirMotion;
        sContextAirMotion.setValues(context);
        break;
      case 8:
        SContextEnvironment sContextEnvironment = new SContextEnvironment();
        this.mEventContext = sContextEnvironment;
        sContextEnvironment.setValues(context);
        break;
      case 9:
        SContextMovementForPositioning sContextMovementForPositioning =
            new SContextMovementForPositioning();
        this.mEventContext = sContextMovementForPositioning;
        sContextMovementForPositioning.setValues(context);
        break;
      case 10:
        SContextCurrentStatusForPositioning sContextCurrentStatusForPositioning =
            new SContextCurrentStatusForPositioning();
        this.mEventContext = sContextCurrentStatusForPositioning;
        sContextCurrentStatusForPositioning.setValues(context);
        break;
      case 11:
        SContextCallPose sContextCallPose = new SContextCallPose();
        this.mEventContext = sContextCallPose;
        sContextCallPose.setValues(context);
        break;
      case 12:
        SContextShakeMotion sContextShakeMotion = new SContextShakeMotion();
        this.mEventContext = sContextShakeMotion;
        sContextShakeMotion.setValues(context);
        break;
      case 13:
        SContextFlipCoverAction sContextFlipCoverAction = new SContextFlipCoverAction();
        this.mEventContext = sContextFlipCoverAction;
        sContextFlipCoverAction.setValues(context);
        break;
      case 14:
        SContextGyroTemperature sContextGyroTemperature = new SContextGyroTemperature();
        this.mEventContext = sContextGyroTemperature;
        sContextGyroTemperature.setValues(context);
        break;
      case 15:
        SContextPutDownMotion sContextPutDownMotion = new SContextPutDownMotion();
        this.mEventContext = sContextPutDownMotion;
        sContextPutDownMotion.setValues(context);
        break;
      case 16:
        SContextWakeUpVoice sContextWakeUpVoice = new SContextWakeUpVoice();
        this.mEventContext = sContextWakeUpVoice;
        sContextWakeUpVoice.setValues(context);
        break;
      case 17:
        SContextBounceShortMotion sContextBounceShortMotion = new SContextBounceShortMotion();
        this.mEventContext = sContextBounceShortMotion;
        sContextBounceShortMotion.setValues(context);
        break;
      case 18:
        SContextBounceLongMotion sContextBounceLongMotion = new SContextBounceLongMotion();
        this.mEventContext = sContextBounceLongMotion;
        sContextBounceLongMotion.setValues(context);
        break;
      case 19:
        SContextWristUpMotion sContextWristUpMotion = new SContextWristUpMotion();
        this.mEventContext = sContextWristUpMotion;
        sContextWristUpMotion.setValues(context);
        break;
      case 20:
        SContextFlatMotion sContextFlatMotion = new SContextFlatMotion();
        this.mEventContext = sContextFlatMotion;
        sContextFlatMotion.setValues(context);
        break;
      case 21:
        SContextMovementAlert sContextMovementAlert = new SContextMovementAlert();
        this.mEventContext = sContextMovementAlert;
        sContextMovementAlert.setValues(context);
        break;
      case 22:
        SContextDevicePosition sContextDevicePosition = new SContextDevicePosition();
        this.mEventContext = sContextDevicePosition;
        sContextDevicePosition.setValues(context);
        break;
      case 23:
        SContextTemperatureAlert sContextTemperatureAlert = new SContextTemperatureAlert();
        this.mEventContext = sContextTemperatureAlert;
        sContextTemperatureAlert.setValues(context);
        break;
      case 24:
        SContextActivityLocationLogging sContextActivityLocationLogging =
            new SContextActivityLocationLogging();
        this.mEventContext = sContextActivityLocationLogging;
        sContextActivityLocationLogging.setValues(context);
        break;
      case 25:
        SContextActivityTracker sContextActivityTracker = new SContextActivityTracker();
        this.mEventContext = sContextActivityTracker;
        sContextActivityTracker.setValues(context);
        break;
      case 26:
        SContextActivityBatch sContextActivityBatch = new SContextActivityBatch();
        this.mEventContext = sContextActivityBatch;
        sContextActivityBatch.setValues(context);
        break;
      case 27:
        SContextActivityNotification sContextActivityNotification =
            new SContextActivityNotification();
        this.mEventContext = sContextActivityNotification;
        sContextActivityNotification.setValues(context);
        break;
      case 28:
        SContextSpecificPoseAlert sContextSpecificPoseAlert = new SContextSpecificPoseAlert();
        this.mEventContext = sContextSpecificPoseAlert;
        sContextSpecificPoseAlert.setValues(context);
        break;
      case 29:
        SContextSleepMonitor sContextSleepMonitor = new SContextSleepMonitor();
        this.mEventContext = sContextSleepMonitor;
        sContextSleepMonitor.setValues(context);
        break;
      case 30:
        SContextActivityNotificationEx sContextActivityNotificationEx =
            new SContextActivityNotificationEx();
        this.mEventContext = sContextActivityNotificationEx;
        sContextActivityNotificationEx.setValues(context);
        break;
      case 31:
        SContextCaptureMotion sContextCaptureMotion = new SContextCaptureMotion();
        this.mEventContext = sContextCaptureMotion;
        sContextCaptureMotion.setValues(context);
        break;
      case 32:
        SContextCallMotion sContextCallMotion = new SContextCallMotion();
        this.mEventContext = sContextCallMotion;
        sContextCallMotion.setValues(context);
        break;
      case 33:
        SContextStepLevelMonitor sContextStepLevelMonitor = new SContextStepLevelMonitor();
        this.mEventContext = sContextStepLevelMonitor;
        sContextStepLevelMonitor.setValues(context);
        break;
      case 34:
        SContextActiveTimeMonitor sContextActiveTimeMonitor = new SContextActiveTimeMonitor();
        this.mEventContext = sContextActiveTimeMonitor;
        sContextActiveTimeMonitor.setValues(context);
        break;
      case 35:
        SContextInactiveTimer sContextInactiveTimer = new SContextInactiveTimer();
        this.mEventContext = sContextInactiveTimer;
        sContextInactiveTimer.setValues(context);
        break;
      case 36:
        SContextFlatMotionForTableMode sContextFlatMotionForTableMode =
            new SContextFlatMotionForTableMode();
        this.mEventContext = sContextFlatMotionForTableMode;
        sContextFlatMotionForTableMode.setValues(context);
        break;
      case 39:
        SContextAutoBrightness sContextAutoBrightness = new SContextAutoBrightness();
        this.mEventContext = sContextAutoBrightness;
        sContextAutoBrightness.setValues(context);
        break;
      case 40:
        SContextExercise sContextExercise = new SContextExercise();
        this.mEventContext = sContextExercise;
        sContextExercise.setValues(context);
        break;
      case 41:
        SContextAbnormalPressure sContextAbnormalPressure = new SContextAbnormalPressure();
        this.mEventContext = sContextAbnormalPressure;
        sContextAbnormalPressure.setValues(context);
        break;
      case 42:
        SContextPhoneStatusMonitor sContextPhoneStatusMonitor = new SContextPhoneStatusMonitor();
        this.mEventContext = sContextPhoneStatusMonitor;
        sContextPhoneStatusMonitor.setValues(context);
        break;
      case 43:
        SContextHallSensor sContextHallSensor = new SContextHallSensor();
        this.mEventContext = sContextHallSensor;
        sContextHallSensor.setValues(context);
        break;
      case 44:
        SContextEnvironmentAdaptiveDisplay sContextEnvironmentAdaptiveDisplay =
            new SContextEnvironmentAdaptiveDisplay();
        this.mEventContext = sContextEnvironmentAdaptiveDisplay;
        sContextEnvironmentAdaptiveDisplay.setValues(context);
        break;
      case 45:
        SContextDualDisplayAngle sContextDualDisplayAngle = new SContextDualDisplayAngle();
        this.mEventContext = sContextDualDisplayAngle;
        sContextDualDisplayAngle.setValues(context);
        break;
      case 46:
        SContextWirelessChargingDetection sContextWirelessChargingDetection =
            new SContextWirelessChargingDetection();
        this.mEventContext = sContextWirelessChargingDetection;
        sContextWirelessChargingDetection.setValues(context);
        break;
      case 47:
        SContextSLocationCore sContextSLocationCore = new SContextSLocationCore();
        this.mEventContext = sContextSLocationCore;
        sContextSLocationCore.setValues(context);
        break;
      case 49:
        SContextFlipMotion sContextFlipMotion = new SContextFlipMotion();
        this.mEventContext = sContextFlipMotion;
        sContextFlipMotion.setValues(context);
        SContextMainScreenDetection sContextMainScreenDetection = new SContextMainScreenDetection();
        this.mDuplicatedEventContext = sContextMainScreenDetection;
        sContextMainScreenDetection.setValues(context);
        break;
      case 50:
        SContextAnyMotionDetector sContextAnyMotionDetector = new SContextAnyMotionDetector();
        this.mEventContext = sContextAnyMotionDetector;
        sContextAnyMotionDetector.setValues(context);
        break;
      case 51:
        SContextDevicePhysicalContextMonitor sContextDevicePhysicalContextMonitor =
            new SContextDevicePhysicalContextMonitor();
        this.mEventContext = sContextDevicePhysicalContextMonitor;
        sContextDevicePhysicalContextMonitor.setValues(context);
        break;
      case 52:
        SContextSensorStatusCheck sContextSensorStatusCheck = new SContextSensorStatusCheck();
        this.mEventContext = sContextSensorStatusCheck;
        sContextSensorStatusCheck.setValues(context);
        break;
    }
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeLong(this.timestamp);
    dest.writeParcelable(this.scontext, flags);
    dest.writeParcelable(this.mEventContext, flags);
    if (this.scontext.getType() == 49) {
      dest.writeParcelable(this.mDuplicatedEventContext, flags);
    }
  }

  private void readFromParcel(Parcel src) {
    this.timestamp = src.readLong();
    this.scontext = (SContext) src.readParcelable(SContext.class.getClassLoader());
    this.mEventContext =
        (SContextEventContext) src.readParcelable(SContextEventContext.class.getClassLoader());
    if (this.scontext.getType() == 49) {
      this.mDuplicatedEventContext =
          (SContextEventContext) src.readParcelable(SContextEventContext.class.getClassLoader());
    }
  }
}
