package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.bignerdranch.android.CLearning.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 直接调用该类的SQLiterDatabase返回一个SQLiteDatabase就可以对
 * raw中PACKAGE_NAME的数据库进行操作
 */


public class DBUtil {
  private static SQLiteDatabase database;
  public static final String DATABASE_FILENAME = "test.db";
  public static final String PACKAGE_NAME = "com.bignerdranch.android.CLearning";
  public static final String DATABASE_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;

  public static SQLiteDatabase openDatabase(Context context) {
    try {
      String databaseFilename = DATABASE_PATH + "/" + DATABASE_FILENAME;
      File dir = new File(DATABASE_PATH);
      if (!dir.exists()) {
        dir.mkdir();
      }
      if (!(new File(databaseFilename)).exists()) {
        InputStream is = context.getResources().openRawResource(R.raw.test);
        FileOutputStream fos = new FileOutputStream(databaseFilename);
        byte[] buffer = new byte[8192];
        int count = 0;
        while ((count = is.read(buffer)) > 0) {
          fos.write(buffer, 0, count);
        }
        fos.close();
        is.close();
      }
      database = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
      return database;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
