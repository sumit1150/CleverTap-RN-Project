package com.myproject;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.clevertap.android.sdk.pushnotification.CTPushNotificationListener;
import com.clevertap.react.CleverTapApplication;
import com.clevertap.react.CleverTapModule;
import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.soloader.SoLoader;
import com.myproject.newarchitecture.MainApplicationReactNativeHost;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.react.CleverTapPackage;
import com.clevertap.android.sdk.CleverTapAPI;

import org.json.JSONObject;

//CTPushNotificationListener
public class MainApplication extends CleverTapApplication implements ReactApplication {

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
        //  packages.add(new CleverTapPackage());// only needed when not auto-linking
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
      };

  private final ReactNativeHost mNewArchitectureNativeHost =
      new MainApplicationReactNativeHost(this);

  @Override
  public ReactNativeHost getReactNativeHost() {
    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      return mNewArchitectureNativeHost;
    } else {
      return mReactNativeHost;
    }
  }

  @Override
  public void onCreate() {
     ActivityLifecycleCallback.register(this);
     //I already put this channel in React native
      //Objects.requireNonNull(CleverTapAPI.getDefaultInstance(getApplicationContext())).createNotificationChannel(this,"General","General","General Channel",5,true);
      // The notification channel importance can have any value from 1 to 5. A higher value means a more interruptive notification.
    super.onCreate();
    // If you opted-in for the New Architecture, we enable the TurboModule system
    ReactFeatureFlags.useTurboModules = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());

    //
      //CleverTapModule.setInitialUri(getIn.getData());

     // CleverTapAPI.getDefaultInstance(this).setCTPushNotificationListener(this);
  }

  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.myproject.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }

    /*@Override
    public void onNotificationClickedPayloadReceived(HashMap<String, Object> payload) {

      System.out.println("Noti_payload: "+payload.toString());

        Log.e("MainApplication", "onNotificationClickedPayloadReceived called");

        final String CLEVERTAP_PUSH_NOTIFICATION_CLICKED = "CleverTapPushNotificationClicked";

        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            public void run() {

                // Construct and load our normal React JS code bundle

                final ReactInstanceManager mReactInstanceManager =

                        ((ReactApplication) getApplicationContext())

                                .getReactNativeHost().getReactInstanceManager();

                ReactContext context = mReactInstanceManager.getCurrentReactContext();

                // If it’s constructed, send a notification

                if (context != null) {

                    sendEvent(CLEVERTAP_PUSH_NOTIFICATION_CLICKED,getWritableMapFromMap(payload),context);

                } else {

                    // Otherwise wait for construction, then send the notification

                    mReactInstanceManager.addReactInstanceEventListener(

                            new ReactInstanceManager.ReactInstanceEventListener() {

                                public void onReactContextInitialized(ReactContext context) {

                                    sendEvent(CLEVERTAP_PUSH_NOTIFICATION_CLICKED

                                            ,getWritableMapFromMap(payload),context);

                                    mReactInstanceManager.removeReactInstanceEventListener(this);

                                }

                            });

                    if (!mReactInstanceManager.hasStartedCreatingInitialContext()) {

                        // Construct it in the background

                        mReactInstanceManager.createReactContextInBackground();

                    }

                }

            }

        });

    }*/


  /*  private void sendEvent(String eventName, Object params,ReactContext context) {

        try {

            context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)

                    .emit(eventName, params);

        } catch (Throwable t) {

            Log.e("Tag", t.getLocalizedMessage());

        }

    }

    private WritableMap getWritableMapFromMap(Map<String, ? extends Object> var1) {

        JSONObject extras = var1 != null ? new JSONObject(var1) : new JSONObject();

        WritableMap extrasParams = Arguments.createMap();

        Iterator extrasKeys = extras.keys();

        while (extrasKeys.hasNext()) {

            String key = null;

            String value = null;

            try {

                key = extrasKeys.next().toString();

                value = extras.get(key).toString();

            } catch (Throwable t) {

                Log.e("Tag", t.getLocalizedMessage());

            }

            if (key != null && value != null) {

                extrasParams.putString(key, value);

            }

        }

        return extrasParams;

    }*/
}
