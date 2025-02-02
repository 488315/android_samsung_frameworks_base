package com.android.server.pm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class DefaultCrossProfileIntentFiltersUtils {
  public static final DefaultCrossProfileIntentFilter EMERGENCY_CALL_MIME =
      new DefaultCrossProfileIntentFilter.Builder(0, 2, false)
          .addAction("android.intent.action.CALL_EMERGENCY")
          .addAction("android.intent.action.CALL_PRIVILEGED")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataType("vnd.android.cursor.item/phone")
          .addDataType("vnd.android.cursor.item/phone_v2")
          .addDataType("vnd.android.cursor.item/person")
          .addDataType("vnd.android.cursor.dir/calls")
          .addDataType("vnd.android.cursor.item/calls")
          .build();
  public static final DefaultCrossProfileIntentFilter EMERGENCY_CALL_DATA =
      new DefaultCrossProfileIntentFilter.Builder(0, 2, false)
          .addAction("android.intent.action.CALL_EMERGENCY")
          .addAction("android.intent.action.CALL_PRIVILEGED")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("tel")
          .addDataScheme("sip")
          .addDataScheme("voicemail")
          .build();
  public static final DefaultCrossProfileIntentFilter DIAL_MIME =
      new DefaultCrossProfileIntentFilter.Builder(0, 4, false)
          .addAction("android.intent.action.DIAL")
          .addAction("android.intent.action.VIEW")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataType("vnd.android.cursor.item/phone")
          .addDataType("vnd.android.cursor.item/phone_v2")
          .addDataType("vnd.android.cursor.item/person")
          .addDataType("vnd.android.cursor.dir/calls")
          .addDataType("vnd.android.cursor.item/calls")
          .build();
  public static final DefaultCrossProfileIntentFilter DIAL_MIME_MANAGED_PROFILE =
      new DefaultCrossProfileIntentFilter.Builder(1, 2, false)
          .addAction("android.intent.action.DIAL")
          .addAction("android.intent.action.VIEW")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataType("vnd.android.cursor.item/phone")
          .addDataType("vnd.android.cursor.item/phone_v2")
          .addDataType("vnd.android.cursor.item/person")
          .addDataType("vnd.android.cursor.dir/calls")
          .addDataType("vnd.android.cursor.item/calls")
          .build();
  public static final DefaultCrossProfileIntentFilter DIAL_DATA =
      new DefaultCrossProfileIntentFilter.Builder(0, 4, false)
          .addAction("android.intent.action.DIAL")
          .addAction("android.intent.action.VIEW")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("tel")
          .addDataScheme("sip")
          .addDataScheme("voicemail")
          .build();
  public static final DefaultCrossProfileIntentFilter DIAL_DATA_MANAGED_PROFILE =
      new DefaultCrossProfileIntentFilter.Builder(1, 2, false)
          .addAction("android.intent.action.DIAL")
          .addAction("android.intent.action.VIEW")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("tel")
          .addDataScheme("sip")
          .addDataScheme("voicemail")
          .build();
  public static final DefaultCrossProfileIntentFilter DIAL_RAW =
      new DefaultCrossProfileIntentFilter.Builder(0, 4, false)
          .addAction("android.intent.action.DIAL")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .build();
  public static final DefaultCrossProfileIntentFilter DIAL_RAW_MANAGED_PROFILE =
      new DefaultCrossProfileIntentFilter.Builder(1, 2, false)
          .addAction("android.intent.action.DIAL")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .build();
  public static final DefaultCrossProfileIntentFilter CALL_BUTTON =
      new DefaultCrossProfileIntentFilter.Builder(0, 4, false)
          .addAction("android.intent.action.CALL_BUTTON")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter SMS_MMS =
      new DefaultCrossProfileIntentFilter.Builder(0, 2, false)
          .addAction("android.intent.action.VIEW")
          .addAction("android.intent.action.SENDTO")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("sms")
          .addDataScheme("smsto")
          .addDataScheme("mms")
          .addDataScheme("mmsto")
          .build();
  public static final DefaultCrossProfileIntentFilter SMS_MMS_MANAGED_PROFILE =
      new DefaultCrossProfileIntentFilter.Builder(1, 2, false)
          .addAction("android.intent.action.VIEW")
          .addAction("android.intent.action.SENDTO")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("sms")
          .addDataScheme("smsto")
          .addDataScheme("mms")
          .addDataScheme("mmsto")
          .build();
  public static final DefaultCrossProfileIntentFilter MOBILE_NETWORK_SETTINGS =
      new DefaultCrossProfileIntentFilter.Builder(0, 2, false)
          .addAction("android.settings.DATA_ROAMING_SETTINGS")
          .addAction("android.settings.NETWORK_OPERATOR_SETTINGS")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter HOME =
      new DefaultCrossProfileIntentFilter.Builder(0, 2, false)
          .addAction("android.intent.action.MAIN")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.HOME")
          .build();
  public static final DefaultCrossProfileIntentFilter GET_CONTENT =
      new DefaultCrossProfileIntentFilter.Builder(0, 0, true)
          .addAction("android.intent.action.GET_CONTENT")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.OPENABLE")
          .addDataType("*/*")
          .build();
  public static final DefaultCrossProfileIntentFilter ACTION_PICK_IMAGES =
      new DefaultCrossProfileIntentFilter.Builder(0, 0, true)
          .addAction("android.provider.action.PICK_IMAGES")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter ACTION_PICK_IMAGES_WITH_DATA_TYPES =
      new DefaultCrossProfileIntentFilter.Builder(0, 0, true)
          .addAction("android.provider.action.PICK_IMAGES")
          .addCategory("android.intent.category.DEFAULT")
          .addDataType("image/*")
          .addDataType("video/*")
          .build();
  public static final DefaultCrossProfileIntentFilter OPEN_DOCUMENT =
      new DefaultCrossProfileIntentFilter.Builder(0, 0, true)
          .addAction("android.intent.action.OPEN_DOCUMENT")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.OPENABLE")
          .addDataType("*/*")
          .build();
  public static final DefaultCrossProfileIntentFilter ACTION_PICK_DATA =
      new DefaultCrossProfileIntentFilter.Builder(0, 0, true)
          .addAction("android.intent.action.PICK")
          .addCategory("android.intent.category.DEFAULT")
          .addDataType("*/*")
          .build();
  public static final DefaultCrossProfileIntentFilter ACTION_PICK_RAW =
      new DefaultCrossProfileIntentFilter.Builder(0, 0, true)
          .addAction("android.intent.action.PICK")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter RECOGNIZE_SPEECH =
      new DefaultCrossProfileIntentFilter.Builder(0, 4, false)
          .addAction("android.speech.action.RECOGNIZE_SPEECH")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter MEDIA_CAPTURE =
      new DefaultCrossProfileIntentFilter.Builder(0, 4, true)
          .addAction("android.media.action.IMAGE_CAPTURE")
          .addAction("android.media.action.IMAGE_CAPTURE_SECURE")
          .addAction("android.media.action.VIDEO_CAPTURE")
          .addAction("android.provider.MediaStore.RECORD_SOUND")
          .addAction("android.media.action.STILL_IMAGE_CAMERA")
          .addAction("android.media.action.STILL_IMAGE_CAMERA_SECURE")
          .addAction("android.media.action.VIDEO_CAMERA")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter SET_ALARM =
      new DefaultCrossProfileIntentFilter.Builder(0, 0, false)
          .addAction("android.intent.action.SET_ALARM")
          .addAction("android.intent.action.SHOW_ALARMS")
          .addAction("android.intent.action.SET_TIMER")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter ACTION_SEND =
      new DefaultCrossProfileIntentFilter.Builder(1, 0, true)
          .addAction("android.intent.action.SEND")
          .addAction("android.intent.action.SEND_MULTIPLE")
          .addCategory("android.intent.category.DEFAULT")
          .addDataType("*/*")
          .build();
  public static final DefaultCrossProfileIntentFilter USB_DEVICE_ATTACHED =
      new DefaultCrossProfileIntentFilter.Builder(1, 0, false)
          .addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED")
          .addAction("android.hardware.usb.action.USB_ACCESSORY_ATTACHED")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter CALL_MANAGED_PROFILE =
      new DefaultCrossProfileIntentFilter.Builder(1, 2, false)
          .addAction("android.intent.action.CALL")
          .addCategory("android.intent.category.DEFAULT")
          .addDataScheme("tel")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_MEDIA_CAPTURE =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.media.action.IMAGE_CAPTURE")
          .addAction("android.media.action.IMAGE_CAPTURE_SECURE")
          .addAction("android.media.action.VIDEO_CAPTURE")
          .addAction("android.provider.MediaStore.RECORD_SOUND")
          .addAction("android.media.action.STILL_IMAGE_CAMERA")
          .addAction("android.media.action.STILL_IMAGE_CAMERA_SECURE")
          .addAction("android.media.action.VIDEO_CAMERA")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_PHOTOPICKER_SELECTION =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.provider.action.USER_SELECT_IMAGES_FOR_APP")
          .addCategory("android.intent.category.DEFAULT")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_SEND_ACTION =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.intent.action.SEND")
          .addAction("android.intent.action.SEND_MULTIPLE")
          .addAction("android.intent.action.SENDTO")
          .addDataType("*/*")
          .build();
  public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_SEND_ACTION =
      new DefaultCrossProfileIntentFilter.Builder(1, 24, false)
          .addAction("android.intent.action.SEND")
          .addAction("android.intent.action.SEND_MULTIPLE")
          .addAction("android.intent.action.SENDTO")
          .addDataType("*/*")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_WEB_VIEW_ACTION =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.intent.action.VIEW")
          .addDataScheme("https")
          .addDataScheme("http")
          .build();
  public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_WEB_VIEW_ACTION =
      new DefaultCrossProfileIntentFilter.Builder(1, 24, false)
          .addAction("android.intent.action.VIEW")
          .addDataScheme("https")
          .addDataScheme("http")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_VIEW_ACTION =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.intent.action.VIEW")
          .addDataType("*/*")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_PICK_INSERT_ACTION =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.intent.action.PICK")
          .addAction("android.intent.action.GET_CONTENT")
          .addAction("android.intent.action.EDIT")
          .addAction("android.intent.action.INSERT")
          .addAction("android.intent.action.INSERT_OR_EDIT")
          .addAction("android.intent.action.OPEN_DOCUMENT")
          .addDataType("*/*")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.OPENABLE")
          .build();
  public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_PICK_INSERT_ACTION =
      new DefaultCrossProfileIntentFilter.Builder(1, 24, false)
          .addAction("android.intent.action.PICK")
          .addAction("android.intent.action.GET_CONTENT")
          .addAction("android.intent.action.EDIT")
          .addAction("android.intent.action.INSERT")
          .addAction("android.intent.action.INSERT_OR_EDIT")
          .addAction("android.intent.action.OPEN_DOCUMENT")
          .addDataType("*/*")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.OPENABLE")
          .build();
  public static final DefaultCrossProfileIntentFilter PARENT_TO_CLONE_DIAL_DATA =
      new DefaultCrossProfileIntentFilter.Builder(1, 24, false)
          .addAction("android.intent.action.DIAL")
          .addAction("android.intent.action.VIEW")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("tel")
          .addDataScheme("sip")
          .addDataScheme("voicemail")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_DIAL_DATA =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.intent.action.DIAL")
          .addAction("android.intent.action.VIEW")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("tel")
          .addDataScheme("sip")
          .addDataScheme("voicemail")
          .build();
  public static final DefaultCrossProfileIntentFilter CLONE_TO_PARENT_SMS_MMS =
      new DefaultCrossProfileIntentFilter.Builder(0, 24, false)
          .addAction("android.intent.action.VIEW")
          .addAction("android.intent.action.SENDTO")
          .addCategory("android.intent.category.DEFAULT")
          .addCategory("android.intent.category.BROWSABLE")
          .addDataScheme("sms")
          .addDataScheme("smsto")
          .addDataScheme("mms")
          .addDataScheme("mmsto")
          .build();

  public static List getDefaultManagedProfileFilters() {
    ArrayList arrayList = new ArrayList();
    arrayList.addAll(
        Arrays.asList(
            EMERGENCY_CALL_MIME,
            EMERGENCY_CALL_DATA,
            CALL_BUTTON,
            SET_ALARM,
            MEDIA_CAPTURE,
            RECOGNIZE_SPEECH,
            ACTION_PICK_RAW,
            ACTION_PICK_DATA,
            ACTION_PICK_IMAGES,
            ACTION_PICK_IMAGES_WITH_DATA_TYPES,
            OPEN_DOCUMENT,
            GET_CONTENT,
            USB_DEVICE_ATTACHED,
            ACTION_SEND,
            HOME,
            MOBILE_NETWORK_SETTINGS));
    arrayList.addAll(getDefaultCrossProfileTelephonyIntentFilters(false));
    return arrayList;
  }

  public static List getDefaultCrossProfileTelephonyIntentFilters(boolean z) {
    if (z) {
      return Arrays.asList(
          DIAL_DATA_MANAGED_PROFILE,
          DIAL_MIME_MANAGED_PROFILE,
          DIAL_RAW_MANAGED_PROFILE,
          CALL_MANAGED_PROFILE,
          SMS_MMS_MANAGED_PROFILE);
    }
    return Arrays.asList(DIAL_DATA, DIAL_MIME, DIAL_RAW, SMS_MMS);
  }

  public static List getDefaultCloneProfileFilters() {
    return Arrays.asList(
        PARENT_TO_CLONE_SEND_ACTION,
        PARENT_TO_CLONE_WEB_VIEW_ACTION,
        PARENT_TO_CLONE_PICK_INSERT_ACTION,
        PARENT_TO_CLONE_DIAL_DATA,
        CLONE_TO_PARENT_MEDIA_CAPTURE,
        CLONE_TO_PARENT_SEND_ACTION,
        CLONE_TO_PARENT_WEB_VIEW_ACTION,
        CLONE_TO_PARENT_VIEW_ACTION,
        CLONE_TO_PARENT_PICK_INSERT_ACTION,
        CLONE_TO_PARENT_DIAL_DATA,
        CLONE_TO_PARENT_SMS_MMS,
        CLONE_TO_PARENT_PHOTOPICKER_SELECTION);
  }
}
