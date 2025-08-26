package com.samsung.android.media;

import java.lang.reflect.Array;

/* loaded from: classes6.dex */
public class SemQuramDngJavaExif {
    static final int KMAX_CFA_PATTERN = 8;
    SemQuramDngUrational mApertureValue;
    SemQuramDngUrational mApproxFocusDistance;
    String mArtist;
    String mBatteryLevelA;
    SemQuramDngUrational mBatteryLevelR;
    SemQuramDngSrational mBrightnessValue;
    byte[][] mCFAPattern;
    long mCFARepeatPatternCols;
    long mCFARepeatPatternRows;
    String mCameraSerialNumber;
    long mColorSpace;
    long mComponentsConfiguration;
    SemQuramDngUrational mCompresssedBitsPerPixel;
    long mContrast;
    String mCopyright;
    String mCopyright2;
    long mCustomRendered;
    SemQuramDngDateTimeInfo mDateTime;
    SemQuramDngDateTimeInfo mDateTimeDigitized;
    SemQuramDngDateTimeStorageInfo mDateTimeDigitizedStorageInfo;
    SemQuramDngDateTimeInfo mDateTimeOriginal;
    SemQuramDngDateTimeStorageInfo mDateTimeOriginalStorageInfo;
    SemQuramDngDateTimeStorageInfo mDateTimeStorageInfo;
    SemQuramDngUrational mDigitalZoomRatio;
    long mExifVersion;
    SemQuramDngSrational mExposureBiasValue;
    SemQuramDngUrational mExposureIndex;
    long mExposureMode;
    long mExposureProgram;
    SemQuramDngUrational mExposureTime;
    SemQuramDngUrational mFNumber;
    long mFileSource;
    String mFirmware;
    long mFlash;
    SemQuramDngUrational mFlashCompensation;
    long mFlashMask;
    long mFlashPixVersion;
    SemQuramDngUrational mFocalLength;
    long mFocalLengthIn35mmFilm;
    long mFocalPlaneResolutionUnit;
    SemQuramDngUrational mFocalPlaneXResolution;
    SemQuramDngUrational mFocalPlaneYResolution;
    SemQuramDngUrational mGPSAltitude;
    long mGPSAltitudeRef;
    String mGPSAreaInformation;
    SemQuramDngUrational mGPSDOP;
    String mGPSDateStamp;
    SemQuramDngUrational mGPSDestBearing;
    String mGPSDestBearingRef;
    SemQuramDngUrational mGPSDestDistance;
    String mGPSDestDistanceRef;
    SemQuramDngUrational[] mGPSDestLatitude;
    String mGPSDestLatitudeRef;
    SemQuramDngUrational[] mGPSDestLongitude;
    String mGPSDestLongitudeRef;
    long mGPSDifferential;
    SemQuramDngUrational mGPSHPositioningError;
    SemQuramDngUrational mGPSImgDirection;
    String mGPSImgDirectionRef;
    SemQuramDngUrational[] mGPSLatitude;
    String mGPSLatitudeRef;
    SemQuramDngUrational[] mGPSLongitude;
    String mGPSLongitudeRef;
    String mGPSMapDatum;
    String mGPSMeasureMode;
    String mGPSProcessingMethod;
    String mGPSSatellites;
    SemQuramDngUrational mGPSSpeed;
    String mGPSSpeedRef;
    String mGPSStatus;
    SemQuramDngUrational[] mGPSTimeStamp;
    SemQuramDngUrational mGPSTrack;
    String mGPSTrackRef;
    long mGPSVersionID;
    long mGainControl;
    SemQuramDngUrational mGamma;
    long mISOSpeed;
    long mISOSpeedLatitudeyyy;
    long mISOSpeedLatitudezzz;
    long[] mISOSpeedRatings;
    String mImageDescription;
    long mImageNumber;
    SemQuramDngFingerPrint mImageUniqueID;
    String mInteroperabilityIndex;
    long mInteroperabilityVersion;
    SemQuramDngSrational[] mLensDistortInfo;
    String mLensID;
    SemQuramDngUrational[] mLensInfo;
    String mLensMake;
    String mLensName;
    boolean mLensNameWasReadFromExif;
    String mLensSerialNumber;
    long mLightSource;
    String mMake;
    SemQuramDngUrational mMaxApertureValue;
    long mMeteringMode;
    String mModel;
    String mOwnerName;
    long mPixelXDimension;
    long mPixelYDimension;
    long mRecommendedExposureIndex;
    String mRelatedImageFileFormat;
    long mRelatedImageLength;
    long mRelatedImageWidth;
    long mSaturation;
    long mSceneCaptureType;
    long mSceneType;
    long mSelfTimerMode;
    long mSensingMethod;
    long mSensitivityType;
    long mSharpness;
    SemQuramDngSrational mShutterSpeedValue;
    String mSoftware;
    long mStandardOutputSensitivity;
    long[] mSubjectArea;
    long mSubjectAreaCount;
    SemQuramDngUrational mSubjectDistance;
    long mSubjectDistanceRange;
    long mTIFF_EP_StandardID;
    String mTitle;
    String mUserComment;
    long mWhiteBalance;

    public SemQuramDngJavaExif() {
        initialize();
    }

    public SemQuramDngJavaExif(SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive) {
        initialize();
        buildExif(semQuramDngJavaExifPrimitive);
    }

