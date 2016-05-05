package in.pathri.nightmode;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.Menu;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
private AudioManager audio;
  private static TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);                
//                 audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
      
//                 audio.setStreamVolume(AudioManager.STREAM_MUSIC,0,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
//       audio.setStreamVolume(AudioManager.STREAM_NOTIFICATION,0,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
//         audio.setStreamVolume(AudioManager.STREAM_RING,0,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
      
//       boolean isVibrateOnTouchEnabled = Settings.System.getInt(getContentResolver(),
//                             Settings.System.HAPTIC_FEEDBACK_ENABLED, 1) != 0;
      
// if(isVibrateOnTouchEnabled) {
// Settings.System.putInt(getContentResolver(), Settings.System.HAPTIC_FEEDBACK_ENABLED, 0);
// }
//       ContentResolver cResolver  = this.getApplicationContext().getContentResolver();
//       Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, 0);
//       Settings.System.putInt(cResolver, Settings.System.ACCELEROMETER_ROTATION, 1);

// LayoutParams layoutpars = getWindow().getAttributes();

// layoutpars.screenBrightness = 0 / (float)255;

// getWindow().setAttributes(layoutpars);

      textView = (TextView) findViewById(R.id.status_view);
      updateStatusText("Initial text");
  
            
try{
// Context myContext = createPackageContext("com.syntellia.fleksy.keyboard",Context.MODE_WORLD_WRITEABLE); // where com.example is the owning  app containing the preferences
  try{
//     SharedPreferences testPrefs = myContext.getSharedPreferences("keyboard_preferences", Context.MODE_WORLD_READABLE); 
//  SharedPreferences testPrefs =  PreferenceManager.getDefaultSharedPreferences(myContext);

    Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "chmod 777 /data/data/com.syntellia.fleksy.keyboard/shared_prefs/com.syntellia.fleksy.keyboard_preferences.xml" });
proc.waitFor();
// proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "chmod 777 /data/data/package_name/shared_prefs" });
// proc.waitFor();
// proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "chmod 777 /data/data/package_name" });
// proc.waitFor();

File preffile = new File("/data/data/com.syntellia.fleksy.keyboard/shared_prefs/com.syntellia.fleksy.keyboard_preferences.xml");

Class prefimplclass = Class.forName("android.app.SharedPreferencesImpl");

Constructor prefimplconstructor = prefimplclass.getDeclaredConstructor(File.class,int.class);
prefimplconstructor.setAccessible(true);

Object prefimpl = prefimplconstructor.newInstance(preffile,Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);

Boolean buttonVal = (Boolean) prefimplclass.getMethod("contains").invoke(prefimpl,"vibBtnBox");
    updateStatusText("Button Present" + ":" + buttonVal);
    Boolean keyVal = (Boolean) prefimplclass.getMethod("contains").invoke(prefimpl,"vibKeyBox");
    updateStatusText("Key Present" + ":" + keyVal);
    
Editor editor = (Editor) prefimplclass.getMethod("edit").invoke(prefimpl);

    editor.putBoolean("vibBtnBox", false);
    editor.putBoolean("vibKeyBox", false);
    
    
boolean retval = editor.commit();
 updateStatusText("Commit-Status" + ":" + retval);
//     Map<String, ?> items = testPrefs.getAll();
//     updateStatusText("MapSize" + ":" + items.size());
// for(String s : items.keySet()){
//     String value=  items.get(s).toString();
//   updateStatusText(s + ":" + value);
//     }

    
  } catch(Exception e){
  updateStatusText("Exception-pkgPref" + ":" + e.getMessage());
}
} catch(Exception e){
  updateStatusText("Exception-context" + ":" + e.getMessage());
}
  


//       vibBtnBox
//         vibKeyBox
//         keyboard_preferences

     
//       com.syntellia.fleksy.keyboard
//       final ArrayList<HashMap<String,String>> LIST = new ArrayList<HashMap<String,String>>();
// Context myContext = createPackageContext("com.example", 
// Context.MODE_WORLD_WRITEABLE); // where com.example is the owning  app containing the preferences
//   SharedPreferences testPrefs = myContext.getSharedPreferences 
// ("test_prefs", Context.MODE_WORLD_READABLE); 


// Map<String, ?> items = testPrefs .getAll();
// for(String s : items.keySet()){
//     //do somthing like String value=  items.get(s).toString());
//     }
//       vibBtnBox
//         vibKeyBox
//         keyboard_preferences
      
    }


  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
  private  static void updateStatusText(String txt){
    String temp = textView.getText().toString();
     textView.setText(temp + "|" + txt);
  }
}

