package com.android.systemui.keyboard.shortcut.data.repository;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Icon;
import android.hardware.input.AppLaunchData;
import android.hardware.input.InputGestureData;
import android.view.InputDevice;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.keyboard.shortcut.data.model.InternalGroupsSource;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutGroup;
import com.android.systemui.keyboard.shortcut.data.model.InternalKeyboardShortcutInfo;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategory;
import com.android.systemui.keyboard.shortcut.shared.model.ShortcutCategoryType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
final class CustomShortcutCategoriesRepository$categories$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ CustomShortcutCategoriesRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomShortcutCategoriesRepository$categories$1(CustomShortcutCategoriesRepository customShortcutCategoriesRepository, Continuation continuation) {
        super(3, continuation);
        this.this$0 = customShortcutCategoriesRepository;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        CustomShortcutCategoriesRepository$categories$1 customShortcutCategoriesRepository$categories$1 = new CustomShortcutCategoriesRepository$categories$1(this.this$0, (Continuation) obj3);
        customShortcutCategoriesRepository$categories$1.L$0 = (InputDevice) obj;
        customShortcutCategoriesRepository$categories$1.L$1 = (List) obj2;
        return customShortcutCategoriesRepository$categories$1.invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01cf  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        InputDevice inputDevice;
        Object objWithContext;
        List<InternalGroupsSource> list;
        String str;
        String string;
        Integer numValueOf;
        Set<String> categories;
        Iterator<String> it;
        InternalKeyboardShortcutInfo internalKeyboardShortcutInfo;
        Icon iconCreateWithResource;
        ActivityInfo activityInfoResolveSingleMatchingActivityFrom;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            inputDevice = (InputDevice) this.L$0;
            List list2 = (List) this.L$1;
            if (inputDevice == null) {
                return EmptyList.INSTANCE;
            }
            InputGestureDataAdapter inputGestureDataAdapter = this.this$0.inputGestureDataAdapter;
            inputGestureDataAdapter.getClass();
            ArrayList arrayList = new ArrayList();
            Iterator it2 = list2.iterator();
            while (true) {
                InternalGroupsSource internalGroupsSource = null;
                if (it2.hasNext()) {
                    InputGestureData inputGestureData = (InputGestureData) it2.next();
                    InputGestureData.KeyTrigger trigger = inputGestureData.getTrigger();
                    int iKeyGestureType = inputGestureData.getAction().keyGestureType();
                    AppLaunchData appLaunchData = inputGestureData.getAction().appLaunchData();
                    InputGestureMaps inputGestureMaps = inputGestureDataAdapter.inputGestureMaps;
                    Integer num = (Integer) inputGestureMaps.gestureToInternalKeyboardShortcutGroupLabelResIdMap.get(Integer.valueOf(iKeyGestureType));
                    String string2 = num != null ? inputGestureDataAdapter.context.getString(num.intValue()) : null;
                    if (string2 != null) {
                        Integer num2 = (Integer) inputGestureMaps.gestureToInternalKeyboardShortcutInfoLabelResIdMap.get(Integer.valueOf(iKeyGestureType));
                        if (num2 != null) {
                            string = inputGestureDataAdapter.context.getString(num2.intValue());
                        } else {
                            if (iKeyGestureType == 51) {
                                appLaunchData.getClass();
                                Intent intentFetchIntentFromAppLaunchData = inputGestureDataAdapter.fetchIntentFromAppLaunchData(appLaunchData);
                                if (intentFetchIntentFromAppLaunchData != null) {
                                    ActivityInfo activityInfoResolveSingleMatchingActivityFrom2 = inputGestureDataAdapter.resolveSingleMatchingActivityFrom(intentFetchIntentFromAppLaunchData);
                                    if (activityInfoResolveSingleMatchingActivityFrom2 == null) {
                                        Intent selector = intentFetchIntentFromAppLaunchData.getSelector();
                                        String next = (selector == null || (categories = selector.getCategories()) == null || (it = categories.iterator()) == null) ? null : it.next();
                                        String strValueOf = String.valueOf(next);
                                        switch (strValueOf.hashCode()) {
                                            case -2061526830:
                                                if (strValueOf.equals("android.intent.category.APP_MAPS")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_maps);
                                                    break;
                                                } else {
                                                    MotionLayout$$ExternalSyntheticOutline0.m("No label for app category ", next, "InputGestureDataUtils");
                                                    numValueOf = null;
                                                    break;
                                                }
                                            case -1895059731:
                                                if (strValueOf.equals("android.intent.category.APP_BROWSER")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_browser);
                                                    break;
                                                }
                                                break;
                                            case -551167607:
                                                if (strValueOf.equals("android.intent.category.APP_MESSAGING")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_sms);
                                                    break;
                                                }
                                                break;
                                            case 431052046:
                                                if (strValueOf.equals("android.intent.category.APP_CONTACTS")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_contacts);
                                                    break;
                                                }
                                                break;
                                            case 510132385:
                                                if (strValueOf.equals("android.intent.category.APP_EMAIL")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_email);
                                                    break;
                                                }
                                                break;
                                            case 517776170:
                                                if (strValueOf.equals("android.intent.category.APP_MUSIC")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_music);
                                                    break;
                                                }
                                                break;
                                            case 645732605:
                                                if (strValueOf.equals("android.intent.category.APP_CALCULATOR")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_calculator);
                                                    break;
                                                }
                                                break;
                                            case 820178937:
                                                if (strValueOf.equals("android.intent.category.APP_CALENDAR")) {
                                                    numValueOf = Integer.valueOf(R.string.keyboard_shortcut_group_applications_calendar);
                                                    break;
                                                }
                                                break;
                                        }
                                        if (numValueOf != null) {
                                            string = inputGestureDataAdapter.context.getString(numValueOf.intValue());
                                        }
                                    } else {
                                        string = activityInfoResolveSingleMatchingActivityFrom2.loadLabel(inputGestureDataAdapter.getUserContext().getPackageManager()).toString();
                                    }
                                }
                            }
                            str = null;
                            if (str == null) {
                                int keycode = trigger.getKeycode();
                                int modifierState = trigger.getModifierState();
                                if (appLaunchData != null) {
                                    Intent intentFetchIntentFromAppLaunchData2 = inputGestureDataAdapter.fetchIntentFromAppLaunchData(appLaunchData);
                                    iconCreateWithResource = (intentFetchIntentFromAppLaunchData2 == null || (activityInfoResolveSingleMatchingActivityFrom = inputGestureDataAdapter.resolveSingleMatchingActivityFrom(intentFetchIntentFromAppLaunchData2)) == null) ? null : Icon.createWithResource(inputGestureDataAdapter.context, activityInfoResolveSingleMatchingActivityFrom.getIconResource());
                                } else {
                                    iconCreateWithResource = null;
                                }
                                internalKeyboardShortcutInfo = new InternalKeyboardShortcutInfo(str, keycode, modifierState, (char) 0, iconCreateWithResource, true, 8, null);
                            } else {
                                internalKeyboardShortcutInfo = null;
                            }
                            if (internalKeyboardShortcutInfo != null) {
                                InternalKeyboardShortcutGroup internalKeyboardShortcutGroup = new InternalKeyboardShortcutGroup(string2, Collections.singletonList(internalKeyboardShortcutInfo), null, 4, null);
                                ShortcutCategoryType shortcutCategoryType = (ShortcutCategoryType) inputGestureMaps.gestureToShortcutCategoryTypeMap.get(Integer.valueOf(iKeyGestureType));
                                if (shortcutCategoryType != null) {
                                    internalGroupsSource = new InternalGroupsSource(Collections.singletonList(internalKeyboardShortcutGroup), shortcutCategoryType);
                                }
                            }
                        }
                        str = string;
                        if (str == null) {
                        }
                        if (internalKeyboardShortcutInfo != null) {
                        }
                    }
                    if (internalGroupsSource != null) {
                        arrayList.add(internalGroupsSource);
                    }
                } else {
                    ShortcutCategoriesUtils shortcutCategoriesUtils = this.this$0.shortcutCategoriesUtils;
                    int id = inputDevice.getId();
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj2 = arrayList.get(i2);
                        i2++;
                        arrayList2.add(((InternalGroupsSource) obj2).groups);
                    }
                    this.L$0 = inputDevice;
                    this.L$1 = arrayList;
                    this.label = 1;
                    shortcutCategoriesUtils.getClass();
                    objWithContext = BuildersKt.withContext(shortcutCategoriesUtils.backgroundCoroutineContext, new ShortcutCategoriesUtils$fetchSupportedKeyCodes$2(arrayList2, shortcutCategoriesUtils, id, null), this);
                    if (objWithContext == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    list = arrayList;
                }
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = (List) this.L$1;
            inputDevice = (InputDevice) this.L$0;
            ResultKt.throwOnFailure(obj);
            objWithContext = obj;
        }
        Set set = (Set) objWithContext;
        CustomShortcutCategoriesRepository customShortcutCategoriesRepository = this.this$0;
        ArrayList arrayList3 = new ArrayList();
        for (InternalGroupsSource internalGroupsSource2 : list) {
            ShortcutCategory shortcutCategoryFetchShortcutCategory = customShortcutCategoriesRepository.shortcutCategoriesUtils.fetchShortcutCategory(internalGroupsSource2.type, internalGroupsSource2.groups, inputDevice, set);
            if (shortcutCategoryFetchShortcutCategory != null) {
                arrayList3.add(shortcutCategoryFetchShortcutCategory);
            }
        }
        return arrayList3;
    }
}
