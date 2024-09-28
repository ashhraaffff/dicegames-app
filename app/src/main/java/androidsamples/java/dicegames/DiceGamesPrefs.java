package androidsamples.java.dicegames;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class DiceGamesPrefs {
    private static final String TAG = "DiceGamePrefs";
    private static final String PREF_KEY_BALANCE = "PREFS_KEY_BALANCE";
    private static final String SHARED_PREF_FILE = "androidsamples.java.dicegames.SHARED_PREF_FILE";

    private static SharedPreferences getSharedPrefs(@NonNull Context context){
         return context.getSharedPreferences(SHARED_PREF_FILE,Context.MODE_PRIVATE);
    }

    static int balance(@NonNull Context context){
            SharedPreferences prefs = getSharedPrefs(context);
            int balance = prefs.getInt(PREF_KEY_BALANCE,0);
            Log.d(TAG,"Retrieving Balance: "+balance);
            return balance;
    }

    static void setBalance(@NotNull Context context, int balance){
        SharedPreferences prefs = getSharedPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_KEY_BALANCE,balance);
        editor.apply();
        Log.d(TAG,"Storing Balance: "+balance);
    }
}