    public void initialize() {
        this.mDateTime = new SemQuramDngDateTimeInfo();
        this.mDateTimeStorageInfo = new SemQuramDngDateTimeStorageInfo();
        this.mDateTimeOriginal = new SemQuramDngDateTimeInfo();
        this.mDateTimeOriginalStorageInfo = new SemQuramDngDateTimeStorageInfo();
        this.mDateTimeDigitized = new SemQuramDngDateTimeInfo();
        this.mDateTimeDigitizedStorageInfo = new SemQuramDngDateTimeStorageInfo();
        this.mExposureTime = new SemQuramDngUrational();
        this.mFNumber = new SemQuramDngUrational();
        this.mShutterSpeedValue = new SemQuramDngSrational();
        this.mApertureValue = new SemQuramDngUrational();
        this.mBrightnessValue = new SemQuramDngSrational();
        this.mExposureBiasValue = new SemQuramDngSrational();
        this.mMaxApertureValue = new SemQuramDngUrational();
        this.mFocalLength = new SemQuramDngUrational();
        this.mDigitalZoomRatio = new SemQuramDngUrational();
        this.mExposureIndex = new SemQuramDngUrational();
        this.mSubjectDistance = new SemQuramDngUrational();
        this.mGamma = new SemQuramDngUrational();
        this.mBatteryLevelR = new SemQuramDngUrational();
        this.mCompresssedBitsPerPixel = new SemQuramDngUrational();
        this.mFocalPlaneXResolution = new SemQuramDngUrational();
        this.mFocalPlaneYResolution = new SemQuramDngUrational();
        this.mImageUniqueID = new SemQuramDngFingerPrint();
        this.mGPSAltitude = new SemQuramDngUrational();
        this.mGPSDOP = new SemQuramDngUrational();
        this.mGPSSpeed = new SemQuramDngUrational();
        this.mGPSTrack = new SemQuramDngUrational();
        this.mGPSImgDirection = new SemQuramDngUrational();
        this.mGPSDestBearing = new SemQuramDngUrational();
        this.mGPSDestDistance = new SemQuramDngUrational();
        this.mGPSHPositioningError = new SemQuramDngUrational();
        this.mApproxFocusDistance = new SemQuramDngUrational();
        this.mFlashCompensation = new SemQuramDngUrational();
        this.mISOSpeedRatings = new long[3];
        this.mSubjectArea = new long[4];
        this.mGPSLatitude = new SemQuramDngUrational[3];
        this.mGPSLongitude = new SemQuramDngUrational[3];
        this.mGPSTimeStamp = new SemQuramDngUrational[3];
        this.mGPSDestLatitude = new SemQuramDngUrational[3];
        this.mGPSDestLongitude = new SemQuramDngUrational[3];
        for (int i = 0; i < 3; i++) {
            this.mGPSLatitude[i] = new SemQuramDngUrational();
            this.mGPSLongitude[i] = new SemQuramDngUrational();
            this.mGPSTimeStamp[i] = new SemQuramDngUrational();
            this.mGPSDestLatitude[i] = new SemQuramDngUrational();
            this.mGPSDestLongitude[i] = new SemQuramDngUrational();
        }
        this.mLensInfo = new SemQuramDngUrational[4];
        this.mLensDistortInfo = new SemQuramDngSrational[4];
        for (int i2 = 0; i2 < 4; i2++) {
            this.mLensInfo[i2] = new SemQuramDngUrational();
            this.mLensDistortInfo[i2] = new SemQuramDngSrational();
        }
        this.mCFAPattern = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, 8, 8);
        this.mImageUniqueID = new SemQuramDngFingerPrint();
    }

    void convertString2Rational(String str, SemQuramDngUrational semQuramDngUrational) {
        String[] strArrSplit = str.split("/");
        if (strArrSplit.length >= 1 && !strArrSplit[0].equals("")) {
            semQuramDngUrational.n = Long.valueOf(strArrSplit[0]).longValue();
        } else {
            semQuramDngUrational.n = 0L;
        }
        if (strArrSplit.length >= 2 && !strArrSplit[1].equals("")) {
            semQuramDngUrational.d = Long.valueOf(strArrSplit[1]).longValue();
        } else {
            semQuramDngUrational.d = 0L;
        }
    }

    void convertString2Rational(String str, SemQuramDngSrational semQuramDngSrational) {
        String[] strArrSplit = str.split("/");
        if (strArrSplit.length >= 1 && !strArrSplit[0].equals("")) {
            semQuramDngSrational.n = Long.valueOf(strArrSplit[0]).longValue();
        } else {
            semQuramDngSrational.n = 0L;
        }
        if (strArrSplit.length >= 2 && !strArrSplit[1].equals("")) {
            semQuramDngSrational.d = Long.valueOf(strArrSplit[1]).longValue();
        } else {
            semQuramDngSrational.d = 0L;
        }
    }

    public void buildExif(SemQuramDngJavaExifPrimitive semQuramDngJavaExifPrimitive) {
        if (semQuramDngJavaExifPrimitive.mImageDescription != null) {
            this.mImageDescription = semQuramDngJavaExifPrimitive.mImageDescription;
        }
        if (semQuramDngJavaExifPrimitive.mMake != null) {
            this.mMake = semQuramDngJavaExifPrimitive.mMake;
        }
        if (semQuramDngJavaExifPrimitive.mModel != null) {
            this.mModel = semQuramDngJavaExifPrimitive.mModel;
        }
        if (semQuramDngJavaExifPrimitive.mSoftware != null) {
            this.mSoftware = semQuramDngJavaExifPrimitive.mSoftware;
        }
        if (semQuramDngJavaExifPrimitive.mArtist != null) {
            this.mArtist = semQuramDngJavaExifPrimitive.mArtist;
        }
        if (semQuramDngJavaExifPrimitive.mCopyright != null) {
            this.mCopyright = semQuramDngJavaExifPrimitive.mCopyright;
        }
        if (semQuramDngJavaExifPrimitive.mUserComment != null) {
            this.mUserComment = semQuramDngJavaExifPrimitive.mUserComment;
        }
        if (semQuramDngJavaExifPrimitive.mGPSLatitudeRef != null) {
            this.mGPSLatitudeRef = semQuramDngJavaExifPrimitive.mGPSLatitudeRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSLongitudeRef != null) {
            this.mGPSLongitudeRef = semQuramDngJavaExifPrimitive.mGPSLongitudeRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSSatellites != null) {
            this.mGPSSatellites = semQuramDngJavaExifPrimitive.mGPSSatellites;
        }
        if (semQuramDngJavaExifPrimitive.mGPSStatus != null) {
            this.mGPSStatus = semQuramDngJavaExifPrimitive.mGPSStatus;
        }
        if (semQuramDngJavaExifPrimitive.mGPSMeasureMode != null) {
            this.mGPSMeasureMode = semQuramDngJavaExifPrimitive.mGPSMeasureMode;
        }
        if (semQuramDngJavaExifPrimitive.mGPSSpeedRef != null) {
            this.mGPSSpeedRef = semQuramDngJavaExifPrimitive.mGPSSpeedRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSTrackRef != null) {
            this.mGPSTrackRef = semQuramDngJavaExifPrimitive.mGPSTrackRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSImgDirectionRef != null) {
            this.mGPSImgDirectionRef = semQuramDngJavaExifPrimitive.mGPSImgDirectionRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSMapDatum != null) {
            this.mGPSMapDatum = semQuramDngJavaExifPrimitive.mGPSMapDatum;
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLatitudeRef != null) {
            this.mGPSDestLatitudeRef = semQuramDngJavaExifPrimitive.mGPSDestLatitudeRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLongitudeRef != null) {
            this.mGPSDestLongitudeRef = semQuramDngJavaExifPrimitive.mGPSDestLongitudeRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestBearingRef != null) {
            this.mGPSDestBearingRef = semQuramDngJavaExifPrimitive.mGPSDestBearingRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestDistanceRef != null) {
            this.mGPSDestDistanceRef = semQuramDngJavaExifPrimitive.mGPSDestDistanceRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSProcessingMethod != null) {
            this.mGPSProcessingMethod = semQuramDngJavaExifPrimitive.mGPSProcessingMethod;
        }
        if (semQuramDngJavaExifPrimitive.mGPSAreaInformation != null) {
            this.mGPSAreaInformation = semQuramDngJavaExifPrimitive.mGPSAreaInformation;
        }
        if (semQuramDngJavaExifPrimitive.mGPSDateStamp != null) {
            this.mGPSDateStamp = semQuramDngJavaExifPrimitive.mGPSDateStamp;
        }
        if (semQuramDngJavaExifPrimitive.mInteroperabilityIndex != null) {
            this.mInteroperabilityIndex = semQuramDngJavaExifPrimitive.mInteroperabilityIndex;
        }
        if (semQuramDngJavaExifPrimitive.mCameraSerialNumber != null) {
            this.mCameraSerialNumber = semQuramDngJavaExifPrimitive.mCameraSerialNumber;
        }
        if (semQuramDngJavaExifPrimitive.mLensMake != null) {
            this.mLensMake = semQuramDngJavaExifPrimitive.mLensMake;
        }
        if (semQuramDngJavaExifPrimitive.mLensName != null) {
            this.mLensName = semQuramDngJavaExifPrimitive.mLensName;
        }
        if (semQuramDngJavaExifPrimitive.mLensSerialNumber != null) {
            this.mLensSerialNumber = semQuramDngJavaExifPrimitive.mLensSerialNumber;
        }
        if (semQuramDngJavaExifPrimitive.mOwnerName != null) {
            this.mOwnerName = semQuramDngJavaExifPrimitive.mOwnerName;
        }
        if (semQuramDngJavaExifPrimitive.mExifVersion != 0) {
            this.mExifVersion = semQuramDngJavaExifPrimitive.mExifVersion;
        }
        if (semQuramDngJavaExifPrimitive.mFlashPixVersion != 0) {
            this.mExposureProgram = semQuramDngJavaExifPrimitive.mFlashPixVersion;
        }
        if (semQuramDngJavaExifPrimitive.mExposureProgram != 0) {
            this.mExposureProgram = semQuramDngJavaExifPrimitive.mExposureProgram;
        }
        if (semQuramDngJavaExifPrimitive.mMeteringMode != 0) {
            this.mMeteringMode = semQuramDngJavaExifPrimitive.mMeteringMode;
        }
        if (semQuramDngJavaExifPrimitive.mLightSource != 0) {
            this.mLightSource = semQuramDngJavaExifPrimitive.mLightSource;
        }
        if (semQuramDngJavaExifPrimitive.mFlash != 0) {
            this.mFlash = semQuramDngJavaExifPrimitive.mFlash;
        }
        if (semQuramDngJavaExifPrimitive.mSensingMethod != 0) {
            this.mSensingMethod = semQuramDngJavaExifPrimitive.mSensingMethod;
        }
        if (semQuramDngJavaExifPrimitive.mColorSpace != 0) {
            this.mColorSpace = semQuramDngJavaExifPrimitive.mColorSpace;
        }
        if (semQuramDngJavaExifPrimitive.mFileSource != 0) {
            this.mFileSource = semQuramDngJavaExifPrimitive.mFileSource;
        }
        if (semQuramDngJavaExifPrimitive.mSceneType != 0) {
            this.mSceneType = semQuramDngJavaExifPrimitive.mSceneType;
        }
        if (semQuramDngJavaExifPrimitive.mCustomRendered != 0) {
            this.mCustomRendered = semQuramDngJavaExifPrimitive.mCustomRendered;
        }
        if (semQuramDngJavaExifPrimitive.mExposureMode != 0) {
            this.mExposureMode = semQuramDngJavaExifPrimitive.mExposureMode;
        }
        if (semQuramDngJavaExifPrimitive.mWhiteBalance != 0) {
            this.mWhiteBalance = semQuramDngJavaExifPrimitive.mWhiteBalance;
        }
        if (semQuramDngJavaExifPrimitive.mSceneCaptureType != 0) {
            this.mSceneCaptureType = semQuramDngJavaExifPrimitive.mSceneCaptureType;
        }
        if (semQuramDngJavaExifPrimitive.mGainControl != 0) {
            this.mGainControl = semQuramDngJavaExifPrimitive.mGainControl;
        }
        if (semQuramDngJavaExifPrimitive.mContrast != 0) {
            this.mContrast = semQuramDngJavaExifPrimitive.mContrast;
        }
        if (semQuramDngJavaExifPrimitive.mSaturation != 0) {
            this.mSaturation = semQuramDngJavaExifPrimitive.mSaturation;
        }
        if (semQuramDngJavaExifPrimitive.mSharpness != 0) {
            this.mSharpness = semQuramDngJavaExifPrimitive.mSharpness;
        }
        if (semQuramDngJavaExifPrimitive.mSubjectDistanceRange != 0) {
            this.mSubjectDistanceRange = semQuramDngJavaExifPrimitive.mSubjectDistanceRange;
        }
        if (semQuramDngJavaExifPrimitive.mFocalLengthIn35mmFilm != 0) {
            this.mFocalLengthIn35mmFilm = semQuramDngJavaExifPrimitive.mFocalLengthIn35mmFilm;
        }
        if (semQuramDngJavaExifPrimitive.mSensitivityType != 0) {
            this.mSensitivityType = semQuramDngJavaExifPrimitive.mSensitivityType;
        }
        if (semQuramDngJavaExifPrimitive.mStandardOutputSensitivity != 0) {
            this.mStandardOutputSensitivity = semQuramDngJavaExifPrimitive.mStandardOutputSensitivity;
        }
        if (semQuramDngJavaExifPrimitive.mRecommendedExposureIndex != 0) {
            this.mRecommendedExposureIndex = semQuramDngJavaExifPrimitive.mRecommendedExposureIndex;
        }
        if (semQuramDngJavaExifPrimitive.mISOSpeed != 0) {
            this.mISOSpeed = semQuramDngJavaExifPrimitive.mISOSpeed;
        }
        if (semQuramDngJavaExifPrimitive.mISOSpeedLatitudeyyy != 0) {
            this.mISOSpeedLatitudeyyy = semQuramDngJavaExifPrimitive.mISOSpeedLatitudeyyy;
        }
        if (semQuramDngJavaExifPrimitive.mISOSpeedLatitudezzz != 0) {
            this.mISOSpeedLatitudezzz = semQuramDngJavaExifPrimitive.mISOSpeedLatitudezzz;
        }
        if (semQuramDngJavaExifPrimitive.mComponentsConfiguration != 0) {
            this.mComponentsConfiguration = semQuramDngJavaExifPrimitive.mComponentsConfiguration;
        }
        if (semQuramDngJavaExifPrimitive.mPixelXDimension != 0) {
            this.mPixelXDimension = semQuramDngJavaExifPrimitive.mPixelXDimension;
        }
        if (semQuramDngJavaExifPrimitive.mPixelYDimension != 0) {
            this.mPixelYDimension = semQuramDngJavaExifPrimitive.mPixelYDimension;
        }
        if (semQuramDngJavaExifPrimitive.mFocalPlaneResolutionUnit != 0) {
            this.mFocalPlaneResolutionUnit = semQuramDngJavaExifPrimitive.mFocalPlaneResolutionUnit;
        }
        if (semQuramDngJavaExifPrimitive.mGPSVersionID != 0) {
            this.mGPSVersionID = semQuramDngJavaExifPrimitive.mGPSVersionID;
        }
        if (semQuramDngJavaExifPrimitive.mGPSAltitudeRef != 0) {
            this.mGPSAltitudeRef = semQuramDngJavaExifPrimitive.mGPSAltitudeRef;
        }
        if (semQuramDngJavaExifPrimitive.mGPSDifferential != 0) {
            this.mGPSDifferential = semQuramDngJavaExifPrimitive.mGPSDifferential;
        }
        int i = 0;
        if (semQuramDngJavaExifPrimitive.mISOSpeedRatings0 != 0) {
            this.mISOSpeedRatings[0] = semQuramDngJavaExifPrimitive.mISOSpeedRatings0;
        }
        if (semQuramDngJavaExifPrimitive.mISOSpeedRatings1 != 0) {
            this.mISOSpeedRatings[1] = semQuramDngJavaExifPrimitive.mISOSpeedRatings1;
        }
        if (semQuramDngJavaExifPrimitive.mISOSpeedRatings2 != 0) {
            this.mISOSpeedRatings[2] = semQuramDngJavaExifPrimitive.mISOSpeedRatings2;
        }
        if (semQuramDngJavaExifPrimitive.mSubjectArea0 != 0) {
            this.mSubjectArea[0] = semQuramDngJavaExifPrimitive.mSubjectArea0;
        }
        if (semQuramDngJavaExifPrimitive.mSubjectArea1 != 0) {
            this.mSubjectArea[1] = semQuramDngJavaExifPrimitive.mSubjectArea1;
        }
        if (semQuramDngJavaExifPrimitive.mSubjectArea2 != 0) {
            this.mSubjectArea[2] = semQuramDngJavaExifPrimitive.mSubjectArea2;
        }
        if (semQuramDngJavaExifPrimitive.mCFARepeatPatternCols != 0) {
            this.mCFARepeatPatternCols = semQuramDngJavaExifPrimitive.mCFARepeatPatternCols;
        }
        if (semQuramDngJavaExifPrimitive.mCFARepeatPatternRows != 0) {
            this.mCFARepeatPatternRows = semQuramDngJavaExifPrimitive.mCFARepeatPatternRows;
        }
        if (semQuramDngJavaExifPrimitive.mExposureTime != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mExposureTime, this.mExposureTime);
        }
        if (semQuramDngJavaExifPrimitive.mFNumber != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mFNumber, this.mFNumber);
        }
        if (semQuramDngJavaExifPrimitive.mApertureValue != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mApertureValue, this.mApertureValue);
        }
        if (semQuramDngJavaExifPrimitive.mMaxApertureValue != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mMaxApertureValue, this.mMaxApertureValue);
        }
        if (semQuramDngJavaExifPrimitive.mFocalLength != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mFocalLength, this.mFocalLength);
        }
        if (semQuramDngJavaExifPrimitive.mDigitalZoomRatio != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mDigitalZoomRatio, this.mDigitalZoomRatio);
        }
        if (semQuramDngJavaExifPrimitive.mExposureIndex != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mExposureIndex, this.mExposureIndex);
        }
        if (semQuramDngJavaExifPrimitive.mSubjectDistance != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mSubjectDistance, this.mSubjectDistance);
        }
        if (semQuramDngJavaExifPrimitive.mGamma != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGamma, this.mGamma);
        }
        if (semQuramDngJavaExifPrimitive.mFocalPlaneXResolution != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mFocalPlaneXResolution, this.mFocalPlaneXResolution);
        }
        if (semQuramDngJavaExifPrimitive.mFocalPlaneYResolution != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mFocalPlaneYResolution, this.mFocalPlaneYResolution);
        }
        if (semQuramDngJavaExifPrimitive.mGPSAltitude != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSAltitude, this.mGPSAltitude);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDOP != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDOP, this.mGPSDOP);
        }
        if (semQuramDngJavaExifPrimitive.mGPSSpeed != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSSpeed, this.mGPSSpeed);
        }
        if (semQuramDngJavaExifPrimitive.mGPSTrack != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSTrack, this.mGPSTrack);
        }
        if (semQuramDngJavaExifPrimitive.mGPSImgDirection != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSImgDirection, this.mGPSImgDirection);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestBearing != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestBearing, this.mGPSDestBearing);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestDistance != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestDistance, this.mGPSDestDistance);
        }
        if (semQuramDngJavaExifPrimitive.mGPSHPositioningError != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSHPositioningError, this.mGPSHPositioningError);
        }
        if (semQuramDngJavaExifPrimitive.mGPSLatitude0 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSLatitude0, this.mGPSLatitude[0]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSLatitude1 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSLatitude1, this.mGPSLatitude[1]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSLatitude2 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSLatitude2, this.mGPSLatitude[2]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSLongitude0 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSLongitude0, this.mGPSLongitude[0]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSLongitude1 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSLongitude1, this.mGPSLongitude[1]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSLongitude2 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSLongitude2, this.mGPSLongitude[2]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSTimeStamp0 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSTimeStamp0, this.mGPSTimeStamp[0]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSTimeStamp1 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSTimeStamp1, this.mGPSTimeStamp[1]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSTimeStamp2 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSTimeStamp2, this.mGPSTimeStamp[2]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLatitude0 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestLatitude0, this.mGPSDestLatitude[0]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLatitude1 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestLatitude1, this.mGPSDestLatitude[1]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLatitude2 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestLatitude2, this.mGPSDestLatitude[2]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLongitude0 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestLongitude0, this.mGPSDestLongitude[0]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLongitude1 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestLongitude1, this.mGPSDestLongitude[1]);
        }
        if (semQuramDngJavaExifPrimitive.mGPSDestLongitude2 != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mGPSDestLongitude2, this.mGPSDestLongitude[2]);
        }
        if (semQuramDngJavaExifPrimitive.mShutterSpeedValue != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mShutterSpeedValue, this.mShutterSpeedValue);
        }
        if (semQuramDngJavaExifPrimitive.mBrightnessValue != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mBrightnessValue, this.mBrightnessValue);
        }
        if (semQuramDngJavaExifPrimitive.mExposureBiasValue != null) {
            convertString2Rational(semQuramDngJavaExifPrimitive.mExposureBiasValue, this.mExposureBiasValue);
        }
        if (semQuramDngJavaExifPrimitive.mCFARepeatPatternCols != 0 && semQuramDngJavaExifPrimitive.mCFARepeatPatternRows != 0) {
            while (true) {
                long j = i;
                if (j >= semQuramDngJavaExifPrimitive.mCFARepeatPatternRows * semQuramDngJavaExifPrimitive.mCFARepeatPatternCols) {
                    break;
                }
                this.mCFAPattern[(int) (j / semQuramDngJavaExifPrimitive.mCFARepeatPatternRows)][(int) (j % semQuramDngJavaExifPrimitive.mCFARepeatPatternCols)] = semQuramDngJavaExifPrimitive.mCFAPattern[i];
                i++;
            }
        }
        if (semQuramDngJavaExifPrimitive.mImageUniqueIDExist) {
            this.mImageUniqueID.data = semQuramDngJavaExifPrimitive.mImageUniqueID;
        }
        if (semQuramDngJavaExifPrimitive.mDateTime != null) {
            this.mDateTime.setTimeInfo(semQuramDngJavaExifPrimitive.mDateTime);
        }
        if (semQuramDngJavaExifPrimitive.mDateTimeOriginal != null) {
            this.mDateTimeOriginal.setTimeInfo(semQuramDngJavaExifPrimitive.mDateTimeOriginal);
        }
        if (semQuramDngJavaExifPrimitive.mDateTimeDigitized != null) {
            this.mDateTimeDigitized.setTimeInfo(semQuramDngJavaExifPrimitive.mDateTimeDigitized);
        }
    }

    public void updateExif(SemQuramDngJavaExif semQuramDngJavaExif) {
        String str = semQuramDngJavaExif.mImageDescription;
        if (str != null) {
            this.mImageDescription = str;
        }
        String str2 = semQuramDngJavaExif.mMake;
        if (str2 != null) {
            this.mMake = str2;
        }
        String str3 = semQuramDngJavaExif.mModel;
        if (str3 != null) {
            this.mModel = str3;
        }
        String str4 = semQuramDngJavaExif.mSoftware;
        if (str4 != null) {
            this.mSoftware = str4;
        }
        String str5 = semQuramDngJavaExif.mArtist;
        if (str5 != null) {
            this.mArtist = str5;
        }
        String str6 = semQuramDngJavaExif.mCopyright;
        if (str6 != null) {
            this.mCopyright = str6;
        }
        String str7 = semQuramDngJavaExif.mUserComment;
        if (str7 != null) {
            this.mUserComment = str7;
        }
        String str8 = semQuramDngJavaExif.mGPSLatitudeRef;
        if (str8 != null) {
            this.mGPSLatitudeRef = str8;
        }
        String str9 = semQuramDngJavaExif.mGPSLongitudeRef;
        if (str9 != null) {
            this.mGPSLongitudeRef = str9;
        }
        String str10 = semQuramDngJavaExif.mGPSSatellites;
        if (str10 != null) {
            this.mGPSSatellites = str10;
        }
        String str11 = semQuramDngJavaExif.mGPSStatus;
        if (str11 != null) {
            this.mGPSStatus = str11;
        }
        String str12 = semQuramDngJavaExif.mGPSMeasureMode;
        if (str12 != null) {
            this.mGPSMeasureMode = str12;
        }
        String str13 = semQuramDngJavaExif.mGPSSpeedRef;
        if (str13 != null) {
            this.mGPSSpeedRef = str13;
        }
        String str14 = semQuramDngJavaExif.mGPSTrackRef;
        if (str14 != null) {
            this.mGPSTrackRef = str14;
        }
        String str15 = semQuramDngJavaExif.mGPSImgDirectionRef;
        if (str15 != null) {
            this.mGPSImgDirectionRef = str15;
        }
        String str16 = semQuramDngJavaExif.mGPSMapDatum;
        if (str16 != null) {
            this.mGPSMapDatum = str16;
        }
        String str17 = semQuramDngJavaExif.mGPSDestLatitudeRef;
        if (str17 != null) {
            this.mGPSDestLatitudeRef = str17;
        }
        String str18 = semQuramDngJavaExif.mGPSDestLongitudeRef;
        if (str18 != null) {
            this.mGPSDestLongitudeRef = str18;
        }
        String str19 = semQuramDngJavaExif.mGPSDestBearingRef;
        if (str19 != null) {
            this.mGPSDestBearingRef = str19;
        }
        String str20 = semQuramDngJavaExif.mGPSDestDistanceRef;
        if (str20 != null) {
            this.mGPSDestDistanceRef = str20;
        }
        String str21 = semQuramDngJavaExif.mGPSProcessingMethod;
        if (str21 != null) {
            this.mGPSProcessingMethod = str21;
        }
        String str22 = semQuramDngJavaExif.mGPSAreaInformation;
        if (str22 != null) {
            this.mGPSAreaInformation = str22;
        }
        String str23 = semQuramDngJavaExif.mGPSDateStamp;
        if (str23 != null) {
            this.mGPSDateStamp = str23;
        }
        String str24 = semQuramDngJavaExif.mInteroperabilityIndex;
        if (str24 != null) {
            this.mInteroperabilityIndex = str24;
        }
        String str25 = semQuramDngJavaExif.mCameraSerialNumber;
        if (str25 != null) {
            this.mCameraSerialNumber = str25;
        }
        String str26 = semQuramDngJavaExif.mLensMake;
        if (str26 != null) {
            this.mLensMake = str26;
        }
        String str27 = semQuramDngJavaExif.mLensName;
        if (str27 != null) {
            this.mLensName = str27;
        }
        String str28 = semQuramDngJavaExif.mLensSerialNumber;
        if (str28 != null) {
            this.mLensSerialNumber = str28;
        }
        String str29 = semQuramDngJavaExif.mOwnerName;
        if (str29 != null) {
            this.mOwnerName = str29;
        }
        long j = semQuramDngJavaExif.mExifVersion;
        if (j != 0) {
            this.mExifVersion = j;
        }
        long j2 = semQuramDngJavaExif.mFlashPixVersion;
        if (j2 != 0) {
            this.mExposureProgram = j2;
        }
        long j3 = semQuramDngJavaExif.mExposureProgram;
        if (j3 != 0) {
            this.mExposureProgram = j3;
        }
        long j4 = semQuramDngJavaExif.mMeteringMode;
        if (j4 != 0) {
            this.mMeteringMode = j4;
        }
        long j5 = semQuramDngJavaExif.mLightSource;
        if (j5 != 0) {
            this.mLightSource = j5;
        }
        long j6 = semQuramDngJavaExif.mFlash;
        if (j6 != 0) {
            this.mFlash = j6;
        }
        long j7 = semQuramDngJavaExif.mSensingMethod;
        if (j7 != 0) {
            this.mSensingMethod = j7;
        }
        long j8 = semQuramDngJavaExif.mColorSpace;
        if (j8 != 0) {
            this.mColorSpace = j8;
        }
        long j9 = semQuramDngJavaExif.mFileSource;
        if (j9 != 0) {
            this.mFileSource = j9;
        }
        long j10 = semQuramDngJavaExif.mSceneType;
        if (j10 != 0) {
            this.mSceneType = j10;
        }
        long j11 = semQuramDngJavaExif.mCustomRendered;
        if (j11 != 0) {
            this.mCustomRendered = j11;
        }
        long j12 = semQuramDngJavaExif.mExposureMode;
        if (j12 != 0) {
            this.mExposureMode = j12;
        }
        long j13 = semQuramDngJavaExif.mWhiteBalance;
        if (j13 != 0) {
            this.mWhiteBalance = j13;
        }
        long j14 = semQuramDngJavaExif.mSceneCaptureType;
        if (j14 != 0) {
            this.mSceneCaptureType = j14;
        }
        long j15 = semQuramDngJavaExif.mGainControl;
        if (j15 != 0) {
            this.mGainControl = j15;
        }
        long j16 = semQuramDngJavaExif.mContrast;
        if (j16 != 0) {
            this.mContrast = j16;
        }
        long j17 = semQuramDngJavaExif.mSaturation;
        if (j17 != 0) {
            this.mSaturation = j17;
        }
        long j18 = semQuramDngJavaExif.mSharpness;
        if (j18 != 0) {
            this.mSharpness = j18;
        }
        long j19 = semQuramDngJavaExif.mSubjectDistanceRange;
        if (j19 != 0) {
            this.mSubjectDistanceRange = j19;
        }
        long j20 = semQuramDngJavaExif.mFocalLengthIn35mmFilm;
        if (j20 != 0) {
            this.mFocalLengthIn35mmFilm = j20;
        }
        long j21 = semQuramDngJavaExif.mSensitivityType;
        if (j21 != 0) {
            this.mSensitivityType = j21;
        }
        long j22 = semQuramDngJavaExif.mStandardOutputSensitivity;
        if (j22 != 0) {
            this.mStandardOutputSensitivity = j22;
        }
        long j23 = semQuramDngJavaExif.mRecommendedExposureIndex;
        if (j23 != 0) {
            this.mRecommendedExposureIndex = j23;
        }
        long j24 = semQuramDngJavaExif.mISOSpeed;
        if (j24 != 0) {
            this.mISOSpeed = j24;
        }
        long j25 = semQuramDngJavaExif.mISOSpeedLatitudeyyy;
        if (j25 != 0) {
            this.mISOSpeedLatitudeyyy = j25;
        }
        long j26 = semQuramDngJavaExif.mISOSpeedLatitudezzz;
        if (j26 != 0) {
            this.mISOSpeedLatitudezzz = j26;
        }
        long j27 = semQuramDngJavaExif.mComponentsConfiguration;
        if (j27 != 0) {
            this.mComponentsConfiguration = j27;
        }
        long j28 = semQuramDngJavaExif.mPixelXDimension;
        if (j28 != 0) {
            this.mPixelXDimension = j28;
        }
        long j29 = semQuramDngJavaExif.mPixelYDimension;
        if (j29 != 0) {
            this.mPixelYDimension = j29;
        }
        long j30 = semQuramDngJavaExif.mFocalPlaneResolutionUnit;
        if (j30 != 0) {
            this.mFocalPlaneResolutionUnit = j30;
        }
        long j31 = semQuramDngJavaExif.mGPSVersionID;
        if (j31 != 0) {
            this.mGPSVersionID = j31;
        }
        long j32 = semQuramDngJavaExif.mGPSAltitudeRef;
        if (j32 != 0) {
            this.mGPSAltitudeRef = j32;
        }
        long j33 = semQuramDngJavaExif.mGPSDifferential;
        if (j33 != 0) {
            this.mGPSDifferential = j33;
        }
        long j34 = semQuramDngJavaExif.mCFARepeatPatternCols;
        if (j34 != 0) {
            this.mCFARepeatPatternCols = j34;
        }
        long j35 = semQuramDngJavaExif.mCFARepeatPatternRows;
        if (j35 != 0) {
            this.mCFARepeatPatternRows = j35;
        }
        long[] jArr = semQuramDngJavaExif.mISOSpeedRatings;
        if (jArr != null) {
            this.mISOSpeedRatings = jArr;
        }
        long[] jArr2 = semQuramDngJavaExif.mSubjectArea;
        if (jArr2 != null) {
            this.mSubjectArea = jArr2;
        }
        SemQuramDngUrational semQuramDngUrational = semQuramDngJavaExif.mExposureTime;
        if (semQuramDngUrational != null) {
            this.mExposureTime = semQuramDngUrational;
        }
        SemQuramDngUrational semQuramDngUrational2 = semQuramDngJavaExif.mFNumber;
        if (semQuramDngUrational2 != null) {
            this.mFNumber = semQuramDngUrational2;
        }
        SemQuramDngUrational semQuramDngUrational3 = semQuramDngJavaExif.mApertureValue;
        if (semQuramDngUrational3 != null) {
            this.mApertureValue = semQuramDngUrational3;
        }
        SemQuramDngUrational semQuramDngUrational4 = semQuramDngJavaExif.mMaxApertureValue;
        if (semQuramDngUrational4 != null) {
            this.mMaxApertureValue = semQuramDngUrational4;
        }
        SemQuramDngUrational semQuramDngUrational5 = semQuramDngJavaExif.mFocalLength;
        if (semQuramDngUrational5 != null) {
            this.mFocalLength = semQuramDngUrational5;
        }
        SemQuramDngUrational semQuramDngUrational6 = semQuramDngJavaExif.mDigitalZoomRatio;
        if (semQuramDngUrational6 != null) {
            this.mDigitalZoomRatio = semQuramDngUrational6;
        }
        SemQuramDngUrational semQuramDngUrational7 = semQuramDngJavaExif.mExposureIndex;
        if (semQuramDngUrational7 != null) {
            this.mExposureIndex = semQuramDngUrational7;
        }
        SemQuramDngUrational semQuramDngUrational8 = semQuramDngJavaExif.mSubjectDistance;
        if (semQuramDngUrational8 != null) {
            this.mSubjectDistance = semQuramDngUrational8;
        }
        SemQuramDngUrational semQuramDngUrational9 = semQuramDngJavaExif.mGamma;
        if (semQuramDngUrational9 != null) {
            this.mGamma = semQuramDngUrational9;
        }
        SemQuramDngUrational semQuramDngUrational10 = semQuramDngJavaExif.mFocalPlaneXResolution;
        if (semQuramDngUrational10 != null) {
            this.mFocalPlaneXResolution = semQuramDngUrational10;
        }
        SemQuramDngUrational semQuramDngUrational11 = semQuramDngJavaExif.mFocalPlaneYResolution;
        if (semQuramDngUrational11 != null) {
            this.mFocalPlaneYResolution = semQuramDngUrational11;
        }
        SemQuramDngUrational semQuramDngUrational12 = semQuramDngJavaExif.mGPSDOP;
        if (semQuramDngUrational12 != null) {
            this.mGPSDOP = semQuramDngUrational12;
        }
        SemQuramDngUrational semQuramDngUrational13 = semQuramDngJavaExif.mGPSSpeed;
        if (semQuramDngUrational13 != null) {
            this.mGPSSpeed = semQuramDngUrational13;
        }
        SemQuramDngUrational semQuramDngUrational14 = semQuramDngJavaExif.mGPSTrack;
        if (semQuramDngUrational14 != null) {
            this.mGPSTrack = semQuramDngUrational14;
        }
        SemQuramDngUrational semQuramDngUrational15 = semQuramDngJavaExif.mGPSImgDirection;
        if (semQuramDngUrational15 != null) {
            this.mGPSImgDirection = semQuramDngUrational15;
        }
        SemQuramDngUrational semQuramDngUrational16 = semQuramDngJavaExif.mGPSDestBearing;
        if (semQuramDngUrational16 != null) {
            this.mGPSDestBearing = semQuramDngUrational16;
        }
        SemQuramDngUrational semQuramDngUrational17 = semQuramDngJavaExif.mGPSDestDistance;
        if (semQuramDngUrational17 != null) {
            this.mGPSDestDistance = semQuramDngUrational17;
        }
        SemQuramDngUrational semQuramDngUrational18 = semQuramDngJavaExif.mGPSHPositioningError;
        if (semQuramDngUrational18 != null) {
            this.mGPSHPositioningError = semQuramDngUrational18;
        }
        SemQuramDngUrational[] semQuramDngUrationalArr = semQuramDngJavaExif.mGPSLatitude;
        if (semQuramDngUrationalArr != null) {
            this.mGPSLatitude = semQuramDngUrationalArr;
        }
        SemQuramDngUrational[] semQuramDngUrationalArr2 = semQuramDngJavaExif.mGPSLongitude;
        if (semQuramDngUrationalArr2 != null) {
            this.mGPSLongitude = semQuramDngUrationalArr2;
        }
        SemQuramDngUrational[] semQuramDngUrationalArr3 = semQuramDngJavaExif.mGPSTimeStamp;
        if (semQuramDngUrationalArr3 != null) {
            this.mGPSTimeStamp = semQuramDngUrationalArr3;
        }
        SemQuramDngUrational[] semQuramDngUrationalArr4 = semQuramDngJavaExif.mGPSDestLatitude;
        if (semQuramDngUrationalArr4 != null) {
            this.mGPSDestLatitude = semQuramDngUrationalArr4;
        }
        SemQuramDngUrational[] semQuramDngUrationalArr5 = semQuramDngJavaExif.mGPSDestLongitude;
        if (semQuramDngUrationalArr5 != null) {
            this.mGPSDestLongitude = semQuramDngUrationalArr5;
        }
        SemQuramDngSrational semQuramDngSrational = semQuramDngJavaExif.mShutterSpeedValue;
        if (semQuramDngSrational != null) {
            this.mShutterSpeedValue = semQuramDngSrational;
        }
        SemQuramDngSrational semQuramDngSrational2 = semQuramDngJavaExif.mBrightnessValue;
        if (semQuramDngSrational2 != null) {
            this.mBrightnessValue = semQuramDngSrational2;
        }
        SemQuramDngSrational semQuramDngSrational3 = semQuramDngJavaExif.mBrightnessValue;
        if (semQuramDngSrational3 != null) {
            this.mBrightnessValue = semQuramDngSrational3;
        }
        SemQuramDngSrational semQuramDngSrational4 = semQuramDngJavaExif.mExposureBiasValue;
        if (semQuramDngSrational4 != null) {
            this.mExposureBiasValue = semQuramDngSrational4;
        }
        byte[][] bArr = semQuramDngJavaExif.mCFAPattern;
        if (bArr != null) {
            this.mCFAPattern = bArr;
        }
        SemQuramDngFingerPrint semQuramDngFingerPrint = semQuramDngJavaExif.mImageUniqueID;
        if (semQuramDngFingerPrint != null) {
            this.mImageUniqueID = semQuramDngFingerPrint;
        }
        SemQuramDngDateTimeInfo semQuramDngDateTimeInfo = semQuramDngJavaExif.mDateTime;
        if (semQuramDngDateTimeInfo != null) {
            this.mDateTime = semQuramDngDateTimeInfo;
        }
        SemQuramDngDateTimeInfo semQuramDngDateTimeInfo2 = semQuramDngJavaExif.mDateTimeOriginal;
        if (semQuramDngDateTimeInfo2 != null) {
            this.mDateTimeOriginal = semQuramDngDateTimeInfo2;
        }
        SemQuramDngDateTimeInfo semQuramDngDateTimeInfo3 = semQuramDngJavaExif.mDateTimeDigitized;
        if (semQuramDngDateTimeInfo3 != null) {
            this.mDateTimeDigitized = semQuramDngDateTimeInfo3;
        }
    }

    public String getImageDescription() {
        return this.mImageDescription;
    }

    public String getMake() {
        return this.mMake;
    }

    public String getModel() {
        return this.mModel;
    }

    public String getSoftware() {
        return this.mSoftware;
    }

    public String getArtist() {
        return this.mArtist;
    }

    public String getCopyright() {
        return this.mCopyright;
    }

    public String getCopyright2() {
        return this.mCopyright2;
    }

    public String getUserComment() {
        return this.mUserComment;
    }

    public SemQuramDngDateTimeInfo getDateTime() {
        return this.mDateTime;
    }

    public SemQuramDngDateTimeStorageInfo getDateTimeStorageInfo() {
        return this.mDateTimeStorageInfo;
    }

    public SemQuramDngDateTimeInfo getDateTimeOriginal() {
        return this.mDateTimeOriginal;
    }

    public SemQuramDngDateTimeStorageInfo getDateTimeOriginalStorageInfo() {
        return this.mDateTimeOriginalStorageInfo;
    }

    public SemQuramDngDateTimeInfo getDateTimeDigitized() {
        return this.mDateTimeDigitized;
    }

    public SemQuramDngDateTimeStorageInfo getDateTimeDigitizedStorageInfo() {
        return this.mDateTimeDigitizedStorageInfo;
    }

    public long getTIFF_EP_StandardID() {
        return this.mTIFF_EP_StandardID;
    }

    public long getExifVersion() {
        return this.mExifVersion;
    }

    public long getFlashPixVersion() {
        return this.mFlashPixVersion;
    }

    public SemQuramDngUrational getExposureTime() {
        return this.mExposureTime;
    }

    public SemQuramDngUrational getFNumber() {
        return this.mFNumber;
    }

    public SemQuramDngSrational getShutterSpeedValue() {
        return this.mShutterSpeedValue;
    }

    public SemQuramDngUrational getApertureValue() {
        return this.mApertureValue;
    }

    public SemQuramDngSrational getBrightnessValue() {
        return this.mBrightnessValue;
    }

    public SemQuramDngSrational getExposureBiasValue() {
        return this.mExposureBiasValue;
    }

    public SemQuramDngUrational getMaxApertureValue() {
        return this.mMaxApertureValue;
    }

    public SemQuramDngUrational getFocalLength() {
        return this.mFocalLength;
    }

    public SemQuramDngUrational getDigitalZoomRatio() {
        return this.mDigitalZoomRatio;
    }

    public SemQuramDngUrational getExposureIndex() {
        return this.mExposureIndex;
    }

    public SemQuramDngUrational getSubjectDistance() {
        return this.mSubjectDistance;
    }

    public SemQuramDngUrational getGamma() {
        return this.mGamma;
    }

    public SemQuramDngUrational getBatteryLevelR() {
        return this.mBatteryLevelR;
    }

    public String getBatteryLevelA() {
        return this.mBatteryLevelA;
    }

    public long getExposureProgram() {
        return this.mExposureProgram;
    }

    public long getMeteringMode() {
        return this.mMeteringMode;
    }

    public long getLightSource() {
        return this.mLightSource;
    }

    public long getFlash() {
        return this.mFlash;
    }

    public long getFlashMask() {
        return this.mFlashMask;
    }

    public long getSensingMethod() {
        return this.mSensingMethod;
    }

    public long getColorSpace() {
        return this.mColorSpace;
    }

    public long getFileSource() {
        return this.mFileSource;
    }

    public long getSceneType() {
        return this.mSceneType;
    }

    public long getCustomRendered() {
        return this.mCustomRendered;
    }

    public long getExposureMode() {
        return this.mExposureMode;
    }

    public long getWhiteBalance() {
        return this.mWhiteBalance;
    }

    public long getSceneCaptureType() {
        return this.mSceneCaptureType;
    }

    public long getGainControl() {
        return this.mGainControl;
    }

    public long getContrast() {
        return this.mContrast;
    }

    public long getSaturation() {
        return this.mSaturation;
    }

    public long getSharpness() {
        return this.mSharpness;
    }

    public long getSubjectDistanceRange() {
        return this.mSubjectDistanceRange;
    }

    public long getSelfTimerMode() {
        return this.mSelfTimerMode;
    }

    public long getImageNumber() {
        return this.mImageNumber;
    }

    public long getFocalLengthIn35mmFilm() {
        return this.mFocalLengthIn35mmFilm;
    }

    public long getISOSpeedRatings(int i) {
        return this.mISOSpeedRatings[i];
    }

    public long getSensitivityType() {
        return this.mSensitivityType;
    }

    public long getStandardOutputSensitivity() {
        return this.mStandardOutputSensitivity;
    }

    public long getRecommendedExposureIndex() {
        return this.mRecommendedExposureIndex;
    }

    public long getISOSpeed() {
        return this.mISOSpeed;
    }

    public long getISOSpeedLatitudeyyy() {
        return this.mISOSpeedLatitudeyyy;
    }

    public long getISOSpeedLatitudezzz() {
        return this.mISOSpeedLatitudezzz;
    }

    public long getSubjectAreaCount() {
        return this.mSubjectAreaCount;
    }

    public long getSubjectArea(int i) {
        return this.mSubjectArea[i];
    }

    public long getComponentsConfiguration() {
        return this.mComponentsConfiguration;
    }

    public SemQuramDngUrational getCompresssedBitsPerPixel() {
        return this.mCompresssedBitsPerPixel;
    }

    public long getPixelXDimension() {
        return this.mPixelXDimension;
    }

    public long getPixelYDimension() {
        return this.mPixelYDimension;
    }

    public SemQuramDngUrational getFocalPlaneXResolution() {
        return this.mFocalPlaneXResolution;
    }

    public SemQuramDngUrational getFocalPlaneYResolution() {
        return this.mFocalPlaneYResolution;
    }

    public long getFocalPlaneResolutionUnit() {
        return this.mFocalPlaneResolutionUnit;
    }

    public byte getCFAPattern(int i, int i2) {
        return this.mCFAPattern[i][i2];
    }

    public long getCFARepeatPatternRows() {
        return this.mCFARepeatPatternRows;
    }

    public long getCFARepeatPatternCols() {
        return this.mCFARepeatPatternCols;
    }

    public SemQuramDngFingerPrint getImageUniqueID() {
        return this.mImageUniqueID;
    }

    public long getGPSVersionID() {
        return this.mGPSVersionID;
    }

    public String getGPSLatitudeRef() {
        return this.mGPSLatitudeRef;
    }

    public SemQuramDngUrational getGPSLatitude(int i) {
        return this.mGPSLatitude[i];
    }

    public String getGPSLongitudeRef() {
        return this.mGPSLongitudeRef;
    }

    public SemQuramDngUrational getGPSLongitude(int i) {
        return this.mGPSLongitude[i];
    }

    public long getGPSAltitudeRef() {
        return this.mGPSAltitudeRef;
    }

    public SemQuramDngUrational getGPSAltitude() {
        return this.mGPSAltitude;
    }

    public SemQuramDngUrational getGPSTimeStamp(int i) {
        return this.mGPSTimeStamp[i];
    }

    public String getGPSSatellites() {
        return this.mGPSSatellites;
    }

    public String getGPSStatus() {
        return this.mGPSStatus;
    }

    public String getGPSMeasureMode() {
        return this.mGPSMeasureMode;
    }

    public SemQuramDngUrational getGPSDOP() {
        return this.mGPSDOP;
    }

    public String getGPSSpeedRef() {
        return this.mGPSSpeedRef;
    }

    public SemQuramDngUrational getGPSSpeed() {
        return this.mGPSSpeed;
    }

    public String getGPSTrackRef() {
        return this.mGPSTrackRef;
    }

    public SemQuramDngUrational getGPSTrack() {
        return this.mGPSTrack;
    }

    public String getGPSImgDirectionRef() {
        return this.mGPSImgDirectionRef;
    }

    public SemQuramDngUrational getGPSImgDirection() {
        return this.mGPSImgDirection;
    }

    public String getGPSMapDatum() {
        return this.mGPSMapDatum;
    }

    public String getGPSDestLatitudeRef() {
        return this.mGPSDestLatitudeRef;
    }

    public SemQuramDngUrational getGPSDestLatitude(int i) {
        return this.mGPSDestLatitude[i];
    }

    public String getGPSDestLongitudeRef() {
        return this.mGPSDestLongitudeRef;
    }

    public SemQuramDngUrational getGPSDestLongitude(int i) {
        return this.mGPSDestLongitude[i];
    }

    public String getGPSDestBearingRef() {
        return this.mGPSDestBearingRef;
    }

    public SemQuramDngUrational getGPSDestBearing() {
        return this.mGPSDestBearing;
    }

    public String getGPSDestDistanceRef() {
        return this.mGPSDestDistanceRef;
    }

    public SemQuramDngUrational getGPSDestDistance() {
        return this.mGPSDestDistance;
    }

    public String getGPSProcessingMethod() {
        return this.mGPSProcessingMethod;
    }

    public String getGPSAreaInformation() {
        return this.mGPSAreaInformation;
    }

    public String getGPSDateStamp() {
        return this.mGPSDateStamp;
    }

    public long getGPSDifferential() {
        return this.mGPSDifferential;
    }

    public SemQuramDngUrational getGPSHPositioningError() {
        return this.mGPSHPositioningError;
    }

    public String getInteroperabilityIndex() {
        return this.mInteroperabilityIndex;
    }

    public long getInteroperabilityVersion() {
        return this.mInteroperabilityVersion;
    }

    public String getRelatedImageFileFormat() {
        return this.mRelatedImageFileFormat;
    }

    public long getRelatedImageWidth() {
        return this.mRelatedImageWidth;
    }

    public long getRelatedImageLength() {
        return this.mRelatedImageLength;
    }

    public String getCameraSerialNumber() {
        return this.mCameraSerialNumber;
    }

    public SemQuramDngUrational getLensInfo(int i) {
        return this.mLensInfo[i];
    }

    public String getLensID() {
        return this.mLensID;
    }

    public String getLensMake() {
        return this.mLensMake;
    }

    public String getLensName() {
        return this.mLensName;
    }

    public String getLensSerialNumber() {
        return this.mLensSerialNumber;
    }

    public boolean ismLensNameWasReadFromExif() {
        return this.mLensNameWasReadFromExif;
    }

    public SemQuramDngUrational getApproxFocusDistance() {
        return this.mApproxFocusDistance;
    }

    public SemQuramDngUrational getFlashCompensation() {
        return this.mFlashCompensation;
    }

    public String getOwnerName() {
        return this.mOwnerName;
    }

    public String getFirmware() {
        return this.mFirmware;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public SemQuramDngSrational getLensDistortInfo(int i) {
        return this.mLensDistortInfo[i];
    }
}
